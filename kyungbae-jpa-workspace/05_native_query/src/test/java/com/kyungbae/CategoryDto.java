package com.kyungbae;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class CategoryDto {
    private Integer categoryCode;
    private String categoryName;
    private Integer menuCount;
}
