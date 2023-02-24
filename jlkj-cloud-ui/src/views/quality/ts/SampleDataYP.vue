<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="180px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="样品类别号" prop="sampleClass">
            <el-input v-model="addForm.sampleClass" />
            <span class="el-text"> *</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="取样工位" prop="sampleFrom">
            <el-input v-model="addForm.sampleFrom">
              <i slot="suffix" class="el-input__icon el-icon-plus" @click="handleQuerySampleFrom" style="cursor:pointer;"></i>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="样品类别描述" prop="sampleDesc">
            <el-input v-model="addForm.sampleDesc" />
            <span class="el-text"> *</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="样品测试成本" prop="sampleCost">
            <el-input v-model="addForm.sampleCost" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="默认显示格式" prop="defaultFormat">
            <el-input v-model="addForm.defaultFormat">
              <i slot="suffix" class="el-input__icon el-icon-plus" @click="handleQueryPlus" style="cursor:pointer;"></i>
            </el-input>
            <span class="el-text"> *</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否自动传输" prop="methodPrepare">
            <el-radio-group v-model="addForm.methodPrepare">
              <el-radio
                v-for="item in optionsMethodPrepare"
                :key="item.value"
                :label="item.label"
              >{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="料号" prop="materialNo">
            <el-input v-model="addForm.materialNo" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否辐射检验" prop="rayCheck">
            <el-radio-group v-model="addForm.rayCheck">
              <el-radio
                v-for="item in optionsRayCheck"
                :key="item.value"
                :label="item.label"
              >{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="同一熔炼样品显示顺序" prop="sampleDisplaySequence">
            <el-select v-model="addForm.sampleDisplaySequence" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsSampleDisplaySequence" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="各样品类别归类" prop="sampleClassification">
            <el-select v-model="addForm.sampleClassification" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsSampleClassification" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="新增人员" >
          <span>{{ addForm.createUser }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="新增时间" >
          <span> {{ addForm.createTime }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="修改人员" >
          <span>{{ addForm.updateUser }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改时间" >
          <span> {{ addForm.updateTime }}</span>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <!-- 默认显示格式"+"查询对话框 -->
    <el-dialog :title="title" v-if="openPlus" :visible.sync="openPlus" width="1000px" :close-on-click-modal="false" class="customDialogStyle" append-to-body >
      <el-form label-width="170px" >
        <el-form-item label="试验元素表格前两码" prop="formatId">
          <el-input v-model="query.formatId" style="width: 231px" placeholder="模糊查询"></el-input>
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

    <!-- 取样工位"+"查询对话框 -->
    <el-dialog :title="title" v-if="openPlus2" :visible.sync="openPlus2" width="1000px" :close-on-click-modal="false" class="customDialogStyle" append-to-body>
      <el-table stripe
                height="55h"
                :data="tableDataPlus1"
                tooltip-effect="dark"
                @row-click="singleElection1"
                style="margin: 0 20px;width: auto;height: 400px;">
        <el-table-column label="" width="30">
          <template slot-scope="scope">
            <el-radio class="radio" v-model="templateSelection1" :label="scope.$index">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="取样工位代码" align="center" prop="dictValue" />
        <el-table-column label="取样工位说明" align="center" prop="dictLabel" />
      </el-table>
      <div slot="footer" class="el-dialog__footer1">
        <el-button type="primary" @click="submitFormPlus()">确 定</el-button>
        <el-button @click="cancelPlus()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryInfoGS } from "@/api/quality/ts/sampleData";

export default {
  name: "sampleDataYP",
  props: {
    formDataYP: Object
  },
  data(){
    var checkA = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('样品类别号栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 30) {
          callback(new Error('样品类别号参数长度为 30 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkB = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('样品类别描述栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 20) {
          callback(new Error('样品类别描述参数长度为 20 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkC = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('默认显示格式栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 10) {
          callback(new Error('默认显示格式参数长度为 10 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkD = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 8) {
          callback(new Error('取样工位参数长度为 8 ，请重新输入！'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    var checkE = (rule, value, callback) => {
      if (value != null) {
        if (isNaN(value)) {
          callback(new Error('请输入数字值！'));
        } else {
          if (value.length > 6) {
            callback(new Error('样品测试成本参数长度为 6 ，请重新输入！'));
          } else {
            callback();
          }
        }
      } else {
        callback();
      }
    };
    var checkF = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 5) {
          callback(new Error('料号参数长度为 5 ，请重新输入！'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      templateSelection: '',
      templateSelection1: '',
      // 是否显示弹出层
      openPlus: false,
      // 是否显示弹出层
      openPlus2: false,
      // 弹出层标题
      title: "",
      pagePlus: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        pageSize: 20,
        pageNum: 1,
        formatId: '',
      },
      addForm: {
        sampleClass: undefined,
        sampleFrom: undefined,
        sampleDesc: undefined,
        sampleCost: undefined,
        defaultFormat: undefined,
        methodPrepare: undefined,
        materialNo: undefined,
        rayCheck: undefined,
        sampleDisplaySequence: undefined,
        sampleClassification: undefined,
        createEmployNo: undefined,
        createUser: undefined,
        creatDate: undefined,
        createTime: undefined,
        modifyEmployNo: undefined,
        updateUser: undefined,
        modifyDate: undefined,
        updateTime: undefined
      },
      tableDataPlus: [],
      tableDataPlus1: [],
      optionsSampleClassification: [],
      rules: {
        sampleClass: [
          { validator: checkA, trigger: 'blur' }
        ],
        sampleDesc: [
          { validator: checkB, trigger: 'blur' }
        ],
        defaultFormat: [
          { validator: checkC, trigger: 'blur' }
        ],
        sampleFrom: [
          { validator: checkD, trigger: 'blur' }
        ],
        sampleCost: [
          { validator: checkE, trigger: 'blur' }
        ],
        materialNo: [
          { validator: checkF, trigger: 'blur' }
        ],
      },
      optionsMethodPrepare: [{
        value: 'Y',
        label: 'Y'
      }, {
        value: 'N',
        label: 'N'
      }],
      optionsRayCheck: [{
        value: 'Y',
        label: 'Y'
      }, {
        value: 'N',
        label: 'N'
      }],
      optionsSampleDisplaySequence: [{
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
      }, {
        value: '5',
        label: '5'
      }, {
        value: '6',
        label: '6'
      }, {
        value: '7',
        label: '7'
      }, {
        value: '8',
        label: '8'
      }, {
        value: '9',
        label: '9'
      }, {
        value: '10',
        label: '10'
      }],
    }
  },
  watch: {
    formDataYP: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  mounted() {
    this.$emit('update:formDataYP', this.addForm)
  },
  created() {
    this.getDicts("quality_sample_class").then(response =>{
      this.optionsSampleClassification = response.data.data;
    })
    this.getDicts("sample_from").then(response =>{
      this.tableDataPlus1 = response.data.data;
    })
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
    /** 点击行信息将formatId传回*/
    singleElection (row) {
      this.addForm.defaultFormat = row.formatId
      this.$set(this.addForm, 'defaultFormat', row.formatId)
    },
    /** 点击行信息将formatId传回*/
    singleElection1 (row) {
      this.addForm.sampleFrom = row.dictLabel
      this.$set(this.addForm, 'sampleFrom', row.dictLabel)
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
    //获取数据刷新页面
    getList() {
      queryInfoGS(this.query).then(response => {
        this.tableDataPlus = response.data.data.list
        this.page.total = response.data.data.total
      })
    },
    //"+"查询
    handleQueryBtn() {
      queryInfoGS(this.query).then(response => {
        this.tableDataPlus = response.data.data.list
        this.pagePlus.total = response.data.data.total
      })
    },
    //"+"查询
    handleQueryGWBtn() {
      // queryInfoGW(this.query).then(response => {
      //   this.tableDataPlus = response.data.data.list
      //   this.pagePlus.total = response.data.data.total
      // })
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
        this.getList()
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
        this.templateSelection= ''
    },
    /** 取样工位"+"按钮操作 */
    handleQuerySampleFrom() {
      this.query = {
        sampleClass: '',
        pageSize: 20,
        pageNum: 1,
      },
        this.openPlus2 = true;
      this.title = "取样工位查询";
      // queryInfoGS(this.query).then(response => {
      //   console.log(response);
      //   this.tableDataPlus = response.data.data.list
      //   this.pagePlus.total = response.data.data.total
      // })
    },
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
  padding-left: 23px;
}
.el-dialog__footer1 {
  padding: 0px 20px 20px
}
</style>
