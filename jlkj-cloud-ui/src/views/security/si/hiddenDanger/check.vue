<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               :disabled="formData.type=='view'"
               label-width="100px">
        <el-row :gutter="50"
                v-if="formData.type!='check'">
          <el-col :span="12">
            <el-form-item prop="acceptance_person_id"
                          label="验收人">
              <el-input v-model="form.acceptance_person_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="acceptance_time"
                          label="验收时间">
              <el-input v-model="form.acceptance_time"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="acceptance_appraise"
                      label="验收评价">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    v-model="form.acceptance_appraise">
          </el-input>
        </el-form-item>
        <el-form-item v-if="formData.type=='check'">
          <el-upload class="upload-demo"
                     :action="action"
                     accept=".png,.jpg"
                     multiple
                     :limit="4"
                     :on-exceed="handleExceed"
                     :on-remove="handleRemove"
                     :on-success="handleSuccess"
                     :file-list="fileList"
                     list-type="picture">
            <el-button size="small"
                       type="primary">点击上传验收图片</el-button>
            <div slot="tip"
                 class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item v-else
                      label="验收图片">
          <el-image v-for="(item,index) in srcList"
                    :key="index"
                    :z-index="10000"
                    style="width: 100px; height: 100px;margin-right:10px;"
                    :src="item"
                    :preview-src-list="srcList">
          </el-image>
        </el-form-item>
      </el-form>
      <div style="width:100%;text-align: right;">
        <el-button @click="cancel">取消</el-button>
        <el-button v-if="formData.type=='check'"
                   :loading="submitLoading"
                   type="primary"
                   @click="submitForm('form')">确定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { dangerAcceptance } from "@/api/security/si/hiddenDanger/index";
export default {
  inject: ['fatherClose', 'fatherSubmitSave'],
  props: {
    formData: {
      type: Object
    }
  },
  data () {
    return {
      form: {},
      // 表单校验
      rules: {
        acceptance_appraise: [{ required: true, message: '验收评价不能为空', trigger: 'blur' }],
      },
      fileList: [],
      srcList: [],
      submitLoading: false,
      action: ''
    }
  },
  created () {
    this.action = this.GLOBAL.fileUrl + '/file/upload'
    this.form = { ...this.formData }
    if (this.form.acceptance_image1) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.acceptance_image1)
    }
    if (this.form.acceptance_image2) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.acceptance_image2)
    }
    if (this.form.acceptance_image3) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.acceptance_image3)
    }
    if (this.form.acceptance_image4) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.acceptance_image4)
    }
    // console.log(this.srcList)
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //赋值图片id的方法
    setImageID (fileList) {
      fileList.forEach((item, index) => {
        if (index == 0) {
          this.form.acceptance_image1 = item.response.data.id
        }
        if (index == 1) {
          this.form.acceptance_image2 = item.response.data.id
        }
        if (index == 2) {
          this.form.acceptance_image3 = item.response.data.id
        }
        if (index == 3) {
          this.form.acceptance_image4 = item.response.data.id
        }
      })
    },
    //文件上传成功时的钩子
    handleSuccess (response, file, fileList) {
      // console.log(fileList, "fileList")
      this.setImageID(fileList)
      // console.log(this.form.acceptance_image1, this.form.acceptance_image2, this.form.acceptance_image3, this.form.acceptance_image4)
    },
    //文件列表移除文件时的钩子
    handleRemove (file, fileList) {
      this.form.acceptance_image1 = ''
      this.form.acceptance_image2 = ''
      this.form.acceptance_image3 = ''
      this.form.acceptance_image4 = ''
      this.setImageID(fileList)
      // console.log(this.form.acceptance_image1, this.form.acceptance_image2, this.form.acceptance_image3, this.form.acceptance_image4)
    },
    //文件超出个数限制时的钩子
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 4 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // this.form.disposer_id = this.$store.getters.userInfo.userId;
          // this.form.disposer_name = this.userInfo.userName;
          this.form.acceptance_depart_id = this.$store.getters.userInfo.dept.deptId
          this.form.acceptance_depart_name = this.$store.getters.userInfo.dept.deptName
          this.submitLoading = true
          dangerAcceptance(this.form).then(res => {
            if (res.data.code === "0") {
              this.$message({ type: "success", message: res.data.msg });
              this.fatherSubmitSave()
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
      this.fatherClose()
    }
  }
}
</script>

<style>
</style>
