import request from '@/utils/request'

// 查询人员分组列表
export function listGroup(query) {
  return request({
    url: '/human/group/list',
    method: 'get',
    params: query
  })
}

// 查询人员分组列表
export function listChoiceGroup(examCode) {
  return request({
    url: '/human/group/choicelist/' + examCode,
    method: 'get'
  })
}

// 查询人员分组详细
export function getGroup(groupId) {
  return request({
    url: '/human/group/' + groupId,
    method: 'get'
  })
}

// 新增人员分组
export function addGroup(data) {
  return request({
    url: '/human/group',
    method: 'post',
    data: data
  })
}

// 修改人员分组
export function updateGroup(data) {
  return request({
    url: '/human/group',
    method: 'put',
    data: data
  })
}

// 删除人员分组
export function delGroup(groupId) {
  return request({
    url: '/human/group/' + groupId,
    method: 'delete'
  })
}

// 导出人员分组
export function exportGroup(query) {
  return request({
    url: '/human/group/export',
    method: 'get',
    params: query
  })
}
