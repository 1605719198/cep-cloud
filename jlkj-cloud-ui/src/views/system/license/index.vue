<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="getInfos"
          v-hasPermi="['system:license:read']"
        >读取信息
        </el-button>
      </el-col>
    </el-row>
    <el-form ref="form" :model="form" label-width="115px">
      <el-row :gutter="10" class="mb8">
        <el-divider><i class="el-icon-mobile-phone"></i>设备信息</el-divider>
        <el-col :span="4">
          <el-form-item label="IP地址：" prop="ipAddress">
            {{ form.licenseCheckModel.ipAddress }}
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="Mac地址：" prop="macAddress">
            {{ form.licenseCheckModel.macAddress }}
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="CPU序列号：" prop="cpuSerial">
            {{ form.licenseCheckModel.cpuSerial }}
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="主板序列号：" prop="mainBoardSerial">
            {{ form.licenseCheckModel.mainBoardSerial }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-divider><i class="el-icon-truck"></i>操作系统</el-divider>
        <el-col :span="10">
          <el-form-item label="操作系统：">
            <el-radio-group v-model="form.licenseCheckModel.osName">
              <el-radio label="windows">Windows</el-radio>
              <el-radio label="linux">Linux</el-radio>
              <el-radio label="mac">Mac OS</el-radio>
              <el-radio label="cent">Cent OS</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="机器码：" prop="machineCode">
            <el-input v-model="form.licenseCheckModel.machineCode"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="8" class="mb8">
        <el-divider><i class="el-icon-medal-1"></i>授权信息</el-divider>
        <el-col :span="1.5">
          <el-form-item label="是否永久：">
            <el-radio-group v-model="form.isNoTimeLimit" @change="select">
              <el-radio label="Y">是</el-radio>
              <el-radio label="N">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="使用者数量：" prop="userNum">
            <el-input-number v-model="form.licenseCheckModel.userNum" placeholder="请输入使用者数量" maxlength="5" :precision="0"
                             :step="1" :min="1" :max="99999">
            </el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="有效期限：" prop="expiryTime" v-show="isShow">
            <el-date-picker clearable
                            class="maxWidth"
                            v-model="form.expiryTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="请选择有效期限"
                            @input="dataPickerChange"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="证书描述信息：" prop="description">
            <el-input v-model="form.description" placeholder="请输入证书描述信息" clearable @input="descInput"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="8" class="mb8">
        <el-col :span="1.5">
          <el-form-item label="授权项目：" prop="subject">
            <el-input v-model="form.subject" placeholder="请输入授权项目名称（英文）" clearable @input="subjectInput"/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="私钥名称：" prop="privateAlias">
            <el-input v-model="form.privateAlias" placeholder="请输入私钥名称（英文）" clearable @input="aliasInput"/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="私钥密码：" prop="keyPass">
            <el-input type="password" v-model="form.keyPass" placeholder="请输入私钥密码（英文）" clearable
                      show-password @input="keyPassInput"/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="授权库密码：" prop="storePass">
            <el-input type="password" v-model="form.storePass" placeholder="请输入授权库密码（英文）" clearable
                      show-password @input="storePassInput"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="8" class="mb8">
        <el-col :span="1.5">
          <el-form-item label="证书存放位置：" prop="licensePath">
            <el-input v-model="form.licensePath" placeholder="例：D:/xxx/xxxx.lic" clearable @input="licensePathInput"/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="私钥文件存放位置：" prop="privateKeysStorePath" label-width="140px">
            <el-input v-model="form.privateKeysStorePath" placeholder="例：D:/xxx/xxxx.keystore" clearable @input="privateKeysStorePathInput"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" class="mb8" type="flex" align="middle">
        <el-col :span="22">
          <el-form-item label="JSON数据：" prop="jsonData">
            <el-input type="textarea" v-model="form.jsonData" maxlength="2500" show-word-limit resize="none" :rows="6"
                      class="textarea-box"/>
          </el-form-item>
        </el-col>
        <el-col :span="2">
          <el-button @click="doCopy" icon="el-icon-document-copy" size="small">复制</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10" class="mb8" type="flex" justify="center">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="small"
          @click="getKeyTool"
          v-hasPermi="['system:license:create']"
        >获取keytool命令
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-printer"
          size="small"
          @click="generateLicense"
          v-hasPermi="['system:license:create']"
        >生成授权License
        </el-button>
      </el-col>
    </el-row>

    <el-dialog title="获取生成公私文件命令" :visible.sync="open" width="800px" append-to-body :close-on-click-modal="false">
      <el-form>
        <el-form-item label="#生成命令：" >
          <el-input type="textarea" v-model="createCommand" resize="none" :rows="4"
                    class="textarea-box"/>
        </el-form-item>
        <el-form-item label="#导出命令：" >
          <el-input type="textarea" v-model="exportCommand" resize="none" :rows="4"
                    class="textarea-box"/>
        </el-form-item>
        <el-form-item label="#导入命令：" >
          <el-input type="textarea" v-model="importCommand" resize="none" :rows="4"
                    class="textarea-box"/>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {getServerInfos, generater} from "@/api/system/license";
import {dateFormat, parseTime} from "@/utils/jlkj";

export default {
  name: "License",
  data() {
    return {
      // 生成命令
      createCommand:`keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.keystore" -storepass "public_password1234" -keypass "private_password1234" -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"`,
      // 导出命令
      exportCommand: `keytool -exportcert -alias "privateKey" -keystore "privateKeys.keystore" -storepass "public_password1234" -file "certfile.cer"`,
      // 导入命令
      importCommand: `keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.keystore" -storepass "public_password1234"`,
      // 打开弹框
      open: false,
      // 遮罩层
      loading: true,
      isShow: true,
      // 字典表格数据
      infos: {
      },
      // 表单参数
      form: {
        subject: "",
        privateAlias: "privateKey",
        keyPass: "",
        storePass: "",
        licensePath: "",
        privateKeysStorePath: "",
        issuedTime: "",
        expiryTime: "",
        consumerType:"User",
        consumerAmount: 1,
        description: "",
        isNoTimeLimit: 'N',
        licenseCheckModel:{
          ipAddress: [],
          macAddress: [],
          cpuSerial: "",
          mainBoardSerial: "",
          osName: "",
          machineCode: "",
          userNum: 1
        }
      },
    };
  },
  watch: {
    'form.isNoTimeLimit': {
      handler(newVal,oldVal) {
        if (newVal === 'Y') {
          this.form.expiryTime = dateFormat(new Date(new Date().setFullYear(new Date().getFullYear()+100)))
        } else {
          this.form.expiryTime = dateFormat(new Date())
        }
      }
    }
  },
  created() {
    this.getInfos();
  },
  methods: {
    /** 查询设备信息 */
    getInfos() {
      this.loading = true;
      getServerInfos().then(response => {
        this.form.licenseCheckModel.ipAddress = response.data.ipAddress
        this.form.licenseCheckModel.macAddress = response.data.macAddress
        this.form.licenseCheckModel.cpuSerial = response.data.cpuSerial
        this.form.licenseCheckModel.mainBoardSerial = response.data.mainBoardSerial
        this.form.licenseCheckModel.osName = response.data.osName
        this.form.licenseCheckModel.machineCode = response.data.machineCode
        this.form.issuedTime = dateFormat(new Date());
        this.form.jsonData = JSON.stringify(response.data);
        this.loading = false;
        }
      );
    },
    //复制授权信息码
    doCopy() {
      this.$copyText(this.form.jsonData).then(
        (e) => {
          this.$modal.msgSuccess("内容已复制到剪切板");
        },
        (e) => {
          this.$modal.msgError("抱歉，复制失败！");
        })
    },
    select() {
      this.$set(this.form, 'isNoTimeLimit', this.isShow == true ? 'Y' : 'N');
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
      this.isShow = !this.isShow;
    },
    dataPickerChange() {
      this.$set(this.form, 'expiryTime', this.form.expiryTime);
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    descInput() {
      this.$set(this.form, 'description', this.form.description);
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    aliasInput() {
      this.$set(this.form, 'privateAlias', this.form.privateAlias);
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    subjectInput() {
      this.$set(this.form, 'subject', this.form.subject);
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    keyPassInput() {
      this.$set(this.form, 'keyPass', this.form.keyPass);
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    storePassInput() {
      this.$set(this.form, 'storePass', this.form.storePass);
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    licensePathInput() {
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    privateKeysStorePathInput() {
      this.form.jsonData = "";
      this.form.jsonData = JSON.stringify(this.form);
    },
    // JDK自带的 keytool 工具生成公私钥证书库
    getKeyTool() {
      this.open = true
    },
    // 生成授权
    generateLicense() {
      generater(this.form).then(response => {
        this.$modal.msgSuccess("授权成功");
      })
    }
  }
};
</script>
