<template>
  <el-row>
    <el-col :span="24"
            style="padding:0 10px">
      <div class="energy_main">
        <div class="avue-crud el-card__body"
             style="width: 98%;border: 0">
          <div class="avue-crud__search"
               style="border: 0">
            <el-form ref="query"
                     :model="query"
                     :rules="rules">
              <el-row :gutter="20">
                <el-col :span="3">
                  <el-form-item label=""
                                prop="startDate">
                    <el-date-picker v-model="query.startDate"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="1"
                        style="margin: 0 -5px;width: 10px;padding: 0">
                  <span style="line-height: 35px;">~</span>
                </el-col>
                <el-col :span="3">
                  <el-form-item label=""
                                prop="endDate"
                                label-width="0">
                    <el-date-picker v-model="query.endDate"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="3">
                  <el-form-item label=""
                                prop="shift">
                    <el-select v-model="query.shift"
                               placeholder="选择班别"
                               :popper-append-to-body="false"
                               clearable>
                      <el-option v-for="dict in classNameOptions"
                                 :key="dict.dictValue"
                                 :label="dict.dictLabel"
                                 :value="dict.dictLabel">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="14">
                  <div class="el-form-item__content"
                       style="margin-left: 0px;">
                    <el-button v-hasPermi="['experimentRecord_queryListMc']"
                               size="mini"
                               type="primary"
                               icon="el-icon-search"
                               @click="handleQuery('query')">搜 索</el-button>
                    <el-button size="mini"
                               type="default"
                               icon="el-icon-refresh-left"
                               @click="handleEmpty">重 置</el-button>
                    <!--                  <el-button type="warning" size="medium" icon="el-icon-download" plain @click="handleExport">导 出</el-button>-->
                  </div>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div>
            <el-form>
              <el-table v-if="this.tableData.length > 0"
                        height="58vh"
                        size="small"
                        :data="tableData"
                        stripe
                        :default-sort="{prop: 'number', order: 'descending'}">
                <template v-for="column in columns">
                  <el-table-column :key="column.prop"
                                   :prop="column.prop"
                                   :label="column.label"
                                   :sortable="column.sortable"
                                   :width="column.width"
                                   :min-width="column.minWidth" />
                </template>
              </el-table>
              <el-table v-else
                        height="67.1vh"
                        size="small"
                        :data="tableData"
                        stripe
                        :default-sort="{prop: 'number', order: 'descending'}">
                <template v-for="column in columns">
                  <el-table-column :key="column.prop"
                                   :prop="column.prop"
                                   :label="column.label"
                                   :sortable="column.sortable"
                                   :width="column.width"
                                   :min-width="column.minWidth" />
                </template>
              </el-table>
              <table v-if="this.tableData.length > 0"
                     style="width: 100%"
                     class="myTable">
                <tr class="column">
                  <td colspan="3">
                    {{ '平均值' }}
                  </td>
                  <td width="151px"
                      v-for="(itemA,i) in this.averageList"
                      :key="'a'+i">
                    {{ itemA }}
                  </td>
                </tr>
                <tr class="column">
                  <td colspan="3">
                    {{ '最大值' }}
                  </td>
                  <td v-for="(itemB,i) in this.maxList"
                      :key="'b'+i">
                    {{ itemB }}
                  </td>
                </tr>
                <tr class="column">
                  <td colspan="3">
                    {{ '最小值' }}
                  </td>
                  <td v-for="(itemC,i) in this.minList"
                      :key="'c'+i">
                    {{ itemC }}
                  </td>
                </tr>
              </table>
              <div style="margin-top: 10px;right: 0"
                   class="avue-crud__pagination">
                <el-pagination background
                               @size-change="handleSizeChange"
                               @current-change="handleCurrentChange"
                               :current-page="page.pageNum"
                               :page-sizes="[20, 50, 100, 200]"
                               :page-size="page.pageSize"
                               :layout="page.layout"
                               :total="page.total">
                </el-pagination>
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { queryMcInfo } from "@/api/quality/ts/cokeQualityLog";

export default {
  name: "cokeQualityLog",
  data () {
    return {
      page: {
        pageNum: 1,
        pageSize: 20,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      classNameOptions: [],
      query: {
        pageNum: 1,
        pageSize: 20,
        startDate: undefined,
        endDate: undefined,
        shift: undefined,
        type: 'MC'
      },
      table: {
        border: true
      },
      rules: {
      },
      columns: [
        { label: '日期', prop: "sendTime", sortable: true, minWidth: '80px' },
        { label: '班别', prop: "shift", sortable: true, minWidth: '60px' },
        { label: '样品编号', prop: "sampleId", sortable: true, minWidth: '100px' },
        { label: '水份（%）', prop: "mt", sortable: true, minWidth: '70px' },
        { label: '灰份（%）', prop: "ad", sortable: true, minWidth: '70px' },
        { label: '挥发份（%）', prop: "vdaf", sortable: true, minWidth: '80px' },
        { label: '硫份（%）', prop: "stad", sortable: true, minWidth: '80px' },
        { label: 'M40（%）', prop: "m40", sortable: true, minWidth: '80px' },
        { label: 'M10（%）', prop: "m10", sortable: true, minWidth: '80px' },
        { label: 'CRI（%）', prop: "cri", sortable: true, minWidth: '80px' },
        { label: 'CSR（%）', prop: "csr", sortable: true, minWidth: '80px' },
      ],
      tableData: [],
      averageList: [],
      maxList: [],
      minList: [],
      start: Date,
      end: Date,
    }
  },
  created () {
    this.getDataTime()
    this.getDicts("sys_classtype").then(response => {
      this.classNameOptions = response.data;
    })
  },
  methods: {
    //查询
    handleQuery () {
      this.start = new Date(this.query.startDate)
      this.end = new Date(this.query.endDate)
      if (typeof (this.query.startDate) != 'undefined') {
        if (this.start <= this.end) {
          this.getList()
        } else {
          this.$message({ type: 'warning', message: '请输入正确的日期区间！' });
        }
      } else {
        this.getList()
      }
    },
    //获取数据刷新页面
    getList () {
      queryMcInfo(this.query).then(response => {
        console.log(response);
        this.tableData = response.data.list
        this.page.total = response.data.total
        this.averageList = response.data.averageList
        this.maxList = response.data.maxList
        this.minList = response.data.minList
        this.loading = false
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        pageNum: 1,
        pageSize: 20,
        startDate: undefined,
        endDate: undefined,
        shift: undefined,
        type: 'MC'
      }
    },
    /** 导出按钮操作 */
    handleExport () {
      this.download('app/quality/export', {
        ...this.query
      }, `quality_${new Date().getTime()}.xlsx`)
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.query.pageSize = val
      this.getList()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.query.pageNum = val
      this.getList()
    },
    getDataTime () {//默认显示今天
      this.query.startDate = new Date();
      this.query.endDate = new Date();
    },

  },

}
</script>

<style scoped>
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

.energy_main {
  height: 86vh;
  background-color: #fff;
}
.myTable {
  border-collapse: collapse;
  margin: 0 auto;
  text-align: center;
}

.myTable td,
.myTable th {
  border: 1px solid #797979;
  color: #666;
  height: 20px;
  font-size: 12px;
}
.el-form-item__content {
  display: -webkit-box;
}
</style>
