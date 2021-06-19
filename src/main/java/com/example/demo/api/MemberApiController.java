package com.example.demo.api;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService = null;

    //개선사항이 많은 api
    // * 기본적으로 엔티티를 프레젠테이션 계층에서 사용하면, 여러군대서 사용할떄 요구사항이 변할때마다
    // * 엔티티 옵션(특정변수를 숨기기)자체도 변할수 있기때문에 데이터계층을 위한 엔티티 본래기능 상실
    // * 엔티티의 민감정보가 화면에 그대로 노출될수도 있음
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }


    //v2에서 DTO를 사용해서 개선
    @GetMapping("/api/v1/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(member -> new MemberDto(member.getName())).collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    //DTO에는 getter,setter를 비교적 자유롭게 쓸수있다
    @AllArgsConstructor
    //결과데이터외 다른 필드를 추가하기 쉽다 (ex : count)
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    //@RequestBody : JSON으로 보낸 데이터를 Member로 매핑
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //같은 엔티티라도 api스펙에 따라 업데이트용DTO를 따로 만들수있음
    @PutMapping
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(),findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }


    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberRequest{

        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {

        private Long id;

        public CreateMemberResponse(Long id){
            this.id = id;
        }
    }
}
