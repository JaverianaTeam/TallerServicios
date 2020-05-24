package com.dispatcher.clients;

import com.dispatcher.model.HeaderService;
import com.dispatcher.model.RemoteResponse;
import com.dispatcher.model.ServiceDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

  protected RemoteResponse processRta(ObjectMapper mapper, String data, String pathReference, String pathValue, String pathMessage) {
    RemoteResponse result =new RemoteResponse();
    try {
      JsonNode rootNode = mapper.readTree(data);
      result.setReference(processPath(rootNode, pathReference));
      result.setValue(processPath(rootNode, pathValue));
      result.setMessage(processPath(rootNode, pathMessage));
      return result;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public abstract RemoteResponse callService(ServiceDescription info);

}
