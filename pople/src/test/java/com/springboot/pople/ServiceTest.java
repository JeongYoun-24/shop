package com.springboot.pople;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.service.UsersService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ServiceTest {

    @Autowired
    private UsersService usersService ;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    @DisplayName("users insert테스트")
    public void testInsert() {
        IntStream.rangeClosed(3, 5).forEach(i -> {
            // 객체 생성
            Users users = Users.builder()
//                    .user_id("title..." + i)
//                    .user_pwd("content..." + i)
//                    .user_name("user" + i)
//                    .user_email("aaa@naver.com" + i)
//                    .phone("010-4324-4234")
//                    .birthdate("19990101")

                    .build();

            // 영속성 컨텍스트에 반영
            Users result = usersRepository.save(users);

        });

    }
    @Test
    @DisplayName("users 생성 테스트")
    public void testRegister(){
        //log.info(boardService.getClass().getName());

        UsersDTO boardDto = UsersDTO.builder()
//                .user_id("bbb" )
//                .user_pwd("1234" )
//                .user_name("user1" )
//                .user_email("aaa@naver.com")
//                .phone("010-4324-4234")
//                .birthdate("19990101")
               .build();

        String user_id = usersService.register(boardDto);


    }



    @Test
    @DisplayName("board select 테스트")
    public void testSelect(){

        String user_id = "aaa";
        Optional<Users> result = usersRepository.findById(user_id);
        Users board = result.orElseThrow();

        log.info(board);
    }










}
