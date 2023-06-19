package com.jlkj.human.hs.controller;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.ImportError;
import com.jlkj.human.hs.service.IImportErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 薪资导入错误信息Controller
 *
 * @author 266861
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/importError")
public class ImportErrorController extends BaseController
{
    @Autowired
    private IImportErrorService importErrorService;


    /**
     * 导出薪资导入错误信息列表
     */
    @Log(title = "薪资导入错误信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImportError importError)
    {
        List<ImportError> list = importErrorService.selectImportErrorList(importError);
        if(list.size()==0){
            throw new ServiceException("未查询到错误信息");
        }
        ExcelUtil<ImportError> util = new ExcelUtil<ImportError>(ImportError.class);
        util.exportExcel(response, list, "导入错误信息数据");
    }



}
