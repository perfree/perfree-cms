package com.perfree.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.system.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfree.system.vo.menu.MenuListReqVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id和菜单类型获取菜单
     * @param userId 用户id
     * @param type 类型
     * @return List<Menu>
     */
    List<Menu> menuListByUserId(@Param("userId") Integer userId, @Param("type") Integer type);

    /**
     * 菜单列表
     * @param pageVO pageVO
     * @return List<Menu>
     */
    default List<Menu> menuList(MenuListReqVO pageVO){
        return selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(pageVO.getName()), Menu::getName, pageVO.getName())
                .orderByAsc(Menu::getSeq));
    }

}
