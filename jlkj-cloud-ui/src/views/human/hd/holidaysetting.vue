<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" clearable>
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="假别名称" prop="holidayTypeCode">
        <el-select v-model="queryParams.holidayTypeCode" placeholder="请选择假别名称" clearable>
          <el-option
            v-for="dict in humanOptions"
            :key="dict.code"
            :label="dict.name"
            :value="dict.code"
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
          v-hasPermi="['human:holidaysetting:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-share"
          size="mini"
          @click="handleCopy"
          v-hasPermi="['human:holidaysetting:copy']"
        >复制</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="holidaysettingList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="假别名称" align="center" prop="holidayTypeCode">
        <template v-slot="scope">
          <dict-tag-human-basis :options="humanOptions" :value="scope.row.holidayTypeCode"/>
        </template>
      </el-table-column>
      <el-table-column label="是否含假日" align="center" prop="isIncHol">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isIncHol"/>
        </template>
      </el-table-column>
      <el-table-column label="常白班请假最小单位" align="center" prop="isNeedCheck" />
      <el-table-column label="常白班请假最小单位" align="center" prop="minUnitDay" />
      <el-table-column label="倒班请假最小单位" align="center" prop="minUnitTurns" />
      <el-table-column label="说明" align="center" prop="description" />
      <el-table-column label="输入人" align="center" prop="creator" />
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
            v-hasPermi="['human:holidaysetting:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:holidaysetting:remove']"
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

    <!-- 添加或修改假别参数设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="190px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="compId">
              <el-select v-model="form.compId" placeholder="请选择公司别" clearable class="maxWidth" :disabled="this.form.id">
                <el-option
                  v-for="dict in companyList"
                  :key="dict.compId"
                  :label="dict.companyName"
                  :value="dict.compId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="假别名称" prop="holidayTypeCode">
              <el-select v-model="form.holidayTypeCode" placeholder="请选择假别名称" clearable class="maxWidth" :disabled="this.form.id">
                <el-option
                  v-for="dict in humanOptions"
                  :key="dict.code"
                  :label="dict.name"
                  :value="dict.code"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="常白班请假最小单位(小时)" prop="minUnitDay">
              <el-input v-model="form.minUnitDay" placeholder="请输入常白班请假最小单位" type="number"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="常白班请假最小单位(班次)" prop="isNeedCheck">
              <el-input v-model="form.isNeedCheck" placeholder="请输入常白班请假最小单位" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否含假日" prop="isIncHol">
              <el-radio-group v-model="form.isIncHol">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="倒班请假最小单位(班次)" prop="minUnitTurns">
              <el-input v-model="form.minUnitTurns" placeholder="请输入倒班请假最小单位" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="说明" prop="description">
          <el-input v-model="form.description" placeholder="请输入说明" show-word-limit type="textarea" maxlength="100" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{form.creator}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 假别参数复制对话框 -->
    <el-dialog :title="titlecopy" :visible.sync="opencopy" width="500px" append-to-body>
      <el-form ref="formcopy" :model="formcopy" :rules="rulescopy" label-width="150px">
        <el-form-item label="来源公司" prop="oldCompId">
          <el-select v-model="formcopy.oldCompId" placeholder="请选择来源公司" clearable>
            <el-option
              v-for="dict in companyList"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="目标公司" prop="newCompId">
          <el-select v-model="formcopy.newCompId" placeholder="请选择目标公司" clearable>
            <el-option
              v-for="dict in companyList"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormcopy">确 定</el-button>
        <el-button @click="cancelcopy">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import DictTagHumanBasis from "@/views/components/human/dictTag/humanBasis"
import { selectCompany } from "@/api/human/hp/deptMaintenance";
import { getBasisOptions } from "@/api/human/hd/attendenceBasis";
import { getDateTime } from "@/api/human/hd/ahumanutils"
import { getBaseInfo } from "@/api/human/hm/baseInfo"
import { getAvatorByUserName} from "@/api/system/user";
import { listHolidaysetting, getHolidaysetting, delHolidaysetting, addHolidaysetting, updateHolidaysetting, copyHolidaysetting } from "@/api/human/hd/holidaysetting";

export default {
  name: "Holidaysetting",
  dicts: ['sys_yes_no'],
  components: {DictTagHumanBasis},
  data() {
    return {
      //公司数据
      companyList:[],
      //复制弹出层标题
      titlecopy:'假别参数设定复制窗口',
      //复制弹出层显示
      opencopy:false,
      //复制表单
      formcopy:{},
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'comp_id',
        ]
      },
      //选单数据
      baseInfoData: [],
      //人事出勤选单
      humanOptions:[],
      //登录人姓名
      nickName: undefined,
      //登录人公司
      logincompId:undefined,
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
      // 假别参数设定表格数据
      holidaysettingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        holidayTypeCode: null,
        compId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        holidayTypeCode: [
          { required: true, message: "假别名称不能为空", trigger: "change" }
        ],
        compId: [
          { required: true, message: "公司别不能为空", trigger: "change" }
        ],
        isIncHol: [
          { required: true, message: "是否含假日不能为空", trigger: "change" }
        ],
        minUnitDay: [
          { required: true, message: "常白班请假最小单位不能为空", trigger: "blur" }
        ],
      },
      rulescopy:{
        oldCompId:[
          { required: true, message: "来源公司不能为空", trigger: "change" }
        ],
        newCompId:[
          { required: true, message: "目标公司不能为空", trigger: "change" }
        ]
      }
    };
  },
  watch: {
    //监听公司别查询参数
    // 'queryParams.compId':{
    //   deep:true,
    //   immediate:false,
    //   handler:function( newV){
    //     this.getList();
    //          }
    // }
    queryParams:{
      deep:true,
      immediate:false,
      handler:function( newV){
        this.getList();
      }
    }

  },
  created() {
    this.getCompanyList();
    //假别类型查询
    var code = 'HD001'
    getBasisOptions(code).then(response=> {
      this.humanOptions=response.rows
    })
    this.getHumandisc();
    this.getName();
  },
  methods: {
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    //获取人事选单字典
    getHumandisc(){
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data;
      });
    },
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName
        this.logincompId=response.data.compId
        this.queryParams.compId=response.data.compId;
      })
    },
    /** 查询假别参数设定列表 */
    getList() {
      this.loading = true;
      listHolidaysetting(this.queryParams).then(response => {
        this.holidaysettingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消复制按钮
    cancelcopy() {
      this.resetcopy();
      this.opencopy = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        holidayTypeCode: null,
        compId: null,
        isIncHol: null,
        isNeedCheck: null,
        minUnitDay: null,
        minUnitTurns: null,
        description: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    //复制表单重置
    resetcopy(){
      this.formcopy={
        oldCompId: '',
        newCompId: '',
        creator:this.nickName,
        creatorId:this.$store.state.user.name,
        createDate:getDateTime(1),
      };
      this.resetForm("formcopy");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.compId = this.logincompId;
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 复制按钮操作 */
    handleCopy(){
      this.resetcopy();
      this.opencopy = true;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.creator = this.nickName;
      this.form.creatorId = this.$store.state.user.name;
      this.form.createDate = getDateTime(1);
      this.open = true;
      this.title = "添加假别参数设定";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHolidaysetting(id).then(response => {
        this.form = response.data;
        this.form.creator = this.nickName;
        this.form.creatorId = this.$store.state.user.name;
        this.form.createDate = getDateTime(1);
        this.open = true;
        this.title = "修改假别参数设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHolidaysetting(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHolidaysetting(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 假别参数复制提交按钮 */
    submitFormcopy(){
      this.$refs["formcopy"].validate(valid => {
        if (valid) {
          if(this.formcopy.newCompId==this.formcopy.oldCompId){
            this.$modal.msgError("请选择两个不同的公司");
          }else{
            copyHolidaysetting(this.formcopy).then(response =>{
              this.$modal.msgSuccess("复制成功");
              this.opencopy = false;
              this.getList();
            })
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除假别参数设定编号为"' + ids + '"的数据项？').then(function() {
        return delHolidaysetting(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
<style scoped>
.maxWidth {
  width: 100%;
}
</style>
