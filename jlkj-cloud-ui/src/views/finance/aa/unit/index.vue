<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="计量单位" name="first">
        <unit ref="unit"  @change="changeData"></unit>
      </el-tab-pane>
      <el-tab-pane label="单位转换设置" name="second">
        <unitChg ref="unitChg"
                 :unitName="unitName" :unitId="unitId" ></unitChg>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import unit from "@/views/finance/aa/unit/unit";
import unitChg from "@/views/finance/aa/unit/unitChg";



export default {
    name: "index",
    components: {unit,unitChg},
    data() {
      return {
        activeName: 'first',
        Verify:null,
        unitName:null,
        unitId:null
      };
  },

  methods: {
    handleClick(tab, event) {


      this.activeName = tab.name


    },
    changeData(tabName,unitName,unitId){
      this.activeName = tabName;
      this.unitName=unitName;
      this.unitId=unitId
    }
  }


};
</script>

