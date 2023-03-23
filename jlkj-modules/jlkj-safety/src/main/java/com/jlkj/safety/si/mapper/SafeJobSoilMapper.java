package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeJobSoil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 7.动土安全作业证 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSoilMapper extends BaseMapper<SafeJobSoil> {

    /**
     * 作业证编号
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            " select max(job_code) as job_code" +
            " from safe_si_job_soil" +
            " where job_code like concat(#{codeHeader}, '%')"
    )
    Map<String, Object> getMaxJobCode(Map<String, Object> condition);

    /**
     * 动土安全作业证-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select("select id, apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
            "safe_area_id,safe_area_name,place,job_content,guardian_person_id,guardian_person_name,other_job,\n" +
            "DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') as start_time,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time,\n" +
            "safe_education_persons,harm_identification,job_name,\n" +
            "status,case status when 0 then '待审批' when 1 then '已审批' when 2 then '已完成' else '' end as status_text,\n" +
            "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout\n" +
            "from safe_si_job_soil\n" +
            "where id=#{id}"
    )
    Map<String, Object> getSafeJobSoilInfo(Map<String, Object> condition);

    /**
     * 动土安全作业证-作业人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code from safe_si_job_soil_persons\n" +
            "where job_id=#{id}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeJobSoilPersonsList(Map<String, Object> condition);

    /**
     * 动土安全作业证-安全措施列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,\n" +
            "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time\n" +
            "from safe_si_job_soil_safety_measures\n" +
            "where job_id=#{id}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobSoilSafetyMeasuresList(Map<String, Object> condition);

    /**
     * 动土安全作业证-审批人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,approval_name,approval_person_id,approval_person_name,is_required,content,is_approval,pick_person_name,signature,\n" +
                    "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time,\n" +
            "case when ISNULL(approval_time) then '' else DATE_FORMAT(approval_time,'%Y-%m-%d %H:%i:%s') end as approval_time,is_single,sort\n" +
            "from safe_si_job_soil_approval\n" +
            "where job_id=#{id}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobSoilApprovalList(Map<String, Object> condition);

    /**
     * 动土安全作业证-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
            "safe_area_id,safe_area_name,place,job_content,guardian_person_id,guardian_person_name,other_job,\n" +
            "DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') as start_time,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time,\n" +
            "harm_identification,safe_education_persons,\n" +
            "status,case status when 0 then '资料建立' when 1 then '审批中' when 2 then '作业中' when 3 then '作业完成' else '' end as status_text,\n" +
            "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout\n" +
            "from safe_si_job_soil\n" +
            "where\n" +
            "(#{condition.start_time} = '' or start_time >= case when #{condition.start_time} = '' then null else #{condition.start_time} end) and\n" +
            "(#{condition.end_time} = '' or start_time <= case when #{condition.end_time} = '' then null else #{condition.end_time} end) and\n" +
            "(#{condition.status} = -1 or status = #{condition.status}) and\n" +
            "(#{condition.safe_area_id} = '' or safe_area_id = #{condition.safe_area_id}) and\n" +
            "(#{condition.apply_person_id} = '' or apply_person_id = #{condition.apply_person_id}) \n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeJobSoilPageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 动土安全作业证-增加安全措施
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_soil_safety_measures(id,job_id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,confirm_time)\n" +
            "select uuid(),#{id},sort,safety_measures,0,0,'',null from v_safe_job_soil_measures"
    )
    int insertSafeJobSoilSafetyMeasures(Map<String, Object> condition);

    /**
     * 动土安全作业证-增加审批人
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_soil_approval(id,job_id,sort,approval_name,approval_person_id,approval_person_name,is_required,content,approval_time,is_approval,pick_person_id,pick_person_name,pick_time)\n" +
            "VALUES (uuid(),#{id},#{sort},#{approval_name},#{approval_person_id},#{approval_person_name},#{is_required},'',null,0,#{pick_person_id},#{pick_person_name},NOW()) "
    )
    int insertSafeJobSoilApproval(Map<String, Object> condition);

    /**
     * 动土安全作业证-删除作业人员
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_soil_persons where job_id=#{id}"
    )
    int deleteSafeJobSoilPersons(Map<String, Object> condition);

    /**
     * 动土安全作业证-删除安全措施
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_soil_safety_measures where job_id=#{id}"
    )
    int deleteSafeJobSoilSafetyMeasures(Map<String, Object> condition);

    /**
     * 动土安全作业证-删除审批人
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_soil_approval where job_id=#{id}"
    )
    int deleteSafeJobSoilApproval(Map<String, Object> condition);

    /**
     * 动土安全作业证-审批人模板列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select * from v_safe_job_soil_approval\n" +
                    " order by sort"
    )
    List<Map<String, Object>> getSafeJobSoilApprovalTemplateList(Map<String, Object> condition);

    /**
     * 动土安全作业证-附件列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.appendix_id,b.file_name from safe_si_job_soil_appendix a\n" +
                    "INNER JOIN sys_fileresource b ON a.appendix_id = b.id\n" +
                    "where a.job_id=#{id}"
    )
    List<Map<String, Object>> getSafeJobSoilFileList(Map<String, Object> condition);

    /**
     * 动土安全作业证-详情-pdf
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "SELECT job_code,apply_depart_name,apply_person_name,job_unit_name,job_content,job_name,\n" +
            "DATE_FORMAT( start_time, '%Y年%m月%d日 %H时%i分' ) AS start_time,DATE_FORMAT( end_time, '%Y年%m月%d日 %H时%i分' ) AS end_time\n" +
            "FROM safe_si_job_soil where id=#{id} LIMIT 1 "
    )
    Map<String, Object> getSafeJobSoilById(Map<String, Object> condition);

    /**
     * 动土安全作业证-审批信息-pdf
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select("select approval_name,approval_person_name,DATE_FORMAT(approval_time,'%Y年%m月%d日 %H时%i分') as approval_time,content,a.signature,department_name " +
            " from safe_si_job_soil_approval a  LEFT JOIN human_hm_personnel b on a.approval_person_id=b.id " +
            "where job_id=#{id}"
    )
    List<Map<String, Object>> getSafeJobSoilApprovalById(Map<String, Object> condition);





}
