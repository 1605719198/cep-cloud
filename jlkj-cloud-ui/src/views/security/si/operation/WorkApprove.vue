<template>
  <div class="avue-crud">
    <el-table :data="tableData" stripe  size="small"  v-loading="tableConfig.loading">
      <el-table-column type="index" label="序号" width="50"/>
      <el-table-column prop="approval_name" label="审批环节"/>
      <el-table-column prop="approval_person_name" label="审批人" width="100px" />
      <el-table-column prop="status" label="审批状态" width="100px" >
        <template scope="scope">
          <span>{{(scope.row.is_approval === undefined)||(scope.row.is_approval === 0) ? '未审批' : '已审批'}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="content" label="审批意见" width="200px">
        <template scope="scope">
          <div v-if="(scope.row.is_approval === undefined)||(scope.row.is_approval === 0) && scope.row.approval_person_id === self.id && scope.row.confirm_time ">
            <el-input v-model="scope.row.content"/>
          </div>
          <div v-else>
<!--            <el-input  v-model="scope.row.content" disabled />-->
            {{scope.row.content}}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="approval_time" label="签字时间" width="180px" />
      <el-table-column prop="pick_person_name" label="选择人" width="70px"/>
      <el-table-column prop="操作" label="操作" min-width="100px" fixed="right">
        <template scope="scope">

          <el-button v-if="isShowSetting(scope)" size="mini" @click="setApproval(scope.$index, scope.row)">设置</el-button>

          <el-button v-if="!scope.row.confirm_time && scope.row.approval_person_id === self.id && setWay !== 0" type="primary" size="mini" @click="handleConfirmApprovalTime(scope.$index, scope.row)" >确认</el-button>

          <!-- 如果是未审批状态并且当前要修改的审批人和当前登入的用户是同一个人才能审批-->
          <el-button v-if="scope.row.confirm_time && scope.row.is_approval===0 && scope.row.approval_person_id === self.id" type="text" size="mini" @click="handleSign(scope.$index, tableData)">审批</el-button>

          <el-button v-if="scope.row.confirm_time && scope.row.is_approval===0 && scope.row.approval_person_id === self.id && setWay !== 0" size="mini" plain type="warning" @click="onReturn(scope.row)" >回退</el-button>

          <el-button v-if="scope.row.is_approval===1" size="mini" plain type="text" @click="onWatch(scope.row,'info')">查看</el-button>

        </template>
      </el-table-column>
    </el-table>
    <div>
      <personnel-selection v-if="dialog.dialogVisible"
                           :userDialogVisible="dialog.dialogVisible"
                           @close-dialog="dialog.dialogVisible = false"
                           :isMultiple="false"
                           :height="500"
                           @single-select="handleSingleSelect"></personnel-selection>
    </div>
    <!-- 做使用el-dialog做签字的弹框 -->
    <el-dialog title="签字" :visible.sync="dialogVisible" width="500px" append-to-body :destroy-on-close="true"
               :close-on-click-modal="false" @close="cancel">
      <!-- 使用这个签名组件 -->
      <vue-esign v-if="resultImg===''" ref="esign" class="mySign"
                 :width="400" :height="200"
                 :isCrop="isCrop" :lineWidth="lineWidth" :lineColor="lineColor" :bgColor.sync="bgColor"/>
      <img v-else :src="resultImg" width="100%" alt=""/>
      <span slot="footer" class="dialog-footer">
          <el-button v-if="imgType !== 'info'" @click="handleAutograph" type="primary">电子签章</el-button>
          <el-button v-if="imgType !== 'info'" @click="handleReset">清空画板</el-button>
          <el-button v-if="imgType !== 'info'" @click="handleGenerate" type="primary">完成</el-button>
          <el-button @click="cancel">取消</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import AddApproverDialog from "@/views/security/si/operation/AddApproverDialog";
import vueEsign from 'vue-esign'
import {
  hoistingApprovalList,
  hoistingConfirmApproval,
  hoistingApprovalTemplateList,
  hoistingConfirmApprovalTime,
  blindingPlatePluggingApprovalList,
  blindingPlatePluggingConfirmApproval,
  hoistingUpdateApproval
} from '@/api/security/si/WorkTicketApi'

import {
  soilApprovalList,
  soilApprovalTemplateList,
  soilConfirmApproval,
  soilConfirmApprovalTime,
  soilUpdateApproval
} from '@/api/security/si/GroundBreakingApi'

import {
  blindPlatePluggingConfirmApprovalTime,
  approvalTemplateList,
  blindPlatePluggingUpdateApproval
} from '@/api/security/si/BindPlatePluggingApi'
import {
  spaceApprovalList,
  spaceApprovalTemplateList,
  spaceConfirmApproval,
  spaceConfirmApprovalTime, spaceUpdateApproval
} from "@/api/security/si/ConfinedSpaceApi";
import {mapGetters} from "vuex";
import {upload} from "@/api/security/si/governance";
import {req} from "@/api/security/si/common";

export default {
  components:{AddApproverDialog, vueEsign},
  name: "WorkApprove",
  props:['dataBundle','flag'],
  computed: {
    ...mapGetters(["userInfo"]),
  },
  data(){
    return {
      tableData:[],
      tableConfig:{
        loading:false
      },
      dialog:{
        dialogVisible:false
      },
      event:{
        isRegister : false
      },
      self:{
        id : this.$store.getters.userInfo.userId,
        name: this.$store.getters.userInfo.nickName,
        departmentName: this.$store.getters.userInfo.dept.deptName,
        departmentID : this.$store.getters.userInfo.dept.deptId
      },
      dialogVisible:false ,
      lineWidth: 6, // 画笔的线条粗细
      lineColor: "#000000", // 画笔的颜色
      bgColor: "", // 画布的背景颜色
      resultImg: "", // 最终画布生成的base64图片
      isCrop: false, // 是否裁剪，在画布设定尺寸基础上裁掉四周空白部分,
      imgType:'',
      signature:'',
      currentConfirmData:null,
      /**
       * 设置审批人得方式
       *  0是和作业票基础信息一起上传，
       *  1是单独用设置审批人接口
       *  2为不可设置
       *  3 特殊(为吊装作业票和有限空间设置的设置, 如果是3则是审批人信息跟作业票基本信息一起上传,却可以确认)
       */
      setWay:0 ,
      setApprovalItemPosition:0,
      currApprovalItemPosition:0,
      currJobCode:'',
      currTicketID: '' ,
      api:{
        getApprovalListImpl:null,//获取审批人列表
        workTicketApprovalImpl:null, // 作业票审批
        updateApprovalImpl:null, //更新审批人,
        getApprovalTemplateListImpl:null, //获取审批模板
        confirmApprovalTimeImpl:null,
      },
      approvalItemID:'',
      spaceConfirmID:'',//这个ID是受限空间的确认ID
      row:{},
      index:{},
      approvalStatus:0
    }
  },
  methods:{
    cancel(){
      this.dialogVisible = false;
      this.resultImg = '';
      this.imgType = "";
    },

    handleSign(index,row){
      //说明当前登入的人和该审批人为同一个人  可以有他（她，它）进行审批
      this.dialogVisible = true ;
      this.currentConfirmData = row[index] ;
      this.currApprovalItemPosition = index ;
    },
    // 清空画板
    handleReset() {
      this.resultImg = "";
      this.$refs.esign.reset();
    },
    // 生成签字图
    handleGenerate() {
      if (this.resultImg === ''){
        let signature = false ;
        this.$refs.esign.generate().then((res) => {
            this.resultImg = res;
            signature = true;
          }).catch((err) => {
          // 画布没有签字时会执行这里提示一下
          window.console.log(err);
          this.$message({
            type: "warning",
            message: "请签名后再生成签字图片",
          });
        });

        setTimeout(() => {
          if (signature){
            let file = this.dataURLtoFile(this.resultImg, 'qm.png');
            let formData = new FormData();
            formData.append('file', file);
            upload(formData).then(res => {
              this.signature = res.data.data.relativePath;
              let params = {
                content:this.currentConfirmData.content === undefined || this.currentConfirmData.content === null? "" : this.currentConfirmData.content,
                id:this.currentConfirmData.id,
                signature:this.signature
              }
              if (this.flag === "DZ"){
                this.api.workTicketApprovalImpl = hoistingConfirmApproval;
              }else if (this.flag === "MD"){
                this.api.workTicketApprovalImpl = blindingPlatePluggingConfirmApproval;
              }else if (this.flag === "DT"){
                this.api.workTicketApprovalImpl = soilConfirmApproval ;
              }else if (this.flag === "SX"){
                this.api.workTicketApprovalImpl = spaceConfirmApproval ;
              }
              this.api.workTicketApprovalImpl(params).then(response=>{
                if (response.data.msg === '审批成功' || response.data.msg==='签字确认成功'){
                  if (this.flag === "DZ"){
                    this.$EventBus.$emit('event-refresh-lifting-work-ticket-list-data', '');
                  }else if (this.flag === "MD"){
                    this.$EventBus.$emit('event-refresh-blinding-work-ticket-list-data', '');
                  }else if (this.flag === "DT"){
                    this.$EventBus.$emit('event-refresh-groundBreaking-work-ticket-list-data', '');
                  }else if (this.flag === "SX"){
                    this.$EventBus.$emit('event-refresh-space-work-ticket-list-data', '');
                  }
                  if (this.currApprovalItemPosition < this.tableData.length -1){
                    this.approvalStatus = 1 ;
                    this.setWay = 1 ;
                  }else {
                    this.approvalStatus = 3 ;
                    this.setWay = 2 ;
                  }
                  if (this.tableData[this.tableData.length - 1].is_approval === 1){
                    //如果当前审批是最后一个环节，发送刷新有限空间显示实际结束时间的通知，如果当前作业票不是有先空间，那就不用管反正也收到通知，是的话就是在基本信息中显示实际结束时间。
                    this.$EventBus.$emit('event-refresh-space-end-time');
                  }
                  if (this.flag === "DZ"){
                    this.$EventBus.$emit('event-approval-component-approval-message-dz');
                  }else if (this.flag === "MD"){
                    this.$EventBus.$emit('event-approval-component-approval-message-md');
                  }else if (this.flag === "DT"){
                    this.$EventBus.$emit('event-approval-component-approval-message-dt');
                  }else if (this.flag === "SX"){
                    this.$EventBus.$emit('event-approval-component-approval-message-sx');
                  }
                  this.$message({
                    message: '审批成功！',
                    type: 'success'
                  });
                  this.getApprovalList(this.currTicketID);
                  this.resultImg = '';
                  this.dialogVisible = false;
                }
              }, error=>{
                // console.log(error);
                this.$message.error('审批失败！');
              })
            }, err => {
              window.console.log(err);
            });
          }
        }, 200);
      }else {
        let params = {
          content:this.currentConfirmData.content === undefined || this.currentConfirmData.content === null? "" : this.currentConfirmData.content,
          id:this.currentConfirmData.id,
          signature:this.signature
        }
        if (this.flag === "DZ"){
          this.api.workTicketApprovalImpl = hoistingConfirmApproval;
        }else if (this.flag === "MD"){
          this.api.workTicketApprovalImpl = blindingPlatePluggingConfirmApproval;
        }else if (this.flag === "DT"){
          this.api.workTicketApprovalImpl = soilConfirmApproval ;
        }else if (this.flag === "SX"){
          this.api.workTicketApprovalImpl = spaceConfirmApproval ;
        }
        this.api.workTicketApprovalImpl(params).then(response=>{
          if (response.data.msg === '审批成功' || response.data.msg==='签字确认成功'){
            if (this.flag === "DZ"){
              this.$EventBus.$emit('event-refresh-lifting-work-ticket-list-data', '');
            }else if (this.flag === "MD"){
              this.$EventBus.$emit('event-refresh-blinding-work-ticket-list-data', '');
            }else if (this.flag === "DT"){
              this.$EventBus.$emit('event-refresh-groundBreaking-work-ticket-list-data', '');
            }else if (this.flag === "SX"){
              this.$EventBus.$emit('event-refresh-space-work-ticket-list-data', '');
            }
            if (this.currApprovalItemPosition < this.tableData.length -1){
              this.approvalStatus = 1 ;
              this.setWay = 1 ;
            }else {
              this.approvalStatus = 3 ;
              this.setWay = 2 ;
            }
            if (this.tableData[this.tableData.length - 1].is_approval === 1){
              //如果当前审批是最后一个环节，发送刷新有限空间显示实际结束时间的通知，如果当前作业票不是有先空间，那就不用管反正也收到通知，是的话就是在基本信息中显示实际结束时间。
              this.$EventBus.$emit('event-refresh-space-end-time');
            }

            if (this.flag === "DZ"){
              this.$EventBus.$emit('event-approval-component-approval-message-dz');
            }else if (this.flag === "MD"){
              this.$EventBus.$emit('event-approval-component-approval-message-md');
            }else if (this.flag === "DT"){
              this.$EventBus.$emit('event-approval-component-approval-message-dt');
            }else if (this.flag === "SX"){
              this.$EventBus.$emit('event-approval-component-approval-message-sx');
            }

            this.$message({
              message: '审批成功！',
              type: 'success'
            });
            this.getApprovalList(this.currTicketID);
            this.resultImg = '';
            this.dialogVisible = false;
          }
        }, error=>{
          // console.log(error);
          this.$message.error('审批失败！');
        })
      }
    },

    handleAutograph(){
      if (this.userInfo.signature === '' || this.userInfo.signature === null) {
        this.$message({type: "error", message: "该用户没有上传电子签章"});
      } else {
        this.resultImg = this.GLOBAL.serveAddress + this.userInfo.signature;
        this.signature = this.userInfo.signature;
      }
    },

    setApproval(index, row){
      this.dialog.dialogVisible = true ;
      this.setApprovalItemPosition = index;
      this.approvalItemID = row.id ;
    },

    //接收到了选中审批人dialog发过来得审批人信息，在这个里面把数据添加到审批人列表中去
    handleSingleSelect(data){
      this.dialog.dialogVisible=false
      //执行添加审批人（新增作业票的时候）
      if (this.setWay === 0){
        this.tableData[this.setApprovalItemPosition]['approval_person_name'] = data.full_name;
        this.tableData[this.setApprovalItemPosition]['approval_person_id'] = data.id ;
        this.tableData[this.setApprovalItemPosition]['pick_person_id'] = this.self.id ;
        this.tableData[this.setApprovalItemPosition]['pick_person_name'] = this.self.name ;
        this.tableData[this.setApprovalItemPosition]['content'] = '' ;
        this.tableData[this.setApprovalItemPosition]['status'] = '未审批' ;
        this.tableData[this.setApprovalItemPosition]['time'] = '' ;
        this.tableData[this.setApprovalItemPosition]['is_approval'] = 0 ;
      }
      else if (this.setWay === 1){
        if (this.flag === "DZ"){
          this.api.updateApprovalImpl = hoistingUpdateApproval;
        }else if (this.flag === "MD"){
          this.api.updateApprovalImpl = blindPlatePluggingUpdateApproval;
        }else if (this.flag === "DT"){
          this.api.updateApprovalImpl = soilUpdateApproval ;
        }else if (this.flag === "SX"){
          this.api.updateApprovalImpl = spaceUpdateApproval ;
        }

        if (this.api.updateApprovalImpl === null){
          return
        }
        let params={
          'id':this.approvalItemID ,
          'approval_person_name':data.full_name,
          'approval_person_id':data.id,
          'pick_person_id':this.self.id,
          'pick_person_name':this.self.name
        }
        // alert(params.id);
        if (params.id === undefined || params.id === ''){
          return;
        }

        this.api.updateApprovalImpl(params).then(response =>{
          if (response.data.code === '0' && response.data.msg === '设置审批人成功'){
            this.getApprovalList(this.currTicketID);
            this.$message({
              message: response.data.msg,
              type: 'success'
            });
          }else {
            window.console.log(response.data.msg);
            this.$message.error("审批人设置失败");
          }
        }, error => {
          window.console.log(error);
          this.$message.error("审批人设置失败");
        });
      }
      else if (this.setWay === 3){
        this.tableData[this.setApprovalItemPosition]['approval_person_name'] = data.full_name;
        this.tableData[this.setApprovalItemPosition]['approval_person_id'] = data.id ;
        this.tableData[this.setApprovalItemPosition]['pick_person_id'] = this.self.id ;
        this.tableData[this.setApprovalItemPosition]['pick_person_name'] = this.self.name ;
        this.tableData[this.setApprovalItemPosition]['content'] = '' ;
        this.tableData[this.setApprovalItemPosition]['status'] = '未审批' ;
        this.tableData[this.setApprovalItemPosition]['time'] = '' ;
        this.tableData[this.setApprovalItemPosition]['is_approval'] = 0 ;
      }
    },

    //获取审批人列表
    getApprovalList(ticketID){
      this.tableConfig.loading = true ;
      if (this.flag === "DZ"){
        this.api.getApprovalListImpl = hoistingApprovalList;
      }else if (this.flag === "MD"){
        this.api.getApprovalListImpl = blindingPlatePluggingApprovalList;
      }else if (this.flag === "DT"){
        this.api.getApprovalListImpl = soilApprovalList ;
      }else if (this.flag === "SX"){
        this.api.getApprovalListImpl = spaceApprovalList ;
      }
      this.api.getApprovalListImpl(ticketID).then(response=>{
        this.tableConfig.loading = false ;
        if (this.dataBundle.type === 1){
          this.tableData = response.data.data;
          for (let i = 0; i < this.tableData.length; i++) {
            this.tableData[i]['isMaTao'] = false ;//马涛是一个很别的存在，在有限空间作业票中，审批人必须要有马涛这个人，自动设置不能修改，如果是马涛就不能进行设置
            if (this.tableData[i]['approval_name'] === '马涛' ){
              this.tableData[i]['isMaTao'] = true ;
            }
          }
        }else if (this.dataBundle.type === 2){
          let tableRes =  response.data.data;
          for (let i = 0; i < tableRes.length; i++) {
            tableRes[i].content = "";
            tableRes[i].isMaTao = tableRes[i]['approval_name'] === "马涛";
            tableRes[i].is_approval = 0 ;
            tableRes[i].pick_person_id=this.self.id;
            tableRes[i].pick_person_name = this.self.name;
            tableRes[i].status = "未审批";
            tableRes[i].time = "";
            tableRes[i].confirm_time = '';
            tableRes[i].approval_time = '';
            tableRes[i]['sort'] = i +1 ;
          }
          this.tableData = tableRes;
        }
      }, error =>{
        this.tableConfig.loading = false ;
        // console.log(error);
      });
    },

    //获取审批模板
    approvalTemplateList(level){
      if (this.flag === "DZ"){
        this.api.getApprovalTemplateListImpl = hoistingApprovalTemplateList;
      }else if (this.flag === "MD"){
        this.api.getApprovalTemplateListImpl = approvalTemplateList;
      }else if (this.flag === "DT"){
        this.api.getApprovalTemplateListImpl = soilApprovalTemplateList ;
      }else if (this.flag === "SX"){
        this.api.getApprovalTemplateListImpl = spaceApprovalTemplateList ;
      }

      if (this.api.getApprovalTemplateListImpl == null ){
        return
      }

      this.api.getApprovalTemplateListImpl(level).then(response=>{
        let data = response.data.data ;
        for (let i = 0; i < data.length; i++) {
          data[i]['approval_person_name'] = '';
          data[i]['approval_person_id'] = '' ;
          data[i]['pick_person_id'] = '' ;
          data[i]['pick_person_name'] = '' ;
          data[i]['content'] = '' ;
          data[i]['status'] = '未审批' ;
          data[i]['time'] = '' ;
          data[i]['is_approval'] = 0 ;
          data[i]['isMaTao'] = false ;//马涛是一个很别的存在，在有限空间作业票中，审批人必须要有马涛这个人，自动设置不能修改，如果是马涛就不能进行设置
          if (data[i]['approval_name'] === '马涛' ){
            data[i]['isMaTao'] = true ;
            data[i]['approval_person_name'] = '马涛';
            data[i]['approval_person_id'] = '52f7af6723c34646b129660bc75f7457' ;
            data[i]['pick_person_id'] = this.self.id ;
            data[i]['pick_person_name'] = this.self.name;
          }
        }
        this.tableData = data ;
      },error => {
        console.log(error);
      });
    },

    handleConfirmApprovalTime(index,row){
      if (index === 0){
        this.row = {...row};
        this.index = index ;
        this.$EventBus.$emit("event-safetymeasures-has-confrim-item");
      }else {
        this.confirmApprovalTime(index, row);
      }
    },
    //确认审批
    confirmApprovalTime(index,row) {
      let needConfirmID = row.id;
      if (this.flag === "DZ") {
        this.api.confirmApprovalTimeImpl = hoistingConfirmApprovalTime;
      } else if (this.flag === "MD") {
        this.api.confirmApprovalTimeImpl = blindPlatePluggingConfirmApprovalTime;
      }else if (this.flag === "DT"){
        this.api.confirmApprovalTimeImpl = soilConfirmApprovalTime ;
      }else if (this.flag === "SX"){
        //TODO 先发送一个广播给采样分析组件验证采样分析是否存在, 如果没有添加采样分析则不能确认,
        this.spaceConfirmID = needConfirmID ;
        this.$EventBus.$emit('event-analysis-component-is-had');
        return;
      }

      if (this.api.confirmApprovalTimeImpl == null) {
        return
      }
      this.api.confirmApprovalTimeImpl({id:needConfirmID}).then(response=>{
        if (response.data.msg === '确认成功'){
          this.getApprovalList(this.currTicketID);
        }else {
          // console.log(response.data.msg);
          this.$message.error("确认失败");
        }
      },error => {
        // console.log(error);
        this.$message.error("确认失败");
      });
    },

    onWatch(row, type){
      this.dialogVisible = true;
      if (row.signature.includes('data:')) {
        this.resultImg = row.signature;
      } else {
        this.resultImg = this.GLOBAL.serveAddress + row.signature;
      }
      this.imgType = type;
    },

    //判断在什么情况下显示设置按钮
    isShowSetting(scope){
      let isShow = false;
      if (this.setWay !== 2){//说明当前是审批人可编辑状态
        if (!scope.row.isMaTao){//如果当前要修改的审批人不是"马涛"才可以修改
          if (this.approvalStatus === 1 || this.approvalStatus === 2){//如果当前的作业票审批状态是"审批中"或者"作业中" 那要是项修改审批人得话, 只能修改自己的那一条审批, 别人的修改不了
            if (scope.row.approval_person_id === this.self.id){//判断当前要修改的审批的审批人ID是否和当前登入人相同, 如果相同就可以修改
              if (scope.row.is_approval === 0 && !scope.row.confirm_time){//如果当前未审批可修改
                isShow = true ;
              }else {//审批完了  不可修改
                isShow = false;
              }
            }else {//如果不同就不可以修改
              isShow = false ;
            }
          }else {//如果当前作业票的审批状态是"资料建立"那么审批人都可以修改
            if (!scope.row.confirm_time){
              isShow = true;
            }else {
              isShow = false;
            }
          }
        }else {//如果当前审批人是"马涛"的话是不可以修改的
          isShow = false ;
        }
      }else {//当前是不可编辑状态
        isShow = false ;
      }
      return isShow ;
    },

    onReturn(row) {
      this.$confirm('是否确定回退审批?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        let url = "";
        if (this.flag === "DZ"){
          url = "hoisting/confirm/return";
        }else if (this.flag === "MD"){
          url = "blindplateplugging/confirm/return";
        }else if (this.flag === "DT"){
          url = "soil/confirm/return";
        }else if (this.flag === "SX"){
          url = "space/confirm/return";
        }
        if (url !== ""){
          let params = {...row}
          if (params.content === null){
            params.content = "";
          }
          req('post', url, params).then(() => {
            this.approvalStatus = 0 ;
            this.setWay = 0 ;
            this.getApprovalList(this.currTicketID);
            if (this.flag === "DZ"){
              this.$EventBus.$emit("event-refresh-lifting-work-ticket-list-data");
            }else if (this.flag === "MD"){
              this.$EventBus.$emit("event-refresh-blinding-work-ticket-list-data");
            }else if (this.flag === "DT"){
              this.$EventBus.$emit("event-refresh-groundBreaking-work-ticket-list-data");
            }else if (this.flag === "SX"){
              this.$EventBus.$emit("event-refresh-space-work-ticket-list-data");
            }
          }, error => {
            window.console.log(error);
          });
        }else {
          this.$message({type: 'warning', message: '作业票类型获取失败'});
        }
      }).catch(() => {
        this.$message({type: 'info', message: '已取消回退'});
      });
    },
  },

  created() {
    if (!this.event.isRegister){
      /**
       * 通知审批组件回传审批人得时候，这个是在新增作业票的时候用到这个事件，因为是新增作业票的时候需要把作业票基本信息和审批人数据一起上传
       */
      this.$EventBus.$on("get-ticket-approve-data", (data)=>{
        switch (data){
          case "blinding":
            // console.log("发送审批人到抽堵盲板作业票");
            this.$EventBus.$emit("ticket-approve-data-blinding", this.tableData);
            break
          case "groundBreaking":
            // console.log("发送审批人到动土作业票");
            this.$EventBus.$emit("ticket-approve-data-groundBreaking", this.tableData);
            break
          case "space":
            // console.log("发送审批人到有限空间");
            this.$EventBus.$emit("ticket-approve-data-space", this.tableData);
            break
          default:
            // console.log("发送审批人到吊装",this.tableData);
            this.$EventBus.$emit("ticket-approve-data-lifting", this.tableData);
            break
        }
      });
      /**
       * 这个是用于获取作业票模板的，有的需要在选作业级别之后获取模板，有的是直接拿到jobCode就获取模板
       * 当args参数为空的时候说明不需要作业级别
       */
      this.$EventBus.$on("event-job-level-change" , (args)=>{
        let jobLevel = args.level ;
        this.currJobCode = args.jobCode;
        this.approvalStatus = 0 ;
        this.setWay = 0 ;
        this.approvalTemplateList(jobLevel);
      });

      /**
       * 通知审批组件当前设置审批人得方式
       * 0 和作业票基本信息一起上传
       * 1 单独设置，用的时候单独的修改审批人得接口
       * 2 不可设置
       */
      this.$EventBus.$on('event-approval-set-way', (setWay) => {
          this.setWay = setWay ;
      });

      /**
       *通知审批组件刷新
       */
      this.$EventBus.$on('event-refresh-approval', () => {
        this.getApprovalList(this.currTicketID);
      });

      /**
       * 返回采样分析是否存在
       */
      this.$EventBus.$on('event-analysis-data-is-had', (isHad) => {
        if (isHad){
          this.api.confirmApprovalTimeImpl = spaceConfirmApprovalTime ;
          if (this.api.confirmApprovalTimeImpl == null) {
            return
          }
          this.api.confirmApprovalTimeImpl({id:this.spaceConfirmID}).then(response=>{
            if (response.data.msg === '确认成功'){
              this.getApprovalList(this.currTicketID);
            }else {
              // console.log(response.data.msg);
              this.$message.error("确认失败");
            }
          },error => {
            // console.log(error);
            this.$message.error("确认失败");
          });
        }else {
          this.$message.error("未添加采样分析, 不能确认");
        }
      });

      /**
       * 成功创建新的作业票的时候更新当前审批的作业票ID
       */
      this.$EventBus.$on('get-ticket-id', (param) => {
        this.currTicketID = param.ticketID;
        this.currJobCode = param.jobCode;
        this.setWay = 1 ;
      });

      /**
       * 接收安全措施发过来的是否存在已经确认的安全措施项, 如果有确认过的安全措施项,就可以在审批的时候直接确认, 如果没有确认过的安全措施项, 在确认的时候需要记性提示
       */
      this.$EventBus.$on("event-safetymeasures-has-confrim-item-result", (isHas) => {
        // console.log("event-safetymeasures-has-confrim-item-result ----- ");
        if (isHas){
          this.confirmApprovalTime(this.index, this.row);
        }else {
          this.$confirm('安全措施没有填写, 是否继续审核?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.confirmApprovalTime(this.index, this.row);
          }).catch(() => {
            this.$message({type: 'info', message: '已取消确认'});
          });
        }
      });

      /**
       * 接收作业票当前的审批状态
       * 如果当前是资料简历阶段保持当前的修改审批人逻辑不变
       * 如果要是审批中或者作业中的话 修改审批人 只能是当前审批人修改自己的审批  不能修改其他人得
       */
      this.$EventBus.$on("event-work-ticket-approval-status-code", (approvalStatusCode) => {
          this.approvalStatus = approvalStatusCode;
      });

      this.event.isRegister = true ;
    }
  },

  mounted() {
    if (this.dataBundle.type === 1){
      this.currTicketID = this.dataBundle.id ;
      this.currJobCode = this.dataBundle.jobCode ;
      this.setWay = 1;
      this.getApprovalList(this.currTicketID);
    }else if(this.dataBundle.type === 2){
      //如果是复制:先是获取审批人列表数据然后把数据都修改成添加审批人得状态
      this.currTicketID = this.dataBundle.id ;
      this.currJobCode = this.dataBundle.jobCode ;
      this.setWay = 0;
      this.getApprovalList(this.currTicketID);
    } else {
      this.setWay = 0;
    }
  },

  beforeDestroy() {
    this.$EventBus.$off('get-ticket-approve-data');
    this.$EventBus.$off('event-job-level-change');
    this.$EventBus.$off('event-approval-set-way');
    this.$EventBus.$off('event-refresh-approval');
    this.$EventBus.$off('event-editable-approval');
    this.$EventBus.$off('event-analysis-data-is-had');
    this.$EventBus.$off('get-ticket-id');
    this.$EventBus.$off("event-safetymeasures-has-confrim-item-result");
    this.$EventBus.$off("event-work-ticket-approval-status-code");
    this.api.updateApprovalImpl = null ;
    this.api.getApprovalListImpl = null ;
    this.api.workTicketApprovalImpl = null ;
  }
}
</script>

<style scoped>

</style>
