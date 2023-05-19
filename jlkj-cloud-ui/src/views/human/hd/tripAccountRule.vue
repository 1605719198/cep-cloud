<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select :popper-append-to-body="false" v-model="queryParams.compId" placeholder="请选择公司别" clearable ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="会计类别" prop="acctType">
        <el-select :popper-append-to-body="false" v-model="queryParams.acctType" placeholder="请选择会计类别" clearable>
          <el-option
            v-for="dict in attendenceOptions.AcctType"
            :key="dict.dicNo"
            :label="dict.dicNo+'-'+dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="费用类别" prop="apprGroup">
        <el-select :popper-append-to-body="false" v-model="queryParams.apprGroup" placeholder="请选择费用类别" clearable>
          <el-option
            v-for="dict in baseInfoData.HP007"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
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
          v-hasPermi="['human:tripAccountRule:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tripAccountRuleList" @selection-change="handleSelectionChange" :span-method="spanTable"  height="67vh">
      <el-table-column label="序号" align="center" prop="index" width="55"/>
      <el-table-column label="会计类别" align="center" prop="acctType">
        <template v-slot="scope">
          {{scope.row.acctType}}-{{scope.row.acctDesc}}
        </template>
      </el-table-column>
      <el-table-column label="类别分类" align="center" prop="acctClassify">
        <template v-slot="scope">
          <el-row>
             <dict-tag-human :options="attendenceOptions.AcctClassify" :value="scope.row.acctClassify"/>
          </el-row>
        </template>
      </el-table-column>

      <el-table-column label="借方会计科目" align="center" prop="acctCoded">
        <el-table-column label="贷方会计科目" align="center" prop="acctCode" width="130">
        </el-table-column>
      </el-table-column>

      <el-table-column label="借方会计中文名称" align="center" prop="acctNamed">
        <el-table-column label="贷方会计中文名称" align="center" prop="acctName" width="200">
        </el-table-column>
      </el-table-column>

      <el-table-column label="借方户号" align="center" prop="idCoded">
        <el-table-column label="贷方户号" align="center" prop="idCode">
        </el-table-column>
      </el-table-column>


      <el-table-column label="借方参号" align="center" prop="refNod">
        <el-table-column label="贷方参号" align="center" prop="refNo">
        </el-table-column>
      </el-table-column>

      <el-table-column label="费用类别" align="center" prop="apprGroup" >
        <template v-slot="scope">
            <dict-tag-human :options="baseInfoData.HP007" :value="scope.row.apprGroup"/>
        </template>
      </el-table-column>

      <el-table-column label="操作人" align="center" prop="creator">
        <el-table-column label="操作时间" align="center" prop="create" width="180">
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:tripAccountRule:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:tripAccountRule:remove']"
          >作废
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

    <!-- 添加或修改出差会计科目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="compId">
             <label>
               {{this.form.compName}}
             </label>
            </el-form-item>
          </el-col>
          <el-col :span="12">
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="会计类别" prop="acctType">
              <el-select :popper-append-to-body="false" v-model="form.acctType" placeholder="请选择会计类别"  ref="selectAcct" @change="changeAcct" >
                <el-option
                  v-for="dict in attendenceOptions.AcctType"
                  :key="dict.dicName"
                  :label="dict.dicNo+'-'+dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类别分类" prop="acctClassify">
              <el-select :popper-append-to-body="false" v-model="form.acctClassify" placeholder="请选择类别分类">
                <el-option
                  v-for="dict in attendenceOptions.AcctClassify"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="费用类别" prop="apprGroup">
              <el-select :popper-append-to-body="false" v-model="form.apprGroup" placeholder="请选择费用类别">
                <el-option
                  v-for="dict in baseInfoData.HP007"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="" prop="compName">
              <el-row :gutter="20">
                <el-col :span="6" align="center">
                  会计科目
                </el-col>
                <el-col :span="8" align="center">
                  中文名称
                </el-col>
                <el-col :span="4" align="center">
                  户号
                </el-col>
                <el-col :span="4" align="center">
                  参号
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="借方">
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-input v-model="form.acctCoded" placeholder="请选择" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
                  </el-input>
                </el-col>
                <el-col :span="8">
                  <el-input v-model="form.idNamed" disabled></el-input>
                </el-col>
                <el-col :span="4">
                  <el-input v-model="form.idCoded" disabled></el-input>
                </el-col>
                <el-col :span="4">
                  <el-input v-model="form.refNod" disabled></el-input>
                </el-col>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="贷方">
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-input v-model="form.acctCoded" placeholder="请选择" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
                  </el-input>
                </el-col>
                <el-col :span="8">
                  <el-input v-model="form.idNamed" disabled></el-input>
                </el-col>
                <el-col :span="4">
                  <el-input v-model="form.idCodec" disabled></el-input>
                </el-col>
                <el-col :span="4">
                  <el-input v-model="form.refNoc" disabled></el-input>
                </el-col>
              </el-row>
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
import {
  listTripAccountRule,
  getTripAccountRule,
  delTripAccountRule,
  addTripAccountRule,
  updateTripAccountRule
} from '@/api/human/hd/tripAccountRule'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import { getBaseInfo } from '@/api/human/hm/baseInfo'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'

export default {
  name: 'TripAccountRule',
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
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['AcctType', 'AcctClassify']
      },
      //出勤选单选项列表
      attendenceOptions: [],
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'HP007'
        ]
      },
      //选单数据
      baseInfoData: [],
      // 出差会计科目表格数据
      tripAccountRuleList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        acctType: null,
        apprGroup: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId:[
          { required:true, message:'公司别不能为空', trigger: "change"}
        ],
        acctType:[
          { required:true, message:'请选择会计类别', trigger:"change"}
        ],
        acctClassify:[
          { required:true, message:'请选择类别分类', trigger:"change"}
        ],
      }
    }
  },
  created() {
    this.initData()
    this.getCompanyList()
    this.getList()
    this.getDisc()
  },
  methods: {
    //会计类别变化方法
    changeAcct(val){
      this.attendenceOptions.AcctType.forEach(value=>{
        if(value.dicNo == val){
          this.form.acctDesc = value.dicName
        }
      })
    },
    //表单数据配置
    setForm(e) {
      this.form.creator = this.user.empName
      this.form.creatorId = this.user.empNo
      this.form.createDate = getDateTime(1)
      this.form.compId = this.user.compId
      this.form.compName = this.$refs.selectCompany.selectedLabel
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
        this.baseInfoData = response.data;
      })
    },
    //表格合并方法
    spanTable({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 1 || columnIndex === 2  || columnIndex === 7 || columnIndex === 9) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }
    },
    /** 查询出差会计科目列表 */
    getList() {
      this.loading = true
      var index = 1
      listTripAccountRule(this.queryParams).then(response => {
        this.tripAccountRuleList = []
        response.rows.forEach((value) => {
          var rule1 = {
            index: (index < 10) ? '0' + index : index,
            id: value.id,
            acctType: value.acctType,
            acctDesc: value.acctDesc,
            apprGroup: value.apprGroup,
            acctClassify: value.acctClassify,
            acctCode: value.acctCoded,
            acctName: null,
            idCode: value.idCoded,
            refNo: value.refNod,
            create: value.creator
          }
          var rule2 = {
            index: (index < 10) ? '0' + index : index,
            id: value.id,
            acctType: value.acctType,
            acctDesc: value.acctDesc,
            apprGroup: value.apprGroup,
            acctClassify: value.acctClassify,
            acctCode: value.acctCodec,
            acctName: null,
            idCode: value.idCodec,
            refNo: value.refNoc,
            create: value.createDate
          }
          index++
          this.tripAccountRuleList.push(rule1)
          this.tripAccountRuleList.push(rule2)
        })
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
        acctType: null,
        apprGroup: null,
        acctDesc: null,
        acctClassify: null,
        acctCoded: null,
        idCoded: null,
        refNod: null,
        dueDated: null,
        acctCodec: null,
        idCodec: null,
        refNoc: null,
        dueDatec: null,
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
      this.setForm();
      this.open = true
      this.title = '添加出差会计科目'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTripAccountRule(id).then(response => {
        this.form = response.data
        this.setForm()
        this.open = true
        this.title = '修改出差会计科目'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTripAccountRule(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTripAccountRule(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    //会计科目弹窗点击事件
    inputClick() {

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认作废该条数据项？').then(function() {
        return delTripAccountRule(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('作废成功')
      }).catch(() => {
      })
    }
  }
}
</script>
<style scoped>

</style>
