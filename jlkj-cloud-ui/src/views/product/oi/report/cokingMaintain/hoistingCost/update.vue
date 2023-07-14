<template>
    <div>
        <div style="padding-top:20px;">
            <el-form ref="form"
                     :model="form"
                     :rules="rules"
                     label-width="120px">
                <el-form-item prop="statistics_date"
                              label="统计日期">
                    <el-date-picker type="date"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期"
                                    v-model="form.statistics_date"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item prop="area_name"
                              label="作业区">
                    <el-select style="width:100%;"
                               :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="form.area_name"
                               clearable
                               filterable
                               placeholder="选择作业区">
                        <el-option v-for="item in operation0ptions"
                                   :key="item.id"
                                   :label="item.name"
                                   :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="car_type"
                              label="车型">
                    <el-select style="width:100%;"
                               :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="form.car_type"
                               clearable
                               filterable
                               placeholder="选择车型">
                        <el-option v-for="item in motorcycleType0ptions"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="tonnes"
                              label="吨数">
                    <el-input v-model="form.tonnes"
                              @input="changeInput('tonnes')"
                              placeholder="输入内容">
                        <i slot="suffix">t</i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="car_time"
                              label="用车时长">
                    <el-input v-model="form.car_time"
                              @input="changeInput('car_time')"
                              placeholder="输入内容">
                        <i slot="suffix">h</i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="day_cost"
                              label="当日费用">
                    <el-input v-model="form.day_cost"
                              @input="changeInput('day_cost')"
                              placeholder="输入内容">
                        <i slot="suffix">元</i>
                    </el-input>
                </el-form-item>
            </el-form>
        </div>
        <div style="width:100%;text-align: right;margin-bottom: 25px;">
            <el-button @click="cancel">取消</el-button>
            <el-button :loading="submitLoading"
                       type="primary"
                       @click="submitForm('form')">确定</el-button>
        </div>
    </div>
</template>

<script>
import { getDicts } from '@/api/system/dict/data'
// import { getSafetyArea } from '@/api/security/WorkTicketApi'
import {
    productionReportLiftingCostsSave,
    productionReportLiftingCostsUpdate,
} from '@/api/production/oi/cokingMaintain'
import { mapGetters } from 'vuex'
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
        return {
            form: {},
            // 表单校验
            rules: {
                statistics_date: [
                    {
                        required: true,
                        message: '统计日期不能为空',
                        trigger: 'change',
                    },
                ],
                area_name: [
                    {
                        required: true,
                        message: '作业区不能为空',
                        trigger: 'change',
                    },
                ],
                car_type: [
                    {
                        required: true,
                        message: '车型不能为空',
                        trigger: 'change',
                    },
                ],
                tonnes: [
                    {
                        required: true,
                        message: '吨数不能为空',
                        trigger: 'blur',
                    },
                ],
                car_time: [
                    {
                        required: true,
                        message: '用车时长不能为空',
                        trigger: 'blur',
                    },
                ],
                day_cost: [
                    {
                        required: true,
                        message: '当日费用不能为空',
                        trigger: 'blur',
                    },
                ],
            },
            submitLoading: false,
            //作业区域下拉
            operation0ptions: [
                { id: '备煤', name: '备煤' },
                { id: '炼焦', name: '炼焦' },
            ],
            motorcycleType0ptions: [],
        }
    },
    created() {
        this.getCarType()
        // this.getSafeArea()
        this.form = { ...this.data }
    },
    computed: {
        ...mapGetters(['userInfo']),
    },
    methods: {
        //获取吊装费用-车型下拉列表
        getCarType() {
            getDicts('report_car_type').then((response) => {
                this.motorcycleType0ptions = response.data.data.map((i) => {
                    return { value: i.dictValue, label: i.dictLabel }
                })
            })
        },
        //获取作业区域
        // getSafeArea() {
        //     getSafetyArea().then((res) => {
        //         // console.log(res)
        //         this.operation0ptions = res.data.data
        //         // this.form.safe_area_id = this.operation0ptions[0].id
        //     })
        // },

        //限制整正数和小数（小数不限制位数）
        changeInput(name) {
            this.form[name] = this.form[name].replace(
                /^\D*(\d*(?:\.\d*)?).*$/g,
                '$1'
            )
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.type == 'add') {
                        this.form.create_user_id = this.userInfo.userId
                        this.form.create_user_name = this.userInfo.userName
                        this.submitLoading = true
                        productionReportLiftingCostsSave(this.form).then(
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
                        this.form.modify_user_id = this.userInfo.userId
                        this.form.modify_user_name = this.userInfo.userName
                        // this.$emit('submitSave')
                        this.submitLoading = true
                        productionReportLiftingCostsUpdate(this.form).then(
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
