package com.example.demo.controller;


import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.domain.item.Book;
import com.example.demo.service.ItemService;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final ItemService itemService = null;


    @PostMapping("/book/new")
    public String create(BookForm form, BindingResult result){


        Book book = new Book();

        itemService.saveItem(book);
        return "redirect:/";

    }


    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId,Model model){
        Book item = (Book) itemService.findItem(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        return "items/test.com";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm bookForm){


        //영속성 컨텍스트가 더이상 관리하지 않는 엔티티 => 준영속 엔티티
        //id가 있기때문에 과거에 db로부터 호출됨 => 엔티티로부터 관리가 된적이 있음
        /*
        Book book = new Book();
        book.setId(bookForm.getId());
        book.setName(bookForm.getName());
        itemService.saveItem(book);
         */

        //컨트롤러 계층에서는 Book엔티티를 직접생성하지 말고 DTO로 대체해야함함


       return "items/test.com";
    }






}
