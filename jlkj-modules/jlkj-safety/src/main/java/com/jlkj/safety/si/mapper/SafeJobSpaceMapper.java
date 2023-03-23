package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeJobSpace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 2.受限空间安全作业证 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSpaceMapper extends BaseMapper<SafeJobSpace> {

    /**
     * 作业证编号
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            " select max(job_code) as job_code" +
            " from safe_si_job_space" +
            " where job_code like concat(#{codeHeader}, '%')"
    )
    Map<String, Object> getMaxJobCode(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
            "job_level,safe_area_id,safe_area_name,place,original_medium_name,job_content,\n" +
            "case when ISNULL(start_time) then '' else DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') end as start_time,\n" +
            "case when ISNULL(end_time) then '' else DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') end as end_time,\n" +
            "job_responsible_person_name,guardian_person_id,guardian_person_name,work_type_name,other_job,\n" +
            "safe_education_person_name,harm_identification,equip_dep_id,equip_dep_name,job_responsible_person_id,\n" +
            "status,case status when 0 then '资料建立' when 1 then '审批中' when 2 then '作业中' when 3 then '作业完成' else '' end as status_text,\n" +
            "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout,work_emp_id,work_emp_name,rescue_equipment\n" +
            "from safe_si_job_space\n" +
            "where id=#{id}"
    )
    Map<String, Object> getSafeJobSpaceInfo(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-作业人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code from safe_si_job_space_persons\n" +
            "where job_id=#{id}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeJobSpacePersonsList(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-分析列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,DATE_FORMAT(analysis_time,'%Y-%m-%d %H:%i:%s') as analysis_time,toxic_medium,combustible_gas,oxygen_content,position,person_name from safe_si_job_space_analysis\n" +
            "where job_id=#{id}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeJobSpaceAnalysisList(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-安全措施列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,\n" +
            "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time\n" +
            "from safe_si_job_space_safety_measures\n" +
            "where job_id=#{id}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobSpaceSafetyMeasuresList(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-审批人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,approval_name,approval_person_id,approval_person_name,is_required,content,is_approval,sort,pick_person_id,pick_person_name,\n" +
            "case when ISNULL(approval_time) then '' else DATE_FORMAT(approval_time,'%Y-%m-%d %H:%i:%s') end as approval_time,\n" +
            "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time,signature\n" +
            "from safe_si_job_space_approval\n" +
            "where job_id=#{id}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobSpaceApprovalList(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
            "job_level,safe_area_id,safe_area_name,place,original_medium_name,job_content,\n" +
            "case when ISNULL(start_time) then '' else DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') end as start_time,\n" +
            "case when ISNULL(end_time) then '' else DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') end as end_time,\n" +
            "job_responsible_person_name,guardian_person_id,guardian_person_name,work_type_name,job_persons,other_job,\n" +
            "safe_education_person_name,harm_identification,equip_dep_id,equip_dep_name,job_code_erp,\n" +
            "status,case status when 0 then '资料建立' when 1 then '审批中' when 2 then '作业中' when 3 then '作业完成' else '' end as status_text,\n" +
            "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout,work_emp_id,work_emp_name,rescue_equipment\n" +
            "from safe_si_job_space\n" +
            "where\n" +
            "(#{condition.start_time} = '' or start_time >= case when #{condition.start_time} = '' then null else #{condition.start_time} end) and\n" +
            "(#{condition.end_time} = '' or start_time <= case when #{condition.end_time} = '' then null else #{condition.end_time} end) and\n" +
            "(#{condition.status} = -1 or status = #{condition.status}) and\n" +
            "(#{condition.job_level} = '' or job_level like concat('%',#{condition.job_level},'%')) and\n" +
            "(#{condition.safe_area_id} = '' or safe_area_id = #{condition.safe_area_id}) and\n" +
            "(#{condition.apply_person_id} = '' or apply_person_id = #{condition.apply_person_id}) \n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeJobSpacePageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 受限空间安全作业证-增加安全措施
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_space_safety_measures(id,job_id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,confirm_time)\n" +
            "select uuid(),#{id},sort,safety_measures,0,0,'',null from v_safe_job_space_measures"
    )
    int insertSafeJobSpaceSafetyMeasures(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-增加审批人
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_space_approval(id,job_id,sort,approval_name,approval_person_id,approval_person_name,is_required,content,approval_time,is_approval,pick_person_id,pick_person_name,pick_time)\n" +
            "select uuid(),#{id},sort,approval_name,0,'',is_required,'',null,0,0,'',null from v_safe_job_space_approval"
    )
    int insertSafeJobSpaceApproval(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-增加作业人员
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_space_persons(id,job_id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code)\n" +
            "select uuid(),#{job_id},construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code from safe_si_construction_unit_persons\n" +
            "where (#{id_list} = convert(varchar, id)) or (#{id_list} like '%,' + convert(varchar, id)) or (#{id_list} like convert(varchar, id) + ',%') or (#{id_list} like '%,' + convert(varchar, id) + ',%')\n"
    )
    int insertSafeJobSpacePersons(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-删除作业人员
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_space_persons where job_id=#{id}"
    )
    int deleteSafeJobSpacePersons(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-删除作业分析
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_space_analysis where job_id=#{id}"
    )
    int deleteSafeJobSpaceAnalysis(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-删除安全措施
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_space_safety_measures where job_id=#{id}"
    )
    int deleteSafeJobSpaceSafetyMeasures(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-删除审批人
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_space_approval where job_id=#{id}"
    )
    int deleteSafeJobSpaceApproval(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-审批人模板列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select * from v_safe_job_space_approval\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobSpaceApprovalTemplateList(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-删除附件
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_space_appendix where job_id=#{id}"
    )
    int deleteSafeJobSpaceFiles(Map<String, Object> condition);

    /**
     * 受限空间安全作业证-附件列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.appendix_id,b.file_name from safe_si_job_space_appendix a\n" +
            "INNER JOIN sys_fileresource b ON a.appendix_id = b.id\n" +
            "where a.job_id=#{id}"
    )
    List<Map<String, Object>> getSafeJobSpaceFileList(Map<String, Object> condition);
}
