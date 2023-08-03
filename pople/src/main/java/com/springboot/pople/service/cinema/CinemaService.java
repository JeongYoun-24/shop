package com.springboot.pople.service.cinema;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.UsersDTO;

import java.util.List;

public interface CinemaService {

    public Long register(CinemaDTO cinemaDTO);
    public CinemaDTO readOne(Long cinemaid);
    public void modify(CinemaDTO cinemaDTO);
    public void remove(Long cinemaid);

    public List<CinemaDTO> AllList();

}
