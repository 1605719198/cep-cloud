package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hs.domain.PersonalSalary;
import com.jlkj.human.hs.dto.PersonalSalaryBankDTO;
import com.jlkj.human.hs.dto.PersonalSalaryDTO;
import com.jlkj.human.hs.dto.PersonalSalaryDetailDTO;
import com.jlkj.human.hs.service.IPersonalSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 薪资核定Controller
 *
 * @author 266861
 * @date 2023-06-01
 */
@RestController
@RequestMapping("/personalSalary")
public class PersonalSalaryController extends BaseController {
    @Autowired
    private IPersonalSalaryService personalSalaryService;

    /**
     * 查询薪资核定列表
     */
    @RequiresPermissions("human:personalSalary:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonalSalary personalSalary) {
        startPage();
        List<PersonalSalary> list = personalSalaryService.selectPersonalSalaryList(personalSalary);
        return getDataTable(list);
    }

    /**
     * 获取薪资核定详细信息
     */
    @RequiresPermissions("human:personalSalary:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(personalSalaryService.selectPersonalSalaryById(id));
    }

    /**
     * 新增薪资核定
     */
    @RequiresPermissions("human:personalSalary:add")
    @Log(title = "薪资核定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonalSalary personalSalary) {
        int result = personalSalaryService.insertPersonalSalary(personalSalary);
        if (result > 0) {
            return success("新增成功");
        } else if(result ==-1) {
            return error("新增失败，指定的生效日期和最大生效日期重复");
        }else if(result ==0){
            return error("新增失败，该员工已有未定薪的未生效资料");
        }else{
            return error();
        }
    }


    /**
     * 修改薪资核定止薪日期
     */
    @RequiresPermissions("human:personalSalary:edit")
    @Log(title = "薪资核定", businessType = BusinessType.UPDATE)
    @PutMapping("/setEndPayDate")
    public AjaxResult setEndPayDate(@RequestBody PersonalSalary personalSalary) {
        return toAjax(personalSalaryService.updatePersonalSalary(personalSalary,true));
    }

    /**
     * 修改薪资核定
     */
    @RequiresPermissions("human:personalSalary:edit")
    @Log(title = "薪资核定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonalSalary personalSalary) {
        int result = personalSalaryService.updatePersonalSalary(personalSalary,false);
        if (result > 0) {
            return AjaxResult.success("修改成功");
        } else if(result ==-1) {
//            return error("修改失败，生效日期不能小于当前日期或最大生效日期");
            return error("新增失败，指定的生效日期和最大生效日期重复");
        }else if(result ==0){
            return error("修改失败，该员工已有未定薪的未生效资料");
        }else{
            return error();
        }
    }


    /**
     * 通过公司别获取薪资支付银行编码、名称ID
     */
    @GetMapping("/getBank/{compId}")
    public List<BasisOptionsDTO> getBank(@PathVariable String compId) {
        return personalSalaryService.getSalaryBank(compId);
    }

    /**
     * 查询薪酬项目列表接口
     */
    @GetMapping("/getPayFormation/{compId}")
    public List<PersonalSalaryDetailDTO> getPayFormation(@PathVariable String compId) {
        return personalSalaryService.getPayFormation(compId);
    }

    /**
     * 导入薪资核定数据
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "薪资核定导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:personalSalary:importSalary")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, String compId) throws Exception {
        ExcelUtil<PersonalSalaryDTO> util = new ExcelUtil<PersonalSalaryDTO>(PersonalSalaryDTO.class);
        List<PersonalSalaryDTO> dtos = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = personalSalaryService.importSalary(dtos, updateSupport, operName, compId);
        return success(message);
    }

    /**
     * 导入薪资核定数据模板
     *
     * @param response
     * @throws IOException
     */
    @RequiresPermissions("human:personalSalary:importSalary")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<PersonalSalaryDTO> util = new ExcelUtil<PersonalSalaryDTO>(PersonalSalaryDTO.class);
        util.importTemplateExcel(response, "薪资核定数据");
    }

    /**
     * 导入银行账号数据
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "薪资核定导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:personalSalary:importBank")
    @PostMapping("/importData/bankNo")
    public AjaxResult importBankNoData(MultipartFile file, boolean updateSupport, String compId) throws Exception {
        ExcelUtil<PersonalSalaryBankDTO> util = new ExcelUtil<PersonalSalaryBankDTO>(PersonalSalaryBankDTO.class);
        List<PersonalSalaryBankDTO> dtos = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = personalSalaryService.importBankNo(dtos, updateSupport, operName, compId);
        return success(message);
    }

    /**
     * 导入银行账号数据模板
     *
     * @param response
     * @throws IOException
     */
    @RequiresPermissions("human:personalSalary:importBank")
    @PostMapping("/importTemplate/bankNo")
    public void importTemplateBankNo(HttpServletResponse response) throws IOException {
        ExcelUtil<PersonalSalaryBankDTO> util = new ExcelUtil<PersonalSalaryBankDTO>(PersonalSalaryBankDTO.class);
        util.importTemplateExcel(response, "银行账号数据");
    }

}