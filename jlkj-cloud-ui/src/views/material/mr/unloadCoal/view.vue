<template>
  <div class="avue-crud">
    <div>
      <div class="title">
        <label>基本信息</label>
      </div>
      <div class="content">
        <el-form :model="form" ref="form" label-width="120px" :inline="true">
          <el-row :gutter="10">
            <!-- <el-col :span="12">
              <el-form-item label="编号：">
                <span>202212365</span>
              </el-form-item>
            </el-col> -->
            <el-col :span="12">
              <el-form-item label="检斤计划编号：">
                <span>{{ form.plan_id }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="检斤计划日期：">
                <span>{{ form.plan_date }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="供货单位名称：">
                <span>{{ form.supplier_name }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="运输方式：">
                <span>{{ form.shipping_method }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="车号：">
                <span>{{ form.vehicle_number }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="储位编号：">
                <span>{{ form.storage_maintain_space_name }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="物料名称：">
                <span>{{ form.material_name }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="物料代码：">
                <span>{{ form.material_code }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="物料重量(t)：">
                <span>{{ (form.material_weight / 1000).toFixed(3) }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="班次：">
                <span>{{ form.shift_name }} </span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="班别：">
                <span>{{ form.class_name }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <!-- <el-col :span="12">
              <el-form-item label="创建人：">
                <span>{{form.create_user_name}}</span>
              </el-form-item>
            </el-col> -->
            <el-col :span="12">
              <el-form-item label="卸车时间：">
                <span>{{ form.unloading_time }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="报文同步时间：">
                <span>{{ form.create_time }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="修改人：">
                <span>{{ form.modify_user_name }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改时间：">
                <span>{{ form.modify_time }}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- <div class="tableContent"></div> -->
    <div class="el-dialog__footer" style="padding:20px 0 0 0;">
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>

  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Object
    }
  },
  data() {
    return {}
  },
  created() {
    this.form = { ...this.data }
    if (this.form.shipping_method === "T") {
      this.form.shipping_method = "铁运";
    } else if (this.form.shipping_method === "Q") {
      this.form.shipping_method = "汽运";
    }
  },
  methods: {
    cancel() {
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
