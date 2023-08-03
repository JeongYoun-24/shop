package com.springboot.pople.dto.movie;

import com.springboot.pople.constant.MovieStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MovieSearchDTO {

    private String searchDateType;
    private MovieStatus movieStatus;
    private String searchBy;
    private String searchQuery = "";


}
