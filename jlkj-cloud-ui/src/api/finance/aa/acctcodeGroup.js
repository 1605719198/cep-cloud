import request from '@/utils/request'

// 查询会计科目-集团级列表
export function listAcctcodeGroup(query) {
  return request({
    url: '/finance/acctcodeGroup/list',
    method: 'get',
    params: query
  })
}

// 查询会计科目-集团级详细
export function getAcctcodeGroup(groupAcctId) {
  return request({
    url: '/finance/acctcodeGroup/' + groupAcctId,
    method: 'get'
  })
}

// 新增会计科目-集团级
export function addAcctcodeGroup(data) {
  return request({
    url: '/finance/acctcodeGroup',
    method: 'post',
    data: data
  })
}

// 修改会计科目-集团级
export function updateAcctcodeGroup(data) {
  return request({
    url: '/finance/acctcodeGroup',
    method: 'put',
    data: data
  })
}

// 删除会计科目-集团级
export function delAcctcodeGroup(groupAcctId) {
  return request({
    url: '/finance/acctcodeGroup/' + groupAcctId,
    method: 'delete'
  })
}

// 删除会计科目-集团级
export function delAcctcodeGroupByCode(groupAcctCode) {
  return request({
    url: '/finance/acctcodeGroup/code/' + groupAcctCode,
    method: 'delete'
  })
}

// 查询部门下拉树结构
export function codeTreeSelect() {
  return request({
    url: '/finance/acctcodeGroup/codeTree',
    method: 'get'
  })
}

// 状态修改
export function changeStatus(groupAcctId, disabledCode) {
  const data = {
    groupAcctId,
    disabledCode
  }
  return request({
    url: '/finance/acctcodeGroup/changeStatus',
    method: 'put',
    data: data
  })
}
