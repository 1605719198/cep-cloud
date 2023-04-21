<template>
  <div v-if="visible">
    <!-- 添加或修改岗位信息数据维护对话框 -->
    <el-dialog :title="title"  :visible.sync="visible" width="1000px" append-to-body>
      <el-tabs tab-position="top" type="border-card">
        <el-tab-pane label="岗位基本设定" >
          <el-form ref="forms" :model="form" :rules="rules" label-width="180px">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="岗位名称" prop="postName">
                  <el-input v-model="form.postName" placeholder="请输入岗位名称" maxlength="200"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="岗位编码" prop="postCode">
                  <el-input v-model="form.postCode" placeholder="请输入岗位编码" maxlength="100" disabled/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="所属公司/机构" prop="orgId">
                  <treeselect v-model="form.orgId" :options="allDeptOptions" :show-count="true" placeholder="请选择所属公司/机构" :normalizer="normalizer" @select="deptChange"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-radio-group v-model="form.status">
                    <el-radio
                      v-for="dict in dict.type.sys_normal_disable"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="专长类别" prop="specialityTypeId">
                  <el-select :popper-append-to-body="false" v-model="form.specialityTypeId" placeholder="请选择专长类别" @change="handleChangeA"  class="maxWidth" >
                    <el-option
                      v-for="dict in baseInfoData.HP013"
                      :key="dict.uuid"
                      :label="dict.dicName"
                      :value="dict.uuid"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="专长" prop="specialityId">
                  <el-select :popper-append-to-body="false" v-model="form.specialityId" placeholder="请选择专长" class="maxWidth" >
                    <el-option
                      v-for="dict in deepOptions1"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="岗位序列" prop="postSequenceId">
                  <el-select :popper-append-to-body="false" v-model="form.postSequenceId" placeholder="请选择岗位序列" @change="handleChangeB" class="maxWidth">
                    <el-option
                      v-for="dict in baseInfoData.HP003"
                      :key="dict.uuid"
                      :label="dict.dicName"
                      :value="dict.uuid"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="岗位类别" prop="postTypeId">
                  <el-select :popper-append-to-body="false" v-model="form.postTypeId" placeholder="请选择岗位类别" class="maxWidth">
                    <el-option
                      v-for="dict in deepOptions2"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="职位名称" prop="jobTitleId">
                  <el-select :popper-append-to-body="false" v-model="form.jobTitleId" placeholder="请选择职位名称" @change="handleChangeC" class="maxWidth">
                    <el-option
                      v-for="dict in humanJobTitle"
                      :key="dict.jobTitleNo"
                      :label="dict.jobTitleNo+'-'+ dict.jobTitleName"
                      :value="dict.jobTitleNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="流水号" prop="serialNumber">
                  <el-input v-model="form.serialNumber" placeholder="请输入流水号" maxlength="20" disabled />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="工作地点" prop="workAddressId">
                  <el-select :popper-append-to-body="false" v-model="form.workAddressId" placeholder="请选择工作地点" class="maxWidth">
                    <el-option
                      v-for="dict in baseInfoData.HP004"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公司(具体工作地点)" prop="workAddress">
                  <el-select :popper-append-to-body="false" v-model="form.workAddress" placeholder="请选择公司" class="maxWidth">
                    <el-option
                      v-for="dict in companyList"
                      :key="dict.compId"
                      :label="dict.companyName"
                      :value="dict.compId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="驻外地点(具体工作地点)" prop="overseasLocations">
                  <el-input v-model="form.overseasLocations" placeholder="请输入驻外地点" maxlength="100"  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="职位类别" prop="deptType">
                  <el-select :popper-append-to-body="false" v-model="form.deptType" placeholder="请选择职位类别" class="maxWidth">
                    <el-option
                      v-for="dict in baseInfoData.HP020"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20" >
              <el-col :span="12">
                <el-form-item label="定员" prop="planCapacity">
                  <el-input v-model="form.planCapacity" placeholder="请输入定员" type="number" maxlength="10"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="现员" prop="nowCapacity">
                  <el-input v-model="form.nowCapacity" placeholder="" type="number" maxlength="10" disabled/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20" >
              <el-col :span="12">
                <el-form-item label="该岗位直接下级定员数" prop="planCapacity" >
                  <el-input v-model="form.planCapacity" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="排序序号" prop="postSort">
                  <el-input v-model="form.postSort" placeholder="请输入排序序号" type="number" maxlength="5"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="上级岗位名称" prop="parentPostName">
                  <el-input maxlength="200"  v-model="form.parentPostName" placeholder="请选择上级岗位" disabled>
                    <el-button slot="append" icon="el-icon-search" @click="inputPostClick()"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="成本中心" prop="costCenterId">
                  <el-input v-model="form.costCenterId" placeholder="请输入成本中心" maxlength="500"/>
                </el-form-item>
              </el-col>
            </el-row>


            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="变更原因" prop="changeReason">
                  <el-input v-model="form.changeReason" type="textarea" show-word-limit   placeholder="请输入内容" maxlength="1000" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="用工性质" prop="employmentNatureId">
                  <el-select :popper-append-to-body="false" v-model="form.employmentNatureId" placeholder="请选择用工性质" class="maxWidth">
                    <el-option
                      v-for="dict in baseInfoData.HP006"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="职位费用类别" prop="positionExpenseCategory">
                  <el-select :popper-append-to-body="false" v-model="form.positionExpenseCategory" placeholder="职位费用类别" class="maxWidth">
                    <el-option
                      v-for="dict in baseInfoData.HP007"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="定员依据" prop="capacityBasis">
                  <el-input v-model="form.capacityBasis" type="textarea" show-word-limit   placeholder="请输入内容" maxlength="500"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="生效日期" prop="effectDate">
                  <el-date-picker clearable
                                  class="maxWidth"
                                  v-model="form.effectDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择生效日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20" >
              <el-col :span="12">
                <el-form-item label="输入人" prop="updateBy">
                  {{form.updateBy}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="输入日期" prop="updateTime">
                  {{form.updateTime}}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="岗位职责设置">
          <duty ref="duty"></duty>
        </el-tab-pane>
        <el-tab-pane label="岗位任职资格设置">
          <qualification ref="qualification"> </qualification>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <div v-show="ifupdate" slot="footer" >
        <el-divider content-position="center" >变更记录</el-divider>
      </div>
      <el-table :data="formHistory"  v-show="ifupdate" slot="footer" >
        <el-table-column label="版本号" prop="versionNo" width="75" align="center" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.versionNo}}</span>
          </template>
        </el-table-column>
        <el-table-column label="岗位编码" prop="postCode" width="75" align="center" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.postCode}}</span>
          </template>
        </el-table-column>
        <el-table-column label="岗位名称" prop="postName" align="center" width="150" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.postName}}</span>
          </template>
        </el-table-column>
        <el-table-column label="所属公司/机构" prop="orgName" align="center" width="200" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.orgName}}</span>
          </template>
        </el-table-column>
        <el-table-column label="定员" prop="planCapacity" width="55" align="center" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.planCapacity}}</span>
          </template>
        </el-table-column>
        <el-table-column label="生效日期" prop="effectDate" align="center" width="100" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.effectDate}}</span>
          </template>
        </el-table-column>
        <el-table-column label="输入人" prop="updateBy" align="center" width="80" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{scope.row.updateBy}}</span>
          </template>
        </el-table-column>
        <el-table-column label="输入日期" prop="updateTime" align="center" show-overflow-tooltip>
          <template v-slot="scope">
            <span v-text="scope.row.updateTime"   ></span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <select-post ref="selectPost" @ok="getPost"/>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {  treeselect,selectCompany } from "@/api/human/hp/deptMaintenance";
import {  listJobTitleidname } from "@/api/human/hp/jobTitle";
import duty from './duty'
import qualification from './qualification'
import { listPostVersion, addPostMaintenance, updatePostMaintenance } from "@/api/human/hp/postMaintenance";
import { getBaseInfo, getDegreeMajorSpecialization } from "@/api/human/hm/baseInfo"
import selectPost from "@/views/components/human/selectView/selectPost";
export default {
  name: 'AddOrUpdate',
  components: {
    duty,
    qualification,
    Treeselect,
    selectPost
  },
  dicts: ['sys_normal_disable'],
  data(){
    return{
      //公司数据
      companyList:[],
      //是否为修改
      ifupdate:false,
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'HP003',
          'HP013',
          'HP004',
          'HP006',
          'HP007',
          'HP020'
        ]
      },
      //部门选单数据
      allDeptOptions:[],
      //选单数据
      baseInfoData: [],
      //专长类别-专长选单
      deepOptions1:[],
      //岗位序列-岗位类别选单
      deepOptions2:[],
      //dialog标题名
      title:'岗位设定管理',
      //dialog是否显示
      visible:false,
      //表单参数
      form: {},
      //职位名称id列表
      humanJobTitle:[],
      //历史表单
      formHistory:[],
      // 表单校验
      rules: {
        orgId: [
          { required: true, message: "所属公司/机构不能为空", trigger: "blur" }
        ],
        jobTitleId: [
          { required: true, message: "职位名称不能为空", trigger: "blur" }
        ],
        postName: [
          { required: true, message: "岗位名称不能为空", trigger: "blur" }
        ],
        specialityTypeId: [
          { required: true, message: "专长类别不能为空", trigger: "change" }
        ],
        specialityId: [
          { required: true, message: "专长不能为空", trigger: "blur" }
        ],
        postSequenceId: [
          { required: true, message: "岗位序列不能为空", trigger: "change" }
        ],
        postTypeId: [
          { required: true, message: "岗位类别不能为空", trigger: "change" }
        ],
        workAddress: [
          { required: true, message: "公司不能为空", trigger: "change" }
        ],
        overseasLocations: [
          { required: true, message: "驻外地点不能为空", trigger: "blur" }
        ],
        planCapacity: [
          { required: true, message: "定员不能为空", trigger: "blur" }
        ],
        postSort: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ],
        costCenterId: [
          { required: true, message: "成本中心不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        parentPostName: [
          { required: true, message: "该岗位上级岗位名称不能为空", trigger: "change" }
        ],
      },
      flagForm: {}
    }
  },
  methods:{
    //岗位弹窗打开事件
    inputPostClick() {
      var compId= this.form.compId;
      this.$refs.selectPost.show(compId);
    },
    //岗位弹窗确定事件
    getPost(postData) {
      this.form.parentPostId = postData.postId;
      this.form.parentPostName = postData.postName
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    init(form) {
      this.getCompanyList();
      listJobTitleidname('J00').then(response =>{
        this.humanJobTitle = response.rows;
      })
      treeselect().then(response => {
        this.allDeptOptions = response.data;
      });
      if(this.baseInfoData.length==0){
        this.getHumandisc();
      }
      this.form=form;
      if (this.form.postId != null){
        this.ifupdate = true;
        var queryParams={
          postId:this.form.postId
        }
        listPostVersion(queryParams).then(response => {
          this.formHistory = response.rows;
        });
      }
      this.deepOptions1 =[];
      this.deepOptions2 =[];
      if(this.form.specialityTypeId){
        this.handleChangeA(this.form.specialityTypeId)
      }
      if(this.form.postSequenceId){
        this.handleChangeB(this.form.postSequenceId)
      }
      this.visible = true;
      this.$nextTick(() => {
        this.$refs.duty.init(this.form)
      })
      this.$nextTick(() => {
        this.$refs.qualification.init(this.form)
      })
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      // if (node.children && !node.children.length) {
      //   delete node.children;
      // }
      return {
        id: node.id,
        label: node.label,
        label2: node.label2,
        children: node.children
      };
    },
    /** 所属公司/机构切换事件 */
    deptChange(val) {
      this.form.orgName = val.label
      this.form.deptId = val.id
      this.form.orgId = val.id
      this.form.compId = val.label2
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["forms"].validate(valid => {
        if (valid) {
          if (this.form.postId != null) {
            updatePostMaintenance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.visible = false;
              this.$emit("get-list");
            });
          } else {
            addPostMaintenance(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.visible = false;
              this.$emit("get-list");
            });
          }
        }
      });
    },
    //获取人事选单字典
    getHumandisc(){
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data;
      });
    },
    //专长类别-专长选项获取
    handleChangeA(value) {
      this.baseInfo.uuid = value
      this.deepOptions1 = []
      getDegreeMajorSpecialization(this.baseInfo).then(response => {
        this.deepOptions1 = response.data
      });
    },
    //岗位序列-岗位类别选项获取
    handleChangeB(value) {
      this.baseInfo.uuid = value
      this.deepOptions2 = []
      getDegreeMajorSpecialization(this.baseInfo).then(response => {
        this.deepOptions2 = response.data
      });
    },
    //职位名称选择
    handleChangeC(value,label){
    },
    //取消操作
    cancel() {
      this.visible = false;
      this.form={};
    },

  }
}
</script>
<style scoped>
.maxWidth{
  width: 100%;
}
.el-select {
  width: 100%;
}
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
