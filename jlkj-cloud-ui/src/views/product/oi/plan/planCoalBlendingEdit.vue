<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="left" label-width="80px">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="计划编号" prop="plan_number">
            <el-input v-model="form.plan_number" disabled/>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="开始时间" prop="plan_start_time">
            <el-date-picker v-model="form.plan_start_time" type="datetime" placeholder="选择开始时间"
                            format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"/>
          </el-form-item>
        </el-col>
<!--        <el-col :span="5">-->
<!--          <el-form-item label="储煤塔号" prop="tower_number">-->
<!--            <el-select v-model="form.tower_number" placeholder="储煤塔号">-->
<!--              <el-option v-for="i in 4" :key="i" :label="i" :value="i" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :span="8">
          <el-form-item label="焦炭等级" prop="material_id">
            <el-select v-model="form.material_id" placeholder="选择焦炭等级">
              <el-option v-for="item in selectCokeType" :key="item.id" :label="item.materials_name" :value="item.id"
                         @click.native="changeCokeType(item)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <!--        <el-col :span="8">-->
        <!--          <el-form-item label="结束时间" prop="plan_end_time">-->
        <!--            <el-date-picker v-model="form.plan_end_time" type="datetime" placeholder="选择结束时间"/>-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->
      </el-row>
      <el-table v-loading="loading" :data="form.tableData" stripe border style="width: 100%">
        <el-table-column prop="warehouse_number" label="配煤仓号">
          <template slot-scope="scope">
            <template v-if="editIndex === scope.$index">
              <el-form-item label="" :prop="'tableData.'+scope.$index+'.warehouse_number'"
                            :rules="rules.warehouse_number" label-width="0px" size="small">
                <el-select v-model="scope.row.warehouse_number" placeholder="选择配煤仓号">
                  <el-option v-for="item in selectWarehouse" :key="item.value" :label="item.label" :value="item.value"
                             v-if="editIndex>=0"/>
                </el-select>
              </el-form-item>
            </template>
            <template v-else>
              <div class="cell">{{ scope.row.warehouse_number }}</div>
            </template>
          </template>
        </el-table-column>
<!--        <el-table-column prop="coalTypName" label="煤种">-->
<!--          <template slot-scope="scope">-->
<!--            <template v-if="editIndex === scope.$index">-->
<!--              <el-form-item label="" :prop="'tableData.'+scope.$index+'.coal_type_id'" :rules="rules.coal_type_id"-->
<!--                            label-width="0px" size="small">-->
<!--                <el-select v-model="scope.row.coal_type_id" placeholder="选择煤种">-->
<!--                  <el-option v-for="item in selectCoalType" :key="item.id" :label="item.materials_name" :value="item.id"-->
<!--                             @click.native="changeCoalType(item)"/>-->
<!--                </el-select>-->
<!--              </el-form-item>-->
<!--            </template>-->
<!--            <template v-else>-->
<!--              <div class="cell">{{ scope.row.coal_type_name }}</div>-->
<!--            </template>-->
<!--          </template>-->
<!--        </el-table-column>-->

        <el-table-column prop="coal_type_name" label="煤种"/>
        <el-table-column prop="materials_small_name" label="小煤种">
          <template slot-scope="scope">
            <template v-if="editIndex === scope.$index">
              <el-form-item label="" :prop="'tableData.'+scope.$index+'.materials_small_code'" :rules="rules.materials_small_code"
                            label-width="0px" size="small">
                <el-select v-model="scope.row.materials_small_code" placeholder="选择小煤种">
                  <el-option v-for="item in selectSmallCoalType" :key="item.id" :label="item.materials_small_name" :value="item.materials_small_code"
                             @click.native="changeSmallCoalType(item)"/>
                </el-select>
              </el-form-item>
            </template>
            <template v-else>
              <div class="cell">{{ scope.row.materials_small_name }}</div>
            </template>
          </template>
        </el-table-column>

        <el-table-column prop="proportion" label="配置比例 %">
          <template slot-scope="scope">
            <template v-if="editIndex === scope.$index">
              <el-form-item label="" :prop="'tableData.'+scope.$index+'.proportion'" :rules="rules.proportion"
                            label-width="0px" size="small">
                <el-input v-model="scope.row.proportion"/>
              </el-form-item>
            </template>
            <template v-else>
              <div class="cell">{{ scope.row.proportion }} %</div>
            </template>
          </template>
        </el-table-column>
        <!--        <el-table-column prop="sort" label="排序">-->
        <!--          <template slot-scope="scope">-->
        <!--            <div class="cell">{{scope.$index+1}}</div>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <template v-if="editIndex === scope.$index">
              <el-button size="mini" plain icon="el-icon-circle-check" type="primary"
                         @click="handleSave(scope.$index, scope.row)"> 保存
              </el-button>
            </template>
            <template v-else>
              <el-button size="mini" plain icon="el-icon-edit" type="success"
                         @click="handleEdit(scope.$index, scope.row)"> 修改
              </el-button>
            </template>
            <el-button size="mini" plain icon="el-icon-delete" type="danger"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>

          </template>
        </el-table-column>
      </el-table>
      <div style="width: 100%;margin: 5px 0;color: #8c939d ">
        <div style=" height:44px;line-height: 44px;text-align: center;border: 2px dashed #E4E4E4">
          <el-link type="info" :underline="false" icon="el-icon-circle-plus-outline" @click="addTable">添加</el-link>
        </div>
      </div>
      <div class="el-dialog__footer">
        <span slot="footer" class="dialog-footer" style="margin-top:10px">
          <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </span>
      </div>
    </el-form>

  </div>
</template>
<script>
import {req} from "@/api/production/oi/common";
import {listMaterialsBoxJ, getMaterialsCodeSmallDic} from "@/api/material/mr/parameter/materialCode";

export default {
  name: "planCoalBlendingEdit",
  props: ['data'],
  data() {
    let positiveNumber = (rule, value, callback) => {
      if (Number(value) > 0) {
        callback()
      } else {
        callback('请输入大于0的数字')
      }
    };
    let proportionSum = (rule, value, callback) => {
      let sum = 0;
      let proportion = this.form.tableData.map((item) => {
        return Number(item.proportion);
      });
      sum = proportion.reduce((z, c) => {
        return z + c;
      });
      if (sum <= 100) {
        callback()
      } else {
        callback('配置比例总和不能超过100%')
      }
    };
    return {
      page: {size: 10000, current: 1, total: 1, order: "create_time", orderby: "asc",},
      query: {planId: ''},
      editIndex: -1,
      loading: false,
      selectWarehouse: [
        {value: '1', label: '1#配煤仓'},
        {value: '2', label: '2#配煤仓'},
        {value: '3', label: '3#配煤仓'},
        {value: '4', label: '4#配煤仓'},
        {value: '5', label: '5#配煤仓'},
        {value: '6', label: '6#配煤仓'},
        {value: '7', label: '7#配煤仓'},
        {value: '8', label: '8#配煤仓'},
        {value: '9', label: '9#配煤仓'},
        {value: '10', label: '10#配煤仓'},
      ],
      selectWarehouseList: [
        {value: '1', label: '1#配煤仓'},
        {value: '2', label: '2#配煤仓'},
        {value: '3', label: '3#配煤仓'},
        {value: '4', label: '4#配煤仓'},
        {value: '5', label: '5#配煤仓'},
        {value: '6', label: '6#配煤仓'},
        {value: '7', label: '7#配煤仓'},
        {value: '8', label: '8#配煤仓'},
        {value: '9', label: '9#配煤仓'},
        {value: '10', label: '10#配煤仓'},
      ],
      excludeWarehouse: [],
      selectCoalType: [],
      selectSmallCoalType: [],
      // selectCoalTypeByCategoryId: [],
      coalTypeCategoryId: {},
      selectCokeType: [],
      form: {plan_number: '', plan_start_time: '', plan_end_time: '', tower_number: '', material_id: '', tableData: [], tableDelData: []},
      rules: {
        plan_number: [{required: true, message: '请输入计划编号', trigger: 'blur'},],
        plan_start_time: [{required: true, message: '请输入开始时间', trigger: 'blur'},],
        plan_end_time: [{required: true, message: '请输入结束时间', trigger: 'blur'},],
        // tower_number: [{required: true, message: '请选择储煤塔号', trigger: 'blur'},],
        material_id: [{required: true, message: '请选择焦炭等级', trigger: 'blur'},],
        warehouse_number: [{required: true, message: '请选择配煤仓号', trigger: 'blur'},],
        // coal_type_id: [{required: true, message: '请选择煤种', trigger: 'blur'},],
        materials_small_code: [{required: true, message: '请选择小煤种', trigger: 'blur'},],
        proportion: [
          {required: true, message: '请输入配置比例', trigger: 'blur'},
          {type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value)},
          {validator: positiveNumber, trigger: 'blur'},
          {validator: proportionSum, trigger: 'blur'},
        ],
      },
      submitLoading: false,
    }
  },
  computed: {
  },
  created() {

    getMaterialsCodeSmallDic().then(res => {
      this.selectSmallCoalType = res.data;//表格数据
    }, error => {
      window.console.log(error);
    });

    listMaterialsBoxJ().then(res => {
      this.selectCokeType = res.filter(item => {
        return item.materials_code.substring(0,5) === '01501';
      });
    }, error => {
      window.console.log(error);
    });
  },
  mounted() {
    req('get', 'getProductionPlanConfigCokePlanNumber', {}).then(res => {
      if(res.code === 200){
        this.form.plan_number = res.data.plan_number;
      }
    });
    if (this.data.id) {
      this.form = {...this.data};
      this.query.planId = this.form.id;
    }
    this.form.tableData = [];
    this.form.tableDelData = [];
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad() {
      let that = this;
      if (this.query.planId !== '') {
        this.loading = true;//加载状态
        let data = {...this.page, ...this.query};
        req('get', 'listProductionPlanCfgCokeDitail', data).then(res => {
          this.loading = false;
          this.form.tableData = [];//表格数据
          res.data.records.forEach(item => {
            that.form.tableData.push({
              id: item.id,
              warehouse_number: item.warehouse_number,
              coal_type_id: item.coal_type_id,
              coal_type_name: item.coal_type_name,
              materials_small_code: item.materials_small_code,
              materials_small_name: item.materials_small_name,
              proportion: item.proportion,
              modify_user_id: that.$store.state.user.userInfo.userName,
              modify_user_name: that.$store.state.user.userInfo.nickName,
              delete_flag: 0,
            });
          });
          this.excludeWarehouse = this.form.tableData.map((item) => {
            return item.warehouse_number;
          });
          this.selectWarehouse = this.selectWarehouseList.filter(i => {
            return !that.excludeWarehouse.includes(Number(i.value));
          });

        }, error => {
          this.loading = false;
          window.console.log(error);
        });
      }
    },
    addTable() {
      if (this.editIndex === -1) {
        this.editIndex = this.form.tableData.length;
        this.form.tableData.push({
          id: '',
          warehouse_number: '',
          coal_type_id: '',
          coal_type_name: '',
          materials_small_code: '',
          materials_small_name: '',
          proportion: '',
          delete_flag: 0,
          create_user_id: this.$store.getters.userInfo.userId,
          create_user_name: this.$store.getters.userInfo.nickName,
        });
        // this.selectCoalTypeByCategoryId = [...this.selectCoalType];
      } else {
        this.$message({type: "error", message: "请先保存正在编辑的数据！",});
      }
    },
    handleEdit(index) {
      if (this.editIndex === -1) {
        // this.selectCoalTypeByCategoryId = [...this.selectCoalType];
        this.editIndex = index;
        // this.changeWarehouseNumber(this.form.tableData[this.editIndex].warehouse_number);
      } else {
        this.$message({type: "error", message: "请先保存正在编辑的数据！",});
      }
    },
    handleSave(index, row) {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.editIndex = -1;
        }
      });
      let that = this;
      this.excludeWarehouse.push(Number(row.warehouse_number));
      this.selectWarehouse = this.selectWarehouseList.filter(i => {
        return !that.excludeWarehouse.includes(Number(i.value));
      });
    },
    handleDelete(index, row) {
      if (this.editIndex === index) {
        delete this.form.tableData.splice(index, 1);
        this.editIndex = -1;
      } else if (this.editIndex === -1 || this.editIndex === index) {
        if (row.id !== '') {
          this.form.tableDelData.push({id: row.id, delete_flag: 1});
        }
        delete this.form.tableData.splice(index, 1);
      } else {
        this.$message({type: "error", message: "请先保存正在编辑的数据！",});
      }
      let that = this;
      this.excludeWarehouse = this.form.tableData.map((item) => {
        return item.warehouse_number;
      });
      this.selectWarehouse = this.selectWarehouseList.filter(i => {
        return !that.excludeWarehouse.includes(Number(i.value));
      });
      // console.log(this.selectWarehouse)
    },
    changeCoalType(item) {
      this.form.tableData[this.editIndex].coal_type_name = item.materials_name;
    },
    changeSmallCoalType(item) {
      this.form.tableData[this.editIndex].coal_type_id = item.materials_id;
      this.form.tableData[this.editIndex].coal_type_name = item.materials_name;
      this.form.tableData[this.editIndex].materials_small_code = item.materials_small_code;
      this.form.tableData[this.editIndex].materials_small_name = item.materials_small_name;
    },
    changeCokeType(item) {
      this.form.material_name = item.materials_name;
    },


    handleChange(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.query.planId === '') {
            req('post', 'addProductionPlanCfgCoke', {
              plan_number: this.form.plan_number,
              plan_start_time: this.form.plan_start_time,
              plan_end_time: this.form.plan_end_time,
              material_id: this.form.material_id,
              material_name: this.form.material_name,
              // tower_number: this.form.tower_number,
              create_user_id: this.$store.getters.userInfo.userId,
              create_user_name: this.$store.getters.userInfo.nickName,
              detail_list: this.form.tableData,
            }).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success", message: "操作成功！", duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
              }
            }, error => {
              window.console.log(error);
            });
          } else {
            req('post', 'editProductionPlanCfgCoke', {
              id: this.query.planId,
              plan_number: this.form.plan_number,
              plan_start_time: this.form.plan_start_time,
              plan_end_time: this.form.plan_end_time,
              material_id: this.form.material_id,
              material_name: this.form.material_name,
              // tower_number: this.form.tower_number,
              modify_user_id: this.$store.getters.userInfo.userId,
              modify_user_name: this.$store.getters.userInfo.nickName,
              detail_list: [...this.form.tableData, ...this.form.tableDelData],
            }).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success", message: "操作成功！", duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
              }
            }, error => {
              window.console.log(error);
            });
          }
        } else {
          return false;
        }
      });
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white
}

.el-dialog__footer {
  padding: 0;
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
