package com.liachen.junit;

import com.github.liaochong.myexcel.core.ExcelBuilder;
import com.github.liaochong.myexcel.core.FreemarkerExcelBuilder;
import com.github.liaochong.myexcel.core.HtmlToExcelFactory;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 功能描述
 *
 * @author chen.liang
 * @date 2020/1/2 10:54
 * @since V1.0
 */
public class ExcelUtilTest {
    @Test
    public void testHtmlToExcel() throws Exception {
        Workbook workbook = HtmlToExcelFactory.readHtml(new File("D:\\PraticeSpace\\stock\\src\\test\\resource\\templates\\test.html")).build();
        FileExportUtil.export(workbook, new File("C:\\Users\\chen.liang\\Desktop\\export.xlxs"));
    }

    @Test
    public void testExportByTemplate() throws Exception {
        Map<String, Object> dataMap = this.getDataMap();
        try (ExcelBuilder excelBuilder = new FreemarkerExcelBuilder()) {
            Workbook workbook = excelBuilder
                    .template("/templates/freemarkerToExcelExample.ftl")
                    .useDefaultStyle()
                    .build(dataMap);
            FileExportUtil.export(workbook, new File("C:\\Users\\chen.liang\\Desktop\\export.xlxs"));
        }
    }

    private Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("sheetName", "freemarker_excel_example");

        List<String> titles = new ArrayList<>();
        titles.add("Category");
        titles.add("Product Name");
        titles.add("Count");
        dataMap.put("titles", titles);

        List<Product> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            if (i % 2 == 0) {
                product.setCategory("蔬菜");
                product.setName("小白菜");
                product.setCount(100);
            } else {
                product.setCategory("电子产品");
                product.setName("ipad");
                product.setCount(999);
            }
            data.add(product);
        }
        dataMap.put("data", data);
        return dataMap;
    }

    @Data
    public class Product {
        private String category;
        private String name;
        private Integer count;
    }
}
