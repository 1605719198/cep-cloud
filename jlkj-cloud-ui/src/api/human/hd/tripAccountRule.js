import request from '@/utils/request'

// 查询出差会计科目列表
export function listTripAccountRule(query) {
  return request({
    url: '/human/tripAccountRule/list',
    method: 'get',
    params: query
  })
}

// 查询出差会计科目详细
export function getTripAccountRule(id) {
  return request({
    url: '/human/tripAccountRule/' + id,
    method: 'get'
  })
}

// 新增出差会计科目
export function addTripAccountRule(data) {
  return request({
    url: '/human/tripAccountRule',
    method: 'post',
    data: data
  })
}

// 修改出差会计科目
export function updateTripAccountRule(data) {
  return request({
    url: '/human/tripAccountRule',
    method: 'put',
    data: data
  })
}

// 删除出差会计科目
export function delTripAccountRule(id) {
  return request({
    url: '/human/tripAccountRule/' + id,
    method: 'delete'
  })
}
