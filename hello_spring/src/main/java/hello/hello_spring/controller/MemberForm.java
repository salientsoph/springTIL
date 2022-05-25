package hello.hello_spring.controller;

public class MemberForm {

    //createMemberForm.html 에서 <input type="text" id="name" name="name"> 의 name에서 설정한 값이 들어온다
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
