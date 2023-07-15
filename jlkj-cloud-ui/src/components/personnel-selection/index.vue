<template>
  <div v-if="userDialogVisible">
    <el-dialog :visible.sync="dialogVisible"
               :width="width"
               :title="title"
               class="customDialogStyle"
               append-to-body
               @close="$emit('close-dialog')"
               :destroy-on-close="true"
               :close-on-click-modal="false">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="输入关键字进行过滤"
                    v-model="filterText"
                    style="">
          </el-input>
          <el-tree :style="{ overflowY: 'auto', maxHeight: screenHeight + 'PX' }"
                   class="filter-tree"
                   :data="organizationData"
                   :props="defaultProps"
                   node-key="id"
                   :expand-on-click-node="false"
                   :default-expanded-keys="defaultShowNodes"
                   :show-checkbox="showCheckbox"
                   :filter-node-method="filterNode"
                   @node-click="handleNodeClick"
                   @check="handleCheck"
                   ref="tree">
          </el-tree>
        </el-col>
        <el-col :span="16">
          <div>
            <h3 style="margin-top: 0;">机构-人员</h3>
            <el-form :model="query"
                     ref="query"
                     :inline="true">
              <el-form-item prop="keywords">
                <el-input placeholder="输入关键字查询"
                          clearable
                          v-model="query.keywords">
                </el-input>
              </el-form-item>
              <!-- 操作按钮 -->
              <el-form-item>
                <el-button @click="handleQuery"
                           type="primary"
                           icon="el-icon-search"
                           size="medium">查 询</el-button>
                <!-- <el-button @click="handleReset"
                         icon="el-icon-refresh-left"
                         size="medium">重 置</el-button> -->
              </el-form-item>
            </el-form>
            <el-table :data="tableData"
                      :height="height"
                      v-loading="tableDataloading"
                      stripe
                      @sort-change="handleSort"
                      size="small">
              <el-table-column prop="job_number"
                               label="工号"
                               sortable
                               minWidth="150">
              </el-table-column>
              <el-table-column prop="full_name"
                               label="姓名"
                               sortable
                               minWidth="150">
              </el-table-column>
              <el-table-column prop="department_name"
                               label="所属机构"
                               sortable
                               minWidth="150">
              </el-table-column>
              <el-table-column fixed="right"
                               label="操作"
                               width="150"
                               align="center">
                <template slot-scope="scope">
                  <el-button size="mini"
                             plain
                             icon="el-icon-circle-plus"
                             type="primary"
                             @click="handleAdd(scope.$index, scope.row)">添加
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px;right: 0;padding: 0;"
                 class="avue-crud__pagination">
              <el-pagination v-show="page.total > 0"
                             background
                             @size-change="handleSizeChange"
                             @current-change="handleCurrentChange"
                             layout="total, sizes, prev, pager, next, jumper"
                             :current-page="page.current"
                             :page-sizes="[10, 50, 100, 200]"
                             :page-size="page.size"
                             :total="page.total">
              </el-pagination>
            </div>
          </div>
          <div v-if="isMultiple">
            <h3>班组成员</h3>
            <el-table :data="teamData"
                      :height="height"
                      stripe
                      size="small">
              <el-table-column prop="job_number"
                               label="工号"
                               width="180">
              </el-table-column>
              <el-table-column prop="full_name"
                               label="姓名"
                               width="180">
              </el-table-column>
              <el-table-column prop="department_name"
                               label="所属机构">
              </el-table-column>
              <el-table-column fixed="right"
                               label="操作"
                               width="180"
                               align="center">
                <template slot-scope="scope">
                  <el-button size="mini"
                             plain
                             icon="el-icon-delete"
                             type="danger"
                             @click="handleDelete(scope.$index, scope.row)">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
      <span v-if="isMultiple"
            slot="footer"
            class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="sealSubmitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { departmentTree, getUserListByDepartmentOrNameOrJobnumber, getUserListByUserIds } from "@/api/sys/index"
export default {
  props: {
    //是否是多选
    isMultiple: {
      type: Boolean,
      default: true
    },
    //显示
    userDialogVisible: {
      type: Boolean,
      default: false
    },
    //宽度
    width: {
      type: String,
      default: '70%'
    },
    //标题
    title: {
      type: String,
      default: '人员选择'
    },
    //是否显示复选框
    showCheckbox: {
      type: Boolean,
      default: false
    },
    //列表高度
    height: {
      type: [Number, String],
      default: 300
    },
    //回显班组成员id(单选不需要传，不需要回显也可以不传)
    user_ids: {
      type: String,
    },
    //回显数组（不需要id请求，外面直接传过来的）
    personList: {
      type: Array,
      default: () => {
        return []
      }
    }

  },
  data () {
    return {
      dialogVisible: false,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      //机构树高度
      screenHeight: '',
      //关键字
      filterText: '',
      //机构树列表
      organizationData: [],
      defaultShowNodes: [], //默认显示id
      //查询对象
      query: {
        keywords: ''
      },
      //分页
      page: {
        size: 10,
        current: 1,
        total: 0,
        order: "id",
        orderby: "asc",
      },
      tableDataloading: false,
      //部门id
      department_id: '',
      //机构人员列表
      tableData: [],
      //班组成员
      teamData: []
    }
  },
  watch: {
    userDialogVisible: {
      handler: function (val) {
        this.dialogVisible = val
      },
      immediate: true
    },
    isMultiple: {
      handler (val) {
        //根据列表高度控制机构树高度
        if (val) {
          this.screenHeight = Number(this.height) * 2 + 110
        } else {
          this.screenHeight = Number(this.height) + 110
        }
      },
      immediate: true
    },
    //监听关键字
    filterText (val) {
      this.$refs.tree.filter(val);
    },
    //监听数据 设置默认展示第一层数据
    organizationData: {
      handler (val) {
        val.forEach(item => {
          this.defaultShowNodes.push(item.id);
        })
      },
      deep: true,
    },
  },
  created () {
    this.getDepartmentTree()
    //根据id获取班组人员列表 （单选不需要回显  ）
    if (this.user_ids && this.isMultiple) {
      let ids = this.user_ids.split(',')
      let params = { user_ids: ids }
      this.getTeamData(params)
    }
    //有班组人员列表 不需要请求接口
    if (this.personList.length != 0 && this.isMultiple) {
      this.teamData = this.personList
    }
  },
  methods: {
    //获取部门树
    getDepartmentTree () {
      departmentTree().then(res => {
        this.organizationData = res.data
      })
    },
    //载入机构人员列表数据
    onLoad () {
      this.query.department_id = this.department_id
      this.tableDataloading = true
      getUserListByDepartmentOrNameOrJobnumber(this.page, this.query).then(res => {
        this.tableDataloading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
      }, error => {
        this.tableDataloading = false
      });
    },
    //获取班组成员列表
    getTeamData (params) {
      getUserListByUserIds(params).then(res => {
        this.teamData = res.data.data
      })
    },
    //关键字过滤
    filterNode (value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //点击节点方法
    handleNodeClick (data) {
      this.department_id = data.domain.id
      this.onLoad()
    },
    //选中复选框的方法
    handleCheck (data, checkeData) {
    },
    //查询机构人员列表方法
    handleQuery () {
      //点搜索页码应该重置，不然除了第一页搜不到人（要求的）
      this.page = {
        size: 10,
        current: 1,
        total: 0,
        order: "id",
        orderby: "asc",
      },
        this.onLoad();
    },
    //添加人员
    handleAdd (index, row) {
      if (this.isMultiple) {
        if (this.teamData.length > 0) {
          //相同人员限制
          for (let i = 0; i < this.teamData.length; i++) {
            if (this.teamData[i].id == row.id) {
              this.$message.warning(`班组成员中存在${row.full_name}`)
              return false
            }
          }
          this.teamData.push(row)
        } else {
          this.teamData.push(row)
        }
        // let arr = this.teamData.map((item) => {
        //   return item.id
        // });
        // if (arr.includes(row.id)) {
        //   this.$message.warning(`班组成员中存在${row.full_name}`)
        // } else {
        //   this.teamData.push(row)
        // }
      } else {
        //发送给父组件（单选方法）
        this.$emit('single-select', row)
        this.dialogVisible = false
      }
    },
    //删除人员
    handleDelete (index, row) {
      this.teamData.splice(index, 1)
    },
    //确定弹窗的方法
    sealSubmitForm () {
      //发送给父组件（多选方法）
      this.$emit('multiple-select', this.teamData)
      this.dialogVisible = false
      this.teamData = []
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.size = val;
      this.onLoad();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.current = val;
      this.onLoad();
    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = "id";
        this.page.orderby = "asc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
  }
}
</script>

<style>
</style>
