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
            //입력파라미터로 들어온 객체의 데이터로 강제로 교체하기때문에 null값도 새로 세팅될수있음(실무에서 잘 안씀)
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
