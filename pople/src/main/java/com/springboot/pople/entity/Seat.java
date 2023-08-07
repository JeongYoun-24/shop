package com.springboot.pople.entity;

import com.springboot.pople.constant.SeatStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString(exclude = "seatStatus" )
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {
//(exclude={"theater","cinema","seatStatus"})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatid;  // 좌석 코드
    private int seatNo; //좌석 번호
    private int seatGroup; // 좌석 그룹
    private String seatLineNo; // 좌석 라인줄 번호

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;  // 상영관 번호  N대 1관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;  // 영화관 번호  N대 1 관계

}
