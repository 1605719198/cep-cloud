import request from '@/utils/request'

// 查询人员排班明细列表
export function listPersonClassDetail(query) {
  return request({
    url: '/human/personClassDetail/list',
    method: 'get',
    params: query
  })
}

// 排班操作
export function setPersonClassDetail(data) {
  return request({
    url: '/human/personClassDetail/setDetail',
    method: 'post',
    data: data
  })
}

// 调班操作
export function changePersonClassDetail(data) {
  return request({
    url: '/human/personClassDetail/changeDetail',
    method: 'post',
    data: data
  })
}

// 查询人员排班明细详细
export function getPersonClassDetail(id) {
  return request({
    url: '/human/personClassDetail/' + id,
    method: 'get'
  })
}

// 新增人员排班明细
export function addPersonClassDetail(data) {
  return request({
    url: '/human/personClassDetail',
    method: 'post',
    data: data
  })
}

// 修改人员排班明细
export function updatePersonClassDetail(data) {
  return request({
    url: '/human/personClassDetail',
    method: 'put',
    data: data
  })
}

// 删除人员排班明细
export function delPersonClassDetail(id) {
  return request({
    url: '/human/personClassDetail/' + id,
    method: 'delete'
  })
}
