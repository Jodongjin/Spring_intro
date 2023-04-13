package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/* Only Java code Test */
class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    /*
    MemberService의 repository와 다른 인스턴스임 (지금은 static이라 괜찮은 것) -> 같은 repository로 테스트해야 함
    -> MemberService class 에서 MemberRepository를 외부에서 넣을 수 있게 끔 생성자 정의
    => dependency injection (의존관계 주입)
    */

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

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
        member1.setName("spring");

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}