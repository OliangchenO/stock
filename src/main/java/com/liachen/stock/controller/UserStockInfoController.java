package com.liachen.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.liachen.common.controller.CurdController;
import com.liachen.stock.entity.UserStockInfo;

/**
 * <p>
 * 用户持仓信息 控制器
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
*/
@RestController
@RequestMapping("/stock/user-stock-info")
public class UserStockInfoController extends CurdController<UserStockInfo> {

}
