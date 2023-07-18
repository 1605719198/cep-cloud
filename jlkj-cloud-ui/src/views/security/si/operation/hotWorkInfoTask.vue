<template>

  <div class="avue-crud">
    <el-form :model="form" ref="form" label-position="left" label-width="80px">
      <el-table size="small" :data="tableData" stripe>
        <el-table-column prop="sort" label="序号" width="50"/>
        <el-table-column prop="approval_name" label="审批环节"/>
        <el-table-column prop="approval_person_name" label="审批人" width="70"/>
        <el-table-column prop="is_approval" label="审批状态" width="80">
          <template slot-scope="scope">
            {{
            scope.row.confirm_time ===''? '未确认':scope.row.is_approval === 0?'未审批':scope.row.is_approval === 1?'已审批':''
            }}
          </template>
        </el-table-column>
        <el-table-column prop="opinion" label="意见" width="150px">
          <template slot-scope="scope">
            <template
              v-if="scope.row.confirm_time !== '' && scope.row.is_approval === 0 && userInfo.userId === scope.row.approval_person_id">
              <el-input v-model="scope.row.content"/>
            </template>
            <template v-else>
              {{scope.row.content}}
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="approval_time" label="签字时间" width="130px"/>
        <el-table-column prop="pick_person_name" label="选择人" width="70"/>
        <el-table-column label="操作" width="150px">
          <template slot-scope="scope">
            <template v-if="type === 'add'">
              <el-button size="mini" plain @click="handleSet(scope.$index,scope.row)">设置</el-button>
            </template>
            <template v-else>
              <el-button
                v-if="scope.row.is_approval === 0 && scope.row.confirm_time === '' && (data.status === 0 || userInfo.userId === scope.row.approval_person_id)"
                size="mini" plain
                @click="handleSet(scope.$index,scope.row)">设置
              </el-button>
              <template v-if="scope.row.id !== '' && userInfo.userId === scope.row.approval_person_id">
                <el-button v-if="scope.row.confirm_time === ''" size="mini" plain type="primary"
                           @click="onConfirm(scope.row)">确认
                </el-button>
                <el-button v-else-if="scope.row.confirm_time !== '' && scope.row.is_approval === 0 " size="mini" plain
                           type="text" @click="onApproval(scope.row,'approval')">审批
                </el-button>
                <el-button
                  v-if="scope.row.confirm_time !== '' && scope.row.is_approval === 0 && userInfo.userId === scope.row.approval_person_id"
                  size="mini" plain type="warning" @click="onReturn(scope.row)">回退
                </el-button>
              </template>
              <el-button v-if="scope.row.confirm_time !== '' && scope.row.is_approval === 1 " size="mini" plain
                         type="info" @click="onLook(scope.row,'info')">查看
              </el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="dialogVisible">
        <!-- 做使用el-dialog做签字的弹框 -->
        <el-dialog title="签字" :visible.sync="dialogVisible" width="500px" append-to-body :destroy-on-close="true"
                   :close-on-click-modal="false" @close="cancel">
          <!-- 使用这个签名组件 -->
          <vue-esign v-if="resultImg===''" ref="esign" class="mySign"
                     :width="500" :height="200"
                     :isCrop="isCrop" :lineWidth="lineWidth" :lineColor="lineColor" :bgColor.sync="bgColor"/>
          <img v-else :src="resultImg" alt="" width="100%"/>
          <span slot="footer" class="dialog-footer">
          <el-button v-if="imgType === 'approval'" @click="handleAutograph" type="primary">电子签章</el-button>
          <el-button v-if="imgType === 'approval'" @click="handleReset">清空画板</el-button>
          <el-button v-if="imgType === 'approval'" @click="handleGenerate" type="primary">完成</el-button>
          <el-button @click="cancel">取消</el-button>
        </span>
        </el-dialog>
      </div>
      <div v-if="approvalTreeOpen">
        <personnel-selection v-if="approvalTreeOpen" :userDialogVisible="approvalTreeOpen"
                             @close-dialog="approvalTreeOpen = false"
                             :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import vueEsign from 'vue-esign'

  import {getHumanTree} from "@/api/human/hm/humanResourceTree";
  import {req} from "@/api/security/si/common";
  import {upload} from "@/api/security/si/governance/index";

  export default {
    name: "heightInfoTask1",
    props: ['data', 'type', 'ticketId', 'lastTaskUserId', 'lastTaskUserName', 'level'],
    components: {vueEsign},
    data() {
      return {
        tableData: [],
        treeData: [],
        formId: '',
        form: {},
        approvalTreeOpen: false,
        editIndex: -1,

        imgType: '',
        id: -1,
        dialogVisible: false, // 弹框是否开启
        lineWidth: 6, // 画笔的线条粗细
        lineColor: "#000000", // 画笔的颜色
        bgColor: "", // 画布的背景颜色
        resultImg: "", // 最终画布生成的base64图片
        isCrop: false, // 是否裁剪，在画布设定尺寸基础上裁掉四周空白部分
        copyId: false,
        signature: '',

        isUpdated: false,
      };
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      if (this.type === 'add' && !this.data.copyId) {
        this.isUpdated = true;
      }
      if (this.data.copyId) {
        this.copyId = true;
      }
      getHumanTree().then(res => {
        this.treeData = res.data.data;
      }, error => {
        window.console.log(error);
      });
      this.formId = this.data.id;
      this.onLoad();
    },
    updated() {
      this.isUpdated = true;
    },
    methods: {
      onLoad() {
        if (this.formId || this.ticketId) {
          req('get', 'fire/approval/list', {id: this.formId || this.ticketId}).then(res => {
            this.tableData = res.data.data;//表格数据
            this.data.approval_list = this.tableData;
            this.loading = false;
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        } else if (this.data.copyId) {
          req('get', 'fire/approval/list', {id: this.data.copyId}).then(res => {
            let d = [];
            res.data.data.forEach((item, index) => {
              d.push({
                sort: item.sort,
                approval_name: item.approval_name,
                approval_person_id: item.approval_person_id,
                approval_person_name: item.approval_person_name,
                pick_person_id: this.$store.getters.userInfo.userId,
                pick_person_name: this.$store.getters.userInfo.userName,
              });
            });
            this.tableData = d;//表格数据
            this.data.approval_list = this.tableData;
            this.loading = false;
            this.copyId = false;
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        }
      },
      handleSet(index) {
        this.editIndex = index;
        this.approvalTreeOpen = true;
      },
      handleNodeClick(data) {
        this.tableData[this.editIndex].approval_person_id = data.id;
        this.tableData[this.editIndex].approval_person_name = data.full_name;
        this.tableData[this.editIndex].pick_person_id = this.$store.getters.userInfo.userId;
        this.tableData[this.editIndex].pick_person_name = this.$store.getters.userInfo.userName;
        this.approvalTreeOpen = false;
        this.data.approval_list = this.tableData;
        if (this.tableData[this.editIndex].id) {
          req('post', 'fire/update/approval', {id: this.tableData[this.editIndex].id, ...this.tableData[this.editIndex]}).then(res => {
            this.$message({type: "success", message: res.data.msg});
          }, error => {
            window.console.log(error);
          });
        }
      },

      // 清空画板
      handleReset() {
        this.resultImg = "";
        this.$refs.esign.reset();
      },
      // 生成签字图
      handleGenerate() {
        if (this.resultImg === '') {
          let signature = false;
          this.$refs.esign.generate().then((res) => { // 使用生成器调用把签字的图片转换成为base64图片格式
            this.resultImg = res;
            signature = true;
          }).catch(() => {
            // 画布没有签字时会执行这里提示一下
            this.$message({type: "warning", message: "请签名后再生成签字图片",});
          });
          // 在这里向后端发请求把转换后的base64文件传给后端，后端接收以后再转换成图片做静态图片存储
          // 当然也可以把base64转成流文件blob格式的，类似上传给后端这样，具体哪种方式看后端要求
          setTimeout(() => {
            if (signature) {
              let file = this.dataURLtoFile(this.resultImg, 'qm.png');
              let formData = new FormData();
              formData.append('file', file);
              upload(formData).then(res => {
                this.signature = res.data.data.relativePath;
                req('post', 'fire/confirm/approval', {
                  id: this.form.id,
                  content: this.form.content ? this.form.content : '',
                  signature: this.signature,
                }).then(res => {
                  if (res.data.code === "0") {
                    this.$message({type: "success", message: "审批成功！"});
                    this.$emit('approvalOver');
                  }
                  this.form = {};
                  this.resultImg = '';
                  this.onLoad();
                }, error => {
                  window.console.log(error);
                });
              })
              // 这里要使用定时器稍微延后以后就能取到base64数据了，当然也可以再加一个确认按钮，如：确认使用这张base64签名图片
              // 点击确认以后，在其回调函数中，再把base64的签名图片传给后端用于存储
              // req('post', 'fire/confirm/approval', {
              //   id: this.form.id,
              //   content: this.form.content ? this.form.content : '',
              //   signature: this.resultImg,
              // }).then(res => {
              //   if (res.data.code === 0) {
              //     this.$message({type: "success", message: "审批成功！"});
              //   }
              //   this.form = {};
              //   this.resultImg = '';
              //   this.onLoad();
              // }, error => {
              //   window.console.log(error);
              // });
            }
          }, 200);
        } else {
          req('post', 'fire/confirm/approval', {
            id: this.form.id,
            content: this.form.content ? this.form.content : '',
            signature: this.signature,
          }).then(res => {
            if (res.data.code === "0") {
              this.$message({type: "success", message: "审批成功！"});
              this.$emit('approvalOver');
            }
            this.form = {};
            this.resultImg = '';
            this.onLoad();
          }, error => {
            window.console.log(error);
          });
        }
        this.dialogVisible = false;
      },
      onConfirm(row) {
        if (row.sort === 1) {
          req('get', 'fire/safetymeasures/list', {id: this.formId || this.ticketId}).then(res => {
            let tableData = res.data.data;//表格数据
            let arr = [];
            arr = tableData.filter(item => item.is_confirm === 1);
            if (arr.length > 0) {
              this.confirmTask(row);
            } else {
              this.$confirm('安全措施没有填写, 是否继续审核?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.confirmTask(row);
              }).catch(() => {
                this.$message({type: 'info', message: '已取消确认'});
              });
            }
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        }else{
          this.confirmTask(row);
        }
      },
      confirmTask(row) {
        req('post', 'fire/confirm/approval/time', {
          id: row.id,
        }).then(() => {
          this.$message({type: "success", message: "确认成功！"});
          this.onLoad();
        }, error => {
          window.console.log(error);
        });
      },
      onApproval(row, type) {
        this.form = row;
        this.dialogVisible = true;
        this.imgType = type;
      },
      onReturn(row) {
        this.$confirm('是否确定回退审批?', '提示', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          let data = {...row};
          data.content = data.content === null ? '': data.content;
          req('post', 'fire/confirm/return', data).then(() => {
            // this.$message({type: "success", message: "审批回退成功！"});
            this.onLoad();
          }, error => {
            window.console.log(error);
          });
        }).catch(() => {
          this.$message({type: 'info', message: '已取消回退'});
        });
      },
      onLook(row, type) {
        this.dialogVisible = true;
        if (row.signature.includes('data:')) {
          this.resultImg = row.signature;
        } else {
          this.resultImg = this.GLOBAL.serveAddress + row.signature;
        }
        this.imgType = type;
      },
      cancel() {
        this.form = {};
        this.resultImg = '';
        this.dialogVisible = false;
        this.imgType = '';
      },
      handleAutograph() {
        if (this.userInfo.signature === '' || this.userInfo.signature === null) {
          this.$message({type: "error", message: "该用户没有上传电子签章"});
        } else {
          this.resultImg = this.GLOBAL.serveAddress + this.userInfo.signature;
          this.signature = this.userInfo.signature;
        }
      }
    },
    watch: {
      ticketId() {
        this.onLoad();
      },
      level(value) {
        if (this.isUpdated) {
          req('get', 'fire/approval/template/list', {job_level: value}).then(res => {
            let data = res.data.data;
            let d = [];
            data.forEach(item => {
              d.push({
                ...{id: '',approval_person_id: '', approval_person_name: '', pick_person_id: '', pick_person_name: '', confirm_time: '', is_approval: 0,},
                ...item
              })
            });
            this.tableData = d;
            if (this.lastTaskUserId !== '') {
              this.tableData[this.tableData.length - 1].approval_person_id = this.lastTaskUserId;
              this.tableData[this.tableData.length - 1].approval_person_name = this.lastTaskUserName;
              this.tableData[this.tableData.length - 1].pick_person_id = this.$store.getters.userInfo.userId;
              this.tableData[this.tableData.length - 1].pick_person_name = this.$store.getters.userInfo.userName;
            }
            this.data.approval_list = this.tableData;
          }, error => {
            window.console.log(error);
          });
        }
      },
      lastTaskUserId() {
        if (this.tableData.length > 0) {
          this.tableData[this.tableData.length - 1].approval_person_id = this.lastTaskUserId;
          this.tableData[this.tableData.length - 1].approval_person_name = this.lastTaskUserName;
          this.data.approval_list = this.tableData;
        }
      }
    }
  }
</script>

<style scoped>

</style>
