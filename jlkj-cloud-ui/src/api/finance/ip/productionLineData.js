import request from '@/utils/request'

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/finance/product/mill/doQueryAll',
  method: 'post',
  data: data
});

// 新增
export function addInfo(data) {
  return request({
    url: '/finance/product/mill/doAdd',
    method: 'post',
    data: data
  })
}

// 产线资料添加
export function addInfoAttri(data) {
  return request({
    url: '/finance/product/mill/doAttriAdd',
    method: 'post',
    data: data
  })
}
// 产线资料添加
export function addInfoSystem(data) {
  return request({
    url: '/finance/product/mill/doSystemAdd',
    method: 'post',
    data: data
  })
}
// 产线资料添加
export function addInfoActivity(data) {
  return request({
    url: '/finance/product/mill/doActivityAdd',
    method: 'post',
    data: data
  })
}
// 产线资料添加
export function addInfoProduct(data) {
  return request({
    url: '/finance/product/mill/doProductAdd',
    method: 'post',
    data: data
  })
}
// 产线资料删除
export function deleteInfoAttri(millId,attriValue) {
  return request({
    url: `/finance/product/mill/doAttriDelete?millId=${millId}&attriValue=${attriValue}`,
    method: 'delete',
  })
}
// 产线资料删除
export function deleteInfoSystem(millId,sysId) {
  return request({
    url: `/finance/product/mill/doSystemDelete?millId=${millId}&sysId=${sysId}`,
    method: 'delete',
  })
}
// 产线资料删除
export function deleteInfoActivity(millId,activityId) {
  return request({
    url: `/finance/product/mill/doActivityDelete?millId=${millId}&activityId=${activityId}`,
    method: 'delete',
  })
}
// 产线资料删除
export function deleteInfoProduct(millId,productCode) {
  return request({
    url: `/finance/product/mill/doProductDelete?millId=${millId}&productCode=${productCode}`,
    method: 'delete',
  })
}
// 修改
export function updateInfo(data) {
  return request({
    url: '/finance/product/mill/doEdit',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(millId) {
  return request({
    url: `/finance/product/mill/doDelete?millId=${millId}`,
    method: 'delete'
  })
}

// 产线单笔资料查询（包含属性信息）
export const queryInfoOne = (params) => request({
  url: '/finance/product/mill/doQueryOne',
  method: 'get',
  params: params
});

// 产线相关系统页签查询
export const queryInfoSys = (params) => request({
  url: '/finance/product/mill/doQueryOne',
  method: 'get',
  params: params
});

// 产线相关活动查询
export const queryInfoActId = (params) => request({
  url: '/finance/product/mill/doQueryOne',
  method: 'get',
  params: params
});

// 产线产出产副品查询
export const queryInfoProd = (params) => request({
  url: '/finance/product/mill/doQueryOne',
  method: 'get',
  params: params
});
