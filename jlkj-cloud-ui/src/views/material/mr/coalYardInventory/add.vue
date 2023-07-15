<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" label-position="top">
        <el-row :gutter="50">
<!--          <el-col :span="12">-->
<!--            <el-form-item prop="category_name" label="物料类别">-->
<!--              <el-select :popper-append-to-bod="false" class="customSelectStyle" @change="changeSlider"-->
<!--                         v-model="form.category_name" clearable filterable placeholder="选择物料类别" style="width:100%">-->
<!--                <el-option v-for="item in options" :key="item.id" :label="item.category_name"-->
<!--                           :value="item.category_name"/>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item prop="materials_name" label="物料代码">-->
<!--              <el-select :popper-append-to-body="false" class="customSelectStyle" v-model="form.materials_name"-->
<!--                         clearable filterable placeholder="选择物料代码" style="width:100%" @change="select">-->
<!--                <el-option v-for="item in pp" :key="item.id" :label="item.name" :value="item.name"/>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="小煤种" prop="materials_small_code">
              <el-select v-model="form.materials_small_code" style="width:100%;" clearable placeholder="请选择小煤种">
                <el-option v-for="item in selectSmallCoalType" :key="item.id" :label="item.materials_small_name"
                           :value="item.materials_small_code"
                           @click.native="changeSmallCoalType(item)"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="storage_spaces_name" label="储位名称">
              <el-select :popper-append-to-body="false" class="customSelectStyle" v-model="form.storage_spaces_name"
                         clearable filterable placeholder="选择储位" style="width:100%" @change="change">
                <el-option v-for="item in storageSpacesOptions" :key="item.id" :label="item.name" :value="item.name"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="inventory" label="库存量">
              <el-input v-model="form.inventory" @input="changeInput" placeholder="输入内容" style="width:100%">
                <i slot="suffix">kg</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading" type="primary" @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
  import {listMaterialsCategoryBox} from "@/api/material/mr/parameter/materialType";
  import { listMaterialsStorageSpacesBox } from '@/api/material/mr/parameter/storeLocation'
  import {listMaterialsBoxM, getMaterialsCodeSmallDic} from "@/api/material/mr/parameter/materialCode";
  import {addMaterialsCoalStock} from "@/api/material/mr/coalYardInventory/index";

  export default {
    props: ['storageSpacesOptions'],
    data() {
      return {
        form: {
          inventory: '',
          category_id: '',
          category_name: '',
          materials_id: '',
          materials_name: '',
          materials_small_code: '',
          materials_small_name: '',
          storage_spaces_id: '',
          storage_spaces_name: '',
        },
        submitLoading: false,
        //表单校验
        rules: {
          // inventory: [{required: true, message: '物资品名不能为空', trigger: 'blur'}],
          // category_name: [{required: true, message: '物料类别不能为空', trigger: 'blur'}],
          // materials_name: [{required: true, message: '物料代码不能为空', trigger: 'blur'}],
          materials_small_code: [{required: true, message: '小煤种不能为空', trigger: 'blur'}],
          storage_spaces_name: [{required: true, message: '储位名称不能为空', trigger: 'blur'}],
        },
        options: [],
        materialsOptions: [],
        selectSmallCoalType: [],

        pp: [],
        dd: []
      }
    },
    created() {
      this.onLoad();
      //   this.form = { ...this.data }
      getMaterialsCodeSmallDic().then(res => {
        this.selectSmallCoalType = res.data.data;//表格数据
      }, error => {
      });
    },
    computed: {
    },
    methods: {
      //获取类别名称
      onLoad() {
        //   this.table.loading = true;//加载状态
        listMaterialsCategoryBox().then(res => {
          this.options = []
          let data = res
          data.map(i => {
            if (i.mtrlType == '00') {
              this.options.push(i)
            }
          })
        }, error => {
          this.table.loading = false;
        });
        listMaterialsBoxM().then((res) => {
          let options = []
          res.forEach(item => {
            let i = {
              id: item.materials_code,
              name: item.materials_name,
              category_id: item.category_id,
            };
            options.push(i)
          });
          this.materialsOptions = options
        })
      },
      //  限制只有两位小数的正整数
      changeInput() {
        this.form.inventory = this.form.inventory.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
        this.form.inventory = this.form.inventory.replace(/^\./g, ""); //验证第一个字符是数字而不是.
        this.form.inventory = this.form.inventory.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
        this.form.inventory = this.form.inventory.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//只允许输入一个小数点
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.form.modify_user_id = this.$store.getters.userInfo.userId;
            this.form.modify_user_name = this.$store.getters.userInfo.nickName;
            this.submitLoading = true;
            addMaterialsCoalStock(this.form).then(res => {
              if (res.code == 200) {
                this.$message({type: "success", message: res.msg});
                this.$emit('submitSave', res.msg)
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
            });
          }
        });
      },
      cancel() {
        this.$emit('close')
      },
      changeSlider(val) {
        this.options.find((item) => {
          if (item.category_name === val) {
            this.dd = item.id;
          }
        });
        this.pp = []
        for (var j = 0; j < this.materialsOptions.length; j++) {
          if (this.dd == this.materialsOptions[j].category_id) {
            this.pp.push(this.materialsOptions[j])
          }
        }
      },
      change(val) {
        this.storageSpacesOptions.find((item) => {
          if (item.name === val) {
            this.form.storage_spaces_id = item.id;
          }
        })
      },
      select(val) {
        this.pp.find((item) => {
          if (item.name === val) {
            this.form.materials_id = item.id;
          }
        })
      },
      changeSmallCoalType(item) {
        this.form.category_id = item.category_code;
        this.form.category_name = item.category_name;
        this.form.materials_id = item.materials_code;
        this.form.materials_name = item.materials_name;
        this.form.materials_small_code = item.materials_small_code;
        this.form.materials_small_name = item.materials_small_name;
      },

    }
  }
</script>

<style scoped lang="scss">
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

.el-input--suffix .el-input__inner {
  padding-right: 60px;
}

.el-col {
  margin-bottom: 0px;
  /* margin-bottom: 8px; */
}

.el-dialog__wrapper {
  ::v-deep.el-col {
    margin-bottom: 0px;
    /* margin-bottom: 8px; */
  }
}
</style>
