package com.springboot.pople.entity;

import com.springboot.pople.constant.ItemSellStatus;
import com.springboot.pople.dto.item.ItemFormDTO;
import com.springboot.pople.exception.OutOfStockException;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id; // 상품 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;   // 카테고리 폴딩키 연결

    private String itemName; // 상품 이름
    private String itemDetail; // 상품 정보
    private int price;  // 상품 가격
    private int stockQty;  // 재고 수량
    private String itemImg; // 상품 이미지

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  // 재고 수량 여부


    public void updateItem(ItemFormDTO itemFormDTO){
        this.itemName = itemFormDTO.getItemName();
        this.price = itemFormDTO.getPrice();
        this.stockQty = itemFormDTO.getStockQty();
        this.itemDetail = itemFormDTO.getItemDetail();
        this.itemSellStatus = itemFormDTO.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        // 최종 수량 = 현재 수량 - 주문 수량
        int restStock = this.stockQty - stockNumber;
        if (restStock<0){
            // 에러 처리 => 사용자 작성한 예외 처리를 생성하여 예외처리 발생 시킴
            throw new OutOfStockException("상품 재고가 부족합니다.(현재 재고 수량: "+this.stockQty+")");
        }

        this.stockQty = restStock;
    }

    // 상품 주문 취소후 재고 수량 증가시키는 메서드
    public void addStock(int stockNumber){
        this.stockQty += stockNumber;
    }


}
