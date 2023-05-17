<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
              v-hasPermi="['human:payFormation:add']"
            >添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['human:payFormation:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-circle-check"
              size="mini"
              @click="handleSave"
              v-hasPermi="['human:payFormation:add']"
            >保存</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="form.payFormationList" @selection-change="handleSelectionChange1">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="薪酬项目编码" align="center" prop="payProCode" />
          <el-table-column label="薪酬项目名称" align="center" prop="payProName" />
          <el-table-column label="说明" align="center" prop="description" />
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入日期" align="center" prop="createDate" width="180">
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
          @pagination="getList"
        />

        <!-- 添加薪酬项目对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="head-container treeDept">
                <el-scrollbar class="treeScrollbar">
                  <el-tree
                    :data="payFormationOptions"
                    :props="defaultProps"
                    :default-expand-all="false"
                    :highlight-current="true"
                    :expand-on-click-node="false"
                    :default-expanded-keys="expandedKeys"
                    node-key="id"
                    ref="tree"
                    @node-click="handleNodeClick"
                  />
                </el-scrollbar>
              </div>
            </el-col>
            <el-col :span="18">
              <div style="margin-bottom: 10px">
                <el-button type="primary" @click="handleSelect">确 定</el-button>
                <el-button type="primary" @click="cancel">取 消</el-button>
              </div>
              <el-table @row-click="clickRow" v-loading="loading" ref="table" :data="tableData" @selection-change="handleSelectionChange"
                        height="360px">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="薪酬项目编码" prop="payProCode"/>
                <el-table-column label="薪酬项目名称" prop="payProName"/>
                <el-table-column label="说明" prop="description"/>
              </el-table>
              <pagination
                v-show="total1>0"
                :total="total1"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
              />
            </el-col>
          </el-row>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listPayFormation, delPayFormation, addPayFormation } from "@/api/human/hs/payFormation";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import { listProjectPayTree, listProjectPay} from '@/api/human/hs/projectPay'

export default {
  name: "PayFormation",
  data() {
    return {
      // 遮罩层
      loading: false,
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
      total1: 0,
      // 公司薪酬项目设定表格数据
      form:{
        payFormationList: [],
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: undefined,
        id: undefined
      },
      companyName: [],
      payFormationOptions: [],
      //el tree默认值
      defaultProps: {
        children: "children",
        label: "payProName",
      },
      //默认展开指定节点
      expandedKeys: [],
      //添加薪酬项目弹窗表格数据
      tableData: [],
      //选中薪酬项目编码
      payProCode: undefined,
      //选中薪酬项目名称
      payProName: undefined,
      //选中薪酬项目ID
      id: undefined
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    /** 查询公司薪酬项目设定列表 */
    getList() {
      this.loading = true;
      listPayFormation(this.queryParams).then(response => {
        this.form.payFormationList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.id = selection.map(item => item.id)
      this.payProCode = selection.map(item => item.payProCode)
      this.payProName = selection.map(item => item.payProName)
    },
    // 多选框选中数据
    handleSelectionChange1(selection) {
      this.ids = selection.map(item => item.uuid)
      this.multiple = !selection.length;
    },
    handleSelect() {
      const form = {
        payProId: this.id[0],
        payProCode: this.payProCode[0],
        payProName: this.payProName[0]
      }
      if (this.id == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      if (this.id.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.open = false
      this.form.payFormationList.push(form)
      this.total = this.form.payFormationList.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.queryParams.compId === undefined) {
        this.$modal.msgError("请先选择公司别进行搜索！");
      } else {
        listProjectPayTree().then(response => {
          this.payFormationOptions = this.handleTree(response, 'id', 'parentid', 'children')
          this.expandedKeys.push(this.payFormationOptions[0].id)
          this.queryParams.id = this.payFormationOptions[0].id
          this.onLoad();
        });
        this.open = true;
        this.title = "添加薪酬项目";
      }

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除公司薪酬项目设定编号为"' + uuids + '"的数据项？').then(function() {
        return delPayFormation(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.id = data.id
      this.tableData = []
      this.onLoad()
    },
    //载入数据
    onLoad() {
      this.loading = true;//加载状态
      listProjectPay(this.queryParams).then(response => {
        this.total1 = response.total
        this.tableData = response.rows//表格数据
        this.loading = false
      })
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    handleSave() {
      this.form.payFormationList.map(value => {
        value.compId = this.queryParams.compId
      })
      addPayFormation(this.form).then(res => {
        this.$modal.msgSuccess("新增成功");
        this.getList();
      })
    }
  }
};
</script>
<style scoped>
.treeDept {
  height: 40vh;
  width: 100%;
}
.treeScrollbar {
  height: 100%;
}
</style>
