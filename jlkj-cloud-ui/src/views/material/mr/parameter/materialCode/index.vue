<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search " style="border: 0">
        <el-form :model="query" ref="query" :inline="true">
            <el-form-item prop="materials_code" label="物料代码">
              <el-input v-model="query.materials_code" type="text" clearable placeholder="物料代码"/>
            </el-form-item>
            <el-form-item prop="mtrlType" label="物料大类">
              <el-select v-model="query.mtrlType" clearable placeholder="物料大类">
                <el-option label="焦炭" value="01"/>
                <el-option label="煤" value="00"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleQuery" type="primary" icon="el-icon-search" size="mini">搜 索</el-button>
              <el-button @click="handleReset" icon="el-icon-refresh-left" size="mini">重 置</el-button>
            </el-form-item>
        </el-form>
      <!-- <el-row style="margin-right: 20px; margin-bottom: 25px">
        <el-button type="primary"
                   size="medium"
                   icon="el-icon-plus"
                   @click="handleAdd('add')">新增
        </el-button>
      </el-row> -->
    </div>
    <div>
      <el-table height="72vh" size="small" v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
        <el-table-column type="index" label="序号" width="80" align="center"/>
        <el-table-column label="物料类别" sortable minWidth="150" align="left" prop="category_name"/>
        <el-table-column label="物料名称" sortable minWidth="150" align="left" prop="materials_name"/>
        <el-table-column label="物料大类" sortable minWidth="150" align="left" prop="mtrlType">
          <template slot-scope="scope">
            <span v-if="scope.row.mtrlType ==='00'">煤</span>
            <span v-else>焦</span>
          </template>
        </el-table-column>
        <el-table-column label="物料代码" sortable minWidth="150" align="left" prop="materials_code"/>
        <el-table-column label="计量单位" sortable minWidth="150" align="left" prop="measurement_unit"/>
      </el-table>
      <div style="margin-top: 10px;float: right;padding: 0 0 20px 20px;" class="avue-crud__pagination">
        <el-pagination v-show="page.total > 0"
                       background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       layout="total, sizes, prev, pager, next, jumper"
                       :current-page="page.current"
                       :page-sizes="[20, 50, 100, 200]"
                       :page-size="page.size"
                       :total="page.total">
        </el-pagination>
      </div>
    </div>
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title"
                 :visible.sync="dialog.visible"
                 :width="dialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template>
          <Add v-if="dialog.type === 'add'"
               @submitSave="submitSave"
               @close="dialog.visible=false"/>
          <Edit v-if=" dialog.type === 'edit'"
                :data="dialog.data"
                @submitSave="submitSave"
                @close="dialog.visible=false"/>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import {listMaterials, delMaterialsCode} from "@/api/material/mr/parameter/materialCode";
  import Add from "./add";
  import Edit from "./edit"

  export default {
    components: {
      Add,
      Edit
    },
    data() {
      return {
        dialog: {
          visible: false,
          type: '',
          title: '',
          width: '40%',
          data: {},
        },
        page: {
          size: 20,
          current: 1,
          total: 1,
          order: "create_time",
          orderby: "desc",
        },
        query: {},
        table: {
          border: true,
          loading: true,
        },
        tableData: [],
      }
    },
    created() {
      this.onLoad();
    },
    methods: {
      //载入数据
      onLoad() {
        this.table.loading = true;//加载状态
        listMaterials(this.page, this.query).then(res => {
          this.table.loading = false;
          let data = res.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.records;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });

      },
      // 分页-每页多少条
      handleSizeChange(val) {
        this.page.size = val;
        this.onLoad();
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.page.current = val;
        this.onLoad();
      },
      // 排序
      handleSort(column) {
        if (column.order === null) {
          //默认
          this.page.order = "create_time";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.onLoad();
      },

      //新增
      handleAdd(type) {
        this.dialog.type = type
        this.dialog.title = '新增物料代码'
        this.dialog.visible = true;
      },
      //修改
      handleEdit(type, index, row) {
        this.dialog.type = type
        this.dialog.title = '编辑物料代码'
        this.dialog.data = row;
        this.dialog.visible = true;
      },
      // 删除
      handleDelete(index, row) {
        this.$confirm('是否确认删除数据项?', '警告', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          delMaterialsCode({id: row.id}).then(res => {
            this.onLoad();
            this.$message({type: 'success', message: res.msg});
          }, error => {
            window.console.log(error);
          });
        }).catch(() => {
          this.$message({type: 'info', message: '已取消删除'});
        });
      },
      //提供给子类调用的方法
      submitSave() {
        this.dialog.visible = false;
        this.dialog.data = {};
        this.onLoad();
      },
      //查询
      handleQuery() {
        this.page.current = 1;
        this.onLoad();
      },
      // 重置
      handleReset() {
        this.page.current = 1;
        this.$refs["query"].resetFields();
        this.handleQuery()
      },
    }
  }
</script>

<style scoped>
</style>

