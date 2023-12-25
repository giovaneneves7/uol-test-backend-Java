package com.github.giovaneneves7.api.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Valor que determina se a 'stackTrace' deve ser mostrada no client.
     */
    @Value(value = "${server.error.include-exception}")
    private boolean printStackTrace;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação. Veja o campo de 'erros' para saber mais detalhes.");

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);

    }

    /**
     *
     * Trata exceções genericas
     *
     * @param methodArgumentNotValidException - O argumento inválido.
     * @param headers - Os cabeçalhos da requisição HTTP.
     * @param status - O código de status da requisição HTTP.
     * @param request - A requisição HTTP.
     * @return uma entidade generica com a mensagem de erro.
     */

    /**
     *
     * Trata a exceção 'Exception'.
     *
     * @param exception - A instância de 'Exception'.
     * @param request - A requisição HTTP.
     * @return uma entidade generica com a mensagem de erro.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            final Exception exception,
            final WebRequest request) {

        final String mensagemErro = "Ocorreu um erro inesperado";

        log.error(mensagemErro, exception);

        return construirMensagemDeErro(
                exception,
                mensagemErro,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }


    /**
     *
     * Trata a exceção 'BusinessException'.
     *
     * @param businessException - A instância de 'BusinessException'.
     * @param request - A requisição HTTP.
     * @return uma entidade generica com a mensagem de erro.
     */
    @ExceptionHandler(InvalidGroupException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBusinessException(
            final InvalidGroupException businessException,
            final WebRequest request) {

        final String mensagemErro = businessException.getMessage();

        log.error(mensagemErro, businessException);

        return construirMensagemDeErro(
                businessException,
                mensagemErro,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    /**
     * Constroi uma mensagem de erro.
     *
     * @param exception  A exceção.
     * @param message    A mensagem de exceção.
     * @param httpStatus O status da requisição.
     * @param request    A requisição.
     * @return a mensagem de erro.
     */
    private ResponseEntity<Object> construirMensagemDeErro(
            final Exception exception,
            final String message,
            final HttpStatus httpStatus,
            final WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (this.printStackTrace) {
            errorResponse.setStacktrace(ExceptionUtils.getStackTrace(exception));
        }

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}