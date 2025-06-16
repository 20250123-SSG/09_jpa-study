package com.podoseee.springdatajpa.menu.service;

import com.podoseee.springdatajpa.menu.dto.MenuDto;
import com.podoseee.springdatajpa.menu.entity.Menu;
import com.podoseee.springdatajpa.menu.repository.MenuRepository;
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

    private final ModelMapper modelMapper;
    private final MenuRepository menuRepository;

    // 1. findById
    public MenuDto findMenuByCode(int menuCode){

        // findById(식별자) : Optional<T>
        // * Optional : NullPointerException 방지를 위한 다양한 기능 제공

        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 코드입니다."));

        // Menu 엔티티 => Menu DTO로 변환해서 반환
        // Entity <=> DTO 상호 변환 도와주는 라이브러리 사용 : ModelMapper
        return modelMapper.map(menu, MenuDto.class);
    }

    // 2. findAll
    public List<MenuDto> findMenuList(){
        // 1) findAll() : List<T>
        //List<Menu> menuList = menuRepository.findAll();

        // 2) findAll(Sort) : List<T> - 정렬 기준을 전달해서 실행
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

        //         .stream()        .map()           .collect()
        // List<Menu> => Stream<Menu> => Stream<MenuDto> => List<MenuDto>
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
    }

}
