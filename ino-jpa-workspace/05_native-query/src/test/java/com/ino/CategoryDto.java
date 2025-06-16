package com.ino;


import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EntityResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.*;

import java.beans.BeanProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CategoryDto {

    private Integer categoryCode;
    private String categoryName;
    private Integer menuCount;
}
