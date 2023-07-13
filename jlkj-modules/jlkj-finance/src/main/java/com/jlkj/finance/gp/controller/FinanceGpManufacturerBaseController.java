package com.jlkj.finance.gp.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.annotation.InnerAuth;
import com.jlkj.finance.gp.domain.FinanceGpManufacturerBase;
import com.jlkj.finance.gp.service.IFinanceGpManufacturerBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 销售管理-厂商基本资料Controller
 *
 * @author 265799
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/finance/manufacturer")
public class FinanceGpManufacturerBaseController extends BaseController
{
    @Autowired
    private IFinanceGpManufacturerBaseService financeGpManufacturerBaseService;

    /**
     * 查询销售管理-厂商基本资料列表
     */
    @GetMapping("/list")
    public TableDataInfo list( FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        startPage();
        List<FinanceGpManufacturerBase> list = financeGpManufacturerBaseService.selectFinanceGpManufacturerBaseList(financeGpManufacturerBase);
        return getDataTable(list);
    }
    /**
     * 弹窗查询厂商基本资料列表
     */
    @GetMapping("/listBaseSelect")
    public TableDataInfo listBaseSelect( FinanceGpManufacturerBase financeGpManufacturerBase)
    {
        startPage();
        List<FinanceGpManufacturerBase> list = financeGpManufacturerBaseService.selectFinanceGpManufacturerBaseListBaseSelect(financeGpManufacturerBase);
        return getDataTable(list);
    }
/**
*
*@author 265799
*@date 2023/7/10  15:39
 * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
*/
    @InnerAuth
    @GetMapping("/getManufacturerList/{companyId}")
    public List<Map<String,String>> getCompanyFeign(@PathVariable("companyId") String companyId)
    {
        return financeGpManufacturerBaseService.selectManufacturerList(companyId);
    }
    /**
    *
    *@author 265799
    *@date 2023/7/10  15:26
     * @param companyId : 
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */        
    @GetMapping("/selectManufacturerList/{companyId}")
    public List<Map<String,String>> selectManufacturerList(@PathVariable("companyId") String companyId){
        return financeGpManufacturerBaseService.selectManufacturerList(companyId);
    }
}
