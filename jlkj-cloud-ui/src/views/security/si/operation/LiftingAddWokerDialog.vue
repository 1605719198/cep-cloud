<template>
  <div>
    <el-form :model="mForm" status-icon :rules="rules" ref="mForm" class="demo-ruleForm" label-position="left">
      <el-row>
        <el-col>
          <el-form-item label="作业人" label-width="100px" prop="person_name">
            <select-tree v-model="mForm.person_id" placeholder="请选择作业人" style="width: 100%"
                         :selectForm="selectForm"
                         :data="personData"
                         :validate="{flag:'U'}"
                         :isSelectNode="false"
                         :isClearable="true"
                         :isAccordion="true"
                         :isNodeChildrenNull="true"
                         :isFilterable="true"
                         @change="getConstructor">
              <template v-slot:after="data">
                {{
                  data.data.work_type_name && '['+data.data.work_type_name+']'
                }}
              </template>
            </select-tree>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="所属单位名称" label-width="100px">
            <el-input style="pointer-events: none" placeholder="所属单位名称" v-model="mForm.construction_unit_name"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="所属单位类型" label-width="100px">
            <el-input style="pointer-events: none" placeholder="所属单位类型" v-model="mForm.typeText"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="特种工作工种" label-width="100px">
            <el-input style="pointer-events: none" placeholder="特种工作工种" v-model="mForm.work_type_name"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="特种工作证号" label-width="100px">
            <el-input style="pointer-events: none" placeholder="特种工作证号" v-model="mForm.work_card_code"/>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <div class="el-dialog__footer" style="padding:20px 0 0 0;">
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancelClick">取 消</el-button>
        <el-button type="primary" @click="addWorker('mForm')" size="small">确 定</el-button>
      </span>
    </div>
  </div>
</template>

<script>
import {getConstructorTree, getConstructorInfo} from '@/api/security/si/WorkTicketApi'
export default {
  name: "LiftingAddWokerDialog",
  data(){
    return {
      loading:false ,
      mForm:{
        construction_unit_name:'',
        person_id:'',
        person_name:'',
        person_type:0,
        type:1,
        typeText:'',
        work_card_code:'',
        work_type_name:'',
      },
      rules:{
        person_name:[{required:true, message:'作业人不能为空',trigger: 'blur'}],
      },
      selectForm:{value: '', label: ''},
      personData:[]
    }
  },
  mounted() {
    this.getConstructorTree();
  },
  methods:{
    cancelClick(){
      this.$emit('close');
    },
    getWorker(obj){
      this.mForm.person_name = obj.label ;
      this.mForm.person_id = obj.value ;
    },
    addWorker(formName){
      this.$refs[formName].validate((valid) => {
        if (valid){
          this.$emit("get-worker-info", this.mForm);
        }else {
          return false;
        }
      });
    },
    getConstructorTree(){
      getConstructorTree().then(response=>{
        // console.log(response);
        this.updateConstructorTree(response.data.data, {id: ""}, (res) => {
          this.personData = [...res];
        });
      }, error=>{
        console.log(error) ;
      });
    },
    getConstructorInfoByID(id){
      getConstructorInfo(id).then(response=>{
        if (response.data.data.length > 0){
          this.mForm = {...response.data.data[0]};
          if (this.mForm.type === 1){
            this.mForm.typeText = '本厂';
          }else {
            this.mForm.typeText = '外委' ;
          }
          //返回的数据里面没有person_type 这里临时先添加一下
          this.mForm['person_type'] = 0;
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
    getConstructor(obj) {
      if (obj.label) {
        this.getConstructorInfoByID(obj.value);
      }


    },
  },
  watch:{
    'mForm.type'(newValue) {
      if (newValue === '1'){
        this.mForm.typeText = '本厂' ;
      }else {
        this.mForm.typeText = '外委' ;
      }
    },
  }
}
</script>

<style scoped>
.el-form-item{
  margin-bottom:17px;
}
</style>
