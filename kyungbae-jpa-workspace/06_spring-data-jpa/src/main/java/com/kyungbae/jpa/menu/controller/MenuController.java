package com.kyungbae.jpa.menu.controller;

import com.kyungbae.jpa.dto.MenuDto;
import com.kyungbae.jpa.menu.entity.Menu;
import com.kyungbae.jpa.menu.service.MenuService;
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

    @GetMapping("/{menuCode}")
    public String menuDetail(@PathVariable int menuCode, Model model){
        MenuDto menu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);

        return "menu/detail";
    }

    @GetMapping("/list")
    public String menuList(Model model){
        List<MenuDto> menuList = menuService.findMenuList();
        model.addAttribute("menuList", menuList);
        return "menu/list";
    }
}
