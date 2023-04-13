<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="top" label-width="100px">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="计划编号" prop="repairPlanNumber">
            <el-input v-model="form.plan_code" :disabled="true"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实际开始时间" prop="real_start_date">
            <el-date-picker v-model="form.real_start_date" type="date" placeholder="选择开始时间" value-format="yyyy-MM-dd"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实际结束时间" prop="real_end_date">
            <el-date-picker v-model="form.real_end_date" type="date" placeholder="选择结束时间" value-format="yyyy-MM-dd"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="计划完成说明" prop="remark">
            <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="form.remark"/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="el-dialog__footer">
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleChange('form')">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </span>
    </div>
  </div>
</template>
<script>
  import {req} from "@/api/production/oi/common";
  export default {
    name: "planRepairOver",
    props: ['data'],
    data() {
      let validateStartTime = (rule, value, callback) => {
        if (value) {
          if (this.form.real_end_date) {
            if (this.compareDate(value, this.form.real_end_date)) {
              callback();
            } else {
              callback(new Error("起始日期不能再结束日期之后，请重新选择"));
            }
          }
        }
      };
      let validateEndTime = (rule, value, callback) => {
        if (value) {
          if (this.form.real_start_date) {
            if (this.compareDate(this.form.real_start_date, value) ) {
              callback();
            } else {
              callback(new Error("结束日期不能在起始日期前，请重新选择"));
            }
          }
        }
      };
      return {
        options: [
          {value: '1', label: '已完成'},
          {value: '0', label: '未完成'},
        ],
        form: {
          real_start_date: '',
          real_end_date: '',
          remark: '',
        },
        rules: {
          real_start_date: [{required: true, message: '请选择实际开始时间', trigger: 'blur'},
            {validator: validateStartTime, trigger: 'blur'}],
          real_end_date: [{required: true, message: '请选择实际结束时间', trigger: 'blur'},
            {validator: validateEndTime, trigger: 'blur'}],
          remark: [{required: true, message: '请输入计划完成说明', trigger: 'blur'},],
        },
      }
    },
    computed: {
    },
    created() {
    },
    mounted() {
      this.form = {...this.data}
    },
    methods: {
      handleChange(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            req('post', 'RepairPlan/complete', {
              id: this.form.id,
              real_start_date: this.form.real_start_date,
              real_end_date: this.form.real_end_date,
              remark: this.form.remark,
              complete_user_id: this.$store.getters.userInfo.userId,
              complete_user_name: this.$store.getters.userInfo.nickName,
            }).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success", message: "操作成功！", duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
              }
            }, error => {
              window.console.log(error);
            });
          } else {
            return false;
          }
        });
      },
      cancel() {
        this.$emit('close')
      },
      // 比较两个日期
      compareDate(start, end) {
        return new Date(end).getTime() >= new Date(start).getTime();
      },
    }
  }
</script>

<style scoped>
  .el-form--label-top .el-form-item__label {
    padding: 0px;
  }

  .el-form-item {
    margin-bottom: 0;
  }
</style>
