package com.dispatcher.controller;

import com.dispatcher.model.PagoRequest;
import com.dispatcher.model.PaymentResponse;
import com.dispatcher.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pay")
public class PaymentController {

  private final PaymentService ps;

  @Autowired
  public PaymentController(PaymentService ps) {
    this.ps = ps;
  }

  @GetMapping
  public PaymentResponse consultarFactura(@RequestParam(value = "idConvenio") Integer idConvenio, @RequestParam(value = "referencia") Integer referencia) {
    return ps.consultarFactura(idConvenio, referencia);
  }

  @PostMapping
  public PaymentResponse pagarFactura(@RequestBody PagoRequest data) {
    return ps.pagarFactura(data);
  }

  @PostMapping
  public PaymentResponse compensarFactura(@RequestBody PagoRequest data) {
    return ps.compensarFactura(data);
  }

}
