<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="巡检时间"
                          prop="check_time">
              <el-date-picker type="datetime"
                              disabled
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择时间"
                              v-model="form.check_time"
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
            <el-form-item prop="check_status"
                          label="巡检地点状况">
              <el-select class="customSelectStyle"
                         v-model="form.check_status"
                         :popper-append-to-body="false"
                         style="width:100%;"
                         clearable
                         placeholder="请选择">
                <el-option v-for="item in stateOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安全巡检类型"
                          prop="check_type_id">
              <el-select class="customSelectStyle"
                         v-model="form.check_type_id"
                         :popper-append-to-body="false"
                         style="width:100%;"
                         clearable
                         placeholder="请选择">
                <el-option v-for="item in securityType0ptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="check_content"
                      label="描述问题">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    placeholder="请输入隐患内容"
                    v-model="form.check_content">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-upload class="upload-demo"
                     :action="action"
                     :headers="headers"
                     accept=".png,.jpg"
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
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { getSafetyArea, checktype } from "@/api/security/si/WorkTicketApi"
import { safeCheckInsert } from "@/api/security/si/inspection/index";
import { mapGetters } from "vuex";
import { dateFormat } from '@/utils/date'
import {getToken} from "@/utils/auth";
export default {
  data () {
    return {
      form: {
        check_time: dateFormat(new Date(), 'yyyy-MM-dd hh:mm:ss'),
        check_status: '',
        safe_area_id: '',
        check_type_id: '',
        //必传字段不传报错
        check_image1: '',
        check_image2: '',
        check_image3: '',
        check_image4: '',
      },
      // 表单校验
      rules: {
        // check_time: [{ required: true, message: '巡检时间不能为空', trigger: 'change' }],
        safe_area_id: [{ required: true, message: '作业区域不能为空', trigger: 'change' }],
        check_type_id: [{ required: true, message: '安全巡检类型不能为空', trigger: 'change' }],
        check_status: [{ required: true, message: '巡检地点状况不能为空', trigger: 'change' }],
        check_content: [{ required: true, message: '描述问题不能为空', trigger: 'blur' }],
      },
      //作业区域下拉
      operation0ptions: [],
      //状态下拉
      stateOptions: [
        {
          id: 1,
          name: "正常"
        },
        {
          id: 2,
          name: "异常"
        }
      ],
      //安全巡检类型列表
      securityType0ptions: [],
      //图片回显列表
      fileList: [],
      submitLoading: false,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      action: ''
    }
  },
  created () {
    this.getSafeArea()
    this.getChecktype()
    this.action = process.env.VUE_APP_BASE_API + '/file/file/upload'
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
        // this.form.safe_area_id = this.operation0ptions[0].id
      })
    },
    //获取安全巡检类型
    getChecktype () {
      checktype().then(res => {
        // console.log(res)
        this.securityType0ptions = res.data.data
        // this.form.check_type_id = this.securityType0ptions[0].id
      })
    },
    //赋值图片id的方法
    setImageID (fileList) {
      fileList.forEach((item, index) => {
        if (index == 0) {
          this.form.check_image1 = item.response.data.id
        }
        if (index == 1) {
          this.form.check_image2 = item.response.data.id
        }
        if (index == 2) {
          this.form.check_image3 = item.response.data.id
        }
        if (index == 3) {
          this.form.check_image4 = item.response.data.id
        }
      })
    },
    //文件上传成功时的钩子
    handleSuccess (response, file, fileList) {
      // console.log(fileList, "fileList")
      this.setImageID(fileList)
      // console.log(this.form.check_image1, this.form.check_image2, this.form.check_image3, this.form.check_image4)
    },
    //文件列表移除文件时的钩子
    handleRemove (file, fileList) {
      this.form.check_image1 = ''
      this.form.check_image2 = ''
      this.form.check_image3 = ''
      this.form.check_image4 = ''
      this.setImageID(fileList)
      // console.log(this.form.check_image1, this.form.check_image2, this.form.check_image3, this.form.check_image4)
    },
    //文件超出个数限制时的钩子
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 4 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //check_type_name（巡检类型名称）
          this.securityType0ptions.forEach(item => {
            if (item.id == this.form.check_type_id) {
              this.form.check_type_name = item.name
            }
          })
          //safe_area_name（区域名称）
          this.operation0ptions.forEach(item => {
            if (item.id == this.form.safe_area_id) {
              this.form.safe_area_name = item.name
            }
          })
          //巡检部门（当前单位）
          this.form.check_depart_id = this.$store.getters.userInfo.dept.deptId
          this.form.check_depart_name = this.$store.getters.userInfo.dept.deptName
          //巡检人（当前登录人）
          this.form.check_person_id = this.$store.getters.userInfo.userId;
          this.form.check_person_name = this.userInfo.userName;
          this.submitLoading = true
          // this.$emit('submitSave')
          safeCheckInsert(this.form).then(res => {
            this.submitLoading = false
            if (res.data.code === "0") {
              this.$message({ type: "success", message: res.data.msg });
              this.$emit('submitSave', res.data.msg)
            }
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
