<template>
  <div>
    <div v-if="isAction" style="text-align: right;padding:0 40px 0 0;" >
      <el-button type="primary" @click="addAnalysis">新增采样分析</el-button>
    </div>
    <el-table size="small" :data="tableData" stripe  v-loading="loading">
      <el-table-column
        prop="oxygen_content"
        label="含氧量（%）"
        min-width="50px"
        align="center"/>

      <el-table-column
        prop="combustible_gas"
        label="可燃气体（%LEL）"
        min-width="100px"
        align="center"/>

      <el-table-column
        prop="toxic_medium"
        label="有毒气体（PPM）"
        min-width="80px"
        align="center"/>

      <el-table-column
        prop="person_name"
        label="分析人"
        min-width="60px"
        align="center"/>

      <el-table-column
        prop="analysis_time"
        label="采样分析时间"
        min-width="65px"
        align="center"/>

      <el-table-column
        prop="操作"
        label="操作"
        min-width="80px"
        fixed="right"
        align="center">
        <template  slot-scope="scope">
          <el-button v-if="isAction" size="mini" plain icon="el-icon-minus" type="text" @click="deleteAnalysis(scope.$index, tableData)"/>

          <el-button v-else size="mini" disabled icon="el-icon-minus" type="info"/>

        </template>
      </el-table-column>
    </el-table>

    <div v-if="dialog.visibility">
      <el-dialog title="添加采样分析" :visible.sync="dialog.visibility" width="600px"
                 append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <AddAnalysisDialog @close="dialog.visibility=false" @get-worker-info="receiveAnalysisInfo"/>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {spaceAnalysisList, spaceDeleteAnalysis, spaceInsertAnalysis} from "@/api/security/si/ConfinedSpaceApi";
import AddAnalysisDialog from "@/views/security/si/operation/AddAnalysisDialog";

export default {
  name: "AnalysisComponent",
  components: {AddAnalysisDialog},
  props:['id'],
  data(){
    return {
      self:{
        id : this.$store.getters.userInfo.userId,
        name: this.$store.getters.userInfo.nickName,
        departmentName: this.$store.getters.userInfo.dept.deptName,
        departmentID : this.$store.getters.userInfo.alternateField14
      },
      tableData:[],
      isAction:true ,
      loading:false,
      dialog:{
        visibility:false
      },
      currTicketID: '',
    }
  },
  methods:{
    addAnalysis(){
      if (this.currTicketID === undefined || this.currTicketID === ''){
        this.$message.error("请先保存作业票");
        return
      }
      this.dialog.visibility = true ;
    },
    deleteAnalysis(index, tableData){
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let row = tableData[index];
        spaceDeleteAnalysis({'id':row.id}).then(response => {
          if (response.data.code ==='0' && response.data.msg ==='分析数据删除成功'){
            tableData.splice(index, 1);
            this.$message({
              message: response.data.msg,
              type: 'success'
            });
          }else {
            this.$message.error(response.data.msg);
          }
        }, error => {
          window.console.log(error);
          this.$message.error('分析数据删除失败');
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //通过ID获取采样分析的列表
    setAnalysisListByID(id, isCopy = false){
      this.currTicketID = id ;
      if (!isCopy){
        spaceAnalysisList(id).then(response => {
          this.tableData = response.data.data ;
        }, error =>{
          window.console.log(error);
        });
      }
    },
    //接收到弹窗发来的分析数据
    receiveAnalysisInfo(data){
      this.dialog.visibility = false ;
      let params = {...data};
      params['job_id'] = this.currTicketID ;
      params['position'] = '';
      this.saveAnalysis(params) ;
    },
    //保存采样分析
    saveAnalysis(data){
      spaceInsertAnalysis(data).then(response => {
        if (response.data.code === '0' && response.data.msg === '分析数据保存成功'){
          this.tableData.push(data);
          if (this.tableData.length === 1){
            this.$EventBus.$emit('event-refresh-space-start-time');
          }
          this.$message({
            message: response.data.msg,
            type: 'success'
          });
        }
      }, error => {
        this.$message.error("分析数据保存失败!");
        window.console.log(error);
      })
    },

    setTicketID(ticketID){
      this.currTicketID = ticketID ;
      this.isAction = true ;
    },

  },
  created() {
    this.currTicketID = this.id ;
   // this.isAction = this.currTicketID !== undefined && this.currTicketID !== '';
    this.$EventBus.$on('event-analysis-component-is-had', ()=>{
      this.$EventBus.$emit('event-analysis-data-is-had', this.tableData.length > 0);
    });
  },
  beforeDestroy() {
    this.$EventBus.$off('event-analysis-component-is-had');
    this.$EventBus.$off('event-analysis-data-is-had');
  }
}
</script>

<style scoped>

</style>
