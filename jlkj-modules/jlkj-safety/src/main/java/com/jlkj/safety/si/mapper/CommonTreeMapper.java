package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.safety.si.entity.Empty;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公共-树型列表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface CommonTreeMapper extends BaseMapper<Empty> {

    /**
     * 施工人员树
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,case when pid = 'D-0' then 'T-1' else pid end as pid,label,work_type_name from (\n" +
            "select '1' as sort, 'T-1' as id, '' as pid,'本厂' as label,'' work_type_name\n" +
            "UNION ALL\n" +
            "select '1-' as sort,concat('D-', a.id) as id,  case when a.label = '吉林建龙钢铁有限责任公司' then 'T-1' else concat('D-', a.parent_id) end as pid, a.label,'' work_type_name from human_hm_perorg_tree a where a.flag = 'dept'\n" +
            "UNION ALL\n" +
            "select concat('1-', c.person_name) as sort,concat('U-', c.id) as id,concat('D-', a.parent_id) as pid,a.label,work_type_name\n" +
            "from human_hm_perorg_tree a\n" +
            "INNER JOIN safe_si_construction_unit_persons c ON a.id = c.person_id and c.type = 1\n" +
            "where a.flag = 'user'\n" +
            "UNION ALL\n" +
            "select '2' as sort, 'T-2' as id, '' as pid,'外委' as label,'' work_type_name\n" +
            "UNION ALL\n" +
            "select '2-' as sort,concat('C-', id) as id,'T-2' as pid,construction_unit_name as label,'' work_type_name from safe_si_construction_unit\n" +
            "UNION ALL\n" +
            "select concat('2-', person_name) as sort,concat('P-', id) as id,concat('C-',construction_unit_id) as pid,person_name as label,work_type_name\n" +
            "from safe_si_construction_unit_persons where type=2\n" +
            ") as a order by a.sort"
    )
    List<Map<String, Object>> getSafeConstructionUnitPersonTree(Map<String, Object> condition);

    /**
     * 施工单位树
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,pid,label,type from (\n" +
            "select '1' as sort, 'T-1' as id, '' as pid,'本厂' as label,0 as type\n" +
            "UNION ALL\n" +
            "select '1-' as sort,id,case when label = '吉林建龙钢铁有限责任公司' then 'T-1' else parent_id end as pid, label, 1 as type from human_hm_perorg_tree where flag = 'dept'\n" +
            "UNION ALL\n" +
            "select '2' as sort, 'T-2' as id, '' as pid,'外委' as label,0 as type\n" +
            "UNION ALL\n" +
            "select '2-' as sort,id,'T-2' as pid,construction_unit_name as label,2 as type from safe_si_construction_unit\n" +
            ") as a order by a.sort"
    )
    List<Map<String, Object>> getSafeConstructionUnitTree(Map<String, Object> condition);

    /**
     * 知识类别树
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.id,a.parent_knowledge_category_id as pid,a.knowledge_category_name as label \n" +
            "from safe_si_knowledge_category a\n" +
            "LEFT JOIN safe_si_knowledge_category b ON a.parent_knowledge_category_id = b.id\n" +
            "order by a.knowledge_category_name"
    )
    List<Map<String, Object>> getSafeKnowledgeCategoryTree(Map<String, Object> condition);
}
