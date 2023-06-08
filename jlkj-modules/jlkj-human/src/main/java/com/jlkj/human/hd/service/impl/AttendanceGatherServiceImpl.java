package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;
import com.jlkj.human.hd.mapper.AttendanceGatherMapper;
import com.jlkj.human.hd.service.IAttendanceGatherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 89373
* @description 针对表【human_hd_attendance_gather(月出勤统计汇总表)】的数据库操作Service实现
* @createDate 2023-04-06 09:15:10
*/
@Service
public class AttendanceGatherServiceImpl extends ServiceImpl<AttendanceGatherMapper, AttendanceGather>
    implements IAttendanceGatherService {

    @Resource
    AttendanceGatherMapper attendanceGatherMapper;

    /**
     * 连表查询加班/请假出勤汇总记录
     * @param attendanceGatherDTO
     * @return 结果
     */
    @Override
    public List<AttendanceGatherDTO> selectJoinList(AttendanceGatherDTO attendanceGatherDTO) {
        return attendanceGatherMapper.selectJoinList(attendanceGatherDTO);
    }

}




