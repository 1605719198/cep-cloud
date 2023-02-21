<template>
  <div class="avue-crud">
    <el-form>
      <el-table height="60vh"
                size="small"
                :data="tableData"
                stripe
                :default-sort="{prop: 'createDate', order: 'descending'}">
        <template v-for="column in columns">
          <el-table-column :key="column.prop"
                           :prop="column.prop"
                           :label="column.label"
                           :sortable="column.sortable"
                           :width="column.width"
                           :min-width="column.minWidth" />
        </template>
        <el-table-column fixed="right"
                         label="操作"
                         width="270px"
                         v-if="showLabel">
          <template slot-scope="scope">
            <div v-if="editBtn">
              <el-button v-hasPermi="['mill_doSystemAdd']"
                         size="mini"
                         plain
                         icon="el-icon-plus"
                         type="primary"
                         @click="handleBtn(scope.$index, scope.row)">添加
              </el-button>
              <el-button v-hasPermi="['mill_doSystemDelete']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="danger"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </div>
            <div v-if="addBtn">
              <el-button v-hasPermi="['mill_doSystemAdd']"
                         size="mini"
                         plain
                         icon="el-icon-plus"
                         type="primary"
                         @click="handleBtn(scope.$index, scope.row)">添加
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-form>

    <el-dialog :title="title"
               v-if="open"
               :visible.sync="open"
               width="500px"
               :close-on-click-modal="false"
               class="customDialogStyle"
               append-to-body>
      <el-form ref="formDataXT"
               :model="formDataXT"
               :rules="rules"
               label-width="100px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="产线代码："
                          prop="millId">
              <div>{{formDataXT.millId}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产线名称："
                          prop="millName">
              <div>{{formDataXT.millName}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="系统代码："
                          prop="sysId">
              <el-select class="activityId"
                         v-model="formDataXT.sysId"
                         :popper-append-to-body="false"
                         placeholder="请选择系统代码">
                <el-option v-for="dict in optionsSysId"
                           :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer"
           class="el-dialog__footer">
        <el-button @click="cancel('formDataXT')">取 消</el-button>
        <el-button type="primary"
                   @click="submitForm('formDataXT')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addInfoSystem, deleteInfoSystem, queryInfoSys } from "@/api/finance/IP/productionLineData";

export default {
  name: "ProductionLineDataXT",
  props: {
    formDataXT: Object,
    type: String,
    millId: String,
    millName: String,
    millId2: String,
  },
  data () {
    return {
      showLabel: true,
      editBtn: false,
      addBtn: false,
      templateSelection: '',
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: "",
      addForm: {

      },
      rules: {
        sysId: [{ required: true, message: '请输入系统代码', trigger: 'blur' },],
      },
      columns: [
        { label: '系统代码', prop: "sysId", sortable: true, minWidth: '100px' },
        { label: '新增人员', prop: "createUser", sortable: true, minWidth: '100px' },
        { label: '新增时间', prop: "createTime", sortable: true, minWidth: '100px' },
      ],
      optionsSysId: [],
      tableData: [
        { sampleClass: '2016', '全焦产率': '11', '冶金焦产率': '23', '干熄焦产率': '41', '石膏产率': '43', '蒸汽产率': '55', },
      ],
      dataTemp: {
        millId: '',
      }
    }
  },
  watch: {
    formDataXT: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  created () {
    this.getDicts("cost_line_sys").then(response => {
      this.optionsSysId = response.data.data;
    })
    if (this.type == 'add') {
      this.addBtn = true
    }
    if (this.type == 'edit' || this.type == 'info') {
      this.editBtn = true
      this.getList();
    }
    if (this.type == 'info') {
      this.showLabel = false
    }
    this.formDataXT.millName = this.millName
    this.formDataXT.millId = this.millId
    this.dataTemp.millId = this.millId2
  },
  computed: {},
  methods: {
    singleElection (row) {
      this.templateRadio = row.id;
      this.addForm.sampleClass = row.sampleClass
    },
    /** 按钮操作 */
    handleBtn () {
      this.formDataXT.millName = this.millName
      this.formDataXT.millId = this.millId
      if (this.formDataXT.millId === '' || typeof this.formDataXT.millId == 'undefined') {
        this.$message({
          type: 'error',
          message: '请填写产线代码'
        });
      } else {
        this.open = true;
        this.title = "相关系统";
        this.reset();
        this.$set(this.formDataXT, 'millName', this.millName);
        this.$set(this.formDataXT, 'millId', this.millId);
      }
    },
    // 删除
    handleDelete (index, row) {
      const sysId = row.sysId;
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        deleteInfoSystem(this.millId, sysId).then(response => {
          this.getList();
          this.$message({
            type: 'success',
            message: response.data.msg
          });
        })
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 取消按钮
    cancel (formName) {
      this.open = false;
      this.reset();
      this.$refs[formName].resetFields();
    },
    // 表单重置
    reset () {
      this.$set(this.formDataXT, 'sysId', undefined);
    },
    /** 提交按钮 */
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // this.formDataXT.createUser = this.userInfo.userName;
          addInfoSystem(this.formDataXT).then(response => {
            this.open = false;
            this.getList()
            if (response.data.code == 0) {
              this.$message({
                type: 'success',
                message: response.data.msg
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.query.pageSize = val
      this.getList()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.query.pageNum = val
      this.getList()
    },
    //获取数据刷新页面
    getList () {
      if (this.type == 'edit' || this.type == 'info') {
        queryInfoSys(this.dataTemp).then(response => {
          this.tableData = response.data.data.systemData
          if (this.tableData.length == 0) {
            if (this.type != 'info') {
              this.tableData = [
                { attriNam: '2016', '全焦产率': '11', },
              ]
            }
          }
        })
      } else {
        queryInfoSys(this.addForm).then(response => {
          this.tableData = response.data.data.systemData
          if (this.tableData.length == 0) {
            if (this.type != 'info') {
              this.tableData = [
                { attriNam: '2016', '全焦产率': '11', },
              ]
            }
          }
        })
      }
    },
  },
  mounted () {
    this.$emit('update:formDataXT', this.addForm)
  }
}
</script>

<style scoped>
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-input {
  width: 190px;
}
/deep/ .is-in-pagination {
  width: 50px;
}
.el-dialog__footer {
  padding: 0px 20px 20px;
}
.activityId /deep/ .el-input {
  width: 121.5%;
}
</style>
