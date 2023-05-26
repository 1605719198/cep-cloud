import request from '@/utils/request'

// 查询公司薪资参数设定列表
export function listPayParamStandard(query) {
  return request({
    url: '/human/payParamStandard/list',
    method: 'get',
    params: query
  })
}

// 新增公司薪资参数设定
export function addPayParamStandard(data) {
  return request({
    url: '/human/payParamStandard',
    method: 'post',
    data: data
  })
}

// 新增公司薪资参数设定
export function copyPayParamStandard(data) {
  return request({
    url: '/human/payParamStandard/copyPayParamStandard',
    method: 'post',
    data: data
  })
}
