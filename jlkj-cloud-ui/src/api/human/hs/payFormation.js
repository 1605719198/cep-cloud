import request from '@/utils/request'

// 查询公司薪酬项目设定列表
export function listPayFormation(query) {
  return request({
    url: '/human/payFormation/list',
    method: 'get',
    params: query
  })
}

// 新增公司薪酬项目设定
export function addPayFormation(data) {
  return request({
    url: '/human/payFormation',
    method: 'post',
    data: data
  })
}

// 删除公司薪酬项目设定
export function delPayFormation(uuids) {
  return request({
    url: `/human/payFormation/delPayFormation?uuid=${uuids}`,
    method: 'delete'
  })
}
