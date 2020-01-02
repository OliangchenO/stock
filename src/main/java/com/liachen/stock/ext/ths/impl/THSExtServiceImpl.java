package com.liachen.stock.ext.ths.impl;

import com.alibaba.fastjson.JSONObject;
import com.liachen.common.enums.UserType;
import com.liachen.common.util.ToolsUtil;
import com.liachen.stock.entity.User;
import com.liachen.stock.entity.UserStockInfo;
import com.liachen.stock.ext.ths.THSExtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class THSExtServiceImpl implements THSExtService {
    private static final String THS_HOST = "http://moni.10jqka.com.cn/%s";
    private static final String FOLLOW_URL = "moni/index/tracemaster4business";
    private static final String COOKIE_STR = "user=MDrM7ND80Me602xjOjpOb25lOjUwMDoxNTM3NDIzMDY6NywxMTExMTExMTExMSw0MDs0NCwxMSw0MDs2LDEsNDA7NSwxLDQwOzEsMSw0MDsyLDEsNDA7MywxLDQwOzUsMSw0MDs4LDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAxLDQwOjI3Ojo6MTQzNzQyMzA2OjE1Nzc2MjUzNDY6OjoxMzQ4NTgyMzgwOjYwNDgwMDowOjFmOTQ0MzRkZDA5ZDk4Yjk1YzkxODE5MzBmYjMzNmExNDpkZWZhdWx0XzQ6MA%3D%3D; userid=143742306; u_name=%CC%EC%D0%FC%D0%C7%BA%D3lc; escapename=%25u5929%25u60ac%25u661f%25u6cb3lc; ticket=729385b016d71fd7264a6268caba73a4; v=AgfYq2MqFZaCQZEaZyHIEmailrDFDNmVNeFfTdn0I7C8Iims4dxrPkWw7pfq; Hm_lvt_78c58f01938e4d85eaf619eae71b4ed1=1577625259,1577847447; Hm_lvt_a9190969a435c4c490361fdf65267856=1577625333,1577625342,1577847447; __utmc=68909069; __utmz=68909069.1577859531.5.2.utmcsr=moni.10jqka.com.cn|utmccn=(referral)|utmcmd=referral|utmcct=/paihang.shtml; __utma=68909069.989995859.1577625333.1577859531.1577872553.6; __utmt=1; Hm_lpvt_a9190969a435c4c490361fdf65267856=1577872598; Hm_lpvt_78c58f01938e4d85eaf619eae71b4ed1=1577872598; __utmb=68909069.2.10.1577872553";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";

    @Override
    public List<User> queryUserList(String type) {
        List<User> userList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format(THS_HOST, UserType.getByType(type).getUrl())).userAgent(USER_AGENT).cookies(ToolsUtil.getCookie(COOKIE_STR)).get();
            Elements elements = doc.select("#myTab_Content0 > table.table_hover_tr > tbody > tr");
            int rank = 1;
            for (Element element : elements) {
                userList.add(convertUser(rank, element, type));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("用户列表查询成功，type={}", type);
        }

        return userList;
    }

    @Override
    public List<UserStockInfo> queryUserStockInfoList(Long userId) {
        List<UserStockInfo> userStockInfoList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format(THS_HOST, userId)).cookies(ToolsUtil.getCookie(COOKIE_STR)).get();
            Elements elements = doc.select("#ccqk_tbl > tbody > tr");
            if (elements.size() == 0) {
                return userStockInfoList;
            }
            for (Element element : elements) {
                userStockInfoList.add(convertUserStockInfo(userId, element));
            }
        } catch (Exception e) {
            log.error("用户userId:{}, 持仓查询失败, 错误信息：{}", userId, e);
        } finally {
            log.info("用户userId:{}, 持仓:{}", userId, JSONObject.toJSONString(userStockInfoList));
        }
        return userStockInfoList;
    }

    @Override
    public void followUser(String userId, String usrId, String userName) {
        Map<String, String> param = new HashMap<>(3);
        param.put("masterid", userId);
        param.put("masterusrid", usrId);
        param.put("mastername", userName);
        try {
            Jsoup.connect(String.format(THS_HOST, FOLLOW_URL)).cookies(ToolsUtil.getCookie(COOKIE_STR)).data(param).post();
        } catch (IOException e) {
            log.error("跟踪用户失败，userId={}, usrId={}, userName={}, 错误信息：{}", userId, usrId, userName, e);
        } finally {
            log.info("跟踪用户，userId={}, usrId={}, userName={}", userId, usrId, userName);
        }
    }

    private UserStockInfo convertUserStockInfo(Long userId, Element element) throws ParseException {
        String code = element.select("td:nth-child(1) > span > a").first().attr("title");
        String name = element.select("td:nth-child(2) > span > a").first().text();
        Integer holdNum = Integer.valueOf(element.select("td:nth-child(3) > span").first().text());
        Date buyTime = DateUtils.parseDate(element.select("td:nth-child(4) > span").first().text(), "yyyy-MM-dd");
        BigDecimal cost = new BigDecimal(element.select("td:nth-child(5) > span").first().text());
        BigDecimal currentPrice = new BigDecimal(element.select("td:nth-child(6) > span").first().text());
        String todayIncreaseStr = element.select("td:nth-child(7) > span").first().text();
        BigDecimal todayIncrease = todayIncreaseStr.contains("%") ? new BigDecimal(StringUtils.removeEnd(todayIncreaseStr, "%")) : BigDecimal.ZERO;
        BigDecimal marketValue = new BigDecimal(element.select("td:nth-child(8) > span").first().text());
        BigDecimal increaseRate = new BigDecimal(StringUtils.removeEnd(element.select("td:nth-child(9) > span").first().text(), "%"));
        BigDecimal floatingProfit = new BigDecimal(element.select("td:nth-child(10) > span").first().text());
        return new UserStockInfo().setUserId(userId).setCode(code).setName(name).setHoldNum(holdNum).setBuyTime(buyTime).setCost(cost).setCurrentPrice(currentPrice).setTodayIncrease(todayIncrease).setMarketValue(marketValue).setIncreaseRate(increaseRate).setFloatingProfit(floatingProfit).setAddTime(new Date());
    }

    private User convertUser(int rank, Element element, String type) {
        Element userInfo = element.select("td:nth-child(11) > p").first();
        String userId = userInfo.attr("userid");
        String usrId = userInfo.attr("usrid");
        String userName = userInfo.attr("username");
        String tradeInfo = userInfo.text();
        String grade = element.select("td:nth-child(3) > p").text();
        BigDecimal totalGain = new BigDecimal(StringUtils.removeEnd(element.select("td:nth-child(4) > p").first().text(), "%"));
        BigDecimal monthGain = new BigDecimal(StringUtils.removeEnd(element.select("td:nth-child(5) > p").first().text(), "%"));
        BigDecimal weekGain = new BigDecimal(StringUtils.removeEnd(element.select("td:nth-child(6) > p").first().text(), "%"));
        BigDecimal dailyGain = new BigDecimal(StringUtils.removeEnd(element.select("td:nth-child(7) > p").first().text(), "%"));
        BigDecimal successRate = new BigDecimal(StringUtils.removeEnd(element.select("td:nth-child(8) > p").first().text(), "%"));
        BigDecimal totalAssets = new BigDecimal(element.select("td:nth-child(9) > p").first().text());
        Long follow = Long.valueOf(element.select("td:nth-child(10) > p").first().text());
        return new User().setType(type).setUserId(Long.valueOf(userId)).setUsrId(Long.valueOf(usrId)).setUserName(userName).setRankNo(rank).setGrade(grade).setTotalGain(totalGain).setMonthGain(monthGain).setWeekGain(weekGain).setDailyGain(dailyGain).setSuccessRate(successRate).setTotalAssets(totalAssets).setFollow(follow).setTradeInfo(tradeInfo).setAddTime(new Date()).setNoStock(0);
    }
}
