<template>
  <div class="avue-crud">
    <div class="head-container">
      <el-table size="small" ref="multipleTable" :data="tableData" stripe
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="seq_no" label="序号" width="50px"/>
        <el-table-column prop="job_level" label="等级" width="80px"/>
        <el-table-column prop="safe_method" label="详情"/>
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer" style="text-align: right;margin-top:20px;">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";

  export default {
    name: "hotWorkLevel",
    props: ['data'],
    data() {
      return {
        tableData: [],
        multipleSelection: [],
      };
    },
    created() {
      this.onLoad();
    },
    methods: {
      onLoad() {
        let that = this;
        req('get', 'fire/joblevel/template/list', {}).then(res => {
          this.tableData = res.data.data;//表格数据
          if (that.data.job_level) {
            setTimeout(() => {
            let arr = that.data.job_level.split(',');
            this.tableData.forEach(row => {
              if (arr.includes(row.seq_no)) {
                this.$refs.multipleTable.toggleRowSelection(row);
              }
            });
            }, 200);
          }
        }, error => {
          window.console.log(error);
        });
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      submitForm() {
        let form = {};
        let seq_no = [];
        let job_level = [];
        let max = '';
        let data = this.multipleSelection.sort(function (a, b) {
          return a.seq_no - b.seq_no;
        });
        data.map((item) => {
            seq_no.push(item.seq_no);
            job_level.push(item.job_level);
            if (item.job_level === '特级') {
              max = '特级';
            } else if (max !== '特级' && item.job_level === '一级') {
              max = '一级';
            } else if (max !== '特级' && max !== '一级' && item.job_level === '二级') {
              max = '二级';
            }
          }
        );
        form.seq_no = seq_no.join(',');
        // form.job_level = [...new Set(job_level)].join(',');
        form.job_level = max;
        form.max = max;
        this.$emit('getLevel', form);
      },
      cancel() {
        this.$emit('close');
      },
    }
  }
</script>

<style scoped>
</style>
