<template>
  <div>
    <el-button type="primary" size="medium" @click="handleSave">保存</el-button>
    <el-button type="primary" size="medium" :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" @click="addLine">添加行信息</el-button>
    <el-form class="base-form" ref="baseForm" :model="baseForm">
      <el-table ref="table-input" class="table" :data="baseForm.familyList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row>
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column label="与本人关系" align="center" key="relationId" prop="relationId">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.relationId'">
              <el-select v-model="scope.row.relationId">
                <el-option
                  v-for="dict in baseInfoData.relation"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="姓名" align="center" key="name" prop="name">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.name'">
              <el-input v-model="scope.row.name" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`familyList.${scope.$index}.name`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="出生日期" align="center" key="birthDate" prop="birthDate" width="230">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.birthDate'">
              <el-date-picker placeholder="请选择出生日期" v-model="scope.row.birthDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`familyList.${scope.$index}.birthDate`)"></el-date-picker>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="现居住地" align="center" key="homeAddress" prop="homeAddress">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.homeAddress'">
              <el-input v-model="scope.row.homeAddress" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`familyList.${scope.$index}.homeAddress`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="户口所在地" align="center" key="registerAddress" prop="registerAddress">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.registerAddress'">
              <el-input v-model="scope.row.registerAddress" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`familyList.${scope.$index}.registerAddress`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="工作单位" align="center" key="workComp" prop="workComp">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.workComp'">
              <el-input v-model="scope.row.workComp" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`familyList.${scope.$index}.workComp`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="联系电话" align="center" key="tele" prop="tele">
          <template v-slot="scope">
            <el-form-item :prop="'familyList.'+scope.$index+'.tele'">
              <el-input v-model="scope.row.tele" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`familyList.${scope.$index}.tele`)"></el-input>
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
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {addCultivateExperienceData} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "FamilyInfo",
  data() {
    return {
      baseForm: {
        familyList: [
          {startDate: ''}
        ]
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      baseInfo: {
        baseInfoList: [
          'relation']
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
    // 增加一个空行, 用于录入或显示第一行
    addLine() {
      const newLine = {
        startDate: ""
      }
      this.index++
      this.baseForm.familyList.push(newLine)
    },
    handleSave() {
      addFamilyData(this.baseForm).then(res => {
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
