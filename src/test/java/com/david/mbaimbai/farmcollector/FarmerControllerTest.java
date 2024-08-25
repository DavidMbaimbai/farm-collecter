package com.david.mbaimbai.farmcollector;

import com.david.mbaimbai.farmcollector.dto.FarmerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FarmerControllerTest {
    @Autowired
    private WebTestClient client;
    @BeforeEach
    void setUp() {
    }

    @Test
    public void saveFarmerSuccess() {
        client.post()
                .uri("/farmer/save")
                .bodyValue(requestObject())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").isEqualTo("201")
                .jsonPath("$.statusMsg").isEqualTo("Farmer with name 78899778 was created successfully");
    }

    private FarmerDto requestObject() {
        FarmerDto dto = new FarmerDto();
        dto.setNationalId("78899778");
        dto.setFullName("David Mbaimbai");
        return dto;
    }


}