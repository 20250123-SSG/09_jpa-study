package com.ino.springdatajpa.menu.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuDto {

    private Integer menuCode;

    private String menuName;

    private Integer menuPrice;

    private String orderableStatus;

    private Integer categoryCode;
}
