<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="155px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="表格代号" prop="formatId">
            <el-input v-model="addForm.formatId" :disabled="status"  style="width: 60%!important;"/>
            <span class="el-text"> *</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="COUNT" prop="elementConut">
            <el-input v-model="addForm.elementConut" :disabled="true" style="width: 50%;"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="表格说明" prop="formatMemo">
          <el-input v-model="addForm.formatMemo" style="width: 60%;"/>
        </el-form-item>
      </el-row>
      <el-table height="50vh" size="small" :data="tableData" stripe
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
            :render-header="addRedStar"
          />
        </template>
        <el-table-column prop="elementSeq" label="显示顺序" v-if="status">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.elementSeq"
            >
            </el-input>
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
          <p> 新增人员：{{ this.dataEdit.createUser }}</p>
        </el-col>
        <el-col :span="12">
          <p> 新增日期：{{ this.dataEdit.createTime }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <p> 修改人员：{{ this.dataEdit.updateUser }}</p>
        </el-col>
        <el-col :span="12">
          <p> 修改日期：{{ this.dataEdit.updateTime }}</p>
        </el-col>
      </el-row>
    </div>
    <div slot="footer" class="el-dialog__footer">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" @click="handleChange('addForm')" :loading="states1">确 定</el-button>
    </div>

    <!-- 试验元素代号设置对话框 -->
    <el-dialog :title="titleElement" v-if="openPlusElement" :visible.sync="openPlusElement" width="1200px" :close-on-click-modal="false" class="customDialogStyle" append-to-body>
      <el-table stripe
                height="55h"
                :data="tableDataPlusElement"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange"
                style="margin: 0 20px;width: auto;height: 500px;">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="试验元素代号" align="center" prop="elementIndex" />
        <el-table-column label="元素名称/试验项目" align="center" prop="elementName" />
        <el-table-column label="单位" align="center" prop="elementUnit" />
      </el-table>
      <div style="position: absolute; bottom: 176px; left: 60px"><span style="color: red">注意：如有翻页选择需求，请先保存当页选择元素或者调整当页显示条数再进行选择</span></div>
      <div style="margin-top: 10px;right: 0" class="avue-crud__pagination">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagePlus.currentPage"
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pagePlus.pageSize"
          :layout="pagePlus.layout"
          :total="pagePlus.total">
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
import {queryInfo, queryInfoYS, query, delInfoA} from "@/api/quality/ts/elementTable";

  export default {
    name: "elementTableAdd",
    props: [
      'dataEdit',
      'editBox',
      'status'
    ],
    data() {
      var checkA = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('表格代号栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 10) {
            callback(new Error('表格代号参数长度为 10 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
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
        query: {
          pageSize: 20,
          pageNum: 0,
        },
        // 试验元素弹出层标题
        titleElement: "",
        pagePlus: {
          pageSize: 20,
          currentPage: 1,
          total: 0,
          layout: "total, sizes, prev, pager, next, jumper",
        },
        addForm: {
          formatId: '',
          formatMemo: '',
        },
        columns: [
          {label: '试验元素代号', prop: "elementIndex", sortable: true, minWidth: '100px'},
          {label: '元素名称/试验项目', prop: "elementName", sortable: true, minWidth: '150px'},
          {label: '单位', prop: "elementUnit", sortable: true, minWidth: '100px'},
        ],
        tableData: [
          {formatId: '', '蒸汽产率': '55',},
        ],
        tableDataPlusElement: [],
        rules: {
          formatId: [
            { validator: checkA, trigger: 'blur' }
          ]
        },
        selectionData:[],
        states1:false
      }
    },
    computed: {},
    created() {
      if (this.editBox == true){
        //编辑查询
        this.dataEdit.pageNum = 1
        this.dataEdit.pageSize = 20
        query(this.dataEdit).then(response => {
          if (response.data.data.tableDataList.length === 0) {
            this.tableData = [{formatId: '', '蒸汽产率': '55',},]
          }else {
            this.tableData = response.data.data.tableDataList
            this.addForm.formatMemo = response.data.data.descriptionList[0].formatMemo
            this.addForm.elementConut = response.data.data.total
          }
        })
      }
    },
    mounted() {
      this.addForm = {...this.dataEdit}
    },
    methods: {
      // 二级页面新增、编辑确定
      handleChange(form) {
        this.states1 = true;
        this.$refs[form].validate((valid) => {
          if (valid) {
            if(!('蒸汽产率' in this.tableData[0])){
              this.tableData.forEach((item)=>{
                item.formatId = this.addForm.formatId;
              })
              // this.addForm.createUser = this.userInfo.userName
              // this.addForm.updateUser = this.userInfo.userName
              this.addForm.elementTable = this.tableData
              this.$emit('submitAdd', this.addForm)
            } else {
              this.states1 = false;
              this.$message.error("请完整填写表单！");
              return false;
            }
          } else {
            this.states1 = false;
            this.$message.error("请完整填写表单！");
            return false;
          }
        });
      },
      /** 试验元素代号操作 */
      handleAdd() {
        this.openPlusElement = true;
        this.titleElement = "试验元素代号设置";
        this.addFormYS = {
          pageSize: 20,
          pageNum: 0,
        }
        queryInfoYS(this.addFormYS).then(response => {
          this.tableDataPlusElement = response.data.data.list
          this.pagePlus.total = response.data.data.total
        })
      },
      handleDelete(index,row) {
        if ( '蒸汽产率' in this.tableData[0] ){
          this.$message({type: 'error', message: '请勿删除默认数据！'});
        }else {
          this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
            confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
          }).then(() => {
            if (this.editBox == true) {
              // 调用删除
              delInfoA(row.elementIndex, this.addForm.formatId).then(response => {
                this.query = {
                  formatId: this.addForm.formatId,
                  pageSize: 20,
                  pageNum: 1
                }
                query(this.query).then(response => {
                  this.tableData = response.data.data.tableDataList
                  if (this.tableData.length === 0) {
                    this.tableData = [{formatId: '', '蒸汽产率': '55',},]
                  }
                  this.addForm.formatMemo = response.data.data.descriptionList[0].formatMemo
                  this.addForm.elementConut = response.data.data.total
                })
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
                this.tableData = [{formatId: '', '蒸汽产率': '55',}];
              }
            }
          }).catch(() => {
            this.$message({type: 'info', message: '已取消删除'});
          });
        }
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('close')
        this.$emit('cancelQuery')
      },
      cancelElement() {
        this.openPlusElement = false;
        this.query = {
          formatId: '',
          pageSize: 20,
          pageNum: 0,
        }
      },
      // 分页-每页多少条
      handleSizeChange(val) {
        this.query.pageSize = val
        this.getList3()
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.query.pageNum = val
        this.getList3()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
        this.selectionData = selection
      },
      //获取数据刷新页面
      getList() {
        queryInfo(this.query).then(response => {
          this.tableData = response.data.data.list
          this.page.total = response.data.data.total
        })
      },
      //获取试验元素表格数据刷新页面
      getList3() {
        queryInfoYS(this.query).then(response => {
          this.tableDataPlusElement = response.data.data.list
          this.pagePlus.total = response.data.data.total
        })
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
      },
      // 必选标识
      addRedStar(h, { column }) {
        return [
          h('span', { style: 'color: #F56C6C' }, '*'),
          h('span', ' ' + column.label)
        ];
      },
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
