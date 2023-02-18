import request from '@/utils/request'
// 获取全部树节点
export function getTreeNode() {
  return request({
    url: '/equip/equipment/domain/tree/getDomainTreeList',
    method: 'get',
  })
}
// 新增树节点
export function addTreeNode(data) {
  return request({
    url: '/equip/equipment/domain/tree/addTreeNode',
    method: 'post',
    data: data
  })
}
// 修改树节点
export function updateTreeNode(data) {
  return request({
    url: '/equip/equipment/domain/tree/updateTreeNode',
    method: 'put',
    data: data
  })
}
// 删除树节点
export function deleteTreeNode(ids) {
  return request({
    url: `/equip/equipment/domain/tree/deleteTreeNode/${ids}`,
    method: 'delete',
  })
}
// 根据ID查询数据
export function getDomainById(id) {
  return request({
    url: '/equip/equipment/domain/tree/getDomainById',
    method: 'get',
    params: {
      id: id
    }
  })
}
