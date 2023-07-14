package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_attendance_gather(月出勤统计汇总表)】的数据库操作Service
* @createDate 2023-04-06 09:15:10
*/
public interface IAttendanceGatherService extends IService<AttendanceGather> {

    /**
     * 连表查询加班和请假出勤汇总记录
     *
     * @param attendanceGatherDTO
     * @return 结果
     */
    List<AttendanceGatherDTO> selectJoinList(AttendanceGatherDTO attendanceGatherDTO);

    /**
     * 连表查询加班和请假出勤汇总记录
     *
     * @param attendanceGatherDTO
     * @return 结果
     */
    List<AttendanceGatherDTO> selectCompJoinList(AttendanceGatherDTO attendanceGatherDTO);

    /**
     * 导出出勤汇总表数据
     * @param attendanceGatherDTO
     * @param response
     * @return 结果
     */
    void exportExcel(HttpServletResponse response, AttendanceGatherDTO attendanceGatherDTO);

    /**
     * 下载出勤汇总表数据模板
     * @param response
     * @return 结果
     */
    void importTemplate(HttpServletResponse response);

    /**
     * 导入出勤汇总数据
     *
     * @param attendanceGatherList 出勤汇总数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<AttendanceGatherDTO> attendanceGatherList, Boolean isUpdateSupport, String operName);

    /**
     * 月初出勤汇总
     *
     * @return 出勤汇总结果
     * @author 266861
     * @Date 2023/7/12 13:34
     **/
    public int attendanceSummary();
}
