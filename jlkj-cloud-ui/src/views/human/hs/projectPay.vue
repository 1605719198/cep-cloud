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
                        <el-button
                          type="primary"
                          size="mini"
                          plain
                          @click="handleSave"
                          v-hasPermi="['human:projectPay:save']"
                          :disabled="multiple">保存</el-button>
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-table height="70vh" size="small" v-loading="table.loading" :row-class-name="addIndex"
                          :data="tableData" @selection-change="handleSelectionChange" @row-click="addLine" stripe
                >
                  <el-table-column type= "selection"  width="45" align="center" />
                  <el-table-column label="薪酬项目编码" align="center" prop="payProCode">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.payProCode" placeholder="请输入内容" :disabled="scope.row.status==='1'"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="薪酬项目名称" align="center" prop="payProName">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.payProName" placeholder="请输入内容" :disabled="scope.row.status==='1'"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="需设定标准" align="center" prop="isSta" width="90">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isSta" true-label="1" false-label="0" :disabled="scope.row.status==='1'"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="标准的内容" align="center" prop="staCon">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.staCon" placeholder="请输入内容" :disabled="scope.row.status==='1'"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="是否岗位定薪项目" align="center" prop="isPostPro" width="80">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isPostPro" true-label="1" false-label="0" :disabled="scope.row.status==='1'"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="是否员工定薪项目" align="center" prop="isEmpPro" width="80">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isEmpPro" true-label="1" false-label="0" :disabled="scope.row.status==='1'"></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="是否下拉选单" align="center" prop="isLov" width="80">
                    <template v-slot="scope">
                      <el-checkbox v-model="scope.row.isLov" true-label="1" false-label="0" :disabled="scope.row.status==='1'" @change="isLovChange($event,scope.row)" ></el-checkbox>
                    </template>
                  </el-table-column>
                  <el-table-column label="下拉选单的内容" align="center" prop="lovConId">
                    <template v-slot="scope">
                      <el-select v-model="scope.row.lovConId" placeholder="请选择"  v-show="scope.row.isLov==='1'" :disabled="scope.row.status==='1'">
                        <el-option
                          v-for="dict in salaryOptions.SalaryParameters"
                          :key="dict.id"
                          :label="dict.dicName"
                          :value="dict.id.toString()"
                        />
                      </el-select>
                      <el-input v-model="scope.row.lovConId" placeholder="请输入" maxlength="32" v-show="scope.row.isLov!=='1'" :disabled="scope.row.status==='1'"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="说明" align="center" prop="salaryDescribe">
                    <template v-slot="scope">
                      <el-input v-model="scope.row.salaryDescribe" placeholder="请输入内容" :disabled="scope.row.status==='1'"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="状态" align="center" prop="status" width="80" >
                    <template v-slot="scope">
                      <el-switch
                        v-if="scope.row.id!=null"
                        v-model="scope.row.status"
                        active-color="#ff4949"
                        inactive-color="#13ce66"
                        active-value="1"
                        inactive-value="0"
                        @change="handleStatusChange(scope.row)"
                      ></el-switch>
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
  </div>
</template>

<script>
import { listProjectPay, saveProjectPay, listProjectPayTree, changeStatus, delProjectPay } from '@/api/human/hs/projectPay'
import { getSalaryOptions } from '@/api/human/hs/salaryBasis'
export default {
  name: 'SalaryProjectBasis',
  dicts: ['sys_normal_disable'],
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
      //登录用户信息
      user:{},
      // 总条数
      total: 0,
      // 弹出层标题
      title: '',
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['SalaryParameters'],
        compId: null
      },
      //薪资选单选项列表
      salaryOptions: [],
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
  created() {
    this.initData()
    this.getBaseInfoTree()
    this.getDisc();
  },
  methods: {
    //是否下拉选单多选框处理
    isLovChange(val,row){
      this.tableData[row.index].lovConId = null;
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
    },
    /** 查询集团级薪资项目输入维护列表 */
    getList() {
      this.getBaseInfoTree()
    },
    //查询薪资选单
    getDisc() {
      getSalaryOptions(this.salaryOptionType).then(response => {
        this.salaryOptions = response.data
      })
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
      saveProjectPay(this.multipleSelection).then(res => {
        this.$modal.msgSuccess('保存成功')
        this.getList()
      })
    },
    // 作废按钮
    cancellation() {
      for(let i = 0;i<this.multipleSelection.length;i++){
        this.multipleSelection[i].status = "1"
      }
      saveProjectPay(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    // 数据状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '吗？').then(function () {
        return changeStatus(row.id,row.parentid, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
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
        if(this.tableData.length===0){
          this.addLine();
        }
        this.table.loading = false
      }, error => {
        this.table.loading = false
      })
    },
    //添加行index
    addIndex({ row, rowIndex }) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.tableData.length === row.index + 1) {
        const newLine = {
          payProCode: '',
          payProName: '',
          isSta: '',
          staCon: '',
          isPostPro: '',
          isEmpPro: '',
          isLov: '0',
          lovConId: '',
          salaryDescribe: '',
          status: '0',
          payType: '1',
          parentid: this.queryParams.id,
          isShowno: '0',
          ifEnd: '1',
          creator: null,
          creatorId: null,
          creatorNo: null,
          createDate: null,
        }
        this.tableData.push(newLine)
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除所选择的数据项？').then(function() {
        return delProjectPay(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
}
</script>
