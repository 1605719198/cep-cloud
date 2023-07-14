<template>
    <div>
        <div style="padding-top:20px;">
            <el-form ref="form"
                     :model="form"
                     :rules="rules"
                     label-width="120px">
                <el-form-item prop="consume_date"
                              label="消耗日期">
                    <el-date-picker type="date"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期"
                                    v-model="form.consume_date"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item prop="device_name"
                              label="设备名称">
                    <el-select style="width:100%;"
                               :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="form.device_name"
                               clearable
                               filterable
                               placeholder="选择设备名称">
                        <el-option v-for="item in equipmentOptions"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="oil_type"
                              label="油品类型">
                    <el-select style="width:100%;"
                               :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="form.oil_type"
                               clearable
                               filterable
                               placeholder="选择油品类型">
                        <el-option v-for="item in oilsTypeOptions"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="daily_consumption"
                              label="日耗">
                    <el-input v-model="form.daily_consumption"
                              @input="changeInput('daily_consumption')"
                              placeholder="输入内容">
                        <i slot="suffix">L</i>
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
    productionReportOilConsumeSave,
    productionReportOilConsumeUpdate,
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
        equipmentOptions: {
            type: Array,
            default: () => {
                return []
            },
        },
        oilsTypeOptions: {
            type: Array,
            default: () => {
                return []
            },
        },
    },
    data() {
        return {
            form: {},
            // 表单校验
            rules: {
                consume_date: [
                    {
                        required: true,
                        message: '消耗日期不能为空',
                        trigger: 'change',
                    },
                ],
                device_name: [
                    {
                        required: true,
                        message: '设备名称不能为空',
                        trigger: 'change',
                    },
                ],
                oil_type: [
                    {
                        required: true,
                        message: '油品类型不能为空',
                        trigger: 'change',
                    },
                ],
                daily_consumption: [
                    {
                        required: true,
                        message: '日耗不能为空',
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
                    this.form.daily_consumption = Number(
                        this.form.daily_consumption
                    )
                    if (this.type == 'add') {
                        this.form.create_user_id = this.userInfo.userId
                        this.form.create_user_name = this.userInfo.userName
                        this.submitLoading = true
                        productionReportOilConsumeSave(this.form).then(
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
                        productionReportOilConsumeUpdate(this.form).then(
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
