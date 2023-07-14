<template>


  <div class="avue-crud el-card__body" style="background-color: #FFF;height: 100%;">
    <div class="avue-crud__search" style="width:99%;">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="5">
            <el-date-picker v-model="queryDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"/>
          </el-col>
          <el-col :span="6">
            <div class="el-form-item__content" style="margin-left: 0px;">
              <el-button size="medium" icon="el-icon-search" type="primary" @click="handleQuery">搜 索</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>


    </div>
    <iframe v-if="isShow" height="90%" width="100%" :src="src"></iframe>

  </div>
</template>

<script>
import {dateFormat} from '@/utils/date'

export default {
  name: "cokeProcess",
  data() {
    return {
      isShow: false,
      url: '/ureport/preview?_u=mysql:工艺日报.ureport.xml',
      src: '',
      queryDate: ''
    }
  },
  created() {
    this.queryDate = dateFormat(new Date(), 'yyyy-MM-dd');
    this.src = this.GLOBAL.pdfFileUrl + this.url + '&queryDate=' + this.queryDate;
    this.isShow = true;
  },
  methods: {
    handleQuery() {
      this.isShow = false;
      this.src = this.GLOBAL.pdfFileUrl + this.url + '&queryDate=' + this.queryDate;
      this.isShow = true;
    },

  }
}
</script>

<style scoped>

</style>
