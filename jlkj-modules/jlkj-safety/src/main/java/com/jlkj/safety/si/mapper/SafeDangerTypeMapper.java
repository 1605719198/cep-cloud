package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiDangerType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 隐患等级 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
public interface SafeDangerTypeMapper extends BaseMapper<SafeSiDangerType> {

    /**
     * 隐患等级-查询
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,danger_type_name,create_user_name,DATE_FORMAT(ceate_time,'%Y-%m-%d %H:%i:%s') as ceate_time\n" +
            "from safe_si_danger_type\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeDangerTypePageList(Page<?> page, @Param("condition") Map<String, Object> condition);
}
