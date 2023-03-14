<template>
  <div>
    <el-button type="primary" size="medium" @click="handleSave">保存</el-button>
    <el-button type="primary" size="medium" :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" @click="addLine">添加行信息</el-button>
    <el-form class="base-form" ref="baseForm" :model="baseForm">
      <el-table ref="table-input" class="table" :data="baseForm.occupationList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row>
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column label="入企前/后" align="center" key="ifInComp" prop="ifInComp">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.ifInComp'">
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
        <el-table-column label="职业证书编号" align="center" key="vocCerNo" prop="vocCerNo">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.vocCerNo'" class="all">
              <el-input v-model="scope.row.vocCerNo" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.vocCerNo`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="职业类别" align="center" key="vocType" prop="vocType">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.vocType'" class="all">
              <el-input v-model="scope.row.vocType" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.vocType`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="名称" align="center" key="name" prop="name">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.name'" class="all">
              <el-input v-model="scope.row.name" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.name`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="证书等级" align="center" key="cerTier" prop="cerTier">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.cerTier'" class="all">
              <el-input v-model="scope.row.cerTier" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.cerTier`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="发证日期" align="center" key="cerDate" prop="cerDate" width="230">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.cerDate'">
              <el-date-picker placeholder="请选择发证日期" v-model="scope.row.cerDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.cerDate`)"></el-date-picker>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="发证机关" align="center" key="cerComp" prop="cerComp">
          <template v-slot="scope">
            <el-form-item :prop="'occupationList.'+scope.$index+'.cerComp'" class="all">
              <el-input v-model="scope.row.cerComp" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.cerComp`)"></el-input>
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
import {addOccupationData} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "OccupationInfo",
  data() {
    return {
      baseForm: {
        occupationList: [
          {startDate: ''}
        ]
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'ifInCompany']
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
      this.baseForm.occupationList.push(newLine)
    },
    handleSave() {
      addOccupationData(this.baseForm).then(res => {
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
