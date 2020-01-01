package com.liachen.stock.controller;

import com.liachen.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@Slf4j
public class StockController {
    @Autowired
    private StockService stockService;

    /**
     * 从同花顺拉取数据
     * @param type
     * @return
     */
    @RequestMapping("/get-info-from-ths")
    public String getInfoFromThs(@RequestParam String type) {
        log.info("拉取数据开始, type={}", type);
        stockService.saveStockInfo(type);
        log.info("拉取数据结束");
        return "success";
    }
}
