import request from '@/utils/request'

// 查询出勤异常统计列表
export function listAttendanceAbnormalStatistics(query) {
  return request({
    url: '/human/attendanceAbnormalStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询出勤异常统计详细
export function getAttendanceAbnormalStatistics(id) {
  return request({
    url: '/human/attendanceAbnormalStatistics/' + id,
    method: 'get'
  })
}

// 新增出勤异常统计
export function addAttendanceAbnormalStatistics(data) {
  return request({
    url: '/human/attendanceAbnormalStatistics',
    method: 'post',
    data: data
  })
}

// 修改出勤异常统计
export function updateAttendanceAbnormalStatistics(data) {
  return request({
    url: '/human/attendanceAbnormalStatistics',
    method: 'put',
    data: data
  })
}

// 删除出勤异常统计
export function delAttendanceAbnormalStatistics(id) {
  return request({
    url: '/human/attendanceAbnormalStatistics/' + id,
    method: 'delete'
  })
}
