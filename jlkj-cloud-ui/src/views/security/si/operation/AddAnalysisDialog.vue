<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="right" label-width="140px">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="氧含量（%）" prop="oxygen_content">
            <el-input v-model="form.oxygen_content"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="可燃气体（%LEL）" prop="combustible_gas">
            <el-input v-model="form.combustible_gas"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="有毒气体（PPM）" prop="toxic_medium">
            <el-input v-model="form.toxic_medium"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="分析人"  prop="person_name">
            <select-tree style="width: 100%" v-model="form.person_name" placeholder="分析人"
                         :select-form="selectForm"
                         :data="personData"
                         :validate="{flag:'U'}"
                         :isSelectNode="false"
                         :isClearable="true"
                         :isAccordion="true"
                         :isNodeChildrenNull="true"
                         :isFilterable="true"
                         @change="getWorker"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="采样分析时间" prop="analysis_time">
            <el-date-picker
              v-model="form.analysis_time"
              placeholder="选择时间"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="el-dialog__footer">
    <span slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="handleChange('form')">确 定</el-button>
    </span>
    </div>
  </div>
</template>
<script>
import {getConstructorInfo, getConstructorTree} from "@/api/security/si/WorkTicketApi";

export default {
  name: "AddAnalysisDialog",
  props: ['data'],
  data() {
    var checkValue = (rule, value, callback) =>{
      const float = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/;
      if (!float.test(value)) {
        callback(new Error('只能为数字'))
      } else {
        callback()
      }
    }
    return {
      form: {
        analysis_time: '',
        combustible_gas: '',
        oxygen_content: '',
        person_id: '',
        person_name: '',
        toxic_medium:'',
      },
      rules: {
        oxygen_content: [{required: true, message: '请输入氧含量', trigger: 'blur'},{validator:checkValue , trigger: 'blur'}],
        combustible_gas: [{required: true, message: '请输入可燃气体', trigger: 'blur'},{validator:checkValue , trigger: 'blur'}],
        toxic_medium: [{required: true, message: '请输入有毒气体', trigger: 'blur'},{validator:checkValue , trigger: 'blur'}],
        person_name:[{required: true, message: '请输入分析人', trigger: 'blur'},],
        analysis_time: [{required: true, message: '请输入分析时间', trigger: 'blur'},],

      },
      personData:[],
      selectForm:{label:'', value:''}
    }
  },
  mounted() {
    this.form = {...this.data}
    this.getConstructorTree();
  },
  methods: {
    handleChange(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.$emit('get-worker-info', this.form);
        } else {
          return false;
        }
      });
    },
    cancel() {
      this.$emit('close')
    },
    getConstructorTree(){
      getConstructorTree().then(response=>{
        this.updateConstructorTree(response.data.data, {id: ""}, (res) => {
          this.personData = [...res];
        });
      }, error=>{
        console.log(error) ;
      });
    },

    //去掉人员ID前面的字母
    deleteIDPrefix(preID){
      if (preID !== undefined && preID !== ''){
        return preID.substring(2);
      }
      return '' ;
    },

    getWorker(obj){
      this.form.person_name = obj.label ;
      this.getConstructorInfoByID(obj.value);
    },

    getConstructorInfoByID(id){
      getConstructorInfo(id).then(response=>{
        if (response.data.data.length > 0){
          let type = response.data.data[0].type;
          //如果是本厂的取person_id, 如果是外委的取id, 外委没有person_id
          if (type === 1){
            this.form.person_id = response.data.data[0].person_id;
          }else if (type === 2){
            this.form.person_id = response.data.data[0].id;
          }
        }
      }, error => {
        console.log(error)
      })
    },

    updateConstructorTree: function (data, pObj, callback) {
      let that = this;
      let list = [];
      data.forEach(function (obj) {
        if (obj.pid === pObj.id) {
          that.updateConstructorTree(data, obj, (res) => {
            list.push({
              id: obj.id.slice(2),
              label: obj.label,
              work_type_name: obj.work_type_name,
              flag: obj.id.slice(0, 1) === 'P' ? 'U' : obj.id.slice(0, 1),
              children: [...res]
            });
          });
        }
      });
      callback(list);
    },
  }
}
</script>

<style scoped>
::v-deep input::-webkit-outer-spin-button,
::v-deep input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
}
::v-deep input[type='number'] {
  -moz-appearance: textfield !important;
}
</style>
