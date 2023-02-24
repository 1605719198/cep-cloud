<template>
  <el-table stripe
            height="55h"
            :data="tableDataPlusElement"
            tooltip-effect="dark"
            style="margin: 0 20px;width: auto;">
    <el-table-column
      label="序号"
      type="index"
      width="100">
    </el-table-column>
    <el-table-column prop="sendStation" label="结果接收站">
      <template slot-scope="scope">
        <el-select v-model="scope.row.sendStation" @change="selectSendStation" :popper-append-to-body="false" placeholder="请选择">
          <el-option v-for="item in optionsSendStation" :key="item.dictLabel" :label="item.dictLabel" :value="item.dictValue">
          </el-option>
        </el-select>
      </template>
    </el-table-column>
    <el-table-column prop="sendFormat" label="发送格式">
      <template slot-scope="scope">
        <el-select v-model="scope.row.sendFormat" :popper-append-to-body="false" placeholder="请选择">
          <el-option v-for="item in optionsSendFormat" :key="item.dictValue" :label="item.dictValue" :value="item.dictValue">
          </el-option>
        </el-select>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { queryDataByParams } from "@/api/system/dict/data"

export default {
  name: "sampleDataJG",
  props: {
    formDataJG: Object,
    sendDataJG: Array,
    editBox: Boolean,
    detailBox: Boolean
  },
  data(){
    return{
      addForm: {},
      tableDataPlusElement: [
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',},
        {sendStation: '', sendFormat: '',}
      ],
      optionsSendStation: [],
      optionsSendFormat: [],
      //当前页
      page: 1,
      //每页记录数
      limit: 20,
      query: {
        dictType: undefined,
        dictLabel: undefined
      }
    }
  },
  watch: {
    formDataJG: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  methods: {
    selectSendStation(val) {
      this.optionsSendFormat = [];
      this.tableDataPlusElement.sendFormat = null
      this.query.dictType = "quailty_send_format"
      this.query.dictLabel = val
      this.getList();
    },
    getList() {
      queryDataByParams(this.page, this.limit, this.query).then(response => {
        this.optionsSendFormat = response.data.data.list
      })
    }
  },
  created() {
    this.getDicts("quailty_send_station").then(response =>{
      this.optionsSendStation = response.data.data;
    })
    if(typeof(this.sendDataJG) != "undefined") {
      if (this.editBox == true || this.detailBox == true) {//传值
        let start = this.sendDataJG.length
        if (10 - start <= 10) {
          for (let i = 1; i <= 10 - start; i++) {
            this.sendDataJG.splice(start + i, 1, {sendStation: '', sendFormat: '',});
          }
        }
        this.tableDataPlusElement = this.sendDataJG
      }
    }
  },
  mounted() {
    this.formDataJG.sendValues = this.tableDataPlusElement//保存传值
    this.$emit('update:formDataJG', this.addForm)
  }
}
</script>

<style scoped>
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-form-item__content {
  width: 250px;
}

</style>
