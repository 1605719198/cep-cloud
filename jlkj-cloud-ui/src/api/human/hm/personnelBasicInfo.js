import request from '@/utils/request'

//获取人员基本信息查询列表
export function queryInfo(data) {
  return request({
    url: '/human/personnel/base/list',
    method: 'get',
    params: data
  })
}

//添加人员基本信息
export function addPersonnelData(data) {
  return request({
    url: '/human/personnel/base/addPersonnelBasicInfo',
    method: 'post',
    data: data
  })
}

//添加工作经历信息
export function addWorkExperienceData(data) {
  return request({
    url: '/human/personnel/work/addWorkExperienceData',
    method: 'post',
    data: data
  })
}

//添加教育经历信息
export function addEducationExperienceData(data) {
  return request({
    url: '/human/personnel/education/addEducationExperienceData',
    method: 'post',
    data: data
  })
}

//添加培训经历信息
export function addCultivateExperienceData(data) {
  return request({
    url: '/human/personnel/cultivate/addCultivateExperienceData',
    method: 'post',
    data: data
  })
}

//添加家属信息
export function addFamilyData(data) {
  return request({
    url: '/human/personnel/family/addFamilyData',
    method: 'post',
    data: data
  })
}

//添加职业资格信息
export function addOccupationData(data) {
  return request({
    url: '/human/personnel/occupation/addOccupationData',
    method: 'post',
    data: data
  })
}

//添加职称信息
export function addProfessionalData(data) {
  return request({
    url: '/human/personnel/professional/addProfessionalData',
    method: 'post',
    data: data
  })
}
