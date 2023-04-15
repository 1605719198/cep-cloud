import request from '@/utils/request'

// 查询金融机构管理分行列表
export function listBranch(query) {
  return request({
    url: '/finance/branch/list',
    method: 'get',
    params: query
  })
}

// 查询金融机构管理分行详细
export function getBranch(id) {
  return request({
    url: '/finance/branch/' + id,
    method: 'get'
  })
}

// 新增金融机构管理分行
export function addBranch(data) {
  return request({
    url: '/finance/branch',
    method: 'post',
    data: data
  })
}

// 修改金融机构管理分行
export function updateBranch(data) {
  return request({
    url: '/finance/branch',
    method: 'put',
    data: data
  })
}

// 删除金融机构管理分行
export function delBranch(id) {
  return request({
    url: '/finance/branch/' + id,
    method: 'delete'
  })
}
