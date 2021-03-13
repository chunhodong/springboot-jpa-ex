package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    private Long id;

    private String name;
}
