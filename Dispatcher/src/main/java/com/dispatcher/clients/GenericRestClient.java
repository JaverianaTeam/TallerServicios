package com.dispatcher.clients;

import com.dispatcher.model.RemoteResponse;
import com.dispatcher.model.ServiceDescription;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GenericRestClient extends AGenericClient {

  private RestTemplate rt = new RestTemplate();

  public GenericRestClient() {
  }

  private HttpMethod getHttpVerb(String method) {
    switch (method) {
      case "POST":
        return HttpMethod.POST;
      case "PUT":
        return HttpMethod.PUT;
      default:
        return HttpMethod.GET;
    }
  }

  @Override
  public RemoteResponse callService(ServiceDescription info) {
    HttpEntity<String> formEntity = new HttpEntity<>(info.getRequestPayload(), getHeaders(info.getServiceHeaders()));
    ResponseEntity<String> rta = rt.exchange(info.getServiceUrl(), getHttpVerb(info.getMethod()), formEntity, String.class);
    return processRta(new JsonMapper(), rta.getBody(), info.getReferencePath(), info.getValuePath(), info.getMessagePath());
  }
}
