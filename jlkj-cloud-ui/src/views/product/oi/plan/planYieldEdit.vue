<template>
  <div class="avue-crud">
    <el-form :model="form"
             ref="form"
             :rules="rules"
             label-position="left"
             label-width="80px">

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="计划年度"
                        prop="planYear">
            <el-date-picker v-model="form.planYear"
                            type="year"
                            placeholder="选择年"
                            value-format="yyyy"
                            :disabled="disabled" />
          </el-form-item>
        </el-col>
        <el-col :span="12"
                v-if="!disabled">
          <el-form-item label="计划产量"
                        prop="planYield">
            <!-- <el-input v-model="form.planYield"
                      type="number" /> -->
            <el-input v-model="form.planYield"
                      @input="changeInput('planYield')">
              <template slot="append">吨</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12"
                v-if="disabled">
          <el-form-item label="计划月份"
                        prop="planMonth">
            <el-input v-model="form.planMonth"
                      :disabled="disabled" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-table :data="tableData"
                v-loading="loading"
                stripe
                border
                style="width: 100%">
        <el-table-column prop="targetItemName"
                         label="指标名称" />
        <el-table-column prop="value"
                         label="指标值">
          <!--          <template slot-scope="scope">-->
          <!--            <el-form-item label="" :prop="scope.row.id" label-width="0px" size="small">-->
          <!--              <el-input v-model="form[scope.row.id]"/>-->
          <!--            </el-form-item>-->
          <!--            {{form[scope.row.id]}}-->
          <!--          </template>-->
        </el-table-column>
        <el-table-column prop="unit"
                         label="计算单位" />
        <el-table-column prop="value"
                         label="产量">
          <template slot-scope="scope">
            <template v-if="type !== 'edit'">
              {{ scope.row[scope.row.id] }}
            </template>
            <el-form-item v-else
                          label=""
                          :prop="scope.row.id"
                          label-width="0px"
                          size="small">
              <el-input v-model="form[scope.row.id]" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column prop="unit"
                         label="单位">
          <template>吨</template>
        </el-table-column>
      </el-table>
    </el-form>
    <div class="el-dialog__footer">
      <span slot="footer"
            class="dialog-footer">
        <el-button type="primary"
                   @click="handleChange('form')"
                   :loading="submitLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>
  </div>
</template>
<script>
import { req } from "@/api/production/oi/common";
import { listYearProductionTargetPlans, listMonthProductionTargetPlans, } from "@/api/production/oi/plan";

export default {
  name: "planYieldEdit",
  props: ['data', 'type', 'columnsList', 'query', 'rulesList'],
  data () {
    return {
      form: { planYear: '', planMonth: '', planYield: 0, },
      rules: {
        planYear: [{ required: true, message: '请输入年份', trigger: 'blur' },],
      },
      tableData: [],
      yieldList: {},
      disabled: false,
      tableType: false,
      loading: false,
      submitLoading: false,
    }
  },
  computed: {
  },
  created () {
    let that = this;
    let table = [];
    this.form = { ...this.data };
    let month = this.data.plan_month;
    if (this.type === 'edit') {
      this.form.planYear = this.query.planYear;
      this.form.planMonth = month;
      this.disabled = true;
      this.columnsList.forEach(function (val) {
        let data = { ...val };
        that.form[val.id] = (that.data[val.id] / 1000).toFixed(3);
        data[val.id] = that.form[val.id];
        table.push(data);
      });
      listMonthProductionTargetPlans({}, { planYear: this.query.planYear }).then(res => {
        let v = res.data[month - 1];
        table.forEach(function (val, index) {
          table[index].value = v[val.id];
        });

        this.tableData = table;
      });
    } else {
      this.columnsList.forEach(function (val) {
        let data = { ...val };
        data[val.id] = 0;
        table.push(data);
      });
      this.tableData = table;
    }
    this.rules = { ...this.rules, ...this.rulesList };
    this.tableType = true;
  },
  mounted () {
  },
  methods: {
    //限制只有两位小数的正整数
    changeInput (name) {
      this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
    },
    handleChange (form) {
      let that = this;
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.submitLoading = true;
          let targetItems = [];
          this.tableData.forEach(function (val, index) {
            if (that.type === 'edit') {
              targetItems.push({ id: val.id, value: that.form[val.id] * 1000 });
            } else {
              targetItems.push({ id: val.id, value: that.tableData[index][val.id] * 1000 });
            }
          });
          targetItems.push({ id: "123456789", value: this.form.planYield * 1000 });//这个是传计划产量到后台的item 理解为表格的最高后一个看不见的行
          if (this.type === 'add') {
            req('post', 'saveYearProductionOutputPlan', {
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
            req('post', 'updateMonthProductionOutputPlan', {
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
    },
    cancel () {
      this.$emit('close')
    }
  },
  watch: {
    'form.planYear' (val) {
      let that = this;
      if (val) {
        listYearProductionTargetPlans({}, {}).then(res => {
          let d = res.data;
          let v = {};
          for (let i = 0; i < d.length; i++) {
            if (d[i].plan_year === parseInt(val)) {
              v = d[i];
              break;
            }
          }
          this.tableData.forEach(function (value, index) {
            that.tableData[index].value = v[value.id];
            if (that.form.planYield) {
              that.tableData[index][value.id] = that.form.planYield * that.tableData[index].value / 100;
            }
          });
          if (!v.plan_year) {
            this.$message({ type: 'error', message: '请先设置' + val + '年指标' });
          }
        });
      }
    },
    'form.planYield' (val) {
      if (val) {
        if (this.form.planYear) {
          let that = this;
          this.tableData.forEach(function (value, index) {
            that.tableData[index][value.id] = val * that.tableData[index].value / 100;
          });
        } else {
          this.$message({ type: 'error', message: '请先选择计划年度' });
        }
      }
    },
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
