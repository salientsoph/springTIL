package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원가입
     * */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원 x
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { //result != null(존재한다면) 동작한다 (optional이기에 가능한 것. optional 안했으면 isNull로 받았을 것)
                                //optional로 감싸면, optional 안에 Member 객체가 있는 것
            throw new IllegalStateException("이미 존재하는 회원입니다. ");
        });*/

        //위에 코드를 깔끔하게!
        /*memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다. ");
                });*/               //해당 코드는 method로 extract 한다


        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다. ");
                        });
    }


    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
