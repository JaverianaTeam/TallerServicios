package com.dispatcher.clients;

import com.dispatcher.model.RemoteResponse;
import com.dispatcher.model.ServiceDescription;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GenericSoapClient extends AGenericClient {

  private RestTemplate rt = new RestTemplate();

  public GenericSoapClient() {
  }

  @Override
  public RemoteResponse callService(ServiceDescription info) {
    HttpHeaders headers = getHeaders(info.getServiceHeaders());
    headers.add("SOAPAction", info.getMethod());
    //todo falta llenar el payload
    HttpEntity<String> formEntity = new HttpEntity<>(info.getRequestPayload(), headers);
    ResponseEntity<String> rta = rt.exchange(info.getServiceUrl(), HttpMethod.POST, formEntity, String.class);
    String aux = rta.getBody();
    aux = aux.substring(aux.indexOf("<S:Body") + 8, aux.indexOf("</S:Body"));
    System.out.println(aux);
    //todo peluquear respuesta
    return processRta(new XmlMapper(), aux, info.getReferencePath(), info.getValuePath(), info.getMessagePath());
  }

}
