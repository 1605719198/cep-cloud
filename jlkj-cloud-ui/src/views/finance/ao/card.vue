<template>
  <div>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="银行卡号" prop="cardNo">
      <el-input v-model="form.cardNo" placeholder="请输入银行卡号">
        <template slot="append">
          <el-upload
            class="upload-demo"
            action=""
            :auto-upload="false"
            :on-change='handleChangeUpload'
            :on-exceed="handleExceed"
            :show-file-list="false"
            list-type="picture">
            <el-button size="small" type="primary"><i class="el-input__icon el-icon-camera" style="color: black"></i></el-button>
          </el-upload>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item label="开户银行" prop="janBudget">
      <el-input v-model="form.janBudget" placeholder="请输入开户银行" />
    </el-form-item>
    <el-form-item label="联行号" prop="bankNo">
      <el-input v-model="form.bankNo" placeholder="请输入联行号" />
    </el-form-item>
    <el-form-item label="开户地区" prop="area">
      <el-input v-model="form.area" placeholder="请输入开户地区" />
    </el-form-item>
    <el-form-item label="省份" prop="province">
      <el-input v-model="form.province" placeholder="请输入省份" />
    </el-form-item>
    <el-form-item label="城市" prop="city">
      <el-input v-model="form.city" placeholder="请输入城市" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submitForm">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>



  <!-- vueCropper 剪裁图片实现-->
  <el-dialog title="图片剪裁" :visible.sync="dialogVisible" class="crop-dialog" append-to-body>
    <div class="cropper-content">
      <div class="cropper" style="text-align:center">
        <vueCropper
          ref="cropper"
          :img="option.img"
          :outputSize="option.size"
          :outputType="option.outputType"
          :info="true"
          :full="option.full"
          :canMove="option.canMove"
          :canMoveBox="option.canMoveBox"
          :original="option.original"
          :autoCrop="option.autoCrop"
          :fixed="option.fixed"
          :fixedNumber="option.fixedNumber"
          :centerBox="option.centerBox"
          :infoTrue="option.infoTrue"
          :fixedBox="option.fixedBox"
          :autoCropWidth="option.autoCropWidth"
          :autoCropHeight="option.autoCropHeight"
          @cropMoving="cropMoving"
        />
      </div>
    </div>
    <div class="action-box">
      <el-upload class="upload-demo"
                 action=""
                 :auto-upload="false"
                 :show-file-list="false"
                 :on-change='handleChangeUpload'>
        <el-button type="primary" plain>更换图片</el-button>
      </el-upload>
      <!-- 这里的按钮可以根据自己的需求进行增删-->
      <el-button type="primary" plain @click="clearImgHandle">清除图片</el-button>
      <el-button type="primary" plain @click="rotateLeftHandle">左旋转</el-button>
      <el-button type="primary" plain @click="rotateRightHandle">右旋转</el-button>
      <el-button type="primary" plain @click="changeScaleHandle(1)">放大</el-button>
      <el-button type="primary" plain @click="changeScaleHandle(-1)">缩小</el-button>
      <el-button type="primary" plain @click="downloadHandle('blob')">下载</el-button>
    </div>
    <div style="position: absolute; bottom: 180px; left: 30px"><span style="color: white">请对齐左右边距即可</span></div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="finish" :loading="loading">确认</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
import {addCard, queryCard} from "@/api/finance/ao/card";
import {VueCropper} from "vue-cropper";

export default {
  name: "Card",
  components: {
    VueCropper
  },
  data() {
    return {
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cardNo: [
          {required: true, message: "银行卡号不能为空", trigger: "blur"},
          {max: 19, trigger: 'blur', message: "银行卡号不能超过19位"}
        ],
        janBudget: [
          {required: true, message: "开户银行不能为空", trigger: "blur"},
        ],
        area: [
          {required: true, message: "开户地区不能为空", trigger: "blur"},
        ],
        province: [
          {required: true, message: "省份不能为空", trigger: "blur"},
        ],
        city: [
          {required: true, message: "城市不能为空", trigger: "blur"},
        ],
      },
      isPreview: false,
      dialogVisible: false,
      previewImg: '', // 预览图片地址
      // 防止重复提交
      loading: false,
      // 裁剪组件的基础配置option
      option: {
        img: '', // 裁剪图片的地址
        info: true, // 裁剪框的大小信息
        outputSize: 1, // 裁剪生成图片的质量
        outputType: 'png', // 裁剪生成图片的格式
        canScale: true, // 图片是否允许滚轮缩放
        autoCrop: true, // 是否默认生成截图框
        canMoveBox: true, // 截图框能否拖动
        autoCropWidth: 400, // 默认生成截图框宽度
        autoCropHeight: 300, // 默认生成截图框高度
        fixedBox: true, // 固定截图框大小 不允许改变
        fixed: false, // 是否开启截图框宽高固定比例
        fixedNumber: [1, 1], // 截图框的宽高比例
        full: false, // 是否输出原图比例的截图
        original: false, // 上传图片按照原始比例渲染
        centerBox: false, // 截图框是否被限制在图片里面
        infoTrue: true // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
      }
    };
  },
  created() {
   this.getUserInfo()
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo(){
      queryCard(this.$store.state.user.userInfo.userName).then(res => {
        this.form = res.data;
        this.form.name = this.$store.state.user.userInfo.nickName;
        this.form.deptName = this.$store.state.user.userInfo.dept.deptName;
        this.form.userNo = this.$store.state.user.userInfo.userName;
        this.form.deptId = this.$store.state.user.userInfo.compId;
      })
    },
    /** 保存按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addCard(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    },
    //文件超出个数限制时的钩子
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    // 上传按钮 限制图片大小和类型
    handleChangeUpload(file, fileList) {
      const isJPG = file.raw.type === 'image/jpeg' || file.raw.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false
      }
      // 上传成功后将图片地址赋值给裁剪框显示图片
      this.$nextTick(async () => {
        // base64方式
        // this.option.img = await fileByBase64(file.raw)
        this.option.img = URL.createObjectURL(file.raw)
        this.loading = false
        this.dialogVisible = true
      })
    },
    // 放大/缩小
    changeScaleHandle(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // 左旋转
    rotateLeftHandle() {
      this.$refs.cropper.rotateLeft();
    },
    // 右旋转
    rotateRightHandle() {
      this.$refs.cropper.rotateRight();
    },
    // 下载
    downloadHandle(type) {
      let aLink = document.createElement('a')
      aLink.download = 'author-img'
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          let downImg = window.URL.createObjectURL(data)
          aLink.href = window.URL.createObjectURL(data)
          aLink.click()
        })
      } else {
        this.$refs.cropper.getCropData((data) => {
          let downImg = data;
          aLink.href = data;
          aLink.click()
        })
      }
    },
    // 清理图片
    clearImgHandle() {
      this.option.img = ''
    },
    // 截图框移动回调函数
    cropMoving(data) {
      // 截图框的左上角 x，y和右下角坐标x，y
    },
    finish() {
      // 获取截图的 blob 数据
      this.$refs.cropper.getCropBlob((blob) => {
        let file = new FormData()
        var abc = new File([blob],'jpg', {type: 'image/jpeg', lastModified: Date.now()});
        file.append('file', abc)
        // 这里根据自己需要的格式上传服务器
        this.$axios.post(process.env.VUE_APP_BASE_API + '/imageIdentify/ocr/idCard', file, {
          headers: {
            'Content-Type': 'multipart/form-data',}
        }).then(res => {
          const {status, data} = res
          if (status === 200) {
            this.dialogVisible = false
            if(data.code == 500){
              this.$message.error("识别失败，请重试");
            }else {
              this.$message.success(data.msg);
            }
          } else {
            this.$message.error('识别错误请重试')
          }
        })
      })
    },
  }
};
</script>
<style lang="scss" scoped>
.cropper-content {
  .cropper {
    width: auto;
    height: 350px;
  }
}
</style>
<style scoped>
.input {
  width: 20%;
}
</style>
