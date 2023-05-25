import request from '@/utils/request'

// 查询流程实例详情信息
export function getDetailInstance(query) {
  return request({
    url: '/flowable/workflow/instance/detail',
    method: 'get',
    params: query
  })
}
// 查询流程实例详情信息
export function getHisByProcInsId(procInsId) {
  return request({
    url: `/flowable/workflow/instance/getHisByProcInsId/${procInsId}`,
    method: 'get',
  })
}
