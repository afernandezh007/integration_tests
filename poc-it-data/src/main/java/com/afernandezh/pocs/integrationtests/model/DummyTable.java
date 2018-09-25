package com.afernandezh.pocs.integrationtests.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "dummytable")
@Data
@ToString
@EqualsAndHashCode
public class DummyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", length = 200)
    private String value;
}
