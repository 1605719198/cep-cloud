<template>
  <el-dialog title="选择部署流程" :visible.sync="visible" append-to-body :close-on-click-modal="false">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="流程分类" prop="category">
        <el-select v-model="queryParams.category" clearable placeholder="请选择" size="small">
          <el-option
            v-for="item in categoryOptions"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="流程名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
<!--      选择流程-->
      <el-table v-loading="publish.loading" :data="publish.dataList">
        <el-table-column label="流程标识" align="center" prop="processKey" :show-overflow-tooltip="true" />
        <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" @click="handleProcessView(scope.row)">
              <span>{{ scope.row.processName }}</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="流程版本" align="center">
          <template slot-scope="scope">
            <el-tag size="medium" >v{{ scope.row.version }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <el-tag type="success" v-if="!scope.row.suspended">激活</el-tag>
            <el-tag type="warning" v-if="scope.row.suspended">挂起</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="mini"
              icon="el-icon-check"
              v-if="!scope.row.suspended"
              @click.native="handleSelect(scope.row)"
              v-hasPermi="['workflow:deploy:status']"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="publishTotal > 0"
        :total="publishTotal"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <!-- 流程图 -->
    <el-dialog :title="processView.title" :visible.sync="processView.open" width="70%" append-to-body :close-on-click-modal="false">
      <process-viewer :key="`designer-${processView.index}`" :xml="processView.xmlData" :style="{height: '400px'}" />
    </el-dialog>
  </el-dialog>
</template>

<script>
import { listAllCategory } from '@/api/workflow/category'
import { getBpmnXml, getDeployListPlus } from '@/api/workflow/deploy'
import ProcessViewer from '@/components/ProcessViewer'

export default {
  name: 'deploy',
  components: {ProcessViewer},
  data() {
    return {
      visible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processName: null,
        category: null,
      },
      // 已部署流程
      publish: {
        loading: false,
        dataList: []
      },
      // 流程图详细信息
      processView: {
        title: '',
        open: false,
        index: undefined,
        xmlData:"",
      },
      // 总条数
      publishTotal: 0,
      // 流程分类
      categoryOptions: [],
    }
  },
  created() {
  },
  methods: {
    /** 页面初始化 */
    init() {
      this.getCategoryList()
      this.getList()
      this.visible = true;
    },
    /** 查询流程分类列表 */
    getCategoryList() {
      listAllCategory().then(response => this.categoryOptions = response.data);
    },
    /** 查询流程部署列表 */
    getList() {
      getDeployListPlus(this.queryParams).then(response => {
        this.publish.dataList = response.rows;
        this.publishTotal = response.total;
      });
    },
    /** 选择流程 */
    handleSelect(row) {
      this.$emit('check',row.definitionId)
      this.visible = false
      this.$modal.msgSuccess("流程绑定成功！")
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
    /** 查看流程图 */
    handleProcessView(row) {
      let definitionId = row.definitionId;
      this.processView.title = "流程图";
      this.processView.index = definitionId;
      // 发送请求，获取xml
      getBpmnXml(definitionId).then(response => {
        this.processView.xmlData = response.msg;
      })
      this.processView.open = true;
    },
  }
}
</script>

<style scoped>

</style>
