package com.springboot.pople.dto;

import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.Board;
import com.springboot.pople.entity.Movie;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    private String title;

    private String content;

    private String writer;
    private int hitcount; // 조회수
    private LocalDateTime regDate;


    @Builder.Default
    private List<BoardImgDTO> imgDTOList = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();
    public static BoardDTO of(Board board){
        // entity -> dto
        return modelMapper.map(board, BoardDTO.class);
    }



}
