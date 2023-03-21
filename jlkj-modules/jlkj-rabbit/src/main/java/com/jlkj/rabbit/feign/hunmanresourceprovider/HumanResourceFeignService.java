package com.jlkj.rabbit.feign.hunmanresourceprovider;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xin
 * @Description
 * @create 2022-04-28 13:40
 */
@Component
@FeignClient(value = "humanresource-service-provider")
public interface HumanResourceFeignService {
    /*****************************************板块资料********************************************/
    /**
     * 新增板块资料
     * @param humanresourceBoardDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/board/addBoardData")
    public AjaxResult addBoardData(@RequestBody HumanresourceBoardDTO humanresourceBoardDTO, @RequestHeader("token") String token);
    /**
     * 修改板块资料
     * @param humanresourceBoardDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/board/updateBoardData")
    public AjaxResult updateBoardData(@RequestBody HumanresourceBoardDTO humanresourceBoardDTO,@RequestHeader("token") String token);
    /**
     * 删除板块资料
     * @param humanresourceBoardDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/board/deleteBoardData")
    public AjaxResult deleteBoardData(@RequestBody HumanresourceBoardDTO humanresourceBoardDTO,@RequestHeader("token") String token);

    /*****************************************机构层级资料********************************************/
    /**
     * 新增机构层级数据
     * @param humanresourceOrganizationTierDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/organization/tier/addOrganizationTierData")
    public AjaxResult addOrganizationTierData(@RequestBody HumanresourceOrganizationTierDTO humanresourceOrganizationTierDTO, @RequestHeader("token") String token);
    /**
     * 修改机构层级数据
     * @param humanresourceOrganizationTierDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/organization/tier/updateOrganizationTierData")
    public AjaxResult updateOrganizationTierData(@RequestBody HumanresourceOrganizationTierDTO humanresourceOrganizationTierDTO,@RequestHeader("token") String token);
    /**
     * 删除机构层级数据
     * @param humanresourceOrganizationTierDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/organization/tier/deleteOrganizationTierData")
    public AjaxResult deleteOrganizationTierData(@RequestBody HumanresourceOrganizationTierDTO humanresourceOrganizationTierDTO,@RequestHeader("token") String token);

    /*****************************************机构类别资料********************************************/
    /**
     * 新增机构类别数据
     * @param humanresourceOrganizationTypeDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/organization/type/addOrganizationTypeData")
    public AjaxResult addOrganizationTypeData(@RequestBody HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO, @RequestHeader("token") String token);
    /**
     * 修改机构类别数据
     * @param humanresourceOrganizationTypeDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/organization/type/updateOrganizationTypeData")
    public AjaxResult updateOrganizationTypeData(@RequestBody HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO,@RequestHeader("token") String token);
    /**
     * 删除机构类别数据
     * @param humanresourceOrganizationTypeDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/organization/type/deleteOrganizationTypeData")
    public AjaxResult deleteOrganizationTypeData(@RequestBody HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO,@RequestHeader("token") String token);

    /*****************************************机构细类资料********************************************/
    /**
     * 新增机构细类数据
     * @param humanresourceOrganizationCategoryDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/organization/category/addOrganizationCategoryData")
    public AjaxResult addOrganizationCategoryData(@RequestBody HumanresourceOrganizationCategoryDTO humanresourceOrganizationCategoryDTO, @RequestHeader("token") String token);
    /**
     * 修改机构细类数据
     * @param humanresourceOrganizationCategoryDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/organization/category/updateOrganizationCategoryData")
    public AjaxResult updateOrganizationCategoryData(@RequestBody HumanresourceOrganizationCategoryDTO humanresourceOrganizationCategoryDTO,@RequestHeader("token") String token);
    /**
     * 删除机构细类数据
     * @param humanresourceOrganizationCategoryDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/organization/category/deleteOrganizationCategoryData")
    public AjaxResult deleteOrganizationCategoryData(@RequestBody HumanresourceOrganizationCategoryDTO humanresourceOrganizationCategoryDTO,@RequestHeader("token") String token);

    /*****************************************岗位序列资料********************************************/
    /**
     * 新增岗位序列数据
     * @param humanresourcePostSequenceDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/post/sequence/addPostSequenceData")
    public AjaxResult addPostSequenceData(@RequestBody HumanresourcePostSequenceDTO humanresourcePostSequenceDTO, @RequestHeader("token") String token);
    /**
     * 修改岗位序列数据
     * @param humanresourcePostSequenceDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/post/sequence/updatePostSequenceData")
    public AjaxResult updatePostSequenceData(@RequestBody HumanresourcePostSequenceDTO humanresourcePostSequenceDTO,@RequestHeader("token") String token);
    /**
     * 删除岗位序列数据
     * @param humanresourcePostSequenceDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/post/sequence/deletePostSequenceData")
    public AjaxResult deletePostSequenceData(@RequestBody HumanresourcePostSequenceDTO humanresourcePostSequenceDTO, @RequestHeader("token") String token);

    /*****************************************岗位类别资料********************************************/
    /**
     * 新增岗位类别数据
     * @param humanresourcePostTypeDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/post/type/addPostTypeData")
    public AjaxResult addPostTypeData(@RequestBody HumanresourcePostTypeDTO humanresourcePostTypeDTO,@RequestHeader("token") String token);
    /**
     * 修改岗位类别数据
     * @param humanresourcePostTypeDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/post/type/updatePostTypeData")
    public AjaxResult updatePostTypeData(@RequestBody HumanresourcePostTypeDTO humanresourcePostTypeDTO,@RequestHeader("token") String token);
    /**
     * 删除岗位类别数据
     * @param humanresourcePostTypeDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/post/type/deletePostTypeData")
    public AjaxResult deletePostTypeData(@RequestBody HumanresourcePostTypeDTO humanresourcePostTypeDTO,@RequestHeader("token") String token);

    /*****************************************职级资料********************************************/
    /**
     * 新增职级数据
     * @param humanresourcePositionsClassDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/positions/class/addPositionsClassData")
    public AjaxResult addPositionsClassData(@RequestBody HumanresourcePositionsClassDTO humanresourcePositionsClassDTO,@RequestHeader("token") String token);
    /**
     * 修改职级数据
     * @param humanresourcePositionsClassDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/positions/class/updatePositionsClassData")
    public AjaxResult updatePositionsClassData(@RequestBody HumanresourcePositionsClassDTO humanresourcePositionsClassDTO,@RequestHeader("token") String token);
    /**
     * 删除职级数据
     * @param humanresourcePositionsClassDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/positions/class/deletePositionsClassData")
    public AjaxResult deletePositionsClassData(@RequestBody HumanresourcePositionsClassDTO humanresourcePositionsClassDTO,@RequestHeader("token") String token);

    /*****************************************职位等级资料********************************************/
    /**
     * 新增职位等级
     * @param humanresourcePositionLevelDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/position/level/addPositionLevelData")
    public AjaxResult addPositionLevelData(@RequestBody HumanresourcePositionLevelDTO humanresourcePositionLevelDTO,@RequestHeader("token") String token);
    /**
     * 修改职位等级
     * @param humanresourcePositionLevelDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/position/level/updatePositionLevelData")
    public AjaxResult updatePositionLevelData(@RequestBody HumanresourcePositionLevelDTO humanresourcePositionLevelDTO,@RequestHeader("token") String token);
    /**
     * 删除职位等级
     * @param humanresourcePositionLevelDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/position/level/deletePositionLevelData")
    public AjaxResult deletePositionLevelData(@RequestBody HumanresourcePositionLevelDTO humanresourcePositionLevelDTO,@RequestHeader("token") String token);

    /*****************************************岗位工作地点资料********************************************/
    /**
     * 新增岗位工作地点数据
     * @param humanresourceWorkAddressDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/work/address/addWorkAddressData")
    public AjaxResult addWorkAddressData(@RequestBody HumanresourceWorkAddressDTO humanresourceWorkAddressDTO,@RequestHeader("token") String token);
    /**
     * 修改岗位工作地点数据
     * @param humanresourceWorkAddressDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/work/address/updateWorkAddressData")
    public AjaxResult updateWorkAddressData(@RequestBody HumanresourceWorkAddressDTO humanresourceWorkAddressDTO,@RequestHeader("token") String token);
    /**
     * 删除岗位工作地点数据
     * @param humanresourceWorkAddressDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/work/address/deleteWorkAddressData")
    public AjaxResult deleteWorkAddressData(@RequestBody HumanresourceWorkAddressDTO humanresourceWorkAddressDTO,@RequestHeader("token") String token);

    /*****************************************职位名称资料********************************************/
    /**
     * 新增职位名称数据
     * @param humanresourceJobTitleDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/job/title/addJobTitleData")
    public AjaxResult addJobTitleData(@RequestBody HumanresourceJobTitleDTO humanresourceJobTitleDTO,@RequestHeader("token") String token);
    /**
     * 修改职位名称数据
     * @param humanresourceJobTitleDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/job/title/updateJobTitleData")
    public AjaxResult updateJobTitleData(@RequestBody HumanresourceJobTitleDTO humanresourceJobTitleDTO,@RequestHeader("token") String token);
    /**
     * 删除职位名称数据
     * @param humanresourceJobTitleDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/job/title/deleteJobTitleData")
    public AjaxResult deleteJobTitleData(@RequestBody HumanresourceJobTitleDTO humanresourceJobTitleDTO,@RequestHeader("token") String token);

    /*****************************************专长类别资料********************************************/
    /**
     * 新增专长类别数据
     * @param humanresourceSpecialityTypeDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/speciality/type/addSpecialityTypeData")
    public AjaxResult addSpecialityTypeData(@RequestBody HumanresourceSpecialityTypeDTO humanresourceSpecialityTypeDTO,@RequestHeader("token") String token);
    /**
     * 修改专长类别数据
     * @param humanresourceSpecialityTypeDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/speciality/type/updateSpecialityTypeData")
    public AjaxResult updateSpecialityTypeData(@RequestBody HumanresourceSpecialityTypeDTO humanresourceSpecialityTypeDTO,@RequestHeader("token") String token);
    /**
     * 删除专长类别数据
     * @param humanresourceSpecialityTypeDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/speciality/type/deleteSpecialityTypeData")
    public AjaxResult deleteSpecialityTypeData(@RequestBody HumanresourceSpecialityTypeDTO humanresourceSpecialityTypeDTO,@RequestHeader("token") String token);


    /*****************************************专长数据资料********************************************/
    /**
     * 新增专长数据资料
     * @param humanresourceSpecialityDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/speciality/base/addSpecialityData")
    public AjaxResult addSpecialityData(@RequestBody HumanresourceSpecialityDTO humanresourceSpecialityDTO,@RequestHeader("token") String token);
    /**
     * 修改专长数据资料
     * @param humanresourceSpecialityDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/speciality/base/updateSpecialityData")
    public AjaxResult updateSpecialityData(@RequestBody HumanresourceSpecialityDTO humanresourceSpecialityDTO,@RequestHeader("token") String token);
    /**
     * 删除专长数据资料
     * @param humanresourceSpecialityDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/speciality/base/deleteSpecialityData")
    public AjaxResult deleteSpecialityData(@RequestBody HumanresourceSpecialityDTO humanresourceSpecialityDTO,@RequestHeader("token") String token);

    /*****************************************机构资料********************************************/
    /**
     * 新增机构资料
     * @param humanresourceOrganizationDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/organization/base/addOrganizationData")
    public AjaxResult addOrganizationData(@RequestBody HumanresourceOrganizationDTO humanresourceOrganizationDTO,@RequestHeader("token") String token);
    /**
     * 修改岗位资料
     * @param humanresourceOrganizationDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/organization/base/updateOrganizationData")
    public AjaxResult updateOrganizationData(@RequestBody HumanresourceOrganizationDTO humanresourceOrganizationDTO,@RequestHeader("token") String token);
    /**
     * 删除机构资料
     * @param humanresourceOrganizationDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/organization/base/deleteOrganizationData")
    public AjaxResult deleteOrganizationData(@RequestBody HumanresourceOrganizationDTO humanresourceOrganizationDTO,@RequestHeader("token") String token);

    /*****************************************岗位资料********************************************/
    /**
     * 新增岗位资料
     * @param humanresourcePostDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/post/base/addPostData")
    public AjaxResult addPostData(@RequestBody HumanresourcePostDTO humanresourcePostDTO,@RequestHeader("token") String token);
    /**
     * 修改岗位资料
     * @param humanresourcePostDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/post/base/updatePostData")
    public AjaxResult updatePostData(@RequestBody HumanresourcePostDTO humanresourcePostDTO,@RequestHeader("token") String token);
    /**
     * 删除岗位资料
     * @param humanresourcePostDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/post/base/deletePostData")
    public AjaxResult deletePostData(@RequestBody HumanresourcePostDTO humanresourcePostDTO,@RequestHeader("token") String token);

    /*****************************************人员资料********************************************/
    /**
     * 新增人员资料
     * @param humanresourcePersonnelDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/personnel/base/addPersonnelData")
    public AjaxResult addPersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token);
    /**
     * 修改人员资料
     * @param humanresourcePersonnelDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/personnel/base/updatePersonnelData")
    public AjaxResult updatePersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token);
    /**
     * 删除人员资料
     * @param humanresourcePersonnelDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/personnel/base/deletePersonnelData")
    public AjaxResult deletePersonnelData(@RequestBody HumanresourcePersonnelDTO humanresourcePersonnelDTO,@RequestHeader("token") String token);

    /*****************************************人员排班资料********************************************/
    /**
     * 新增人员排班资料
     * @param humanresourcePersonnelSchedulingDTO
     * @param token
     * @return
     */
    @PostMapping("/humanresource/personnel/scheduling/addPersonnelSchedulingData")
    public AjaxResult addPersonnelSchedulingData(@RequestBody HumanresourcePersonnelSchedulingDTO humanresourcePersonnelSchedulingDTO,@RequestHeader("token") String token);
    /**
     * 修改人员排班资料
     * @param humanresourcePersonnelSchedulingDTO
     * @param token
     * @return
     */
    @PutMapping("/humanresource/personnel/scheduling/updatePersonnelSchedulingData")
    public AjaxResult updatePersonnelSchedulingData(@RequestBody HumanresourcePersonnelSchedulingDTO humanresourcePersonnelSchedulingDTO,@RequestHeader("token") String token);
    /**
     * 删除人员排班资料
     * @param humanresourcePersonnelSchedulingDTO
     * @param token
     * @return
     */
    @DeleteMapping("/humanresource/personnel/scheduling/deletePersonnelSchedulingData")
    public AjaxResult deletePersonnelSchedulingData(@RequestBody HumanresourcePersonnelSchedulingDTO humanresourcePersonnelSchedulingDTO,@RequestHeader("token") String token);
}
