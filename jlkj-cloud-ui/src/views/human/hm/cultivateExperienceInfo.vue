<template>
  <div>
    <el-button v-hasPermi="['human:cultivateExperienceInfo:save']" type="primary" size="medium" plain @click="handleSave">保存</el-button>
    <el-button v-hasPermi="['human:cultivateExperienceInfo:remove']" type="danger" size="medium" plain :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" plain @click="addLine">添加行信息</el-button>
    <div class="head-container" style="height: 30vh;width: 100%;">
      <el-scrollbar style="height: 100%;">
        <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules">
          <el-table ref="table-input" class="table" :data="baseForm.cultivateExperienceList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row :cell-style="{paddingBottom:'0px'}">
            <el-table-column type="selection" width="100" align="center" />
            <el-table-column label="入企前/后" align="center" key="ifInComp" prop="ifInComp">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.ifInComp'">
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
            <el-table-column label="开始日期" align="center" key="startDate" prop="startDate" width="230">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.startDate'" :rules="rules.startDate">
                  <el-date-picker placeholder="请选择开始日期" v-model="scope.row.startDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.startDate`)"></el-date-picker>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="结束日期" align="center" key="endDate" prop="endDate" width="230">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.endDate'">
                  <el-date-picker placeholder="请选择结束日期" v-model="scope.row.endDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.endDate`)"></el-date-picker>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="授课单位" align="center" key="trainComp" prop="trainComp">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.trainComp'">
                  <el-input v-model="scope.row.trainComp" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.trainComp`)"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="类别（内容）" align="center" key="content" prop="content">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.content'">
                  <el-input v-model="scope.row.content" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.content`)"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="项目" align="center" key="project" prop="project">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.project'">
                  <el-input v-model="scope.row.project" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.project`)"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="获得资格" align="center" key="qualification" prop="qualification">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.qualification'">
                  <el-input v-model="scope.row.qualification" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.qualification`)"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="获得证书" align="center" key="certificate" prop="certificate">
              <template v-slot="scope">
                <el-form-item :prop="'cultivateExperienceList.'+scope.$index+'.certificate'">
                  <el-input v-model="scope.row.certificate" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`cultivateExperienceList.${scope.$index}.certificate`)"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="修改人" align="center" key="creator" prop="creator" />
            <el-table-column label="修改日期" align="center" key="createDate" prop="createDate" />
          </el-table>
        </el-form>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {
  addCultivateExperienceData, delCultivateExperienceInfo,
  queryCultivateExperienceInfo,
  queryCultivateInfo
} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "CultivateExperienceInfo",
  data() {
    return {
      baseForm: {
        cultivateExperienceList: []
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      //人员基本信息表id
      empId: undefined,
      baseInfo: {
        baseInfoList: [
          'ifInCompany']
      },
      rules: {
        startDate: [
          {required: true, message: "请选择", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    queryCultivateExperienceInfo(query) {
      this.empId = query.empId
      queryCultivateExperienceInfo(query).then(res => {
        this.baseForm.cultivateExperienceList = res.data
      })
    },
    getList() {
      queryCultivateInfo(this.empId).then(res => {
        this.baseForm.cultivateExperienceList = res.data
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid);
      this.multiple = !selection.length;
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine() {
      const newLine = {
        startDate: ""
      }
      this.index++
      this.baseForm.cultivateExperienceList.push(newLine)
    },
    handleSave() {
      for (const item of this.baseForm.cultivateExperienceList) {
        item.empId = this.empId
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          addCultivateExperienceData(this.baseForm).then(res => {
            if (res.code == 200) {
              this.$message({
                type: 'success',
                message: res.msg
              })
            }
            this.getList();
          })
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + uuids + '"的数据项？').then(function() {
        return delCultivateExperienceInfo(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
}
</script>

<style>
.el-form-item {
  margin-bottom: 11px;
}
</style>
