package com.springboot.pople.dto;


import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Theater;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {

    private Long seatid;  // 좌석 코드
    private int seatNo; //좌석 번호
    private int seatGroup; // 좌석 그룹
    private int seatLineNo; // 좌석 라인줄 번호

    private Long theaterid;  // 상영관 번호  N대 1관계

    private Long cinemaid;  // 영화관 번호  N대 1 관계


}
