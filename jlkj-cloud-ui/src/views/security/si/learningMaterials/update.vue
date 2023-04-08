<template>
  <div class="avue-crud">
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item prop="title"
                      label="标题">
          <el-input v-model="form.title"
                    :disabled="type=='view'?true:false"
                    placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item prop="keywords"
                      label="关键字">
          <el-input v-model="form.keywords"
                    :disabled="type=='view'?true:false"
                    placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item label="知识类别"
                      prop="knowledge_category_id">
          <select-tree :isDisabled="type=='view'?true:false"
                       style="width: 100%;"
                       v-model="form.knowledge_category_id"
                       placeholder="请选择"
                       :selectForm="knowledgeForm"
                       :data="knowledgeData"
                       :isSelectNode="true"
                       :isClearable="true"
                       :isAccordion="true"
                       :isFilterable="true"
                       :isNodeChildrenNull="true"
                       @change="getDept" />
        </el-form-item>
        <el-form-item label="附件">
          <el-upload v-if="type!='view'"
                     class="upload-demo"
                     :action="action"
                     :headers="headers"
                     multiple
                     :show-file-list="false"
                     :before-upload="handleBeforeUpload"
                     :on-change="handleChange"
                     :on-success="handleSuccess">
            <el-button size="small"
                       type="primary">上传附件</el-button>
          </el-upload>
          <el-progress v-show="showProcess"
                       :percentage="processlength"
                       :stroke-width="2"></el-progress>
        </el-form-item>
        <!-- 附件列表 -->
        <el-form-item v-if="form.file_list.length">
          <el-table size="small"
                    :data="form.file_list"
                    stripe>
            <el-table-column label="文件名称"
                             minWidth="150"
                             align="center"
                             prop="file_name" />
            <el-table-column label="文件大小"
                             minWidth="150"
                             align="center"
                             prop="file_size" />
            <el-table-column label="操作"
                             width="180"
                             align="center">
              <template slot-scope="scope">
                <el-button size="mini"
                           :disabled="false"
                           plain
                           icon="el-icon-edit"
                           type="primary"
                           @click="handleDownload(scope.$index, scope.row)">下载
                </el-button>
                <el-button v-if="type!='view'"
                           size="mini"
                           plain
                           icon="el-icon-delete"
                           type="text"
                           @click="handleDelete(scope.$index, scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item label="富文本">
          <editor-bar ref="weditor"
                      v-model="form.contents"
                      :isReadOnly="type=='view'?true:false"></editor-bar>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 v-if="type!='view'"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { knowledgelibraryInsert, knowledgelibraryUpdate, fileList } from "@/api/security/si/learningMaterials/index";
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
        file_list: [],
        //富文本
        contents: ''
      },
      // 表单校验
      rules: {
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        keywords: [{ required: true, message: '关键字不能为空', trigger: 'blur' }],
        knowledge_category_id: [{ required: true, message: '知识类别不能为空', trigger: 'blur' }]
      },
      //知识类别树
      knowledgeData: [],
      //回显选中的数据（默认值）
      knowledgeForm: { value: '', label: '' },
      processlength: 0,
      showProcess: false,
      submitLoading: false,
      action: '',
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      download: ''
    }
  },
  watch: {
    'form.knowledge_category_id' (newValue) {
      this.knowledgeForm.value = newValue;
    }
  },
  created () {
    this.action = process.env.VUE_APP_BASE_API + '/file/file/upload', // 上传的图片服务器地址
    this.download = process.env.VUE_APP_BASE_API + '/file/file/download/';
    // console.log(this.type, "this.type")
    this.getknowledgecategoryTreeData()
    if (this.type == 'edit' || this.type == 'view') {
      this.getFileList(this.data.id)
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
      })
    },
    //选中下拉赋值
    getDept (obj) {
      this.form.knowledge_category_id = obj.value;
      this.form.knowledge_category_name = obj.label
    },
    //获取附件列表
    getFileList (id) {
      fileList({ id }).then(res => {
        // console.log(res)
        let data = res.data.data
        this.form = { ...this.data, file_list: data }
      })
    },
    //上传成功之前的钩子
    handleBeforeUpload () {
      if (this.form.file_list.length > 2) {
        this.showProcess = false
        this.$message.warning(`当前限制选择 3 个文件,本次选择4个文件,共选择了 ${this.form.file_list.length + 1} 个文件`);
        return false
      }
    },
    /** 文件进度 */
    handleChange (file, fileList) {
      console.log(file, fileList)
      if (file.status === 'ready') {
        this.processlength = 0
        this.showProcess = true //进度条显示
        const interval = setInterval(() => {
          if (this.processlength >= 99) {
            clearInterval(interval)
            return
          }
          this.processlength += 1 //进度条进度
        }, 20)
      }
      if (file.status === 'success') {
        this.processlength = 100
        this.showProcess = false
      }
    },
    //文件上传成功时的钩子
    handleSuccess (response, file, fileList) {
      console.log(file, fileList);
      let i = {
        file_id: response.data.id,
        disk_name: response.data.relativePath,
        file_name: file.name,
        file_size: file.size
      }
      this.form.file_list.push(i)
      console.log(this.form.file_list)
    },
    //下载
    handleDownload (index, row) {
      // let download = `http://192.168.105.249:9300/file/download/${row.file_id}`
      let download = this.download + row.file_id
      // console.log(download)
      window.open(download)
    },
    //删除
    handleDelete (index, row) {
      this.$confirm(`确定移除 ${row.file_name}？`, '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        this.form.file_list.splice(index, 1);

      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    submitForm (formName) {
      // console.log(this.form, "this.form")
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            knowledgelibraryInsert(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
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
            knowledgelibraryUpdate(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
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
