<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

      <el-form-item label="厂商编码" prop="manufacturerId">
        <el-input v-model="queryParams.manufacturerId"
                  placeholder="请输入厂商编码"
                  clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="中文名称" prop="manufacturerChineseName">
        <el-input v-model="queryParams.manufacturerChineseName"
                  placeholder="请输入中文名称"
                  clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="统一编号" prop="taxNo">
        <el-input v-model="queryParams.taxNo"
                  placeholder="请输入统一编号"
                  clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   size="mini"
                   @click="handleQuery">搜索
        </el-button>
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
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table stripe v-loading="loading" :data="base1List" @selection-change="handleSelectionChange"
              :default-sort="{ prop: 'date', order: 'descending' }" height="67vh">
      <el-table-column type="selection"
                       width="55"
                       align="center"/>
      <el-table-column label="厂商编码"
                       sortable
                       align="center"
                       prop="manufacturerId"
                       key="manufacturerId"/>
      <el-table-column label="中文名称"
                       sortable
                       align="center"
                       prop="manufacturerChineseName"
                       key="manufacturerChineseName"
                       :show-overflow-tooltip='true'/>
      <el-table-column label="厂商简称"
                       sortable
                       align="center"
                       prop="manufacturerShortName"
                       key="manufacturerShortName"
                       :show-overflow-tooltip='true'/>
      <el-table-column label="统一编号"
                       sortable
                       align="center"
                       prop="taxNo"
                       key="taxNo"/>
      <el-table-column label="英文名称"
                       sortable
                       align="center"
                       prop="manufacturerEnglishName"
                       key="manufacturerEnglishName"/>
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button
            icon="el-icon-info"
            type="text"
            size="mini"
            @click="handleDetails(scope.row)">详情
          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
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

    <!-- 添加或修改参数配置对话框 -->
    <add-or-update  v-if="addOrUpdateVisible"
                   ref="addOrUpdate" @getLists="getListHandle"></add-or-update>
  </div>
</template>

<script>
import {queryAll,delManufacturerBasics} from "@/api/finance/gp/base1";
import AddOrUpdate from './manufacturertest'

export default {
  name: "Base1",
  components: {
    AddOrUpdate
  },
  data() {
    return {
      currentPage4: 1,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //选框选中
      manufacturerChineseName:'',
      //选框选中
      taxNo:'',
      // 非单个禁用
      single: true,
      //添加-修改熟悉
      addOrUpdateVisible: false,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 厂商基本资料表格数据
      base1List: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        manufacturerChineseName: undefined,
        manufacturerId: undefined,
        taxNo: undefined,
        compId: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
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
    /** 查询厂商基本资料列表 */
    getList() {
      queryAll(this.queryParams).then(response => {
        this.loading = false;
        if (response == null) {
          this.base1List = []
          this.total = 0;
          this.costAccount = true
          this.loading = false;
        } else {
          this.base1List = response.data.list;
          this.total = response.data.total;
          this.loading = false;
        }

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
        compId: undefined,
        manufacturerId: null,
        manufacturerChineseName: null,
        manufacturerShortName: null,
        taxNo: null,
        manufacturerEnglishName: null
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
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        manufacturerChineseName: undefined,
        manufacturerId: undefined,
        taxNo: undefined,
        compId: undefined
      }
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.manufacturerId)
      this.manufacturerChineseName = selection[0].manufacturerChineseName.toString();
      this.taxNo = selection[0].taxNo.toString();
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 添加按钮操作 */
    handleAdd () {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.AddPage()
      })

    },
/*    /!** 查看详情按钮操作 *!/
    handleDetails(row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row.manufacturerId, row.manufacturerChineseName,row.taxNo)
      })
    },*/
    /** 查看修改按钮操作 */
    handleUpdate(row) {
      const id = row.manufacturerId || this.ids;
      const name = row.manufacturerChineseName || this.manufacturerChineseName;
      const tax = row.taxNo || this.taxNo;
      this.addOrUpdateVisible = true
      this.$nextTick(() => {

        this.$refs.addOrUpdate.revise(id.toString(), name,tax)
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids;
      const name = row.manufacturerChineseName || this.manufacturerChineseName;
      this.$confirm('此操作将永久删除'+name+'数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法
        delManufacturerBasics(id).then(response => {

          if (response.msg === "操作成功") {
            this.getList();
          }
        })
      })
    },
    getListHandle () {
      this.getList()

    }
  }
};

</script>


<style scoped>
.link-type {
  color: #337ab7;
  cursor: pointer;
}

/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
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
