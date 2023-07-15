<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0;">
    <div class="avue-crud__search" style="border: 0;">
          <!-- 表单筛选 -->
          <el-form :model="query" ref="query" :inline="true">
            <!-- 操作按钮 -->
            <!-- <el-form-item>
              <el-button @click="handleQuery"
                         type="primary"
                         icon="el-icon-search"
                         size="medium">查 询</el-button>
              <el-button @click="handleReset"
                         icon="el-icon-refresh-left"
                         size="medium">重 置</el-button>
            </el-form-item> -->
          </el-form>
          <div style="text-align: left;margin:0 10px 20px 0;">
            <el-button type="primary" size="mini" icon="el-icon-plus" plain @click="handleAdd('add')">新增
            </el-button>
          </div>
    </div>
    <div>
      <el-table height="73vh" size="small" v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
        <el-table-column label="序号" type="index" width="55" align="center" />
        <el-table-column label="成本中心代号" sortable minWidth="150" align="left" prop="cost_center_name" />
        <el-table-column label="耗用产出类别代号" sortable minWidth="150" align="left" prop="in_out_type_id"
          :formatter="quenchingMethodFormatter" />
        <el-table-column label="卖方成本中心" sortable minWidth="150" align="left" prop="charge_cost_center_name" />
        <el-table-column label="上限" sortable minWidth="150" align="left" prop="up_limit" />
        <el-table-column label="下限" sortable minWidth="150" align="left" prop="down_limit" />
        <el-table-column fixed="right" label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="text"
                       icon="el-icon-edit"
              @click="handleEdit('edit', scope.$index, scope.row)">修改
            </el-button>
            <el-button size="mini"
                       type="text"
                       icon="el-icon-delete"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px;right: 0;padding: 0;" class="avue-crud__pagination">
        <pagination v-show="page.total > 0" background @size-change="handleSizeChange"
          @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper"
          :current-page="page.current" :page-sizes="[20, 50, 100, 200]" :page-size="page.size" :total="page.total">
        </pagination>
      </div>
    </div>
    <!-- 弹窗 -->
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title" :visible.sync="dialog.visible" :width="dialog.width" class="customDialogStyle"
        append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template>
          <Update v-if="dialog.type === 'add' || dialog.type === 'edit'" :type="dialog.type" :data="dialog.data"
            @submitSave="submitSave" @close="dialog.visible = false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getEnergyInformationConfigurationPage, deleteEnergyInformationConfiguration } from "@/api/energy/ee/parameter";
import Update from "./update"
import { mapGetters } from "vuex";

export default {
  components: {
    Update
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
        loading: false,
      },
      tableData: [],
      optionsInEngyType: [],
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  created() {
    this.getDicts("energy_in_out_type").then(response => {
      this.optionsInEngyType = response.data;
    })
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      getEnergyInformationConfigurationPage(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
      }, error => {
        this.table.loading = false;
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
    //查询
    handleQuery() {
      // console.log(this.query.issueDate)
      this.onLoad();
    },
    // 重置
    handleReset() {
      this.page.current = 1
      this.$refs["query"].resetFields();
      this.handleQuery()
    },
    //新增
    handleAdd(type) {
      this.dialog.type = type
      this.dialog.title = '新增能源信息配置'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //修改
    handleEdit(type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑能源信息配置'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        deleteEnergyInformationConfiguration({
          id: row.id,
          delete_user_id: this.$store.state.user.userInfo.userName,
          delete_user_name: this.$store.state.user.userInfo.nickName,
        }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: res.msg });
        }, error => {
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    //格式化熄焦方式
    quenchingMethodFormatter(row) {
      var stateCode = ''
      for (var i = 0; i < this.optionsInEngyType.length; i++) {
        if (this.optionsInEngyType[i].dictValue == row.in_out_type_id) {
          stateCode = this.optionsInEngyType[i].dictLabel
          break
        }
      }
      return stateCode
    },
    //提供给子类调用的方法
    submitSave() {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
  }
}
</script>

<style scoped lang="scss">
.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
</style>

