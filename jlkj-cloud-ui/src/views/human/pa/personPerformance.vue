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
              @pagination="getList"
            />
            <el-table v-loading="loading" :data="personPerformanceList" border @row-click="getPersonPerformance">
              <el-table-column label="年月" width="75" align="center" prop="meritMonth" />
              <el-table-column label="类别" width="50" align="center" prop="meritType">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritType" :value="scope.row.meritType"/>
                </template>
              </el-table-column>
              <el-table-column label="状态" width="95" align="center" prop="meritStatus">
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
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAdd"
                  v-hasPermi="['human:personPerformance:add']"
                >新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  @click="handleDelete"
                  v-hasPermi="['human:personPerformance:remove']"
                >删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-right"
                  size="mini"
                  @click="handleSend"
                  v-hasPermi="['human:personPerformance:send']"
                >呈送</el-button>
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
                    <el-select v-model="form.deptType" disabled>
                      <el-option
                        v-for="dict in baseInfoData.HP020"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="职称" prop="jobTitleId">
                    <el-select v-model="form.jobTitleId" disabled>
                      <el-option
                        v-for="dict in humanJobTitle"
                        :key="dict.jobTitleNo"
                        :label="dict.jobTitleName"
                        :value="dict.jobTitleNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
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
                <el-col :span="1.5">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handlePlan" :disabled="isPoint">添加计划指标</el-button>
                </el-col>
                <el-col :span="1.5" style="margin-left: 3px">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleKpi" :disabled="isKpi">添加KPI指标</el-button>
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
                  <el-table-column label="重点工作内容" align="center" prop="item">
                    <template v-slot="scope">
                      <el-input type="textarea" size="mini" v-model="scope.row.item" placeholder="请输入备注"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="完成标准/目标" align="center" prop="itemGoal">
                    <template v-slot="scope">
                      <el-input type="textarea" size="mini" v-model="scope.row.itemGoal" placeholder="请输入完成标准/目标"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="评价标准" width="150" align="center">
                    <template v-slot="scope">
                      <el-row>
                        <el-col :span="3">
                          <span>S:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldS"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>A:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldA"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>B:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldB"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>C:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldC"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>D:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldD"/>
                        </el-col>
                      </el-row>
                    </template>
                  </el-table-column>
                  <el-table-column label="完成时间" width="150" align="center" prop="completeTime">
                    <template v-slot="scope">
                      <el-date-picker
                        v-model="scope.row.completeTime"
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 100%"
                        size="mini">
                      </el-date-picker>
                    </template>
                  </el-table-column>
                  <el-table-column label="权重%" width="75" align="center" prop="weight">
                    <template v-slot="scope">
                      <el-input size="mini" v-model="scope.row.weight" placeholder="请输入备注"/>
                    </template>
                  </el-table-column>
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
                        icon="el-icon-edit"
                        @click="handleUpdate(scope.row)"
                        v-hasPermi="['human:personPerformance:edit']"
                      >修改
                      </el-button>
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-delete"
                        :disabled="showDelete"
                        @click="handleDeleteDetail(scope.row)"
                        v-hasPermi="['human:personPerformance:remove']"
                      >删除
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
                  <el-table-column label="重点工作内容" align="center" prop="item">
                    <template v-slot="scope">
                      <el-input type="textarea" size="mini" v-model="scope.row.item" placeholder="请输入备注"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="完成标准/目标" align="center" prop="itemGoal">
                    <template v-slot="scope">
                      <el-input type="textarea" size="mini" v-model="scope.row.itemGoal" placeholder="请输入完成标准/目标"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="评价标准" width="150" align="center">
                    <template v-slot="scope">
                      <el-row>
                        <el-col :span="3">
                          <span>S:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldS"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>A:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldA"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>B:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldB"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>C:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldC"/>
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="3">
                          <span>D:</span>
                        </el-col>
                        <el-col :span="21">
                          <el-input type="textarea" size="mini" :rows="1" v-model="scope.row.fieldD"/>
                        </el-col>
                      </el-row>
                    </template>
                  </el-table-column>
                  <el-table-column label="完成时间" width="150" align="center" prop="completeTime">
                    <template v-slot="scope">
                      <el-date-picker
                        v-model="scope.row.completeTime"
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 100%"
                        size="mini">
                      </el-date-picker>
                    </template>
                  </el-table-column>
                  <el-table-column label="权重%" width="75" align="center" prop="weight">
                    <template v-slot="scope">
                      <el-input size="mini" v-model="scope.row.weight" placeholder="请输入备注"/>
                    </template>
                  </el-table-column>
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
                        icon="el-icon-edit"
                        @click="handleUpdate(scope.row)"
                        v-hasPermi="['human:personPerformance:edit']"
                      >修改
                      </el-button>
                      <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-delete"
                        :disabled="showDelete"
                        @click="handleDeleteDetail(scope.row)"
                        v-hasPermi="['human:personPerformance:remove']"
                      >删除
                      </el-button>
                    </template>
                  </el-table-column>
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

        <!-- 添加计划指标对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
          <el-form ref="planForm" :model="planForm" label-width="100px">
            <el-form-item label="项次" prop="num">
              <el-input-number v-model="planForm.num" controls-position="right" :min="1" />
            </el-form-item>
            <el-form-item label="重点工作内容" prop="item">
              <el-input type="textarea" v-model="planForm.item" placeholder="请输入指标项目"/>
            </el-form-item>
            <el-form-item label="完成目标/标准" prop="itemGoal">
              <el-input type="textarea" v-model="planForm.itemGoal" placeholder="请输入完成目标/标准"/>
            </el-form-item>
            <el-form-item label="评价标准">
              <el-row>
                <el-col :span="2">
                  <span>S:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="planForm.fieldS"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>A:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="planForm.fieldA"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>B:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="planForm.fieldB"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>C:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="planForm.fieldC"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>D:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="planForm.fieldD"/>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item label="完成时间" prop="completeTime">
                <el-date-picker
                  v-model="planForm.completeTime"
                  value-format="yyyy-MM-dd"
                  type="date">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="权重" prop="weight">
              <el-input v-model="planForm.weight" placeholder="请输入权重" style="width: 51%"><span slot="suffix">%</span></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" v-hasPermi="['human:personPerformance:addDetail']">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 添加KPI指标对话框 -->
        <el-dialog :title="title1" :visible.sync="open1" width="600px" append-to-body>
          <el-form ref="kpiForm" :model="kpiForm" label-width="100px">
            <el-form-item label="项次" prop="num">
              <el-input-number v-model="kpiForm.num" controls-position="right" :min="1" />
            </el-form-item>
            <el-form-item label="重点工作内容" prop="item">
              <el-input type="textarea" v-model="kpiForm.item" placeholder="请输入指标项目"/>
            </el-form-item>
            <el-form-item label="完成目标/标准" prop="itemGoal">
              <el-input type="textarea" v-model="kpiForm.itemGoal" placeholder="请输入完成目标/标准"/>
            </el-form-item>
            <el-form-item label="评价标准">
              <el-row>
                <el-col :span="2">
                  <span>S:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="kpiForm.fieldS"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>A:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="kpiForm.fieldA"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>B:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="kpiForm.fieldB"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>C:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="kpiForm.fieldC"/>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="2">
                  <span>D:</span>
                </el-col>
                <el-col :span="22">
                  <el-input type="textarea" size="mini" :rows="1" v-model="kpiForm.fieldD"/>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item label="完成时间" prop="completeTime">
              <el-date-picker
                v-model="kpiForm.completeTime"
                value-format="yyyy-MM-dd"
                type="date">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="权重" prop="weight">
              <el-input v-model="kpiForm.weight" placeholder="请输入权重" style="width: 51%"><span slot="suffix">%</span></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitKpiForm" v-hasPermi="['human:personPerformance:addDetail']">确 定</el-button>
            <el-button @click="cancel1">取 消</el-button>
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
  addPersonPerformance,
  addPersonPerformanceDetail,
  addPersonPerformanceKpiDetail,
  delPersonPerformance, delPersonPerformanceDetail,
  listPersonPerformance,
  listPersonPerformanceDetail,
  listPersonPerformanceSchedule,
  sendPersonPerformance,
  updatePersonPerformanceDetail
} from "@/api/human/pa/personPerformance";
import {queryInfo} from "@/api/human/hm/personnelBasicInfo";
import {getPostMaintenance} from "@/api/human/hp/postMaintenance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {listJobTitleidname} from "@/api/human/hp/jobTitle";
import {getBaseInfo} from "@/api/human/hm/baseInfo";

export default {
  name: "PersonPerformance",
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
      // 计划指标弹出层标题
      title: "",
      // KPI指标弹出层标题
      title1: "",
      // 是否显示计划弹出层
      open: false,
      // 是否显示KPI弹出层
      open1: false,
      // 删除按钮控制
      showDelete: true,
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
      // 计划指标表单参数
      planForm: {},
      // KPI指标表单参数
      kpiForm: {},
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
      isPoint: true,
      isKpi: true,
      //职位名称id列表
      humanJobTitle:[],
      //呈送表单数据
      sendForm:{}
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
      listPersonPerformanceSchedule(this.form).then(res =>{
        this.scheduleList = res.data.rows;
        for (const item of this.scheduleList) {
          if (item.apprEmp) {
            item.apprEmp = item.apprEmp + '-' + item.apprName
          }
          if (item.num === '1' && item.apprStatus === '0'){
            this.showDelete = false
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
    // 表单重置
    reset() {
      this.planForm = {
        num: null,
        item: null,
        itemGoal: null,
        fieldS: null,
        fieldA: null,
        fieldB: null,
        fieldC: null,
        fieldD: null,
        completeTime: null,
        weight: null
      };
      this.kpiForm = {
        num: null,
        item: null,
        itemGoal: null,
        fieldS: null,
        fieldA: null,
        fieldB: null,
        fieldC: null,
        fieldD: null,
        completeTime: null,
        weight: null
      };
      this.resetForm("planForm");
      this.resetForm("kpiForm");
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
    /** 添加计划指标按钮操作 */
    handlePlan() {
      if (this.form.creator === null) {
        this.$modal.msgSuccess("请点击左侧列表选择所要操作的数据");
      } else {
        this.reset();
        this.open = true;
        this.title = "添加计划指标";
      }
    },
    /** 添加KPI指标按钮操作 */
    handleKpi() {
      if (this.form.creator === null) {
        this.$modal.msgSuccess("请点击左侧列表选择所要操作的数据");
      } else {
        this.reset();
        this.open1 = true;
        this.title1 = "添加KPI指标";
      }
    },
    /** 重点工作项提交按钮 */
    submitForm() {
      this.$refs["planForm"].validate(valid => {
        if (valid) {
          this.planForm.personPerformanceId = this.form.id
          this.planForm.compId = this.form.compId
          addPersonPerformanceDetail(this.planForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getDetailList();
          });
        }
      });
    },
    /** KPI指标提交按钮 */
    submitKpiForm() {
      this.$refs["kpiForm"].validate(valid => {
        if (valid) {
          this.kpiForm.personPerformanceId = this.form.id
          this.kpiForm.compId = this.form.compId
          addPersonPerformanceKpiDetail(this.kpiForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open1 = false;
            this.getDetailList();
          });
        }
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
      })
      this.isPoint = this.form.isPoint !== '0';
      this.isKpi = this.form.isKpi !== '0';
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
      this.sendForm.id = this.form.id
      this.sendForm.keyWorkItemsList = this.keyWorkItemsList
      this.sendForm.kpiList = this.kpiList
      sendPersonPerformance(this.sendForm).then(res =>{
        this.getScheduleList();
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
