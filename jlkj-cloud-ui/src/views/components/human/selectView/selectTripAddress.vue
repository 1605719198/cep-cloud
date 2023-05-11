<template>
  <el-dialog title="选择出差地点" :visible.sync="visible" width="1200px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
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
        <el-input maxlength="32"  v-model="queryParams.address" placeholder="请输入地点" clearable />
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
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="addressList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
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
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listTripAddressRule } from "@/api/human/hd/tripAddressRule";
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
export default {
  dicts: ['sys_normal_disable'],
  components:{DictTagHuman},
  data() {
    return {
      // 判断选择单笔还是多笔
      isSingle: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      userIds: [],
      // 总条数
      total: 0,
      // 一级部门
      addressList: [],
      // 部门树选项
      deptOptionsa: undefined,
      //公司数据
      companyList: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['AddressType']
      },
      //出勤选单选项列表
      attendenceOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        address: null,
        type: null,
      },
    };
  },
  created() {
  },
  watch: {
    'queryParams.compId'(val) {
      this.getList();
    }
  },
  methods: {
    // 显示弹框
    show(val) {
      this.getDisc()
      this.getCompanyList()
      this.queryParams.compId=val;
      this.getList();
      this.visible = true;
      this.isSingle = true;

    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userIds = selection;
    },
    // 查询表数据
    getList() {
      listTripAddressRule(this.queryParams).then(res => {
        this.addressList = res.rows;
        this.total = res.total;
      });
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
    /** 选择地点操作 */
    handleSelectUser() {
      if (this.userIds.length === 0) {
        this.$modal.msgError("请选择一笔数据");
        return;
      }
      if (this.userIds.length > 1 && this.isSingle === true) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok",this.userIds);
    },
    //获取出勤字典
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
  }
};
</script>
<style scoped>
</style>
