<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
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
              @click="handleSave"
              v-hasPermi="['human:director:add']"
            >保存</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['human:director:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :key="key" :data="form.directorList" :row-class-name="tableRowClassName" @row-click="addLine" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="职责" align="center" prop="dutyCode">
            <template v-slot="scope">
              <el-select v-model="scope.row.dutyCode" placeholder="请选择">
                <el-option
                  v-for="dict in directorData.Responsibility"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="岗位" align="center" prop="postId" width="500">
            <template v-slot="scope">
              <el-input v-model="scope.row.postId" placeholder="请输入岗位" disabled />
            </template>
          </el-table-column>
          <el-table-column label="工号" align="center" prop="empId">
            <template v-slot="scope">
              <el-input v-model="scope.row.empId" placeholder="请输入工号" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入日期" align="center" prop="createDate" width="180">
            <template slot-scope="scope">
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
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listDirector, delDirector, addDirector } from "@/api/human/hs/director";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";

export default {
  name: "Director",
  components: {selectUser},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
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
        pageNum: 1,
        pageSize: 10,
        compId: null,
      },
      // 表单参数
      form: {
        // 各公司人事业务负责人表格数据
        directorList: []
      },
      // 表单校验
      rules: {
      },
      //登录人信息
      user: {},
      // 公司别下拉选单
      companyName: [],
      index: 0,
      rowIndex: 0,
      salaryOptionType: {
        id: '19',
        optionsType: [
          'Responsibility'
        ],
        compId:'J00',
      },
      directorData: [],
      key: undefined,
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getSalaryOptions(this.salaryOptionType).then(response =>{
      this.directorData = response.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询各公司人事业务负责人列表 */
    getList() {
      this.loading = true;
      listDirector(this.queryParams).then(response => {
        this.form.directorList = response.data.rows;
        if (this.form.directorList.length === 0) {
          this.form.directorList = [
            {
              dutyCode: undefined
            }
          ]
        }
        this.total = response.data.total;
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
        uuid: null,
        compId: null,
        dutyCode: null,
        empId: null,
        postId: null,
        orgId: null,
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
      this.ids = selection.map(item => item.uuid)
      this.multiple = !selection.length
    },
    /** 保存按钮操作 */
    handleSave() {
      this.$refs['queryForm'].validate(valid => {
        this.form.directorList.map(value => {
          value.compId = this.queryParams.compId
        })
        addDirector(this.form).then(res => {
          this.$modal.msgSuccess("新增成功");
          this.getList();
        })
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除各公司人事业务负责人编号为"' + uuids + '"的数据项？').then(function() {
        return delDirector(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    tableRowClassName({row, rowIndex}) {
      // 把每一行的索引放到row里
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      this.rowIndex = row.index
      if (this.form.directorList.length === row.index + 1) {
        const newLine = {
          dutyCode: undefined
        }
        this.index++
        this.form.directorList.push(newLine)
      }
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.form.directorList[0].empId = val
      this.form.empNo = val
      queryNewPostNameAndChangeDetail(this.form).then(res => {
        this.form.directorList[this.rowIndex].postId = res.data.list1[0].newPostName
        this.key = Math.random()
      })
    },
  }
};
</script>
