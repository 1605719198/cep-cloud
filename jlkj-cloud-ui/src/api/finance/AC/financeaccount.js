import request from '@/utils/request';

// 获取全部树节点FinanceProduct
export function getTreeNode() {
  return request({
    url: '/finance/finance/cost/menu/generateTree',
    method: 'get',
  })
}
// 分页查询科目树节点
export function queryDataByParams(params) {
  return request({
    url: '/finance/finance/cost/code/doQueryAll',
    method: 'get',
    params: params
  })
}
//根据ID查询科目数据
export function getData(typeId) {
  return request({
    url: '/finance/finance/cost/code/doQueryOne/' + typeId,
    method: 'get'
  })
}
// 新增科目数据
export function doAddCode(data) {
  return request({
    url: '/finance/finance/cost/code/doAdd',
    method: 'post',
    data: data
  })
}
// 成本科目索引修改
export function updateMenuData(data) {
  return request({
    url: '/finance/finance/cost/menu/doEdit',
    method: 'put',
    data: data
  })
}
// 树删除
export function deleteMenu(ids,upperCodex) {
  return request({
    url: '/finance/finance/cost/menu/doDelete',
    method: 'delete',
    params:{
      ids:ids,
      upperCodex:upperCodex
    }
  })
}
// 新增科目数据
export function doAddMenu(data) {
  return request({
    url: '/finance/finance/cost/menu/doAdd',
    method: 'post',
    data: data
  })
}
//成本科目资料修改
export function updateCodeData(data) {
  return request({
    url: '/finance/finance/cost/code/doEdit',
    method: 'put',
    data: data
  })
}
// 成本科目删除
export function deleteCodeData(ids) {
  return request({
    url: '/finance/finance/cost/code/doDelete/'+ids,
    method: 'delete',
  })
}
  // 分页查询科目节点
  export function queryMenuList(params) {
    return request({
      url: '/finance/finance/cost/menu/queryMenuList',
      method: 'get',
      params: params
    })
}
// 分页查询科目节点
export function queryMenu(params) {
  return request({
    url: '/finance/finance/cost/menu/queryMenu',
    method: 'get',
    params: params

  })
}
//根据ID查询成本科目索引数据
export function getMenuData(typeId) {
  return request({
    url: '/finance/finance/cost/menu/queryOne/' + typeId,
    method: 'get'
  })
}



// 获取全部树节点
export function getTreeTestNode() {
  return request({
    url: '/finance/finance/manufacturer/ManufacturerTreeList',
    method: 'get',
  })
}


