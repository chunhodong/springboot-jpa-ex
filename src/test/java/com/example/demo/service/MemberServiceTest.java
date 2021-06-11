package com.example.demo.service;

import com.example.demo.MemberRepository;
import com.example.demo.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/*
//회원가입을 성공해야한다
//회원가입할 때 같은 이름이 있으면 예외가 발생해야 한다.
* */
@RunWith(SpringRunner.class)
@SpringBootTest
//있어야 롤백
@Transactional
public class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    //쿼리가 날라가는거 확인하고 싶으면 설정
    //@Rollback(value = false)
    public void 회원가입() throws Exception{
        //given
        //데이터가 주어졌을때
        Member member = new Member();
        member.setName("kim");

        //when
        //로직을 실행하면
        //트랜잭션이 commit을해야 쿼리가 날라가는데 테스트에서는 @Transaction이 rollback되므로
        //쿼리가 날아가진않는다.
        Long savedId = memberService.join(member);

        //then
        //결과를 도출한다다
        assertEquals(member,memberRepository.find(savedId));

    }

    //테스트중 발생한 예외클래스의 종류를 작성
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{

        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        //같은 이름으로 추가했기때문에 메소드에 설정한 Exceptio이 발생해야성공
        //Exception의 발생은 @Test(expected = IllegalStateException.class)에 잡아줌
        memberService.join(member1);
        memberService.join(member2);

        //then
        //동일한 이름으로 추가했기떄문에 service에서 예외가 발생해야한다.
        //fail코드가 실행되면 실패하는 테스트
        fail("예외가 발생해야한다.");

    }

}