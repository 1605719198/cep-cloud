<template>
  <div v-if="visible">
    <!-- 添加或修改岗位信息数据维护对话框 -->
      <el-form ref="form" :model="form" :rules="rules" label-width="180px" width="900">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="岗位名称" prop="postName">
              <el-input v-model="form.postName" placeholder="请输入岗位名称" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="岗位编码" prop="postCode">
              <el-input v-model="form.postCode" placeholder="请输入岗位编码" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="岗位工作概述" prop="workDesc">
          <el-input v-model="form.workDesc" type="textarea" show-word-limit   placeholder="请输入内容" maxlength="500" />
        </el-form-item>
        <el-form-item label="岗位职责内容" prop="workCon">
          <el-input v-model="form.workCon" type="textarea" show-word-limit   placeholder="请输入内容" maxlength="500" />
        </el-form-item>


          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="考核指标" prop="apprKpi">
                <el-input v-model="form.apprKpi" type="textarea"  show-word-limit  placeholder="请输入考核指标" maxlength="500" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="检讨指标" prop="reviewKpi">
                <el-input v-model="form.reviewKpi" type="textarea"  show-word-limit  placeholder="请输入检讨指标" maxlength="500" />
              </el-form-item>
            </el-col>
          </el-row>



        <el-form-item label="工作标准" prop="workSta">
          <el-input v-model="form.workSta" type="textarea" show-word-limit   placeholder="请输入内容" maxlength="500" />
        </el-form-item>

        <el-form-item label="工作权限" prop="workAut">
          <el-input v-model="form.workAut" placeholder="请输入工作权限" maxlength="200" />
        </el-form-item>
        <el-form-item label="工作环境" prop="workEnv">
          <el-input v-model="form.workEnv" placeholder="请输入工作环境" maxlength="200"/>
        </el-form-item>
        <el-form-item label="操作设备" prop="opeEquHp">
          <el-input v-model="form.opeEquHp" placeholder="请输入操作设备" maxlength="200" />
        </el-form-item>
        <el-form-item label="使用工具" prop="toolUsed">
          <el-input v-model="form.toolUsed" placeholder="请输入使用工具" maxlength="200" />
        </el-form-item>
        <el-form-item label="办公用品" prop="officeEqu">
          <el-input v-model="form.officeEqu" placeholder="请输入办公用品" maxlength="200" />
        </el-form-item>
        <el-form-item label="劳保标准" prop="labourSta">
          <el-input v-model="form.labourSta" placeholder="请输入劳保标准" maxlength="200"/>
        </el-form-item>

      </el-form>
  </div>
</template>

<script>
import { listPostMaintenance, getPostMaintenance, addPostMaintenance, updatePostMaintenance } from "@/api/human/hp/postMaintenance";
export default {
  name: 'dutp',
  dicts: ['post_sequence_id', 'speciality_type_id', 'employment_nature_id', 'work_address_id', 'work_address', 'job_title_id', 'sys_normal_disable', 'post_type_id' ,'comp_id'],
  data(){
    return{
      //标题
      title:'新增或修改岗位信息',
      //dialog是否显示
      visible:false,
      //表单参数
      form: {},
      // 表单校验
      rules: {

      }
    }
  },
  methods:{
    init(form) {
      this.form=form;
      this.visible = true;
      // queryRelations(id).then(response => {
      //   this.relationsForm = response.data
      //   this.flagForm={}
      //   this.relationsForm.map(item => {
      //     if (item.relation === "FC") {
      //       this.flagForm.FC = item.relation
      //       this.$nextTick(() => {
      //         this.$refs.queryFc.initFc(id,manufacturerChineseName)
      //       })
      //     }else if (item.relation === "MP"){
      //       this.flagForm.MP = item.relation
      //       this.$nextTick(() => {
      //         this.$refs.queryMp.initMP(id,manufacturerChineseName)
      //       })
      //     }else if (item.relation === "ST"){
      //       this.flagForm.ST = item.relation
      //       this.$nextTick(() => {
      //         this.$refs.querySt.initSt(id,manufacturerChineseName)
      //       })
      //     }else if (item.relation === "SO"){
      //       this.flagForm.SO = item.relation
      //       this.$nextTick(() => {
      //         this.$refs.querySo.initSo(id,manufacturerChineseName)
      //       })
      //     }
      //   })
      // });
      // getBase1(id).then(response => {
      //   this.dataForm = response.data
      //   console.log(response.data);
      // });
    },
  }
}
</script>

<style scoped>

</style>
