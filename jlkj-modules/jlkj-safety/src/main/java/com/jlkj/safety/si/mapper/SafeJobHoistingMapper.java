package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.SafeSiJobHoisting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 4.吊装安全作业证 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobHoistingMapper extends BaseMapper<SafeSiJobHoisting> {

    /**
     * 作业证编号
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            " select max(job_code) as job_code" +
                    " from safe_si_job_hoisting" +
                    " where job_code like concat(#{codeHeader}, '%')"
    )
    Map<String, Object> getMaxJobCode(Map<String, Object> condition);

    /**
     * 吊装安全作业证-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
                    "safe_area_id,safe_area_name,place,hoisting_tools,job_content,weight,\n" +
                    "DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') as start_time,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time,\n" +
                    "guardian_person_id,guardian_person_name,\n" +
                    "hoist_charge_id,hoist_charge_name,\n" +
                    "safe_education_persons,harm_identification,special_name,\n" +
                    "status,case status when 0 then '资料建立' when 1 then '审批中' when 2 then '作业中' when 3 then '作业完成' else '' end as status_text,\n" +
                    "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout,hoist_name,job_level\n" +
                    "from safe_si_job_hoisting\n" +
                    "where id=#{id}"
    )
    Map<String, Object> getSafeJobHoistingInfo(Map<String, Object> condition);

    /**
     * 吊装安全作业证-作业人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,construction_unit_id,construction_unit_name,type,person_id,person_name,work_type_name,work_card_code,person_type,case person_type when 0 then '吊装人员' when 1 then '吊装指挥' else '' end as person_type_text from safe_si_job_hoisting_persons\n" +
                    "where job_id=#{id}\n" +
                    "order by id"
    )
    List<Map<String, Object>> getSafeJobHoistingPersonsList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-安全措施列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,\n" +
                    "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time\n" +
                    "from safe_si_job_hoisting_safety_measures\n" +
                    "where job_id=#{id}\n" +
                    "order by sort"
    )
    List<Map<String, Object>> getSafeJobHoistingSafetyMeasuresList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-审批人员列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,approval_name,approval_person_id,approval_person_name,is_required,content,is_approval,sort,pick_person_id,pick_person_name,\n" +
                    "case when ISNULL(approval_time) then '' else DATE_FORMAT(approval_time,'%Y-%m-%d %H:%i:%s') end as approval_time,\n" +
                    "case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d %H:%i:%s') end as confirm_time,signature\n" +
                    "from safe_si_job_hoisting_approval\n" +
                    "where job_id=#{id}\n" +
                    "order by sort"
    )
    List<Map<String, Object>> getSafeJobHoistingApprovalList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select id,apply_depart_id,apply_depart_name,apply_person_id,apply_person_name,job_code, \n" +
                    "safe_area_id,safe_area_name,place,hoisting_tools,job_content,weight,\n" +
                    "DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') as start_time,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time,\n" +
                    "guardian_person_id,guardian_person_name,\n" +
                    "harm_identification,safe_education_persons,special_name,job_code_erp,\n" +
                    "status,case status when 0 then '资料建立' when 1 then '审批中' when 2 then '作业中' when 3 then '作业完成' else '' end as status_text,\n" +
                    "case when status = 2 and finish_time > end_time then '已超时' when status < 2 and now() > end_time then '已超时' else '未超时' end as is_timeout,hoist_name,job_level,hoist_charge_id,hoist_charge_name\n" +
                    "from safe_si_job_hoisting\n" +
                    "where\n" +
                    "(#{condition.start_time} = '' or start_time >= case when #{condition.start_time} = '' then null else #{condition.start_time} end) and\n" +
                    "(#{condition.end_time} = '' or start_time <= case when #{condition.end_time} = '' then null else #{condition.end_time} end) and\n" +
                    "(#{condition.status} = -1 or status = #{condition.status}) and\n" +
                    "(#{condition.job_level} = '' or job_level like concat('%',#{condition.job_level},'%')) and\n" +
                    "(#{condition.safe_area_id} = '' or safe_area_id = #{condition.safe_area_id}) and\n" +
                    "(#{condition.apply_person_id} = '' or apply_person_id = #{condition.apply_person_id}) \n" +
                    "order by ${condition.order} ${condition.orderby}"
    )
    IPage<Map<String, Object>> getSafeJobHoistingPageList(Page<?> page,@Param("condition") Map<String, Object> condition);

    /**
     * 吊装安全作业证-增加安全措施
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_hoisting_safety_measures(id,job_id,sort,safety_measures,is_confirm,confirm_person_id,confirm_person_name,confirm_time)\n" +
                    "select uuid(),#{id},sort,safety_measures,0,0,'',null from v_safe_job_hoisting_measures"
    )
    int insertSafeJobHoistingSafetyMeasures(Map<String, Object> condition);

    /**
     * 吊装安全作业证-增加审批人
     * @param condition 数据
     * @return 结果
     */
    @Insert(
            "insert into safe_si_job_hoisting_approval(id,job_id,sort,approval_name,approval_person_id,approval_person_name,is_required,content,approval_time,is_approval,pick_person_id,pick_person_name,pick_time)\n" +
                    "select uuid(),#{id},sort,approval_name,0,'',is_required,'',null,0,0,'',null from v_safe_job_hoisting_approval"
    )
    int insertSafeJobHoistingApproval(Map<String, Object> condition);

    /**
     * 吊装安全作业证-删除作业人员
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_hoisting_persons where job_id=#{id}"
    )
    int deleteSafeJobHoistingPersons(Map<String, Object> condition);

    /**
     * 吊装安全作业证-删除安全措施
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_hoisting_safety_measures where job_id=#{id}"
    )
    int deleteSafeJobHoistingSafetyMeasures(Map<String, Object> condition);

    /**
     * 吊装安全作业证-删除审批人
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_hoisting_approval where job_id=#{id}"
    )
    int deleteSafeJobHoistingApproval(Map<String, Object> condition);

    /**
     * 吊装安全作业证-审批人模板列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select * from v_safe_job_hoisting_approval\n" +
                    "where job_level=#{job_level}\n" +
                    "order by sort"
    )
    List<Map<String, Object>> getSafeJobHoistingApprovalTemplateList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-删除附件
     * @param condition 数据
     * @return 结果
     */
    @Delete(
            "delete from safe_si_job_hoisting_appendix where job_id=#{id}"
    )
    int deleteSafeJobHoistingFiles(Map<String, Object> condition);

    /**
     * 吊装安全作业证-附件列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select a.appendix_id,b.file_name from safe_si_job_hoisting_appendix a\n" +
                    "INNER JOIN sys_fileresource b ON a.appendix_id = b.id\n" +
                    "where a.job_id=#{id}"
    )
    List<Map<String, Object>> getSafeJobHoistingFileList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-报文-信息
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select \n" +
                    "a.id as uuid,\n" +
                    "a.job_code as hoist_no,\n" +
                    "m.organization_code as hoist_dep_no,\n" +
                    "a.apply_depart_name as hoist_dep_no_ch,\n" +
                    "a.place as hoist_place,\n" +
                    "a.hoist_name,\n" +
                    "a.job_content as hoist_memo,\n" +
                    "b.job_number as hoist_charge_no,\n" +
                    "b.full_name as hoist_charge_name,\n" +
                    "c.job_number as custodian_no,\n" +
                    "c.full_name as custodian_name,\n" +
                    "a.hoisting_tools as hoist_use_name,\n" +
                    "a.weight as hoist_wgt,\n" +
                    "d.special_no,\n" +
                    "d.hoist_user_no,\n" +
                    "d.hoist_user_name,\n" +
                    "e.special_comr_no,\n" +
                    "e.hoist_comr_no,\n" +
                    "e.hoist_com_name,\n" +
                    "a.special_name as educ_comr_no,\n" +
                    "'' as educ_com_name,\n" +
                    "g.hoist_danger,\n" +
                    "h.hoist_respon_no,\n" +
                    "h.hoist_respon_name,\n" +
                    "i.handover_no,\n" +
                    "i.handover_name,\n" +
                    "case when ISNULL(j.security_emp_no) then '' else j.security_emp_no end as security_emp_no,\n" +
                    "case when ISNULL(j.security_emp_name) then '' else j.security_emp_name end as security_emp_name,\n" +
                    "case when ISNULL(k.dep_boss_no) then '' else k.dep_boss_no end as dep_boss_no,\n" +
                    "case when ISNULL(k.dep_boss_name) then '' else k.dep_boss_name end as dep_boss_name,\n" +
                    "'' as hois_plan_date,\n" +
                    "'' as hois_plan_time,\n" +
                    "DATE_FORMAT(a.end_time,'%Y%m%d') as hoist_date_end,\n" +
                    "DATE_FORMAT(a.end_time,'%H%i%s') as hoist_time_end,\n" +
                    "DATE_FORMAT(a.start_time,'%Y%m%d') as hoist_date_start,\n" +
                    "DATE_FORMAT(a.start_time,'%H%i%s') as hoist_time_start,\n" +
                    "'' as f_do_date_start,\n" +
                    "'' as f_do_time_start,\n" +
                    "'' as hoist_grade,\n" +
                    "'' as `status`,\n" +
                    "l.job_number as create_no,\n" +
                    "DATE_FORMAT(a.apply_time,'%Y%m%d') as create_date,\n" +
                    "DATE_FORMAT(a.apply_time,'%H%i%s') as create_time,\n" +
                    "'' as update_no,\n" +
                    "'' as update_date,\n" +
                    "'' as update_time,\n" +
                    "'' as seq_no\n" +
                    "from safe_si_job_hoisting a\n" +
                    "INNER JOIN human_hm_personnel b ON a.id=#{id} and a.hoist_charge_id = b.id\n" +
                    "INNER JOIN human_hm_personnel c ON a.id=#{id} and a.guardian_person_id = c.id\n" +
                    "INNER JOIN (\n" +
                    "select a.job_id,a.work_card_code as special_no,ifnull(b.job_number,'') as hoist_user_no,a.person_name as hoist_user_name from safe_si_job_hoisting_persons a LEFT JOIN human_hm_personnel b ON a.person_id = b.id \n" +
                    "where a.job_id=#{id} and a.person_type = 0 limit 1\n" +
                    ") as d ON a.id = d.job_id\n" +
                    "INNER JOIN (\n" +
                    "select a.job_id,a.work_card_code as special_comr_no,ifnull(b.job_number,'') as hoist_comr_no,a.person_name as hoist_com_name from safe_si_job_hoisting_persons a LEFT JOIN human_hm_personnel b ON a.person_id = b.id \n" +
                    "where a.job_id=#{id} and a.person_type = 1 limit 1\n" +
                    ") as e ON a.id = e.job_id\n" +
                    "INNER JOIN (\n" +
                    "select a.id as job_id,GROUP_CONCAT(DISTINCT b.danger_code ORDER BY b.danger_code SEPARATOR '|') as hoist_danger from safe_si_job_hoisting a,v_safe_job_hoisting_harm_identification b\n" +
                    "where a.id=#{id} and ((a.harm_identification like concat(b.danger_name,'|%'))\n" +
                    "or (a.harm_identification like concat('%|',b.danger_name,'|%'))\n" +
                    "or (a.harm_identification like concat('%|',b.danger_name))\n" +
                    "or (a.harm_identification = b.danger_name))\n" +
                    "group by a.id\n" +
                    ") as g ON a.id = g.job_id\n" +
                    "INNER JOIN (\n" +
                    "select a.job_id,b.job_number as hoist_respon_no,b.full_name as hoist_respon_name from safe_si_job_hoisting_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
                    "where a.job_id=#{id} and a.approval_name='作业单位负责人意见：'\n" +
                    ") as h ON a.id = h.job_id\n" +
                    "INNER JOIN (\n" +
                    "select a.job_id,b.job_number as handover_no,b.full_name as handover_name from safe_si_job_hoisting_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
                    "where a.job_id=#{id} and a.approval_name='作业单位安全工程师意见：'\n" +
                    ") as i ON a.id = i.job_id\n" +
                    "LEFT JOIN (\n" +
                    "select a.job_id,b.job_number as security_emp_no,b.full_name as security_emp_name from safe_si_job_hoisting_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
                    "where a.job_id=#{id} and a.approval_name='安保处专工意见：'\n" +
                    ") as j ON a.id = j.job_id\n" +
                    "LEFT JOIN (\n" +
                    "select a.job_id,b.job_number as dep_boss_no,b.full_name as dep_boss_name from safe_si_job_hoisting_approval a INNER JOIN human_hm_personnel b ON a.approval_person_id = b.id \n" +
                    "where a.job_id=#{id} and a.approval_name='所属单位一级主管意见：'\n" +
                    ") as k ON a.id = k.job_id\n" +
                    "INNER JOIN human_hm_personnel l ON a.id=#{id} and a.apply_person_id = l.id\n" +
                    "INNER JOIN human_hm_organization m ON a.apply_depart_id = m.id\n"
    )
    Map<String, Object> getErpDataSafeJobHoistingInfo(Map<String, Object> condition);

    /**
     * 吊装安全作业证-报文-安全措施列表
     * @param condition 查询条件
     * @return 结果
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
                    "from safe_si_job_hoisting a \n" +
                    "INNER JOIN safe_si_job_hoisting_safety_measures b ON a.id = b.job_id\n" +
                    "INNER JOIN human_hm_personnel c ON a.apply_person_id = c.id\n" +
                    "LEFT JOIN human_hm_personnel d ON b.confirm_person_id = d.id\n" +
                    "where a.id=#{id}"
    )
    List<Map<String, Object>> getErpDataSafeJobHoistingSafetyMeasuresList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-报文-作业级别列表
     * @param condition 查询条件
     * @return 结果
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
                    "d.seq_no as is_grade\n" +
                    "from safe_si_job_hoisting a, (select aa.*,case when ISNULL(cc.seq_no) then '_n' else '_y' end as is_use\n" +
                    "from v_safe_job_hoisting_job_level aa\n" +
                    "LEFT JOIN (select b.* from safe_si_job_hoisting a, v_safe_job_hoisting_job_level b\n" +
                    "where a.id=#{id} and ((a.job_level like concat(b.job_level,',%'))\n" +
                    "or (a.job_level like concat('%,',b.job_level,',%'))\n" +
                    "or (a.job_level like concat('%,',b.job_level))\n" +
                    "or (a.job_level = b.job_level))) cc ON aa.seq_no = cc.seq_no) b, human_hm_personnel c, v_safe_job_hoisting_job_level d\n" +
                    "where a.id=#{id} and a.apply_person_id = c.id and a.job_level = d.job_level"
    )
    List<Map<String, Object>> getErpDataSafeJobHoistingJobLevelList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-报文-审批人列表
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select * from (\n" +
                    "select\n" +
                    "b.id as id,\n" +
                    "'N' as actionCode,\n" +
                    "case when b.sort < 10 then concat('0', convert(b.sort, char)) else convert(b.sort, char) end as chkNo,\n" +
                    "case when b.content = '' then '同意' else b.content end as content,\n" +
                    "case when b.approval_name = '完工验收，作业负责人：' then '03' else '02' end as `status`,\n" +
                    "'' as receiveDate,\n" +
                    "'' as receiveTime,\n" +
                    "DATE_FORMAT(b.approval_time,'%Y%m%d') as dealDate,\n" +
                    "DATE_FORMAT(b.approval_time,'%H%i%s') as dealTime,\n" +
                    "c.job_number as dealEmpno\n" +
                    "from safe_si_job_hoisting a \n" +
                    "INNER JOIN safe_si_job_hoisting_approval b ON a.id = b.job_id\n" +
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
                    "from safe_si_job_hoisting a \n" +
                    "INNER JOIN human_hm_personnel b ON a.apply_person_id = b.id\n" +
                    "where a.id=#{id}\n" +
                    ") as a order by chkNo\n"
    )
    List<Map<String, Object>> getErpDataSafeJobHoistingApprovalList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-报文-审批确认列表
     * @param condition 查询条件
     * @return 结果
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
                    "from safe_si_job_hoisting a \n" +
                    "INNER JOIN safe_si_job_hoisting_approval b ON a.id = b.job_id\n" +
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
                    "from safe_si_job_hoisting a \n" +
                    "INNER JOIN human_hm_personnel b ON a.apply_person_id = b.id\n" +
                    "where a.id=#{id}\n"
    )
    List<Map<String, Object>> getErpDataSafeJobHoistingConfirmList(Map<String, Object> condition);

    /**
     * 吊装安全作业证-报文-附件列表
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select a.appendix_id,b.file_name,b.suffix,b.relative_path from safe_si_job_hoisting_appendix a\n" +
                    "INNER JOIN sys_fileresource b ON a.appendix_id = b.id\n" +
                    "where a.job_id=#{id}"
    )
    List<Map<String, Object>> getErpDataSafeJobHoistingFileList(Map<String, Object> condition);
}
