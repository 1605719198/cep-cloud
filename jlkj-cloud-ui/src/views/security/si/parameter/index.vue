<template>
  <div class="main">
    <div>
    <el-row class="theme-hey">
      <el-col :span="4"
              class="avue-sidebar leftClass">
        <table-tabs title="安环参数维护项"
                    :tableList="tableList"
                    @handleChange="handleChange($event)"></table-tabs>
      </el-col>
      <el-col :span="20">
        <div class="rightClass">
          <ConstructionUnit v-if="tabPosition==='constructionUnit' " />
          <ConstructionPersonnel v-else-if="tabPosition==='constructionPersonnel'" />
          <PollingType v-else-if="tabPosition==='pollingType'" />
          <OperationArea v-else-if="tabPosition==='operationArea'" />
          <Occupation v-else-if="tabPosition==='occupation'" />
          <HazardLevel v-else-if="tabPosition==='hazardLevel'" />
          <KnowledgeCategory v-else-if="tabPosition==='knowledgeCategory'" />
          <EnvironmentalStandard v-else-if="tabPosition==='EnvironmentalStandard'" />
          <EnvironmentalCollectionPoints v-else-if="tabPosition==='EnvironmentalCollectionPoints'" />
          <div v-else></div>
        </div>
      </el-col>
    </el-row>

  </div>
  </div>

</template>

<script>
import ConstructionUnit from './constructionUnit/index.vue'
import ConstructionPersonnel from './constructionPersonnel/index.vue'
import PollingType from './pollingType/index.vue'
import OperationArea from './operationArea/index.vue'
import Occupation from './occupation/index.vue'
import HazardLevel from './hazardLevel/index.vue'
import KnowledgeCategory from './knowledgeCategory/index.vue'
import EnvironmentalStandard from './EnvironmentalStandard/index.vue'
import EnvironmentalCollectionPoints from './EnvironmentalCollectionPoints/index.vue'
export default {
  components: {
    ConstructionUnit,
    ConstructionPersonnel,
    PollingType,
    OperationArea,
    Occupation,
    HazardLevel,
    KnowledgeCategory,
    EnvironmentalStandard,
    EnvironmentalCollectionPoints,
  },
  data () {
    return {
      tableList: [
        {
          title: '施工单位',
          code: 'constructionUnit',
          icon: 'el-icon-tickets'
        },
        {
          title: '施工人员',
          code: 'constructionPersonnel',
          icon: 'el-icon-tickets'
        },
        {
          title: '安全巡检类型',
          code: 'pollingType',
          icon: 'el-icon-tickets'
        },
        {
          title: '作业区域',
          code: 'operationArea',
          icon: 'el-icon-tickets'
        },
        {
          title: '工种',
          code: 'occupation',
          icon: 'el-icon-tickets'
        },
        {
          title: '隐患等级',
          code: 'hazardLevel',
          icon: 'el-icon-tickets'
        },
        {
          title: '知识类别',
          code: 'knowledgeCategory',
          icon: 'el-icon-tickets'
        },
        {
          title: '环保标准指标',
          code: 'EnvironmentalStandard',
          icon: 'el-icon-tickets'
        },
        {
          title: '环保采集点位',
          code: 'EnvironmentalCollectionPoints',
          icon: 'el-icon-tickets'
        },
      ],
      tabPosition: 'constructionUnit'
    }
  },
  methods: {
    handleChange (val) {
      this.tabPosition = val
      // console.log(val,this.tabPosition,"val")
    }
  }
}
</script>

<style scoped lang="scss">
.theme-hey {
  padding: 5px 0 0 20px;
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
.leftClass[data-v-3ae7d5b7] {
    height: 89vh;
    background-color: #f9f9f9;
    border-radius: 20px 0 0 20px;
}
</style>
