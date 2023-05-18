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
      <el-form-item label="报表名称">
        <el-input
          v-model="queryParams.reportName"
          placeholder="请输入报表名称"
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
        <div style="margin-top: 3px;font-size:14px">印表项目：</div>
      </el-col>
      <el-col :span="1.5">
        <el-tag type="info" style="width:200px">{{!pItemCode ? "" : pItemCode+'_'+pItemName}}</el-tag>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAddItemList"
          v-hasPermi="['aa:item:Add']"
        >添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['aa:item:add']"
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
          v-hasPermi="['finance:calType:remove']"
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
        <el-table-column label="加减项" align="center" prop="operator" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.operator'" :rules="rules.operator">
              <el-select v-model="scope.row.operator">
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
        <el-table-column label="代码类别" align="center" prop="kind" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.kind'" :rules="rules.kind">
              <el-select v-model="scope.row.kind" placeholder="请选择类别">
                <el-option
                  v-for="dict in dict.type.aa_content_kind"
                  :key="dict.value"
                  :label="dict.value+'_'+dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="会计科目/印表项目 " align="center" prop="code"  :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.code'" :rules="rules.code">
              <el-input v-model="scope.row.code" placeholder="请选择">
                <el-button slot="append" icon="el-icon-search" @click="inputAcctName(scope.row)"></el-button>
              </el-input>

            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column label="金额取数方式" align="center" prop="drcrkind" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.drcrkind'" :rules="rules.drcrkind">
              <el-select v-model="scope.row.drcrkind" placeholder="请选择类别">
                <el-option
                  v-for="dict in dict.type.aa_content_drcrkind"
                  :key="dict.value"
                  :label="dict.value+':'+dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="表达方式" align="center" prop="amtqty" :render-header="addRedstar">
          <template slot-scope="scope">
            <el-form-item :prop="'itemList.' + scope.$index + '.amtqty'" :rules="rules.amtqty">
              <el-select v-model="scope.row.amtqty" placeholder="请选择控制">
                <el-option
                  v-for="dict in dict.type.aa_content_amtqty"
                  :key="dict.value"
                  :label="dict.value+':'+dict.label"
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
        <el-table-column label="异动人" align="center" prop="updateName" />
        <el-table-column label="异动日期" align="center" prop="updateTime" :formatter="dateFormat"></el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeleteSingle(scope.row)"
              v-hasPermi="['aa:item:remove']"
            >删除</el-button>
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
    <acctcodeCorpPop ref="selectAcctCodeCorpPop" :companyId="pCompanyId" @ok="getAcctCodeCorpPop"/>
    <projectFormulaPop ref="selectProjectFormulaPop" @ok="getProjectFormulaPop"/>
  </div>
</template>

<script>
import { listProjectContent, addProjectContent, delProjectContent, changeUserStatus } from "@/api/finance/aa/projectContent";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";
import projectFormulaPop from "@/views/finance/aa/projectFormulaPop";
import {parseTime} from "@/utils/jlkj";
export default {
  name: "Item",
  dicts: ['aa_formula_sign','aa_content_kind','aa_content_drcrkind','aa_content_amtqty'],
  components: { acctcodeCorpPop,projectFormulaPop },
  data() {
    return {
      // 父组件传值
      pCompanyId: "",
      pReportId: "",
      pReportNo: "",
      pReportName: "",
      pItemCode: "",
      pItemName: "",
      indexRow:'',
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
      itemList: [],
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
        reportName:null,
      },
      // 会计公司下拉选单
      companyList: [],
      // 报表代号下拉选单
      reportNoList: [],
      // 表单参数
      form: { itemList: []},
      // 表单校验
      rules: {
        operator: [
          {required: true, message: '请输入加减项', trigger: 'blur'}
        ],
        kind: [
          {required: true, message: '请输入代码类别', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '此栏位不可为空', trigger: 'blur'},
        ],
        drcrkind: [
          {required: true, message: '请输入取数方式', trigger: 'blur'},
        ],
        amtqty: [
          {required: true, message: '请输入表达方式', trigger: 'blur'},
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
      this.pItemCode = obj.itemCode
      this.pItemName = obj.itemName
      this.getList()
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList= response;

      });
    },
    /** 会计科目点击事件 */
    inputAcctName(val) {
      this.indexRow= val.index
      if(this.form.itemList[this.indexRow-1].kind=='A'){
        this.$refs.selectAcctCodeCorpPop.show();
      }else if(this.form.itemList[this.indexRow-1].kind=='B'){
        this.$refs.selectProjectFormulaPop.show();
      }else{
        this.$modal.msgError("请先选择代码类别");
      }


    },
    getAcctCodeCorpPop(val){
      this.form.itemList[this.indexRow-1].code=val[0].acctCode
    },
    getProjectFormulaPop(val){
      this.form.itemList[this.indexRow-1].code=val[0].itemCode
    },
    /** 查询现金流量代码-对应会计科目列表 */
    getList() {
      this.loading = true;
      listProjectContent(this.queryParams).then(response => {
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
        operator: null,
        kind: null,
        code: null,
        drcrkind: null,
        amtqty: null,
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
        this.dataList[i].itemCode = this.pItemCode
      }
      this.form.dataList = this.dataList
      this.$refs["form"].validate(valid => {
        if (valid) {
          addProjectContent(this.form).then(response => {
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
      obj.itemCode = "";
      obj.code = "";
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
            return delProjectContent(ids);
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
          return delProjectContent(ids);
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
    dateFormat(row,column){
      var date = row[column.property];
      if(date === undefined){
        return "";
      }
      return parseTime(date,'{y}-{m}-{d}');
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
