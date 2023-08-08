package com.springboot.pople.dto;

import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Users;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRevFormDTO {

    private Long revid;

    private Users users;
    private Movie movie;

    
    private String revTitle;
    private String revContent;
    private int star;
    private LocalDateTime revDate;





}
