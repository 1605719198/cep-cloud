package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.safety.si.entity.Empty;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公共-数据列表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface CommonListMapper extends BaseMapper<Empty> {

    /**
     * 外委单位人员下拉列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code from safe_si_construction_unit_persons\n" +
            "where (#{id_list} = id) or (#{id_list} like concat('%,', id)) or (#{id_list} like concat(id + ',%')) or (#{id_list} like concat('%,', id, ',%'))\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeConstructionUnitPersonList(Map<String, Object> condition);
}
