package com.david.mbaimbai.farmcollector;

import com.david.mbaimbai.farmcollector.dto.FarmDto;
import com.david.mbaimbai.farmcollector.dto.FarmerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FarmTest {
    @Autowired
    private WebTestClient client;
    @BeforeEach
    void setUp() {
    }

    @Test
    public void saveFarmerSuccess() {
        client.post()
                .uri("/farm/save")
                .bodyValue(requestObject())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").isEqualTo("201")
                .jsonPath("$.statusMsg").isEqualTo("Farm with name 247 Pine Avenue was created successfully");
    }

    private FarmDto requestObject() {
        FarmerDto ownerDto = new FarmerDto();
        ownerDto.setNationalId("78899778");
        ownerDto.setFullName("Mbaimbai David");

        FarmDto dto = new FarmDto();
        dto.setName("kwekwe");
        dto.setAddress("247 Pine Avenue");
        dto.setTotalHectares(20.0);
        dto.setOwner(ownerDto);

        return dto;
    }


}