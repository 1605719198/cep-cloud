<template>
  <div v-if="visible">
    <el-dialog
      class="customDialogStyle"
      custom-class="saveAsDialog"
      title="基本资料"
      :visible.sync="visible"
      :width="openDialog.width"
      @close="cancel">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" style="height:auto" label-width="150px">
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="产副品代码:" prop="productCode">
              <el-input v-model="ruleForm.productCode" :disabled="editInput" style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产副品名称:" prop="productCodeDesc">
              <el-input v-model="ruleForm.productCodeDesc" style="width: 210px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="产副品帐务分类:" style="height: 50px" prop="productAccountAttri">
              <el-select v-model="ruleForm.productAccountAttri" placeholder="请选择活动区域" style="width: 210px">
                <el-option v-for="dict in dict.type.cost_productAccountAttri"
                           :key="dict.value"
                           :label="dict.value+'_'+dict.label"
                           :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计量单位:" prop="pdUnit">
              <el-select v-model="ruleForm.pdUnit" placeholder="请选择活动区域" style="width: 210px">
                <el-option v-for="dict in dict.type.cost_pdUnit"
                           :key="dict.value"
                           :label="dict.value+'_'+dict.label"
                           :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="height: 70px">
          <el-col :span="12">
            <el-form-item label="有效期限:" style="height: 50px" prop="validateDate">
              <el-date-picker
                style="width: 210px"
                v-model="ruleForm.validateDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="app-container">
          <el-tabs>
            <el-tab-pane label="属性资料">
              <el-table stripe v-loading="loading" :data="financeattritestList" :key="financeattritestListKey"
                        :header-cell-style="{background:'#DCDCDC'}">
                <el-table-column label="属性名称" align="center" prop="productCode" v-if="false"/>
                <el-table-column label="属性名称" align="center" prop="value"/>
                <el-table-column label="属性说明" align="center" prop="label"/>
                <el-table-column label="属性值" align="center" prop="attriValue"/>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button
                      v-if="AddVisible "
                      size="mini" plain icon="el-icon-edit" type="primary"
                      @click="handleAdd(scope.row,scope.$index)"
                    >添加
                    </el-button>
                    <el-button
                      v-if="UpdateVisible"
                      size="mini"
                      plain icon="el-icon-edit"
                      type="primary"
                      @click="handleUpd(scope.row,scope.$index)"
                    >修改
                    </el-button>
                    <el-button
                      v-if="UpdateVisible"
                      size="mini" plain icon="el-icon-delete" type="danger"
                      @click="attributeDelete(scope.row,scope.$index)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="财务资料">
              <el-table stripe v-loading="loading" :data="financeAccountList" :key="financeAccountListtKey"
                        :header-cell-style="{background:'#DCDCDC'}">
                <el-table-column label="科目名称" align="center" prop="accountName"/>
                <el-table-column label="成本科目" align="center" prop="costCode"/>
                <el-table-column label="会计科目" align="center" prop="accountCode"/>
                <el-table-column label="户号" align="center" prop="accountIdIdex"/>
                <el-table-column label="参号" align="center" prop="accountRefnoIndex"/>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button
                      v-if="AddVisible "
                      size="mini" plain icon="el-icon-edit" type="primary"
                      @click="handleAccount(scope.row,scope.$index)"
                    >添加
                    </el-button>
                    <el-button
                      v-if="UpdateVisible"
                      size="mini"
                      plain icon="el-icon-edit"
                      @click="handleUpAccount(scope.row,scope.$index)"
                    >-修改-
                    </el-button>
                    <el-button
                      v-if="UpdateVisible"
                      size="mini" plain icon="el-icon-delete" type="danger"
                      @click="financeeDelete(scope.row,scope.$index)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                background
                :total="total"
                :current-page="queryParams.pageNum"
                :page-sizes="[20, 50, 100, 200]"
                :page-size="queryParams.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                style="float: right;">
              </el-pagination>
            </el-tab-pane>
          </el-tabs>
          <div class="el-dialog__footer" style="padding:20px 0 0 0;">
            <span slot="footer" class="dialog-footer">
              <el-button @click="cancel" size="medium">取 消</el-button>
              <el-button type="primary" v-if="determine" size="medium" @click="submitFormAdd('ruleForm')"
                         :loading="lisetloading">确 定</el-button>
            </span>
          </div>
          <!-- 添加或修改测试属性对话框 -->
          <div v-if="open">
            <el-dialog  class="customDialogStyle" :data="openDialog.type" title="属性" :visible.sync="open" width="400px" append-to-body>
              <el-form ref="form" :model="attributeform" :rules="rules" label-width="80px">
                <el-form-item label="属性名称" prop="attriName">
                  <el-input v-model="attributeform.attriName" :disabled="true"/>
                </el-form-item>
                <el-form-item label="属性说明" prop="attriDesc">
                  <el-input v-model="attributeform.attriDesc" :disabled="true"/>
                </el-form-item>
                <el-form-item label="属性值" prop="attriValue">
                  <el-select v-model="attributeform.attriValue" placeholder="属性值" v-if="isbalchkIf" style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_isbalchk"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label"
                    />
                  </el-select>
                  <el-select v-model="attributeform.attriValue" placeholder="属性值" v-if="mrvsipIf" style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_mrvsip"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label"
                    />
                  </el-select>
                  <el-select v-model="attributeform.attriValue" placeholder="属性值" v-if="pdclassifyIf" style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_pdclassify"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label"
                    />
                  </el-select>
                  <el-select v-model="attributeform.attriValue" placeholder="属性值" v-if="revsysIf" style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_revsys"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label"
                    />
                  </el-select>
                  <el-select v-model="attributeform.attriValue" placeholder="属性值" v-if="salecompIf" style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_salecomp"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label"
                    />
                  </el-select>
                  <el-select v-model="attributeform.attriValue" placeholder="属性值" v-if="workareaIf" style="width: 240px">
                    <el-option v-for="dict in dict.type.cost_workarea"
                               :key="dict.value"
                               :label="dict.value+'_'+dict.label"
                               :value="dict.value+'_'+dict.label"
                    />
                  </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button size="medium" @click="cancel1">取 消</el-button>
                <el-button size="medium" type="primary" @click="submitForm">确 定</el-button>
              </div>
            </el-dialog>

          </div>
          <finaccounting v-if="finaccounting" ref="finaccounting" @account="getAccount"></finaccounting>
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
  addFinanceAccount,
  getFinanceByproduct,
  updateAccount
} from "@/api/finance/ip/financeproduct";
import finaccounting from './FinanaceAccounting'


export default {
  name: "FinanceProductCost",
  dicts: ['cost_isbalchk','cost_mrvsip','cost_pdclassify','cost_revsys','cost_salecomp','cost_workarea','cost_pdUnit','cost_productAccountAttri'],
  components: {
    finaccounting
  },
  data() {
    return {
      isbalchkIf:false,
      mrvsipIf:false,
      pdclassifyIf:false,
      revsysIf:false,
      salecompIf:false,
      workareaIf:false,
      //增改加载按钮
      lisetloading:false,
      //成本科目编码
      costCodeAccount:'',
      financeAccountListtKey: 0,
      financeattritestListKey: 0,
      //财务资料表格
      finaccounting: false,
      statusText:'',
      statusTextUpdate:'',
      statusTextAdd:'',
      statusTextAddFinanceattri:'',
      //属性资料表格
      attributeTable: true,
      //边框是否编辑
      editInput: true,
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
      openDialog: {open: false, type: '', title: '', width: '500px', data: {},},
      // 测试属性表格数据
      financeattritestList: [],
      financeattritestListDialog: [],
      //财务表格数据
      financeAccountList: [],
      financeAccountListDialog:[],
      pagedata: {},
      pagedatatest: {},
      pagedataAdd:{},
      pagedataAddFinance:{},
      id: '',
      //科目名称转换
      accountName: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        leafId: '010103',
        productCode: ''
      },
      // 表单参数
      attributeform: {
        attriValue: '',
        attriName: '',
        attriDesc: '',
        parentId: ''
      },
      ruleForm: {compId: 'J00'},
      rules: {
        productCode: [
          {required: true, message: '请输入产副品编码', trigger: 'blur'},
        ],
        productCodeDesc: [
          {required: true, message: '请输入产副品名称', trigger: 'blur'},
        ],
        productAccountAttri: [
          {required: true, message: '请输入产副品帐务属性', trigger: 'blur'}
        ],
        pdUnit: [
          {required: true, message: '请输入计量单位', trigger: 'blur'}
        ],
        validateDate: [
          {required: true, message: '请输入有效期', trigger: 'blur'}
        ]
      },
    };

  },
  created() {

  },
  methods: {
    // 分页数据
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    /** 查询测试列表 */
    getList() {
      this.loading = false;
      getFinanceByproduct(this.queryParams).then(response => {
        this.financeAccountList = response.data.data.list;
        let j = this.financeAccountList.length
        for (let i = 0; i < j; i++) {
          this.financeAccountList[i].accountName = response.data.data.list[i].itemA + ':' + response.data.data.list[i].dataA
          this.$set(this.financeAccountList[i], 'costCode', response.data.data.list[i].costCode+'_'+response.data.data.list[i].costDesc);

          this.$set(this.financeAccountList[i], 'accountCode', response.data.data.list[i].accountCode);
          this.$set(this.financeAccountList[i], 'accountIdIdex', response.data.data.list[i].accountIdIdex);
          this.$set(this.financeAccountList[i], 'accountRefnoIndex', response.data.data.list[i].accountRefnoIndex);
        }
        this.total = response.data.data.total;
        this.loading = false;
      })
    },
    // 取消按钮
    cancel() {
      this.lisetloading = false
      //验证吧
      this.visible = false;
      //添加按键熟悉
      this.AddVisible = false,
        this.UpdateVisible = false;
      this.costCenterReadable = false;
      this.finaccounting = false
      this.determine = true;
      this.$emit('getLists');
    },
    //财务资料按钮
    accounting() {
      this.finaccounting = true;
      this.attributeTable = false

    },
    //属性资料按钮
    attributeData() {
      this.finaccounting = false;
      this.attributeTable = true
    },
    //属性页面取消,
    cancel1() {
      this.open = false;
    },
    //属性查询
    attribute() {
      this.financeattritestList = []
      this.financeattritestListDialog = []
      let j = this.dict.type.finance_producttype_name.length
      for (let i = 0; i < j; i++) {
        this.dict.type.finance_producttype_name[i].attriValue = ''
        this.dict.type.finance_producttype_name[i].parentid = this.financeattritestList[i].parentid
        this.financeattritestList.push(this.dict.type.finance_producttype_name[i])
        this.financeattritestListDialog.push(this.dict.type.finance_producttype_name[i])
      }

    },

    /** 删除按钮操作 */
    attributeDelete(row) {
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
    /** 财务资料删除按钮操作 */
    financeeDelete(row) {
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        this.financeAccountList.forEach(item => {
          if (item.id === row.id) {
            item['costCode'] = '';
            item['costCode'] = '';
            item['accountCode'] = '';
            item['accountIdIdex'] = '';
            item['accountRefnoIndex'] = '';
            item['costCodeAccount']=''
            this.financeAccountListDialog.push(item)
          }
        });
        this.financeAccountListtKey += 1;
      })
    },
//确定报存
    submitFormAdd(formName) {
      this.lisetloading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.id != null) {
            this.lisetloading = false
            updateFinancetest(this.ruleForm).then(response => {
              this.pagedata.propertydata = this.financeattritestListDialog;
              this.pagedata.productCode = response.data.data[0].productCode
              console.log(this.pagedata);
              updateFinanceattri(this.pagedata).then(response => {
                this.statusText = response.statusText
              })
              this.pagedatatest.propertydata = this.financeAccountListDialog
              this.pagedatatest.productCode = response.data.data[0].productCode
              this.pagedatatest.costCode = this.costCodeAccount
              console.log(this.pagedatatest);
              updateAccount(this.pagedatatest).then(response => {
                this.statusTextUpdate = response.statusText
              })
              if (this.pagedata.productCode !== null && this.statusText !== null && this.statusTextUpdate !== null) {
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
            this.ruleForm.compId = 'J00'
            this.lisetloading = false

            addFinancetest(this.ruleForm).then(response => {
              console.log(response.data.data);
              this.pagedataAdd.propertydata = this.financeattritestList;
              this.pagedataAdd.productCode = response.data.data[0].productCode
              this.pagedataAdd.compId =response.data.data[0].compId
              addFinanceattri(this.pagedataAdd).then(response => {
                this.statusTextAdd = response.statusText
              })
              this.pagedataAddFinance.propertydata = this.financeAccountList
              this.pagedataAddFinance.productCode = response.data.data[0].productCode
              this.pagedataAddFinance.compId = response.data.data[0].compId
              console.log(this.pagedataAddFinance);
              addFinanceAccount(this.pagedataAddFinance).then(response => {
                this.statusTextAddFinanceattri = response.statusText
              })
              if (this.pagedata.productCode !== null && this.statusTextAdd !== null && this.statusTextAddFinanceattri !== null) {
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
          this.$emit('getLists');
        } else {
          return false;
        }
      });
      this.lisetloading = false
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.open = true;
      attributeQuery().then(response => {
        this.financeattritestList = response.data.data;
      })
    },
    //属性添加查询
    handleAdd(row, index) {
      this.open = true;
      this.openDialog.type = index;
      this.isbalchkIf=false;
      this.mrvsipIf=false;
      this.pdclassifyIf=false;
      this.revsysIf=false;
      this.salecompIf=false;
      this.workareaIf=false;
      this.attributeform.attriName = this.financeattritestList[index].value
      this.attributeform.attriDesc = this.financeattritestList[index].label
      if (this.financeattritestList[index].value == 'ISBALCHK') {
        this.isbalchkIf = true
      }
      if (this.financeattritestList[index].value == 'MRVSIP') {
        this.mrvsipIf = true
      }
      if (this.financeattritestList[index].value == 'PDCLASSIFY') {
        this.pdclassifyIf = true
      }
      if (this.financeattritestList[index].value == 'REVSYS') {
        this.revsysIf = true
      }
      if (this.financeattritestList[index].value == 'SALECOMP') {
        this.salecompIf = true
      }
      if (this.financeattritestList[index].value == 'WORKAREA') {
        this.workareaIf = true
      }
      this.attributeform.attriValue = this.financeattritestList[index].attriValue
    },
    //属性修改查询
    handleUpd(row, index) {
      this.open = true;
      this.openDialog.type = index;
      this.isbalchkIf=false;
      this.mrvsipIf=false;
      this.pdclassifyIf=false;
      this.revsysIf=false;
      this.salecompIf=false;
      this.workareaIf=false;
      this.attributeform.attriName = this.financeattritestList[index].value
      this.attributeform.attriDesc = this.financeattritestList[index].label
      if (this.financeattritestList[index].value == 'ISBALCHK') {
        this.isbalchkIf = true
      }
      if (this.financeattritestList[index].value == 'MRVSIP') {
        this.mrvsipIf = true
      }
      if (this.financeattritestList[index].value == 'PDCLASSIFY') {
        this.pdclassifyIf = true
      }
      if (this.financeattritestList[index].value == 'REVSYS') {
        this.revsysIf = true
      }
      if (this.financeattritestList[index].value == 'SALECOMP') {
        this.salecompIf = true
      }
      if (this.financeattritestList[index].value == 'WORKAREA') {
        this.workareaIf = true
      }
      this.attributeform.attriValue = this.financeattritestList[index].attriValue
    },
    //财务添加查询
    handleAccount(row, index) {
      this.finaccounting = true
      this.$nextTick(() => {
        this.$refs.finaccounting.AddAccount(index, this.financeAccountList, this.ruleForm.productCode + '_' + this.ruleForm.productCodeDesc)
      })
    },
    //财务修改查询
    handleUpAccount(row, index) {
      this.finaccounting = true
      this.$nextTick(() => {
        this.$refs.finaccounting.UpAccount(index, this.financeAccountList, this.ruleForm.productCode + '_' + this.ruleForm.productCodeDesc)
      })
    },
    //属性表单确定
    submitForm() {
      this.financeattritestListDialog[this.openDialog.type].attriValue = this.attributeform.attriValue;
      this.financeattritestList = []
      this.financeattritestListDialog.forEach(item => {
        this.financeattritestList.push(item)
      })
      this.open = false;
    },
    // 表单重置
    reset() {
      this.ruleForm = {
        compId: 'J00',
        fiscalYear: '',
        costCenter: '',
        costCenterDesc: '',
        ccType: '',
        isProduct: '',
        stusCode: '',
        isPostAc: '',
        updateDate: '',
        updateUserId: '',
        createDate: '',
        createUser: '',
        attriValue: ''
      };
      this.resetForm("ruleForm");
    },
    //属性查询
    AddPage(dictType) {

      this.editInput = false
      this.ruleForm = {}
      this.financeattritestList = []
      this.financeattritestListDialog = []
      let j = dictType.length
      for (let i = 0; i < j; i++) {
        dictType[i].attriValue = ''
        this.financeattritestList.push(dictType[i])
        this.financeattritestListDialog.push(dictType[i])
      }
      this.queryParams.productCode = ''
      getFinanceInformation(this.queryParams).then(response => {
        this.financeAccountList = response.data.data.list;
        let j = this.financeAccountList.length
        for (let i = 0; i < j; i++) {
          this.financeAccountList[i].accountName = response.data.data.list[i].itemA + ':' + response.data.data.list[i].dataA
        }
        this.total = response.data.data.total;
        this.loading = false;
      })
      this.visible = true
      this.AddVisible = true
    },

    //详情
    Details: function (productCode, id, dictType) {
      this.editInput = true
      this.visible = true
      this.determine = false;
      getFinancetest(productCode, id).then(response => {
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
      attributeQuery(productCode).then(response => {
        let tempList = response.data.data
        this.financeattritestList = []
        this.financeattritestListDialog = []
        for (let x = 0; x < dictType.length; x++) {
          let t = ''
          for (let i = 0; i < tempList.length; i++) {
            if (dictType[x].value == tempList[i].attriName) {
              t = tempList[i].attriValue
              break
            }
          }
          dictType[x].attriValue = t
          this.financeattritestList.push(dictType[x])
          this.financeattritestListDialog.push(dictType[x])
        }
      }),
        this.queryParams.productCode = productCode
      getFinanceByproduct(this.queryParams).then(response => {
        this.financeAccountList = response.data.data.list;
        let j = this.financeAccountList.length
        for (let i = 0; i < j; i++) {
          this.financeAccountList[i].accountName = response.data.data.list[i].itemA + ':' + response.data.data.list[i].dataA
          this.$set(this.financeAccountList[i], 'costCode', response.data.data.list[i].costCode+'_'+response.data.data.list[i].costDesc);
          this.$set(this.financeAccountList[i], 'accountCode', response.data.data.list[i].accountCode);
          this.$set(this.financeAccountList[i], 'accountIdIdex', response.data.data.list[i].accountIdIdex);
          this.$set(this.financeAccountList[i], 'accountRefnoIndex', response.data.data.list[i].accountRefnoIndex);
        }
        this.total = response.data.data.total;
        this.loading = false;
      })

    },
    //产副品修改弹窗查询
    Update(productCode, id, dictType) {
      this.editInput = true
      this.visible = true
      this.UpdateVisible = true
      this.costCenterReadable = true
      getFinancetest(productCode, id).then(response => {
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

      attributeQuery(productCode).then(response => {

        let tempList = response.data.data
        this.id = response.data.data.id
        this.financeattritestList = []
        this.financeattritestListDialog = []

        for (let x = 0; x < dictType.length; x++) {
          let t = ''
          let c = ''
          let b = ''
          for (let i = 0; i < tempList.length; i++) {

            if (dictType[x].value == tempList[i].attriName) {
              t = tempList[i].attriValue
              c = tempList[i].id
              b = tempList[i].productCode
              break
            }
          }
          dictType[x].attriValue = t
          dictType[x].id = c
          dictType[x].productCode = b
          this.financeattritestList.push(dictType[x])
          this.financeattritestListDialog.push(dictType[x])
        }
      }),
        this.queryParams.productCode = productCode
      getFinanceByproduct(this.queryParams).then(response => {
        this.financeAccountList = response.data.data.list;

        let j = this.financeAccountList.length
        for (let i = 0; i < j; i++) {
          this.financeAccountList[i].accountName = response.data.data.list[i].itemA + ':' + response.data.data.list[i].dataA
          this.$set(this.financeAccountList[i], 'costCodeAccount', response.data.data.list[i].costCode);
          if(response.data.data.list[i].costCode != "") {
            this.$set(this.financeAccountList[i], 'costCode', response.data.data.list[i].costCode + '_' + response.data.data.list[i].costDesc);
          }
          if(response.data.data.list[i].accountChineseName != null){
            this.$set(this.financeAccountList[i], 'accountCode', response.data.data.list[i].accountCode+'_'+response.data.data.list[i].accountChineseName);
          }
          this.$set(this.financeAccountList[i], 'accountIdIdex', response.data.data.list[i].accountIdIdex);
          this.$set(this.financeAccountList[i], 'accountRefnoIndex', response.data.data.list[i].accountRefnoIndex);
          this.financeAccountListDialog.push(this.financeAccountList[i])

        }
        this.total = response.data.data.total;
        this.loading = false;
      })

    },
    getAccount(value) {
      let i = ''
      i = value.Id
      this.$set(this.financeAccountList[i], 'costCodeAccount',value.costCode);
      this.$set(this.financeAccountList[i], 'costCode', value.costCode);
      this.$set(this.financeAccountList[i], 'accountCode', value.accountCode);
      this.$set(this.financeAccountList[i], 'accountIdIdex', value.accountIdIdex);
      this.$set(this.financeAccountList[i], 'accountRefnoIndex', value.accountRefnoIndex);
      this.financeAccountListDialog.push(this.financeAccountList[i])
    }

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
