<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="考核年月" prop="meritMonth">
            <el-date-picker
              v-model="queryParams.meritMonth"
              type="monthrange"
              value-format="yyyy-MM"
              range-separator="至"
              start-placeholder="开始月份"
              end-placeholder="结束月份"
              @change="dateFormat">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="考评类别" prop="meritType">
            <el-select v-model="queryParams.meritType" placeholder="考评类别" style="width: 100%">
              <el-option
                v-for="dict in performanceOptions.MeritType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="queryParams.empName" disabled>
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="20">
          <el-col :span="4">
            <pagination
              v-show="personPerformanceTotal>0"
              :total="personPerformanceTotal"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              style="margin-top: -12px;margin-bottom: 24px;right: 48px"
              @pagination="getList"
            />
            <el-table v-loading="loading" :data="personPerformanceList" border @row-click="getPersonPerformance">
              <el-table-column label="年月" align="center" prop="meritMonth" />
              <el-table-column label="类别" align="center" prop="meritType">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritType" :value="scope.row.meritType"/>
                </template>
              </el-table-column>
              <el-table-column label="状态" align="center" prop="meritStatus">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritStatus" :value="scope.row.meritStatus"/>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="20">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-right"
                  size="mini"
                  @click="handleSend"
                  :disabled="form.selfAppr==null"
                  v-if="showSend"
                  v-hasPermi="['human:personPerformanceEvaluation:send']"
                >呈送</el-button>
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-check"
                  size="mini"
                  @click="handleConfirm"
                  v-else
                  v-hasPermi="['human:personPerformanceEvaluation:confirm']"
                >确认</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="员工姓名" prop="empId">
                    {{this.user.empId}}-{{this.user.empName}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="所属单位" prop="dept">
                    {{form.dept}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="岗位" prop="postId">
                    {{form.postId}}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="考核年月" prop="meritMonth">
                    <el-date-picker
                      v-model="form.meritMonth"
                      value-format="yyyy-MM"
                      type="month">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评类别" prop="meritType">
                    <el-select v-model="form.meritType" placeholder="考评类别">
                      <el-option
                        v-for="dict in performanceOptions.MeritType"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="人员类别" prop="deptType">
                    <dict-tag-human :options="baseInfoData.HP020" :value="form.deptType"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="输入人" prop="creator">
                    {{form.creator}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="输入日期" prop="createDate">
                    {{form.createDate}}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="自评总结" prop="selfAppr">
                    <el-input v-model="form.selfAppr" disabled>
                      <el-button slot="append" icon="el-icon-edit-outline" :disabled="showSelfAppr" @click="selfEvaluationSummary">自评总结</el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="主管考评意见" prop="admAppr">
                    <el-input v-model="form.admAppr" disabled>
                      <el-button slot="append" icon="el-icon-edit-outline" :disabled="showAdmAppr" @click="supervisorEvaluation">主管考评</el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="1.5">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini">上传成果</el-button>
                </el-col>
                <el-col :span="1.5" :push="21">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-download"
                  >下载
                  </el-button>
                </el-col>
              </el-row>
            </el-form>
            <div class="divFather">
              <div class="page3_div">
                <div class="page3_div_left">
                  <label >
                    重点工作项
                  </label>
                </div>
                <el-table v-loading="keyWorkItemsLoading" :data="keyWorkItemsList" max-height="300px" border>
                  <el-table-column label="项次" width="45" align="center" prop="num"/>
                  <el-table-column label="重点工作内容" align="center" prop="item"/>
                  <el-table-column label="完成标准/目标" align="center" prop="itemGoal"/>
                  <el-table-column label="评价标准" width="150" align="center">
                    <template v-slot="scope">
                      <el-row>
                        <el-col :span="3">
                          <span>S:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldS"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>A:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldA"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>B:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldB"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>C:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldC"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>D:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldD"/>
                        </el-col>
                      </el-row>
                    </template>
                  </el-table-column>
                  <el-table-column label="完成时间" width="150" align="center" prop="completeTime">
                    <template v-slot="scope">
                      <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="权重%" width="75" align="center" prop="weight"/>
                  <el-table-column label="实际完成情况" align="center" prop="completeStatue">
                    <template v-slot="scope">
                      <el-input size="mini" type="textarea" v-model="scope.row.completeStatue" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="自评" align="center" prop="selfScore">
                    <template v-slot="scope">
                      <el-input size="mini" v-model="scope.row.selfScore" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="主管考评" align="center" prop="admScore">
                    <template v-slot="scope">
                      <el-input size="mini" v-model="scope.row.admScore" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="备注" align="center" prop="remark">
                    <template v-slot="scope">
                      <el-input size="mini" type="textarea" v-model="scope.row.remark" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template v-slot="scope">
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit-outline"
                        :disabled="showSelfAppr"
                        @click="handleSelf(scope.row)"
                      >自评
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
            <div class="divFather">
              <div class="page3_div">
                <div class="page3_div_left">
                  <label >
                    KPI指标
                  </label>
                </div>
                <el-table v-loading="kpiLoading" :data="kpiList" max-height="300px" border>
                  <el-table-column label="项次" width="45" align="center" prop="num"/>
                  <el-table-column label="重点工作内容" align="center" prop="item"/>
                  <el-table-column label="完成标准/目标" align="center" prop="itemGoal"/>
                  <el-table-column label="评价标准" width="150" align="center">
                    <template v-slot="scope">
                      <el-row>
                        <el-col :span="3">
                          <span>S:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldS"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>A:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldA"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>B:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldB"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>C:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldC"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>D:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldD"/>
                        </el-col>
                      </el-row>
                    </template>
                  </el-table-column>
                  <el-table-column label="完成时间" width="150" align="center" prop="completeTime">
                    <template v-slot="scope">
                      <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="权重%" width="75" align="center" prop="weight"/>
                  <el-table-column label="实际完成情况" align="center" prop="completeStatue">
                    <template v-slot="scope">
                      <el-input size="mini" type="textarea" v-model="scope.row.completeStatue" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="自评" align="center" prop="selfScore">
                    <template v-slot="scope">
                      <el-input size="mini" v-model="scope.row.selfScore" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="主管考评" align="center" prop="admScore">
                    <template v-slot="scope">
                      <el-input size="mini" v-model="scope.row.admScore" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="备注" align="center" prop="remark">
                    <template v-slot="scope">
                      <el-input size="mini" type="textarea" v-model="scope.row.remark" disabled/>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template v-slot="scope">
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit-outline"
                        :disabled="showSelfAppr"
                        @click="handleSelf(scope.row)"
                      >自评
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
            <div class="divFather">
              <div class="page4_div">
                <div class="page3_div_left">
                  <label >
                    绩效分数
                  </label>
                </div>
                <el-table v-loading="performanceScoreLoading" :data="performanceScoreList" border>
                  <el-table-column label="自评分数" align="center" prop="selfScore"/>
                  <el-table-column label="主管考评分数" align="center" prop="admScore"/>
                  <el-table-column label="团队绩效分数" align="center" prop="teamScore"/>
                  <el-table-column label="最终评定分数" align="center"prop="score"/>
                  <el-table-column label="其他加扣分" align="center" prop="otherAdjust"/>
                  <el-table-column label="等第" align="center" prop="grade"/>
                </el-table>
              </div>
            </div>
            <span>计划节点完成情况</span>
            <el-table v-loading="scheduleLoading" :data="scheduleList" border>
              <el-table-column label="项次" width="55" align="center" prop="num"/>
              <el-table-column label="流程说明" align="center" prop="project"/>
              <el-table-column label="考评者" align="center" prop="apprEmp"/>
              <el-table-column label="确认时间" align="center" prop="apprTime">
                <template v-slot="scope">
                  <span>{{ parseTime(scope.row.apprTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="完成情况" align="center" prop="apprStatus">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.ApprStatus" :value="scope.row.apprStatus"/>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>

        <!-- 自评对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
          <el-form ref="selfForm" :model="selfForm" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项次" prop="num">
                  <el-input-number v-model="selfForm.num" disabled controls-position="right" :min="1" />
                </el-form-item>
                <el-form-item label="重点工作内容" prop="item">
                  <el-input type="textarea" v-model="selfForm.item" disabled placeholder="请输入指标项目"/>
                </el-form-item>
                <el-form-item label="完成目标/标准" prop="itemGoal">
                  <el-input type="textarea" v-model="selfForm.itemGoal" disabled placeholder="请输入完成目标/标准"/>
                </el-form-item>
                <el-form-item label="评价标准">
                  <el-row>
                    <el-col :span="2">
                      <span>S:</span>
                    </el-col>
                    <el-col :span="22">
                      <el-input type="textarea" size="mini" :rows="1" disabled v-model="selfForm.fieldS"/>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="2">
                      <span>A:</span>
                    </el-col>
                    <el-col :span="22">
                      <el-input type="textarea" size="mini" :rows="1" disabled v-model="selfForm.fieldA"/>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="2">
                      <span>B:</span>
                    </el-col>
                    <el-col :span="22">
                      <el-input type="textarea" size="mini" :rows="1" disabled v-model="selfForm.fieldB"/>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="2">
                      <span>C:</span>
                    </el-col>
                    <el-col :span="22">
                      <el-input type="textarea" size="mini" :rows="1" disabled v-model="selfForm.fieldC"/>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="2">
                      <span>D:</span>
                    </el-col>
                    <el-col :span="22">
                      <el-input type="textarea" size="mini" :rows="1" disabled v-model="selfForm.fieldD"/>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item label="完成时间" prop="completeTime">
                  <el-date-picker
                    v-model="selfForm.completeTime"
                    value-format="yyyy-MM-dd"
                    style="width: 37%"
                    disabled
                    type="date">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="权重" prop="weight">
                  <el-input v-model="selfForm.weight" placeholder="请输入权重" disabled style="width: 37%"><span slot="suffix">%</span></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="实际完成情况" prop="completeStatue">
                  <el-input type="textarea" v-model="selfForm.completeStatue" :autosize="{ minRows: 5, maxRows: 4}" placeholder="请输入实际完成情况"/>
                </el-form-item>
                <el-form-item label="自评" prop="selfScore">
                  <el-input v-model="selfForm.selfScore" placeholder="请输入自评" style="width: 37%"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" v-hasPermi="['human:personPerformanceEvaluation:edit']">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 自评总结对话框 -->
        <el-dialog :title="title1" :visible.sync="open1" width="800px" append-to-body>
          <el-form ref="summarizeForm" :model="summarizeForm" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="员工姓名" prop="empId">
                  {{this.user.empId}}-{{this.user.empName}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所属单位" prop="dept">
                  {{summarizeForm.dept}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="岗位" prop="postId">
                  {{summarizeForm.postId}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="考核年月" prop="meritMonth">
                  {{summarizeForm.meritMonth}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="考评类别" prop="meritType">
                  <dict-tag-human :options="performanceOptions.MeritType" :value="summarizeForm.meritType"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="人员类别" prop="deptType">
                  <dict-tag-human :options="baseInfoData.HP020" :value="summarizeForm.deptType"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="自评总结" prop="selfAppr">
                  <el-input type="textarea" v-model="summarizeForm.selfAppr" :autosize="{ minRows: 2, maxRows: 4}" maxlength="400" show-word-limit placeholder="请输入自评总结"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitSummarizeForm" v-hasPermi="['human:personPerformanceEvaluation:edit']">确 定</el-button>
            <el-button @click="cancel1">取 消</el-button>
          </div>
        </el-dialog>
        <!-- 主管考评对话框 -->
        <el-dialog :title="title2" :visible.sync="open2" width="1200px" append-to-body>
          <el-form ref="supervisorEvaluationForm" :model="supervisorEvaluationForm" label-width="100px">
            <el-row>
              <el-col :span="4">
                <el-form-item label="员工姓名" prop="empId">
                  {{this.user.empId}}-{{this.user.empName}}
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="所属单位" prop="dept">
                  {{supervisorEvaluationForm.dept}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="岗位" prop="postId">
                  {{supervisorEvaluationForm.postId}}
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="考评类别" prop="meritType">
                  <dict-tag-human :options="performanceOptions.MeritType" :value="supervisorEvaluationForm.meritType"/>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="考核年月" prop="meritMonth">
                  {{supervisorEvaluationForm.meritMonth}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="自评总结" prop="selfAppr">
                  <el-input type="textarea" v-model="supervisorEvaluationForm.selfAppr" :autosize="{ minRows: 2, maxRows: 4}" maxlength="400" show-word-limit disabled placeholder="请输入自评总结"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="主管评价意见" prop="admAppr">
                  <el-input type="textarea" v-model="supervisorEvaluationForm.admAppr" :autosize="{ minRows: 2, maxRows: 4}" maxlength="400" show-word-limit placeholder="请输入主管评价意见"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="divFather">
            <div class="page3_div">
              <div class="page3_div_left">
                <label >
                  重点工作项
                </label>
              </div>
              <el-table v-loading="keyWorkItemsLoading" :data="supervisorEvaluationForm.keyWorkItemsList" max-height="300px" border>
                <el-table-column label="项次" width="45" align="center" prop="num"/>
                <el-table-column label="重点工作内容" align="center" prop="item"/>
                <el-table-column label="完成标准/目标" align="center" prop="itemGoal"/>
                <el-table-column label="评价标准" width="150" align="center">
                  <template v-slot="scope">
                    <el-row>
                      <el-col :span="3">
                        <span>S:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldS"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>A:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldA"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>B:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldB"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>C:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldC"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>D:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldD"/>
                      </el-col>
                    </el-row>
                  </template>
                </el-table-column>
                <el-table-column label="完成时间" width="150" align="center" prop="completeTime">
                  <template v-slot="scope">
                    <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="权重%" width="75" align="center" prop="weight"/>
                <el-table-column label="实际完成情况" align="center" prop="completeStatue">
                  <template v-slot="scope">
                    <el-input size="mini" type="textarea" v-model="scope.row.completeStatue" disabled/>
                  </template>
                </el-table-column>
                <el-table-column label="自评" align="center" prop="selfScore">
                  <template v-slot="scope">
                    <el-input size="mini" v-model="scope.row.selfScore" disabled/>
                  </template>
                </el-table-column>
                <el-table-column label="主管考评" align="center" prop="admScore">
                  <template v-slot="scope">
                    <el-input size="mini" v-model="scope.row.admScore"/>
                  </template>
                </el-table-column>
                <el-table-column label="备注" align="center" prop="remark">
                  <template v-slot="scope">
                    <el-input size="mini" type="textarea" v-model="scope.row.remark"/>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <div class="divFather">
            <div class="page3_div">
              <div class="page3_div_left">
                <label >
                  KPI指标
                </label>
              </div>
              <el-table v-loading="kpiLoading" :data="supervisorEvaluationForm.kpiList" max-height="300px" border>
                <el-table-column label="项次" width="45" align="center" prop="num"/>
                <el-table-column label="重点工作内容" align="center" prop="item"/>
                <el-table-column label="完成标准/目标" align="center" prop="itemGoal"/>
                <el-table-column label="评价标准" width="150" align="center">
                  <template v-slot="scope">
                    <el-row>
                      <el-col :span="3">
                        <span>S:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldS"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>A:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldA"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>B:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldB"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>C:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldC"/>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="3">
                        <span>D:</span>
                      </el-col>
                      <el-col :span="21">
                        <el-input type="textarea" size="mini" :rows="1" disabled v-model="scope.row.fieldD"/>
                      </el-col>
                    </el-row>
                  </template>
                </el-table-column>
                <el-table-column label="完成时间" width="150" align="center" prop="completeTime">
                  <template v-slot="scope">
                    <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="权重%" width="75" align="center" prop="weight"/>
                <el-table-column label="实际完成情况" align="center" prop="completeStatue">
                  <template v-slot="scope">
                    <el-input size="mini" type="textarea" v-model="scope.row.completeStatue" disabled/>
                  </template>
                </el-table-column>
                <el-table-column label="自评" align="center" prop="selfScore">
                  <template v-slot="scope">
                    <el-input size="mini" v-model="scope.row.selfScore" disabled/>
                  </template>
                </el-table-column>
                <el-table-column label="主管考评" align="center" prop="admScore">
                  <template v-slot="scope">
                    <el-input size="mini" v-model="scope.row.admScore"/>
                  </template>
                </el-table-column>
                <el-table-column label="备注" align="center" prop="remark">
                  <template v-slot="scope">
                    <el-input size="mini" type="textarea" v-model="scope.row.remark"/>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <div class="divFather">
            <div class="page4_div">
              <div class="page3_div_left">
                <label >
                  绩效分数
                </label>
              </div>
              <el-table v-loading="performanceScoreLoading" :data="supervisorEvaluationForm.performanceScoreList" border>
                <el-table-column label="自评分数" align="center" prop="selfScore"/>
                <el-table-column label="主管考评分数" align="center" prop="admScore"/>
                <el-table-column label="团队绩效分数" align="center" prop="teamScore"/>
                <el-table-column label="最终评定分数" align="center"prop="score"/>
                <el-table-column label="其他加扣分" align="center" prop="otherAjust"/>
                <el-table-column label="等第" align="center" prop="grade"/>
              </el-table>
            </div>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitSupervisorEvaluationForm" v-hasPermi="['human:personPerformanceEvaluation:edit']">确 定</el-button>
            <el-button @click="cancel2">取 消</el-button>
          </div>
        </el-dialog>
        <select-user ref="select" @ok="getEmpId"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import {
  addPersonPerformance, confirmPersonalPerformance,
  delPersonPerformance, delPersonPerformanceDetail,
  listPersonPerformance,
  listPersonPerformanceDetail,
  listPersonPerformanceSchedule, sendSelfAppr, updateAdmAppr,
  updatePersonPerformanceDetail, updateSelfAppr, updateSelfApprDetail
} from "@/api/human/pa/personPerformance";
import {queryInfo} from "@/api/human/hm/personnelBasicInfo";
import {getPostMaintenance} from "@/api/human/hp/postMaintenance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {listJobTitleidname} from "@/api/human/hp/jobTitle";
import {getBaseInfo} from "@/api/human/hm/baseInfo";

export default {
  name: "PersonalPerformanceEvaluation",
  components: { selectUser,DictTagHuman },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 重点工作项遮罩层
      keyWorkItemsLoading: false,
      // KPI指标遮罩层
      kpiLoading: false,
      // 绩效进度遮罩层
      scheduleLoading: false,
      // 绩效分数遮罩层
      performanceScoreLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 个人绩效主档表格总条数
      personPerformanceTotal: 0,
      // 个人绩效主档表格数据
      personPerformanceList: [],
      // 重点工作项表格数据
      keyWorkItemsList: [],
      // KPI指标表格数据
      kpiList: [],
      // 绩效进度表格数据
      scheduleList: [],
      // 绩效分数表格数据
      performanceScoreList: [],
      // 计划指标弹出层标题
      title: "",
      // 自评总结弹出层标题
      title1: "",
      // 主管考评弹出层标题
      title2: "",
      // 是否显示计划弹出层
      open: false,
      // 是否显示自评总结弹出层
      open1: false,
      // 是否显示主管考评弹出层
      open2: false,
      // 自评按钮控制
      showSelfAppr: true,
      // 主管考评按钮控制
      showAdmAppr: true,
      // 是否显示呈送按钮
      showSend: true,
      params1: false,
      params2: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pageNum1: 1,
        pageSize1: 10,
        compId: null,
        meritMonth: null,
        empName: null,
        empId: null,
        empNo: null
      },
      // 明细表单参数
      form: {
        creator: null
      },
      // 自评表单参数
      selfForm: {},
      // 自评总结表单参数
      summarizeForm: {},
      // 主管考评表单参数
      supervisorEvaluationForm: {},
      // 表单校验
      rules: {
        meritMonth:[
          { required: true, message: "考核年月不能为空", trigger: ['blur', 'change'] }
        ],
        meritType:[
          { required: true, message: "考评类别不能为空", trigger: ['blur', 'change'] }
        ],
      },
      // 公司别下拉选单
      companyName: [],
      //登录人信息
      user: {},
      //绩效选单选项列表
      performanceOptions:{},
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:[
          'MeritType',
          'MeritStatus',
          'ApprStatus']
      },
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'HP020'
        ]
      },
      //选单数据
      baseInfoData: [],
      postId: 0,
      postName: null,
      //职位名称id列表
      humanJobTitle:[],
      //呈送表单数据
      sendForm:{},
      //确认表单数据
      confirmForm:{},
      //是否最终主管
      finalSupervisor: false
    };
  },
  created() {
    this.initData();
    this.getDisc();
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    //初始化数据
    initData(){
      listJobTitleidname(this.queryParams.compId).then(response =>{
        this.humanJobTitle = response.rows;
      })
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data;
      });
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.user.empId = this.$store.state.user.userInfo.userName;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.queryParams.compId = this.user.compId
    },
    /** 查询个人绩效主档列表 */
    getList() {
      this.loading = true;
      listPersonPerformance(this.queryParams).then(response => {
        this.personPerformanceList = response.data.rows;
        this.personPerformanceTotal = response.data.total;
        this.loading = false;
      });
    },
    getDetailList() {
      this.keyWorkItemsList = []
      this.kpiList = []
      this.keyWorkItemsLoading = true;
      this.kpiLoading = true;
      listPersonPerformanceDetail(this.form).then(response => {
        for (const item of response.data.rows) {
          if (item.type === '1') {
            this.keyWorkItemsList.push(item)
          } else if (item.type === '2') {
            this.kpiList.push(item)
          }
        }
        this.keyWorkItemsLoading = false;
        this.kpiLoading = false;
      });
    },
    getScheduleList() {
      this.scheduleList = []
      this.scheduleLoading = true;
      this.showSelfAppr = true;
      this.showAdmAppr = true;
      this.params1 = false;
      this.params2 = false;
      this.finalSupervisor = false;
      listPersonPerformanceSchedule(this.form).then(res =>{
        this.scheduleList = res.data.rows;
        for (const item of this.scheduleList) {
          if (item.apprEmp) {
            item.apprEmp = item.apprEmp + '-' + item.apprName
          }
          if (item.num === '2' && item.apprStatus === '1'){
            this.params1 = true;
          }
          if (item.num === '3' && item.apprStatus === '0'){
            this.params2 = true;
          }
          if (this.params1 === true && this.params2 === true) {
            this.showSelfAppr = false;
          }
          if (item.num === '4' && item.apprStatus === '1'){
            this.finalSupervisor = true;
          }
          if (item.num === '4' && item.apprStatus === '4'){
            this.showAdmAppr = false;
          }
          if (item.num === '5' && item.apprStatus === '5'){
            this.showSend = false;
          }
        }
        this.scheduleLoading = false;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel1() {
      this.open1 = false;
      this.reset();
    },
    // 取消按钮
    cancel2() {
      this.open2 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.selfForm = {
        num: null,
        item: null,
        itemGoal: null,
        fieldS: null,
        fieldA: null,
        fieldB: null,
        fieldC: null,
        fieldD: null,
        completeTime: null,
        weight: null,
        completeStatue: null,
        selfAppr: null
      };
      this.resetForm("selfForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.compId = this.queryParams.compId
          this.form.empId = this.user.empId
          this.queryParams.empNo = this.user.empId
          queryInfo(this.queryParams).then(response => {
            this.form.dept = response.data[0].departmentId
            this.form.postId = response.data[0].postId
            this.postId = response.data[0].postId
            getPostMaintenance(this.postId).then(response => {
              this.form.deptType = response.data.deptType
              this.form.jobTitleId = response.data.jobTitleId
              addPersonPerformance(this.form).then(res =>{
                this.$modal.msgSuccess("新增成功");
              })
            })
          })
        }
      });
    },
    /** 自评按钮操作 */
    handleSelf(data) {
      this.reset();
      this.selfForm = data
      this.open = true;
      this.title = "自评";
    },
    /** 自评提交按钮 */
    submitForm() {
      updateSelfApprDetail(this.selfForm).then(response => {
        this.$modal.msgSuccess("自评成功");
        this.open = false;
        this.getDetailList();
        this.getScheduleList();
      });
    },
    /** 自评总结提交按钮 */
    submitSummarizeForm() {
      this.summarizeForm.postId = null
      updateSelfAppr(this.summarizeForm).then(response => {
        this.form.postId = this.postName
        this.$modal.msgSuccess("更新成功");
        this.open1 = false;
      });
    },
    /** 主管考评提交按钮 */
    submitSupervisorEvaluationForm() {
      this.supervisorEvaluationForm.postId = null
      updateAdmAppr(this.supervisorEvaluationForm).then(response => {
        this.form.postId = this.postName
        this.$modal.msgSuccess("新增成功");
        this.open2 = false;
        this.getDetailList();
        this.getScheduleList();
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      delPersonPerformance(this.form).then(res => {
        this.form = {}
        this.getDetailList();
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 删除按钮操作 */
    handleDeleteDetail(data) {
      delPersonPerformanceDetail(data).then(res => {
        this.getDetailList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 查询人员类别考评项目设定明细档表格 */
    getPersonPerformance(row) {
      this.form.postId = null
      this.form = row
      this.form.empNo = row.empId
      queryInfo(this.form).then(response => {
        this.form.dept = response.data[0].departmentName
        this.form.postId = response.data[0].postName
        this.postName = response.data[0].postName
      })
      this.getDetailList();
      this.getScheduleList();
    },
    /** 修改按钮操作 */
    handleUpdate(data) {
      updatePersonPerformanceDetail(data).then(res =>{
        this.getDetailList();
        this.$modal.msgSuccess("修改成功");
      })
    },
    /** 获取员工姓名点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getEmpId(val,userName) {
      this.queryParams.empName = val + '-' +userName
      this.queryParams.empId = val
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    /** 呈送按钮 */
    handleSend() {
      this.sendForm = {}
      this.sendForm = this.form
      this.sendForm.keyWorkItemsList = this.keyWorkItemsList
      this.sendForm.kpiList = this.kpiList
      this.sendForm.performanceScoreList = this.performanceScoreList
      this.sendForm.finalSupervisor = this.finalSupervisor
      sendSelfAppr(this.sendForm).then(res =>{
        this.getScheduleList();
      })
    },
    selfEvaluationSummary() {
      this.summarizeForm = this.form
      this.open1 = true
      this.title1 = "自评总结";
    },
    supervisorEvaluation() {
      this.supervisorEvaluationForm = this.form
      this.supervisorEvaluationForm.keyWorkItemsList = this.keyWorkItemsList
      this.supervisorEvaluationForm.kpiList = this.kpiList
      this.supervisorEvaluationForm.performanceScoreList = this.performanceScoreList
      this.open2 = true
      this.title2 = "主管考评";
    },
    handleConfirm(){
      this.confirmForm.id = this.form.id
      confirmPersonalPerformance(this.confirmForm).then(res =>{
        this.getScheduleList();
        this.$modal.msgSuccess("确认成功");
      })
    }
  }
};
</script>
<style scoped>
.divFather{
  display: flex;
  justify-content: flex-end;
  width: 100%
}
.page3_div{
  background-color: #f7f7f7;
  width: 100%;
  height: 300px;
  display: flex;
  flex-direction:row;
}
.page4_div{
  background-color: #f7f7f7;
  width: 100%;
  height: 100px;
  display: flex;
  flex-direction:row;
}
.page3_div_left{
  display: flex;
  flex-direction:row;
  justify-content:center;
  align-items: center;
  width: 40px;
  border-style: solid;
  border-color: #FFFFFF;
  border-width:3px ;
  writing-mode:vertical-lr;
}
</style>
