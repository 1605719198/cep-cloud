import request from '@/utils/request'

// 查询月出勤统计汇总列表
export function listAttendanceGather(query) {
  return request({
    url: '/human/attendanceGather/list',
    method: 'get',
    params: query
  })
}
