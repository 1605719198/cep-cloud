import request from '@/utils/request'

//部门树
export function baseInfoTree() {
  return request({
    url: '/human/baseInfo/tree',
    method: 'get'
  })
}

//获取子节点查询列表
export function getChildrenList(data) {
  return request({
    url: '/human/baseInfo/list',
    method: 'get',
    params: data
  })
}

//添加作业数据
export function addBaseInfo(data) {
  return request({
    url: '/human/baseInfo/add',
    method: 'post',
    data: data
  })
}

//修改作业数据
export function updateBaseInfo(data) {
  return request({
    url: '/human/baseInfo/update',
    method: 'post',
    data: data
  })
}

// 删除作业数据
export function deleteBaseInfo(uuid) {
  return request({
    url: `/human/baseInfo/delete?uuid=${uuid}` ,
    method: 'delete'
  })
}

//获取用户管理专业下拉选单
export function getBaseInfo(data) {
  return request({
    url: '/human/baseInfo/getDegreeMajor',
    method: 'get',
    params: data
  })
}

//获取专业细分下拉选单
export function getDegreeMajorSpecialization(data) {
  return request({
    url: '/human/baseInfo/getDegreeMajorSpecialization',
    method: 'get',
    params: data
  })
}
