import request from '@/utils/request'
//安全隐患-查询列表
export const dangerList = (page, query) => {
  return request({
    url: '/safety/safe/danger/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const dangerInsert = (params) => {
  return request({
    url: '/safety/safe/danger/insert',
    method: 'post',
    data: { ...params }
  })
};
//整改
export const dangerDispose = (params) => {
  return request({
    url: '/safety/safe/danger/dispose',
    method: 'post',
    data: { ...params }
  })
};

//验收
export const dangerAcceptance = (params) => {
  return request({
    url: '/safety/safe/danger/acceptance',
    method: 'post',
    data: { ...params }
  })
};

//详情
export const safeDangerInfo = (params) => {
  return request({
    url: '/safety/safe/danger/info',
    method: 'get',
    params
  })
};

//删除
export const safeDangerDelete = (ids) => {
  return request({
    url: '/safety/safe/danger/delete',
    method: 'post',
    data: ids
  })
};
