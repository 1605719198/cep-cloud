<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="上报信息"
                   name="appear">
        <Appear :formData="formData"
                v-if="this.formData.danger_id" />
      </el-tab-pane>
      <el-tab-pane label="整改信息"
                   name="rectify">
        <Rectify :formData="formData"
                 v-if="this.formData.danger_id" />
      </el-tab-pane>
      <el-tab-pane v-if="this.type != 'edit'"
                   label="验收信息"
                   name="check">
        <Check :formData="formData"
               v-if="this.formData.danger_id" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Appear from './appear'
import Rectify from './rectify'
import Check from './check'
import { safeDangerInfo } from "@/api/security/si/hiddenDanger/index";
export default {
  props: {
    type: {
      type: String
    },
    danger_id: {
      type: String
    }
  },
  components: {
    Appear,
    Rectify,
    Check
  },
  data () {
    return {
      activeName: 'appear',
      formData: {}
    };
  },
  created () {
    if (this.type == 'edit') {
      this.activeName = 'rectify'
    }
    if (this.type == 'check') {
      this.activeName = 'check'
    }
    this.getData()
  },
  methods: {
    getData () {
      safeDangerInfo({ danger_id: this.danger_id }).then(res => {
        // console.log(res)
        this.formData = { ...res.data.data, type: this.type, danger_id: this.danger_id }
      })
    },
  }
}
</script>

<style>
</style>
