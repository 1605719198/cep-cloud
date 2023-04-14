import request from '@/utils/request'

// 查询会计系统参数维护列表
export function listPara(query) {
  return request({
    url: '/finance/para/list',
    method: 'get',
    params: query
  })
}

// 查询会计系统参数维护详细
export function getPara(id) {
  return request({
    url: '/finance/para/' + id,
    method: 'get'
  })
}

// 新增会计系统参数维护
export function addPara(data) {
  return request({
    url: '/finance/para',
    method: 'post',
    data: data
  })
}

// 修改会计系统参数维护
export function updatePara(data) {
  return request({
    url: '/finance/para',
    method: 'put',
    data: data
  })
}

// 删除会计系统参数维护
export function delPara(id) {
  return request({
    url: '/finance/para/' + id,
    method: 'delete'
  })
}
