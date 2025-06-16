package com.ino.springdatajpa.menu.controller;

import com.ino.springdatajpa.menu.dto.CategoryDto;
import com.ino.springdatajpa.menu.dto.MenuDto;
import com.ino.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String menuDetail(@PathVariable int menuCode, Model model){
        MenuDto menu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);
        return "menu/detail";
    }

//    @GetMapping("/list")
//    public String menuList(Model model){
//        List<MenuDto> menu = menuService.findMenuList();
//        model.addAttribute("menu", menu);
//        return "menu/list";
//    }

    // /menu/list?page=xx&size=xx&sort=xxx,asc|desc
    @GetMapping("/list")
    public String menuList(@PageableDefault Pageable pageable, Model model){
        log.info("pageable: {}", pageable); // pageable 매개변수에 page, size, sort 자동바인딩
        pageable = pageable.withPage(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1);

        if(pageable.getSort().isEmpty()) {
            // 정렬만 변경 불가 -> 재생성 필요
            pageable = PageRequest.of(pageable.getPageNumber()
                                     ,pageable.getPageSize()
                                     , Sort.by("menuCode").descending());
        }

        Map<String, Object> map = menuService.findMenuList(pageable);

        model.addAttribute("menu", map.get("list"));
        model.addAttribute("page", map.get("page"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));
        model.addAttribute("isFirst", map.get("isFirst"));
        model.addAttribute("isLast", map.get("isLast"));

        return "menu/list";
    }

    @GetMapping("/regist")
    public void registPage(){
    }

    @GetMapping(value = "/categories", produces = "application/json")
    @ResponseBody
    public List<CategoryDto> categoryList(){
        return menuService.findCategoryList();
    }

    @PostMapping("/regist")
    public String registMenu(MenuDto menu){
        menuService.registMenu(menu);
        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public String modifyPage(int code, Model model){
        model.addAttribute("menu", menuService.findMenuByCode(code));
        return "menu/modify";
    }

    @PostMapping("/modify")
    public String modifyMenu(MenuDto modMenu, int code){
        menuService.modifyMenu(modMenu);
        return "redirect:/menu/" + modMenu.getMenuCode();
    }

    @GetMapping("/remove")
    public String removeMenu(int code){
        menuService.removeMenu(code);
        return "redirect:/";
    }
}
