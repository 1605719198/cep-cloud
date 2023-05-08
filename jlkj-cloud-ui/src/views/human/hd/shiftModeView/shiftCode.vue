<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="轮班方式" prop="shiftmodeId">
        <el-select v-model="queryParams.shiftmodeId" placeholder="请选择轮班方式" clearable>
          <el-option
            v-for="dict in modeList"
            :key="dict.id"
            :label="dict.turnTypeName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['human:shiftCode:add']"
          >新增</el-button>
      </el-form-item>
    </el-form>
<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button type="primary"-->
<!--                   plain-->
<!--                   icon="el-icon-plus"-->
<!--                   size="mini"-->
<!--                   @click="handleAdd"-->
<!--                   v-hasPermi="['human:shiftCode:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->
    <el-table v-loading="loading" :data="shiftCodeList" @selection-change="handleSelectionChange">
      <el-table-column label="班次编码" align="center" prop="shiftCode" />
      <el-table-column label="班次开始时间_时" align="center" prop="startHour" />
      <el-table-column label="班次工作时间_时" align="center" prop="conHour" />
      <el-table-column label="是否中午打卡" align="center" prop="isNoon">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isNoon"/>
        </template>
      </el-table-column>
      <el-table-column label="中间休息次数" align="center" prop="restCount" />
      <el-table-column label="大小夜" align="center" prop="bigSmaNight">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.bigSmaNight"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="description" />
      <el-table-column label="一次休息时长" align="center" prop="restConMin" />
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:shiftCode:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:shiftCode:remove']"
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

    <!-- 添加或修改班次数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1400px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="轮班方式" prop="shiftmodeId">
              <el-select v-model="form.shiftmodeId" placeholder="请选择轮班方式">
                <el-option
                  v-for="dict in modeList"
                  :key="dict.id"
                  :label="dict.turnTypeName"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="班次编码" prop="shiftCode">
              <el-input v-model="form.shiftCode" placeholder="请输入班次编码" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="班次开始时间" prop="startTime">
              <el-input v-model="form.startHour"  style="width: 35%" @blur="inputOnblur(0)" />
              时
              <el-input v-model="form.startMin"  style="width: 35%" @blur="inputOnblur(0)"    />
              分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="班次工作时间" prop="conTime">
              <el-input v-model="form.conHour" style="width: 35%" @blur="inputOnblur(0)"  />
              时
              <el-input v-model="form.conMin" style="width: 35%" @blur="inputOnblur(0)"  />
              分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="班次结束时间" prop="endTime">
              <el-input v-model="form.endTime" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="中间休息次数" prop="restCount">
              <el-select v-model="form.restCount" placeholder="请选择次数">
                <el-option
                  v-for="dict in attendenceOptions.restCount"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="中间/中午休息开始时间" prop="restStartTime">
              <el-input v-model="form.restStartHour"  style="width: 35%" @blur="inputOnblur(1)"/>
              时
              <el-input v-model="form.restStartMin"  style="width: 35%" @blur="inputOnblur(1)" />
              分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="休息时间" prop="restConMin">
              <el-input v-model="form.restConMin" style="width: 50%" @blur="inputOnblur(1)" type="number"/>分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="休息结束时间" prop="restEndTime">
              <el-input v-model="form.restEndTime" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="this.form.restCount==2||this.form.restCount==3">
          <el-col :span="6">
            <el-form-item>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="中间/中午休息开始时间" prop="restStartTime2">
              <el-input v-model="form.restStartHour2"  style="width: 35%" @blur="inputOnblur(2)" />
              时
              <el-input v-model="form.restStartMin2"  style="width: 35%" @blur="inputOnblur(2)" />
              分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="休息时间" prop="restConMin2">
              <el-input v-model="form.restConMin2" style="width: 50%" @blur="inputOnblur(2)"/>分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="休息结束时间" prop="restEndTime2">
              <el-input v-model="form.restEndTime2" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="this.form.restCount==3">
          <el-col :span="6">
            <el-form-item>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="中间/中午休息开始时间" prop="restStartTime3">
              <el-input v-model="form.restStartHour3"  style="width: 35%" @blur="inputOnblur(3)" />
              时
              <el-input v-model="form.restStartMin3"  style="width: 35%" @blur="inputOnblur(3)" />
              分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="休息时间" prop="restConMin3">
              <el-input v-model="form.restConMin3" style="width: 50%" @blur="inputOnblur(3)"/>分
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="休息结束时间" prop="restEndTime3">
              <el-input v-model="form.restEndTime3" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="说明" prop="description">
          <el-input type="textarea" v-model="form.description" placeholder="请输入说明" :autosize="{minRows:2}" />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="大小夜" prop="bigSmaNight">
              <el-select v-model="form.bigSmaNight" placeholder="请选择大小夜">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            {{form.creatorId}}-{{form.creator}}
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listShiftCode, getShiftCode, delShiftCode, addShiftCode, updateShiftCode , getShiftCodeByPerson} from "@/api/human/hd/shiftCode";
import { getDateTime } from "@/api/human/hd/ahumanUtils"
import { getAttendenceOptions } from "@/api/human/hd/attendenceBasis";
export default {
  name: "ShiftCode",
  dicts: ['sys_yes_no', 'sys_normal_disable'],
  data() {
    return {
      //出勤选单类型查询
      attendenceOptionType:{
        id:'',
        optionsType:['restCount']
      },
      //出勤选单选项列表
      attendenceOptions:{},
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      //轮班方式列表数据
      modeList:[],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 班次数据表格数据
      shiftCodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        shiftmodeId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shiftCode: [
          { required: true, message: "班次编码不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, validator: this.validateStartTime, trigger: "blur" }
        ],
        conTime: [
          { required: true, validator: this.validateConTime, trigger: "blur" }
        ],
        endTime:[
          { required: true, validator: this.validateYes, trigger: "blur" }
        ],
        // status: [
        //   { required: true, message: "状态不能为空", trigger: "change" }
        // ],
        description: [
          { required: true, message: "说明不能为空", trigger: "blur" }
        ],
        restStartTime: [
          { required: true, validator: this.validateRestStartTime, trigger: "blur" }
        ],
        restStartTime2: [
          { required: true, validator: this.validateRestStartTime2, trigger: "blur" }
        ],
        restStartTime3: [
          { required: true, validator: this.validateRestStartTime3, trigger: "blur" }
        ],
        restEndTime: [
          { required: true, validator: this.validateYes, trigger: "blur" }
        ],
        restEndTime2: [
          { required: true, validator: this.validateYes, trigger: "blur" }
        ],
        restEndTime3: [
          { required: true, validator: this.validateYes, trigger: "blur" }
        ],
        restConMin: [
          { required: true, validator: this.validateRestConMin, trigger: "blur" }
        ],
        restConMin2: [
          { required: true, validator: this.validateRestConMin, trigger: "blur" }
        ],
        restConMin3: [
          { required: true, validator: this.validateRestConMin, trigger: "blur" }
        ],
      }
    };
  },
  methods: {
    //获取出勤字典
    getDisc(){
      getAttendenceOptions(this.attendenceOptionType).then(response=> {
        this.attendenceOptions=response.data
      })
    },
    //开始休息时间失去焦点事件
    inputOnblur(e) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.loading = true;
            this.loading = false;
          }
        }
      });
    },
    //页面初始化
    init(modeData) {
      this.getDisc();
      this.modeList = modeData.modeList;
      this.queryParams.compId = modeData.compId;
      this.getList();
    },
    //设置表单值
    setForm(e){
      this.form.creator = this.nickName;
      this.form.creatorId = this.userEmpId;
      this.form.createDate = getDateTime(1);
      this.form.compId = this.queryParams.compId;
      if(e==0){
        this.form.restCount = 1;
        this.form.status = '0';
        this.form.shiftmodeId = this.queryParams.shiftmodeId;
      }
    },
    /** 查询班次数据列表 */
    getList() {
      this.loading = true;
      listShiftCode(this.queryParams).then(response => {
        this.shiftCodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        shiftmodeId: null,
        shiftCode: null,
        startHour: null,
        startMin: null,
        startTime: null,
        conHour: null,
        conMin: null,
        conTime: null,
        endHour: null,
        endMin: null,
        endTime: null,
        isNoon: null,
        restCount: null,
        bigSmaNight: null,
        status: null,
        description: null,
        isUsed: null,
        restStartHour: null,
        restStartMin: null,
        restStartTime: null,
        restConMin: null,
        restEndHour: null,
        restEndMin: null,
        restEndTime:null,
        restStartHour2: null,
        restStartMin2: null,
        restStartTime2: null,
        restConMin2: null,
        restEndHour2: null,
        restEndMin2: null,
        restEndTime2: null,
        restStartHour3: null,
        restStartMin3: null,
        restStartTime3: null,
        restConMin3: null,
        restEndHour3: null,
        restEndMin3: null,
        restEndTime3: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.setForm(0);
      this.open = true;
      this.title = "添加班次数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShiftCode(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改班次数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.setForm(1)
            updateShiftCode(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShiftCode(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除班次数据编号为"' + ids + '"的数据项？').then(function() {
        return delShiftCode(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //设置班次结束时间
    setEndTime(){
      if(this.form.startTime!=null&&this.form.conTime!=null){
        var startHour = parseInt(this.form.startHour)
        var startMin = parseInt(this.form.startMin)
        var conHour = parseInt(this.form.conHour)
        var conMin = parseInt(this.form.conMin)
        var restConMin = parseInt(this.form.restConMin)
        var restMin = 0
        if(this.judge(this.form.restConMin)){
          var restConMin = parseInt(this.form.restConMin);
          restMin += restConMin
        }
        if(this.form.restCount >1){
          if(this.judge(this.form.restConMin2))
          var restConMin2 = parseInt(this.form.restConMin2)
          restMin+=restConMin2;
          if(this.form.restCount >2){
            if(this.judge(this.form.restConMin3)){
              var restConMin3 = parseInt(this.form.restConMin3)
              restMin+=restConMin3;
            }
          }
        }
        var plusHour = Math.floor((restMin+conMin+startMin)/60)
        var min = (restMin+conMin+startMin)%60
        var hour = ((startHour+conHour+plusHour)>23)? (startHour+conHour+plusHour)-24:(startHour+conHour+plusHour);
        var finalMin = (min>59)? min-60:min
        var finalHour = (hour>23)? hour-24:hour
        this.form.endHour = finalHour;
        this.form.endMin = finalMin;
        finalMin = (finalMin<10)? '0'+finalMin:finalMin
        finalHour = (finalHour<10)? '0'+finalHour:finalHour
        var time =finalHour+':'+finalMin
        this.form.endTime = time;
      }
    },
    //设置休息结束时间
    setRestEndTime(e){
      switch (e){
        case 1:
          var hour = parseInt(this.form.restStartHour);
          var min =  parseInt(this.form.restStartMin);
          var con = parseInt(this.form.restConMin)
          break;
        case 2:
          var hour = parseInt(this.form.restStartHour2);
          var min =  parseInt(this.form.restStartMin2);
          var con = parseInt(this.form.restConMin2)
          break;
        case 3:
          var hour = parseInt(this.form.restStartHour3);
          var min =  parseInt(this.form.restStartMin3);
          var con = parseInt(this.form.restConMin3)
          break;
      }
      var plusHour = ((min+con)>59)? Math.floor((min+con)/60):0
      var min2 = ((con+min)>59)? (con+min)%60:(con+min)
      var hour2;
      var finalHour ;
      if(hour +plusHour>23){
        hour2 =(hour +plusHour)-24
      }else{
        hour2 = (hour +plusHour)
      }
      var finalHour = (hour2<10)? '0'+hour2:hour2
      var finalMin = (min2<10)? '0'+min2:min2

      switch (e){
        case 1:
          this.form.restEndHour = hour2;
          this.form.restEndMin = min2;
          this.form.restEndTime =finalHour+':'+finalMin
          break;
        case 2:
          this.form.restEndHour2 = hour2;
          this.form.restEndMin2 = min2;
          this.form.restEndTime2 =finalHour+':'+finalMin
          break;
        case 3:
          this.form.restEndHour3 = hour2;
          this.form.restEndMin3 = min2;
          this.form.restEndTime3 =finalHour+':'+finalMin
          break;
      }
    },
    //输入框校验
    validateYes(rule, value, callback){
      callback();
    },
    validateStartTime(rule, value, callback) {
      var numberTest = /^[0-9]*$/
      var hour = this.form.startHour;
      var min =this.form.startMin
      if (!hour){
        this.form.startTime = null,
        callback(new Error('班次开始时间_时不可为空'))
      }else if (!numberTest.test(hour)) {
        this.form.startTime = null,
        callback(new Error('请输入纯数字小时数'))
      }else if(hour>23||this.form.startHour<0){
        this.form.startTime = null,
        callback(new Error('请输入0-23之间的小时值'))
      }else if (!min){
        this.form.startTime = null,
        callback(new Error('班次开始时间_分不可为空'))
      }else if (!numberTest.test(min)) {
        this.form.startTime = null,
        callback(new Error('请输入纯数字分钟数'))
      }else if(min>60||min<0){
        this.form.startTime = null,
        callback(new Error('请输入0-59之间的分钟值'))
      }else {
        callback();
        this.form.startTime = 1;
        this.setEndTime();
      }
    },
    validateConTime(rule, value, callback) {
      var numberTest = /^[0-9]*$/
      var hour = this.form.conHour;
      var min =this.form.conMin
      if (!hour){
        this.form.conTime = null;
        callback(new Error('班次开始时间_时不可为空'))
      }else if (!numberTest.test(hour)) {
        this.form.conTime = null;
        callback(new Error('请输入纯数字小时数'))
      }else if(hour>23||this.form.startHour<0){
        this.form.conTime = null;
        callback(new Error('请输入0-23之间的小时值'))
      }else if (!min){
        this.form.conTime = null;
        callback(new Error('班次开始时间_分不可为空'))
      }else if (!numberTest.test(min)) {
        this.form.conTime = null;
        callback(new Error('请输入纯数字分钟数'))
      }else if(min>60||min<0){
        this.form.conTime = null;
        callback(new Error('请输入0-59之间的分钟值'))
      }else{
        callback();
        this.form.conTime = 1;
        this.setEndTime();
      }
    },
    validateRestStartTime(rule, value, callback) {
      var numberTest = /^[0-9]*$/
      var restHour = this.form.restStartHour;
      var restMin =this.form.restStartMin;
      if (!restHour){
        this.form.restStartTime = null,
        callback(new Error('休息开始时间_时不可为空'))
      }else if(!numberTest.test(restHour)){
        this.form.restStartTime = null,
        callback(new Error('请输入纯数字小时数'))
      }else if(restHour>23||restHour<0){
        this.form.restStartTime = null,
        callback(new Error('请输入0-23之间的小时值'))
      }else if(!restMin){
        this.form.restStartTime = null,
        callback(new Error('休息开始时间_分不可为空'))
      }else if(!numberTest.test(restMin)){
        this.form.restStartTime = null,
        callback(new Error('请输入纯数字分钟数'))
      }else if(restMin>59||restMin<0){
        this.form.restStartTime = null,
        callback(new Error('请输入0-59之间的分钟值'))
      }else{
        callback();
        var hour = (restHour<10)? ('0'+restHour):restHour
        var min = (restMin<10)?('0'+restMin):restMin
        this.form.restStartTime =hour+':'+min
        if(this.judge(this.form.restConMin)){
          this.setRestEndTime(1);
        }
      }
    },
    validateRestStartTime2(rule, value, callback) {
      var numberTest = /^[0-9]*$/
      var restHour = this.form.restStartHour2;
      var restMin =this.form.restStartMin2;
      if (!restHour){
        this.form.restStartTime2 = null,
          callback(new Error('休息开始时间_时不可为空'))
      }else if(!numberTest.test(restHour)){
        this.form.restStartTime2 = null,
          callback(new Error('请输入纯数字小时数'))
      }else if(restHour>23||restHour<0){
        this.form.restStartTime2 = null,
          callback(new Error('请输入0-23之间的小时值'))
      }else if(!restMin){
        this.form.restStartTime2 = null,
          callback(new Error('休息开始时间_分不可为空'))
      }else if(!numberTest.test(restMin)){
        this.form.restStartTime2 = null,
          callback(new Error('请输入纯数字分钟数'))
      }else if(restMin>59||restMin<0){
        this.form.restStartTime2 = null,
          callback(new Error('请输入0-59之间的分钟值'))
      }else{
        callback();
        var hour = (restHour<10)? ('0'+restHour):restHour
        var min = (restMin<10)?('0'+restMin):restMin
        this.form.restStartTime2 =hour+':'+min
        if(this.judge(this.form.restConMin2)){
          this.setRestEndTime(2);
        }
      }
    },
    validateRestStartTime3(rule, value, callback) {
      var numberTest = /^[0-9]*$/
      var restHour = this.form.restStartHour3;
      var restMin =this.form.restStartMin3;
      if (!restHour){
        this.form.restStartTime3 = null,
          callback(new Error('休息开始时间_时不可为空'))
      }else if(!numberTest.test(restHour)){
        this.form.restStartTime3 = null,
          callback(new Error('请输入纯数字小时数'))
      }else if(restHour>23||restHour<0){
        this.form.restStartTime3 = null,
          callback(new Error('请输入0-23之间的小时值'))
      }else if(!restMin){
        this.form.restStartTime3 = null,
          callback(new Error('休息开始时间_分不可为空'))
      }else if(!numberTest.test(restMin)){
        this.form.restStartTime3 = null,
          callback(new Error('请输入纯数字分钟数'))
      }else if(restMin>59||restMin<0){
        this.form.restStartTime3 = null,
          callback(new Error('请输入0-59之间的分钟值'))
      }else{
        callback();
        var hour = (restHour<10)? ('0'+restHour):restHour
        var min = (restMin<10)?('0'+restMin):restMin
        this.form.restStartTime3 =hour+':'+min
        if(this.judge(this.form.restConMin3)){
          this.setRestEndTime(3);
        }
      }
    },
    validateRestConMin(rule, value, callback){
      var numberTest = /^[0-9]*$/
      if(!value){
        callback(new Error('休息时间不可为空'))
      }else if(!numberTest.test(value)){
        callback(new Error('请输入纯数字的休息时间'))
      }else {
        callback();
        this.setEndTime();
        if(this.form.restStartTime!=null){
          this.setRestEndTime(1)
        }
        if(this.form.restStartTime2!=null){
          this.setRestEndTime(2)
        }
        if(this.form.restStartTime3!=null){
          this.setRestEndTime(3)
        }
      }
    },
    //输入框数字校验
    judge(e){
      var numberTest = /^[0-9]*$/
      if(e==null||e==''){
        return false;
      }else if(!numberTest.test(e)){
        return false;
      }else if(e<0){
        return false;
      }else{
        return true;
      }
    },
    /**改变公司别 */
    changeCompId(val,list){
      this.queryParams.compId = val;
      this.queryParams.shiftmodeId =null;
      this.modeList = list;
      this.getList();
    },
  }
};
</script>


<style scoped>

</style>
