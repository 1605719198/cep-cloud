import request from '@/utils/request';
// 查询厂商基本资料列表
export function queryAll(data) {
  return request({
    url: `/finance/finance/manufacturer/queryAll`,
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
