<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资料名称" prop="name">
        <el-input maxlength="200"
                  v-model="queryParams.name"
                  placeholder="请输入资料名称"
        />
      </el-form-item>
      <el-form-item label="资料编码" prop="code">
        <el-input maxlength="10"
                  v-model="queryParams.code"
                  placeholder="请输入资料编码"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['human:attendenceBasis:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attendenceBasisList" @selection-change="handleSelectionChange" height="67vh" row-key="id" :default-expand-all="isExpandAll" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="资料名称" align="center" prop="name" />
      <el-table-column label="资料编码" align="center" prop="code" />
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:attendenceBasis:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handlechild(scope.row)"
          >添加下级</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:attendenceBasis:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 修改员工出勤基本资料维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="资料编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入资料编码" maxlength="10" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资料名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入资料名称" maxlength="200" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序序号" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="请输入排序序号" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{form.creator}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
             {{form.createDate}}
            </el-form-item>
          </el-col>
        </el-row>

<!--        <el-form-item label="是否显示编码" prop="isShowno">-->
<!--          <el-input v-model="form.isShowno" placeholder="请输入是否显示编码" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="输入人id" prop="creatorId">-->
<!--          <el-input v-model="form.creatorId" placeholder="请输入输入人id" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加员工出勤基本资料维护对话框 -->
    <el-dialog :title="title" :visible.sync="opencreate" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="资料编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入资料编码" />
        </el-form-item>
        <el-form-item label="资料名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入资料名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAttendenceBasis, getAttendenceBasis, delAttendenceBasis, addAttendenceBasis, updateAttendenceBasis } from "@/api/human/hd/attendenceBasis";
import { getAvatorByUserName } from "@/api/system/user";
import { getDateTime } from '@/api/human/hd/ahumanutils'
export default {
  name: "AttendenceBasis",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      //登录人姓名
      nickName:null,
      //登录人公司
      logincompId:null,
      //出勤树数据
      attendenceOptions:[],
      // 遮罩层
      loading: true,
      //是否展开出勤树
      isExpandAll: true,
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
      // 员工出勤基本资料维护表格数据
      attendenceBasisList: [],
      // 弹出层标题
      title: "",
      // 是否显示修改弹出层
      open: false,
      //是否显示新增弹出层
      opencreate: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name:null,
        code:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          { required: true, message: "资料编码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "资料名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getName();
    this.getList();
  },
  methods: {
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName
        this.logincompId=response.data.compId
      })
    },
    /** 查询员工出勤基本资料维护列表 */
    getList() {
      this.loading = true;
      listAttendenceBasis(this.queryParams).then(response => {
        this.attendenceBasisList = response.rows;
        this.attendenceBasisList = this.handleTree(response.rows, "id","parentid");
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.opencreate = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        status: '0',
        compId: null,
        isShowno: null,
        isChecked: null,
        orderNum: null,
        parentid: null,
        parents: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 添加下级操作 */
    handlechild(row){
      this.reset();
      this.form.parentid = row.id
      this.form.creator = this.nickName;
      this.form.creatorId = this.$store.state.user.name;
      this.form.createDate = getDateTime(1)
      this.open = true;
      this.title = "添加下级窗口";
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.creator = this.nickName;
      this.form.creatorId = this.$store.state.user.name;
      this.form.createDate = getDateTime(1)
      this.opencreate = true;
      this.title = "新增窗口";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAttendenceBasis(id).then(response => {
        this.form = response.data;
        this.form.creator = this.nickName;
        this.form.creatorId = this.$store.state.user.name;
        this.form.createDate = getDateTime(1)
        this.open = true;
        this.title = "修改窗口";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAttendenceBasis(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.opencreate = false;
              this.getList();
            });
          } else {
            addAttendenceBasis(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.opencreate = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除员工出勤基本资料维护编号为"' + ids + '"的数据项？').then(function() {
        return delAttendenceBasis(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/attendenceBasis/export', {
        ...this.queryParams
      }, `attendenceBasis_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.maxWidth {
  width: 100%;
}
</style>
