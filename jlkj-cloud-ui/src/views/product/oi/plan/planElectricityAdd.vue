<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="left" label-width="80px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="计划年度" prop="planYear">
            <el-date-picker v-model="form.planYear" type="year" placeholder="选择年" value-format="yyyy"
              :disabled="disabled" />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="disabled">
          <el-form-item label="计划月份" prop="planMonth">
            <el-input v-model="form.planMonth" :disabled="disabled" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-table :data="form.tableData" stripe border style="width: 100%">
        <el-table-column prop="targetItemName" label="指标项目" />
        <el-table-column v-if="type === 'add'" prop="measures_and_goals" label="措施及完成目标">
          <template slot-scope="scope">
            <el-form-item label="" :prop="'tableData.'+scope.$index+'.measures_and_goals'"
              :rules="rules.measures_and_goals" label-width="0px" size="small">
              <el-input v-model="scope.row.measures_and_goals" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column v-if="type === 'add'" prop="responsible_person" label="责任人">
          <template slot-scope="scope">
            <el-form-item label="" :prop="'tableData.'+scope.$index+'.responsible_person'"
              :rules="rules.responsible_person" label-width="0px" size="small">
              <el-input v-model="scope.row.responsible_person" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column prop="value" label="指标值">
          <template slot-scope="scope">
            <el-form-item label="" :prop="scope.row.id" label-width="0px" size="small">
              <el-input v-model="form[scope.row.id]" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column prop="calcUnitId" label="计算单位">
          <template slot-scope="scope">
            {{scope.row.calcUnitName}}
          </template>
        </el-table-column>
      </el-table>
    </el-form>


    <div class="el-dialog__footer">
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>
  </div>
</template>

<script>
import { req } from "@/api/production/oi/common";

export default {
  name: "planElectricityAdd",
  props: ['data', 'type', 'columnsList', 'query', 'rulesList'],
  data() {
    return {
      form: { planYear: '', planMonth: '', targetItems: [], tableData: [], },
      rules: {
        planYear: [{ required: true, message: '请输入年份', trigger: 'blur' },],
        measures_and_goals: [{ required: true, message: '请输入措施及完成目标', trigger: 'blur' },],
        responsible_person: [{ required: true, message: '请输入责任人', trigger: 'blur' },],
      },
      disabled: false, tableType: false,
      submitLoading: false,
    }
  },
  computed: {
  },
  created() {
    this.form = { ...this.data };
    const tableData = [...this.columnsList]
    this.form.tableData = tableData;
    this.rules = { ...this.rules, ...this.rulesList };
    let month = this.data.plan_month;
    if (this.type === 'edit') {
      this.form.planYear = this.query.planYear;
      this.form.planMonth = month;
      this.disabled = true;
    }
    this.tableType = true;
    // console.log(this.rules)
  },
  mounted() {
  },
  methods: {
    handleChange(form) {
      let that = this;
      if (this.columnsList.length > 0) {

        this.$refs[form].validate((valid) => {
          if (valid) {
            this.submitLoading = true;
            let targetItems = [];
            this.form.tableData.forEach(function (val) {
              targetItems.push({
                id: val.id,
                value: that.form[val.id],
                measures_and_goals: val.measures_and_goals,
                responsible_person: val.responsible_person,
                calcUnitId: val.calcUnitId,
              });
            });
            if (this.type === 'add') {
              req('post', 'saveYearPowerGenerationTargetPlan', {
                planYear: this.form.planYear,
                targetItems: targetItems,
                userId: this.$store.state.user.userInfo.userName,
                userName: this.$store.state.user.userInfo.nickName,
              }).then(res => {
                if (res.code === 200) {
                  this.$message({
                    type: "success", message: "操作成功！", duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.msg)
                    }
                  });
                }
                this.submitLoading = false;
              }, error => {
                this.submitLoading = false;
                window.console.log(error);
              });
            } else if (this.type === 'edit') {
              req('post', 'updateMonthPowerGenerationTargetPlan', {
                planYear: this.form.planYear,
                planMonth: this.form.planMonth,
                targetItems: targetItems,
                userId: this.$store.state.user.userInfo.userName,
                userName: this.$store.state.user.userInfo.nickName,
              }).then(res => {
                if (res.code === 200) {
                  this.$message({
                    type: "success", message: "操作成功！", duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.msg)
                    }
                  });
                }
                this.submitLoading = false;
              }, error => {
                this.submitLoading = false;
                window.console.log(error);
              });
            }
          } else {
            return false;
          }
        });
      } else {
        this.$message({ type: 'error', message: '请先添加发电指标！' });
      }
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
.el-dialog__footer {
  padding: 0;
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
