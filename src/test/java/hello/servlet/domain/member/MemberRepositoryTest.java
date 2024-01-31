package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("채원", 26);
        // when
        Member savedMember = memberRepository.save(member);
        // then
        Member foundMember = memberRepository.findById(savedMember.getId());
        assertThat(savedMember).isEqualTo(foundMember);
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("채원", 26);
        Member member2 = new Member("성수", 26);
        memberRepository.save(member1);
        memberRepository.save(member2);
        // when
        List<Member> members = memberRepository.findAll();
        // then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1,member2);
    }
}
