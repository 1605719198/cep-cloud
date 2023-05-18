import request from '@/utils/request'

// 查询集团管理-纵向印项目公式列表
export function listProjectFormula(query) {
  return request({
    url: '/finance/projectFormula/list',
    method: 'get',
    params: query
  })
}

// 查询集团管理-纵向印项目公式详细
export function getProjectFormula(id) {
  return request({
    url: '/finance/projectFormula/' + id,
    method: 'get'
  })
}

// 新增集团管理-纵向印项目公式
export function addProjectFormula(data) {
  return request({
    url: '/finance/projectFormula',
    method: 'post',
    data: data
  })
}

// 修改集团管理-纵向印项目公式
export function updateProjectFormula(data) {
  return request({
    url: '/finance/projectFormula',
    method: 'put',
    data: data
  })
}

// 删除集团管理-纵向印项目公式
export function delProjectFormula(id) {
  return request({
    url: '/finance/projectFormula/' + id,
    method: 'delete'
  })
}

// 查询报表代号(下拉选单用)
export function selectReportNoList() {
  return request({
    url: '/finance/projectFormula/reportNoList',
    method: 'get',
  })
}

// 状态修改
export function changeUserStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/finance/projectFormula/changeStatus',
    method: 'put',
    data: data
  })
}
