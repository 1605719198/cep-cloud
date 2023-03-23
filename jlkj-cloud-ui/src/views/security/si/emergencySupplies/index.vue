<template>
  <div style="padding: 0px 10px 0px 10px;">
    <div class="header">
      <el-row>
        <el-col :span="6">
          <div class="header_title">应急物资</div>
        </el-col>
        <el-col :span="12">
          <div style="text-align: center;min-width: 585px">
            <el-radio-group v-model="tabPosition"
                            @change="handleChange">
              <!-- <el-radio-button label="Gas">气防站应急救援物资</el-radio-button> -->
              <el-radio-button label="焦炉车间">焦炉车间明细台账</el-radio-button>
              <el-radio-button label="煤焦车间">煤焦车间明细台账</el-radio-button>
              <el-radio-button label="发电车间">发电车间明细台账</el-radio-button>
            </el-radio-group>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="main">
      <!-- <Gas v-if="tabPosition=='Gas'" /> -->
      <!-- <CokeOven v-if="tabPosition=='CokeOven'" />
      <Tar v-if="tabPosition=='Tar'" /> -->
      <!-- <Generate v-else
                :workshop_name="tabPosition" /> -->
      <Generate :workshop_name="tabPosition" />
    </div>
  </div>
</template>

<script>
import Gas from './gas/index.vue'
import CokeOven from './cokeOven/index.vue'
import Tar from './tar/index.vue'
import Generate from './generate/index.vue'
export default {
  components: {
    Gas,
    CokeOven,
    Tar,
    Generate
  },
  data () {
    return {
      // tabPosition: 'Gas'
      tabPosition: '焦炉车间'
    }
  },
  methods: {
    handleChange (val) {
      console.log(val)
    }
  }
}
</script>

<style scoped lang="scss">
.header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.main {
  height: 85vh;
  background-color: #fff;
}
</style>

