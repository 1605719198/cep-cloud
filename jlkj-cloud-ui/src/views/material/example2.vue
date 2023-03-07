<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item label="日期" prop="attendDate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.attendDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工号" prop="jobNumber">
        <el-input
          v-model="queryParams.jobNumber"
          placeholder="请输入工号"
          maxlength="8"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出勤情况" prop="isAttend">
        <el-select v-model="queryParams.isAttend" placeholder="请选择出勤情况" clearable size="small">
          <el-option
            v-for="dict in dict.type.dailyreport_attendance_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['listMaterialsWeighingPlan']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dailyreport:attendances:add']"
        >新增</el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['dailyreport:attendances:edit']"-->
      <!--        >修改</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dailyreport:attendances:remove']"
        >删除</el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['dailyreport:attendances:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attendancesList" @sort-change="handleSort" height="67vh">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日期" align="center" prop="attendDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.attendDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工号" align="center" prop="jobNumber" />
      <!--      <el-table-column label="姓名" align="center" prop="jobName" />-->
      <el-table-column label="出勤情况" align="center" prop="isAttend">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dailyreport_attendance_type" :value="scope.row.isAttend"/>
        </template>
      </el-table-column>
      <el-table-column label="出勤时间" align="center" prop="attendTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.attendTime, '{h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="出勤异常描述" align="center" prop="unattendDescription" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleView(scope.row)"
            v-hasPermi="['dailyreport:attendances:edit']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dailyreport:attendances:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dailyreport:attendances:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 日报系统-出勤数据详情对话框 -->
    <el-dialog :title="title" :visible.sync="viewopen" width="450px" append-to-body>
      <el-form ref="form" :model="form"  label-width="80px">
        <el-form-item label="日期:" prop="attendDate" label-width="110px">
          {{form.attendDate}}
        </el-form-item>
        <el-form-item label="工号:" prop="jobNumber" label-width="110px">
          {{form.jobNumber}}
        </el-form-item>
        <el-form-item label="出勤情况:" prop="isAttend" label-width="110px">
          {{form.isAttend}}
        </el-form-item>
        <el-form-item label="出勤时间:" prop="attendTime" label-width="110px" v-if="!isattend" disabled>
          {{form.attendTime}}
        </el-form-item>
        <el-form-item label="出勤异常描述:" prop="unattendDescription" label-width="110px" v-if="isattend2">
          {{form.unattendDescription}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="viewopen = false">确 定</el-button>
        <el-button @click="viewopen = false">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"></select-user>
  </div>
</template>
