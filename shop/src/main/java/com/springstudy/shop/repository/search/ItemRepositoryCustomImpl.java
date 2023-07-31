package com.springstudy.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springstudy.shop.constant.ItemSellStatus;
import com.springstudy.shop.dto.ItemSearchDTO;
import com.springstudy.shop.entity.Item;
import com.springstudy.shop.entity.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.SecondaryTable;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements  ItemRepositoryCustom {
    private JPAQueryFactory queryFactory; // 동적 쿼리 생성

    public ItemRepositoryCustomImpl(EntityManager em){
        // 동적 쿼리 객체 생성
        this.queryFactory = new JPAQueryFactory(em);

    }
    // 상품 판매 상태 조건이 전체 (null)일 경우는 null반환
    // null 이면 whre절에서 해당 조건 무시 , null아니면 판매중 or품절 상태 기준에 해당 조건의 상품만 조회
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchsellStatus){
        return  searchsellStatus == null? null : QItem.item.itemSellStatus.eq(searchsellStatus);
    }
    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();
        if(StringUtils.equals("all",searchDateType) || searchDateType == null){

        }else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);

        }else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);

        }else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m,", searchDateType)){
            dateTime = dateTime.minusDays(6);

        }else if(StringUtils.equals("1s", searchDateType)){

        }

        return  QItem.item.regDate.after(dateTime); // "this> right" 수식이 반환
    }
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // this like 키워드 문자열   형식으로 전환
        if(StringUtils.equals("itenNm",searchBy)){
            return QItem.item.itemNm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy",searchBy)){
            return QItem.item.createBy.like("%"+searchQuery+"%");

        }
        return null;
    }


    @Override
    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {

        QueryResults<Item> result = queryFactory
                .selectFrom(QItem.item) // 상품 데이터 조회
                // where 조건에 ',' 단위는 and 조건식으로 인식
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
                        searchSellStatusEq(itemSearchDTO.getSellStatus()),
                        searchByLike(itemSearchDTO.getSearchBy(),itemSearchDTO.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인텍스
                .limit(pageable.getPageSize()) // 한번에 가져올 최대 개수
                .fetchResults(); // 조회 리스트 및 전체 개수 를 포함한 QyeryResult 반환

        List<Item> content = result.getResults();
        long total = result.getTotal();


        return new PageImpl<>(content,pageable,total);
    }


}
