<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-row :gutter="20">
        <el-col :span="3">
          <div class="el-form-item el-form-item--small">
            <div class="el-form-item__content">
              <el-date-picker v-model="query.start_time" type="date" placeholder="选择起始日期" value-format="yyyy-MM-dd " />
            </div>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="el-form-item el-form-item--small">
            <div class="el-form-item__content">
              <el-date-picker v-model="query.end_time" type="date" placeholder="选择结束日期" value-format="yyyy-MM-dd " />
            </div>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="el-form-item el-form-item--small">
            <div class="el-form-item__content">
              <el-select v-model="query.category" placeholder="类别" clearable>
                <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </div>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="el-form-item__content" style="margin-left: 0px;">
            <el-button v-hasPermi="['getCokeItemYieldList']" size="medium" type="primary" icon="el-icon-search"
              @click="handleQuery">搜 索</el-button>
            <el-button size="medium" type="default" icon="el-icon-refresh-left" @click="handleResetting">重 置</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-table height="66vh" size="small" :data="tableData" stripe  @sort-change="handleSort" style="width: 100%" v-loading="loading">
        <el-table-column prop="goods_name" label="类别" min-width="140px"></el-table-column>
        <el-table-column prop="complete_pound_num" label="产量(t)" min-width="140px">
          <template slot-scope="scope">
            {{ (scope.row.complete_pound_num / 1000).toFixed(3) }}
          </template>
        </el-table-column>
        <el-table-column prop="pound_date" label="时间" min-width="140px"></el-table-column>
      </el-table>
      <div style="margin-top: 10px;right: 0;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="page.current" :page-sizes="[20, 50, 100, 200]" :page-size="page.size"
          layout="total, sizes, prev, pager, next, jumper" :total="page.total">
        </el-pagination>
      </div>
    </div>
    <!-- 弹窗 -->
    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title" :visible.sync="openDialog.open" :width="openDialog.width"
        class="customDialogStyle" append-to-body :destroy-on-close="true" :close-on-click-modal="false"
        @close="openDialog.open = false">
        <CokeItemYieldEditor :isAdd="openDialog.isAdd" :dataBundle="openDialog.data" @close="handleClose" />
      </el-dialog>
    </div>
  </div>
</template>

<script>
import CokeItemYieldEditor from "@/views/product/oi/actual/coke/CokeItemYieldEditor";
import { deleteCokeItemYieldList, getCokeItemYieldList , getCokeItemYieldList2 } from "@/api/production/oi/actual/coke/CokeItemYieldApi";
import { listMaterialsBoxJ } from "@/api/material/mr/parameter/materialCode";

export default {
  name: "CokeItemYield", //焦炭分项产量
  components: {
    CokeItemYieldEditor
  },
  data() {
    return {
      categoryOptions: [],
      query: {
        start_time: '',
        end_time: '',
        category: '',
      },
      tableData: [],
      page: {
        current: 1,
        size: 20,
        total: 10,
        order: "pound_date",
        orderby: "desc"
      },
      openDialog: {
        open: false,
        title: "编辑",
        width: "500px",
        isAdd: false,
        data: {}
      },
      loading: false
    }
  },
  computed: {
  },
  mounted() {
    this.handleQuery();
    this.getMaterialsOptions();
  },
  methods: {
    handleQuery() {
      let params = {
        start_time: this.query.start_time,
        end_time: this.query.end_time,
        materials_code: this.query.category,
        current: this.page.current,
        size: this.page.size,
        order: this.page.order,
        orderby: this.page.orderby,
        // order: "pound_date",
        // orderby: "desc"
      }
      this.loading = true;
      getCokeItemYieldList2(params).then(res => {
        this.loading = false;
        if (res.code === 200) {
          this.tableData = res.data.records;
          this.page.total = res.data.total;
        }
      }, err => {
        this.loading = false;
        window.console.log(err);
      });

    },
    // 排序
    handleSort (column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "pound_date";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.handleQuery();
    },
    handleResetting() {
      this.query.category = "";
      this.query.end_time = "";
      this.query.start_time = "";
      this.handleQuery();
    },
    handleAddNew() {
      this.openDialog.title = "新增分项产量"
      this.openDialog.data = {};
      this.openDialog.isAdd = true;
      this.openDialog.open = true;

    },
    handleEdit(row) {
      this.openDialog.title = "修改分项产量";
      this.openDialog.isAdd = false
      this.openDialog.data = { ...row };
      this.openDialog.open = true;
    },
    handleDelete(index, rows) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCokeItemYieldList({
          id: rows[index].id,
          delete_user_id: this.$store.state.user.userInfo.userName,
          delete_user_name: this.$store.state.user.userInfo.nickName,
        }).then(res => {
          if (res.code === 200) {
            rows.splice(index, 1);
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          } else {
            this.$message.error("删除失败");
          }
        }, err => {
          window.console.log(err);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSizeChange(val) {
      this.page.size = val;
      this.handleQuery();
    },
    handleCurrentChange(val) {
      this.page.current = val;
      this.handleQuery();
    },

    //获取物料代码下拉列表
    getMaterialsOptions() {
      const param = {}
      listMaterialsBoxJ(param).then((res) => {
        console.log(res)
        let options = []
        res.data.forEach(item => {
          let i = {
            value: item.id,
            label: item.materials_name
          }
          options.push(i)
        });
        this.categoryOptions = options
      })
    },
    handleClose() {
      this.openDialog.open = false;
      this.handleQuery();
    }
  }
}
</script>

<style scoped>

</style>
