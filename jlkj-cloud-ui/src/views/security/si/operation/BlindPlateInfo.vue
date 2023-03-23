<template>
  <div>
    <el-form :model="myForm"
             status-icon
             :rules="rules"
             ref="myForm"
             label-position="right"
             label-width="120px"
             style="padding: 0 40px"
             v-loading="event.tableLoading">
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="设备单位">
            <el-input class="disabled"
                      placeholder="设备单位"
                      v-model="myForm.apply_depart_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="申请人">
            <el-input class="disabled"
                      placeholder="申请人"
                      v-model="myForm.apply_person_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业票编号">
            <el-input class="disabled"
                      style="width: 100%"
                      placeholder="作业票编号"
                      v-model="myForm.job_code" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="管道名称"
                        prop="equipment_piping_name">
            <el-input :disabled="event.isDisenabled"
                      placeholder="管道名称"
                      style="width: 100%"
                      v-model="myForm.equipment_piping_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="开始时间"
                        prop="start_time">
            <el-date-picker :disabled="event.isDisenabled"
                            type="datetime"
                            style="width: 100%"
                            placeholder="作业时间"
                            v-model="myForm.start_time"
                            value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结束时间"
                        prop="end_time">
            <el-date-picker :disabled="event.isDisenabled"
                            type="datetime"
                            style="width: 100%"
                            placeholder="作业时间"
                            v-model="myForm.end_time"
                            value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="介质"
                        prop="medium">
            <el-input :disabled="event.isDisenabled"
                      placeholder="介质"
                      v-model="myForm.medium" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="温度"
                        prop="temperature">
            <el-input :disabled="event.isDisenabled"
                      placeholder="温度"
                      v-model="myForm.temperature"
                      @input="changeInput('temperature')">
              <template slot="append">℃</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="压力"
                        prop="pressure">
            <el-input :disabled="event.isDisenabled"
                      placeholder="压力"
                      v-model="myForm.pressure"
                      @input="changeInput('pressure')">
              <template slot="append">Kpa</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="盲板材质"
                        prop="blind_plate_material_quality">
            <el-input :disabled="event.isDisenabled"
                      placeholder="盲板材质"
                      v-model="myForm.blind_plate_material_quality" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="盲板规格"
                        prop="blind_plate_specifications">
            <el-input :disabled="event.isDisenabled"
                      placeholder="盲板规格"
                      v-model="myForm.blind_plate_specifications" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="盲板编号"
                        prop="blind_plate_code">
            <el-input :disabled="event.isDisenabled"
                      placeholder="盲板编号"
                      v-model="myForm.blind_plate_code" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="生产单位指挥人"
                        prop="production_unit_job_director_person_name">
            <el-input v-model="myForm.production_unit_job_director_person_name"
                      placeholder="生产单位指挥人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonAndDepartClick('production_unit_job_director_person_name')"
                      :disabled="event.isDisenabled" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item v-if="!event.isDisenabled"
                        label="作业单位"
                        prop="job_unit_name">
            <select-tree style="width: 100%"
                         v-model="myForm.job_unit_name"
                         placeholder="作业单位"
                         :select-form="treeSelectData.jobUnit"
                         :data="treeSelectData.constructionUnitData"
                         :otherForm="['flag']"
                         :isSelectNode="true"
                         :isClearable="true"
                         :isAccordion="true"
                         :isFilterable="true"
                         :isNodeChildrenNull="true"
                         @change="getJobUnit" />
          </el-form-item>

          <el-form-item v-else
                        label="作业单位"
                        prop="production_unit_job_director_person_name">
            <el-input :value="myForm.job_unit_name"
                      disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业负责人"
                        prop="job_unit_responsible_person_name">
            <el-input v-model="myForm.job_unit_responsible_person_name"
                      placeholder="作业负责人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonAndDepartClick('job_unit_responsible_person_name')"
                      :disabled="event.isDisenabled" />
          </el-form-item>

        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="安全教育人"
                        prop="safe_education_persons">
            <el-input v-model="myForm.safe_education_persons"
                      placeholder="安全教育人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonAndDepartClick('safe_education_persons')"
                      :disabled="event.isDisenabled" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="监护人"
                        prop="guardian_person_name">
            <el-input v-model="myForm.guardian_person_name"
                      placeholder="监护人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonAndDepartClick('guardian_person_name')"
                      :disabled="event.isDisenabled" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业类型"
                        prop="pumping_blocking_text">
            <el-radio-group :disabled="event.isDisenabled"
                            v-model="myForm.pumping_blocking">
              <el-radio-button :label="0">抽作业</el-radio-button>
              <el-radio-button :label="1">堵作业</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="安全区域"
                        prop="safe_area_name">
            <el-select :disabled="event.isDisenabled"
                       class="customSelectStyle"
                       :popper-append-to-body="false"
                       v-model="myForm.safe_area_name"
                       placeholder="安全区域"
                       filterable
                       style="width: 100%"
                       @change="selectSafeArea">
              <el-option v-for="item in safeAreaArray"
                         :key="item.id"
                         :label="item.name"
                         :value="{value:item.id, label:item.name}" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="详细地点"
                        prop="place">
            <el-input :disabled="event.isDisenabled"
                      placeholder="安全详细地点"
                      v-model="myForm.place" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-form-item label="危害识别"
                        prop="hazard_identification">
            <el-checkbox-group class="customSelectStyle" :disabled="event.isDisenabled"
                       v-model="myForm.hazard_identification">
              <el-checkbox v-for="item in dangerOptions"
                         :key="item.value"
                         :label="item.label"/>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-form-item label="其他作业"
                        prop="other_job">
            <el-input :disabled="event.isDisenabled"
                      placeholder="其他作业"
                      v-model="myForm.other_job" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-tabs v-model="activeName"
             type="card">

      <el-tab-pane label="作业人员"
                   name="first">
        <WorkerCompoent ref="workerComponent" :flag="flag"/>
      </el-tab-pane>

      <el-tab-pane label="盲板位置图附件"
                   name="second">
        <AppendixComponent ref="appendixComponent" :flag="flag"/>
      </el-tab-pane>
    </el-tabs>

    <personnel-selection v-if="dialogVisible"
                         :userDialogVisible="dialogVisible"
                         @close-dialog="dialogVisible = false"
                         :isMultiple="false"
                         :height="500"
                         @single-select="handleSingleSelect"></personnel-selection>

    <div class="el-dialog__footer"
         style="padding:10px 40px 40px 40px;">
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
<!--        //是否显示确认键, 如果是新增作业票或者资料建立阶段显示确认键  其他情况下隐藏确认键-->
        <el-button type="primary"
                   v-if="mShowConfirmBtn"
                   :id="mId"
                   @click="confirmEditorData('myForm')"
                   :loading="event.loading">确 定</el-button>
      </span>
    </div>
  </div>
</template>

<script>
import {
  getSafetyArea,
  blindPlatePluggingCode,
  insertBlindPlatePluggingTicket,
  hoistingInfo
} from '@/api/security/si/WorkTicketApi'
import { blindPlatePluggingInfo, blindPlatePluggingUpdate } from '@/api/security/si/BindPlatePluggingApi'
import AppendixComponent from "@/views/security/si/operation/AppendixComponent";
import moment from "moment";
import WorkerCompoent from "@/views/security/si/operation/WorkerComponent";
import { getHumanTree } from "@/api/human/hm/humanResourceTree";
export default {
  components: {
    WorkerCompoent,
    AppendixComponent
  },
  name: "BlindPlateInfo",
  props: ['dataBundle' ,"isShowComfirm"],
  data () {
    var start_time = (rule, value, callback) => {
      if (!value) {
        callback(new Error('开始时间不能为空'));
      } else {
        let startDate = new Date(value);
        let startTime = startDate.getTime();
        let endDate = new Date(this.myForm.end_time);
        let endTime = endDate.getTime();
        // console.log(startTime, endTime)
        if (startTime && startTime > endTime && endTime) {
          callback(new Error('开始时间不能大于结束时间'));
        } else if (startTime && startTime == endTime && endTime) {
          callback(new Error('开始时间不能等于结束时间'));
        } else {
          callback();
        }
      }
    };
    var end_time = (rule, value, callback) => {
      if (!value) {
        callback(new Error('结束时间不能为空'));
      } else {
        let startDate = new Date(this.myForm.start_time);
        let startTime = startDate.getTime();
        let endDate = new Date(value);
        let endTime = endDate.getTime();
        // console.log(startTime, endTime)
        if (startTime && startTime > endTime && endTime) {
          callback(new Error('开始时间不能大于结束时间'));
        } if (startTime && startTime == endTime && endTime) {
          callback(new Error('开始时间不能等于结束时间'));
        } else {
          callback();
        }
      }
    };
    return {
      flag:'MD',
      self: {
        id: this.$store.getters.userInfo.userId,
        name: this.$store.getters.userInfo.nickName,
        departName: this.$store.getters.userInfo.dept.deptName,
        departID: this.$store.getters.userInfo.dept.deptId,
      },
      myForm: {
        guardian_person_id_plugging: '',
        guardian_person_id_take_out: '',
        guardian_person_name_plugging: '',
        guardian_person_name_take_out: '',
        implementation_time_plugging: '',
        implementation_time_take_out: '',

        apply_depart_id: '',
        apply_depart_name: '',
        apply_person_id: '',
        apply_person_name: '',
        approval_list: '',
        blind_plate_code: '',
        blind_plate_material_quality: '',
        blind_plate_specifications: '',
        equipment_piping_name: '',
        hazard_identification: [],
        job_code: '',
        job_person_plugging: '',
        job_person_take_out: '',
        job_unit_id: '',
        job_unit_name: '',
        job_unit_responsible_person_id: '',
        job_unit_responsible_person_name: '',
        medium: '',
        other_job: '',
        place: '',
        positon_content: '',
        pressure: '',
        production_unit_job_director_person_name: '',
        safe_area_id: '',
        safe_area_name: '',
        safe_education_persons: '',
        temperature: '',
        upload_file_list: [],
        guardian_person_id: '',
        guardian_person_name: '',
        start_time: '',
        end_time: '',
        pumping_blocking: 0,
        pumping_blocking_text: '抽作业'
      },
      rules: {
        equipment_piping_name: [{ required: true, message: '管道名称不能为空', trigger: 'blur' }],
        start_time: [{ required: true, validator: start_time, trigger: 'change' }],
        end_time: [{ required: true, validator: end_time, trigger: 'change' }],
        medium: [{ required: true, message: '介质不能为空', trigger: 'blur' }],
        temperature: [{ required: true, message: '温度不能为空', trigger: 'blur' }],
        pressure: [{ required: true, message: '压力不能为空', trigger: 'blur' }],
        blind_plate_material_quality: [{ required: true, message: '盲板材质不能为空', trigger: 'blur' }],
        blind_plate_specifications: [{ required: true, message: '盲板规格不能为空', trigger: 'blur' }],
        blind_plate_code: [{ required: true, message: '盲板编号不能为空', trigger: 'blur' }],
        production_unit_job_director_person_name: [{ required: true, message: '生产单位指挥人不能为空', trigger: 'change' }],
        job_unit_name: [{ required: true, message: '作业单位不能为空', trigger: 'blur' }],
        job_unit_responsible_person_name: [{ required: true, message: '作业负责人不能为空', trigger: 'change' }],
        safe_education_persons: [{ required: true, message: '安全教育人不能为空', trigger: 'change' }],
        guardian_person_name: [{ required: true, message: '监护人不能为空', trigger: 'change' }],
        hazard_identification: [{ required: true, message: '危害因素辨识不能为空', trigger: 'blur' }],
        place: [{ required: true, message: '安全详细地点不能为空', trigger: 'blur' }],
        pumping_blocking_text: [{ required: true, message: '作业类型不能为空', trigger: 'blur' }],
        safe_area_name: [{ required: true, message: '安全区域不能为空', trigger: 'change' }],
      },
      appendixData: [],
      activeName: 'first',
      treeSelectData: {
        constructorTreeData: [],
        constructionUnitData: [],
        jobUnit: { value: '', label: '' },
      },
      dangerOptions: [
        { value: 1, label: '灼烫' },
        { value: 2, label: '中毒和窒息' },
        { value: 3, label: '其他爆炸' },
        { value: 4, label: '其他伤害' },
      ],
      safeAreaArray: [],
      pageStatus: 0,
      event: {
        isRegister: false,
        isDisenabled: false,
        loading: false,
        tableLoading: false
      },
      ticketType: 0,//0为抽票 1为堵票,
      dialogVisible: false,
      selectPersonType: '',
      currTickeID: '',
      currApprovalStatus: -1,
      isEditable: true,
      mId:'',
      mShowConfirmBtn:true,
    }
  },
  methods: {
    //限制只有两位小数的正负数
    changeInput (name) {
      // this.myForm[name] = this.myForm[name].replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
      this.myForm[name] = this.myForm[name].replace(/^\./g, ""); //验证第一个字符是数字而不是.
      this.myForm[name] = this.myForm[name].replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
      this.myForm[name] = this.myForm[name].replace(/\-{2,}/g, "-"); //只保留第一个-（负号） 清除多余的-（负号）
      this.myForm[name] = this.myForm[name].replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//只允许输入一个小数点
      this.myForm[name] = this.myForm[name].replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
      this.myForm[name] = this.myForm[name].replace(/[^\-?\d.]/g, '') //小数，正数，负数
    },
    confirmEditorData (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pageStatus === 0 && this.currApprovalStatus === -1) {
            this.$EventBus.$emit("get-ticket-approve-data", "blinding");
          }else if (this.pageStatus === 2){
            this.$EventBus.$emit("get-ticket-approve-data", "blinding");
          }else {
            this.updateBlindPlate();
          }
        } else {
          // console.log('表单验证不通过');
          return false;
        }
      });
    },
    cancel () {
      this.$emit('close');
    },
    //把pidip转成children对象的格式
    transData (a, idStr, pidStr, childrenStr) {
      var r = [], hash = {}, id = idStr, pid = pidStr, children = childrenStr, i = 0, j = 0, len = a.length;
      for (; i < len; i++) {
        hash[a[i][id]] = a[i];
      }
      for (; j < len; j++) {
        var aVal = a[j], hashVP = hash[aVal[pid]];
        if (aVal['id'].startsWith("U-") || aVal['id'].startsWith("P-")) {
          aVal['flag'] = 'user';
        } else {
          aVal['flag'] = 'depart';
        }
        if (hashVP) {
          !hashVP[children] && (hashVP[children] = []);
          hashVP[children].push(aVal);
          if (hashVP['id'].startsWith("U-") || aVal['id'].startsWith("P-")) {
            hashVP['flag'] = 'user';
          } else {
            hashVP['flag'] = 'depart';
          }
        } else {
          var data = aVal;
          delete data[pidStr];
          if (data['id'].startsWith("U-") || aVal['id'].startsWith("P-")) {
            data['flag'] = 'user';
          } else {
            data['flag'] = 'depart';
          }
          r.push(data);
        }
      }
      return r;
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
              list.push({ ...obj, children: children })
            } else {
              list.push({ ...obj })
            }
          }
        }
      });
      callback(list);
    },
    //获取施工单位树
    //获取部门树
    getConstructionUnitTree () {
      getHumanTree().then(res => {
        this.updateDeptTreeData(res.data, 1, (res) => {
          this.treeSelectData.constructionUnitData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
    },

    getJobUnit (obj) {
      this.myForm.job_unit_id = obj.value;
      this.myForm.job_unit_name = obj.label;
    },
    //获取安全区域
    getSafeAreaData () {
      getSafetyArea().then(response => {
        this.safeAreaArray = response.data.data;
      });
    },
    //根据id查找安全区域对应的名称
    getSafetyAreaNameByID (id) {
      let name = '';
      for (let i = 0; i < this.safeAreaArray.length; i++) {
        let item = this.safeAreaArray[i];
        if (item.id === id) {
          name = item.name;
          break;
        }
      }
      return name;
    },
    //获取作业证票号
    getBlindPlatePluggingCode () {
      this.event.tableLoading = true;
      blindPlatePluggingCode().then(response => {
        this.event.tableLoading = false;
        this.myForm.job_code = response.data.data.job_code;
        this.$EventBus.$emit('event-job-level-change', { level: '', jobCode: this.myForm.job_code });//通知审批组件获取审批模板
      }, error => {
        this.event.tableLoading = false;
        console.log(error);
      });
    },
    //对危害辨识的所有选择项进行拼接   使用|把每一项拼接起来
    jointHarmIdentification (array) {
      if (array === undefined || array.length === 0) {
        return "";
      } else {
        return array.join("|");
      }
    },
    //显示详情的时候需要把危害辨识字符串拆分成数值
    splitHarmIdentificationString (str) {
      if (str === undefined || str === null || str === "") {
        return [];
      } else {
        return str.split("|");
      }
    },
    //去掉人员或者闭门id的前缀
    deleteIDPrefix (preID) {
      if (preID === undefined || preID === '') {
        return "";
      } else {
        if (preID.startsWith("U-") || preID.startsWith("D-") || preID.startsWith("C-") || preID.startsWith("P-")) {
          return preID.substring(2);
        } else {
          return preID;
        }
      }
    },

    makeFileIDStr (fileArray) {
      let idArray = [];
      if (fileArray !== undefined) {
        for (let i = 0; i < fileArray.length; i++) {
          let id = fileArray[i]['appendix_id'];
          idArray.push(id);
        }
      }
      return JSON.stringify(idArray);
    },
    //保存作业票
    insertBlindPlate (approvalData) {
      let params = { ...this.myForm };
      params['pumping_blocking_text'] = this.myForm.pumping_blocking === '0' ? "抽作业" : "堵作业";
      params['hazard_identification'] = this.jointHarmIdentification(this.myForm.hazard_identification);
      params['upload_file_list'] = this.makeFileIDStr(this.$refs.appendixComponent.getAppendixList());
      params['approval_list'] = approvalData;
      params['job_unit_id'] = this.deleteIDPrefix(this.myForm.job_unit_id);
      params['person_list'] = this.$refs.workerComponent.getWorkerList();
      params['safe_education_persons_id'] = this.myForm.safe_education_persons_id === undefined ? '' : this.myForm.safe_education_persons_id;
      this.event.loading = true;
      insertBlindPlatePluggingTicket(params).then(response => {
        this.event.loading = false;
        this.$EventBus.$emit('event-refresh-blinding-work-ticket-list-data', "");
        if (response.data.msg === '盲板抽堵安全作业证保存成功') {
          this.currTickeID = response.data.data.id;
          //把新获取到的作业票ID通过eventBus 发出去，实际上就是发给“安全措施组件”和“作业票审批组件”
          this.$EventBus.$emit('get-ticket-id', { "ticketID": this.currTickeID, "jobCode": this.myForm.job_code });
          this.pageStatus = 1;
          this.currApprovalStatus = 0;
          this.$message({
            message: response.data.msg,
            type: 'success'
          });
          this.$emit('close');
        }
      }, error => {
        this.$message.error("作业票保存失败!");
        this.event.loading = false;
        // console.log(error);
      })
    },
    //更新作业p
    updateBlindPlate () {
      // console.log(this.myForm,'this.myForm')
      this.myForm['id'] = this.currTickeID;
      this.myForm['upload_file_list'] = this.makeFileIDStr(this.$refs.appendixComponent.getAppendixList());
      this.myForm['start_time'] = moment(this.myForm.start_time).format("YYYY-MM-DD HH:mm:ss");
      this.myForm['end_time'] = moment(this.myForm.end_time).format("YYYY-MM-DD HH:mm:ss");
      this.myForm['guardian_person_id_plugging'] = '';
      this.myForm['guardian_person_id_take_out'] = '';
      this.myForm['guardian_person_name_plugging'] = '';
      this.myForm['guardian_person_name_take_out'] = '';
      this.myForm['hazard_identification'] = this.jointHarmIdentification(this.myForm.hazard_identification);
      this.myForm['person_list'] = this.$refs.workerComponent.getWorkerList();
      this.event.loading = true;
      blindPlatePluggingUpdate(this.myForm).then(response => {
        this.event.loading = false;
        if (response.data.msg === '盲板抽堵安全作业证保存成功') {
          this.$EventBus.$emit('event-refresh-blinding-work-ticket-list-data', "");
          this.$message({
            message: '作业票修改成功！',
            type: 'success'
          });
          this.$emit('close');
        }
      }, error => {
        this.$message.error("作业票修改失败!");
        this.event.loading = false;
        // console.log(error);
      });
    },

    selectSafeArea (data) {
      let { value, label } = data;
      this.myForm.safe_area_name = label;
      this.myForm.safe_area_id = value;
    },
    //选择开始时间的监听
    pickStartTimeListener (timestamp) {
      if (this.myForm.implementation_time_plugging) {
        // if (this.myForm.end_time - timestamp > 86400000){
        //   this.$message.error("作业时间不得超过24小时");
        //   this.myForm.start_time = '';
        // }

        if (this.myForm.implementation_time_plugging - timestamp < 0) {
          this.$message.error("时间选择有误");
          this.myForm.implementation_time_take_out = '';
        }
      }
    },
    //选择结束时间的监听
    pickEndTimeListener (timestamp) {
      if (this.myForm.implementation_time_take_out) {
        // if (timestamp - this.myForm.start_time > 86400000){
        //   this.$message.error("作业时间不得超过24小时");
        //   this.myForm.end_time = '';
        // }

        if (timestamp - this.myForm.implementation_time_take_out < 0) {
          this.$message.error("时间选择有误");
          this.myForm.implementation_time_plugging = '';
        }
      }
    },
    //将时间戳改成日期时间格式
    dateTimeFormat (timestamp) {
      return moment(timestamp).format("yyyy-MM-DD HH:mm:ss");
    },
    //获取作业票详情
    getBlindPlatePluggingInfo (id) {
      this.event.tableLoading = true;
      blindPlatePluggingInfo(id).then(response => {
        this.event.tableLoading = false;
        this.event.isDisenabled = true;
        this.myForm = { ...response.data.data };
        this.myForm['hazard_identification'] = this.splitHarmIdentificationString(this.myForm['hazard_identification']);
        this.myForm['pumping_blocking_text'] = this.myForm['pumping_blocking'] === 0 ? '抽作业' : '堵作业';
        //判断 如果要是审批状态为0 是可以修改这个作业票的所有内容，否者不可以对作业票进行修改除了附件之外
        this.currApprovalStatus = this.myForm.status;
        if (this.currApprovalStatus === 0 && this.pageStatus === 1) {
          this.event.isDisenabled = false;
          this.isEditable = true;
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        } else if (this.pageStatus === 2){
          this.event.tableLoading = true;
          blindPlatePluggingCode().then(response => {
            this.event.tableLoading = false;
            this.myForm.job_code = response.data.data.job_code;
            this.event.isDisenabled = false;
            this.isEditable = true;
            this.myForm['apply_person_id'] = this.self.id;
            this.myForm['apply_person_name'] = this.self.name;
            this.myForm['apply_depart_name'] = this.self.departName;
            this.myForm['apply_depart_id'] = this.self.departID;
            this.myForm['start_time'] = '';
            this.myForm['end_time'] = '' ;
            this.$refs.appendixComponent.setAppendixByTicketID(id);
            this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
            this.$EventBus.$emit('event-approval-set-way', 0);
            this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
            this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
          }, error => {
            this.event.tableLoading = false;
            // console.log(error);
          });
        }else if (this.currApprovalStatus === 1 || this.currApprovalStatus === 2 && this.pageStatus === 1){
          this.event.isDisenabled = true;
          this.isEditable = true;
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        } else {
          this.event.isDisenabled = true;
          this.isEditable = false;
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        }
      }, error => {
        this.event.tableLoading = false;
        window.console.log(error);
      });
    },
    //选人和部门的input点击事件
    selectPersonAndDepartClick (type) {
      this.dialogVisible = true;
      this.selectPersonType = type;
    },
    //选人的时候返回数据，分类进行赋值
    handleSingleSelect (data) {
      switch (this.selectPersonType) {
        case 'production_unit_job_director_person_name':
          this.myForm.production_unit_job_director_person_name = data.full_name;
          break
        case 'job_unit_responsible_person_name':
          // console.log(data);
          this.myForm.job_unit_responsible_person_name = data.full_name;
          this.myForm.job_unit_responsible_person_id = data.id;
          break
        case 'safe_education_persons':
          this.myForm.safe_education_persons = data.full_name;
          this.myForm.safe_education_persons_id = data.id;
          break
        case 'guardian_person_name':
          this.myForm.guardian_person_name = data.full_name;
          this.myForm.guardian_person_id = data.id;
          break
      }
    },
  },
  created () {
    this.$EventBus.$on('ticket-approve-data-blinding', (data) => {
      let isApprovalComplete = true;
      for (let i = 0; i < data.length; i++) {
        let approvalPersonName = data[i].approval_person_name;
        let approvalPersonID = data[i].approval_person_id;
        if (!approvalPersonName || !approvalPersonID) {
          isApprovalComplete = false;
          break
        }
      }

      if (!isApprovalComplete) {
        this.$message.error("审批人设置不完整");
        return;
      }

      this.insertBlindPlate(data);
    });
    /**
     * 接收审批后的消息,
     * 当接收到审批组件发过来的消息的时候, 把当前作业票状态刷新, 改成不可修改
     */
    this.$EventBus.$on("event-approval-component-approval-message-md" , ()=>{
      blindPlatePluggingInfo(this.currTickeID).then(response => {
        this.currApprovalStatus = response.data.data.status;
        if (this.currApprovalStatus !== 0){
          this.event.isDisenabled = true;
          this.mShowConfirmBtn = false;
          this.mId = new Date();
        }
      });
    });
  },
  mounted () {
    this.pageStatus = this.dataBundle.type;
    if (this.pageStatus === 0) {
      this.event.isDisenabled = false;
      this.myForm.apply_person_id = this.$store.getters.userInfo.userId;
      this.myForm.apply_person_name = this.$store.getters.userInfo.nickName;
      // this.myForm.apply_depart_name = this.$store.getters.userInfo.departmentName;
      // this.myForm.apply_depart_id = this.$store.getters.userInfo.alternateField14;
      this.myForm.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
      this.myForm.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
      // this.getConstructor();
      this.getConstructionUnitTree();
      this.getSafeAreaData();
      this.getBlindPlatePluggingCode();
      this.$EventBus.$emit('event-approval-set-way', 0);
    } else if (this.pageStatus === 2){
      this.currTickeID = this.dataBundle.id;
      this.getConstructionUnitTree();
      this.getSafeAreaData();
      this.getBlindPlatePluggingInfo(this.currTickeID);
    }else {
      // 当前为修改或者是审批状态，所有的作业票数据都是在上一页传进来得，不需要在进行额外获取，
      // 当前页面只有作业人和附件是可以修改的  其他的信息都是不可修改
      this.currTickeID = this.dataBundle.id;
      this.getConstructionUnitTree();
      this.getSafeAreaData();
      this.getBlindPlatePluggingInfo(this .currTickeID);
      // console.log("0000000000000000000");
      // console.log(this.dataBundle);
    }
    this.mShowConfirmBtn = this.isShowComfirm;
  },
  destroyed () {
    this.$EventBus.$off('ticket-approve-data-blinding');
    this.$EventBus.$off('event-approval-component-approval-message-md');
  },
  watch: {
    'myForm.job_unit_id' (newValue) {
      this.treeSelectData.jobUnit.value = newValue;
    },

    'myForm.job_unit_name' (newValue) {
      this.treeSelectData.jobUnit.label = newValue;
    },
  }
}
</script>

<style scoped>
/*时间选择框的样式*/
.el-date-editor.el-input {
  width: 200px;
}
.el-form-item {
  margin-bottom: 10px;
}
.disabled {
  pointer-events: none;
}
</style>
<style>
/*.el-radio-button__inner {*/
/*  white-space: nowrap;*/
/*  background: #FFF;*/
/*  border: 1px solid #DCDFE6;*/
/*  font-weight: 500;*/
/*  border-left: 0;*/
/*  color: #606266;*/
/*  -webkit-appearance: none;*/
/*  text-align: center;*/
/*  -webkit-box-sizing: border-box;*/
/*  box-sizing: border-box;*/
/*  outline: 0;*/
/*  margin: 0;*/
/*  position: relative;*/
/*  cursor: pointer;*/
/*  -webkit-transition: all .3s cubic-bezier(.645,.045,.355,1);*/
/*  transition: all .3s cubic-bezier(.645,.045,.355,1);*/
/*  padding: 12px 28px;*/
/*  font-size: 14px;*/
/*  border-radius: 0;*/
/*}*/
</style>
