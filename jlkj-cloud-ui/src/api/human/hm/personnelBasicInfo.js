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

//获取工作经历列表
export function queryWorkExperienceInfo(data) {
  return request({
    url: '/human/personnel/work/queryWorkExperienceInfo',
    method: 'get',
    params: data
  })
}

//获取教育经历列表
export function queryEducationalExperienceInfo(data) {
  return request({
    url: '/human/personnel/education/queryEducationalExperienceInfo',
    method: 'get',
    params: data
  })
}

//获取培训经历列表
export function queryCultivateExperienceInfo(data) {
  return request({
    url: '/human/personnel/cultivate/queryCultivateExperienceInfo',
    method: 'get',
    params: data
  })
}

//获取家属信息列表
export function queryFamilyInfo(data) {
  return request({
    url: '/human/personnel/family/queryFamilyInfo',
    method: 'get',
    params: data
  })
}

//获取职业资格信息列表
export function queryOccupationInfo(data) {
  return request({
    url: '/human/personnel/occupation/queryOccupationInfo',
    method: 'get',
    params: data
  })
}

//获取职称信息列表
export function queryProfessionalInfo(data) {
  return request({
    url: '/human/personnel/professional/queryProfessionalInfo',
    method: 'get',
    params: data
  })
}

//删除工作经历信息
export function delWorkExperienceInfo(uuids) {
  return request({
    url: `/human/personnel/work/delete?uuid=${uuids}`,
    method: 'delete'
  })
}

//删除教育经历信息
export function delEducationalExperienceInfo(uuids) {
  return request({
    url: `/human/personnel/education/delete?uuid=${uuids}`,
    method: 'delete'
  })
}

//删除培训经历信息
export function delCultivateExperienceInfo(uuids) {
  return request({
    url: `/human/personnel/cultivate/delete?uuid=${uuids}`,
    method: 'delete'
  })
}

//删除家属信息
export function delFamilyInfo(uuids) {
  return request({
    url: `/human/personnel/family/delete?uuid=${uuids}`,
    method: 'delete'
  })
}

//删除职业资格信息
export function delOccupationInfo(uuids) {
  return request({
    url: `/human/personnel/occupation/delete?uuid=${uuids}`,
    method: 'delete'
  })
}

//删除职称信息
export function delProfessionalInfo(uuids) {
  return request({
    url: `/human/personnel/professional/delete?uuid=${uuids}`,
    method: 'delete'
  })
}

//获取工作经历列表
export function queryWorkInfo(empId) {
  return request({
    url: '/human/personnel/work/queryWorkInfo',
    method: 'get',
    params: {
      empId: empId
    }
  })
}

//获取教育经历列表
export function queryEducationalInfo(empId) {
  return request({
    url: '/human/personnel/education/queryEducationalInfo',
    method: 'get',
    params: {
      empId: empId
    }
  })
}

//获取培训经历列表
export function queryCultivateInfo(empId) {
  return request({
    url: '/human/personnel/cultivate/queryCultivateInfo',
    method: 'get',
    params: {
      empId: empId
    }
  })
}

//获取家属信息列表
export function queryFamily(empId) {
  return request({
    url: '/human/personnel/family/queryFamily',
    method: 'get',
    params: {
      empId: empId
    }
  })
}

//获取职业资格信息列表
export function queryOccupation(empId) {
  return request({
    url: '/human/personnel/occupation/queryOccupation',
    method: 'get',
    params: {
      empId: empId
    }
  })
}

//获取职称信息列表
export function queryProfessional(empId) {
  return request({
    url: '/human/personnel/professional/queryProfessional',
    method: 'get',
    params: {
      empId: empId
    }
  })
}
