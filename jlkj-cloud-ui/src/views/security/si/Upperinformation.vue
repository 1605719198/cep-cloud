<template>
  <div class="avue-crud">
  <el-row>
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="90px">
      <el-col span="12">
        <el-form-item label="上报单位" prop="reported_department">
          <el-input v-model="addForm.reported_department" placeholder="新泰科" readonly></el-input>
        </el-form-item>
      </el-col>
      <el-col span="12">
        <el-form-item label="上报人员" prop="reported_people">
          <el-input v-model="addForm.reported_people" placeholder="李华" readonly></el-input>
        </el-form-item>
      </el-col>
      <el-col span="12">
        <el-form-item label="上报时间" prop="reported_date">
          <el-input v-model="addForm.reported_date" placeholder="2020.3.20" readonly></el-input>
        </el-form-item>
      </el-col>
      <el-col span="12">
        <el-form-item label="作业区域" prop="reported_area">
          <el-input v-model="addForm.reported_area" placeholder="鼓冷" readonly></el-input>
        </el-form-item>
      </el-col>
        <el-col span="12">
          <el-form-item label="隐患等级" prop="reported_level">
            <el-input v-model="addForm.reported_level" placeholder="一般隐患" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="完成期限" prop="reported_finish">
            <el-input v-model="addForm.reported_finish" placeholder="2020.4.10" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="责任单位" prop="reported_duty_department">
            <el-input v-model="addForm.reported_duty_department" placeholder="维修车间" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="责任人" prop="reported_duty_people">
            <el-input v-model="addForm.reported_duty_people" placeholder="小白" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="单位/级别" prop="reported_rate">
            <el-input v-model="addForm.reported_rate" placeholder="新泰科" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="安全人员" prop="reported_security_people" >
            <el-input v-model="reported_security_people" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="验收人" prop="reported_correction_people">
            <el-input v-model="addForm.reported_correction_people" placeholder="李雷" readonly></el-input>
          </el-form-item>
        </el-col>
      <el-col>
        <el-form-item label="隐患内容" prop="hidden_description">
          <el-input type="textarea"
                    v-model="addForm.hidden_description"
                    maxlength="100"
                    :rows="6"
                    show-word-limit
                    readonly/>
        </el-form-item>
      </el-col>
      <el-col>
        <el-form-item label="上传图片" prop="load_picture">
          <el-input type="textarea"
                    v-model="addForm.load_picture"
                    maxlength="100"
                    :rows="6"
                    show-word-limit
                    readonly/>
        </el-form-item>
      </el-col>
    </el-form>
  </el-row>
  </div>

</template>
<script>
export default{
  name:"Upperinformation",
  data(){
    return{
      addForm: {
        reported_department: "",
        reported_people: "",
        reported_date: "",
        reported_area: "",
        reported_level: "",
        reported_finish: "",
        reported_duty_department: "",
        reported_duty_people: "",
        reported_rate: "",
        reported_security_people: "",
        reported_correction_people: "",
        hidden_description: "",
        load_picture: "",
      },
    }

  },
  methods:{

  }

}
</script>
<style>

</style>
