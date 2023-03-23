<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="right" label-width="100px">
      <el-row :gutter="10">
        <el-col :span="24" style="padding: 10px 40px 0px 40px">
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
          <!--          <el-col :span="8" style="height:62px"></el-col>-->
          <el-col :span="8">
            <el-form-item label="作业地点" prop="place">
              <el-input v-model="form.place" placeholder="临时用电安全详细地点" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业区域" prop="safe_area_id">
              <el-select v-model="form.safe_area_id" placeholder="请选择临时用电安全区域" :disabled="isDisabled" filterable>
                <el-option v-for="item in safeAreaOptions" :key="item.id" :label="item.name" :value="item.id"
                           @click.native="changeSafeArea(item)"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="作业名称" prop="job_name">
              <el-input v-model="form.job_name" placeholder="" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电源接入点" prop="power_access_point">
              <el-input v-model="form.power_access_point" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工作电压" prop="work_voltage">
              <el-input v-model="form.work_voltage" :disabled="isDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备及功率" prop="electrical_equipment">
              <el-input v-model="form.electrical_equipment" :disabled="isDisabled"/>
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
                <el-checkbox v-for="(value,index) in harmOptions" :key="index" :label="value"/>
              </el-checkbox-group>
            </el-form-item>
          </el-col>

        </el-col>
        <el-col :span="24">
          <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="作业人员" name="personnel">
              <operator :data="data" :isEdit="isEdit" type="temporaryelectricity"/>
            </el-tab-pane>
            <el-tab-pane label="附件" name="file">
              <fileList :data="data" type="temporaryelectricity" file=""/>
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
  import {getHumanTree} from "@/api/human/hm/humanResourceTree";
  import {req} from "@/api/security/si/common";
  import {getSafetyArea} from "@/api/security/si/WorkTicketApi";
  import {mapGetters} from "vuex";
  import operator from "./common/operator";
  import fileList from "./common/fileList";

  export default {
    name: "temporaryElectricalInfoApplication",
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
          harm_identification_list: [],
        },
        rules: {
          apply_depart_id: [{required: true, message: '请选择申请单位', trigger: 'blur'},],
          apply_person_id: [{required: true, message: '请选择申请人', trigger: 'blur'},],
          start_time: [{required: true, message: '请选择作业开始时间', trigger: 'blur'},
            {validator: validateStartTime, trigger: 'blur'}],
          end_time: [{required: true, message: '请选择作业结束时间', trigger: 'blur'},
            {validator: validateEndTime, trigger: 'blur'}],
          safe_area_id: [{required: true, message: '请输入选择区域', trigger: 'blur'},],
          place: [{required: true, message: '请输入临时用电安全详细地点', trigger: 'blur'},],
          job_name: [{required: true, message: '请输入作业名称', trigger: 'blur'},],
          power_access_point: [{required: true, message: '请输入电源接入点', trigger: 'blur'},],
          work_voltage: [{required: true, message: '请输入工作电压', trigger: 'blur'},],
          electrical_equipment: [{required: true, message: '请输入设备及功率', trigger: 'blur'},],
          harm_identification_list: [{required: true, message: '请选择危害辨识', trigger: 'blur'},],
          safe_education_persons: [{required: true, message: '请输入安全教育人'}],
        },
        //下拉列表
        jobLevelOptions: ['一级', '二级', '三级'],
        harmOptions: ['触电', '漏电', '火灾', '灼伤', '机械伤害', '其他'],
        safeAreaOptions: [],
        treeData: [], treeOpen: false, treeOpenType: '',
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
      getHumanTree().then(res => {
        this.treeData = res.data.data;
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
        req('get', 'temporaryelectricity/code', {}).then(res => {
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
        req('get', 'temporaryelectricity/code', {}).then(res => {
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
                  person_list: person_list,
                  upload_file_list: JSON.stringify(file_list),
                  approval_list: this.data.approval_list,
                  harm_identification: this.form.harm_identification_list.join('|'),
                  create_user_id: this.$store.getters.userInfo.userId,
                  create_user_name: this.$store.getters.userInfo.nickName,
                };
                req('post', 'temporaryelectricity/insert', data).then(res => {
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
                  person_list: person_list,
                  upload_file_list: JSON.stringify(file_list),
                  approval_list: this.data.approval_list,
                  harm_identification: this.form.harm_identification_list.join('|'),
                  modify_user_id: this.$store.getters.userInfo.userId,
                  modify_user_name: this.$store.getters.userInfo.nickName,
                };
                req('post', 'temporaryelectricity/update', data).then(res => {
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
</style>
<style>
  .el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
