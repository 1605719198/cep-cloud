package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiConstructionUnitPersons;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 施工单位-施工人员 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-01-06
 */
public interface SafeConstructionUnitPersonsMapper extends BaseMapper<SafeSiConstructionUnitPersons> {

    /**
     * 施工人员-查询
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code,create_user_name,case when ISNULL(ceate_time) then '' else DATE_FORMAT(ceate_time,'%Y-%m-%d %H:%i:%s') end as ceate_time,\n" +
            "is_leader,case is_leader when 0 then '否' when 1 then '是' else '' end as is_leader_text,case type when 1 then '本厂' when 2 then '外委' else '' end as type_text\n" +
            "from safe_si_construction_unit_persons\n" +
            "where (#{condition.construction_unit_id} = '' or construction_unit_id = #{condition.construction_unit_id}) and\n" +
            "(#{condition.type} = 0 or type = #{condition.type}) and\n" +
            "(#{condition.person_name} = '' or person_name like (concat('%', #{condition.person_name}, '%')))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeConstructionUnitPersonsPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 更新施工单位信息
     * @param condition 数据
     * @return 结果
     */
    @Update(" update safe_si_construction_unit_persons set" +
            " construction_unit_name = #{construction_unit_name}\n" +
            " where construction_unit_id = #{id}")
    int updateSafeConstructionUnitPersonsConstructionUnit(Map<String, Object> condition);


}
