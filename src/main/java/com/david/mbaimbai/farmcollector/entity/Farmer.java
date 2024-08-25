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
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column( unique = true, nullable = false)
    private String fullName;
    @Column( unique = true, nullable = false)
    private String nationalId;
    @OneToMany(mappedBy = "owner")
    private List<Farm> farms;
}
