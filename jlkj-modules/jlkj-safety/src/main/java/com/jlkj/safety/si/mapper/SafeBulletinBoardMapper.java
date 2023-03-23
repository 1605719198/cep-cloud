package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeBulletinBoard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 安全承诺公告牌 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-05-13
 */
public interface SafeBulletinBoardMapper extends BaseMapper<SafeBulletinBoard> {

    /**
     * 安全承诺公告牌-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,title,team_risk_assessment,team_commitment,principal_responsible_person,create_user_name,\n" +
            "case when ISNULL(create_time) then '' else DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') end as create_time\n" +
            "from safe_si_bulletin_board\n" +
            "where (#{condition.start_time} = '' or create_time >= #{condition.start_time}) and (#{condition.end_time} = '' or create_time <= #{condition.end_time}) and\n" +
            "(#{condition.title} = '' or title like (concat('%', #{condition.title},'%')))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeBulletinBoardPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 安全承诺公告牌-模板
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select title,team_risk_assessment,team_commitment,create_time from v_safe_bulletin_board\n"
    )
    Map<String, Object> getSafeBulletinBoardTemplateInfo(Map<String, Object> condition);
}
