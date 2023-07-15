<template>
  <div v-if="userDialogVisible">
    <el-dialog :visible.sync="dialogVisible" :width="width" :title="title" class="customDialogStyle" append-to-body
               @close="$emit('close-dialog')" :destroy-on-close="true" :close-on-click-modal="false">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="输入关键字进行过滤" v-model="filterText">
          </el-input>
          <el-tree :style="{ overflowY: 'auto', maxHeight: screenHeight + 'PX' }"
                   class="filter-tree"
                   :data="organizationData"
                   :props="defaultProps"
                   node-key="id"
                   :expand-on-click-node="false"
                   :default-expanded-keys="defaultShowNodes"
                   :filter-node-method="filterNode"
                   @node-click="handleNodeClick"
                   ref="tree">
          </el-tree>
        </el-col>
        <el-col :span="16">
          <div>
            <h3 style="margin-top: 0;">设备</h3>
            <el-form :model="query" ref="query" :inline="true">
              <el-form-item prop="equipmentName">
                <el-input placeholder="输入设备名称" clearable v-model="query.equipmentName"/>
              </el-form-item>
              <!-- 操作按钮 -->
              <el-form-item>
                <el-button @click="handleQuery" type="primary" icon="el-icon-search" size="medium">查 询
                </el-button>
              </el-form-item>
            </el-form>
            <el-table :data="tableData" :height="height" v-loading="tableDataloading" stripe @sort-change="handleSort"
                      size="small">
              <el-table-column prop="equipmentName" label="设备名称" sortable minWidth="150"/>
              <el-table-column prop="equipmentNumbert" label="设备编码" sortable minWidth="150"/>
              <el-table-column prop="equipmentType" label="设备类型" sortable minWidth="150"/>
              <el-table-column fixed="right" label="操作" width="150" align="center">
                <template slot-scope="scope">
                  <el-button size="mini" icon="el-icon-thumb" type="text"
                             @click="handleAdd(scope.$index, scope.row)">选择
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px;right: 0;padding: 0;" class="avue-crud__pagination">
              <el-pagination v-show="page.total > 0"
                             background
                             @size-change="handleSizeChange"
                             @current-change="handleCurrentChange"
                             layout="total, sizes, prev, pager, next, jumper" :current-page="page.current"
                             :page-sizes="[10, 50, 100, 200]" :page-size="page.size"
                             :total="page.total"/>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
  import {
    getEquipmentCombineTree,
    getEquipmentAccountByParams,
    getEquipmentByLocationId
  } from "@/api/equip/me/ledgera";

  export default {
    props: {
      //是否是多选
      isMultiple: {type: Boolean, default: true},
      //显示
      userDialogVisible: {type: Boolean, default: false},
      //宽度
      width: {type: String, default: '70%'},
      //标题
      title: {type: String, default: '人员选择'},
      //列表高度
      height: {type: [Number, String], default: 300},
    },
    data() {
      return {
        dialogVisible: false,
        defaultProps: {
          children: 'children',
          label: function (data) {
            return data.domainNo + "-" + data.domainName
          },
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
          equipmentName: '',
          equipmentLocation:'',
          firstResponsiblePerson:'',
        },
        //分页
        page: {size: 20, current: 1, total: 0, order: "id", orderby: "asc"},
        tableDataloading: false,
        //位置id
        equipmentLocation: '',
        //机构人员列表
        tableData: [],
      }
    },
    watch: {
      userDialogVisible: {
        handler: function (val) {
          this.dialogVisible = val
        },
        immediate: true
      },
      //监听关键字
      filterText(val) {
        this.$refs.tree.filter(val);
      },
      //监听数据 设置默认展示第一层数据
      organizationData: {
        handler(val) {
          val.forEach(item => {
            this.defaultShowNodes.push(item.id);
          })
        },
        deep: true,
      },
    },
    created() {
      this.getDepartmentTree();
    },
    methods: {
      //获取部门树
      getDepartmentTree() {
        // 合并设备域、上下级设备树
        getEquipmentCombineTree().then(response => {
          this.organizationData = response.data
        });
      },
      //载入机构人员列表数据
      onLoad() {
        this.query.equipmentLocation = this.equipmentLocation;
        this.tableDataloading = true;
        let queryParams = {
          page:this.page.current,
          limit:this.page.size,
          equipmentName:this.query.equipmentName,
          equipmentLocation:this.equipmentLocation,
          firstResponsiblePerson:'',
        };
        getEquipmentAccountByParams(queryParams).then(res => {
          this.tableDataloading = false;
          let data = res.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.list;//表格数据
        }, error => {
          this.tableDataloading = false;
        });
      },
      //关键字过滤
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      //点击节点方法
      handleNodeClick(data) {
        this.equipmentLocation = data.id;
        this.onLoad()
      },
      //查询机构人员列表方法
      handleQuery() {
        this.page.current = 1;
        this.onLoad();
      },
      //添加人员
      handleAdd(index, row) {
        //发送给父组件（单选方法）
        this.$emit('single-select', row)
        this.dialogVisible = false
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
