import request from '@/utils/request'

// 查询公司薪资计算参数列表
export function listPayFormulaParam(query) {
  return request({
    url: '/human/payFormulaParam/list',
    method: 'get',
    params: query
  })
}

// 查询公司薪资计算参数详细
export function getPayFormulaParam(uuid) {
  return request({
    url: '/human/payFormulaParam/' + uuid,
    method: 'get'
  })
}

// 新增公司薪资计算参数
export function addPayFormulaParam(data) {
  return request({
    url: '/human/payFormulaParam',
    method: 'post',
    data: data
  })
}

// 修改公司薪资计算参数
export function updatePayFormulaParam(data) {
  return request({
    url: '/human/payFormulaParam',
    method: 'put',
    data: data
  })
}

// 删除公司薪资计算参数
export function delPayFormulaParam(uuid) {
  return request({
    url: '/human/payFormulaParam/' + uuid,
    method: 'delete'
  })
}
