import request from '@/utils/request'

// 查询成本中心设定列表
export function listFinancetest(data) {
  return request({
    url: '/finance/acct/company/queryAll',
    method: 'get',
    params: data
  })
}
// 查询属性资料列表
export function attributeQuery(id) {
  return request({
    url: `/finance/acct/company/attributeQuery?id=${id}`,
    method: 'get',

  })
}
// 查询属性资料列表attributeQueryOne
export function attributeQuery1() {
  return request({
    url: '/finance/acct/company/attributeQuery',
    method: 'get',

  })
}
// 查询属性资料列表
export function attributeQueryOne(attriName) {
  return request({
    url: `/ffinance/acct/company/attributeQueryOne?attriName=${attriName}`,
    method: 'get'
  })
}
// 查询成本中心设定详细
export function getFinancetest(compId,id) {
  return request({
    url: '/finance/acct/company/queryOne',
    method: 'get',
    params:{
      compId: compId,
      id:id
    }
  })
}

// 新增测试
export function addFinancetest(data) {
  return request({
    url: '/finance/acct/company/doAdd',
    method: 'post',
    data: data
  })
}
// 属性新增测试
export function addFinanceattri(data) {
  return request({
    url: `/finance/acct/company/attriAdd`,
    method: 'post',
    data: data
  })
}


// 修改测试
export function updateFinancetest(data) {
  return request({
    url: '/finance/acct/company/doEdit',
    method: 'post',
    data: data
  })
}
// 修改测试
export function updateFinanceattri(data) {
  return request({
    url: `/finance/acct/company/doEditAttri`,
    method: 'post',
    data: data
  })
}

// 删除测试delAttribute
export function delFinancetest(id) {
  return request({
    url: `/finance/acct/company/delete?id=${id}` ,
    method: 'delete'
  })

}
// 删除测试delAttribute
export function delAttribute(attriValue,value) {
  return request({
    url: '/finance/acct/company/delAttribute' ,
    method: 'delete',
    params:{
      attriValue: attriValue,
      value:value
    }

  })
}
