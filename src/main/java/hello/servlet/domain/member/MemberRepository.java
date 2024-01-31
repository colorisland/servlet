package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    // 싱글톤 클래스를 가져오는 용도의 메소드.
    public static MemberRepository getInstance() {
        return instance;
    }
    // 싱글톤으로 쓸 클래스이기 때문에 생성자를 private 으로 해놓는다.
    private MemberRepository() {

    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        // 이렇게 해야 밖에서 값을 조작해도 store에 있는 값은 변하지 않는다.
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
