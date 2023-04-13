<template>
  <div style="width: 100%">
    <el-form :model="formData"
             :rules="rules"
             ref="formData"
             label-width="95px"
             class="demo-ruleForm">
      <el-form-item label="开始时间"
                    prop="start_time">
        <el-date-picker type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        placeholder="请选择开始时间"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        v-model="formData.start_time"
                        style="width: 100%"
                        @change="startTimeDataPickerChange" />
      </el-form-item>

      <el-form-item label="结束时间"
                    prop="end_time">
        <el-date-picker type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        placeholder="请选择结束时间"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        v-model="formData.end_time"
                        style="width: 100%"
                        @change="endTimeDataPickerChange" />
      </el-form-item>

      <el-form-item label="上煤时长">
        <el-input readonly
                  v-model="formData.duration">
          <i slot="suffix">min</i>
        </el-input>
      </el-form-item>

      <el-form-item label="班次">
        <el-input readonly
                  v-model="formData.shift_text" />
      </el-form-item>

      <el-form-item label="班别">
        <el-input readonly
                  v-model="formData.class_name" />
      </el-form-item>

      <el-form-item label="储位名称"
                    prop="slot_number">
        <el-select :popper-append-to-body="false"
                   class="customSelectStyle"
                   v-model="formData.slot_number"
                   placeholder="选择储位"
                   style="width: 100%">
          <el-option v-for="item in storageSpacesOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="堆取料机号"
                    prop="stacker_reclaimer_number">
        <el-select :popper-append-to-body="false"
                   class="customSelectStyle"
                   v-model="formData.stacker_reclaimer_number"
                   placeholder="选择堆取料机号"
                   style="width: 100%">
          <el-option label="1"
                     value="1"></el-option>
          <el-option label="2"
                     value="2"></el-option>
          <el-option label="3"
                     value="3"></el-option>
        </el-select>
      </el-form-item>

    </el-form>
    <div style="text-align:right; margin-bottom:25px;">
            <span slot="footer"
                  class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary"
                           @click="handleChange('formData')">确 定</el-button>
            </span>
    </div>
  </div>
</template>

<script>
import { listMaterialsStorageSpacesBox } from '@/api/material/mr/parameter/storeLocation'
import moment from 'moment'
import {
  insertProductionStackerReclaimerPerformance,
  updateProductionStackerReclaimerPerformance,
  getUserSchedulingInfo,
} from '@/api/production/oi/actual/coal/StackerReclaimerPerformanceApi'

export default {
  name: 'StackerReclaimerActualEditor',
  props: ['data', 'isAdd'],
  data() {
    return {
      formData: {
        shift_name: '',
        shift_text: '',
        class_name: '',
        stacker_reclaimer_number: '',
        slot_number: '',
        slot_name: '',
        start_time: '',
        end_time: '',
        duration: '',
      },
      storageSpacesOptions: [],
      rules: {
        start_time: [
          {
            required: true,
            message: '请选择开始时间',
            trigger: 'blur',
          },
        ],
        end_time: [
          {
            required: true,
            message: '请选择结束时间',
            trigger: 'blur',
          },
        ],
        slot_number: [
          {
            required: true,
            message: '请选择储位',
            trigger: 'change',
          },
        ],
        stacker_reclaimer_number: [
          {
            required: true,
            message: '请选择堆取料机号',
            trigger: 'change',
          },
        ],
      },
    }
  },
  mounted() {
    this.getSpacesOptions()
    if (!this.isAdd) {
      this.formData = { ...this.data }
    }
  },
  methods: {
    // 获取物料储位下拉列表
    getSpacesOptions() {
      listMaterialsStorageSpacesBox({}).then((res) => {
        let options = []
        res.forEach((item) => {
          let i = {
            id: item.id,
            name: item.materials_small_name,
          }
          options.push(i)
        })
        this.storageSpacesOptions = options
      })
    },

    //取消
    cancel() {
      this.$emit('close')
    },

    //确认
    handleChange() {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          if (this.isAdd) {
            insertProductionStackerReclaimerPerformance(
              this.formData
            ).then(
              (res) => {
                if (res.code === 200) {
                  this.$message.success(res.msg)
                  setTimeout(() => {
                    this.$emit('close')
                  }, 1000)
                }
              },
              (err) => {
                window.console.log(err)
              }
            )
          } else {
            this.formData['id'] = this.data.id
            updateProductionStackerReclaimerPerformance(
              this.formData
            ).then(
              (res) => {
                if (res.code === 200) {
                  this.$message.success(res.msg)
                  setTimeout(() => {
                    this.$emit('close')
                  }, 1000)
                }
              },
              (err) => {
                window.console.log(err)
              }
            )
          }
        } else {
          return false
        }
      })
    },
    //获取班别班次
    getUserSchedulingInfo() {
      let params = {
        jobNumber: this.$store.state.user.userInfo.userName,
        dueAttendanceTimeWork: this.formData.start_time,
      }
      getUserSchedulingInfo(params).then(
        (res) => {
          if (res.code === 200) {
            if (res != null) {
              this.formData.class_name = res.classType
              this.formData.shift_name = res.shift
            }
          }
        },
        (err) => {
          window.console.log(err)
        }
      )
    },

    //通过班次code找名称   通过名称找code
    shiftFormat(type, param) {
      if (type === 0) {
        //code找名称
        switch (param) {
          case '01':
            return '常白班'
          case '2201':
            return '白班'
          case '2202':
            return '夜班'
          case '00':
            return '休息'
          default:
            return param
        }
      } else {
        //通过名称找code
        switch (param) {
          case '常白班':
            return '01'
          case '白班':
            return '2201'
          case '夜班':
            return '2202'
          case '休息':
            return '00'
          default:
            return param
        }
      }
    },

    startTimeDataPickerChange() {
      if (
        this.formData.start_time !== null &&
        this.formData.start_time !== ''
      ) {
        this.getUserSchedulingInfo()
      }

      if (
        this.formData.start_time !== '' &&
        this.formData.start_time !== null &&
        this.formData.end_time !== null &&
        this.formData.end_time !== ''
      ) {
        let startTimeMinutes = 0
        let endTimeMinutes = 0

        startTimeMinutes =
          moment(this.formData.start_time).valueOf() / 1000 / 60
        endTimeMinutes =
          moment(this.formData.end_time).valueOf() / 1000 / 60
        this.formData.duration = Math.ceil(
          endTimeMinutes - startTimeMinutes
        )
      }
    },

    endTimeDataPickerChange() {
      if (
        this.formData.start_time === '' ||
        this.formData.start_time === null
      ) {
        return
      }

      if (
        this.formData.end_time === '' ||
        this.formData.end_time === null
      ) {
        return
      }

      let startTimeMinutes = 0
      let endTimeMinutes = 0

      startTimeMinutes =
        moment(this.formData.start_time).valueOf() / 1000 / 60
      endTimeMinutes =
        moment(this.formData.end_time).valueOf() / 1000 / 60
      this.formData.duration = Math.ceil(
        endTimeMinutes - startTimeMinutes
      )
    },
  },
  watch: {
    'formData.slot_number'(val) {
      this.storageSpacesOptions.forEach((i) => {
        if (i.id === val) {
          this.formData.slot_name = i.name
        }
      })
    },
    'formData.shift_name'(val) {
      this.formData.shift_text = this.shiftFormat(0, val)
    },
  },
}
</script>

<style scoped>
</style>
