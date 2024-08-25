package com.david.mbaimbai.farmcollector;

import com.david.mbaimbai.farmcollector.dto.SeasonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SeasonTest {
    @Autowired
    private WebTestClient client;
    @BeforeEach
    void setUp() {
    }

    @Test
    public void saveFarmerSuccess() {
        client.post()
                .uri("/season/save")
                .bodyValue(requestObject())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").isEqualTo("201")
                .jsonPath("$.statusMsg").isEqualTo("Season with name Kwekwetsi was created successfully");
    }

    private SeasonDto requestObject() {
        SeasonDto dto = new SeasonDto();
        dto.setSeasonName("Kwekwetsi");
        dto.setPeriod("2024 -2025");

        return dto;
    }



}