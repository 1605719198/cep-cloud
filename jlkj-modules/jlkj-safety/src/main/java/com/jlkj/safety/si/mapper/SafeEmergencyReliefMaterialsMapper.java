package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeEmergencyReliefMaterials;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 气防站应急救援物资 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
public interface SafeEmergencyReliefMaterialsMapper extends BaseMapper<SafeEmergencyReliefMaterials> {

    /**
     * 气防站应急救援物资-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,material_name,specification,amount,charge_person_id,charge_person,remark,\n" +
            "create_user,DATE_FORMAT(create_time,'%Y-%m-%d') as create_time\n" +
            "from safe_si_emergency_relief_materials\n" +
            "where (#{condition.material_name} = '' or material_name like (concat('%', #{condition.material_name}, '%')))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeEmergencyReliefMaterialsPageList(Page<?> page, @Param("condition") Map<String, Object> condition);
}
