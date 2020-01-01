SELECT
	CODE as '股票代码',
	NAME as '股票名称',
	DATE_FORMAT(add_time,'%Y-%m-%d') AS '日期',
	DATE_FORMAT(MIN(buy_time),'%Y-%m-%d') AS '最早买入日期',
	DATE_FORMAT(MAX(buy_time),'%Y-%m-%d') AS '最晚买入日期',
    Avg(hold_num) AS '平均持仓量',
	sum(market_value) as '买入总市值',
	avg(cost) as '平均成本',
	current_price as '当前价格',
	today_increase as '今日涨幅%',
	avg(increase_rate) as '平均收益率%',
  avg(floating_profit) as '平均浮动盈亏',
  count(1) as '持有人数'
FROM
	user_stock_info
GROUP BY
	CODE
ORDER BY
	sum(market_value) DESC