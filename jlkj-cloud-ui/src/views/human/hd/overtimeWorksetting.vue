<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="compId" placeholder="请选择公司别" clearable size="small">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
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
          icon="el-icon-check"
          size="mini"
          @click="handleSave"
          v-hasPermi="['human:organizationSetting:save']"
        >保存</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <div style="border-color: #c8c8c8;border-width: 2px; border-style: solid; padding:15px">
      <el-form ref="form" :model="form" :rules="rules" label-width="250px">

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="存班小时是否累计到下一月度" prop="isTranMon">
              <el-radio-group v-model="form.isTranMon">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="存班小时是否累计到下一季度" prop="isTranQua">
              <el-radio-group v-model="form.isTranQua">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="存班小时是否累计到下一年度" prop="isTranYear">
              <el-radio-group v-model="form.isTranYear">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="月最大加班小时数（单位：小时）" prop="monthOve">
              <el-input type="number" v-model="form.monthOve"  />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="月最大剩余存班小时数（单位：小时）" prop="resvAttr3">
              <el-input type="number" v-model="form.resvAttr3"  />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="月最大可送出异常次数" prop="resvAttr1">
              <el-input type="number" v-model="form.resvAttr1"  />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="补休最小单位（单位：小时）" prop="holUnit">
              <el-input type="number" v-model="form.holUnit"  />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="补休的最小班次数" prop="resvAttr2" >
              <el-input type="number" v-model="form.resvAttr2"  />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="加班最小单位（单位：小时）" prop="oveUnit">
              <el-input type="number" v-model="form.oveUnit"  />
            </el-form-item>
          </el-col>
        </el-row>



        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="输入日期" prop="createDate" >
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="createDate" >

            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入人" prop="creator" >
              {{form.creator}}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </div>

  </div>
</template>

<script>
import { getAvatorByUserName } from "@/api/system/user";
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { listOvertimeWorksetting, getOvertimeWorksetting, delOvertimeWorksetting, addOvertimeWorksetting, updateOvertimeWorksetting } from "@/api/human/hd/overtimeWorksetting";
import { selectCompany } from "@/api/human/hp/deptMaintenance";
export default {
  name: "OvertimeWorkSetting",
  dicts: ['sys_yes_no'],
  data() {
    return {
      //公司选单值
      compId:null,
      //公司数据
      companyList:[],
      //登录人姓名
      nickName:null,
      //登录人公司
      logincompId:null,
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        compId: null,
      },
      // 表单参数
      form: {

      },
      // 表单校验
      rules: {

      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    compId(val){
      this.queryParams.compId = val;
      this.getList();
    }
  },
  created() {
    this.getCompanyList();
    this.getName();
  },
  methods: {
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    //历史版本详情
    handleView(row){
      this.formHistory = row;
      this.open = true;
      this.title = '历史版本查询窗口'
    },
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName;
        this.logincompId=response.data.compId;
        this.compId = response.data.compId;
      })
    },
    /** 查询机构参数列表 */
    getList() {
      this.loading = true;
      listOvertimeWorksetting(this.queryParams).then(response => {
        if(response.rows[0]!=undefined){
          this.form = response.rows[0];
          this.total = response.total;
        }else{
          this.reset();
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        isTranMon: null,
        isTranQua: null,
        isTranYear: null,
        holUnit: null,
        oveUnit: null,
        monthOve: null,
        resvAttr3: null,
        resvAttr1: null,
        resvAttr2: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.compId=this.logincompId;
      this.handleQuery();
    },
    /** 保存按钮操作 */
    handleSave(row) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.creator = this.nickName;
          this.form.creatorId = this.$store.state.user.name;
          this.form.createDate = getDateTime(1);
          if(this.form.id!=null){
            updateOvertimeWorksetting(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            });
          }else{
            this.form.compId = this.queryParams.compId;
            addOvertimeWorksetting(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getList();
            });
          }
        }
      });
    },
  }
};
</script>
<style scoped>
.inputWidth {
  width: 70%;
}
</style>
