package com.github.giovaneneves7.api.entity.jogador.service;

import com.github.giovaneneves7.api.entity.jogador.dto.JogadorGetResponseDto;
import com.github.giovaneneves7.api.entity.jogador.dto.JogadorPostResponseDto;
import com.github.giovaneneves7.api.entity.jogador.model.Jogador;
import com.github.giovaneneves7.api.entity.jogador.repository.IJogadorRepository;


import com.github.giovaneneves7.api.infrastructure.exception.InvalidGroupException;
import com.github.giovaneneves7.api.infrastructure.util.ObjectMapperUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Implementação da interface de serviço da entidade 'Jogador'.
 *
 * @author Giovane Neves
 */
@Service
public class JogadorService implements IJogadorService {

    @Autowired
    private ObjectMapperUtil _objectMapperUtil;

    @Autowired
    private IJogadorRepository _jogadorRepository;

    final ModelMapper MODEL_MAPPER = new ModelMapper();

    @Override
    public JogadorPostResponseDto salvarJogador(Jogador jogador) {

        String group = jogador.getGrupo();
        List<String> codinomes = null;

        if(group.equals("Liga da Justiça")){

            StringBuffer response = fazerRequisicaoHttp("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml");
            codinomes = decodificarXml(response);

        } else if(group.equals("Os Vingadores")){

            StringBuffer response = fazerRequisicaoHttp("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json");
            codinomes = decodificarJson(response);
        }

        List<String> inMemCodinomes = this._jogadorRepository.findAllCodinome(group);
        List<String> avaliableCodinomes = new ArrayList<>();
        for (String codinome : codinomes) {

            if (!inMemCodinomes.contains(codinome))
                avaliableCodinomes.add(codinome);

        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(avaliableCodinomes.size());

        jogador.setCodinome(avaliableCodinomes.get(randomIndex));


        // TODO: Disparar exceção.
        return this._objectMapperUtil.map(this._jogadorRepository.save(jogador), new JogadorPostResponseDto());

    }

    @Override
    public List<JogadorGetResponseDto> listarJogadores() {

        return this._jogadorRepository.findAll()
                .stream()
                .map(j -> this._objectMapperUtil.map(j, new JogadorGetResponseDto()))
                .collect(Collectors.toList());

    }

    private StringBuffer fazerRequisicaoHttp(String path) {

        URL url = null;

        try {

            url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );

            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {

                content.append(inputLine);
            }

            in.close();
            return content;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> decodificarXml(StringBuffer xmlResponse) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new InputSource(new StringReader(xmlResponse.toString())));

            NodeList nodeList = doc.getElementsByTagName("codinome");
            List<String> codinomesList = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                codinomesList.add(node.getTextContent());
            }

            return codinomesList;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
        List<String> decodificarJson(StringBuffer jsonResponse){

            Gson gson = new Gson();

            JsonObject jsonObject = gson.fromJson(jsonResponse.toString(), JsonObject.class);
            JsonArray vingadoresArray = jsonObject.getAsJsonArray("vingadores");

            List<String> codinomes = new ArrayList<>();

            for(JsonElement j : vingadoresArray){
                JsonObject vingadoresObject = j.getAsJsonObject();
                String codinome = vingadoresObject.get("codinome").getAsString();
                codinomes.add(codinome);
            }

            return codinomes;


        }
}
