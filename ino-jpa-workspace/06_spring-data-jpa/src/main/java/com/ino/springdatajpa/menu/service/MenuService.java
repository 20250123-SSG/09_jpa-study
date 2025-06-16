package com.ino.springdatajpa.menu.service;

import com.ino.springdatajpa.menu.dto.CategoryDto;
import com.ino.springdatajpa.menu.dto.MenuDto;
import com.ino.springdatajpa.menu.entity.Category;
import com.ino.springdatajpa.menu.entity.Menu;
import com.ino.springdatajpa.menu.repository.CategoryRepository;
import com.ino.springdatajpa.menu.repository.MenuRepository;
import com.ino.springdatajpa.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final PageUtil pageUtil;
    private final CategoryRepository categoryRepository;

    // 1. findById
    public MenuDto findMenuByCode(int menuCode) {
        // findById(식별자)
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("wrong menuCode"));

        log.info("menu: {}", menu);

        // Menu Entity -> MenuDTO 변환을 통해 반환
        // Entity <-> DTO 상호 변환 라이브러리 : ModelMapper
        MenuDto menuDto = modelMapper.map(menu, MenuDto.class);

        return menuDto;
    }

    public Map<String, Object> findMenuList(Pageable pageable) {
        Page<Menu> menuPage = menuRepository.findAll(pageable);

        Map<String, Object> map = pageUtil.getPageInfo(menuPage, 5);

        map.put("list", menuPage.getContent().stream().map(data -> {
            return modelMapper.map(data, MenuDto.class);
        }).collect(Collectors.toList()));

        return map;
    }

    public List<CategoryDto> findCategoryList() {
        List<Category> categoryList = categoryRepository.findAllSubCategory();

        // List<Category> List<catedto>
        List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> {
            return modelMapper.map(category, CategoryDto.class);
        }).toList();

        return categoryDtoList;
    }

    @Transactional // transaction 처리 대리
    public void registMenu(MenuDto menu) {
        menuRepository.save(modelMapper.map(menu, Menu.class)); // persist
    }

    @Transactional
    public void modifyMenu(MenuDto modMenu) {

        // 조회된 엔티티 => 영속 컨텍스트에 저장(@Id - @Entity, 스냅샷(복사본))
        Menu foundedMenu = menuRepository.findById(modMenu.getMenuCode())
                .orElseThrow(() -> new IllegalArgumentException("wrong menu number"));
        foundedMenu.setMenuName(modMenu.getMenuName());
        foundedMenu.setMenuPrice(modMenu.getMenuPrice());
        foundedMenu.setCategoryCode(modMenu.getCategoryCode());
        foundedMenu.setOrderableStatus(modMenu.getOrderableStatus());
    }

    @Transactional
    public void removeMenu(int code) {
        Menu foundedMenu = menuRepository.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("wrong menu number"));
        menuRepository.delete(foundedMenu);
        // menuRepository.deleteById(code); 곧장 삭제, 존재하지 않을 경우 예외 컨트롤을 위해 상기 방법 이용
    }
}
