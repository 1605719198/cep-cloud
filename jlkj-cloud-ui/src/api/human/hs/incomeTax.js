import request from '@/utils/request'

// 查询各公司所得税起征点设定列表
export function listIncomeTax(query) {
  return request({
    url: '/human/incomeTax/list',
    method: 'get',
    params: query
  })
}

// 查询各公司所得税起征点设定详细
export function getIncomeTax(uuid) {
  return request({
    url: '/human/incomeTax/' + uuid,
    method: 'get'
  })
}

// 新增各公司所得税起征点设定
export function addIncomeTax(data) {
  return request({
    url: '/human/incomeTax',
    method: 'post',
    data: data
  })
}

// 修改各公司所得税起征点设定
export function updateIncomeTax(data) {
  return request({
    url: '/human/incomeTax',
    method: 'put',
    data: data
  })
}

// 删除各公司所得税起征点设定
export function delIncomeTax(uuid) {
  return request({
    url: '/human/incomeTax/' + uuid,
    method: 'delete'
  })
}


//获取公司列表
export function selectVersion(compId) {
  return request({
    url: '/human/incomeTax/selectVersion/'+compId,
    method: 'get',
  })
}
