import request from '@/utils/request'

// 查询产副品资料
export function listFinancetest(data) {
  return request({
    url: '/finance/account/period/queryAll',
    method: 'get',
    params: data
  })
}
// 查询属性资料列表
export function attributeQuery(productCode) {
  return request({
    url: `api/neplatform/fin/finance/account/period/attributeQuery?productCode=${productCode}`,
    method: 'get',

  })
}
// 查询属性资料列表attributeQueryOne
export function attributeQuery1() {
  return request({
    url: 'api/neplatform/fin/finance/account/period/attributeQuery',
    method: 'get',

  })
}
// 查询属性资料列表
export function attributeQueryOne(attriName) {
  return request({
    url: `/finance/account/period/attributeQueryOne?attriName=${attriName}`,
    method: 'get'
  })
}
// 查询成本中心设定详细
export function getFinancetest(productCode,id) {
  return request({
    url: '/finance/account/period/queryOne' ,
    method: 'get',
    params:{
      productCode: productCode,
      id:id
    }

  })
}
// 查询财务资料的科目名称
export function getFinanceInformation(data) {
  return request({
    url: '/finance/account/period/queryAccount' ,
    method: 'get',
    params: data
  })
}
// 查询财务资料的详细资料
export function getFinanceByproduct(data) {
  return request({
    url: '/finance/account/period/queryByproduct' ,
    method: 'get',
    params: data
  })
}

// 新增测试
export function addFinancetest(data) {
  return request({
    url: '/finance/account/period/doAdd',
    method: 'post',
    data: data
  })
}
// 属性新增测试
export function addFinanceattri(data) {
  return request({
    url: `/finance/account/period/attriAdd`,
    method: 'post',
    data: data
  })
}
// 财务新增测试
export function addFinanceAccount(data) {
  return request({
    url: `/finance/account/period/accountAdd`,
    method: 'post',
    data: data
  })
}


// 属性修改测试
export function updateFinanceattri(data) {
  return request({
    url: `/finance/product/code/doEditAttri`,
    method: 'post',
    data: data
  })
}
// 财务资料修改测试
export function updateAccount(data) {
  return request({
    url: `/finance/account/period/doEditAccount`,
    method: 'post',
    data: data
  })
}

// 状态修改
export function updatecloseYn(isClosed,id) {
  return request({
    url: `/finance/account/period/doEditYn`,
    method: 'post',
    params:{
      isClosed: isClosed,
      id:id
    }

  })
}
// 删除测试delAttribute
export function delFinancetest(accountPeriodDelete,compId) {
  return request({
    url: '/finance/account/period/delete',
    method: 'delete',
    params:{
      accountPeriodDelete: accountPeriodDelete,
      compId:compId
    }
  })

}
// 删除测试delAttribute
export function delAttribute(attriValue,value) {
  return request({
    url: '/finance/account/period/delAttribute' ,
    method: 'delete',
    params:{
      attriValue: attriValue,
      value:value
    }

  })
}
