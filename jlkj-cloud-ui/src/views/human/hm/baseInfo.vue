<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body " style="width: 98%;border: 0;">
        <el-row :gutter="20">
          <!-- 左侧选单配置树 -->
          <el-col :span="4" class="left_tree">
            <div class="head-container" style="height: 75vh;width: 100%;">
              <el-scrollbar style="height: 100%;">
                <el-tree ref="tree"
                         class="filter-tree"
                         node-key="uuid"
                         :props="defaultProps"
                         :data="menuData"
                         :expand-on-click-node="false"
                         :default-expanded-keys="defaultShowNodes"
                         @node-click="handleNodeClick">
                </el-tree>
              </el-scrollbar>
            </div>
          </el-col>
          <!-- 右侧列表 -->
          <el-col :span="20">
            <div>
              <div class="avue-crud__search" style="border: 0">
                <el-row>
                  <el-col :span="20">
                    <el-form :inline="true">
                      <!-- 操作按钮 -->
                      <el-form-item>
                        <el-button @click="handleAdd('add')" plain type="primary" icon="el-icon-plus" size="mini">新增
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-table height="70vh" size="small" v-loading="table.loading" :data="tableData" stripe>
                  <el-table-column label="资料代号" minWidth="150" align="center" prop="dicNo"/>
                  <el-table-column label="资料名称" minWidth="150" align="center" prop="dicName"/>
                  <el-table-column label="状态" minWidth="150" align="center" prop="status">
                    <template v-slot="scope">
                      <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="输入人" minWidth="150" align="center" prop="updateEmp"/>
                  <el-table-column label="输入日期" minWidth="150" align="center" prop="updateDate"/>
                  <el-table-column label="操作" align="center" min-width="160px">
                    <template slot-scope="scope">
                      <el-button size="mini" type="text" icon="el-icon-edit"
                                 @click="handleEdit('edit',scope.$index, scope.row)">
                        修改
                      </el-button>
                      <el-button size="mini" type="text" icon="el-icon-delete"
                                 @click="handleDelete(scope.$index, scope.row)">
                        删除
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <div style="margin-top: 10px;margin-left: 58%;padding: 25px 0px 20px 20px;"
                     class="avue-crud__pagination">
                  <el-pagination v-show="queryParams.total > 0"
                                 background
                                 @size-change="handleSizeChange"
                                 @current-change="handleCurrentChange"
                                 layout="total, sizes, prev, pager, next, jumper"
                                 :current-page="queryParams.pageNum"
                                 :page-sizes="[10, 20, 30, 50]"
                                 :page-size="queryParams.pageSize"
                                 :total="queryParams.total">
                  </el-pagination>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
        <!-- 弹窗 -->
        <div v-if="dialog.visible">
          <el-dialog :title="dialog.title"
                     :visible.sync="dialog.visible"
                     :width="dialog.width"
                     class="customDialogStyle"
                     append-to-body
                     :destroy-on-close="true"
                     :close-on-click-modal="false">
            <template>
              <Update v-if="dialog.type === 'add' || dialog.type === 'edit'"
                      :type="dialog.type"
                      :data="dialog.data"
                      :uuid="uuid"
                      @submitSave="submitSave"
                      @close="dialog.visible=false"/>
            </template>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {baseInfoTree, getChildrenList, deleteBaseInfo} from "@/api/human/hm/baseInfo"
import Update from "./update.vue"
import '@/assets/styles/humanStyles.scss';

export default {
  name: "BaseInfo",
  dicts: ['sys_normal_disable'],
  components: {
    Update
  },
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'dicName'
      },
      menuData: [],
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '30%',
        data: {},
      },
      queryParams: {
        pageSize: 10,
        pageNum: 1,
        total: 0,
        uuid: '1633336433080074242'
      },
      uuid: undefined,
      table: {
        border: true,
        loading: false,
      },
      tableData: [],
      defaultShowNodes: [], //默认显示id
    }
  },
  watch: {
    //监听数据 设置默认展示第一层数据
    menuData: {
      handler(val) {
        val.forEach(item => {
          this.uuid = item.uuid
          this.defaultShowNodes.push(item.uuid);
        })
      },
      deep: true,
    }
  },
  created() {
    this.getBaseInfoTree()
    this.onLoad()
  },
  methods: {
    //获取选单配置树
    getBaseInfoTree() {
      baseInfoTree().then(res => {
        this.menuData = res.data
      })
    },
    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.uuid = data.uuid
      this.uuid = this.queryParams.uuid
      this.tableData = []
      this.onLoad()
    },
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      getChildrenList(this.queryParams).then(res => {
        this.queryParams.total = res.data.total;//数据总数
        this.tableData = res.data.rows;//表格数据
        this.table.loading = false;
      }, error => {
        this.table.loading = false;
      });
    },
    // 分页-每页多少条
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.onLoad();
    },
    // 分页-当前页
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.onLoad();
    },
    //新增
    handleAdd(type) {
      this.dialog.type = type
      this.dialog.title = '新增选单资料'
      this.dialog.data = {};
      this.dialog.data.status = "0";
      this.dialog.visible = true;
    },
    //修改
    handleEdit(type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑选单资料'
      this.dialog.data = row;
      this.dialog.visible = true;
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法
        deleteBaseInfo(row.uuid).then(res => {
          this.onLoad();
          this.getBaseInfoTree()
          this.tableData = []
          this.$message({type: 'success', message: res.msg});
        }, error => {
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
      this.getBaseInfoTree()
    },
  }
}
</script>

<style>
.el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-scrollbar__bar.is-horizontal {
  display: none;
}
</style>
