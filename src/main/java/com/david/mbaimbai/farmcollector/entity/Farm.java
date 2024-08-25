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
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Farmer.class, cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "farm_id")
    private Farmer owner;
    @Column(unique = true, nullable = false)
    private String name;
    private String address;
    private Double totalHectares;
    @OneToMany(mappedBy = "farm")
    private List<FarmActivityTracker> farmActivityTracker;

}
