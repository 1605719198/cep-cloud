<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" label-position="left" label-width="90px">
      <div class="title"><label>煤场配置</label></div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="煤场长度">
            <el-input v-model="form.silo_length" disabled>
              <template slot="append">米</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="预设储位数">
            <el-input v-model="form.count" disabled/>
          </el-form-item>
        </el-col>
      </el-row>
      <div class="title"><label>煤区配置</label></div>
      <template v-for="(item,index) in list" v-if="isSiloArea">
        <el-row style="line-height: 46px" :key="index">
          <el-col :span="2" style="padding-top: 5px">
            <el-color-picker v-model="item.color_value"/>
          </el-col>
          <el-col :span="4">{{item.storage_spaces_name}}</el-col>
          <el-col :span="15">
            <el-col :span="18" style="padding: 5px 15px 0 0">
              <el-slider :key="item.componentKey"
                         v-model="item.location"
                         :max="form.silo_length"
                         show-stops
                         range
                         @change="changeSlider(index)"/>
            </el-col>
            <el-col :span="6">
              <el-input-number v-model="item.location[0]"
                               controls-position="right"
                               :precision="0"
                               :min="0"
                               :max="form.silo_length"
                               style="width: 90px"
                               @change="changeSlider(index)"/>
              <el-input-number v-model="item.location[1]"
                               controls-position="right"
                               :precision="0"
                               :min="0"
                               :max="form.silo_length"
                               style="width: 90px"
                               @change="changeSlider(index)"/>
            </el-col>
          </el-col>
          <!--          <el-col :span="5">-->
          <!--            <el-col :span="4"><label>煤种</label></el-col>-->
          <!--            <el-col :span="20"-->
          <!--                    style="padding-right: 5px">-->
          <!--              <el-select v-model="item.materials_id"-->
          <!--                         placeholder="选择煤种"-->
          <!--                         :popper-append-to-body="false"-->
          <!--                         class="regionSelect">-->
          <!--                <el-option v-for="item in item.options"-->
          <!--                           :key="item.value"-->
          <!--                           :label="item.label"-->
          <!--                           :value="item.value" />-->
          <!--              </el-select>-->
          <!--            </el-col>-->
          <!--          </el-col>-->
          <el-col :span="3"
                  v-if="item.id">
            <el-button v-hasPermi="['getPageMaterialsSiloAreaHistory']" type="primary" size="medium"
                       @click="historyList(item.storage_spaces_id)">历史
            </el-button>
            <el-button v-hasPermi="['deleteMaterialsSiloArea']" type="danger" size="medium"
                       @click="handleDelete(item,index)">删除
            </el-button>
          </el-col>
        </el-row>
      </template>
    </el-form>
    <div class="el-dialog__footer">
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button v-hasPermi="['saveMaterialsSiloArea']"
                   type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
      </span>
    </div>
    <div v-if="historyOpen">
      <el-dialog class="customDialogStyle"
                 title="历史记录"
                 :visible.sync="historyOpen"
                 width="600px"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <el-form>
          <el-table size="small" :data="history_list" stripe>
            <el-table-column prop="materials_name" label="煤种"/>
            <el-table-column prop="start_time" label="开始时间"/>
            <el-table-column prop="end_time" label="结束时间"/>
          </el-table>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {listMaterialsBoxM} from "@/api/material/mr/parameter/materialCode"
  import {
    listMaterialsSiloArea,
    getPageMaterialsSiloAreaHistory,
    saveMaterialsSiloArea,
    listStorageSpacesBySiloId,
    deleteMaterialsSiloArea,
  } from "@/api/material/mr/region";

  export default {
    name: "regionConfigure",
    props: ['siloId', 'siloLength', 'coalSort'],
    data() {
      return {
        isSiloArea: true,
        form: {
          silo_id: '',
          storage_spaces_id: '',
          silo_length: 200,
          count: 0,
        },
        options: [], materials: {},
        list: [],
        historyOpen: false, history_list: [],
        submitLoading: false,
      }
    },
    computed: {

    },
    created() {
      let that = this;
      this.form.silo_id = this.siloId;
      this.form.silo_length = this.siloLength;
      listMaterialsBoxM().then(res => {
        let materials = {};
        let options = [];
        res.data.forEach(item => {
          let i = {category_id: item.category_id, value: item.id, label: item.materials_name};
          materials[item.id] = item.materials_name;
          options.push(i);
        });
        this.materials = materials;
        this.options = options;


        listMaterialsSiloArea({silo_id: that.form.silo_id, show_zero: 1, coal_sort: that.coalSort}).then(res => {
          let data = res.data;
          let list = [];
          data.forEach(function (value, index) {
            let option = options.filter(item => {
              return item.category_id === value.material_category_id
            });
            list.push({
              id: value.id,
              componentKey: index * 10000,
              color_value: value.color_value,
              storage_spaces_id: value.storage_spaces_id,
              storage_spaces_name: value.storage_spaces_name,
              location: [value.start_location || 0, value.end_location || 0],
              start_location: value.start_location || 0,
              end_location: value.end_location || 0,
              materials_id: value.materials_id,
              options: option,
            });
          });
          listStorageSpacesBySiloId({silo_id: that.form.silo_id, coal_sort: that.coalSort}).then(res2 => {
            let data = res.data;
            res2.data.forEach(function (value, index) {
              if (!data.some(function (x) {
                return x.storage_spaces_id === value.id;
              })) {
                let option = options.filter(item => {
                  return item.category_id === value.material_category_id
                });
                list.push({
                  id: '',
                  componentKey: index * 10000,
                  color_value: '',
                  storage_spaces_id: value.id,
                  storage_spaces_name: value.storage_spaces_name,
                  location: [that.form.silo_length, that.form.silo_length],
                  start_location: that.form.silo_length,
                  end_location: that.form.silo_length,
                  materials_id: value.material_category_id,
                  options: option,
                })
              }
            });
            this.list = [...list];
            this.form.count = this.list.length;
          }, error => {
            window.console.log(error);
          });
        }, error => {
          window.console.log(error);
        });
      });
    },
    mounted() {
    },
    methods: {
      onLoad() {

      },
      changeSlider(index) {
        this.list[index].componentKey += 1;
        if (this.list[index].location[0] < this.list[index].location[1]) {
          this.list[index].start_location = this.list[index].location[0];
          this.list[index].end_location = this.list[index].location[1];
        } else {
          this.list[index].start_location = this.list[index].location[1];
          this.list[index].end_location = this.list[index].location[0];
        }
      },
      handleChange() {
        let valid = true;
        let list = [];
        let that = this;

        for (let i = 0; i < this.list.length; i++) {
          let value = this.list[i];
          if (value.color_value === '') {
            this.$message({type: 'error', message: '请选择颜色'});
            valid = false;
            break;
          }
          // if (value.start_location === value.end_location) {
          //   this.$message({type: 'error', message: '储位长度不能为0'});
          //   valid = false;
          //   break;
          // }
          for (let x = 0; x < this.list.length; x++) {
            let v = this.list[x];
            if (x !== i) {
              if (value.start_location > v.start_location && value.start_location < v.end_location) {
                this.$message({type: 'error', message: '储位区间不能重合'});
                valid = false;
                break;
              }
              if (value.end_location > v.start_location && value.start_location < v.end_location) {
                this.$message({type: 'error', message: '储位区间不能重合'});
                valid = false;
                break;
              }
            }
          }
          if (!valid) {
            break;
          }
          if (value.materials_id === '') {
            this.$message({type: 'error', message: '请选择煤种'});
            valid = false;
            break;
          }

          list.push({
            storage_spaces_id: value.storage_spaces_id,
            materials_id: value.materials_id,
            materials_name: that.materials[value.materials_id],
            start_location: value.start_location,
            end_location: value.end_location,
            color_value: value.color_value,
          })
        }
        if (valid) {
          this.submitLoading = true;
          saveMaterialsSiloArea({
            silo_id: this.form.silo_id,
            create_user_id: this.$store.getters.userInfo.userId,
            create_user_name: this.$store.getters.userInfo.nickName,
            storage_spaces: list
          }).then(res => {
            if (res.code == 200) {
              this.$message({
                type: "success", message: "操作成功！", duration: 1000,
                onClose: () => {
                  this.$emit('submitSave', res.msg)
                }
              });
            }
            this.submitLoading = false;
          }, error => {
            this.submitLoading = false;
            window.console.log(error);
          });
        }
      },
      cancel() {
        this.$emit('close')
      },
      historyList(id) {
        this.historyOpen = true;
        getPageMaterialsSiloAreaHistory({
          silo_id: this.form.silo_id,
          storage_spaces_id: id,
          current: 1,
          size: 99999,
        }).then(res => {
          this.history_list = res.data.records;
        }, error => {
          window.console.log(error);
        });
      },
      // 删除
      handleDelete(row, index) {
        let that = this;
        this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          deleteMaterialsSiloArea({
            id: row.id,
            delete_user_id: this.$store.getters.userInfo.userId,
            delete_user_name: this.$store.getters.userInfo.nickName,
          }).then(res => {
            if (res.code == 200) {
              this.$message({
                type: "success", message: "操作成功！", duration: 1000,
                onClose: () => {
                  this.isSiloArea = false;
                  that.list.splice(index, 1);
                  // that.list[index] ={
                  //   id: '',
                  //   componentKey: index * 10000,
                  //   color_value: '',
                  //   storage_spaces_id: row.id,
                  //   storage_spaces_name: row.storage_spaces_name,
                  //   location: [that.form.silo_length, that.form.silo_length],
                  //   start_location: that.form.silo_length,
                  //   end_location: that.form.silo_length,
                  //   materials_id: '',
                  //   options: row.option,
                  // };
                  this.isSiloArea = true;
                }
              });
            }
          }, error => {
            window.console.log(error);
          });
        }).catch(() => {
          this.$message({type: 'info', message: '已取消删除'});
        });
      },
    },
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

  .el-slider__stop {
    background-color: #e4e7ed !important;
  }
</style>
<style>
  .region .el-slider__stop {
    background-color: #e4e7ed !important;
  }

  .regionSelect .el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }
</style>
