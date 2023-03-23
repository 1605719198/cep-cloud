package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiDangerRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 隐患处理 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2022-06-22
 */
public interface SafeDangerRecordMapper extends BaseMapper<SafeSiDangerRecord> {

    /**
     * 安全隐患-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select a.danger_content,a.dispose_content,a.dispose_measure,\n" +
            "case when ISNULL(a.danger_time) then '' else DATE_FORMAT(a.danger_time,'%Y-%m-%d %H:%i:%s') end as danger_time,\n" +
            "case when ISNULL(a.dispose_time) then '' else DATE_FORMAT(a.dispose_time,'%Y-%m-%d %H:%i:%s') end as dispose_time,\n" +
            "case when ISNULL(a.danger_deadline_time) then '' else DATE_FORMAT(a.danger_deadline_time,'%Y-%m-%d') end as danger_deadline_time,\n" +
            "case when ISNULL(b.relative_path) then '' else b.relative_path end as submit_image1,\n" +
            "case when ISNULL(c.relative_path) then '' else c.relative_path end as submit_image2,\n" +
            "case when ISNULL(d.relative_path) then '' else d.relative_path end as submit_image3,\n" +
            "case when ISNULL(e.relative_path) then '' else e.relative_path end as submit_image4,\n" +
            "case when ISNULL(f.relative_path) then '' else f.relative_path end as submit_sign_image,\n" +
            "acceptance_depart_name,acceptance_person_name,acceptance_appraise,\n" +
            "case when ISNULL(a.acceptance_time) then '' else DATE_FORMAT(a.acceptance_time,'%Y-%m-%d %H:%i:%s') end as acceptance_time,\n" +
            "case when ISNULL(g.relative_path) then '' else g.relative_path end as acceptance_image1,\n" +
            "case when ISNULL(h.relative_path) then '' else h.relative_path end as acceptance_image2,\n" +
            "case when ISNULL(i.relative_path) then '' else i.relative_path end as acceptance_image3,\n" +
            "case when ISNULL(j.relative_path) then '' else j.relative_path end as acceptance_image4,\n" +
            "case when ISNULL(k.relative_path) then '' else k.relative_path end as acceptance_sign_image,\n" +
            "a.danger_status,\n" +
            "submit_person_id,\n" +
            "submit_person_name,\n" +
            "acceptance_person_id,\n" +
            "acceptance_person_name,\n" +
            "safe_area_id,\n" +
            "safe_area_name,\n" +
            "danger_type_id,\n" +
            "danger_type_name,\n" +
            "duty_depart_id,\n" +
            "duty_depart_name,\n" +
            "duty_person_id,\n" +
            "duty_person_name\n" +
            "from safe_si_danger_record a\n" +
            "LEFT JOIN sys_fileresource b ON a.submit_image1 = b.id\n" +
            "LEFT JOIN sys_fileresource c ON a.submit_image2 = c.id\n" +
            "LEFT JOIN sys_fileresource d ON a.submit_image3 = d.id\n" +
            "LEFT JOIN sys_fileresource e ON a.submit_image4 = e.id\n" +
            "LEFT JOIN sys_fileresource f ON a.submit_sign_image = f.id\n" +
            "LEFT JOIN sys_fileresource g ON a.acceptance_image1 = g.id\n" +
            "LEFT JOIN sys_fileresource h ON a.acceptance_image2 = h.id\n" +
            "LEFT JOIN sys_fileresource i ON a.acceptance_image3 = i.id\n" +
            "LEFT JOIN sys_fileresource j ON a.acceptance_image4 = j.id\n" +
            "LEFT JOIN sys_fileresource k ON a.acceptance_sign_image = k.id\n" +
            "where a.id=#{danger_id}"
    )
    Map<String, Object> getSafeDangerRecordById(Map<String, Object> condition);

    /**
     * 安全隐患-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id as danger_id,danger_number,submit_depart_name,submit_person_id,submit_person_name,danger_time,safe_area_name,danger_status,safe_area_id,danger_type_id,danger_type_name,\n" +
            "duty_depart_id,duty_depart_name,duty_person_id,duty_person_name,danger_content,acceptance_person_id,acceptance_person_name,\n" +
            "case when ISNULL(a.danger_deadline_time) then '' else DATE_FORMAT(a.danger_deadline_time,'%Y-%m-%d') end as danger_deadline_time,\n" +
            "case when (danger_status = 2 and acceptance_time <= danger_deadline_time) or (danger_status < 2 and now() <= danger_deadline_time) then 0 else 1 end as timeout_status,submit_image1,submit_image2,submit_image3,submit_image4\n" +
            "from safe_si_danger_record a\n" +
            "where\n" +
            "(#{condition.id} = '' or a.id = #{condition.id}) and\n" +
            "(#{condition.danger_time_start} = '' or danger_time >= case when #{condition.danger_time_start} = '' then null else #{condition.danger_time_start} end) and\n" +
            "(#{condition.danger_time_end} = '' or danger_time <= case when #{condition.danger_time_end} = '' then null else #{condition.danger_time_end} end) and\n" +
            "(#{condition.safe_area_id} = '' or safe_area_id = #{condition.safe_area_id}) and\n" +
            "(#{condition.danger_type_id} = '' or danger_type_id = #{condition.danger_type_id}) and\n" +
            "(#{condition.danger_status} = -1 or danger_status = #{condition.danger_status}) and\n" +
            "(#{condition.timeout_status} = -1 or (#{condition.timeout_status} = 0 and ((danger_status = 2 and acceptance_time <= danger_deadline_time) or (danger_status < 2 and now() <= danger_deadline_time))) or (#{condition.timeout_status} = 1 and ((danger_status = 2 and acceptance_time > danger_deadline_time) or (danger_status < 2 and now() > danger_deadline_time))))\n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeDangerRecordPageList(Page<?> page, @Param("condition") Map<String, Object> condition);
}
