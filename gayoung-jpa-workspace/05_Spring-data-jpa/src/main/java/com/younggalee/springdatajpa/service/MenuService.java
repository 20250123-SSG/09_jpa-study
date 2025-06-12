package com.younggalee.springdatajpa.service;

import com.younggalee.springdatajpa.dto.CategoryDto;
import com.younggalee.springdatajpa.dto.MenuDto;
import com.younggalee.springdatajpa.menu.entity.Category;
import com.younggalee.springdatajpa.menu.entity.Menu;
import com.younggalee.springdatajpa.repository.CategoryRepository;
import com.younggalee.springdatajpa.repository.MenuRepository;
import com.younggalee.springdatajpa.util.PageUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final PageUtil pageUtil;
    private final CategoryRepository categoryRepository;

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
        //<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending()); //엔티티 필드명   //정렬기준 하나
        List<Menu> menuList = menuRepository.findAll(Sort.by(
                Sort.Order.asc("categoryCode"),
                Sort.Order.desc("menuPrice")));
        // if 정렬기준이 두개이상이라면 순차적으로 여러개 둘 수 있음

        // DTO 변환 : List<Menu> => List<MenuDto>
        //           .stream()       .map()            .collect()
        // List<Menu> => Stream<Menu> => Stream<MenuDto> => List<MenuDto>
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
    }


    // 3) 페이저블 객체 전달받는 경우
    public Map<String, Object> findMenuList(Pageable pageable) {
        // findAll(Pageable) : Page<T> 출력형태 - 페이지 정보와 해당 요청 페이지에 필요한 엔티티목록조회결과(List<T>)가 담긴
        Page<Menu> pageAndMenu = menuRepository.findAll(pageable);

        log.info("총 개수: {}", pageAndMenu.getTotalElements()); // totalCount (조회안해와도됨)
        log.info("한 페이지당 표현할 개수: {}", pageAndMenu.getSize()); // display
        log.info("총 페이지수: {}", pageAndMenu.getTotalPages());  // totalPage
        // Page객체에 다 담겨있음 (따로 계산해주지 않아도)
        log.info("첫 페이지 여부: {}", pageAndMenu.isFirst());
        log.info("마지막 페이지 여부: {}", pageAndMenu.isLast());
        log.info("정렬 방식: {}", pageAndMenu.getSort());
        log.info("요청 페이지에 실제 조회된 개수 : {}", pageAndMenu.getNumberOfElements());
        log.info("현재 페이지에 조회된 메뉴 목록: {}", pageAndMenu.getContent());

        //페이징바 만들기 위해서 size를 고려한 begin, end페이지는 직접 계산해줘야함. (Util 클래스)
        Map<String, Object> map = pageUtil.getPageInfo(pageAndMenu, 5);
        map.put("menuList", pageAndMenu.getContent()
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .toList()); // DTO로 변환해서 담기
        return map;
    }

    // 3. Native Query 사용
    public List<CategoryDto> findCategoryList() {
        //List<Category> categoryList = categoryRepository.findAll(); // 상위 카테고리 포함 전체조회
        List<Category> categoryList = categoryRepository.findAllSubCategory();

        // DTO로 변환
        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class)).toList();
    }

    @Transactional
    public void registMenu(MenuDto newMenu) {
        //엔티티객체로 변환후, save : 영속상태되고 쓰기지연 저장됨 이후 commit
        menuRepository.save(modelMapper.map(newMenu, Menu.class));
    }

    @Transactional
    public void modifyMenu(MenuDto modifyMenu) {
        // 수정 : 조회 >> setter로 필드변경 >> commit
        // 엔티티 조회하면, 기존 복사본과 변경이 감지되면 업데이트 자동으로 해줌
        Menu menu = menuRepository.findById(modifyMenu.getMenuCode()).orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 코드입니다.")); // orElseThrow(발생시킬예외객체) : 해당조회결과가 있다면 엔티티객체를 반환하고, 없으면 null. 그때 예외를 발생시킴)
        // ㄴ영속상태
        //setter로 필드변경
        menu.setMenuName(modifyMenu.getMenuName());
        menu.setMenuPrice(modifyMenu.getMenuPrice());
        menu.setOrderableStatus(modifyMenu.getOrderableStatus());
        menu.setCategoryCode(modifyMenu.getCategoryCode());

        //setter에 의해서 변경된 값을 조회했을때 얻은 스냅샷과 비교해서
        //변경감지되면(dirty checking)되면 UPDATE쿼리가 쓰기 짖연 저장소에 저장
        // commit 시점에서 db에 반영
    }

    @Transactional
    public void removeMenu(int menuCode) {
//        menuRepository.deleteById(menuCode); // 바로 삭제시킴 존재하지 않은 경우 확인 불가능
        // 있는 메뉴인지 확인 : 조회
        Menu menu = menuRepository.findById(menuCode).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴번호입니다")); // orElseThrow(발생시킬예외객체) : 해당조회결과가 있다면 엔티티객체를 반환하고, 없으면 null. 그때 예외를 발생시킴)
        menuRepository.delete(menu);

    }

    public List<MenuDto> findMenuByPrice(int price) {

        // 전달된 가격값과 일치하는 메뉴 조회 (WHERE절)
        // Native query  + 파라미터
        List<Menu> menuList = menuRepository.findByMenuPrice(price);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).toList();
    }

    public List<MenuDto> findMenuByName(String name) {

        // 쿼리 메소드로 where절 조회하는 방법도 있음
        // 포함되어있어야하는거지 같다로 조회하니까 안나오지 아오
        List<Menu> menuList = menuRepository.findByMenuNameContaining(name);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).toList();
    }

    public List<MenuDto> findMenuByPriceAndName(String[] quaryList) {
        // 가격 이상, 메뉴명 포함되어있는

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanEqualAndMenuNameContaining(Integer.parseInt(quaryList[0]), quaryList[1]);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDto.class)).toList();
    }
}




















