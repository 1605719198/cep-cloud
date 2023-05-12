<template>
  <el-dialog title="添加薪酬项目" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-row :gutter="20">
      <!-- 左侧选单配置树 -->
      <el-col :span="4" class="left_tree">
        <div class="head-container" style="height: 75vh;width: 100%;">
          <el-scrollbar style="height: 100%;">
            <el-tree ref="tree"
                     class="filter-tree"
                     node-key="id"
                     :props="defaultProps"
                     :data="menuData"
                     :expand-on-click-node="false"
                     :default-expanded-keys="defaultShowNodes"
                     @node-click="handleNodeClick"
            >
            </el-tree>
          </el-scrollbar>
        </div>
      </el-col>
      <!-- 右侧列表 -->
      <el-col :span="20">
        <div style="margin-bottom: 15px">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
        <div>
          <el-table height="50vh" size="small" v-loading="table.loading" :row-class-name="addIndex" :data="tableData"
                    @selection-change="handleSelectionChange" stripe
          >
            <el-table-column type="selection" width="45" align="center"/>
            <el-table-column label="薪酬项目编码" align="center" prop="payProCode" show-overflow-tooltip />
            <el-table-column label="薪酬项目名称" align="center" prop="payProName" show-overflow-tooltip  />
            <el-table-column label="说明" align="center" prop="salaryDescribe" show-overflow-tooltip  />
          </el-table>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="onLoad"
          />
        </div>
      </el-col>

    </el-row>
  </el-dialog>
</template>

<script>
import {
  listProjectPayTree, listProjectPay,
} from '@/api/human/hs/projectPay'
export default {
  name: 'SalaryProjectBasis',
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //表格属性
      table: {
        border: true,
        loading: false
      },
      //列表数据
      tableData: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null
      },
      //选单树数据转化
      defaultProps: {
        children: 'children',
        label: 'payProName'
      },
      //默认显示id
      defaultShowNodes: [],
      //选单目录数据
      menuData: [],
      //多选框选中数据
      multipleSelection: []
    }
  },
  watch: {

  },
  created() {
    this.getBaseInfoTree()
  },
  methods: {
    // 显示弹框
    show(val) {
      this.queryParams.compId = val
      this.getBaseInfoTree()
      this.visible = true
      this.isSingle = true
    },
    //获取选单配置树
    getBaseInfoTree() {
      listProjectPayTree().then(response => {
        this.menuData = this.handleTree(response, 'id', 'parentid', 'children')
        this.defaultShowNodes.push(this.menuData[0].id)
        this.queryParams.id = this.menuData[0].id
        this.onLoad()
      })
    },
    //载入数据
    onLoad() {
      this.table.loading = true//加载状态
      listProjectPay(this.queryParams).then(response => {
        this.total = response.total
        this.tableData = response.rows//表格数据
        this.table.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection
    },
    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.id = data.id
      this.tableData = []
      this.onLoad()
    },
    addIndex({ row, rowIndex }) {
      row.index = rowIndex
    },
    // 提交按钮
    submitForm(){
      this.$emit("ok", this.multipleSelection);
      this.visible = false;
    },
    // 取消按钮
    cancel() {
      this.visible = false
    },
  }
}
</script>
