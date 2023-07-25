package com.springstudy.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@Getter
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private String cerateBy; // 수정자

    @LastModifiedDate
    private String modifindBy;// 등록자



}
