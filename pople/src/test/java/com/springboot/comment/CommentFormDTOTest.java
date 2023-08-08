package com.springboot.comment;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.MovieCommentRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CommentFormDTOTest {

    @Autowired
    private MovieCommentRepository movieCommentRepository;
    @Test
    @DisplayName(value = "댓글 테스트 ")
    public void listtest(){
        Long movieid= 1L;

        Movie movie = Movie.builder()
                .movieid(movieid)
                .build();

//     List<Comment> comments =  movieCommentRepository.findComment(movie.getMovieid());
//        log.info(comments);



    }



}
