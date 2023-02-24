<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="150px">
      <el-form-item label="试验元素代号" prop="elementIndex">
        <el-input v-model="addForm.elementIndex" :disabled="status"/>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="元素名称/试验项目" prop="elementName">
        <el-input v-model="addForm.elementName" />
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="单位" prop="elementUnit">
        <el-input v-model="addForm.elementUnit" />
      </el-form-item>
      <el-form-item label="新增人员" prop="createUser">
        <el-input v-model="addForm.createUser" :disabled="true"/>
      </el-form-item>
      <el-form-item label="新增时间" prop="createTime">
        <el-input v-model="addForm.createTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="修改人员" prop="updateUser">
        <el-input v-model="addForm.updateUser" :disabled="true"/>
      </el-form-item>
      <el-form-item label="修改时间" prop="updateTime">
        <el-input v-model="addForm.updateTime" :disabled="true"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="el-dialog__footer">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" @click="handleChange('addForm')" :loading="states1">确 定</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    name: "elementCodeAdd",
    props: ['dataEdit','status'],
    data() {
      var checkA = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('试验元素代号栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 4) {
            callback(new Error('试验元素代号参数长度为 4 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkB = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('元素名称/试验项目栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 20) {
            callback(new Error('元素名称/试验项目参数长度为 20 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkC = (rule, value, callback) => {
        if (value != null) {
          if (value.length > 10) {
            callback(new Error('单位参数长度为 10 ，请重新输入！'));
          } else {
            callback();
          }
        } else {
          callback();
        }

      };
      return {
        addForm: {
          elementType: '1',
          elementIndex: '',
          elementName: '',
          elementUnit: '',
          createUser: '',
          createTime: '',
          updateUser: '',
          updateTime: '',
          integerNo: '0',
          pointerNo: '0',
        },
        rules: {
          elementIndex: [
            { validator: checkA, trigger: 'blur' }
          ],
          elementName: [
            { validator: checkB, trigger: 'blur' }
          ],
          elementUnit: [
            { validator: checkC, trigger: 'blur' }
          ],
        },
        states1:false,
      }
    },
    created() {
    },
    mounted() {
      this.addForm = {...this.dataEdit}
    },
    methods: {
      handleChange(form) {
        this.states1 = true;
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.$emit('submitAdd', this.addForm)
          } else {
            this.states1 = false;
            return false;
          }
        });
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>
/deep/ .el-form-item__content {
  white-space: nowrap;
}
/deep/ .el-text{
  color: red;
}
/deep/ .el-input.el-input--suffix{
  width: auto;
}
/deep/ .el-input{
  width: 90%;
}
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-form-item__label {
  text-align: left;
  padding-left: 10px;
}
/deep/ .el-dialog__footer {
  padding: 15px 0 25px;
}
</style>
