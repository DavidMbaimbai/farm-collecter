package com.david.mbaimbai.farmcollector;

import com.david.mbaimbai.farmcollector.dto.CropDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CropTest {
    @Autowired
    private WebTestClient client;
    @BeforeEach
    void setUp() {
    }

    @Test
    public void saveCropSuccess() {
        client.post()
                .uri("/crops/save")
                .bodyValue(requestObject())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").isEqualTo("201")
                .jsonPath("$.statusMsg").isEqualTo("Crop with name Corn was created successfully");
    }

    private CropDto requestObject() {
        CropDto dto = new CropDto();
        dto.setCropName("Corn");
        return dto;
    }

}