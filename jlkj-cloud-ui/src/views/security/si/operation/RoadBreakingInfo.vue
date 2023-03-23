<template>
  <div>
    <el-tabs v-model="activeName" type="card" style="min-height:500px ">
      <el-tab-pane label="申请信息" name="application">
        <roadBreakingInfoApplication ref="application" :type="type" :data="data" @setTicketId="setTicketId" @setSubmitLoading="setSubmitLoading"
                                     @cancel="cancel"/>
      </el-tab-pane>
      <el-tab-pane label="安全措施" name="security">
        <roadBreakingInfoSecurity :type="type" :data="data" :ticketId="ticketId"/>
      </el-tab-pane>
      <el-tab-pane label="作业审批" name="task">
        <roadBreakingInfoTask :type="type" :data="data" :ticketId="ticketId" @approvalOver="approvalOver"/>
      </el-tab-pane>
      <el-tab-pane label="审批记录" name="record">
        <approvalRecord v-if="activeName==='record'" ref="record" :type="type" :data="data" :jobType="7"/>
      </el-tab-pane>
    </el-tabs>
    <div class="el-dialog__footer">
    <span slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <template v-if="type === 'edit'">
      <el-button type="primary" v-if="activeName==='application' && data.status !== 3 && !isApproval"
                 @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
      </template>
      <template v-else>
      <el-button type="primary" @click="handleChange('form')" v-if="activeName!=='security'" :loading="submitLoading">确 定</el-button>
      </template>
    </span>
    </div>
  </div>
</template>

<script>
  import roadBreakingInfoApplication from "./roadBreakingInfoApplication";
  import roadBreakingInfoSecurity from "./roadBreakingInfoSecurity";
  import roadBreakingInfoTask from "./roadBreakingInfoTask";
  import approvalRecord from "./common/approvalRecord";

  export default {
    name: "roadBreakingInfo",
    props: ['data', 'type'],
    components: {
      roadBreakingInfoApplication,
      roadBreakingInfoSecurity,
      roadBreakingInfoTask,
      approvalRecord,
    },
    data() {
      return {
        activeName: 'application',
        ticketId: '',
        form: {status: 0},
        submitLoading: false,
        isApproval: false,
      };
    },
    created() {
      if (!this.data.id) {
        this.data.status = 0;
      }
    },
    methods: {
      setTicketId(id) {
        this.ticketId = id;
      },
      setSubmitLoading(submitLoading){
        this.submitLoading = submitLoading;
      },
      handleChange() {
        this.$refs.application.saveMethod();
      },
      approvalOver() {
        this.isApproval = true;
        this.$refs.application.approvalOver();
      },
      cancel() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>

</style>
