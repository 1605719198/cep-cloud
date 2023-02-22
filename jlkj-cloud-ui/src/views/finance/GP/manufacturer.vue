<template>
  <div>
    <el-row>
      <el-col :span="24"
              style="padding:0 10px">
        <div class="plan_main">
          <div class="avue-crud el-card__body"
               style="width: 98%;border: 0">
            <!--           条件搜索 -->
            <div class="avue-crud__search"
                 style="border: 0">
              <el-form :model="queryParams"
                       ref="queryForm">
                <el-row :gutter="20">
                  <el-col :span="3">
                    <div class="el-form-item el-form-item--small">
                      <div class="el-form-item__content">
                        <el-input v-model="queryParams.manufacturerId"
                                  placeholder="请输入厂商编码"
                                  clearable
                                  @keyup.enter.native="handleQuery" />
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="3">
                    <div class="el-form-item el-form-item--small">
                      <div class="el-form-item__content">
                        <el-input v-model="queryParams.manufacturerChineseName"
                                  placeholder="请输入中文名称"
                                  clearable
                                  @keyup.enter.native="handleQuery" />
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="3">
                    <div class="el-form-item el-form-item--small">
                      <div class="el-form-item__content">
                        <el-input v-model="queryParams.taxNo"
                                  placeholder="请输入统一编号"
                                  clearable
                                  @keyup.enter.native="handleQuery" />
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="10">
                    <div class="el-form-item__content"
                         style="margin-left: 0px;">
                      <el-button v-hasPermi="['manufacturer_queryAll']"
                                 type="primary"
                                 size="mini"
                                 @click="handleQuery"
                                 icon="el-icon-search">搜索</el-button>
                      <el-button size="mini"
                                 @click="resetQuery"
                                 icon="el-icon-refresh-left">重置</el-button>
                    </div>
                  </el-col>
                </el-row>
              </el-form>
            </div>
            <!--            表单数据-->
            <div>
              <el-form>
                <el-table height="67vh"
                          stripe
                          v-loading="loading"
                          :data="base1List"
                          @selection-change="handleSelectionChange"
                          :header-cell-style="{background:'#FAFAFA'}"
                          :default-sort="{ prop: 'date', order: 'descending' }">
                  <el-table-column type="selection"
                                   width="55"
                                   align="center" />
                  <el-table-column label="厂商编码"
                                   sortable
                                   align="center"
                                   prop="manufacturerId"
                                   key="manufacturerId" />
                  <el-table-column label="中文名称"
                                   sortable
                                   align="center"
                                   prop="manufacturerChineseName"
                                   key="manufacturerChineseName"
                                   :show-overflow-tooltip='true' />
                  <el-table-column label="厂商简称"
                                   sortable
                                   align="center"
                                   prop="manufacturerShortName"
                                   key="manufacturerShortName"
                                   :show-overflow-tooltip='true' />
                  <el-table-column label="统一编号"
                                   sortable
                                   align="center"
                                   prop="taxNo"
                                   key="taxNo" />
                  <el-table-column label="英文名称"
                                   sortable
                                   align="center"
                                   prop="manufacturerEnglishName"
                                   key="manufacturerEnglishName" />
                  <el-table-column label="操作"
                                   align="center"
                                   class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                      <el-button v-hasPermi="['manufacturer_queryOne']"
                                 icon="el-icon-info"
                                 type="text"
                                 size="mini"
                                 @click="handleUpdate(scope.row)">详情</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <!--                分页-->
                <div style="margin-top: 10px;right: 0"
                     class="avue-crud__pagination">

                  <el-pagination background
                                 :total="total"
                                 :current-page="queryParams.pageNum"
                                 :page-sizes="[20, 50, 100, 200]"
                                 :page-size="queryParams.pageSize"
                                 layout="total, sizes, prev, pager, next, jumper"
                                 @size-change="handleSizeChange"
                                 @current-change="handleCurrentChange"
                                 style="float: right;">
                  </el-pagination>
                </div>
              </el-form>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 添加或修改参数配置对话框 -->
    <add-or-update v-if="addOrUpdateVisible"
                   ref="addOrUpdate"></add-or-update>
  </div>

</template>

<script>
import { queryAll } from "@/api/finance/GP/base1";
import AddOrUpdate from './manufacturertest'
export default {
  name: "Base1",
  components: {
    AddOrUpdate
  },
  data () {
    return {
      currentPage4: 1,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
      rules: {
      }
    };
  },
  created () {
    this.getList();
  },
  methods: {
    // 分页数据
    handleSizeChange (val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange (val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    /** 查询厂商基本资料列表 */
    getList () {
      queryAll(this.queryParams).then(response => {
        this.loading = false;
        if (response.data.data == null) {
          this.base1List = []
          this.total = 0;
          this.costAccount = true
          this.loading = false;
        } else {
          this.base1List = response.data.data.list;
          this.total = response.data.data.total;
          this.loading = false;
        }

      });
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
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
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
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
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.manufacturerId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 查看详情按钮操作 */
    handleUpdate (row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row.manufacturerId, row.manufacturerChineseName)
      })
    },
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
