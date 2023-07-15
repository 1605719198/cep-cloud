<template>
  <div>
    <el-row class="theme-hey">
      <el-col :span="4"
              class="avue-sidebar leftClass">
        <table-tabs title="料场参数维护项"
                    :tableList="tableList"
                    @handleChange="handleChange($event)"></table-tabs>
      </el-col>
      <el-col :span="20">
        <div class="rightClass">
          <stockMaintain v-if="tabPosition==='lcwh' " />
          <storeLocation v-else-if="tabPosition==='cwwh'" />
          <materialType v-else-if="tabPosition==='type'" />
          <materialCode v-else-if="tabPosition==='whcode'" />
          <materialCodeSmall v-else-if="tabPosition==='xmz'" />
          <div v-else></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import materialCode from './materialCode/index.vue'
import materialType from './materialType/index.vue'
import stockMaintain from './stockMaintain/index.vue'
import storeLocation from './storeLocation/index.vue'
import materialCodeSmall from './materialCodeSmall/index.vue'

export default {
  components: {
    materialCode,
    materialType,
    stockMaintain,
    storeLocation,
    materialCodeSmall,
  },
  data () {
    return {
      tableList: [
        {
          title: '料仓维护',
          code: 'lcwh',
          icon: 'el-icon-setting'
        },
        {
          title: '储位维护',
          code: 'cwwh',
          icon: 'el-icon-user-solid'
        },
        {
          title: '物料类别',
          code: 'type',
          icon: 'el-icon-star-on'
        },
        {
          title: '物料代码',
          code: 'whcode',
          icon: 'el-icon-question'
        },
        {
          title: '物料小煤种',
          code: 'xmz',
          icon: 'el-icon-s-cooperation'
        }
      ],
      tabPosition: 'lcwh'
    }
  },
  methods: {
    handleChange (val) {
      this.tabPosition = val
    }
  }
}
</script>

<style scoped>
.theme-hey {
  padding: 5px 0 0 20px;
}
.leftClass {
  height: 87vh;
  background-color: #f9f9f9;
  border-radius: 20px 0 0 20px;
}
.rightClass {
  height: 87vh;
  width: 99%;
  background-color: #fff;
  border-radius: 0 20px 20px 0;
}
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
