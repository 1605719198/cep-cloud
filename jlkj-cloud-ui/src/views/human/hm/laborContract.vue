<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in dict.type.comp_id"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工号">
            <el-input
              v-model="queryParams.empNo"
              placeholder="请输入工号"
              disabled
            >
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="mini"
                       type="primary"
                       @click="getList"
                       icon="el-icon-search">搜 索</el-button>
            <el-button size="mini"
                       type="default"
                       @click="handleEmpty"
                       icon="el-icon-refresh-left">重 置</el-button>
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
              v-hasPermi="['human:laborContract:add']"
            >新增</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="contractList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="合同起始日期" align="center" prop="startDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更原因" align="center" prop="chaReaId">
            <template v-slot="scope">
              <dict-tag-human :options="baseInfoData.changeReason" :value="scope.row.chaReaId"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:laborContract:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:laborContract:remove']"
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

        <!-- 添加或修改人员劳动合同对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="140px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="工号" prop="empNo">
                  <el-input v-model="form.empNo" placeholder="请输入工号" disabled>
                    <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="姓名" prop="empName" v-if="bol">
                  {{form.empName}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所在机构" prop="deptName">
                  {{form.deptName}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="劳动关系状态" prop="relStatus">
                  <el-select v-model="form.relStatus">
                    <el-option
                      v-for="dict in baseInfoData.RelationStatus"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同编号" prop="contractNo">
                  <el-input v-model="form.contractNo" placeholder="请输入合同编号" style="width: 220px"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="签订合同日期" prop="signDate">
                  <el-date-picker clearable
                                  v-model="form.signDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择签订合同日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="合同起始日期" prop="startDate">
                  <el-date-picker clearable
                                  v-model="form.startDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择合同起始日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="入企时间" prop="enterDate">
                  <el-date-picker clearable
                                  v-model="form.enterDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择入企时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同到期日期" prop="endDate">
                  <el-date-picker clearable
                                  v-model="form.endDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择合同到期日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="长期工作地" prop="workAddrId">
                  <el-select v-model="form.workAddrId">
                    <el-option
                      v-for="dict in baseInfoData.EncourageType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="用工性质" prop="empFeaId">
                  <el-select v-model="form.empFeaId">
                    <el-option
                      v-for="dict in baseInfoData.HP006"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职工身份" prop="empIdeId">
                  <el-select v-model="form.empIdeId">
                    <el-option
                      v-for="dict in baseInfoData.employeeIdentity"
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
                <el-form-item label="劳动关系类型" prop="relTypeId">
                  <el-select v-model="form.relTypeId">
                    <el-option
                      v-for="dict in baseInfoData.laborRelationType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="离职日期" prop="leaveEffectDate">
                  <el-date-picker clearable
                                  v-model="form.leaveEffectDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择离职日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="试用期开始日期" prop="probStartDate">
                  <el-date-picker clearable
                                  v-model="form.probStartDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择试用期开始日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="试用期限" prop="probPeriId">
                  <el-select v-model="form.probPeriId">
                    <el-option
                      v-for="dict in baseInfoData.probationPeriod"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="试用期结束日期" prop="probEndDate">
                  <el-date-picker clearable
                                  v-model="form.probEndDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择试用期结束日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="是否试用期" prop="ifProb">
                  <el-radio-group v-model="form.ifProb">
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="变更原因" prop="chaReaId">
                  <el-select v-model="form.chaReaId">
                    <el-option
                      v-for="dict in baseInfoData.changeReason"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="变更原因补充说明" prop="chaReaDesc">
                  <el-input v-model="form.chaReaDesc" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="协议类型" prop="protType1Id">
                  <el-select v-model="form.protType1Id">
                    <el-option
                      v-for="dict in baseInfoData.agreementType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="协议补充说明" prop="protDesc1">
                  <el-input v-model="form.protDesc1" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="协议类型" prop="protType2Id">
                  <el-select v-model="form.protType2Id">
                    <el-option
                      v-for="dict in baseInfoData.agreementType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="协议补充说明" prop="protDesc2">
                  <el-input v-model="form.protDesc2" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="协议类型" prop="protType3Id">
                  <el-select v-model="form.protType3Id">
                    <el-option
                      v-for="dict in baseInfoData.agreementType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="协议补充说明" prop="protDesc3">
                  <el-input v-model="form.protDesc3" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="解除合同的有关说明" prop="releseDesc">
                  <el-input v-model="form.releseDesc" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="更新人员" prop="creator">
                  {{form.creator}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="更新时间" prop="createDate">
                  {{form.createDate}}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listLaborContract, getLaborContract, delLaborContract, addLaborContract, updateLaborContract } from "@/api/human/hm/laborContract";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import {getBaseInfo} from "@/api/human/hm/baseInfo";

export default {
  name: "laborContract",
  dicts: ['comp_id', 'sys_yes_no'],
  components: {selectUser,DictTagHuman},
  data() {
    return {
      // 遮罩层
      loading: false,
      bol: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员劳动合同表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        relStatus: [
          { required: true, message: "劳动关系状态不能为空", trigger: "change" }
        ],
        enterDate: [
          { required: true, message: "入企时间不能为空", trigger: "blur" }
        ],
        relTypeId: [
          { required: true, message: "劳动关系类型不能为空", trigger: "change" }
        ],
        ifProb: [
          { required: true, message: "是否试用期不能为空", trigger: "change" }
        ],
        chaReaId: [
          { required: true, message: "变更原因不能为空", trigger: "change" }
        ],
      },
      //选单数据
      baseInfoData: [],
      baseInfo: {
        baseInfoList: [
          'RelationStatus',
          'HP006',
          'employeeIdentity',
          'laborRelationType',
          'probationPeriod',
          'changeReason',
          'agreementType']
      },
    };
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  watch: {

  },
  methods: {
    /** 查询人员劳动合同列表 */
    getList() {
      this.loading = true;
      listLaborContract(this.queryParams).then(response => {
        this.contractList = response.data.rows;
        this.total = response.data.total;
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
        uuid: null,
        compId: null,
        empNo: null,
        empId: null,
        relStatus: null,
        empName: null,
        contractNo: null,
        signDate: null,
        startDate: null,
        enterDate: null,
        endDate: null,
        workAddrId: null,
        empFeaId: null,
        empIdeId: null,
        relTypeId: null,
        leaveEffectDate: null,
        probStartDate: null,
        probPeriId: null,
        probEndDate: null,
        ifProb: null,
        chaReaId: null,
        chaReaDesc: null,
        protType1Id: null,
        protDesc1: null,
        protType2Id: null,
        protDesc2: null,
        protType3Id: null,
        protDesc3: null,
        releseDesc: null,
        versionNo: null,
        creator: null,
        creatorId: null,
        createDate: null,
        alternateField1: null,
        alternateField2: null,
        alternateField3: null,
        alternateField4: null
      };
      this.resetForm("form");
    },
    /** 重置按钮操作 */
    handleEmpty() {
      this.queryParams = {
        compId: undefined,
        empNo: undefined
      }
      this.getList()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增劳动合同资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid;
      this.bol = false
      getLaborContract(uuid).then(response => {
        queryNewPostNameAndChangeDetail(this.queryParams).then(res => {
          this.form.deptName = res.data.list1[0].newPostName
          this.$nextTick(() => {
            this.bol = true
          })
        })
        this.form = response.data;
        this.open = true;
        this.title = "修改劳动合同资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateLaborContract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLaborContract(this.form).then(response => {
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
      const uuids = row.uuid;
      this.$modal.confirm('是否确认删除人员劳动合同编号为"' + uuids + '"的数据项？').then(function() {
        return delLaborContract(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val, userName) {
      this.queryParams.empNo = val
      this.form.empNo = val
      this.form.empName = userName
      this.bol = false
      this.getList();
      queryNewPostNameAndChangeDetail(this.form).then(res => {
        this.form.deptName = res.data.list1[0].newPostName
        this.$nextTick(() => {
          this.bol = true
        })
      })
    },
  }
};
</script>
<style scoped>
.el-input-group {
  width: 220px;
}
</style>
