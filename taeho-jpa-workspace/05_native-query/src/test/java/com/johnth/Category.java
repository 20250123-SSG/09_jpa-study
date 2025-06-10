package com.johnth;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name="category")
@Table(name="tbl_category")
// 쿼리 실행 결과 일부를 엔티티로 매핑
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping1",
        entities = {@EntityResult(entityClass = Category.class)},
        columns = {@ColumnResult(name = "menu_count")}
)
// 쿼리 실행 결과를 DTO로 매핑
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping2",
        classes = @ConstructorResult(
                targetClass = CategoryDto.class,
                columns = {
                        @ColumnResult(name="category_code", type=Integer.class),
                        @ColumnResult(name="category_name", type=String.class),
                        @ColumnResult(name="menu_count", type=Integer.class)
                }
        )
)
public class Category {
    @Id
    @Column(name="category_code")
    private Integer categoryCode;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="ref_category_code")
    private Integer refCategoryCode;
}
