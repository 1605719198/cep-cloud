<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :disabled="isDisabled"
               :model="form"
               :rules="rules"
               label-width="100px">
        <el-row :gutter="50">

          <el-col :span="8">
            <el-form-item prop="coal_tower_number"
                          label="储煤塔号">
              <el-input disabled
                        v-model="form.coal_tower_number"
                        placeholder="储煤塔号">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="start_time"
                          label="配煤开始">
              <el-date-picker type="datetime"
                              format="yyyy-MM-dd HH:mm"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择开始时间"
                              v-model="form.start_time"
                              @change="changeStartDate"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="end_time"
                          label="配煤结束">
              <el-date-picker type="datetime"
                              format="yyyy-MM-dd HH:mm"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择结束时间"
                              v-model="form.end_time"
                              @change="changeEndDate"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="50"
                v-for="(item, index) in form.configList"
                :key="index">
          <el-col :span="3"
                  style="margin-top:12px;">{{item.bunker_number}}#配煤仓</el-col>
          <el-col :span="7">
            <el-form-item prop="material_name"
                          label="物料名称">
              <el-input disabled
                        v-model="item.material_name"
                        placeholder="物料名称">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item prop="material_small_name"
                          label="小煤种名称">
              <el-input disabled
                        v-model="item.material_small_name"
                        placeholder="小煤种名称">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item :prop="'configList.'+index+'.material_weight'"
                          :rules="rules.material_weight"
                          label="配煤重量">
              <el-input v-model="item.material_weight"
                        :disabled="item.material_name == '未配煤'"
                        maxlength="10"
                        @input="changeInputInt(index,'material_weight')"
                        placeholder="输入内容">
                <i slot="suffix">kg</i>
              </el-input>
            </el-form-item>
          </el-col>
          <!--          <el-col :span="7">-->
          <!--            <el-form-item :prop="'configList.'+index+'.after_material_level'"-->
          <!--                          :rules="rules.after_material_level"-->
          <!--                          label="配煤后料位">-->
          <!--              <el-input v-model="item.after_material_level"-->
          <!--                        :disabled="item.material_name == '未配煤'"-->
          <!--                        @input="changeInput(index,'after_material_level')"-->
          <!--                        placeholder="输入内容">-->
          <!--                <i slot="suffix">m</i>-->
          <!--              </el-input>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;margin-bottom: 25px;">
      <el-button @click="cancel">取消</el-button>
      <el-button v-if="!isDisabled"
                 :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import {
  getLastConfigPlan,
  addManualConfigCoal,
  getBlendWareDetailList,
} from '@/api/production/oi/handCoalBlending'
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
  //接受爷爷传过来的
  inject: ['grandpaOnLoad'],
  data() {
    let validateStartTime = (rule, value, callback) => {
      if (value) {
        if (this.form.end_time) {
          let times = this.compareDate(value, this.form.end_time)
          if (times > 0) {
            callback()
          } else if (times === 0) {
            callback(
              new Error('配煤开始时间和配煤结束时间不能相同')
            )
          } else {
            callback(
              new Error(
                '配煤开始时间不能在配煤结束时间之后，请重新选择'
              )
            )
          }
        }
      } else {
        callback(new Error('配煤开始时间不能为空'))
      }
    }
    var validateEndTime = (rule, value, callback) => {
      if (value) {
        if (this.form.start_time) {
          let times = this.compareDate(this.form.start_time, value)
          if (times > 0) {
            callback()
          } else if (times === 0) {
            callback(
              new Error('配煤开始时间和配煤结束时间不能相同')
            )
          } else {
            callback(
              new Error(
                '配煤结束时间不能在配煤开始时间之前，请重新选择'
              )
            )
          }
        }
      } else {
        callback(new Error('配煤结束时间不能为空'))
      }
    }

    return {
      form: {
        duration: null,
        configList: [
          {
            bunker_number: 1,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 2,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 3,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 4,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 5,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 6,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 7,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 8,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 9,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
          {
            bunker_number: 10,
            before_material_level: 0,
            after_material_level: 0,
            before_stock: 0,
            after_stock: 0,
            material_weight: 0,
            duration: 0,
            material_number: null,
            material_name: '未配煤',
            material_small_code: null,
            material_small_name: '未配煤',
          },
        ],
      },
      // 表单校验
      rules: {
        start_time: [
          {
            required: true,
            validator: validateStartTime,
            trigger: 'change',
          },
        ],
        end_time: [
          {
            required: true,
            validator: validateEndTime,
            trigger: 'change',
          },
        ],
        coal_tower_number: [
          {
            required: true,
            message: '储煤塔号不能为空',
            trigger: 'change',
          },
        ],
        material_weight: [
          {
            required: true,
            message: '配煤重量不能为空',
            trigger: 'blur',
          },
        ],
        after_material_level: [
          {
            required: true,
            message: '配煤后料位不能为空',
            trigger: 'blur',
          },
        ],
      },
      submitLoading: false,
      isDisabled: false,
      teamData: null,
    }
  },
  created() {
    this.form.coal_tower_number = this.data.coal_tower_number
    if (this.type == 'view') {
      this.isDisabled = true
      this.form.start_time = this.data.start_time
      this.form.end_time = this.data.end_time
      this.onLoad()
    }
    // if (this.type == 'edit') { }
  },
  computed: {
  },
  methods: {
    //详情
    onLoad() {
      getBlendWareDetailList({ tower_id: this.data.id }).then((res) => {
        // console.log(res.data.data, 'res')
        let data = res.data
        // 更新configList中的数据
        this.form.configList.forEach((item1, index) => {
          const arr = data.filter((item2) => {
            return item2.bunker_number == item1.bunker_number // 找到bunker_number一样的item
          })
          arr[0] && this.form.configList.splice(index, 1, arr[0]) // 找到一样的item后，进行替换
        })
      })
    },
    // 比较两个日期
    compareDate(start, end) {
      return new Date(end).getTime() - new Date(start).getTime()
    },
    //开始时间
    changeStartDate(val) {
      // console.log(val, 'val')
      getLastConfigPlan({
        start_time: val,
        tower_number: this.data.coal_tower_number,
      }).then((res) => {
        // console.log(res.data)
        let data = res.data
        if (res.code == 200) {
          this.form.plan_id = data.plan.id
          this.form.coal_level_materials_code = data.plan.materialCode
          this.form.coal_level_materials_name = data.plan.materialName
          this.form.coal_weight_befor = data.tower.coal_weight_after
          this.form.coal_material_level_befor =
            data.tower.coke_material_level_after
          for (var i = 0; i < this.form.configList.length; i++) {
            for (var j = 0; j < data.config.length; j++) {
              if (
                this.form.configList[i].bunker_number ==
                data.config[j].warehouse_number
              ) {
                this.form.configList[i].before_material_level =
                  data.config[j].coke_material_level_before
                this.form.configList[i].before_stock =
                  data.config[j].before_stock
                this.form.configList[i].material_number =
                  data.config[j].materials_code
                this.form.configList[i].material_name =
                  data.config[j].coal_type_name
                this.form.configList[i].material_small_code =
                  data.config[j].materials_small_code
                this.form.configList[i].material_small_name =
                  data.config[j].materials_small_name
              }
            }
          }
        } else {
          this.form.plan_id = ''
          this.form.coal_level_materials_code = ''
          this.form.coal_level_materials_name = ''
          this.form.coal_weight_befor = ''
          this.form.coal_material_level_befor = ''
          this.form.configList.forEach((item) => {
            item.before_material_level = 0
            item.after_material_level = 0
            item.before_stock = 0
            item.material_weight = 0
            item.after_stock = 0
            item.duration = 0
            item.material_number = null
            item.material_name = '未配煤'
            item.material_small_code = null
            item.material_small_name = '未配煤'
          })
        }
      })
      getHumanresourceSchedule({ startTime: val }).then((res) => {
        // console.log(res, '班组信息')
        if (res.code == 200) {
          this.teamData = res.data[0]
        }
        // console.log(this.teamData, ' this.teamData ')
      })
      this.calculateDuration()
    },
    //结束时间
    changeEndDate() {
      this.calculateDuration()
    },
    //计算配煤时长
    calculateDuration() {
      // console.log(this.form.start_time, this.form.end_time)
      if (this.form.start_time && this.form.end_time) {
        let time =
          (new Date(this.form.end_time).getTime() -
            new Date(this.form.start_time).getTime()) /
          1000 /
          60
        // console.log(time, 'time')
        this.form.duration = time
        this.form.configList.forEach((item) => {
          item.duration = time
        })
      }
    },
    //限制正整数
    changeInputInt(index, name) {
      this.form.configList[index][name] = this.form.configList[index][
        name
        ].replace(/[^\d]/g, '')
      this.form.configList[index].after_stock =
        Number(this.form.configList[index].before_stock) +
        Number(this.form.configList[index][name])
    },
    //限制只有两位小数的正整数
    changeInput(index, name) {
      this.form.configList[index][name] = this.form.configList[index][
        name
        ].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let newArr = this.form.configList.filter((item) => {
            return item.material_name !== '未配煤'
          })
          this.form.coal_blendings = newArr
          this.form.coal_material_level_after = 0
          this.form.coal_weight = 0
          // this.form.coal_weight_befor = 0
          this.form.coal_blendings.forEach((item) => {
            this.form.coal_weight += Number(item.material_weight)
            // this.form.coal_weight_befor += Number(item.before_stock)
          })
          this.form.coal_weight_after =
            Number(this.form.coal_weight_befor) +
            Number(this.form.coal_weight)
          // this.form.department_id = this.userInfo.alternateField14;
          this.form.user_id = this.$store.getters.userInfo.userId
          this.form.user_name = this.$store.state.user.userInfo.nickName
          // if (this.userInfo.isTeam) {
          //     this.form.shift_start_time =
          //         this.userInfo.team.due_attendance_time_work
          //     this.form.shift_end_time =
          //         this.userInfo.team.due_attendance_time_offduty
          //     this.form.shift_name = this.userInfo.team.shift
          //     this.form.class_name = this.userInfo.team.class_type
          //     // console.log(this.form, "this.form")
          //     this.submitLoading = true
          //     addManualConfigCoal(this.form).then(
          //         (res) => {
          //             if (res.data.code === '0') {
          //                 this.$message({
          //                     type: 'success',
          //                     message: res.msg,
          //                 })
          //                 this.$emit('submitSave', res.msg)
          //                 this.grandpaOnLoad()
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
          // console.log(this.teamData);
          if (this.teamData != undefined) {
            this.form.shift_start_time = this.teamData.start_time
            this.form.shift_end_time = this.teamData.end_time
            this.form.shift_name = this.teamData.shift_no
            this.form.class_name = this.teamData.class_name
            // console.log(this.form, "this.form")
            this.submitLoading = true
            addManualConfigCoal(this.form).then(
              (res) => {
                if (res.code === 200) {
                  this.$message({
                    type: 'success',
                    message: res.msg,
                  })
                  this.$emit('submitSave', res.msg)
                  this.grandpaOnLoad()
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
