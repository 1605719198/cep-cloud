<template>
  <div class="avue-crud">
    <div class="head-container">
      <el-form :model="personForm" ref="personForm" :rules="rules" label-position="right" label-width="120px"
               style="padding: 0 10px">
        <el-form-item label="作业人" prop="person_name">
          <select-tree v-model="personForm.person_id" placeholder="请选择作业人"
                       :selectForm="selectForm" :data="personData"
                       :validate="{flag:'U'}"
                       :isSelectNode="false" :isClearable="true" :isAccordion="true" :isNodeChildrenNull="true"
                       :isFilterable="true"
                       @change="getConstructor">
            <template v-slot:after="data">
              {{
              data.data.work_type_name && '['+data.data.work_type_name+']'
              }}
            </template>
          </select-tree>
        </el-form-item>
        <el-form-item label="所属单位名称" prop="construction_unit_name">
          <el-input placeholder="所属单位名称" v-model="personForm.construction_unit_name" class="disabled"/>
        </el-form-item>
        <el-form-item label="所属单位类型" prop="type">
          <el-input placeholder="所属单位类型" v-model="personForm.typeName" class="disabled"/>
        </el-form-item>
        <el-form-item label="特种工作工种" prop="work_type_name">
          <el-input placeholder="特种工作工种" v-model="personForm.work_type_name" class="disabled"/>
        </el-form-item>
        <el-form-item label="特种工作证号" prop="work_card_code">
          <el-input placeholder="特种工作证号" v-model="personForm.work_card_code" class="disabled"/>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer" style="text-align: right;padding: 10px 40px 40px 40px ;">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
  import {getConstructorTree, getConstructorInfo} from '@/api/security/si/WorkTicketApi'

  export default {
    name: "operatorAdd",
    props: ['personList'],
    data() {
      return {
        personForm: {
          construction_unit_name: '',
          person_id: '',
          person_name: '',
          work_card_code: '',
          type: '',
          typeName: '',
          work_type_name: '',
        },
        rules: {
          person_name: [{required: true, message: '作业人不能为空', trigger: 'blur'}],
        },
        selectForm: {value: '', label: ''},
        personData: []
      }
    },
    mounted() {
      getConstructorTree().then(res => {
        this.updateConstructorTree(res.data.data, {id: ""}, (res) => {
          this.personData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
    },
    methods: {
      cancel() {
        this.$emit('close');
      },
      submitForm() {
        this.$refs['personForm'].validate((valid) => {
          if (valid) {
            let arr = this.personList.map((item) => {
              return item.id
            });
            if (arr.includes(this.personForm.id)) {
              this.$message({type: "error", message: "此作业人已存在！"});
            } else {
              this.$emit("setPersonForm", this.personForm);
            }
          }
        });
      },
      getConstructorInfoByID(id) {
        getConstructorInfo(id).then(response => {
          if (response.data.data.length > 0) {
            let data = response.data.data[0];
            this.personForm = data;
            this.personForm.typeName = data.type === 1 ? '本厂' : '外委';
          }
        }, error => {
          window.console.log(error);
        })
      },
      getConstructor(obj) {
        if (obj.label) {
          this.getConstructorInfoByID(obj.value);
        }


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

    },
  }
</script>

<style scoped>
  .disabled {
    pointer-events: none
  }
</style>
