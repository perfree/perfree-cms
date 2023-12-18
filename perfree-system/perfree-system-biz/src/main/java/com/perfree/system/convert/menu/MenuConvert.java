package com.perfree.system.convert.menu;

import com.perfree.system.model.Menu;
import com.perfree.system.vo.system.MenuTreeListRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuTreeListRespVO> convertTreeList(List<Menu> menuList);

}
