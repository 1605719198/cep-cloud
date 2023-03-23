<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="right" label-width="100px">
      <el-row :gutter="10">
        <el-col :span="24" style="padding: 0px 40px">
          <el-col :span="8">
            <el-form-item label="申请单位">
              <el-input class="disabled" placeholder="申请单位" v-model="form.apply_depart_name"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人">
              <el-input class="disabled" placeholder="申请人" v-model="form.apply_person_name"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业证编号" prop="job_code">
              <el-input v-model="form.job_code" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开始时间" prop="start_time">
              <el-date-picker v-model="form.start_time" type="datetime" placeholder="计划开始时间"
                              format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束时间" prop="end_time">
              <el-date-picker v-model="form.end_time" type="datetime" placeholder="计划结束时间"
                              format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业级别" prop="job_level">
              <el-select v-model="form.job_level" placeholder="请选择作业级别" disabled>
                <el-option v-for="(value,index) in jobLevelOptions" :key="index" :label="value" :value="value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业区域" prop="safe_area_id">
              <el-select v-model="form.safe_area_id" placeholder="请选择高处安全区域" :disabled="isDisabled" filterable>
                <el-option v-for="item in safeAreaOptions" :key="item.id" :label="item.name" :value="item.id"
                           @click.native="changeSafeArea(item)"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业地点" prop="place">
              <el-input v-model="form.place" placeholder="高处安全详细地点" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="作业内容" prop="job_content">
              <el-input v-model="form.job_content" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="作业高度" prop="job_height">
              <el-input v-model="form.job_height" :disabled="isDisabled" @input="updateLevel">
                <template slot="append">米</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业类别" prop="job_category">
              <el-input v-model="form.job_category" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="其他作业" prop="other_job">
              <el-input v-model="form.other_job" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="安全教育人" prop="safe_education_persons">
              <el-input v-if="isDisabled" readonly="readonly" ref="level" v-model="form.safe_education_persons"
                        disabled/>
              <el-input v-else readonly="readonly" v-model="form.safe_education_persons"
                        @click.native="treeOpen=true,treeOpenType='safe_education_persons'"/>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="危害辨识" prop="harm_identification_list">
              <!-- <el-select v-model="form.harm_identification_list" placeholder="请选择危害辨识" multiple clearable :disabled="isDisabled"
                         @change="$forceUpdate()">
                <el-option v-for="(value,index) in harmOptions" :key="index" :label="value" :value="value"/>
              </el-select> -->
              <el-checkbox-group v-model="form.harm_identification_list" placeholder="请选择危害辨识" multiple clearable
                                 :disabled="isDisabled"
                                 @change="$forceUpdate()">
                <el-checkbox v-for="(value,index) in harmOptions" :key="index" :label="value"/>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="受教育人" prop="educ_comr_id">
              <!--              <el-input readonly="readonly" v-model="form.educ_com_name"-->
              <!--                        @click.native="treeOpen=true,treeOpenType='educ_com'"/>-->
              <select-tree v-model="form.educ_comr_id" placeholder="请选择受教育人" :isDisabled="isDisabled"
                           :selectForm="educComrForm" :data="treeData" :otherForm="['flag']"
                           :validate="{flag:'U'}"
                           :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                           :isFilterable="true" @change="getEducComrForm"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="监护人" prop="guardian_person_name">
              <el-input v-if="isDisabled" readonly="readonly" ref="level" v-model="form.guardian_person_name" disabled/>
              <el-input v-else readonly="readonly" v-model="form.guardian_person_name"
                        @click.native="treeOpen=true,treeOpenType='guardian_person'"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="负责人" prop="job_manager_name">
              <el-input v-if="isDisabled" readonly="readonly" ref="level" v-model="form.job_manager_name" disabled/>
              <el-input v-else readonly="readonly" v-model="form.job_manager_name"
                        @click.native="treeOpen=true,treeOpenType='job_manager'"/>
            </el-form-item>
          </el-col>
        </el-col>
        <el-col :span="24">
          <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="作业人员" name="personnel">
              <operator :data="data" :isEdit="isEdit" type="high"/>
            </el-tab-pane>
            <el-tab-pane label="附件" name="file">
              <fileList :data="data" type="high"/>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="treeOpen">
      <personnel-selection v-if="treeOpen" :userDialogVisible="treeOpen" @close-dialog="treeOpen = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
    </div>
  </div>
</template>

<script>
  import {getConstructorTree} from '@/api/security/si/WorkTicketApi'
  import {req} from "@/api/security/si/common";
  import {getSafetyArea} from "@/api/security/si/WorkTicketApi";
  import {mapGetters} from "vuex";
  import operator from "./common/operator";
  import fileList from "./common/fileList";

  export default {
    name: "heightInfoApplication1",
    props: ['data', 'type'],
    components: {operator, fileList},
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
        isEdit: true,
        isCopy: false,
        form: {
          status: 0,
          job_code: '',
          educ_comr_id: '',
          educ_com_name: '',
          safe_education_persons: '',
          guardian_person_name: '',
          job_manager_name: '',
          harm_identification_list: [],
        },
        rules: {
          apply_depart_id: [{required: true, message: '请选择申请单位', trigger: 'blur'},],
          apply_person_id: [{required: true, message: '请选择申请人', trigger: 'blur'},],
          start_time: [{required: true, message: '请选择作业开始时间', trigger: 'blur'},
            {validator: validateStartTime, trigger: 'blur'}],
          end_time: [{required: true, message: '请选择作业结束时间', trigger: 'blur'},
            {validator: validateEndTime, trigger: 'blur'}],
          job_level: [{required: true, message: '请输入选择级别', trigger: 'blur'},],
          job_content: [{required: true, message: '请输入作业内容', trigger: 'blur'},],
          safe_area_id: [{required: true, message: '请输入选择区域', trigger: 'blur'},],
          place: [{required: true, message: '请输入作业地点', trigger: 'blur'},],
          job_height: [{required: true, message: '请输入作业高度', trigger: 'blur'},
            {type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value)}],
          other_job: [{required: true, message: '请输入其他作业', trigger: 'blur'},],
          job_category: [{required: true, message: '请输入作业类别', trigger: 'blur'},],
          harm_identification_list: [{required: true, message: '请选择危害辨识', trigger: 'blur'},],

          safe_education_persons: [{required: true, message: '请输入安全教育人'}],
          educ_comr_id: [{required: true, message: '请选择受教育人', trigger: 'blur'}],
          guardian_person_name: [{required: true, message: '请选择监护人'}],
          job_manager_name: [{required: true, message: '请选择负责人',}],
        },
        //下拉列表
        jobLevelOptions: ['一级', '二级', '三级', '特级'],
        harmOptions: ['高处坠落', '物体打击', '高空坠物', '起重伤害', '火灾', '中毒', '其他'],
        safeAreaOptions: [],

        treeData: [], treeOpen: false, treeOpenType: '',
        educComrForm: {value: '', label: ''},

        isDisabled: false
      }
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      if (this.type === 'edit') {
        this.isDisabled = this.data.status > 0;
      }

      getSafetyArea().then(res => {
        this.safeAreaOptions = res.data.data;
      }, error => {
        window.console.log(error);
      });
      getConstructorTree().then(res => {
        this.updateConstructorTree(res.data.data, {id: ""}, (res) => {
          this.treeData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
    },
    mounted() {
      if (this.data.copyId) {
        this.isCopy = true;
        this.form = {...this.data};
        this.form.apply_person_id = this.$store.getters.userInfo.userId;
        this.form.apply_person_name = this.$store.getters.userInfo.nickName;
        this.form.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
        this.form.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
        this.form.start_time = '';
        this.form.end_time = '';
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
        req('get', 'high/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      } else if (this.data.id) {
        this.isEdit = this.data.status === 0;
        this.form = {...this.data};
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
      } else {
        this.form.apply_person_id = this.$store.getters.userInfo.userId;
        this.form.apply_person_name = this.$store.getters.userInfo.nickName;
        this.form.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
        this.form.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
        req('get', 'high/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      }
    },
    methods: {
      //————————————功能方法————————————//
      saveMethod() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$emit('setSubmitLoading', true);
            if (Number(this.form.job_height) > 1) {
              if (this.validateApproval()) {
                let file_list = this.data.file_list;
                if (file_list === undefined) {
                  file_list = [];
                }
                let person_list = this.data.person_list;
                if (person_list === undefined) {
                  person_list = [];
                }
                if (!this.form.id) {
                  let data = {
                    ...this.form,
                    harm_identification: this.form.harm_identification_list.join('|'),
                    person_list: person_list,
                    file_list: file_list,
                    approval_list: this.data.approval_list,
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
                    this.$emit('setSubmitLoading', false);
                    this.$emit('cancel');
                  }, error => {
                    this.$emit('setSubmitLoading', false);
                    window.console.log(error);
                  });
                } else {
                  let data = {
                    ...this.form,
                    harm_identification: this.form.harm_identification_list.join('|'),
                    person_list: person_list,
                    file_list: file_list,
                    approval_list: this.data.approval_list,
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
                    this.$emit('setSubmitLoading', false);
                    this.$emit('cancel');
                  }, error => {
                    this.$emit('setSubmitLoading', false);
                    window.console.log(error);
                  });
                }
              } else {
                this.$message.error('请先设置审批人！');
                this.$emit('setSubmitLoading', false);
              }
            } else {
              this.$message({type: "error", message: "作业高度不能低于2米！"});
              this.$emit('setSubmitLoading', false);
            }
          }
        });
      },
      handleNodeClick(data) {
        if (this.treeOpenType === 'safe_education_persons') {
          this.form.safe_education_persons_id = data.id;
          this.form.safe_education_persons = data.full_name;
        } else if (this.treeOpenType === 'educ_com') {
          this.form.educ_comr_id = data.id;
          this.form.educ_com_name = data.full_name;
        } else if (this.treeOpenType === 'guardian_person') {
          this.form.guardian_person_id = data.id;
          this.form.guardian_person_name = data.full_name;
        } else if (this.treeOpenType === 'job_manager') {
          this.form.job_manager_id = data.id;
          this.form.job_manager_name = data.full_name;
          this.form.job_manager_no = data.job_number;
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
      getEducComrForm(obj) {
        this.form.educ_comr_id = obj.value;
        this.form.educ_com_name = obj.label;
      },
      updateConstructorTree: function (data, pObj, callback) {
        let that = this;
        let list = [];
        data.forEach(function (obj) {
          if (obj.pid === pObj.id) {
            that.updateConstructorTree(data, obj, (res) => {
              list.push({
                id: obj.id.slice(2),
                label: obj.label,
                flag: obj.id.slice(0, 1) === 'P' ? 'U' : obj.id.slice(0, 1),
                children: [...res]
              });
            });
          }
        });
        callback(list);
      },
      updateLevel(newValue) {
        if (!isNaN(newValue)) {
          if (newValue < 5) {
            this.$set(this.form, 'job_level', '一级')
          } else if (newValue >= 5 && newValue < 15) {
            this.$set(this.form, 'job_level', '二级')
          } else if (newValue >= 15 && newValue < 30) {
            this.$set(this.form, 'job_level', '三级')
          } else {
            this.$set(this.form, 'job_level', '特级')
          }
        } else {
          this.$message({type: "error", message: "请输入整数！"});
        }
      },
      approvalOver() {
        this.isDisabled = true;
      }

    },
    // 侦听器
    watch: {
      'form.job_level'(newValue) {
        if (this.form.status === 0) {
          this.$emit('setLevel', newValue);
        }
      },
      'form.educ_comr_id'(newValue) {
        this.educComrForm.value = newValue;
      },
      'form.job_height'(newValue) {

      },
    }
  }
</script>

<style scoped>
  .disabled {
    pointer-events: none
  }

  .el-col {
    margin-bottom: 0
  }

  .el-col .el-form-item {
    margin-bottom: 20px
  }
</style>
<style>
  .el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
