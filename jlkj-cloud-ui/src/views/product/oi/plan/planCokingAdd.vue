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
      <el-table :data="tableData" stripe border style="width: 100%">
        <el-table-column prop="targetItemName" label="指标名称" />
        <el-table-column prop="value" label="指标值">
          <template slot-scope="scope">
            <el-form-item label="" :prop="scope.row.id" label-width="0px" size="small">
              <el-input @input="changeInput(scope.row.id)" v-model="form[scope.row.id]" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column prop="unit" label="计算单位" />
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
import { saveYearProductionTargetPlan, updateMonthProductionTargetPlan } from "@/api/production/oi/plan";

export default {
  name: "planCokingAdd",
  props: ['data', 'type', 'columnsList', 'query', 'rulesList'],
  data() {
    return {
      form: { planYear: '', planMonth: '', },
      rules: {
        planYear: [{ required: true, message: '请输入年份', trigger: 'blur' },],
      },
      tableData: [],
      disabled: false,
      tableType: false,
      submitLoading: false,
    }
  },
  computed: {
  },
  created() {
  },
  mounted() {
    this.form = { ...this.data };
    this.tableData = [...this.columnsList];
    this.rules = { ...this.rules, ...this.rulesList };
    let month = this.data.plan_month;
    if (this.type === 'edit') {
      this.form.planYear = this.query.planYear;
      this.form.planMonth = month;
      this.disabled = true;
    }
    this.tableType = true;
  },
  methods: {
    //限制只有两位小数的正整数
    changeInput(name) {
      this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
    },
    handleChange(form) {
      let that = this;
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.submitLoading = true;
          let targetItems = [];
          this.tableData.forEach(function (val) {
            targetItems.push({ id: val.id, value: that.form[val.id] });
          });
          if (this.type === 'add') {
            saveYearProductionTargetPlan({
              planYear: this.form.planYear,
              targetItems: targetItems,
              userId: this.$store.state.user.userInfo.userName,
              userName: this.$store.state.user.userInfo.nickName,
            }).then(res => {
                this.$message({
                  type: "success", message: "新增成功！", duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          } else if (this.type === 'edit') {
            updateMonthProductionTargetPlan({
              planYear: this.form.planYear,
              planMonth: this.form.planMonth,
              targetItems: targetItems,
              userId: this.$store.state.user.userInfo.userName,
              userName: this.$store.state.user.userInfo.nickName,
            }).then(res => {
                this.$message({
                  type: "success", message: "修改成功！", duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
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
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
