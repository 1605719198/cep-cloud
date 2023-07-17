import request from '@/utils/request'

// 查询分组用户列表
export function listGroupperson(query) {
  return request({
    url: '/human/groupperson/list',
    method: 'get',
    params: query
  })
}

// 查询分组用户详细
export function getGroupperson(groupCode) {
  return request({
    url: '/human/groupperson/' + groupCode,
    method: 'get'
  })
}

// 新增分组用户
export function addGroupperson(data) {
  return request({
    url: '/human/groupperson',
    method: 'post',
    data: data
  })
}

// 修改分组用户
export function updateGroupperson(data) {
  return request({
    url: '/human/groupperson/update',
    method: 'post',
    data: data
  })
}

// 删除分组用户
export function delGroupperson(groupCode) {
  return request({
    url: '/human/groupperson/delete/' + groupCode,
    method: 'get'
  })
}

// 导出分组用户
export function exportGroupperson(query) {
  return request({
    url: '/human/groupperson/export',
    method: 'get',
    params: query
  })
}
