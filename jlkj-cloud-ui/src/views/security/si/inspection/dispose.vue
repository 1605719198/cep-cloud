<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item label="处理时间"
                      prop="check_time">
          <el-date-picker type="datetime"
                          disabled
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择时间"
                          v-model="form.check_time"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="disposer_name"
                      label="处理人员">
          <el-input v-model="form.disposer_name"
                    :readonly="true"
                    placeholder="请选处理人"
                    @focus="dialogVisible = true"></el-input>
        </el-form-item>
        <personnel-selection v-if="dialogVisible"
                             :userDialogVisible="dialogVisible"
                             @close-dialog="dialogVisible = false"
                             :isMultiple="false"
                             :height="500"
                             @single-select="handleSingleSelect"></personnel-selection>
        <el-form-item prop="dispose_content"
                      label="处理结果">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    placeholder="请输入处理结果"
                    v-model="form.dispose_content">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-upload class="upload-demo"
                     :action="action"
                     :headers="headers"
                     accept=".png,.jpg"
                     multiple
                     :limit="3"
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
import { safeCheckDispose } from "@/api/security/si/inspection/index";
import { mapGetters } from "vuex";
import { dateFormat } from '@/utils/date'
export default {
  props: ['safeCheckId'],
  data () {
    return {
      form: {
        check_time: dateFormat(new Date(), 'yyyy-MM-dd hh:mm:ss'),
        disposer_name: '',
        //必传字段不传报错
        disposer_image1: '',
        disposer_image2: '',
        disposer_image3: '',
      },
      // 表单校验
      rules: {
        dispose_content: [{ required: true, message: '处理结果不能为空', trigger: 'blur' }],
        disposer_name: [{ required: true, message: '处理人不能为空', trigger: 'change' }]
      },
      dialogVisible: false,
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
    // console.log(this.safeCheckId)
    this.action = process.env.VUE_APP_BASE_API + '/file/file/upload'
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //单选回调方法
    handleSingleSelect (val) {
      this.form.disposer_id = val.id
      this.form.disposer_name = val.full_name
    },
    //赋值图片id的方法
    setImageID (fileList) {
      fileList.forEach((item, index) => {
        if (index == 0) {
          this.form.disposer_image1 = item.response.data.id
        }
        if (index == 1) {
          this.form.disposer_image2 = item.response.data.id
        }
        if (index == 2) {
          this.form.disposer_image3 = item.response.data.id
        }
      })
    },
    //文件上传成功时的钩子
    handleSuccess (response, file, fileList) {
      // console.log(fileList, "fileList")
      this.setImageID(fileList)
      // console.log(this.form.disposer_image1, this.form.disposer_image2, this.form.disposer_image3, this.form.check_image4)
    },
    //文件列表移除文件时的钩子
    handleRemove (file, fileList) {
      this.form.disposer_image1 = ''
      this.form.disposer_image2 = ''
      this.form.disposer_image3 = ''
      this.setImageID(fileList)
      // console.log(this.form.disposer_image1, this.form.disposer_image2, this.form.disposer_image3, this.form.check_image4)
    },
    //文件超出个数限制时的钩子
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.safe_check_id = this.safeCheckId
          this.submitLoading = true
          // this.$emit('submitSave')
          safeCheckDispose(this.form).then(res => {
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
