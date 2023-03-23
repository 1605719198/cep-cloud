package com.jlkj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.system.dto.user.GetPersonnelSchedulingDTO;
import com.jlkj.system.entity.HumanresourcePersonnel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【human_hm_personnel(人员数据)】的数据库操作Mapper
* @createDate 2022-06-09 14:45:48
* @Entity com.jlkj.system.entity.HumanresourcePersonnel
*/
public interface HumanresourcePersonnelMapper extends BaseMapper<HumanresourcePersonnel> {

    /**
     * 用户班组信息 - 根据工号查询
     * @param jobNumber
     * @return
     */
    @Select("SELECT b.post_id,b.post_name,shift,class_type,class_type_no," +
            "DATE_FORMAT(due_attendance_time_work,'%Y-%m-%d %H:%i:%s') AS due_attendance_time_work," +
            "DATE_FORMAT(due_attendance_time_offduty,'%Y-%m-%d %H:%i:%s') AS due_attendance_time_offduty, " +
            "CASE shift WHEN '01' THEN '常白班' WHEN '2201' THEN '白班' WHEN '2202' THEN '夜班' WHEN '00' THEN '休息' ELSE '' END AS shift_text " +
            "FROM human_hm_personnel_scheduling a " +
            "LEFT JOIN human_hm_perorg_tree b ON a.job_number = b.job_number " +
            "WHERE scheduling_date = DATE_FORMAT(NOW(), '%Y-%m-%d') and a.job_number = #{jobNumber} " +
            "LIMIT 1 ")
    List<Map<String, Object>> getTeamByJobNumber(String jobNumber);




    /**
     * 查询-分页-获取人员排班信息列表
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPersonnelScheduling(Page<?> page, GetPersonnelSchedulingDTO condition);

}




