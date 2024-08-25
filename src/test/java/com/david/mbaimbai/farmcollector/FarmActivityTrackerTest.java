package com.david.mbaimbai.farmcollector;

import com.david.mbaimbai.farmcollector.dto.*;
import com.david.mbaimbai.farmcollector.enums.ActivityType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FarmActivityTrackerTest extends AbstractWebClient {

    @Autowired
    private WebTestClient client;

    @Test
    public void saveActivitySuccess() {
        client.post()
                .uri("/activity/save")
                .bodyValue(requestObject())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").isEqualTo("201")
                .jsonPath("$.statusMsg").isEqualTo("Farm Activity Tracker with name PLANTED was created successfully");
    }

    @Test
    public void findBySeasonAndFarm() {
        client.get()
                .uri("/activity/season/{seasonName}/farm/{farmName}", "Kwekwetsi", "kwekwe")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].activityType").isEqualTo("PLANTED")
                .jsonPath("$.[0].plantingArea").isEqualTo(10.0)
                .jsonPath("$.[0].cropDto.cropName").isEqualTo("corn")
                .jsonPath("$.[0].product").isEqualTo(20.0)
                .jsonPath("$.[0].farmDto.address").isEqualTo("247 Pine Avenue")
                .jsonPath("$.[0].farmDto.totalHectares").isEqualTo(20.0)
                .jsonPath("$.[0].farmDto.name").isEqualTo("kwekwe")
                .jsonPath("$.[0].farmDto.owner.nationalId").isEqualTo("78899778")
                .jsonPath("$.[0].farmDto.owner.fullName").isEqualTo("David Mbaimbai")
                .jsonPath("$.[0].seasonDto.seasonName").isEqualTo("Kwekwetsi")
                .jsonPath("$.[0].seasonDto.period").isEqualTo("2024 -2025");
    }

    @Test
    public void getActivityByCrop() {
        client.get()
                .uri("/activity/crop/{cropName}", "corn")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].activityType").isEqualTo("PLANTED")
                .jsonPath("$.[0].plantingArea").isEqualTo(10.0)
                .jsonPath("$.[0].cropDto.cropName").isEqualTo("corn")
                .jsonPath("$.[0].product").isEqualTo(20.0)
                .jsonPath("$.[0].farmDto.address").isEqualTo("247 Pine Avenue")
                .jsonPath("$.[0].farmDto.totalHectares").isEqualTo(20.0)
                .jsonPath("$.[0].farmDto.name").isEqualTo("kwekwe")
                .jsonPath("$.[0].farmDto.owner.nationalId").isEqualTo("78899778")
                .jsonPath("$.[0].farmDto.owner.fullName").isEqualTo("David Mbaimbai")
                .jsonPath("$.[0].seasonDto.seasonName").isEqualTo("Kwekwetsi")
                .jsonPath("$.[0].seasonDto.period").isEqualTo("2024 -2025");
    }

    private FarmActivityTrackerDto requestObject() {
        FarmActivityTrackerDto dto = new FarmActivityTrackerDto();
        dto.setCropDto(CropDto.builder().cropName("corn").build());
        dto.setActivityType(ActivityType.valueOf("PLANTED"));
        dto.setProduct(20.0d);
        dto.setPlantingArea(10.0d);
        dto.setFarmDto(FarmDto.builder()
                .name("kwekwe")
                .address("247 Pine Avenue")
                .totalHectares(20.0)
                .owner(FarmerDto.builder()
                        .nationalId("78899778")
                        .fullName("David Mbaimbai")
                        .build())
                .build());
        dto.setSeasonDto(SeasonDto.builder()
                .seasonName("Kwekwetsi")
                .period("2024 -2025")
                .build());

        return dto;
    }


}