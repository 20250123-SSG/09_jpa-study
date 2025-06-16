package com.kyungbae.jpa.menu.service;

import com.kyungbae.jpa.dto.MenuDto;
import com.kyungbae.jpa.menu.entity.Menu;
import com.kyungbae.jpa.menu.repository.MenuRepository;
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

    // 1. findById
    public MenuDto findMenuByCode(int menuCode) {

        // findById(식별자) : Optional<T>
        // * Optional : NPE 방지를 위한 다양한 기능 제공

        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(/*발생시킬예외객체*/
                        () -> new IllegalArgumentException("잘못된 메뉴 코드입니다.")
                );

        // Menu 엔티티 -> Menu DTO로 변환해서 반환
        // Entity <-> DTO 상호 변환 라이브러리 사용 : ModelMapper
        return modelMapper.map(menu, MenuDto.class);
    }

    // 2. findAll
    public List<MenuDto> findMenuList() {
        // 1) findAll() : List<T>
//        List<Menu> menuList = menuRepository.findAll();

        // 2) findAll(Sort) : List<T> - 정렬기준을 전달해서 실행
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .toList();
//                .collect(Collectors.toList());
    }
}
