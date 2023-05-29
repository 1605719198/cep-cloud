<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="轮班方式" prop="shiftmodeId">
        <el-select v-model="queryParams.shiftmodeId" placeholder="请选择轮班方式" clearable>
          <el-option
            v-for="dict in modeList"
            :key="dict.id"
            :label="dict.turnTypeName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary"
                   plain
                   icon="el-icon-plus"
                   size="mini"
                   @click="handleAdd"
                   v-hasPermi="['human:shiftClass:add']"
        >新增</el-button>
      </el-form-item>
    </el-form>

<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['human:shiftClass:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

    <el-table v-loading="loading" :data="shiftClassList" @selection-change="handleSelectionChange" height="60vh">
      <el-table-column label="班别编码" align="center" prop="classMode" />
      <el-table-column label="班别名称" align="center" prop="className" />
      <el-table-column label="说明" align="center" prop="description" />
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
            v-hasPermi="['human:shiftClass:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:shiftClass:remove']"
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

    <!-- 添加或修改班别数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="轮班方式" prop="shiftmodeId">
              <el-select v-model="form.shiftmodeId" placeholder="请选择轮班方式">
                <el-option
                  v-for="dict in modeList"
                  :key="dict.id"
                  :label="dict.turnTypeName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="班别编码" prop="classMode">
              <el-input v-model="form.classMode" placeholder="请输入班别编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班别名称" prop="className">
              <el-input v-model="form.className" placeholder="请输入班别名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="说明" prop="description">
              <el-input v-model="form.description" placeholder="请输入说明" />
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{form.creatorId}}-{{form.creator}}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { listShiftClass, getShiftClass, delShiftClass, addShiftClass, updateShiftClass } from "@/api/human/hd/shiftClass";
import { getDateTime } from "@/api/human/hd/ahumanUtils"
export default {
  name: "ShiftClass",
  dicts: ['sys_yes_no', 'sys_normal_disable'],
  data() {
    return {
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      //轮班方式列表数据
      modeList:[],
      // 遮罩层
      loading: true,
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
      // 班别数据表格数据
      shiftClassList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        shiftmodeId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classMode:[
          { required: true, message: "班别编码不能为空", trigger: "blur" }
        ],
        className:[
          { required: true, message: "班别名称不能为空", trigger: "blur" }
        ],
        description: [
          { required: true, message: "说明不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    //页面初始化
    init(modeData) {
      this.modeList = modeData.modeList;
      this.queryParams.compId = modeData.compId;
      this.getList();
    },
    //设置表单值
    setForm(e){
      this.form.creator = this.nickName;
      this.form.creatorId = this.userEmpId;
      this.form.createDate = getDateTime(1);
      this.form.compId = this.queryParams.compId;
      if(e==0){
        this.form.status = '0';
        this.form.shiftmodeId = this.queryParams.shiftmodeId;
      }
    },
    /** 查询班别数据列表 */
    getList() {
      this.loading = true;
      listShiftClass(this.queryParams).then(response => {
        this.shiftClassList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        shiftmodeId: null,
        classMode: null,
        className: null,
        description: null,
        status: null,
        isUsed: null,
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
      this.setForm(0);
      this.open = true;
      this.title = "添加班别数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShiftClass(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改班别数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.setForm(1)
            updateShiftClass(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShiftClass(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除班别数据编号为"' + ids + '"的数据项？').then(function() {
        return delShiftClass(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/shiftClass/export', {
        ...this.queryParams
      }, `shiftClass_${new Date().getTime()}.xlsx`)
    },
    /**改变公司别 */
    changeCompId(val,list){
      this.queryParams.compId = val;
      this.queryParams.shiftmodeId =null;
      this.modeList = list;
      this.getList();
    }
  }
};
</script>
