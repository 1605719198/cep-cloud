package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SocialSecurityBasis;
import com.jlkj.human.hs.dto.SocialSecurityBasisDTO;
import com.jlkj.human.hs.service.ISocialSecurityBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 社保公积金标准核定Controller
 *
 * @author 266861
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/socialSecurityBasis")
public class SocialSecurityBasisController extends BaseController {
    @Autowired
    private ISocialSecurityBasisService socialSecurityBasisService;

    /**
     * 查询社保公积金标准核定列表
     */
    @RequiresPermissions("human:socialSecurityBasis:list")
    @GetMapping("/list")
    public TableDataInfo list(SocialSecurityBasis socialSecurityBasis) {
        startPage();
        List<SocialSecurityBasis> list = socialSecurityBasisService.selectSocialSecurityBasisList(socialSecurityBasis);
        return getDataTable(list);
    }

    /**
     * 获取社保公积金标准核定详细信息
     */
    @RequiresPermissions("human:socialSecurityBasis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(socialSecurityBasisService.selectSocialSecurityBasisById(id));
    }

    /**
     * 新增社保公积金标准核定
     */
    @RequiresPermissions("human:socialSecurityBasis:add")
    @Log(title = "社保公积金标准核定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SocialSecurityBasis socialSecurityBasis) {
        int result = socialSecurityBasisService.insertSocialSecurityBasis(socialSecurityBasis);
        if (result > 0) {
            return success("新增成功");
        } else {
            return error("新增失败，生效日期不能小于当前日期或最大生效日期");
        }
    }

    /**
     * 修改社保公积金标准核定
     */
    @RequiresPermissions("human:socialSecurityBasis:edit")
    @Log(title = "社保公积金标准核定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SocialSecurityBasis socialSecurityBasis) {
        int result = socialSecurityBasisService.updateSocialSecurityBasis(socialSecurityBasis);
        if (result > 0) {
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("修改失败，生效日期不能小于当前日期或最大生效日期");
        }
    }

    /**
     * 导入社保公积金核定数据
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "社保公积金核定导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:socialSecurityBasis:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, String compId) throws Exception {
        ExcelUtil<SocialSecurityBasisDTO> util = new ExcelUtil<SocialSecurityBasisDTO>(SocialSecurityBasisDTO.class);
        List<SocialSecurityBasisDTO> dtos = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = socialSecurityBasisService.importUser(dtos, updateSupport, operName, compId);
        return success(message);
    }

    /**
     * 导入社保公积金核定数据模板
     *
     * @param response
     * @throws IOException
     */
    @RequiresPermissions("human:socialSecurityBasis:import")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<SocialSecurityBasisDTO> util = new ExcelUtil<SocialSecurityBasisDTO>(SocialSecurityBasisDTO.class);
        util.importTemplateExcel(response, "社保公积金核定数据");
    }

}
