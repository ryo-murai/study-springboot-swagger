package com.example.studyswagger.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@RestController
public class CustomController {
  @RequestMapping(value = "/custom", method = RequestMethod.POST)
  public String custom() {
    return "custom";
  }

  @RequestMapping(value = "/custom", method = RequestMethod.GET)
  public String getCustom() {
    return "custom got";
  }
}
