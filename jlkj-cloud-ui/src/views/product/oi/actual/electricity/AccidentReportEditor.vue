<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="top">
      <el-row :gutter="60">
        <el-col :span="12">
          <el-form-item label="发生时间" prop="happen_time" style="width: 100%">
            <!-- 是否可以修改 -->
            <!-- :disabled="type=='edit'?true:false" -->
            <el-date-picker v-model="form.happen_time" type="datetime" placeholder="选择时间"
              value-format="yyyy-MM-dd HH:mm:dd" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计划完成时间" prop="measures_completion_time" style="width: 100%">
            <el-date-picker v-model="form.measures_completion_time" type="datetime" placeholder="选择时间"
              value-format="yyyy-MM-dd HH:mm:dd" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="60">
        <el-col :span="24">
          <el-form-item label="主体单位" prop="department_name" style="width: 100%">
            <el-input v-model="form.department_name" placeholder="请输入主体单位 "  maxlength="20" show-word-limit/>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row :gutter="60">
        <el-col :span="24">
          <el-form-item label="事故名称" prop="accident_name" style="width: 100%">
            <el-input v-model="form.accident_name" placeholder="请输入事故名称 "  maxlength="50" show-word-limit/>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row :gutter="60">
        <el-col :span="12">
          <el-form-item label="专业责任人" prop="track_responsible_person" style="width: 100%">
            <el-input v-model="form.track_responsible_person" readonly="readonly" placeholder="请选负责人"
              @click.native="dialogVisible = true">
            </el-input>
          </el-form-item>
          <personnel-selection v-if="dialogVisible" :userDialogVisible="dialogVisible"
            @close-dialog="dialogVisible = false" :isMultiple="false" :height="500" @single-select="handleSingleSelect">
          </personnel-selection>
        </el-col>
        <el-col :span="12">
          <!-- measures_implementation -->
          <el-form-item label="措施落实情况" prop="measures_implementation" style="width: 100%">
            <el-select v-model="form.measures_implementation" placeholder="落实情况">
              <el-option v-for="item in conditionArray" :value="item.value" :label="item.label" :key="item.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :span="60">
        <el-col :span="11">
          <el-form-item label="跟踪责任人" prop="professional_responsible_person" style="width: 100%">
            <el-input v-model="form.professional_responsible_person" :readonly="true" placeholder="请选责任人"
              @focus="acceptanceDialogVisible = true">
            </el-input>
          </el-form-item>
          <personnel-selection v-if="acceptanceDialogVisible" :userDialogVisible="acceptanceDialogVisible"
            @close-dialog="acceptanceDialogVisible = false" :isMultiple="false" :height="500"
            @single-select="acceptanceHandleSingleSelect">
          </personnel-selection>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="防范措施简述" prop="preventive_measures" style="width: 100%">
            <el-input type="textarea" v-model="form.preventive_measures"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <div style="width:100%; height: 1px ; background-color: #e8e8e8" />
      <div class="el-dialog__footer" style="padding: 0; padding-top: 10px;  padding-bottom: 40px;">
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleCancel">取 消</el-button>
          <!-- <el-button type="primary" v-if="form.type ==='add'" @click="handleSave('form')" :loading="submitLoading">确 定</el-button> -->
          <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
        </span>
      </div>
    </el-form>

  </div>
</template>

<script>
import { addYa, upYa } from '@/api/production/oi/YA.js';

export default {
  computed: {
  },
  mounted() {
    this.form = { ...this.data }
  },
  name: "AccidentReportEditor",
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },

  created() {
    this.form = { ...this.data }
  },
  data() {
    return {
      dialogVisible: false,//责任人显示
      acceptanceDialogVisible: false, //验收人显示
      submitLoading: false,//防止多次提交s
      form: {
        track_responsible_person: '',
        professional_responsible_person: '',

      },
      rules: {
        happen_time: [
          { required: true, message: '选择发生时间', trigger: 'blur' }
        ],
        measures_completion_time: [
          { required: true, message: '选择计划完成时间', trigger: 'blur' }
        ],
        department_name: [
          { required: true, message: '选择主体单位', trigger: 'blur' }
        ],
        accident_name: [
          { required: true, message: '填入事故名称', trigger: 'blur' }
        ],
        track_responsible_person: [
          { required: true, message: '选择专业责任人' }
        ],
        measures_implementation: [
          { required: true, message: '选择措施落实情况', trigger: 'blur' }
        ],
        professional_responsible_person: [
          { required: true, message: "选择跟踪责任人" }
        ],
        accident_report_file: [
          { required: true, message: "填入事故报告" }
        ],
        preventive_measures: [
          { required: true, message: "填入防范措施简述", trigger: 'blur' }
        ]
      },
      conditionArray: [
        { value: "已完成", label: "已完成" },
        { value: "未完成", label: "未完成" },
      ],


    }
  },

  methods: {
    //专业
    handleSingleSelect(val) {
      this.$set(this.form, 'track_responsible_person', val.full_name)
    },
    //跟踪
    acceptanceHandleSingleSelect(val) {
      this.$set(this.form, 'professional_responsible_person', val.full_name)

    },
    handleChange(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.submitLoading = true
          if (this.type === 'add') {
            this.form.create_user_id = this.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            addYa({
              accidentName: this.form.accident_name,//事故名称
              departmentName: this.form.department_name,//主体单位
              happenTime: this.form.happen_time,//发生时间
              measuresCompletionTime: this.form.measures_completion_time,//计划完成时间
              measuresImplementation: this.form.measures_implementation,//措施落实情况
              preventiveMeasures: this.form.preventive_measures,//防范措施简述
              professionalResponsible: this.form.professional_responsible_person,//跟踪负责人
              trackResponsiblePerson: this.form.track_responsible_person,//专业负责人
              createUserId: this.form.create_user_id,
              createUserNname: this.form.create_user_name,
              departmentId: '',
              accidentReportFile: this.form.accident_report_file//事故报告
            }).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: "增加成功" });
                this.$emit('submitSave', res.data.msg)
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else if (this.type === 'edit') {
            this.form.modify_user_id = this.userInfo.userId;
            this.form.modify_user_name = this.userInfo.userName;
            upYa({
              id: this.form.id,
              accidentName: this.form.accident_name,//事故名称
              departmentId: '',
              departmentName: this.form.department_name,//主体单位
              happenTime: this.form.happen_time,//发生时间
              preventiveMeasures: this.form.preventive_measures,//防范措施简述
              measuresCompletionTime: this.form.measures_completion_time,//计划完成时间
              measuresImplementation: this.form.measures_implementation,//措施落实情况
              professionalResponsible: this.form.professional_responsible_person,//跟踪负责人
              trackResponsiblePerson: this.form.track_responsible_person,//专业负责人
              modifyUserId: this.form.modify_user_id,
              modifyUserName: this.form.modify_user_name,
              accidentReportFile: this.form.accident_report_file//事故报告
            }).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: "更新成功" });
                this.$emit('submitSave', res.data.msg)
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else {
            return false;
          }
        }
      });
    },
    // handleEdit(formName){
    //   console.log(this.form);
    //   this.$refs[formName].validate((valid) => {
    //     if (valid) {
    //       this.form.create_user_id = this.userInfo.userId;
    //       this.form.create_user_name = this.userInfo.userName;
    //       // this.$emit('submitSave')
    //       this.submitLoading = true

    //     }
    //   });
    // },
    handleCancel() {
      this.$emit('close');
    },
    submitSave(msg) {
      this.openDialog.open = false;
      this.openDialog.data = {};
      this.onLoad();
    },
    close() {
      this.openDialog.open = false;
    }

  }
}
</script>

<style scoped>

</style>
