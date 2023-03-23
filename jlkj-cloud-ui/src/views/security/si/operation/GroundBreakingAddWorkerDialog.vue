<template>
  <div>
    <el-form :model="mForm" status-icon :rules="rules" ref="mForm" class="demo-ruleForm" label-position="left"
             :hide-required-asterisk="true">
      <el-row>
        <el-col>
          <el-form-item label="作业人" label-width="100px" prop="worker">
            <el-input size="small" placeholder="作业人" v-model="mForm.worker"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="所属单位名称" label-width="100px" prop="unitName">
            <el-input size="small" placeholder="所属单位名称" v-model="mForm.unitName"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="所属单位类型" label-width="100px" prop="unitType">
            <el-input size="small" placeholder="所属单位类型" v-model="mForm.unitType"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="特种工作工种" label-width="100px" prop="workType">
            <el-input size="small" placeholder="特种工作工种" v-model="mForm.workType"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="特种工作证号" label-width="100px" prop="workNumber">
            <el-input size="small" placeholder="特种工作证号" v-model="mForm.workNumber"/>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <div class="el-dialog__footer" style="padding:20px 0 0 0;">
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancelClick">取 消</el-button>
        <el-button type="primary" @click="confirmEditorData" size="small">添 加</el-button>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "RoadBreakingAddWorkerDialog",
  data() {
    return {
      mForm: {
        worker: '',
        unitName: '',
        unitType: '',
        workType: '',
        workNumber: '',
      },
      rules: [],

    }
  },
  methods:{
    confirmEditorData(){
        this.getWorkerInfo(this.mForm);
    },

    cancelClick(){
      this.$emit('close');
    },

    getWorkerInfo(data){
      this.$emit('get-worker-info', data);
    }
  }
}
</script>

<style scoped>
.el-form-item {
  margin-bottom: 17px!important;
}
</style>
