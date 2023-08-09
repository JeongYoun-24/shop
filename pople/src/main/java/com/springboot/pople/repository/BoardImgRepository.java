package com.springboot.pople.repository;

import com.springboot.pople.entity.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg,Long> {

    List<BoardImg> findByBoardIdOrderByIdAsc(Long boardId);

    // 주문 상품 대표이미지 조회
    BoardImg findByBoardIdAndRepImgYn(Long boardId, String repimgYn);

    BoardImg findByBoardId(Long boardid);

//    @Query("select o from Order o where o.id = :orderId")
//    @Query("select i from Board_Img i where i.id =:boardid ")
//    BoardImg findBoardImg(@Param("biardid")Long boardid);

    @Modifying
    @Query(value =("delete from Board_Img where board_id=:boardid"), nativeQuery=true)
    void findDelete(@Param("boardid")Long boardid);

}
