package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hd.domain.AttendanceAbnormal;
import com.jlkj.human.hd.dto.AttendanceAbnormalDTO;
import com.jlkj.human.hd.mapper.AttendanceAbnormalMapper;
import com.jlkj.human.hd.service.IAttendanceAbnormalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_attendance_abnormal(出勤异常表)】的数据库操作Service实现
* @createDate 2023-04-07 09:28:34
*/
@Service
public class AttendanceAbnormalServiceImpl extends ServiceImpl<AttendanceAbnormalMapper, AttendanceAbnormal>
    implements IAttendanceAbnormalService {

    /**
     * 查询出勤异常表接口
     * @author HuangBing
     * @date 2023-07-15
     * @param attendanceAbnormalDTO 查询参数集
     * @return 应出勤开始时间与参数时间同月的数据
     */
    @Override
    public List<AttendanceAbnormal> queryAttendanceAbnormal(AttendanceAbnormalDTO attendanceAbnormalDTO){
        List<AttendanceAbnormal> list = lambdaQuery()
                .eq(AttendanceAbnormal::getCompId, attendanceAbnormalDTO.getCompId())
                .eq(AttendanceAbnormal::getEmpNo, attendanceAbnormalDTO.getEmpNo())
                .like(AttendanceAbnormal::getNorOndutyBegin, attendanceAbnormalDTO.getIncomingTime()).list();
        return list;
    }

    /**
     * 定时转旷工
     * @author HuangBing
     * @date 2023-07-14
     * @return 结果
     */
    @Override
    public boolean absenteeism(){
        List<AttendanceAbnormal> attendanceAbnormalList = lambdaQuery()
                .and(i -> i.eq(AttendanceAbnormal::getDisposeId, Constants.STR_ZERO_NINE).or().eq(AttendanceAbnormal::getDisposeId, Constants.STR_ZERO_EIGHT))
                .like(AttendanceAbnormal::getNorOndutyBegin, DateUtils.getLastMonth())
                .list();
        for (AttendanceAbnormal item : attendanceAbnormalList) {
            item.setUndutyType(Constants.STR_THREE);
        }
        return updateBatchById(attendanceAbnormalList);
    }
}




