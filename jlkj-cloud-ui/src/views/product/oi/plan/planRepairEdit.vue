<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="top" label-width="100px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="计划编号" prop="plan_code">
            <el-input v-model="form.plan_code" :disabled="true"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开始时间" prop="plan_start_date">
            <el-date-picker v-model="form.plan_start_date" type="date" placeholder="选择开始时间"
                            value-format="yyyy-MM-dd HH:mm:ss"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门" prop="department_id">
            <!--            <el-select v-model="form.department_id" placeholder="请选择部门">-->
            <!--              <el-option v-for="item in selectDepartment" :key="item.value" :label="item.label" :value="item.value"/>-->
            <!--            </el-select>-->
            <!--            <selectDeptUserTree v-model="form.department_id" :selectForm="deptForm" :isSelectNode="true"-->
            <!--                                :isOnlyDept="true" :isClearable="true" :isFilterable="true"-->
            <!--                                placeholder="请选择部门" @change="getDept"/>-->
            <select-tree v-model="form.department_id" placeholder="请选择部门" :selectForm="deptForm" :data="deptData"
                         :otherForm="['flag']" :isSelectNode="true" :isClearable="true" :isAccordion="true"
                         :isFilterable="true"
                         :isNodeChildrenNull="true" @change="getDept"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结束时间" prop="plan_end_date">
            <el-date-picker v-model="form.plan_end_date" type="date" placeholder="选择结束时间"
                            value-format="yyyy-MM-dd HH:mm:ss"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备名称" prop="equipment_name">
            <el-input readonly="readonly" v-model="form.equipment_name" @click.native="treeOpen2=true" placeholder="请选择设备"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="主体责任人" prop="person_main_liable_name">
            <el-input readonly="readonly" v-model="form.person_main_liable_name" placeholder="请选择主体责任人"
                      @click.native="treeOpen=true,treeOpenType='person_main_liable'"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="维修类型" prop="repair_type_id">
            <el-select v-model="form.repair_type_id" placeholder="请选择维修类型">
              <el-option v-for="item in selectRepairType" :key="item.value" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="专业责任人" prop="person_profession_liable_name">
            <el-input readonly="readonly" v-model="form.person_profession_liable_name" placeholder="请选择专业责任人"
                      @click.native="treeOpen=true,treeOpenType='person_profession_liable'"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="维修内容" prop="repair_content">
            <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="form.repair_content"/>
          </el-form-item>
        </el-col>
        <el-col :span="24" style="margin-top: 10px;line-height: 40px">
          <el-row>
            <el-col :span="12">
              <el-col :span="7"><label class="required">影响产量</label></el-col>
              <el-col :span="17">
                <el-form-item label="" prop="is_affect_output">
                  <el-radio-group v-model="form.is_affect_output">
                    <el-radio-button label="0">未影响</el-radio-button>
                    <el-radio-button label="1">&nbsp;影&nbsp;&nbsp;响&nbsp;</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-col :span="7"><label class="required">预提醒天数</label></el-col>
              <el-col :span="17">
                <el-form-item label="" prop="remind_days">
                  <el-input-number v-model="form.remind_days" controls-position="right" :min="0"/>
                </el-form-item>
              </el-col>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
    <div class="el-dialog__footer">
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>
    <div v-if="treeOpen">
      <personnel-selection v-if="treeOpen" :userDialogVisible="treeOpen" @close-dialog="treeOpen = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
    </div>
    <div v-if="treeOpen2">
      <equipment-selection v-if="treeOpen2" :userDialogVisible="treeOpen2" @close-dialog="treeOpen2 = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick2"/>
    </div>
  </div>
</template>
<script>
  import {req} from "@/api/production/oi/common";
  import {getHumanTree} from "@/api/human/hm/humanResourceTree";

  export default {
    name: "planRepairEdit",
    props: ['data', 'type'],
    data() {
      let validateStartTime = (rule, value, callback) => {
        if (value) {
          if (this.form.plan_end_date) {
            let times = this.compareDate(value, this.form.plan_end_date);
            if (times > 0) {
              callback();
            } else if (times === 0) {
              callback();
              // callback(new Error("开始时间和结束时间不能相同"));
            } else {
              callback(new Error("起始日期不能再结束日期之后，请重新选择"));
            }
          }
        }
      };
      let validateEndTime = (rule, value, callback) => {
        if (value) {
          if (this.form.plan_start_date) {
            let times = this.compareDate(this.form.plan_start_date, value);
            if (times > 0) {
              callback();
            } else if (times === 0) {
              callback();
              // callback(new Error("开始时间和结束时间不能相同"));
            } else {
              callback(new Error("结束日期不能在起始日期前，请重新选择"));
            }
          }
        }
      };
      return {
        deptData: [],
        userData: [],
        selectEquipment: [
          {value: '1', label: '设备1'},
          {value: '2', label: '设备2'},
        ],
        selectRepairType: [
          {value: 1, label: '大修'}, {value: 2, label: '中修'}, {value: 3, label: '小修'}, {value: 4, label: '临修'},
          {value: 5, label: '设备消缺'}, {value: 6, label: '工艺技改'}, {value: 7, label: '零星修缮'}, {value: 8, label: '环保项目'},
        ],
        deptForm: {value: '', label: ''},
        mainForm: {value: '', label: ''},
        professionForm: {value: '', label: ''},
        form: {
          department_id: '',
          repair_type_id: '',
          person_main_liable_id: '',
          person_main_liable_name: '',
          person_profession_liable_id: '',
          person_profession_liable_name: '',
          equipment_number: '',
          equipment_name: '',
          plan_start_date: '',
          plan_end_date: '',
          repair_content: '',
          is_affect_output: '',
        },
        rules: {
          department_id: [{required: true, message: '请选择部门', trigger: 'blur'},],
          plan_start_date: [{required: true, message: '请输入开始时间', trigger: 'blur'},
            {validator: validateStartTime, trigger: 'blur'}],
          plan_end_date: [{required: true, message: '请输入结束时间', trigger: 'blur'},
            {validator: validateEndTime, trigger: 'blur'}],
          equipment_name: [{required: true, message: '请选择设备'},],
          person_main_liable_name: [{required: true, message: '请选择主体责任人'},],
          person_profession_liable_name: [{required: true, message: '请选择专业责任人'},],
          repair_type_id: [{required: true, message: '请选择维修类型', trigger: 'blur'},],
          repair_content: [{required: true, message: '请输入维修内容', trigger: 'blur'},],
          is_affect_output: [{required: true, message: '请选择是否影响产量', trigger: 'blur'},],
          remind_days: [{required: true, message: '请输入预提醒天数'},],
        },
        submitLoading: false,
        treeData: [], treeOpen: false, treeOpenType: '',
        treeOpen2: false,
      }
    },
    computed: {
    },
    created() {
      getHumanTree().then(res => {
        this.userData = res.data;
        this.updateDeptTreeData(res.data, (res) => {
          this.deptData = [...res];
        });
      }, error => {
        window.console.log(error);
      });
    },
    mounted() {
      this.form.plan_code = this.$moment().format("yyyyMMDDHHmmssSSS");
      if (this.data.id) {
        this.form = {...this.data};
      }
    },
    methods: {
      handleDelete(index) {
        delete this.tableData.splice(index, 1)
      },
      handleChange(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.submitLoading = true;
            let data = {
              plan_code: this.form.plan_code,
              department_id: this.form.department_id,
              repair_type_id: this.form.repair_type_id,
              person_main_liable_id: this.form.person_main_liable_id,
              person_main_liable_name: this.form.person_main_liable_name,
              person_profession_liable_id: this.form.person_profession_liable_id,
              person_profession_liable_name: this.form.person_profession_liable_name,
              equipment_number: this.form.equipment_number,
              equipment_name: this.form.equipment_name,
              plan_start_date: this.form.plan_start_date,
              plan_end_date: this.form.plan_end_date,
              repair_content: this.form.repair_content,
              is_affect_output: this.form.is_affect_output,
              remind_days: this.form.remind_days,
            };
            if (this.type === 'add') {
              req('post', 'RepairPlan/add', {
                ...data,
                create_user_id: this.$store.getters.userInfo.userId,
                create_user_name: this.$store.getters.userInfo.nickName,
              }).then(res => {
                if (res.code === 200) {
                  this.$message({
                    type: "success", message: "操作成功！", duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.msg)
                    }
                  });
                }
                this.submitLoading = false;
              }, error => {
                this.submitLoading = false;
                window.console.log(error);
              });
            } else if (this.type === 'edit') {
              req('post', 'RepairPlan/edit', {
                id: this.form.id,
                ...data,
                modify_user_id: this.$store.getters.userInfo.userId,
                modify_user_name: this.$store.getters.userInfo.nickName,
              }).then(res => {
                if (res.code === 200) {
                  this.$message({
                    type: "success", message: "操作成功！", duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.msg)
                    }
                  });
                }
                this.submitLoading = false;
              }, error => {
                this.submitLoading = false;
                window.console.log(error);
              });
            }
          } else {
            return false;
          }
        });
      },
      cancel() {
        this.$emit('close')
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
      getDept(obj) {
        this.form.department_id = obj.value;
      },
      getEquipmentName(label) {
        this.form.equipment_name = label;
      },
      handleNodeClick(data) {
        if (this.treeOpenType === 'person_profession_liable') {
          this.form.person_profession_liable_id = data.id;
          this.form.person_profession_liable_name = data.full_name;
        } else if (this.treeOpenType === 'person_main_liable') {
          this.form.person_main_liable_id = data.id;
          this.form.person_main_liable_name = data.full_name;
        }
        this.treeOpen = false;
        this.treeOpenType = '';
      },
      handleNodeClick2(data) {
        // console.log(data)
        this.form.equipment_number = data.id;
        this.form.equipment_name = data.equipmentName;
        this.treeOpen2 = false;
        this.treeOpenType = '';
      },
      // 比较两个日期
      compareDate(start, end) {
        return new Date(end).getTime() - new Date(start).getTime();
      },
    },
    watch: {
      'form.department_id'(newValue) {
        this.deptForm.value = newValue;
      },
      'form.person_main_liable_id'(newValue) {
        this.mainForm.value = newValue;
      },
      'form.person_profession_liable_id'(newValue) {
        this.professionForm.value = newValue;
      },
    }
  }
</script>

<style scoped>
  .el-form--label-top .el-form-item__label {
    padding: 0px;
  }

  .el-form-item {
    margin-bottom: 0;
  }

  .required:before {
    content: '*';
    color: #F56C6C;
    margin-right: 4px;
  }

  .el-dialog__footer {
    padding: 0;
    margin-top: 10px;
    margin-bottom: 20px;
  }
</style>
