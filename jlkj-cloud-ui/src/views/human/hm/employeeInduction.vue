<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
          <el-form :model="queryParams" ref="queryForm" label-width="40px" :inline="true" v-show="showSearch">
              <el-form-item label="公司">
                <el-select v-model="queryParams.compId" placeholder="请选择公司">
                  <el-option
                    v-for="dict in companyName"
                    :key="dict.compId"
                    :label="dict.companyName"
                    :value="dict.compId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="工号">
                <el-input v-model="queryParams.empNo" placeholder="请输入工号" disabled>
                  <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button v-hasPermi="['human:employeeInduction:list']"
                           size="mini"
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
              <el-button v-hasPermi="['human:employeeInduction:add']"
                         size="mini"
                         type="primary"
                         plain
                         @click="handleAdd"
                         icon="el-icon-plus">新 增</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
          </el-row>
          <el-table :data="postList">
            <el-table-column label="工号" align="center" prop="empNo" v-if="columns[0].visible"/>
            <el-table-column label="姓名" align="center" prop="empName" v-if="columns[1].visible"/>
            <el-table-column label="职位等级" align="center" prop="postLevel" v-if="columns[2].visible"/>
            <el-table-column label="生效日期" align="center" prop="effectDate" v-if="columns[3].visible"/>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button
                  v-hasPermi="['human:employeeInduction:edit']"
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                >修改
                </el-button>
                <el-button
                  v-hasPermi="['human:employeeInduction:remove']"
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        <!-- 添加或修改员工入职资料对话框 -->
        <el-dialog
          :title="title"
          :visible.sync="open"
          width="1300px"
        >
          <el-form
            ref="addJsonForm"
            :model="addJsonForm"
            :rules="rules"
            label-width="80px"
          >
            <el-row>
              <el-col :span="6">
                <el-form-item label="工号" prop="empNo">
                  <el-input v-model="addJsonForm.empNo" placeholder="请输入工号" :maxlength="6" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick" v-show="updatePop"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="姓名" prop="empName">
                  <el-input v-model="addJsonForm.empName" placeholder="请输入姓名" :maxlength="6" :disabled="!updatePop"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="生效日期" prop="effectDate">
                  <el-date-picker
                    v-model="addJsonForm.effectDate"
                    type="date"
                    placeholder="选择生效日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="职位等级">
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
            <el-row style="margin-bottom: 10px">
              <el-col>
                <span style="font-size: 18px">【岗位信息明细】</span>
              </el-col>
            </el-row>
            <el-button type="primary" plain icon="el-icon-plus" @click="addLine">添加</el-button>
            <el-button type="danger" plain icon="el-icon-delete" @click="delTableItem">删除</el-button>
            <el-table
              :data="addJsonForm.employeeInductionList"
              border
              :key="key"
              @selection-change="addJsonSelectionChange"
              :cell-style="{paddingBottom:'0px'}"
              class="aa"
            >
              <el-table-column type="selection" width="55" align="center">
              </el-table-column>
              <el-table-column label="岗位类别" align="center">
                <template v-slot="scope">
                  <el-form-item
                    :prop="'employeeInductionList.' + scope.$index + '.postTypeId'"
                  >
                    <el-select v-model="scope.row.postTypeId" @change="checkPostTypeId">
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
              <el-table-column label="岗位" align="center">
                <template v-slot="scope">
                  <el-form-item
                    :prop="'employeeInductionList.' + scope.$index + '.newPostName'"
                    style="text-align: right"
                  >
                    {{scope.row.newPostName}}
                    <el-button icon="el-icon-search" @click="openPostPop(scope.$index)"></el-button>
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
            :model="form"
            ref="form"
            :rules="rules"
            label-width="80px"
          >
            <el-table :data="form.postPop" border :cell-style="{verticalAlign:'top',textAlign: 'left'}">
              <el-table-column  label="所属组织机构" align="center">
                <template v-slot="scope">
                  <el-form-item>
                    <el-select v-model="compId" placeholder="请选择公司别" clearable size="small">
                      <el-option
                        v-for="dict in companyName"
                        :key="dict.compId"
                        :label="dict.companyName"
                        :value="dict.compId"
                      />
                    </el-select>
                    <div class="head-container" style="height: 25vh;width: 100%;">
                      <el-scrollbar style="height: 100%;">
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
                      </el-scrollbar>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="选取岗位" align="center">
                <template v-slot="scope">
                  <div id="changeColor" v-for="dict in postMaintenanceList" @click="changePostName(dict)">
                    {{dict.postCode}} {{dict.postName}}
                  </div>
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
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {queryEmployeeInduction, addEmployeeInduction, queryEmployeeInductionByUuid, delEmployeeInduction, updateEmployeeInduction} from "@/api/human/hm/employeeInduction";
import {selectCompany, treeselect} from "@/api/human/hp/deptMaintenance";
import {listPostMaintenance} from "@/api/human/hp/postMaintenance";
import {checkRealName} from "@/utils/jlkj";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "EmployeeInduction",
  components: {selectUser},
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 选中数组
      ids: [],
      // 表单参数
      addJsonForm: {
        employeeInductionList: [
          {
            postTypeId: "01",
            newPostName: undefined,
            uuid: 1
          }
        ],
        postPop: [
          {
            postTypeId: undefined,
            newPostName: undefined,
          }
        ]
      },
      form: {},
      //查询参数
      queryParams: {
        compId: undefined,
        empNo: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `工号`, visible: true },
        { key: 1, label: `姓名`, visible: true },
        { key: 2, label: `职位等级`, visible: true },
        { key: 3, label: `生效日期`, visible: true },
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
          'post_type_id']
      },
      // 表单校验
      rules: {
        empNo: [
          { pattern: /^[+]?(0|([1-9]\d*))?$/, message: "请输入数字", trigger: "blur"},
          { min: 0, max: 6, message: '工号长度必须介于 0 和 6 之间', trigger: 'blur' }
        ],
        empName: [
          { required: false, validator: checkRealName, trigger: "blur" }
        ],
        effectDate: [
          { required: true, message: "生效日期不能为空", trigger: "blur" }
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
      addJsonMultiple: [],
      updatePop: true,
      key: undefined,
      companyName: [],
      compName: undefined,
      num: 0
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
    },
    //监听数据 设置默认展示第一层数据
    newPostNameOptions: {
      handler(val) {
        val.forEach(item => {
          this.expandedKeys.push(item.id);
        })
      },
      deep: true,
    }
  },
  created() {
    this.queryParams.empNo = this.$store.state.user.name
    this.queryParams.compId = this.$store.state.user.userInfo.compId
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    getList() {
      queryEmployeeInduction(this.queryParams).then(res => {
        this.postList = res.data
      })
    },
    /** 获取工号 */
    getJobNumber(val,userName) {
      this.queryParams.empNo = val
      this.addJsonForm.empNo = val
      this.addJsonForm.empName = userName
      this.getList();
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
        employeeInductionList: [
          {
            postTypeId: '01',
            newPostName: undefined,
          }
        ]
      }
      this.form = {
        postPop: [
          {
            postTypeId: undefined,
            newPostName: undefined,
          }
        ]
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.updatePop = true
      this.title = "新增员工入职资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      queryEmployeeInductionByUuid(row.uuid).then(res => {
        this.addJsonForm = res.data.list[0]
        this.addJsonForm.employeeInductionList = res.data.employeeInductionList
        this.open = true
        this.title = "修改员工入职资料"
        this.updatePop = false
      })
    },
    submitForm() {
      this.$refs["addJsonForm"].validate(valid => {
        if (valid) {
          let result = []
          this.addJsonForm.employeeInductionList.map(val => {
            this.$delete(val, "uuid"); // 删除uuid属性
            result.push(val);
          });
          this.addJsonForm.employeeInductionList = result
          if (this.addJsonForm.uuid !== undefined) {
            updateEmployeeInduction(this.addJsonForm).then(res => {
              if (res.code === 200) {
                this.$message({type: "success", message: res.msg});
              }
              this.open = false;
              this.getList();
            });
          } else {
            this.addJsonForm.compId = this.queryParams.compId
            addEmployeeInduction(this.addJsonForm).then(res => {
              if (res.code === 200) {
                this.$message({type: "success", message: res.msg});
              }
              this.open = false
              this.getList();
            })
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuid = row.uuid;
      const empNo = row.empNo;
      this.$modal.confirm('是否确认删除工号为"' + empNo + '"的员工入职资料？').then(function() {
        return delEmployeeInduction(uuid,empNo);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
// 增加一个空行, 用于录入或显示第一行
    addLine() {
      this.index++
      if (this.addJsonForm.employeeInductionList.length === 0) {
        const newLine = {
          postTypeId: "01",
          newPostName: "",
          uuid: this.index
        }
        this.addJsonForm.employeeInductionList.push(newLine)
      } else {
        const newLine = {
          postTypeId: "02",
          newPostName: "",
          uuid: this.index
        }
        this.addJsonForm.employeeInductionList.push(newLine)
      }
    },
    delTableItem() {
      // 确认删除
      if (this.addJsonMultiple.length > 0) {
        let arrs = [];
        let ids = this.addJsonMultiple.map(val => val.uuid); //拿到选中的数据id,
        this.addJsonForm.employeeInductionList.forEach(item => {
          if (!ids.includes(item.uuid)) {
            // 当uuid在employeeInductionList中，表示数据被选中，该将其删除，即将不被选中的保留
            arrs.push(item);
          }
        });
        this.addJsonForm.employeeInductionList = arrs;
        this.key = Math.random()
      } else {
        this.$message.warning("请选择要删除的数据");
      }
    },
    addJsonSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid);
      this.addJsonMultiple = selection;
    },
    resetAddJsonPopup() {
      //关闭 固定值弹窗
      this.$set(this.addJsonForm, "employeeInductionList", []);
      this.open = false;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.orgId = data.id
      this.addJsonForm.departmentName = data.label
      this.addJsonForm.departmentId = data.id
      this.handleQuery();
    },
    handleQuery() {
      listPostMaintenance(this.queryParams).then(response => {
        this.postMaintenanceList = response.rows;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect(this.queryParams).then(response => {
        this.compName = response.data[0].label
        this.newPostNameOptions = response.data;
      });
    },
    changePostName(val) {
      this.openPostName = false
      this.addJsonForm.employeeInductionList[this.index].newPostId = val.postId
      this.addJsonForm.employeeInductionList[this.index].newPostName = this.compName + '-' + val.orgName + '-' + val.postName
    },
    openPostPop(val) {
      this.form = {
        postPop: [
          {
            postTypeId: undefined,
            newPostName: undefined,
          }
        ]
      }
      this.index = val
      this.openPostName = true
      this.compId = undefined
      this.postMaintenanceList = []
    },
    checkPostTypeId() {
      this.num = 0
      this.addJsonForm.employeeInductionList.forEach(item =>{
        if (item.postTypeId === '01') {
          this.num++
        }
        if (this.num > 1) {
          this.$modal.msgError("只能有一笔主岗位资料");
        }
      })
    }
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
#changeColor:hover{
  background-color: #7f7f7f;
}
.el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-scrollbar__bar.is-horizontal {
  display: none;
}
</style>
