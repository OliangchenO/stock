package com.liachen.stock.service.impl;

import com.liachen.stock.entity.UserStockInfo;
import com.liachen.stock.mapper.UserStockInfoMapper;
import com.liachen.stock.service.IUserStockInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户持仓信息 服务实现类
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
 */
@Service
public class UserStockInfoServiceImpl extends ServiceImpl<UserStockInfoMapper, UserStockInfo> implements IUserStockInfoService {

}
