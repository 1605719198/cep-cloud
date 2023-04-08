import request from '@/utils/request'

// 查询加班补休参数设定列表
export function listOvertimeWorksetting(query) {
  return request({
    url: '/human/overtimeWorksetting/list',
    method: 'get',
    params: query
  })
}

// 查询加班补休参数设定详细
export function getOvertimeWorksetting(id) {
  return request({
    url: '/human/overtimeWorksetting/' + id,
    method: 'get'
  })
}

// 新增加班补休参数设定
export function addOvertimeWorksetting(data) {
  return request({
    url: '/human/overtimeWorksetting',
    method: 'post',
    data: data
  })
}

// 修改加班补休参数设定
export function updateOvertimeWorksetting(data) {
  return request({
    url: '/human/overtimeWorksetting',
    method: 'put',
    data: data
  })
}

// 删除加班补休参数设定
export function delOvertimeWorksetting(id) {
  return request({
    url: '/human/overtimeWorksetting/' + id,
    method: 'delete'
  })
}
