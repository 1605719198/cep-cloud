package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiJobFire;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 1.动火安全作业证 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeJobFireMapper extends BaseMapper<SafeSiJobFire> {

    /**
     * 作业证编号
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            " select max(job_code) as job_code" +
            " from safe_si_job_fire" +
            " where job_code like concat(#{codeHeader}, '%')"
    )
    Map<String, Object> getMaxJobCode(Map<String, Object> condition);

    /**
     * 动火安全作业证-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select  * " +
            "from safe_si_job_fire\n" +
            "where id=#{id}"
    )
    Map<String, Object> getSafeJobFireInfo(Map<String, Object> condition);

    /**
     * 动火安全作业证-作业人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code from safe_si_job_fire_persons\n" +
            "where job_id=#{id}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeJobFirePersonsList(Map<String, Object> condition);

    /**
     * 动火安全作业证-分析列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,DATE_FORMAT(analysis_time,'%Y-%m-%d %H:%i:%s') as analysis_time,person_id,person_name,analysis_area,co_density,o2_density,othre_density from safe_si_job_fire_analysis\n" +
            "where job_id=#{id}\n" +
            "order by id"
    )
    List<Map<String, Object>> getSafeJobFireAnalysisList(Map<String, Object> condition);

    /**
     * 动火安全作业证-安全措施列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,\n" +
            "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time\n" +
            "from safe_si_job_fire_safety_measures\n" +
            "where job_id=#{id}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobFireSafetyMeasuresList(Map<String, Object> condition);

    /**
     * 动火安全作业证-审批人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,approval_name,approval_person_id,approval_person_name,is_required,content,is_approval,sort,pick_person_id,pick_person_name,\n" +
            "case when ISNULL(approval_time) then '' else DATE_FORMAT(approval_time,'%Y-%m-%d %H:%i:%s') end as approval_time,\n" +
            "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time,signature\n" +
            "from safe_si_job_fire_approval\n" +
            "where job_id=#{id}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobFireApprovalList(Map<String, Object> condition);

    /**
     * 动火安全作业证-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
            "job_level,safe_area_id,safe_area_name,place,mode,job_responsible_person_id,job_responsible_person_name,\n" +
            "DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') as start_time,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time,\n" +
            "other_job,safe_education_person_name,harm_identification,guardian_person_id,guardian_person_name,\n" +
            "work_type_name,production_responsible_person_name,relation_guardian_person_id,relation_guardian_person_name,\n" +
            "relation_guardian_person_work_type_name,\n" +
            "status,case status when 0 then '资料建立' when 1 then '审批中' when 2 then '作业中' when 3 then '作业完成' else '' end as status_text,\n" +
            "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout,\n" +
            "equipment_unit_id,equipment_unit_name,special_job_no,job_contents,job_responsible_person_phone,is_gas_area,special_name,job_code_erp\n" +
            "from safe_si_job_fire\n" +
            "where\n" +
            "(#{condition.start_time} = '' or start_time >= case when #{condition.start_time} = '' then null else #{condition.start_time} end) and\n" +
            "(#{condition.end_time} = '' or start_time <= case when #{condition.end_time} = '' then null else #{condition.end_time} end) and\n" +
            "(#{condition.status} = -1 or status = #{condition.status}) and\n" +
            "(#{condition.is_gas_area} = -1 or is_gas_area = #{condition.is_gas_area}) and\n" +
            "(#{condition.job_level} = '' or  (#{condition.job_level} = '特级' and job_level like '%1%') or  (#{condition.job_level} = '一级' and job_level not like '%1%' and job_level != 6) or (#{condition.job_level} = '二级' and job_level = 6)) and\n" +
            "(#{condition.safe_area_id} = '' or safe_area_id = #{condition.safe_area_id}) and\n" +
            "(#{condition.apply_person_id} = '' or apply_person_id = #{condition.apply_person_id}) \n" +
            "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeJobFirePageList(Page<?> page, @Param("condition") Map<String, Object> condition);

    /**
     * 动火安全作业证-增加安全措施
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_fire_safety_measures(id,job_id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,confirm_time)\n" +
            "select uuid(),#{id},sort,safety_measures,0,0,'',null from v_safe_job_fire_measures"
    )
    int insertSafeJobFireSafetyMeasures(Map<String, Object> condition);

    /**
     * 动火安全作业证-增加审批人
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_fire_approval(id,job_id,sort,approval_name,approval_person_id,approval_person_name,is_required,content,approval_time,is_approval,pick_person_id,pick_person_name,pick_time)\n" +
            "select uuid(),#{id},sort,approval_name,0,'',is_required,'',null,0,0,'',null from v_safe_job_fire_approval"
    )
    int insertSafeJobFireApproval(Map<String, Object> condition);

    /**
     * 动火安全作业证-删除作业人员
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_fire_persons where job_id=#{id}"
    )
    int deleteSafeJobFirePersons(Map<String, Object> condition);

    /**
     * 动火安全作业证-删除作业分析
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_fire_analysis where job_id=#{id}"
    )
    int deleteSafeJobFireAnalysis(Map<String, Object> condition);

    /**
     * 动火安全作业证-删除安全措施
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_fire_safety_measures where job_id=#{id}"
    )
    int deleteSafeJobFireSafetyMeasures(Map<String, Object> condition);

    /**
     * 动火安全作业证-删除审批人
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_fire_approval where job_id=#{id}"
    )
    int deleteSafeJobFireApproval(Map<String, Object> condition);

    /**
     * 动火安全作业证-审批模板列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select * from v_safe_job_fire_approval\n" +
            "where job_level=#{job_level}\n" +
            "order by sort"
    )
    List<Map<String, Object>> getSafeJobFireApprovalTemplateList(Map<String, Object> condition);

    /**
     * 动火安全作业证-删除附件
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_fire_appendix where job_id=#{id}"
    )
    int deleteSafeJobFireFiles(Map<String, Object> condition);

    /**
     * 动火安全作业证-附件列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.appendix_id,b.file_name from safe_si_job_fire_appendix a\n" +
            "INNER JOIN sys_fileresource b ON a.appendix_id = b.id\n" +
            "where a.job_id=#{id}"
    )
    List<Map<String, Object>> getSafeJobFireFileList(Map<String, Object> condition);

    /**
     * 动火安全作业证-作业级别模板列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select * from v_safe_job_fire_job_level\n" +
            "order by seq_no"
    )
    List<Map<String, Object>> getSafeJobFireJobLevelTemplateList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-信息
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select \n" +
            "a.id as uuid,\n" +
            "b.class_emp_no,\n" +
            "b.class_emp_name,\n" +
            "c.job_number as create_no,\n" +
            "DATE_FORMAT(a.apply_time,'%Y%m%d') as create_date,\n" +
            "DATE_FORMAT(a.apply_time,'%H%i%s') as create_time,\n" +
            "d.job_number as custodian_no,\n" +
            "d.full_name as custodian_name,\n" +
            "case when ISNULL(e.dep_boss_no) then '' else e.dep_boss_no end as dep_boss_no,\n" +
            "case when ISNULL(e.dep_boss_name) then '' else e.dep_boss_name end as dep_boss_name,\n" +
            "'xx' as equip_dep_no,\n" +
            "'xx' as equip_dep_no_ch,\n" +
            "'' as f_do_date_start,\n" +
            "'' as f_do_time_start,\n" +
            "f.fire_danger,\n" +
            "'' as fire_date,\n" +
            "'' as fire_date_end,\n" +
            "'' as fire_date_start,\n" +
            "n.organization_code as fire_dep01_no,\n" +
            "a.equipment_unit_name as fire_dep01_no_ch,\n" +
            "m.organization_code as fire_dep_no,\n" +
            "a.apply_depart_name as fire_dep_no_ch,\n" +
            "'' as fire_grade,\n" +
            "a.job_contents as fire_memo,\n" +
            "'' as fire_no,\n" +
            "a.place as fire_place,\n" +
            "DATE_FORMAT(a.start_time,'%Y%m%d') as fire_plan_date,\n" +
            "DATE_FORMAT(a.start_time,'%H%i%s') as fire_plan_time,\n" +
            "g.job_number as fire_respon_no,\n" +
            "g.full_name as fire_respon_name,\n" +
            "g.my_mobile_phone as fire_respon_phone,\n" +
            "'' as fire_time,\n" +
            "'' as fire_time_end,\n" +
            "'' as fire_time_start,\n" +
            "h.fire_type,\n" +
            "'' as fire_user_name,\n" +
            "'' as fire_user_no,\n" +
            "'' as first_trial_name,\n" +
            "'' as first_trial_no,\n" +
            "i.foreman_emp_name,\n" +
            "i.foreman_emp_no,\n" +
            "case when ISNULL(j.gas_station_emp_no) then '' else j.gas_station_emp_no end as gas_station_emp_no,\n" +
            "case when ISNULL(j.gas_station_emp_name) then '' else j.gas_station_emp_name end as gas_station_emp_name,\n" +
            "'' as handover_name,\n" +
            "'' as handover_no,\n" +
            "a.is_gas_area as is_gas,\n" +
            "a.relation_guardian_person_name as out_custodian_name,\n" +
            "'' as out_custodian_no,\n" +
            "case when ISNULL(a.special_name) then '' else a.special_name end as receive_name,\n" +
            "'' as receive_no,\n" +
            "k.safe_emp_no,\n" +
            "k.safe_emp_name,\n" +
            "case when ISNULL(l.security_emp_name) then '' else l.security_emp_name end as security_emp_name,\n" +
            "case when ISNULL(l.security_emp_no) then '' else l.security_emp_no end as security_emp_no,\n" +
            "'' as `status`,\n" +
            "'' as update_no,\n" +
            "'' as update_date,\n" +
            "'' as update_time,\n" +
            "'' as seq_no\n" +
            "from safe_si_job_fire a\n" +
            "INNER JOIN (\n" +
            "select a.job_id,b.job_number as class_emp_no,b.full_name as class_emp_name from safe_si_job_fire_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
            "where a.job_id=#{id} and a.approval_name='动火前，设备所属单位当班班长验票:'\n" +
            ") as b ON a.id = b.job_id\n" +
            "INNER JOIN human_hm_personnel c ON a.id=#{id} and a.apply_person_id = c.id\n" +
            "INNER JOIN human_hm_personnel d ON a.id=#{id} and a.guardian_person_id = d.id\n" +
            "LEFT JOIN (\n" +
            "select a.job_id,b.job_number as dep_boss_no,b.full_name as dep_boss_name from safe_si_job_fire_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
            "where a.job_id=#{id} and a.approval_name='设备所属单位一级主管:'\n" +
            ") as e ON a.id = e.job_id\n" +
            "INNER JOIN (\n" +
            "select a.id as job_id,GROUP_CONCAT(DISTINCT b.danger_code ORDER BY b.danger_code SEPARATOR '|') as fire_danger from safe_si_job_fire a,v_safe_job_fire_harm_identification b\n" +
            "where a.id=#{id} and ((a.harm_identification like concat(b.danger_name,'|%'))\n" +
            "or (a.harm_identification like concat('%|',b.danger_name,'|%'))\n" +
            "or (a.harm_identification like concat('%|',b.danger_name))\n" +
            "or (a.harm_identification = b.danger_name))\n" +
            "group by a.id\n" +
            ") as f ON a.id = f.job_id\n" +
            "INNER JOIN human_hm_personnel g ON a.id=#{id} and a.job_responsible_person_id = g.id\n" +
            "INNER JOIN (\n" +
            "select a.id as job_id,GROUP_CONCAT(DISTINCT b.type_code ORDER BY b.type_code SEPARATOR '|') as fire_type from safe_si_job_fire a,v_safe_job_fire_type b\n" +
            "where a.id=#{id} and ((a.mode like concat(b.type_name,'|%'))\n" +
            "or (a.mode like concat('%|',b.type_name,'|%'))\n" +
            "or (a.mode like concat('%|',b.type_name))\n" +
            "or (a.mode = b.type_name))\n" +
            "group by a.id\n" +
            ") as h ON a.id = h.job_id\n" +
            "INNER JOIN (\n" +
            "select a.job_id,b.job_number as foreman_emp_no,b.full_name as foreman_emp_name from safe_si_job_fire_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
            "where a.job_id=#{id} and a.approval_name='设备所属作业区作业长（值班作业长）:'\n" +
            ") as i ON a.id = i.job_id\n" +
            "LEFT JOIN (\n" +
            "select a.job_id,b.job_number as gas_station_emp_no,b.full_name as gas_station_emp_name from safe_si_job_fire_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
            "where a.job_id=#{id} and a.approval_name='煤气防护站:'\n" +
            ") as j ON a.id = j.job_id\n" +
            "INNER JOIN (\n" +
            "select a.job_id,b.job_number as safe_emp_no,b.full_name as safe_emp_name from safe_si_job_fire_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
            "where a.job_id=#{id} and a.approval_name='设备所属单位安全工程师（员）:'\n" +
            ") as k ON a.id = k.job_id\n" +
            "LEFT JOIN (\n" +
            "select a.job_id,b.job_number as security_emp_no,b.full_name as security_emp_name from safe_si_job_fire_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
            "where a.job_id=#{id} and a.approval_name='安保处专业工程师:'\n" +
            ") as l ON a.id = l.job_id\n" +
            "INNER JOIN human_hm_organization m ON a.apply_depart_id = m.id\n" +
            "INNER JOIN human_hm_organization n ON a.equipment_unit_id = n.id\n"
    )
    Map<String, Object> getErpDataSafeJobFireInfo(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-安全措施列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select\n" +
            "b.id as uuid,\n" +
            "a.id as parent_id,\n" +
            "b.sort as seq_no,\n" +
            "b.safety_measures as safe_method,\n" +
            "case when b.is_confirm = 1 then '_y' else '_n' end as is_use,\n" +
            "case when ISNULL(d.job_number) then '' else d.job_number end as confirm_no,\n" +
            "b.confirm_person_name as confirm_name,\n" +
            "'' as type,\n" +
            "c.job_number as create_no,\n" +
            "DATE_FORMAT(a.apply_time,'%Y%m%d') as create_date,\n" +
            "DATE_FORMAT(a.apply_time,'%H%i%s') as create_time,\n" +
            "'' as update_no,\n" +
            "'' as update_date,\n" +
            "'' as update_time,\n" +
            "a.job_level as is_grade\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN safe_si_job_fire_safety_measures b ON a.id = b.job_id\n" +
            "INNER JOIN human_hm_personnel c ON a.apply_person_id = c.id\n" +
            "LEFT JOIN human_hm_personnel d ON b.confirm_person_id = d.id\n" +
            "where a.id=#{id}"
    )
    List<Map<String, Object>> getErpDataSafeJobFireSafetyMeasuresList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-作业级别列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select\n" +
            "uuid() as uuid,\n" +
            "a.id as parent_id,\n" +
            "b.seq_no,\n" +
            "b.safe_method,\n" +
            "b.is_use,\n" +
            "'' as confirm_no,\n" +
            "'' as confirm_name,\n" +
            "'' as type,\n" +
            "c.job_number as create_no,\n" +
            "DATE_FORMAT(a.apply_time,'%Y%m%d') as create_date,\n" +
            "DATE_FORMAT(a.apply_time,'%H%i%s') as create_time,\n" +
            "'' as update_no,\n" +
            "'' as update_date,\n" +
            "'' as update_time,\n" +
            "a.job_level as is_grade\n" +
            "from safe_si_job_fire a, (select aa.*,case when ISNULL(cc.seq_no) then '_n' else '_y' end as is_use\n" +
            "from v_safe_job_fire_job_level aa\n" +
            "LEFT JOIN (select b.* from safe_si_job_fire a, v_safe_job_fire_job_level b\n" +
            "where a.id=#{id} and ((a.job_level like concat(seq_no,',%'))\n" +
            "or (a.job_level like concat('%,',b.seq_no,',%'))\n" +
            "or (a.job_level like concat('%,',b.seq_no))\n" +
            "or (a.job_level = b.seq_no))) cc ON aa.seq_no = cc.seq_no) b, human_hm_personnel c\n" +
            "where a.id=#{id} and a.apply_person_id = c.id"
    )
    List<Map<String, Object>> getErpDataSafeJobFireJobLevelList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-审批人列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select * from (\n" +
            "select\n" +
            "b.id as id,\n" +
            "'N' as actionCode,\n" +
            "case when b.sort < 10 then concat('0', convert(b.sort, char)) else convert(b.sort, char) end as chkNo,\n" +
            "case when b.content = '' then '同意' else b.content end as content,\n" +
            "case when b.approval_name = '完工验收作业负责人：' then '03' else '02' end as `status`,\n" +
            "'' as receiveDate,\n" +
            "'' as receiveTime,\n" +
            "DATE_FORMAT(b.approval_time,'%Y%m%d') as dealDate,\n" +
            "DATE_FORMAT(b.approval_time,'%H%i%s') as dealTime,\n" +
            "c.job_number as dealEmpno\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN safe_si_job_fire_approval b ON a.id = b.job_id\n" +
            "INNER JOIN human_hm_personnel c ON b.approval_person_id = c.id\n" +
            "where a.id=#{id}\n" +
            "union all\n" +
            "select\n" +
            "uuid() as id,\n" +
            "'N' as actionCode,\n" +
            "'00' as chkNo,\n" +
            "'' as content,\n" +
            "'00' as `status`,\n" +
            "'' as receiveDate,\n" +
            "'' as receiveTime,\n" +
            "DATE_FORMAT(a.apply_time,'%Y%m%d') as dealDate,\n" +
            "DATE_FORMAT(a.apply_time,'%H%i%s') as dealTime,\n" +
            "b.job_number as dealEmpno\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN human_hm_personnel b ON a.apply_person_id = b.id\n" +
            "where a.id=#{id}\n" +
            ") as a order by chkNo\n"
    )
    List<Map<String, Object>> getErpDataSafeJobFireApprovalList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-审批确认列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select\n" +
            "b.id as uuid,\n" +
            "c.job_number as updateNo,\n" +
            "DATE_FORMAT(b.confirm_time,'%Y%m%d') as updateDate,\n" +
            "DATE_FORMAT(b.confirm_time,'%H%i%s') as updateTime,\n" +
            "'01' as status,\n" +
            "c.job_number as responNo,\n" +
            "c.full_name as responName,\n" +
            "'' as workMemo,\n" +
            "case when b.sort < 10 then concat('0', convert(b.sort, char)) else convert(b.sort, char) end as seqNo\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN safe_si_job_fire_approval b ON a.id = b.job_id\n" +
            "INNER JOIN human_hm_personnel c ON b.approval_person_id = c.id\n" +
            "where a.id=#{id}\n" +
            "union all\n" +
            "select\n" +
            "uuid() as uuid,\n" +
            "b.job_number as updateNo,\n" +
            "DATE_FORMAT(a.apply_time,'%Y%m%d') as updateDate,\n" +
            "DATE_FORMAT(a.apply_time,'%H%i%s') as updateTime,\n" +
            "'01' as `status`,\n" +
            "b.job_number as responNo,\n" +
            "b.full_name as responName,\n" +
            "'' as workMemo,\n" +
            "'00' as seqNo\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN human_hm_personnel b ON a.apply_person_id = b.id\n" +
            "where a.id=#{id}\n"
    )
    List<Map<String, Object>> getErpDataSafeJobFireConfirmList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-分析列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select\n" +
            "b.id as uuid,\n" +
            "a.id as parent_id,\n" +
            "'' as seq_no,\n" +
            "DATE_FORMAT(b.analysis_time,'%Y%m%d') as fire_date,\n" +
            "DATE_FORMAT(b.analysis_time,'%H%i%s') as fire_time,\n" +
            "b.analysis_area as fire_place,\n" +
            "b.co_density,\n" +
            "b.o2_density,\n" +
            "b.othre_density,\n" +
            "c.job_number as fire_no,\n" +
            "c.full_name as fire_name,\n" +
            "c.job_number as create_no,\n" +
            "DATE_FORMAT(b.analysis_time,'%Y%m%d') as create_date,\n" +
            "DATE_FORMAT(b.analysis_time,'%H%i%s') as create_time,\n" +
            "'' as update_no,\n" +
            "'' as update_date,\n" +
            "'' as update_time\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN safe_si_job_fire_analysis b ON a.id = b.job_id\n" +
            "INNER JOIN human_hm_personnel c ON b.person_id = c.id\n" +
            "where a.id=#{id}"
    )
    List<Map<String, Object>> getErpDataSafeJobFireAnalysisList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-作业人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select\n" +
            "b.id as uuid,\n" +
            "a.id as parent_id,\n" +
            "'' as seq_no,\n" +
            "case when b.type = 1 then ifnull(c.job_number,'') else '' end as fire_user_no,\n" +
            "case when b.type = 1 then b.person_name else '' end as fire_user_name,\n" +
            "'' as out_fire_user_no,\n" +
            "b.out_fire_user_name,\n" +
            "b.work_card_code as special_no,\n" +
            "'' as type,\n" +
            "'' as create_no,\n" +
            "'' as create_date,\n" +
            "'' as create_time,\n" +
            "'' as update_no,\n" +
            "'' as update_date,\n" +
            "'' as update_time\n" +
            "from safe_si_job_fire a \n" +
            "INNER JOIN safe_si_job_fire_persons b ON a.id = b.job_id\n" +
            "LEFT JOIN human_hm_personnel c ON b.person_id = c.id\n" +
            "where a.id=#{id}"
    )
    List<Map<String, Object>> getErpDataSafeJobFirePersonList(Map<String, Object> condition);

    /**
     * 动火安全作业证-报文-附件列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.appendix_id,b.file_name,b.suffix,b.relative_path from safe_si_job_fire_appendix a\n" +
            "INNER JOIN sys_fileresource b ON a.appendix_id = b.id\n" +
            "where a.job_id=#{id}"
    )
    List<Map<String, Object>> getErpDataSafeJobFireFileList(Map<String, Object> condition);
}
