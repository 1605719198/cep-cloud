package com.jlkj.finance.gp.controller;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.finance.gp.dto.ManufacturerTreeDTO;
import com.jlkj.finance.gp.service.ManufacturerTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wm
 * @since 2022-04-11
 */
@RestController
@RequestMapping("/finance/manufacturer")
public class ManufactureTreeController {
    @Autowired
    private ManufacturerTreeService manufacturerTreeService;

    /**
     * 获取机构树结构资料
     *
     */
    @Operation(summary = "获取机构树结构资料")
    @GetMapping("/ManufacturerTreeList")
    public Object getOrganizationTreeList() {
        List<ManufacturerTreeDTO> list = manufacturerTreeService.getManufacturerTreeList();
        return AjaxResult.success(list);
    }

}
