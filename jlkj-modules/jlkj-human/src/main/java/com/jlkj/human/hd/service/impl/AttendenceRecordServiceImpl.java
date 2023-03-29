package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hd.domain.AttendenceRecord;
import com.jlkj.human.hd.service.IAttendenceRecordService;
import com.jlkj.human.hd.mapper.AttendenceRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author 89373
* @description 针对表【human_hd_attendence_record(员工出勤有效记录资料表)】的数据库操作Service实现
* @createDate 2023-03-29 15:03:45
*/
@Service
public class AttendenceRecordServiceImpl extends ServiceImpl<AttendenceRecordMapper, AttendenceRecord>
    implements IAttendenceRecordService {

}




