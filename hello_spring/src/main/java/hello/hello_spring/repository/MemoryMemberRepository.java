package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //0, 1, 2, 등등.. key값을 생성해줌


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //null이 반환될 수 있어 최근에는 Optional로 감싸서 return한다
    }

    @Override
    public Optional<Member> findByName(String name) {
        //람다식
        return store.values().stream()
                //member.getName이 파라미터로 넘어온 name과 같은지 확인
                //같은 경우에만 filtering
                //하나라도 찾으면 반환(findAny())
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        //store에 있는 member들이 반환됨
        //Map -> List 로 반환 (new ArrayList<>())
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear(); //저장소를 지운다
    }
}
