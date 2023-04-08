import request from '@/utils/request'

// 查询人员卡钟组织机构列表
export function listPersonColockOrg(query) {
  return request({
    url: '/human/personColockOrg/list',
    method: 'get',
    params: query
  })
}

// 查询人员卡钟组织机构详细
export function getPersonColockOrg(id) {
  return request({
    url: '/human/personColockOrg/' + id,
    method: 'get'
  })
}

// 新增人员卡钟组织机构
export function addPersonColockOrg(data) {
  return request({
    url: '/human/personColockOrg',
    method: 'post',
    data: data
  })
}

// 修改人员卡钟组织机构
export function updatePersonColockOrg(data) {
  return request({
    url: '/human/personColockOrg',
    method: 'put',
    data: data
  })
}

// 删除人员卡钟组织机构
export function delPersonColockOrg(id) {
  return request({
    url: '/human/personColockOrg/' + id,
    method: 'delete'
  })
}
