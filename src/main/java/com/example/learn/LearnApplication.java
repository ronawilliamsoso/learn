package com.example.learn;

import com.example.learn.configFromYml.YmlBeanPerson;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(value = YmlBeanPerson.class)
@EnableEurekaServer
public class LearnApplication{

	public static void main(String[] args) {
		SpringApplication.run(LearnApplication.class,args);
	}

	@Bean
	public RestTemplate restTemplate(){return new RestTemplate();}

	@Bean
	public Redisson redisson() {
		// 此为单机模式
		Config config = new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        /*config.useClusterServers() 	// 此为集群模式模式
                .addNodeAddress("redis://192.168.0.61:8001")
                .addNodeAddress("redis://192.168.0.62:8002")
                .addNodeAddress("redis://192.168.0.63:8003")
                .addNodeAddress("redis://192.168.0.61:8004")
                .addNodeAddress("redis://192.168.0.62:8005")
                .addNodeAddress("redis://192.168.0.63:8006");*/
		return (Redisson) Redisson.create(config);
	}

}
