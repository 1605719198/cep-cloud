<template >
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="日期" prop="datetimeRange">
        <el-date-picker v-model="datetimeRange"
                        type="datetimerange"
                        start-placeholder="更新开始日期"
                        end-placeholder="更新结束日期"
                        @change="dateTimeChange"
                        value-format="yyyy-MM-dd"
                        format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item label="缺陷项目" prop="defectTitle">
        <el-input v-model="queryParams.defectTitle" placeholder="请输入缺陷项目" clearable />
      </el-form-item>
      <el-form-item label="处理条件" prop="processConditionNo">
        <el-select v-model="queryParams.processConditionNo" placeholder="请选择处理条件" style="width: auto !important;">
          <el-option v-for="dict in dict.type.equipment_condition_type"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value" />
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
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableData" >
      <el-table-column label="序号" type="index" width="100" align="center" />
      <el-table-column prop="defectTitle" label="缺陷项目" sortable width="180" align="center" />
      <el-table-column prop="defectSpecialtyName" sortable label="专业" align="center" />
      <el-table-column prop="importanceName" sortable label="重要程度" align="center" />
      <el-table-column prop="pointCheckPersonName" sortable label="点检责任人" align="center" />
      <el-table-column prop="workingZonePersonName" sortable label="作业区责任人" align="center" />
      <el-table-column prop="defectUpdateDate" sortable label="更新时间" align="center" />
      <el-table-column prop="processConditionName" sortable label="处理条件" align="center" />
      <el-table-column prop="completionRemark" sortable label="完成条件追踪" align="center" show-overflow-tooltip />
      <el-table-column prop="remark" sortable label="备注" align="center" show-overflow-tooltip />
      <el-table-column fixed="right" label="操作" width="180px" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)">修改</el-button>
          <el-button
            size="mini"
            icon="el-icon-delete"
            type="text"
            @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="handleQuery"
    />

    <!--  添加或修改参数配置对话框 -->
    <div v-if="open">
      <el-dialog :title="title"
                 class="customDialogStyle"
                 :visible.sync="open"
                 width="900px"
                 append-to-body
                 @close="cancel"
                 :close-on-click-modal="false">
        <el-form :model="ruleForm"
                 :inline="true"
                 :rules="rules"
                 ref="ruleForm"
                 label-width="150px"
                 class="demo-form-inline">
          <el-form-item label="缺陷项目"
                        prop="defectTitle">
            <el-input v-model="ruleForm.defectTitle"
                      style="width: 206px" />
          </el-form-item>
          <el-form-item label="更新日期"
                        prop="updateTime">
            <el-date-picker style="width: 206px"
                            v-model="ruleForm.defectUpdateDate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="专业"
                        prop="defectSpecialtyName">
            <el-select v-model="ruleForm.defectSpecialtyName"
                       placeholder="请选择专业"
                       clearable
                       style="width: 206px"
                       @change="specialtySelect">
              <el-option v-for="dict in dict.type.equipment_profession_type"
                         :key="dict.value"
                         :label="dict.label"
                         :value="{value:dict.value,label:dict.label}" />
            </el-select>
          </el-form-item>
          <el-form-item label="点检责任人"
                        prop="pointCheckPersonName">
            <el-popover ref="checkUserList"
                        placement="bottom-start"
                        trigger="click">
              <human-tree :datas="datas"
                          ref="humanTree1"
                          @handleData="nodeClick1"></human-tree>
            </el-popover>
            <el-input v-model="ruleForm.pointCheckPersonName"
                      ref="input1"
                      v-popover:checkUserList
                      :readonly="true"
                      class="menu-list__input"
                      style="width: 206px" />
          </el-form-item>
          <el-form-item label="重要程度"
                        prop="importanceName">
            <el-select v-model="ruleForm.importanceName"
                       placeholder="重要程度"
                       clearable
                       style="width: 206px;"
                       @change="importanceSelect">
              <el-option v-for="dict in dict.type.equipment_important_type"
                         :key="dict.value"
                         :label="dict.label"
                         :value="{value:dict.value,label:dict.label}" />
            </el-select>
          </el-form-item>
          <el-form-item label="作业区责任人"
                        prop="workingZonePersonName">
            <el-popover ref="workUserList"
                        placement="bottom-start"
                        trigger="click">
              <human-tree :datas="datas"
                          ref="humanTree2"
                          @handleData="nodeClick2"></human-tree>
            </el-popover>
            <el-input v-model="ruleForm.workingZonePersonName"
                      ref="input2"
                      v-popover:workUserList
                      :readonly="true"
                      class="menu-list__input"
                      style="width: 206px" />
          </el-form-item>
          <el-form-item label="处理条件"
                        prop="processConditionName">
            <el-select v-model="ruleForm.processConditionName"
                       placeholder="处理条件"
                       clearable
                       style="width: 206px"
                       @change="conditionSelect">
              <el-option v-for="dict in dict.type.equipment_condition_type"
                         :key="dict.value"
                         :label="dict.label"
                         :value="{value:dict.value,label:dict.label}" />
            </el-select>
          </el-form-item>
          <el-form-item label="完成情况跟踪"
                        prop="completionRemark">
            <el-input type="textarea"
                      :rows="3"
                      v-model="ruleForm.completionRemark"
                      maxlength="500"
                      clearable
                      show-word-limit
                      style="width: 540px;padding: 0px 58px 0px 0px" />
          </el-form-item>
          <el-form-item label="备注"
                        prop="remark">
            <el-input type="textarea"
                      :rows="3"
                      v-model="ruleForm.remark"
                      maxlength="500"
                      show-word-limit
                      style="width: 540px;padding: 0px 58px 0px 0px" />
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary"
                     @click="submitForm"
                     :loading="states">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  queryInfo,
  addInfo,
  updateInfo,
  delInfo,
  queryInfoById
} from "@/api/equip/me/flawstats";
import { getHumanTree } from "@/api/human/hm/humanResourceTree";
import HumanTree from '@/views/components/human/human-resource-tree/humanTree'
export default {
  name: "flawstats",
  dicts: ["equipment_important_type", "equipment_profession_type", "equipment_condition_type"],
  components: { HumanTree },
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      // 人员组织树资料
      datas: [],
      //提交状态
      states: false,
      // 弹出表单
      ruleForm: {
        // 缺陷项目
        defectTitle: "",
        // 更新日期
        defectUpdateDate: "",
        // 所属专业名称
        defectSpecialtyName: "",
        // 所属专业编号
        defectSpecialtyNo: "",
        // 点检责任人姓名
        pointCheckPersonName: "",
        // 点检责任人工号
        pointCheckPerson: "",
        // 重要程度名称
        importanceName: "",
        // 重要程度编号
        importanceNo: "",
        // 作业区责任人名称
        workingZonePersonName: "",
        // 作业区责任人工号
        workingZonePerson: "",
        // 处理条件名称
        processConditionName: "",
        // 处理条件编号
        processConditionNo: "",
        // 完成情况
        completionRemark: "",
        // 备注
        remark: "",
      },
      // 更新日期区间
      datetimeRange: [],
      // 总条数
      total: 0,
      // 遮罩层
      loading: true,
      // 字典表格数据
      tableData: [],
      // 弹出层标题
      title: "新增设备缺陷统计",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 20,
        startDate: undefined,
        endDate: undefined,
        defectTitle: undefined,
        processConditionNo: undefined,
      },
      // 表单校验
      rules: {
        defectTitle: [
          { required: true, message: "请输入缺陷项目", trigger: "blur" },
          { max: 15, message: '缺陷项目最长为15 个字符', trigger: 'blur' }
        ],
        defectUpdateDate: [
          { required: true, message: "请选择日期", trigger: "change" }
        ],
        defectSpecialtyName: [
          { required: true, message: "请选择专业", trigger: "change" }
        ],
        pointCheckPersonName: [
          { required: true, message: "请选择点检责任人", trigger: "blur" }
        ],
        importanceName: [
          { required: true, message: "请选择重要程度", trigger: "change" }
        ],
        workingZonePersonName: [
          { required: true, message: "请选择作业区责任人", trigger: "blur" }
        ],
        completionRemark: [
          { required: true, message: "请填写完成情况跟踪", trigger: "blur" }
        ],
      },
    };
  },
  created () {
    this.handleQuery();
    this.getTreeInfo();
  },
  methods: {
    // 专业change
    specialtySelect (val) {
      this.ruleForm.defectSpecialtyName = val.label
      this.ruleForm.defectSpecialtyNo = val.value
    },
    // 重要程度change
    importanceSelect (val) {
      this.ruleForm.importanceName = val.label
      this.ruleForm.importanceNo = val.value
    },
    // 处理条件change
    conditionSelect (val) {
      this.ruleForm.processConditionName = val.label
      this.ruleForm.processConditionNo = val.value
    },
    // 选择点检责任人
    nodeClick1 (node) {
      if (node.flag == 'user') {
        this.ruleForm.pointCheckPersonName = node.label
        this.ruleForm.pointCheckPerson = node.jobNumber
        this.$refs.input1.focus()
        this.$refs.checkUserList.doClose()
      }
    },
    // 选择作业区责任人
    nodeClick2 (node) {
      if (node.flag == 'user') {
        this.ruleForm.workingZonePersonName = node.label
        this.ruleForm.workingZonePerson = node.jobNumber
        this.$refs.input2.focus()
        this.$refs.workUserList.doClose()
      }
    },
    //修改/添加确定按钮
    submitForm () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.states = true
          if (this.ruleForm.id) {
            updateInfo(this.ruleForm).then((res) => {
              this.states = false
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.handleQuery();
            });
          } else {
            addInfo(this.ruleForm).then((res) => {
              this.states = false
              this.$modal.msgSuccess("添加成功");
              this.handleQuery();
              this.open = false;
            });
          }
        } else {
          this.$modal.msgWarning("请完善信息");
          return false;
        }
      });
    },
    // 取消按钮
    cancel () {
      this.reset()
      this.open = false;
    },
    /** 搜索按钮操作 */
    handleQuery () {
      let params = {
        ...this.queryParams,
      };
      queryInfo(params).then((res) => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.loading = false
      });
    },
    //修改页面
    handleEdit (row) {
      queryInfoById(row.id).then(response => {
        this.ruleForm = response.data
        this.open = true
        this.title = "编辑设备缺陷统计"
      })
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.datetimeRange = []
      this.queryParams = {}
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset()
      this.open = true;
      this.title = "新增设备缺陷统计";
    },
    /**获取人员机构数据结构*/
    getTreeInfo () {
      getHumanTree().then((response) => {
        this.datas = response.data
      });
    },
    // 选择日期区间
    dateTimeChange (value) {
      if (value === null) {
        this.queryParams.startDate = value
        this.queryParams.endDate = value
      } else {
        this.queryParams.startDate = value[0]
        this.queryParams.endDate = value[1]
      }
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        delInfo(row.id).then((res) => {
          this.$modal.msgSuccess("删除成功");
          this.queryParams.pageNum = 1
          this.handleQuery();
        });
      });
    },
    // 表单重置
    reset () {
      this.ruleForm = {
        // 缺陷项目
        defectTitle: "",
        // 更新日期
        defectUpdateDate: "",
        // 所属专业名称
        defectSpecialtyName: "",
        // 所属专业编号
        defectSpecialtyNo: "",
        // 点检责任人姓名
        pointCheckPersonName: "",
        // 点检责任人工号
        pointCheckPerson: "",
        // 重要程度名称
        importanceName: "",
        // 重要程度编号
        importanceNo: "",
        // 作业区责任人名称
        workingZonePersonName: "",
        // 作业区责任人工号
        workingZonePerson: "",
        // 处理条件名称
        processConditionName: "",
        // 处理条件编号
        processConditionNo: "",
        // 完成情况
        completionRemark: "",
        // 备注
        remark: "",
      }
    }
  },
};
</script>
<style>
.el-tooltip__popper {
  font-size: 14px;
  max-width: 50%;
}
</style>

<style scoped>
/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
<style>
.el-drawer__body {
  margin-left: 5px;
}

.el-drawer__container {
  position: absolute;
}

#el-drawer__title::before {
  content: "\e7a1";
}

.el-drawer__header {
  background-color: #eee;
  padding: 10px;
}

.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>


