import request from '@/utils/request'
//现场环境治理-查询列表
export const safeGovernanceList = (page, query) => {
  return request({
    url: '/safety/safe/safeSiteEnvironmentalGovernance/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//现场环境治理-奖励比例列表
export const listProportions = (params) => {
  return request({
    url: '/safety/safe/safeSiteEnvironmentalGovernance/listProportions',
    method: 'get',
    params
  })
};
//新增
export const safeGovernanceSave = (params) => {
  return request({
    url: '/safety/safe/safeSiteEnvironmentalGovernance/save',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const safeGovernanceUpdate = (params) => {
  return request({
    url: '/safety/safe/safeSiteEnvironmentalGovernance/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const safeGovernanceDelete = (ids) => {
  return request({
    url: '/safety/safe/safeSiteEnvironmentalGovernance/delete',
    method: 'post',
    data: ids
  })
};

//上传
export const upload = (file) => {
  return request({
    url: '/fileresource/file/upload',
    method: 'post',
    data: file,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    }
  })
};
