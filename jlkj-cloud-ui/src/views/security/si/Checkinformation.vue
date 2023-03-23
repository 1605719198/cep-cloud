<template>
  <div class="avue-crud">
    <el-row>
      <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="90px">
        <el-col span="12">
          <el-form-item label="验收人员" prop="reported_security_people" >
            <el-input v-model="addForm.reported_security_people" placeholder="刘三"></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="验收时间" prop="reported_correction_people">
            <el-input v-model="addForm.reported_correction_people" placeholder="2022.3.26"  ></el-input>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="验收评价" prop="hidden_description">
            <el-input type="textarea"
                      v-model="addForm.hidden_description"
                      maxlength="100"
                      :rows="5"
                      show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="验收图片" prop="load_picture">
            <el-input type="textarea"
                      v-model="addForm.load_picture"
                      maxlength="100"
                      :rows="5"
                      show-word-limit
            />
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>

</template>
<script>
export default{
  name:"Checkinformation",
  data(){
    return{
      addForm: {

      },
    }

  },
  methods:{

  }

}
</script>
<style>

</style>
