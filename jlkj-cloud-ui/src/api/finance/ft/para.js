import request from '@/utils/request'

// 查询固定资产参数维护列表
export function listPara(query) {
  return request({
    url: '/finance/ft/para/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产参数维护详细
export function getPara(id) {
  return request({
    url: '/finance/ft/para/' + id,
    method: 'get'
  })
}

// 新增固定资产参数维护
export function addPara(data) {
  return request({
    url: '/finance/ft/para',
    method: 'post',
    data: data
  })
}

// 修改固定资产参数维护
export function updatePara(data) {
  return request({
    url: '/finance/ft/para',
    method: 'put',
    data: data
  })
}

// 删除固定资产参数维护
export function delPara(id) {
  return request({
    url: '/finance/ft/para/' + id,
    method: 'delete'
  })
}
// 根据参数键名查询键值
export function getParamValue(paramKey) {
  return request({
    url: '/finance/ft/para/getParamValue/' + paramKey,
    method: 'get'
  })
}
