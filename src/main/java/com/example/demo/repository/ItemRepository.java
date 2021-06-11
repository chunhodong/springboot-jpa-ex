package com.example.demo.repository;

import com.example.demo.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em = null;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }
        else{
            //이미 DB에 등록된걸 가져온상태
            em.merge(item);

        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
