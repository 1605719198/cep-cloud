import request from '@/utils/request'

// 查询厂商增户证照资料列表
export function listAddfile(query) {
  return request({
    url: '/finance/finance/addfile/list',
    method: 'get',
    params: query
  })
}

// 查询厂商增户证照资料详细
export function getAddfile(uuid) {
  return request({
    url: '/finance/finance/addfile/' + uuid,
    method: 'get'
  })
}

// 新增厂商增户证照资料
export function addAddfile(data) {
  return request({
    url: '/finance/finance/addfile',
    method: 'post',
    data: data
  })
}

// 修改厂商增户证照资料
export function updateAddfile(data) {
  return request({
    url: `/finance/finance/addfile`,
    method: 'put',
    data: data
  })
}

// 删除厂商增户证照资料
export function delAddfile(uuid) {
  return request({
    url: '/finance/finance/addfile/' + uuid,
    method: 'delete'
  })
}
