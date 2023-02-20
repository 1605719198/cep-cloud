<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="产线编号" prop="mgma1no">
        <el-input v-model="queryParams.mgma1no"
                  type="text"
                  clearable
                  placeholder="请输入产线编号" />
      </el-form-item>
      <el-form-item label="产线名称" prop="mgma1name">
        <el-input v-model="queryParams.mgma1name"
                  type="text"
                  clearable
                  placeholder="请输入产线名称" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" plain type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="deleteBatch">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" :default-sort="{ prop: 'date', order: 'descending' }">
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" label="序号" :index="indexMethod" align="center" />
      <el-table-column prop="mgma1no" label="产线编号" sortable align="center" />
      <el-table-column prop="mgma1name" label="产线名称" sortable align="center" />
      <el-table-column prop="mgma1type" label="产线性质" sortable align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_mgma1_type"
                    :value="scope.row.mgma1type" />
        </template>
      </el-table-column>
      <el-table-column prop="groupcode" label="停机原因群码" sortable align="center" />
      <el-table-column prop="mgma1grade" label="重要等级" sortable align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_mgma1_grade"
                    :value="scope.row.mgma1grade" />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            plain
            icon="el-icon-edit"
            type="text"
            @click="handleEdit(scope.row)">修改</el-button>
          <el-button
            size="mini"
            plain
            icon="el-icon-delete"
            type="text"
            @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.page"
      :limit.sync="queryParams.limit"
      @pagination="handleQuery"
    />

    <!-- 添加或修改参数配置对话框 -->
    <div v-if="open">
      <el-dialog :title="title"
                 class="customDialogStyle"
                 :visible.sync="open"
                 width="1000px"
                 append-to-body
                 @close="cancel"
                 :close-on-click-modal="false">
        <el-form :model="ruleForm"
                 :inline="true"
                 :rules="rules"
                 ref="ruleForm"
                 label-width="150px"
                 class="demo-form-inline">
          <el-form-item label="产线编号"
                        prop="mgma1no">
            <el-input style="width: 240px"
                      :disabled="isWrite"
                      v-model="ruleForm.mgma1no"
                      @blur="sendvalidateno"></el-input>
          </el-form-item>
          <el-form-item label="产线名称"
                        prop="mgma1name">
            <el-input v-model="ruleForm.mgma1name"
                      style="width: 240px"
                      @blur="sendvalidatename"></el-input>
          </el-form-item>
          <el-form-item label="产线性质"
                        prop="mgma1type">
            <el-select v-model="ruleForm.mgma1type"
                       placeholder="产线性质"
                       clearable
                       style="width: 240px">
              <el-option v-for="dict in dict.type.equipment_mgma1_type"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="停机原因群码"
                        prop="groupcode">
            <el-select v-model="ruleForm.groupcode"
                       style="width: 240px"
                       filterable
                       placeholder="请选择">
              <el-option v-for="item in options"
                         :key="item.groupcode"
                         :label="item.groupcode"
                         :value="item.groupcode">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="重要等级"
                        prop="mgma1grade">
            <el-select v-model="ruleForm.mgma1grade"
                       placeholder="重要等级"
                       clearable
                       style="width: 240px">
              <el-option v-for="dict in dict.type.equipment_mgma1_grade"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary"
                     @click="submitForm"
                     :loading="states">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { addInfo, checkname, checkno, delInfo, querygropcode, queryInfo, updateInfo, queryLineEdit } from "@/api/equip/MG/lineManagement";

export default {
  name: "lineregister",
  dicts: ["equipment_mgma1_type", "equipment_mgma1_grade"],
  components: {},
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      loading: true,
      //添加或修改弹出
      open: false,
      //是否能编辑产线编号
      isWrite: false,
      // 弹出层标题
      title: "新增产线资料",
      // table多选
      ids: [],
      //停机群码
      options: [],
      // 字典表格数据
      tableData: [],
      //提交状态
      states: false,
      //总数
      total: 0,
      //弹出表单
      ruleForm: {
        id: "",
        mgma1no: "",
        mgma1name: "",
        groupcode: "",
        mgma1type: "",
        mgma1grade: "",
      },
      // 查询参数
      queryParams: {
        //当前页
        page: 1,
        //每页记录数
        limit: 10,
        mgma1type: undefined,
        mgma1grade: undefined,
      },
      // 表单校验
      rules: {
        mgma1no: [
          { required: true, message: "请输入产线编号", trigger: "blur" },
          { max: 10, message: '产线编号最长为10 个字符', trigger: 'blur' }
        ],
        mgma1name: [
          { required: true, message: "请输入产线名称", trigger: "blur" },
          { max: 20, message: ' 长度最大20个字符', trigger: 'blur' }
        ],

        groupcode: [
          { required: true, message: "停机原因群码不能为空", trigger: "blur" },
        ],
        mgma1grade: [
          { required: true, message: "请选择重要等级", trigger: "change" },

        ],
        mgma1type: [
          { required: true, message: "请选择产线性质", trigger: "change" },
        ],
      },
    }
  },
  watch: {
    open (newVal) {
      if (newVal) {
        querygropcode().then((res) => {
          this.options = res.data;
        });
      }
    }
  },
  created () {
    this.handleQuery();
  },
  methods: {
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 新增按钮操作
    handleAdd () {
      this.isWrite = false
      this.open = true
      this.resetForm()
      this.title = "新增产线资料";
    },
    // 产线编号验证
    sendvalidateno () {
      checkno(this.ruleForm.mgma1no, this.ruleForm.id).then((res) => {
        if (res.code != 200) {
          this.ruleForm.mgma1no = null
        }
      }).catch(() =>{
        this.ruleForm.mgma1no = null
      });
    },
    // 产线名字验证
    sendvalidatename () {
      checkname(this.ruleForm.mgma1name, this.ruleForm.id).then((res) => {
        if (res.code != 200) {
          this.ruleForm.mgma1name = null
        }
      }).catch(()=> {
        this.ruleForm.mgma1name = null
      });
    },
    //新增/修改方法
    submitForm () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.states = true
          if (this.ruleForm.id) {
            updateInfo(this.ruleForm).then((res) => {
              this.states = false
              this.$modal.msgSuccess("修改成功");
              this.handleQuery();
              this.open = false;
            });
          } else {
            addInfo(this.ruleForm).then((res) => {
              this.states = false
              this.$modal.msgSuccess("添加成功");
              this.handleQuery();
              this.open = false;
            });
          }
        } else {
          this.$modal.msgWarning("请完善信息");
          this.states = false
          return false;
        }
      });
    },
    // 打开修改页面
    handleEdit (row) {
      this.ruleForm = JSON.parse(JSON.stringify(row));
      queryLineEdit(row.mgma1no).then((res) => {
        if (res.data == 1) {
          this.isWrite = true
        } else if (res.data == 2) {
          this.isWrite = false
        }
      })
      this.open = true;
      this.title = "修改产线资料";
    },
    /** 查询按钮列表 */
    handleQuery () {
      this.loading = true
      queryInfo(this.queryParams).then((res) => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.queryParams.limit = res.data.size;
        this.queryParams.page = res.data.current;
      });
      this.loading = false
    },
    // 导出
    handleExport () {
      console.log("导出")
    },
    //批量删除参数
    handleSelectionChange (selection) {
      this.ids = selection.map((item) => item.id);
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      this.$confirm("确认删除吗？删除后数据无法找回！", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        delInfo(row.id).then((res) => {
          this.$modal.msgSuccess("删除成功");
          this.handleQuery();
        });
      });
    },
    //批量删除操作
    deleteBatch () {
      if (!this.ids.length) {
        this.$modal.msgWarning("请选择要删除的数据");
        return;
      } else {
        this.$confirm('确认删除吗？删除后数据无法找回！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delInfo(this.ids).then((response) => {
            this.$modal.msgSuccess("删除成功");
            this.handleQuery();
          });
        })
      }
    },
    // 序号设置
    indexMethod (index) {
      index = (index + 1) + (this.queryParams.page - 1) * this.queryParams.limit
      return index
    },
    // 取消按钮
    cancel () {
      this.resetForm();
      this.open = false;
      this.title = "新增产线资料";
    },
    // 表单重置
    resetForm () {
      this.ruleForm = {};
    },
  }
}
</script>

<style scoped>
.avue-crud .el-date-editor.el-input {
  width: auto !important;
}
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
