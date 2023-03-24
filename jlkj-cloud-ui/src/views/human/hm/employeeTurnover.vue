<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" label-width="40px" :inline="true" v-show="showSearch">
          <el-form-item label="公司">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in dict.type.comp_id"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工号">
            <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="mini"
                       type="primary"
                       @click="getList"
                       icon="el-icon-search">搜 索</el-button>
            <el-button size="mini"
                       type="default"
                       @click="handleEmpty"
                       icon="el-icon-refresh-left">重 置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button size="mini"
                       type="primary"
                       @click="handleAdd"
                       icon="el-icon-plus">新 增</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>
        <el-table :data="postList">
          <el-table-column label="工号" align="center" prop="empNo" v-if="columns[0].visible"/>
          <el-table-column label="姓名" align="center" prop="empName" v-if="columns[1].visible"/>
          <el-table-column label="异动时间" align="center" prop="createDate" v-if="columns[2].visible"/>
          <el-table-column label="异动类别" align="center" prop="changeTypeId" v-if="columns[3].visible">
            <template v-slot="scope">
              <dict-tag-human :options="baseInfoData.ChangeCategory" :value="scope.row.changeTypeId"/>
            </template>
          </el-table-column>
          <el-table-column label="生效日期" align="center" prop="effectDate" v-if="columns[4].visible"/>
          <el-table-column label="操作" align="center">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 添加或修改员工异动资料对话框 -->
        <el-dialog
          :title="title"
          :visible.sync="open"
          width="1200px"
        >
          <el-form
            :model="addJsonForm"
            ref="addJsonForm"
            :rules="rules"
            label-width="120px"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="工号">
                  <el-input v-model="addJsonForm.empNo" placeholder="请输入工号" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick" v-show="updatePop"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="姓名">
                  <el-input v-model="addJsonForm.empName" placeholder="请输入姓名" :disabled="!updatePop"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="当前主岗位">
                  <el-input v-model="addJsonForm.nowPostName" :disabled="true"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="当前职位等级">
                  <el-input v-model="addJsonForm.nowPostLevel" :disabled="true"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="异动后职位等级" prop="postLevel">
                  <el-select v-model="addJsonForm.postLevel" placeholder="职位等级" style="width: 100%">
                    <el-option
                      v-for="dict in baseInfoData.HP005"
                      :key="dict.dicName"
                      :label="dict.dicName"
                      :value="dict.dicName"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="异动类别">
                  <el-select v-model="addJsonForm.changeTypeId" placeholder="异动类别" style="width: 100%" @change="changePostLevel">
                    <el-option
                      v-for="dict in baseInfoData.ChangeCategory"
                      :key="dict.uuid"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item>
                  <el-select v-model="addJsonForm.changeTypeItemId" style="width: 100%">
                    <el-option
                      v-for="dict in postLevelDetail"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="生效日期" prop="effectDate">
                  <el-date-picker
                    v-model="addJsonForm.effectDate"
                    type="date"
                    style="width: 100%"
                    placeholder="选择生效日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-bottom: 10px">
              <el-col>
                <span style="font-size: 18px">【岗位资料】</span>
              </el-col>
            </el-row>
            <el-button type="primary" @click="addLine">添加岗位信息</el-button>
            <el-button type="primary" @click="delTableItem" :disabled="addJsonMultiple">删除岗位信息</el-button>
            <el-table
              :data="addJsonForm.employeeTurnoverList"
              border
              :key="key"
              @selection-change="addJsonSelectionChange"
              :cell-style="{paddingBottom:'0px'}"
              class="aa"
            >
              <el-table-column type="selection" width="55" align="center">
              </el-table-column>
              <el-table-column label="岗位类型" align="center">
                <template v-slot="scope">
                  <el-form-item
                    :prop="'employeeTurnoverList.' + scope.$index + '.postTypeId'"
                  >
                    <el-select v-model="scope.row.postTypeId">
                      <el-option
                        v-for="dict in baseInfoData.post_type_id"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="异动前岗位" align="center">
                <template v-slot="scope">
                  <el-form-item
                    :prop="'employeeTurnoverList.' + scope.$index + '.newPostName'"
                    align="center"
                  >
                    {{scope.row.newPostName}}
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="异动后岗位" align="center">
                <template v-slot="scope">
                  <el-form-item
                    :prop="'employeeTurnoverList.' + scope.$index + '.nowPostName'"
                    style="text-align: right"
                  >
                    {{scope.row.nowPostName}}
                    <el-button icon="el-icon-search" @click="openPostName = true"></el-button>
                  </el-form-item>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确定</el-button>
            <el-button @click="resetAddJsonPopup">取 消</el-button>
          </span>
        </el-dialog>
        <el-dialog title="选择岗位"
                   :visible.sync="openPostName"
                   width="700px">
          <el-form
            :model="addJsonForm"
            ref="addJsonForm"
            :rules="rules"
            label-width="80px"
          >
            <el-table :data="addJsonForm.employeeTurnoverList" border>
              <el-table-column  label="所属组织机构" align="center">
                <template v-slot="scope">
                  <el-form-item>
                    <el-select v-model="compId" placeholder="请选择公司别" clearable size="small" @change="changeLabel">
                      <el-option
                        v-for="dict in baseInfoData.comp_id"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      />
                    </el-select>
                    <el-tree
                      :data="newPostNameOptions"
                      :props="defaultProps"
                      :default-expand-al="false"
                      :highlight-current="true"
                      :expand-on-click-node="false"
                      :default-expanded-keys="expandedKeys"
                      v-show="tree"
                      node-key="id"
                      ref="tree"
                      @node-click="handleNodeClick"
                    />
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column  label="选取岗位" align="center">
                <template v-slot="scope">
                  <el-form-item>
                    <el-select v-model="scope.row.postName" placeholder="请选择岗位" clearable @change="changePostName">
                      <el-option
                        v-for="dict in postMaintenanceList"
                        :key="dict.postName"
                        :label="dict.postName"
                        :value="dict.postName"
                      />
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-dialog>
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import selectUser from "@/views/human/hm/selectUser";
import {getBaseInfo, getDegreeMajorSpecialization} from "@/api/human/hm/baseInfo";
import {queryEmployeeInduction, queryEmployeeInductionByUuid, delEmployeeInduction, delEmployeeInductionDetail} from "@/api/human/hm/employeeInduction";
import {
  addEmployeeTurnover,
  delEmployeeTurnover,
  queryNewPostNameAndChangeDetail,
  updateEmployeeTurnover
} from "@/api/human/hm/employeeTurnover"
import {treeselect} from "@/api/human/hp/deptMaintenance";
import {listPostMaintenance} from "@/api/human/hp/postMaintenance";
import DictTagHuman from "@/views/human/hp/DictTag/index"
export default {
  name: "EmployeeTurnover",
  dicts: ['comp_id'],
  components: {selectUser,DictTagHuman},
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 选中数组
      ids: [],
      // 表单参数
      addJsonForm: {
        employeeTurnoverList: [
          {
            postTypeId: undefined,
            newPostName: undefined,
          }
        ]
      },
      //查询参数
      queryParams: {
        compId: undefined,
        empNo: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `工号`, visible: true },
        { key: 1, label: `姓名`, visible: true },
        { key: 2, label: `异动时间`, visible: true },
        { key: 3, label: `异动类别`, visible: true },
        { key: 4, label: `生效日期`, visible: true },
      ],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openPostName: false,
      // 非多个禁用
      multiple: true,
      //选单数据
      baseInfoData: [],
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'HP005',
          'post_type_id',
          'comp_id',
          'ChangeCategory']
      },
      // 表单校验
      rules: {
        effectDate: [
          { required: true, message: "生效日期不能为空", trigger: "blur" }
        ],
        postLevel: [
          { required: true, message: "异动后职位等级不能为空", trigger: "blur" }
        ],
      },
      //岗位树选项
      newPostNameOptions: undefined,
      // 公司名称
      compId: undefined,
      //el tree默认值
      defaultProps: {
        children: "children",
        label: "label",
      },
      //默认展开指定节点
      expandedKeys: [],
      index: 0,
      //是否展示树和表
      tree:false,
      // 岗位数据
      postMaintenanceList: [],
      //员工入职表数据
      postList: [],
      // 非多个禁用
      addJsonMultiple: true,
      updatePop: true,
      key: undefined,
      label: undefined,
      parentPostName: undefined,
      orgName: undefined,
      postName: undefined,
      //异动类别细分
      postLevelDetail: []
    }
  },
  watch: {
    // 根据名称筛选部门树
    compId(val) {
      this.queryParams.deptId = undefined
      this.queryParams.compId = val;
      if(val){
        this.tree=true
        this.getTreeselect();
      }else{
        this.tree=false
      }
    }
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    getList() {
      queryEmployeeInduction(this.queryParams).then(res => {
        this.postList = res.data
      })
    },
    /** 获取工号 */
    getJobNumber(val, userName) {
      this.queryParams.empNo = val
      this.addJsonForm.empNo = val
      this.addJsonForm.empName = userName
      queryNewPostNameAndChangeDetail(this.addJsonForm).then(res => {
        this.addJsonForm.nowPostName = res.data.list1[0].newPostName
        this.addJsonForm.nowPostLevel = res.data.list[0].postLevel
        this.addJsonForm.versionNo = res.data.list[0].versionNo
        this.addJsonForm.employeeTurnoverList = res.data.list1
      })
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    handleEmpty() {
      this.queryParams = {
        compId: undefined,
        empNo: undefined
      }
      this.getList()
    },
    // 表单重置
    reset() {
      this.addJsonForm = {
        empNo: undefined,
        empName: undefined,
        effectDate: undefined,
        postLevel: undefined,
        employeeTurnoverList: [
          {
            postTypeId: undefined,
            newPostName: undefined,
          }
        ]
      };
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.updatePop = true
      this.title = "添加岗位异动资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      queryEmployeeInductionByUuid(row.uuid).then(res => {
        this.addJsonForm = res.data.list[0]
        this.addJsonForm.nowPostName = res.data.employeeInductionList[0].newPostName
        this.addJsonForm.nowPostLevel = res.data.list[0].postLevel
        this.addJsonForm.employeeTurnoverList = res.data.employeeInductionList
        this.addJsonForm.employeeTurnoverList[this.index].nowPostName = res.data.employeeInductionList[this.index].newPostName
        this.open = true
        this.title = "修改岗位异动资料"
        this.updatePop = false
      })
    },
    submitForm() {
      if (this.addJsonForm.uuid != undefined) {
        updateEmployeeTurnover(this.addJsonForm).then(res => {
          if (res.code === 200) {
            this.$message({ type: "success", message: res.msg });
          }
          this.open = false;
          this.getList();
        });
      } else {
        addEmployeeTurnover(this.addJsonForm).then(res => {
          if (res.code === 200) {
            this.$message({ type: "success", message: res.msg });
          }
          this.open = false
        })
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuid = row.uuid;
      const empNo = row.empNo;
      this.$modal.confirm('是否确认删除工号为"' + empNo + '"的员工异动资料？').then(function() {
        return delEmployeeTurnover(uuid,empNo);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
// 增加一个空行, 用于录入或显示第一行
    addLine() {
      const newLine = {
        postTypeId: "",
        newPostName: ""
      }
      this.index++
      this.addJsonForm.employeeTurnoverList.push(newLine)
    },
    delTableItem() {
      const uuids = this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + uuids + '"的数据项？').then(function() {
        return delEmployeeInductionDetail(uuids);
      }).then(() => {
        queryEmployeeInductionByUuid(uuids).then(res => {
          this.addJsonForm.employeeTurnoverList = res.data.employeeTurnoverList
          this.key = Math.random()
        })
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    addJsonSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid);
      this.addJsonMultiple = !selection.length;
    },
    resetAddJsonPopup() {
      //关闭 固定值弹窗
      this.$set(this.addJsonForm, "employeeTurnoverList", []);
      this.open = false;
    },
    RndNum(n) {
      // 生成随机数
      let rdmNum = "";
      for (let i = 0; i < n; i++) {
        rdmNum += Math.floor(Math.random() * 10); // [0,10)的整数
      }
      return rdmNum;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    handleQuery() {
      listPostMaintenance(this.queryParams).then(response => {
        this.orgName = response.rows[0].orgName
        this.parentPostName = response.rows[0].parentPostName
        this.postMaintenanceList = response.rows;
        this.addJsonForm.postName = this.postMaintenanceList[0].postName
        this.addJsonForm.postId = this.postMaintenanceList[0].postId
        this.addJsonForm.departmentName = this.postMaintenanceList[0].orgName
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect(this.queryParams).then(response => {
        this.newPostNameOptions = response.data;
      });
    },
    changeLabel(val) {
      if (val == 'J00') {
        this.label = '吉林建龙'
      } else if (val == 'J01') {
        this.label = '吉林龙翔冷轧新型材料有限公司'
      } else {
        this.label = '吉林建龙信息科技'
      }
    },
    changePostName(val) {
      this.openPostName = false
      this.postName = val
      this.addJsonForm.employeeTurnoverList[this.index].nowPostName = this.label + '-' + this.parentPostName + '-' + this.orgName + '-' + this.postName
    },
    changePostLevel(val) {
      const selectedItem = this.baseInfoData.ChangeCategory.find((item) => {
        return item.dicNo === val
      })
      this.baseInfo.uuid = selectedItem.uuid
      this.postLevelDetail = []
      getDegreeMajorSpecialization(this.baseInfo).then(response => {
        this.postLevelDetail = response.data
      });
    },
  }
}
</script>

<style scoped>
.el-form-item {
  margin-bottom: 11px;
}
.aa >>> .el-form-item__content {
  margin-left:0px !important;
}
</style>
