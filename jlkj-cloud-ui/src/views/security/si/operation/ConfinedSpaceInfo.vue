<template>
  <div>
    <el-form :model="formData"
             ref="formData"
             :rules="rules"
             status-icon
             label-position="right"
             style="padding: 0 40px"
             label-width="130px"
             v-loading="event.tableLoading">
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item label="申请单位">
            <el-input class="disabled"
                      placeholder="申请单位"
                      v-model="formData.apply_depart_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="申请人">
            <el-input class="disabled"
                      placeholder="申请人"
                      v-model="formData.apply_person_name" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业票编号">
            <el-input class="disabled"
                      style="width: 100%"
                      placeholder="作业票编号"
                      v-model="formData.job_code" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="8">
          <el-form-item v-if="!event.isDisenabled"
                        label="设备单位"
                        prop="equip_dep_name">
            <select-tree style="width: 100%"
                         v-model="formData.equip_dep_name"
                         placeholder="作业单位"
                         :select-form="treeSelectData.deviceUnit"
                         :data="treeSelectData.constructionUnitData"
                         :otherForm="['flag']"
                         :isSelectNode="true"
                         :isClearable="true"
                         :isAccordion="true"
                         :isFilterable="true"
                         :isNodeChildrenNull="true"
                         @change="getDeviceUnit" />
          </el-form-item>
          <el-form-item v-else
                        label="设备单位">
            <el-input :value="formData.equip_dep_name"
                      disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业负责人"
                        prop="job_responsible_person_name">
            <el-input v-model="formData.job_responsible_person_name"
                      placeholder="作业负责人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonClick('job_responsible_person_name')"
                      :disabled="event.isDisenabled" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="监护人"
                        prop="guardian_person_name">
            <el-input v-model="formData.guardian_person_name"
                      placeholder="监护人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonClick('guardian_person_name')"
                      :disabled="event.isDisenabled" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="实际开始时间">
            <el-input :readonly="true"
                      placeholder="实际作业开始时间"
                      v-model="formData.start_time" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实际结束时间">
            <el-input :readonly="true"
                      placeholder="实际作业结束时间"
                      v-model="formData.end_time" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col>
          <el-form-item label="许可证编制人"
                        prop="work_emp_name">
            <el-input v-model="formData.work_emp_name"
                      placeholder="许可证编制人"
                      :readonly="true"
                      @focus="event.isDisenabled ? null : selectPersonClick('work_emp_name')"
                      :disabled="event.isDisenabled" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col>
          <el-form-item label="作业内容描述"
                        prop="job_content">
            <el-input :disabled="event.isDisenabled"
                      v-model="formData.job_content"
                      placeholder="作业内容描述" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col>
          <el-form-item label="主要危险因素"
                        prop="harm_identification">
            <!-- <el-select :disabled="event.isDisenabled"
                       class="customSelectStyle"
                       :popper-append-to-body="false"
                       v-model="formData.harm_identification"
                       multiple
                       placeholder="主要危险因素"
                       style="width: 100%">申请有限空间作业票
              <el-option v-for="item in dangerOptions"
                         :key="item.value"
                         :label="item.label"
                         :value="item.label" />
            </el-select> -->
            <el-checkbox-group :disabled="event.isDisenabled"
                       class="customSelectStyle"
                       :popper-append-to-body="false"
                       v-model="formData.harm_identification"
                       multiple
                       placeholder="主要危险因素"
                       style="width: 100%">
              <el-checkbox v-for="item in dangerOptions"
                         :key="item.value"
                         :label="item.label" />
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col>
          <el-form-item label="实施安全教育人"
                        prop="safe_education_person_name">
            <el-input :disabled="event.isDisenabled"  v-model="formData.safe_education_person_name" placeholder="请输入实施安全教育人" maxlength="100" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col>
          <el-form-item label="救援器具"
                        prop="rescue_equipment">
            <el-select :disabled="event.isDisenabled"
                       class="customSelectStyle"
                       :popper-append-to-body="false"
                       v-model="formData.rescue_equipment"
                       multiple
                       placeholder="救援器具"
                       style="width: 100%">
              <el-option v-for="item in rescueEquipmentOptions"
                         :key="item.value"
                         :label="item.label"
                         :value="item.label" />
            </el-select>
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

      <el-tab-pane label="采样分析"
                   name="second">
        <AnalysisComponent ref="analysisComponent" />
      </el-tab-pane>

      <el-tab-pane label="附件"
                   name="third">
        <AppendixComponent ref="appendixComponent" :flag="flag"/>
      </el-tab-pane>
    </el-tabs>
    <personnel-selection v-if="openSelectPersonDialog.isShow"
                         :userDialogVisible="openSelectPersonDialog.isShow"
                         @close-dialog="openSelectPersonDialog.isShow = false"
                         :isMultiple="false"
                         :height="500"
                         @single-select="handleSingleSelect"></personnel-selection>

    <div class="el-dialog__footer"
         style="padding:10px 40px 40px 0;">
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary"
                   @click="confirmEditorData('formData')"
                   v-if="mShowConfirmBtn"
                   :id="mId"
                   :loading="event.loading">确 定</el-button>
      </span>
    </div>
  </div>
</template>


<script>
import AppendixComponent from "@/views/security/si/operation/AppendixComponent";
import AnalysisComponent from "@/views/security/si/operation/AnalysisComponent";
import WorkerCompoent from "@/views/security/si/operation/WorkerComponent";
import { spaceCode, spaceInfo, spaceInsert, spaceUpdate } from "@/api/security/si/ConfinedSpaceApi";
import { getHumanTree } from "@/api/human/hm/humanResourceTree";
import {hoistingInfo} from "@/api/security/si/WorkTicketApi";
export default {
  components: {
    WorkerCompoent,
    AnalysisComponent,
    AppendixComponent
  },
  name: "ConfiendSpaceInfo",
  props: ['dataBundle', "isShowComfirm"],
  data () {
    return {
      flag:'SX',
      self: {
        id: this.$store.getters.userInfo.userId,
        name: this.$store.getters.userInfo.nickName,
        // departName: this.$store.getters.userInfo.departmentName ,
        // departID: this.$store.getters.userInfo.alternateField14 ,
        departName: this.$store.getters.userInfo.dept.deptName,
        departID: this.$store.getters.userInfo.dept.deptId
      },
      openDialog: {
        open: false,
        type: "",
        title: "",
        width: "500px",
        data: {},
      },
      openSelectPersonDialog: {
        isShow: false,
        selectTag: ''
      },
      formData: {
        apply_depart_name: '',
        apply_person_name: '',
        job_code: '',
        job_responsible_person_name: '',
        job_responsible_person_id: '',
        guardian_person_name: '',
        guardian_person_id: '',
        work_emp_name: '',
        work_emp_id: '',
        start_time: '',
        end_time: '',
        job_content: '',
        harm_identification: [],
        rescue_equipment: '',
        equip_dep_name: '',//设备单位
        equip_dep_id: '',//设备单位ID
        safe_education_person_name: '',

        /***************下面是多余的数据********************/
        job_level: '',
        original_medium_name: '',
        other_job: '',
        place: '',
        safe_area_id: '',
        safe_area_name: '',
        work_type_name: '',
      },
      rules: {
        equip_dep_name: [{ required: true, message: '设备单位不能为空', trigger: 'change' }],
        job_responsible_person_name: [{ required: true, message: '作业负责人不能为空', trigger: 'change' }],
        guardian_person_name: [{ required: true, message: '监护人不能为空', trigger: 'change' }],
        work_emp_name: [{ required: true, message: '许可证编制人不能为空', trigger: 'change' }],
        device_unit_safety_engineer_name: [{ required: true, message: '设备所属单位安全工程师不能为空', trigger: 'change' }],
        job_content: [{ required: true, message: '作业内容描述不能为空', trigger: 'blur' }],
        harm_identification: [{ required: true, message: '主要危险因素不能为空', trigger: 'blur' }],
        rescue_equipment: [{ required: true, message: '救援器具不能为空', trigger: 'blur' }],
        safe_education_person_name: [{ required: true, message: '安全实施教育人不能为空', trigger: 'blur' }],
      },
      event: {
        isDisenabled: false,
        loading: false,
        tableLoading: false,
      },
      treeSelectData: {
        constructionUnitData: [],
        deviceUnit: { label: '', value: '' }
      },
      dangerOptions: [
        { label: '爆炸性粉尘', value: '1' },
        { label: '易燃性物质', value: '2' },
        { label: '腐蚀性液体', value: '3' },
        { label: '挥发性物质', value: '4' },
        { label: '高压气体/液体', value: '5' },
        { label: '有毒有害化学品', value: '6' },
        { label: '高温/低温', value: '7' },
        { label: '触电', value: '8' },
        { label: '惰性气体、有毒气体', value: '9' },
        { label: '噪音', value: '10' },
        { label: '产生火花／静电', value: '11' },
        { label: '旋转设备', value: '12' },
        { label: '蒸汽', value: '13' },
        { label: '淹没/埋没', value: '14' },
        { label: '坠落', value: '15' },
        { label: '空间内活动受限 ', value: '16' },
      ],
      rescueEquipmentOptions: [
        { label: '空气呼吸器', value: '1' },
        { label: '有害气体检测仪', value: '2' },
        { label: '氧含量检测仪', value: '3' },
        { label: '救援绳', value: '4' },
        { label: '煤气报警仪', value: '5' },
        { label: '通风设施', value: '6' },
        { label: '五点式安全带', value: '7' },
        { label: '对讲机', value: '8' },
        { label: '警戒带', value: '9' },
        { label: '防尘面具', value: '10' },
        { label: '测温仪', value: '11' },
        { label: '其它', value: '12' },
      ],
      activeName: 'first',
      pageStatus: 0,
      currApprovalStatus: -1,
      currTicketID: '',
      isEditable: true,
      mId:'',
      mShowConfirmBtn:true,
    }


  },
  methods: {
    handleOpenWindow (type, index, row) {
      let title = "采样分析";
      this.openDialog.type = type;
      this.openDialog.open = true;
      this.openDialog.data = row;
      if (type === "add") {

        this.openDialog.title = "新增" + title;
        this.openDialog.width = "750px";
        this.openDialog.data = {};
      }
    },
    getDeviceUnit (data) {
      this.formData.equip_dep_name = data.label;
      this.formData.equip_dep_id = data.value;
    },
    selectPersonClick (type) {
      this.openSelectPersonDialog.isShow = true;
      this.openSelectPersonDialog.selectTag = type;
    },
    handleSingleSelect (data) {
      switch (this.openSelectPersonDialog.selectTag) {
        case 'job_responsible_person_name':
          this.formData.job_responsible_person_name = data.full_name;
          this.formData.job_responsible_person_id = data.id;
          break
        case 'guardian_person_name':
          this.formData.guardian_person_name = data.full_name;
          this.formData.guardian_person_id = data.id;
          break
        case 'work_emp_name':
          this.formData.work_emp_name = data.full_name;
          this.formData.work_emp_id = data.id;
          break
        case 'device_unit_safety_engineer_name':
          this.formData.device_unit_safety_engineer_name = data.full_name;
          this.formData.device_unit_safety_engineer_id = data.id;
          break
      }
    },
    //确认
    confirmEditorData (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pageStatus === 0 && this.currApprovalStatus === -1) {
            this.$EventBus.$emit("get-ticket-approve-data", "space");
          }else if (this.pageStatus === 2){
            this.$EventBus.$emit("get-ticket-approve-data", "space");
          } else {
            //this.updateSpaceTicket();
            this.$EventBus.$emit("get-ticket-approve-data", "space");
          }
        } else {
          console.log('表单验证不通过');
          return false;
        }
      });
    },
    //取消
    cancel () {
      this.$emit('close');
    },
    //获取作业票编号
    getTicketCode () {
      this.event.tableLoading = true;
      spaceCode().then(response => {
        this.event.tableLoading = false;
        this.formData.job_code = response.data.data.job_code;
        this.$EventBus.$emit('event-job-level-change', { level: '', jobCode: this.formData.job_code });//通知审批组件获取审批模板
      }, error => {
        this.event.tableLoading = false;
        window.console.log(error);
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
    insertSpace (approvalData) {
      let params = { ...this.formData };
      params['approval_list'] = approvalData;
      params['file_list'] = this.$refs.appendixComponent.getAppendixList();
      params['harm_identification'] = this.jointHarmIdentification(this.formData.harm_identification);
      params['person_list'] = this.$refs.workerComponent.getWorkerList();
      params['rescue_equipment'] = this.jointHarmIdentification(this.formData.rescue_equipment);
      this.event.loading = true;
      spaceInsert(params).then(response => {
        this.event.loading = false;
        this.$EventBus.$emit('event-refresh-space-work-ticket-list-data', "");
        if (response.data.msg === '受限空间作业证保存成功') {
          this.currTicketID = response.data.data.id;
          //把新获取到的作业票ID通过eventBus 发出去，实际上就是发给“安全措施组件”和“作业票审批组件”
          this.$EventBus.$emit('get-ticket-id', { "ticketID": this.currTicketID, "jobCode": this.formData.job_code });
          this.$refs.analysisComponent.setTicketID(this.currTicketID);
          this.pageStatus = 1;
          this.currApprovalStatus = 0;
          this.$message({
            message: response.data.msg,
            type: 'success'
          });
          this.$emit("close");
        }
      }, error => {
        this.$message.error("作业票保存失败!");
        this.event.loading = false;
        window.console.log(error);
      })
    },
    //修改有限空间作业票
    updateSpaceTicket (approvalData) {
      // let params = { ... this.formData };
      // params['id'] = this.currTicketID;
      // params['file_list'] = this.$refs.appendixComponent.getAppendixList();
      // params['harm_identification'] = this.jointHarmIdentification(this.formData.harm_identification);
      // params['person_list'] = this.$refs.workerComponent.getWorkerList();
      // params['rescue_equipment'] = this.jointHarmIdentification(this.formData.rescue_equipment);

      let params = { ...this.formData };
      params['id'] = this.currTicketID;
      params['approval_list'] = approvalData;
      params['file_list'] = this.$refs.appendixComponent.getAppendixList();
      params['harm_identification'] = this.jointHarmIdentification(this.formData.harm_identification);
      params['person_list'] = this.$refs.workerComponent.getWorkerList();
      params['rescue_equipment'] = this.jointHarmIdentification(this.formData.rescue_equipment);

      this.event.loading = true;
      spaceUpdate(params).then(response => {
        this.event.loading = false;
        if (response.data.msg === '受限空间作业证保存成功') {
          this.$EventBus.$emit('event-refresh-space-work-ticket-list-data', "");
          this.$message({
            message: response.data.msg,
            type: 'success'
          });
          this.$emit('close');
        }
      }, error => {
        this.event.loading = false;
        this.$message.error("作业票保存失败!");
        window.console.log(error);
      })
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
    getConstructionUnitTree () {
      getHumanTree().then(res => {
        this.updateDeptTreeData(res.data, 1, (res) => {
          this.treeSelectData.constructionUnitData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
    },
    //获取作业票详情
    getSpaceInfo (id) {
      this.event.tableLoading = true;
      spaceInfo(id).then(response => {
        this.event.tableLoading = false;
        //给作业票设置数据
        this.formData = { ...response.data.data };
        this.formData['harm_identification'] = this.splitHarmIdentificationString(this.formData['harm_identification']);
        this.formData['rescue_equipment'] = this.splitHarmIdentificationString(this.formData['rescue_equipment']);
        //判断 如果要是审批状态为0 是可以修改这个作业票的所有内容，否者不可以对作业票进行修改除了附件之外
        this.currApprovalStatus = this.formData.status;
        if (this.currApprovalStatus === 0 && this.pageStatus === 1) {
          this.event.isDisenabled = false;
          this.isEditable = true;

          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$refs.analysisComponent.setAnalysisListByID(id, false);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 3 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        } else if (this.pageStatus === 2){
          this.event.tableLoading = true;
          spaceCode().then(response => {
            this.event.tableLoading = false;
            this.formData.job_code = response.data.data.job_code;
            this.formData['apply_person_id'] = this.self.id;
            this.formData['apply_person_name'] = this.self.name;
            this.formData['apply_depart_name'] = this.self.departName;
            this.formData['apply_depart_id'] = this.self.departID;
            this.formData['start_time'] = '';
            this.formData['end_time'] = '' ;
            this.event.isDisenabled = false;
            this.isEditable = true;
            this.$refs.appendixComponent.setAppendixByTicketID(id);
            this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
            this.$refs.analysisComponent.setAnalysisListByID(id, true);
            this.$EventBus.$emit('event-approval-set-way', 0);
            this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
            this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
          }, error => {
            this.event.tableLoading = false;
            window.console.log(error);
          });
        }else if (this.currApprovalStatus === 1 || this.currApprovalStatus === 2 && this.pageStatus === 1){
          this.event.isDisenabled = true;
          this.isEditable = true;
          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$refs.analysisComponent.setAnalysisListByID(id, false);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        } else {
          this.event.isDisenabled = true;
          this.isEditable = false;

          this.$refs.appendixComponent.setAppendixByTicketID(id);
          this.$refs.workerComponent.setWorkerListByTicketID(id, this.isEditable);
          this.$refs.analysisComponent.setAnalysisListByID(id, false);
          this.$EventBus.$emit('event-approval-set-way', this.isEditable ? 1 : 2);
          this.$EventBus.$emit('event-isEditable-safetymeasures', this.isEditable);
          this.$EventBus.$emit("event-work-ticket-approval-status-code",this.currApprovalStatus);
        }
      }, error => {
        this.event.tableLoading = false;
        window.console.log(error);
      });
    }
  },
  created () {
    this.$EventBus.$on('ticket-approve-data-space', data => {
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
        this.insertSpace(data);
      }else if (this.pageStatus === 1){
        this.updateSpaceTicket(data);
      }

    });
    //刷新实际开始时间的通知
    this.$EventBus.$on('event-refresh-space-start-time', () => {
      spaceInfo(this.currTicketID).then(response => {
        //请求详情信息，把开始时间设置到表单中
        this.formData.start_time = response.data.data.start_time;
      }, error => {
        window.console.log(error);
      });
    });
    //刷新实际结束时间的通知
    this.$EventBus.$on('event-refresh-space-end-time', () => {
      spaceInfo(this.currTicketID).then(response => {
        //请求详情信息，把开始时间设置到表单中
        this.formData.end_time = response.data.data.end_time;
      }, error => {
        window.console.log(error);
      });
    });

    /**
     * 接收审批后的消息,
     * 当接收到审批组件发过来的消息的时候, 把当前作业票状态刷新, 改成不可修改
     */
    this.$EventBus.$on("event-approval-component-approval-message-sx" , ()=>{
      spaceInfo(this.currTicketID).then(response => {
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
      this.formData.apply_person_id = this.self.id;
      this.formData.apply_person_name = this.self.name;
      this.formData.apply_depart_name = this.self.departName;
      this.formData.apply_depart_id = this.self.departID;
      this.getTicketCode();
      this.getConstructionUnitTree();
      this.$EventBus.$emit('event-approval-set-way', 0);
    }else if (this.pageStatus === 2){
      this.getConstructionUnitTree();
      this.currTicketID = this.dataBundle.id;
      this.getSpaceInfo(this.currTicketID);
    }else {
      this.getConstructionUnitTree();
      this.currTicketID = this.dataBundle.id;
      this.getSpaceInfo(this.currTicketID);
    }
    this.mShowConfirmBtn = this.isShowComfirm;
  },
  beforeDestroy () {
    this.$EventBus.$off('ticket-approve-data-space');
    this.$EventBus.$off('event-refresh-space-start-time');
    this.$EventBus.$off('event-refresh-space-end-time');
    this.$EventBus.$off('event-approval-component-approval-message-sx');
  },
  watch: {
    'formData.equip_dep_name' (newValue) {
      this.treeSelectData.deviceUnit.label = newValue;
    },
    'formData.equip_dep_id' (newValue) {
      this.treeSelectData.deviceUnit.value = newValue;
    }
  }
}
</script>

<style scoped>
.disabled {
  pointer-events: none;
}
</style>
