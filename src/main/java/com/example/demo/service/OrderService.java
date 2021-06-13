package com.example.demo.service;

import com.example.demo.MemberRepository;
import com.example.demo.domain.Delivery;
import com.example.demo.domain.Member;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.item.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository = null;
    private final MemberRepository memberRepository = null;
    private final ItemRepository itemRepository = null;

    @Transactional
    public Long order(Long memberId,Long itemId,int count){
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        Order order = Order.createOrder(member,delivery,orderItem);
        return order.getId();
    }


    /*JPA를 안쓰면 비즈니스로직에서 상태를 변경했을때, 개발자가 상응하는 쿼리를 직접만들어서 날려야함*/
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }


    public List<Order> findOrders(OrderSearch orderSearch) {

        return orderRepository.findAllByString(orderSearch);
    }
}
