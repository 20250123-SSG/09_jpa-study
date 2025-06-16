package com.younggalee;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "category")
@Table(name = "tbl_category")
@SqlResultSetMapping( // 네이티브 쿼리의 결과를 엔티티나 디티오등 원하는 형태로 매핑시키는 어노테이션
        name = "categoryAndMenuCountMapping1",
        entities = {@EntityResult(entityClass = Category.class)}, //쿼리실행결과중 일부를 카테고리엔티티에 메핑되어서 반환
        columns = {@ColumnResult(name = "menu_count")}
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

