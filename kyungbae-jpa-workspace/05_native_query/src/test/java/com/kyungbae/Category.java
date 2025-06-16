package com.kyungbae;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "category4")
@Table(name = "tbl_category")
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping1",
        entities = {@EntityResult(entityClass = Category.class)},
        columns = {@ColumnResult(name = "menu_count")}
)
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping2",
        classes = @ConstructorResult(
                targetClass = CategoryDto.class,
                columns = { // column 순서도 중요
                        @ColumnResult(name = "category_code", type = Integer.class),
                        @ColumnResult(name = "category_name", type = String.class),
                        @ColumnResult(name = "menu_count", type = Integer.class)
                }
        )
)
public class Category {

    @Id
    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

}
