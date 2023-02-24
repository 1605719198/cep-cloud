<template>
  <el-row>
    <el-col :span="24"
            style="padding:0 10px">
      <div class="energy_main">
        <div class="avue-crud el-card__body"
             style="width: 98%;border: 0">
          <div class="avue-crud__search"
               style="border: 0">
            <el-form>
              <el-row :gutter="20">
                <el-col :span="3">
                  <el-form-item label="">
                    <el-input v-model="query.formatId"
                              placeholder="表格代号"
                              @keyup.enter.native="handleQuery">
                      <i slot="suffix"
                         class="el-input__icon el-icon-plus"
                         @click="handleQueryPlus"
                         style="cursor:pointer;"></i>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="3">
                  <el-form-item label="">
                    <el-input v-model="this.dataEditFormatMemo.formatMemo"
                              placeholder="表格说明"
                              disabled />
                  </el-form-item>
                </el-col>
                <el-col :span="13">
                  <div class="el-form-item__content"
                       style="margin-left: 0px;">
                    <el-button v-hasPermi="['elementTable_queryList']"
                               size="medium"
                               type="primary"
                               icon="el-icon-search"
                               @click="handleQuery">搜 索</el-button>
                    <el-button size="medium"
                               type="default"
                               icon="el-icon-refresh-left"
                               @click="handleEmpty">重 置</el-button>
                  </div>
                </el-col>
                <el-col :span="5"
                        style="white-space: nowrap">
                  <div class="el-form-item__content"
                       style="float: right">
                    <el-button v-hasPermi="['elementTable_add']"
                               type="primary"
                               size="medium"
                               icon="el-icon-plus"
                               @click="handleAdd">新增</el-button>
                    <el-button v-hasPermi="['elementTable_update']"
                               size="medium"
                               icon="el-icon-edit"
                               type="success"
                               @click="handleEdit">修改</el-button>
                    <el-button v-hasPermi="['elementTable_delete']"
                               size="medium"
                               icon="el-icon-delete"
                               type="danger"
                               @click="handleDelete">删除</el-button>
                  </div>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div>
            <el-form>
              <el-table height="67.1vh"
                        size="small"
                        :data="tableData"
                        stripe>
                <template v-for="column in columns">
                  <el-table-column :key="column.prop"
                                   :prop="column.prop"
                                   :label="column.label"
                                   :sortable="column.sortable"
                                   :width="column.width"
                                   :min-width="column.minWidth" />
                </template>
              </el-table>
              <div id="elTable1"
                   style="margin-top: 10px;right: 0;"
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
          <div v-if="addBox">
            <el-dialog :title="(editBox?'编辑':'新建')+'试验元素表格维护'"
                       :visible.sync="addBox"
                       width="1200px"
                       class="customDialogStyle"
                       append-to-body
                       :destroy-on-close="true"
                       :close-on-click-modal="false"
                       style="margin-top: -10vh">
              <elementTableAdd :dataEdit="dataEdit"
                               :totalPlus="totalPlus"
                               :editBox="editBox"
                               :status="status"
                               @submitAdd="submitAdd"
                               @close="addBox=false,editBox=false"
                               @cancelQuery="handleQuery" />
            </el-dialog>
          </div>

          <!-- "+"查询对话框 -->
          <el-dialog :title="title"
                     :visible.sync="openPlus"
                     width="1000px"
                     :close-on-click-modal="false"
                     class="customDialogStyle"
                     v-if="openPlus"
                     append-to-body>
            <el-form label-width="135px">
              <el-form-item label="表格代号前两码"
                            prop="formatId">
                <el-input v-model="query.formatId"
                          style="width: 231px"
                          placeholder="模糊查询"></el-input>
                <el-button type="primary"
                           size="medium"
                           icon="el-icon-search"
                           @click="handleQueryBtn"
                           style="margin-left: 40px;">搜索</el-button>
              </el-form-item>
            </el-form>
            <el-table stripe
                      height="55h"
                      :data="tableDataPlus"
                      tooltip-effect="dark"
                      @row-click="singleElection"
                      style="margin: 0 20px;width: auto;height: 400px">
              <el-table-column label=""
                               width="30">
                <template slot-scope="scope">
                  <el-radio class="radio"
                            v-model="templateSelection"
                            :label="scope.$index">&nbsp;</el-radio>
                </template>
              </el-table-column>
              <el-table-column label="表格代号"
                               align="center"
                               prop="formatId" />
              <el-table-column label="表格代号说明"
                               align="center"
                               prop="formatMemo" />
            </el-table>
            <div id="elTable2"
                 style="margin-top: 10px;right: 0"
                 class="avue-crud__pagination">
              <el-pagination background
                             @size-change="handleSizeChange"
                             @current-change="handleCurrentChange"
                             :current-page="pagePlus.pageNum"
                             :page-sizes="[20, 50, 100, 200]"
                             :page-size="pagePlus.pageSize"
                             :layout="pagePlus.layout"
                             :total="pagePlus.total">
              </el-pagination>
            </div>
            <div slot="footer"
                 class="el-dialog__footer">
              <el-button type="primary"
                         @click="submitFormPlus()">确 定</el-button>
              <el-button type="warning"
                         @click="handleEmpty2">重 置</el-button>
              <el-button @click="cancel()">取 消</el-button>
            </div>
          </el-dialog>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>

import elementTableAdd from "./ElementTableAdd";
import { addInfo, delInfo, query, queryInfo, updateInfo } from "@/api/quality/ts/elementTable";

export default {
  components: {
    elementTableAdd
  },
  name: "elementTable",
  data () {
    return {
      templateSelection: '',
      // 是否显示弹出层
      openPlus: false,
      // 弹出层标题
      title: "",
      addBox: false,
      editBox: false,
      status: false,
      dataEdit: {},
      dataEditFormatMemo: {},
      page: {
        pageSize: 20,
        pageNum: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      pagePlus: {
        pageSize: 20,
        pageNum: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        formatId: '',
        pageSize: 20,
        pageNum: 0,
      },
      table: {
        border: true
      },
      columns: [
        { label: '试验元素代号', prop: "elementIndex", sortable: true, minWidth: '100px' },
        { label: '元素名称/试验项目', prop: "elementName", sortable: true, minWidth: '150px' },
        { label: '单位', prop: "elementUnit", sortable: true, minWidth: '100px' },
        { label: '显示顺序', prop: "elementSeq", sortable: true, minWidth: '100px' },
        { label: '新增人员', prop: "createUser", sortable: true, minWidth: '100px' },
        { label: '新增日期', prop: "createTime", sortable: true, minWidth: '100px' },
      ],
      tableData: [],
      // 表格数据
      tableDataPlus: [],
      totalPlus: '',
      // 进阶表单参数
      formPlus: {},
      formatId: '',
    }
  },
  created () {
  },
  methods: {
    singleElection (row) {
      this.query.formatId = row.formatId
      this.dataEditFormatMemo.formatMemo = row.formatMemo
    },
    cancel () {
      this.openPlus = false;
      this.query = {
        formatId: '',
        pageSize: 20,
        pageNum: 0,
      }
      this.templateSelection = ''
    },
    //查询
    handleQuery () {
      this.query.pageNum = 1
      this.query.pageSize = 20
      if (this.query.formatId == '' || typeof (this.query.formatId) == "undefined") {
        this.$message.error("请先进行查询！");
      } else {
        queryInfo(this.query).then(response => {
          this.tableData = response.data.data.list
          this.page.total = response.data.data.total
        })
      }
    },
    //"+"查询
    handleQueryBtn () {
      this.getList2()
    },
    /** "+"按钮操作 */
    handleQueryPlus () {
      this.openPlus = true;
      this.title = "表格代号查询";
      this.getList2()
    },
    // 清空
    handleEmpty () {
      this.query = {
        formatId: '',
        pageSize: 20,
        pageNum: 0,
      },
        this.templateSelection = ''
      this.tableData = []
      this.dataEditFormatMemo.formatMemo = ''
    },
    // 清空
    handleEmpty2 () {
      this.query = {
        formatId: '',
        pageSize: 20,
        pageNum: 0,
      },
        this.templateSelection = '',
        this.handleQueryPlus()
    },
    // 新增
    handleAdd () {
      this.dataEdit = {};
      this.addBox = true;
      this.status = false;
      this.editBox = false;
    },
    // 修改
    handleEdit () {
      if (this.tableData.length == 0) {
        this.dataEdit = { formatId: this.query.formatId, };
      } else {
        this.dataEdit = this.tableData[0];//传递子组件
      }
      this.editBox = true;
      this.status = true
      this.addBox = true;
    },
    // 删除
    handleDelete () {
      if (typeof (this.tableData[0]) == "undefined") {
        this.tableData.push({
          elementIndex: '',
          formatId: this.query.formatId,
        })
      }
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        delInfo(this.query.formatId).then(response => {
          this.getList();
          if (response.data.code == '0') {
            this.$message({
              type: 'success',
              message: response.data.msg
            });
          }
          this.query = {
            formatId: '',
            pageSize: 20,
            pageNum: 1,
          },
            this.dataEditFormatMemo.formatMemo = ''
          this.tableDataPlus = []
          this.title = "表格代号查询";
          this.getList2()
        })
      }).catch((err) => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.query.pageSize = val
      this.getList()
      this.getList2()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.query.pageNum = val
      this.getList()
      this.getList2()
    },
    getList2 () {
      query(this.query).then(response => {
        this.tableDataPlus = response.data.data.descriptionList
        this.pagePlus.total = response.data.data.total1
      })
    },
    //获取数据刷新页面
    getList () {
      queryInfo(this.query).then(response => {
        if (response.data.data != null) {
          this.tableData = response.data.data.list
          this.page.total = response.data.data.total
        }
      })
    },
    submitFormPlus () {
      this.openPlus = false;
    },
    //提供给子类调用父类的方法
    submitAdd (data) {
      if (this.editBox == true) {
        updateInfo(data).then(response => {
          this.query = {
            formatId: data.formatId,
            pageSize: 20,
            pageNum: 0,
          }
          this.getList()
          if (response.data.code == '0') {
            this.$message({
              type: 'success',
              message: response.data.msg
            });
          }
        });
      } else {
        addInfo(data).then(response => {
          this.query = {
            formatId: '',
            pageSize: 20,
            pageNum: 0
          }
          this.tableData = []
          if (response.data.code == '0') {
            this.$message({
              type: 'success',
              message: response.data.msg
            });
          }
          this.getList2()
        });
      }
      this.addBox = false;
      this.editBox = false;
    },
  }
}
</script>

<style scoped>
.energy_main {
  height: 86vh;
  background-color: #fff;
}

/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
