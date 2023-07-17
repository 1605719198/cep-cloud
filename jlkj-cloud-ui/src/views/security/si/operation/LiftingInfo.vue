<template>
  <div>
    <el-form :model="myform"
             status-icon
             :rules="rules"
             ref="myform"
             class="demo-ruleForm"
             label-position="right"
             label-width="95px"
             style="padding: 10px 40px 0px 40px"
             v-loading="event.tableLoading">
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="申请单位">
            <el-input class="disabled"
                      placeholder="申请单位"
                      v-model="myform.apply_depart_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="申请人">
            <el-input class="disabled"
                      placeholder="申请人"
                      v-model="myform.apply_person_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业票编号">
            <el-input class="disabled"
                      placeholder="作业票编号"
                      v-model="myform.job_code" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="开始时间"
                        prop="start_time">
            <el-date-picker :disabled="event.isDisenabled"
                            style="width: 100%"
                            v-model="myform.start_time"
                            type="datetime"
                            placeholder="开始时间"
                            value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结束时间"
                        prop="end_time">
            <el-date-picker :disabled="event.isDisenabled"
                            style="width: 100%"
                            v-model="myform.end_time"
                            type="datetime"
                            placeholder="结束时间"
                            value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业票级别">
            <el-input class="disabled"
                      placeholder="作业票级别"
                      v-model="myform.job_level" />
            <!--            <el-select class="customSelectStyle" :popper-append-to-body="false" v-model="myform.job_level" placeholder="作业票级别" :disabled="event.isDisenabled" @change="jobLevelChangeListener">-->
            <!--              <el-option-->
            <!--                v-for="item in ticketLevelOptions"-->
            <!--                :key="item.label"-->
            <!--                :label="item.label"-->
            <!--                :value="item.label"/>-->
            <!--            </el-select>-->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col>
          <el-form-item label="作业内容"
                        prop="job_content">
            <el-input :disabled="event.isDisenabled"
                      placeholder="作业内容"
                      v-model="myform.job_content" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="作业区域"
                        prop="safe_area_name">
            <el-select class="customSelectStyle"
                       filterable
                       :popper-append-to-body="false"
                       :disabled="event.isDisenabled"
                       v-model="myform.safe_area_name"
                       placeholder="作业区域"
                       style="width: 100%"
                       @change="selectSafeArea">
              <el-option v-for="item in workAreaOptions"
                         :key="item.id"
                         :label="item.name"
                         :value="{value:item.id , label:item.name}" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="作业地点"
                        prop="place">
            <el-input :disabled="event.isDisenabled"
                      placeholder="作业地点"
                      v-model="myform.place" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="吊起重量"
                        prop="weight">
            <el-input :disabled="event.isDisenabled"
                      placeholder="吊起重量"
                      v-model="myform.weight"
                      @input="jobLevelInputBlurListener">
              <template slot="append">吨</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="吊起工具"
                        prop="hoisting_tools">
            <!--            <el-input :disabled="event.isDisenabled" placeholder="吊起工具" v-model="myform.hoisting_tools"/>-->
            <el-select class="customSelectStyle"
                       :popper-append-to-body="false"
                       v-model="myform.hoisting_tools"
                       placeholder="吊钟工具"
                       :disabled="event.isDisenabled"
                       style="width: 100%">
              <el-option v-for="item in hoistingTools"
                         :key="item.value"
                         :label="item.label"
                         :value="item.label" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="吊装名称"
                        prop="hoist_name">
            <el-input :disabled="event.isDisenabled"
                      placeholder="吊装名称"
                      v-model="myform.hoist_name" />
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="危害辨识"
                        prop="harm_identification">
            <el-checkbox-group class="customSelectStyle"
                       v-model="myform.harm_identification" :disabled="event.isDisenabled">
              <el-checkbox v-for="item in dangerOptions"
                         :key="item.value"
                         :label="item.label"/>
            </el-checkbox-group>
            <!-- <el-checkbox-group v-model="myform.harm_identification" >
              <el-checkbox label="机械伤害"></el-checkbox>
              <el-checkbox label="物体打击"></el-checkbox>
              <el-checkbox label="车辆伤害"></el-checkbox>
              <el-checkbox label="起重伤害"></el-checkbox>
              <el-checkbox label="其他"></el-checkbox>
            </el-checkbox-group> -->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="监护人"
                        prop="guardian_person_name">
            <el-input :disabled="event.isDisenabled"
                      v-model="myform.guardian_person_name"
                      placeholder="监护人"
                      :readonly="true"
                      @click.native="!event.isDisenabled ? selectPersonInputClick('guardian') : null">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业负责人"
                        prop="hoist_charge_name">
            <el-input :disabled="event.isDisenabled"
                      v-model="myform.hoist_charge_name"
                      placeholder="作业负责人"
                      :readonly="true"
                      @click.native="!event.isDisenabled ? selectPersonInputClick('charge') : null" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="安全教育人"
                        prop="safe_education_persons">
            <el-input :disabled="event.isDisenabled"
                      v-model="myform.safe_education_persons"
                      placeholder="安全教育人"
                      :readonly="true"
                      @click.native="!event.isDisenabled ? selectPersonInputClick('education') : null" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="接受交底人" prop="special_name">
          <el-input :disabled="event.isDisenabled" v-model="myform.special_name" placeholder="请输入接受交底人"/>
        </el-form-item>
      </el-row>
    </el-form>
    <el-tabs v-model="activeName"
             type="card">
      <el-tab-pane label="作业人员"
                   name="first">
        <WorkerComponent ref="workerComponent"
                         :ticketType="'lifting'" :flag="flag"/>
      </el-tab-pane>
      <el-tab-pane label="附件"
                   name="second">
        <AppendixComponent ref="appendixComponent" :flag="flag"/>
      </el-tab-pane>
    </el-tabs>
    <personnel-selection v-if="dialog.selectDialogVisibility"
                         :userDialogVisible="dialog.selectDialogVisibility"
                         @close-dialog="dialog.selectDialogVisibility = false"
                         :isMultiple="false"
                         :height="500"
                         @single-select="handleSingleSelect"></personnel-selection>
    <div class="el-dialog__footer"
         style="padding:10px 40px 40px 40px; ">
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="cancelClick">取 消</el-button>
        <el-button type="primary"
                   @click="confirmEditorData('myform')"
                   v-if="mShowConfirmBtn"
                   :id="mId"
                   :loading="event.buttonLoading">确 定</el-button>
      </span>
    </div>

  </div>
</template>

<script>
import LiftingAddWokerDialog from "@/views/security/si/operation/LiftingAddWokerDialog";
import WorkerComponent from "@/views/security/si/operation/WorkerComponent";
import AppendixComponent from "@/views/security/si/operation/AppendixComponent";
import {
  getHoistingWorkTicketCode,
  getSafetyArea, hoistingInfo,
  insertHoistingWorkTicket,
  updateHoistingWorkTicket
} from "@/api/security/si/WorkTicketApi"
import { getHumanTree } from "@/api/human/hm/humanResourceTree";
import moment from "moment";
export default {
  components: {
    LiftingAddWokerDialog,
    WorkerComponent,
    AppendixComponent
  },
  name: "LiftingInfo",
  props: ['dataBundle',"isShowComfirm"],
  data () {
    var start_time = (rule, value, callback) => {
      if (!value) {
        callback(new Error('开始时间不能为空'));
      } else {
        let startDate = new Date(value);
        let startTime = startDate.getTime();
        let endDate = new Date(this.myform.end_time);
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
    var end_time = (rule, value, callback) => {
      if (!value) {
        callback(new Error('结束时间不能为空'));
      } else {
        let startDate = new Date(this.myform.start_time);
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
    var checkValue = (rule, value, callback) => {
      const float = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/;
      if (!float.test(value)) {
        callback(new Error('只能为数字'));
      } else {
        callback();
      }
    }
    return {
      checkList: [],
      checked: true,
      flag:'DZ',
      pageStatus: 0, //页面状态 0为当前是新增  1为当前是修改审批
      activeName: 'first',
      ticketLevelOptions: [
        {
          value: '1',
          label: '一级'
        },
        {
          value: '2',
          label: '二级'
        },
        {
          value: '3',
          label: '三级'
        }
      ],
      workAreaOptions: [],
      rules: {
        start_time: [{ required: true, validator: start_time, trigger: 'change' },],
        end_time: [{ required: true, validator: end_time, trigger: 'change' },],
        job_content: [{ required: true, message: '作业内容不能为空', trigger: 'blur' }],
        safe_area_name: [{ required: true, message: '作业区域不能为空', trigger: 'change' }],
        place: [{ required: true, message: '作业地点不能为空', trigger: 'blur' }],
        weight: [{ required: true, message: '起吊重量不能为空', trigger: 'blur' }, { validator: checkValue, trigger: 'blur' }],
        hoisting_tools: [{ required: true, message: '起吊工具不能为空', trigger: 'change' }],
        safe_education_persons: [{ required: true, message: '安全教育人不能为空', trigger: 'change' }],
        harm_identification: [{ required: true, message: '危害辨识不能为空', trigger: 'blur' }],
        guardian_person_name: [{ required: true, message: '作业负责人不能为空', trigger: 'change' }],
        hoist_name: [{ required: true, message: '吊装名称不能为空', trigger: 'blur' }],
        hoist_charge_name: [{ required: true, message: '监护人不能为空', trigger: 'change' }],
        special_name:[{ required: true, message: '接受交底人不能为空', trigger: 'blur' }]
      },
      myform: {
        apply_depart_id: '',
        apply_depart_name: '',
        apply_person_id: '',
        apply_person_name: '',
        end_time: '',
        timeRange: '',
        guardian_person_id: '',
        guardian_person_name: '',
        harm_identification: [],
        hoist_name: '',
        hoisting_tools: '',
        id: '',
        is_timeout: '',
        job_code: '',
        job_content: '',
        job_level: '',
        place: '',
        safe_area_id: '',
        safe_area_name: '',
        safe_education_persons: '',
        safe_education_persons_id: '',
        start_time: '',
        status: '',
        status_text: '',
        weight: '',
        hoist_charge_name: '',
        hoist_charge_id: '',
        special_name:'',
      },

      personData: [],
      event: {
        isRegister: false,
        isDisenabled: false,
        buttonLoading: false,
        tableLoading: false
      },
      dangerOptions: [
        { value: 1, label: '机械伤害' },
        { value: 2, label: '物体打击' },
        { value: 3, label: '车辆伤害' },
        { value: 4, label: '起重伤害' },
        { value: 5, label: '其他' },
      ],
      dialog: {
        selectDialogVisibility: false,
        selectPersonType: ''
      },
      hoistingTools: [
        { value: '1', label: '天车' },
        { value: '2', label: '手拉葫芦' },
        { value: '3', label: '电动葫芦' },
        { value: '4', label: '汽车吊' },
        { value: '5', label: '塔吊' },
        { value: '6', label: '卷扬机' },
      ],
      currApprovalStatus: -1,
      currTicketID: '',
      isEditable: true,
      mId: "",
      mShowConfirmBtn:true
    }
  },
  methods: {
    //获取吊装作业的数据
    confirmEditorData (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pageStatus === 0 && this.currApprovalStatus === -1) {
            this.$EventBus.$emit("get-ticket-approve-data", "1");
          }else if (this.pageStatus === 2){
            this.$EventBus.$emit("get-ticket-approve-data", "1");
          }
          else {
            //this.updateHoistingWorkTicket();
            this.$EventBus.$emit("get-ticket-approve-data", "1");
          }
        } else {
          console.log('表单验证不通过');
          return false;
        }
      });
    },

    updateHoistingWorkTicket (data) {
      // let params = { ...this.myform };
      // params['id'] = this.currTicketID;
      // params['file_list'] = this.$refs.appendixComponent.getAppendixList();
      // params['harm_identification'] = this.jointHarmIdentification(this.myform.harm_identification);
      // params['person_list'] = this.$refs.workerComponent.getWorkerList();
      // params['safe_education_persons_id'] = this.dataBundle.safe_education_persons_id === undefined ? "" : this.dataBundle.safe_education_persons_id;

      let params = {};
      params['id'] = this.currTicketID;
      params['apply_depart_id'] = this.myform.apply_depart_id;
      params['apply_depart_name'] = this.myform.apply_depart_name;
      params['apply_person_id'] = this.myform.apply_person_id;
      params['apply_person_name'] = this.myform.apply_person_name;
      params['approval_list'] = data;
      params['end_time'] = this.myform.end_time;
      params['guardian_person_id'] = this.myform.guardian_person_id;
      params['guardian_person_name'] = this.myform.guardian_person_name;
      params['harm_identification'] = this.jointHarmIdentification(this.myform.harm_identification);
      params['hoist_name'] = this.myform.hoist_name;
      params['hoisting_tools'] = this.myform.hoisting_tools;
      params['job_code'] = this.myform.job_code;
      params['job_content'] = this.myform.job_content;
      params['job_level'] = this.myform.job_level;
      //对作业人员进行验证：要求是作业人员至少为两个人，其中还必须有一个人是指挥（不是这几个人的头，是指挥打掉作业的人）。
      let workerList = this.$refs.workerComponent.getWorkerList();
      let currWorkerCount = workerList.length;
      let currCommanderCount = 0;
      for (let i = 0; i < workerList.length; i++) {
        if (workerList[i]['person_type'] === 1) {
          currCommanderCount++;
        }
      }
      if (currWorkerCount < 2) {
        this.$message.error("作业人员不得少于2人");
        return
      }

      if (currCommanderCount === 0) {
        this.$message.error("至少需要一名指挥人员");
        return
      }
      params['person_list'] = workerList;
      params['place'] = this.myform.place;
      params['safe_area_id'] = this.myform.safe_area_id;
      params['safe_area_name'] = this.getSafetyAreaNameByID(this.myform.safe_area_id);
      params['safe_education_persons'] = this.myform.safe_education_persons;
      params['start_time'] = this.myform.start_time;
      params['weight'] = this.myform.weight;
      params['file_list'] = this.$refs.appendixComponent.getAppendixList();
      params['hoist_charge_name'] = this.myform.hoist_charge_name;
      params['hoist_charge_id'] = this.myform.hoist_charge_id;
      params['safe_education_persons_id'] = this.myform.safe_education_persons_id === undefined ? '' : this.myform.safe_education_persons_id;
      params['special_name'] = this.myform.special_name ;



      this.event.buttonLoading = true;
      updateHoistingWorkTicket(params).then(response => {
        debugger
        this.event.buttonLoading = false;
        if (response.msg === '吊装安全作业证保存成功') {
          this.$EventBus.$emit('event-refresh-lifting-work-ticket-list-data', "");
          this.$message({
            message: '作业票修改成功！',
            type: 'success'
          });
          this.$emit('close');
        }
      }, error => {
        this.event.buttonLoading = false;
        this.$message.error("作业票修改失败!");
        // console.log(error);
      });
    },

    handleConfirmToNet (data) {
      let params = {};
      params['apply_depart_id'] = this.myform.apply_depart_id;
      params['apply_depart_name'] = this.myform.apply_depart_name;
      params['apply_person_id'] = this.myform.apply_person_id;
      params['apply_person_name'] = this.myform.apply_person_name;
      params['approval_list'] = data;
      params['end_time'] = this.myform.end_time;
      params['guardian_person_id'] = this.myform.guardian_person_id;
      params['guardian_person_name'] = this.myform.guardian_person_name;
      params['harm_identification'] = this.jointHarmIdentification(this.myform.harm_identification);
      params['hoist_name'] = this.myform.hoist_name;
      params['hoisting_tools'] = this.myform.hoisting_tools;
      params['job_code'] = this.myform.job_code;
      params['job_content'] = this.myform.job_content;
      params['job_level'] = this.myform.job_level;
      //对作业人员进行验证：要求是作业人员至少为两个人，其中还必须有一个人是指挥（不是这几个人的头，是指挥打掉作业的人）。
      let workerList = this.$refs.workerComponent.getWorkerList();
      let currWorkerCount = workerList.length;
      let currCommanderCount = 0;
      for (let i = 0; i < workerList.length; i++) {
        if (workerList[i]['person_type'] === 1) {
          currCommanderCount++;
        }
      }
      if (currWorkerCount < 2) {
        this.$message.error("作业人员不得少于2人");
        return
      }

      if (currCommanderCount === 0) {
        this.$message.error("至少需要一名指挥人员");
        return
      }
      params['person_list'] = workerList;
      params['place'] = this.myform.place;
      params['safe_area_id'] = this.myform.safe_area_id;
      params['safe_area_name'] = this.getSafetyAreaNameByID(this.myform.safe_area_id);
      params['safe_education_persons'] = this.myform.safe_education_persons;
      params['start_time'] = this.myform.start_time;
      params['weight'] = this.myform.weight;
      params['file_list'] = this.$refs.appendixComponent.getAppendixList();
      params['hoist_charge_name'] = this.myform.hoist_charge_name;
      params['hoist_charge_id'] = this.myform.hoist_charge_id;
      params['safe_education_persons_id'] = this.myform.safe_education_persons_id === undefined ? '' : this.myform.safe_education_persons_id;
      params['special_name'] = this.myform.special_name ;
      this.event.buttonLoading = true;
      insertHoistingWorkTicket(params).then(response => {
        this.event.buttonLoading = false;
        this.$EventBus.$emit('event-refresh-lifting-work-ticket-list-data', "");
        if (response.msg === '吊装安全作业证保存成功') {
          this.currTicketID = response.data.id;
          //把新获取到的作业票ID通过eventBus 发出去，实际上就是发给“安全措施组件”和“作业票审批组件”
          this.$EventBus.$emit('get-ticket-id', { "ticketID": this.currTicketID, "jobCode": this.myform.job_code });
          this.pageStatus = 1;
          this.currApprovalStatus = 0;
          this.$message({
            message: '作业票保存成功！',
            type: 'success'
          });
          this.$emit('close');
        }
      }, error => {
        this.event.buttonLoading = false;
        this.$message.error("作业票保存失败!");
        // console.log(error);
      });
    },

    cancelClick () {
      this.$emit('close');
    },
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
            list.push({ ...obj, children: children })
          } else {
            list.push({ ...obj })
          }
        }
      });
      callback(list);
    },
    //根据id查找安全区域对应的名称
    getSafetyAreaNameByID (id) {
      let name = '';
      for (let i = 0; i < this.workAreaOptions.length; i++) {
        let item = this.workAreaOptions[i];
        if (item.id === id) {
          name = item.name;
          break;
        }
      }
      return name;
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

    jobLevelInputBlurListener (value) {
      // let weight = event.target.value;
      let weight = value;
      const float = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/;
      //作业票中显示的等级
      if (float.test(weight)) {
        if (0 <= weight && weight < 40) {
          this.myform.job_level = '三级';
        } else if (40 <= weight && weight <= 100) {
          this.myform.job_level = '二级';
        } else if (weight > 100) {
          this.myform.job_level = '一级';
        }
      }
      //审批流的等级
      if (float.test(weight)) {
        if (0 <= weight && weight < 10) {
          this.jobLevelChangeListener("一级");
        } else if (10 <= weight && weight < 20) {
          this.jobLevelChangeListener("二级");
        } else if (weight > 20) {
          this.jobLevelChangeListener("三级");
        }
      }
    },
    //作业等级的监听
    jobLevelChangeListener (level) {
      //当选着作业等级的时候，把作业等级通过事件总线发送给作业审批组件，请求响审批模板
      let data = {
        tag: 'lifting',
        level: level,
        jobCode: this.myform.job_code
      }
      this.$EventBus.$emit('event-job-level-change', data);
    },
    //将时间戳改成日期时间格式
    dateTimeFormat (timestamp) {
      return moment(timestamp).format("yyyy-MM-DD HH:mm:ss");
    },
    //选着安全区域的监听
    selectSafeArea (data) {
      let { value, label } = data;
      this.myform.safe_area_name = label;
      this.myform.safe_area_id = value;
    },
    //获取作业票详情
    getHoistingDetail (id) {
      this.event.tableLoading = true;
      hoistingInfo(id).then(response => {
        this.event.tableLoading = false;
        this.myform = { ...response.data.data };
        this.event.isDisenabled = true;
        this.myform.harm_identification = this.splitHarmIdentificationString(this.myform.harm_identification);
        //判断 如果要是审批状态为0 或者 页面状态为复制(2) 是可以修改这个作业票的所有内容，否者不可以对作业票进行修改除了附件之外
        this.currApprovalStatus = this.myform.status;
        if (this.currApprovalStatus === 0 && this.pageStatus ===1) {
          this.event.isDisenabled = false;
          this.isEditable = true;
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 3 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        }else if (this.pageStatus === 2){
          getHoistingWorkTicketCode().then(response => {
            this.event.tableLoading = false;
            this.myform['job_code'] = response.data.data.job_code;
            this.myform['apply_person_id'] = this.$store.getters.userInfo.userId;
            this.myform['apply_person_name'] = this.$store.getters.userInfo.nickName;
            this.myform['apply_depart_name'] = this.$store.getters.userInfo.dept.deptName;
            this.myform['apply_depart_id'] = this.$store.getters.userInfo.dept.deptId;
            this.myform['start_time'] = '';
            this.myform['end_time'] = '' ;
            this.event.isDisenabled = false;
            this.isEditable = true;
            this.$refs.appendixComponent.setAppendixByTicketID(id);
            this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
            this.$EventBus.$emit('event-approval-set-way', 0);
            this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
            this.$EventBus.$emit("event-work-ticket-approval-status-code",0);
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
        }else {
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
      })
    },
    //选人的时候返回数据，分类进行赋值
    handleSingleSelect (data) {
      switch (this.dialog.selectPersonType) {
        case 'guardian':
          this.myform.guardian_person_name = data.full_name;
          this.myform.guardian_person_id = data.id;
          break
        case 'charge':
          this.myform.hoist_charge_name = data.full_name;
          this.myform.hoist_charge_id = data.id;
          break
        case 'education':
          this.myform.safe_education_persons = data.full_name;
          this.myform.safe_education_persons_id = data.id;
          break
      }
    },
    //选人输入框的点击事件
    selectPersonInputClick (type) {
      this.dialog.selectDialogVisibility = true;
      this.dialog.selectPersonType = type;
    }
  },
  watch: {
    // 'myform.weight' (val) {
    //   if (this.pageStatus === 0||this.pageStatus === 2) {
    //     this.jobLevelInputBlurListener(val);
    //   }
    // }
  },
  created () {
    if (!this.event.isRegister) {
      this.$EventBus.$on("ticket-approve-data-lifting", (data) => {
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


        if (this.pageStatus === 0 || this.pageStatus === 2){
          this.handleConfirmToNet(data);
        }else if (this.pageStatus === 1){
          this.updateHoistingWorkTicket(data);
        }

      });

      /**
       * 接收审批后的消息,
       * 当接收到审批组件发过来的消息的时候, 把当前作业票状态刷新, 改成不可修改
       */
      this.$EventBus.$on("event-approval-component-approval-message-dz" , ()=>{
        hoistingInfo(this.currTicketID).then(response => {
          this.currApprovalStatus = response.data.data.status;
          if (this.currApprovalStatus !== 0){
            this.event.isDisenabled = true;
            this.mShowConfirmBtn = false;
            this.mId = new Date();
          }
        });
      });
      this.event.isRegister = true;
    }
  },
  mounted () {
    this.pageStatus = this.dataBundle.type;
    if (this.pageStatus === 0) {
      // 表示当前为新增状态
      //获取当前用户的用户真名，用户id 部门名称和部门id
      this.event.isDisenabled = false;
      this.myform.apply_person_id = this.$store.getters.userInfo.userId;
      this.myform.apply_person_name = this.$store.getters.userInfo.nickName;
      // this.myform.apply_depart_name = this.$store.getters.userInfo.alternateField13;
      // this.myform.apply_depart_id = this.$store.getters.userInfo.alternateField12;
      this.myform.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
      this.myform.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
      //表示为新增需要获取作业票编号，当前用户姓名，当前用户所在单位
      this.event.tableLoading = true;
      getHoistingWorkTicketCode().then(response => {
        this.event.tableLoading = false;
        this.myform.job_code = response.data.data.job_code;
      }, error => {
        this.event.tableLoading = false;
        // console.log(error);
      });
      //获取安全区域
      getSafetyArea().then(response => {
        this.workAreaOptions = response.data.data;
      });
      //获取人员树
      getHumanTree().then(res => {
        this.personData = res.data.data;
      }, error => {
        window.console.log(error);
      });
      this.$EventBus.$emit('event-approval-set-way', 0);
    } else if (this.pageStatus === 1) {
      //获取安全区域
      getSafetyArea().then(response => {
        this.workAreaOptions = response.data.data;
      });
      // 当前为修改或者是审批状态，所有的作业票数据都是在上一页传进来得，不需要在进行额外获取，
      // 当前页面只有作业人和附件是可以修改的  其他的信息都是不可修改
      this.currTicketID = this.dataBundle.id;
      this.getHoistingDetail(this.currTicketID);
    }else if (this.pageStatus === 2){
      //todo 如果是复制状态就是  新建和查看详情相结合
      //获取安全区域
      getSafetyArea().then(response => {
        this.workAreaOptions = response.data.data;
      });
      // 当前为修改或者是审批状态，所有的作业票数据都是在上一页传进来得，不需要在进行额外获取，
      // 当前页面只有作业人和附件是可以修改的  其他的信息都是不可修改
      this.currTicketID = this.dataBundle.id;
      this.getHoistingDetail(this.currTicketID);
    }
    this.mShowConfirmBtn = this.isShowComfirm ;
  },
  beforeDestroy () {
    this.$EventBus.$off("ticket-approve-data-lifting");
    this.$EventBus.$off("event-approval-component-approval-message-dz");
  },
}
</script>

<style scoped>
::v-deep.el-form-item {
  margin-bottom: 17px!important;
}
.disabled {
  pointer-events: none;
}
.inputBeforeStyle {
  border: 1px solid #dcdfe6;
  border-radius: 4px 4px 4px 4px;
  width: 100%;
  height: 30px;
  text-align: center;
  line-height: 30px;
  color: #7a7d80;
}
</style>

