package com.dispatcher.clients;

import com.dispatcher.model.HeaderService;
import com.dispatcher.model.PaymentResponse;
import com.dispatcher.model.ServiceDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AGenericClient {

  protected HttpHeaders getHeaders(List<HeaderService> headers) {
    HttpHeaders rta = new HttpHeaders();
    headers.forEach(h -> rta.add(h.getHeaderName(), h.getHeaderValue()));
    return rta;
  }

  public String processPath(JsonNode data, String path) {
    if (data == null || path == null || path.equals("")) {
      return null;
    }
    String[] aux = path.split(Pattern.quote("."));
    JsonNode node = data.path(aux[0]);
    if (aux.length == 1) {
      return node.asText();
    } else {
      return processPath(node, String.join(".", Arrays.copyOfRange(aux, 1, aux.length)));
    }
  }

  protected PaymentResponse processRta(ObjectMapper mapper, String data, String pathReference, String pathValue, String pathMessage) {
    PaymentResponse result = new PaymentResponse();
    try {
      JsonNode rootNode = mapper.readTree(data);
      result.setReferencia(processPath(rootNode, pathReference));
      result.setValor(processPath(rootNode, pathValue));
      result.setMensaje(processPath(rootNode, pathMessage));
      return result;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public abstract PaymentResponse callService(ServiceDescription info);

}
