package com.springboot.pople.service.users;

import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersJoinService {

    private final UsersRepository usersRepository;

    public Users saveUsers(Users users){
        // 서버에서 validate적용
        validateDuplicateMember(users);
        return usersRepository.save(users);
    }

    private void validateDuplicateMember(Users users){
        Users findUsers = usersRepository.findByEmail(users.getEmail());

        // 이미 가입된 회원인 경우 예외 발생
        if (findUsers != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }


}
