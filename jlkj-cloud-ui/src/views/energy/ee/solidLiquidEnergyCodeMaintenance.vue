<template>
  <div class="app-container">
    <el-form style="text-align: left;":inline="true" label-width="68px">
      <el-form-item label="能源代码">
        <el-select v-model="query.engyIdStart"
                   clearable
                   :popper-append-to-body="false"
                   placeholder="请选择能源代码"
                   style="width: 215px;">
          <el-option v-for="item in optionsEngyIdStart"
                     :key="item.value"
                     :label="item.value"
                     :value="item.value">
          </el-option>
        </el-select>
        ~
        <el-select v-model="query.engyIdEnd"
                   clearable
                   :popper-append-to-body="false"
                   placeholder="请选择能源代码"
                   style="width: 215px;">
          <el-option v-for="item in optionsEngyIdEnd"
                     :key="item.value"
                     :label="item.value"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['code_querySolid']" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleEmpty">重置</el-button>
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
          v-hasPermi="['code_addSolid']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="openIsDisabled"
          @click="handleDelete"
          v-hasPermi="['code_deleteSolid']"
        >删除</el-button>
      </el-col>
      <right-toolbar  @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table stripe
              :data="tableData"
              tooltip-effect="dark"
              @selection-change="handleSelectionChange"
              style="margin: 0 13px 0 5px;width: auto;">
      <!--                <el-table height="68.1vh"-->
      <!--                          size="small"-->
      <!--                          :data="tableData"-->
      <!--                          stripe-->
      <!--                          @selection-change="handleSelectionChange"-->
      <!--                          :default-sort="{prop: 'createTime', order: 'descending'}">-->
      <el-table-column type="selection"
                       width="55"
                       align="center" />
      <el-table-column label="能源缩写"
                       align="center"
                       prop="engyAc" />
      <el-table-column label="能源代码"
                       align="center"
                       prop="engyId" />
      <el-table-column label="能源名称"
                       align="center"
                       prop="engyName" />
      <el-table-column label="计量单位"
                       align="center"
                       prop="engyUnit" />
      <el-table-column label="热值系数"
                       align="center"
                       prop="calValue" />
      <el-table-column label="热值系数单位"
                       align="center"
                       prop="calUnit" />
      <el-table-column label="来源方式" align="center" prop="srcType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_src_type" :value="scope.row.srcType" />
        </template>
      </el-table-column>
      <el-table-column label="能源种类" align="center" prop="engyType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_solid_type" :value="scope.row.engyType" />
        </template>
      </el-table-column>
      <el-table-column label="抛帐系统" align="center" prop="acctSys" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_acct_sys" :value="scope.row.acctSys" />
        </template>
      </el-table-column>
      <el-table-column label="建立人员"
                       align="center"
                       prop="createEmpNo" />
      <el-table-column label="建立日期"
                       align="center"
                       width="160"
                       prop="createTime" />
      <el-table-column align="center"
                       label="操作"
                       width="180px">
        <template slot-scope="scope">
          <el-button v-hasPermi="['code_updateSolid']"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleEdit(scope.$index, scope.row)">修改
          </el-button>
          <el-button v-hasPermi="['code_deleteSolid']"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>



    <pagination background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="page.currentPage"
                :page-sizes="[20, 50, 100, 200]"
                :page-size="page.pageSize"
                :layout="page.layout"
                :total="page.total">
    </pagination>
    <div v-if="addBox">
      <el-dialog :title="(editBox?'编辑':'新建')+'固液体能源代码'"
                 :visible.sync="addBox"
                 width="500px"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <solidLiquidEnergyAdd :dataEdit="dataEdit"
                              :addBox="addBox"
                              :editBox="editBox"
                              @submitAdd="submitAdd"
                              @close="addBox=false,editBox=false" />
      </el-dialog>
    </div>

  </div>
</template>
<script>

import solidLiquidEnergyAdd from "./solidLiquidEnergyAdd";
import { addInfo, delInfo, queryInfo, updateInfo, queryEngyIds } from "@/api/energy/ee/solidLiquidEnergyCodeMaintenance";

export default {
  components: {
    solidLiquidEnergyAdd
  },
  name: "solidLiquidEnergyCodeMaintenance",
  dicts: ['engy_engy_type','engy_acct_sys','engy_src_type','engy_solid_type'],
  data () {
    return {
      addBox: false,
      editBox: false,
      delBox: false,
      dataEdit: {},
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        engyIdStart: undefined,
        engyIdEnd: undefined,
        pageSize: 20,
        pageNum: 1,
      },
      table: {
        border: true
      },
      tableData: [],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      openIsDisabled: "",
    }
  },
  created () {
    this.getList();
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data;
      this.optionsEngyIdEnd = response.data;
      this.loading = false
    })
  },
  methods: {
    //查询
    handleQuery () {
      let query = this.query
      queryInfo(query).then(response => {
        this.tableData = response.data.list
        this.page.total = response.data.total
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        engyIdStart: undefined,
        engyIdEnd: undefined,
        pageSize: 20,
        pageNum: 1,
      },
        this.getList();
    },
    // 新增
    handleAdd () {
      this.dataEdit = {};
      this.addBox = true;
      this.editBox = false;
    },
    // 修改
    handleEdit (index, row) {
      this.dataEdit = row;//传递子组件
      this.editBox = true;
      this.addBox = true;
    },
    // 删除
    handleDelete (index, row) {
      //单条或多条 id
      let ids = null;
      if (typeof (row) == 'undefined') {
        ids = this.ids;
      } else {
        ids = row.id;
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        delInfo(ids).then(response => {
          this.$modal.msgSuccess(response.msg)
          this.getList();
        })
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
      queryInfo(this.query).then(response => {
        if (response.data != null) {
          this.tableData = response.data.list
          this.page.total = response.data.total
        } else {
          this.tableData = [];
          this.page.total = 0;
        }
      })
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
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
    submitAdd (data) {
      this.addBox = false;
      this.editBox = false;
      if (data.id != null) {
        updateInfo(data).then(response => {
          this.$modal.msgSuccess(response.msg)
          this.getList();
        });
      } else {
        addInfo(data).then(response => {
          if (response.code == '0') {
            this.$modal.msgSuccess(response.msg)
          }
          this.getList();
        });
      }
    },
  }
}
</script>

<style scoped>
.energy_main {
  height: 86vh;
  background-color: #fff;
}

/*下拉框最后一个显示不完全*/
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/*页面大小修改时新增删除串行*/
::v-deep .el-col {
  white-space: nowrap;
}
</style>
