import request from '@/utils/request'

// 查询人员机构树结构信息
export function getHumanTree() {
  return request({
    url: '/human/humanresource/perorg/tree/getOrganizationTreeList',
    method: 'get'
  })
}
// 获取组织机构树结构资料
export function getOrganizationDeptTree() {
  return request({
    url: `/human/humanresource/perorg/tree/getOrganizationDeptTreeList`,
    method: 'get'
  })
}
// 根据部门ID获取人员信息资料
export function getOrganizationUserList(id) {
  return request({
    url: `/human/humanresource/perorg/tree/getOrganizationUserList`,
    method: 'get',
    params: {
      id: id
    }
  })
}
// 获取人员信息数据
export function getPersonnerlInfoList() {
  return request({
    url: `/human/humanresource/perorg/tree/getPersonnerlInfoList`,
    method: 'get',
  })
}
