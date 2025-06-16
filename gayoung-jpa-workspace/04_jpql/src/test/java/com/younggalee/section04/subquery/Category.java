package com.younggalee.section04.subquery;

import com.younggalee.section03.join.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString

@Entity(name = "category4")
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

}

