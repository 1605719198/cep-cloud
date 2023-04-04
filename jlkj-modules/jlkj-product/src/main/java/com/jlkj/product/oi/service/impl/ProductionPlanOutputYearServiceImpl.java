package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanOutputYear;
import com.jlkj.product.oi.mapper.ProductionPlanOutputYearMapper;
import com.jlkj.product.oi.service.ProductionPlanOutputYearService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_year(计划管理--年产量计划)】的数据库操作Service实现
* @createDate 2022-04-21 14:46:03
*/
@Service
public class ProductionPlanOutputYearServiceImpl extends ServiceImpl<ProductionPlanOutputYearMapper, ProductionPlanOutputYear>
    implements ProductionPlanOutputYearService {

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    };

    public Object get(List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_year, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_output else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_output_year group by plan_year order by plan_year desc");
        List<Map<String, String>> targetList = getColToRowList(sqlString.toString());

        return AjaxResult.success(targetList);
    }
}




