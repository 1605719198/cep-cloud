import request from '@/utils/request'

// 查询排班明细列表
export function listArrangeClass(query) {
  return request({
    url: '/human/arrangeClass/list',
    method: 'get',
    params: query
  })
}

// 查询排班明细详细
export function getArrangeClass(id) {
  return request({
    url: '/human/arrangeClass/' + id,
    method: 'get'
  })
}

// 新增排班明细
export function addArrangeClass(data) {
  return request({
    url: '/human/arrangeClass',
    method: 'post',
    data: data
  })
}

// 修改排班明细
export function updateArrangeClass(data) {
  return request({
    url: '/human/arrangeClass',
    method: 'put',
    data: data
  })
}

// 删除排班明细
export function delArrangeClass(id) {
  return request({
    url: '/human/arrangeClass/' + id,
    method: 'delete'
  })
}
