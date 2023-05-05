import request from '@/utils/request'

// 查询员工销假列表
export function listCancelHoliday(query) {
  return request({
    url: '/human/cancelHoliday/list',
    method: 'get',
    params: query
  })
}

// 查询员工销假详细
export function getCancelHoliday(id) {
  return request({
    url: '/human/cancelHoliday/' + id,
    method: 'get'
  })
}

// 新增员工销假
export function addCancelHoliday(data) {
  return request({
    url: '/human/cancelHoliday',
    method: 'post',
    data: data
  })
}

// 修改员工销假
export function updateCancelHoliday(data) {
  return request({
    url: '/human/cancelHoliday',
    method: 'put',
    data: data
  })
}

// 删除员工销假
export function delCancelHoliday(id) {
  return request({
    url: '/human/cancelHoliday/' + id,
    method: 'delete'
  })
}


// 撤回
export function withdrawCancelHoliday(id) {
  return request({
    url: '/human/cancelHoliday/withdraw',
    method: 'post',
    data: id
  })
}
