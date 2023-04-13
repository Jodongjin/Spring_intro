package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/* Spring code Test */
@SpringBootTest
@Transactional // Test Case에 붙으면 Test 후에 DB transaction을 commit하지 않고 rollback -> DB 비워줌 (중복 insert 방지)
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Commit // Commit 붙으면 Rollback 안 하고 실제로 DB에 반영
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("ddd");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get(); // 가입한 멤버의 id로 멤버를 조회
        assertThat(member.getName()).isEqualTo(findMember.getName()); // 가입한 멤버와 조회된 멤버의 이름이 같은지 비교
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring3");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // assertj에서 제공하는 exception 검증 메서드, 람다식에서 터지는 예외가 첫 번째 인자에 있는 예외 클래스와 같아야 에러 안 뜸 (예측 성공)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        try { // try ~ catch로 exception 잡기
            memberService.join(member2);
            fail(); // 예외 잡기 실패
        } catch (IllegalStateException e) { // 예외 잡기 성공
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */
    }
}