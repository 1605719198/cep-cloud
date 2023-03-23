import request from '@/utils/request'
//部门树
export const departmentTree = (params) => {
  return request({
    url: '/system/department/tree',
    method: 'get',
    params
  })
};
//权限树
export const permissionTree = (params) => {
  return request({
    url: '/system/permission/tree',
    method: 'get',
    params
  })
};
//根据部门、姓名、工号获取人员列表
export const getUserListByDepartmentOrNameOrJobnumber = (page, query) => {
  return request({
    url: '/system/personnel/getUserListByDepartmentOrNameOrJobnumber',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//根据人员id集合获取人员列表
export const getUserListByUserIds = (params) => {
  return request({
    url: '/system/personnel/getUserListByUserIds',
    method: 'post',
    data: params
  })
};
//部门分页列表
export const departmentList = (page, query) => {
  return request({
    url: '/system/department/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//获取系统角色分页列表
export const getRole = (page, query) => {
  return request({
    url: '/system/role/get_role',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增系统角色
export const addRole = (params) => {
  return request({
    url: '/system/role/add_role',
    method: 'post',
    data: { ...params }
  })
};
//修改系统角色
export const editRole = (params) => {
  return request({
    url: '/system/role/edit_role',
    method: 'post',
    data: { ...params }
  })
};
//删除系统角色
export const delRole = (ids) => {
  return request({
    url: '/system/role/del_role',
    method: 'post',
    data: ids
  })
};
//获取角色绑定的组织机构列表
export const roleOrganizationList = (params) => {
  return request({
    url: '/system/role_organization/list',
    method: 'get',
    params
  })
};
//获取角色绑定的权限
export const getListByRole = (params) => {
  return request({
    url: '/system/role_permission/getListByRole',
    method: 'get',
    params
  })
};
//绑定角色对应的组织机构
export const roleOrganizationUpdate = (params) => {
  return request({
    url: '/system/role_organization/update',
    method: 'post',
    data: { ...params }
  })
};
//保存角色权限
export const rolePermissionUpdate = (params) => {
  return request({
    url: '/system/role_permission/update',
    method: 'post',
    data: { ...params }
  })
};
//绑定角色对应的人员
export const roleUserUpdate = (params) => {
  return request({
    url: '/system/role_user/update',
    method: 'post',
    data: { ...params }
  })
};
//在线人员
export const getOnlines = () => {
  return request({
    url: '/system/get_onlines',
    method: 'get',
    params: {}
  })
};
//密码重置
export const getReset = (params) => {
  return request({
    url: '/system/personnel/password/reset',
    method: 'post',
    data: params
  })
};

//获取人员签章
export const getHumanresourcePersonnelSignature = (params) => {
  return request({
    url: '/system/humanresourcepersonnel/getHumanresourcePersonnelSignature',
    method: 'get',
    params:params
  })
};

//保存人员签章
export const updateHumanresourcePersonnelSignature = (params) => {
  return request({
    url: '/system/humanresourcepersonnel/updateHumanresourcePersonnelSignature',
    method: 'PUT',
    data: params
  })
};


