import request from '@/utils/request'

// 查询出勤身份列表
export function listOrgClassMaster(query) {
  return request({
    url: '/human/orgClassMaster/list',
    method: 'get',
    params: query
  })
}

// 查询出勤身份详细
export function getOrgClassMaster(id) {
  return request({
    url: '/human/orgClassMaster/' + id,
    method: 'get'
  })
}

// 新增出勤身份
export function addOrgClassMaster(data) {
  return request({
    url: '/human/orgClassMaster',
    method: 'post',
    data: data
  })
}

// 修改出勤身份
export function updateOrgClassMaster(data) {
  return request({
    url: '/human/orgClassMaster',
    method: 'put',
    data: data
  })
}

// 删除出勤身份
export function delOrgClassMaster(id) {
  return request({
    url: '/human/orgClassMaster/' + id,
    method: 'delete'
  })
}
