<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="产线" prop="mgma1Name">
        <el-select v-model="queryParams.mgma1Name" placeholder="请选择产线" clearable @change="selectChange">
          <el-option
            v-for="item in lineList"
            :key="item.value"
            :label="item.label"
            :value="{ value: item.value, label: item.label }">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="停机记录编号" prop="mgms1No">
        <el-input v-model="queryParams.mgms1No" type="text" clearable placeholder="请输入停机记录编号" />
      </el-form-item>
      <el-form-item label="班别" prop="deptclassMgms1No">
        <el-select v-model="queryParams.deptclassMgms1No" placeholder="请选择班别" clearable>
          <el-option
            v-for="dict in dict.type.sys_classtype"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="停机时间" prop="datetimeRange">
        <el-date-picker v-model="queryParams.datetimeRange"
                        type="datetimerange"
                        start-placeholder="停机发生时间"
                        end-placeholder="结束时间"
                        :default-time="['12:00:00']"
                        @change="dateTimeChange"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        format="yyyy-MM-dd HH:mm:ss" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh-left" @click="handleReset">重置</el-button>
        <el-button type="text" icon="el-icon-info" @click="handleAdvanceFilter">高级筛选</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" plain type="primary" :loading="states" @click="handlePutonFile">手动立案</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableData" stripe :default-sort="{ prop: 'mgmp3no', order: 'descending' }">
      <template v-for="column in columns">
        <el-table-column :key="column.prop"
                         :fixed="column.fixed"
                         :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width"
                         :align="column.align"
                         :show-overflow-tooltip="column.show">
        </el-table-column>
      </template>
      <el-table-column prop="mgms1Stus" sortable label="停机记录状态" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_record_state" :value="scope.row.mgms1Stus" />
        </template>
      </el-table-column>
      <el-table-column prop="deptclassMgms1No" sortable label="单位班别" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_classtype" :value="scope.row.deptclassMgms1No" />
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="270" header-align="center" align="center">
        <template slot-scope="scope">
          <el-button :disabled="scope.row.totalTime > 0" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button :disabled="scope.row.totalTime > 0" size="mini" type="text" @click="handleEdit(scope.row)">重启</el-button>
          <el-button :disabled="!scope.row.totalTime > 0" size="mini" type="text" @click="handleCheckView(scope.row)">责任分摊</el-button>
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

    <!--    添加或修改弹框-->
    <div v-if="open">
      <el-dialog :title="title"
                 class="customDialogStyle"
                 :visible.sync="open"
                 width="1000px"
                 :destroy-on-close="true"
                 append-to-body
                 :close-on-click-modal="false">
        <el-form :model="ruleForm"
                 :rules="rules"
                 :inline="true"
                 ref="ruleForm"
                 label-width="150px"
                 class="demo-form-inline">
          <el-form-item label="产线名称"
                        prop="mgma1Name">
            <el-select v-model="ruleForm.mgma1Name"
                       placeholder="请选择"
                       style="width: 240px !important"
                       @change="mgms1nameselection">
              <el-option v-for="item in lineList"
                         :key="item.value"
                         :label="item.label"
                         :value="{ value: item.value, label: item.label }">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单位班别"
                        prop="deptclassMgms1No">
            <el-select v-model="ruleForm.deptclassMgms1No"
                       placeholder="班别"
                       clearable
                       style="width: 240px !important">
              <el-option v-for="dict in dict.type.sys_classtype"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="停机主类型"
                        prop="mgmc1Name">
            <el-select v-model="ruleForm.mgmc1Name"
                       placeholder="请选择"
                       style="width: 240px !important"
                       @change="mgmc1noselection">
              <el-option v-for="item in mainList"
                         :key="item.value"
                         :label="item.label"
                         :value="{ value: item.value, label: item.label }">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="记录来源"
                        prop="mgms1Type">
            <el-select v-model="ruleForm.mgms1Type"
                       placeholder="记录来源"
                       disabled
                       style="width: 240px !important">
              <el-option v-for="dict in dict.type.equipment_record_source"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="异常现象"
                        prop="mgms1Name">
            <el-input type="textarea"
                      v-model="ruleForm.mgms1Name"
                      style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="停机发生时间"
                        prop="beginTime">
            <el-date-picker v-model="ruleForm.beginTime"
                            style="width: 240px"
                            type="datetime"
                            placeholder="选择日期时间"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="resetForm">取 消</el-button>
          <el-button type="primary"
                     @click="submitForm">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--   点击编辑时的弹窗 -->
    <div v-if="editOpen">
      <el-dialog :title="editTitle"
                 class="customDialogStyle"
                 :visible.sync="editOpen"
                 width="1000px"
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <el-form :model="editForm"
                 :rules="editRules"
                 :inline="true"
                 ref="editForm"
                 label-width="150px"
                 class="demo-form-inline">
          <el-form-item label="产线名称"
                        prop="mgma1Name">
            <el-input v-model="editForm.mgma1Name"
                      disabled
                      style="width: 206px;" />
          </el-form-item>
          <el-form-item label="单位班别"
                        prop="deptclassMgms1No">
            <el-select v-model="editForm.deptclassMgms1No"
                       disabled
                       style="width: 206px !important">
              <el-option v-for="dict in dict.type.sys_classtype"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="停机记录编号"
                        prop="mgms1No">
            <el-input v-model="editForm.mgms1No"
                      disabled
                      style="width: 206px;" />
          </el-form-item>
          <el-form-item label="记录来源"
                        prop="mgms1Type">
            <el-select v-model="editForm.mgms1Type"
                       placeholder="记录来源"
                       disabled
                       style="width: 206px !important">
              <el-option v-for="dict in dict.type.equipment_record_source"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="异常现象"
                        prop="mgms1Name">
            <el-input type="textarea"
                      disabled
                      v-model="editForm.mgms1Name"
                      style="width: 206px;" />
          </el-form-item>
          <el-form-item label="主管单位"
                        prop="respDeptNo">
            <el-popover ref="menuListPopover"
                        placement="bottom-start"
                        trigger="click">
              <el-tree style="height: 200px;overflow: scroll;"
                       :data="sortOptions"
                       :props="menuListTreeProps"
                       node-key="id"
                       ref="menuListTree"
                       @current-change="menuListTreeCurrentChangeHandle"
                       :default-expand-all="false"
                       :highlight-current="true"
                       :expand-on-click-node="false">
              </el-tree>
            </el-popover>
            <el-input v-model="editForm.respDeptName"
                      v-popover:menuListPopover
                      :readonly="true"
                      placeholder="点击选择上级菜单"
                      class="menu-list__input"
                      style="width: 206px"></el-input>
          </el-form-item>
          <el-form-item label="停机主类型"
                        prop="mgmc1Name">
            <el-select v-model="editForm.mgmc1Name"
                       placeholder="请选择"
                       @change="editformMgmc1Selection"
                       style="width: 206px;">
              <el-option v-for="item in mainList"
                         :key="item.value"
                         :label="item.label"
                         :value="{ value: item.value, label: item.label }">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="停机发生时间"
                        prop="beginTime">
            <el-date-picker disabled
                            v-model="editForm.beginTime"
                            style="width: 206px"
                            type="datetime"
                            placeholder="选择日期时间"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="停机总时间"
                        prop="totalTime">
            <el-input v-model="editForm.totalTime"
                      disabled
                      style="width: 206px;" />
          </el-form-item>
          <el-form-item label="恢复运行时间"
                        prop="modifyDate">
            <el-date-picker @change="changeModifyDate"
                            v-model="editForm.modifyDate"
                            style="width: 206px"
                            type="datetime"
                            placeholder="选择日期时间"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            format="yyyy-MM-dd HH:mm:ss"
                            :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="停机细类型"
                        prop="mgmc2Name">
            <el-select v-model="editForm.mgmc2Name"
                       placeholder="请选择"
                       @change="mgmc2noselection"
                       style="width: 206px;">
              <el-option v-for="item in mgmc2List"
                         :key="item.value"
                         :label="item.label"
                         :value="{ value: item.value, label: item.label }">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="停机原因"
                        prop="reasonNo">
            <el-select v-model="editForm.reasonNo"
                       placeholder="请选择"
                       @change="reasonnoselection"
                       style="width: 206px;">
              <el-option v-for="item in resonNoList"
                         :key="item.value"
                         :label="item.label"
                         :value="{ value: item.value, label: item.label }">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="editCancel">取 消</el-button>
          <el-button type="primary"
                     @click="editConfirm">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--   高级筛选抽屉 -->
    <el-drawer class="el-icon-info"
               title="高级筛选"
               :append-to-body="true"
               :before-close="handleClose"
               :visible.sync="drawer"
               direction="rtl"
               custom-class="demo-drawer"
               :wrapperClosable="false">
      <div class="demo-drawer__content">
        <el-form :model="queryParams"
                 ref="queryForm">
          <el-row>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">产线:</label>
                  <el-select v-model="queryParams.mgma1Name"
                             placeholder="请选择"
                             style="width: 160px !important"
                             @change="selectChange">
                    <el-option v-for="item in lineList"
                               :key="item.value"
                               :label="item.label"
                               :value="{ value: item.value, label: item.label }">
                    </el-option>
                  </el-select>
                </div>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">班别:</label>
                  <el-select v-model="queryParams.deptclassMgms1No"
                             placeholder="班别"
                             clearable
                             style="width: 160px !important">
                    <el-option v-for="dict in dict.type.sys_classtype"
                               :key="dict.value"
                               :label="dict.label"
                               :value="dict.value" />
                  </el-select>
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">停机记录编号:</label>
                  <el-input v-model="queryParams.mgms1No"
                            type="text"
                            clearable
                            placeholder="停机记录编号"
                            style="width: 160px" />
                </div>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">停机记录状态:</label>
                  <el-select v-model="queryParams.mgms1Stus"
                             placeholder="停机记录状态"
                             clearable
                             style="width: 160px !important">
                    <el-option v-for="dict in dict.type.equipment_record_state"
                               :key="dict.value"
                               :label="dict.label"
                               :value="dict.value" />
                  </el-select>
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">停机主要类型:</label>
                  <el-select v-model="queryParams.mgmc1Name"
                             placeholder="请选择"
                             clearable
                             style="width: 420px !important"
                             @change="selectChangetype">
                    <el-option v-for="item in mainList"
                               :key="item.value"
                               :label="item.label"
                               :value="{ value: item.value, label: item.label }">
                    </el-option>
                  </el-select>
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">停机发生时间:</label>
                  <el-date-picker v-model="queryParams.datetimeRange"
                                  type="datetimerange"
                                  start-placeholder="开始日期"
                                  end-placeholder="结束日期"
                                  :default-time="['12:00:00']"
                                  @change="dateTimeChange"
                                  value-format="yyyy-MM-dd HH:mm:ss"
                                  format="yyyy-MM-dd HH:mm:ss"
                                  style="width: 350px !important;" />
                </div>
              </div>
            </el-col>
          </el-row>
        </el-form>
        <div class="demo-drawer__footer"
             style="text-align: center">
          <el-button v-hasPermi="['mgms1_query']"
                     size="medium"
                     icon="el-icon-search"
                     type="primary"
                     @click="handleQuery">搜 索</el-button>
          <el-button size="medium"
                     type="default"
                     @click="handleReset"
                     icon="el-icon-refresh-left">重 置</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { queryInfo, addInfo, updateInfo, initSelect, queryEditForm, getChildrenMgmc1Name, getChildrenReasonNo, updateEditForm } from "@/api/equip/MG/putonfile";
import { getOrganizationDeptTree } from "@/api/human/HM/humanResourceTree"
export default {
  name: "putonfile",
  dicts: ["equipment_record_source", "sys_classtype", "equipment_record_state"],
  components: {},
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      menuListTreeProps: {
        label: 'label',
        children: 'children'
      },
      // 编辑标题
      editTitle: '编辑手动立案',
      // 产线名称
      lineList: [],
      // 停机类型
      mainList: [],
      // 停机细类型
      mgmc2List: [],
      // 停机原因
      resonNoList: [],
      // 部门树选项
      sortOptions: [],
      // 总页数
      total: 0,
      // 是否显示抽屉
      drawer: false,
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      // 编辑弹出
      editOpen: false,
      // 表格数据
      tableData: [],
      //提交状态
      states: false,
      //恢复运行时间小于停机时间
      pickerOptions: {},
      // 表格字段
      columns: [
        { label: "产线", prop: "mgma1Name", sortable: true, align: "center", fixed: false, },
        { label: "停机记录编号", prop: "mgms1No", sortable: true, align: "center", fixed: false, show: true },
        { label: "停机主类型", prop: "mgmc1Name", sortable: true, align: "center", fixed: false, },
        { label: "停机发生时间", prop: "beginTime", sortable: true, align: "center", fixed: false, },
        { label: "停机总时间/分钟", prop: "totalTime", sortable: true, align: "center", fixed: false, },
        { label: "提交时间", prop: "createTime", sortable: true, align: "center", fixed: false, },
      ],
      // 查询参数
      queryParams: {
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 10,
        // 日期区间
        datetimeRange: [],
        //停机记录编号
        mgms1No: undefined,
        //班别
        deptclassMgms1No: undefined,
        //停机记录状态
        mgms1Stus: undefined,
        //产线姓名
        mgma1Name: undefined,
        //停机主类型
        mgmc1No: undefined,
        //停机主类型名称
        mgmc1Name: undefined,
      },
      // 编辑手动立案
      editForm: {
        mgma1Name: undefined,
        deptclassMgms1No: undefined,
        mgms1No: undefined,
        mgms1Type: "1",
        mgms1Name: undefined,
        respDeptNo: undefined,
        mgmc1Name: undefined,
        beginTime: undefined,
        totalTime: undefined,
        modifyDate: undefined,
        mgmc2Name: undefined,
        mgms1Stus: "1",
      },
      //标题栏
      title: "手动立案",
      // 新增参数
      ruleForm: {
        //产线编号
        mgma1No: "",
        //产线姓名
        mgma1Name: "",
        //单位班别
        deptclassMgms1No: "",
        //停机主类型
        mgmc1No: "",
        //停机类型名称
        mgmc1Name: "",
        //记录来源
        mgms1Type: "1",
        //异常现象
        mgms1Name: "",
        //停机发生时间
        beginTime: "",
        //停机记录状态
        mgms1Stus: "2",
      },
      // 表单校验
      rules: {
        mgma1Name: [
          { required: true, message: "产线名称不能为空", trigger: "change" },
        ],
        deptclassMgms1No: [
          { required: true, message: "班别不能为空", trigger: "change" },
        ],
        mgmc1Name: [
          { required: true, message: "停机类型名称不能为空", trigger: "change" },
        ],
        mgms1Name: [
          { required: true, message: "异常现象不能为空", trigger: "blur" },
          { max: 100, message: '异常现象最长为100 个字符', trigger: 'blur' }
        ],
        beginTime: [
          { required: true, message: "停机发生时间不能为空", trigger: "blur" },
        ],
      },
      // 编辑时表单校验
      editRules: {
        mgmc1Name: [
          { required: true, message: "停机主类型不能为空", trigger: "change" },
        ],
        mgmc2Name: [
          { required: true, message: "停机细类型不能为空", trigger: "change" },
        ],
        reasonNo: [
          { required: true, message: "停机原因不能为空", trigger: "change" },
        ],
        modifyDate: [
          { required: true, message: "恢复运行时间不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created () {
    this.handleQuery();
    this.initSelect();
  },
  methods: {
    /**
     * 树节点选择事件
     */
    menuListTreeCurrentChangeHandle (data) {
      this.editForm.respDeptNo = data.id
      this.editForm.respDeptName = data.label
      this.$refs.menuListPopover.doClose()
    },
    // editForm停机原因切换
    reasonnoselection (optionValue) {
      this.editForm.reasonNo = optionValue.label
    },
    // editForm停机细类型切换
    mgmc2noselection (optionValue) {
      //细类型名称
      this.editForm.mgmc2Name = optionValue.label
      //细类型编号
      this.editForm.mgmc2No = optionValue.value
      //停机原因编码
      this.editForm.reasonNo = ''
      getChildrenReasonNo(optionValue.value).then(response => {
        this.resonNoList = response.data
      })
    },
    // editForm 停机主类型切换
    editformMgmc1Selection (optionValue) {
      //主类型名称
      this.editForm.mgmc1Name = optionValue.label
      //主类型编号
      this.editForm.mgmc1No = optionValue.value
      //停机原因编码
      this.editForm.reasonNo = ''
      //细类型名称
      this.editForm.mgmc2Name = ''
      //细类型编码
      this.editForm.mgmc2No = ''
      getChildrenMgmc1Name(optionValue.value).then(response => {
        this.mgmc2List = response.data
      })
    },
    // 计算停机总时间
    getDifferTime (startDate, endDate) {
      const startTime = new Date(Date.parse(startDate)).getTime();
      const endTime = new Date(Date.parse(endDate)).getTime();
      const calculateTime = Math.abs((startTime - endTime) / (1000 * 60)).toFixed(0)
      return calculateTime;
    },
    // 更改恢复运行时间
    changeModifyDate (value) {
      const calculateTime = this.getDifferTime(value, this.editForm.beginTime)
      this.editForm.totalTime = calculateTime
    },
    // 编辑
    handleEdit (row) {
      this.editTitle = '重启停机立案'
      // 获取组织机构数结构
      getOrganizationDeptTree().then(response => {
        this.sortOptions = response.data
      })
      queryEditForm(row.id).then(response => {
        this.editForm = response.data
        this.editForm.mgms1Stus = "1"
        // 查询停机细类型
        getChildrenMgmc1Name(this.editForm.mgmc1No).then(response => {
          this.mgmc2List = response.data
        })
        // 查询停机原因
        getChildrenReasonNo(this.editForm.mgmc2No).then(response => {
          this.resonNoList = response.data
        })
      })
      this.editOpen = true
    },
    // 修改数据
    handleUpdate (row) {
      this.title = '编辑停机立案'
      queryEditForm(row.id).then(response => {
        const data = response.data
        this.ruleForm.id = data.id
        this.ruleForm.mgma1Name = data.mgma1Name
        this.ruleForm.deptclassMgms1No = data.deptclassMgms1No
        this.ruleForm.mgmc1Name = data.mgmc1Name
        this.ruleForm.mgms1Name = data.mgms1Name
        this.ruleForm.beginTime = data.beginTime
        this.ruleForm.mgma1No = data.mgma1No
      })
      this.open = true
    },
    // 产线select
    selectChange (value) {
      this.queryParams.mgma1Name = value.label;
    },
    // 停机主要类型
    selectChangetype (value) {
      this.queryParams.mgmc1Name = value.label;
    },
    //查询初始化
    initSelect () {
      initSelect().then(response => {
        this.lineList = response.data.lineList;
        this.mainList = response.data.mainList;

      });
    },
    // 高级筛选关闭
    handleClose (done) {
      this.queryParams = {};
      done();
    },
    // 高级筛选弹出
    handleAdvanceFilter () {
      this.drawer = true;
    },
    // 重置
    handleReset () {
      this.resetForm("queryForm")
      this.handleQuery();
    },
    // 手动立案
    handlePutonFile () {
      this.open = true;
      this.ruleForm = {
        //记录来源
        mgms1Type: "1",
        //停机记录状态
        mgms1Stus: "2",
      }
    },
    // 导出
    handleExport () {
    },
    // 选择日期区间
    dateTimeChange (value) {
      if (value === null) {
        this.datetimeRange = []
      } else {
        this.datetimeRange = value
      }
    },
    //手动立案提交
    submitForm () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.states = true
          if (this.ruleForm.id != undefined) {
            updateInfo(this.ruleForm).then(response => {
              this.states = false
              this.$modal.msgSuccess("修改成功")
              this.handleQuery();
              this.resetForm();
            })
          } else {
            addInfo(this.ruleForm).then((res) => {
              this.states = false
              this.$modal.msgSuccess("添加成功")
              this.handleQuery();
              this.resetForm();
            });
          }
        } else {
          this.$modal.msgWarning("请完善信息");
          return false;
        }
      });
    },
    // 表单重置
    resetForm () {
      this.ruleForm = {
        //产线编号
        mgma1No: "",
        //产线姓名
        mgma1Name: "",
        //单位班别
        deptclassMgms1No: "",
        //停机主类型
        mgmc1No: "",
        //停机类型名称
        mgmc1Name: "",
        //记录来源
        mgms1Type: "1",
        //异常现象
        mgms1Name: "",
        //停机发生时间
        beginTime: "",
        //停机记录状态
        mgms1Stus: "2",
      }
      this.open = false;
    },
    //查询
    handleQuery () {
      queryInfo(this.queryParams).then((res) => {
        this.tableData = res.data.list;
        this.total = res.data.total;
        this.drawer = false;
      });
    },
    //停机主类型
    mgmc1noselection (val) {
      this.ruleForm.mgmc1Name = val.label;
      this.ruleForm.mgmc1No = val.value;
    },
    //产线名称
    mgms1nameselection (val) {
      this.ruleForm.mgma1Name = val.label;
      this.ruleForm.mgma1No = val.value;
    },
    // 点击责任分摊跳转
    handleCheckView (row) {
      if (row.totalTime > 0) {
        this.$router.push({ path: '/equip/achieve/responsibilitySharing/index/' + row.mgms1No });
      }
    },
    // 取消编辑手动立案
    editCancel () {
      this.editOpen = false
      this.editForm = {}
    },
    // 编辑手动立案确定
    editConfirm () {
      this.states = true
      this.$refs.editForm.validate(valid => {
        if (valid) {
          updateEditForm(this.editForm).then(response => {
            this.states = false
            this.$modal.msgSuccess("保存成功")
            this.editOpen = false
            this.handleQuery()
          })
        } else {
          this.$modal.msgWarning("请完善信息");
          this.states = false
        }
      })
    }
  },
};
</script>

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
