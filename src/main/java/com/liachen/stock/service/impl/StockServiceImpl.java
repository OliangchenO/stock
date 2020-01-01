package com.liachen.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liachen.stock.entity.User;
import com.liachen.stock.entity.UserStockInfo;
import com.liachen.stock.ext.ths.THSExtService;
import com.liachen.stock.service.IUserService;
import com.liachen.stock.service.IUserStockInfoService;
import com.liachen.stock.service.StockService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {
    private static final String NEED_FOLLOW = "追踪可看";
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserStockInfoService userStockInfoService;
    @Autowired
    private THSExtService thsExtService;
    @Override
    public void saveStockInfo(String type) {
        QueryWrapper queryUser = new QueryWrapper<User>().eq("type", type).apply("modify_time > DATE({0}) AND modify_time <= DATE_ADD({0}, INTERVAL 1 DAY)", DateUtil.beginOfDay(new Date()).toJdkDate());
        List<User> userList = userService.list(queryUser);
        if (CollectionUtil.isEmpty(userList)) {
            userList = saveUser(type);
        }
        userList.forEach(user -> {
            QueryWrapper queryStock = new QueryWrapper<UserStockInfo>().eq("user_id", user.getUserId()).apply("modify_time > DATE({0}) AND modify_time <= DATE_ADD({0}, INTERVAL 1 DAY)", DateUtil.beginOfDay(new Date()).toJdkDate());
            List<UserStockInfo> stockInfoList = userStockInfoService.list(queryStock);
            if (CollectionUtil.isEmpty(stockInfoList)) {
                saveStock(user);
            }
        });
    }

    private void saveStock(User user) {
        List<UserStockInfo> stockInfoList;
        if (NEED_FOLLOW.equalsIgnoreCase(user.getTradeInfo())) {
            thsExtService.followUser(user.getUserId().toString(), user.getUsrId().toString(), user.getUserName());
        }
        if (user.getNoStock() == 0) {
            stockInfoList = thsExtService.queryUserStockInfoList(user.getUserId());
            //查到同花顺有持仓，更新持仓信息；否则设置user为空仓
            if (CollectionUtil.isNotEmpty(stockInfoList)) {
                //清楚用户持仓再插入持仓信息
                userStockInfoService.remove(new QueryWrapper<UserStockInfo>().eq("user_id", user.getUserId()));
                userStockInfoService.saveOrUpdateBatch(stockInfoList);
            } else {
                user.setNoStock(1);
                userService.saveOrUpdate(user);
            }
        }

    }

    private List<User> saveUser(String type) {
        List<User> userList;
        userList = thsExtService.queryUserList(type);
        List<User> needUpdateUser = new ArrayList<>();
        userList.forEach(user -> {
            if (userService.count(new QueryWrapper<User>().eq("user_id", user.getUserId())) > 0) {
                userService.save(user);
            }
            needUpdateUser.add(user);
        });
        if (CollectionUtil.isNotEmpty(needUpdateUser)) {
            userService.saveOrUpdateBatch(needUpdateUser);
        }
        return userList;
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }
}
