<template>
  <div>
    <div style="text-align: right;padding:0 40px 0 0;" >
      <el-upload
        class="upload-demo"
        :action="action"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-progress="uploadProgress"
        :on-success="uploadSuccess"
        :on-error="uploadError">
        <el-button type="primary" :loading="event.buttonLoading">新增附件</el-button>
      </el-upload>
    </div>
    <el-table size="small" :data="appendixData" stripe style="width: 100% ; margin-top: 10px" v-loading="event.tableLoading">
      <el-table-column
        prop="appendix_name"
        label="附件名称"
        min-width="450px"
      />

      <el-table-column
        prop="操作"
        label="操作"
        min-width="100px">
        <template scope="scope">
          <el-button size="mini" plain icon="el-icon-minus" type="text" @click="deleteAppendix(scope.$index, appendixData)"/>
          <el-button size="mini" plain type="primary" @click="downloadAppendix(scope.row)">下载</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {hoistingFileList} from "@/api/security/si/WorkTicketApi";
import {blindPlatePluggingFileList} from '@/api/security/si/BindPlatePluggingApi'
import {soilFileList} from "@/api/security/si/GroundBreakingApi";
import {spaceFileList} from "@/api/security/si/ConfinedSpaceApi";

export default {
  name: "AppendixComponent",
  props:['flag'],
  data(){
    return{
      dialog:{
        appendixDialogVisible:false
      },
      appendixData:[],
      api:{
        getAppendixListImpl:null
      },
      event:{
        tableLoading:false,
        buttonLoading:false
      },
      action : '' ,
      download : ''
    }
  },
  methods:{
    //删除附件
    deleteAppendix(index, rows){
      this.$confirm('此操作将永久删除该附件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
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
    //开始上传之前
    beforeUpload(){
      this.event.buttonLoading = true;
    },
    //上传进度
    uploadProgress(progress){
      console.log(progress);
    },
    //上传成功
    uploadSuccess(info){
      this.event.buttonLoading = false;
      let appendixInfo = {
        appendix_name:info.data.fileName,
        appendix_id:info.data.id
      }
      this.appendixData.push(appendixInfo);
      this.$message({
        message: '上传成功',
        type: 'success'
      });
    },
    //上传失败
    uploadError(error){
      this.event.buttonLoading = false;
      this.$message.error('上传失败');
      window.console.log(error);
    },
    //下载附件
    downloadAppendix(row){
      // console.log(row);
      let download = this.download + "/" + row.appendix_id;
      window.open(download);
    },

    setAppendixByTicketID(ticketID){
      if (this.flag ==="DZ") {
        this.api.getAppendixListImpl = hoistingFileList;
      } else if (this.flag ==="MD") {
        this.api.getAppendixListImpl = blindPlatePluggingFileList;
      }else if (this.flag ==="DT"){
        this.api.getAppendixListImpl =soilFileList;
      }else if (this.flag ==="SX"){
        this.api.getAppendixListImpl = spaceFileList;
      }

      if (this.api.getAppendixListImpl === null ){
        return
      }
      this.event.tableLoading = true ;
      this.api.getAppendixListImpl(ticketID).then(response=>{
        this.event.tableLoading = false ;
        this.appendixData = response.data.data ;
        //由于服务返回的文件名称不是appendix_name  是file_name 所以需要转
        for (let appendixDataKey in this.appendixData) {
          this.appendixData[appendixDataKey]['appendix_name'] = this.appendixData[appendixDataKey].file_name;
        }
      },error=>{
        this.event.tableLoading = false ;
        // console.log(error);
      })
    },

    getAppendixList(){
      return this.appendixData;
    }
  },
  created() {
    this.action = this.GLOBAL.fileUrl + '/file/upload';
    this.download = this.GLOBAL.fileUrl + '/file/download';
  }
}
</script>

<style scoped>

</style>
