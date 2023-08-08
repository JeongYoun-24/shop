package com.springboot.pople.dto;

import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Users;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentFormDTO {


    private Long id;

    private Movie movie;

    private Users users;

    private String title; // 제목
    private String content; // 댓글 내용
    private LocalDateTime regDate; // 작성 날짜
    private int star; //별점

    private static ModelMapper modelMapper = new ModelMapper();
    public static CommentFormDTO of(Comment comment){
        // entity -> dto
        return modelMapper.map(comment, CommentFormDTO.class);
    }

}
