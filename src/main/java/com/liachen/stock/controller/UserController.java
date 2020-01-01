package com.liachen.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.liachen.common.controller.CurdController;
import com.liachen.stock.entity.User;

/**
 * <p>
 * 用户信息 控制器
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
*/
@RestController
@RequestMapping("/stock/user")
public class UserController extends CurdController<User> {

}
