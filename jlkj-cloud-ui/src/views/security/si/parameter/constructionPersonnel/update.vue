<template>
  <div  style="padding-top:20px;">
    <div>
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-row>
          <el-col :span="12" class="aaa">
            <el-form-item label="所属单位"
                          prop="construction_unit_id">
              <select-tree style="width: 100%;"
                           v-model="form.construction_unit_id"
                           placeholder="请选择所属单位"
                           :selectForm="deptForm"
                           :data="deptData"
                           :otherForm="['type']"
                           :validate="{flag:'indep'}"
                           :isDisabled="type=='edit'?true:false"
                           :isSelectNode="true"
                           :isClearable="true"
                           :isAccordion="true"
                           :isFilterable="true"
                           :isNodeChildrenNull="true"
                           @change="getDept" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.type==1"
                          prop="person_id"
                          label="人员姓名">
              <el-select class="customSelectStyle"
                         style="width: 100%;"
                         @change="changePerson"
                         :popper-append-to-body="false"
                         v-model="form.person_id"
                         filterable
                         clearable
                         placeholder="请选择">
                <el-option v-for="item in userOptions"
                           :key="item.id"
                           :label="item.label"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-else
                          prop="person_name"
                          label="人员姓名">
              <el-input v-model="form.person_name"
                        placeholder="请输入人员姓名"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="isDuty"
                          label="是否负责人">
              <el-switch v-model="form.is_leader"
                         :active-value="1"
                         :inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="work_card_code"
                          label="作业证编号">
              <el-input v-model="form.work_card_code"
                        placeholder="请输入作业证编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="el1">
            <el-form-item label="工种"
                          prop="work_type_name">
              <el-select class="customSelectStyle"
                         style="width: 100%;"
                         :popper-append-to-body="false"
                         v-model="form.work_type_name"
                         clearable
                         placeholder="请选择工种">
                <el-option v-for="item in occupationOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { constructionunitpersonsInsert, constructionunitpersonsUpdate } from "@/api/security/si/parameter/constructionPersonnel";
import { getOrganizationUserList } from '@/api/human/hm/humanResourceTree'
import { getconstructionunitTree, getWorktype } from "@/api/security/si/WorkTicketApi"
import { treeDataformat } from "@/utils/data"
import { mapGetters } from "vuex";
export default {
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {
        //1本厂2外委
        type: '',
        //人员id
        person_id: '',
        //人员名
        person_name: '',
        //是否是负责人
        is_leader: 0,
        isDuty: false,
      },
      // 表单校验
      rules: {
        construction_unit_id: [{ required: true, message: '所属单位不能为空', trigger: 'blur' }],
        person_id: [{ required: true, message: '人员姓名不能为空', trigger: 'change' }],
        person_name: [{ required: true, message: '人员姓名不能为空', trigger: 'blur' }],
        work_card_code: [{ required: true, message: '作业证编号不能为空', trigger: 'blur' }],
        work_type_name: [{ required: true, message: '工种不能为空', trigger: 'change' }]
      },
      //绑定部门下拉树的单条数据
      deptForm: { value: '', label: '' },
      //部门下拉树
      deptData: [],
      //人员列表
      userOptions: [],
      //工种下拉列表
      occupationOptions: [],
      submitLoading: false
    }
  },
  watch: {
    'form.construction_unit_id' (newValue) {
      this.deptForm.value = newValue;
    }
  },
  created () {
    this.getOrganizationTree()
    this.getWorktypeOptions()
    if (this.type == 'edit') {
      this.getUserOptions(this.data.construction_unit_id)
      this.form = { ...this.data }
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取施工单位下拉树
    getOrganizationTree () {
      getconstructionunitTree().then(res => {
        // console.log(res)
        this.deptData = treeDataformat(res.data.data, 'id', 'pid', 'children');
        // console.log(this.deptData)
      })
    },
    //获取人员下拉
    getUserOptions (id) {
      getOrganizationUserList(id).then(res => {
        // console.log(res)
        this.userOptions = res.data
      })
    },
    //获取工种下拉列表
    getWorktypeOptions () {
      getWorktype().then(res => {
        // console.log(res)
        this.occupationOptions = res.data.data
      })
    },
    //单位选择赋值
    getDept (obj) {
      // console.log(obj, "obj")
      this.form.construction_unit_id = obj.value;
      this.form.construction_unit_name = obj.label
      this.form.type = obj.type
      if (this.form.type)
        if (this.form.type == 1) {
          this.form.person_name = ''
          this.form.person_id = ''
          this.getUserOptions(obj.value)

        } else {
          this.form.person_name = ''
          this.form.person_id = 0
        }
    },
    //改变人员
    changePerson (val) {
      // console.log(val)
      this.userOptions.forEach((item) => {
        if (item.id == val) {
          this.form.person_name = item.label
          // console.log(this.form.storage_maintain_space_name)
        }
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            constructionunitpersonsInsert(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
              }else {
                this.$message({ type: "error", message: res.data.msg });
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else {
            this.form.modify_user_id = this.$store.getters.userInfo.userId;
            this.form.modify_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            constructionunitpersonsUpdate(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
              }else {
                this.$message({ type: "error", message: res.data.msg });
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          }

        }
      });
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">

</style>
