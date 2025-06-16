package com.podoseee;

public class CategoryDto {
    private Integer categoryCode;
    private String categoryName;
    private Integer menuCount;

    public CategoryDto(Integer categoryCode, String categoryName, Integer menuCount) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.menuCount = menuCount;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", menuCount=" + menuCount +
                '}';
    }
}
