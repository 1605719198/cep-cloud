package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiWorkshopAccSection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 车间明细台账-工段 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-06-04
 */
public interface SafeWorkshopAccSectionMapper extends BaseMapper<SafeSiWorkshopAccSection> {

    /**
     * 工段-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,section_name\n" +
                    "from safe_si_workshop_acc_section\n" +
                    "where workshop_name = #{condition.workshop_name}\n" +
                    "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeWorkshopAccSectionPageList(Page<?> page, @Param("condition") Map<String, Object> condition);
}
