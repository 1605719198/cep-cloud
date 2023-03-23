<template>
  <div>
    <el-form :model="myform"
             status-icon
             :rules="rules"
             ref="myform"
             label-position="right"
             label-width="95px"
             style="padding: 0 40px"
             v-loading="event.tableLoading">
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="申请单位">
            <el-input readonly="readonly"
                      placeholder="申请单位"
                      v-model="myform.apply_depart_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="申请人">
            <el-input readonly="readonly"
                      placeholder="申请人"
                      v-model="myform.apply_person_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业票编号">
            <el-input readonly="readonly"
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
                            type="datetime"
                            placeholder="作业时间"
                            v-model="myform.start_time"
                            value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结束时间"
                        prop="end_time">
            <el-date-picker :disabled="event.isDisenabled"
                            type="datetime"
                            placeholder="作业时间"
                            v-model="myform.end_time"
                            value-format="yyyy-MM-dd HH:mm:ss" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="监护人"
                        prop="guardian_person_name">
            <el-input :disabled="event.isDisenabled"
                      readonly="readonly"
                      placeholder="监护人"
                      v-model="myform.guardian_person_name"
                      @click.native="!event.isDisenabled ? selectPersonInputClick('guardian') : null " />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="作业名称" prop="job_name">
            <el-input :disabled="event.isDisenabled" placeholder="请输入作业名称" v-model="myform.job_name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="作业内容" prop="job_content">
            <el-input :disabled="event.isDisenabled" placeholder="请输入作业内容" v-model="myform.job_content"></el-input>
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
          <el-form-item label="安全教育人"
                        prop="safe_education_persons">
            <el-input :disabled="event.isDisenabled"
                      readonly="readonly"
                      placeholder="安全教育人"
                      v-model="myform.safe_education_persons"
                      @click.native="!event.isDisenabled ? selectPersonInputClick('education') : null " />
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="其他作业"
                        prop="other_job">
            <el-input :disabled="event.isDisenabled"
                      placeholder="其他作业"
                      style="width: 100%"
                      v-model="myform.other_job" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
      </el-row>
    </el-form>
    <el-tabs v-model="activeName"
             type="card">
      <el-tab-pane label="作业人员"
                   name="first">
        <WorkerComponent ref="workerComponent" :flag="flag"/>
      </el-tab-pane>
      <el-tab-pane label="附件"
                   name="second">
        <AppendixComponent ref="appendixComponent" :flag="flag" />
      </el-tab-pane>
    </el-tabs>

    <div class="el-dialog__footer"
         style="padding:10px 40px 40px 0;">
      <span slot="footer"
            class="dialog-footer">
        <el-button size="small"
                   @click="cancelClick">取 消</el-button>
        <el-button type="primary"
                   @click="confirmEditorData('myform')"
                   size="small"
                   v-if="mShowConfirmBtn"
                   :id="mId"
                   :loading="event.buttonLoading">确 定</el-button>
      </span>
    </div>

    <div>
      <personnel-selection v-if="dialogVisible"
                           :userDialogVisible="dialogVisible"
                           @close-dialog="dialogVisible = false"
                           :isMultiple="false"
                           :height="500"
                           @single-select="handleSingleSelect"></personnel-selection>
    </div>
  </div>
</template>

<script>
import GroundBreakingAddWorkerDialog from './GroundBreakingAddWorkerDialog';
import WorkerComponent from "@/views/security/si/operation/WorkerComponent";
import AppendixComponent from "@/views/security/si/operation/AppendixComponent";
import {
  soilCode,
  soilInfo,
  soilInsert,
  soilUpdate
} from '@/api/security/si/GroundBreakingApi'
import {getSafetyArea, hoistingInfo} from "@/api/security/si/WorkTicketApi";
import { getHumanTree } from "@/api/human/hm/humanResourceTree";
export default {
  components: {
    GroundBreakingAddWorkerDialog,
    WorkerComponent,
    AppendixComponent
  },
  name: "GroundBreakingInfo",
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
    return {
      flag:'DT',
      self: {
        id: this.$store.getters.userInfo.userId,
        name: this.$store.getters.userInfo.nickName,
        // departName: this.$store.getters.userInfo.departmentName ,
        // departID: this.$store.getters.userInfo.alternateField14 ,
        departName: this.$store.getters.userInfo.dept.deptName,
        departID: this.$store.getters.userInfo.dept.deptId
      },
      rules: {
        timeRange: [{ required: true, message: '作业时间不能为空', trigger: 'blur' },],
        safe_area_name: [{ required: true, message: '作业区域不能为空', trigger: 'change' }],
        place: [{ required: true, message: '作业地点不能为空', trigger: 'blur' }],
        safe_education_persons: [{ required: true, message: '安全教育人不能为空' }],
        guardian_person_name: [{ required: true, message: '监护人不能为空' }],
        start_time: [{ required: true, validator: start_time, trigger: 'change' }],
        end_time: [{ required: true, validator: end_time, trigger: 'change' }],
        job_name:[{ required: true, message: '作业名称不能为空', trigger: 'blur' }],
        job_content:[{ required: true, message: '作业内容不能为空', trigger: 'blur' }],
      },
      myform: {
        apply_depart_id: '',
        apply_depart_name: '',
        apply_person_id: '',
        apply_person_name: '',
        approval_list: [],
        end_time: '',
        guardian_person_id: '',
        guardian_person_name: '',
        harm_identification: '',
        job_code: '',
        job_content: '',
        job_name: '',
        other_job: '',
        person_list: [],
        place: '',
        safe_area_id: '',
        safe_area_name: '',
        safe_education_persons: '',
        safe_education_persons_id: '',
        start_time: '',
        upload_file_list: [],
        timeRange: [],
      },
      activeName: 'first',
      workAreaOptions: [],
      treeOpen: false,
      treeData: [],
      treeOpenType: '',
      event: {
        isDisenabled: false,
        buttonLoading: false,
        tableLoading: false
      },
      pageStatus: 0,
      dialogVisible: false,
      selectPersonType: '',
      currTickeID: '',
      currApprovalStatus: -1,
      isEditable: true,
      mId:'',
      mShowConfirmBtn:true
    }
  },
  created () {
    this.$EventBus.$on('ticket-approve-data-groundBreaking', (data) => {
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
      this.saveNewSoil(data);
    });
    /**
     * 接收审批后的消息,
     * 当接收到审批组件发过来的消息的时候, 把当前作业票状态刷新, 改成不可修改
     */
    this.$EventBus.$on("event-approval-component-approval-message-dt" , ()=>{
      soilInfo(this.currTickeID).then(response => {
        this.currApprovalStatus = response.data.data.status;
        if (this.currApprovalStatus !== 0){
          this.event.isDisenabled = true;
          this.mShowConfirmBtn = false;
          this.mId = new Date();
        }
      });
    });
    getSafetyArea().then(res => {
      this.workAreaOptions = res.data.data;
    }, error => {
      window.console.log(error);
    });
    getHumanTree().then(res => {
      this.treeData = res.data.data;
    }, error => {
      window.console.log(error);
    });
  },
  mounted () {
    this.pageStatus = this.dataBundle.type;
    if (this.pageStatus === 0) {
      this.myform.apply_person_id = this.$store.getters.userInfo.userId;
      this.myform.apply_person_name = this.$store.getters.userInfo.nickName;
      // this.myform.apply_depart_name = this.$store.getters.userInfo.departmentName;
      // this.myform.apply_depart_id = this.$store.getters.userInfo.alternateField14;
      this.myform.apply_depart_name = this.$store.getters.userInfo.dept.deptName;
      this.myform.apply_depart_id = this.$store.getters.userInfo.dept.deptId;
      this.getSoilCode();
      this.$EventBus.$emit('event-approval-set-way', 0);
    } else if (this.pageStatus === 2){
      this.currTickeID = this.dataBundle.id;
      this.getSoilDetail(this.currTickeID);
    }else {
      this.currTickeID = this.dataBundle.id;
      this.getSoilDetail(this.currTickeID);
    }
    this.mShowConfirmBtn = this.isShowComfirm ;
  },
  beforeDestroy () {
    this.$EventBus.$off('ticket-approve-data-groundBreaking');
    this.$EventBus.$off('event-approval-component-approval-message-dt');
  },
  methods: {
    //获取吊装作业的数据
    confirmEditorData (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pageStatus === 0 && this.currApprovalStatus === -1) {
            this.$EventBus.$emit("get-ticket-approve-data", "groundBreaking");
          }else if (this.pageStatus === 2){
            this.$EventBus.$emit("get-ticket-approve-data", "groundBreaking");
          }else {
            this.updateSoilAppendix();
          }
        } else {
          console.log('表单验证不通过');
          return false;
        }
      });
    },
    //取消
    cancelClick () {
      this.$emit('close');
    },
    //选着安全区域的监听
    selectSafeArea (data) {
      let { value, label } = data;
      this.myform.safe_area_name = label;
      this.myform.safe_area_id = value;
    },
    //选人的时候返回的数据，分类进行赋值
    handleSingleSelect (data) {
      // console.log(data);
      if (this.selectPersonType === 'education') {
        this.myform.safe_education_persons_id = data.id;
        this.myform.safe_education_persons = data.full_name;
      } else if (this.selectPersonType === 'guardian') {
        this.myform.guardian_person_id = data.id;
        this.myform.guardian_person_name = data.full_name;
      }
    },
    //获取作业证票号
    getSoilCode () {
      this.event.tableLoading = true;
      soilCode().then(response => {
        this.event.tableLoading = false;
        this.myform.job_code = response.data.data.job_code;
        this.$EventBus.$emit('event-job-level-change', { level: '', jobCode: this.myform.job_code })//通知审批组件获取审批模板
      }, error => {
        this.event.tableLoading = false;
        // console.log(error);
      });
    },
    //获取作业票详情
    getSoilDetail (id) {
      this.event.tableLoading = true;
      soilInfo(id).then(response => {
        this.event.tableLoading = false;
        this.event.isDisenabled = true;
        this.myform = { ...response.data.data };

        //判断 如果要是审批状态为0 是可以修改这个作业票的所有内容，否者不可以对作业票进行修改除了附件之外
        this.currApprovalStatus = this.myform.status;
        if (this.currApprovalStatus === 0 && this.pageStatus === 1) {
          this.event.isDisenabled = false;
          this.isEditable = true;
          //安全措施、作业审批、作业人和附件
          this.$refs.workerComponent.setWorkerListByTicketID(id);
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        }else if (this.pageStatus === 2){
          this.event.tableLoading = true;
          soilCode().then(response => {
            this.event.tableLoading = false;
            this.myform.job_code = response.data.data.job_code;

            this.myform['apply_person_id'] = this.self.id;
            this.myform['apply_person_name'] = this.self.name;
            this.myform['apply_depart_name'] = this.self.departName;
            this.myform['apply_depart_id'] = this.self.departID;
            this.myform['start_time'] = '';
            this.myform['end_time'] = '' ;
            this.event.isDisenabled = false;
            this.isEditable = true;
            //安全措施、作业审批、作业人和附件
            this.$refs.workerComponent.setWorkerListByTicketID(id);
            this.$refs.appendixComponent.setAppendixByTicketID(id);
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
          //安全措施、作业审批、作业人和附件
          this.$refs.workerComponent.setWorkerListByTicketID(id);
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        }
      }, error => {
        this.event.tableLoading = false;
        // console.log(error);
      });
    },
    //生产文件ID的字符串
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
    saveNewSoil (approvalData) {
      this.myform['upload_file_list'] = this.makeFileIDStr(this.$refs.appendixComponent.getAppendixList());
      this.myform['person_list'] = this.$refs.workerComponent.getWorkerList();
      this.myform['approval_list'] = approvalData;
      this.event.buttonLoading = true;
      soilInsert(this.myform).then(response => {
        this.event.buttonLoading = false;
        this.$EventBus.$emit('event-refresh-groundBreaking-work-ticket-list-data', "");
        if (response.data.msg === '动土安全作业证保存成功') {
          this.currTicketID = response.data.data.id;
          //把新获取到的作业票ID通过eventBus 发出去，实际上就是发给“安全措施组件”和“作业票审批组件”
          this.$EventBus.$emit('get-ticket-id', { "ticketID": this.currTicketID, "jobCode": this.myform.job_code });
          this.pageStatus = 1;
          this.currApprovalStatus = 0;
          this.$message({
            message: response.data.msg,
            type: 'success'
          });
          this.$emit('close');
        }
      }, error => {
        this.event.buttonLoading = false;
        this.$message.error("作业票保存失败!");
        // console.log(error);
      })
    },
    //修改作业票附件
    updateSoilAppendix () {
      let params = { ...this.myform };
      params['upload_file_list'] = this.makeFileIDStr(this.$refs.appendixComponent.getAppendixList());
      params['person_list'] = this.$refs.workerComponent.getWorkerList();
      this.event.buttonLoading = true;
      soilUpdate(params).then(response => {
        this.event.buttonLoading = false;
        if (response.data.msg === '动土安全作业证保存成功') {
          this.$EventBus.$emit('event-refresh-groundBreaking-work-ticket-list-data', "");
          this.$message({
            message: '作业票修改成功！',
            type: 'success'
          });
          this.$emit('close');
        } else {
          this.$message.error("作业票修改失败");
        }
      }, error => {
        this.event.buttonLoading = false;
        // console.log(error);
      });
    },
    //选人的点击事件
    selectPersonInputClick (type) {
      this.dialogVisible = true;
      this.selectPersonType = type;
    }
  }
}
</script>

<style scoped>
.el-form-item {
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
