package com.jjanggu;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor // categoryCode, categoryName, menuCount
@Getter
@Setter
@ToString
public class CategoryDto {

    private Integer categoryCode;
    private String categoryName;
    private Integer menuCount;

}
