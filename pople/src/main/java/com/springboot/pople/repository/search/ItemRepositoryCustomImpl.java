package com.springboot.pople.repository.search;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.pople.constant.ItemSellStatus;
import com.springboot.pople.dto.item.ItemSearchDTO;
import com.springboot.pople.dto.item.MainItemDTO;
import com.springboot.pople.dto.item.QMainItemDTO;
import com.springboot.pople.dto.movie.QMainMovieDTO;
import com.springboot.pople.entity.Item;
import com.springboot.pople.entity.QItem;
import com.springboot.pople.entity.QItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements  ItemRepositoryCustom{

    private JPAQueryFactory queryFactory;// 동적 쿼리 객체변수

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em); // 동적 쿼리 객체 생성
    }

    // 상품 판매 상태 조건이 전체(null)일 경우는 null 반환
    // null이면 whre절에서 해당 조건 무시,
    // null아니면 판매중 or 품절상태 기준에 해당 조건의 상품만 조회
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchItemSellStatus){
        // "this == searchItemSellStatus" 형식으로 반환
        return (searchItemSellStatus == null )
                ? null : QItem.item.itemSellStatus.eq(searchItemSellStatus);
    }

   /* private BooleanExpression  regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if (StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if (StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if (StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        // "this > dateTime" 수식이 반환
        return QItem.item.regDate.after(dateTime);
    }*/

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // "this like %searchQuery% " 형식으로 반환
        if (StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemName.like("%"+searchQuery+"%");
        }
       /* else if (StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%"+searchQuery+"%");
        }*/

        return null;
    }


   /* @Override
    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {

        QueryResults<Item> result = queryFactory
                .selectFrom(QItem.item) // 상품 데이터 조회
                // where 조건절에 ','단위는 and조건식으로 인식
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
                        searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())   // 데이터를 가지고 올 시작 인텍스
                .limit(pageable.getPageSize())  // 한번에 가져올 최대 개수
                .fetchResults(); // 조회한 리스트 및 전체 개수를 포함한 QueryResult반환

        List<Item> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);

    }*/

    /* 메인 페이지에 보여줄 상품 리스를 가져오는 메서드 구현 */

    // 검색어가 null이 아니면 조건식 반환
    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)? null : QItem.item.itemName.like("%"+searchQuery+"%");
    }
    @Override
    public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
        // 도메인에 있는 상품정보, 상품이미지 정보 엔티티 도메인객체 생성
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        // 조인(innter join): 기준 테이블과 조인 테이블 모두 데이터가 존재해야 조회
        List<MainItemDTO> content = queryFactory
                .select( new QMainItemDTO(
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price
                        )
                )
                .from(itemImg)
                .join(itemImg.item, item)    // inner join(교집합)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNmLike(itemSearchDTO.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // 여러개 반환할 시(collection구조)

        long total = queryFactory
                .select(Wildcard.count)  //  count(*)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNmLike(itemSearchDTO.getSearchQuery()))
                .fetchOne();// 하나만 반환


        return new PageImpl<>(content, pageable, total);

        /*
          Querydsl 조회 결과를 반화는 메서드

          QueryResults<T> fetchResults() : 조회 대상 리스트 및 전체 개수를 포함하는 QueryResults로 반환
          T fetchOne() : 조회 대상이 1건이면 해당 타입 반환, 1건이상이면 에러 발생
          List<T> fetch(): 조회 대상이 리스트 반환

          T fetchFirst(): 조회 대상이 1건 또는 1건 이상이면 1건만 반환
          long fetchCount(): 전체 개수 반환
         */
    }
}
