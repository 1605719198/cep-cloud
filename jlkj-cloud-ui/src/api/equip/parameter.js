import request from '@/utils/request'

// 获取全部树节点
export function getTreeNode() {
  return request({
    url: '/equip/equipment/down/type/tree/getDownTypeTreeList',
    method: 'get',
  })
}

// 新增树节点
export function addTreeNode(data) {
  return request({
    url: '/equip/equipment/down/type/tree/addTreeNode',
    method: 'post',
    data: data
  })
}

// 修改树节点
export function updateTreeNode(data) {
  return request({
    url: '/equip/equipment/down/type/tree/updateTreeNode',
    method: 'put',
    data: data
  })
}

// 删除树节点
export function deleteTreeNode(ids) {
  return request({
    url: `/equip/equipment/down/type/tree/deleteTreeNode/${ids}`,
    method: 'delete',
  })
}
