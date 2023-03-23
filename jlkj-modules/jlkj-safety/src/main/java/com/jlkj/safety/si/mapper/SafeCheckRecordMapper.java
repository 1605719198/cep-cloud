package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiCheckRecord;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * <p>
 * 安全巡检 Mapper 接口
 * </p>
 *
 * @author cy
 * @since 2021-04-08
 */
public interface SafeCheckRecordMapper extends BaseMapper<SafeSiCheckRecord> {

    /**
     * 安全巡检-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id as safe_check_id,check_content,check_person_name,check_type_name,safe_area_name,check_image1,check_image2,check_image3,check_image4,check_status,check_disposed,\n" +
            "DATE_FORMAT(check_time,'%Y-%m-%d %H:%i:%s') as check_time,disposer_id,disposer_image1,disposer_image2,disposer_image3,disposer_id,disposer_name,dispose_content,DATE_FORMAT(dispose_time,'%Y-%m-%d %H:%i:%s') as dispose_time\n" +
            "from safe_si_check_record \n" +
            "where\n" +
            "(#{condition.check_time_start} = '' or check_time >= case when #{condition.check_time_start} = '' then null else #{condition.check_time_start} end) and\n" +
            "(#{condition.check_time_end} = '' or check_time <= case when #{condition.check_time_end} = '' then null else #{condition.check_time_end} end) and\n" +
            "(#{condition.safe_area_id} = '' or safe_area_id = #{condition.safe_area_id}) and\n" +
            "(#{condition.check_type_id} = '' or check_type_id = #{condition.check_type_id}) and\n" +
            "(#{condition.check_status} = -1 or check_status = #{condition.check_status}) and\n" +
            "(#{condition.check_person_name} = '' or check_person_name like concat('%',#{condition.check_person_name},'%'))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeCheckRecordPageList(Page<?> page, @Param("condition")Map<String, Object> condition);

    /**
     * 安全巡检-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select a.id as safe_check_id,a.check_status,a.check_content,\n" +
            "DATE_FORMAT(a.check_time,'%Y-%m-%d %H:%i:%s') as check_time,\n" +
            "a.check_disposed,a.check_person_id,a.check_person_name,a.check_depart_id,a.check_depart_name,a.check_type_id,a.check_type_name,a.safe_area_id,a.safe_area_name,\n" +
            "check_image1,check_image2,check_image3,check_image4,disposer_image1,disposer_image2,disposer_image3,\n" +
            "case when ISNULL(b.relative_path) then '' else b.relative_path end as check_image1_path,\n" +
            "case when ISNULL(c.relative_path) then '' else c.relative_path end as check_image2_path,\n" +
            "case when ISNULL(d.relative_path) then '' else d.relative_path end as check_image3_path,\n" +
            "case when ISNULL(e.relative_path) then '' else e.relative_path end as check_image4_path,\n" +
            "case when ISNULL(f.relative_path) then '' else f.relative_path end as disposer_image1_path,\n" +
            "case when ISNULL(g.relative_path) then '' else g.relative_path end as disposer_image2_path,\n" +
            "case when ISNULL(h.relative_path) then '' else h.relative_path end as disposer_image3_path\n" +
            "from safe_si_check_record a\n" +
            "LEFT JOIN sys_fileresource b ON a.check_image1 = b.id\n" +
            "LEFT JOIN sys_fileresource c ON a.check_image2 = c.id\n" +
            "LEFT JOIN sys_fileresource d ON a.check_image3 = d.id\n" +
            "LEFT JOIN sys_fileresource e ON a.check_image4 = e.id\n" +
            "LEFT JOIN sys_fileresource f ON a.disposer_image1 = f.id\n" +
            "LEFT JOIN sys_fileresource g ON a.disposer_image2 = g.id\n" +
            "LEFT JOIN sys_fileresource h ON a.disposer_image3 = h.id\n" +
            "where a.id = #{safe_check_id}"
    )
    Map<String, Object> getSafeCheckRecordById(Map<String, Object> condition);

}
