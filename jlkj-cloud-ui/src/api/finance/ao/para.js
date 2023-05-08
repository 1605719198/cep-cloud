import request from '@/utils/request'

// 查询报支参数维护列表
export function listPara(query) {
  return request({
    url: '/finance/ao/para/list',
    method: 'get',
    params: query
  })
}

// 查询报支参数维护详细
export function getPara(id) {
  return request({
    url: '/finance/ao/para/' + id,
    method: 'get'
  })
}

// 新增报支参数维护
export function addPara(data) {
  return request({
    url: '/finance/ao/para',
    method: 'post',
    data: data
  })
}

// 修改报支参数维护
export function updatePara(data) {
  return request({
    url: '/finance/ao/para',
    method: 'put',
    data: data
  })
}

// 删除报支参数维护
export function delPara(id) {
  return request({
    url: '/finance/ao/para/' + id,
    method: 'delete'
  })
}

// 刷新参数缓存
export function refreshCache() {
  return request({
    url: '/finance/ao/para/refreshCache',
    method: 'delete'
  })
}
