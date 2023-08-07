package com.springboot.pople.service.seat;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.dto.TheaterDTO;

import java.util.List;

public interface SeatService {

    public Long register(SeatDTO seatDTO);
    public SeatDTO readOne(Long seatid);
    public void modify(SeatDTO seatDTO);
    public void remove(Long seatid);

    public List<SeatDTO> theaterList(Long theaterid);
    public List<SeatDTO> AllList();

}
