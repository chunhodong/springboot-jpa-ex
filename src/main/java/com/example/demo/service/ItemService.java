package com.example.demo.service;

import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository = null;

    //클래스에서 있는 @Transactional보다 우선순위가 높다
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //트랜잭션이 자동으로 commit함
    @Transactional
    public void updateItem(Long itemId, Book bookParam){
        Item findItem = itemRepository.findOne(itemId);
        //update시 조회후 변경된 데이터만 수정해주면
        //jpa가 트랜잭션commit시 자동을 변경을 감지하고 update쿼리를 전송
        findItem.setPrice(bookParam.getPrice());

    }

    public List<Item> findItems(){
        return itemRepository.findAll();

    }

    public Item findItem(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
