package com.springboot.pople.dto;

import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Seat;
import com.springboot.pople.entity.Theater;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
@Builder
public class SeatFormDTO {

    private Long seatid;  // 좌석 코드
    private int seatNo; //좌석 번호
    private int seatGroup; // 좌석 그룹
    private String seatLineNo; // 좌석 라인줄 번호

    private Theater theater;  // 상영관 번호  N대 1관계

    private Cinema cinema;  // 영화관 번호  N대 1 관계

    private static ModelMapper modelMapper = new ModelMapper();
    public static SeatFormDTO of(Seat seat){
        // entity -> dto
        return modelMapper.map(seat, SeatFormDTO.class);
    }


}
