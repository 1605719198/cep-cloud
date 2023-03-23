package com.jlkj.safety.si.utils;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * pdf打印工具类
 *
 * @author: yzl
 * @time: 2022年10月8日 08:54:46
 */
@Slf4j
@Data
@Component
public class PdfUtil {

    private static String pdfTemplatesFileUrl;

    @Value("${pdfTemplates.url}")
    public void setPdfTemplatesFileUrl(String v) {
        pdfTemplatesFileUrl = v;
    }


    static {
        System.setProperty("java.awt.headless", "true");
    }

    /**
     * 导出 pdf
     *
     * @param response response
     * @param template 模板名称
     * @param map      填充数据
     */
    @SneakyThrows
    public static void exportStreamPdf(HttpServletResponse response, String template, Map<String, Object> map) {
        String jasperPath = pdfTemplatesFileUrl +  template + ".jasper";
        //填充数据---使用JavaBean数据源方式填充
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, new JREmptyDataSource());
        String jrxmlPath = pdfTemplatesFileUrl + template + ".jrxml";
        JasperCompileManager.compileReportToFile(jrxmlPath);
        exportStream(response, jasperPrint);
    }
    /**
     * 导出 pdf
     *
     * @param response response
     * @param template 模板名称
     * @param map      填充数据
     */
    @SneakyThrows
    public static void exportDownloadPdf(HttpServletResponse response, String template, Map<String, Object> map, String fileName) {
        String jasperPath = pdfTemplatesFileUrl +  template + ".jasper";
        //填充数据---使用JavaBean数据源方式填充
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, new JREmptyDataSource());
        String jrxmlPath = pdfTemplatesFileUrl + template + ".jrxml";
        JasperCompileManager.compileReportToFile(jrxmlPath);
        exportFile(response, jasperPrint, fileName);
    }


    /**
     * 填充数据---使用JavaBean数据源方式填充
     *
     * @param map      填充数据
     * @param template 模板名称
     * @return
     */
    @SneakyThrows
    public static JasperPrint setJasperPrintList(Map<String, Object> map, String template) {
        String jasperPath = pdfTemplatesFileUrl +  template + ".jasper";
        //填充数据---使用JavaBean数据源方式填充
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, new JREmptyDataSource());
        return jasperPrint;
    }

    /**
     * 填充数据---使用JavaBean数据源方式填充
     *
     * @param list     填充数据
     * @param template 模板名称
     * @return
     */
    @SneakyThrows
    public static List<JasperPrint> setJasperPrintList(List<Map<String, Object>> list, String template) {
        String jasperPath = pdfTemplatesFileUrl +  template + ".jasper";
        //打印列表
        List<JasperPrint> reportlist = new ArrayList();
        for (Map<String, Object> paramters : list) {
            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, paramters, new JREmptyDataSource());
            reportlist.add(jasperPrint);
        }
        return reportlist;
    }


    /**
     * 打印输出流
     *
     * @param response    response
     * @param jasperPrint 模版数据
     */
    public static void exportStream(HttpServletResponse response, JasperPrint jasperPrint) {
        //输出pdf文件
        response.setContentType("application/pdf");
        JRPdfExporter exporter = new JRPdfExporter();
        try {
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.exportReport();
        } catch (Exception e) {
            log.info("打印输出流失败");
            log.error(e.getMessage() + e.getCause());
        }
    }

    /**
     * 导出文件
     *
     * @param response        response
     * @param jasperPrint     数据
     * @param defaultFilename 文件名
     * @throws IOException
     * @throws JRException
     */
    private static void exportFile(HttpServletResponse response, JasperPrint jasperPrint, String defaultFilename) throws IOException, JRException {
        response.setContentType("application/pdf");
        String defaultName = null;
        if (!"".equals(defaultFilename.trim())) {
            defaultName = defaultFilename + ".pdf";
        } else {
            defaultName = "export.pdf";
        }
        String fileName = new String(defaultName.getBytes("GBK"), "ISO8859_1");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
}
