<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form" :model="form">
        <div style="text-align: center; min-width: 500px;margin-bottom: 20px;">
          <el-radio-group v-model="tabPosition">
            <el-radio-button label="inStorage">焦炭入库</el-radio-button>
            <el-radio-button label="outStorage">焦炭出库</el-radio-button>
            <el-radio-button label="adjustment">调整记录</el-radio-button>
          </el-radio-group>
        </div>
        <div>
          <InOutStorage :type="tabPosition" v-if="tabPosition==='inStorage'||tabPosition==='outStorage'" :data="data"/>
          <Adjustment v-if="tabPosition==='adjustment'"/>
        </div>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import InOutStorage from "./inOutStorage"
  import Adjustment from "./adjustment"

  export default {
    components: {
      InOutStorage,
      Adjustment
    },
    props: ['data'],
    data() {
      return {
        tabPosition: 'inStorage',
        form: {},
      }
    },
    created() {
    },
    computed: {
    },
    methods: {
      cancel() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped lang="scss">
  ::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
