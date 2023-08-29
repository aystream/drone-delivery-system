package com.musala.gatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GatewayApplicationTests {

    @Autowired
    private GatewayAutoConfiguration gatewayAutoConfiguration;

    @Test
    public void contextLoads() {
        assertThat(gatewayAutoConfiguration).isNotNull();
    }

}
