<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="155px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="样品类别号" prop="sampleClass">
            <el-input v-model="addForm.sampleClass" placeholder="请输入样品类别号" style="width: 50%">
              <i slot="suffix" class="el-input__icon el-icon-plus" @click="handleQueryPlus" style="cursor:pointer;"></i> </el-input>
            <span class="el-text"> *</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="样品类别描述" prop="sampleDesc">
            <el-input v-model="addForm.sampleDesc" :disabled="true" style="width: 50%;"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-table height="55vh" size="small" :data="tableData" stripe :row-style="{height:'80px'}"
                :default-sort="{prop: 'createDate', order: 'descending'}">
        <el-table-column
          label="数目"
          type="index"
          width="50">
        </el-table-column>
        <template v-for="column in columns">
          <el-table-column
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :sortable="column.sortable"
            :width="column.width"
            :min-width="column.minWidth"
          />
        </template>
        <el-table-column label="最低值" width="200px">
          <template slot-scope="scope">
            <el-form :model="scope.row" :rules="checkParamsRule" ref="form1" >
              <el-form-item prop="minValue">
                <el-input
                  v-model="scope.row.minValue"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="最高值" width="200px">
          <template slot-scope="scope">
            <el-form :model="scope.row" :rules="checkParamsRule" ref="form1" >
              <el-form-item prop="maxValue">
                <el-input
                  v-model="scope.row.maxValue"
                  :disabled="status"
                >
                </el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="180px">
          <template slot-scope="scope">
            <el-button size="mini" type="primary"
                       @click="handleAdd(scope.$index, scope.row)">+
            </el-button>
            <el-button size="mini" type="danger"
                       @click="handleDelete(scope.$index, scope.row)">-
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <div>
      <el-row>
        <el-col :span="12">
          <p> 新增人员:{{ this.dataEdit.createUser }}</p>
        </el-col>
        <el-col :span="12">
          <p> 新增日期:{{this.dataEdit.createTime }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <p> 修改人员:{{ this.dataEdit.updateUser }}</p>
        </el-col>
        <el-col :span="12">
          <p> 修改日期:{{ this.dataEdit.updateTime }}</p>
        </el-col>
      </el-row>
    </div>
    <div slot="footer" class="el-dialog__footer">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" :loading="states1" @click="handleChange('addForm')">确 定</el-button>
    </div>

    <!-- "+"对话框 -->
    <el-dialog :title="title" :visible.sync="openPlus" width="1000px" :close-on-click-modal="false" class="customDialogStyle" v-if="openPlus" append-to-body>
      <el-form label-width="135px" >
        <el-form-item label="样品类别前两码" prop="sampleClass">
          <el-input v-model="addForm.sampleClass" style="width: 231px" placeholder="模糊查询"></el-input>
          <el-button type="primary" size="medium" icon="el-icon-search" @click="handleQueryBtn" style="margin-left: 40px;">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table stripe
                height="55h"
                :data="tableDataPlus"
                tooltip-effect="dark"
                @row-click="singleElection"
                style="margin: 0 20px;width: auto;height: 400px">
        <el-table-column label="" width="30">
          <template slot-scope="scope">
            <el-radio class="radio" v-model="templateSelection" :label="scope.$index">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="样品类别" align="center" prop="sampleClass" />
        <el-table-column label="样品类别说明" align="center" prop="sampleDesc" />
      </el-table>
      <div style="margin-top: 10px;right: 0" class="avue-crud__pagination">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagePlusAdd.currentPage"
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pagePlusAdd.pageSize"
          :layout="pagePlusAdd.layout"
          :total="pagePlusAdd.total">
        </el-pagination>
      </div>
      <div slot="footer" class="el-dialog__footer1">
        <el-button type="primary" @click="submitFormPlus()">确 定</el-button>
        <el-button type="warning" @click="handleEmpty">重 置</el-button>
        <el-button @click="cancelPlus()">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 试验元素高低标准设置对话框 -->
    <el-dialog :title="titleElement" :visible.sync="openPlusElement" width="1200px" :close-on-click-modal="false" class="customDialogStyle" v-if="openPlusElement" append-to-body>
      <el-table stripe
                height="55h"
                :data="tableDataPlusElement"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange"
                :row-style="{height:'80px !important'}"
                style="margin: 0 20px;width: auto;height: 500px;">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="试验元素代号" align="center" prop="elementIndex" />
        <el-table-column label="元素名称/试验项目" align="center" prop="elementName" />
        <el-table-column label="单位" align="center" prop="elementUnit" />
      </el-table>
      <div style="margin-top: 10px;right: 0" class="avue-crud__pagination">
        <el-pagination
          background
          @size-change="handleSizeChange2"
          @current-change="handleCurrentChange2"
          :current-page="pagePlusAdd.currentPage"
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pagePlusAdd.pageSize"
          :layout="pagePlusAdd.layout"
          :total="pagePlusAdd.total">
        </el-pagination>
      </div>
      <div slot="footer" class="el-dialog__footer1">
        <el-button @click="cancelElement()">取 消</el-button>
        <el-button type="primary" @click="submitFormElement()">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {queryInfo, queryInfoClass, query, delInfoA} from "@/api/quality/ts/sampleStandard";
import {queryInfoYS} from "@/api/quality/ts/elementTable";

  export default {
    name: "sampleStandardAdd",
    props: [
      'dataEdit',
      'editBox'],
    data() {
      var checkA = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('样品类别号栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 20) {
            callback(new Error('样品类别号参数长度为 20 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkB = (rule, value, callback) => {
        this.minValue = value
        this.status = false
        if (value != null) {
          if (isNaN(value)) {
            callback(new Error('请输入数字值'));
          }else {
            if (value > 9999.9999) {
              callback(new Error('整数参数长度为 4，请重新输入！'));
            } else {
              if(typeof (value.toString().split(".")[1]) != 'undefined'){
                if(value.toString().split(".")[1].length>4){
                  callback(new Error('小数参数长度为 4，请重新输入！'));
                }else {
                  callback();
                }
              }
            }
          }
        } else {
          callback(new Error('请输入最小值'));
        }
      };
      var checkC = (rule, value, callback) => {
        this.maxValue = value
        if (value != null) {
          if (isNaN(value)) {
            callback(new Error('请输入数字值'));
          }else {
            if (value > 9999.9999) {
              callback(new Error('整数参数长度为 4，请重新输入！'));
            } else {
              if(typeof (value.toString().split(".")[1]) != 'undefined'){
                if(value.toString().split(".")[1].length>4){
                  callback(new Error('小数参数长度为 4，请重新输入！'));
                }else {
                  callback();
                }
              }
              if (
                Number(this.maxValue) &&
                Number(this.minValue) &&
                Number(this.minValue) >= Number(this.maxValue)
              ) {
                return callback(new Error("最大值不能小于等于最小值"))
              } else {
                this.minValue = '';
                this.maxValue = '';
                this.status = true
                callback();
              }
            }
          }
        } else {
          callback(new Error('请输入最大值'));
        }
      };
      return {
        message: '',
        templateSelection: '',
        // 是否显示弹出层
        openPlus: false,
        // 是否显示弹出层
        openPlusElement: false,
        // 弹出层标题
        title: "",
        // 试验元素弹出层标题
        titleElement: "",
        pagePlusAdd: {
          pageSize: 20,
          currentPage: 1,
          total: 0,
          layout: "total, sizes, prev, pager, next, jumper",
        },
        query: {
          sampleClass: '',
          pageSize: 20,
          pageNum: 0,
        },
        addForm: {
          sampleClass: '',
          sampleDesc: '',
        },
        minValue: '',
        maxValue: '',
        columns: [
          {label: '试验元素代号', prop: "elementIndex", sortable: true, minWidth: '100px'},
          {label: '元素名称/试验项目', prop: "elementName", sortable: true, minWidth: '150px'},
          {label: '单位', prop: "elementUnit", sortable: true, minWidth: '100px'},
        ],
        tableData: [
          {sampleClass: '', '蒸汽产率': '55',},
        ],
        tableDataPlus: [
          {sampleClass: '', '蒸汽产率': '55',},
        ],
        tableDataPlusElement: [],
        rules: {
          sampleClass: [
            { validator: checkA, trigger: 'blur' }
          ],
        },
        checkParamsRule: {
          minValue: [
            { validator: checkB, trigger: 'blur' }
          ],
          maxValue: [
            { validator: checkC, trigger: 'blur' }
          ]
        },
        selectionData:[],
        states1:false,
        status: true
      }
    },
    computed: {},
    created() {
      if (this.editBox == true) {
        //编辑查询
        query(this.dataEdit.sampleClass).then(response => {
          if (response.data.data.elementHighLowStandardList.length == 0) {
            this.tableData = [{sampleClass: '', '蒸汽产率': '55',}];
          } else {
            this.tableData = response.data.data.elementHighLowStandardList
            this.addForm.sampleDesc = response.data.data.sampleBasicDataList[0].sampleDesc
          }
        })
      }
    },
    mounted() {
      this.addForm = {...this.dataEdit}
    },
    methods: {
      singleElection (row) {
        this.templateRadio = row.id;
        this.addForm.sampleClass = row.sampleClass
        this.addForm.sampleDesc = row.sampleDesc
      },
      // 二级页面新增、编辑确定
      handleChange(form) {
        this.states1 = true;
        this.$refs[form].validate((valid) => {
          if (valid) {
            let formHandleChange=''
            if (typeof (this.$refs['form1'])!='undefined'){
              formHandleChange='form1'
            } else {
              this.states1 = false;
              this.addForm.elementHighLowStandard = this.tableData
              this.$emit('submitAdd', this.addForm)
            }
            this.$refs[formHandleChange].validate((valid) => {
              if (valid) {
                if(!('蒸汽产率' in this.tableData[0])){
                  this.tableData.forEach((item)=>{
                    item.sampleClass = this.addForm.sampleClass;
                    // item.createUser = this.userInfo.userName
                    // item.updateUser = this.userInfo.userName
                  })
                  this.addForm.elementHighLowStandard = this.tableData
                  this.$emit('submitAdd', this.addForm)
                } else {
                  this.states1 = false;
                  this.addForm.elementHighLowStandard = this.tableData
                  this.$emit('submitAdd', this.addForm)
                }
              }else {
                this.states1 = false;
                this.$message.error("填写栏位不符合规范！");
                return false
              }
            })
          } else {
            this.states1 = false;
            this.$message.error("请完整填写表单！");
            return false;
          }
        });
      },
      /** "+"按钮操作 */
      handleQueryPlus() {
        this.addForm = {
          sampleClass: '',
          pageSize: 20,
          pageNum: 1,
        },
        this.openPlus = true;
        this.title = "样品类别查询";
        queryInfoClass(this.addForm).then(response => {
          this.tableDataPlus = response.data.data.list
          this.pagePlusAdd.total = response.data.data.total
        })
      },
      /** 试验元素代号高低标准操作 */
      handleAdd() {
        this.openPlusElement = true;
        this.titleElement = "试验元素高低标准设置";
        this.addFormYS = {
          pageSize: 20,
          pageNum: 1,
        }
        queryInfoYS(this.addFormYS).then(response => {
          this.tableDataPlusElement = response.data.data.list
          this.pagePlusAdd.total = response.data.data.total
        })
      },
      handleDelete(index,row) {
        if ( '蒸汽产率' in this.tableData[0] ){
          this.$message({type: 'error', message: '请勿删除默认数据！'});
        }else {
          this.$confirm('此操作将永久删除, 是否继续?', '提示', {
            confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
          }).then(() => {
            if (this.editBox == true) {
              //调用删除的方法
              delInfoA(row.elementIndex, this.addForm.sampleClass).then( response => {
                this.query = {
                  sampleClass: this.addForm.sampleClass,
                  pageSize: 20,
                  pageNum: 0
                }
                if(this.tableData.length===0){
                  this.tableData.push({sampleClass: '', '蒸汽产率': '55',})
                }
                this.getList();
                if(response.data.code == '0') {
                  this.$message({
                    type: 'success',
                    message: response.data.msg
                  });
                }
              })
            }else {
              this.tableData.splice(index,1)
              if ( this.tableData.length===0 ){
                this.tableData = [{sampleClass: '', '蒸汽产率': '55',}];
              }
            }
          }).catch(() => {
            this.$message({type: 'info', message: '已取消删除'});
          })
        }
      },
      // 清空
      handleEmpty() {
        this.addForm = {
          sampleClass: '',
          pageSize: 20,
          pageNum: 0,
        },
          this.templateSelection= '',
          this.getList()
      },
      //"+"查询
      handleQueryBtn() {
        queryInfoClass(this.addForm).then(response => {
          this.tableDataPlus = response.data.data.list
          this.pagePlusAdd.total = response.data.data.total
        })
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('close')
      },
      cancelPlus() {
        this.openPlus = false;
        this.query = {
          sampleClass: '',
          pageSize: 20,
          pageNum: 0,
        },
        this.templateSelection= ''
      },
      cancelElement() {
        this.openPlusElement = false;
        this.query = {
          sampleClass: '',
          pageSize: 20,
          pageNum: 0,
        }
      },
      // 分页-每页多少条
      handleSizeChange(val) {
        this.query.pageSize = val
        this.getList2()
      },
      // 试验表格分页-每页多少条
      handleSizeChange2(val) {
        this.query.pageSize = val
        this.getList3()
      },
      // 分页-当前页
      handleCurrentChange2(val) {
        this.query.pageNum = val
        this.getList3()
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.query.pageNum = val
        this.getList2()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
        this.selectionData = selection
      },
      getList2() {
        queryInfoClass(this.query).then(response => {
          this.tableDataPlus = response.data.data.list
          this.pagePlusAdd.total = response.data.data.total
        })
      },
      //获取数据刷新页面
      getList() {
        queryInfo(this.query).then(response => {
          this.tableData = response.data.data.list
        })
      },
      //获取试验元素表格数据刷新页面
      getList3() {
        queryInfoYS(this.query).then(response => {
          this.tableDataPlusElement = response.data.data.list
          this.pagePlusAdd.total = response.data.data.total
        })
      },
      submitFormPlus() {
        this.openPlus = false;
      },
      // 三级页面保存
      submitFormElement(){
        this.selectionData.forEach(item=>{
          item.createUser = undefined
          item.createTime = undefined
          item.updateUser = undefined
          item.updateTime = undefined
        })
        this.selectionData.forEach((item)=>{
          if (this.editBox == true){
            // item.createUser = this.userInfo.userName;
          }
        })
        if( this.editBox == false ){
          this.tableData=[...this.tableData,...this.selectionData]
          if ( '蒸汽产率' in this.tableData[0] ){
            this.tableData.splice(0,1)
          }
        }else {
          this.tableData=[...this.tableData,...this.selectionData]
          if ( '蒸汽产率' in this.tableData[0] ){
            this.tableData.splice(0,1)
          }
        }
        this.tableData.forEach(item=>{
          item.updateUser = undefined
          item.updateTime = undefined
        })
        this.openPlusElement = false;
      }
    }
  }
</script>

<style scoped>
/deep/ .el-form-item__content {
  white-space: nowrap;
}
/deep/ .el-text{
  color: red;
}
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-dialog__footer {
  padding: 15px 0 25px;
}
.el-dialog__footer1 {
  padding: 0px 40px 15px;
}

</style>
