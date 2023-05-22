import request from '@/utils/request'

// 查询应付参数维护列表
export function listPara(query) {
  return request({
    url: '/finance/ap/para/list',
    method: 'get',
    params: query
  })
}

// 查询应付参数维护详细
export function getPara(id) {
  return request({
    url: '/finance/ap/para/' + id,
    method: 'get'
  })
}

// 新增应付参数维护
export function addPara(data) {
  return request({
    url: '/finance/ap/para',
    method: 'post',
    data: data
  })
}

// 修改应付参数维护
export function updatePara(data) {
  return request({
    url: '/finance/ap/para',
    method: 'put',
    data: data
  })
}

// 删除应付参数维护
export function delPara(id) {
  return request({
    url: '/finance/ap/para/' + id,
    method: 'delete'
  })
}
