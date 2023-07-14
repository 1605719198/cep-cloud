package com.jlkj.product.oi.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel
 *
 * @author 李瑞蛟
 * @date 2022/12/14/ 18:12:09
 */
public class ExportExcelUtil<T, S extends IService<T>>{
    @Autowired
    protected S service;
    /**
     * 导出excel
     *
     * @param list 数据集合
     * @param pojoClass 数据类型
     * @param fileName 文件名称
     * @param title 表明
     * @param response 响应对象
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, String title,String sheetName, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        // 自定义字典查询规则
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            try {
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-Type", "application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
                workbook.write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
