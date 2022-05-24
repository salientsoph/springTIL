package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //메소드가 끝날 때 마다 동작하는 것
    public void afterEach(){
        repository.clearStore();
    }

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
        //import org.junit.jupiter.api.Test
        //Assertions.assertEquals(expected, actual);
        //import 할 때 static import를 하면 그냥 assertThat(member).~ 로 쓸 수 있다.
        assertEquals(member, result);

        //import org.assertj.core.api.Assertions
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findById(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        //위에걸 복붙해왔다면 shift+f6 누르면 rename됨
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); //get: optional을 한번 까서 가져오므로 Optional로 받을 필요가 없음
        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }



}
