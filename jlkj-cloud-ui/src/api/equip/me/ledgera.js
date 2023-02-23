import request from '@/utils/request'

// 新增基本信息
export function addEquipmentBasicProperties(data) {
  return request({
    url: '/equip/equipment/basic/properties/addEquipmentBasicProperties',
    method: 'post',
    data: data
  })
}
// 新增设备台账
export function addEquipmentAccount(data) {
  return request({
    url: '/equip/equipment/account/addEquipmentAccount',
    method: 'post',
    data: data
  })
}
// 查询所有台账数据信息
export function queryAllDataList() {
  return request({
    url: '/equip/equipment/basic/properties/queryEquipmentAccount',
    method: 'get'
  })
}
// 删除台账信息
export function deleteAccountData(ids) {
  return request({
    url: `/equip/equipment/basic/properties/deleteAccountData/${ids}`,
    method: 'delete',
  })
}
// 根据ID查询台账及基础资料
export function getAccountValueById(id) {
  return request({
    url: '/equip/equipment/basic/properties/getAccountValueById',
    method: 'get',
    params: {
      id: id
    }
  })
}
// 根据ID修改台账及基础资料
export function updateAccountById(data) {
  return request({
    url: '/equip/equipment/basic/properties/updateAccountValueById',
    method: 'put',
    data: data
  })
}
// 条件查询设备台账及基础资料
export function getEquipmentAccountByParams(data) {
  return request({
    url: '/equip/equipment/basic/properties/getEquipmentAccountByParams',
    method: 'get',
    params: data
  })
}
// 获取上级设备树
export function getEquipmentAccountTree() {
  return request({
    url: '/equip/equipment/account/getEquipmentAccountTree',
    method: 'get'
  })
}
// 设备域、设备树结合
export function getEquipmentCombineTree() {
  return request({
    url: '/equip/equipment/account/getEquipmentCombineTree',
    method: 'get'
  })
}
// 根据设备位置获取上级设备树结构信息
export function getEquipmentByLocationId(locationId) {
  return request({
    url: '/equip/equipment/basic/properties/getEquipmentByLocationId',
    method: 'get',
    params: {
      locationId: locationId
    }
  })
}
// 根据树节点ID查询t_equipment_basic_properties，判断是设备域还是上级设备
export function getEquipmentBasicNumById(id) {
  return request({
    url: '/equip/equipment/basic/properties/getEquipmentBasicNumById',
    method: 'get',
    params: {
      id: id
    }
  })
}
