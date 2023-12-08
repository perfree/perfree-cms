package com.perfree.convert.menu;

import com.perfree.model.Menu;
import com.perfree.vo.system.MenuTreeListRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuTreeListRespVO> convertTreeList(List<Menu> menuList);

}
