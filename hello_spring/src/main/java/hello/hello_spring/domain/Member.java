package hello.hello_spring.domain;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity //JPA가 관리하는 엔티티
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id: pk (db에서 pk 값을 생성해주고 있음 -> identity 전략)
    //@GeneratedValue:
    private Long id; //데이터 구분을 위해 시스템이 저장하는 id

    @Column(name = "username")  //db에서 "username" 이라는 칼럼에 매핑됨
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
