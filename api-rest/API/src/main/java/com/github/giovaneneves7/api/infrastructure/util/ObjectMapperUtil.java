package com.github.giovaneneves7.api.infrastructure.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;


    static {
        MODEL_MAPPER = new ModelMapper();
    }

    public <Input, Output> Output map(final Input object, final Class<Output> clazz){

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);


        Output c =  MODEL_MAPPER.map(object, clazz);

        return c;

    }

    public <Source, Target> Target map(final Source s, Target t){

        // TODO: Remover logs

        System.out.println("Convertendo source: " + s.toString());
        System.out.print("para t " + t.toString());
        System.out.println();

        try{

            System.out.println("Source possui a seguinte lista de atributos : " + s.getClass().getDeclaredFields().toString());

            // Criando Foreach na lista de atributos da classe Source.
            for(Field sourceField : s.getClass().getDeclaredFields()){

                boolean fieldExists = Arrays.stream(t.getClass().getDeclaredFields())
                        .anyMatch(f -> f.getName().equals(sourceField.getName()));

                System.out.println("O atributo com o nome '" + sourceField.getName() + (fieldExists ? "' existe" : "' não existe"));

                // Salta a iteração caso ambas as classes não possuem o atributo com o mesmo nome.
                if(!fieldExists)
                    continue;

                // Pega o campo do Target que receberá o valor do campo com o mesmo nome em Source.
                Field targetField = t.getClass().getDeclaredField(sourceField.getName());
                sourceField.setAccessible(true);
                targetField.setAccessible(true);

                // Verifica se o atributo atual de Source é um Record.
                if(isRecord(sourceField.getType())){

                    System.out.println("O atributo é um Record agregado!");

                    Object sourceAggregateObject = sourceField.get(s);

                    Object targetAggregateObject = targetField.getType().getDeclaredConstructor().newInstance();
                    // Copia os atributos do Record para os atributos do objeto agregado de mesmo nome com uma chamada recursiva.
                    targetField.set(t, map(sourceAggregateObject, targetAggregateObject));

                    continue;
                }

                System.out.println("O atributo agregado não é um Record!");
                // Atribui o valor do atributo atual de source para o atributo de mesmo nome em Target.
                Object value = sourceField.get(s);

                targetField.set(t, value);

            }

        } catch(Exception ex){

            ex.printStackTrace();

        }


        return t;

    }

    private boolean isRecord(Class<?> clazz){

        return clazz.isRecord();

    }

    public <Input, Output> List<Output> mapAll(final Collection<Input> objectList, Class<Output> out){

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        return objectList.stream()
                .map(obj -> MODEL_MAPPER.map(obj, out))
                .toList();
    }



}
