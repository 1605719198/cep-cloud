import request from '@/utils/request'

// 查询公司卡钟设定列表
export function listClockwork(query) {
  return request({
    url: '/human/clockwork/list',
    method: 'get',
    params: query
  })
}

// 查询公司卡钟设定详细
export function getClockwork(id) {
  return request({
    url: '/human/clockwork/' + id,
    method: 'get'
  })
}

// 通过公司查询公司卡钟设定
export function getCompClockwork(compId) {
  return request({
    url: '/human/clockwork/listByComp/' + compId,
    method: 'get'
  })
}



// 新增公司卡钟设定
export function addClockwork(data) {
  return request({
    url: '/human/clockwork',
    method: 'post',
    data: data
  })
}

// 修改公司卡钟设定
export function updateClockwork(data) {
  return request({
    url: '/human/clockwork',
    method: 'put',
    data: data
  })
}

// 删除公司卡钟设定
export function delClockwork(id) {
  return request({
    url: '/human/clockwork/' + id,
    method: 'delete'
  })
}
