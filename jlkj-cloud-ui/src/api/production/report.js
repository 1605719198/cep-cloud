import request from "@/utils/request";
//--------------------日志维护--------------------//
//日志查询
export const logmaintainList = (page, query) => {
  return request({
    url: "/api/neplatform/production/logmaintain/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//日志新增
export const logmaintainSave = (params) => {
  return request({
    url: "/api/neplatform/production/logmaintain/save",
    method: "post",
    data: { ...params },
  });
};
//日志修改
export const logmaintainUpdate = (params) => {
  return request({
    url: "/api/neplatform/production/logmaintain/update",
    method: "post",
    data: { ...params },
  });
};
//日志删除
export const logmaintainDel = (params) => {
  return request({
    url: "/api/neplatform/production/logmaintain/del",
    method: "post",
    data: { ...params },
  });
};
