package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    //service는 한번만 등록하면 된다
    private final MemberService memberService; //= new MemberService();

    @Autowired //생성자에 autowired: spring container에 있는 memberService를 가져다 연결시킴
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
