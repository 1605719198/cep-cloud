import request from '@/utils/request'

// 查询出勤身份列表
export function listPersonClassMaster(query) {
  return request({
    url: '/human/personClassMaster/list',
    method: 'get',
    params: query
  })
}

// 查询员工出勤身份轮班方式
export function getClassMasterByPerson(query) {
  return request({
    url: '/human/personClassMaster/queryPerson',
    method: 'get',
    params: query
  })
}

// 查询出勤身份详细
export function getPersonClassMaster(id) {
  return request({
    url: '/human/personClassMaster/' + id,
    method: 'get'
  })
}

// 新增出勤身份
export function addPersonClassMaster(data) {
  return request({
    url: '/human/personClassMaster',
    method: 'post',
    data: data
  })
}

// 修改出勤身份
export function updatePersonClassMaster(data) {
  return request({
    url: '/human/personClassMaster',
    method: 'put',
    data: data
  })
}

// 删除出勤身份
export function delPersonClassMaster(id) {
  return request({
    url: '/human/personClassMaster/' + id,
    method: 'delete'
  })
}
