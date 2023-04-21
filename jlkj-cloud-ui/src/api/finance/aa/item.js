import request from '@/utils/request'

// 查询现金流量代码-对应会计科目列表
export function listItem(query) {
  return request({
    url: '/finance/item/list',
    method: 'get',
    params: query
  })
}

// 查询现金流量代码-对应会计科目详细
export function getItem(id) {
  return request({
    url: '/finance/item/' + id,
    method: 'get'
  })
}

// 新增现金流量代码-对应会计科目
export function addItem(data) {
  return request({
    url: '/finance/item',
    method: 'post',
    data: data
  })
}

// 修改现金流量代码-对应会计科目
export function updateItem(data) {
  return request({
    url: '/finance/item',
    method: 'put',
    data: data
  })
}

// 删除现金流量代码-对应会计科目
export function delItem(id) {
  return request({
    url: '/finance/item/' + id,
    method: 'delete'
  })
}
