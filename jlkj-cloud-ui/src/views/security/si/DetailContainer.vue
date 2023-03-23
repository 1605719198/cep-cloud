<template>

  <div>
    <el-tabs v-model="activeName" type="card" style="min-height:500px " >
      <el-tab-pane label="上报信息" >
        <Upperinformation ref="upper"/>
      </el-tab-pane>
      <el-tab-pane label="整改信息">
        <Correctioninformation/>
      </el-tab-pane>
      <el-tab-pane label="验收信息">
        <Checkinformation/>
      </el-tab-pane>
    </el-tabs>
    <div class="el-dialog__footer">
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleChange('form')">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </span>
    </div>
  </div>
</template>

<script>
import Checkinformation from "@/views/security/si/Checkinformation";
import Upperinformation from "@/views/security/si/Upperinformation";
import Correctioninformation from "@/views/security/si/Correctioninformation";
export default {
  components:{
    Correctioninformation,
    Upperinformation,
    Checkinformation,
  },
  name: "DetailContainer",
  props:['data'],
  data(){
    return {
      activeName: 'application',
      props: {
        multiple: true,
      },
      containerConfig:{
        from : this.data.from
      }
    }
  },
  methods:{
    confirm(){

    },
    handleChange(form) {
      this.$refs.application.saveMethod()
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
