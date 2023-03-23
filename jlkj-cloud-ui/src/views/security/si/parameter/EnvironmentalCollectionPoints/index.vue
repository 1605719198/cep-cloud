<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-form-item label="" prop="sort">
              <el-select v-model="query.sort" placeholder="请选择位号" clearable filterable :popper-append-to-body="false">
                <el-option v-for="i in 32" :key="i" :label="i" :value="i"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="" prop="sort">
              <el-input v-model="query.deviceName" placeholder="名称" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="" prop="pollutantName">
              <el-select v-model="query.pollutantName" placeholder="污染物名称" :popper-append-to-body="false" clearable>
                <el-option label="颗粒物" value="颗粒物"/>
                <el-option label="氮氧化物" value="氮氧化物"/>
                <el-option label="二氧化硫" value="二氧化硫"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <div class="el-form-item__content" style="margin-left: 0px;">
              <el-button size="mini" icon="el-icon-search" type="primary"
                         @click="handleQuery">搜索
              </el-button>
              <el-button size="mini" icon="el-icon-refresh-left" type="default" @click="handleEmpty">重置</el-button>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="el-form-item__content" style="float: right">
              <el-button type="primary" size="mini" icon="el-icon-plus"
                           style="margin: 5px 0;" @click="handleOpenWindow('add')">新增
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="69vh" size="small" v-loading="table.loading" :data="tableData" stripe>
          <template v-for="(column,index) in columns">
            <el-table-column v-if="column.customStyle" :key="index"
                             :prop="column.prop" :label="column.label" :type="column.type" :width="column.width"
                             :min-width="column.minWidth" :sortable="column.sortable">
              <template slot-scope="scope">
                <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
              </template>
            </el-table-column>
            <el-table-column v-else :key="index"
                             :prop="column.prop" :label="column.label" :type="column.type"
                             :width="column.width" :min-width="column.minWidth" :sortable="column.sortable"
                             :formatter="column.formatter"/>
          </template>
          <el-table-column fixed="right" label="操作" width="180px">
            <template slot-scope="scope">
              <el-button v-hasPermi="['updateCokeOvenParameterStandard']"
                         size="mini" plain icon="el-icon-edit" type="text"
                         @click="handleOpenWindow('edit',scope.$index, scope.row)"> 修改
              </el-button>
              <el-button v-hasPermi="['deleteCokeOvenParameterStandard']"
                         size="mini" plain icon="el-icon-delete" type="text"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;right: 0;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         layout="total, sizes, prev, pager, next, jumper"
                         :current-page="page.current"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.size"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title"
                 :visible.sync="openDialog.open"
                 :width="openDialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template v-if="openDialog.type === 'add' || openDialog.type === 'edit'">
          <update :type="openDialog.type" :data="openDialog.data" @submitSave="submitSave"
                  @close="openDialog.open=false"/>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import {
    listEnvironmentalCollectionPoints,
    deleteEnvironmentalCollectionPoints
  } from "@/api/security/si/parameter/EnvironmentalCollectionPoints";
  import update from "./update";
  import {mapGetters} from "vuex";

  export default {
    components: {update},
    name: "EnvironmentalCollectionPoints",
    data() {
      return {
        openDialog: {open: false, type: '', title: '', width: '500px', data: {}},
        page: {size: 20, current: 1, total: 1, order: "sort", orderby: "asc"},
        query: {sort: '', deviceName: '', pollutantName: ''},
        table: {border: true, loading: true},
        columns: [
          // {label: '序号', type: 'index', width: '80px'},
          {label: '位号', prop: "sort", sortable: true, width: '90px'},
          {label: '名称', prop: "device_name", sortable: true, minWidth: '90px'},
          {label: '污染物名称', prop: "pollutant_name", sortable: true, minWidth: '90px'},
          {label: '采集点位', prop: "collection_point", sortable: true, minWidth: '90px'},
          {label: '排放限值', prop: "emission_limit", sortable: true, minWidth: '90px'},
          {label: '单位', prop: "unit", sortable: true, minWidth: '90px'},
        ],
        tableData: [],
      }
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      this.onLoad();
    },
    methods: {
      //载入数据
      onLoad() {
        this.table.loading = true;//加载状态
        listEnvironmentalCollectionPoints({...this.page, ...this.query}).then(res => {
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
          this.page.order = "sort";
          this.page.orderby = "asc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.onLoad();
      },
      //查询
      handleQuery() {
        this.onLoad();
      },
      // 清空
      handleEmpty() {
        this.query = {sort: '', deviceName: '', pollutantName: ''};
        this.onLoad();
      },
      // 打开窗口
      handleOpenWindow(type, index, row) {
        let title = '环保采集点位';
        this.openDialog.open = true;
        this.openDialog.type = type;
        this.openDialog.width = '600px';
        this.openDialog.data = row;
        if (type === 'add') {
          this.openDialog.title = '新增' + title;
          this.openDialog.data = {};
        } else if (type === 'edit') {
          this.openDialog.title = '编辑' + title;
        } else {
          this.openDialog.open = false;
          this.openDialog.data = {};
        }
      },
      // 删除
      handleDelete(index, row) {
        this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          deleteEnvironmentalCollectionPoints({id: row.id}).then(res => {
            this.onLoad();
            this.$message({type: 'success', message: res.data.msg});
          }, error => {
            window.console.log(error);
          });
        }).catch(() => {
          this.$message({type: 'info', message: '已取消删除'});
        });
      },
      //提供给子类调用的方法
      submitSave() {
        this.openDialog.open = false;
        this.openDialog.data = {};
        this.onLoad();
      },
    }
  }
</script>

<style scoped></style>


