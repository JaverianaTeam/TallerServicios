package com.dispatcher.controller;

import com.dispatcher.clients.GenericRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  GenericRestClient grc;

  @Autowired
  public PaymentController(GenericRestClient grc) {
    this.grc = grc;
  }

  @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
  public String test() {
    String rta = "A";
    //String rta = grc.getMethod("http://localhost:8086/usuarios/api/2");
    System.out.println(rta);

    JsonNode rootNode;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      rootNode = objectMapper.readTree(rta);
      System.out.println("idUsuario :" + rootNode.path("idUsuario").asText());
      System.out.println("nombre :" + rootNode.path("nombre").asText());
      System.out.println("email :" + rootNode.path("email").asText());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    String[] prueba = "fafaffa".split(Pattern.quote("."));
    String[] prueba2 = "fafaffa.45446.12131".split(Pattern.quote("."));
    Arrays.stream(prueba).forEach(System.out::println);
    Arrays.stream(prueba2).forEach(System.out::println);
    XmlMapper xmlMapper = new XmlMapper();

    try {
      rootNode = xmlMapper.readTree("<ResultadoConsulta xmlns=\"http://www.servicios.co/pagos/schemas\"><referenciaFactura><referenciaFactura>1234000057</referenciaFactura></referenciaFactura><totalPagar>7765.56</totalPagar></ResultadoConsulta>");
      System.out.println(rootNode.path("referenciaFactura").path("referenciaFactura").asText());
      System.out.println("Parsing Path " + grc.processPath(rootNode, "referenciaFactura.referenciaFactura"));
      System.out.println(rootNode.path("totalPagar").asText());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

/*

    System.out.println("----------------------------------------------------CONSULTA-------------------------------------------------------------------------------");
    rta += " " + grc.getSoap("http://54.83.136.144:8080/gas-service/PagosService", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://www.servicios.co/pagos/schemas\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <sch:ReferenciaFactura>" +
            "         <sch:referenciaFactura>1234000057</sch:referenciaFactura>" +
            "      </sch:ReferenciaFactura>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>", "consultar");

    System.out.println("----------------------------------------------------Extrae XML-------------------------------------------------------------------------------");

    System.out.println(" ");
    System.out.println("Inicial " + rta.indexOf("<S:Envelope"));
    System.out.println("Final " + rta.indexOf("</S:Envelope"));
    System.out.println(rta.substring(rta.indexOf("<S:Envelope"), rta.indexOf("</S:Envelope") + 13 ));

    System.out.println("----------------------------------------------------PAGO-------------------------------------------------------------------------------");

    rta += grc.getSoap("http://54.83.136.144:8080/gas-service/PagosService", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://www.servicios.co/pagos/schemas\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <sch:PagoResource>" +
            "         <sch:referenciaFactura>" +
            "            <sch:referenciaFactura>1234000057</sch:referenciaFactura>" +
            "         </sch:referenciaFactura>" +
            "         <sch:totalPagar>7765.56</sch:totalPagar>" +
            "      </sch:PagoResource>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>", "pagar");

    System.out.println(" ");
    System.out.println("----------------------------------------------------COMPENSACION-------------------------------------------------------------------------------");

    rta += grc.getSoap("http://54.83.136.144:8080/gas-service/PagosService", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://www.servicios.co/pagos/schemas\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <sch:PagoResource>" +
            "         <sch:referenciaFactura>" +
            "            <sch:referenciaFactura>1234000057</sch:referenciaFactura>" +
            "         </sch:referenciaFactura>" +
            "         <sch:totalPagar>7765.56</sch:totalPagar>" +
            "      </sch:PagoResource>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>", "compensar");
*/


    return rta;
  }

}
