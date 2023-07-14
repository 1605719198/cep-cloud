package com.jlkj.human.hd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author HuangBing
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

    /**
     * 连表查询加班/请假出勤汇总记录
     * @param attendanceGatherDTO
     * @return 结果
     */
    public List<AttendanceGatherDTO> selectCompJoinList(AttendanceGatherDTO attendanceGatherDTO);

    /**
     * 查询定时出勤汇总的人员
     *
     * @param attendanceGather 查询参数
     * @return 符合条件的人员列表
     * @author 266861
     * @Date 2023/7/13 9:28
     **/
    public List<AttendanceGather> selectSummaryEmp(AttendanceGather attendanceGather);

    /**
     * 批量汇总员工出勤数据
     *
     * @param list 员工出勤汇总数据汇总列表
     * @return 批量保存结果
     * @author 266861
     * @Date 2023/7/13 10:15
     **/
    public int batchSummaryData(@RequestBody List<AttendanceGather> list);

}




