<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="180px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪检量线代码" prop="equipmenteAlloy">
            <el-input v-model="addForm.equipmenteAlloy">
              <i slot="suffix" class="el-input__icon el-icon-plus" @click="handleQueryPlus" style="cursor:pointer;"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结果检查形式" prop="resultCheckType">
            <el-select v-model="addForm.resultCheckType" :popper-append-to-body="false">
              <el-option v-for="item in optionsResultCheckType" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="X荧光检量线代码" prop="equipmentxGroup">
            <el-input v-model="addForm.equipmentxGroup">
              <i slot="suffix" class="el-input__icon el-icon-plus" @click="handleQueryPlus2" style="cursor:pointer;"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="直读、荧光结果发送状态" prop="sampleCost">
            <el-select v-model="addForm.allResultCheck" :popper-append-to-body="false">
              <el-option v-for="item in optionsAllResultCheck" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="统计权数" prop="staticNumber">
            <el-select v-model="addForm.staticNumber" :popper-append-to-body="false">
              <el-option v-for="item in optionsStaticNumber" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- "+"查询对话框 -->
    <el-dialog :title="title" v-if="openPlus" :visible.sync="openPlus" width="600px" :close-on-click-modal="false" class="customDialogStyle" append-to-body>
      <el-form label-width="170px" >
        <el-form-item label="试验元素表格前两码" prop="formatId">
          <el-input v-model="query.formatId" style="width: 60%" placeholder="模糊查询"></el-input>
          <el-button type="primary" size="medium" icon="el-icon-search" @click="handleQueryBtn" style="margin-left: 40px;">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table stripe
                height="55h"
                :data="tableDataPlus"
                tooltip-effect="dark"
                @row-click="singleElection"
                style="margin: 0 20px;width: auto;height: 400px;">
        <el-table-column label="" width="30">
          <template slot-scope="scope">
            <el-radio class="radio" v-model="templateSelection" :label="scope.$index">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="试验元素表格" align="center" prop="formatId" />
        <el-table-column label="试验元素表格说明" align="center" prop="formatMemo" />
      </el-table>
      <div style="margin-top: 10px;right: 0" class="avue-crud__pagination">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagePlus.currentPage"
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pagePlus.pageSize"
          :layout="pagePlus.layout"
          :total="pagePlus.total">
        </el-pagination>
      </div>
      <div slot="footer" class="el-dialog__footer1">
        <el-button type="primary" @click="submitFormPlus()">确 定</el-button>
        <el-button type="warning" @click="handleEmpty">重 置</el-button>
        <el-button @click="cancelPlus()">取 消</el-button>
      </div>
    </el-dialog>

    <!-- "+"查询对话框 -->
    <el-dialog :title="title" v-if="openPlus2" :visible.sync="openPlus2" width="600px" :close-on-click-modal="false" class="customDialogStyle" append-to-body>
      <el-form label-width="170px" >
        <el-form-item label="试验元素表格前两码" prop="formatId">
          <el-input v-model="query.formatId" style="width: 60%" placeholder="模糊查询"></el-input>
          <el-button type="primary" size="medium" icon="el-icon-search" @click="handleQueryBtn" style="margin-left: 40px;">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table stripe
                height="55h"
                :data="tableDataPlus"
                tooltip-effect="dark"
                @row-click="singleElection2"
                style="margin: 0 20px;width: auto;height: 400px;">
        <el-table-column label="" width="30">
          <template slot-scope="scope">
            <el-radio class="radio" v-model="templateSelection2" :label="scope.$index">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="试验元素表格" align="center" prop="formatId" />
        <el-table-column label="试验元素表格说明" align="center" prop="formatMemo" />
      </el-table>
      <div style="margin-top: 10px;right: 0" class="avue-crud__pagination">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagePlus.currentPage"
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pagePlus.pageSize"
          :layout="pagePlus.layout"
          :total="pagePlus.total">
        </el-pagination>
      </div>
      <div slot="footer" class="el-dialog__footer1">
        <el-button type="primary" @click="submitFormPlus()">确 定</el-button>
        <el-button type="warning" @click="handleEmpty">重 置</el-button>
        <el-button @click="cancelPlus()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryInfoGS } from "@/api/quality/ts/sampleData";

export default {
  name: "sampleDataFX",
  props: {
    formDataFX: Object
  },
  data(){
    return {
      templateSelection: '',
      // 是否显示弹出层
      openPlus: false,
      templateSelection2: '',
      // 是否显示弹出层
      openPlus2: false,
      // 弹出层标题
      title: "",
      tableDataPlus: [],
      pagePlus: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        formatId: '',
        pageSize: 20,
        pageNum: 1,
      },
      addForm: {
        equipmenteAlloy: undefined,
        equipmentxGroup: undefined,
        allResultCheck: undefined,
        staticNumber: undefined,
        resultCheckType: undefined,
      },
      rules: {},
      optionsAllResultCheck: [{
        value: 'W',
        label: 'W-警告'
      }, {
        value: 'E',
        label: 'E-错误'
      }, {
        value: 'C',
        label: 'C-确认'
      }],
      optionsStaticNumber: [{
        value: '1',
        label: '1'
      }, {
        value: '2',
        label: '2'
      }, {
        value: '3',
        label: '3'
      }, {
        value: '4',
        label: '4'
      }],
      optionsResultCheckType: [{
        value: 'GE',
        label: 'GE'
      }, {
        value: 'GK',
        label: 'GK'
      }, {
        value: 'GH',
        label: 'GH'
      }]
    }
  },
  watch: {
    formDataFX: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  methods: {
    /** 默认显示格式"+"按钮操作 */
    handleQueryPlus() {
      this.openPlus = true;
      this.title = "试验元素表格代码查询";
      queryInfoGS(this.query).then(response => {
        this.tableDataPlus = response.data.data.list
        this.pagePlus.total = response.data.data.total
      })
    },
    /** 默认显示格式"+"按钮操作 */
    handleQueryPlus2() {
      this.openPlus2 = true;
      this.title = "试验元素表格代码查询";
      queryInfoGS(this.query).then(response => {
        this.tableDataPlus = response.data.data.list
        this.pagePlus.total = response.data.data.total
      })
    },
    singleElection (row) {
      this.templateRadio = row.id;
      this.addForm.equipmenteAlloy = row.formatId
    },
    singleElection2 (row) {
      this.templateRadio = row.id;
      this.addForm.equipmentxGroup = row.formatId
    },
    // 分页-每页多少条
    handleSizeChange(val) {
      this.query.pageSize = val
      this.getList()
    },
    // 分页-当前页
    handleCurrentChange(val) {
      this.query.pageNum = val
      this.getList()
    },
    //"+"查询
    handleQueryBtn() {
      queryInfoGS(this.query).then(response => {
        this.tableDataPlus = response.data.data.list
        this.pagePlus.total = response.data.data.total
      })
    },
    // 确定
    submitFormPlus() {
      this.openPlus = false;
      this.openPlus2 = false;
    },
    // 重置
    handleEmpty() {
      this.query = {
        sampleClass: '',
        pageSize: 20,
        pageNum: 1,
      },
        this.templateSelection= '',
        this.templateSelection2= '',
        this.getList()
    },
    //获取数据刷新页面
    getList() {
      queryInfoGS(this.query).then(response => {
        this.tableDataPlus = response.data.data.list
        this.pagePlus.total = response.data.data.total
      })
    },
    // 取消
    cancelPlus() {
      this.openPlus = false;
      this.openPlus2 = false;
      this.query = {
        sampleClass: '',
        pageSize: 20,
        pageNum: 1,
      },
        this.templateSelection= '',
      this.templateSelection2= ''
    },
  },
  created() {
  },
  mounted() {
    this.$emit('update:formDataFX', this.addForm)
  }
}
</script>

<style scoped>
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-input {
  width: 250px;
}
/deep/ .is-in-pagination{
  width: 50px;
}
/deep/ .el-form-item__label {
  text-align: left;
  padding-left: 10px;
}
.el-dialog__footer1 {
  padding: 0px 20px 20px
}
</style>
