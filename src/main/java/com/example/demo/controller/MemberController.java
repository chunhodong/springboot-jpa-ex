package com.example.demo.controller;


import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService = null;


    @PostMapping("/member/new")
    //MemberForm객체에있는 인스턴스변수에대해서 @붙은변수에 한해 validation해줌
    //BindingResult는 앞에 @Valid한 변수에 대한 결과를 담는다
    //파라미터로 Member(Entity)로 작성하면 Member 클래스가 어노테이션으로 인해서 복잡해질수있음
    //파라미터로 Member를 사용하면 Member에 화면에 종속적인 기능이 추가될수 있다.
    //Member(Entity)는 최대한 의존성없이 객체지향적인 형태를 유지해야함
    public String create(MemberForm form, BindingResult result){



        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(),form.getStreet(),form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";

    }


    @GetMapping
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
