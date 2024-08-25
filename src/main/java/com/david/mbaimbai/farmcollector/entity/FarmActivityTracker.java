package com.david.mbaimbai.farmcollector.entity;

import com.david.mbaimbai.farmcollector.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FarmActivityTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double plantingArea;
    private Double product;
    private ActivityType activityType;
    @ManyToOne(targetEntity = Farm.class, cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Farm farm;
    @ManyToOne(targetEntity = Season.class, cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id")
    private Season season;
    @ManyToOne(targetEntity = Crop.class, cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id")
    private Crop crop;
}
