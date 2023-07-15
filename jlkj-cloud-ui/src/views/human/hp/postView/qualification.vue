<template>
  <div v-if="visible">
    <!-- 添加或修改岗位信息数据维护对话框 -->
      <el-form ref="form" :model="form" :rules="rules" label-width="180px" width="900">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="岗位名称" prop="postName">
              <el-input v-model="form.postName" placeholder="请输入岗位名称" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="岗位编码" prop="postCode">
              <el-input v-model="form.postCode" placeholder="请输入岗位编码" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <div class="divFather">
          <div class="page3_div">
            <div class="page3_div_left">
              <label >
                任职资格
              </label>
            </div>
            <div class="page3_div_right">
              <div class="page3_div_right_child" style="height: 16%;">
                  <label style="margin-left:112px; ">年龄</label>
                  <el-input v-model="form.minAge" placeholder="" style="width: 80px;margin-left: 20px;margin-right: 10px;" type="number" maxlength="3" @blur="watchAge"/>
                  ——
                  <el-input v-model="form.maxAge" placeholder="" style="width: 80px;margin-left: 10px;"  type="number" maxlength="3" @blur="watchAge"/>

                  <label prop="gender" style="margin-left: 50px;">性别</label>
                  <el-radio-group v-model="form.gender" style="margin-left: 20px;">
                    <el-radio
                      v-for="dict in dict.type.sys_user_sex"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
              </div>
              <div class="page3_div_right_child" style="height: 55%;">
                <div class="page3_div_left" style="height: 100%;border-width: 0;border-right-width: 2px;">
                  <label>基本条件</label>
                </div>
                <div class="div_rightes">
                  <div class="divdiv" >
                    <label class="labellabel">最低学历</label>
                    <el-input v-model="form.recordId" placeholder="请输入最低学历" style="width: 500px;" maxlength="32" />
                  </div>
                  <div class="divdiv">
                    <label class="labellabel">专业要求</label>
                    <el-input v-model="form.speClaim"  placeholder="请输入内容" style="width: 500px;" maxlength="500" />
                  </div>
                  <div class="divdiv">
                    <label class="labellabel">职业资格</label>
                    <el-input v-model="form.proQua"  placeholder="请输入内容" style="width: 500px;" maxlength="500"/>
                  </div>
                  <div class="divdiv">
                    <label class="labellabel">工作经验</label>
                    <el-input v-model="form.workExp"  placeholder="请输入内容" style="width: 500px;" maxlength="500"/>
                  </div>
                  <div class="divdiv">
                    <label class="labellabel">其他要求</label>
                    <el-input v-model="form.otherClaim"  placeholder="请输入内容" style="width: 500px;" maxlength="500"/>
                  </div>
                </div>
              </div>
              <div class="page3_div_right_child" style="height: 30%;flex-direction: column; width: 100%;align-items: center;">
                <div style="display: flex;flex-direction: row;width: 100%; align-items: center;justify-content: flex-end;height: 50px;">
                  <label prop="knowTec" style="width: 100px;margin-right: 40px;">知识与技能</label>
                  <el-input v-model="form.knowTec"  placeholder="请输入内容" style="width: 500px;margin-right: 15px" maxlength="500"/>
                </div>
                <div style="display: flex;flex-direction: row;width: 100%; align-items: center;justify-content: flex-end;height: 50px;">
                  <label prop="proAct" style="width: 100px;margin-right: 40px;">职业化行为</label>
                  <el-input v-model="form.proAct"  placeholder="请输入内容" style="width: 500px;margin-right: 15px" maxlength="500" />
                </div>
              </div>

            </div>
          </div>
        </div>

      </el-form>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { listPostMaintenance, getPostMaintenance, addPostMaintenance, updatePostMaintenance } from "@/api/human/hp/postMaintenance";
export default {
  name: 'qualification',
  dicts: ['sys_user_sex'],
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
    watchAge(){
      if(this.form.maxAge!==null&&this.form.minAge!==null){
        if(this.form.minAge>=this.form.maxAge){
          this.$modal.msgError("最大年龄应大于最小年龄")
          this.form.minAge=1;
          this.form.maxAge=99
        }
      }
      if(this.form.minAge>99){
        this.form.minAge=99
      }
      if(this.form.minAge<1){
        this.form.minAge=1
      }
      if(this.form.maxAge>99){
        this.form.maxAge=99
      }
      if(this.form.maxAge<1){
        this.form.maxAge=1
      }
    },
    init(form) {
      this.form=form;
      this.visible = true;
    },
  }
}
</script>

<style scoped>
.divFather{
  display: flex;
  justify-content: flex-end;
  width: 100%
}
.page3_div{
  background-color: #f7f7f7;
  width: 90%;
  height: 500px;
  display: flex;
  flex-direction:row;

}
.page3_div_left{
  display: flex;
  flex-direction:row;
  justify-content:center;
  align-items: center;
  width: 100px;
  border-style: solid;
  border-color: #FFFFFF;
  border-width:3px ;
}
.page3_div_right{
  display: flex;
  flex-direction:column;
  /*justify-content:  flex-end;*/
  /*align-items: center;*/
  width:100%;

}
.page3_div_right_child{
  display: flex;
  flex-direction: row;
  border-color: #FFFFFF;
  border-style: solid;
  border-width: 1px;
  align-items: center;
  justify-content: flex-start;

}
.divdiv{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
  margin-right: 15px;
  width: 100%;
  height: 50px;
}
.labellabel{
  width: 80px;
  margin-left: 10px;
  margin-right: 10px;
}
.div_rightes{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: flex-end;
  width: 100%;
}
</style>
