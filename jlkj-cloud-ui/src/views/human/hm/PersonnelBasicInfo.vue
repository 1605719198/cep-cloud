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
                  <el-input v-model="query.empNo"
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
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="人事基本信息" name="first" :key="'first'">
            <HumanAffairsBaseInfo v-if="isFirst" ref="humanAffairsBaseInfo" @empId="passData"/>
          </el-tab-pane>
          <el-tab-pane label="工作经历信息" name="second" :key="'second'">
            <WorkExperienceInfo v-if="isSecond" ref="workExperienceInfo"/>
          </el-tab-pane>
          <el-tab-pane label="教育经历信息" name="third" :key="'third'">
            <EducationalExperienceInfo v-if="isThird" ref="educationalExperienceInfo"/>
          </el-tab-pane>
          <el-tab-pane label="培训经历信息" name="fourth" :key="'fourth'">
            <CultivateExperienceInfo v-if="isFourth" ref="cultivateExperienceInfo"/>
          </el-tab-pane>
          <el-tab-pane label="家属信息" name="fifth" :key="'fifth'">
            <FamilyInfo v-if="isFifth" ref="familyInfo"/>
          </el-tab-pane>
          <el-tab-pane label="职业资格信息" name="sixth" :key="'sixth'">
            <OccupationInfo v-if="isSixth" ref="occupationInfo"/>
          </el-tab-pane>
          <el-tab-pane label="职称信息" name="seventh" :key="'seventh'">
            <ProfessionalInfo v-if="isSeventh" ref="professionalInfo"/>
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
        empNo: undefined,
        empId: undefined
      },
      activeName: 'first',
      isFirst: true,
      isSecond: false,
      isThird: false,
      isFourth: false,
      isFifth: false,
      isSixth: false,
      isSeventh: false,
    }
  },
  methods: {
    /** 查询人员基本信息 */
    handleQuery() {
      this.activeName = 'first'
      this.isFirst = true
      this.isSecond = false
      this.isThird = false
      this.isFourth = false
      this.isFifth = false
      this.isSixth = false
      this.isSeventh = false
      this.$nextTick(function () {
        this.$refs.humanAffairsBaseInfo.queryInfo(this.query)
      })
    },
    /** 重置按钮操作 */
    handleEmpty() {
      this.query = {
        compId: '',
        empNo: ''
      }
      this.$refs.humanAffairsBaseInfo.handleEmpty()
    },
    handleClick(tab) {
      if (tab.name === 'first') {
        this.$nextTick(function () {
          this.handleQuery()
        })
        this.isFirst = true
        this.isSecond = false
        this.isThird = false
        this.isFourth = false
        this.isFifth = false
        this.isSixth = false
        this.isSeventh = false
      } else if (tab.name === 'second') {
        this.$nextTick(function () {
          this.$refs.workExperienceInfo.queryWorkExperienceInfo(this.query)
        })
        this.isFirst = false
        this.isSecond = true
        this.isThird = false
        this.isFourth = false
        this.isFifth = false
        this.isSixth = false
        this.isSeventh = false
      } else if (tab.name === 'third') {
        this.$nextTick(function () {
          this.$refs.educationalExperienceInfo.queryEducationalExperienceInfo(this.query)
        })
        this.isFirst = false
        this.isSecond = false
        this.isThird = true
        this.isFourth = false
        this.isFifth = false
        this.isSixth = false
        this.isSeventh = false
      } else if (tab.name === 'fourth') {
        this.$nextTick(function () {
          this.$refs.cultivateExperienceInfo.queryCultivateExperienceInfo(this.query)
        })
        this.isFirst = false
        this.isSecond = false
        this.isThird = false
        this.isFourth = true
        this.isFifth = false
        this.isSixth = false
        this.isSeventh = false
      } else if (tab.name === 'fifth') {
        this.$nextTick(function () {
          this.$refs.familyInfo.queryFamilyInfo(this.query)
        })
        this.isFirst = false
        this.isSecond = false
        this.isThird = false
        this.isFourth = false
        this.isFifth = true
        this.isSixth = false
        this.isSeventh = false
      } else if (tab.name === 'sixth') {
        this.$nextTick(function () {
          this.$refs.occupationInfo.queryOccupationInfo(this.query)
        })
        this.isFirst = false
        this.isSecond = false
        this.isThird = false
        this.isFourth = false
        this.isFifth = false
        this.isSixth = true
        this.isSeventh = false
      } else if (tab.name === 'seventh') {
        this.$nextTick(function () {
          this.$refs.professionalInfo.queryProfessionalInfo(this.query)
        })
        this.isFirst = false
        this.isSecond = false
        this.isThird = false
        this.isFourth = false
        this.isFifth = false
        this.isSixth = false
        this.isSeventh = true
      }
    },
    passData(data) {
      this.query.empId = data
    }
  }
}
</script>

<style scoped>
.el-select {
  width: 100%;
}
</style>
