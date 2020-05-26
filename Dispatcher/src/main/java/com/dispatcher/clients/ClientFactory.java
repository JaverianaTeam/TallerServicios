package com.dispatcher.clients;

public class ClientFactory {

  public static AGenericClient getClient(String type) {
    if (type.equalsIgnoreCase("REST")) {
      return new GenericRestClient();
    } else {
      return new GenericSoapClient();
    }
  }
}
