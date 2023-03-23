import request from '@/utils/request'


/**
 * 查询环保采集点位 列表
 */
export const listEnvironmentalCollectionPoints = (params) => {
  return request({
    url:'/safety/environmentalCollectionPoints/list',
    method:'get',
    params
  });
};

/**
 * 修改
 */
export const updateEnvironmentalCollectionPoints = (params) => {
  return request({
    url:'/safety/environmentalCollectionPoints/update',
    method:'post',
    data:{...params}
  });
};

/**
 * 新增
 */
export const saveEnvironmentalCollectionPoints = (params) => {
  return request({
    url:'/safety/environmentalCollectionPoints/save',
    method:'post',
    data:{...params}
  });
};

/**
 * 删除
 */
export const deleteEnvironmentalCollectionPoints = (params) => {
  return request({
    url:'/safety/environmentalCollectionPoints/delete',
    method:'post',
    data:{...params}
  });
};
