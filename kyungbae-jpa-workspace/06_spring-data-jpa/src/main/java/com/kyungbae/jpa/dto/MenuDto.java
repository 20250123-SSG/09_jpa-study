package com.kyungbae.jpa.dto;

import lombok.*;

/*
    Controller <-> Service <-> Repository
               DTO        Entity
    1. 책임 분리
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class MenuDto {
    private Integer menuCode;
    private String menuName;
    private Integer menuPrice;
    private Integer categoryCode;
    private String orderableStatus;
}
