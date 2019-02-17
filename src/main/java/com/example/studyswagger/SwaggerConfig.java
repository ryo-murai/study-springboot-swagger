package com.example.studyswagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private static final String CLIENT_ID = "";
  private static final String CLIENT_SECRET = "";
  private static final String AUTH_SERVER = "";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.example.studyswagger.api"))
      .paths(PathSelectors.ant("/custom/**"))
      .build()
      .apiInfo(apiInfo())
      .useDefaultResponseMessages(false)
      .globalResponseMessage(RequestMethod.GET, responseMessages())
      .securitySchemes(securityScheme())
      .securityContexts(securityContext());
  }

  @Bean
  public SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
      .clientId(CLIENT_ID)
      .clientSecret(CLIENT_SECRET)
      .scopeSeparator(" ")
      .useBasicAuthenticationWithAccessCodeGrant(true)
      .build();
  }


  private List<ResponseMessage> responseMessages() {
    return Arrays.asList(
        new ResponseMessageBuilder()
          .code(500)
          .message("500's message")
          .responseModel(new ModelRef("Error"))
          .build(),
        new ResponseMessageBuilder()
          .code(403)
          .message("forbidden den den den")
          .build());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
      "My REST API",
      "custom description of api",
      "1.0",
      "Terms of Service",
      new Contact("freddie mercury", "www.example.com", "freddie.mercury@example.com"),
      "MIT License",
      "url of license",
      Collections.emptyList());
  }

  private List<SecurityContext> securityContext() {
    return Arrays.asList(SecurityContext.builder()
      .securityReferences(
        Arrays.asList(new SecurityReference("spring_oauth", scopes()))
      ).forPaths(PathSelectors.regex("/custom.*"))
      .build());
  }

  private List<SecurityScheme> securityScheme() {
    GrantType grantType = new AuthorizationCodeGrantBuilder()
      .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
      .tokenRequestEndpoint(
        new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_ID))
      .build();
    SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
      .grantTypes(Arrays.asList(grantType))
      .scopes(Arrays.asList(scopes()))
      .build();
    return Arrays.asList(oauth);
  }

  private AuthorizationScope[] scopes() {
    return new AuthorizationScope[] {
      new AuthorizationScope("read", "for read operations"),
      new AuthorizationScope("write", "for write operations"),
      new AuthorizationScope("foo", "Access foo API")
    };
  }

}
