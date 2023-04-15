import request from '@/utils/request'

// 查询金融机构管理列表
export function listBank(query) {
  return request({
    url: '/finance/bank/list',
    method: 'get',
    params: query
  })
}

// 查询金融机构管理详细
export function getBank(id) {
  return request({
    url: '/finance/bank/' + id,
    method: 'get'
  })
}

// 新增金融机构管理
export function addBank(data) {
  return request({
    url: '/finance/bank',
    method: 'post',
    data: data
  })
}

// 修改金融机构管理
export function updateBank(data) {
  return request({
    url: '/finance/bank',
    method: 'put',
    data: data
  })
}

// 删除金融机构管理
export function delBank(id) {
  return request({
    url: '/finance/bank/' + id,
    method: 'delete'
  })
}
