import request from '@/utils/request'

// 查询月出勤统计汇总列表
export function listAttendanceGather(query) {
  return request({
    url: '/human/attendanceGather/list',
    method: 'get',
    params: query
  })
}

// 查询月出勤统计汇总列表
export function listAttendance(query) {
  return request({
    url: '/human/attendanceGather/attendanceList',
    method: 'get',
    params: query
  })
}

// 取消排班按钮操作
export function cancelScheduling(query) {
  return request({
    url: '/human/attendanceGather/cancelScheduling',
    method: 'post',
    data: query
  })
}

// 反（取消排班）按钮操作
export function oppositeCancelScheduling(query) {
  return request({
    url: '/human/attendanceGather/oppositeCancelScheduling',
    method: 'post',
    data: query
  })
}

// 确认按钮操作
export function confirm(query) {
  return request({
    url: '/human/attendanceGather/confirm',
    method: 'post',
    data: query
  })
}

// 反确认（考勤）按钮操作
export function counterConfirmation(query) {
  return request({
    url: '/human/attendanceGather/counterConfirmation',
    method: 'post',
    data: query
  })
}

// 确认（可薪资计算）按钮操作
export function confirmSalaryCalculation(query) {
  return request({
    url: '/human/attendanceGather/confirmSalaryCalculation',
    method: 'post',
    data: query
  })
}

// 反确认（可薪资计算）按钮操作
export function counterConfirmationSalaryCalculation(query) {
  return request({
    url: '/human/attendanceGather/counterConfirmationSalaryCalculation',
    method: 'post',
    data: query
  })
}
