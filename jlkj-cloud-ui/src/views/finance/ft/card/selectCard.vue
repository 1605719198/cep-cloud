<template>
  <!-- 挑选资产 -->
  <el-dialog title="选取资产" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :queryData="queryData" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资产编码" prop="assetNo">
        <el-input v-model="queryParams.assetNo" placeholder="请输入资产编码" />
      </el-form-item>
      <el-form-item label="资产名称" prop="assetName">
        <el-input v-model="queryParams.assetName" placeholder="请输入资产名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="assetList" @selection-change="handleSelectionChange" height="260px" >
        <el-table-column type="selection" width="50" align="center"></el-table-column>
        <el-table-column label="资产编码" prop="assetNo" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="资产名称" prop="assetName" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="数量" prop="qty" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="原值" prop="orgValue" :show-overflow-tooltip="true" align="center"/>

        <el-table-column label="净值" prop="netValue" :show-overflow-tooltip="true" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.accuDepr==null?scope.row.orgValue:scope.row.orgValue-scope.row.accuDepr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="累计折旧" prop="accuDepr" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="保管单位" prop="userDept" :show-overflow-tooltip="true" align="center"/>
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
      <el-button type="primary" @click="handleSelectCard">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
<!--   <label>ddd {{queryData}}</label>-->
  </el-dialog>
</template>

<script>
import { selectCardList } from "@/api/finance/ft/card";
export default {
  props: {
    queryData:{
      type:Object,
      required:true,
    }
  },
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      assetNo: [],
      // 选中数组值
      assetIds: [],
      // 选中的报支类别
      checkedList: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      assetList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        assetStatus: '',
        assetUser: '',
        product:'',
        isCleared:'',
        assetId:''
      }
    };
  },
  methods: {
    // 显示弹框
    show() {
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.assetNos = selection.map(item => item.assetNo);
      this.assetIds = selection.map(item => item.assetId);
      this.checkedList.splice(0,this.checkedList.length);
      selection.map((item) => this.checkedList.push(item));
      console.log("itemNos======>",this.assetNos.join(","))
    },

    // 查询表数据
    getList() {
      this.queryParams.product = this.queryData.product;
      this.queryParams.companyId = this.queryData.companyId;
      this.queryParams.isCleared = this.queryData.isCleared;
      if(this.queryData.assetId != undefined && this.queryData.assetId.length>0){
        this.queryParams.assetId = this.queryData.assetId.join(",");
      }else{
        this.queryParams.assetId="";
      }
      selectCardList(this.queryParams).then(res => {
        this.assetList = res.rows;
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
    /** 选择授权用户操作 */
    handleSelectCard() {
      if (this.assetIds == "") {
        this.$modal.msgError("请选择资产！");
        return;
      }
      this.checkedList.forEach(value =>
        value.companyId = this.companyId,
      );
     // alert(this.checkedList);
      console.log("this.checkedList=======>",this.checkedList)
      this.$emit('chooseData',this.checkedList);
      this.visible = false;

    }
  }
};
</script>
