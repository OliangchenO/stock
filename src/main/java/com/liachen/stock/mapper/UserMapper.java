package com.liachen.stock.mapper;

import com.liachen.stock.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
 */
public interface UserMapper extends BaseMapper<User> {
    @Select({"sect * from User where type= #{type} and modify_time > #{modifyTime}"})
    List<User> queryByParam(@Param("type") String type, @Param("modifyTime") Date modifyTime);
}
