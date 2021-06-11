package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//readOnly옵션을 넣어주면 조회메서드에서 성능상이점
@Transactional(readOnly = true)
//final이 붙은 인스턴스 메서드만 생성자를 만들어서 자동으로 초기화해줌
@RequiredArgsConstructor
public class MemberService {
    /*
    //Autowired를 쓰면 고정되버리기때문에, 유연하지 못함(테스트의 경우 Mock객체로 대체하기 어려움)
    @Autowired
    private MemberRepository memberRepository;*/

    private final MemberRepository memberRepository = null;

    @Transactional
    public Long join(Member member){

        //멀티쓰레드를 허용하는 상황에서 동시접근이 있을수 있기때문에
        //DB차원에서 Member테이블의 name필드는 unique제약을 걸어두는게 좋음
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
