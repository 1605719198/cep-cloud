import request from '@/utils/request'

// 查询劳动合同统计分析列表
export function listContractStatistics(query) {
  return request({
    url: '/human/contractStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询劳动合同统计分析详细
export function getContractStatistics(compId) {
  return request({
    url: '/human/contractStatistics/' + compId,
    method: 'get'
  })
}

// 新增劳动合同统计分析
export function addContractStatistics(data) {
  return request({
    url: '/human/contractStatistics',
    method: 'post',
    data: data
  })
}

// 修改劳动合同统计分析
export function updateContractStatistics(data) {
  return request({
    url: '/human/contractStatistics',
    method: 'put',
    data: data
  })
}

// 删除劳动合同统计分析
export function delContractStatistics(compId) {
  return request({
    url: '/human/contractStatistics/' + compId,
    method: 'delete'
  })
}
