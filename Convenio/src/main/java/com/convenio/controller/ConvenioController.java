package com.convenio.controller;

import com.convenio.exception.ConvenioNotFoundException;
import com.convenio.model.ConvenioResponse;
import com.convenio.services.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("convenios/")
public class ConvenioController {

  private ConvenioService cs;

  @Autowired
  public ConvenioController(ConvenioService cs) {
    this.cs = cs;
  }

  @GetMapping(path = "byId/{id}")
  public ConvenioResponse getConvenioById(@PathVariable("id") Integer id) {
    return cs.getConvenioById(id);
  }

  @GetMapping(path = "byName/{name}")
  public List<ConvenioResponse> getConvenioByName(@PathVariable("name") String name) {
    return cs.getConvenioByName(name);
  }

}
