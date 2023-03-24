<template>
  <div>
    <el-button type="primary" size="medium" plain @click="handleSave">保存</el-button>
    <el-button type="danger" size="medium" plain :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" plain @click="addLine">添加行信息</el-button>
    <div class="head-container" style="height: 30vh;width: 100%;">
      <el-scrollbar style="height: 100%;">
        <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules">
          <el-table class="table" :data="baseForm.familyList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row :cell-style="{paddingBottom:'0px'}">
            <el-table-column type="selection" width="100" align="center" />
            <el-table-column label="与本人关系" align="center" :render-header="addRedStar">
              <template v-slot="scope">
                <el-form-item :prop="'familyList.'+scope.$index+'.relationId'" :rules="rules.relationId">
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
                <el-form-item :prop="'familyList.'+scope.$index+'.name'" :rules="rules.name">
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
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {addFamilyData, delFamilyInfo, queryFamilyInfo, queryFamily} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "FamilyInfo",
  data() {
    return {
      baseForm: {
        familyList: []
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      //人员基本信息表id
      empId: undefined,
      baseInfo: {
        baseInfoList: [
          'relation']
      },
      rules: {
        relationId: [
          { required: true, message: "请选择", trigger: "blur" }
        ],
        name: [
          {
            required: true,
            message: "请输入姓名",
            trigger: "blur"
          }
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
    queryFamilyInfo(query) {
      this.empId = query.empId
      queryFamilyInfo(query).then(res => {
        this.baseForm.familyList = res.data
      })
    },
    getList() {
      queryFamily(this.empId).then(res => {
        this.baseForm.familyList = res.data
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
      this.baseForm.familyList.push(newLine)
    },
    handleSave() {
      for (const item of this.baseForm.familyList) {
        item.empId = this.empId
      }
      addFamilyData(this.baseForm).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        }
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + uuids + '"的数据项？').then(function() {
        return delFamilyInfo(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 必选标识
    addRedStar(h, { column }) {
      return [
        h('span', column.label + ' '),
        h('span', { style: 'color: #F56C6C' }, '*')
      ];
    },
  }
}
</script>

<style>
.el-form-item {
  margin-bottom: 11px;
}
</style>
