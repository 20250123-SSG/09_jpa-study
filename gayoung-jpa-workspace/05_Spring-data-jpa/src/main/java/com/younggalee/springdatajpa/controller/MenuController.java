package com.younggalee.springdatajpa.controller;


import com.younggalee.springdatajpa.dto.MenuDto;
import com.younggalee.springdatajpa.menu.entity.Menu;
import com.younggalee.springdatajpa.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/menu")
@Controller
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{menuCode}") // 변수  // { } : 동적으로 변할 수 있는 값을 의미
    public String menuDetail(@PathVariable int menuCode, Model model) { //  URL 경로에 포함된 값을 메서드의 매개변수로 받을 때 사용하는 어노테이션
        MenuDto menu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);

        return "/menu/detail";
    }

    @GetMapping("/list") // 경로 같으니까 void
    public void menuList(Model model) {
        List<MenuDto> menuList = menuService.findMenuList();
        model.addAttribute("menuList", menuList);
    }
}
