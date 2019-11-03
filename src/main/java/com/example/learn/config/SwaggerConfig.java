package com.example.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("documents for learning swagger").description("describtion").build();
  }

  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//// 加了ApiOperation注解的类，才生成接口文档
        .paths(PathSelectors.any()).build();
  }

}
