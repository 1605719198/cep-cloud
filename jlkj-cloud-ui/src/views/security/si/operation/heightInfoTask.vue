<template>

  <div class="avue-crud">
    <el-form :model="form" ref="form" label-position="left" label-width="80px">
      <el-table size="small" :data="tableData" stripe>
        <el-table-column prop="approval_name" label="审批环节"/>
        <el-table-column prop="opinion" label="意见">
          <template slot-scope="scope">
            <template v-if="scope.row.is_required === 0 && scope.row.is_approval === 0">
              <el-input v-model="scope.row.content"/>
            </template>
            <template v-else>
              {{scope.row.content}}
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="is_approval" label="审批状态" width="100px">
          <template slot-scope="scope">
            {{
            scope.row.confirm_time ===''? '未确认':scope.row.is_approval === 0?'未审批':'已审批'
            }}
          </template>
        </el-table-column>
        <el-table-column prop="approval_person_name" label="签字人" width="100px"/>
        <el-table-column prop="approval_time" label="签字时间" width="150px"/>
        <el-table-column label="操作" width="150px">
          <template slot-scope="scope">
            <template v-if="userInfo.userId === scope.row.approval_person_id">
              <el-button v-if="scope.row.confirm_time === ''" size="mini" plain type="primary"
                         @click="onConfirm(scope.row)">确认
              </el-button>
              <el-button v-else-if="scope.row.confirm_time !== '' && scope.row.is_approval === 0 " size="mini" plain
                         type="text" @click="onApproval(scope.row,'approval')">审批
              </el-button>
              <el-button v-else size="mini" plain type="info" @click="onLook(scope.row,'info')">查看
              </el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <!-- 做使用el-dialog做签字的弹框 -->
      <el-dialog title="签字" :visible.sync="dialogVisible" width="500px" append-to-body :destroy-on-close="true"
                 :close-on-click-modal="false">
        <!-- 使用这个签名组件 -->
        <vue-esign v-if="resultImg===''" ref="esign" class="mySign"
                   :width="500" :height="200"
                   :isCrop="isCrop" :lineWidth="lineWidth" :lineColor="lineColor" :bgColor.sync="bgColor"/>
        <img v-else :src="resultImg" alt=""/>
        <span slot="footer" class="dialog-footer">
          <el-button v-if="imgType === 'approval'" @click="handleGenerate" type="primary">完成</el-button>
          <el-button v-if="imgType === 'approval'" @click="handleReset">清空画板</el-button>
          <el-button @click="cancel">取消</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";
  import vueEsign from 'vue-esign'
  import {mapGetters} from "vuex";

  export default {
    name: "heightInfoTask",
    props: ['data', 'type', 'ticketId'],
    components: {vueEsign},
    data() {
      return {
        tableData: [],
        formId: '',
        form: {},
        imgType: '',
        id: -1,
        dialogVisible: false, // 弹框是否开启
        lineWidth: 6, // 画笔的线条粗细
        lineColor: "#000000", // 画笔的颜色
        bgColor: "", // 画布的背景颜色
        resultImg: "", // 最终画布生成的base64图片
        isCrop: false, // 是否裁剪，在画布设定尺寸基础上裁掉四周空白部分
      };
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      this.formId = this.data.id;
      this.onLoad();
    },
    methods: {
      onLoad() {
        // console.log(this.form)
        if (this.formId) {
          req('get', 'high/approval/list', {id: this.formId}).then(res => {
            this.tableData = res.data.data;//表格数据
            this.loading = false;
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        } else {
          req('get', 'high/approval/list', {id: this.ticketId}).then(res => {
            this.tableData = res.data.data;//表格数据
            this.loading = false;
          }, error => {
            this.loading = false;
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
        this.$refs.esign.generate().then((res) => { // 使用生成器调用把签字的图片转换成为base64图片格式
          this.resultImg = res;
        }).catch(() => {
          // 画布没有签字时会执行这里提示一下
          this.$message({type: "warning", message: "请签名后再生成签字图片",});
        });
        // 在这里向后端发请求把转换后的base64文件传给后端，后端接收以后再转换成图片做静态图片存储
        // 当然也可以把base64转成流文件blob格式的，类似上传给后端这样，具体哪种方式看后端要求
        setTimeout(() => {
          // 这里要使用定时器稍微延后以后就能取到base64数据了，当然也可以再加一个确认按钮，如：确认使用这张base64签名图片
          // 点击确认以后，在其回调函数中，再把base64的签名图片传给后端用于存储
          req('post', 'high/confirm/approval', {
            id: this.form.id,
            content: this.form.content ? this.form.content : '',
            signature: this.resultImg,
          }).then(res => {
            // console.log(res)
            if (res.data.code === 0) {
              this.$message({type: "success", message: "审批成功！"});
            }
            this.form = {};
            this.resultImg = '';
            this.onLoad();
          }, error => {
            window.console.log(error);
          });
        }, 200);
        this.dialogVisible = false;
      },
      onConfirm(row) {
        req('post', 'high/confirm/approval/time', {
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
      onLook(row, type) {
        this.dialogVisible = true;
        this.resultImg = row.signature;
        this.imgType = type;
      },
      cancel() {
        this.form = {};
        this.resultImg = '';
        this.dialogVisible = false;
        this.imgType = '';
      }
    },
    watch: {
      ticketId() {
        this.onLoad();
      }
    }
  }
</script>

<style scoped>

</style>
