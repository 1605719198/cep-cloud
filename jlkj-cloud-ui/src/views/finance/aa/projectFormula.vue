<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="报表代号" prop="reportNo">
        <el-input
          v-model="queryParams.reportNo"
          placeholder="请输入报表代号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <div style="margin-top: 3px;font-size:14px">报表代号：</div>
      </el-col>
      <el-col :span="1.5">
        <el-tag type="info" style="width:200px">{{!pReportNo ? "" : pReportNo + '_' + pReportName}}</el-tag>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAddItemList"
          v-hasPermi="['finance:reportDefin:add']"
        >添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['finance:reportDefin:add']"
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
          v-hasPermi="['finance:reportDefin:remove']"
        >删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-form ref="form" :model="form" :rules="rules">
      <el-table v-loading="loading" :data="form.itemList"
                @selection-change="handleSelectionChange"
                :row-class-name="rowDataIndex"  ref="table">
        <el-form-item  >
          <el-table-column type="selection" width="55" align="center" />
        </el-form-item>
        <el-table-column label="印表项目 " align="center" prop="itemCode"  :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.itemCode'" :rules="rules.itemCode">
              <el-input v-model="scope.row.itemCode" placeholder="请输入印表项目"/>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="项目名称 " align="center" prop="itemName" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.itemName'" :rules="rules.itemName">
              <el-input v-model="scope.row.itemName" placeholder="请输入项目名称" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="行次" align="center" prop="itemRow" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.itemRow'" :rules="rules.itemRow">
              <el-input v-model="scope.row.itemRow" type="number" placeholder="请输入行次"/>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="类别" align="center" prop="itemType" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.itemType'" :rules="rules.itemType">
              <el-select v-model="scope.row.itemType" placeholder="请选择类别">
                <el-option
                  v-for="dict in dict.type.aa_item_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="控制" align="center" prop="sumControl" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.sumControl'" :rules="rules.sumControl">
              <el-select v-model="scope.row.sumControl" placeholder="请选择控制">
                <el-option
                  v-for="dict in dict.type.aa_sum_control"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="空格 " align="center" prop="skipSpace" >
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.skipSpace'" >
              <el-input v-model="scope.row.skipSpace" type="number"/>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="正负" align="center" prop="sign" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.sign'" :rules="rules.sign">
              <el-select v-model="scope.row.sign">
                <el-option
                  v-for="dict in dict.type.aa_formula_sign"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" key="status" >
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeleteSingle(scope.row)"
              v-hasPermi="['finance:reportDefin:remove']"
            >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="gotoContentTab(scope.row)"
            v-hasPermi="['finance:reportDefin:edit']"
          >项目公式</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { listProjectFormula, addProjectFormula,delProjectFormula, changeUserStatus } from "@/api/finance/aa/projectFormula";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";

export default {
  name: "Item",
  dicts: ['aa_sum_control', 'aa_item_type','aa_formula_sign'],
  data() {
    return {
      // 父组件传值
      pCompanyId:null,
      pReportId:null,
      pReportNo: null,
      pReportName: null,
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
      // 多笔数据
     // itemList: [],
      // 子表选中数据
      checkedItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询数据
      dataList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId:null,
        reportNo:null,
        itemName:null,
      },
      // 会计公司下拉选单
      companyList: [],
      // 表单参数
      form: { itemList: []},
      // 表单校验
      rules: {
        itemCode: [
          {required: true, message: '请输入印表项目', trigger: 'blur'},
          {max: 10, message: '最大长度10个字符', trigger: 'blur'}
        ],
        itemName: [
          {required: true, message: '请输入项目名称', trigger: 'blur'},
          {max: 100, message: '最大长度100个字符', trigger: 'blur'}
        ],
        itemRow: [
          {required: true, message: '请输入行次', trigger: 'blur'},
        ],
        itemType: [
          {required: true, message: '请输入类别', trigger: 'blur'},
        ],
        sumControl: [
          {required: true, message: '请输入控制', trigger: 'blur'},
        ],
        sign: [
          {required: true, message: '请输入正负', trigger: 'blur'},
        ],
      }
    };
  },

  created() {
    this.getList();
    this.getCompanyList();
  },
  methods: {
    /** 初始化方法 */
    init(obj) {
      // 根据传递过来的值进行查询
      this.queryParams.companyId = obj.companyId
      this.queryParams.reportNo = obj.reportNo
      this.pCompanyId = obj.companyId
      this.pReportId = obj.reportId
      this.pReportNo = obj.reportNo
      this.pReportName = obj.reportName
      this.getList()
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList= response;

      });
    },
    /** 查询现金流量代码-对应会计科目列表 */
    getList() {
      this.loading = true;
      listProjectFormula(this.queryParams).then(response => {
        this.form.itemList = response.rows;
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
        companyId: null,
        reportId: null,
        reportNo: null,
        itemCode: null,
        itemName: null,
        itemRow: null,
        itemType: null,
        sumControl: null,
        skipSpace: null,
        sign: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
      };
      this.resetForm("form");
    },

    /** 项目名称点击事件 */
    gotoContentTab(row) {
      this.$emit('gotoContentTab',row,this.pReportName)
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
      this.checkedItemList = selection.map(item => item)
      this.dataList = selection
      this.ids.length = 0;
      for(const k of selection){
        if (k.id != null&&k.id!=''&&k.id!=undefined){
          this.ids.push(k.id)
        }
      }
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if(!this.pReportNo){
        this.$modal.msgError("报表代号为空不可保存!");
        return;
      }
      if (this.dataList.length == 0) {
        this.$modal.msgError("请至少勾选一笔数据");
        return;
      }
      for (let i = 0; i < this.dataList.length; i++) {
        this.dataList[i].companyId = this.pCompanyId
        this.dataList[i].reportId = this.pReportId
        this.dataList[i].reportNo = this.pReportNo
      }
      this.form.dataList = this.dataList
      this.$refs["form"].validate(valid => {
        if (valid) {
          addProjectFormula(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 细项维护序号 */
    rowDataIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 细项维护添加按钮操作 */
    handleAddItemList() {
      let obj = {};
      obj.companyId = "";
      obj.reportId = "";
      obj.reportNo = "";
      obj.status = "0";
      this.form.itemList.push(obj);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let item = null;
      if (this.checkedItemList.length == 0) {
        this.$modal.msgError("请先选择要删除的数据");
      } else {
        for(let i=0;i<this.checkedItemList.length;i++){
          item = this.checkedItemList[i]
          if (item.id == null||item.id==''||item.id==undefined){
            const tDataList = this.form.itemList;
            const checkedItemList = this.checkedItemList;
            this.form.itemList = tDataList.filter(el =>  item.index != el.index );
          }
        }
        const ids = row.id || this.ids;
        if(ids.length != 0){
          this.$modal.confirm('确认要删除吗？').then(function () {
            return delProjectFormula(ids);
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {
          });
        }
      }
    },
    handleDeleteSingle(row) {
      if (row.id == null||row.id==''||row.id==undefined){
        const tDataList = this.form.itemList;
        const checkedItemList = this.checkedItemList;
        this.form.itemList = tDataList.filter(el =>  row.index != el.index );
      }else {
        const ids = row.id || this.ids;
        this.$modal.confirm('确认要删除吗？').then(function () {
          return delProjectFormula(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      /*this.download('aa/item/export', {
        ...this.queryParams
      }, `item_${new Date().getTime()}.xlsx`)*/
    },
    addRedstar(h, {column }){
      return[
        h("span",""+ column.label),
        h("span", {style: "color: red"},"*"),
      ];
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '吗？').then(function () {
        return changeUserStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>
