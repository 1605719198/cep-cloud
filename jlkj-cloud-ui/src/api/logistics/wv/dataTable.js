import request from '@/utils/request'

// 新增
export function addData(data) {
  return request({
    url: '/logistics/logistics/dict/data/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updateData(data) {
  return request({
    url: '/logistics/logistics/dict/data/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delData(dictCode) {
  return request({
    url: '/logistics/logistics/dict/data/delete/' + dictCode,
    method: 'delete'
  })
}

// 查询日运输计划
export function queryAll(params) {
  return request({
    url: '/logistics/logistics/dict/data/queryDataByParams',
    method: 'get',
    params: {
      ...params
    }
  })
}
// 查询字典类型列表
export function getDictOptionselect() {
  return request({
    url: '/logistics/logistics/dict/data/getDictOptionselect',
    method: 'get'
  })
}

// 根据id查询数据
export function getDataById(id) {
  return request({
    url: '/logistics/logistics/dict/data/getDataById/'+id,
    method: 'get'
  })
}
