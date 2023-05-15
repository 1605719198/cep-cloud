<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别"  size="small" ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="人员类别" prop="titleKind">
        <el-select v-model="queryParams.titleKind" placeholder="请选择人员类别" clearable>
          <el-option
            v-for="dict in baseInfoData.people_hierarchy"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="国别" prop="nation">
        <el-select v-model="queryParams.nation" placeholder="请选择国别" clearable>
          <el-option
            v-for="dict in attendenceOptions.Country"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="城市" prop="area">
        <el-select v-model="queryParams.area" placeholder="请选择城市" clearable>
          <el-option
            v-for="dict in cityList"
            :key="dict.id"
            :label="dict.area"
            :value="dict.area"
          />
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
          v-hasPermi="['human:tripDayRule:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['human:tripDayRule:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tripDayRuleList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column label="人员类别" align="center" prop="titleKind">
        <template v-slot="scope">
          <dict-tag-human :options="baseInfoData.people_hierarchy" :value="scope.row.titleKind"/>
        </template>
      </el-table-column>
      <el-table-column label="国别" align="center" prop="nation">
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.Country" :value="scope.row.nation"/>
        </template>
      </el-table-column>
      <el-table-column label="城市" align="center" prop="area"/>
      <el-table-column label="住宿费" align="center" prop="lodgAllow"/>
      <el-table-column label="交通费" align="center" prop="trafficAllow"/>
      <el-table-column label="伙食费" align="center" prop="foodAllow"/>
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="是否国内" align="center" prop="isInternal">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isInternal"/>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="creator"/>
      <el-table-column label="操作日期" align="center" prop="createDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:tripDayRule:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:tripDayRule:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改出差日标准维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="compName">
              {{this.form.compName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="人员类别" prop="titleKind">
              <el-select v-model="form.titleKind" placeholder="请选择人员类别" clearable>
                <el-option
                  v-for="dict in baseInfoData.people_hierarchy"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="国别" prop="nation">
              <el-select v-model="form.nation" placeholder="请选择国别" clearable>
                <el-option
                  v-for="dict in attendenceOptions.Country"
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
            <el-form-item label="城市" prop="area">
              <el-input v-model="form.area" placeholder="请输入城市" maxlength="10"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否国内" prop="isInternal">
              <el-radio-group v-model="form.isInternal">
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

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="住宿费" prop="lodgAllow">
              <el-input v-model="form.lodgAllow" placeholder="请输入住宿费" type="number"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交通费" prop="trafficAllow">
              <el-input v-model="form.trafficAllow" placeholder="请输入交通费" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="伙食费" prop="foodAllow">
              <el-input v-model="form.foodAllow" placeholder="请输入伙食费" type="number"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.id!=null">
            <el-form-item label="状态" prop="status">
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
        </el-row>

        <el-row :gutter="20" v-if="this.form.id!=null">
          <el-col :span="12">
            <el-form-item label="操作日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作人" prop="creator">
              {{form.creatorId}}-{{form.creator}}
            </el-form-item>
          </el-col>
        </el-row>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
import {
  listTripDayRule,
  getTripDayRule,
  delTripDayRule,
  addTripDayRule,
  updateTripDayRule,
  listTripDayArea,
} from '@/api/human/hd/tripDayRule'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getBaseInfo } from '@/api/human/hm/baseInfo'
import { getDateTime } from "@/api/human/hd/ahumanUtils"
export default {
  name: 'TripDayRule',
  dicts: ['sys_yes_no', 'sys_normal_disable'],
  components: { DictTagHuman },
  data() {
    return {
      // 遮罩层
      loading: true,
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
      //登录人信息
      user: {
        empNo: null,
        empName: null,
        compId: null
      },
      //公司数据
      companyList: [],
      // 出差日标准维护表格数据
      tripDayRuleList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['Country']
      },
      //出勤选单选项列表
      attendenceOptions: [],
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'people_hierarchy'
        ]
      },
      //选单数据
      baseInfoData: [],
      //出差城市列表
      cityList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        titleKind: null,
        nation: null,
        area: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId: [
          { required: true, message: '公司别不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.initData()
    this.getCompanyList()
    this.getDisc()
    this.getList()
  },
  methods: {
    //表单数据配置
    setForm(e){
      this.form.creator = this.user.empName;
      this.form.creatorId = this.user.empNo;
      this.form.compId = this.queryParams.compId;
      this.form.createDate = getDateTime(1);
      this.form.compName = this.$refs.selectCompany.selectedLabel;
      if(e===0){
        this.form.status = "0";
        this.form.isInternal = 'Y'
      }
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    //获取出勤字典
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data
      })
      listTripDayArea(this.queryParams).then(response => {
        this.cityList = response.rows;
      })
    },
    /** 查询出差日标准维护列表 */
    getList() {
      this.loading = true
      listTripDayRule(this.queryParams).then(response => {
        this.tripDayRuleList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        compName: null,
        titleKind: null,
        nation: null,
        area: null,
        lodgAllow: null,
        trafficAllow: null,
        foodAllow: null,
        status: null,
        isInternal: null,
        creator: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.setForm(0);
      this.open = true
      this.title = '添加出差日标准维护'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTripDayRule(id).then(response => {
        this.form = response.data
        this.setForm();
        this.open = true
        this.title = '修改出差日标准维护'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTripDayRule(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTripDayRule(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除出差日标准维护编号为"' + ids + '"的数据项？').then(function() {
        return delTripDayRule(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/tripDayRule/export', {
        ...this.queryParams
      }, `tripDayRule_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
