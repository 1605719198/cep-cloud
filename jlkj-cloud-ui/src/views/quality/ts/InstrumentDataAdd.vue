<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="100px">
      <el-form-item label="仪器代号" prop="equipNo">
        <el-input v-model="addForm.equipNo" />
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="分析单位" prop="inspectionStationNo">
        <el-select v-model="addForm.inspectionStationNo" :popper-append-to-body="false" placeholder="请选择">
          <el-option
            v-for="item in optionsUnit"
            :key="item.dictLabel"
            :label="item.dictLabel"
            :value="item.dictValue">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="仪器说明" prop="equipDesc">
        <el-input type="textarea"
                  v-model="addForm.equipDesc"
                  maxlength="20"
                  :rows="3"
                  show-word-limit />
      </el-form-item>
      <el-form-item label="传出IP" prop="sendIp">
        <el-input v-model="addForm.sendIp" />
      </el-form-item>
      <el-form-item label="操作类型" prop="workType">
        <el-radio-group v-model="addForm.workType">
          <el-radio
            v-for="item in optionsWorkType"
            :key="item.value"
            :label="item.value"
          >{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="ID接收状态" prop="receiveStatus">
        <el-radio-group v-model="addForm.receiveStatus">
          <el-radio
            v-for="item in optionsOnOff"
            :key="item.value"
            :label="item.label"
          >{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="工作档名" prop="workFileName">
        <el-input v-model="addForm.workFileName" />
      </el-form-item>
      <el-form-item label="接收PORT" prop="receivePort">
        <el-input v-model="addForm.receivePort" />
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="传出PORT" prop="sendPort">
        <el-input v-model="addForm.sendPort" />
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
    <div slot="footer" class="el-dialog__footer" v-show="footerButton">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" @click="handleChange('addForm')" :loading="states1">确 定</el-button>
    </div>
  </div>
</template>
<script>

export default {
  name: "instrumentDataAdd",
  props: ['dataEdit','detailBox'],
  data() {
    var checkA = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('仪器代号栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 10) {
          callback(new Error('仪器代号参数长度为 10 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkB = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 20) {
          callback(new Error('传出IP参数长度为 20 ，请重新输入！'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    var checkC = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 12) {
          callback(new Error('工作档名参数长度为 12 ，请重新输入！'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    var checkD = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('接收PORT栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 10) {
          callback(new Error('接收PORT参数长度为 10 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkE = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 10) {
          callback(new Error('传出PORT参数长度为 10 ，请重新输入！'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      addForm: {
        equipNo: '',
        equipDesc: '',
        workType: '',
        receiveStatus: '',
        workFileName: '',
        sendIp: '',
        sendPort: '',
        receivePort: '',
        createUser: '',
        createTime: '',
        updateUser: '',
        updateTime: '',
        inspectionStationNo: ''
      },
      rules: {
        equipNo: [
          { validator: checkA, trigger: 'blur' }
        ],
        sendIp: [
          { validator: checkB, trigger: 'blur' }
        ],
        workFileName: [
          { validator: checkC, trigger: 'blur' }
        ],
        receivePort: [
          { validator: checkD, trigger: 'blur' }
        ],
        sendPort: [
          { validator: checkE, trigger: 'blur' }
        ],
      },
      optionsOnOff: [{
        value: 'ON',
        label: 'ON'
      }, {
        value: 'OFF',
        label: 'OFF'
      }],
      optionsWorkType: [{
        value: 'A',
        label: 'A-自动'
      }, {
        value: 'M',
        label: 'M-手动'
      }],
      optionsUnit: [],
      tableData: [],
      footerButton: true,
      states1:false
    }
  },
  created() {
    this.getDicts("quality_analysis_unit").then(response =>{
      this.optionsUnit = response.data.data;
    })
  },
  mounted() {
    this.addForm = {...this.dataEdit};
    if (this.addForm.workType == '自动') {
      this.addForm.workType = 'A'
    } else {
      this.addForm.workType = 'M'
    }
    if (this.addForm.receiveStatus == '开') {
      this.addForm.receiveStatus = 'ON'
    } else {
      this.addForm.receiveStatus = 'OFF'
    }
    if (this.detailBox == true) {
      this.footerButton = false;
    }
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
  width: 250px;
}
/deep/ .el-text{
  color: red;
}
/deep/ .el-form-item--feedback{
  display: inline-block;
  padding-left: 5px;
}

/deep/ .el-input.el-input--suffix{
  width: auto;
}
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
.el-dialog__footer {
  padding: 12px 0px 25px;
}
/deep/ .el-form-item__label {
  text-align: left;
  padding-left: 5%;
}
</style>
