import request from '@/utils/request'

// 查询现金流量代码公司级-对应会计科目列表
export function listItemCompid(query) {
  return request({
    url: '/finance/itemCompid/list',
    method: 'get',
    params: query
  })
}

// 查询现金流量代码公司级-对应会计科目详细
export function getItemCompid(id) {
  return request({
    url: '/finance/itemCompid/' + id,
    method: 'get'
  })
}

// 新增现金流量代码公司级-对应会计科目
export function addItemCompid(data) {
  return request({
    url: '/finance/itemCompid',
    method: 'post',
    data: data
  })
}

// 修改现金流量代码公司级-对应会计科目
export function updateItemCompid(data) {
  return request({
    url: '/finance/itemCompid',
    method: 'put',
    data: data
  })
}

// 删除现金流量代码公司级-对应会计科目
export function delItemCompid(id) {
  return request({
    url: '/finance/itemCompid/' + id,
    method: 'delete'
  })
}
