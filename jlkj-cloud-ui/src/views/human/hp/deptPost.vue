<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-select :popper-append-to-body="false" v-model="compId" placeholder="请选择公司名称" size="small">
            <el-option
              v-for="dict in companyList"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            />
          </el-select>
        </div>
        <div class="head-container treeDept" v-show="treeandtable">
          <el-scrollbar class="treeScrollbar">
            <el-tree
              :data="deptOptions"
              :props="defaultProps"
              :default-expand-all="false"
              :highlight-current="true"
              :expand-on-click-node="false"
              :default-expanded-keys="expandedKeys"
              node-key="deptId"
              ref="tree"
              @node-click="handleNodeClick"
            />
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px"
        >
          <el-form-item label="岗位编码" prop="postCode">
            <el-input
                      clearable
                      v-model="queryParams.postCode"
                      placeholder="请输入岗位编码"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="postPersons" height="67vh" v-show="treeandtable">
          <el-table-column label="职工编号" align="center" prop="empNo" width="100"/>
          <el-table-column label="姓名" align="center" prop="fullName" width="100"/>
          <el-table-column label="公司" align="center" prop="compId" width="100">
            <template v-slot="scope">
              <dict-tag-human-base :options="dicCompanyList" :value="scope.row.compId"/>
            </template>
          </el-table-column>
          <el-table-column label="部门" align="center" prop="departmentName" width="100"/>
          <el-table-column label="岗位编码" align="center" prop="postCode" width="75"/>
          <el-table-column label="职位中文名称" align="center" prop="postName" width="350" show-overflow-tooltip/>
          <el-table-column label="手机" align="center" prop="myMobilePhone"/>
          <el-table-column label="办公电话" align="center" prop="officeTelephone"/>
          <el-table-column label="办公Email" align="center" prop="officeEmail"/>
          <el-table-column label="办公地点" align="center" prop="officeAddress"/>
        </el-table>

        <pagination
          :total="total"
          v-show="total>0"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss'
import DictTagHumanBase from '@/views/components/human/dictTag/humanBaseInfo'
import { getBaseInfo } from '@/api/human/hm/baseInfo'
import { deptPostTree, selectCompany, queryPersonByPost } from '@/api/human/hp/deptMaintenance'
import { listPostMaintenance } from '@/api/human/hp/postMaintenance'
export default {
  name: 'deptPost',
  dicts: ['sys_normal_disable'],
  components: { DictTagHumanBase },
  data() {
    return {
      //岗位人员数据
      postPersons: [],
      //公司列表
      companyList: [],
      dicCompanyList: [],
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'HP002'
        ]
      },
      //选单数据
      baseInfoData: [],
      //登录人姓名
      nickName: undefined,
      //登录人公司
      logincompId: undefined,
      // 公司名称
      compId: undefined,
      //默认展开指定节点
      expandedKeys: [],
      // 部门树选项
      deptOptions: [],
      //是否展示树和表
      treeandtable: true,
      // 遮罩层
      loading: false,
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
      // 弹出层标题
      title: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        postCode: null,
        postId: null,
      },
      // 表单参数
      form: {},
      //el tree默认值
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  watch: {
    // 根据名称筛选部门树
    compId(val) {
      // this.$refs.tree.filter(val);
      this.queryParams.compId = val
      if (val) {
        this.treeandtable = true
        this.getTreeselect()
      } else {
        this.treeandtable = false
      }
    }
  },
  created() {
    this.getCompanyList()
    this.getHumandisc()
    this.getName()
  },
  methods: {
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
        this.companyList.forEach((value, index, array) => {
          var compDic = {
            dicNo: null,
            dicName: null
          }
          compDic.dicNo = value.compId
          compDic.dicName = value.companyName
          this.dicCompanyList.push(compDic)
        })
      })
    },
    //获取人事选单字典
    getHumandisc() {
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data
      })
    },
    // 获取当前登录用户名称/信息
    getName() {
      this.nickName = this.$store.state.user.userInfo.nickName
      this.logincompId = this.$store.state.user.userInfo.compId
      this.compId = this.logincompId
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      deptPostTree(this.queryParams).then(response => {
        this.deptOptions = response.data
        this.expandedKeys.push(response.data[0].deptId)
      })
    },
    // 节点单击事件
    handleNodeClick(data) {
      if (data.postId) {
        this.queryParams.postId = data.postId
        // this.queryParams.postCode = data.postCode
        this.getList()
      }
    },
    /** 查询部门资料维护列表 */
    getList() {
      this.loading = true
      queryPersonByPost(this.queryParams).then(response => {
        this.postPersons = response.data.rows
        this.loading = false
        this.total = response.data.total
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if(this.queryParams.postCode){
        let param = {
          postCode :this.queryParams.postCode,
          compId: this.queryParams.compId
        }
        listPostMaintenance(param).then(response=>{
          if(response.total===1){
            this.queryParams.postId = response.rows[0].postId;
            this.queryParams.pageNum = 1
            this.getList()
          }else if(response.total===0){
            this.postPersons = [];
            this.total = 0;
          }
        })
      }else{
        this.$modal.msgWarning("请输入岗位编码")
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.compId = this.logincompId
      this.postPersons = [];
      this.total = 0;
    }
  }
}
</script>

<style scoped>
.treeDept {
  height: 81vh;
  width: 100%;
}

.treeScrollbar {
  height: 100%;
}
</style>
