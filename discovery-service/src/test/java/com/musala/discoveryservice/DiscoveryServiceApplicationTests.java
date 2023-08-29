package com.musala.discoveryservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DiscoveryServiceApplicationTests {

	@Autowired
	private EurekaServerAutoConfiguration eurekaServerAutoConfiguration;

	@Test
	public void contextLoads() {
		assertThat(eurekaServerAutoConfiguration).isNotNull();
	}

}
