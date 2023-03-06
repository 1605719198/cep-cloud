<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form">
        <div style="text-align: center; min-width: 500px;margin-bottom: 20px;">
          <el-radio-group v-model="tabPosition">
            <el-radio-button label="onCoal">配煤记录</el-radio-button>
            <el-radio-button label="unload">卸煤记录</el-radio-button>
            <el-radio-button label="adjustment">调整记录</el-radio-button>
          </el-radio-group>
        </div>
        <div>
          <OnCoal v-if="tabPosition==='onCoal'"  :data="data"/>
          <Unload v-if="tabPosition==='unload'"  :data="data"/>
          <Adjustment v-if="tabPosition==='adjustment'" :data="data" />
        </div>
        <!-- <el-form-item style="text-align: right;">
          <el-button @click="cancel">取消</el-button>
        </el-form-item> -->
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <!-- <el-button type="primary"
                 @click="submitForm('form')">提交</el-button> -->
    </div>
  </div>

</template>

<script>
// import { updateMaterials } from "@/api/material/mr/parameter/materialCode";
import { mapGetters } from "vuex";
import OnCoal from "./onCoal"
import Unload from "./unload"
import Adjustment from "./adjustment"
export default {
  components: {
    OnCoal,
    Adjustment,
    Unload
  },
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      tabPosition: 'onCoal',
      form: {},
    }
  },
  created () {
  },
  computed: {
  },
  methods: {
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
