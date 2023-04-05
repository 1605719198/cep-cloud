<template>
  <div class="avue-crud">
    <div>
      <div class="title">
        <label>基本信息</label>
      </div>
      <div class="content">
        <el-row :gutter="20">
          <el-col :span="12"><label style="width: 120px">计划编号：</label><div>&nbsp;{{data.plan_code}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">作业区：</label><div>&nbsp;{{data.organization_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">开始时间：</label><div>&nbsp;{{data.plan_start_date}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">结束时间：</label><div>&nbsp;{{data.plan_end_date}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">主体责任人：</label><div>&nbsp;{{data.person_main_liable_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">专业责任人：</label><div>&nbsp;{{data.person_profession_liable_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">设备名称：</label><div>&nbsp;{{data.equipment_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">维修类型：</label><div>&nbsp;{{repairTypeList[data.repair_type_id]}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">是否影响产量：</label><div>&nbsp;{{data.is_affect_output === 1 ? '影响':'不影响'}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">预提醒天数：</label><div>&nbsp;{{data.remind_days}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">创建人：</label><div>&nbsp;{{data.create_user_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">创建时间：</label><div>&nbsp;{{data.create_time}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">修改人：</label><div>&nbsp;{{data.modify_user_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">修改时间：</label><div>&nbsp;{{data.modify_time}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">维修内容：</label><div>&nbsp;{{data.repair_content}}</div></el-col>

        </el-row>
      </div>
      <div class="title">
        <label>计划执行信息</label>
      </div>
      <div class="content">
        <el-row :gutter="20">
          <el-col :span="12"><label style="width: 120px">计划执行状态：</label><div>&nbsp;{{data.plan_state}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">计划完成操作人：</label><div>&nbsp;{{data.complete_user_name}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">实际开始时间：</label><div>&nbsp;{{data.real_start_date}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">实际结束时间：</label><div>&nbsp;{{data.real_end_date}}</div></el-col>
          <el-col :span="12"><label style="width: 120px">计划完成备注：</label><div>&nbsp;{{data.remark}}</div></el-col>
        </el-row>
      </div>
    </div>
    <div class="el-dialog__footer" style="padding:20px 0 0 0;">
    <span slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </span>
    </div>

  </div>
</template>

<script>

  export default {
    name: "planRepairInfo",
    props: ['data'],
    data() {
      return {
        repairTypeList: ['','大修','中修','小修','临修','设备消缺','工艺技改','零星修缮','环保项目'],
      }
    },
    methods:{
      cancel() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>
  .title {
    border-left: 10px solid #409EFF;
    margin: 20px 0;
  }

  .title label {
    margin-left: 15px;
    font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
    font-weight: 700;
    text-decoration: none;
  }

  .content {
    padding-left: 25px;
    line-height: 35px;
    font-size: 14px;
  }

  .content label {
    float: left;
    text-align: right;
    font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
    font-weight: 700;
    font-style: normal;
    color: rgb(153, 153, 153);
    line-height: 34px;
  }

  .content .el-col div {
    margin-left: 125px;
    font-family: 微软雅黑;
    font-style: normal;
    text-align: left;
    font-size: 15px;
  }

  .content .el-col {
    /*margin: 10px 0;*/
  }

  .tableContent {
    border: 1px solid #ebeef5;
    border-bottom: unset;
  }
</style>
