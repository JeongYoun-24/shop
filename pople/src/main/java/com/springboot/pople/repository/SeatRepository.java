package com.springboot.pople.repository;

import com.springboot.pople.entity.Seat;
import com.springboot.pople.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {


    List<Seat> findByTheater_Id(Long theaterid);
//    List<Seat> findByCinema_Cinemaid(Long cinemaid);

}
