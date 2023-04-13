<template>
  <div>
    <el-form :model="erpForm" :rules="rules" ref="erpForm">
      <el-row>
        <el-col :span="24">
          <el-form-item label="部门" prop="organization_code">
<!--            <selectDeptUserTree style="width: 100%" v-model="erpForm.organization_code"-->
<!--                                :selectForm="deptForm"-->
<!--                                :isSelectNode="true"-->
<!--                                :isOnlyDept="true"-->
<!--                                :isClearable="true"-->
<!--                                :isFilterable="true"-->
<!--                                placeholder="请选择部门"-->
<!--                                @change="getDept"/>-->

            <select-tree v-model="erpForm.organization_code" placeholder="请选择部门" :selectForm="deptForm" :data="deptData"
                         :otherForm="['flag']" :isSelectNode="true" :isClearable="true" :isAccordion="true"
                         :isFilterable="true"
                         :isNodeChildrenNull="true" @change="getDept" style="width: 100%"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="料号" prop="matrlnoA">
            <el-select :popper-append-to-body="false" style="width: 100%"
                       class="customSelectStyle"
                       v-model="erpForm.matrlnoA"
                       clearable
                       filterable
                       placeholder="选择物料代码">
              <el-option v-for="item in materialsOptions"
                         :key="item.id"
                         :label="item.name"
                         :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="储位" prop="stgNoA">
<!--            <el-select :popper-append-to-body="false" style="width: 100%"-->
<!--                       class="customSelectStyle"-->
<!--                       v-model="erpForm.stgNoA"-->
<!--                       clearable-->
<!--                       filterable-->
<!--                       placeholder="选择储位">-->
<!--              <el-option v-for="item in storageSpacesOptions"-->
<!--                         :key="item.id"-->
<!--                         :label="item.name"-->
<!--                         :value="item.name">-->
<!--              </el-option>-->
<!--            </el-select>-->
            <el-input v-model="erpForm.stgNoA" readonly disabled/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="重量" prop="qty">
            <el-input v-model="erpForm.qty" style="width:100%" placeholder="请输入重量">
              <template slot="append">kg</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="水分" prop="waterRate">
            <el-input v-model="erpForm.waterRate" style="width:100%" placeholder="请输入水分 (没有水分为-1)"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <div class="el-dialog__footer" style="padding: 0; padding-top:0px;  padding-bottom: 25px;">
          <span slot="footer" class="dialog-footer">
              <el-button @click="handleCancel">取 消</el-button>
              <el-button type="primary" @click="handleChange('erpForm')">确 定
              </el-button>
          </span>
      </div>
    </el-form>
  </div>
</template>

<script>
import selectDeptUserTree from "@/views/components/human/selectDeptUserTree";
import {send} from "@/api/production/oi/actual/coal/MaterialLoadingPerformanceApi";
import {listMaterialsBoxM} from "@/api/material/mr/parameter/materialCode";
import {listMaterialsStorageSpacesBox} from "@/api/material/mr/parameter/storeLocation";
import {getHumanTree} from "@/api/human/hm/humanResourceTree";

export default {
  name: "CoalingActualERPEditor",
  components: {
    selectDeptUserTree
  },
  data(){
    var checkValue = (rule, value, callback) =>{
      const float = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/;
      if (!float.test(value)) {
        callback(new Error('只能为数字'));
      } else {
        let numStr = value.toString();
        let strArray  = numStr.split(".");
        if (strArray.length > 1){
          if (strArray[1].length > 2){
            callback(new Error('只能保留两位小数'));
          }else {
            callback();
          }
        }else {
          callback();
        }
      }
    }
    var checkWeightValue = (rule, value, callback) =>{
      const float = /^(0|([1-9]\d*))(\.\d+)?$/;
      if (!float.test(value)) {
        callback(new Error('只能为正数'));
      } else {
        callback();
      }
    }
    return{
      rules: {
        organization_code: [{ required: true, message: '请选择部门', trigger: 'blur' }],
        matrlnoA: [{ required: true, message: '请填写料号', trigger: 'blur' }],
        stgNoA: [{ required: true, message: '请填写储位', trigger: 'blur' }],
        qty: [{ required: true, message: '请填写重量(整数)', trigger: 'blur' },{validator:checkWeightValue , trigger: 'blur'}],
        waterRate: [{ required: true, message: '请填写水分(保留两位小数)', trigger: 'blur' },{validator:checkValue , trigger: 'blur'}],
      },
      deptForm: { value: '', label: '' },
      erpForm:{
        organization_code :'',
        matrlnoA:'',
        stgNoA:'WK0100',
        qty:'',
        waterRate:-1
      },
      materialsOptions:[],
      storageSpacesOptions:[],
      deptData: [],
    }
  },

  methods:{
    handleCancel(){
      this.$emit("close");
    },

    handleChange(form){
      this.$refs[form].validate((valid) => {
        if (valid) {
          send(this.erpForm).then(res => {
            if (res.code == 200){
              this.$message.success(res.msg);
              setTimeout(()=>{
                this.$emit("close");
              },1000);
            }
          }, err => {
            window.console.log(err);
          });
        } else {
          return false;
        }
      });
    },

    getDept(obj) {
      this.erpForm.organization_code = obj.value;
    },
    //获取物料代码下拉列表
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        // console.log(res)
        let options = []
        res.forEach(item => {
          let i = {
            id: item.materials_code,
            name: item.materials_name
          }
          options.push(i);
        });
        this.materialsOptions = options
      })
    },
    // 获取物料储位下拉列表
    getSpacesOptions () {
      const param = {}
      listMaterialsStorageSpacesBox(param).then((res) => {
        // console.log(res)
        let options = []
        res.forEach(item => {
          let i = {
            id: item.id,
            name: item.storage_spaces_name
          }
          options.push(i)
        });
        this.storageSpacesOptions = options
      })
    },

    //递归过滤数据，将部门筛选出来
    updateDeptTreeData: function (data, callback) {
      let that = this;
      let list = [];
      data.forEach(function (obj) {
        if (obj.flag === 'dept') {
          if (obj.children) {
            let children = [];
            that.updateDeptTreeData(obj.children, (res) => {
              children = [...res];
            });
            list.push({...obj, children: children})
          } else {
            list.push({...obj})
          }
        }
      });
      callback(list);
    },
  },

  mounted() {
    getHumanTree().then(res => {
      this.userData = res.data.data;
      this.updateDeptTreeData(res.data.data, (res) => {
        this.deptData = [...res];
      });
    }, error => {
      window.console.log(error);
    });


    this.getMaterialsOptions();
    this.getSpacesOptions();
  }
}
</script>

<style scoped>

</style>
