package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService; // = new MemberService();
    MemoryMemberRepository memberRepository; //= new MemoryMemberRepository();

    @BeforeEach //각 테스트를 실행하기 전에
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository(); //동일한 인스턴스 사용됨
        memberService = new MemberService(memberRepository); //service 입장에서 보면 외부에서 memberRepository를 넣어줌 (dependency injection: DI)
    }


    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() { //test는 한국어로 method이름 적어도됨 -> build될 때 test코드는 포함되지 않음
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }


    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. ");

        /*
        try{
            memberService.join(member2);
            fail("예외가 발생해야 합니다. "); //만약 catch로 가지 않고 윗줄이 실행된 경우
        } catch(IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. 123");
        }  */

        //then


    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}