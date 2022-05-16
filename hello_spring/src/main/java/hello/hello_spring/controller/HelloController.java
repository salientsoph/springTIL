package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") //web application에서 'hello' 라고 들어오면 이 메소드 호출
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
    }
}
