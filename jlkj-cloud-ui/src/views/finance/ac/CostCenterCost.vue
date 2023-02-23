<template>
  <div v-if="visible">
    <el-dialog class="customDialogStyle"
               custom-class="saveAsDialog"
               title="基本资料"
               :visible.sync="visible"
               @close="cancel">
      <el-form :model="ruleForm"
               :rules="rules"
               ref="ruleForm"
               style="height:auto"
               label-width="150px">
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="公司别:">
              <el-input v-model="ruleForm.compId"
                        :disabled="true"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年度:"
                          prop="fiscalYear">
              <el-date-picker style="width: 210px"
                              value-format="yyyy"
                              format="yyyy"
                              v-model="ruleForm.fiscalYear"
                              type="year"
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="成本中心编码:"
                          style="height: 50px"
                          prop="costCenter">
              <el-input v-model="ruleForm.costCenter"
                        :disabled="costCenterReadable"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成本中心中文说明:"
                          prop="costCenterDesc">
              <el-input v-model="ruleForm.costCenterDesc"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="成本中心形态属性:"
                          style="height: 50px"
                          prop="ccType">
              <el-select v-model="ruleForm.ccType"
                         placeholder="请选择成本中心形态属性">
                <el-option v-for="dict in dict.type.cost_cctype_name"
                           :key="dict.value"
                           :label="dict.value+'_'+dict.label"
                           :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品产出属性:"
                          prop="isProduct">
              <el-radio-group v-model="ruleForm.isProduct">
                <el-radio v-for="dict in dict.type.cost_isproduct"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="状态:"
                          style="height: 50px"
                          prop="stusCode">
              <el-radio-group v-model="ruleForm.stusCode">
                <el-radio v-for="dict in dict.type.cost_stuscode_name"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否抛AC帐:"
                          prop="isPostAc">
              <el-radio-group v-model="ruleForm.isPostAc">
                <el-radio v-for="dict in dict.type.sys_yes_no"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="新增人:"
                          style="height: 50px">
              <el-input v-model="ruleForm.createUser"
                        :disabled="true"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新增时间:">
              <el-input v-model="ruleForm.createTime"
                        :disabled="true"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="修改人:"
                          style="height: 50px">
              <el-input v-model="ruleForm.updateUser"
                        :disabled="true"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="修改时间:">
              <el-input v-model="ruleForm.updateTime"
                        :disabled="true"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="app-container">
          <el-row>
            <el-col :span="24"
                    style="padding:0 10px">
              <div class="plan_header">
                <el-row>
                  <el-col :span="7">
                    <div style="font-size: 18px"
                         class="plan_header_title">属性资料</div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
          </el-row>

          <el-table stripe
                    v-loading="loading"
                    :data="financeattritestList"
                    :key="financeattritestListKey"
                    :header-cell-style="{background:'#DCDCDC'}">
            <el-table-column label="属性名称"
                             align="center"
                             prop="parentId"
                             v-if="false" />
            <el-table-column label="属性名称"
                             align="center"
                             prop="value" />
            <el-table-column label="属性说明"
                             align="center"
                             prop="label" />
            <el-table-column label="属性值"
                             align="center"
                             prop="attriValue" />
            <el-table-column label="操作"
                             align="center"
                             class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button v-if="AddVisible "
                           v-hasPermi="['costCenter_attriAdd']"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="primary"
                           @click="handleAdd(scope.row,scope.$index)">添加
                </el-button>
                <el-button v-if="UpdateVisible"
                           v-hasPermi="['costCenter_doEditAttri']"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="primary"
                           @click="handleUpd(scope.row,scope.$index)">修改
                </el-button>
                <el-button v-if="UpdateVisible"
                           v-hasPermi="['costCenter_delAttribute']"
                           size="mini"
                           plain
                           icon="el-icon-delete"
                           type="danger"
                           @click="attributeDelete(scope.row,scope.$index)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div slot="footer"
               class="el-dialog__footer"
               style="padding:20px 0 0 0;">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary"
                       v-if="determine"
                       size="medium"
                       @click="submitFormAdd('ruleForm')"
                       :loading="lisetloading">确 定</el-button>
          </div>

          <!-- 添加或修改 -->
          <div v-if="open">
            <el-dialog class="customDialogStyle"
                       :data="openDialog.type"
                       title="属性"
                       :visible.sync="open"
                       width="400px"
                       append-to-body>
              <el-form ref="form"
                       :model="attributeform"
                       :rules="rules"
                       label-width="80px">
                <el-form-item label="属性名称"
                              prop="attriName">
                  <el-input v-model="attributeform.attriName"
                            :disabled="true" />
                </el-form-item>
                <el-form-item label="属性说明"
                              prop="attriDesc">
                  <el-input v-model="attributeform.attriDesc"
                            :disabled="true" />
                </el-form-item>
                <el-form-item label="属性值"
                              prop="attriValue">
                  <el-select v-model="attributeform.attriValue"
                             placeholder="请选择属性值"
                             v-if="CCTypeIf"
                             style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_CCType"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label" />
                  </el-select>
                  <el-input v-model="attributeform.attriValue"
                            v-if="attriValueIf" />
                </el-form-item>
              </el-form>
              <div slot="footer"
                   class="dialog-footer"
                   style="padding:20px 0 0 0;">
                <el-button @click="cancel1">取 消</el-button>
                <el-button type="primary"
                           @click="submitForm">确 定</el-button>
              </div>
            </el-dialog>
          </div>
        </div>
      </el-form>
    </el-dialog>
  </div>

</template>
<script>
import {
  addFinancetest,
  getFinancetest,
  updateFinancetest,
  attributeQuery,

  addFinanceattri,
  updateFinanceattri
} from "@/api/finance/ac/financetest";
import { mapGetters } from "vuex";

export default {
  name: "CostCenterCost",
  dicts: ['cost_cctype_name', 'cost_isproduct', 'sys_yes_no', 'cost_stuscode_name', 'cost_CCType'],
  data () {
    return {
      //增改加载按钮
      lisetloading: false,
      CCTypeIf: false,
      attriValueIf: false,
      financeattritestListKey: 0,
      //添加按键熟悉
      AddVisible: false,
      //编辑按键熟悉
      UpdateVisible: false,
      //弹窗
      costCenterReadable: false,
      visible: false,
      determine: true,
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
      attributeform: {
        attriValue: '',
        attriName: '',
        attriDesc: '',
        parentId: ''
      },
      ruleForm: {
        compId: '',
        fiscalYear: '',
        costCenter: '',
        costCenterDesc: '',
        ccType: '',
        isProduct: 'Y',
        stusCode: '空',
        isPostAc: 'Y',
        updateDate: '',
        updateUserId: '',
        createDate: '',
        createUser: '',
        createTime: '',
        updateTime: ''
      },
      rules: {
        fiscalYear: [
          { required: true, message: '请输入年度', trigger: 'blur' },
        ],
        costCenter: [
          { required: true, message: '请输入成本中心编码', trigger: 'blur' },
          {
            type: 'string',
            max: 5,
            message: '只能输入5位编码,不能有汉字',
            trigger: 'blur',
            pattern: /^(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{1,23}$/
          },
        ],
        costCenterDesc: [
          { required: true, message: '请输入成本中心中文说明', trigger: 'blur' }
        ],
        ccType: [
          { required: true, message: '请输入成本中心中文说明', trigger: 'blur' }
        ],
        isProduct: [
          { required: true, message: '请输入产品产出属性', trigger: 'blur' }
        ],
        stusCode: [
          { required: true, message: '请输入状态', trigger: 'blur' }
        ],
        isPostAc: [
          { required: true, message: '请输入状态', trigger: 'blur' }
        ],
      },
    };

  },
  created () {
  },
  computed: {
    ...mapGetters(["userInfo"])
  },
  methods: {
    // 取消按钮
    cancel () {
      this.ruleForm = {
        isProduct: 'Y',
        stusCode: '空',
        isPostAc: 'Y',
      }
      //验证吧
      this.visible = false;
      //添加按键熟悉
      this.AddVisible = false;
      this.UpdateVisible = false;
      this.costCenterReadable = false;
      this.determine = true;
      this.CCTypeIf = false;
      this.attriValueIf = false;
      this.lisetloading = false
    },
    //属性页面取消
    cancel1 () {
      this.open = false;
      this.CCTypeIf = false;
      this.attriValueIf = false;
    },
    //属性查询
    attribute () {
      this.financeattritestList = []
      this.financeattritestListDialog = []
      let j = this.dict.type.finance_type_name.length
      for (let i = 0; i < j; i++) {
        this.dict.type.finance_type_name[i].attriValue = ''
        this.dict.type.finance_type_name[i].parentid = this.financeattritestList[i].parentid
        this.financeattritestList.push(this.dict.type.finance_type_name[i])
        this.financeattritestListDialog.push(this.dict.type.finance_type_name[i])
      }
    },
    /** 删除按钮操作 */
    attributeDelete (row) {
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        this.financeattritestList.forEach(item => {
          if (item.id === row.id) {
            item['attriValue'] = '';
          }
        });
        this.financeattritestListKey += 1;
      })
    },

    //确定报存
    submitFormAdd (formName) {
      this.lisetloading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.id != null) {
            this.ruleForm.updateUser = this.userInfo.jobNumber
            this.ruleForm.updateUserName = this.userInfo.userName
            this.lisetloading = false
            updateFinancetest(this.ruleForm).then(response => {
              this.pagedata.propertydata = this.financeattritestList;
              this.pagedata.parentId = response.data.data[0].id
              updateFinanceattri(this.pagedata)
              if (response.data.data) {
                this.$message({
                  type: 'success',
                  message: '修改成功'
                })
              }
              this.visible = false;
              this.$emit('getLists');
              this.reset();
            })
          } else {
            this.ruleForm.createUser = this.userInfo.jobNumber
            this.ruleForm.createUserName = this.userInfo.userName
            this.lisetloading = false
            console.log(this.ruleForm);
            addFinancetest(this.ruleForm).then(response => {
              this.pagedata.propertydata = this.financeattritestList;
              this.pagedata.parentId = response.data.data[0].id;
              addFinanceattri(this.pagedata)
              if (response.data.data) {
                this.$message({
                  type: 'success',
                  message: '新增成功'
                })
              }
              this.visible = false;
              this.$emit('getLists');
              this.reset();
            })
          }
          this.$emit('getLists');
        } else {
          return false;
        }
      });
      this.lisetloading = false
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    },
    /** 修改按钮操作 */
    handleUpdate () {
      this.open = true;
      attributeQuery().then(response => {
        this.financeattritestList = response.data.data;
      })
    },
    //属性添加查询
    handleAdd (row, index) {
      this.open = true;
      this.openDialog.type = index;
      this.CCTypeIf = false;
      this.attriValueIf = false;
      if (this.financeattritestList[index].value === 'CCType') {
        this.CCTypeIf = true;
      } else {
        this.attriValueIf = true;
      }
      this.attributeform.attriName = this.financeattritestList[index].value
      this.attributeform.attriDesc = this.financeattritestList[index].label
      this.attributeform.attriValue = this.financeattritestList[index].attriValue
    },
    //属性修改查询
    handleUpd (row, index) {
      this.open = true;
      this.openDialog.type = index;
      if (this.financeattritestList[index].value === 'CCType') {
        this.CCTypeIf = true;
      } else {
        this.attriValueIf = true;
      }
      this.attributeform.attriName = this.financeattritestList[index].value
      this.attributeform.attriDesc = this.financeattritestList[index].label
      this.attributeform.attriValue = this.financeattritestList[index].attriValue
    },
    submitForm () {
      this.financeattritestListDialog[this.openDialog.type].attriValue = this.attributeform.attriValue;
      this.financeattritestList = []
      this.financeattritestListDialog.forEach(item => {
        this.financeattritestList.push(item)
      })
      this.CCTypeIf = false;
      this.attriValueIf = false;
      this.open = false;
    },
    // 表单重置
    reset () {
      this.ruleForm = {
        isProduct: 'Y',
        stusCode: '空',
        isPostAc: 'Y',
      };
      this.resetForm("ruleForm");

    },
    //属性查询
    AddPage (dictType) {
      this.financeattritestList = []
      this.financeattritestListDialog = []
      let j = dictType.length
      for (let i = 0; i < j; i++) {
        dictType[i].attriValue = ''
        this.financeattritestList.push(dictType[i])
        this.financeattritestListDialog.push(dictType[i])
      }
      this.visible = true
      this.AddVisible = true
    },
    Details (costCenter, id, dictType) {
      this.visible = true
      this.determine = false;
      getFinancetest(costCenter, id).then(response => {
        this.ruleForm = response.data.data
      })
      this.financeattritestList = []
      this.financeattritestListDialog = []
      let j = dictType.length
      for (let i = 0; i < j; i++) {
        dictType[i].attriValue = ''
        this.financeattritestList.push(dictType[i])
        this.financeattritestListDialog.push(dictType[i])
      }
      attributeQuery(id).then(response => {
        let tempList = response.data.data
        this.financeattritestList = []
        this.financeattritestListDialog = []
        for (let x = 0; x < dictType.length; x++) {
          let t = ''
          for (let i = 0; i < tempList.length; i++) {
            if (dictType[x].value === tempList[i].value
              && dictType[x].label === tempList[i].label) {
              t = tempList[i].attriValue
              break
            }
          }
          dictType[x].attriValue = t
          this.financeattritestList.push(dictType[x])
          this.financeattritestListDialog.push(dictType[x])
        }
      })
    },
    //成本中心修改弹窗查询
    Update (costCenter, id, dictType) {
      this.visible = true
      this.UpdateVisible = true
      this.costCenterReadable = true
      getFinancetest(costCenter, id).then(response => {
        this.ruleForm = response.data.data
      })
      this.financeattritestList = []
      this.financeattritestListDialog = []
      let j = dictType.length
      for (let i = 0; i < j; i++) {
        dictType[i].attriValue = ''
        this.financeattritestList.push(dictType[i])
        this.financeattritestListDialog.push(dictType[i])
      }
      attributeQuery(id).then(response => {
        let tempList = response.data.data
        this.id = response.data.data.id
        this.financeattritestList = []
        this.financeattritestListDialog = []
        for (let x = 0; x < dictType.length; x++) {
          let t = ''
          let b = ''
          let c = ''
          for (let i = 0; i < tempList.length; i++) {
            if (dictType[x].value === tempList[i].value
              && dictType[x].label === tempList[i].label) {
              t = tempList[i].attriValue
              b = tempList[i].parentId
              c = tempList[i].id
              break
            }
          }
          dictType[x].attriValue = t
          dictType[x].parentId = b
          dictType[x].id = c
          this.financeattritestList.push(dictType[x])
          this.financeattritestListDialog.push(dictType[x])
        }

      })
    },

  }
}

</script>


<style lang="scss" scoped>
.el-form-item {
  margin-bottom: 0;
}

::v-deep .saveAsDialog {
  width: 1250px !important;
}

.dialog-wrap {
  ::v-deep .el-form-item__label {
    width: 86px !important;
    text-align: right;
  }
}
</style>
