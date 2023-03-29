import request from '@/utils/request'

// 查询员工出勤有效记录资料列表
export function listAttendenceRecord(query) {
  return request({
    url: '/human/attendenceRecord/list',
    method: 'get',
    params: query
  })
}
