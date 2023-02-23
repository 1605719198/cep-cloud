<template>
  <div v-if="visible">
    <el-dialog class="customDialogStyle"
               custom-class="saveAsDialog"
               title="基本资料"
               :visible.sync="visible"
               @close="cancel">
      <el-form :model="ruleForm"
               :rules="rulesAccount"
               ref="ruleForm"
               style="height:auto"
               label-width="150px">
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="帐务代码:"
                          style="height: 50px"
                          prop="accountItemCode">
              <el-input v-model="ruleForm.accountItemCode"
                        :disabled="costCenterReadable"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="帐务名称:"
                          style="height: 50px"
                          prop="accountItemName">
              <el-input v-model="ruleForm.accountItemName"
                        style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="24">
            <el-form-item label="用途说明:"
                          style="height: 50px"
                          prop="accountItemCodeDesc">
              <el-input type="textarea"
                        v-model="ruleForm.accountItemCodeDesc"
                        style="width: 640px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-table stripe
                  v-loading="loading"
                  :data="financeattritestList"
                  :key="financeattritestListKey"
                  :header-cell-style="{background:'#DCDCDC'}">
          <el-table-column label="属性名称"
                           align="center"
                           prop="accountItemCode"
                           v-if="false" />
          <el-table-column label="属性名称"
                           align="center"
                           prop="accountItemAttriName" />
          <el-table-column label="属性说明"
                           align="center"
                           prop="label" />
          <el-table-column label="属性值"
                           align="center"
                           prop="accountItemAttriValue" />
          <el-table-column label="操作"
                           align="center"
                           class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button v-if="AddVisible"
                         v-hasPermi="['accountItemCode_doEditAttri']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="primary"
                         @click="handleAdd(scope.row,scope.$index)">添加
              </el-button>
              <el-button v-if="UpdateVisible"
                         v-hasPermi="['accountItemCode_doEditAttri']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="primary"
                         @click="handleUpd(scope.row,scope.$index)">-修改-
              </el-button>
              <el-button v-if="UpdateVisible"
                         v-hasPermi="['accountItemCode_doEditAttri']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="danger"
                         @click="attributeDelete(scope.row,scope.$index)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="el-dialog__footer"
             style="padding:20px 0 0 0;">
          <span slot="footer"
                class="dialog-footer">
            <el-button @click="cancel"
                       size="medium">取 消</el-button>
            <el-button type="primary"
                       v-if="determine"
                       size="medium"
                       @click="submitFormAdd('ruleForm')"
                       :loading="lisetloading">确 定</el-button>
          </span>
        </div>
        <!-- 添加或修改测试厂商对话框 -->
        <div v-if="open">
          <el-dialog class="customDialogStyle"
                     :data="openDialog.type"
                     title="属性"
                     :visible.sync="open"
                     width="400px"
                     append-to-body>
            <el-form ref="form"
                     :model="attributeform"
                     :rules="rulesAttribute"
                     label-width="80px">
              <el-form-item label="属性名称"
                            prop="accountItemAttriName">
                <el-input v-model="attributeform.accountItemAttriName"
                          :disabled="true" />
              </el-form-item>
              <el-form-item label="属性说明"
                            prop="attriDesc">
                <el-input v-model="attributeform.attriDesc"
                          :disabled="true" />
              </el-form-item>
              <el-form-item label="属性值"
                            prop="accountItemAttriValue">
                <el-select v-model="attributeform.accountItemAttriValue"
                           placeholder="属性值"
                           v-if="typeIf"
                           style="width: 240px">
                  <el-option v-for="dict in dict.type.cost_io_type"
                             :key="dict.value"
                             :label="dict.value+'_'+dict.label"
                             :value="dict.value+'_'+dict.label" />
                </el-select>
                <el-radio-group v-model="attributeform.accountItemAttriValue"
                                v-if="isAddNoIf">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value+'_'+dict.label">{{dict.label}}</el-radio>
                </el-radio-group>
                <el-radio-group v-model="attributeform.accountItemAttriValue"
                                v-if="mccitemsigIf">
                  <el-radio v-for="dict in dict.type.cost_mccItemSig"
                            :key="dict.value"
                            :label="dict.value+'_'+dict.label">{{dict.label}}</el-radio>
                </el-radio-group>
                <el-select v-model="attributeform.accountItemAttriValue"
                           placeholder="属性值"
                           v-if="mccrptitemIf"
                           style="width: 240px">
                  <el-option v-for="item in financeattriMccList"
                             :key="item.value"
                             :label="item.value+'_'+item.label"
                             :value="item.value+'_'+item.label" />
                </el-select>
                <el-select v-model="attributeform.accountItemAttriValue"
                           placeholder="属性值"
                           v-if="mccrptitem1If"
                           style="width: 240px">
                  <el-option label="请选择"
                             value=""></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer"
                 class="dialog-footer"
                 style="padding:20px 0 0 0;">
              <el-button size="medium"
                         @click="cancel1">取 消</el-button>
              <el-button size="medium"
                         type="primary"
                         @click="submitForm">确 定</el-button>
            </div>
          </el-dialog>
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
  updateFinanceattri,
  getFinanceInformation,
  getFinanceMcc
} from "@/api/finance/ip/accountingcode";

export default {
  name: "AccountingCodeCost",
  dicts: ['cost_io_type', 'sys_yes_no', 'cost_mccItemSig',],

  data () {
    return {
      //弹窗判断
      typeIf: false,
      isAddNoIf: false,
      mccitemsigIf: false,
      mccrptitemIf: false,
      mccrptitem1If: false,
      //增改加载按钮
      lisetloading: false,
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
      //弹窗数组
      financeattriMccList: ['MCCRPTITEM'],
      financeUpdate: '',
      statusText: '',
      financeUpdatex: "",
      // 测试厂商表格数据
      financeattritestList: [],
      financeattritestListDialog: [],
      pagedata: {},
      id: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        accountItemName: '',
        accountItemCode: '',
        compId: 'J00',
        leafId: '01010801'
      },
      // 表单参数
      attributeform: {
        accountItemAttriValue: '',
        accountItemAttriName: '',
        attriDesc: ''
      },
      ruleForm: {
        compId: 'J00',
        leafid: '01010801',
        accountItemCode: '',
        accountItemName: '',
        accountItemCodeDesc: ''
      },
      rulesAccount: {
        accountItemCode: [
          { required: true, message: '请输入帐务代码', trigger: 'blur' },
          { min: 0, max: 2, message: '长度在 0 到 2 个字符', trigger: 'blur' }
        ],
        accountItemCodeDesc: [
          { required: true, message: '请输入用途说明', trigger: 'blur' }
        ],
        accountItemName: [
          { required: true, message: '请输入帐务名称', trigger: 'blur' }
        ]
      },
      rulesAttribute: {
        accountItemAttriValue: [
          { required: true, message: '请输入属性值', trigger: 'blur' }
        ]
      }
    };

  },
  created () {},
  computed: {},
  methods: {
    // 取消按钮
    cancel () {
      this.lisetloading = false
      //验证吧
      this.visible = false;
      //添加按键熟悉
      this.AddVisible = false,
        this.UpdateVisible = false;
      this.costCenterReadable = false;
      this.determine = true;
      this.reset();
    },
    //属性页面取消
    cancel1 () {
      this.open = false;
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
            item['accountItemAttriValue'] = '';
          }
        });
        this.financeattritestListKey += 1;
      })
    },

    //确定报存
    submitFormAdd: function (formName) {
      this.lisetloading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.id != null) {
            // this.ruleForm.updateUser = this.userInfo.jobNumber
            // this.ruleForm.updateUserName = this.userInfo.userName
            this.lisetloading = false
            updateFinancetest(this.ruleForm).then(response => {
              this.financeUpdate = response.data.data.length
              this.pagedata.propertydata = this.financeattritestList;
              this.pagedata.accountItemCode = response.data.data[0].accountItemCode
              updateFinanceattri(this.pagedata).then(response => {
                this.financeUpdatex = response.data.data.length
              })
              if (this.financeUpdate !== null && this.financeUpdatex !== null) {
                this.$message({
                  type: 'success',
                  message: '修改成功'
                })
              }
              this.visible = false;
              this.reset();
            })
          } else {
            // this.ruleForm.createUser = this.userInfo.jobNumber
            // this.ruleForm.createUserName = this.userInfo.userName
            this.lisetloading = false
            addFinancetest(this.ruleForm).then(response => {
              this.pagedata.propertydata = this.financeattritestList;
              this.pagedata.accountItemCode = response.data.data[0].accountItemCode;
              addFinanceattri(this.pagedata).then(response => {
                this.statusText = response.statusText
              })
              if (this.pagedata.accountItemCode !== null && this.statusText !== null) {
                this.$message({
                  type: 'success',
                  message: '新增成功'
                })
              }
              this.visible = false;
              this.$emit('getLists');
              this.reset();
              this.lisetloading = false
              //添加按键熟悉
              this.AddVisible = false,
                this.UpdateVisible = false;
              this.costCenterReadable = false;
              this.determine = true;
            })

          }

        } else {
          return false;
        }
      });

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
    handleAdd: function (row, index) {
      this.open = true;
      this.openDialog.type = index;
      this.typeIf = false,
        this.isAddNoIf = false,
        this.mccitemsigIf = false,
        this.mccrptitemIf = false,
        this.mccrptitem1If = false,
        this.attributeform.accountItemAttriValue = this.financeattritestList[index].accountItemAttriValue
      this.attributeform.accountItemAttriName = this.financeattritestList[index].accountItemAttriName
      this.attributeform.attriDesc = this.financeattritestList[index].label

      if (this.financeattritestList[index].itemA == 'IO_TYPE') {
        this.typeIf = true
      }

      if (this.financeattritestList[index].itemA == 'ISCONSUMPTION') {
        this.isAddNoIf = true
      }
      if (this.financeattritestList[index].itemA == 'MCCITEMSIG') {
        this.mccitemsigIf = true
      }
      if (this.financeattritestList[index].itemA == 'MCCRPTITEM') {
        this.mccrptitemIf = true
        this.compIdMcc = "J00";
        this.leafIdMcc = "01010802";
        getFinanceMcc(this.compIdMcc, this.leafIdMcc).then(response => {
          this.financeattriMccList = response.data.data.list
          let length = this.financeattriMccList.length;
          for (let a = 0; a < length; a++) {
            this.financeattriMccList[a].value = this.financeattriMccList[a].itemA
            this.financeattriMccList[a].label = this.financeattriMccList[a].dataA
          }
        })
      }
      if (this.financeattritestList[index].itemA == 'MCCRPTITEM1') {
        this.mccrptitem1If = true
      }


    },
    //属性修改查询
    handleUpd (row, index) {
      this.open = true;
      this.openDialog.type = index;
      this.typeIf = false,
        this.isAddNoIf = false,
        this.mccitemsigIf = false,
        this.mccrptitemIf = false,
        this.mccrptitem1If = false,
        this.attributeform.accountItemAttriValue = this.financeattritestList[index].accountItemAttriValue
      this.attributeform.accountItemAttriName = this.financeattritestList[index].accountItemAttriName
      this.attributeform.attriDesc = this.financeattritestList[index].label
      if (this.financeattritestList[index].itemA == 'IO_TYPE') {
        this.typeIf = true
      }
      if (this.financeattritestList[index].itemA == 'ISCONSUMPTION') {
        this.isAddNoIf = true
      }
      if (this.financeattritestList[index].itemA == 'MCCITEMSIG') {
        this.mccitemsigIf = true

      }
      if (this.financeattritestList[index].itemA == 'MCCRPTITEM') {
        this.mccrptitemIf = true
        this.compIdMcc = "J00";
        this.leafIdMcc = "01010802";
        getFinanceMcc(this.compIdMcc, this.leafIdMcc).then(response => {
          this.financeattriMccList = response.data.data.list
          let length = this.financeattriMccList.length;
          for (let a = 0; a < length; a++) {
            this.financeattriMccList[a].value = this.financeattriMccList[a].itemA
            this.financeattriMccList[a].label = this.financeattriMccList[a].dataA
          }

        })
      }
      if (this.financeattritestList[index].itemA == 'MCCRPTITEM1') {
        this.mccrptitem1If = true
      }
    },
    submitForm: function () {
      this.financeattritestListDialog = []
      let length = this.financeattritestList.length;
      for (let b = 0; b < length; b++) {
        this.financeattritestListDialog.push(this.financeattritestList[b])
      }
      this.financeattritestListDialog[this.openDialog.type].accountItemAttriValue = this.attributeform.accountItemAttriValue;
      this.financeattritestList = []
      this.financeattritestListDialog.forEach(item => {
        this.financeattritestList.push(item)
      })
      this.open = false;

    },
    // 表单重置
    reset () {
      this.ruleForm = { compId: 'J00' };
      this.resetForm("ruleForm");
    },
    //属性表格查询
    AddPage () {
      this.visible = true
      this.AddVisible = true
      getFinanceInformation(this.queryParams).then(response => {
        this.financeattritestList = response.data.data.list;
        let j = this.financeattritestList.length
        for (let i = 0; i < j; i++) {
          this.financeattritestList[i].accountItemAttriName = response.data.data.list[i].itemA
          this.financeattritestList[i].label = response.data.data.list[i].dataA
        }
      })
    },
    Details (accountItemCode, id) {
      this.visible = true
      this.determine = false;
      getFinanceInformation(this.queryParams).then(response => {
        this.financeattritestList = response.data.data.list;
        let j = this.financeattritestList.length
        for (let i = 0; i < j; i++) {
          this.$set(this.financeattritestList[i], 'accountItemAttriName', response.data.data.list[i].itemA);
          this.$set(this.financeattritestList[i], 'label', response.data.data.list[i].dataA);
        }
        getFinancetest(accountItemCode, id).then(response => {
          this.ruleForm = response.data.data.one
          let length = response.data.data.resultList1.length;
          for (let i = 0; i < length; i++) {
            this.financeattritestList[i].accountItemAttriValue = response.data.data.resultList1[i].accountItemAttriValue;
          }
          this.financeattritestListKey += 1;
          console.log(this.financeattritestList);
        })
      })

    },
    //成本中心修改弹窗查询
    Update (accountItemCode, id) {
      this.visible = true
      this.UpdateVisible = true
      this.costCenterReadable = true
      getFinanceInformation(this.queryParams).then(response => {
        this.financeattritestList = response.data.data.list;
        let j = this.financeattritestList.length
        for (let i = 0; i < j; i++) {
          this.$set(this.financeattritestList[i], 'accountItemAttriName', response.data.data.list[i].itemA);
          this.$set(this.financeattritestList[i], 'label', response.data.data.list[i].dataA);
        }
        getFinancetest(accountItemCode, id).then(response => {
          this.ruleForm = response.data.data.one
          let length = response.data.data.resultList1.length;
          for (let c = 0; c < length; c++) {
            this.$set(this.financeattritestList[c], 'accountItemAttriValue', response.data.data.resultList1[c].accountItemAttriValue);
          }
          this.financeattritestListKey += 1;
        })
      })

    },

  }
}

</script>


<style lang="scss" scoped>
.small-width {
  width: 150px;
  height: 5px;
}

.small-widths {
  width: 100px;
  height: 38px;
}

.small-widthc {
  width: 80px;
  height: 38px;
}

.small-widtha {
  width: 70px;
  height: 38px;
}

.small-widthx {
  width: 95px;
  height: 5px;
}

.small-widthz {
  width: 150px;
  height: 5px;
}

.small-widtht {
  width: 500px;
}

.small-widthb {
  width: 120px;
}

.small-widthl {
  width: 550px;
}

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
