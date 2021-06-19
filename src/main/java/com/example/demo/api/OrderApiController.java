package com.example.demo.api;

import com.example.demo.domain.Address;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository = null;

    @GetMapping
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for(Order order:all){
            List<OrderItem> orderItems = order.getOrderItems();

        }

        return all;

    }

    //1대N에서 Fetch join은 limit사용불가, limit를쓰면 전부읽어온 다음에 메모리에서 페이징처리를 하기때문에
    //매우 비효율적이고 위험
    @GetMapping
    public List<OrderDto> orderV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream().map(order -> new OrderDto(order)).collect(Collectors.toList());
    }


    //ONE TO MANY에서 N+1,paging 문제를 해결하기 위해 fetch_size 옵션사용
    public List<OrderDto> orderV3_page(){

        return null;

    }

    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order){
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.address = order.getAddress();
            this.orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());

        }
    }

    static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getItem().getPrice();
            count = orderItem.getCount();
        }
    }
}
