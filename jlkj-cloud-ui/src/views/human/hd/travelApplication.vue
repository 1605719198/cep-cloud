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
      <el-form-item label="工号" prop="empNo">
        <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
          <el-button slot="append" icon="el-icon-search" @click="inputClick(query)"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="出差单号" prop="travelNo">
        <el-input
          v-model="queryParams.travelNo"
          placeholder="请输入出差单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出差日期" prop="startDate">
        <el-date-picker
          v-model="queryParams.startDate"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        >
        </el-date-picker>
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
          v-hasPermi="['human:travelapplication:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['human:travelapplication:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:travelapplication:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:travelapplication:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="travelapplicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="出差单号" align="center" prop="travelNo" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />

      <el-table-column label="代理人" align="center" prop="agentName" />
      <el-table-column label="出差日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出差事由" align="center" prop="travelReason">
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.TravelReason" :value="scope.row.travelReason"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.FlowStatus" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="creator" />
      <el-table-column label="申请日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:travelapplication:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:travelapplication:remove']"
          >删除</el-button>
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

    <!-- 添加或修改出差申请对话框 -->
    <el-dialog :title="title" :visible.sync="open"  :close-on-click-modal="false"  width="1500px" append-to-body align="left" >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row>
          <el-col :span="8">
            <el-form-item label="出差单号" prop="travelNo">
              {{ form.travelNo }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出差类别" align="left" :required="true" >
              <el-form-item prop="travelTpye" size="large" style="display: inline-block">
                <el-radio-group v-model="form.travelTpye" @change="changeType()">
                  <el-radio
                    style="margin-right: 5px"
                    v-for="dict in attendenceOptions.TravelType"
                    :key="dict.dicNo"
                    :label="dict.dicNo"
                  >{{ dict.dicName }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item prop="isEngineer" size="large" style="display: inline-block">
                <el-select v-model="form.isEngineer" placeholder="请选择" clearable style="width: 130px">
                  <el-option
                    v-for="dict in attendenceOptions.IsEngineer"
                    :key="dict.dicNo"
                    :label="dict.dicName"
                    :value="dict.dicNo"
                  />
                </el-select>
              </el-form-item>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出差事由类别" prop="travelReason">
              <el-select :popper-append-to-body="false" v-model="form.travelReason" placeholder="请选择出差事由类别">
                <el-option
                  v-for="dict in attendenceOptions.TravelReason"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true" class="inputInner" style="width: 160px">
                <el-button slot="append" icon="el-icon-search" @click="inputClick('empno')" clearable></el-button>
              </el-input>{{ form.empName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="部门" prop="deptName">
              {{ form.deptName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态" prop="status">
              <el-select :popper-append-to-body="false" v-model="form.status" disabled >
                <el-option
                  v-for="dict in attendenceOptions.FlowStatus"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>

          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="代理人" prop="agentNo">
              <el-input v-model="form.agentNo" placeholder="请选择代理人" :disabled="true" class="inputInner" style="width: 160px">
                <el-button slot="append" icon="el-icon-search" @click="inputClick('agent')" clearable></el-button>
              </el-input>{{ form.agentName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="成本中心" prop="costCenter">
              <el-input v-model="form.costCenter" placeholder="请选择成本中心" class="inputInner" style="width: 200px">
                <el-button slot="append" icon="el-icon-search" @click="inputCenter()" clearable></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工程细预算编号" prop="contract">
              <el-input v-model="form.contract" placeholder="请选择工程细预算编号" class="inputInner" style="width: 200px">
                <el-button slot="append" icon="el-icon-search" @click="inputCenter()" clearable></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16" >
            <el-form-item label="出差日期" prop="empNo">
              <el-date-picker clearable
                              style="width: 160px"
                              v-model="form.startDate"
                              type="date"
                              placeholder="请选择时间">
              </el-date-picker>          ~
              <el-date-picker clearable
                              style="width: 160px"
                              v-model="form.endDate"
                              type="date"
                              placeholder="请选择时间">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="出差天数" >
              {{ getTravelDays() }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="实际出差日期" prop="realStartDate">
              <el-date-picker clearable
                              style="width: 160px"
                              v-model="form.realStartDate"
                              type="date"
                              placeholder="请选择时间">
              </el-date-picker>         ~
              <el-date-picker clearable
                              style="width: 160px"
                              v-model="form.realEndDate"
                              type="date"
                              placeholder="请选择时间">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="实际出差天数">
              {{ getRealTravelDays() }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.travelTpye=='01'">
          <el-col :span="6">
            <el-form-item label="出差地点1" prop="billNo">
              <el-input v-model="form.resvAttr1" placeholder="请选择出差地点" class="inputInner" style="width: 200px">
                <el-button slot="append" icon="el-icon-search" @click="inputCenter()" clearable></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出差地点2" prop="billNo">
              <el-input v-model="form.resvAttr2" placeholder="请选择出差地点" class="inputInner" style="width: 200px">
                <el-button slot="append" icon="el-icon-search" @click="inputCenter()" clearable></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出差地点3" prop="billNo">
              <el-input v-model="form.resvAttr3" placeholder="请选择出差地点" class="inputInner" style="width: 200px">
                <el-button slot="append" icon="el-icon-search" @click="inputCenter()" clearable></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出差地点4" prop="billNo">
              <el-input v-model="form.resvAttr4" placeholder="请选择出差地点" class="inputInner" style="width: 200px">
                <el-button slot="append" icon="el-icon-search" @click="inputCenter()" clearable></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.travelTpye=='02'" >
          <el-col :span="6">
            <el-form-item label="出差地点1" prop="resvAttr1">

              <el-select :popper-append-to-body="false" v-model="form.resvAttr1"  placeholder="请选择出差地点" >
                <el-option
                  v-for="dict in attendenceOptions.TravelAbroad"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出差地点2" prop="resvAttr2">
              <el-select :popper-append-to-body="false" v-model="form.resvAttr2"  placeholder="请选择出差地点" >
                <el-option
                  v-for="dict in attendenceOptions.TravelAbroad"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出差地点3" prop="resvAttr3">

              <el-select :popper-append-to-body="false" v-model="form.resvAttr3"  placeholder="请选择出差地点" >
                <el-option
                  v-for="dict in attendenceOptions.TravelAbroad"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出差地点4" prop="resvAttr4">

              <el-select :popper-append-to-body="false" v-model="form.resvAttr4"  placeholder="请选择出差地点" >
                <el-option
                  v-for="dict in attendenceOptions.TravelAbroad"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="报支单号" prop="billNo">
              {{ form.billNo }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人" prop="creator">
              {{ form.creator }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请日期" prop="creatDate">
              {{ form.createDate}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { listTravelapplication, getTravelapplication, delTravelapplication, addTravelapplication, updateTravelapplication } from "@/api/human/hd/travelapplication";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import {queryInfo} from "@/api/human/hm/personnelBasicInfo";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHumanBasis from "@/views/components/human/dictTag/humanBaseInfo";
export default {
  name: "Travelapplication",
  components: { DictTagHumanBasis, selectUser },
  //components: {selectUser},
  dicts: ['travel_addr_abroad'],
  data() {
    return {
      //登录人信息
      user: {
        empNo: null,
        empName: null,
        compId: null
      },
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['TravelReason','TravelType','FlowStatus','IsEngineer','TravelAbroad']
      },
      //出勤选单选项列表
      attendenceOptions: {},
      companyList:[],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中数组
      nos: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出差申请表格数据
      travelapplicationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        travelNo: null,
        travelTpye: null,
        travelReason: null,
        compId: null,
        empId: null,
        empNo: null,
        empName: null,
        deptName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empNo:[
          { required:true, message:'不能为空', trigger: "change"}
        ],
        startDate:[
          { required:true, message:'不能为空', trigger: "change"}
        ],
        endDate:[
          { required:true, message:'不能为空', trigger:"change"}
        ],
        isEngineer:[
          { required:true, message:'不能为空', trigger:"change"}
        ],
        travelReason:[
          { required:true, message:'不能为空', trigger:"change"}
        ],
      }
    };
  },
  created() {
    this.initData()
    this.getCompanyList()
    this.getDisc()
    this.getList()
  },
  methods: {
    /** 查询出勤字典 */
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    // //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
      //this.queryParams.empNo = this.user.empNo
    },
    /** 查询出差申请列表 */
    getList() {
      this.loading = true;
      listTravelapplication(this.queryParams).then(response => {
        this.travelapplicationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        travelNo: null,
        travelTpye: '01',
        travelReason: null,
        isEngineer:null,
        compId: null,
        empId: null,
        empNo: null,
        empName: null,
        deptName: null,
        status: null,
        agentId: null,
        agentNo: null,
        agentName: null,
        billNo: null,
        costCenter: null,
        contract: null,
        startDate: null,
        startTime: null,
        endDate: null,
        endTime: null,
        realStartDate: null,
        realStartTime: null,
        realEndDate: null,
        realEndTime: null,
        reasonDesc: null,
        creator: null,
        creatorId: null,
        createDate: null,
        resvAttr1: null,
        resvAttr2: null,
        resvAttr3: null,
        resvAttr4: null
      };
      this.resetForm("form");
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
      this.ids = selection.map(item => item.id)
      this.nos = selection.map(item => item.travelNo)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.compId = this.queryParams.compId;
      this.title = "添加出差申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTravelapplication(id).then(response => {
        this.form = response.data;
        this.form.compId = this.queryParams.compId;
        this.open = true;
        this.title = "修改出差申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTravelapplication(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTravelapplication(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const nos = row.travelNo || this.nos;
      this.$modal.confirm('是否确认删除出差申请单号为"' + nos + '"的数据项？').then(function() {
        return delTravelapplication(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/travelapplication/export', {
        ...this.queryParams
      }, `travelapplication_${new Date().getTime()}.xlsx`)
    },
    /** 工号点击事件 */
    inputCenter() {
      //this.tagsrc = val;
      // this.$refs.select.show();
    },
    /** 工号点击事件 */
    inputClick(val) {
      this.tagsrc = val;
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val, userName) {
      if(this.tagsrc=='query'){
        this.queryParams.empNo = val
        this.getList();
      }else if (this.tagsrc=='empno'){
        this.form.empNo = val
        this.form.empName = userName
        queryInfo(this.form).then(res => {
          console.log(res.data[0].departmentName)
          this.form.deptName = res.data[0].departmentName
        })
      }else if(this.tagsrc=='agent'){
        this.form.agentNo = val
        this.form.agentName = userName
        console.log(this.form.agentName)
      }
      // queryNewPostNameAndChangeDetail(this.form).then(res => {
      //   this.form.postName = res.data.list1[0].newPostName
      // })
    },
    getTravelDays(){
      if( this.form.startDate && this.form.endDate){
        return Math.floor((new Date(this.form.endDate).getTime()-new Date(this.form.startDate).getTime())/(24*3600*1000)) + 1
      }
      return '';
    },
    getRealTravelDays(){
      if( this.form.realStartDate && this.form.realEndDate){
        return Math.floor((new Date(this.form.realEndDate).getTime()-new Date(this.form.realStartDate).getTime())/(24*3600*1000)) + 1
      }
      return ''
    },
    changeType(){
      // 切换出差类别，清空原出差地点内容
      this.form.resvAttr1='';
      this.form.resvAttr2='';
      this.form.resvAttr3='';
      this.form.resvAttr4='';
    }
  }
};

</script>
<style scoped>
.inputInner {
  width: 30%;
}
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>