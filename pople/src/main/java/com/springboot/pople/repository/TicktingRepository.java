package com.springboot.pople.repository;

import com.springboot.pople.entity.Tickting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicktingRepository extends JpaRepository<Tickting,Long> {
}
