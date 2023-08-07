package com.springboot.pople.entity;


import com.springboot.pople.constant.TicktingStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tickting")
public class Tickting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticking_id")
    private Long id;  //영화 내역 번호

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private MovieSchedule movieSchedule;

    private long movieCode;  // 영화 번호
    private Long theaterCode;  // 상영관 번호
    private LocalDateTime dayDate; // 애매 날짜
    private String movieName; // 영화 이름
    private String movieTime; // 영화 런닝타임
    private String cinemaName; // 영화관 이름
    private String seat;       // 좌석
    private int count;       // 예매 인원수
    private int ticktingPrice; // 티켓 가격
    private String card;       // 카드사
    private String cardNum;   // 카드 번호
    private String phone;     // 핸드폰 번호
    private String cardCheck;   // 카드 결제 유무
    private LocalDateTime regDate;  // 결제 날짜
    @Enumerated(EnumType.STRING)
    private TicktingStatus ticktingStatus;  // 예매 여부 얘매중, 취소
    private String movieRating; // 관람등급

    @OneToMany(
            fetch = FetchType.LAZY,
//              mappedBy = "/templates/order",
            orphanRemoval = true, // 고아객체 제거
            cascade = CascadeType.ALL) // 고객이 주문할 상품을 선택하고 주문할 때 주문 엔티티를 저장하면서 주문 상품 엔티티도 함께 저장되는 경우
    private List<Tickting> orderMovies = new ArrayList<>();


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long ticktingno;  //영화 내역 번호
//    @Column(nullable = false,length = 10)
//    private Long ticktingCount;  // 예약 인원수
//    @Column(nullable = false,length = 30)
//    private String seatNumber; // 예약좌석 번호
//    private LocalDateTime ticktingDate;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Movie movie; // 영화 번호
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Users users; // 회원 아이디
//    @ManyToOne(fetch = FetchType.LAZY)
//    private TimeTable timeTable;  // 영화 상영 시간 번호

}
