package com.link.Webapp3.controller;

import com.link.Webapp3.model.Service;
import com.link.Webapp3.model.ServiceRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class ServiceController {
  @Autowired
  JdbcTemplate jdbcTemplate;

@GetMapping("/dashboard")
public ModelAndView getDashboard(){
  ModelAndView dashboardView = new ModelAndView("dashboard.html");
  // luam din db produse
  String sql = "SELECT * FROM `service`";
  List<Service> serviceList =jdbcTemplate.query(sql, new ServiceRowMapper());
  //setam cu addObject -> services
  dashboardView.addObject("services",serviceList);
  return dashboardView;
}

@GetMapping("/services/{id}")
  public ModelAndView getServiceById(@PathVariable Integer id){
  // query in db care identifica dupa id
 String query = "SELECT * FROM `auto`.`service` WHERE id ="+ id;
Service dbServiceElem = jdbcTemplate.queryForObject(query, new ServiceRowMapper());

  // afisez in template
  ModelAndView serviceView = new ModelAndView("service.html");
  serviceView.addObject("serviceElem", dbServiceElem);
  return serviceView;

}
}
