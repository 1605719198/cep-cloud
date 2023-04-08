<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
        <el-form :model="query" ref="query" :inline="true">
            <el-form-item prop="materialsCode" label="物料代码">
              <el-input v-model="query.materialsCode" type="text" clearable placeholder="物料代码"/>
            </el-form-item>
            <el-form-item prop="materialsName" label="物料名称">
              <el-input v-model="query.materialsName" type="text" clearable placeholder="物料名称"/>
            </el-form-item>
            <el-form-item prop="vendorName" label="供应商名称">
              <el-input v-model="query.vendorName" type="text" clearable placeholder="供应商名称"/>
            </el-form-item>
            <el-form-item prop="sendVendorName" label="发货地">
              <el-input v-model="query.sendVendorName" type="text" clearable placeholder="发货地"/>
            </el-form-item>
            <el-form-item>
              <el-button @click="handleQuery" type="primary" icon="el-icon-search" size="mini">搜 索</el-button>
              <el-button @click="handleReset" icon="el-icon-refresh-left" size="mini">重 置</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div>
      <el-table height="72vh" size="small" v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
        <el-table-column label="物料类别" sortable minWidth="100" prop="category_name"/>
        <el-table-column label="物料代码" sortable minWidth="90" prop="materialsCode"/>
        <el-table-column label="物料名称" sortable minWidth="150" prop="materialsName"/>
        <el-table-column label="物料小煤种代码" sortable minWidth="150" prop="materials_small_code"/>
        <el-table-column label="物料小煤种简称" sortable minWidth="150" prop="materials_small_name"/>
        <el-table-column label="供应商名称" sortable minWidth="200" prop="vendorName"/>
        <el-table-column label="发货地名称" sortable minWidth="200" prop="sendVendorName"/>
        <el-table-column label="计量单位" minWidth="75" prop="measurement_unit"/>
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button size="mini" plain icon="el-icon-edit" type="primary"
                       @click="handleEdit('edit',scope.$index, scope.row)">编辑
            </el-button>
          </template>
        </el-table-column>
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
      <el-dialog :title="dialog.title" :visible.sync="dialog.visible" :width="dialog.width"
                 class="customDialogStyle" append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template>
          <Edit v-if=" dialog.type === 'edit'" :data="dialog.data" @submitSave="submitSave"
                @close="dialog.visible=false"/>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import {listSmallMaterials} from "@/api/material/mr/parameter/materialCode";
  import Edit from "./edit"

  export default {
    components: {Edit},
    data() {
      return {
        dialog: {visible: false, type: '', title: '', width: '40%', data: {},},
        page: {size: 20, current: 1, total: 1, order: "create_time", orderby: "desc",},
        query: {
          materialsCode: '',
          materialsName: '',
          vendorName: '',
          sendVendorName: ''
        },
        table: {border: true, loading: true,},
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
        listSmallMaterials(this.page, this.query).then(res => {
          this.table.loading = false;
          // console.log(res);
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
        // console.log(column, "column")
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
      //修改
      handleEdit(type, index, row) {
        this.dialog.type = type;
        this.dialog.title = '编辑物料小煤种';
        this.dialog.data = row;
        this.dialog.visible = true;
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

