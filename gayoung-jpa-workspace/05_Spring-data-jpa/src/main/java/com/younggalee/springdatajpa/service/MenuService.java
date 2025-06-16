package com.younggalee.springdatajpa.service;

import com.younggalee.springdatajpa.dto.MenuDto;
import com.younggalee.springdatajpa.menu.entity.Menu;
import com.younggalee.springdatajpa.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    // 단일 엔티티 조회 - ex 상세조회
    // 1. findById
    public MenuDto findMenuByCode(int menuCode) { //findbyId라는 이름으로 이미 jpaRepository 상위 인터페이스에 있음. 따라서 그냥 사용하면됨.
        // findById(식별자) : Optional<T> //   값이 있을 수도 있고, 없을 수도 있는 Wrapper클래스
        // * Optional : nullpointerException 방지를 위한 다양한 기능 제공

        Menu menu = menuRepository.findById(menuCode).orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 코드입니다.")); // orElseThrow(발생시킬예외객체) : 해당조회결과가 있다면 엔티티객체를 반환하고, 없으면 null. 그때 예외를 발생시킴)
        //엔티티 자체를 뷰에 보내주고 작업을 하면 값이 변할 수 있기 때문에
        // 뷰단에서는 비영속상태의 DTO를 사용해서 DB에 값이 변하는 위험 방지

        //따라서 Service에서는 DTO로 변환하여 반환
        //엔티티 (get) > DTO (set) 변환하기 : 이걸 자동으로 해주는 라이브러리(modelMapper) 가져와서 사용할거임.
        //원래는 이렇게 MenuDto menuDto = new MenuDto(menu.getMenuCode(), menu.getMenuName(), ); //귀찮음 심지어 변수명, 타입도 같은데..
        // 직접 mvn가서 modelmapper검색 .gradle에 직접 추가
        MenuDto menuDto = modelMapper.map(menu, MenuDto.class); //변환시키고자하는 원본객체, 뭘로 변환시킬건지 목적지 객체

        return menuDto;
    }


    // 전체 엔티티 조회 - ex 전체목록조회
    //2. findAll
    public List<MenuDto> findMenuList() {
        // 1) findAll(): List<T>  // T로 Menu.class로 넣어놓음. >> repository interface
        // List<Menu> menuList = menuRepository.findAll(); // 정렬기준, 페이지네이션을 위한 객체 넘길 수 있음

        // 2) findAll(Sort) : List<T> - 정렬 기준을 전달해서 실행
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending()); //엔티티 필드명

        // 3) 페이징처리는 내일

        // DTO 변환 : List<Menu> => List<MenuDto>
        //           .stream()       .map()            .collect()
        // List<Menu> => Stream<Menu> => Stream<MenuDto> => List<MenuDto>
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).collect(Collectors.toList());
        }
    }






















