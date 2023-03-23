package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiKnowledgeCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 知识类别 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeKnowledgeCategoryMapper extends BaseMapper<SafeSiKnowledgeCategory> {

    /**
     * 知识类别-查询
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.id,a.knowledge_category_name,a.parent_knowledge_category_id,case when ISNULL(b.knowledge_category_name) then '' else b.knowledge_category_name end as parent_knowledge_category_name,a.create_user_name,DATE_FORMAT(a.ceate_time,'%Y-%m-%d %H:%i:%s') as ceate_time\n" +
            "from safe_si_knowledge_category a\n" +
            "LEFT JOIN safe_si_knowledge_category b ON a.parent_knowledge_category_id = b.id\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeKnowledgeCategoryPageList(Page<?> page, @Param("condition") Map<String, Object> condition);
}
