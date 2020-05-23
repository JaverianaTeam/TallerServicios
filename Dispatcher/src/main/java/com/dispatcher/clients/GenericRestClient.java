package com.dispatcher.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GenericRestClient {

  private RestTemplate rt;

  @Autowired
  public GenericRestClient(RestTemplate rt) {
    this.rt = rt;
  }

  public String getMethod(String url) {
    ResponseEntity<String> rta = rt.exchange(url, HttpMethod.GET, null, String.class);
    return rta.getBody();
  }

  public String getSoap(String url, String payload, String action) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/xml");
    headers.add("SOAPAction", action);
    HttpEntity<String> formEntity = new HttpEntity<>(payload, headers);
    ResponseEntity<String> rta = rt.exchange(url, HttpMethod.POST, formEntity, String.class);
    System.out.println(rta);
    return rta.getBody();
  }

}
