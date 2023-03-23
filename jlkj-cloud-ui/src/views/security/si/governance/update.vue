<template>
  <div class="avue-crud">
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="100px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item label="单位"
                          prop="department_id">
              <select-tree style="width: 100%;"
                           v-model="form.department_id"
                           placeholder="请选择"
                           :selectForm="deptForm"
                           :data="deptData"
                           :isSelectNode="true"
                           :isClearable="true"
                           :isAccordion="true"
                           :isFilterable="true"
                           :isNodeChildrenNull="true"
                           @change="getDept" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="complete_state"
                          label="完成情况">
              <el-select class="customSelectStyle"
                         style="width: 100%;"
                         :popper-append-to-body="false"
                         v-model="form.complete_state"
                         clearable
                         placeholder="选择完成情况">
                <el-option v-for="item in options"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="work_items"
                      label="工作事项">
          <!-- <el-input v-model="form.work_items"
                        placeholder="请输入工作事项"></el-input> -->
          <el-input type="textarea"
                    :autosize="{ minRows: 3, maxRows: 3 }"
                    placeholder="工作事项"
                    v-model="form.work_items">
          </el-input>
        </el-form-item>
        <el-form-item prop="work_target"
                      label="完成目标">
          <el-input type="textarea"
                    :autosize="{ minRows: 3, maxRows: 3 }"
                    placeholder="完成目标"
                    v-model="form.work_target">
          </el-input>
        </el-form-item>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item>
              <el-upload class="upload-demo"
                         :action="action"
                         accept=".png,.jpg"
                         multiple
                         :limit="3"
                         :on-exceed="handleExceed"
                         :on-remove="handleRemove"
                         :on-success="handleSuccess"
                         :file-list="fileList"
                         list-type="picture">
                <el-button size="small"
                           type="primary">点击上传整改前照片</el-button>
                <div slot="tip"
                     class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <el-upload class="upload-demo"
                         :action="action"
                         accept=".png,.jpg"
                         multiple
                         :limit="3"
                         :on-exceed="handleExceed"
                         :on-remove="handleRemoveAfter"
                         :on-success="handleSuccessAfter"
                         :file-list="afterFileList"
                         list-type="picture">
                <el-button size="small"
                           type="primary">点击上传整改后照片</el-button>
                <div slot="tip"
                     class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- form列表 -->
      <el-form ref="formData"
               :model="formData"
               :rules="rulesData">
        <el-table v-loading="loading"
                  :data="formData.tableData"
                  stripe
                  border
                  style="width: 100%">
          <el-table-column prop="responsible_user_no"
                           label="工号">
            <template slot-scope="scope">
              <el-form-item v-if="editIndex === scope.$index"
                            label=""
                            :prop="'tableData.'+scope.$index+'.responsible_user_no'"
                            :rules="rulesData.responsible_user_no"
                            label-width="0px"
                            size="small">
                <el-input disabled
                          v-model="scope.row.responsible_user_no"
                          placeholder="工号"></el-input>
              </el-form-item>
              <div v-else
                   class="cell">{{scope.row.responsible_user_no}}</div>
            </template>
          </el-table-column>
          <el-table-column prop="responsible_user_name"
                           label="负责人">
            <template slot-scope="scope">
              <!-- <el-form-item v-if="editIndex === scope.$index"
                            label=""
                            :prop="'tableData.'+scope.$index+'.responsible_user_id'"
                            :rules="rulesData.responsible_user_id"
                            label-width="0px"
                            size="small">
                <select-tree v-model="scope.row.responsible_user_id"
                             style="width:100%;"
                             placeholder="请选择"
                             :selectForm="scope.row.mainForm"
                             :data="userData"
                             :otherForm="['flag']"
                             :validate="{flag:'user'}"
                             :isSelectNode="false"
                             :isClearable="true"
                             :isAccordion="true"
                             :isNodeChildrenNull="true"
                             :isFilterable="true"
                             @change="getMainUser($event,scope.$index)" />
              </el-form-item> -->
              <el-form-item v-if="editIndex === scope.$index"
                            label=""
                            :prop="'tableData.'+scope.$index+'.responsible_user_id'"
                            :rules="rulesData.responsible_user_id"
                            label-width="0px"
                            size="small">
                <el-input v-model="scope.row.responsible_user_name"
                          :readonly="true"
                          placeholder="请选负责人"
                          @focus="dialogVisible = true"></el-input>
                <personnel-selection v-if="dialogVisible"
                                     :userDialogVisible="dialogVisible"
                                     @close-dialog="dialogVisible = false"
                                     :isMultiple="false"
                                     :height="500"
                                     @single-select="handleSingleSelect($event,scope.$index)"></personnel-selection>
              </el-form-item>
              <div v-else
                   class="cell">{{scope.row.responsible_user_name}}</div>
            </template>
          </el-table-column>
          <el-table-column prop="reward_proportion"
                           label="奖励比例(%)">
            <template slot-scope="scope">
              <el-form-item v-if="editIndex === scope.$index"
                            label=""
                            :prop="'tableData.'+scope.$index+'.reward_proportion'"
                            :rules="rulesData.reward_proportion"
                            label-width="0px"
                            size="small">
                <el-input v-model="scope.row.reward_proportion"
                          placeholder="请输入"
                          onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')" />
              </el-form-item>
              <div v-else
                   class="cell">{{scope.row.reward_proportion}}</div>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button v-if="editIndex === scope.$index"
                         size="mini"
                         plain
                         icon="el-icon-circle-check"
                         type="primary"
                         @click="handleSave(scope.$index, scope.row)"> 确定
              </el-button>
              <el-button v-else
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="text"
                         @click="handleEdit(scope.$index, scope.row)"> 修改
              </el-button>
              <el-button size="mini"
                         plain
                         icon="el-icon-delete"
                         type="text"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="width: 100%;margin: 5px 0;color: #8c939d ">
          <div style=" height:44px;line-height: 44px;text-align: center;border: 2px dashed #E4E4E4">
            <el-link type="info"
                     :underline="false"
                     icon="el-icon-circle-plus-outline"
                     @click="addTable">添加</el-link>
          </div>
        </div>
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
import { safeGovernanceSave, safeGovernanceUpdate, listProportions } from "@/api/security/si/governance/index";
import { getOrganizationDeptTree, getHumanTree } from "@/api/human/hm/humanResourceTree";
import { mapGetters } from "vuex";
import { getUrlArray } from "@/utils/data"
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
    var reward_proportion = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('奖励比例不能为空'));
      } else {
        let sum = 0
        this.formData.tableData.forEach(item => {
          sum += Number(item.reward_proportion)
        })
        // console.log(sum, "sum")
        if (sum > 100) {
          callback(new Error('奖励比例不能大于100%'));
        } else {
          callback();
        }
      }
    };
    return {
      action: '',
      form: {
        rectification_before_url: [],
        rectification_after_url: []
      },
      //控制列表保存编辑按钮显示和逻辑
      editIndex: -1,
      loading: false,
      // 表单校验
      rules: {
        department_id: [{ required: true, message: '单位不能为空', trigger: 'blur' }],
        work_items: [{ required: true, message: '工作事项不能为空', trigger: 'blur' }],
        complete_state: [{ required: true, message: '完成情况不能为空', trigger: 'change' }],
        work_target: [{ required: true, message: '完成目标不能为空', trigger: 'blur' }],
      },
      dialogVisible: false,
      //列表的表单
      formData: {
        tableData: [],
      },
      //列表验证
      rulesData: {
        // responsible_user_no: [{ required: true, message: '工号不能为空', trigger: 'blur' }],
        responsible_user_id: [{ required: true, message: '负责人不能为空', trigger: 'change' }],
        // reward_proportion: [{ required: true, message: '奖励比例不能为空', trigger: 'blur' }],
        reward_proportion: [{ validator: reward_proportion, trigger: ['blur', 'change'] }],
      },
      options: [
        {
          id: 1,
          name: "已完成"
        },
        {
          id: 2,
          name: "未完成"
        }
      ],
      //人员下拉树
      userData: [],
      //选中回显的数据
      mainForm: { value: '', label: '' },
      //选中回显的数据
      deptForm: { value: '', label: '' },
      //部门下拉树
      deptData: [],
      //图片回显列表
      fileList: [],
      afterFileList: [],
      submitLoading: false
    }
  },
  watch: {
    'form.department_id' (newValue) {
      this.deptForm.value = newValue;
    }
  },
  created () {
    this.action = this.GLOBAL.fileUrl + '/file/upload'
    this.getOrganizationTree()
    this.getTree()
    if (this.type == 'edit') {
      this.form = { ...this.data }
      this.fileList = this.form.rectification_before_url
      this.afterFileList = this.form.rectification_after_url
      if (this.form.id) {
        this.onLoad(this.form.id)
      }
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取单位下拉树
    getOrganizationTree () {
      getOrganizationDeptTree().then(res => {
        // console.log(res, "res")
        this.deptData = res.data
      })
    },
    //更改赋值
    getDept (obj) {
      this.form.department_id = obj.value;
    },
    //人员下拉树
    getTree () {
      getHumanTree().then(res => {
        this.userData = res.data.data;
      }, error => {
        window.console.log(error);
      });
    },
    //责任人回调
    handleSingleSelect (val, index) {
      // console.log(val, index)
      this.formData.tableData[index].responsible_user_id = val.id
      this.formData.tableData[index].responsible_user_name = val.full_name
      this.formData.tableData[index].responsible_user_no = val.job_number
    },
    //赋值
    // getMainUser (obj, index) {
    //   this.formData.tableData[index].responsible_user_id = obj.value;
    //   this.formData.tableData[index].responsible_user_name = obj.label;
    // },
    //奖励比例列表
    onLoad (id) {
      this.loading = true
      listProportions({ id }).then(res => {
        // console.log(res)
        this.formData.tableData = res.data
        this.loading = false
      })
    },
    //添加
    addTable () {
      if (this.editIndex === -1) {
        this.editIndex = this.formData.tableData.length;
        this.formData.tableData.push({
          id: '',
          responsible_user_no: '',
          responsible_user_id: '',
          responsible_user_name: '',
          reward_proportion: '',
          // mainForm: { value: '', label: '' },
        })
      } else {
        this.$message({ type: "error", message: "请先保存正在编辑的数据！", });
      }
    },
    //保存
    handleSave () {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          this.editIndex = -1
        }
      })
    },
    //编辑
    handleEdit (index) {
      if (this.editIndex === -1) {
        this.editIndex = index;
        // this.formData.tableData[index].mainForm = { value: this.formData.tableData[index].responsible_user_id, label: this.formData.tableData[index].responsible_user_name }
      } else {
        this.$message({ type: "error", message: "请先保存正在编辑的数据！", });
      }
    },
    //删除
    handleDelete (index) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        this.editIndex = -1;
        this.formData.tableData.splice(index, 1);
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    //文件上传成功时的钩子
    handleSuccess (response, file, fileList) {
      this.form.rectification_before_url = getUrlArray(fileList, this.GLOBAL.serveAddress)
      // console.log(this.form.rectification_before_url)
    },
    handleSuccessAfter (response, file, fileList) {
      this.form.rectification_after_url = getUrlArray(fileList, this.GLOBAL.serveAddress)
      // console.log(this.form.rectification_after_urll)
    },
    //文件列表移除文件时的钩子
    handleRemove (file, fileList) {
      this.form.rectification_before_url = getUrlArray(fileList, this.GLOBAL.serveAddress)
      // console.log(file, fileList);
      // console.log(this.form.rectification_before_url)
    },
    handleRemoveAfter (file, fileList) {
      this.form.rectification_after_url = getUrlArray(fileList, this.GLOBAL.serveAddress)
      // console.log(file, fileList);
      // console.log(this.form.rectification_after_url)
    },
    //文件超出个数限制时的钩子
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //判断选择图片
          if (this.form.rectification_before_url.length == 0) {
            this.$message({ type: "error", message: "请上传整改前照片！", });
            return false
          }
          if (this.form.rectification_after_url.length == 0) {
            this.$message({ type: "error", message: "请上传整改后照片！", });
            return false
          }
          //列表没数据，没保存不让提交
          if (this.editIndex == -1 && this.formData.tableData.length == 0) {
            this.$message({ type: "error", message: "请添加奖金信息", });
            return false
          }
          if (this.editIndex != -1) {
            this.$message({ type: "error", message: "请先保存正在编辑的数据！", });
            return false
          }
          this.form.proportions = this.formData.tableData
          //数组转JSON(注意一下代码只能转一次，执行二次图片加载不出来，地址不对)
          this.form.rectification_before_url = JSON.stringify(this.form.rectification_before_url)
          this.form.rectification_after_url = JSON.stringify(this.form.rectification_after_url)
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            safeGovernanceSave(this.form).then(res => {
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
            safeGovernanceUpdate(this.form).then(res => {
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
