<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body " style="width: 98%;border: 0;">
        <el-row :gutter="20">
          <!-- 左侧选单配置树 -->
          <el-col :span="4" class="left_tree">
            <div>
              <div style="background:#409EFF" align="center">
                缴费地区
              </div>
              <div id="changeColor" v-for="(dict,index) in salaryOptions.SocialSecurity" align="center" @click="changePostName(dict,index)">
                {{dict.dicName}}
              </div>
            </div>
          </el-col>
          <!-- 右侧列表 -->
          <el-col :span="20">
            <div>
              <div class="avue-crud__search" style="border: 0">
                <el-row>
                  <el-col :span="20">
                    <el-form :inline="true" >
                      <el-form-item label="版本号" prop="version">
                        <el-select v-model="queryParams.version" placeholder="请选择版本号">
                          <el-option
                            v-for="dict in versionList"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                          />
                        </el-select>
                      </el-form-item>
                      <!-- 操作按钮 -->
                      <el-form-item>
                        <el-button type="primary" icon="el-icon-search" plain size="mini" @click="handleQuery">搜索</el-button>
                      </el-form-item>
                      <el-form-item>
                        <el-button v-hasPermi="['human:socialSecurity:add']" icon="el-icon-edit" type="primary" size="mini" plain :disabled="multiple" @click="handleSave">保存</el-button>
                      </el-form-item>
                      <el-form-item>
                        <el-button
                          type="danger"
                          plain
                          icon="el-icon-delete"
                          size="mini"
                          :disabled="multiple"
                          @click="handleDelete"
                          v-hasPermi="['human:socialSecurity:remove']"
                        >作废</el-button>
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-row>
                <el-row>
                  <el-form :inline="true">
                    <el-form-item label="缴费地区" prop="payAreaId">
                      <el-select v-model="queryParams.payAreaId" placeholder="请选择缴费地区" disabled>
                        <el-option
                          v-for="dict in salaryOptions.SocialSecurity"
                          :key="dict.dicNo"
                          :label="dict.dicName"
                          :value="dict.dicNo"
                        />
                      </el-select>
                    </el-form-item>
                    <el-form-item label="生效日期" prop="effectDate">
                      <el-date-picker
                        v-model="queryParams.effectDate"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="选择日期"
                        @change="dateFormat">
                      </el-date-picker>
                    </el-form-item>
                  </el-form>
                </el-row>
              </div>
              <div>

                <el-form ref="form" :model="form" :rules="rules">
                  <el-table height="70vh" size="small" v-loading="table.loading" :row-class-name="addIndex" :data="form.socialSecurityList" @selection-change="handleSelectionChange" @row-click="addLine" stripe>
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="社保公积金项目" align="center" prop="salaryProjectId" width="120">
                      <template v-slot="scope">
                        <el-select v-model="scope.row.salaryProjectId" >
                          <el-option
                            v-for="dict in salaryList"
                            :key="dict.id"
                            :label="dict.payProName"
                            :value="dict.id"
                          />
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column label="单位缴纳比例%" align="center" prop="comPro" width="120">
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.comPro'" :rules="rules.mon">
                          <el-input v-model="scope.row.comPro" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="单位附加金额" align="center" prop="comMon" >
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.comMon'" :rules="rules.mon">
                          <el-input v-model="scope.row.comMon" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="个人缴纳比例%" align="center" prop="perPro" width="120">
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.perPro'" :rules="rules.mon">
                          <el-input v-model="scope.row.perPro" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="个人附加金额" align="center" prop="perMon" >
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.perMon'" :rules="rules.mon">
                          <el-input v-model="scope.row.perMon" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="基数上限" align="center" prop="baseUcl" >
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.baseUcl'" :rules="rules.mon">
                          <el-input v-model="scope.row.baseUcl" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="基数下限" align="center" prop="baseLl" >
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.baseUcl'" :rules="rules.mon">
                          <el-input v-model="scope.row.baseLl" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="小数位数" align="center" prop="deciBit" >
                      <template v-slot="scope">
                        <el-form-item :prop="'socialSecurityList.' + scope.$index + '.deciBit'" :rules="rules.mon">
                          <el-input v-model="scope.row.deciBit" placeholder="请输入内容"></el-input>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column label="进位方式" align="center" prop="carryMode">
                      <template v-slot="scope">
                        <el-select v-model="scope.row.carryMode" >
                          <el-option
                            v-for="dict in salaryOptions.CarryMethod"
                            :key="dict.dicNo"
                            :label="dict.dicName"
                            :value="dict.dicNo"
                          />
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column label="生效日期" align="center" prop="effectDate" >
                      <template v-slot="scope">
                        <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="输入人" align="center" prop="creator" />
                    <el-table-column label="输入日期" align="center" prop="createDate" >
                      <template v-slot="scope">
                        <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form>

                <pagination
                  v-show="total>0"
                  :total="total"
                  :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize"
                  @pagination="onLoad"
                />
              </div>
            </div>
          </el-col>


        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { listSocialSecurity, delSocialSecurity, addSocialSecurity, selectVersion } from "@/api/human/hs/socialSecurity";
import { listSalaryProjectBasis} from "@/api/human/hs/salaryProjectBasis";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import { getSalaryOptions } from "@/api/human/hs/salaryBasis";


export default {
  name: "SocialSecurity",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      //版本号数据
      versionList: [],
      //薪资列表
      salaryList:[],
      multipleSelection: [],
      //tab方向
      tabPosition: 'left',
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //样式数组
      styleList:['','',''],
      //表格属性
      table: {
        border: true,
        loading: false,
      },
      //登录人工号
      userEmpId: null,
      //登录人姓名
      nickName: null,
      //登录人公司
      logincompId: null,
      //薪资选单选项列表
      salaryOptions: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['SocialSecurity','CarryMethod'],
        compId:null,
      },
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 社保公积金缴费比例设定表格数据
      socialSecurityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        date: null,
        payAreaId: null,
        version: null,
        effectDate: null,
      },
      // 表单参数
      form: {
        socialSecurityList: [],
      },
      // 表单校验
      rules: {
        mon: [
          { pattern: /^\d+$|^\d+[.]?\d+$/, message: "请输入数字", trigger: "change"},
        ]
      },
    };
  },
  created() {
    this.getSalaryList()
    this.initData();
    this.getDisc();
    this.addLine();
  },
  methods: {
    /** 查询列表 */
    getSalaryList(){
      let obj={
        id:'62',
      }
      listSalaryProjectBasis(obj).then(response =>{
          this.salaryList = response.data.rows
        }
      )
    },
    //初始化数据
    initData(){
      this.userEmpId= this.$store.state.user.name;
      this.nickName= this.$store.state.user.userInfo.nickName;
      this.logincompId= this.$store.state.user.userInfo.compId;
    },
    //获取缴费地区列表
    getVersionList() {
      selectVersion(this.queryParams.payAreaId).then(response => {
        this.versionList = response.data
      })
    },
    //表单值设置
    setForm(e){
      this.form.creator = this.nickName;
      this.form.creatorId = this.userEmpId;
      this.form.createDate = getDateTime(1)
      if(e===0){
        this.form.parentid = this.queryParams.id;
      }
    },
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = null
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        salaryProjectId: null,
        comPro: null,
        comMon: null,
        perPro: null,
        perMon: null,
        baseUcl: null,
        baseLl: null,
        deciBit: null,
        carryMode: null,
        payAreaId: null,
        effectDate: null,
        version: null,
        creator: null,
        creatorId: null,
        parentid: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.onLoad();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 日期变更*/
    dateFormat(val){
      this.queryParams.date =this.queryParams.effectDate;
    },
    /** 保存按钮 */
    handleSave() {
      if (this.queryParams.effectDate != null) {
        if (this.queryParams.effectDate > getDateTime(1)) {
          for (let i = 0; i < this.multipleSelection.length; i++) {
            this.multipleSelection[i].effectDate = this.queryParams.effectDate
          }
          addSocialSecurity(this.multipleSelection).then(res => {
            this.$modal.msgSuccess("保存成功");
            this.onLoad();
            this.getVersionList();
          })
        } else {
          this.$modal.msgError("生效日期必须大于当前日期");
        }
      }else {
        this.$modal.msgError("生效日期不能为空");
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      for (const idsKey in  this.multipleSelection) {
        if ( this.multipleSelection[idsKey].effectDate > getDateTime(1)) {
          this.$modal.confirm('是否确认删除此数据项？').then(function () {
            return delSocialSecurity(ids);
          }).then(() => {
            this.onLoad();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {
          })
        }else {
          this.$modal.msgError("已生效日期不能作废");
          break;
        }
      }
    },
    //点击节点方法
    changePostName(data,index){
      this.queryParams.version = null;
      this.queryParams.effectDate = null;
      this.queryParams.payAreaId = data.dicNo;
      this.queryParams.date = null;
      this.getVersionList()
      this.form.socialSecurityList = []
      this.onLoad()
    },
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      listSocialSecurity(this.queryParams).then(response => {
        this.total = response.total;
        this.form.socialSecurityList = response.rows;//表格数据
        if(this.form.socialSecurityList.length===0){
          this.addLine();
        }
        this.table.loading = false;
      }, error => {
        this.table.loading = false;
        const newLine = {
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
          parentid: this.queryParams.id,
          effectDate: this.queryParams.effectDate,
        }
        this.form.socialSecurityList.push(newLine)
      });
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.form.socialSecurityList.length == row.index + 1) {
        const newLine = {
          id: null,
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
          parentid: this.queryParams.id,
          payAreaId: this.queryParams.payAreaId,
          effectDate: this.queryParams.effectDate,
        }
        this.index++
        this.form.socialSecurityList.push(newLine)
      }
    },
  }
};
</script>

<style scoped>
.el-form-item {
  margin-bottom: 11px;
}
.aa >>> .el-form-item__content {
  margin-left:0px !important;
}
#changeColor:hover{
  background-color: #F2F6FC;
}
.el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-scrollbar__bar.is-horizontal {
  display: none;
}
</style>
