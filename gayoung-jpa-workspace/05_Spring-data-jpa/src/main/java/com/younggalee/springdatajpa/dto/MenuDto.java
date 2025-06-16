package com.younggalee.springdatajpa.dto;

/*
    Controller <-> Service <-> Repository
               DTO   변환   Entity
    > database에 flush되어 반영될 수 있기 때문에 (엔티티는 db에 매핑되어 있는거, 따라서 뷰에서는 모두 필요하지 않을 수 있음 필요한것만 dto에 담아서 불필요한 노출을 막는 것이 좋음(ex.비밀번호) 엔티티는 모든 정보를 다 가지고 있으니까)
    1. 레이어 간 책임 분리
    2. 엔티티의 불필요한 노출 방지
    3. 프레젠테이션 로직에서 엔티티 사용시 발생될 수 있는 문제 방지
    4. API 응답 구조 일관성

    ## ModelMapper ##
    1. 자바 객체 간에 데이터 매핑을 자동으로 처리해주는
    2. 구조가 비슷한 객체간에 필드값을 손쉽게 복사할
        ex) 엔티티, 디티오
 */

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class MenuDto {
    private Integer menuCode;
    private String menuName;
    private Integer menuPrice;
    private Integer categoryCode;
    private String orderableStatus;
}
