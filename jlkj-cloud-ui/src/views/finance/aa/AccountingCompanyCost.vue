<template>
  <div v-if="visible">
    <el-dialog
      class="customDialogStyle"
      custom-class="saveAsDialog"
      title="会计公司编辑"
      :visible.sync="visible"
      @close="cancel">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" style="height:auto" label-width="150px">
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="公司编码:" style="height: 50px" prop="compId">
              <el-input v-model="ruleForm.compId" :disabled="compIdDisabled" style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="24">
            <el-form-item label="公司全名:" style="height: 50px" prop="companyChineseName">
              <el-input v-model="ruleForm.companyChineseName"
                        style="width: 640px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="公司缩写:" style="height: 50px" prop="companyShortName">
              <el-input v-model="ruleForm.companyShortName"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计帐币别:" style="height: 50px" prop="crcy">
              <el-select v-model="ruleForm.crcy" placeholder="请选择计帐币别">
                <el-option
                  v-for="dict in dict.type.vendor_manufacturertest_currency"
                  :key="dict.value"
                  :label="dict.value+'_'+dict.label"
                  :value="dict.value"
                  style="height: 50px"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="会计分类:" style="height: 50px" prop="acctGroupMax">
              <el-select v-model="ruleForm.acctGroupMax" placeholder="请选择会计分类">
                <el-option
                  v-for="dict in dict.type.finance_acctGroup"
                  :key="dict.value"
                  :label="dict.value+'_'+dict.label"
                  :value="dict.value"
                  style="height: 50px"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报表分类:" prop="acctGroupMiddle">
              <el-select v-model="ruleForm.acctGroupMiddle" placeholder="请选择报表分类">
                <el-option
                  v-for="dict in dict.type.finance_reportGroup"
                  :key="dict.value"
                  :label="dict.value+'_'+dict.label"
                  :value="dict.value"
                  style="height: 50px"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="24">
            <el-form-item label="英文名称:" style="height: 50px" prop="companyEnglishName">
              <el-input v-model="ruleForm.companyEnglishName" style="width: 640px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="24">
            <el-form-item label="税号:" style="height: 50px" prop="taxNo">
              <el-input v-model="ruleForm.taxNo" style="width: 640px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" v-if="determine" size="medium" @click="submitFormAdd('ruleForm')"
                   :loading="lisetloading">确 定</el-button>
          </span>
        </div>
      </el-form>
    </el-dialog>
  </div>


</template>

<script>
import {
  addFinancetest,
  getFinancetest,
  updateFinancetest
} from "@/api/finance/aa/accountingCompany";

export default {
  name: "accountingCompanyCost",
  dicts: ['vendor_manufacturertest_currency', 'finance_acctGroup', 'finance_reportGroup',],
  data() {
    let validPassword=(rule,value,callback)=>{
      let reg= /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{0,4}$/
      if(!reg.test(value)){callback(new Error('公司编码必须是由0-4位字母+数字组合'))
      }else{
        callback()
      }
    };
    return {
      compIdDisabled: true,
      CCTypeIf: false,
      attriValueIf: false,
      financeattritestListKey: 0,
      //添加按键熟悉
      AddVisible: false,
      //编辑按键熟悉
      UpdateVisible: false,
      //弹窗
      visible: false,
      determine: true,
      //增改加载按钮
      lisetloading: false,
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
      // 总条数
      total: 0,
      openDialog: {
        open: false,
        type: '',
        title: '',
        width: '500px',
        data: {},
      },
      // 测试厂商表格数据
      financeattritestList: [],
      financeattritestListDialog: [],
      pagedata: {},
      id: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数

      ruleForm: {
        parentCompId: 'J00'
      },
      rules: {
        compId: [
          {required: true, message: '请输入公司编码', trigger: 'blur'},
          { validator: validPassword, trigger: 'blur' }
        ],
        companyChineseName: [
          {required: true, message: '请输入成本中心编码', trigger: 'blur'},

        ],
        companyShortName: [
          {required: true, message: '请输入公司缩写', trigger: 'blur'}
        ],
        crcy: [
          {required: true, message: '请输入计帐币别', trigger: 'blur'}
        ],
        acctGroupMax: [
          {required: true, message: '请输入会计分类', trigger: 'blur'}
        ],
        acctGroupMiddle: [
          {required: true, message: '请输入报表分类', trigger: 'blur'}
        ],
      },
    };

  },
  created() {
  },
  computed: {},
  methods: {
    // 取消按钮
    cancel() {
      this.lisetloading = false

      //验证吧
      this.visible = false;
      //添加按键熟悉
      this.AddVisible = false,
        this.UpdateVisible = false;
      this.determine = true;
      this.CCTypeIf = false;
      this.attriValueIf = false;
    },
    //属性页面取消
    cancel1() {
      this.open = false;
      this.CCTypeIf = false;
      this.attriValueIf = false;
    },

//确定报存
    submitFormAdd(formName) {
      this.lisetloading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.id != null) {
            // this.ruleForm.updateUser  = this.userInfo.jobNumber
            // this.ruleForm.updateUserName  = this.userInfo.userName
            updateFinancetest(this.ruleForm).then(response => {
              console.log(response.data.data);
              if (response.data.data) {
                debugger
                this.$message({
                  type: 'success',
                  message: '修改成功'
                })
              }
              this.lisetloading = false
              this.visible = false;
              this.$emit('getLists');
              this.reset();
            })
          } else {
            // this.ruleForm.createUser = this.userInfo.jobNumber
            // this.ruleForm.createUserName = this.userInfo.userName
            this.ruleForm.parentCompId = 'J00'
            addFinancetest(this.ruleForm).then(response => {
              if (response.data.code === "0"){
                this.$message({
                  type: 'success',
                  message: '新增成功'
                })
              }
              this.lisetloading = false
              this.visible = false;
              this.$emit('getLists');
              this.reset();
            })
          }

        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 表单重置
    reset() {
      this.ruleForm = {
        parentCompId: 'J00',
      };
      this.resetForm("ruleForm");

    },
    //属性查询
    AddPage() {
      this.ruleForm = {parentId: 'J00'}
      this.visible = true
      this.AddVisible = true
      this.compIdDisabled = false
    },
    Details(compId, id) {
      this.visible = true
      this.determine = false;
      this.compIdDisabled = false
      getFinancetest(compId, id).then(response => {
        this.ruleForm = response.data.data
      })
    },
    //成本中心修改弹窗查询
    Update(compId, id) {
      this.visible = true
      this.UpdateVisible = true
      this.compIdDisabled = true
      getFinancetest(compId, id).then(response => {
        this.ruleForm = response.data.data
      })
    },

  }
}
</script>

<style scoped>

</style>
