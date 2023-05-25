import request from '@/utils/request'

// 根据流程实例id查询绑定表单信息
export function getFromByInsId(query) {
  return request({
    url: '/flowable/workflow/instanceform/getFromByInsId',
    method: 'get',
    params: query
  })
}

// 根据表单id查询是否有绑定流程
export function getTaskByFormId(formId) {
  return request({
    url: `/flowable/workflow/instanceform/getTaskByFormId/${formId}`,
    method: 'get',
  })
}
