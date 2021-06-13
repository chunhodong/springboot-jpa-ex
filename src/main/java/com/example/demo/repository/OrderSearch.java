package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderSearch {

    private String memberName;


    private OrderStatus orderStatus;


}
