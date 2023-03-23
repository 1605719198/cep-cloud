package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 安全区域 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeAreaMapper extends BaseMapper<SafeSiArea> {

    /**
     * 安全区域-查询
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,safe_area_name,lng,lat,create_user_name,case when ISNULL(ceate_time) then '' else DATE_FORMAT(ceate_time,'%Y-%m-%d %H:%i:%s') end as ceate_time\n" +
            "from safe_si_area\n" +
            "where (#{condition.safe_area_name} = '' or safe_area_name like concat('%',#{condition.safe_area_name},'%'))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeAreaPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

}
