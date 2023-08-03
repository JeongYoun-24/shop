package com.springboot.users;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.service.LoginService;
import com.springboot.pople.service.UsersService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@Log4j2
@AutoConfigureMockMvc
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usrsService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;


    public Users createMember(){
        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setUserid("ttt");
        usersDTO.setPassword("1234");
        usersDTO.setName("알파");
        usersDTO.setEmail("ttt@naver.com");
        usersDTO.setPhone("010-1231-3123");
        usersDTO.setBirthDate("19990101");

        // dto -> entity, 암호화 적용
        return Users.createMember(usersDTO, passwordEncoder);
    }
    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){

        Users users = createMember();
        Users savedMember =  loginService.saveUsers(users);

        // assertEquals메소드를 이용하여 저장하려고 요청했던 값과 실제 저장된 데이를 비교
        // assertEquals(기대값, 실제값);
        assertEquals(users.getEmail(), savedMember.getEmail());
        assertEquals(users.getName(), savedMember.getName());
        assertEquals(users.getUserid(), savedMember.getUserid());
        assertEquals(users.getPassword(), savedMember.getPassword());
        assertEquals(users.getRole(), savedMember.getRole());

    }

    @Test@DisplayName("중복 회원 테스트-이메일 기준")
    public void saveDuplicateMemberTest(){
        Users memberTest1 = createMember();
        Users memberTest2 = createMember();

        loginService.saveUsers(memberTest1);

        Throwable e = assertThrows(IllegalStateException.class, () ->{
            loginService.saveUsers(memberTest2);
        });

        assertEquals("이미 가입된 회원입니다.",e.getMessage());
    }


    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "aaa@naver.com";
        String password ="1234";

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email)
                .password(password)
        ).andExpect(SecurityMockMvcResultMatchers.authenticated());

    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String email = "aaa@naver.com";
        String password = "1234";

//        this.createMember(email,password);

        mockMvc.perform(
                formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email)
                        .password(password)
        ).andExpect(SecurityMockMvcResultMatchers.unauthenticated());


    }



    @Test
    @DisplayName("회원 정보 등록 테스트 ")
    public void userinsertTest(){

        Users users = Users.builder()
                .userid("aaa")
                .password("1234")
                .name("알파")
                .email("aaa@naver.com")
                .phone("010-1234-2344")
                .birthDate("19990101")
                .build();

        Users result = usersRepository.save(users);

        log.info(result);
    }




}