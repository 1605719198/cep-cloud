import request from '@/utils/request'

// 查询公司薪资计算参数列表
export function listPayFormulaParam(query) {
  return request({
    url: '/human/payFormulaParam/list',
    method: 'get',
    params: query
  })
}

// 保存公司薪资计算参数
export function savePayFormulaParam(data) {
  return request({
    url: '/human/payFormulaParam/save',
    method: 'post',
    data: data
  })
}

// 修改公司薪资计算参数
export function copyPayFormulaParam(data) {
  return request({
    url: '/human/payFormulaParam/copy',
    method: 'post',
    data: data
  })
}

