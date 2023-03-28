import request from '@/utils/request'

// 查询劳动合同列表
export function listLaborContract(query) {
  return request({
    url: '/human/laborContract/list',
    method: 'get',
    params: query
  })
}

// 查询劳动合同详细
export function getLaborContract(uuid) {
  return request({
    url: '/human/laborContract/' + uuid,
    method: 'get'
  })
}

// 新增劳动合同
export function addLaborContract(data) {
  return request({
    url: '/human/laborContract',
    method: 'post',
    data: data
  })
}

// 修改劳动合同
export function updateLaborContract(data) {
  return request({
    url: '/human/laborContract',
    method: 'put',
    data: data
  })
}

// 删除劳动合同
export function delLaborContract(uuid) {
  return request({
    url: '/human/laborContract/' + uuid,
    method: 'delete'
  })
}
