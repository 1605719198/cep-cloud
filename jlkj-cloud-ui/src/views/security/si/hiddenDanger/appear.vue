<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               disabled
               :model="form"
               :rules="rules"
               label-width="100px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="submit_depart_name"
                          label="上报单位">
              <el-input v-model="form.submit_depart_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="submit_person_id"
                          label="上报人">
              <el-input v-model="form.submit_person_name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="限期完成"
                          prop="danger_deadline_time">
              <el-input v-model="form.danger_deadline_time"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="作业区域"
                          prop="safe_area_id">
              <el-input v-model="form.safe_area_name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="责任单位"
                          prop="duty_depart_id">
              <el-input v-model="form.duty_depart_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="duty_person_id"
                          label="责任人">
              <el-input v-model="form.duty_person_name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="隐患等级"
                          prop="danger_type_id">
              <el-input v-model="form.danger_type_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="acceptance_person_id"
                          label="验收人">
              <el-input v-model="form.acceptance_person_name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="danger_content"
                      label="隐患内容">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    v-model="form.danger_content">
          </el-input>
        </el-form-item>
        <el-form-item prop="dispose_measure"
                      label="整改措施">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    v-model="form.dispose_measure">
          </el-input>
        </el-form-item>
        <el-form-item label="上报图片">
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
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  inject: ['fatherClose'],
  props: {
    formData: {
      type: Object
    }
  },
  data () {
    return {
      form: {},
      // 表单校验
      rules: {},
      srcList: []
    }
  },
  created () {
    this.form = this.formData
    this.form.submit_depart_name = this.$store.getters.userInfo.dept.deptName
    this.form.submit_depart_id = this.$store.getters.userInfo.dept.deptId
    // console.log(this.$store.getters.userInfo.dept.deptId, this.$store.getters.userInfo.dept.deptName)
    // console.log(this.danger_id)
    // console.log(this.formData, "formData")
    if (this.form.submit_image1) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.submit_image1)
    }
    if (this.form.submit_image2) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.submit_image2)
    }
    if (this.form.submit_image3) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.submit_image3)
    }
    if (this.form.submit_image4) {
      this.srcList.push(this.GLOBAL.serveAddress + this.form.submit_image4)
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    cancel () {
      this.fatherClose()
    }
  }
}
</script>

<style>
</style>
