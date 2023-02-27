import request from '@/utils/request'

// 查询成本中心设定列表
export function listFinancetest(data) {
  return request({
    url: '/finance/product/accountItemCode/queryAll',
    method: 'get',
    params: data
  })
}
// 查询属性资料列表
export function attributeQuery(id) {
  return request({
    url: `api/neplatform/fin/finance/product/accountItemCode/attributeQuery?id=${id}`,
    method: 'get',

  })
}
// 查询属性资料属性名称、属性说明
export function getFinanceInformation(data) {
  return request({
    url: '/finance/product/accountItemCode/queryAccount' ,
    method: 'get',
    params: data
  })
}

// 查询属性资料属性名称、属性说明
export function getFinanceMcc(compIdMcc,leafIdMcc) {
  return request({
    url: '/finance/product/accountItemCode/queryAccountMcc' ,
    method: 'get',
    params:{
      compIdMcc: compIdMcc,
      leafIdMcc:leafIdMcc
    }
  })
}

// 查询属性资料列表attributeQueryOne
export function attributeQuery1() {
  return request({
    url: 'api/neplatform/fin/finance/product/accountItemCode/attributeQuery',
    method: 'get',

  })
}
// 查询属性资料列表
export function attributeQueryOne(attriName) {
  return request({
    url: `/finance/product/accountItemCode/attributeQueryOne?attriName=${attriName}`,
    method: 'get'
  })
}
// 查询成本中心设定详细FinanceProduct
export function getFinancetest(accountItemCode,id) {
  return request({
    url: '/finance/product/accountItemCode/queryOne' ,
    method: 'get',
    params:{
      accountItemCode: accountItemCode,
      id:id
    }
  })
}

// 新增测试
export function addFinancetest(data) {
  return request({
    url: '/finance/product/accountItemCode/doAdd',
    method: 'post',
    data: data
  })
}
// 属性新增测试
export function addFinanceattri(data) {
  return request({
    url: `/finance/product/accountItemCode/attriAdd`,
    method: 'post',
    data: data
  })
}


// 修改测试
export function updateFinancetest(data) {
  return request({
    url: '/finance/product/accountItemCode/doEdit',
    method: 'post',
    data: data
  })
}
// 修改测试
export function updateFinanceattri(data) {
  return request({
    url: '/finance/product/accountItemCode/doEditAttri',
    method: 'post',
    data: data
  })
}

// 删除测试delAttribute
export function delFinancetest(id) {
  return request({
    url: `/finance/product/accountItemCode/delete?id=${id}` ,
    method: 'delete'
  })


}
