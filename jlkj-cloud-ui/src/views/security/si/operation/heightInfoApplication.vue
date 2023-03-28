<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="right" label-width="100px" style="padding: 0 10px">
      <el-row :gutter="10">
        <el-col :span="24" style="padding-right: 20px">
          <el-col :span="8">
            <el-form-item label="申请单位" prop="apply_depart_id">
              <select-tree v-model="form.apply_depart_id" placeholder="请选择申请单位"
                           :selectForm="applyDeptForm" :data="deptData" :otherForm="['flag']"
                           :isSelectNode="true" :isClearable="true" :isAccordion="true" :isFilterable="true"
                           :isNodeChildrenNull="true" @change="getApplyDeptForm"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人" prop="apply_person_id">
              <el-select v-model="form.apply_person_id" placeholder="请选择申请人" :popper-append-to-body="false">
                <el-option v-for="item in applyPersonData" :key="item.id" :label="item.label" :value="item.id"
                           @click.native="getApplyPersonForm(item)">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业证编号" prop="job_code">
              <el-input v-model="form.job_code" disabled/>
            </el-form-item>
          </el-col>

          <el-col :span="16">
            <el-form-item label="作业时间" prop="planDate">
              <el-date-picker v-model="form.planDate" type="datetimerange" start-placeholder="计划开始日期"
                              range-separator="-"
                              end-placeholder="计划结束日期"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业级别" prop="job_level">
              <el-select v-model="form.job_level" placeholder="请选择作业级别" :disabled="type==='edit'">
                <el-option v-for="(value,index) in jobLevelOptions" :key="index" :label="value" :value="value"/>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="8">
            <el-form-item label="作业区域" prop="safe_area_id">
              <el-select v-model="form.safe_area_id" placeholder="请选择高处安全区域">
                <el-option v-for="item in safeAreaOptions" :key="item.id" :label="item.name" :value="item.id"
                           @click.native="changeSafeArea(item)"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业地点" prop="place">
              <el-input v-model="form.place" placeholder="高处安全详细地点"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业内容" prop="job_content">
              <el-input v-model="form.job_content"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="作业高度" prop="job_height">
              <el-input v-model="form.job_height"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业类别" prop="job_category">
              <el-input v-model="form.job_category"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="其他作业" prop="other_job">
              <!--              涉及的其它特殊作业-->
              <el-input v-model="form.other_job"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="安全教育人" prop="safe_education_persons_id">
              <select-tree v-model="form.safe_education_persons_id" placeholder="请选择监护人"
                           :selectForm="educationPersonForm" :data="userData" :otherForm="['flag']"
                           :validate="{flag:'user'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true" @change="getEducationPersonForm"/>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="危害辨识" prop="harm_identification_list">
              <el-select v-model="form.harm_identification_list" placeholder="请选择危害辨识" multiple clearable
                         @change="$forceUpdate()">
                <el-option v-for="(value,index) in harmOptions" :key="index" :label="value" :value="value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="受教育人" prop="educ_comr_id">
              <select-tree v-model="form.educ_comr_id" placeholder="请选择监护人"
                           :selectForm="educComrForm" :data="userData" :otherForm="['flag']"
                           :validate="{flag:'user'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true" @change="getEducComrForm"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="监护人" prop="guardian_person_id">
              <select-tree v-model="form.guardian_person_id" placeholder="请选择监护人"
                           :selectForm="guardianPersonForm" :data="userData" :otherForm="['flag']"
                           :validate="{flag:'user'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true" @change="getGuardianPersonForm"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="负责人" prop="job_manager_id">
              <select-tree v-model="form.job_manager_id" placeholder="请选择负责人"
                           :selectForm="managerForm" :data="userData" :otherForm="['jobNumber']"
                           :validate="{flag:'user'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true" @change="getManagerForm"/>
            </el-form-item>
          </el-col>
        </el-col>
        <el-col :span="24">
          <div style="position: absolute;">
            <el-button class="bottonRight" v-if="activeName=== 'personnel'" type="primary" size="mini"
                       icon="el-icon-plus"
                           style="margin: 5px 0;" @click="personOpen = true">作业人员
            </el-button>
          </div>
          <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="作业人员" name="personnel">
              <el-table size="small" :data="person_list" stripe v-loading="loading1">
                <el-table-column prop="person_name" label="作业人"/>
                <el-table-column prop="construction_unit_name" label="所属单位名称"/>
                <el-table-column prop="type" label="所属单位类型">
                  <template slot-scope="scope">
                    {{ scope.row.type === 1 ? '本厂':scope.row.type === 2 ? '外委':'' }}
                  </template>
                </el-table-column>
                <el-table-column prop="work_type_name" label="特种作业工种"/>
                <el-table-column prop="work_card_code" label="特种作业证号"/>
                <el-table-column label="操作" width="100px">
                  <template slot-scope="scope">
                    <el-button size="mini" plain icon="el-icon-delete" type="text"
                               @click="handleDelete(scope.$index,'personnelOpen')"/>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="审批人员" name="approval">
              <el-table size="small" :data="approval_list" stripe v-loading="loading3">
                <el-table-column type="index" label="序号" width="90"/>
                <el-table-column prop="approval_name" label="审批环节"/>
                <el-table-column prop="approval_person_name" label="审批人姓名" width="150"/>
                <el-table-column prop="pick_person_name" label="选择人姓名" width="150">
                  <template slot-scope="scope">
                    {{scope.row.pick_person_name}}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="130px" v-if="!form.id">
                  <template slot-scope="scope">
                    <el-button size="mini" plain icon="el-icon-edit" @click="handleEdit(scope.$index,scope.row)"/>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="附件" name="file">
              <el-upload
                style="width: 50%"
                class="upload-demo"
                ref="upload"
                action="http://192.168.105.249:9300/file/upload"
                multiple
                :on-remove="handleRemove"
                :on-preview="handlePreview"
                :on-success="handelSuccess"
                :file-list="fileList"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <el-button style="margin-left: 10px;" size="small" type="text" @click="submitUpload">上传到服务器
                </el-button>
                <div slot="tip" class="el-upload__tip">文件不能超过1024MB</div>
              </el-upload>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="personOpen">
      <el-dialog class="customDialogStyle" title="作业人员" :visible.sync="personOpen" width="600px"
                 append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <div class="head-container">
          <el-form :model="personForm" ref="personForm" :rules="personRules" label-position="right" label-width="100px"
                   style="padding: 0 10px">

            <el-form-item label="作业人" prop="person_id">
              <select-tree v-model="personForm.person_id" placeholder="请选择作业人"
                           :selectForm="constructorForm" :data="constructorData"
                           :otherForm="['pid','pLabel','topLabel']"
                           :validate="{flag:'U'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true"
                           @change="getConstructor"/>
            </el-form-item>
            <el-form-item label="工种" prop="work_type_name">
              <el-input v-model="personForm.work_type_name"/>
            </el-form-item>
            <el-form-item label="作业证编号" prop="work_card_code">
              <el-input v-model="personForm.work_card_code"/>
            </el-form-item>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm('personOpen')">确 定</el-button>
          <el-button @click="cancel('personOpen')">取 消</el-button>
        </div>
      </el-dialog>
    </div>
    <div v-if="approvalOpen">
      <el-dialog class="customDialogStyle" title="审批人员" :visible.sync="approvalOpen" width="600px"
                 append-to-body :close-on-click-modal="false">
        <div class="head-container">
          <el-form :model="approvalForm" ref="approvalForm" :rules="approvalRules" label-position="right"
                   label-width="110px"
                   style="padding: 0 10px">
            <el-form-item label="序号" prop="sort">
              <el-input v-model="approvalForm.sort" disabled/>
            </el-form-item>
            <el-form-item label="审批环节" prop="approval_name">
              <el-input v-model="approvalForm.approval_name" disabled/>
            </el-form-item>
            <el-form-item label="审批人姓名" prop="approval_person_id">
              <select-tree v-model="approvalForm.approval_person_id" placeholder="请选择作业人"
                           :selectForm="approvalPersonForm" :data="userData" :validate="{flag:'user'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true"
                           @change="getApprovalPerson"/>
            </el-form-item>
            <el-form-item label="选择人姓名" prop="pick_person_id">
              <select-tree v-model="approvalForm.pick_person_id" placeholder="请选择作业人"
                           :selectForm="pickPersonForm" :data="userData" :validate="{flag:'user'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true"
                           @change="getPickPerson"/>
            </el-form-item>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm('approvalOpen')">确 定</el-button>
          <el-button @click="cancel('approvalOpen')">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {getHumanTree, getOrganizationUserList} from "@/api/human/hm/humanResourceTree";
  import {getConstructorTree} from '@/api/security/si/WorkTicketApi'
  import {req} from "@/api/security/si/common";
  import {getSafetyArea} from "@/api/security/si/WorkTicketApi";
  import {mapGetters} from "vuex";

  export default {
    name: "heightInfoApplication",
    props: ['data', 'type'],
    data() {
      return {
        activeName: 'personnel',
        form: {
          harm_identification_list: [],
          file_list: [],
        },
        tableData: [{}],
        planDate: [],
        rules: {
          apply_depart_id: [{required: true, message: '请选择申请单位', trigger: 'blur'},],
          apply_person_id: [{required: true, message: '请选择申请人', trigger: 'blur'},],
          planDate: [{required: true, message: '请输入作业时间', trigger: 'blur'},],
          job_level: [{required: true, message: '请输入选择级别', trigger: 'blur'},],
          job_content: [{required: true, message: '请输入作业内容', trigger: 'blur'},],
          safe_area_id: [{required: true, message: '请输入选择区域', trigger: 'blur'},],
          place: [{required: true, message: '请输入作业地点', trigger: 'blur'},],
          job_height: [{required: true, message: '请输入作业高度', trigger: 'blur'},],
          other_job: [{required: true, message: '请输入其他作业', trigger: 'blur'},],
          job_category: [{required: true, message: '请输入作业类别', trigger: 'blur'},],
          safe_education_persons: [{required: true, message: '请输入安全教育人', trigger: 'blur'},],
          harm_identification_list: [{required: true, message: '请选择危害辨识', trigger: 'blur'},],
          guardian_person_id: [{required: true, message: '请选择监护人', trigger: 'blur'},],
          job_manager_id: [{required: true, message: '请选择负责人', trigger: 'blur'},],
        },
        //作业人员
        personForm: {
          construction_unit_id: '', construction_unit_name: '', person_id: '', person_name: '',
          type: '', work_type_name: '', work_card_code: '',
        },
        personRules: {
          person_id: [{required: true, message: '请选择作业人', trigger: 'blur'},],
          work_type_name: [{required: true, message: '请输入工种', trigger: 'blur'},],
          work_card_code: [{required: true, message: '请输入作业证编号', trigger: 'blur'},],
        },
        person_list: [], loading1: true, personOpen: false,
        //审批人员
        approvalForm: {},
        approvalRules: {},
        approval_list: [], approvalIndex: -1, loading3: true, approvalOpen: false,
        //附件
        fileList: [],

        //下拉树
        userData: [], deptData: [], applyPersonData: [], constructorData: [],
        applyDeptForm: {value: '', label: ''}, applyPersonForm: {value: '', label: ''},
        educationPersonForm: {value: '', label: ''}, educComrForm: {value: '', label: ''},
        guardianPersonForm: {value: '', label: ''}, managerForm: {value: '', label: ''},
        constructorForm: {value: '', label: ''},
        approvalPersonForm: {value: '', label: ''},
        pickPersonForm: {value: '', label: ''},
        //下拉列表
        jobLevelOptions: ['一级', '二级', '三级'],
        safeAreaOptions: [],
        harmOptions: ['高处坠落', '物体打击', '高空坠物', '起重伤害', '火灾', '中毒', '其他'],
      };
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      getHumanTree().then(res => {
        this.userData = res.data.data;
        this.updateDeptTreeData(res.data, (res) => {
          this.deptData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
      getConstructorTree().then(res => {
        this.updateConstructorTree(res.data.data, {id: ""}, "", (res) => {
          this.constructorData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
      getSafetyArea().then(res => {
        this.safeAreaOptions = res.data.data;
      }, error => {
        window.console.log(error);
      });
    },
    mounted() {
      if (this.data.id) {
        getOrganizationUserList(this.data.apply_depart_id).then(res => {
          this.applyPersonData = res.data.data;
        }, error => {
          window.console.log(error);
        });
        this.form = {...this.data};
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
        this.form.planDate = [this.data.start_time, this.data.end_time];
        this.onLoad();
      } else {
        this.loading1 = false;
        this.loading2 = false;
        this.loading3 = false;
        req('get', 'high/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      }
    },
    methods: {
      //————————————功能方法————————————//
      onLoad() {
        let that = this;
        alert(1)
        req('get', 'high/person/list', {id: this.form.id}).then(res => {
          console.log(res);
          alert(res)
          this.person_list = res.data.data;//表格数据
          this.loading1 = false;
        }, error => {
          this.loading1 = false;
          window.console.log(error);
        });
        req('get', 'high/approval/list', {id: this.form.id}).then(res => {
          this.approval_list = res.data.data;//表格数据
          this.loading3 = false;
        }, error => {
          this.loading3 = false;
          window.console.log(error);
        });
        let url = window.location.hostname;
        url = '192.168.105.249';
        req('get', 'high/file/list', {id: this.form.id}).then(res => {
          this.form.file_list = res.data.data;
          res.data.data.forEach(function (obj) {
            that.fileList.push({
              name: obj.file_name,
              appendixId: obj.appendix_id,
              url: 'http://' + url + ':9300/file/download/' + obj.appendix_id
            });
          });
        }, error => {
          window.console.log(error);
        });
      },
      saveMethod() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (!this.form.id) {
              let data = {
                ...this.form,
                harm_identification: this.form.harm_identification_list.join('|'),
                person_list: this.person_list,
                approval_list: this.approval_list,
                create_user_id: this.$store.getters.userInfo.userId,
                create_user_name: this.$store.getters.userInfo.nickName,
              };
              req('post', 'high/insert', data).then(res => {
                if (res.data.code === "0") {
                  this.$emit('setTicketId', res.data.data.id);
                  this.form.id = res.data.data.id;
                  this.$message({
                    type: "success", message: "操作成功！", duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.data.msg)
                    }
                  });
                }
              }, error => {
                window.console.log(error);
              });
            } else {
              let data = {
                ...this.form,
                harm_identification: this.form.harm_identification_list.join('|'),
                person_list: this.person_list,
                approval_list: this.approval_list,
                modify_user_id: this.$store.getters.userInfo.userId,
                modify_user_name: this.$store.getters.userInfo.nickName,
              };
              req('post', 'high/update', data).then(res => {
                if (res.data.code === "0") {
                  this.$message({
                    type: "success", message: "操作成功！", duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.data.msg)
                    }
                  });
                }
              }, error => {
                window.console.log(error);
              });
            }
          }
        });
      },
      //————————————列表方法————————————//
      //删除
      handleDelete(index, open) {
        //作业人员
        if (open === 'personnelOpen') {
          delete this.person_list.splice(index, 1);
        }
      },
      //审批
      handleEdit(index, row) {
        this.approvalOpen = true;
        this.approvalIndex = index;
        this.approvalForm = row;
      },
      submitForm(open) {
        if (open === 'personOpen') {
          this.person_list.push(this.personForm);
          this.constructorForm = {value: '', label: ''};
          this.personForm = {};
          this.personOpen = false;
        } else if (open === 'approvalOpen') {
          this.approval_list[this.approvalIndex] = this.approvalForm;
          this.approvalPersonForm = {value: '', label: ''};
          this.pickPersonForm = {value: '', label: ''};
          this.approvalForm = {};
          this.approvalOpen = false;
        }
      },
      cancel(open) {
        if (open === 'personOpen') {
          this.personOpen = false;
        } else if (open === 'approvalOpen') {
          this.approvalPersonForm = {value: '', label: ''};
          this.pickPersonForm = {value: '', label: ''};
          this.approvalForm = {};
          this.approvalOpen = false;
        }
      },
      //————————————附件————————————//
      submitUpload() {
        this.$refs.upload.submit();
      },
      handleRemove(file) {
        if (file.appendixId) {
          this.form.file_list = this.form.file_list.filter(item => item.appendix_id !== file.appendixId);
        } else {
          this.form.file_list = this.form.file_list.filter(item => item.appendix_id !== file.response.data.id);
        }
      },
      handelSuccess(response) {
        this.form.file_list.push({appendix_id: response.data.id});
      },
      handlePreview(file) {
        if (file.url) {
          window.open(file.url);
        }
      },
      //————————————下拉树————————————//
      getApplyDeptForm(obj) {
        this.form.apply_depart_id = obj.value;
        this.form.apply_depart_name = obj.label;
        getOrganizationUserList(obj.value).then(res => {
          this.applyPersonData = res.data.data;
        }, error => {
          window.console.log(error);
        });
      },
      getApplyPersonForm(obj) {
        this.form.apply_person_name = obj.label;
      },
      getEducationPersonForm(obj) {
        this.form.safe_education_persons_id = obj.value;
        this.form.safe_education_persons = obj.label;
      },
      getEducComrForm(obj) {
        this.form.educ_comr_id = obj.value;
        this.form.educ_com_name = obj.label;
      },
      getGuardianPersonForm(obj) {
        this.form.guardian_person_id = obj.value;
        this.form.guardian_person_name = obj.label;
      },
      getManagerForm(obj) {
        this.form.job_manager_id = obj.value;
        this.form.job_manager_name = obj.label;
        this.form.job_manager_no = obj.job_number;
      },
      getConstructor(obj) {
        this.personForm.person_id = obj.value;
        this.personForm.person_name = obj.label;
        this.personForm.type = obj.topLabel === '本厂' ? 1 : 2;
        this.personForm.construction_unit_id = obj.pid;
        this.personForm.construction_unit_name = obj.pLabel;
      },
      getApprovalPerson(obj) {
        this.approvalForm.approval_person_id = obj.value;
        this.approvalForm.approval_person_name = obj.label;
      },
      getPickPerson(obj) {
        this.approvalForm.pick_person_id = obj.value;
        this.approvalForm.pick_person_name = obj.label;
      },
      changeSafeArea(obj) {
        this.form.safe_area_name = obj.name;
      },
      //————————————递归————————————//
      //递归过滤数据，将部门筛选出来
      updateDeptTreeData: function (data, callback) {
        let that = this;
        let list = [];
        data.forEach(function (obj) {
          if (obj.flag === 'dept') {
            if (obj.children) {
              let children = [];
              that.updateDeptTreeData(obj.children, (res) => {
                children = [...res];
              });
              list.push({...obj, children: children})
            } else {
              list.push({...obj})
            }
          }
        });
        callback(list);
      },
      updateConstructorTree: function (data, pObj, topLabel, callback) {
        let that = this;
        let list = [];
        let pLabel = "";
        if (pObj.label) {
          pLabel = pObj.label
        }
        if (pObj.pid === "") {
          topLabel = pObj.label
        }
        data.forEach(function (obj) {
          if (obj.pid === pObj.id) {
            that.updateConstructorTree(data, obj, topLabel, (res) => {
              list.push({
                id: obj.id.slice(2), label: obj.label, flag: obj.id.slice(0, 1) === 'P' ? 'U':obj.id.slice(0, 1),
                pid: pObj.id.slice(2), pLabel: pLabel, topLabel: topLabel, children: [...res]
              });
            });
          }
        });
        callback(list);
      },
    },
    // 侦听器
    watch: {
      'form.planDate'(newValue) {
        if (newValue.length > 0) {
          this.form.start_time = this.$moment(newValue[0]).format('YYYY-MM-DD HH:mm:ss');
          this.form.end_time = this.$moment(newValue[1]).format('YYYY-MM-DD HH:mm:ss');
        } else {
          this.form.start_time = "";
          this.form.end_time = "";
        }
      },
      'form.apply_depart_id'(newValue) {
        this.applyDeptForm.value = newValue;
      },
      'form.apply_person_id'(newValue) {
        this.applyPersonForm.value = newValue;
      },
      'form.safe_education_persons_id'(newValue) {
        this.educationPersonForm.value = newValue;
      },
      'form.educ_comr_id'(newValue) {
        this.educComrForm.value = newValue;
      },
      'approvalForm.approval_person_id'(newValue) {
        if (newValue && newValue !== '0') {
          this.approvalPersonForm.value = newValue;
        }
      },
      'approvalForm.pick_person_id'(newValue) {
        if (newValue && newValue !== '0') {
          this.pickPersonForm.value = newValue;
        }
      },
      'form.guardian_person_id'(newValue) {
        this.guardianPersonForm.value = newValue;
      },
      'form.job_manager_id'(newValue) {
        this.managerForm.value = newValue;
      },
      'form.job_level'() {
        if (!this.form.id) {
          this.loading3 = true;
          req('get', 'high/approval/template/list', {job_level: this.form.job_level}).then(res => {
            let data = res.data.data;
            let d = [];
            data.forEach(item => {
              d.push({...item, ...{approval_person_name: '', pick_person_name: ''}})
            });
            this.approval_list = d;
            this.loading3 = false;
          }, error => {
            window.console.log(error);
          });
        }
      },
    },
  }
</script>

<style scoped>
  .el-col {
    margin-bottom: 0
  }

  .bottonRight {
    position: relative;
    left: 270px;
    top: 3px;
    z-index: 10
  }
</style>
<style>
  .el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
