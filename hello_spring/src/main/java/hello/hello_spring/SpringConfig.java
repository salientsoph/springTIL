package hello.hello_spring;


import hello.hello_spring.repository.JdbcMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    //@Autowired
    private DataSource dataSource; //spring이 application.properties에 있는 설정을 보고 알아서 bean을 생성함

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource); //이때 들어가는 dataSource 파라미터는 spring이 제공하는걸 쓴다.
    }
}
