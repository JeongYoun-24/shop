package com.springboot.pople.repository;

import com.springboot.pople.entity.Board;
import com.springboot.pople.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface BoardRopository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {


    @Modifying
    @Query(value = ("update Board i set i.hitcount= i.hitcount+1 i where i.board_id =:boardid"), nativeQuery=true)
    int hitBoard(@Param("boardid") Long boardid);

//    @Query("select o from Order o where o.id = :orderId")
//    @Update("update manager_board set hitcount=hitcount+1 where m_board= #{m_board}") // 조회수 증가 업데이트
}
