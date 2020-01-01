package com.liachen.stock.ext.ths;

import com.liachen.stock.entity.User;
import com.liachen.stock.entity.UserStockInfo;

import java.util.List;

public interface THSExtService {
    List<User> queryUserList(String type);

    List<UserStockInfo> queryUserStockInfoList(Long userId);

    void followUser(String userId, String usrId, String userName);
}
