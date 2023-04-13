<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="150px">
        <el-row :gutter="50">
          <el-col :span="12">
            <!-- <el-form-item prop="config_coke_plan_number"
              label="配煤计划编号">
  <el-input disabled
            v-model="form.config_coke_plan_number">
  </el-input>
</el-form-item> -->
            <el-form-item prop="config_coke_plan_id"
                          label="配煤计划编号">
              <el-select style="width:100%;"
                         :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="form.config_coke_plan_id"
                         clearable
                         filterable
                         placeholder="选择配煤计划编号"
                         @change="planNumberChange">
                <el-option v-for="item in planNumberOptions"
                           @click.native="changeItem(item)"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>

          </el-col>
          <el-col :span="12">
            <el-form-item prop="coke_warehouse_number"
                          label="配煤仓号">
              <el-select style="width:100%;"
                         :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="form.coke_warehouse_number"
                         clearable
                         filterable
                         placeholder="选择配煤仓号"
                         @change="bunkerNumberChange">
                <el-option v-for="item in bunkerNumberOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="coal_level_materials_name"
                          label="焦炭等级名称">
              <el-input disabled
                        v-model="form.coal_level_materials_name">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="coke_materials_name"
                          label="煤的料号名称">
              <el-input disabled
                        v-model="form.coke_materials_name">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="coke_materials_small_name"
                          label="小煤种名称">
              <el-input disabled
                        v-model="form.coke_materials_small_name">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="start_time"
                          label="上煤开始时间">
              <el-date-picker type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择时间"
                              v-model="form.start_time"
                              @change="changeStartDate"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="end_time"
                          label="上煤结束时间">
              <el-date-picker type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择时间"
                              v-model="form.end_time"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="coke_weight"
                          label="上煤重量">
              <el-input v-model="form.coke_weight"
                        @input="changeInput('coke_weight')"
                        placeholder="输入内容">
                <i slot="suffix">kg</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="coke_material_level_befor"
                          label="上煤前料位">
              <el-input v-model="form.coke_material_level_befor"
                        @input="changeInput('coke_material_level_befor')"
                        placeholder="输入内容">
                <i slot="suffix">m</i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="coke_material_level_after"
                          label="上煤后料位">
              <el-input v-model="form.coke_material_level_after"
                        @input="changeInput('coke_material_level_after')"
                        placeholder="输入内容">
                <i slot="suffix">m</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;margin-bottom: 25px;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定
      </el-button>
    </div>
  </div>
</template>

<script>
import {
  getPlanConfigCoke,
  getProductionPlanConfigCokeDetailInfoByWarehouseNumber,
  insertProductionConveyingCoalRecord,
} from '@/api/production/oi/upCoalRecord'
import { getHumanresourceSchedule } from '@/api/sys/index'

export default {
  props: {
    type: {
      type: String,
    },
    data: {
      type: Object,
    },
  },
  data() {
    let validateStartTime = (rule, value, callback) => {
      if (value) {
        // if (this.form.end_time) {
        //   let times = this.compareDate(value, this.form.end_time);
        //   if (times > 0) {
        //     callback();
        //   } else if (times === 0) {
        //     callback(new Error("上煤开始时间和上煤结束时间不能相同"));
        //   } else {
        //     callback(new Error("上煤开始时间不能在上煤结束时间之后，请重新选择"));
        //   }
        // }
        if (
          this.userInfo.isTeam &&
          this.userInfo.team.due_attendance_time_work
        ) {
          let classTime = this.compareDate(
            this.userInfo.team.due_attendance_time_work,
            value
          )
          // console.log(classTime, "classTime")
          if (classTime < 0) {
            callback(
              new Error(
                `选择上煤开始时间不能小于${this.userInfo.userName}的上班时间，请重新选择`
              )
            )
          } else {
            // callback();
            if (this.form.end_time) {
              let times = this.compareDate(
                value,
                this.form.end_time
              )
              if (times > 0) {
                callback()
              } else if (times === 0) {
                callback(
                  new Error(
                    '上煤开始时间和上煤结束时间不能相同'
                  )
                )
              } else {
                callback(
                  new Error(
                    '上煤开始时间不能在上煤结束时间之后，请重新选择'
                  )
                )
              }
            }
          }
        } else {
          callback(
            new Error(
              `${this.userInfo.userName}没有排班日期或者休息中`
            )
          )
        }
      } else {
        callback(new Error('上煤开始时间不能为空'))
      }
    }
    var validateEndTime = (rule, value, callback) => {
      if (value) {
        // if (this.form.start_time) {
        //   let times = this.compareDate(this.form.start_time, value);
        //   if (times > 0) {
        //     callback();
        //   } else if (times === 0) {
        //     callback(new Error("上煤开始时间和上煤结束时间不能相同"));
        //   } else {
        //     callback(new Error("上煤结束时间不能在上煤开始时间之前，请重新选择"));
        //   }
        // }
        if (
          this.userInfo.isTeam &&
          this.userInfo.team.due_attendance_time_offduty
        ) {
          let classTime = this.compareDate(
            this.userInfo.team.due_attendance_time_offduty,
            value
          )
          // console.log(classTime, "classTime")
          if (classTime > 0) {
            callback(
              new Error(
                `选择上煤结束时间不能大于${this.userInfo.userName}的下班时间，请重新选择`
              )
            )
          } else {
            // callback();
            if (this.form.start_time) {
              let times = this.compareDate(
                this.form.start_time,
                value
              )
              if (times > 0) {
                callback()
              } else if (times === 0) {
                callback(
                  new Error(
                    '上煤开始时间和上煤结束时间不能相同'
                  )
                )
              } else {
                callback(
                  new Error(
                    '上煤结束时间不能在上煤开始时间之前，请重新选择'
                  )
                )
              }
            }
          }
        } else {
          callback(
            new Error(
              `${this.userInfo.userName}没有排班日期或者休息中`
            )
          )
        }
      } else {
        callback(new Error('上煤结束时间不能为空'))
      }
    }
    var cokeMaterialLevelBefor = (rule, value, callback) => {
      if (value) {
        if (this.form.coke_material_level_after) {
          let sum = value - this.form.coke_material_level_after
          if (sum > 0) {
            callback(new Error('上煤前料位不能大于上煤后料位'))
          } else if (sum == 0) {
            callback(new Error('上煤前料位不能等于上煤后料位'))
          } else {
            callback()
          }
        }
      } else {
        callback(new Error('上煤前料位不能为空'))
      }
    }
    var cokeMaterialLevelAfter = (rule, value, callback) => {
      if (value) {
        if (this.form.coke_material_level_befor) {
          let sum = value - this.form.coke_material_level_befor
          if (sum < 0) {
            callback(new Error('上煤后料位不能小于上煤前料位'))
          } else if (sum == 0) {
            callback(new Error('上煤后料位不能等于上煤前料位'))
          } else {
            callback()
          }
        }
      } else {
        callback(new Error('上煤后料位不能为空'))
      }
    }
    return {
      form: {
        config_coke_plan_number: null,
        coal_level_materials_name: null,
        coke_materials_name: null,
        coke_warehouse_number: null,
        coke_materials_small_name: null,
      },
      // 表单校验
      rules: {
        config_coke_plan_id: [
          {
            required: true,
            message: '配煤计划编号不能为空',
            trigger: 'change',
          },
        ],
        coal_level_materials_name: [
          {
            required: true,
            message: '焦炭等级名称不能为空',
            trigger: 'blur',
          },
        ],
        coke_materials_name: [
          {
            required: true,
            message: '煤的料号名称不能为空',
            trigger: 'blur',
          },
        ],
        coke_materials_small_name: [
          {
            required: true,
            message: '小煤种名称不能为空',
            trigger: 'blur',
          },
        ],
        start_time: [
          {
            required: true,
            validator: validateStartTime,
            trigger: ['change', 'blur'],
          },
        ],
        end_time: [
          {
            required: true,
            validator: validateEndTime,
            trigger: ['change', 'blur'],
          },
        ],
        coke_warehouse_number: [
          {
            required: true,
            message: '配煤仓号不能为空',
            trigger: 'change',
          },
        ],
        coke_weight: [
          {
            required: true,
            message: '上煤重量不能为空',
            trigger: 'blur',
          },
        ],
        coke_material_level_befor: [
          {
            required: true,
            validator: cokeMaterialLevelBefor,
            trigger: 'blur',
          },
        ],
        coke_material_level_after: [
          {
            required: true,
            validator: cokeMaterialLevelAfter,
            trigger: 'blur',
          },
        ],
      },
      planNumberOptions: [],
      bunkerNumberOptions: [
        // { id: 1, name: '1号' },
        // { id: 2, name: '2号' },
        // { id: 3, name: '3号' },
        // { id: 4, name: '4号' },
        // { id: 5, name: '5号' },
        // { id: 6, name: '6号' },
        // { id: 7, name: '7号' },
        // { id: 8, name: '8号' },
        // { id: 9, name: '9号' },
        // { id: 10, name: '10号' },
      ],
      submitLoading: false,
      teamData: null,
    }
  },
  created() {
    // console.log(this.userInfo)
    // console.log(this.userInfo.team.due_attendance_time_work)
    // console.log(this.userInfo.team.due_attendance_time_offduty)
    this.getCoalBlendingList()
  },
  computed: {
  },
  methods: {
    //获取配煤计划下拉
    getCoalBlendingList() {
      getPlanConfigCoke().then((res) => {
        // console.log(res.data, "获取配煤计划下拉")
        if (res.data.code == 0) {
          let options = []
          res.data.data.forEach((item) => {
            let state =
              item.plan_state == 1
                ? '未确认'
                : item.plan_state == 2
                ? '已确认'
                : item.plan_state == 3
                  ? '执行中'
                  : '已完成'
            let i = {
              id: item.id,
              name: `${item.plan_number} (${state} ) `,
              list: item.detail_list,
            }
            options.push(i)
          })
          this.planNumberOptions = options
        }
      })
    },
    //改变配煤计划
    planNumberChange(val) {
      // console.log(val)
      this.form.coke_warehouse_number = ''
      this.form.coal_level_materials_name = ''
      this.form.coke_materials_name = ''
      this.form.coke_materials_small_name = ''
    },
    //获取下拉选中的内容
    changeItem(item) {
      // console.log(item, 'item')
      this.form.config_coke_plan_number = item.plan_number
      let options = []
      item.list.forEach((i) => {
        let e = {
          id: i.warehouse_number,
          name: i.warehouse_number + '号',
        }
        options.push(e)
      })
      this.bunkerNumberOptions = options
    },
    //配煤仓号改变
    bunkerNumberChange(val) {
      if (val) {
        this.getCoalPlanInformation(val)
      }
    },
    //获取配合配煤计划信息
    getCoalPlanInformation(Number) {
      const params = {
        warehouse_number: Number,
        plan_id: this.form.config_coke_plan_id,
      }
      getProductionPlanConfigCokeDetailInfoByWarehouseNumber(params).then(
        (res) => {
          // console.log(res, '获取配合配煤计划信息')
          let data = res.data.data
          if (data) {
            // this.form.config_coke_plan_id = data.plan_id
            // this.form.config_coke_plan_number = data.plan_number
            this.form.coal_level_materials_code = data.material_code
            this.form.coal_level_materials_name = data.material_name
            this.form.coke_materials_code = data.coal_type_code
            this.form.coke_materials_name = data.coal_type_name
            this.form.coke_materials_small_code =
              data.materials_small_code
            this.form.coke_materials_small_name =
              data.materials_small_name
          } else {
            // this.form.config_coke_plan_id = ''
            // this.form.config_coke_plan_number = ''
            this.form.coal_level_materials_code = ''
            this.form.coal_level_materials_name = ''
            this.form.coke_materials_code = ''
            this.form.coke_materials_name = ''
            this.form.coke_materials_small_code = ''
            this.form.coke_materials_small_name = ''
          }
        }
      )
    },
    //开始时间
    changeStartDate(val) {
      // console.log(val, 'val')
      getHumanresourceSchedule({ startTime: val }).then((res) => {
        // console.log(res, '班组信息')
        if (res.data.code == 0) {
          this.teamData = res.data.data[0]
        }
        // console.log(this.teamData, ' this.teamData ')
      })
    },
    // 比较两个日期
    compareDate(start, end) {
      return new Date(end).getTime() - new Date(start).getTime()
    },
    changeInput(name) {
      // if (name == 'coke_weight') {
      //   //限制正整数
      //   this.form[name] = this.form[name].replace(/[^\d]/g, "")
      // } else {
      //   //限制只有两位小数的正整数
      //   this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
      // }
      //限制正整数
      this.form[name] = this.form[name].replace(/[^\d]/g, '')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type === 'add') {
            // if (this.userInfo.isTeam) {
            //     this.form.due_attendance_time_work =
            //         this.userInfo.team.due_attendance_time_work
            //     this.form.due_attendance_time_offduty =
            //         this.userInfo.team.due_attendance_time_offduty
            //     this.form.department_id =
            //         this.userInfo.alternateField14
            //     this.form.create_user_id = this.$store.getters.userInfo.userId
            //     this.form.create_user_name = this.userInfo.userName
            //     this.form.shift_name = this.userInfo.team.shift
            //     this.form.class_name = this.userInfo.team.class_type
            //     this.submitLoading = true
            //     insertProductionConveyingCoalRecord(this.form).then(
            //         (res) => {
            //             if (res.data.code === '0') {
            //                 this.$message({
            //                     type: 'success',
            //                     message: res.data.msg,
            //                 })
            //                 this.$emit('submitSave', res.data.msg)
            //             }
            //             this.submitLoading = false
            //         },
            //         (error) => {
            //             this.submitLoading = false
            //             window.console.log(error)
            //         }
            //     )
            // } else {
            //     this.$message.error('当前用户非当班用户！')
            // }

            if (this.teamData.start_time) {
              this.form.due_attendance_time_work =
                this.teamData.start_time
              this.form.due_attendance_time_offduty =
                this.teamData.end_time
              this.form.shift_name = this.teamData.shift_no
              this.form.class_name = this.teamData.class_name
              this.form.department_id = this.$store.getters.userInfo.dept.deptId
              this.form.create_user_id = this.$store.getters.userInfo.userId
              this.form.create_user_name = this.$store.state.user.userInfo.nickName
              this.submitLoading = true
              insertProductionConveyingCoalRecord(this.form).then(
                (res) => {
                  if (res.data.code === '0') {
                    this.$message({
                      type: 'success',
                      message: res.data.msg,
                    })
                    this.$emit('submitSave', res.data.msg)
                  }
                  this.submitLoading = false
                },
                (error) => {
                  this.submitLoading = false
                  window.console.log(error)
                }
              )
            } else {
              this.$message.error('未获取到排班信息！')
            }
          }
        }
      })
    },
    cancel() {
      this.$emit('close')
    },
  },
}
</script>

<style>
</style>
