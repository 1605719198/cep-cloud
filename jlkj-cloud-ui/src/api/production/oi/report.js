import request from "@/utils/request";
//--------------------日志维护--------------------//
//日志查询
export const logmaintainList = (page, query) => {
  return request({
    url: "/product/logmaintain/list",
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
    url: "/product/logmaintain/save",
    method: "post",
    data: { ...params },
  });
};
//日志修改
export const logmaintainUpdate = (params) => {
  return request({
    url: "/product/logmaintain/update",
    method: "post",
    data: { ...params },
  });
};
//日志删除
export const logmaintainDel = (params) => {
  return request({
    url: "/product/logmaintain/del",
    method: "post",
    data: { ...params },
  });
};
