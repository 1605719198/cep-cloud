<template>
  <div>
    <div style="text-align: right;padding:0 40px 10px 0;" v-if="this.data.status < 3">
      <el-upload ref="upload" class="upload-demo" multiple
                 :action="action"
                 :show-file-list="false"
                 :on-success="uploadSuccess"
                 :on-error="uploadError">
        <el-button type="primary">新增附件</el-button>
      </el-upload>
    </div>
    <el-table size="small" :data="appendixData" stripe v-loading="loading">
      <el-table-column prop="file_name" label="附件名称" min-width="450px"/>
      <el-table-column label="操作">
        <template scope="scope">
          <el-button size="mini" plain icon="el-icon-minus" type="text" v-if="data.status < 3"
                     @click="deleteAppendix(scope.$index)"/>
          <el-button size="mini" plain type="primary" @click="downloadFile(scope.row)">下载</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";
  // import {fileUrl} from '@/config/env';

  export default {
    name: "fileList",
    props: ['data', 'type'],
    data() {
      return {
        loading: true,
        action: "",
        download: "",
        appendixData: [],
      }
    },
    created() {
      // this.action = this.GLOBAL.fileUrl + '/file/upload';
      // this.download = this.GLOBAL.fileUrl + '/file/download/';
    },
    mounted() {
      if (this.data.id) {
        req('get', this.type + '/file/list', {id: this.data.id}).then(res => {
          this.appendixData = res.data.data;//表格数据
          this.data.file_list = this.appendixData;
          this.loading = false;
        }, error => {
          this.loading = false;
          window.console.log(error);
        });
      } else {
        this.loading = false;
        this.data.status = 0;
      }
    },
    methods: {
      //上传进度
      deleteAppendix(index) {
        delete this.appendixData.splice(index, 1);
        this.data.file_list = this.appendixData;
      },
      //上传成功
      uploadSuccess(info) {
        let appendixInfo = {
          file_name: info.data.fileName,
          appendix_id: info.data.id
        };
        this.appendixData.push(appendixInfo);
        this.data.file_list = this.appendixData;
        this.$message({message: '上传成功', type: 'success'});
      },
      //上传失败
      uploadError(error) {
        this.$message.error('上传失败');
      },
      //下载文件
      downloadFile(row) {
        window.open(this.download + row.appendix_id);
      },
    },
  }
</script>

<style scoped>

</style>
