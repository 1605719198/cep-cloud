<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryParams" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="停机记录编号" prop="mgms1No">
        <el-input style="width: 200px" v-model="queryParams.mgms1No" placeholder="请输入停机记录编号" clearable  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button size="mini"  icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" size="mini" plain  icon="el-icon-plus" @click="handleAdd">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-row>
      <el-col :span="9">停机记录编号：{{form.mgms1No}}</el-col>
      <el-col :span="5">停机总时间：{{totalTime}}</el-col>
      <el-col :span="5">未分摊责任时间：{{unShareTime}}</el-col>
    </el-row>

    <el-table stripe v-loading="loading" :data="dataList">
      <el-table-column label="责任分摊编号" align="center" prop="mgmsaNo" />
      <el-table-column label="责任分摊名称" align="center" prop="mgmsaName" />
      <el-table-column label="停机细类型" align="center" prop="mgmc1Name" />
      <el-table-column label="停机原因" align="center" prop="mgmc2Name" />
      <el-table-column label="停机责任时间(分)" align="center" prop="shareTime" />
      <el-table-column label="停机责任百分比(%)" align="center" prop="sharePercent" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
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

    <!-- 添加或修改参数配置对话框 -->
    <div v-if="open">
      <el-dialog :title="title" class="customDialogStyle" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
        <el-form ref="form" :model="form" :rules="rules" label-width="150px" :inline="true" class="demo-form-inline">
          <el-form-item label="停机纪录编号">
            <el-input disabled v-model="form.mgms1No" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="产线编号" >
            <el-input disabled v-model="form.mgma1No" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="停机总时间">
            <el-input disabled v-model="form.totalTime" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="未分摊责任时间" >
            <el-input disabled v-model="form.unShareTime" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="责任分摊编号" >
            <el-input disabled v-model="form.mgmsaNo" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="责任分摊名称" prop="mgmsaName">
            <el-input v-model="form.mgmsaName" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="停机类型" prop="selectMgmc1Name">
            <el-input placeholder="请输入内容" v-model="form.selectMgmc1Name" class="input-with-select" disabled style="width: 120px" />
            <el-select v-model="form.mgmc1Name" placeholder="请选择" style="width: 520px !important" @change="mgmc1noselection">
              <el-option
                v-for="item in shutdowndetailline"
                :key="item.value"
                :label="item.label"
                :value="{ value: item.value, label: item.label }"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="停机原因名称" prop="mgmc2Name">
            <el-select v-model="form.mgmc2Name" placeholder="请选择" style="width: 240px !important" @change="mgmc2noselection">
              <el-option
                v-for="item in mgmc2noOptions"
                :key="item.value"
                :label="item.label"
                :value="{ value: item.value, label: item.label }"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="停机责任时间" prop="shareTime">
            <el-input type="number" :min="1" v-model="form.shareTime" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="停机责任百分比" prop="sharePercent">
            <el-input type="number" min="0" disabled v-model="form.sharePercent" style="width: 240px"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm" :loading="states">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  querydetails,
  querymgmc2no,
  querymgmc1no,
  queryInfo,
  addInfo,
  queryone,
  delInfo,
  updateInfo
} from "@/api/equip/mg/responsibilitySharing";
export default {
  name: "responsibilitySharing",
  data() {
    // 停机责任时间判断
    var validateNum = (rule,value,callback) => {
      if ( !(value > 0)) {
        callback(new Error("停机责任时间必须大于0！"))
      }
      callback()
    }
    return {
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //提交状态
      states:false,
      //停机总时间
      totalTime: 0,
      // 未分摊责任时间
      unShareTime: 0,
      // 停机类型
      selectMgmc1Name: '',
      // 加载
      loading: false,
      //停机细则编号
      mgmc2noOptions: [],
      //停机细类行
      shutdowndetailline: [],
      // 表格数据
      dataList: [],
      // 弹出层标题
      title: "责任分摊",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 10,
        // 停机记录编号
        mgms1No: undefined,
      },
      // 表单参数
      form: {
        // 停机纪录编号
        mgms1No: undefined,
        // 产线编号
        mgma1No: undefined,
        // 停机责任时间
        totalTime: undefined,
        // 未分摊时间
        unShareTime: undefined,
        // 停机责任分摊编号
        mgmsaNo: undefined,
        // 停机责任分摊名称
        mgmsaName: undefined,
        // 停机类型编号
        mgmc1No: undefined,
        // 停机类型名称
        mgmc1Name: undefined,
        // 停机原因编号
        mgmc2No: undefined,
        // 停机原因名称
        mgmc2Name: undefined,
        // 停机责任时间
        shareTime: undefined,
        // 停机责任百分比
        sharePercent: undefined,
      },
      // 表单校验
      rules: {
        mgmsaName: [
          { required: true, message: "分摊名称不能为空", trigger: "blur" },
        ],
        mgmc1Name: [
          { required: true, message: "停机类型不能为空", trigger: "change" },
        ],
        mgmc2Name: [
          { required: true, message: "停机原因编号不能为空", trigger: "change" },
        ],
         shareTime: [
          { required: true, message: "分摊时间不能为空", trigger: "blur" },
           {validator: validateNum,trigger: "blur" }
        ],
      },
    };
  },
  created() {
    const mgms1No = this.$route.params;
    this.queryParams.mgms1No = mgms1No.mgms1No;
    this.getdetails(mgms1No);
    this.getList();
  },
  watch: {
    "form.shareTime": function (val) {
      this.form.sharePercent = ((val / this.form.totalTime) * 100).toFixed(2);
    },
  },
  methods: {
    /** 查询立案详细 */
    getdetails(params) {
      querydetails(params).then((response) => {
        if (response.data != null) {
          this.form = response.data.data
          this.form.unShareTime = response.data.unShareTime
          this.form.selectMgmc1Name=response.data.data.mgmc1Name
        }
        this.unShareTime = this.form.unShareTime
        this.totalTime = this.form.totalTime
        this.selectMgmc1Name = this.form.selectMgmc1Name
        this.form.id = undefined
        this.form.mgmc1Name = undefined
        this.form.mgmc2Name = undefined
        this.form.mgmc2No = undefined
        this.getquerymgmc1no()
      });
    },
    /** 查询停机细类型 */
    getquerymgmc1no() {
      querymgmc1no(this.form.mgmc1No).then((response) => {
        this.shutdowndetailline = response.data;
      });
    },
    /** 查询责任分摊列表 */
    getList() {
      queryInfo(this.queryParams).then((response) => {
        this.dataList = response.data.records;
        this.total = response.data.total;
      });
    },
    //取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
     this.resetForm("queryForm")
     this.getList();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
      this.reset();
      this.getdetails(this.queryParams);
      this.title = "添加责任分摊数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true
      this.title = "修改责任分摊数据"
      this.mgmc2noOptions = []
      queryone(row.id).then((response) => {
        this.form = response.data
        this.form.totalTime = this.totalTime
        this.form.unShareTime = this.unShareTime
        this.form.selectMgmc1Name = this.selectMgmc1Name
        querymgmc2no(this.form.mgmc1No).then(response => {
          this.mgmc2noOptions = response.data
        })
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
           this.states=true
          if (this.form.id != undefined) {
            updateInfo(this.form).then((response) => {
              this.states=false
              this.$modal.msgSuccess("修改成功")
              this.getdetails(this.queryParams);
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.states=false
              this.$modal.msgSuccess("添加成功")
              this.getdetails(this.queryParams);
              this.open = false;
              this.getList();
            });
          }
        }
        else {
          this.$modal.msgWarning("请完善信息");
          return false;
          }
      });
    },
    // 停机类型change事件
    mgmc1noselection(val) {
      // 下拉选单重置
      this.form.mgmc2Name = undefined
      this.form.mgmc2No = undefined
      this.form.mgmc1Name = val.label
      this.form.mgmc1No = val.value
      querymgmc2no(val.value).then((response) => {
        this.mgmc2noOptions = response.data
      });
    },
    //停机类型change事件
    mgmc2noselection(val) {
      this.form.mgmc2Name = val.label
      this.form.mgmc2No = val.value
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除？', {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        delInfo(row.id).then((response) => {
          this.$modal.msgSuccess("删除成功")
          this.getdetails(this.queryParams);
          this.getList();
        });
      }).catch(() => {});
    },
  },
};
</script>

<style scoped>
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
</style>
<style>
.el-drawer__body {
  margin-left: 5px;
}

.el-drawer__container {
  position: absolute;
}

#el-drawer__title::before {
  content: "\e7a1";
}

.el-drawer__header {
  background-color: #eee;
  padding: 10px;
}

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

