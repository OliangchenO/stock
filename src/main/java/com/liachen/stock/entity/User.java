package com.liachen.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Liachen
 * @since 2020-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

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
     * usrId
     */
    private Long usrId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 段位
     */
    private String grade;

    /**
     * 总盈利
     */
    private BigDecimal totalGain;

    /**
     * 月盈利
     */
    private BigDecimal monthGain;

    /**
     * 周盈利
     */
    private BigDecimal weekGain;

    /**
     * 日盈利
     */
    private BigDecimal dailyGain;

    /**
     * 选股成功率
     */
    private BigDecimal successRate;

    /**
     * 总资产
     */
    private BigDecimal totalAssets;

    /**
     * 被追踪数
     */
    private Long follow;

    /**
     * 今日动态
     */
    private String tradeInfo;

    /**
     * 类型
     */
    private String type;

    /**
     * 排名
     */
    private Integer rankNo;

    /**
     * 是否空仓
     */
    private Integer noStock;

    /**
     * 添加时间
     */
    private Date addTime;

    private Date modifyTime;


}
