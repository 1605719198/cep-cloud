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
    <el-form ref="form" :model="infos" label-width="115px">
      <el-row :gutter="10" class="mb8">
        <el-divider><i class="el-icon-mobile-phone"></i>设备信息</el-divider>
        <el-col :span="4">
          <el-form-item label="IP地址：" prop="ipAddress">
            {{ infos.ipAddress }}
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="Mac地址：" prop="macAddress">
            {{ infos.macAddress }}
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="CPU序列号：" prop="cpuSerial">
            {{ infos.cpuSerial }}
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="主板序列号：" prop="mainBoardSerial">
            {{ infos.mainBoardSerial }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-divider><i class="el-icon-truck"></i>操作系统</el-divider>
        <el-col :span="10">
          <el-form-item label="操作系统：">
            <el-radio-group v-model="infos.osName">
              <el-radio label="windows">Windows</el-radio>
              <el-radio label="linux">Linux</el-radio>
              <el-radio label="mac">Mac OS</el-radio>
              <el-radio label="cent">Cent OS</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="机器码：" prop="machineCode">
            <el-input v-model="infos.machineCode"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="8" class="mb8">
        <el-divider><i class="el-icon-medal-1"></i>授权信息</el-divider>
        <el-col :span="1.5">
          <el-form-item label="是否永久：">
            <el-radio-group v-model="infos.isNoTimeLimit" @change="select">
              <el-radio label="Y">是</el-radio>
              <el-radio label="N">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="使用者数量：" prop="userAcount">
            <el-input-number v-model="infos.userAcount" placeholder="请输入使用者数量" maxlength="5" :precision="0"
                             :step="1" :min="1" :max="99999">
            </el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="有效期限：" prop="licenseLimit" v-show="isShow">
            <el-date-picker clearable
                            class="maxWidth"
                            v-model="infos.licenseLimit"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="请选择有效期限">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="证书描述信息：" prop="description">
            <el-input v-model="infos.description" placeholder="请输入证书描述信息" clearable/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="8" class="mb8">
        <el-col :span="1.5">
          <el-form-item label="授权项目：" prop="subject">
            <el-input v-model="infos.subject" placeholder="请输入授权项目名称（英文）" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="私钥名称：" prop="privateAlias">
            <el-input v-model="infos.privateAlias" placeholder="请输入私钥名称（英文）" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="私钥密码：" prop="keyPass">
            <el-input type="password" v-model="infos.keyPass" placeholder="请输入私钥密码（英文）" clearable
                      show-password/>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-form-item label="授权库密码：" prop="storePass">
            <el-input type="password" v-model="infos.storePass" placeholder="请输入授权库密码（英文）" clearable
                      show-password/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24" class="mb8" type="flex" align="middle">
        <el-col :span="22">
          <el-form-item label="JSON数据：" prop="jsonData">
            <el-input type="textarea" v-model="infos.jsonData" maxlength="2500" show-word-limit resize="none" :rows="6"
                      class="textarea-box"/>
          </el-form-item>
        </el-col>
          <el-col :span="2" >
          <el-button @click="doCopy"  icon="el-icon-document-copy" size="small">复制</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10" class="mb8" type="flex" justify="center">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-printer"
          size="small"
          @click="getInfos"
          v-hasPermi="['system:license:create']"
        >生成授权License
        </el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getServerInfos} from "@/api/system/license";

export default {
  name: "License",
  data() {
    return {
      // 遮罩层
      loading: true,
      isShow: true,
      // 字典表格数据
      infos: {
        isNoTimeLimit: 'N',
        userAcount: 10,
        description: "",
        privateAlias: "privateKey",
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getInfos();
  },
  methods: {
    /** 查询字典类型列表 */
    getInfos() {
      this.loading = true;
      getServerInfos().then(response => {
          this.infos = response.data;
          this.infos.isNoTimeLimit = 'N';
          this.infos.userAcount = 10;
          this.infos.privateAlias = "privateKey";
          this.infos.consumerType = "User";
          this.infos.jsonData = JSON.stringify(response.data);
          this.loading = false;
        }
      );
    },
    doCopy() {
      this.$copyText(this.infos.jsonData).then(
        (e) => {
          this.$message.success("内容已复制到剪切板");
          console.log(e);
        },
        (e) => {
          this.$message.error("抱歉，复制失败！");
        })
    },
    select(){
      this.isShow = !this.isShow;
    }
  }
};
</script>
