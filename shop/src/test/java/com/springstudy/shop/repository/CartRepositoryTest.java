package com.springstudy.shop.repository;

import com.springstudy.shop.dto.MemberDTO;
import com.springstudy.shop.entity.Cart;
import com.springstudy.shop.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
@Transactional // 테스트용 , lollback 자동 적용
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;


    public Member createMember(){
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setEmail("test@email.com");
        memberDTO.setName("엄동복");
        memberDTO.setAddress("수준");
        memberDTO.setPassword("1234");

        return Member.createMember(memberDTO,passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트 ")
    public void findCartAandMemberTest(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        // JPA는 영속성 컨텍스트 에 데이터를 저장후 트랜잭션이 끝날때 Jlush() 호출하여 데이터에 반영
        em.flush();
        // JAP는 영속성 컨텍스트로 부터 엔티티를 조회 후 영속성 컨텍스트에 엔티티가 없을경우 데이터베이스 조회
        em.clear();

        Cart saveCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(saveCart.getMember().getId(),member.getId());


    }





}
