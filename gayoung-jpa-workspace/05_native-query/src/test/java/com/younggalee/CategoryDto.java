package com.younggalee;

import jakarta.persistence.*;

@Entity(name = "category")
@Table(name = "tbl_category")
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping2",
        classes = @ConstructorResult(
                targetClass = CategoryDto.class,
                columns = {
                        @ColumnResult(name = "category_code", type = Integer.class),
                        @ColumnResult(name = "category_name", type = String.class),
                        @ColumnResult(name = "ref_category_code", type = Integer.class)
                }
        )
)


public class CategoryDto {

    @Id
    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

}

