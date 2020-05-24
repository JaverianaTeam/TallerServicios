package com.convenio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescription {
  private Integer configurationId;
  private String serviceType = "REST";
  private String serviceUrl;
  private String operation = "CONSULTAR";
  private String method;
  private List<HeaderService> serviceHeaders;
  private String requestPayload;
  private String referencePath;
  private String valuePath;
  private String messagePath;

  public ServiceDescription(Integer configurationId, String serviceType, String serviceUrl, String operation, String method,
                            String requestPayload, String referencePath, String valuePath, String messagePath) {
    this.configurationId = configurationId;
    this.serviceType = serviceType;
    this.serviceUrl = serviceUrl;
    this.operation = operation;
    this.method = method;
    this.requestPayload = requestPayload;
    this.referencePath = referencePath;
    this.valuePath = valuePath;
    this.messagePath = messagePath;
  }
}
