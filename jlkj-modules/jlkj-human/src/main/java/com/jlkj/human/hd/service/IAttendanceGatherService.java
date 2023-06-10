package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;

import java.util.List;

/**
* @author 89373
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

}
