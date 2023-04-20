import request from '@/utils/request'

// 查询系统选单-索引设定列表
export function listIndex(query) {
  return request({
    url: '/finance/index/list',
    method: 'get',
    params: query
  })
}

// 查询系统选单-索引设定详细
export function getIndex(id) {
  return request({
    url: '/finance/index/' + id,
    method: 'get'
  })
}
// 获取全部树节点FinanceProduct
export function getTreeNode(companyId) {
  return request({
    url: `/finance/index/tree?companyId=${companyId}`,
    method: 'get',
  })
}
// 新增系统选单-索引设定
export function addIndex(data) {
  return request({
    url: '/finance/index',
    method: 'post',
    data: data
  })
}

// 修改系统选单-索引设定
export function updateIndex(data) {
  return request({
    url: '/finance/index',
    method: 'put',
    data: data
  })
}

// 删除系统选单-索引设定
export function delIndex(id) {
  return request({
    url: '/finance/index/' + id,
    method: 'delete'
  })
}
