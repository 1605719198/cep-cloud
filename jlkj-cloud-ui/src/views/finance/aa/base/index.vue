<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small"
             :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="节点代码" prop="leafId">
        <el-input
          disabled
          v-model="queryParams.leafId"
          placeholder="请输入节点代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点名称" prop="nodeName">
        <el-input
          disabled
          v-model="queryParams.nodeName"
          placeholder="请输入节点名称"
          clearable
          @keyup.enter.native="handleQuery"
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
          @click="submitForm"
          v-hasPermi="['aa:base:add']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
          v-hasPermi="['aa:base:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 添加或修改系统选单-基本设定对话框 -->

      <el-form ref="form" :model="form" style="width: 100%" :rules="rules" label-width="96px">
        <el-row :gutter="20">
          <el-col :span="6">
        <el-form-item label="项次一名称" prop="itema">
          <el-input v-model="form.itema" placeholder="请输入项次一名称" style="width: 220px"/>
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="项次二名称" prop="itemb">
          <el-input v-model="form.itemb" placeholder="请输入项次二名称" style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="项次三名称" prop="itemc">
          <el-input v-model="form.itemc" placeholder="请输入项次三名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="项次四名称" prop="itemd">
          <el-input v-model="form.itemd" placeholder="请输入项次四名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="项次五名称" prop="iteme">
          <el-input v-model="form.iteme" placeholder="请输入项次五名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料一名称" prop="dataa">
          <el-input v-model="form.dataa" placeholder="请输入资料一名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料二名称" prop="datab">
          <el-input v-model="form.datab" placeholder="请输入资料二名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料三名称" prop="datac">
          <el-input v-model="form.datac" placeholder="请输入资料三名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料四名称" prop="datad">
          <el-input v-model="form.datad" placeholder="请输入资料四名称" style="width: 220px"/>
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料五名称" prop="datae">
          <el-input v-model="form.datae" placeholder="请输入资料五名称" style="width: 220px"/>
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料六名称" prop="dataf">
          <el-input v-model="form.dataf" placeholder="请输入资料六名称"style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="6">
        <el-form-item label="资料七名称" prop="datag">
          <el-input v-model="form.datag" placeholder="请输入资料七名称"style="width: 220px" />
        </el-form-item>
          </el-col>
        </el-row>
      </el-form>
  </div>
</template>

<script>
import { listBase, getBase, delBase, addBase, updateBase } from "@/api/finance/aa/base";

export default {
  name: "Base",
  data() {
    return {
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
      // 系统选单-基本设定表格数据
      baseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        leafId: null,
        nodeName:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itema:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        itemb:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        itemc:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        itemd:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        iteme:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        dataa:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        datab:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        datac:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        datad:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        datae:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        dataf:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],
        datag:[
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}
        ],

      },
      companyid:null,
      leafId:null
    };
  },
  props: ['nodeNo','nodeName','companyId'],
  mounted() {
    this.$watch('nodeNo', function (newVal) {

      this.queryParams.leafId = newVal;
       this.leafId = newVal
      console.log(this.leafId);
      this.getList();
    })
    this.$watch('nodeName', function (newVal) {

      this.queryParams.nodeName = newVal;
      this.getList();
    })
    this.$watch('companyId', function (newVal) {

      this.queryParams.companyId = newVal;
      this.companyid = newVal;

    })
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询系统选单-基本设定列表 */
    getList() {
      this.loading = true;
      listBase(this.queryParams).then(response => {
        this.baseList = response.rows;
        if ( response.rows.length==1){
          this.form = response.rows[0];
        }else {
          this.reset();
        }

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
        companyId: null,
        itema: null,
        itemb: null,
        itemc: null,
        itemd: null,
        iteme: null,
        dataa: null,
        datab: null,
        datac: null,
        datad: null,
        datae: null,
        dataf: null,
        datag: null,
        createBy: null,
        createDate: null,
        updateBy: null,
        updateDate: null,
        createTime: null,
        updateTime: null,
        createName: null,
        updateName: null,
        leafId: null
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
      this.$emit('nodeNoAdd', 'third' );
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBase(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改系统选单-基本设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.companyId = this.companyid
          this.form.leafId = this.leafId
          console.log(this.leafId);
          console.log( this.form.leafId);
           if (this.form.id != null) {
            updateBase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBase(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      const leafId = this.leafId;
      this.$modal.confirm('是否确认删除系统选单-基本设定编号为"' + leafId + '"的数据项？').then(function() {
        return delBase(leafId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('aa/base/export', {
        ...this.queryParams
      }, `base_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
