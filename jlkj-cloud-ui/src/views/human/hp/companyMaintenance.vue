<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-select  v-model="treestatus" placeholder="请选择公司状态" size="small">
            <el-option
              v-for="dict in baseInfoData.dept_status"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            />
          </el-select>
        </div>
        <div class="head-container treeDept" v-show="treeandtable">
          <el-scrollbar class="treeScrollbar">
            <el-tree
              :data="deptOptions"
              :props="defaultProps"
              :highlight-current="true"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              :default-expanded-keys="expandedKeys"
              node-key="id"
              ref="tree"
              @node-click="handleNodeClick"
            />
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px"
        >
          <!--      <el-form-item label="机构id值" prop="deptId">-->
          <!--        <el-input maxlength="20"-->
          <!--          type="number"-->
          <!--          v-model="queryParams.deptId"-->
          <!--          placeholder="请输入机构id"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <el-form-item label="公司编码" prop="deptCode">
            <el-input maxlength="8"
                      v-model="queryParams.deptCode"
                      clearable
                      placeholder="请输入公司编码"
            />
          </el-form-item>
          <el-form-item label="公司名称" prop="deptName">
            <el-input maxlength="200"
                      v-model="queryParams.deptName"
                      clearable
                      placeholder="请输入公司名称"
            />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery(0)">搜索</el-button>
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
              v-hasPermi="['human:deptMaintenance:add']"
            >新增
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="deptmaintenanceList" @selection-change="handleSelectionChange"
                  height="67vh" v-show="treeandtable"
        >
          <el-table-column label="机构编码" align="center" width="120" prop="deptCode"/>
          <el-table-column label="机构名称" align="center" width="300" prop="deptName"/>
          <el-table-column label="排序序号" align="center" prop="orderNum"/>
          <el-table-column label="输入人" align="center" prop="updateBy" :formatter="userFormat"/>
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-info"
                @click="handleView(scope.row)"
              >详情
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:deptMaintenance:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:deptMaintenance:remove']"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="(total>0)&&treeandtable"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
    <!-- 添加或修改部门资料维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false" class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司编码" prop="deptCode">
              <el-input maxlength="8" v-model="form.deptCode" placeholder="请输入公司编码" :disabled="this.form.deptId!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司名称" prop="deptName">
              <el-input maxlength="20" v-model="form.deptName" placeholder="请输入公司名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司简称" prop="companyName">
              <el-input maxlength="20" v-model="form.companyName" placeholder="请输入公司简称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属板块类别" prop="boardId">
              <el-select :popper-append-to-body="false" v-model="form.boardId" placeholder="请选择板块类别" clearable
                         size="small" class="maxWidth"
              >
                <el-option
                  v-for="dict in baseInfoData.HP001"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="当前状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级机构" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :show-count="true" placeholder="请选择上级机构"
                          :normalizer="normalizer" @select="deptChange"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序序号" prop="orderNum">
              <el-input maxlength="20" v-model.number="form.orderNum" type="number"  placeholder="请输入排序序号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组织树是否显示" prop="ifDisplay">
              <el-radio-group v-model="form.ifDisplay">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="变更原因" prop="changeReason" v-if="ifupdate">
          <el-input maxlength="300" v-model="form.changeReason" type="textarea" show-word-limit placeholder="请输入变更原因"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable size="small"
                              class="maxWidth"
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人员" prop="updateBy">
              {{ userFormat(form,1) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="updateTime">
              {{ form.updateTime }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel(1)">取 消</el-button>
      </div>
      <div slot="footer" v-show="ifupdate">
        <el-divider content-position="center">变更记录</el-divider>
        <el-table slot="footer" :data="deptversionlist" ref="deptversion" v-show="ifupdate">
          <el-table-column label="版本号" prop="versionNo" align="center" width="60px" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.versionNo }}</span>
            </template>
          </el-table-column>
          <el-table-column label="公司名称" prop="deptName" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.deptName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="该公司上级" prop="parentName" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.parentName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更原因" prop="changeReason" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.changeReason }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更人" prop="updateBy" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ userFormat(scope.row) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更日期" prop="updateTime" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.updateTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                @click="getVersion(scope.row)"
                v-hasPermi="['human:deptMaintenance:list']"
              >版本详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>

    <!--数据详情界面-->
    <el-dialog :title="title" :visible.sync="openDetail" width="900px" append-to-body :close-on-click-modal="false" class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司编码" prop="deptCode">
              {{ form.deptCode }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司名称" prop="deptName">
              {{ form.deptName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司简称" prop="companyName">
              {{ form.companyName }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属板块类别" prop="boardId">
              <dict-tag-human :options="baseInfoData.HP001" :value="form.boardId"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="当前状态" prop="status">
              <dict-tag :options="dict.type.sys_normal_disable" :value="form.status"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级机构" prop="parentName">
              {{ form.parentName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序序号" prop="orderNum">
              {{ form.orderNum }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组织树是否显示" prop="ifDisplay">
              <dict-tag :options="dict.type.sys_yes_no" :value="form.ifDisplay"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="变更原因" prop="changeReason" v-if="ifupdate">
          {{ form.changeReason }}
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectDate">
              {{ form.effectDate }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人员" prop="updateBy">
              {{ userFormat(form,1) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="updateTime">
              {{ form.updateTime }}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel(2)">返 回</el-button>
      </div>
      <div slot="footer" v-show="ifupdate">
        <el-divider content-position="center">变更记录</el-divider>
        <el-table slot="footer" :data="deptversionlist" ref="deptversion" v-show="ifupdate">
          <el-table-column label="版本号" prop="versionNo" align="center" width="60px" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.versionNo }}</span>
            </template>
          </el-table-column>
          <el-table-column label="公司名称" prop="deptName" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.deptName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="该公司上级" prop="parentName" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.parentName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更原因" prop="changeReason" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.changeReason }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更人" prop="updateBy" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ userFormat(scope.row) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更日期" prop="updateTime" align="center" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ scope.row.updateTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                @click="getVersion(scope.row)"
                v-hasPermi="['human:deptMaintenance:list']"
              >版本详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
    <!--    历史版本详情界面-->
    <el-dialog title="历史版本详情" :visible.sync="versionDetail" width="900px" append-to-body :close-on-click-modal="false" class="customDialogStyle">
      <el-form ref="versionForm" :model="versionForm" label-width="130px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司编码" prop="deptCode">
              {{ versionForm.deptCode }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司名称" prop="deptName">
              {{ versionForm.deptName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司简称" prop="companyName">
              {{ versionForm.companyName }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属板块类别" prop="boardId">
              <dict-tag-human :options="baseInfoData.HP001" :value="versionForm.boardId"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="当前状态" prop="status">
              <dict-tag :options="dict.type.sys_normal_disable" :value="versionForm.status"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级机构" prop="parentName">
              {{ versionForm.parentName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序序号" prop="orderNum">
              {{ versionForm.orderNum }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组织树是否显示" prop="ifDisplay">
              <dict-tag :options="dict.type.sys_yes_no" :value="versionForm.ifDisplay"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="变更原因" prop="changeReason" v-if="ifupdate">
          {{ versionForm.changeReason }}
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectDate">
              {{ versionForm.effectDate }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人员" prop="updateBy">
              {{ userFormat(versionForm,1) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="updateTime">
              {{ versionForm.updateTime }}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel(3)">返 回</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { getBaseInfo } from '@/api/human/hm/baseInfo'
import { queryAllUser } from "@/api/system/user"
import {
  listDeptmaintenance,
  getDeptmaintenance,
  delDeptmaintenance,
  addDeptmaintenance,
  updateDeptmaintenance,
  treeselect,
  listDeptversion,
  getVersion,
} from '@/api/human/hp/deptMaintenance'
import Treeselect from '@riophae/vue-treeselect'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { checkSpecialKey } from '@/utils/jlkj'

export default {
  name: 'Deptmaintenance',
  dicts: ['sys_normal_disable', 'sys_yes_no'],
  components: { Treeselect, DictTagHuman },
  data() {
    return {
      //用户字典
      resUserDiction: [],
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'dept_status',
          'HP001',
          'if_display'
        ]
      },
      //选单数据
      baseInfoData: [],
      //登录人信息
      user: {
        empNo: null,
        empName: null,
        compId: null
      },
      // 公司状态
      treestatus: '2',
      //展开列表
      expandedKeys: [],
      // 部门树选项
      deptOptions: [],
      //全部门树
      alldeptOptions: undefined,
      //是否展示树和表
      treeandtable: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      deptCodes: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 部门资料维护表格数据
      deptmaintenanceList: [],
      //部门维护历史版本
      deptversionlist: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      openDetail: false,
      versionDetail: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        compId: null,
        ifCompany: 1,
        deptCode: null,
        deptName: null
      },
      //历史版本查询参数
      queryParams2: {
        deptId: null
      },
      //treeselect查询参数
      queryParams3: {
        ifCompany: 1
      },
      currentNodeId: '',
      // 表单参数
      form: {},
      versionForm: {},
      //下拉选单数据配置
      defaultProps: {
        children: 'children',
        label: 'label7',
        ancestors: 'ancestors'
      },
      //是否修改
      ifupdate: false,
      // 表单校验
      rules: {
        deptCode: [
          { required: true, message: '请输入机构编码', trigger: 'blur' },
          { required: true, validator: checkSpecialKey, trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '请输入机构名称', trigger: 'blur' },
          { required: true, validator: checkSpecialKey, trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '请选择该机构上级', trigger: 'blur' },
          { pattern: /^100$/, message: '请选择集团为机构上级', trigger: 'blur' }
        ],
        orgTierId: [
          { required: true, message: '请选择机构层级', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择当前状态', trigger: 'change' }
        ],
        leader: [
          { required: true, message: '请输入法人', trigger: 'blur' }
        ],
        ifDisplay: [
          { required: true, message: '请选择组织树是否显示', trigger: 'change' }
        ],
        boardId: [
          { required: true, message: '请选择板块类别', trigger: 'change' }
        ],
        effectDate: [
          { required: true, message: '生效日期不能为空', trigger: 'change' }
        ],
        changeReason: [
          { required: true, message: '请输入变更原因', trigger: 'blur' }
        ],
        phone: [
          {
            pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
            message: '手机号格式不正确',
            trigger: 'blur'
          }
        ],
        email: [
          {
            pattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
            message: '邮箱格式不正确',
            trigger: 'blur'
          }
        ],
        fax: [
          { pattern: /^(?:\d{3,4}-)?\d{7,8}(?:-\d{1,6})?$/, message: '传真格式不正确', trigger: 'blur' }
        ],
        orderNum:[
          { type: 'number',min:0,max:10000, message: '必须在0到10000之间', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    // 根据名称筛选部门树
    treestatus(val) {
      this.$refs.tree.filter(val)
      this.queryParams.compId = val
      this.queryParams.deptId = null
      if (val) {
        this.treeandtable = true
      } else {
        this.treeandtable = false
      }
    }
  },
  created() {
    this.initData()
    this.getHumandisc()
    this.getTreeselect()
    this.treeandtable = true
  },
  beforeMount() {
    /** 装载人员信息 */
    queryAllUser().then(response => {
      this.resUserDiction = response.rows;
    })
  },
  methods: {
    // 人员字典翻译
    userFormat(row, column){
      let showBy = (row.updateBy)? 'updateBy' : 'createBy'
      if(column){
        /** UserDictFullName 显示工号_姓名 */
        return this.UserDictFullName(this.resUserDiction, row[showBy]);
      }else{
        /** UserDictOnlyName 只显示姓名 */
        return this.UserDictOnlyName(this.resUserDiction, row[showBy]);
      }
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
    },
    //表单数据配置
    setForm(e) {
      if (e === 0) {
        this.form.createBy = this.user.empNo
        this.form.createTime = getDateTime(0)
        this.form.versionNo = 1
        this.form.ifCompany = 1
        this.form.status = '0'
        this.form.ifDisplay = 'Y'
        this.form.effectDate = getDateTime(1)
      } else if (e === 1) {
        this.form.versionNo++
      }
      this.form.isNew = 1
      this.form.updateBy = this.user.empNo
      this.form.updateTime = getDateTime(0)
    },
    //获取人事选单字典
    getHumandisc() {
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data
      })
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect(this.queryParams3).then(response => {
        this.deptOptions = response.data
        this.expandedKeys.push(response.data[0].id);
        this.queryParams.deptId = this.expandedKeys[0];
        this.getList()
      })
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      return {
        id: node.id,
        label: node.label,
        label2: node.label2,
        children: node.children
      }
    },
    /** 点选获取人员信息 */
    getJobNumber(val) {
      this.form.leader = val[0].nickName
      if (val[0].phonenumber != null) {
        this.form.phone = val[0].phonenumber
        this.form.email = val[0].email
      }
    },
    /** 上级部门切换事件 */
    deptChange(val) {
      this.form.parentName = val.label
      this.form.parentId = val.id
    },
    // 筛选节点
    filterNode(value, data) {
      return (data.label4 <= value) && (data.label3 == 1)
    },
    //折叠节点
    collapseAll(data) {
      let self = this
      data.forEach((el) => {
        self.$refs.tree.store.nodesMap[el.id].expanded = false
        el.children && el.children.length > 0
          ? self.collapseAll(el.children)
          : '' // 子级递归
      })
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id
      this.queryParams.compId = null
      this.handleQuery()
    },
    /** 查询部门资料维护列表 */
    getList() {
      this.loading = true
      listDeptmaintenance(this.queryParams).then(response => {
        this.deptmaintenanceList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel(e) {
      switch (e) {
        case 1:
          this.open = false
          this.$nextTick(()=>{
            this.reset()
          })
          break
        case 2:
          this.openDetail = false
          this.$nextTick(()=>{
            this.reset()
          })
          break
        case 3:
          this.versionDetail = false
          break
      }
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: null,
        deptCode: null,
        deptName: null,
        boardId: null,
        parentId: null,
        parentName: null,
        parentFullName: null,
        ancestors: null,
        orgTierId: null,
        orderNum: null,
        leader: null,
        email: null,
        delFlag: null,
        costCenterId: null,
        fax: null,
        phone: null,
        changeReason: null,
        effectDate: null,
        versionNo: null,
        ifCompany: null,
        compId: null,
        ifDisplay: null,
        createBy: null,
        status: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isNew: null,
        companyName: null
      }
      this.resetForm('form')
    },

    // 历史表单重置
    resetVersion() {
      this.versionForm = {
        id: null,
        deptId: null,
        deptCode: null,
        deptName: null,
        boardId: null,
        parentId: null,
        parentName: null,
        parentFullName: null,
        ancestors: null,
        orgTierId: null,
        orderNum: null,
        leader: null,
        email: null,
        delFlag: null,
        costCenterId: null,
        fax: null,
        phone: null,
        changeReason: null,
        effectDate: null,
        versionNo: null,
        ifCompany: null,
        compId: null,
        ifDisplay: null,
        createBy: null,
        status: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isNew: null,
        companyName: null
      }
      this.resetForm('versionForm')
    },
    /** 搜索按钮操作 */
    handleQuery(e) {
      this.queryParams.pageNum = 1
      if (e === 0) {
        this.queryParams.deptId = '100'
      }
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.treestatus = '2';
      this.queryParams.deptId = this.expandedKeys[0];
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.deptId)
      this.deptCodes = selection.map(item => item.deptCode)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.ifupdate = false
      this.reset()
      this.setForm(0)
      this.open = true
      this.title = '添加公司资料维护'
    },
    /** 详情按钮操作 */
    handleView(row) {
      this.ifupdate = true
      this.reset()
      let deptId = row.deptId || this.ids
      getDeptmaintenance(deptId).then(response => {
        this.form = response.data
        this.openDetail = true
        this.title = '公司资料详情'
      })
      this.queryParams2.deptId = deptId
      listDeptversion(this.queryParams2).then(response => {
        this.deptversionlist = response.rows
      })
    },
    //查询历史版本详情
    getVersion(row) {
      this.resetVersion()
      getVersion(row.id).then(response => {
        this.versionForm = response.data
        this.versionDetail = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.ifupdate = true
      this.reset()
      let deptId = row.deptId || this.ids
      getDeptmaintenance(deptId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改公司资料维护'
      })
      this.queryParams2.deptId = deptId
      listDeptversion(this.queryParams2).then(response => {
        this.deptversionlist = response.rows
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.compId = this.form.deptCode
          if (this.form.deptId != null) {
            this.setForm(1)
            updateDeptmaintenance(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getTreeselect()
            })
          } else {
            addDeptmaintenance(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getTreeselect()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const deptIds = row.deptId || this.ids
      const deptCodes = row.deptCode || this.deptCodes
      this.$modal.confirm('是否确认删除公司编码为"' + deptCodes + '"的数据项？').then(function() {
        return delDeptmaintenance(deptIds)
      }).then(() => {
        this.getTreeselect()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
  }
}
</script>
<style scoped>
.treeDept {
  height: 81vh;
  width: 100%;
}

.treeScrollbar {
  height: 100%;
}

.maxWidth {
  width: 100%;
}

</style>
