<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" :popper-append-to-body="false">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="题目类型" prop="name">
        <el-select
          v-model="form.type"
          placeholder="请选择题目类型"
        >
          <el-option
            v-for="dict in attendenceOptions.QuestionType"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题目" prop="banks">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入题目"
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
          v-hasPermi="['human:questions:add']"
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
          v-hasPermi="['human:questions:edit']"
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
          v-hasPermi="['human:questions:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:questions:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目" align="center" prop="name" />
      <el-table-column label="题目类型" align="center"  prop="type">
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.QuestionType" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center"  prop="isEnable">
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.IsYesNo" :value="scope.row.isEnable"/>
        </template>
      </el-table-column>
      <el-table-column label="所属题库" align="center" prop="banks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:questions:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:questions:remove']"
          >删除</el-button>
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

    <!-- 添加或修改试题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="题库" prop="banks">
              <el-select
                v-model="form.banks"
              >
                <el-option
                  v-for="item in banks"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="题目" prop="name">
          <el-input
            v-model="form.name"
            type="textarea"
            placeholder="请输入题目"
          />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="题目类型" prop="type">
                <el-select
                  v-model="form.type"
                  @change="(item) => (this.currentQuestionTye = item)"
                  placeholder="请选择题目类型"
                >
                  <el-option
                    v-for="dict in attendenceOptions.QuestionType"
                    :key="dict.dicNo"
                    :label="dict.dicName"
                    :value="dict.dicNo"
                  />
                </el-select>
              </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-show="currentQuestionTye == 'answer'" label="正确答案" prop="answer">
              <el-input
                v-model="form.answer"
                type="textarea"
                placeholder="请输入问答题答案"
              />
            </el-form-item>
            <el-form-item v-show="currentQuestionTye == 'verdict'" label="正确答案" prop="answer">
              <el-radio-group v-model="form.answer">
                <el-radio label="1">正确</el-radio>
                <el-radio label="0">错误</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item v-show="currentQuestionTye == 'select' || currentQuestionTye == 'selects'" label="正确答案"
                          prop="answer">
              <el-select v-show="currentQuestionTye == 'select'" v-model="form.answer" placeholder="请选择单选题答案">
                <el-option
                  v-for="item in formlist.list"
                  :key="item.$id"
                  :label="item.option"
                  :value="item.$id"
                >
                </el-option>
              </el-select>
              <el-select v-show="currentQuestionTye == 'selects'" multiple v-model="form.selectAnswer"
                         placeholder="请选择多选题答案">
                <el-option
                  v-for="item in formlist.list"
                  :key="item.$id"
                  :label="item.option"
                  :value="item.$id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>

        <el-row v-show="currentQuestionTye == 'select' || currentQuestionTye == 'selects'"
                style="margin-top: 10px; margin-bottom: 10px">
          <el-button @click="addRow" type="primary">添加一行</el-button>
          <el-button @click="removeRow" type="danger">删除一行</el-button>
        </el-row>
        <el-form-item v-show="currentQuestionTye == 'select' || currentQuestionTye == 'selects'" label="题目选项">
          <el-form :model="formlist" label-position="top">
            <el-table ref="tableOptions" :data="formlist.list"
                      @selection-change="(data) => {this.currentSelectOption = data; }">
              <el-table-column type="selection" width="50"></el-table-column>
              <el-table-column prop="option" width="60"></el-table-column>
              <el-table-column prop="name">
                <template v-slot="scope">
                  <el-form-item>
                    <el-input v-model="scope.row.name" type="textarea" placeholder="请输入题目选项"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-form-item>
        <el-form-item label="试题解析" prop="analysis">
          <el-input
            v-model="form.analysis"
            type="textarea"
            placeholder="请输入试题解析"
          />
        </el-form-item>
        <el-form-item label="是否启用" prop="isEnable">
          <el-switch
            v-model="form.isEnable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="启用"
            inactive-text="未启用"
            inactive-value="0"
            active-value="1">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestions, getQuestions, delQuestions, addQuestions, updateQuestions } from "@/api/human/ex/questions";
import { getBankSelect } from "@/api/human/ex/questionBank";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "Questions",
  components: {DictTagHuman},
  data() {
    return {

      //公司列表
      companyList:[],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['QuestionType','IsYesNo'],
      },
      //出勤选单选项列表
      attendenceOptions: {},
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
      // 试题表格数据
      questionsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        images: null,
        type: null,
        answer: null,
        analysis: null,
        isEnable: null,
        deptid: null,
        compId: null,
        labels: null,
        banks: null
      },
      currentQuestionTye: "",
      //当前选中的单选题或者多选题的
      currentSelectOption: "",
      currentIndex: 1,
      // 表单参数
      form: {
        list: [], //多选题或者单选题的选项
        banks: [], //题库
      },
      formlist: {
        list: [
          {$id: 0, name: "", option: "A"},
          {$id: 1, name: "", option: "B"},
          {$id: 2, name: "", option: "C"},
          {$id: 3, name: "", option: "D"}
        ]
      },
      // 表单校验
      rules: {
      },
      //登录人信息
      user: {},
      //题库列表
      banks: [],
      //题库的下拉框的搜索列表
      bankList: [],
    };
  },
  created() {
    this.initData();
    this.getList();
    this.getDisc();
    this.getCompanyList();
    this.getQuestionBankList();

  },
  methods: {
    /** 查询题库列表 */
    getQuestionBankList() {
      getBankSelect(this.queryParams.compId).then((res) => {
        this.bankList = res.rows;
        this.banks = res.rows;
      });
    },
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    //题库的搜索监听
    remoteMethodBanks(query) {
      let ary = this.bankList.filter((item) => {
        if (item.name.indexOf(query) >= 0) {
          return item;
        }
      });
      this.banks = ary;
    },
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId;
      console.log("this.queryParams.compId",this.queryParams.compId);
      this.form.compId =  this.queryParams.compId
    },
    /** 查询出勤字典 */
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    /** 查询试题列表 */
    getList() {
      this.loading = true;
      listQuestions(this.queryParams).then(response => {
        this.questionsList = response.rows;
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
        name: null,
        images: null,
        type: "", //试题类型
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deptid: null,
        compId: null,
        labels: null,

        banks: [], //题库
        answer: "", //问答题答案
        selectAnswer: [],//多选题答案
        analysis: "", //试题的解析
        isEnable: "", //是否启用
        list: [], //多选题或者单选题的选项

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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.currentQuestionTye = null;
      this.open = true;
      this.title = "添加试题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getQuestions(id).then(response => {
        this.form = response.data;
        //回显设置题目的类型
        this.currentQuestionTye = response.data.type
        if(this.currentQuestionTye == "selects"){
          debugger
          let answer =  this.form.answer;
          let strs = [];
          strs=  answer.split(",");
          this.form.selectAnswer = strs;
        }
        this.open = true;
        this.title = "修改试题";
      });
    },
    //新增数据提交前的初始化
    submitFormInit() {
      //根据题目类型判断，清空其他的答案数据
      if (this.currentQuestionTye == "selects") {
        // 多选题
        this.form.answer = null;
        this.form.list = this.formlist.list;

        let strs = [];
        this.form.list.forEach((item) => {
          this.form.selectAnswer.forEach((obj, index) => {
            if (item.$id == obj) {
              strs.push(item.option);
            }
          });
        });
        this.form.selectAnswer.length = 0;
        this.form.selectAnswer = strs;
        this.form.answer = strs.toString();
      } else if (this.currentQuestionTye == "select") {
        //单选题
        this.form.list = this.formlist.list;
        this.form.selectAnswer = [];
        if (this.form.id != null) {
          this.form.list.some((item) => {
            if (item.option == this.form.answer) {
              this.form.answer = item.$id;
            }
          });
        } else {
          this.form.list.some((item) => {
            if (item.$id == this.form.answer) {
              this.form.answer = item.option;
            }
          });
        }

      } else if (this.currentQuestionTye == "verdict") {
        //判断题答案
        this.form.selectAnswer = null;
        this.form.list = [];
        this.formlist.list = [{$id: 0, name: "", option: "A"}];
      } else if (this.currentQuestionTye == "answer") {
        //问答题答案
        this.form.selectAnswer = null;
        this.form.list = [];
        this.formlist.list = [{$id: 0, name: "", option: "A"}];
      } else {
        this.form.selectAnswer = null;
        this.form.list = [];
        this.formlist.list = [];
      }
      //是否启用
      this.form.isEnable = this.isEnable == true ? 0 : 1;
    },
    /** 提交按钮 */
    submitForm() {
      this.submitFormInit();
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.compId=this.queryParams.compId;
          if (this.form.id != null) {
            updateQuestions(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQuestions(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除试题编号为"' + ids + '"的数据项？').then(function() {
        return delQuestions(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/questions/export', {
        ...this.queryParams
      }, `questions_${new Date().getTime()}.xlsx`)
    },
    // 采购计划明细信息物料栏位输入建议
    querySearch(queryString, cb) {
      var resourceNames = this.resourceNames;
      var results = queryString ? resourceNames.filter(this.createFilter(queryString)) : resourceNames;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    handleSelect(item) {
      this.resourcePurchasePlanDetailList[this.rowNum].planDataName = item.planDataName;
    },
    //多选或者单选的题目选项添加一行
    addRow() {
      let list = this.formlist.list;
      if (list.length == 6) {
        this.$message.error("最多只能添加7个选项");
      } else {
        let obj = {name: "", option: "", $id: this.currentIndex};
        this.currentIndex = this.currentIndex + 1;
        list.push({name: "", option: "", $id: this.currentIndex});
        this.optionInit();
      }
    },
    //多选单选的题目选项的移除一行
    removeRow() {
      this.currentSelectOption.forEach((element) => {
        this.formlist.list.forEach((item, index) => {
          if (item.$id == element.$id) {
            this.formlist.list.splice(index, 1);
          }
        });
      });
      //
      this.optionInit();
    },
    //选项的ABCD数据初始化
    optionInit() {
      this.formlist.list = this.formlist.list.map((item, index) => {
        switch (index) {
          case 0:
            item.option = "A";
            break;
          case 1:
            item.option = "B";
            break;
          case 2:
            item.option = "C";
            break;
          case 3:
            item.option = "D";
            break;
          case 4:
            item.option = "E";
            break;
          case 5:
            item.option = "F";
            break;
          case 6:
            item.option = "G";
            break;
        }
        return item;
      });
    },
  }
};
</script>
