package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanOutputDate;
import com.jlkj.product.oi.dto.productionplanoutputdate.ListProductionPlanOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanDayDTO;
import com.jlkj.product.oi.mapper.ProductionPlanOutputDateMapper;
import com.jlkj.product.oi.service.ProductionPlanOutputDateService;
import com.jlkj.product.oi.vo.productionplanoutputdate.ListProductionPlanOutputDateTargetItemVO;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_date(计划管理--日产量计划表)】的数据库操作Service实现
* @createDate 2022-04-21 14:46:03
*/
@Service
public class ProductionPlanOutputDateServiceImpl extends ServiceImpl<ProductionPlanOutputDateMapper, ProductionPlanOutputDate>
    implements ProductionPlanOutputDateService {

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    };

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionPlanOutputDateTargetItemVO> getProductionPlanOutputDateTargetItemChartData(ListProductionPlanOutputDateTargetItemDTO listProductionPlanOutputDateTargetItemDTO) {
        return getBaseMapper().getProductionPlanOutputDateTargetItemChartData(listProductionPlanOutputDateTargetItemDTO);
    }

    /**
     * 查询日生产产量计划
     * @param dto
     * @param itemlist
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Object get(GetProductionPlanDayDTO dto, List<ProductionParameterTargetItem> itemlist) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select plan_date, ");
        for (ProductionParameterTargetItem item : itemlist) {
            sqlString.append("max(case target_item_id when '");
            sqlString.append(item.getId());
            sqlString.append("' then target_item_output else 0 end) as '");
            sqlString.append(item.getId());
            sqlString.append("', ");
        }
        sqlString.append("'' as blk");
        sqlString.append(" from product_oi_plan_output_date ");
        sqlString.append("where plan_year = ");
        sqlString.append(dto.getPlanYear());
        sqlString.append(" and plan_month = ");
        sqlString.append(dto.getPlanMonth());
        sqlString.append(" group by plan_date order by plan_year desc, plan_month asc, plan_date desc");
        List<Map<String, String>> targetList = getColToRowList(sqlString.toString());
        return AjaxResult.success(targetList);
    }
}




