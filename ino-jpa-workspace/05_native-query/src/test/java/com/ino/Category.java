package com.ino;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "category")
@Table(name = "tbl_category")
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping1",
        entities = {@EntityResult(entityClass = Category.class)}, // 결과중 일부 컬럼을 category Entity에 매핑할것 의미
        columns = {@ColumnResult(name = "menu_count")}
)
@SqlResultSetMapping(
        name = "categoryAndMenuCountMapping2",
        classes = @ConstructorResult(
                targetClass = CategoryDto.class,
                columns = {
                        @ColumnResult(name = "category_code", type = Integer.class),
                        @ColumnResult(name = "category_name", type = String.class),
                        @ColumnResult(name = "menu_count", type = Integer.class)
                } // 클래스 필드 정의 순서에 맞추어 전달해주어야함. 순서대로 생성자에 전달되며 생성됨
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
