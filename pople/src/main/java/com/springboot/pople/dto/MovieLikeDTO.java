package com.springboot.pople.dto;

import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieLikeDTO {


    private Long id ;

    private long movieid;

    private String userid;

    private LocalDateTime regDate; //추천 날짜


}
