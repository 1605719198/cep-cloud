<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" clearable size="small">
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
    <el-form ref="form" :model="form" :rules="rules"  label-width="200px">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="上班打卡有效时间(分钟)" prop="onDutyTime">
            <el-input  type="number" v-model="form.onDutyTime" placeholder="请输入上班打卡有效时间" class="inputWidth" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="下班打卡有效时间(分钟)" prop="offDutyTime">
            <el-input  type="number" v-model="form.offDutyTime" placeholder="请输入下班打卡有效时间" class="inputWidth" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="迟到时间(分钟)" prop="lateTime" label-width="120px">
            <el-input  type="number" v-model="form.lateTime" placeholder="请输入迟到时间" class="inputWidth" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="上午下班打卡结束时间(分钟)" prop="offDutyAm">
            <el-input  type="number" v-model="form.offDutyAm" placeholder="请输入下午上班打卡开始时间" class="inputWidth" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="下午上班打卡开始时间(分钟)" prop="onDutyPm">
            <el-input  type="number" v-model="form.onDutyPm" placeholder="请输入下午上班打卡开始时间" class="inputWidth" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="早退时间(分钟)" prop="leaTime" label-width="120px">
            <el-input  type="number" v-model="form.leaTime" placeholder="请输入早退时间" class="inputWidth" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="打卡间隔时间(分钟)" prop="spaceTime">
            <el-input  type="number" v-model="form.spaceTime" placeholder="请输入打卡间隔时间" class="inputWidth" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="往前审核天数(天)" prop="beforeCheckDays">
            <el-input  type="number" v-model="form.beforeCheckDays" placeholder="请输入往前审核天数" class="inputWidth" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="缓冲时间(分钟)" prop="delayTime" label-width="120px">
            <el-input  type="number" v-model="form.delayTime" placeholder="请输入缓冲时间" class="inputWidth"/>
          </el-form-item>
        </el-col>

      </el-row>

      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="生效日期" prop="effectDate">
            <el-date-picker clearable
                            class="inputWidth"
                            v-model="form.effectDate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择生效日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="输入人员" prop="creator"  >
            {{form.creator}}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="输入日期" prop="createDate" label-width="100px">
            {{form.createDate}}
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="备注" prop="description"   >
        <el-input v-model="form.description" type="textarea" placeholder="请输入内容" maxlength="500" :autosize="{ minRows: 2, maxRows: 6 }" show-word-limit   />
      </el-form-item>


      <!--        <el-form-item label="公司别" prop="compId">-->
      <!--          <el-input  type="number" v-model="form.compId" placeholder="请输入公司别" />-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="公司名" prop="compName">-->
      <!--          <el-input  type="number" v-model="form.compName" placeholder="请输入公司名" />-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="组织机构" prop="organizationId">-->
      <!--          <el-input  type="number" v-model="form.organizationId" placeholder="请输入组织机构" />-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="组织机构名" prop="organizationName">-->
      <!--          <el-input  type="number" v-model="form.organizationName" placeholder="请输入组织机构名" />-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="输入人id" prop="creatorId">-->
      <!--          <el-input  type="number" v-model="form.creatorId" placeholder="请输入输入人id" />-->
      <!--        </el-form-item>-->

    </el-form>
    </div>
    <el-divider content-position="center"v-if="changeList">变更记录</el-divider>
    <el-table :data="organizationSettingList"  ref="settingVersion" v-if="changeList" >
      <el-table-column label="公司名" prop="compName" align="center" width="300px;"  show-overflow-tooltip>
        <template v-slot="scope">
          <span>{{scope.row.compName}}</span>
        </template>
      </el-table-column>
      <el-table-column label="生效日期" prop="effectDate" align="center"  show-overflow-tooltip>
        <template v-slot="scope">
          <span>{{scope.row.effectDate}}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="description" align="center" width="700px;"  show-overflow-tooltip>
        <template v-slot="scope">
          <span>{{scope.row.description}}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" prop="creator" align="center"   show-overflow-tooltip>
        <template v-slot="scope">
          <span>{{scope.row.creator}}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入日期" prop="createDate" align="center"   show-overflow-tooltip>
        <template v-slot="scope">
          <span>{{scope.row.createDate}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-info"
                      @click="handleView(scope.row)"
                    >详情</el-button>
        </template>
      </el-table-column>
    </el-table>


    <!-- 历史机构参数详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1350px" append-to-body>
      <el-form ref="formHistory" :model="formHistory"  label-width="220px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="上班打卡有效时间(分钟)：" prop="onDutyTime">
              {{formHistory.onDutyTime}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="下班打卡有效时间(分钟)：" prop="offDutyTime">
              {{formHistory.offDutyTime}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="迟到时间(分钟)：" prop="lateTime" label-width="120px">
              {{formHistory.lateTime}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="上午下班打卡结束时间(分钟)：" prop="offDutyAm">
              {{formHistory.offDutyAm}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="下午上班打卡开始时间(分钟)：" prop="onDutyPm">
              {{formHistory.onDutyPm}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="早退时间(分钟)：" prop="leaTime" label-width="120px">
              {{formHistory.leaTime}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="打卡间隔时间(分钟)：" prop="spaceTime">
              {{formHistory.spaceTime}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="往前审核天数(天)：" prop="beforeCheckDays">
              {{formHistory.beforeCheckDays}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="缓冲时间(分钟)：" prop="delayTime" label-width="120px">
              {{formHistory.delayTime}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="生效日期：" prop="effectDate">
              {{formHistory.effectDate}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入日期：" prop="createDate">
              {{formHistory.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入人员：" prop="creator" label-width="100px" >
              {{formHistory.creator}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注：" prop="description"   >
          {{formHistory.description}}
        </el-form-item>


        <!--        <el-form-item label="公司别" prop="compId">-->
        <!--          <el-input  type="number" v-model="form.compId" placeholder="请输入公司别" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="公司名" prop="compName">-->
        <!--          <el-input  type="number" v-model="form.compName" placeholder="请输入公司名" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="组织机构" prop="organizationId">-->
        <!--          <el-input  type="number" v-model="form.organizationId" placeholder="请输入组织机构" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="组织机构名" prop="organizationName">-->
        <!--          <el-input  type="number" v-model="form.organizationName" placeholder="请输入组织机构名" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="输入人id" prop="creatorId">-->
        <!--          <el-input  type="number" v-model="form.creatorId" placeholder="请输入输入人id" />-->
        <!--        </el-form-item>-->

      </el-form>
      <div slot="footer" class="dialog-footer">
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
        <el-button @click="cancel">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { getAvatorByUserName } from "@/api/system/user";
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { listOrganizationSetting, getOrganizationSetting,  addOrganizationSetting, updateOrganizationSetting } from "@/api/human/hd/organizationSetting";
import { selectCompany } from "@/api/human/hp/deptMaintenance";
export default {
  name: "OrganizationSetting",
  data() {
    return {
      //历史变更数据比是否显示
      changeList:true,
      //公司数据
      companyList:[],
      //是否修改
      ifupdate:false,
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
      // 历史机构参数表格数据
      organizationSettingList: [],
      // 历史记录详情
      formHistory:{},
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
      //最新表单数据
      lastform:{

      },
      //历史版本表单
      oldform:{

      },
      //修改前生效日期
      oldeffectDate:null,
      // 表单校验
      rules: {
        onDutyTime: [
          { required: true, message: "上班打卡有效时间不能为空", trigger: "blur" }
        ],
        offDutyTime: [
          { required: true, message: "下班打卡有效时间不能为空", trigger: "blur" }
        ],
        lateTime: [
          { required: true, message: "迟到时间不能为空", trigger: "blur" }
        ],
        leaTime: [
          { required: true, message: "早退时间不能为空", trigger: "blur" }
        ],
        delayTime: [
          { required: true, message: "缓冲时间不能为空", trigger: "blur" }
        ],
        effectDate: [
          { required: true, message: "生效日期不能为空", trigger: "blur" }
        ],
        beforeCheckDays: [
          { required: true, message: "往前审核天数不能为空", trigger: "blur" }
        ],
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
   queryParams:{
     deep:true,
     immediate:false,
     handler:function(newV){this.getList();}
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
    //日期比较
    compare () {
      var newDate = new Date(this.form.effectDate)
      var oldDate = new Date(this.oldeffectDate)
      var nowDate = new Date(getDateTime(1))
      if(newDate.getTime()<=nowDate.getTime()){
        return -1;
      }else{
        if(oldDate.getTime()<=nowDate.getTime()){
          return 1;
        }else if(oldDate.getTime()>nowDate.getTime()){
          return 0;
        }
      }

    },
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName;
        this.logincompId=response.data.compId;
        this.queryParams.compId=this.logincompId;
      })
    },
    /** 查询机构参数列表 */
    getList() {
      this.loading = true;
      listOrganizationSetting(this.queryParams).then(response => {
        if(response.rows[0]!=undefined){
          this.form = response.rows[0];
          this.oldeffectDate=this.form.effectDate;
          response.rows.splice(0,1);
          this.organizationSettingList = response.rows;
          this.total = response.total;
          this.changeList = true;
        }else{
          this.changeList = false;
          this.oldeffectDate='2000-01-01'
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
        onDutyTime: null,
        offDutyTime: null,
        lateTime: null,
        leaTime: null,
        offDutyAm: null,
        onDutyPm: null,
        spaceTime: null,
        effectDate: null,
        delayTime: null,
        beforeCheckDays: null,
        description: null,
        compId: null,
        compName: null,
        organizationId: null,
        organizationName: null,
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
          switch (this.compare()){
            case 0:
              this.form.creator = this.nickName;
              this.form.creatorId = this.$store.state.user.name;
              this.form.createDate = getDateTime(1);
              updateOrganizationSetting(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.getList();
              });
              break;
            case 1:
              this.form.creator = this.nickName;
              this.form.creatorId = this.$store.state.user.name;
              this.form.createDate = getDateTime(1);
              this.form.compId = this.queryParams.compId;
              this.companyList.forEach((value,index,array)=>{
                if(array[index].compId==this.form.compId){
                  this.form.compName = array[index].deptName;
                }
            })
              addOrganizationSetting(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.getList();
              });
              break;
            case -1:
              this.$modal.msgWarning("生效日期必须大于当前日期");
              break;
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
