<template>
  <div class="avue-crud" style="min-height: 400px;margin-bottom: 20px;">
    <el-form :model="form" ref="form" label-position="left" label-width="80px">
      <el-table size="small" :data="tableData" stripe>
        <el-table-column prop="sort" label="序号" width="50"/>
        <el-table-column prop="approval_name" label="审批环节"/>
        <el-table-column prop="content" label="意见" width="130px"/>
        <el-table-column prop="approval_person_name" label="审批人" width="70"/>
        <el-table-column prop="approval_time" label="审批时间" width="130px"/>
        <el-table-column prop="is_pass" label="是否通过" width="90px">
          <template slot-scope="scope">
            {{scope.row.is_pass === 1 ? '通过':'退回'}}
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";

  export default {
    name: "approvalRecord",
    props: ['data', 'type', 'jobType'],
    data() {
      return {
        tableData: [], form: {},
      };
    },
    created() {
      if(this.data.id){
        this.onLoad();
      }
    },
    methods: {
      onLoad() {
        req('get', 'safeJobApprovalRecord/list', {jobId: this.data.id, jobType: this.jobType}).then(res => {
          this.tableData = res.data.data;//表格数据
        }, error => {
          window.console.log(error);
        });

      }
    },
  }
</script>

<style scoped>

</style>
