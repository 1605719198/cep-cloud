<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" >
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status" v-show="this.activeName=='ShiftMode'">
        <el-select v-model="queryParams.status" placeholder="请选择状态" >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" v-show="this.activeName=='ShiftMode'">搜索</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName" @tab-click="handleClick" type="card" tab-position="top">
      <el-tab-pane label="轮班方式" name="ShiftMode">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['human:shiftMode:add']"
            >新增
            </el-button>
          </el-col>
<!--          <right-toolbar :showSearch.sync="showSearch" @queryTable="getModeList"></right-toolbar>-->
        </el-row>

        <el-table v-loading="loading" :data="shiftModeList" @selection-change="handleSelectionChange" height="60vh">
          <el-table-column label="轮班方式编码" align="center" prop="turnTypeCode"/>
          <el-table-column label="轮班方式名称" align="center" prop="turnTypeName"/>
          <el-table-column label="排班内是否计算加班" align="center" prop="resvAttr1a0">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.resvAttr1a0"/>
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="description"/>
          <el-table-column label="是否倒班" align="center" prop="isTurn">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isTurn"/>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="输入人" align="center" prop="creator"/>
          <el-table-column label="输入日期" align="center" prop="createDate" width="180">
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
                v-hasPermi="['human:shiftMode:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:shiftMode:remove']"
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
          @pagination="getModeList"
        />

      </el-tab-pane>
      <el-tab-pane label="班次" name="ShiftCode">
        <shift-code ref="ShiftCode"></shift-code>
      </el-tab-pane>
      <el-tab-pane label="班别" name="ShiftClass">
        <shift-class ref="ShiftClass"></shift-class>
      </el-tab-pane>
      <el-tab-pane label="年度排班" name="ArrangeClass">
        <arrange-class ref="ArrangeClass"></arrange-class>
      </el-tab-pane>
    </el-tabs>

    <!-- 添加或修改轮班方式数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="轮班方式编码" prop="turnTypeCode">
              <el-input v-model="form.turnTypeCode" placeholder="请输入轮班方式编码" maxlength="20" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="轮班方式名称" prop="turnTypeName">
              <el-input v-model="form.turnTypeName" placeholder="请输入轮班方式名称" maxlength="100" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排班内是否计算加班" prop="resvAttr1a0">
              <el-radio-group v-model="form.resvAttr1a0">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否倒班" prop="isTurn">
              <el-radio-group v-model="form.isTurn">
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

        <el-form-item label="备注" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" maxlength="500" show-word-limit/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
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
import { listShiftMode, getShiftMode, delShiftMode, addShiftMode, updateShiftMode, listShiftModeAll } from '@/api/human/hd/shiftMode'
import { getDateTime } from "@/api/human/hd/ahumanUtils"
import { selectCompany } from "@/api/human/hp/deptMaintenance";
import { getAvatorByUserName} from "@/api/system/user";
import ShiftCode from '@/views/human/hd/shiftModeView/shiftCode'
import ShiftClass from '@/views/human/hd/shiftModeView/shiftClass'
import ArrangeClass from './shiftModeView/arrangeClass'
export default {
  name: 'ShiftMode',
  dicts: ['sys_yes_no', 'sys_normal_disable'],
  components: { ArrangeClass, ShiftCode,ShiftClass},
  data() {
    return {
      //公司数据
      companyList:[],
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      //tab名称
      activeName:'ShiftMode',
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
      // 轮班方式数据表格数据
      shiftModeList: [],
      //轮班方式所有数据
      shiftModeListAll: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        status: '0',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        turnTypeCode: [
          { required: true, message: '轮班方式编码不能为空', trigger: 'blur' }
        ],
        turnTypeName: [
          { required: true, message: '轮班方式名称不能为空', trigger: 'blur' }
        ],
        isTurn :[
          { required: true, message: '请选择是否倒班', trigger: 'change' }
        ],
      }
    }
  },
  created() {
    this.getCompanyList();
    this.getName();
  },
  watch:{
    'queryParams.compId'(val){
      listShiftModeAll(this.queryParams).then(response =>{
        this.shiftModeListAll = response.rows;
        var obj = this.shiftModeListAll;
        switch (this.activeName){
          case 'ShiftCode':
            this.$refs.ShiftCode.changeCompId(val,obj)
            break;
          case 'ShiftClass':
            this.$refs.ShiftClass.changeCompId(val,obj)
            break;
          case 'ArrangeClass':
            this.$refs.ArrangeClass.changeCompId(val,obj);
            break;
        }
      })
    },
  },
  methods: {
    //设置表单值
    setForm(e){
      this.form.creator = this.nickName;
      this.form.creatorId = this.userEmpId;
      this.form.createDate = getDateTime(1);
      this.form.compId = this.queryParams.compId;
      if(e===0){
        this.form.isUsed = 'Y';
        this.form.status = '0';
      }
    },
    /** tab 切换事件 */
    handleClick(tab, event) {
      this.activeName = tab.name;
      var modeData={
          nickName:this.nickName,
          loginid:this.$store.state.user.name,
          compId:this.queryParams.compId,
          modeList:this.shiftModeListAll,
      }
      switch (tab.name){
        case 'ShiftCode':
          this.$refs.ShiftCode.init(modeData)
          break;
        case 'ShiftClass':
          this.$refs.ShiftClass.init(modeData)
          break;
        case 'ArrangeClass':
          this.$refs.ArrangeClass.init(modeData);
          break;
      }
    },
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName
        this.logincompId=response.data.compId
        this.queryParams.compId=response.data.compId;
        this.getModeList();
      })
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    /** 查询轮班方式数据列表 */
    getModeList() {
      this.loading = true
      listShiftMode(this.queryParams).then(response => {
        this.shiftModeList = response.rows
        this.total = response.total
        this.loading = false;
      })
      listShiftModeAll(this.queryParams).then(response =>{
        this.shiftModeListAll = response.rows
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
        turnTypeCode: null,
        turnTypeName: null,
        resvAttr1a0: null,
        description: null,
        isTurn: null,
        status: null,
        isUsed: null,
        creator: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getModeList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.compId = this.logincompId;
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
      this.open = true;
      this.title = '添加轮班方式数据'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getShiftMode(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改轮班方式数据'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.setForm(1)
            updateShiftMode(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getModeList()
            })
          } else {
            addShiftMode(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getModeList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除轮班方式数据编号为"' + ids + '"的数据项？').then(function() {
        return delShiftMode(ids)
      }).then(() => {
        this.getModeList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
  }
}
</script>
