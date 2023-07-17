import request from '@/utils/request'

// 查询用户选择列表
export function listUser(query) {
  debugger
  return request({
    url: '/human/userdata/list',
    method: 'get',
    params: query
  })
}

export function listChoiceUser(query) {
    return request({
      url: '/human/userdata/choicelist',
      method: 'get',
      params: query
    })
  }
