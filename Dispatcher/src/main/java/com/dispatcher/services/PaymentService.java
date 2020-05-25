package com.dispatcher.services;

import com.dispatcher.utils.JsonConverter;
import com.dispatcher.clients.AGenericClient;
import com.dispatcher.clients.ClientFactory;
import com.dispatcher.exception.PaymentBadRequestException;
import com.dispatcher.exception.PaymentNotFoundException;
import com.dispatcher.kafka.KafkaProducer;
import com.dispatcher.model.*;
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
    if (payload != null && reference != null) {
      return payload.replace("$1", reference.toString());
    }
    return payload;
  }

  private String fillPayload(String payload, Integer reference, Integer valor) {
    if (payload != null) {
      return fillPayload(payload, reference).replace("$2", valor == null ? "" : valor.toString());
    }
    return payload;
  }

  public PaymentResponse consultarFactura(Integer idConvenio, Integer referencia) {
    if (idConvenio == null || referencia == null) {
      throw new PaymentBadRequestException("Debe especificar el identificador del convenio y el número de referencia de recaudo o factura");
    }
    PaymentResponse rta;
    ServiceDescription sd = getConfiguration(idConvenio, "CONSULTAR");
    // Se establece el valor de la referencia a consultar
    sd.setRequestPayload(fillPayload(sd.getRequestPayload(), referencia));
    sd.setServiceUrl(fillPayload(sd.getServiceUrl(), referencia));
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
    sd.setServiceUrl(fillPayload(sd.getServiceUrl(), data.getReferencia(), data.getValor()));
    AGenericClient client = ClientFactory.getClient(sd.getServiceType());
    UsuarioObject userData = up.getUsuario(data.getIdUsuario());
    NotificationObject info = new NotificationObject();
    info.setServicio("PAGAR");
    info.setEmail(userData.getEmail());
    info.setIdUsuario(userData.getIdUsuario());
    info.setNombreUsuario(userData.getNombre());
    info.setIdConvenio(data.getIdConvenio());
    info.setReferencia(data.getReferencia());
    info.setValor(data.getValor());
    info.setNroTransaccion(1);
    rta = client.callService(sd);
    info.setMensaje(rta.getMensaje());
    kp.sendMessage(JsonConverter.toJSON(info));
    return rta;
  }

  public PaymentResponse compensarFactura(PagoRequest data) {
    if (data.getIdConvenio() == null || data.getReferencia() == null || data.getValor() == null) {
      throw new PaymentBadRequestException("Debe especificar el identificador del convenio y el número de referencia de recaudo o factura");
    }
    PaymentResponse rta;
    ServiceDescription sd = getConfiguration(data.getIdConvenio(), "COMPENSAR");
    sd.setRequestPayload(fillPayload(sd.getRequestPayload(), data.getReferencia(), data.getValor()));
    sd.setServiceUrl(fillPayload(sd.getServiceUrl(), data.getReferencia(), data.getValor()));
    AGenericClient client = ClientFactory.getClient(sd.getServiceType());
    UsuarioObject userData = up.getUsuario(data.getIdUsuario());
    NotificationObject info = new NotificationObject();
    info.setServicio("COMPENSAR");
    info.setEmail(userData.getEmail());
    info.setIdUsuario(userData.getIdUsuario());
    info.setNombreUsuario(userData.getNombre());
    info.setIdConvenio(data.getIdConvenio());
    info.setReferencia(data.getReferencia());
    info.setValor(data.getValor());
    info.setNroTransaccion(1);
    rta = client.callService(sd);
    info.setMensaje(rta.getMensaje());
    kp.sendMessage(JsonConverter.toJSON(info));
    return rta;
  }


}
