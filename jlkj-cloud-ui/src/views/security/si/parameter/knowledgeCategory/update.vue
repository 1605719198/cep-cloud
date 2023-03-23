<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item prop="knowledge_category_name"
                      label="类别名称">
          <el-input v-model="form.knowledge_category_name"
                    placeholder="请输入类别名称"></el-input>
        </el-form-item>
        <!-- <el-form-item prop="sort"
                      label="序号">
          <el-input v-model="form.sort"
                    placeholder="请输入序号"></el-input>
        </el-form-item> -->
        <el-form-item label="上级类别"
                      prop="parent_knowledge_category_id">
          <select-tree style="width: 100%;"
                       v-model="form.parent_knowledge_category_id"
                       placeholder="请选择上级类别"
                       :selectForm="knowledgeForm"
                       :data="knowledgeData"
                       :isSelectNode="true"
                       :isClearable="true"
                       :isAccordion="true"
                       :isFilterable="true"
                       :isNodeChildrenNull="true"
                       @change="getDept" />
        </el-form-item>
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
import { knowledgecategoryInsert, knowledgecategoryUpdate } from "@/api/security/si/parameter/knowledgeCategory";
import { getknowledgecategoryTree } from "@/api/security/si/WorkTicketApi";
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
        //上级类别id
        parent_knowledge_category_id: '0',
      },
      // 表单校验
      rules: {
        knowledge_category_name: [{ required: true, message: '类别名称不能为空', trigger: 'blur' }],
        // sort: [{ required: true, message: '序号不能为空', trigger: 'blur' }],
        parent_knowledge_category_id: [{ required: true, message: '上级类别不能为空', trigger: 'blur' }]
      },
      //知识类别树
      knowledgeData: [],
      //回显选中的数据（默认值）
      knowledgeForm: { value: '0', label: '根节点' },
      submitLoading: false
    }
  },
  watch: {
    'form.parent_knowledge_category_id' (newValue) {
      this.knowledgeForm.value = newValue;
    }
  },
  created () {
    this.getknowledgecategoryTreeData()
    if (this.type == 'edit') {
      this.form = { ...this.data }
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取知识类别树
    getknowledgecategoryTreeData () {
      getknowledgecategoryTree().then(res => {
        // console.log(res)
        this.knowledgeData = treeDataformat(res.data.data, 'id', 'pid', 'children');
        //手动添加根节点（默认项）
        this.knowledgeData.unshift({
          id: '0',
          isOpen: true,
          label: '根节点',
          pid: ''
        })
      })
    },
    //选中下拉赋值
    getDept (obj) {
      this.form.parent_knowledge_category_id = obj.value;
    },
    submitForm (formName) {
      //序号(没用了但是必传项)
      this.form.sort = 1
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            knowledgecategoryInsert(this.form).then(res => {
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
            knowledgecategoryUpdate(this.form).then(res => {
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

<style>
</style>
