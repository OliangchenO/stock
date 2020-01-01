package com.liachen.stock.service.impl;

import com.liachen.stock.entity.User;
import com.liachen.stock.mapper.UserMapper;
import com.liachen.stock.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
