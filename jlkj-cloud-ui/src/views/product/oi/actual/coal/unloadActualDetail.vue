<!--
  配煤实绩的详情
-->
<template>
  <div class="avue-crud">
    <div>
      <div class="title">
        <label>基本信息</label>
      </div>
      <div class="content">
        <el-row :gutter="10">
          <!--          <el-col :span="12"><label style="width: 100px">编号：</label><div style="white-space: nowrap">{{detailData.id}}</div></el-col>-->
          <el-col :span="24"><label style="width: 100px">检斤计划编号：</label>
            <div>{{ detailData.planId }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12"><label style="width: 100px">检斤计划日期：</label>
            <div>{{ detailData.planDate }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">供货单位名称：</label>
            <div>{{ detailData.supplierNumber }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12"><label style="width: 100px">运输方式：</label>
            <div>{{ detailData.shippingMethod }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">车号：</label>
            <div>{{ detailData.vehicleNumber }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12"><label style="width: 100px">储位编号：</label>
            <div>{{ detailData.storageSpacesId }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">物料名称：</label>
            <div>{{ detailData.materialName }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12"><label style="width: 100px">物料代码：</label>
            <div>{{ detailData.materialCode }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">物料重量(t)：</label>
            <div>{{ (detailData.materialWeight / 1000).toFixed(3) }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12"><label style="width: 100px">班次：</label>
            <div>{{ detailData.shiftName }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">班别：</label>
            <div>{{ detailData.className }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <!-- <el-col :span="12"><label style="width: 100px">创建人：</label>
            <div>{{detailData.createUserName}}</div>
          </el-col> -->
          <el-col :span="12"><label style="width: 100px">卸车时间：</label>
            <div>{{ detailData.unloadingTime }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">报文同步时间：</label>
            <div>{{ detailData.createTime }}</div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="12"><label style="width: 100px">修改人：</label>
            <div>{{ detailData.modifyUserName }}</div>
          </el-col>
          <el-col :span="12"><label style="width: 100px">修改时间：</label>
            <div>{{ detailData.modifyTime }}</div>
          </el-col>
        </el-row>

      </div>
    </div>
    <div class="el-dialog__footer" style="padding:10px 0 25px 0;">
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>

  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "coalBlendingActualDetail",
  props: ['dataEdit'],
  data() {
    return {
      detailData: {
        id: "",
        planId: "",
        planDate: "",
        supplierNumber: "",
        shippingMethod: "",
        vehicleNumber: "",
        storageSpacesId: "",
        materialName: "",
        materialCode: "",
        materialWeight: "",
        shiftName: "",
        className: "",
        createUserName: "",
        createTime: "",
        modifyUserName: "",
        modifyTime: "",
        unloadingTime: "",
      }
    }
  },
  mounted() {
    this.detailData.id = this.dataEdit.id;
    this.detailData.planId = this.dataEdit.plan_id;
    this.detailData.planDate = this.dataEdit.plan_date === null ? "" : moment(this.dataEdit.plan_date).format("YYYY-MM-DD HH:mm:ss");
    this.detailData.supplierNumber = this.dataEdit.supplier_name;
    if (this.dataEdit.shipping_method === "T") {
      this.detailData.shippingMethod = "铁运";
    } else if (this.dataEdit.shipping_method === "Q") {
      this.detailData.shippingMethod = "汽运";
    } else {
      this.detailData.shippingMethod = this.dataEdit.shipping_method;
    }
    this.detailData.vehicleNumber = this.dataEdit.vehicle_number;
    this.detailData.storageSpacesId = this.dataEdit.storage_maintain_space_name;
    this.detailData.materialName = this.dataEdit.material_name;
    this.detailData.materialCode = this.dataEdit.material_code;
    this.detailData.materialWeight = this.dataEdit.material_weight;
    switch (this.dataEdit.shift_name) {
      case "01":
        this.detailData.shiftName = "常白班";
        break
      case "2201":
        this.detailData.shiftName = "白班";
        break
      case "2202":
        this.detailData.shiftName = "夜班";
        break
      case "00":
        this.detailData.shiftName = "休息";
        break
      default:
        this.detailData.shiftName = this.dataEdit.shift_name;
        break
    }
    this.detailData.className = this.dataEdit.class_name;
    this.detailData.eateUserName = this.dataEdit.create_user_name;
    this.detailData.createTime = this.dataEdit.create_time === null ? "" : this.dataEdit.create_time;
    this.detailData.modifyUserName = this.dataEdit.modify_user_name;
    this.detailData.modifyTime = this.dataEdit.modify_time === null ? "" : this.dataEdit.modify_time;
    this.detailData.unloadingTime = this.dataEdit.unloading_time === null ? "" : this.dataEdit.unloading_time;
  },
  methods: {
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
.title {
  border-left: 10px solid #409eff;
  margin: 20px 0;
}

.title label {
  margin-left: 15px;
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  font-weight: 700;
  text-decoration: none;
}

.content {
  padding-left: 25px;
  line-height: 35px;
  font-size: 14px;
}

.content label {
  float: left;
  text-align: right;
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  font-weight: 700;
  font-style: normal;
  color: rgb(153, 153, 153);
  line-height: 34px;
}

.content .el-col div {
  margin-left: 105px;
  font-family: 微软雅黑;
  font-style: normal;
  text-align: left;
  font-size: 15px;
}

.content .el-col {
  /*margin: 10px 0;*/
}

.tableContent {
  border: 1px solid #ebeef5;
  border-bottom: unset;
}
</style>
