import request from '@/utils/request'

// 查询集团管理-报表内容资料列表
export function listProjectContent(query) {
  return request({
    url: '/finance/projectContent/list',
    method: 'get',
    params: query
  })
}

// 查询集团管理-报表内容资料详细
export function getProjectContent(id) {
  return request({
    url: '/finance/projectContent/' + id,
    method: 'get'
  })
}

// 新增集团管理-报表内容资料
export function addProjectContent(data) {
  return request({
    url: '/finance/projectContent',
    method: 'post',
    data: data
  })
}

// 修改集团管理-报表内容资料
export function updateProjectContent(data) {
  return request({
    url: '/finance/projectContent',
    method: 'put',
    data: data
  })
}

// 删除集团管理-报表内容资料
export function delProjectContent(id) {
  return request({
    url: '/finance/projectContent/' + id,
    method: 'delete'
  })
}

// 状态修改
export function changeUserStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/finance/projectContent/changeStatus',
    method: 'put',
    data: data
  })
}
