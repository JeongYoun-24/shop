package com.springboot.pople.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRevDTO {

    private Long revid;
    private String userId;
    private Long movieId;
    private String revTitle;
    private String revContent;
    private LocalDateTime revDate;





}
