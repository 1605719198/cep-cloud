<template>
  <div>
    <el-button type="primary" size="medium" @click="handleSave">保存</el-button>
    <el-button type="primary" size="medium" :disabled="multiple" @click="handleDelete">删除</el-button>
    <el-button type="success" size="medium" @click="addLine">添加行信息</el-button>
    <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules">
      <el-table ref="table-input" class="table" :data="baseForm.professionalList" @selection-change="handleSelectionChange" style="margin-top: 10px" highlight-current-row>
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column label="入企前/后" align="center" key="ifInComp" prop="ifInComp">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.ifInComp'">
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
        <el-table-column label="职业类别" align="center" key="proTypeId" prop="proTypeId">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.proTypeId'">
              <el-select v-model="scope.row.proTypeId" @change="handleChange">
                <el-option
                  v-for="dict in baseInfoData.professionalTitleCategory"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.uuid"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="职称专业" align="center" key="proSpecId" prop="proSpecId" :render-header="addRedStar">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.proSpecId'">
              <el-select v-model="scope.row.proSpecId">
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
        <el-table-column label="职称级别" align="center" key="proTierId" prop="proTierId" :render-header="addRedStar">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.proTierId'">
              <el-select v-model="scope.row.proTierId">
                <el-option
                  v-for="dict in baseInfoData.professionalTitleLevel"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="证书编号" align="center" key="cerNo" prop="cerNo" :render-header="addRedStar">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.cerNo'" :rules="rules.cerNo">
              <el-input v-model="scope.row.cerNo" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`professionalList.${scope.$index}.cerNo`)"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="评定日期" align="center" key="cerDate" prop="cerDate" width="230">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.cerDate'">
              <el-date-picker placeholder="请选择评定日期" v-model="scope.row.cerDate" type="date" clearable @focus="$refs.baseForm.clearValidate(`professionalList.${scope.$index}.cerDate`)"></el-date-picker>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="发证机关" align="center" key="cerComp" prop="cerComp">
          <template v-slot="scope">
            <el-form-item :prop="'professionalList.'+scope.$index+'.cerComp'">
              <el-input v-model="scope.row.cerComp" placeholder="请输入" clearable @focus="$refs.baseForm.clearValidate(`professionalList.${scope.$index}.cerComp`)"></el-input>
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
import {
  addProfessionalData,
  delProfessionalInfo,
  queryProfessionalInfo,
  queryProfessional
} from "@/api/human/hm/personnelBasicInfo";

export default {
  name: "ProfessionalInfo",
  data() {
    return {
      baseForm: {
        professionalList: [
          {startDate: ''}
        ]
      },
      // 非多个禁用
      multiple: true,
      baseInfoData: [],
      index: 0,
      degreeMajorSpecialization: [],
      //人员基本信息表id
      empId: undefined,
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'ifInCompany',
          'professionalTitleCategory',
          'professionalTitleLevel']
      },
      rules: {
        cerNo: [
          {required: true, message: "请输入", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    queryProfessionalInfo(query) {
      this.empId = query.empId
      queryProfessionalInfo(query).then(res => {
        this.baseForm.professionalList = res.data
      })
    },
    getList() {
      queryProfessional(this.empId).then(res => {
        this.baseForm.professionalList = res.data
      })
    },
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
      this.baseForm.professionalList.push(newLine)
    },
    handleSave() {
      for (const item of this.baseForm.professionalList) {
        item.empId = this.empId
      }
      addProfessionalData(this.baseForm).then(res => {
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
        return delProfessionalInfo(uuids);
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

<style scoped>

</style>
