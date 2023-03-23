import request from '@/utils/request'
//安全重点工作列表查询
export const safeImportantWorkList = (page, query) => {
  return request({
    url: '/safety/safe/safeImportantWork/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//安全重点工作-奖励比例列表
export const safeImportantWorkListProportions = (params) => {
  return request({
    url: '/safety/safe/safeImportantWork/listProportions',
    method: 'get',
    params
  })
};
//新增
export const safeImportantWorkSave = (params) => {
  return request({
    url: '/safety/safe/safeImportantWork/save',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const safeImportantWorkUpdate = (params) => {
  return request({
    url: '/safety/safe/safeImportantWork/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const safeImportantWorkDelete = (ids) => {
  return request({
    url: '/safety/safe/safeImportantWork/delete',
    method: 'post',
    data: ids
  })
};
