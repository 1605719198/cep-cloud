<template>
    <div>
        <div style="padding-top:20px;">
            <el-form ref="form"
                     :model="form"
                     :rules="rules"
                     label-width="120px">
                <el-form-item prop="statistics_date"
                              label="消耗日期">
                    <el-date-picker type="date"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期"
                                    v-model="form.statistics_date"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item prop="unloading_fee"
                              label="卸车费">
                    <el-input v-model="form.unloading_fee"
                              @input="changeInput('unloading_fee')"
                              placeholder="输入内容">
                        <i slot="suffix">元</i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="reverse_fee"
                              label="倒运费">
                    <el-input v-model="form.reverse_fee"
                              @input="changeInput('reverse_fee')"
                              placeholder="输入内容">
                        <i slot="suffix">元</i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="temporary_fee"
                              label="临时用">
                    <el-input v-model="form.temporary_fee"
                              @input="changeInput('temporary_fee')"
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
import {
    productionReportLogisticsCostsSave,
    productionReportLogisticsCostsUpdate,
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
                unloading_fee: [
                    {
                        required: true,
                        message: '卸车费不能为空',
                        trigger: 'blur',
                    },
                ],
                reverse_fee: [
                    {
                        required: true,
                        message: '倒运费不能为空',
                        trigger: 'blur',
                    },
                ],
                temporary_fee: [
                    {
                        required: true,
                        message: '临时用不能为空',
                        trigger: 'blur',
                    },
                ],
            },
            submitLoading: false,
        }
    },
    created() {
        this.form = { ...this.data }
    },
    computed: {
        ...mapGetters(['userInfo']),
    },
    methods: {
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
                        productionReportLogisticsCostsSave(this.form).then(
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
                        productionReportLogisticsCostsUpdate(this.form).then(
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
