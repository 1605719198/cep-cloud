import request from '@/utils/request'

// 查询印项目计算结果列表
export function listProjectResult(query) {
  return request({
    url: '/finance/projectResult/list',
    method: 'get',
    params: query
  })
}

// 查询印项目计算结果详细
export function getProjectResult(id) {
  return request({
    url: '/finance/projectResult/' + id,
    method: 'get'
  })
}

// 新增印项目计算结果
export function addProjectResult(data) {
  return request({
    url: '/finance/projectResult',
    method: 'post',
    data: data
  })
}

// 修改印项目计算结果
export function updateProjectResult(data) {
  return request({
    url: '/finance/projectResult',
    method: 'put',
    data: data
  })
}

// 删除印项目计算结果
export function delProjectResult(id) {
  return request({
    url: '/finance/projectResult/' + id,
    method: 'delete'
  })
}
