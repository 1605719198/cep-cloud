<template>
  <div>
    <el-button type="primary" size="medium" @click="handleSave">保存</el-button>
    <el-button type="primary" size="medium" :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" @click="addLine">添加行信息</el-button>
    <el-form class="base-form" ref="baseForm" :model="baseForm">
      <el-table ref="table-input" class="table" :data="baseForm.educationalExperienceList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row>
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column label="入企前/后" align="center" key="ifInComp" prop="ifInComp">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.ifInComp'">
              <el-select v-model="scope.row.ifInComp">
                <el-option
                  v-for="dict in baseInfoData.ifInCompany"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="入学日期" align="center" key="startDate" prop="startDate" width="230">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.startDate'">
              <el-date-picker placeholder="请选择入学日期" v-model="scope.row.startDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`educationalExperienceList.${scope.$index}.startDate`)"></el-date-picker>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="毕业时间" align="center" key="endDate" prop="endDate" width="230">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.endDate'">
              <el-date-picker placeholder="请选择毕业时间" v-model="scope.row.endDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`educationalExperienceList.${scope.$index}.endDate`)"></el-date-picker>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="学校" align="center" key="school" prop="school">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.school'" class="all">
              <el-input v-model="scope.row.school" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`educationalExperienceList.${scope.$index}.school`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="专业" align="center" key="specId" prop="specId">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.specId'">
              <el-select v-model="scope.row.specId" @change="handleChange">
                <el-option
                  v-for="dict in baseInfoData.profession"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.uuid"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="专业细分" align="center" key="psubSpecId" prop="psubSpecId">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.psubSpecId'">
              <el-select v-model="scope.row.psubSpecId">
                <el-option
                  v-for="dict in degreeMajorSpecialization"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="培养方式" align="center" key="trainStypeId" prop="trainStypeId">
          <template v-slot="scope">
            <el-form-item :prop="'educationalExperienceList.'+scope.$index+'.trainStypeId'">
              <el-select v-model="scope.row.trainStypeId">
                <el-option
                  v-for="dict in baseInfoData.trainingMethod"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="修改人" align="center" key="creator" prop="creator" />
        <el-table-column label="修改日期" align="center" key="createDate" prop="createDate" />
      </el-table>
    </el-form>
  </div>
</template>

<script>
import {getBaseInfo, getDegreeMajorSpecialization} from "@/api/human/hm/baseInfo";
import {addEducationExperienceData} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "EducationalExperienceInfo",
  data() {
    return {
      baseForm: {
        educationalExperienceList: [
          {startDate: ''}
        ]
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      degreeMajorSpecialization: [],
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'ifInCompany',
          'profession',
          'trainingMethod',
          'qualification',
          'degree']
      },
    }
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid);
      this.multiple = !selection.length;
    },
    handleChange(value) {
      this.baseInfo.uuid = value
      this.degreeMajorSpecialization = []
      getDegreeMajorSpecialization(this.baseInfo).then(response => {
        this.degreeMajorSpecialization = response.data
      });
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine() {
      const newLine = {
        startDate: ""
      }
      this.index++
      this.baseForm.educationalExperienceList.push(newLine)
    },
    handleSave() {
      addEducationExperienceData(this.baseForm).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      console.log(row);
      // const userIds = row.userId || this.ids;
      // this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function() {
      //   return delUser(userIds);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("删除成功");
      // }).catch(() => {});
    },
  }
}
</script>

<style scoped>

</style>
