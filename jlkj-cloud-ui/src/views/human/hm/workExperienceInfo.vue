<template>
  <div>
    <div>
      <el-row>
        <el-col>
          <span style="font-size: 18px">【经历资料】</span>
        </el-col>
      </el-row>
      <div style="margin-top: 10px">
        <el-button type="primary" size="medium" @click="handleSave">保存</el-button>
        <el-button type="primary" size="medium" :disabled="multiple" @click="handleDelete">删除</el-button>
        <el-button type="success" size="medium" @click="addLine">添加行信息</el-button>
      </div>
      <div class="head-container" style="height: 30vh;width: 100%;">
        <el-scrollbar style="height: 100%;">
          <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules">
            <el-table ref="table-input" class="table" :data="baseForm.workExperienceList" :header-cell-class-name="cellClass" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row :cell-style="{paddingBottom:'0px'}">
              <el-table-column type="selection" width="100" align="center" />
              <el-table-column label="到职日期" align="center" key="startDate" prop="startDate" width="230">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.startDate'" :rules="rules.startDate">
                    <el-date-picker placeholder="请选择到职日期" v-model="scope.row.startDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.startDate`)"></el-date-picker>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="离职日期" align="center" key="endDate" prop="endDate" width="230">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.endDate'">
                    <el-date-picker placeholder="请选择到职日期" v-model="scope.row.endDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.endDate`)"></el-date-picker>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="工作单位" align="center" key="comp" prop="comp">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.comp'">
                    <el-input v-model="scope.row.comp" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.comp`)"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="职务（岗位）" align="center" key="post" prop="post">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.post'">
                    <el-input v-model="scope.row.post" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.post`)"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="证明人" align="center" key="prover" prop="prover">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.prover'">
                    <el-input v-model="scope.row.prover" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.prover`)"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="证明人联系方式" align="center" key="tele" prop="tele">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.tele'">
                    <el-input v-model="scope.row.tele" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.tele`)"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="离职原因" align="center" key="changeReasonId" prop="changeReasonId">
                <template v-slot="scope">
                  <el-form-item :prop="'workExperienceList.'+scope.$index+'.changeReasonId'">
                    <el-input v-model="scope.row.changeReasonId" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`workExperienceList.${scope.$index}.changeReasonId`)"></el-input>
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
    <div style="margin-top: 50px">
      <el-row>
        <el-col>
          <span style="font-size: 18px">【公司内经历资料】</span>
        </el-col>
      </el-row>
      <div class="head-container" style="height: 30vh;width: 100%;">
        <el-scrollbar style="height: 100%;">
          <el-table :data="baseForm.companyExperienceList">
            <el-table-column label="开始日期" align="center" key="startDate" prop="startDate" width="300"/>
            <el-table-column label="截止日期" align="center" key="endDate" prop="endDate" width="250"/>
            <el-table-column label="职务（岗位）" align="center" key="post" prop="post" width="620"/>
            <el-table-column label="异动原因" align="center" key="changeReasonId" prop="changeReasonId" />
            <el-table-column label="修改人" align="center" key="creator" prop="creator" />
            <el-table-column label="修改日期" align="center" key="createDate" prop="createDate" />
          </el-table>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<script>
import { addWorkExperienceData, queryWorkExperienceInfo, delWorkExperienceInfo, queryWorkInfo } from "@/api/human/hm/personnelBasicInfo";
export default {
  name: "WorkExperienceInfo",
  data() {
    return {
      baseForm: {
        // 工作经历数据
        workExperienceList: [],
        companyExperienceList: [],
      },
      index: 0,
      // 非多个禁用
      multiple: true,
      // 选中数组
      ids: [],
      //人员基本信息表id
      empId: '',
      rules: {
        startDate: [
          { required: true, message: "请选择", trigger: "blur" }
        ],
      }
    }
  },
  methods: {
    queryWorkExperienceInfo(query) {
      this.empId = query.empId
      queryWorkExperienceInfo(query).then(res => {
        this.baseForm.workExperienceList = res.data
      })
    },
    getList() {
      queryWorkInfo(this.empId).then(res => {
        this.baseForm.workExperienceList = res.data
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
      this.baseForm.workExperienceList.push(newLine)
    },
    // 设置表头的class
    cellClass(row){
      if (row.columnIndex === 0) {
        return 'addAllChecked'
      }
    },
    handleSave() {
      for (const item of this.baseForm.workExperienceList) {
        item.empId = this.empId
      }
      addWorkExperienceData(this.baseForm).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        }
        this.getList()
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + uuids + '"的数据项？').then(function() {
        return delWorkExperienceInfo(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
}
</script>

<style>
/* 去掉全选按钮 */
.el-table .addAllChecked .cell .el-checkbox__inner{
  /* display: none !important;  */
  margin-left: -170px;
}
.el-table .addAllChecked .cell::before{
  content: '入企前';
  text-align: center;
  margin-left: 15px;
}
.el-form-item {
  margin-bottom: 11px;
}
.el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-scrollbar__bar.is-horizontal {
  display: none;
}
</style>
