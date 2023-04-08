<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="right" label-width="107px">
      <el-row :gutter="10">
        <el-col :span="24" style="padding: 0px 40px ">
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
              <el-date-picker v-model="form.start_time" type="datetime" placeholder="断路开始时间"
                              format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束时间" prop="end_time">
              <el-date-picker v-model="form.end_time" type="datetime" placeholder="断路结束时间"
                              format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" style="height:62px"></el-col>
          <el-col :span="8">
            <el-form-item label="作业区域" prop="safe_area_id">
              <el-select v-model="form.safe_area_id" placeholder="请选择断路安全区域" :disabled="isDisabled" filterable>
                <el-option v-for="item in safeAreaOptions" :key="item.id" :label="item.name" :value="item.id"
                           @click.native="changeSafeArea(item)"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="作业地点" prop="place">
              <el-input v-model="form.place" placeholder="断路安全详细地点" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="作业内容" prop="job_content">
              <el-input v-model="form.job_content" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="监护人" prop="guardian_person_name">
              <el-input v-if="isDisabled" readonly="readonly" ref="level" v-model="form.guardian_person_name" disabled/>
              <el-input v-else readonly="readonly" v-model="form.guardian_person_name"
                        @click.native="treeOpen=true,treeOpenType='guardian_person_name'"/>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="涉及单位" prop="other_units">
              <el-input v-model="form.other_units" placeholder="涉及相关单位(部门)" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>


          <el-col :span="24">
            <el-form-item label="断路原因" prop="cause">
              <el-input v-model="form.cause" :disabled="isDisabled"/>
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
              <!--              <el-input v-model="form.harm_identification" :disabled="isDisabled"/>-->
              <el-checkbox-group v-model="form.harm_identification_list" placeholder="请选择危害辨识" multiple clearable
                                 :disabled="isDisabled"
                                 @change="$forceUpdate()">
                <el-checkbox v-for="(value,index) in harmOptions" :key="index" :label="value" :value="value"/>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="断路示意图" prop="url">
              <el-upload class="upload-demo" :action="action" :show-file-list="false"
                         :on-success="uploadSuccess"
                         :headers="headers"
                         :on-error="uploadError"
                         accept="image/png,image/gif,image/jpg,image/jpeg">
                <el-button size="small" type="primary" v-if="!isDisabled">点击上传</el-button>
              </el-upload>
              <img v-if="form.url!==''" width="100%" :src="imgUrl" alt="" @click="dialogVisibleImg=true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业单位" prop="job_unit_id">
              <select-tree v-model="form.job_unit_id"
                           placeholder="请选择作业单位"
                           :isDisabled="isDisabled"
                           :selectForm="deptForm"
                           :data="deptData"
                           :otherForm="['flag']"
                           :isSelectNode="true"
                           :isClearable="true"
                           :isAccordion="true"
                           :isFilterable="true"
                           :isNodeChildrenNull="true"
                           @change="getDeptForm"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业负责人" prop="job_responsible_person_name">
              <el-input v-if="isDisabled" readonly="readonly" ref="level" v-model="form.job_responsible_person_name"
                        disabled/>
              <el-input v-else readonly="readonly" v-model="form.job_responsible_person_name"
                        @click.native="treeOpen=true,treeOpenType='job_responsible_person_name'"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="断路相关说明" prop="content">
              <el-input v-model="form.content" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>

        </el-col>
        <el-col :span="24">
          <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="作业人员" name="personnel">
              <operator :data="data" :isEdit="isEdit" type="cutroad"/>
            </el-tab-pane>
            <el-tab-pane label="附件" name="file">
              <fileList :data="data" type="cutroad" file=""/>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="treeOpen">
      <personnel-selection v-if="treeOpen" :userDialogVisible="treeOpen" @close-dialog="treeOpen = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
    </div>
    <el-dialog :visible.sync="dialogVisibleImg" :modal="false" custom-class="imgBigCustomDialogStyle">
      <img width="100%" :src="imgUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
  import {getHumanTree} from "@/api/human/hm/humanResourceTree";
  import {req} from "@/api/security/si/common";
  import {getSafetyArea} from "@/api/security/si/WorkTicketApi";
  import {mapGetters} from "vuex";
  import operator from "./common/operator";
  import fileList from "./common/fileList";
  import {getToken} from "@/utils/auth";

  export default {
    name: "roadBreakingInfoApplication",
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
        form: {
          job_code: '',
          safe_education_persons: '',
          guardian_person_name: '',
          job_responsible_person_name: '',
          url: '',
          harm_identification_list: [],
        },
        rules: {
          apply_depart_id: [{required: true, message: '请选择申请单位', trigger: 'blur'},],
          apply_person_id: [{required: true, message: '请选择申请人', trigger: 'blur'},],
          start_time: [{required: true, message: '请选择断路开始时间', trigger: 'blur'},
            {validator: validateStartTime, trigger: 'blur'}],
          end_time: [{required: true, message: '请选择断路结束时间', trigger: 'blur'},
            {validator: validateEndTime, trigger: 'blur'}],
          safe_area_id: [{required: true, message: '请输入选择区域', trigger: 'blur'},],
          place: [{required: true, message: '请输入断路安全详细地点', trigger: 'blur'},],
          job_content: [{required: true, message: '请输入作业内容', trigger: 'blur'},],
          guardian_person_name: [{required: true, message: '请选择监护人'}],
          other_units: [{required: true, message: '请输入涉及相关单位(部门)', trigger: 'blur'},],
          cause: [{required: true, message: '请输入断路原因', trigger: 'blur'},],
          harm_identification_list: [{required: true, message: '请选择危害辨识', trigger: 'blur'},],
          safe_education_persons: [{required: true, message: '请输入安全教育人'}],
          job_unit_id: [{required: true, message: '请选择作业单位', trigger: 'blur'},],
          job_responsible_person_name: [{required: true, message: '请选择作业负责人'}],

          url: [{required: true, message: '断路示意图', trigger: 'blur'},],
          content: [{required: true, message: '请输入断路相关说明', trigger: 'blur'},],

        },
        //下拉列表
        jobLevelOptions: ['一级', '二级', '三级'],
        harmOptions: ['车辆伤害', '物体打击', '机械伤害', '其他伤害'],
        safeAreaOptions: [],

        treeData: [], treeOpen: false, treeOpenType: '',
        deptData: [],
        deptForm: {value: '', label: ''},
        isDisabled: false,
        action: process.env.VUE_APP_BASE_API + '/file/file/upload',
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        imgUrl: '',
        dialogVisibleImg: false,
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
      getHumanTree().then(res => {
        this.treeData = res.data.data;
        this.updateDeptTreeData(res.data, 1, (res) => {
          this.deptData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
    },
    mounted() {

      if (this.data.copyId) {
        this.form = {...this.data};
        this.form.apply_person_id = this.$store.getters.userInfo.userId;
        this.form.apply_person_name = this.$store.getters.userInfo.nickName;
        this.form.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
        this.form.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
        this.form.start_time = '';
        this.form.end_time = '';
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
        this.deptForm.value =  this.form.job_unit_id;
        this.deptForm.label =  this.form.job_unit_name;
        req('get', 'cutroad/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      } else if (this.data.id) {
        this.isEdit = this.data.status === 0;
        this.form = {...this.data};
        this.form.harm_identification_list = [...this.form.harm_identification.split('|')];
        this.deptForm.value =  this.form.job_unit_id;
        this.deptForm.label =  this.form.job_unit_name;
      } else {
        this.form.apply_person_id = this.$store.getters.userInfo.userId;
        this.form.apply_person_name = this.$store.getters.userInfo.nickName;
        this.form.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
        this.form.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
        req('get', 'cutroad/code', {}).then(res => {
          this.form.job_code = res.data.data.job_code;
        }, error => {
          window.console.log(error);
        });
      }
      if (this.form.url !== '') {
        this.imgUrl = this.GLOBAL.serveAddress + this.form.url;
      }
    },
    methods: {
      //————————————功能方法————————————//
      saveMethod() {
        // console.log(this.form)
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$emit('setSubmitLoading', true);
            if (this.validateApproval()) {
              let file_list = [];
              if (this.data.file_list !== undefined) {
                file_list = this.data.file_list.map((item) => {
                  return item.appendix_id
                });
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
                  upload_file_list: JSON.stringify(file_list),
                  approval_list: this.data.approval_list,
                  create_user_id: this.$store.getters.userInfo.userId,
                  create_user_name: this.$store.getters.userInfo.nickName,
                };
                req('post', 'cutroad/insert', data).then(res => {
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
                  upload_file_list: JSON.stringify(file_list),
                  approval_list: this.data.approval_list,
                  modify_user_id: this.$store.getters.userInfo.userId,
                  modify_user_name: this.$store.getters.userInfo.nickName,
                };
                req('post', 'cutroad/update', data).then(res => {
                  console.log(res,363);
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
          }
        });
      },


      handleNodeClick(data) {
        if (this.treeOpenType === 'safe_education_persons') {
          this.form.safe_education_persons_id = data.id;
          this.form.safe_education_persons = data.full_name;
        } else if (this.treeOpenType === 'guardian_person_name') {
          this.form.guardian_person_id = data.id;
          this.form.guardian_person_name = data.full_name;
        } else if (this.treeOpenType === 'job_responsible_person_name') {
          this.form.job_responsible_person_id = data.id;
          this.form.job_responsible_person_name = data.full_name;
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
        });
        callback(list);
      },
      getDeptForm(obj) {
        this.form.job_unit_id = obj.value;
        this.form.job_unit_name = obj.label;
      },
      //上传成功
      uploadSuccess(info) {
        this.form.url = info.data.relativePath;
        this.imgUrl = this.GLOBAL.serveAddress + info.data.relativePath;
      },
      //上传失败
      uploadError(error) {
        this.$message.error('上传失败');
      },
      approvalOver() {
        this.isDisabled = true;
      }
    },
  }
</script>

<style scoped>
  .disabled {
    pointer-events: none
  }

  .el-col {
    margin-bottom: 0
  }

  ::v-deep .imgBigCustomDialogStyle .el-dialog__header {
    padding: 0;
  }

  ::v-deep .imgBigCustomDialogStyle {
    -webkit-box-shadow: unset;
  }
</style>
<style>
  .el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
