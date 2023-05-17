import request from '@/utils/request'

// 查询会计科目-公司级列表
export function listAcctcodeCorp(query) {
  return request({
    url: '/finance/acctcodeCorp/list',
    method: 'get',
    params: query
  })
}

// 查询会计科目-公司级详细
export function getAcctcodeCorp(groupAcctId) {
  return request({
    url: '/finance/acctcodeCorp/' + groupAcctId,
    method: 'get'
  })
}

// 新增会计科目-公司级
export function addAcctcodeCorp(data) {
  return request({
    url: '/finance/acctcodeCorp',
    method: 'post',
    data: data
  })
}

// 修改会计科目-公司级
export function updateAcctcodeCorp(data) {
  return request({
    url: '/finance/acctcodeCorp',
    method: 'put',
    data: data
  })
}

// 删除会计科目-公司级
export function delAcctcodeCorp(groupAcctId) {
  return request({
    url: '/finance/acctcodeCorp/' + groupAcctId,
    method: 'delete'
  })
}

// 查询部门下拉树结构
export function codeTreeSelect(query) {
  return request({
    url: '/finance/acctcodeCorp/codeTree',
    method: 'get',
    params: query
  })
}

// 查询集团级会计科目列表
export function selectCodeGroupList(acctCode,companyId) {
  return request({
    url: '/finance/acctcodeCorp/codeGroupList/' + acctCode+"/"+companyId,
    method: 'get',
  })
}


// 批量新增集团级会计科目列表
export function addBatchAcctcodeCorp(data) {
  return request({
    url: '/finance/acctcodeCorp/batch',
    method: 'post',
    data: data
  })
}


// 状态修改
export function changeStatus(groupAcctId, disabledCode) {
  const data = {
    groupAcctId,
    disabledCode
  }
  return request({
    url: '/finance/acctcodeCorp/changeStatus',
    method: 'put',
    data: data
  })
}


// 查询会计科目-公司级列表
export function listAcctcodeCorpPop(query) {
  return request({
    url: '/finance/acctcodeCorp/listPop',
    method: 'get',
    params: query
  })
}
