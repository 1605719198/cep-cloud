<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="110px">
      <el-form-item label="产线代码" prop="millIdCode">
        <el-input v-model="addForm.millIdCode" maxlength="8"/>
      </el-form-item>
      <el-form-item label="产线名称" prop="millIdName">
        <el-input v-model="addForm.millIdName" maxlength="25"/>
      </el-form-item>
      <el-form-item label="成本中心" prop="costCenter">
        <el-autocomplete
          class="inline-input"
          v-model="addForm.costCenter"
          :fetch-suggestions="costCenterSearchAsync"
          placeholder="成本中心"
          @select="handleSelectCostCenter"
          style="width: 90%!important;"
        ></el-autocomplete>
      </el-form-item>
    </el-form>
    <div slot="footer" class="el-dialog__footer">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" @click="handleChange('addForm')" :loading="states">确 定</el-button>
    </div>
  </div>
</template>
<script>
  import {listApplyLike} from "@/api/energy/ee/productionLineCostCenter"

  export default {
    name: "productionLineAdd",
    dicts: ['energy_mill_code'],
    props: ['dataEdit'],
    data() {
      var checkA = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('产线代码栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 8) {
            callback(new Error('产线代码参数长度为 8 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkB = (rule, value, callback) => {
        if (value != null) {
          if (value.length > 50) {
            callback(new Error('产线名称参数长度为 50 ，请重新输入！'));
          } else {
            callback();
          }
        } else {
          return callback(new Error('产线名称栏位为空，请重新输入！'));
        }
      };
      return {
        states: false,
        addForm: {},
        queryParams: {
          costCenter: null
        },
        // 表单校验
        rules: {
          millIdCode: [
            {required: true, message: "产线代码不能为空", trigger: "blur"}
          ],
          millIdName: [
            {required: true, message: "产线名称不能为空", trigger: "blur"}
          ],
          costCenter: [
            {required: true, message: "成本中心不能为空", trigger: "blur"}
          ]
        },
      }
    },
    created() {
    },
    mounted() {
      this.addForm = {...this.dataEdit}
    },
    methods: {
      handleChange(form) {
        this.states = true;
        this.$refs[form].validate((valid) => {
          if (valid) {
            // this.addForm.createEmpNo = this.userInfo.userName;
            this.$emit('submitAdd', this.addForm)
          } else {
            this.states = false;
            return false;
          }
        });
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('close')
      },
      costCenterSearchAsync(queryString, cb) {
        this.loadAacdemyCostCenter(queryString, (data) => {
          let results = queryString ? data.filter(this.costCenterStateFilter(queryString)) : data;
          clearTimeout(this.timeout);
          this.timeout = setTimeout(() => {
            cb(results);
          }, 30);
        })
      },
      costCenterStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1);
        };
      },
      handleSelectCostCenter(item) {
        this.addForm.costCenter = item.name
      },
      loadAacdemyCostCenter(query, fun) {//后台根据用户输入的内容进行模糊查询
        let costCenter = [];
        this.queryParams.costCenter = this.addForm.costCenter
        listApplyLike(this.queryParams).then(response => {
          if (response.data.length > 0) {
            response.data.forEach((item) => {
              costCenter.push({
                name: item.costCenter,
                value: item.costCenterDesc
              })
            })
          } else {
            costCenter.push({
              value: "未找到相关结果",
            })
          }
          fun(costCenter);
        }).catch((error) => {
        })
      },
    }
  }
</script>

<style scoped>
.el-dialog__footer {
  padding: 15px 0 25px;
}
::v-deep .el-form-item__content {
  white-space: nowrap;
}
::v-deep .el-form-item__label {
  text-align: left;
}
::v-deep .el-text{
  color: red;
}
::v-deep .el-input.el-input--suffix{
  width: auto;
}
::v-deep .el-input{
  width: 90%;
}
/*下拉框最后一个显示不完全*/
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
