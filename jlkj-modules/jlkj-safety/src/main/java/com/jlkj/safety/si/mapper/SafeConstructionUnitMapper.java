package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiConstructionUnit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 外委施工单位 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeConstructionUnitMapper extends BaseMapper<SafeSiConstructionUnit> {

    /**
     * 外委施工单位-查询
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_name,content,create_user_name,case when ISNULL(ceate_time) then '' else DATE_FORMAT(ceate_time,'%Y-%m-%d %H:%i:%s') end as ceate_time\n" +
            "from safe_si_construction_unit\n" +
            "where (#{condition.construction_unit_name} = '' or construction_unit_name like (concat('%', #{condition.construction_unit_name}, '%')))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeConstructionUnitPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

}
