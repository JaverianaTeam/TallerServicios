package com.notificacion.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonConverter {

  /**
   *
   * @param <T> Tipo de objeto que debe retornar el método
   * @param jsonString - String que contiene el objeto en formanto JSON que se desea deserializar
   * @param objectType - Tipo de objeto en el que se desea deserializar la cadena JSON
   * @return - El objeto deserializado.
   */
  public static <T> T toObject(String jsonString, Class<T> objectType){
    ObjectMapper mapper = new ObjectMapper();
    T result = null;
    try {
      result = mapper.readValue(jsonString, objectType);
    } catch (JsonMappingException | JsonParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String toJSON(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    String result = null;
    try {
      result = mapper.writeValueAsString(object);
    } catch (JsonGenerationException | JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

}
