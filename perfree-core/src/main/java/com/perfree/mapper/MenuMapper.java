package com.perfree.mapper;

import com.perfree.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

}
