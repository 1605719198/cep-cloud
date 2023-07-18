package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.AttendanceAbnormal;
import com.jlkj.human.hd.dto.AttendanceAbnormalDTO;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_attendance_abnormal(出勤异常表)】的数据库操作Service
* @createDate 2023-04-07 09:28:34
*/
public interface IAttendanceAbnormalService extends IService<AttendanceAbnormal> {

    /**
     * 查询出勤异常表接口
     * @author HuangBing
     * @date 2023-07-15
     * @param attendanceAbnormalDTO 查询参数集
     * @return 应出勤开始时间与参数时间同月的数据
     */
    List<AttendanceAbnormal> queryAttendanceAbnormal(AttendanceAbnormalDTO attendanceAbnormalDTO);

    /**
     * 定时转旷工
     * @author HuangBing
     * @date 2023-07-14
     * @return 结果
     */
    boolean absenteeism();
}
