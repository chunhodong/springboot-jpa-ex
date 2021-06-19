package com.example.demo.api;


import com.example.demo.domain.Address;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository = null;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }


    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        //LAZY-LOADING으로 조회한상태
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());


        //1. 루프를 돌면서 db에서 읽어온 memberId를 가지고 영속성 컨텍스트에서 member객체를 찾는다
        //2. LAZY-LOADING이기때문에 member객체는 존재하지 않기떄문에 member객체를 읽어오기위한 쿼리를 보낸다
        //** 만약 영속성 컨텍스트에 객체가 존재하면(memberId를 가진 객체) 영속성 컨텍스트에서 조회
        //3. delivery도 똑같다.
        //N + 1 문제 order의 개수만큼 각각의 member,delivery 쿼리가 나간다 => 페치조인으로 해결
        //EAGER로 바꾸면 연쇄적인 조회가 일어날수있음
        return orders.stream().map(order -> new SimpleOrderDto())
                .collect(Collectors.toList());

    }

    
    //패치조인으로 구현
    //패치조인은 LAZY와 EAGER자체를 무시하고, JOIN쿼리를 보낸다
    //** 기본적으로 LAZY로 설정하고 필요한곳은 fetch join으로 구현
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream().map(order -> new SimpleOrderDto(order)).collect(Collectors.toList());
        return result;

    }




    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getAddress();

        }
    }
}
