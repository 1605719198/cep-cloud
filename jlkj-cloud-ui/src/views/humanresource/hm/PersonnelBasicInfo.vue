<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body " style="width: 98%;border: 0;">
        <div class="avue-crud__search"
             style="border: 0">
          <el-form :model="query" ref="queryForm" label-width="40px">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="公司">
                  <el-select v-model="query.compId" placeholder="请选择公司">
                    <el-option
                      v-for="dict in dict.type.comp_id"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="工号">
                  <el-input v-model="query.jobNumber"
                            placeholder="请输入工号" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item>
                    <el-button v-hasPermi="['sampleBasicData_queryList']"
                               size="mini"
                               type="primary"
                               @click="handleQuery"
                               icon="el-icon-search">搜 索</el-button>
                    <el-button size="mini"
                               type="default"
                               @click="handleEmpty"
                               icon="el-icon-refresh-left">重 置</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="人事基本信息" name="first">
            <HumanAffairsBaseInfo ref="humanAffairsBaseInfo"/>
          </el-tab-pane>
          <el-tab-pane label="工作经历信息" name="second">
            <WorkExperienceInfo/>
          </el-tab-pane>
          <el-tab-pane label="教育经历信息" name="third">
            <EducationalExperienceInfo/>
          </el-tab-pane>
          <el-tab-pane label="培训经历信息" name="fourth">
            <CultivateExperienceInfo/>
          </el-tab-pane>
          <el-tab-pane label="家属信息" name="fifth">
            <FamilyInfo/>
          </el-tab-pane>
          <el-tab-pane label="职业资格信息" name="sixth">
            <OccupationInfo/>
          </el-tab-pane>
          <el-tab-pane label="职称信息" name="seventh">
            <ProfessionalInfo/>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import HumanAffairsBaseInfo from "@/views/humanresource/hm/HumanAffairsBaseInfo";
import WorkExperienceInfo from "@/views/humanresource/hm/WorkExperienceInfo";
import EducationalExperienceInfo from "@/views/humanresource/hm/EducationalExperienceInfo";
import CultivateExperienceInfo from "@/views/humanresource/hm/CultivateExperienceInfo";
import FamilyInfo from "@/views/humanresource/hm/FamilyInfo";
import OccupationInfo from "@/views/humanresource/hm/OccupationInfo";
import ProfessionalInfo from "@/views/humanresource/hm/ProfessionalInfo";
export default {
  name: "PersonnelBasicInfo",
  dicts: ['comp_id'],
  components:{
    HumanAffairsBaseInfo,
    WorkExperienceInfo,
    EducationalExperienceInfo,
    CultivateExperienceInfo,
    FamilyInfo,
    OccupationInfo,
    ProfessionalInfo
  },
  data() {
    return {
      //查询参数
      query: {
        compId: undefined,
        jobNumber: undefined
      },
      activeName: 'first',
    }
  },
  methods: {
    /** 查询人员基本信息 */
    handleQuery() {
      this.$refs.humanAffairsBaseInfo.queryInfo(this.query)
    },
    /** 重置按钮操作 */
    handleEmpty() {
      this.query = {
        compId: '',
        jobNumber: ''
      }
      this.$refs.humanAffairsBaseInfo.handleEmpty()
    },
  }
}
</script>

<style scoped>
.el-select {
  width: 100%;
}
</style>
