package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemberRepository repository = new MemoryMemberRepository();

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        //optional인 경우, get()을 이용해서 꺼낸다
        //좋은 방법은 아니나, test에선 get() 바로 사용
        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (result == member));

        //계속 찍어내서 확인할 순 없으므로 Assertions 사용
        //from org.junit.jupiter.api.Test
        //Assertions.assertEquals(expected, actual);
        assertEquals(member, result);

        //from org.assertj.core.api.Assertions
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = 
    }
}
