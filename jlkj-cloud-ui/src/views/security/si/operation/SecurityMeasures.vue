<template>
  <div class="avue-crud">
    <el-table :data="measuresData" stripe size="small" v-loading="tableData.loading">
      <el-table-column
        prop="safety_measures"
        label="安全措施"
        min-width="400px">
        <template scope="scope">
          <el-input v-if="scope.row.safety_measures.indexOf('（') !== -1 && scope.row.is_confirm === 0" v-model="scope.row.safety_measures"/>
          <span v-else>{{scope.row.safety_measures}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="confirm_time"
        label="确认时间"
        width="150px"
        fixed="right"/>
      <el-table-column
        prop="confirm_person_name"
        label="确认人"
        width="100px"
        fixed="right"/>
      <el-table-column
        prop="querenshijian"
        label="确认"
        width="100px"
        fixed="right">
        <template scope="scope">
          <el-button v-if="isAction && scope.row.is_confirm === 0"
                     type="primary"
                     size="mini"
                     @click="confirm(scope.$index, scope.row)"
                     :loading="scope.row.isLoading">确定</el-button>

          <el-button v-if="isAction && scope.row.is_confirm === 1"
                     type="text"
                     size="mini"
                     @click="cancel(scope.$index, scope.row)"
                     :loading="scope.row.isLoading">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
    <personnel-selection v-if="dialogVisible"
                         :userDialogVisible="dialogVisible"
                         @close-dialog="dialogVisible = false"
                         :isMultiple="false"
                         :height="500"
                         @single-select="handleSingleSelect"></personnel-selection>
  </div>
</template>

<script>
import {hoistingSafetymeasuresList, hoistingConfirmSafetymeasures, hoistingCancelSafetymeasures, getBlindplatepluggingSafetymeasuresList,
  blindplatepluggingConfirmSafetyMeasures, blindplatepluggingCancelSafetyMeasures} from '@/api/security/si/WorkTicketApi'
import {
  soilCancelSafetymeasures,
  soilConfirmSafetymeasures,
  soilSafetymeasuresList
} from '@/api/security/si/GroundBreakingApi'
import moment from "moment";
import {
  spaceCancelSsfetymeasures,
  spaceConfirmSafetymeasures,
  spaceSafetymeasuresList
} from "@/api/security/si/ConfinedSpaceApi";
export default {
  name: "SecurityMeasures",
  props:['dataBundle', 'flag'],
  data(){
    return {
      self:{
        id : this.$store.getters.userInfo.userId,
        name: this.$store.getters.userInfo.nickName,
        departmentName: this.$store.getters.userInfo.dept.deptName,
        departmentID : this.$store.getters.userInfo.dept.deptId
      },
      measuresData:[],
      tableData:{
        loading:false
      },
      currTicketID:'',
      currJobCode:'',
      getSafetyMeasuresListImpl:null,
      confirmSafetyMeasuresImpl:null,
      cancelSafetyMeasuresImpl:null,
      isAction:true,
      dialogVisible:false,
      row:{},
      index:0,
    }
  },
  methods:{
    getSafetymeasuresList(ticketID){
      this.tableData.loading = true ;
      if (this.flag === "DZ"){
          this.getSafetyMeasuresListImpl = hoistingSafetymeasuresList;
      }else if (this.flag === "MD"){
          this.getSafetyMeasuresListImpl = getBlindplatepluggingSafetymeasuresList;
      }else if (this.flag === "DT"){
        this.getSafetyMeasuresListImpl = soilSafetymeasuresList ;
      }else if (this.flag === "SX"){
        this.getSafetyMeasuresListImpl = spaceSafetymeasuresList ;
      }

      if (this.getSafetyMeasuresListImpl === undefined){
        return ;
      }

      this.getSafetyMeasuresListImpl(ticketID).then(response=>{
        this.tableData.loading = false ;
        this.measuresData = response.data.data;
        this.measuresData.forEach(item=>{
          item.isLoading = false ;
        })
      }, error => {
        this.tableData.loading = false ;
        console.log(error);
      });
    },
    //确认安全措施
    confirm(index,row){
      if (this.flag === "DZ" || this.flag === "SX" || this.flag === "MD" || this.flag === "DT"){
        //如果是吊装或者有限空间弹出选人的弹窗
        this.row = row;
        this.index = index ;
        this.dialogVisible = true;
      }
      else {
        let params = {
          confirm_person_id:this.self.id,
          confirm_person_name:this.self.name,
          id:row.id,
          safety_measures:row.safety_measures
        }
         if (this.flag === "MD"){
          this.confirmSafetyMeasuresImpl = blindplatepluggingConfirmSafetyMeasures;
        }else if (this.flag === 'DT'){
          this.confirmSafetyMeasuresImpl = soilConfirmSafetymeasures ;
        }
        if (this.confirmSafetyMeasuresImpl === undefined){
          return
        }
        this.measuresData[index]['isLoading'] = true;
        this.confirmSafetyMeasuresImpl(params).then(response=>{
          this.measuresData[index]['isLoading'] = false;
          if (response.data.msg === "安全措施确认成功"){
            this.measuresData[index]['is_confirm']=1;
            this.measuresData[index]['confirm_time'] = moment(new Date()).format("YYYY-MM-DD HH:mm:ss");
            this.measuresData[index]['confirm_person_name'] = this.self.name;
            this.$message({
              message: '安全措施确认成功！',
              type: 'success'
            });
          }
        }, error=>{
          this.measuresData[index]['isLoading'] = false;
          // console.log(error);
          this.$message.error('安全措施确认失败！');
        });
      }
    },
    //取消安全措施
    cancel(index,row){
      if (this.flag === "DZ"){
        this.cancelSafetyMeasuresImpl = hoistingCancelSafetymeasures;
      }else if (this.flag === "MD"){
        this.cancelSafetyMeasuresImpl = blindplatepluggingCancelSafetyMeasures;
      }else if (this.flag === "DT"){
        this.cancelSafetyMeasuresImpl = soilCancelSafetymeasures ;
      }else if (this.flag === "SX"){
        this.cancelSafetyMeasuresImpl = spaceCancelSsfetymeasures ;
      }

      if (this.cancelSafetyMeasuresImpl === undefined){
        return
      }
      this.measuresData[index]['isLoading'] = true;
      this.cancelSafetyMeasuresImpl({id:row.id}).then(response=>{
        this.measuresData[index]['isLoading'] = false;
        if (response.data.msg==='安全措施取消成功'){
          this.measuresData[index]['is_confirm']=0;
          this.measuresData[index]['confirm_time'] = '';
          this.measuresData[index]['confirm_person_name'] = '';
          this.$message({
            message: '安全措施取消成功！',
            type: 'success'
          });
        }
      },error=>{
        this.measuresData[index]['isLoading'] = false;
        // console.log(error);
        this.$message.error('安全措施取消失败！');
      })
    },

    handleSingleSelect(data){
      let params = {
        confirm_person_id:data.id,
        confirm_person_name:data.full_name,
        id:this.row.id,
        safety_measures:this.row.safety_measures
      }

      if (this.flag === "DZ"){
        this.confirmSafetyMeasuresImpl = hoistingConfirmSafetymeasures;
      }else if (this.flag === 'SX'){
        this.confirmSafetyMeasuresImpl = spaceConfirmSafetymeasures ;
      }else if (this.flag === "MD"){
        this.confirmSafetyMeasuresImpl = blindplatepluggingConfirmSafetyMeasures;
      }else if (this.flag === 'DT'){
        this.confirmSafetyMeasuresImpl = soilConfirmSafetymeasures ;
      }
      if (this.confirmSafetyMeasuresImpl === undefined){
        return
      }
      this.measuresData[this.index]['isLoading'] = true;
      this.confirmSafetyMeasuresImpl(params).then(response=>{
        this.measuresData[this.index]['isLoading'] = false;
        if (response.data.msg === "安全措施确认成功"){
          this.measuresData[this.index]['is_confirm']=1;
          this.measuresData[this.index]['confirm_time'] = moment(new Date()).format("YYYY-MM-DD HH:mm:ss");
          this.measuresData[this.index]['confirm_person_name'] = data.full_name;
          this.$message({
            message: '安全措施确认成功！',
            type: 'success'
          });
        }
      }, error=>{
        this.measuresData[this.index]['isLoading'] = false;
        // console.log(error);
        this.$message.error('安全措施确认失败！');
      });
    },
  },

  created() {
    //在刚刚加载完成的时候注册接受作业票id的event bus ，这样在新建作业票的时候就可以拿到当前新建作业票的id
    this.$EventBus.$on('get-ticket-id', data=>{
      //这里拿到了作业票ID的时候在进行请求安全措施
      // console.log("安全措施 - 新建作业票的作业票ID: " + data);
      if (data !== undefined && data !== null && data !== {}){
        this.currJobCode = data["jobCode"];
        this.getSafetymeasuresList(data['ticketID']);
      }
    });

    this.$EventBus.$on('event-isEditable-safetymeasures', (isEditable) =>{
      this.isAction = isEditable ;
    });

    //接收作业票审批模块发来的消息, 查询当前作业票的安全措施是否有确认的
    this.$EventBus.$on("event-safetymeasures-has-confrim-item", ()=>{
      let isHas = false ;
      this.measuresData.forEach(i => {
        if (i.is_confirm === 1){
          isHas = true ;
        }
      });
      this.$EventBus.$emit("event-safetymeasures-has-confrim-item-result", isHas);
    });
  },

  mounted() {
    if (this.dataBundle.type === 1){
      let ticketID = this.dataBundle.id;
      this.currJobCode = this.dataBundle.jobCode;
      if (ticketID !== undefined && ticketID !== '' && ticketID !== null){
        this.getSafetymeasuresList(ticketID);
        this.isAction = false ;
      }
    }
  },

  beforeDestroy() {

    this.$EventBus.$off('get-ticket-id');
    this.$EventBus.$off('event-isEditable-safetymeasures');
    this.$EventBus.$off("event-safetymeasures-has-confrim-item");
    this.getSafetyMeasuresListImpl = null ;
    this.confirmSafetyMeasuresImpl = null ;
    this.cancelSafetyMeasuresImpl = null ;
  }
}
</script>

<style scoped>

</style>
