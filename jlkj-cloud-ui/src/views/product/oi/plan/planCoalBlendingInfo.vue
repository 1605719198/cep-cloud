<template>
  <div class="avue-crud">
    <div>
      <div class="title">
        <label>基本信息</label>
      </div>
      <div class="content">
        <el-row>
          <el-col :span="12"><label style="width: 100px">计划编号：</label><div>{{data.plan_number}}</div></el-col>
          <el-col :span="12"><label style="width: 100px">接收人员：</label><div>{{data.receive_user_name}}</div></el-col>
          <el-col :span="12"><label style="width: 100px">开始时间：</label><div>{{data.plan_start_time}}</div></el-col>
          <el-col :span="12"><label style="width: 100px">结束时间：</label><div>{{data.plan_end_time}}</div></el-col>
<!--          <el-col :span="12"><label style="width: 100px">储煤塔号：</label><div>{{data.tower_number}}</div></el-col>-->
          <el-col :span="12"><label style="width: 100px">焦炭等级：</label><div>{{data.material_name}}</div></el-col>
        </el-row>
      </div>
      <div class="title">
        <label>详细信息</label>
      </div>
      <div class="tableContent">
        <el-table v-loading="table.loading" :data="tableData" stripe style="width: 100%">
          <el-table-column prop="warehouse_number" width="100" label="配煤仓号"/>
          <el-table-column prop="coal_type_name" label="煤种"/>
          <el-table-column prop="materials_small_name" label="小煤种">
            <template slot-scope="scope">
              <span>{{scope.row.materials_small_name+'_'+scope.row.materials_small_code}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="proportion" width="150" sortable label="配置比例 %"/>
        </el-table>
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
  import {req} from "@/api/production/oi/common";
  export default {
    name: "planCoalBlendingInfo",
    props: ['data'],
    data() {
      return {
        page: {size: 10000, current: 1, total: 1, order: "create_time", orderby: "asc",},
        query: {planId:this.data.id},
        table: {border: true, loading: true,},
        tableData: [],
      }
    },
    created() {
      this.onLoad();
    },
    methods:{
      //载入数据
      onLoad() {
        this.table.loading = true;//加载状态
        let data = {...this.page, ...this.query};
        req('get', 'listProductionPlanCfgCokeDitail', data).then(res => {
          this.table.loading = false;
          this.tableData = res.data.records;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      },
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
    line-height: 35px;
  }

  .content .el-col div {
    margin-left: 105px;
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
