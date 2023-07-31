package com.springboot.pople.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatid;  // 좌석 코드
    private int seatNo; //좌석 번호
    private int seatGroup; // 좌석 그룹
    private int seatLineNo; // 좌석 라인줄 번호

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;  // 상영관 번호  N대 1관계
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;  // 영화관 번호  N대 1 관계

}
