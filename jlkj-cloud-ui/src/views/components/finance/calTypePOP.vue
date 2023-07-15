<template>
<el-dialog title="核算项目pop" :visible.sync="visible" width="1080px" top="5vh"
           append-to-body :close-on-click-modal="false">
<el-form :model="queryParams" ref="queryForm" size="small"
  :inline="true" >
  <el-form-item  prop="calTypeCode">
    <el-select v-model="queryParams.calTypeCode"
               filterable placeholder="请输入核算项目类别"
               :disabled="selectIf"  >
      <el-option
        v-for="item in calTypeList"
        :key="item.value"
        :label="item.label"
        @click.native="changCompanyId(item)"
        :value="item.value">
      </el-option>
    </el-select>
  </el-form-item>
  <el-form-item   prop="sys"    v-if="tableIf">
    <el-select v-model="queryParams.sys"
               filterable placeholder="请输入系统别">
      <el-option
        v-for="item in calTypeCodeaSystemList"
        :key="item.value"
        :label="item.label"
        @click.native="changType(item)"
        :value="item.value"
      >
      </el-option>
    </el-select>
  </el-form-item>
  <el-form-item v-for="(item, index) in tableColumnsInput" :key="index" >
    <el-input v-model="item.value" v-if=" item.nameEn!=='Id'&& item.nameEn!=='uuid'"></el-input>
  </el-form-item>
  <el-form-item label="" v-if="tableCodeIf" prop="calCode" label-width="130px">
    <el-input
      v-model="queryParams.calCode"
      placeholder="请输入核算项目编码"
      clearable
      @keyup.enter.native="handleQuery"
    />
  </el-form-item>
  <el-form-item label=""  v-if="tableCodeIf"prop="calName" label-width="130px">
    <el-input
      v-model="queryParams.calName"
      placeholder="请输入核算项目名称"
      clearable
      @keyup.enter.native="handleQuery"
    />
  </el-form-item>
  <el-form-item>
    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" v-if="tableIf">搜索</el-button>
    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryCode" v-if="tableCodeIf">搜索</el-button>
    <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
  </el-form-item>
</el-form>
  <el-row>
    <el-table  :data="detailList" ref="table"
              @selection-change="handleSelectionChange"
               @row-click="clickRow"
               v-if="tableIf"
    >
      <el-table-column type="selection" width="55" align="center" v-if="this.tableColumns.length>0"/>
      <el-table-column
        v-for="(item,index) in tableColumns"
        :key="index"
        :prop="item.nameEn"
        align='center'
        :label="item.name"
      v-if=" item.nameEn!=='Id'&&item.nameEn!=='uuid'">
      </el-table-column>
    </el-table>
    <el-table  v-if="tableCodeIf"
               :data="detailCodeList" ref="table"
               @selection-change="handleSelectionCodeChange"
               @row-click="clickCodeRow">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="核算项目代码" align="center" prop="calCode"/>
      <el-table-column label="核算项目名称" align="center" prop="calName"/>
    </el-table>
    <el-pagination
      v-if="tableIf"
      background
      :total="total"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 30, 50]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="float: right; position: relative;height: 25px; margin-bottom: 10px;margin-top: 15px; padding: 10px 20px !important;">
    </el-pagination>
    <el-pagination
      v-if="tableCodeIf"
      background
      :total="total"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 30, 50]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeCodeChange"
      @current-change="handleCurrentCodeChange"
      style="float: right; position: relative;height: 25px; margin-bottom: 10px;margin-top: 15px; padding: 10px 20px !important;">
    </el-pagination>
  </el-row>
  <div slot="footer" class="dialog-footer">
    <el-button type="primary" @click="handleSelectUser">确 定</el-button>
    <el-button @click="visible = false">取 消</el-button>
  </div>
</el-dialog>
</template>

<script>
import { calTypeListTable2,selectCalTypeList,selectCalTypeSystemList,calTypeListTab,
  calTypeListTable}
  from "@/api/finance/aa/calType";
import { listCalcode }
  from "@/api/finance/aa/calcode";
export default {
  name: "calTypePOP",
  data(){
    return{
      // 选中数组
      id: [],
      // 选中数组
      chineseName: [],
      // 选中数组
      compId: [],
      // 选中数组
      codeNode:[],
      // v-for input框绑定数组
      tableColumnsInput:[],
      // 遮罩层
      visible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        calTypeCode:'',
        calTypeName:"",
        companyId:'J00',
        sys:"",
        key:{},
        value:{},
        calNo:'',
        calName:''
      },
      queryParam: {
        companyId:'',
        sys:"",
        calTypeCode:'',
        key:{},
        value:{},
        calNo:'',
        calName:''
      },
      // 核算项目类别选单
      calTypeList: [],
      // 系统别选单
      calTypeCodeaSystemList: [],

      tableColumns: [

      ],
      tableIf:false,
      tableCodeIf:false,
      // 总条数
      total: 0,
      numbertotal:0,
      totalVal:'10',
      detailCodeList:[],
      // 指标定义表格数据
      detailList: [],
      fundsList1:[],
      fundsList2:[],
      compIdList:{},
      selectIf:false
    };

  },
  methods: {
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
      this.$emit("pop",row);
    },
    clickCodeRow(row) {
      this.$refs.table.toggleRowSelection(row);
      this.$emit("pop",row);
    },
    handleSelectUser() {
      if (this.compId.length>1){
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      if (this.compId.length=0){
        this.$modal.msgError("请选择要分配的核算项目");
        return;
      }
      this.visible = false;
      this.$emit("pop", this.compIdList);
    },
    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams = queryParams;
      }
      if(!this.queryParams.companyId){
        this.$message.error('公司别不能为空');
        return
      }

      this.queryParams.calTypeCode = queryParams.calTypeCode
      if (!!this.queryParams.calTypeCode){
        this.selectIf = true
        this.changCompanyId(this.queryParams);
      }else {
        this.selectIf = false
        this.queryParams.sys=''
        this.detailCodeList=[]
        this.detailList=[]
        this.tableColumns=[]
        this.tableColumnsInput=[]
      }
      this.getCalTypeList();
      this.visible = true;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.compId=selection
      this.compIdList=this.compId[0]
      this.$set( this.compIdList,"sys", this.queryParams.sys)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 多选框选中数据
    handleSelectionCodeChange(selection) {
      this.compId=selection
      this.compIdList=this.compId[0]

      if(!!this.compId.length>0){
        this.compIdList.calNo=this.compId[0].calCode
      }
      if(!!this.compId.length>0){
        this.compIdList.Id=this.compId[0].id
      }

      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 搜索按钮操作 */
    handleQueryCode() {
      this.queryParams.pageNum = 1;
      this.getCodeList();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      for (let i=1;i<this.tableColumns.length;i++){
        if (this.tableColumns[i].value==null||this.tableColumns[i].value==''){
          this.$set(this.queryParam.key,this.tableColumns[i].nameEn,"null")
        }else {
          this.$set(this.queryParam,this.tableColumns[i].nameEn, this.tableColumns[i].value)
          this.$set(this.queryParam.key,this.tableColumns[i].nameEn,this.tableColumns[i].value)
        }
      }
      this.queryParam.companyId= this.queryParams.companyId
      this.queryParam.calTypeCode= this.queryParams.calTypeCode
      this.queryParam.sys= this.queryParams.sys
      calTypeListTable2(this.queryParam).then(response => {
        this.detailList= response.data
        this.fundsList1 =response.data;
        this.detailList = this.fundsList1.slice(0,this.totalVal)
        this.total =this.detailList.length;
        let number = Math.ceil(this.total / this.totalVal);
        if (this.numbertotal <= number && this.numbertotal !==0){
          if (this.numbertotal===1){
            this.detailList =  response.rows.slice(0, this.totalVal)
          }else if (this.total>this.totalVal*this.numbertotal){
            this.fundsList1 = response.rows;
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
            this.detailList = this.fundsList2
            this.fundsList2=[]
          }else if ( number === this.numbertotal){
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
            this.detailList = this.fundsList2
            this.fundsList2=[]
          }
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams={
        pageNum: 1,
        pageSize: 10,
        calTypeCode:'',
        sys:"",
        companyId:''
      }
    },
    //分页方法
    handleSizeChange(val) {
      this.totalVal = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.numbertotal=  val
      this.getList()
    },
    //分页方法
    handleSizeCodeChange(val) {
      this.totalVal = val
      this.getCodeList()
    },
    handleCurrentCodeChange(val) {
      this.numbertotal=  val
      this.getCodeList()
    },
    //表格数据
    getCodeList(){
      listCalcode(this.queryParams).then(response => {
        this.detailCodeList = response.rows;

        this.total = response.total;
        this.loading = false;
      });
    },
    //表格数据
    getList(){
      calTypeListTable(this.queryParams).then(response => {
        this.detailList= response.data
        this.fundsList1 =response.data;
        this.detailList = this.fundsList1.slice(0,this.totalVal)
        this.total =this.detailList.length;
        let number = Math.ceil(this.total / this.totalVal);
        if (this.numbertotal <= number && this.numbertotal !==0){
          if (this.numbertotal===1){
            this.detailList =  response.rows.slice(0, this.totalVal)
          }else if (this.total>this.totalVal*this.numbertotal){
            this.fundsList1 = response.rows;
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
            this.detailList = this.fundsList2
            this.fundsList2=[]
          }else if ( number === this.numbertotal){
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
            this.detailList = this.fundsList2
            this.fundsList2=[]
          }
        }

      });
    },
    getCalTypeList() {
      selectCalTypeList().then(response => {
        this.calTypeList = response;
      });
    },
    getCalTypeSystemList() {
      this.queryParams.sys=''
      selectCalTypeSystemList(this.queryParams).then(response => {
        this.calTypeCodeaSystemList = response;
        if (this.calTypeCodeaSystemList[0].calRule!="04"){
          this.tableIf = true
          this.tableCodeIf = false
          if (this.selectIf==true){
            this.changType()
          }
        }else if (this.calTypeCodeaSystemList[0].calRule=="04"){
          this.getCodeList();
          this.tableColumnsInput=[]
          this.tableIf = false
          this.tableCodeIf = true
        }
      });
    },
    //公司下拉选单掉用方法
    changCompanyId(val) {
      if (!!val.value){
        this.queryParams.calTypeCode = val.value
      }
      this.getCalTypeSystemList()
    },
    //系统别下拉选单掉用方法
    changType(val) {
      if (!!val){
        this.queryParams.sys = val.value
      }else {
        this.queryParams.sys =  this.calTypeCodeaSystemList[0].value
      }

      calTypeListTab(this.queryParams).then(response => {
        this.tableColumns= response.data
        this.tableColumnsInput=  this.tableColumns
        if (this.tableColumns.length>0){
          this.getList()
        }
      });
    },
  }
}
</script>

<style scoped>

</style>
