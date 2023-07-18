<template>
  <div class="avue-crud">
    <el-form :model="form"
             ref="form"
             :rules="rules"
             label-position="right"
             label-width="120px">
      <el-row :gutter="10">
        <el-col :span="24"
                style="padding: 0px 40px">
          <el-col :span="8">
            <el-form-item label="申请单位">
              <el-input class="disabled"
                        placeholder="申请单位"
                        v-model="form.apply_depart_name"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人">
              <el-input class="disabled"
                        placeholder="申请人"
                        v-model="form.apply_person_name"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业证编号"
                          prop="job_code">
              <el-input v-model="form.job_code"
                        disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备单位"
                          prop="equipment_unit_id">
              <select-tree v-model="form.equipment_unit_id"
                           placeholder="请选择设备单位"
                           :isDisabled="isDisabled"
                           :selectForm="equipmentDeptForm"
                           :data="deptData"
                           :otherForm="['flag']"
                           :isSelectNode="true"
                           :isClearable="true"
                           :isAccordion="true"
                           :isFilterable="true"
                           :isNodeChildrenNull="true"
                           @change="getEquipmentDeptForm"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开始时间"
                          prop="start_time">
              <el-date-picker v-model="form.start_time"
                              type="datetime"
                              placeholder="计划开始时间"
                              format="yyyy-MM-dd HH:mm:ss"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束时间"
                          prop="end_time">
              <el-date-picker v-model="form.end_time"
                              type="datetime"
                              placeholder="计划结束时间"
                              format="yyyy-MM-dd HH:mm:ss"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业负责人"
                          prop="job_responsible_person_name">
              <el-input v-if="isDisabled"
                        readonly="readonly"
                        ref="level"
                        v-model="form.job_responsible_person_name"
                        disabled/>
              <el-input v-else
                        readonly="readonly"
                        v-model="form.job_responsible_person_name"
                        @click.native="treeOpen=true,treeOpenType='job_responsible_person_name'"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业负责人电话"
                          prop="job_responsible_person_phone">
              <el-input v-model="form.job_responsible_person_phone"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="特种作业证号"
                          prop="special_job_no">
              <el-input v-model="form.special_job_no"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否煤气区域"
                          prop="is_gas_area">
              <el-radio-group v-model="form.is_gas_area"
                              style="padding-top: 13px"
                              :disabled="isDisabled">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业级别"
                          prop="job_level_name">
              <el-input v-if="isDisabled"
                        readonly="readonly"
                        ref="level"
                        v-model="form.job_level_name"
                        disabled/>
              <el-input v-else
                        readonly="readonly"
                        ref="level"
                        v-model="form.job_level_name"
                        @click.native="levelOpen=true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业区域" prop="safe_area_id">
              <!-- <select-tree v-model="form.safe_area_id"
                           placeholder="请选择作业区域"
                           :isDisabled="isDisabled"
                           :selectForm="safeAreaForm"
                           :data="deptData"
                           :otherForm="['flag']"
                           :isSelectNode="true"
                           :isClearable="true"
                           :isAccordion="true"
                           :isFilterable="true"
                           :isNodeChildrenNull="true"
                           @change="getSafeAreaForm" /> -->
              <el-select class="customSelectStyle"
                         v-model="form.safe_area_id"
                         :popper-append-to-body="false"
                         style="width:100%;"
                         filterable
                         clearable
                         :disabled="isDisabled"
                         placeholder="请选择">
                <el-option v-for="item in safeAreaOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业地点"
                          prop="place">
              <el-input v-model="form.place"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="作业内容"
                          prop="job_contents">
              <el-input v-model="form.job_contents"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="其他作业"
                          prop="other_job">
              <el-input v-model="form.other_job"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="作业方式"
                          prop="mode_list">
              <el-select v-model="form.mode_list"
                         placeholder="请选择作业方式"
                         multiple
                         clearable
                         @change="$forceUpdate()"
                         :disabled="isDisabled">
                <el-option v-for="(value,index) in modeOptions"
                           :key="index"
                           :label="value"
                           :value="value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="安全教育人"
                          prop="safe_education_person_name">
              <el-input v-if="isDisabled"
                        readonly="readonly"
                        ref="level"
                        v-model="form.safe_education_person_name"
                        disabled/>
              <el-input v-else
                        readonly="readonly"
                        v-model="form.safe_education_person_name"
                        @click.native="treeOpen=true,treeOpenType='safe_education_person_name'"/>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="16">
            <el-form-item label="危害辨识"
                          prop="harm_identification_list">
              <el-checkbox-group v-model="form.harm_identification_list"
                         @change="$forceUpdate()">
                <el-checkbox v-for="(value,index) in harmOptions"
                           :key="index"
                           :label="value"/>
              </el-checkbox-group>
            </el-form-item>
          </el-col> -->
          <el-col :span="16">
            <el-form-item label="危害辨识" prop="harm_identification_list">
              <!--              <el-input v-model="form.harm_identification" :disabled="isDisabled"/>-->
              <el-checkbox-group v-model="form.harm_identification_list" placeholder="请选择危害辨识" multiple clearable
                                 :disabled="isDisabled"
                                 @change="$forceUpdate()">
                <el-checkbox v-for="(value,index) in harmOptions" :key="index" :label="value"/>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="监护人"
                          prop="guardian_person_name">
              <el-input v-if="isDisabled"
                        readonly="readonly"
                        ref="level"
                        v-model="form.guardian_person_name"
                        disabled/>
              <el-input v-else
                        readonly="readonly"
                        v-model="form.guardian_person_name"
                        @click.native="treeOpen=true,treeOpenType='guardian_person_name'"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工种"
                          prop="work_type_name">
              <el-input v-model="form.work_type_name"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产单位负责人"
                          prop="production_responsible_person_name">
              <el-input v-if="isDisabled"
                        readonly="readonly"
                        ref="level"
                        v-model="form.production_responsible_person_name"
                        disabled/>
              <el-input v-else
                        readonly="readonly"
                        v-model="form.production_responsible_person_name"
                        @click.native="treeOpen=true,treeOpenType='production_responsible_person_name'"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="相关单位监护人"
                          prop="relation_guardian_person_name">
              <el-input v-if="isDisabled"
                        readonly="readonly"
                        ref="level"
                        v-model="form.relation_guardian_person_name"
                        disabled/>
              <el-input v-else
                        readonly="readonly"
                        v-model="form.relation_guardian_person_name"
                        @click.native="treeOpen=true,treeOpenType='relation_guardian_person_name'"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工种"
                          prop="relation_guardian_person_work_type_name">
              <el-input v-model="form.relation_guardian_person_work_type_name"
                        :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="交底人" prop="special_name">
              <el-input v-model="form.special_name" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
        </el-col>
        <el-col :span="24">
          <el-tabs v-model="activeName"
                   type="card">
            <el-tab-pane label="作业人员"
                         name="personnel">
              <operator :data="data"
                        :isEdit="isEdit"
                        type="fire"/>
            </el-tab-pane>
            <el-tab-pane label="作业分析"
                         name="analysis">
              <hotWorkInfoApplicationAnalysis :data="data"/>
            </el-tab-pane>
            <el-tab-pane label="附件"
                         name="file">
              <fileList :data="data"
                        type="fire"/>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="treeOpen">
      <personnel-selection v-if="treeOpen"
                           :userDialogVisible="treeOpen"
                           @close-dialog="treeOpen = false"
                           :isMultiple="false"
                           :height="500"
                           @single-select="handleNodeClick"/>
    </div>
    <div v-if="levelOpen">
      <el-dialog class="customDialogStyle levelOpen"
                 title="选择等级"
                 :visible.sync="levelOpen"
                 width="800px"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <hotWorkLevel :data="form"
                      @close="levelOpen=false"
                      @getLevel="getLevel"/>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {getHumanTree} from "@/api/human/hm/humanResourceTree";
  import {req} from "@/api/security/si/common";
  import {getSafetyArea} from "@/api/security/si/WorkTicketApi";
  import {mapGetters} from "vuex";
  import operator from "./common/operator";
  import fileList from "./common/fileList";
  import hotWorkInfoApplicationAnalysis from "./hotWorkInfoApplicationAnalysis";
  import hotWorkLevel from "./hotWorkLevel";

  export default {
    name: "hotWorkInfoApplication",
    props: ['data', 'type'],
    components: {operator, fileList, hotWorkInfoApplicationAnalysis, hotWorkLevel},
    data() {
      let validateStartTime = (rule, value, callback) => {
        if (value) {
          if (this.form.end_time) {
            let times = this.compareDate(value, this.form.end_time);
            if (times > 86400000) {
              callback(new Error("作业时间不能超过一天"));
            } else if (times > 0) {
              callback();
            } else if (times === 0) {
              callback(new Error("开始时间和结束时间不能相同"));
            } else {
              callback(new Error("起始日期不能再结束日期之后，请重新选择"));
            }
          }
        }
      };
      let validateEndTime = (rule, value, callback) => {
        if (value) {
          if (this.form.start_time) {
            let times = this.compareDate(this.form.start_time, value);
            if (times > 86400000) {
              callback(new Error("作业时间不能超过一天"));
            } else if (times > 0) {
              callback();
            } else if (times === 0) {
              callback(new Error("开始时间和结束时间不能相同"));
            } else {
              callback(new Error("结束日期不能在起始日期前，请重新选择"));
            }
          }
        }
      };
      return {
        activeName: 'personnel',
        isUpdated: false,
        isEdit: true,
        job_level_max: '',
        form: {
          job_code: '',
          job_responsible_person_name: '',
          safe_education_person_name: '',
          guardian_person_name: '',
          relation_guardian_person_name: '',
          production_responsible_person_name: '',
          job_level_name: '',
          harm_identification_list: [],
        },
        rules: {
          apply_depart_id: [{required: true, message: '请选择申请单位', trigger: 'blur'},],
          apply_person_id: [{required: true, message: '请选择申请人', trigger: 'blur'},],
          start_time: [{required: true, message: '请选择作业开始时间', trigger: 'blur'},
            {validator: validateStartTime, trigger: 'blur'}],
          end_time: [{required: true, message: '请选择作业结束时间', trigger: 'blur'},
            {validator: validateEndTime, trigger: 'blur'}],
          safe_education_persons: [{required: true, message: '请输入安全教育人'}],
          educ_com_name: [{required: true, message: '请选择受教育人'}],
          guardian_person_name: [{required: true, message: '请选择监护人'}],
          job_manager_name: [{required: true, message: '请选择负责人',}],

          job_code: [{required: true, message: '请输入作业证编号', trigger: 'blur'},],
          planDate: [{required: true, message: '请输入作业时间', trigger: 'blur'},],
          job_level_name: [{required: true, message: '请选择作业级别', trigger: 'blur'},],
          mode_list: [{required: true, message: '请选择作业方式', trigger: 'blur'},],
          safe_area_id: [{required: true, message: '请输入作业区域', trigger: 'blur'},],
          place: [{required: true, message: '请输入作业地点', trigger: 'blur'},],
          other_job: [{required: true, message: '请输入其他作业', trigger: 'blur'},],
          job_responsible_person_name: [{required: true, message: '请选择作业负责人'},],
          safe_education_person_name: [{required: true, message: '请选择安全教育人'},],
          harm_identification_list: [{required: true, message: '请选择危害辨识', trigger: 'blur'},],

          guardian_person_id: [{required: true, message: '请输入监护人'},],
          production_responsible_person_name: [{required: true, message: '请选择生产单位负责人'},],
          work_type_name: [{required: true, message: '请输入工种', trigger: 'blur'},],
          relation_guardian_person_name: [{required: true, message: '请选择相关单位监护人'},],

          relation_guardian_person_work_type_name: [{required: true, message: '请输入工种', trigger: 'blur'},],

          equipment_unit_id: [{required: true, message: '请选择设备单位', trigger: 'blur'},],
          special_job_no: [{required: true, message: '请输入特种作业证号', trigger: 'blur'},],
          job_contents: [{required: true, message: '请输入作业内容', trigger: 'blur'},],
          job_responsible_person_phone: [
            {required: true, message: '请输入作业负责人电话', trigger: 'blur'},
            {
              pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/,
              required: true,
              message: "请输入正确的电话号码",
              trigger: "blur"
            }],
          is_gas_area: [{required: true, message: '请选择是否煤气区域', trigger: 'blur'},],
          special_name: [{required: true, message: '请输入交底人', trigger: 'blur'},],
        },
        //下拉列表
        jobLevelOptions: [],
        modeOptions: ['焊接', '钻孔', '切割', '明火', '打磨', '燃烧'],
        harmOptions: ['爆炸', '火灾', '窒息', '中毒', '机械伤害', '触电', '泄漏', '坠落', '物体打击', '灼烫', '其他'],
        safeAreaOptions: [],

        deptData: [],
        equipmentDeptForm: {value: '', label: ''},
        safeAreaForm: {value: '', label: ''},
        treeData: [], treeOpen: false, treeOpenType: '',
        levelOpen: false,
        isDisabled: false
      }
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      let that = this;
      if (this.type === 'edit') {
        this.isDisabled = this.data.status > 0;
      }
      getSafetyArea().then(res => {
        this.safeAreaOptions = res.data.data;
      }, error => {
        window.console.log(error);
      });
      getHumanTree().then(res => {
        this.treeData = res.data.data;
        this.updateDeptTreeData(res.data, 1, (res) => {
          this.deptData = [...res];
        });
      }, error => {
        window.console.log(error);
      });

      if (this.data.copyId) {
        this.form = {...this.data, job_level_name: ''};
        this.form.apply_person_id = this.$store.getters.userInfo.userId;
        this.form.apply_person_name = this.$store.getters.userInfo.nickName;
        this.form.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
        this.form.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
        this.form.start_time = '';
        this.form.end_time = '';
        this.form.mode_list = [...this.form.mode.split('|')];
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
        this.form.job_level_name = '';
        req('get', 'fire/joblevel/template/list', {}).then(res => {
          let data = res.data.data;
          let job_level = that.form.job_level.split(',');
          let level = '';
          data.map((item) => {
            if (job_level.includes(item.seq_no)) {
              if (item.job_level === '特级') {
                level = '特级';
              } else if (level !== '特级' && item.job_level === '一级') {
                level = '一级';
              } else if (level !== '特级' && level !== '一级' && item.job_level === '二级') {
                level = '二级';
              }
            }
          });
          that.form.job_level_name = level;
          that.$set(this, 'job_level_max', level);
        }, error => {
          window.console.log(error);
        });
        req('get', 'fire/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      } else if (this.data.id) {
        this.isEdit = this.data.status === 0;
        this.form = {...this.data, job_level_name: ''};
        this.form.mode_list = [...this.form.mode.split('|')];
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
        this.form.job_level_name = '';
        req('get', 'fire/joblevel/template/list', {}).then(res => {
          let data = res.data.data;
          let job_level = that.form.job_level.split(',');
          let level = '';
          data.map((item) => {
            if (job_level.includes(item.seq_no)) {
              if (item.job_level === '特级') {
                level = '特级';
              } else if (level !== '特级' && item.job_level === '一级') {
                level = '一级';
              } else if (level !== '特级' && level !== '一级' && item.job_level === '二级') {
                level = '二级';
              }
            }
          });
          that.form.job_level_name = level;
          that.$set(this, 'job_level_max', level);
        }, error => {
          window.console.log(error);
        });
      } else {
        this.form.apply_person_id = this.$store.getters.userInfo.userId;
        this.form.apply_person_name = this.$store.getters.userInfo.nickName;
        this.form.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
        this.form.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
        req('get', 'fire/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      }
    },
    updated() {
      this.isUpdated = true;
      console.log('updated')
    },
    methods: {
      //————————————功能方法————————————//
      saveMethod() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$emit('setSubmitLoading', true);
            //safe_area_name（区域名称）
            this.safeAreaOptions.forEach(item => {
              if (item.id === this.form.safe_area_id) {
                this.form.safe_area_name = item.name
              }
            })
            if (this.validateApproval()) {
              let file_list = this.data.file_list;
              if (file_list === undefined) {
                file_list = [];
              }
              let person_list = this.data.person_list;
              if (person_list === undefined) {
                person_list = [];
              }
              let analysis_list = this.data.analysis_list;
              if (analysis_list === undefined) {
                analysis_list = [];
              }

              if (this.form.job_level_name !== '二级' && analysis_list.length === 0) {
                this.$message.error('特技、一级单据必须填写动火分析数据！');
                this.$emit('setSubmitLoading', false);
              } else {
                if (!this.form.id) {
                  let data = {
                    ...this.form,
                    mode: this.form.mode_list.join('|'),
                    harm_identification: this.form.harm_identification_list.join('|'),
                    person_list: person_list,
                    analysis_list: analysis_list,
                    file_list: file_list,
                    approval_list: this.data.approval_list,
                    create_user_id: this.$store.getters.userInfo.userId,
                    create_user_name: this.$store.getters.userInfo.nickName,
                  };
                  req('post', 'fire/insert', data).then(res => {
                    if (res.code === 200) {
                      this.$emit('setTicketId', res.data.data.id);
                      this.form.id = res.data.id;
                      this.$message({
                        type: "success", message: "操作成功！", duration: 1000,
                        onClose: () => {
                          this.$emit('submitSave', res.data.msg)
                        }
                      });
                    }
                    this.$emit('setSubmitLoading', false);
                    this.$emit('cancel');
                  }, error => {
                    this.$emit('setSubmitLoading', false);
                    window.console.log(error);
                  });
                } else {
                  let data = {
                    ...this.form,
                    mode: this.form.mode_list.join('|'),
                    harm_identification: this.form.harm_identification_list.join('|'),
                    person_list: person_list,
                    analysis_list: analysis_list,
                    file_list: file_list,
                    approval_list: this.data.approval_list,
                    modify_user_id: this.$store.getters.userInfo.userId,
                    modify_user_name: this.$store.getters.userInfo.nickName,
                  };
                  req('post', 'fire/update', data).then(res => {
                    console.log(res);
                    if (res.code === 200) {
                      this.$message({
                        type: "success", message: "操作成功！", duration: 1000,
                        onClose: () => {
                          this.$emit('submitSave', res.msg)
                        }
                      });
                    }
                    this.$emit('setSubmitLoading', false);
                    this.$emit('cancel');
                  }, error => {
                    this.$emit('setSubmitLoading', false);
                    window.console.log(error);
                  });
                }
              }
            } else {
              this.$message.error('请先设置审批人！');
              this.$emit('setSubmitLoading', false);
            }
          }
        });
      },
      handleNodeClick(data) {
        if (this.treeOpenType === 'job_responsible_person_name') {
          this.form.job_responsible_person_id = data.id;
          this.form.job_responsible_person_name = data.full_name;
          this.$emit('setLastTaskUser', {id: data.id, name: data.full_name});
        } else if (this.treeOpenType === 'safe_education_person_name') {
          this.form.safe_education_persons_id = data.id;
          this.form.safe_education_person_name = data.full_name;
        } else if (this.treeOpenType === 'guardian_person_name') {
          this.form.guardian_person_id = data.id;
          this.form.guardian_person_name = data.full_name;
        } else if (this.treeOpenType === 'relation_guardian_person_name') {
          this.form.relation_guardian_person_id = data.id;
          this.form.relation_guardian_person_name = data.full_name;
        } else if (this.treeOpenType === 'production_responsible_person_name') {
          this.form.production_responsible_person_id = data.id;
          this.form.production_responsible_person_name = data.full_name;
        }
        this.treeOpen = false;
        this.treeOpenType = '';
      },
      changeSafeArea(obj) {
        this.form.safe_area_name = obj.name;
      },
      setPersonForm(form) {
        this.person_list.push(form);
        this.personOpen = false;
      },
      // 比较两个日期
      compareDate(start, end) {
        return new Date(end).getTime() - new Date(start).getTime();
      },
      validateApproval() {
        let approval_list = this.data.approval_list;
        let validate = true;
        if (approval_list && approval_list.length > 0) {
          for (let i = 0; i < approval_list.length; i++) {
            if (approval_list[i].approval_person_name === '') {
              validate = false;
            }
          }
        } else {
          validate = false;
        }
        return validate;
      },
      //递归过滤数据，将部门筛选出来
      updateDeptTreeData: function (data, level, callback) {
        let that = this;
        let list = [];
        data.forEach(function (obj) {
          if (level < 4) {
            if (obj.flag === 'dept') {
              if (obj.children) {
                let children = [];
                that.updateDeptTreeData(obj.children, level + 1, (res) => {
                  children = [...res];
                });
                list.push({...obj, children: children})
              } else {
                list.push({...obj})
              }
            }
          }
        });
        callback(list);
      },
      getEquipmentDeptForm(obj) {
        this.form.equipment_unit_id = obj.value;
        this.form.equipment_unit_name = obj.label;
      },
      getSafeAreaForm(obj) {
        this.form.safe_area_id = obj.value;
        this.form.safe_area_name = obj.label;
      },
      getLevel(form) {
        this.levelOpen = false;
        this.$refs.level.focus();
        this.form.job_level = form.seq_no;
        this.form.job_level_name = form.job_level;
        this.job_level_max = form.max;
        this.$refs.level.blur();
      },
      approvalOver() {
        this.isDisabled = true;
      }

    },
    // 侦听器
    watch: {
      'form.is_gas_area'(newValue) {
        if (this.isUpdated) {
          if (newValue === 0) {
            if (this.job_level_max === '特级' || this.job_level_max === '一级') {
              this.$emit('setLevel', '动火证特、一级（无煤气）');
            } else {
              this.$emit('setLevel', '动火证二级（无煤气）');
            }
          } else if (newValue === 1) {
            if (this.job_level_max === '特级' || this.job_level_max === '一级') {
              this.$emit('setLevel', '特级、一级动火证流程');
            } else {
              this.$emit('setLevel', '二级动火证流程');
            }
          }

        }
      },
      'form.job_level'() {
        if (this.isUpdated) {
          if (this.form.is_gas_area === 0) {
            if (this.job_level_max === '特级' || this.job_level_max === '一级') {
              this.$emit('setLevel', '动火证特、一级（无煤气）');
            } else {
              this.$emit('setLevel', '动火证二级（无煤气）');
            }
          } else if (this.form.is_gas_area === 1) {
            if (this.job_level_max === '特级' || this.job_level_max === '一级') {
              this.$emit('setLevel', '特级、一级动火证流程');
            } else {
              this.$emit('setLevel', '二级动火证流程');
            }
          }
        }
      },
      'form.equipment_unit_id'(newValue) {
        this.equipmentDeptForm.value = newValue;
      },
      'form.safe_area_id'(newValue) {
        this.safeAreaForm.value = newValue;
      },
    }
  }
</script>

<style scoped>
  .el-col {
    margin-bottom: 0;
  }

  .disabled {
    pointer-events: none;
  }
</style>
<style>
  .el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
