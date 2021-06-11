package com.example.demo.service;

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

    public List<Item> findItems(){
        return itemRepository.findAll();

    }

    public Item findItem(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
