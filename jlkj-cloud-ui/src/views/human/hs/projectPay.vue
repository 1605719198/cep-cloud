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
            <div>
              <div class="avue-crud__search" style="border: 0">
                <el-row>
                  <el-col :span="20">
                    <el-form :inline="true">
                      <!-- 操作按钮 -->
                      <el-form-item>
                        <el-button type="primary" size="mini" plain @click="handleSave">保存</el-button>
                      </el-form-item>
                      <el-form-item>
                        <el-button
                          type="danger"
                          plain
                          size="mini"
                          @click="cancellation"
                        >作废
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-table height="70vh" size="small" v-loading="table.loading" :row-class-name="addIndex"
                          :data="tableData" @selection-change="handleSelectionChange" @row-click="addLine" stripe
                >
                  <el-table-column type="selection" width="45" align="center"/>
                  <el-table-column label="薪酬项目编码" align="center" prop="payProCode">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.payProCode" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="薪酬项目名称" align="center" prop="payProName">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.payProName" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="需设定标准" align="center" prop="isSta" width="90">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isSta" true-label="1" false-label="0"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="标准的内容" align="center" prop="staCon">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.staCon" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="是否岗位定薪项目" align="center" prop="isPostPro" width="80">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isPostPro" true-label="1" false-label="0"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="是否员工定薪项目" align="center" prop="isEmpPro" width="80">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isEmpPro" true-label="1" false-label="0"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="是否下拉选单" align="center" prop="isLov" width="80">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isLov" true-label="1" false-label="0"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="下拉选单的内容" align="center" prop="lovConId">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.lovConId" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="说明" align="center" prop="salaryDescribe">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.salaryDescribe" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="状态" align="center" prop="status" width="80">
                    <template v-slot="scope">
                      <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="输入人" align="center" prop="creator" width="80"/>
                  <el-table-column label="输入日期" align="center" prop="createDate" width="100">
                    <template v-slot="scope">
                      <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
                    </template>
                  </el-table-column>
                </el-table>
                <pagination
                  v-show="total>0"
                  :total="total"
                  :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize"
                  @pagination="onLoad"
                />
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <select-project-pay ref="select" @ok="getPopData"></select-project-pay>
  </div>
</template>

<script>
import { listProjectPay, addProjectPay, listProjectPayTree } from '@/api/human/hs/projectPay'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import selectProjectPay from '@/views/components/human/selectView/hs/selectProjectPay'

export default {
  name: 'SalaryProjectBasis',
  dicts: ['sys_normal_disable'],
  components: { selectProjectPay },
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      //登录人工号
      userEmpId: null,
      //登录人姓名
      nickName: null,
      //登录人公司
      logincompId: null,
      // 总条数
      total: 0,
      // 弹出层标题
      title: '',
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
        id: null,
        payType: 1
      },
      //选单树数据转化
      defaultProps: {
        children: 'children',
        label: 'payProName'
      },
      //选单目录数据
      menuData: [],
      //默认显示id
      defaultShowNodes: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //选中项数据
      multipleSelection: []
    }

  },
  watch: {},
  created() {
    this.initData()
    this.getBaseInfoTree()
  },
  methods: {
    //初始化数据
    initData() {
      this.userEmpId = this.$store.state.user.name
      this.nickName = this.$store.state.user.userInfo.nickName
      this.logincompId = this.$store.state.user.userInfo.compId
    },
    /** 查询集团级薪资项目输入维护列表 */
    getList() {
      this.getBaseInfoTree()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        payProCode: null,
        payProName: null,
        isSta: null,
        staCon: null,
        isPostPro: null,
        isEmpPro: null,
        isLov: null,
        lovConId: null,
        salaryDescribe: null,
        parentid: null,
        parents: null,
        payType: null,
        isShowno: null,
        num: null,
        defaultValue: null,
        status: null,
        ifUsed: null,
        creator: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 保存按钮 */
    handleSave() {
      addProjectPay(this.multipleSelection).then(res => {
        this.$modal.msgSuccess('保存成功')
        this.getList()
      })
    },
    // 作废按钮
    cancellation() {
      //添加薪酬项目pop
      // this.openPop()
      for(let i = 0;i<this.multipleSelection.length;i++){
        this.multipleSelection[i].status = "1"
      }
      addProjectPay(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    //获取选单配置树
    getBaseInfoTree() {
      var param = {
        payType:1,
      }
      listProjectPayTree(param).then(response => {
        this.menuData = this.handleTree(response, 'id', 'parentid', 'children')
        this.defaultShowNodes.push(this.menuData[0].id)
        if(this.queryParams.id===null){
          this.queryParams.id = this.defaultShowNodes[0];
        }
        this.onLoad()
      })
    },
    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.id = data.id
      this.tableData = []
      this.onLoad()
    },
    //载入数据
    onLoad() {
      this.table.loading = true//加载状态
      listProjectPay(this.queryParams).then(response => {
        this.total = response.total
        this.tableData = response.rows
        const newLine = {
          payProCode: '',
          payProName: '',
          isSta: '',
          staCon: '',
          isPostPro: '',
          isEmpPro: '',
          isLov: '',
          lovConId: '',
          salaryDescribe: '',
          status: '0',
          payType: '1',
          parentid: this.queryParams.id,
          isShowno: '0',
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1)
        }
        this.tableData.push(newLine)
        this.table.loading = false
      }, error => {
        this.table.loading = false
      })
    },
    /** 添加下级操作 */
    handlechild(row) {
      this.reset()
      this.form.parentid = row.id
      this.form.creator = this.nickName
      this.form.creatorId = this.$store.state.user.name
      this.form.createDate = getDateTime(1)
      this.open = true
      this.title = '添加下级窗口'
    },
    addIndex({ row, rowIndex }) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (this.tableData.length == row.index + 1) {
        const newLine = {
          payProCode: '',
          payProName: '',
          isSta: '',
          staCon: '',
          isPostPro: '',
          isEmpPro: '',
          isLov: '',
          lovConId: '',
          salaryDescribe: '',
          status: '0',
          payType: '1',
          parentid: this.queryParams.id,
          isShowno: '0',
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1)
        }
        this.tableData.push(newLine)
      }
    },
    // 获取弹窗数据
    getPopData(val) {
 
    },
    // 打开薪资选择弹窗
    openPop() {
      this.$refs.select.show(this.queryParams.compId)
    }
  }
}
</script>
