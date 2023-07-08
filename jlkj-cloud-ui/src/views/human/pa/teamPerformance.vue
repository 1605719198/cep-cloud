<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="考核年月" prop="meritMonth">
            <el-date-picker
              v-model="queryParams.meritMonth"
              type="monthrange"
              value-format="yyyy-MM"
              range-separator="至"
              start-placeholder="开始月份"
              end-placeholder="结束月份"
              @change="dateFormat">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="考评类别" prop="meritType">
            <el-select v-model="queryParams.meritType" placeholder="考评类别" style="width: 100%">
              <el-option
                v-for="dict in performanceOptions.MeritType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="20">
          <el-col :span="4">
            <pagination
              v-show="teamPerformanceTotal>0"
              :total="teamPerformanceTotal"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              style="margin-top: -12px;margin-bottom: 24px;right: 48px"
              @pagination="getList"
            />
            <el-table v-loading="loading" :data="teamPerformanceList" border @row-click="getTeamPerformance">
              <el-table-column label="年月" align="center" prop="meritMonth" />
              <el-table-column label="类别" align="center" prop="meritType">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritType" :value="scope.row.meritType"/>
                </template>
              </el-table-column>
              <el-table-column label="状态" align="center" prop="meritStatus">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritStatus" :value="scope.row.meritStatus"/>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="20">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAdd"
                  v-hasPermi="['human:teamPerformance:add']"
                >新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  @click="handleDelete"
                  :disabled="form.meritStatus==='2'"
                  v-hasPermi="['human:teamPerformance:remove']"
                >删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-right"
                  size="mini"
                  @click="handleConfirm"
                  v-hasPermi="['human:teamPerformance:confirm']"
                >确认</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="考核年月" prop="meritMonth">
                    <el-date-picker
                      v-model="form.meritMonth"
                      value-format="yyyy-MM"
                      type="month">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评类别" prop="meritType">
                    <el-select v-model="form.meritType" placeholder="考评类别">
                      <el-option
                        v-for="dict in performanceOptions.MeritType"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="状态" prop="meritStatus">
                    <dict-tag-human :options="performanceOptions.TeamStatus" :value="form.meritStatus"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="输入人" prop="creator">
                    {{form.creator}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="输入日期" prop="createDate">
                    {{form.createDate}}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="1.5">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleUpdate" :disabled="form.meritStatus==='2'">修改</el-button>
                </el-col>
              </el-row>
            </el-form>
            <el-table v-loading="keyWorkItemsLoading" :data="processedData" :span-method="objectSpanMethod" max-height="300px" border>
              <el-table-column label="单位" align="center" prop="dept"/>
              <el-table-column label="指标项目" align="center" prop="item"/>
              <el-table-column label="目标值" align="center" prop="goalValue">
                <template v-slot="scope">
                  <el-input size="mini" v-model="scope.row.goalValue" placeholder="请输入目标值"/>
                </template>
              </el-table-column>
              <el-table-column label="评价标准" width="150" align="center" prop="standard">
                <template v-slot="scope">
                  <el-input size="mini" v-model="scope.row.standard" placeholder="请输入评价标准"/>
                </template>
              </el-table-column>
              <el-table-column label="权重%" width="75" align="center" prop="weight">
                <template v-slot="scope">
                  <el-input size="mini" v-model="scope.row.weight"  disabled placeholder="请输入权重"/>
                </template>
              </el-table-column>
              <el-table-column label="实际完成值" align="center" prop="resultValue">
                <template v-slot="scope">
                  <el-input size="mini" v-model="scope.row.resultValue" disabled/>
                </template>
              </el-table-column>
              <el-table-column label="得分" align="center" prop="score">
                <template v-slot="scope">
                  <el-input size="mini" v-model="scope.row.selfScore"/>
                </template>
              </el-table-column>
              <el-table-column label="数据提供单位" align="center" prop="dataDept">
                <template v-slot="scope">
                  <el-input size="mini" v-model="scope.row.dataDept" disabled/>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import {
  addTeamPerformance,
  delTeamPerformance,
  listTeamPerformance,
  listTeamPerformanceDetail,
  updateTeamPerformanceDetail, updateTeamPerformanceMeritStatus
} from "@/api/human/pa/teamPerformance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "TeamPerformance",
  components: {DictTagHuman },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 重点工作项遮罩层
      keyWorkItemsLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 团队绩效主档表格总条数
      teamPerformanceTotal: 0,
      // 团队绩效主档表格数据
      teamPerformanceList: [],
      // 重点工作项表格数据
      keyWorkItemsList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        meritMonth: null,
        meritType: null
      },
      // 明细表单参数
      form: {},
      // 表单校验
      rules: {
        meritMonth:[
          { required: true, message: "考核年月不能为空", trigger: ['blur', 'change'] }
        ],
        meritType:[
          { required: true, message: "考评类别不能为空", trigger: ['blur', 'change'] }
        ],
      },
      // 公司别下拉选单
      companyName: [],
      //登录人信息
      user: {},
      //绩效选单选项列表
      performanceOptions:{},
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:[
          'MeritType',
          'MeritStatus',
          'TeamStatus']
      },
      // 存放所有的表头 一定要与keyWorkItemsList一致
      colFields: [
        "dept",
        "item",
        "goalValue",
        "standard",
        "weight",
        "resultValue",
        "score",
        "dataDept"
      ],
      spanArr: [], //存储合并单元格的开始位置
      updateForm: {}
    };
  },
  created() {
    this.initData();
    this.getDisc();
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  computed:{
    // 计算小计数据
    processedData() {
      const result = [];
      // 小计数量
      let subtotal = 0;
      this.keyWorkItemsList.forEach((item, index) => {
        result.push(item);
        if (item.dept !== this.keyWorkItemsList[index + 1]?.dept){
          subtotal += item.score;
          result.push({ dept: null, item: null, goalValue: null, standard: null, weight: null, resultValue: '小计', score: subtotal, dataDept: null });
          subtotal = 0;
        }
      });
      return result;
    },
  },
  methods: {
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.user.empId = this.$store.state.user.userInfo.userName;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.queryParams.compId = this.user.compId
    },
    /** 查询团队绩效主档列表 */
    getList() {
      this.loading = true;
      listTeamPerformance(this.queryParams).then(response => {
        this.teamPerformanceList = response.data.rows;
        this.teamPerformanceTotal = response.data.total;
        this.loading = false;
      });
    },
    getDetailList() {
      this.keyWorkItemsLoading = true;
      listTeamPerformanceDetail(this.form).then(response => {
        this.keyWorkItemsList = response.data.rows;
        this.keyWorkItemsLoading = false;
        this.getSpanArr();
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
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.compId = this.queryParams.compId
          addTeamPerformance(this.form).then(res =>{
            this.$modal.msgSuccess("新增成功");
          })
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      delTeamPerformance(this.form).then(res => {
        this.form = {}
        this.getDetailList();
        this.getList();
        this.$modal.msgSuccess("删除成功");
    })
    },
    /** 查询人员类别考评项目设定明细档表格 */
    getTeamPerformance(row) {
      this.form = row
      this.getDetailList();
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.updateForm.keyWorkItemsList = this.keyWorkItemsList
      updateTeamPerformanceDetail(this.updateForm).then(res =>{
        this.getDetailList();
        this.$modal.msgSuccess("修改成功");
      })
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    /** 分析每一列，找出相同的 */
    getSpanArr() {
      for (let i = 0; i < this.keyWorkItemsList.length; i++) {
        let row = i;
        // let col = i % this.colCount;
        if (row === 0) {
          // i 表示行 j表示列
          for (let j = 0; j < this.colFields.length; j++) {
            this.spanArr[i * this.colFields.length + j] = {
              rowspan: 1,
              colspan: 1,
            };
          }
        } else {
          for (let j = 0; j < this.colFields.length; j++) {
            // 当前和上一次的一样
            if (
              this.colFields[j] == "dept"
            ) { // 指定合并哪些列

              if (
                this.keyWorkItemsList[row]["dept"] !==
                this.keyWorkItemsList[row - 1]["dept"]
              ) { // 哪些不合并:dept不一样的,不合并
                this.spanArr[row * this.colFields.length + j] = {
                  rowspan: 1,
                  colspan: 1,
                };
              } else if (
                this.keyWorkItemsList[row][this.colFields[j]] ===
                this.keyWorkItemsList[row - 1][this.colFields[j]]
              ) {
                let beforeItem =
                  this.spanArr[(row - 1) * this.colFields.length + j];
                this.spanArr[row * this.colFields.length + j] = {
                  rowspan: 1 + beforeItem.rowspan,// 合并几列
                  colspan: 1,// 合并几行
                };
                beforeItem.rowspan = 0;
                beforeItem.colspan = 0;
              } else {
                // rowspan 和 colspan 都为1表格此单元格不合并
                this.spanArr[row * this.colFields.length + j] = {
                  rowspan: 1,
                  colspan: 1,
                };
              }
            }


          }
        }
      }
      // 对数据进行倒序
      let stack = [];

      for (let i = 0; i < this.colFields.length; i++) {
        for (let j = 0; j < this.keyWorkItemsList.length; j++) {
          // console.log("i=" + i + " j=" + j);
          // i 表示列 j表示行
          if (j === 0) {
            if (this.spanArr[j * this.colFields.length + i].rowspan === 0) {
              stack.push(this.spanArr[j * this.colFields.length + i]);
            }
          } else {
            if (this.spanArr[j * this.colFields.length + i]?.rowspan === 0) {
              stack.push(this.spanArr[j * this.colFields.length + i]);
            } else {
              stack.push(this.spanArr[j * this.colFields.length + i]);
              while (stack.length > 0) {
                let pop = stack.pop();
                let len = stack.length;
                this.spanArr[(j - len) * this.colFields.length + i] = pop;
              }
            }
          }
        }
      }
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      return this.spanArr[rowIndex * this.colFields.length + columnIndex];
    },
    handleConfirm() {
      updateTeamPerformanceMeritStatus(this.form).then(res => {
        this.form.meritStatus = '2'
        this.$modal.msgSuccess("确认成功");
      })
    }
  }
};
</script>
<style scoped>
.divFather{
  display: flex;
  justify-content: flex-end;
  width: 100%
}
.page3_div{
  background-color: #f7f7f7;
  width: 100%;
  height: 300px;
  display: flex;
  flex-direction:row;

}
.page3_div_left{
  display: flex;
  flex-direction:row;
  justify-content:center;
  align-items: center;
  width: 40px;
  border-style: solid;
  border-color: #FFFFFF;
  border-width:3px ;
  writing-mode:vertical-lr;
}
</style>
