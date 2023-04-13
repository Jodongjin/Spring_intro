package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    /*
    * JPQL: select m from Member m where m.name = ?
    * finyByName에서 findBy 뒤에 오는 text로 조건문 조회 가동
    */
    @Override
    Optional<Member> findByName(String name);
}
