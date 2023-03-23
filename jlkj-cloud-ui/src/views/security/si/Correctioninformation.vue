<template>
  <div class="avue-crud">
    <el-row>
      <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="90px">
        <el-col span="12">
          <el-form-item label="整改人员" prop="reported_security_people" >
            <el-input v-model="addForm.reported_security_people" ></el-input>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="整改时间" prop="reported_correction_people">
            <el-input v-model="addForm.reported_correction_people" placeholder="2022.5.6"></el-input>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="隐患内容" prop="hidden_description">
            <el-input type="textarea"
                      v-model="addForm.hidden_description"
                      maxlength="100"
                      :rows="6"
                      show-word-limit
                      />
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="整改措施" prop="load_picture">
            <el-input type="textarea"
                      v-model="addForm.load_picture"
                      maxlength="100"
                      :rows="6"
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
  name:"Correctioninformation",
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
