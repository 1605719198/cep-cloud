<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="150px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="shift_text"
                          label="班次">
              <el-input disabled
                        v-model="form.shift_text">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="class_name"
                          label="班别">
              <el-input disabled
                        v-model="form.class_name">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="coke_oven_number"
                          label="炉号">
              <el-select style="width:100%;"
                         :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="form.coke_oven_number"
                         clearable
                         filterable
                         placeholder="选择炉号">
                <el-option v-for="item in furnaceNumOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="carbonization_chamber_number"
                          label="炭化室号">
              <el-select style="width:100%;"
                         :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="form.carbonization_chamber_number"
                         clearable
                         filterable
                         placeholder="选择炭化室号">
                <el-option v-for="item in cokingChamberNumOptions"
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
            <el-form-item prop="production_date"
                          label="日期">
              <el-date-picker type="datetime"
                              format="yyyy-MM-dd"
                              value-format="yyyy-MM-dd"
                              placeholder="选择时间"
                              v-model="form.production_date"
                              @change="changeStartDate"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="actual_production_time"
                          label="出炉时间">
              <!-- <el-date-picker type="datetime"
                              format="yyyy-MM-dd HH:mm"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择时间"
                              v-model="form.actual_production_time"
                              style="width: 100%;"></el-date-picker> -->
              <el-time-picker style="width: 100%;"
                              v-model="form.actual_production_time"
                              format="HH:mm"
                              value-format="HH:mm:ss"
                              placeholder="选择时间"
                              @change="changeStartDate">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="actual_load_coal_time"
                          label="出炉后装煤时间">
              <!-- <el-date-picker type="datetime"
                              format="yyyy-MM-dd HH:mm"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="选择时间"
                              v-model="form.actual_load_coal_time"
                              style="width: 100%;"></el-date-picker> -->
              <el-time-picker style="width: 100%;"
                              v-model="form.actual_load_coal_time"
                              format="HH:mm"
                              value-format="HH:mm:ss"
                              placeholder="选择时间">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="loading_coal"
                          label="出炉后装煤重量">
              <el-input v-model="form.loading_coal"
                        @input="changeInput('loading_coal')"
                        placeholder="输入内容">
                <i slot="suffix">kg</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="coal_tower_number"
                          label="储煤塔号">
              <el-select style="width:100%;"
                         :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="form.coal_tower_number"
                         clearable
                         filterable
                         placeholder="选择储煤塔号">
                <el-option v-for="item in towerNumOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="coke_type"
                          label="焦炭类别">
              <el-select style="width:100%;"
                         :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="form.coke_type"
                         clearable
                         filterable
                         placeholder="选择焦炭类别">
                <el-option v-for="item in cokeTypeOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
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
import { insertProductionOutputPerformanceManualBake } from "@/api/production/oi/actual/coke/OutputPerformanceApi";
import { dateFormat } from '@/utils/date'
import { getHumanresourceSchedule } from '@/api/sys/index'
export default {
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {
        shift_text: null,
        shift_name: null,
        class_name: null,
        production_date: dateFormat(new Date(), 'yyyy-MM-dd'),
      },
      // 表单校验
      rules: {
        shift_text: [{ required: true, message: '班次不能为空', trigger: 'blur' }],
        class_name: [{ required: true, message: '班别不能为空', trigger: 'blur' }],
        production_date: [{ required: true, message: '日期不能为空', trigger: 'change' }],
        actual_production_time: [{ required: true, message: '出炉时间不能为空', trigger: 'change' }],
        coke_oven_number: [{ required: true, message: '炉号不能为空', trigger: 'change' }],
        carbonization_chamber_number: [{ required: true, message: '炭化室号不能为空', trigger: 'change' }],
        actual_load_coal_time: [{ required: true, message: '装煤时间不能为空', trigger: 'change' }],
        loading_coal: [{ required: true, message: '装煤重量不能为空', trigger: 'blur' }],
        coal_tower_number: [{ required: true, message: '储煤塔号不能为空', trigger: 'change' }],
        coke_type: [{ required: true, message: '焦炭类别不能为空', trigger: 'change' }],
      },
      furnaceNumOptions: [
        { id: 1, name: '1号' },
        { id: 2, name: '2号' },
        { id: 3, name: '3号' },
        { id: 4, name: '4号' },
        { id: 5, name: '5号' },
        { id: 6, name: '6号' },
        { id: 7, name: '7号' },
        { id: 8, name: '8号' },
        { id: 9, name: '9号' },
        { id: 10, name: '10号' },
        { id: 11, name: '11号' },
        { id: 12, name: '12号' },
        { id: 13, name: '13号' },
        { id: 14, name: '14号' },
        { id: 15, name: '15号' },
        { id: 16, name: '16号' },
      ],
      cokingChamberNumOptions: [
        { id: 1, name: '1号' },
        { id: 2, name: '2号' },
        { id: 3, name: '3号' },
        { id: 4, name: '4号' },
        { id: 5, name: '5号' },
        { id: 6, name: '6号' },
        { id: 7, name: '7号' },
        { id: 8, name: '8号' },
        { id: 9, name: '9号' },
        { id: 10, name: '10号' },
        { id: 11, name: '11号' },
        { id: 12, name: '12号' },
        { id: 13, name: '13号' },
        { id: 14, name: '14号' },
        { id: 15, name: '15号' },
        { id: 16, name: '16号' },
        { id: 17, name: '17号' },
      ],
      towerNumOptions: [
        { id: 1, name: '1号' },
        { id: 2, name: '2号' },
        { id: 3, name: '3号' },
        { id: 4, name: '4号' },
      ],
      cokeTypeOptions: [
        { id: 1, name: '干熄焦' },
        { id: 2, name: '湿熄焦' },
      ],
      submitLoading: false,
      teamData: null,
    }
  },
  created() {
    // console.log(this.userInfo)
    // this.form.shift_text = this.userInfo.team.shift_text
    // this.form.shift_name = this.userInfo.team.shift
    // this.form.class_name = this.userInfo.team.class_type
  },
  computed: {
  },
  methods: {
    changeInput (name) {
      // if (name == 'loading_coal') {
      //   //限制正整数
      //   this.form[name] = this.form[name].replace(/[^\d]/g, "")
      // } else {
      //   //限制只有两位小数的正整数
      //   this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
      // }
      //限制正整数
      this.form[name] = this.form[name].replace(/[^\d]/g, '')
    },
    changeStartDate() {
      if (this.form.production_date && this.form.actual_production_time) {
        // console.log(
        //     this.form.production_date +
        //         ' ' +
        //         this.form.actual_production_time
        // )
        let startTime =
          this.form.production_date +
          ' ' +
          this.form.actual_production_time
        getHumanresourceSchedule({ startTime: startTime }).then(
          (res) => {
            // console.log(res, '班组信息')
            if (res.data.code == 0) {
              this.teamData = res.data.data[0]
              this.form.shift_text = this.teamData.shift_name
              this.form.shift_name = this.teamData.shift_no
              this.form.class_name = this.teamData.class_name
            }
            // console.log(this.teamData, ' this.teamData ')
          }
        )
      }
    },
    // 比较两个日期
    compareDate(start, end) {
      return new Date(end).getTime() - new Date(start).getTime()
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        // console.log(this.form, 'this.form')
        if (valid) {
          if (this.userInfo.isTeam && this.teamData.start_time) {
            let outFurnaceDate =
              this.form.production_date +
              ' ' +
              this.form.actual_production_time
            let coalingDate =
              this.form.production_date +
              ' ' +
              this.form.actual_load_coal_time

            let outFurnaceStartTime = this.compareDate(
              outFurnaceDate,
              this.userInfo.team.due_attendance_time_work
            )
            let outFurnaceEndTime = this.compareDate(
              outFurnaceDate,
              this.userInfo.team.due_attendance_time_offduty
            )

            let coalingStartTime = this.compareDate(
              coalingDate,
              this.userInfo.team.due_attendance_time_work
            )
            let coalingEndTime = this.compareDate(
              coalingDate,
              this.userInfo.team.due_attendance_time_offduty
            )

            // console.log(outFurnaceDate, outFurnaceStartTime, outFurnaceEndTime)

            if (outFurnaceStartTime > 0 || outFurnaceEndTime < 0) {
              this.$message.error(
                '出炉时间非当前用户工作时间，请重新选择！'
              )
              return false
            }

            if (coalingStartTime > 0 || coalingEndTime < 0) {
              this.$message.error(
                '装煤时间非当前用户工作时间，请重新选择！'
              )
              return false
            }

            this.form.class_start_time =
              // this.userInfo.team.due_attendance_time_work
              this.teamData.start_time
            this.form.class_end_time =
              // this.userInfo.team.due_attendance_time_offduty
              this.teamData.end_time
            // this.form.production_date = this.form.actual_production_time.substring(0, 10);
            this.form.create_user_id = this.$store.getters.userInfo.userId
            this.form.create_user_name = this.userInfo.userName
            insertProductionOutputPerformanceManualBake(
              this.form
            ).then(
              (res) => {
                if (res.code == 200) {
                  this.$message({
                    type: 'success',
                    message: res.msg,
                  })
                  this.$emit('submitSave', res.msg)
                }
                this.submitLoading = false
              },
              (error) => {
                this.submitLoading = false
                window.console.log(error)
              }
            )
          } else {
            this.$message.error(
              '当前用户非当班用户或未获取到排班信息！'
            )
          }
        }
      })
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style>
</style>
