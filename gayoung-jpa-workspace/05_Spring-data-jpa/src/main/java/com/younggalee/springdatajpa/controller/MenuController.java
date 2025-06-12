package com.younggalee.springdatajpa.controller;


import com.younggalee.springdatajpa.dto.CategoryDto;
import com.younggalee.springdatajpa.dto.MenuDto;
import com.younggalee.springdatajpa.menu.entity.Category;
import com.younggalee.springdatajpa.menu.entity.Menu;
import com.younggalee.springdatajpa.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

//    // 페이징 전
//    @GetMapping("/list") // 경로 같으니까 void
//    public void menuList(Model model) {
//        List<MenuDto> menuList = menuService.findMenuList();
//        model.addAttribute("menuList", menuList);
//    }

    /*
        ##Pageable##
        1. 페이징 처리에 필요한 정보 (page, size, sort)를 처리하는 인터페이스
        2. pageable 객체를 통해서 페이징 처리와 정렬을 동시에 처리할 수 있음
        3. 사용방법
            1) 페이징 처리에 필요한 정보듣 따로 파라미터 전달받아 직접 생성하는 방법
                PageRequest.of(요청페이지 정보, 조회할 데이터 건수, Sort객체)
            2) 정해진 파라미터 (page, size, sort)러 전달받아 생성된 객체를 바로 주입하는 방법
                @PageableDefault Pageable pageable
                => 따로 전달된 파라미터가 존재하지 않을 경우 기분값(0, 10, 없음)
         4. 주의사항
            Pageable 인터페이스는 조회할 페이지번호를 0부터 인식
            >> 넘어오는 페이지 번호 -1 해야됨
    */

    // /menu/list?page=xx&size=xx&sort=xxx,asc|desc
    // 페이징 후
    @GetMapping("/list") // 경로 같으니까 void
    public void menuList(@PageableDefault Pageable pageable, Model model) {
       log.info("pageable: {}", pageable); // [number: 2, size 20, sort: menuPrice: DESC] 이런 식으로 자동바인딩됨.
        // page 번호 -1 시키기 - withPage(): 현재 pagable의 기존설정(size,sort)는 그대로 두고, 페이지 번호만 바꾼 새로운 Pageable 객체 생성
        pageable = pageable.withPage(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1);  // 단 기존 number이 0일때는 제외
        // 따라서 덮어씌워야함
        log.info("변경 후, pageable: {}", pageable);

        if(pageable.getSort().isEmpty()) { // 정렬 파라미터가 존재하지 않을 경우 > 기본 정렬 기준 세우기
            // 정렬만 바꾸는건 따로 존재하지 않음 >>
            pageable = PageRequest.of(pageable.getPageNumber()
                    , pageable.getPageSize()
                    , Sort.by("menuCode").descending());
        }

        Map<String, Object> map = menuService.findMenuList(pageable);
        log.info("{}",map);
        model.addAttribute("menuList", map.get("menuList"));
        model.addAttribute("page", map.get("page"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));
        model.addAttribute("isFirst", map.get("isFirst"));
        model.addAttribute("isLast", map.get("isLast"));
        // 이걸로 페이징바 만들 수 있음

    }

    @GetMapping("/regist")
    public void registPage() {}

    @ResponseBody
    @GetMapping(value = "/categories", produces = "application/json")
    public List<CategoryDto> categoryList(){
        return menuService.findCategoryList();
    }

    @PostMapping("/regist")
    public String regist(MenuDto newMenu){ // DTO로 데이터 받기
        menuService.registMenu(newMenu);
        return "redirect:/menu/list"; //재요청
    }

    @GetMapping("/modify")
    public void modifyPage(int code, Model model) {
        // 조회 후, 수정 페이지에 값 전달
        MenuDto menu = menuService.findMenuByCode(code);
        model.addAttribute("menu", menu);
        log.info("7번에 대한 정보, menu: {}", menu);
    }

    @PostMapping("/modify")
    public String modifyMenu(MenuDto modifyMenu) {
        // 저장
        menuService.modifyMenu(modifyMenu);
        return "redirect:/menu/" + modifyMenu.getMenuCode();
    }

    @GetMapping("/remove")
    public String removeMenu(int menuCode) {
        menuService.removeMenu(menuCode);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchMenu(String type, String query, Model model) {//@requestParam 생략됨
        List<MenuDto> menuList = new ArrayList<>();
        if("price".equals(type)){ // type이 혹시 null일 수도 있으니까 npe 피하기 위해 비교할 값을 앞에다 적는편
            menuList = menuService.findMenuByPrice(Integer.parseInt(query));
            menuList.forEach(System.out::println);
        } else if ("name".equals(type)){
            menuList = menuService.findMenuByName(query);
            menuList.forEach(System.out::println);
        } else if ("both".equals(type)){ //query = "10000,마늘";
            menuList = menuService.findMenuByPriceAndName(query.split(",")); // String[] 형태로 전달됨
            menuList.forEach(System.out::println);
        }

        return "redirect:/";
    }
}














