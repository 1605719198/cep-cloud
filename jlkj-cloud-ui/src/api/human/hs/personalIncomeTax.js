import request from '@/utils/request'

// 查询个人所得税比例维护列表
export function listPersonalIncomeTax(query) {
  return request({
    url: '/human/personalIncomeTax/list',
    method: 'get',
    params: query
  })
}

// 查询个人所得税比例维护详细
export function getPersonalIncomeTax(id) {
  return request({
    url: '/human/personalIncomeTax/' + id,
    method: 'get'
  })
}

// 新增个人所得税比例维护
export function addPersonalIncomeTax(data) {
  return request({
    url: '/human/personalIncomeTax/save',
    method: 'post',
    data: data
  })
}

// 修改个人所得税比例维护
export function updatePersonalIncomeTax(data) {
  return request({
    url: '/human/personalIncomeTax',
    method: 'put',
    data: data
  })
}

// 删除个人所得税比例维护
export function delPersonalIncomeTax(id) {
  return request({
    url: '/human/personalIncomeTax/' + id,
    method: 'delete'
  })
}

//获取类别列表
export function selectVersion(type) {

  return request({
    url: '/human/personalIncomeTax/selectVersion/'+type,
    method: 'get',
  })
}
