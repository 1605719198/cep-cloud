package com.jlkj.human.hd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;

import java.util.List;

/**
* @author 89373
* @description 针对表【human_hd_attendance_gather(月出勤统计汇总表)】的数据库操作Mapper
* @createDate 2023-04-06 09:15:10
* @Entity com.jlkj.human.hd.domain.AttendanceGather
*/
public interface AttendanceGatherMapper extends BaseMapper<AttendanceGather> {

    /**
     * 连表查询加班/请假出勤汇总记录
     * @param attendanceGatherDTO
     * @return 结果
     */
    public List<AttendanceGatherDTO> selectJoinList(AttendanceGatherDTO attendanceGatherDTO);

}




