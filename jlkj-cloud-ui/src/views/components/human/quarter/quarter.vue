<template>
  <div>
    <el-input v-model="seasonValue" class="w-50 m-2" placeholder="请选择季度" style="width:220px" @focus="showSeason = true" prefix-icon="el-icon-date" />
    <el-card class="box-card" v-if="showSeason" style="height:180px;margin-left:60px;margin-top:10px;width:300px;z-index:999;position:fixed">
      <template #header>
        <div class="card-header">
          <el-button @click="prev" style="border:none" icon="el-icon-d-arrow-left"></el-button>
          <span style="text-align:center">{{year}}</span>
          <el-button  @click="next" style="border:none" icon="el-icon-d-arrow-right"></el-button>
        </div>
      </template>
      <div class="text item" style="text-align:center;">
        <el-button
          type="text"
          size="medium"
          style="width:40%;color: #606266;float:left;"
          @click="selectSeason('第一季度')"
        >第一季度</el-button>
        <el-button
          type="text"
          size="medium"
          style="float:right;width:40%;color: #606266;"
          @click="selectSeason('第二季度')"
        >第二季度</el-button>
      </div>
      <div class="text item" style="text-align:center;">
        <el-button
          type="text"
          size="medium"
          style="width:40%;color: #606266;float:left;"
          @click="selectSeason('第三季度')"
        >第三季度</el-button>
        <el-button
          type="text"
          size="medium"
          style="float:right;width:40%;color: #606266;"
          @click="selectSeason('第四季度')"
        >第四季度</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "quarter",
  data() {
    return {
      showSeason: false, //是否显示选择季度面板
      year: new Date().getFullYear(), //默认当前年
      seasonValue: '', //input中显示的内容
      time: ''
    }
  },
  created() {
  },
  methods: {
    prev() {
      this.year = +this.year - 1
    },
    next() {
      this.year = +this.year + 1
    },
    selectSeason(quarter) {
      this.seasonValue = this.year.toString() + '-' + quarter.toString()
      this.showSeason = false
      switch(quarter){
        case '第一季度':
          this.time = this.year.toString() + '-' + '13'
          break;
        case '第二季度':
          this.time = this.year.toString() + '-' + '14'
          break;
        case '第三季度':
          this.time = this.year.toString() + '-' + '15'
          break;
        case '第四季度':
          this.time = this.year.toString() + '-' + '16'
          break;
      }
      this.$emit('getQuarter',this.time)  //传值给父组件
    }
  }
}
</script>

<style scoped>

</style>
