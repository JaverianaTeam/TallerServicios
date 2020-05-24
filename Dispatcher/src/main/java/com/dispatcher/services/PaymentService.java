package com.dispatcher.services;

import com.dispatcher.clients.AGenericClient;
import com.dispatcher.clients.ClientFactory;
import com.dispatcher.exception.PaymentBadRequestException;
import com.dispatcher.exception.PaymentNotFoundException;
import com.dispatcher.kafka.KafkaProducer;
import com.dispatcher.model.ConvenioObject;
import com.dispatcher.model.PagoRequest;
import com.dispatcher.model.PaymentResponse;
import com.dispatcher.model.ServiceDescription;
import com.dispatcher.proxies.ConvenioProxy;
import com.dispatcher.proxies.UsuarioProxy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final ConvenioProxy cp;
  private final UsuarioProxy up;
  private final KafkaProducer kp;

  public PaymentService(ConvenioProxy cp, UsuarioProxy up, KafkaProducer kp) {
    this.cp = cp;
    this.up = up;
    this.kp = kp;
  }

  private ServiceDescription getConfiguration(Integer idConvenio, String operacion) {
    // Se obtiene la información del convenio
    ConvenioObject co = cp.getInfoConvenio(idConvenio);
    // Se obtiene la configuración para el servicio de consulta.
    ServiceDescription sd = co.getConfiguracion().stream().filter(x -> x.getOperation().equals(operacion)).findFirst().orElse(null);
    if (sd == null) {
      throw new PaymentNotFoundException("El convenio de recaudo especificado no existe");
    }
    return sd;
  }

  private String fillPayload(String payload, Integer reference) {
    return payload.replace("$1", reference.toString());
  }

  private String fillPayload(String payload, Integer reference, Integer valor) {
    return fillPayload(payload, reference).replace("$2", valor.toString());
  }

  public PaymentResponse consultarFactura(Integer idConvenio, Integer referencia) {
    if (idConvenio == null || referencia == null) {
      throw new PaymentBadRequestException("Debe especificar el identificador del convenio y el número de referencia de recaudo o factura");
    }
    PaymentResponse rta;
    ServiceDescription sd = getConfiguration(idConvenio, "CONSULTAR");
    // Se establece el valor de la referencia a consultar
    sd.setRequestPayload(fillPayload(sd.getRequestPayload(), referencia));
    AGenericClient client = ClientFactory.getClient(sd.getServiceType());
    return client.callService(sd);
  }

  public PaymentResponse pagarFactura(PagoRequest data) {
    if (data.getIdConvenio() == null || data.getReferencia() == null || data.getValor() == null) {
      throw new PaymentBadRequestException("Debe especificar el identificador del convenio y el número de referencia de recaudo o factura");
    }
    PaymentResponse rta;
    ServiceDescription sd = getConfiguration(data.getIdConvenio(), "PAGAR");
    // Se establece el valor de la referencia a consultar
    sd.setRequestPayload(fillPayload(sd.getRequestPayload(), data.getReferencia(), data.getValor()));
    AGenericClient client = ClientFactory.getClient(sd.getServiceType());
    return client.callService(sd);
  }

  public PaymentResponse compensarFactura(PagoRequest data) {
    if (data.getIdConvenio() == null || data.getReferencia() == null || data.getValor() == null) {
      throw new PaymentBadRequestException("Debe especificar el identificador del convenio y el número de referencia de recaudo o factura");
    }
    PaymentResponse rta;
    ServiceDescription sd = getConfiguration(data.getIdConvenio(), "COMPENSAR");
    sd.setRequestPayload(fillPayload(sd.getRequestPayload(), data.getReferencia(), data.getValor()));
    AGenericClient client = ClientFactory.getClient(sd.getServiceType());
    return client.callService(sd);
  }


}
