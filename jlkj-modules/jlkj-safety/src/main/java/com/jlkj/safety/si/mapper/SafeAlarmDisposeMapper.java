package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.safety.si.entity.Empty;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * <p>
 * 安全报警处理 Mapper 接口
 * </p>
 *
 * @author cy
 * @since 2021-04-25
 */
public interface SafeAlarmDisposeMapper extends BaseMapper<Empty> {

    /**
     * 安全报警处理-查询列表
     * @param page 分页
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "<script>"
            + "select id as gasalarm_id,isnull(alarm_local,'') as alarm_local,isnull(device_position,'') as device_position," +
            "isnull(device_index,'') as device_index,isnull(alarm_value,'') as alarm_value,DATE_FORMAT(alarm_time,'%Y-%m-%d') as alarm_time," +
            "isnull(notifier_id,0) as notifier_id,isnull(notifier_name,'') as notifier_name,alarm_status, " +
            "isnull(disposer_id,0) as disposer_id,isnull(disposer_name,'') as disposer_name," +
            "isnull(confirm_person_id,0) as confirm_person_id,isnull(confirm_person_name,'') as confirm_person_name " +
            " from T_SE_Alarm_Dispose where alarm_status in (1,2,3) " +
            "<if test=\"condition.alarm_status != null and condition.alarm_status != '' \">" +
            "<if test=\"condition.alarm_status == 2 or condition.alarm_status == '2' \">" +
            " and disposer_id = #{condition.user_id} and alarm_status = 2 " +
            "</if>" +
            "<if test=\"condition.alarm_status == 3 or condition.alarm_status == '3' \">" +
            " and confirm_person_id = #{condition.user_id} and alarm_status = 3 " +
            "</if>" +
            "</if>" +
            " order by alarm_time desc" +
             "</script>"
    )
    IPage<Map<String, Object>> getSafeAlarmDisposePageList(Page<?> page, @Param("condition")Map<String, Object> condition);

    /**
     * 安全报警处理-详情
     * @param condition 查询条件
     * @return 结果
     */
    @Select(
            "select top 1 id as gasalarm_id,isnull(alarm_local,'') as alarm_local,isnull(device_position,'') as device_position," +
                    "isnull(device_index,'') as device_index,isnull(alarm_value,'') as alarm_value,DATE_FORMAT(alarm_time,'%Y-%m-%d') as alarm_time," +
                    "isnull(notifier_id,0) as notifier_id,isnull(notifier_name,'') as notifier_name,alarm_status, " +
                    "isnull(disposer_id,0) as disposer_id,isnull(disposer_name,'') as disposer_name," +
                    "isnull(confirm_person_id,0) as confirm_person_id,isnull(confirm_person_name,'') as confirm_person_name," +
                    "case when ISNULL(dispose_time) then '' else DATE_FORMAT(dispose_time,'%Y-%m-%d') end as dispose_time,case when ISNULL(confirm_time) then '' else DATE_FORMAT(confirm_time,'%Y-%m-%d') end as confirm_time," +
                    "isnull(alarm_case,'') as alarm_case,isnull(dispose_measure,'') as dispose_measure," +
                    "case when ISNULL(return_normal_time) then '' else DATE_FORMAT(return_normal_time,'%Y-%m-%d') end as return_normal_time" +
                    " from T_SE_Alarm_Dispose where alarm_status in (1,2,3) and id = #{gasalarm_id}"

    )
    Map<String, Object> getSafeAlarmDisposeById(Map<String, Object> condition);

    /**
     * 安全报警处理-上报新数据
     * @param condition 数据
     * @return 返回值
     */
    @Insert("insert into T_SE_Alarm_Dispose([alarm_time],[alarm_local],[device_position],[device_index],[notifier_id]"
            + ",[notifier_name],[alarm_value],[disposer_id],[disposer_name],[confirm_person_id],[confirm_person_name],[alarm_status]) \n"
            + " values(#{alarm_time},#{alarm_local},#{device_position},#{device_index},#{notifier_id}"
            + ",#{notifier_name},#{alarm_value},#{disposer_id},#{disposer_name},#{confirm_person_id},#{confirm_person_name},'2')")
    int addNewSafeAlarmDispose(Map<String, Object> condition);

    /**
     * 安全报警处理-上报自动采集数据
     * @param condition 数据
     * @return 返回值
     */
    @Update("<script> update T_SE_Alarm_Dispose set [alarm_status] = 2,[notifier_name] = '自动采集',[disposer_id] = #{disposer_id},[disposer_name] = #{disposer_name}"
            + "<if test=\"confirm_person_id != null and confirm_person_id != '' \"> ,[confirm_person_id] = #{confirm_person_id} </if>"
            + "<if test=\"confirm_person_name != null and confirm_person_name != '' \"> ,[confirm_person_name] = #{confirm_person_name} </if>"
            + " where id = #{gasalarm_id} </script>")
    int addAutoSafeAlarmDispose(Map<String, Object> condition);

    /**
     * 安全报警处理-上传处理
     * @param condition 数据
     * @return 返回值
     */
    @Update("<script> update T_SE_Alarm_Dispose set [alarm_status] = 3,[dispose_time] = GETDATE(),[alarm_case] = #{alarm_case},[dispose_measure] = #{dispose_measure}"
            + "<if test=\"confirm_person_id != null and confirm_person_id != '' \"> ,[confirm_person_id] = #{confirm_person_id} </if>"
            + "<if test=\"confirm_person_name != null and confirm_person_name != '' \"> ,[confirm_person_name] = #{confirm_person_name} </if>"
            + " where id = #{gasalarm_id} </script>")
    int doDisposeSafeAlarmDispose(Map<String, Object> condition);

    /**
     * 安全报警处理-上传确认
     * @param condition 数据
     * @return 返回值
     */
    @Update("update T_SE_Alarm_Dispose set [alarm_status] = 4,[confirm_time] = now(),[return_normal_time] = #{return_normal_time} "
            + " where id = #{gasalarm_id}")
    int doConfirmSafeAlarmDispose(Map<String, Object> condition);

}
