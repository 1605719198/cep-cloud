<template>
  <div class="avue-crud">
    <div>
      <div class="content">
        <el-form :model="form"
                 ref="form"
                 label-width="120px"
                 :inline="true">
          <div class="title">
            <label>基本信息</label>
          </div>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="单位：">
                <span>{{form.department_name}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="完成情况：">
                <span>{{form.complete_state==0?"全部": form.complete_state==1? "已完成" : "未完成" }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="工作事项：">
                <span>{{form.work_items}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="完成目标：">
                <span>{{form.work_target}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <div class="title">
            <label>奖金信息</label>
          </div>
          <el-table v-loading="loading"
                    :data="form.tableData"
                    stripe
                    border
                    style="width: 100%">
            <el-table-column prop="responsible_user_no"
                             label="工号">
            </el-table-column>
            <el-table-column prop="responsible_user_name"
                             label="负责人">
            </el-table-column>
            <el-table-column prop="reward_proportion"
                             label="奖励比例(%)">
            </el-table-column>
          </el-table>
          <br>
          <div class="title">
            <label>现场照片</label>
          </div>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="整改前：">
                <el-image v-for="(item,index) in form.rectification_before_url"
                          :key="index"
                          :z-index="10000"
                          style="width: 100px; height: 100px;margin-right:10px;"
                          :src="item.url"
                          :preview-src-list="srcList">
                </el-image>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="整改后：">
                <el-image v-for="(item,index) in form.rectification_after_url"
                          :key="index"
                          :z-index="10000"
                          style="width: 100px; height: 100px;margin-right:10px;"
                          :src="item.url"
                          :preview-src-list="afterSrcList">
                </el-image>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- <div class="tableContent"></div> -->
    <div class="el-dialog__footer"
         style="padding:20px 0 0 0;">
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>

  </div>
</template>

<script>
import { listProportions } from "@/api/security/si/governance/index";
export default {
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      loading: false,
      //查看图片列表
      srcList: [],
      afterSrcList: []
    }
  },
  created () {
    this.form = { ...this.data }
    this.form.rectification_before_url.forEach(item => {
      this.srcList.push(item.url)
    })
    this.form.rectification_after_url.forEach(item => {
      this.afterSrcList.push(item.url)
    })
    if (this.form.id) {
      this.onLoad(this.form.id)
    }
  },
  methods: {
    //奖励比例列表
    onLoad (id) {
      this.loading = true
      listProportions({ id }).then(res => {
        // console.log(res)
        this.form.tableData = res.data
        this.loading = false
      })
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
.title {
  border-left: 10px solid #409eff;
  margin-bottom: 20px;
}

.title label {
  margin-left: 15px;
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  font-weight: 700;
  text-decoration: none;
}

::v-deep.el-form-item {
  margin-bottom: 0;
}
.content {
  font-size: 14px;
  span {
    font-family: 微软雅黑;
    font-style: normal;
    font-size: 15px;
  }
}

::v-deep.el-form-item__label {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  font-weight: 700;
  font-style: normal;
  color: rgb(153, 153, 153);
}

.tableContent {
  border: 1px solid #ebeef5;
  border-bottom: unset;
}
</style>
