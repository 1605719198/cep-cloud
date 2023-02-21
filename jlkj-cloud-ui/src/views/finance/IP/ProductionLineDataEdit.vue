<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="left" label-width="100px">
      <el-row :gutter="20">
        <el-col :span="7">
          <div class="el-form-item el-form-item--small">
            <div class="el-form-item__content">
              <el-form-item label="产线代码：" prop="millId">
                <el-input v-model="form.millId" :disabled="inputMillId" style="width: 90%!important;" placeholder="请输入产线代码"/>
              </el-form-item>
            </div>
          </div>
        </el-col>
        <el-col :span="7">
          <div class="el-form-item el-form-item--small">
            <div class="el-form-item__content">
              <el-form-item label="产线名称：" prop="millName">
                <el-input v-model="form.millName"  style="width: 90%!important;" placeholder="请输入产线名称"/>
              </el-form-item>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <div style="min-width: 585px;">
            <el-radio-group v-model="tabPosition" @change="switchRadio">
              <el-radio-button label="SX">属性资料</el-radio-button>
              <el-radio-button label="XT">相关系统</el-radio-button>
              <el-radio-button label="HD">相关活动</el-radio-button>
              <el-radio-button label="CC">产出产副品</el-radio-button>
            </el-radio-group>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <div class="plan_main">
      <keep-alive>
        <productionLineDataSX @finalSX="finalSX" :millId2="this.data.millId" :millName2="this.data.millName" :millId="this.form.millId" :millName="this.form.millName" v-bind="$attrs" v-on="$listeners" :formDataSX.sync="addForm" ref="form1" v-show="tabPosition==='SX'"/>
      </keep-alive>
      <keep-alive>
        <productionLineDataXT :millId="this.form.millId" :millId2="this.data.millId" :millName="this.form.millName" v-bind="$attrs" v-on="$listeners" :formDataXT.sync="addForm" ref="form2" v-show="tabPosition==='XT'"/>
      </keep-alive>
      <keep-alive>
        <productionLineDataHD :millId="this.form.millId" :millId2="this.data.millId" :millName="this.form.millName" v-bind="$attrs" v-on="$listeners" :formDataHD.sync="addForm" ref="form3" v-show="tabPosition==='HD'"/>
      </keep-alive>
      <keep-alive>
        <productionLineDataCC :millId="this.form.millId" :millId2="this.data.millId" :millName="this.form.millName" v-bind="$attrs" v-on="$listeners" :formDataCC.sync="addForm" ref="form4" v-show="tabPosition==='CC'"/>
      </keep-alive>
    </div>
    <div slot="footer" class="el-dialog__footer" v-show="footerButton">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="handleChange('form')">确 定</el-button>
    </div>
  </div>
</template>
<script>
  import productionLineDataSX from "@/views/finance/IP/ProductionLineDataSX";
  import productionLineDataXT from "@/views/finance/IP/ProductionLineDataXT";
  import productionLineDataHD from "@/views/finance/IP/ProductionLineDataHD";
  import productionLineDataCC from "@/views/finance/IP/ProductionLineDataCC";

  export default {
    name: "ProductionLineDataEdit",
    props: ['data','detailBox','editBox','millId'],
    data() {
      return {
        inputMillId: false,
        tabPosition: 'SX',
        form: {
          millName: '',
          millId: '',
        },
        addForm: {
        },
        footerButton: true,
        rules: {
          millName: [{required: true, message: '请输入产线名称', trigger: 'blur'},],
          millId: [{required: true, message: '请输入产线代码', trigger: 'blur'},],
        },
        editIndex: -1,
        attriList:[]//从数据，属性资料
      }
    },
    created() {
    },
    components: {
      productionLineDataSX,
      productionLineDataXT,
      productionLineDataHD,
      productionLineDataCC,
    },
    mounted() {
      this.form = {...this.data}
      if (this.detailBox == true) {
        this.footerButton = false;
      }
      if (this.editBox == true) {
        this.inputMillId = true;
      }
    },
    methods: {
      finalSX(data){
        this.attriList = data;//从数据，属性资料
      },
      switchRadio(index){
        this.tabPosition = index
      },
      handleEdit(index) {
        this.editIndex = index;
      },
      handleDelete(index) {
        delete this.tableData.splice(index, 1)
      },
      handleChange(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.form.attriList = this.attriList//从数据，属性资料
            this.$emit('submitSave', this.form)
          } else {
            return false;
          }
        });
        // const p1 = this.$refs.form1.$refs.formDataSX;
        // // const p2 = this.$refs.form2.$refs.formDataXT;
        //
        // Promise.all([p1].map(this.getFormPromise)).then(res => {
        //   const validateRes = res.every(item => !!item);
        //   if (validateRes) {
        //     this.$message({type: 'success', message: '表单必填项校验通过'});
        //     this.$emit('submitSave', this.form)
        //   } else {
        //     this.$message({type: 'error', message: '表单必填项校验未通过'});
        //     return false;
        //   }
        // });
      },
      getFormPromise(form) {
        this.$nextTick(()=> {
          return new Promise(resolve => {
            form.validate(res => {
              resolve(res)
            })
          })
        })
      },
      cancel() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>
.el-dialog__footer {
  padding: 10px 0px 25px;
}
</style>
