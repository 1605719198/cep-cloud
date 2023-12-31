import request from '@/utils/request'

// 查询各公司主管查询下属薪资授权设定列表
export function listUnderlingEmpower(query) {
  return request({
    url: '/human/underlingEmpower/list',
    method: 'get',
    params: query
  })
}

// 新增各公司主管查询下属薪资授权设定
export function addUnderlingEmpower(data) {
  return request({
    url: '/human/underlingEmpower',
    method: 'post',
    data: data
  })
}

// 删除各公司主管查询下属薪资授权设定
export function delUnderlingEmpower(uuids) {
  return request({
    url: `/human/underlingEmpower/delUnderlingEmpower?uuid=${uuids}`,
    method: 'delete'
  })
}

// 查询各公司主管查询下属薪资授权设定岗位类别下拉选单
export function getPostTypeId() {
  return request({
    url: '/human/underlingEmpower/getPostTypeId',
    method: 'get'
  })
}
