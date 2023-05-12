import request from '@/utils/request'

// 查询出勤异常列表
export function listAttendanceAbnormal(query) {
  return request({
    url: '/human/attendanceAbnormal/list',
    method: 'get',
    params: query
  })
}

// 查询个人出勤异常
export function listAttendanceAbnormalQuery(query) {
  return request({
    url: '/human/attendanceAbnormal/abnormal',
    method: 'get',
    params: query
  })
}

// 查询出勤异常详细
export function getAttendanceAbnormal(id) {
  return request({
    url: '/human/attendanceAbnormal/' + id,
    method: 'get'
  })
}

// 修改出勤异常
export function updateAttendanceAbnormal(data) {
  return request({
    url: '/human/attendanceAbnormal',
    method: 'put',
    data: data
  })
}

// 出勤异常申请送审
export function sendAttendanceAbnormal(data) {
  return request({
    url: '/human/attendanceAbnormal/send',
    method: 'post',
    data: data
  })
}

// 出勤异常申请撤回
export function recallAttendanceAbnormal(data) {
  return request({
    url: '/human/attendanceAbnormal/recall',
    method: 'post',
    data: data
  })
}

// 查询出勤异常列表
export function listAttendanceAnomalyConfirmationList(query) {
  return request({
    url: '/human/attendanceAbnormal/attendanceAnomalyConfirmationList',
    method: 'get',
    params: query
  })
}
