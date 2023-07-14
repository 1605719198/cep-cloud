<template>
    <div class="main">
        <div>
            <el-row class="theme-hey">
                <el-col :span="4"
                        class="avue-sidebar leftClass">
                    <table-tabs title="焦化生产日报内容维护"
                                :tableList="tableList"
                                @handleChange="handleChange($event)"></table-tabs>
                </el-col>
                <el-col :span="20">
                    <div class="rightClass">
                        <OilsConsume v-if="tabPosition==='oilsConsume' " />
                        <IngredientsConsume v-else-if="tabPosition==='ingredientsConsume'" />
                        <CoalUnload v-else-if="tabPosition==='coalUnload'" />
                        <HoistingCost v-else-if="tabPosition==='hoistingCost'" />
                        <LogisticsCost v-else-if="tabPosition==='logisticsCost'" />
                        <div v-else></div>
                    </div>
                </el-col>
            </el-row>

        </div>
    </div>

</template>
  
  <script>
import OilsConsume from './oilsConsume/index'
import IngredientsConsume from './ingredientsConsume/index'
import CoalUnload from './coalUnload/index'
import HoistingCost from './hoistingCost/index'
import LogisticsCost from './logisticsCost/index'
export default {
    components: {
        OilsConsume,
        IngredientsConsume,
        CoalUnload,
        HoistingCost,
        LogisticsCost,
    },
    data() {
        return {
            tableList: [
                {
                    title: '油品消耗',
                    code: 'oilsConsume',
                    icon: 'el-icon-tickets',
                },
                {
                    title: '辅料消耗',
                    code: 'ingredientsConsume',
                    icon: 'el-icon-tickets',
                },
                {
                    title: '煤场卸车情况',
                    code: 'coalUnload',
                    icon: 'el-icon-tickets',
                },
                {
                    title: '吊装费用',
                    code: 'hoistingCost',
                    icon: 'el-icon-tickets',
                },
                {
                    title: '物流费用',
                    code: 'logisticsCost',
                    icon: 'el-icon-tickets',
                },
            ],
            tabPosition: 'oilsConsume',
        }
    },
    methods: {
        handleChange(val) {
            this.tabPosition = val
            // console.log(val,this.tabPosition,"val")
        },
    },
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
  