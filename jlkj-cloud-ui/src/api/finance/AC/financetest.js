import request from '@/utils/request';

// 查询成本中心设定列表
export function listFinancetest(data) {
  return request({
    url: '/finance/finance/cost/costCenter/queryAll',
    method: 'get',
    params: data
  })
}
// 查询属性资料列表
export function attributeQuery(id) {
  return request({
    url: `/finance/finance/cost/costCenter/attributeQuery?id=${id}`,
    method: 'get',

  })
}
// 查询属性资料列表attributeQueryOne
export function attributeQuery1() {
  return request({
    url: '/finance/finance/cost/costCenter/attributeQuery',
    method: 'get',

  })
}
// 查询属性资料列表
export function attributeQueryOne(attriName) {
  return request({
    url: `/finance/finance/cost/costCenter/attributeQueryOne?attriName=${attriName}`,
    method: 'get'
  })
}
// 查询成本中心设定详细
export function getFinancetest(costCenter,id) {
  return request({
    url: '/finance/finance/cost/costCenter/queryOne',
    method: 'get',
    params:{
      costCenter: costCenter,
      id:id
    }
  })
}

// 新增测试
export function addFinancetest(data) {
  return request({
    url: '/finance/finance/cost/costCenter/doAdd',
    method: 'post',
    data: data
  })
}
// 属性新增测试
export function addFinanceattri(data) {
  return request({
    url: `/finance/finance/cost/costCenter/attriAdd`,
    method: 'post',
    data: data
  })
}


// 修改测试
export function updateFinancetest(data) {
  return request({
    url: '/finance/finance/cost/costCenter/doEdit',
    method: 'post',
    data: data
  })
}
// 修改测试
export function updateFinanceattri(data) {
  return request({
    url: `/finance/finance/cost/costCenter/doEditAttri`,
    method: 'post',
    data: data
  })
}

// 删除测试delAttribute
export function delFinancetest(id) {
  return request({
    url: `/finance/finance/cost/costCenter/delete?id=${id}` ,
    method: 'delete'
  })

}

