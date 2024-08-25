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
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "season_name", unique = true, nullable = false)
    private String seasonName;
    private String period;
    @OneToMany(mappedBy = "season")
    private List<FarmActivityTracker> farmActivityTracker;
}
