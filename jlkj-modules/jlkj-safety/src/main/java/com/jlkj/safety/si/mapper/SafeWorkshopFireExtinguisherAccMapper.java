package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeWorkshopFireExtinguisherAcc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 灭火器台账 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
public interface SafeWorkshopFireExtinguisherAccMapper extends BaseMapper<SafeWorkshopFireExtinguisherAcc> {

    /**
     * 灭火器台账-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,workshop_name,position,material_name,specification,amount,\n" +
            "case when ISNULL(out_date) then '' else DATE_FORMAT(out_date,'%Y-%m-%d') end as out_date,\n" +
            "case when ISNULL(first_date) then '' else DATE_FORMAT(first_date,'%Y-%m-%d') end as first_date,\n" +
            "case when ISNULL(next_date) then '' else DATE_FORMAT(next_date,'%Y-%m-%d') end as next_date,\n" +
            "case when ISNULL(bad_date) then '' else DATE_FORMAT(bad_date,'%Y-%m-%d') end as bad_date,\n" +
            "charge_person,remark,create_user,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as create_time\n" +
            "from safe_si_workshop_fire_extinguisher_acc\n" +
            "where (#{condition.workshop_name} = '' or workshop_name like (concat('%', #{condition.workshop_name}, '%'))) and (#{condition.material_name} = '' or material_name like (concat('%', #{condition.material_name}, '%')))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeWorkshopFireExtinguisherAccPageList(Page<?> page, @Param("condition") Map<String, Object> condition);
}
