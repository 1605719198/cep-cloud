<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="地点" prop="address">
        <el-input maxlength="32"  v-model="queryParams.address" placeholder="请输入地点"  clearable/>
      </el-form-item>
      <el-form-item label="地点属性" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择地点属性" clearable>
          <el-option
            v-for="dict in attendenceOptions.AddressType"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          ></el-option>
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
          v-hasPermi="['human:tripAddressRule:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tripAddressRuleList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column label="国别" align="center" prop="nation">
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.Country" :value="scope.row.nation"/>
        </template>
      </el-table-column>
      <el-table-column label="地点" align="center" prop="address" />
      <el-table-column label="拼音缩写" align="center" prop="simpl" />
      <el-table-column label="地点属性" align="center" prop="type" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.AddressType" :value="scope.row.type"/>
        </template>
      </el-table-column>
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
            v-hasPermi="['human:tripAddressRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:tripAddressRule:remove']"
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

    <!-- 添加或修改出差地点记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司别" prop="compId">
          <label>
            {{this.form.compName}}
          </label>
        </el-form-item>
        <el-form-item label="国别" prop="nation">
          <el-select v-model="form.nation" placeholder="请选择国别" style="width: 100%">
            <el-option
              v-for="dict in attendenceOptions.Country"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            />
          </el-select>
        </el-form-item>
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
        <el-form-item label="地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入地点" maxlength="100" />
        </el-form-item>
        <el-form-item label="拼音缩写" prop="simpl">
          <el-input v-model="form.simpl" placeholder="请输入拼音缩写" maxlength="20" />
        </el-form-item>
        <el-form-item label="地点属性" prop="type">
          <el-select v-model="form.type" placeholder="请选择地点属性" style="width: 100%">
            <el-option
              v-for="dict in attendenceOptions.AddressType"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
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
import { listTripAddressRule, getTripAddressRule, delTripAddressRule, addTripAddressRule, updateTripAddressRule } from "@/api/human/hd/tripAddressRule";
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
export default {
  name: "TripAddressRule",
  dicts: ['sys_yes_no'],
  components:{DictTagHuman},
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
      user: {},
      //公司数据
      companyList: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['AddressType','Country']
      },
      //出勤选单选项列表
      attendenceOptions: [],
      // 出差地点记录表格数据
      tripAddressRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        address: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        address:[
          { required:true, message:'地点不能为空', trigger: "blur"}
        ],
        simpl:[
          { required:true, message:'拼音缩写不能为空', trigger: "blur"}
        ],
        type:[
          { required:true, message:'地点属性不能为空', trigger: "change"}
        ],
        nation:[
          { required:true, message:'请选择国别', trigger:"change"}
        ],
        isInternal: [
          { required:true, message:'请选择是否国内', trigger:"change"}
        ]
      }
    };
  },
  watch: {
    'queryParams.compId'(val) {
      this.getList();
    }
  },
  created() {
    this.getDisc()
    this.getCompanyList()
    this.initData()
    this.getList();
  },
  methods: {
    //获取出勤字典
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    //表单数据配置
    setForm(e) {
      this.form.creator = this.user.empName
      this.form.creatorId = this.user.empNo
      this.form.createDate = getDateTime(1)
      this.form.compId = this.queryParams.compId
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
      this.user.empNo = this.$store.state.user.name;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId;
      this.queryParams.address = null;
    },
    /** 查询出差地点记录列表 */
    getList() {
      this.loading = true;
      listTripAddressRule(this.queryParams).then(response => {
        this.tripAddressRuleList = response.rows;
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
        compId: null,
        compName: null,
        address: null,
        simpl: null,
        type: null,
        nation: null,
        isInternal: null,
        creator: null,
        creatorId: null,
        createDate: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.setForm();
      this.open = true;
      this.title = "添加出差地点记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTripAddressRule(id).then(response => {
        this.form = response.data;
        this.form.compName = this.$refs.selectCompany.selectedLabel;
        this.open = true;
        this.title = "修改出差地点记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTripAddressRule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTripAddressRule(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除所选数据项？').then(function() {
        return delTripAddressRule(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

  }
};
</script>
