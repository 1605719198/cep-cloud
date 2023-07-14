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
                <el-form-item prop="coal_way"
                              label="来煤方式">
                    <el-select style="width:100%;"
                               :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="form.coal_way"
                               clearable
                               filterable
                               placeholder="选择来煤方式">
                        <el-option v-for="item in coalSourceMethodOptions"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="coal_num"
                              label="来煤车数">
                    <el-input v-model="form.coal_num"
                              @input="changeInput('coal_num')"
                              placeholder="输入内容">
                        <!-- <i slot="suffix">KG</i> -->
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
                <el-form-item prop="unloading_coal_screw"
                              label="卸煤-螺旋卸">
                    <el-input v-model="form.unloading_coal_screw"
                              @input="changeInput('unloading_coal_screw')"
                              placeholder="输入内容">
                        <!-- <i slot="suffix">KG</i> -->
                    </el-input>
                </el-form-item>
                <el-form-item prop="unloading_coal_part"
                              label="卸煤-分卸">
                    <el-input v-model="form.unloading_coal_part"
                              @input="changeInput('unloading_coal_part')"
                              placeholder="输入内容">
                        <!-- <i slot="suffix">KG</i> -->
                    </el-input>
                </el-form-item>
                <el-form-item prop="unloaded_screw"
                              label="待卸-螺旋卸">
                    <el-input v-model="form.unloaded_screw"
                              @input="changeInput('unloaded_screw')"
                              placeholder="输入内容">
                        <!-- <i slot="suffix">KG</i> -->
                    </el-input>
                </el-form-item>
                <el-form-item prop="unloaded_part"
                              label="待卸-分卸">
                    <el-input v-model="form.unloaded_part"
                              @input="changeInput('unloaded_part')"
                              placeholder="输入内容">
                        <!-- <i slot="suffix">KG</i> -->
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
    productionReportUnloadingSave,
    productionReportUnloadingUpdate,
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
        coalSourceMethodOptions: {
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
                statistics_date: [
                    {
                        required: true,
                        message: '统计日期不能为空',
                        trigger: 'change',
                    },
                ],
                coal_way: [
                    {
                        required: true,
                        message: '来煤方式不能为空',
                        trigger: 'change',
                    },
                ],
                coal_num: [
                    {
                        required: true,
                        message: '来煤车数不能为空',
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
                unloading_coal_screw: [
                    {
                        required: true,
                        message: '卸煤-螺旋卸不能为空',
                        trigger: 'blur',
                    },
                ],
                unloading_coal_part: [
                    {
                        required: true,
                        message: '卸煤-分卸不能为空',
                        trigger: 'blur',
                    },
                ],
                unloaded_screw: [
                    {
                        required: true,
                        message: '待卸-螺旋卸不能为空',
                        trigger: 'blur',
                    },
                ],
                unloaded_part: [
                    {
                        required: true,
                        message: '待卸-分卸不能为空',
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
        //限制正整数
        changeInput(name) {
            this.form[name] = this.form[name].replace(/[^\d]/g, '')
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.type == 'add') {
                        this.form.create_user_id = this.userInfo.userId
                        this.form.create_user_name = this.userInfo.userName
                        this.submitLoading = true
                        productionReportUnloadingSave(this.form).then(
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
                        productionReportUnloadingUpdate(this.form).then(
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
