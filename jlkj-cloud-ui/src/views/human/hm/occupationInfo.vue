<template>
  <div>
    <el-button v-hasPermi="['human:occupationInfo:save']" type="primary" size="medium" plain @click="handleSave">保存</el-button>
    <el-button v-hasPermi="['human:occupationInfo:remove']" type="danger" size="medium" plain :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" plain @click="addLine">添加行信息</el-button>
    <div class="head-container" style="height: 30vh;width: 100%;">
      <el-scrollbar style="height: 100%;">
        <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules">
          <el-table ref="table-input" class="table" :data="baseForm.occupationList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row :cell-style="{paddingBottom:'0px'}">
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
            <el-table-column label="职业证书编号" align="center" key="vocCerNo" prop="vocCerNo" :render-header="addRedStar">
              <template v-slot="scope">
                <el-form-item :prop="'occupationList.'+scope.$index+'.vocCerNo'" :rules="rules.vocCerNo">
                  <el-input v-model="scope.row.vocCerNo" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.vocCerNo`)" :maxlength="30" show-word-limit></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="职业类别" align="center" key="vocType" prop="vocType">
              <template v-slot="scope">
                <el-form-item :prop="'occupationList.'+scope.$index+'.vocType'">
                  <el-input v-model="scope.row.vocType" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.vocType`)" :maxlength="20" show-word-limit></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="名称" align="center" key="name" prop="name" :render-header="addRedStar">
              <template v-slot="scope">
                <el-form-item :prop="'occupationList.'+scope.$index+'.name'" :rules="rules.name">
                  <el-input v-model="scope.row.name" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.name`)" :maxlength="20" show-word-limit></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="证书等级" align="center" key="cerTier" prop="cerTier">
              <template v-slot="scope">
                <el-form-item :prop="'occupationList.'+scope.$index+'.cerTier'">
                  <el-input v-model="scope.row.cerTier" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.cerTier`)" :maxlength="10" show-word-limit></el-input>
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
                <el-form-item :prop="'occupationList.'+scope.$index+'.cerComp'">
                  <el-input v-model="scope.row.cerComp" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`occupationList.${scope.$index}.cerComp`)" :maxlength="20" show-word-limit></el-input>
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
  addOccupationData,
  delOccupationInfo,
  queryOccupationInfo,
  queryOccupation
} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "OccupationInfo",
  data() {
    return {
      baseForm: {
        occupationList: []
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      //人员基本信息表id
      empId: undefined,
      compId: undefined,
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'ifInCompany']
      },
      rules: {
        vocCerNo: [
          {required: true, message: "请输入", trigger: "blur"}
        ],
        name: [
          {required: true, message: "请输入", trigger: "blur"},
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
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
    queryOccupationInfo(query) {
      this.empId = query.empId
      this.compId = query.compId
      queryOccupationInfo(query).then(res => {
        this.baseForm.occupationList = res.data
      })
    },
    getList() {
      queryOccupation(this.empId).then(res => {
        this.baseForm.occupationList = res.data
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
      this.baseForm.occupationList.push(newLine)
    },
    handleSave() {
      for (const item of this.baseForm.occupationList) {
        item.empId = this.empId
        item.compId = this.compId
      }
      addOccupationData(this.baseForm).then(res => {
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
        return delOccupationInfo(uuids);
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
