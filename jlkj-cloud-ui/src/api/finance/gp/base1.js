import request from '@/utils/request';
// 新增厂商基础资料
export function addManufacturerBasics(data) {
  return request({
    url: `/finance/finance/manufacturer/doAdd`,
    method: 'post',
    data: data
  })
}
// 修改厂商基础资料
export function updateManufacturerBasics(data) {
  return request({
    url: `/finance/finance/manufacturer/doEdit`,
    method: 'post',
    data: data
  })
}
// 查询销售管理-厂商基本资料列表
export function listBase(query) {
  return request({
    url: `/finance/finance/manufacturer/list`,
    method: 'get',
    params: query
  })
}

// 删除厂商基础资料
export function delManufacturerBasics(id) {
  return request({
    url: `/finance/finance/manufacturer/delete?id=${id}` ,
    method: 'delete'
  })

}




// 修改厂商采购关系资料
export function updateManufacturerBasicsMp(data) {
  return request({
    url: `/finance/finance/manufacturer/doEditMp`,
    method: 'post',
    data: data
  })
}
// 修改厂商客户关系资料
export function updateManufacturerBasicSo(data) {
  return request({
    url: `/finance/finance/manufacturer/doEditSo`,
    method: 'post',
    data: data
  })
}
// 修改厂商报支关系资料
export function updateManufacturerBasicFc(data) {
  return request({
    url: `/finance/finance/manufacturer/doEditFc`,
    method: 'post',
    data: data
  })
}
// 修改厂商报支关系资料
export function updateManufacturerBasicSt(data) {
  return request({
    url: `/finance/finance/manufacturer/doEditSt`,
    method: 'post',
    data: data
  })
}


// 查询厂商基本资料列表
export function queryAll(data) {
  return request({
    url: `/finance/finance/manufacturer/queryAll`,
    method: 'get',
    params: data
  })
}
// 查询厂商基本资料列表
export function selectGoodsByGroupId(data) {
  return request({
    url: `/finance/finance/manufacturer/selectGoodsByGroupId`,
    method: 'get',
    params: data
  })
}
// 查询厂商报支关系
export function queryFc(manufacturerId) {
  return request({
    url: `/finance/finance/manufacturer/queryFc?manufacturerId=${manufacturerId}`,
    method: 'get'
  })
}
// 查询厂商客户关系
export function querySo(manufacturerId) {
  return request({
    url: `/finance/finance/manufacturer/querySo?manufacturerId=${manufacturerId}`,
    method: 'get'
  })
}
// 查询厂商采购关系
export function queryMp(manufacturerId) {
  return request({
    url: `/finance/finance/manufacturer/queryMp?manufacturerId=${manufacturerId}`,
    method: 'get'
  })
}
// 查询厂商承运关系
export function querySt(manufacturerId) {
  return request({
    url: `/finance/finance/manufacturer/querySt?manufacturerId=${manufacturerId}`,
    method: 'get'
  })
}
// 查询厂商基本资料详细
export function getBase1(manufacturerId) {
  return request({
    url: `/finance/finance/manufacturer/queryOne?manufacturerId=${manufacturerId}`,
    method: 'get'
  })
}
// 查询接口
export function queryInterface(manufacturerId,compId) {
  return request({
    url: '/finance/finance/manufacturer/queryInterface',
    method: 'get',
    params:{
      manufacturerId:manufacturerId,
      compId:compId
    }
  })
}
// 查询厂商关系
export function queryRelations(manufacturerId) {
  return request({
    url: `/finance/finance/manufacturer/queryRelations?manufacturerId=${manufacturerId}` ,
    method: 'get'
  })
}
