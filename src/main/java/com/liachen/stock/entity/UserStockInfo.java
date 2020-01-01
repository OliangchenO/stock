package com.liachen.stock.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户持仓信息
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_stock_info")
public class UserStockInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 股票code
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 持仓数目
     */
    private Integer holdNum;

    /**
     * 买入时间
     */
    private Date buyTime;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 今日涨幅
     */
    private BigDecimal todayIncrease;

    /**
     * 持仓市值
     */
    private BigDecimal marketValue;

    /**
     * 盈亏率
     */
    private BigDecimal increaseRate;

    /**
     * 浮动盈亏
     */
    private BigDecimal floatingProfit;

    /**
     * 是否有效 0:有效，1:无效
     */
    private Boolean deleted;

    /**
     * 添加时间
     */
    private Date addTime;

    private Date modifyTime;


}
