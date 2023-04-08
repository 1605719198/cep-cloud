<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="100px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="submit_depart_name"
                          label="上报单位">
              <el-input v-model="form.submit_depart_name"
                        disabled
                        placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="submit_person_id"
                          label="上报人">
              <el-input v-model="form.submit_person_name"
                        disabled
                        placeholder="请选上报人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="限期完成"
                          prop="danger_deadline_time">
              <el-date-picker type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择时间"
                              v-model="form.danger_deadline_time"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="作业区域"
                          prop="safe_area_id">
              <el-select class="customSelectStyle"
                         v-model="form.safe_area_id"
                         :popper-append-to-body="false"
                         style="width:100%;"
                         filterable
                         clearable
                         placeholder="请选择">
                <el-option v-for="item in operation0ptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="责任单位"
                          prop="duty_depart_id">
              <select-tree style="width: 100%;"
                           v-model="form.duty_depart_id"
                           placeholder="请选择"
                           :selectForm="deptForm"
                           :data="deptData"
                           :isSelectNode="true"
                           :isClearable="true"
                           :isAccordion="true"
                           :isFilterable="true"
                           :isNodeChildrenNull="true"
                           @change="getDept" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="duty_person_id"
                          label="责任人">
              <el-select class="customSelectStyle"
                         style="width: 100%;"
                         :popper-append-to-body="false"
                         v-model="form.duty_person_id"
                         filterable
                         clearable
                         placeholder="请选择">
                <el-option v-for="item in userOptions"
                           :key="item.id"
                           :label="item.label"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="隐患等级"
                          prop="danger_type_id">
              <el-select class="customSelectStyle"
                         v-model="form.danger_type_id"
                         :popper-append-to-body="false"
                         style="width:100%;"
                         clearable
                         placeholder="请选择">
                <el-option v-for="item in hidden0ptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="acceptance_person_id"
                          label="验收人">
              <el-input v-model="form.acceptance_person_name"
                        :readonly="true"
                        placeholder="请选验收人"
                        @focus="acceptanceDialogVisible = true"></el-input>
            </el-form-item>
            <personnel-selection v-if="acceptanceDialogVisible"
                                 :userDialogVisible="acceptanceDialogVisible"
                                 @close-dialog="acceptanceDialogVisible = false"
                                 :isMultiple="false"
                                 :height="500"
                                 @single-select="acceptanceHandleSingleSelect"></personnel-selection>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item prop="danger_content"
                          label="隐患内容">
              <el-input type="textarea"
                        :autosize="{ minRows: 5, maxRows: 5 }"
                        placeholder="请输入隐患内容"
                        v-model="form.danger_content">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item prop="dispose_measure"
                          label="整改措施">
              <el-input type="textarea"
                        placeholder="请输入整改内容"
                        :autosize="{ minRows: 5, maxRows: 5 }"
                        v-model="form.dispose_measure">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item>
              <el-upload class="upload-demo"
                         :action="action"
                         accept=".png,.jpg"
                         :headers="headers"
                         multiple
                         :limit="4"
                         :on-exceed="handleExceed"
                         :on-remove="handleRemove"
                         :on-success="handleSuccess"
                         :file-list="fileList"
                         list-type="picture">
                <el-button size="small"
                           type="primary">点击上传图片</el-button>
                <div slot="tip"
                     class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确认</el-button>
    </div>
  </div>
</template>

<script>
import { getSafetyArea, dangertype } from "@/api/security/si/WorkTicketApi"
import { dangerInsert } from "@/api/security/si/hiddenDanger/index";
import { getOrganizationDeptTree, getOrganizationUserList, getHumanTree } from "@/api/human/hm/humanResourceTree";
import { mapGetters } from "vuex";
import {getToken} from "@/utils/auth";
// import { dateFormat } from '@/utils/date'
export default {
  data () {
    return {
      acceptanceDialogVisible: false, //验收人显示
      form: {
        // danger_deadline_time: dateFormat(new Date(), 'yyyy-MM-dd'),
        //必传字段不传报错
        submit_person_id: '',
        acceptance_person_id: '',
        submit_image1: '',
        submit_image2: '',
        submit_image3: '',
        submit_image4: '',
        dispose_measure: '',
        submit_sign_image: ''
      },
      // 表单校验
      rules: {
        danger_deadline_time: [{ required: true, message: '限期完成不能为空', trigger: 'change' }],
        duty_depart_id: [{ required: true, message: '责任单位不能为空', trigger: 'blur' }],
        safe_area_id: [{ required: true, message: '作业区域不能为空', trigger: 'change' }],
        duty_person_id: [{ required: true, message: '责任人不能为空', trigger: 'change' }],
        danger_type_id: [{ required: true, message: '隐患等级不能为空', trigger: 'change' }],
        acceptance_person_id: [{ required: true, message: '验收人不能为空', trigger: 'change' }],
        danger_content: [{ required: true, message: '隐患内容不能为空', trigger: 'blur' }],
        dispose_measure: [{ required: true, message: '整改措施不能为空', trigger: 'blur' }],
      },
      //作业区域下拉
      operation0ptions: [],
      //责任人下拉
      userOptions: [],
      //隐患等级下拉
      hidden0ptions: [],
      //单位下拉树
      deptData: [],
      //人员下拉树
      userData: [],
      //单位选中回显的数据
      deptForm: { value: '', label: '' },
      //图片回显列表
      fileList: [],
      submitLoading: false,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      action: ''
    }
  },
  watch: {
    'form.duty_depart_id' (newValue) {
      this.deptForm.value = newValue;
    },
  },
  created () {
    this.getOrganizationTree()
    this.getTree()
    this.getSafeArea()
    this.getDangertype()
    this.action = process.env.VUE_APP_BASE_API + '/file/file/upload'
    //上报单位（当前登录人的单位）
    this.form.submit_depart_name = this.$store.getters.userInfo.dept.deptName
    this.form.submit_depart_id = this.$store.getters.userInfo.dept.deptId
    // console.log(this.$store.getters.userInfo.dept.deptId, this.$store.getters.userInfo.dept.deptName)
    //上报人（当前登录人）
    this.form.submit_person_id = this.$store.getters.userInfo.userId;
    this.form.submit_person_name = this.userInfo.userName;
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取安全区域
    getSafeArea () {
      getSafetyArea().then(res => {
        // console.log(res)
        this.operation0ptions = res.data.data
      })
    },
    //获取安全隐患等级
    getDangertype () {
      dangertype().then(res => {
        // console.log(res)
        this.hidden0ptions = res.data.data
      })
    },
    //获取单位下拉树
    getOrganizationTree () {
      getOrganizationDeptTree().then(res => {
        // console.log(res, "res")
        this.deptData = res.data
      })
    },
    //更改赋值
    getDept (obj) {
      console.log(obj.value, "obj")
      this.form.duty_depart_id = obj.value;
      this.form.duty_depart_name = obj.label;
      if (this.form.duty_person_id) {
        this.form.duty_person_id = ''
      }
      getOrganizationUserList(obj.value).then(res => {
        // console.log(res)
        this.userOptions = res.data
      })
    },
    //人员下拉树
    getTree () {
      getHumanTree().then(res => {
        this.userData = res.data.data;
      }, error => {
        window.console.log(error);
      });
    },
    //验收人回调
    acceptanceHandleSingleSelect (val) {
      this.form.acceptance_person_id = val.id
      this.form.acceptance_person_name = val.full_name
    },
    //赋值图片id的方法
    setImageID (fileList) {
      fileList.forEach((item, index) => {
        if (index == 0) {
          this.form.submit_image1 = item.response.data.id
        }
        if (index == 1) {
          this.form.submit_image2 = item.response.data.id
        }
        if (index == 2) {
          this.form.submit_image3 = item.response.data.id
        }
        if (index == 3) {
          this.form.submit_image4 = item.response.data.id
        }
      })
    },
    //文件上传成功时的钩子
    handleSuccess (response, file, fileList) {
      // console.log(fileList, "fileList")
      this.setImageID(fileList)
      // console.log(this.form.submit_image1, this.form.submit_image2, this.form.submit_image3, this.form.submit_image4)
    },
    //文件列表移除文件时的钩子
    handleRemove (file, fileList) {
      this.form.submit_image1 = ''
      this.form.submit_image2 = ''
      this.form.submit_image3 = ''
      this.form.submit_image4 = ''
      this.setImageID(fileList)
      // console.log(this.form.submit_image1, this.form.submit_image2, this.form.submit_image3, this.form.submit_image4)
    },
    //文件超出个数限制时的钩子
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 4 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //danger_type_name（隐患等级名称）
          this.hidden0ptions.forEach(item => {
            if (item.id == this.form.danger_type_id) {
              this.form.danger_type_name = item.name
            }
          })
          //safe_area_name（区域名称）
          this.operation0ptions.forEach(item => {
            if (item.id == this.form.safe_area_id) {
              this.form.safe_area_name = item.name
            }
          })
          //duty_person_name(责任人名称)
          this.userOptions.forEach(item => {
            if (item.id == this.form.duty_person_id) {
              this.form.duty_person_name = item.label
            }
          })
          this.form.create_user_id = this.$store.getters.userInfo.userId;
          this.form.create_user_name = this.userInfo.userName;
          // this.$emit('submitSave')
          this.submitLoading = true
          dangerInsert(this.form).then(res => {
            if (res.data.code === "0") {
              this.$message({ type: "success", message: res.data.msg });
              this.$emit('submitSave', res.data.msg)
            }
            this.submitLoading = false
          }, error => {
            this.submitLoading = false
            window.console.log(error);
          });
        }
      });
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style>
</style>
