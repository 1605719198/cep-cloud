<template>
  <div>
    <div v-if="isAction" style="text-align: right;padding:0 40px 0 0;" >
      <el-button type="primary" @click="addWorker">新增作业人</el-button>
    </div>
    <el-table size="small" :data="tableData" stripe  v-loading="loading">
      <el-table-column
        prop="person_name"
        label="作业人"
        min-width="40px"
        align="center"/>

      <el-table-column
        prop="construction_unit_name"
        label="所属单位名称"
        min-width="100px"
        align="center"/>

      <el-table-column
        prop="type"
        label="所属单位类型"
        min-width="80px"
        align="center">
        <template scope="scope">
          <span>{{scope.row.type === 1 ? '本厂' : '外委'}}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="work_type_name"
        label="作业类型"
        min-width="60px"
        align="center"/>

      <el-table-column
        prop="work_card_code"
        label="作业证编号"
        min-width="65px"
        align="center"/>

      <el-table-column
        v-if="isSetCommander"
        prop="person_type"
        label="是否指挥"
        min-width="60px"
        align="center">
        <template scope="scope">
          <span>{{scope.row.person_type === 0 ? '否' : '是'}}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="操作"
        label="操作"
        min-width="80px"
        fixed="right"
        align="center">
        <template  slot-scope="scope">
          <el-button v-if="isAction" size="mini" plain icon="el-icon-minus" type="text" @click="deleteWorker(scope.$index, tableData)"/>

          <el-button v-else size="mini" disabled icon="el-icon-minus" type="info"/>

          <el-button v-if="isSetCommander && isAction" size="mini" plain type="primary" @click="handleCommander(scope.$index , scope.row)">{{scope.row.person_type === 0 ? '指定指挥' : '取消指挥' }} </el-button>

          <el-button v-else-if="isSetCommander" size="mini" disabled type="info">{{scope.row.person_type === 0 ? '指定指挥' : '取消指挥' }} </el-button>

        </template>
      </el-table-column>
    </el-table>

    <div v-if="dialog.dialogVisible">
      <el-dialog title="添加作业人员" :visible.sync="dialog.dialogVisible" width="600px"
                 append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <LiftingAddWokerDialog @close="dialog.dialogVisible=false" @get-worker-info="receiveWorkerInfo"/>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import LiftingAddWokerDialog from "@/views/security/si/operation/LiftingAddWokerDialog";
import {hoistingPersonList} from "@/api/security/si/WorkTicketApi";
import {soilPersonList} from "@/api/security/si/GroundBreakingApi";
import {blindPlatePluggingPersonList} from "@/api/security/si/BindPlatePluggingApi";
import {spacePersonList} from "@/api/security/si/ConfinedSpaceApi";
export default {
  components:{
    LiftingAddWokerDialog
  },
  name: "WorkerCompoent",
  props:['ticketType', 'flag'],
  data(){
    return{
      loading:false,
      tableData:[],
      dialog:{
        dialogVisible:false,
      },
      api:{
        getWorkerListImpl:null ,
      },
      isAction:true,
      isSetCommander:false
    }
  },
  methods:{
    //添加作业人员
    addWorker(){
      this.dialog.dialogVisible = true ;
    },

    //删除作业人员
    deleteWorker(index, rows){
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // console.log(index);
        rows.splice(index, 1);
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    //接收添加作业人员dialog传过来的人员数据
    receiveWorkerInfo(infoData){
      let isExist = false ;
      for (let i = 0; i < this.tableData.length; i++) {
        let item = this.tableData[i];
        if (item.id === infoData.id){
          isExist = true ;
          break
        }
      }
      if (!isExist){
        this.tableData.push({...infoData}) ;
      }else {
        this.$message.warning("作业人员已存在")
      }

      this.dialog.dialogVisible = false;
    },

    setWorkerListByTicketID(ticketID, isEditable = true){
      this.isAction = isEditable;
      if (this.flag ==="DZ") {
        this.api.getWorkerListImpl = hoistingPersonList;
      } else if (this.flag ==="MD") {
        this.api.getWorkerListImpl = blindPlatePluggingPersonList;
      }else if (this.flag ==="DT"){
        this.api.getWorkerListImpl = soilPersonList;
      }else if (this.flag ==="SX"){
        this.api.getWorkerListImpl = spacePersonList;
      }

      if (this.api.getWorkerListImpl === null ){
        return
      }

      this.api.getWorkerListImpl(ticketID).then(response=>{
        this.tableData = response.data.data ;
      }, error=>{
        // console.log(error);
      })
    },

    getWorkerList(){
      return this.tableData ;
    },
    //处理设置指挥的方法  指定和取消指挥
    handleCommander(index, row){
      if (row.person_type === 0){
        row.person_type = 1 ;
      }else {
        row.person_type = 0 ;
      }
    }
  },
  created() {
    switch (this.ticketType){
      case 'lifting':
        this.isSetCommander = true ;
        break
      default:
        this.isSetCommander = false ;
        break
    }
  }
}
</script>

<style scoped>

</style>
