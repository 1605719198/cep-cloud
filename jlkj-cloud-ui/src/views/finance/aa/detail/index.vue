<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"
             v-show="showSearch" label-width="98px">
      <el-form-item label="节点指示码" prop="leafId">
        <el-input
          disabled
          v-model="queryParams.leafId"
          placeholder="请输入节点指示码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点名称" prop="nodeName">
        <el-input
          disabled
          v-model="queryParams.nodeName"
          placeholder="请输入节点名称"
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
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['aa:detail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAddTCapitalDetail">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleRefresh"
          v-hasPermi="['aa:detail:edit']"
        >刷新缓存</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-form ref="form" :model="form" :rules="rules">
    <el-table v-loading="loading" :data="form.detailList"
              @selection-change="handleSelectionChange"
              :row-class-name="rowTCapitalDetailIndex"
             >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="dataSeq" width="80">
        <template slot-scope="scope">
            <el-input v-model="scope.row.dataSeq"  placeholder="请输入序号"/>
        </template>
      </el-table-column>
      <el-table-column
        v-for="(item,index) in tableColumns"
        :key="index"
        :prop="item.nameEn"
        align='center'
        :label="item.name">
        <template slot-scope="scope">
            <el-input v-model="scope.row[item.nameEn]"
                      class="name-input"></el-input>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['aa:detail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['aa:detail:remove']"
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

  </div>
</template>

<script>

import { listDetail,  delDetail, addDetail, updateDetail } from "@/api/finance/aa/detail";
import { listBaseTab } from "@/api/finance/aa/base";
export default {
  name: "Detail",
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
      // 系统选单-明细设定表格数据
      detailList: [],
      // 子表选中数据
      checkedTCapitalDetail: [],
      tableColumns: [

      ],
      tdetailList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        leafId: null,
        nodeName:null,
      },
      // 表单参数
      form: {detailList:[]},
      // 表单校验
      rules: {
      },
      companyid:null,
      leafId:null,
      isAddCol: true,
    };
  },
  props: ['nodeNo','nodeName','companyId'],
  mounted() {
    this.$watch('nodeNo', function (newVal) {
      this.queryParams.leafId = newVal;
      this.leafId = newVal;
      this.form.leafId = newVal;
    })
    this.$watch('nodeName', function (newVal) {
      this.queryParams.nodeName = newVal;
    })
    this.$watch('companyId', function (newVal) {
      this.queryParams.companyId = newVal;
      this.companyid= newVal;
    })
  },
  created() {
    this.getList()
    this.getListBase();
  },
  methods: {

    /** 查询系统选单-明细设定列表 */
    getList() {
      this.loading = true;
      listDetail(this.queryParams).then(response => {
        this.form.detailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 根据基本页资料查询表头 */
    getListBase() {
      this.loading = true;
      listBaseTab(this.queryParams).then(response => {
        if(response.data.length>0){
          this.tableColumns= response.data
          this.loading = false;
        }else {
          this.tableColumns=[]
          this.$message.error('请至少保存一笔基本资料');
        }
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
        leafId: null,
        itema: null,
        itemb: null,
        itemc: null,
        itemd: null,
        iteme: null,
        dataa: null,
        datab: null,
        datac: null,
        datad: null,
        datae: null,
        dataf: null,
        datag: null,
        dataSeq: null,
        status: null,
        createBy: null,
        createDate: null,
        updateBy: null,
        updateDate: null,
        createTime: null,
        updateTime: null,
        createName: null,
        updateName: null
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
    /** 序号 */
    rowTCapitalDetailIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.checkedTCapitalDetail = selection.map(item => item.index)
      this.leafId = selection.map(item => item.leafId)
      this.tdetailList=selection
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 细项维护添加按钮操作 */
    handleAddTCapitalDetail() {
      let item = {
        id: null,
        companyId: null,
        leafId: null,
        itema: null,
        itemb: null,
        itemc: null,
        itemd: null,
        iteme: null,
        dataa: null,
        datab: null,
        datac: null,
        datad: null,
        datae: null,
        dataf: null,
        datag: null,
        dataSeq: null,
        status: null,
        createBy: null,
        createDate: null,
        updateBy: null,
        updateDate: null,
        createTime: null,
        updateTime: null,
        createName: null,
        updateName: null
      };
      this.form.detailList.push(item);
    },

    detailGet(leafId,nodeName,companyId){
      if (leafId !=null){
        this.queryParams.leafId=leafId
        this.queryParams.nodeName=nodeName
        this.form.leafId=leafId
        this.form.companyId=companyId
      }
      this.getList()
      this.getListBase();
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.tdetailList.length == 0) {
        this.$modal.msgError("请至少勾选一笔数据");
        return
      }
      for (let i = 0; i < this.tdetailList.length; i++) {
        this.tdetailList[i].companyId = this.companyid
        this.tdetailList[i].leafId = this.form.leafId
        this.form.financeDetailList = this.tdetailList
      }
      addDetail(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      updateDetail(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDetail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetail(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleRefresh(){
      this.$modal.msgSuccess("刷新成功");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      if (row.id == null||row.id==''||row.id==undefined){
        if (this.checkedTCapitalDetail.length == 0) {
          this.$modal.msgError("请先选择要删除的数据");
        } else {
          const detailList = this.form.detailList;
          const checkedTCapitalDetail = this.checkedTCapitalDetail;
          this.form.detailList = detailList.filter(function (item) {
            return checkedTCapitalDetail.indexOf(item.index) == -1
          });
        }
      }else {
        const ids = row.id;
        this.$modal.confirm('是否确认删除序号为"' + row.dataSeq + '"的数据项？').then(function () {
          return delDetail(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      }

    /*  const leafId = this.leafId;
      this.$modal.confirm('是否确认删除系统选单-明细设定编号为"' +leafId + '"的数据项？').then(function() {
        return delDetail(leafId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});*/
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('aa/detail/export', {
        ...this.queryParams
      }, `detail_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
