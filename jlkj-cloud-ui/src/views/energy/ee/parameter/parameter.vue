<template>
  <el-row class="theme-hey">
    <el-col :span="4"
            class="avue-sidebar leftClass">
      <div class="titleClass">
        <i class="el-icon-s-flag" />
        <span>&nbsp;&nbsp;&nbsp;能源参数维护项</span>
      </div>
      <el-menu default-active="1"
               class="el-menu-vertical-demo">
        <el-menu-item index="1"
                      @click="handleChange('ZBX')">
          <i class="el-icon-menu" />
          <span slot="title">指标项维护</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="20">
      <div class="rightClass">
        <parameterItem v-if="tabPosition==='ZBX' " />
        <div v-else></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { mapGetters } from "vuex";
import parameterItem from "./informationConfiguration/index";


export default {
  name: "parameter",
  components: {
    parameterItem,
  },
  data () {
    return {
      tabPosition: 'ZBX'
    };
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    handleChange (val) {
      this.tabPosition = val
    }
  }
}
</script>

<style scoped>
.titleClass {
  height: 70px;
  text-align: center;
  line-height: 70px;
  font-size: 20px;
  color: #999999;
  font-weight: 400;
  text-decoration: none;
}

.theme-hey {
  padding-left: 10px;
}

.leftClass {
  height: 89vh;
  background-color: #f9f9f9;
  border-radius: 20px 0 0 20px;
}

.rightClass {
  height: 89vh;
  width: 99%;
  background-color: #fff;
  border-radius: 0 20px 20px 0;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
