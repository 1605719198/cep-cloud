<template>
  <div class="app-container">
    <el-form :inline="true" label-width="68px" v-show="showSearch">
      <el-form-item label="产线代码">
        <el-select v-model="queryParams.millIdCodeStart" clearable placeholder="请选择工厂产线代码"
                   style="width: 215px!important;">
          <el-option v-for="item in optionsMillIdCodeStart" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        ~
        <el-select v-model="queryParams.millIdCodeEnd" clearable placeholder="请选择工厂产线代码"
                   style="width: 215px!important;">
          <el-option v-for="item in optionsMillIdCodeEnd" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['mill:cost:query']" type="primary" size="mini" icon="el-icon-search"
                   @click="handleQuery" style="margin-left: 20px">搜 索
        </el-button>
        <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleEmpty">重 置
        </el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['mill:cost:add']" type="primary" plain size="mini" icon="el-icon-plus"
                   @click="handleAdd">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['mill:cost:delete']" type="danger" size="mini" icon="el-icon-delete"
                   :disabled="openIsDisabled" @click="handleDelete">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table size="small" height="67vh" :data="tableData" stripe style="margin: 0 0px 0 10px;width: auto;"
              @selection-change="handleSelectionChange" :default-sort="{prop: 'createTime', order: 'descending'}">
      <el-table-column type="selection" width="55" align="center"/>
      <template v-for="column in columns">
        <el-table-column :key="column.prop" :prop="column.prop" :label="column.label"
                         :sortable="column.sortable" :width="column.width" :min-width="column.minWidth"/>
      </template>
      <el-table-column fixed="right" label="操作" width="180px">
        <template v-slot="scope">
          <el-button v-hasPermi="['mill:cost:delete']" size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 添加或修改产线与成本中心资料 -->
    <div v-if="addBox">
      <el-dialog :title="(editBox?'编辑':'新建')+'产线与成本中心资料'"
                 :visible.sync="addBox" width="500px" class="customDialogStyle" append-to-body
                 :destroy-on-close="true" :close-on-click-modal="false">
        <productionLineAdd :dataEdit="dataEdit" @submitAdd="submitAdd" @close="addBox=false,editBox=false"/>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import productionLineAdd from "./productionLineAdd";
import {addInfo, delInfo, queryInfo, querySelectInfo} from "@/api/energy/ee/productionLineCostCenter";

export default {
  name: "productionLineCostCenter",
  components: {
    productionLineAdd
  },
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      addBox: false,
      editBox: false,
      dataEdit: {},
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        millIdCodeStart: undefined,
        millIdCodeEnd: undefined,
      },
      optionsMillIdCodeStart: [],
      optionsMillIdCodeEnd: [],
      table: {
        border: true
      },
      columns: [
        {label: '产线代码', prop: "millIdCode", sortable: true, minWidth: '100px'},
        {label: '产线名称', prop: "millIdName", sortable: true, minWidth: '100px'},
        {label: '成本中心', prop: "costCenter", sortable: true, minWidth: '100px'},
        {label: '建立人员', prop: "createBy", sortable: true, minWidth: '100px'},
        {label: '建立日期', prop: "createTime", sortable: true, minWidth: '100px'}
      ],
      tableData: [],
      openIsDisabled: ''
    }
  },
  created() {
    this.getList(),
      this.getSelectsZh()
  },
  methods: {
    //查询
    handleQuery() {
      queryInfo(this.queryParams).then(response => {
        this.tableData = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },
    // 清空
    handleEmpty() {
      this.queryParams = {
        millIdCodeStart: "",
        millIdCodeEnd: "",
      },
        this.getList();
    },
    // 新增
    handleAdd() {
      this.dataEdit = {};
      this.addBox = true;
      this.editBox = false;
    },
    // 删除
    handleDelete(index, row) {
      //单条或多条 id
      let ids = null;
      if (typeof (row) == 'undefined') {
        ids = this.ids;
      } else {
        ids = row.id;
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //调用删除的方法
        delInfo(ids).then(response => {
          this.$message({
            type: 'success',
            message: response.msg
          });
          this.getList();
        })
      }).catch(() => {
        this.$message({type: 'info', message: '已取消删除'});
      });
    },
    //获取数据刷新页面
    getList() {
      queryInfo(this.query).then(response => {
        this.tableData = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },
    getSelectsZh() {
      querySelectInfo(this.query).then(response => {
        this.optionsMillIdCodeStart = response.data;
        this.optionsMillIdCodeEnd = response.data;
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length

      if (this.ids.length != 0) {
        this.openIsDisabled = false
      } else {
        this.openIsDisabled = true
      }
    },
    //提供给子类调用父类的方法
    submitAdd(data) {
      this.addBox = false;
      this.editBox = false;
      addInfo(data).then(response => {
        if (response.code == 0) {
          this.$message({
            type: 'success',
            message: response.msg
          });
        }
        this.getList();
      });
    },
  }
}
</script>

