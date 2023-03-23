<template>
  <div class="avue-crud">
    <div class="head-container">
      <div style="text-align: right;padding:0 10px 10px 0;">
        <el-button v-if="data.status !== 3" type="primary" @click="analysisOpen = true">添加作业分析</el-button>
      </div>
      <el-table size="small" :data="analysis_list" stripe v-loading="loading">
        <el-table-column prop="person_name" label="分析人"/>
        <el-table-column prop="analysis_time" label="分析时间"/>
        <el-table-column prop="analysis_area" label="动火分析地点"/>
        <el-table-column label="分析介质名称及分析结果" align="center">
          <el-table-column prop="co_density" label="一氧化碳浓度PPM"/>
          <el-table-column prop="o2_density" label="氧气浓度%"/>
          <el-table-column prop="othre_density" label="可燃气体浓度LEL%"/>
        </el-table-column>
        <el-table-column label="操作" width="100px">
          <template slot-scope="scope">
            <el-button size="mini" plain icon="el-icon-delete" type="text"
                       @click="handleDelete(scope.$index,scope.row)"/>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-if="analysisOpen">
      <el-dialog class="customDialogStyle" title="作业分析" :visible.sync="analysisOpen" width="600px"
                 append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <div class="head-container">
          <el-form :model="analysisForm" ref="analysisForm" :rules="analysisRules" class="analysisForm"
                   label-position="right" label-width="110px" style="padding: 0 10px">
            <el-form-item label="分析人" prop="person_name">
              <el-input readonly="readonly" v-model="analysisForm.person_name"
                        @click.native="treeOpen=true"/>
            </el-form-item>
            <el-form-item label="分析时间" prop="analysis_time">
              <el-date-picker v-model="analysisForm.analysis_time" type="datetime" placeholder="选择分析时间"
                              value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="动火分析地点" prop="analysis_area">
              <el-input v-model="analysisForm.analysis_area"/>
            </el-form-item>
            <el-form-item label="一氧化碳浓度" prop="co_density">
              <el-input v-model="analysisForm.co_density">
                <template slot="append">PPM</template>
              </el-input>
            </el-form-item>
            <el-form-item label="氧气浓度" prop="o2_density">
              <el-input v-model="analysisForm.o2_density">
                <template slot="append">%</template>
              </el-input>
            </el-form-item>
            <el-form-item label="可燃气体浓度" prop="othre_density">
              <el-input v-model="analysisForm.othre_density">
                <template slot="append">%LEL</template>
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="analysisOpen = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>
    <div v-if="treeOpen">
      <personnel-selection v-if="treeOpen" :userDialogVisible="treeOpen" @close-dialog="treeOpen = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
    </div>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";

  export default {
    name: "hotWorkInfoApplicationAnalysis",
    props: ['data'],
    data() {
      const isNum = (rule, value, callback) => {
        const float = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/;
        if (!float.test(value)) {
          callback(new Error('只能为数字'))
        } else {
          callback()
        }
      };
      return {
        treeOpen: false,
        loading: true,
        analysis_list: [],
        //作业分析
        analysisForm: {
          analysis_time: '',
          person_id: '',
          person_name: '',
          analysis_area: '',
          co_density: '',
          o2_density: '',
          othre_density: ''
        },
        analysisRules: {
          analysis_time: [{required: true, message: '请选择分析时间', trigger: 'blur'},],
          person_name: [{required: true, message: '请输入分析人'},],
          analysis_area: [{required: true, message: '请输入动火分析地点', trigger: 'blur'},],
          co_density: [{required: true, message: '请输入一氧化碳浓度', trigger: 'blur'},
            {validator: isNum, trigger: 'blur'}],
          o2_density: [{required: true, message: '请输入氧气浓度', trigger: 'blur'},
            {validator: isNum, trigger: 'blur'}],
          othre_density: [{required: true, message: '请输入可燃气体浓度', trigger: 'blur'},
            {validator: isNum, trigger: 'blur'}],
        },
        analysisOpen: false,
      };
    },
    created() {
      this.onLoad();
    },
    methods: {
      onLoad() {
        if (this.data.id) {
          req('get', 'fire/analysis/list', {id: this.data.id}).then(res => {
            this.analysis_list = res.data.data;//表格数据
            this.data.analysis_list = this.analysis_list;
            this.loading = false;
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        } else {
          this.loading = false;
        }
      },
      submitForm() {
        this.$refs['analysisForm'].validate((valid) => {
          if (valid) {
            if (this.data.id) {
              req('post', 'fire/insert/analysis', {
                ...this.analysisForm,
                job_id: this.data.id
              }).then(() => {
                this.$message({type: "success", message: "操作成功！"});
                this.onLoad();
              }, error => {
                window.console.log(error);
              });
            }
            this.analysis_list.push({...this.analysisForm});
            this.data.analysis_list = this.analysis_list;
            this.analysisOpen = false;
            this.analysisForm = {
              analysis_time: '',
              person_id: '',
              person_name: '',
              analysis_area: '',
              co_density: '',
              o2_density: '',
              othre_density: ''
            }
          }
        });
      },
      handleDelete(index, row) {
        if (this.data.id) {
          req('post', 'fire/delete/analysis', {id: row.id}).then(() => {
            this.$message({type: "success", message: "操作成功！"});
            this.onLoad();
          }, error => {
            window.console.log(error);
          });
        }
        delete this.analysis_list.splice(index, 1);
        this.data.analysis_list = this.analysis_list;
      },
      cancel() {
        this.$emit('close');
      },
      handleNodeClick(data) {
        this.analysisForm.person_id = data.id;
        this.analysisForm.person_name = data.full_name;
        this.treeOpen = false;
      },
    }
  }
</script>

<style>
  .analysisForm .el-input-group__append {
    width: 30px !important;
    text-align: center;
  }

</style>
