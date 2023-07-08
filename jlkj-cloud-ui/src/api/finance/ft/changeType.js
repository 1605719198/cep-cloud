import request from '@/utils/request'

// 查询固定资产变动方式列表
export function listChangeType(query) {
  return request({
    url: '/finance/changeType/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产变动方式详细
export function getChangeType(uuid) {
  return request({
    url: '/finance/changeType/' + uuid,
    method: 'get'
  })
}

// 新增固定资产变动方式
export function addChangeType(data) {
  return request({
    url: '/finance/changeType',
    method: 'post',
    data: data
  })
}

// 修改固定资产变动方式
export function updateChangeType(data) {
  return request({
    url: '/finance/changeType',
    method: 'put',
    data: data
  })
}

// 删除固定资产变动方式
export function delChangeType(uuid) {
  return request({
    url: '/finance/changeType/' + uuid,
    method: 'delete'
  })
}

// 查询变动方式下拉选单
export function selectChangeTypeList(companyId,billNature) {
  return request({
    url: '/finance/changeType/getSelect/'+companyId+"/"+billNature ,
    method: 'get',
    //data: data
  })
}
