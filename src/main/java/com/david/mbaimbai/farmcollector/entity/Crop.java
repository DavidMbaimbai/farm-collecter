package com.david.mbaimbai.farmcollector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "crop_name", unique = true, nullable = false)
    private String cropName;
    @OneToMany(mappedBy = "crop")
    private List<FarmActivityTracker> farmActivityTracker;

}
