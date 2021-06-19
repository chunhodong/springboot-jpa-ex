package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //Entity에 화면을 위한 Valid검증이 들어가기때문에 안좋은코드
    //Entity에 의존성이 Presentation계층까지 확장되는 현상
    //DTO로 만들어야함
    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void setUsername(String memberA) {
    }
}
