<template>
  <div v-if="visible">
    <el-dialog class="customDialogStyle" custom-class="saveAsDialog" :title="title" :visible.sync="visible"
               @close="cancel"  >
      <el-form v-if="AddYear" :model="ruleForm" :rules="rules" ref="ruleForm" style="height:150px" label-width="84px">
        <el-row style="height:100px">
<!--          <el-col>
            <span style="color: red">
              重要说明：
               </span><br>
            <span style="color: red">1、会计周期以月为单位，起始日期为每月1日，终止日期为每月最后一日；</span><br>
            <span style="color: red"> 2、输入会计年度，点击确定后，将一次性新增 从起始年月到年末最后一个月（12月）的所有周期</span>
          </el-col>-->
          <el-col :span="24" style="height:80px">
            <el-form-item label="公司:" prop="compId">
              <el-input v-model="ruleForm.compId"
                        placeholder="请输入公司"
                        style="width: 190px"
                        clearable
                      />
            </el-form-item>
          </el-col>
          <el-col :span="24" style="height:80px">
            <el-form-item label="会计年度:" prop="accountYear">
              <el-date-picker
                style="width: 195px"
                v-model="ruleForm.accountYear"
                type="year"
                format="yyyy"
                value-format="yyyy"
                placeholder="选择年">
              </el-date-picker>
            </el-form-item>
          </el-col>
<!--          <el-col :span="24" style="height:80px">
            <el-form-item label="月份:" prop="stMonth">
              <el-select v-model="ruleForm.stMonth" placeholder="请选择月份" style="width: 220px">
                <el-option
                  v-for="dict in dict.type.finance_month"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>-->
        </el-row>
      </el-form>
      <el-form v-if="UpdateYear" :model="ruleForm" :rules="rules" ref="ruleForm" style="height:200px"
               label-width="150px">
        <el-row style="height: 70px">
          <el-col :span="24">
            <el-form-item label="会计周期:" prop="accountPeriod">
              <el-input v-model="ruleForm.accountPeriod" :disabled="true" style="width: 220px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="起始日期:" prop="startDate">
              <el-date-picker
                :readonly="true"
                :disabled="true"
                value-format="yyyyMMdd"
                format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
                v-model="ruleForm.startDate"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="终止日期:" prop="endDate">
              <el-date-picker
                :readonly="true"
                :disabled="true"
                value-format="yyyyMMdd"
                format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期" v-model="ruleForm.endDate"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
         <el-button @click="cancel" size="medium">取 消</el-button>
              <el-button type="primary" v-if="determine" size="medium" @click="submitFormAdd('ruleForm')"
                         :loading="lisetloading">确 定</el-button>
          </span>
      </div>

    </el-dialog>
  </div>

</template>
<script>
import {
  addFinancetest,
  getFinanceByproduct,
} from "@/api/finance/aa/fiscalYear";

export default {
  name: "fiscalYearCost",
  dicts: ['finance_month'],
  data() {
    return {
      //确定判断
      determine: true,
      //增改加载按钮
      lisetloading:false,
      //新增弹窗
      AddYear: false,
      //编辑弹窗
      UpdateYear: false,
      visible: false,
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
      },
      ruleForm: {
        id:'',
        startDate: '',
        endDate: '',
        compId: 'J00',
        isClosed: 'N',
        stMonth: '',
        manVoucherhrSrl:'0',
        transVoucherCurrentSrl:'0',
        trans3VoucherCurrentSrl:'0',
        payVoucherCurrentSrl:'0',
        receiveVoucherCurrentSrl:'0',
      },
      rules: {
        accountYear: [
          {required: true, trigger: 'blur',message: "年度不能为空"},
        ],
        startDate: [
          {required: true, trigger: 'blur',message: "开始日期不能为空"},
        ],
        endDate: [
          {required: true, trigger: 'blur',message: "结束日期不能为空"},
        ]
      },
    };

  },
  created() {
  },
  computed: {},
  methods: {

    /** 查询测试列表 */
    getList() {
      this.loading = false;
      getFinanceByproduct(this.queryParams);
    },
    // 取消按钮
    cancel() {
      this.lisetloading = false
      //验证吧
      this.UpdateYear = false;
      this.AddYear = false;
      this.visible = false;
      this.ruleForm = {
        startDate: '',
        endDate: '',
        compId: 'J00',
        isClosed: 'N',
        stMonth: '',
        mVarcharhrId: '',
        tVarcharhrId: '',
        pVarcharhrId: '',
        rVarcharhrId: '',
        mVchrsrl: '0',
        tVchrsrl: '0',
        t3vchrsrl: '0',
        accountPeriod: '0',
        yVchrsrl: '0'
      }
    },
//确定报存
    submitFormAdd(formName) {
      this.lisetloading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.lisetloading = false
          // this.ruleForm.updateUser  = this.userInfo.jobNumber
          // this.ruleForm.updateUserName  = this.userInfo.userName
              addFinancetest(this.ruleForm).then(response => {
                if (response.data.data) {
                  this.$message({
                    type: 'success',
                    message: '新增成功'
                  })
                }
                this.$emit('getLists');
                this.open = false
                this.visible = false;
                this.reset();
              })
            }

      });
      this.lisetloading = false
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /** 修改按钮操作 */
    See(accountPeriod, startDate, endDate) {
      this.determine = false
      this.visible = true
      this.UpdateYear = true
      this.title = "查看会计周期"
      this.ruleForm.accountPeriod = accountPeriod;
      this.ruleForm.startDate = startDate;
      this.ruleForm.endDate = endDate;
    },
    /** 新增按钮操作 */
    AddPage() {
      this.visible = true
      this.AddYear = true
      this.title = "新增会计年度"
      this.determine = true
    },
    // 表单重置
    reset() {
      this.ruleForm = {compId: 'J00'};

      this.resetForm("ruleForm");
    },


  }
}

</script>


<style lang="scss" scoped>

.el-form-item {
  margin-bottom: 0;
}

::v-deep .saveAsDialog {
  width: 500px !important;
}

.dialog-wrap {
  ::v-deep .el-form-item__label {
    width: 86px !important;
    text-align: right;
  }
}
</style>
