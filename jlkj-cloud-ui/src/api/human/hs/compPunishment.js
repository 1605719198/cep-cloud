import request from '@/utils/request'

// 查询各公司专业奖罚明细项目维护列表
export function listCompPunishment(query) {
  return request({
    url: '/human/compPunishment/list',
    method: 'get',
    params: query
  })
}

// 查询各公司专业奖罚明细项目维护详细
export function getCompPunishment(uuid) {
  return request({
    url: '/human/compPunishment/' + uuid,
    method: 'get'
  })
}

// 保存各公司专业奖罚明细项目维护
export function saveCompPunishment(data) {
  return request({
    url: '/human/compPunishment/save',
    method: 'post',
    data: data
  })
}

// 修改各公司专业奖罚明细项目维护
export function updateCompPunishment(data) {
  return request({
    url: '/human/compPunishment',
    method: 'put',
    data: data
  })
}

// 删除各公司专业奖罚明细项目维护
export function delCompPunishment(uuid) {
  return request({
    url: '/human/compPunishment/' + uuid,
    method: 'delete'
  })
}
