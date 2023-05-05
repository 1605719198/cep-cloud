<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24"
              style="padding:0 10px">
        <!--       table主体 -->
        <div class="plan_main">
          <div class="avue-crud el-card__body"
               style="width: 98%;border: 0">
            <el-row :gutter="20">
              <!--      树形结构数据-->
              <el-col :span="4"
                      :xs="24">
                <div class="head-container">
                  <el-select v-model="queryParams.companyId" style="margin-bottom: 20px;    width: 228px;" filterable placeholder="请输入会计公司">
                    <el-option
                      v-for="item in companyList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      @click.native="changCompanyId(item)"
                      @keyup.enter.native="handleQuery">
                    </el-option>
                  </el-select>
                  <el-input placeholder="搜索系统选单索引"
                            v-model="filterText"
                            style="margin-bottom: 20px">
                  </el-input>
                </div>

                <div class="head-container"
                     style="height:700px;overflow:auto;">
                  <el-tree class="filter-tree"
                           :data="treeData"
                           :props="defaultProps"
                           :filter-node-method="filterNode"
                           ref="tree"
                           @node-click="handleNodeClick">
                  </el-tree>
                </div>
              </el-col>
              <el-col :span="20"
                      :xs="24">
                <!--           条件搜索 -->
                <div class="avue-crud__search"
                     style="border: 0">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="节点名称" prop="nodeName">
        <el-input
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
                </div>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['aa:index:add']"
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
          v-hasPermi="['aa:index:edit']"
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
          v-hasPermi="['aa:index:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="indexList"  @row-dblclick="rowClick"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="节点编码" align="center" prop="nodeNo" />
      <el-table-column label="节点类型" align="center" prop="nodeType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.aa_costtype" :value="scope.row.nodeType"/>
        </template>
      </el-table-column>
      <el-table-column label="节点名称" align="center" prop="nodeName"    :show-overflow-tooltip='true'/>
      <el-table-column label="用途说明" align="center" prop="useDesc" />
      <el-table-column label="排列指示" align="center" prop="dataSeq" />
      <el-table-column label="节点授权" align="center" prop="nodeAuthgrp" />
      <el-table-column label="明细授权" align="center" prop="dataAuthgrp" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  width="250">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['aa:index:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['aa:index:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleBase(scope.row)"
            v-hasPermi="['aa:code:remove']"
          >基本设置
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleDetail(scope.row)"
            v-hasPermi="['aa:code:remove']"
          >明细设置
          </el-button>
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

    <!-- 添加或修改系统选单-索引设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px"  class="customDialogStyle"append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="106px">
        <el-row :gutter="20">
          <el-col :span="12">
        <el-form-item label="公司" prop="companyId">
          <el-select v-model="form.companyId" filterable placeholder="请输入会计公司">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="节点编码" prop="nodeNo">
          <el-select v-model="form.nodeNo"  placeholder="请选择节点编码"  style="width: 220px">
            <el-option
              v-for="dict in nodeNoList"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="节点类别" prop="nodeType">
          <el-select v-model="form.nodeType"  placeholder="请输入节点类别"  style="width: 220px">
            <el-option
              v-for="dict in dict.type.aa_costtype"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="节点名称" prop="nodeName">
          <el-input v-model="form.nodeName" placeholder="请输入节点名称"  style="width: 220px"/>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="列表指示" prop="dataSeq">
          <el-input v-model="form.dataSeq" placeholder="请输入排列指示"  style="width: 220px"/>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="节点授权" prop="nodeAuthgrp">
          <el-input v-model="form.nodeAuthgrp" placeholder="请输入节点资料操作授权群组" style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="明细授权" prop="dataAuthgrp">
          <el-input v-model="form.dataAuthgrp" placeholder="请输入明细资料操作授权群组" style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="用途说明" prop="useDesc">
          <el-input v-model="form.useDesc" placeholder="请输入用途说明"  style="width: 220px"/>
        </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listIndex, getIndex, delIndex, addIndex, updateIndex ,getTreeNode}
  from "@/api/finance/aa/index";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
export default {
  name: "Index",
  dicts: ['aa_costtype'],
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'nodeName'
      },

      // 树节点过滤
      filterText: '',
      // 树节点数据
      treeData: [],
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
      // 系统选单-索引设定表格数据
      indexList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nodeName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId:[
          {required: true, message: '请输入公司', trigger: 'blur'},
        ],
        nodeNo:[
          {required: true, message: '请输入节点编码', trigger: 'blur'},
        ],
        nodeType:[
          {required: true, message: '请输入节点类别', trigger: 'blur'},
        ],
        nodeName:[
          {required: true, message: '请输入节点名称', trigger: 'blur'},
          {max: 50, message: '最大长度为50个字符', trigger: 'blur'}
        ],
        nodeAuthgrp:[
          {max: 32, message: '最大长度为32个字符', trigger: 'blur'}
        ],
        dataAuthgrp:[
          {max: 32, message: '最大长度为32个字符', trigger: 'blur'}
        ],
        useDesc:[
          {max: 200, message: '最大长度为200个字符', trigger: 'blur'}
        ],
      },
      nodeNoList:[],
      nodeNo:'',
      parentId:'0000',
      parentNo:'',
      companyList:[],
      companyId:''
    };
  },
  created() {
    this.queryParams.parentId = "00000"
    this.getList();
    this.getCompanyList();
    this.initTree()
  },
  methods: {
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.initTree()
      });
    },
    //公司下拉选单掉用方法
    changCompanyId(val) {
      this.queryParams.companyId = val.value
      this.initTree()
    },
    initTree() {
      getTreeNode(this.queryParams.companyId).then(response => {
        this.treeData = response.data
      })
    },

    // 树节点搜索过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.levelName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.parentId = data.id
      this.parentNo = data.parentNo
      this.parentId = data.id
      this.nodeNo= data.nodeNo
      this.companyId = data.companyId
      this.getList()
    },
    /** 查询系统选单-索引设定列表 */
    getList() {
      this.loading = true;
      listIndex(this.queryParams).then(response => {
        this.indexList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.nodeNoList=[]
      for (let i =1;i<=100;i++){
        var obj = {};
        if (i<10){
          obj.label = '0'+i;
          obj.value = '0'+i;
        }else {
          obj.label = i;
          obj.value = i;
        }
        this.nodeNoList.push(obj)
      }

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
        parentNo: null,
        parentId: null,
        nodeNo: null,
        nodeType: null,
        nodeName: null,
        useDesc: null,
        dataSeq: null,
        nodeAuthgrp: null,
        dataAuthgrp: null,
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
    rowClick(row, column, e) {

        this.$emit('change', 'second', row.nodeNo, row.nodeName,row.companyId);
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
      this.open = true;
      this.title = "添加系统选单-索引设定";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIndex(id).then(response => {
        this.form = response.data;
        this.form.nodeNo=this.form.nodeNo.substring( this.form.nodeNo.length-2);
        this.open = true;
        this.title = "修改系统选单-索引设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
           this.$refs["form"].validate(valid => {
             if (valid) {
               this.form.parentId = this.parentId
               if (this.form.id != null) {
                    this.form.nodeNo = this.form.parentNo+ this.form.nodeNo
                 updateIndex(this.form).then(response => {
                   this.$modal.msgSuccess("修改成功");
                   this.open = false;
                   this.getList();
                   this.initTree()
                 });
               } else {
                 this.form.nodeNo = this.nodeNo+ this.form.nodeNo
                 this.form.parentNo =  this.nodeNo
                 addIndex(this.form).then(response => {
                   this.$modal.msgSuccess("新增成功");
                   this.open = false;
                   this.getList();
                   this.initTree()
                 });
               }
             }
           });
    },
    handleBase(row) {
      this.$emit('change', 'second', row.nodeNo, row.nodeName,row.companyId);
    },
    handleDetail(row) {
      this.$emit('change', 'third', row.nodeNo, row.nodeName,row.companyId);
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除系统选单-索引设定编号为"' + ids + '"的数据项？').then(function() {
        return delIndex(ids);
      }).then(() => {
        this.getList();
        this.initTree()
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('aa/index/export', {
        ...this.queryParams
      }, `index_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.link-type {
  color: #337ab7;
  cursor: pointer;
}

/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
/deep/ .el-table th.el-table__cell  .cell {
  display: inline-block;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  position: relative;
  vertical-align: middle;
  padding-left: 0px;
  padding-right: 0px;
  width: 100%;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
