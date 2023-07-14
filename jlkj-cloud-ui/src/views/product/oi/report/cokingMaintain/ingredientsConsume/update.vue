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
                <el-form-item prop="excipient_name"
                              label="名称">
                    <el-select style="width:100%;"
                               :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="form.excipient_name"
                               clearable
                               filterable
                               placeholder="选择名称">
                        <el-option v-for="item in nameOptions"
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
                        <i slot="suffix">KG</i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="stock"
                              label="库存">
                    <el-input v-model="form.stock"
                              @input="changeInput('stock')"
                              placeholder="输入内容">
                        <i slot="suffix">KG</i>
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
    productionReportExcipientConsumeSave,
    productionReportExcipientConsumeUpdate,
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
        nameOptions: {
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
                excipient_name: [
                    {
                        required: true,
                        message: '名称不能为空',
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
                stock: [
                    {
                        required: true,
                        message: '库存不能为空',
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
                        productionReportExcipientConsumeSave(this.form).then(
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
                        productionReportExcipientConsumeUpdate(this.form).then(
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
