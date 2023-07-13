<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" status-icon :rules="rules" :inline="true" label-width="102px">
      <el-form-item>
        <div class="block">
          <el-date-picker
            v-model="queryParams.datePicker1" type="daterange" value-format="yyyy-MM-dd" range-separator="至"
            start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['consume:output:query']" type="primary" size="mini" icon="el-icon-search"
                   @click="handleQuery">搜 索
        </el-button>
        <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleEmpty">重 置
        </el-button>
        <el-button type="text" icon="el-icon-info" @click="handleAdvanceFilter">高级筛选
        </el-button>
      </el-form-item>
      <el-drawer class="el-icon-info" title="高级筛选" :append-to-body="true" :before-close="handleClose"
                 :visible.sync="drawer" direction="rtl" custom-class="demo-drawer" :wrapperClosable="true">
        <div class="demo-drawer__content">
          <el-form ref="queryData" :model="queryParams" status-icon :rules="rules" label-width="120px">
            <el-row :gutter="20">
              <el-col :span="24" style="margin-bottom: 0;">
                <div class="el-form-item__content">
                  <el-row>
                    <el-col style="display: flex;">
                      <el-form-item label="能源计量日期" prop="eCountDateStart">
                        <el-date-picker style="width: 83%!important;" v-model="queryParams.eCountDateStart"
                                        type="date" placeholder="选择日期">
                        </el-date-picker>
                      </el-form-item>
                      <div style="display: flex;margin-left: -21px;">
                        <div>~</div>
                        <el-form-item label="" prop="eCountDateEnd" label-width="0">
                          <el-date-picker style="width: 83%!important;" v-model="queryParams.eCountDateEnd" type="date"
                                          placeholder="选择日期">
                          </el-date-picker>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-form-item label="成本中心" prop="costCenterA">
                    <el-select v-model="queryParams.costCenterA" placeholder="请选择" style="width: 40%!important;">
                      <el-option v-for="item in optionsCostCenterA" :key="item.key" :label="item.label"
                                 :value="item.value">
                      </el-option>
                    </el-select>
                    &nbsp;、
                    <el-select v-model="queryParams.costCenterB" placeholder="请选择" style="width: 40%!important;">
                      <el-option v-for="item in optionsCostCenterB" :key="item.key" :label="item.label"
                                 :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="能源代码">
                    <el-select v-model="queryParams.engyIdStart" placeholder="请选择" style="width: 40%!important;">
                      <el-option v-for="item in optionsEngyIdStart" :key="item.key"
                                 :label="item.label" :value="item.value">
                      </el-option>
                    </el-select>
                    &nbsp;&nbsp;~
                    <el-select v-model="queryParams.engyIdEnd" placeholder="请选择"
                               style="width: 40%!important;">
                      <el-option v-for="item in optionsEngyIdEnd" :key="item.key" :label="item.label"
                                 :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" style="margin-bottom: 0;">
                <el-form-item label="排序方式" prop="orderByNo">
                  <el-select v-model="queryParams.orderByNo" :popper-append-to-body="false" placeholder="请选择"
                             style="width: 40%!important;">
                    <el-option v-for="item in optionsOrderByNo" :key="item.key" :label="item.label"
                               :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="demo-drawer__footer" style="text-align: center">
            <el-button v-hasPermi="['consume:output:query']" type="primary" size="mini" icon="el-icon-search"
                       @click="handleSupperQuery">搜 索
            </el-button>
            <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleReset">重 置
            </el-button>
          </div>
        </div>
      </el-drawer>
    </el-form>

    <el-form style="margin:0px 20px 0px 0px ;">
      <el-table ref="tables" v-loading="loading" height="75vh" :data="tableData" stripe tooltip-effect="dark"
                :default-sort="{prop: 'proConQty', order: 'descending'}" show-summary :summary-method="getSummaries"
                sum-text="能源产耗总量">
        <el-table-column label="能源计量日期" align="center" prop="ecountDate" sortable/>
        <el-table-column label="产耗类别" align="center" prop="ioTypeId" sortable>
          <template v-slot="scope">
            <dict-tag :options="dict.type.energy_in_out_type" :value="scope.row.ioTypeId"/>
          </template>
        </el-table-column>
        <el-table-column label="成本中心" align="center" prop="costCenter" sortable/>
        <el-table-column label="能源代码" align="center" prop="engyId" sortable/>
        <el-table-column label="能源名称" align="center" prop="engyName"/>
        <el-table-column label="能源产耗量" align="center" prop="proConQty"/>
        <el-table-column label="计量单位" align="center" prop="engyUnit">
          <template v-slot="scope">
            <dict-tag :options="dict.type.aa_unit_baseunitid" :value="scope.row.engyUnit"/>
          </template>
        </el-table-column>
        <el-table-column label="热值系数" align="center" prop="calValue"/>
        <el-table-column label="热值系数单位" align="center" prop="calUnit"/>
      </el-table>
    </el-form>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
  </div>
</template>
<script>

import {queryInfo, queryEngyIds} from "@/api/energy/ee/costCenterDailyEnergyQuery";
import {dateFormat} from "@/utils/date";

export default {
  name: "costCenterDailyEnergyQuery",
  dicts: ['energy_in_out_type', 'aa_unit_baseunitid'],
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: false,
      // 是否显示抽屉
      drawer: false,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eCountDateStart: '',
        eCountDateEnd: '',
        costCenterA: undefined,
        costCenterB: undefined,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        orderByNo: undefined,
        datePicker1: [dateFormat(new Date(), 'yyyy-MM-dd'), dateFormat(new Date(), 'yyyy-MM-dd')],
      },
      table: {
        border: true
      },
      tableData: [],
      // 表单校验
      rules: {
        eCountDateStart: [
          {required: true, message: "开始日期不能为空", trigger: "blur"}
        ],
        eCountDateEnd: [
          {required: true, message: "结束日期不能为空", trigger: "blur"}
        ],
        orderByNo: [
          {required: true, message: "排序日期不能为空", trigger: "blur"}
        ]
      },
      optionsCostCenterA: [],
      optionsCostCenterB: [],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      optionsOrderByNo: [{
        value: '0A',
        label: '成本中心代号 , 能源计量日期 , 能源代码 , 耗用产出类别代号'
      }, {
        value: '0B',
        label: '能源计量日期 , 成本中心代号 , 能源代码 , 耗用产出类别代号'
      }, {
        value: '0C',
        label: '能源代码 , 能源计量日期 , 成本中心代号 , 耗用产出类别代号'
      }, {
        value: '0D',
        label: '耗用产出类别代号 , 能源代码 , 能源计量日期 , 成本中心代号'
      }],
    }
  },
  created() {
    this.getList();
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data;
      this.optionsEngyIdEnd = response.data;
      this.loading = false;
    })
  },
  updated() {
    this.$nextTick(() => {
      // tab 绑定ref 属性
      this.$refs["tables"].doLayout();
    });
  },
  methods: {
    getSummaries(param) {
      const {columns, data} = param;//这里可以看出，自定义函数会传入每一列，以及数据
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '能源产耗总量：';
          return;
        }
        if (column.property == "proConQty") {
          //页面分别统计 处理
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);

            return sums[index] = sums[index].toFixed(2);

          } else {
            sums[index] = ''; //如果列的值有一项不是数字，就显示这个自定义内容
          }
        } else {
          sums[index] = ''
        }
      });
      return sums;//最后返回合计行的数据
    },
    //查询
    handleQuery() {
      this.loading = true;
      this.queryParams.eCountDateStart = this.queryParams.datePicker1 == null ? '' : this.queryParams.datePicker1[0];
      this.queryParams.eCountDateEnd = this.queryParams.datePicker1 == null ? '' : this.queryParams.datePicker1[1];
      this.getList();
    },
    handleSupperQuery() {
      this.loading = true;
      this.getList();
    },
    // 高级筛选关闭
    handleClose(done) {
      this.queryParams = {};
      done();
    },
    // 高级筛选弹出
    handleAdvanceFilter() {
      this.drawer = true;
    },
    // 重置
    handleReset(formName) {
      this.queryParams = {
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 10,
        eCountDateStart: undefined,
        eCountDateEnd: undefined,
        costCenterA: undefined,
        costCenterB: undefined,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        orderByNo: undefined,
      };
    },
    // 清空
    handleEmpty(formName) {
      this.queryParams = {
        pageSize: 10,
        pageNum: 1,
        eCountDateStart: undefined,
        eCountDateEnd: undefined,
        costCenterA: undefined,
        costCenterB: undefined,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        orderByNo: undefined,
      }
      this.getList();
    },
    //获取数据刷新页面
    getList() {
      queryInfo(this.queryParams).then(response => {
        if (response != null) {
          this.tableData = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      })
    }
  }
}
</script>
