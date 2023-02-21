<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input placeholder="输入关键字进行过滤" clearable size="small" prefix-icon="el-icon-search" v-model="filterText" style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree class="filter-tree"
                   :data="combineTreeData"
                   node-key="id"
                   :default-expanded-keys="defaultKeys"
                   :props="defaultProps"
                   :filter-node-method="filterNode"
                   :expand-on-click-node="false"
                   ref="tree"
                   default-expand-all
                   highlight-current
                   @node-click="handleNodeClick">
          </el-tree>
        </div>
      </el-col>

      <el-col :span="20" :xs="24">

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button plain size="mini" icon="el-icon-plus" type="primary" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button plain size="mini" type="danger" icon="el-icon-delete" @click="handleDelete">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="text" icon="el-icon-info" @click="handleAdvanceFilter">高级筛选</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="tableData" stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="60" align="center" />
          <el-table-column label="设备位置" width="200" prop="equipmentLocation" :show-overflow-tooltip="true" align="center" fixed>
            <template slot-scope="scope">
              <treeselect :options="treeData" disabled :normalizer="normalizer" :value="scope.row.equipmentLocation" />
            </template>
          </el-table-column>
          <template v-for="column in columns">
            <el-table-column :key="column.prop"
                             :fixed="column.fixed"
                             :prop="column.prop"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :align="column.align">
            </el-table-column>
          </template>
          <el-table-column label="第一责任人" prop="firstResponsiblePerson" width="100" :show-overflow-tooltip="true" />
          <el-table-column label="操作" header-align="center" align="center" fixed="right" width="280">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.page"
          :limit.sync="queryParams.limit"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!--    添加或修改弹框-->
<!--    <div v-if="open">-->
      <el-dialog :title="title"
                 class="customDialogStyle"
                 :visible.sync="open"
                 width="1200px"
                 append-to-body
                 :close-on-click-modal="false">
        <el-collapse v-model="activeNames">
          <el-collapse-item name="1">
            <template slot="title">
              基本信息<i class="header-icon el-icon-s-order"></i>
            </template>
            <el-form ref="baseForm"
                     :model="baseForm"
                     :rules="baseFormRules"
                     label-width="120px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="设备位置"
                                prop="equipmentLocation">
                    <treeselect v-model="baseForm.equipmentLocation"
                                noOptionsText="暂无数据"
                                :options="locationOptions"
                                :normalizer="normalizer"
                                placeholder="选择设备位置"
                                @select="selectLocationTree" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="上级设备"
                                prop="upperLevel">
                    <treeselect ref="equipmentTree"
                                v-model="baseForm.upperLevel"
                                noOptionsText="暂无数据"
                                :options="equipmentTreeData"
                                :normalizer="normalizer_1"
                                placeholder="选择上级设备"
                                @select="selectUpperEquipmentTree" />
                    <!--                    <el-input v-model="baseForm.upperLevel" placeholder="请选择上级设备" />-->
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="设备类型"
                                prop="equipmentType">
                    <el-select v-model="baseForm.equipmentType"
                               placeholder="请选择设备类型"
                               @change="equipmentTypeSelect"
                               style="width: 253px;">
                      <el-option v-for="item in dict.type.equipment_type"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="{ value: item.value, label: item.label }">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="设备专业"
                                prop="equipmentProfessionNumber">
                    <el-select v-model="baseForm.equipmentProfessionNumber"
                               placeholder="请选择设备专业"
                               @change="equipmentProfessionSelect"
                               style="width: 253px;">
                      <el-option v-for="item in dict.type.equipment_profession_type"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="{ value: item.value, label: item.label }">
                      </el-option>
                    </el-select>
                    <!--                    <el-input v-model="baseForm.equipmentProfessionNumber" placeholder="请选择设备专业" />-->
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="设备名称"
                                prop="equipmentName">
                    <el-input v-model="baseForm.equipmentName"
                              placeholder="请输入设备名称" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="设备编码"
                                prop="equipmentNumbert">
                    <el-input v-model="baseForm.equipmentNumbert"
                              placeholder="请输入设备编码" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="油品类别"
                                prop="oilTypeId">
                    <el-select v-model="baseForm.oilTypeId"
                               placeholder="请选择油品类别"
                               @change="oilTypeSelect"
                               style="width: 253px;">
                      <el-option v-for="item in dict.type.equipment_oil_category"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="{ value: item.value, label: item.label, type: item.raw.field1 }">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="油品"
                                prop="oilName">
                    <el-select v-model="baseForm.oilName"
                               placeholder="请选择油品"
                               @change="oilNameSelect"
                               style="width: 253px;">
                      <el-option v-for="item in oilNameArr"
                                 :key="item.dictValue"
                                 :label="item.dictLabel"
                                 :value="{ value: item.dictValue, label: item.dictLabel }">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="物联设备"
                                prop="internetOfThings">
                    <el-radio-group v-model="baseForm.internetOfThings">
                      <el-radio v-for="dict in dict.type.sys_yes_no"
                                :key="dict.value"
                                :label="dict.value">{{dict.label}}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="第一责任人"
                                prop="responsiblePerson">
                    <el-select v-model="responsiblePerson"
                               multiple
                               filterable
                               placeholder="请选择第一责任人"
                               style="width: 100%;"
                               @change="responsiblePersonChange">
                      <el-option v-for="item in responseUserArr"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="item.label">
                      </el-option>
                    </el-select>
                    <!--                    <el-input v-model="baseForm.firstResponsiblePerson" placeholder="请选择第一责任人" />-->
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-collapse-item>
          <el-collapse-item name="2">
            <template slot="title">
              设备台账<i class="header-icon el-icon-s-grid"></i>
            </template>
            <el-form ref="ledgerForm"
                     :model="ledgerForm"
                     :rules="baseFormRules2"
                     label-width="120px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="供应厂商"
                                prop="supplier">
                    <el-input v-model="ledgerForm.supplier"
                              placeholder="请输入供应厂商" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="规格型号"
                                prop="specificationsAndModels">
                    <el-input v-model="ledgerForm.specificationsAndModels"
                              placeholder="请输入规格型号" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="设备专业编码"
                                prop="equipmentNumbert">
                    <el-input v-model="ledgerForm.equipmentNumbert"
                              placeholder="请输入设备专业编码" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="台账类型"
                                prop="accountType">
                    <el-input v-model="ledgerForm.accountType"
                              placeholder="请输入台账类型" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="使用单位"
                                prop="useUnit">
                    <el-input v-model="ledgerForm.useUnit"
                              placeholder="请输入使用单位" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="使用日期"
                                prop="useDate">
                    <el-date-picker style="width: 253px;"
                                    v-model="ledgerForm.useDate"
                                    type="date"
                                    placeholder="请选择日期"
                                    value-format="yyyy-MM-dd"
                                    format="yyyy-MM-dd"
                                    :picker-options="pickerOptions1"
                                    @change="useDateChange">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="使用部门"
                                prop="useDepartmentId">
                    <el-input v-model="ledgerForm.useDepartmentId"
                              placeholder="请输入使用部门" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="制造厂商"
                                prop="manufacturers">
                    <el-input v-model="ledgerForm.manufacturers"
                              placeholder="请输入制造厂商" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="资产状态"
                                prop="assetStatus">
                    <el-select v-model="ledgerForm.assetStatus"
                               placeholder="请选择资产状态"
                               @change="assetStatusSelect"
                               style="width: 253px;">
                      <el-option v-for="item in dict.type.equipment_assets_state"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="{ value: item.value, label: item.label }">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="设备级别"
                                prop="equipmentLevel">
                    <el-input v-model="ledgerForm.equipmentLevel"
                              placeholder="请输入设备级别" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="成本中心"
                                prop="costCenter">
                    <el-input v-model="ledgerForm.costCenter"
                              placeholder="请输入成本中心" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="设备阶层"
                                prop="equipmentClass">
                    <el-input v-model="ledgerForm.equipmentClass"
                              placeholder="请输入设备阶层" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="购入日期"
                                prop="purchaseDate">
                    <el-date-picker style="width: 253px;"
                                    v-model="ledgerForm.purchaseDate"
                                    type="date"
                                    placeholder="请选择日期"
                                    value-format="yyyy-MM-dd"
                                    format="yyyy-MM-dd"
                                    @change="purchaseDateChange">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="维护单位"
                                prop="maintenanceUnit">
                    <el-input v-model="ledgerForm.maintenanceUnit"
                              placeholder="请输入维护单位" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="产权单位"
                                prop="propertyUnit">
                    <el-input v-model="ledgerForm.propertyUnit"
                              placeholder="请输入产权单位" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="安装日期"
                                prop="installationUnit">
                    <el-date-picker style="width: 253px;"
                                    v-model="ledgerForm.installationUnit"
                                    type="date"
                                    placeholder="请选择日期"
                                    value-format="yyyy-MM-dd"
                                    format="yyyy-MM-dd"
                                    :picker-options="pickerOptions"
                                    @change="installDateChange">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="维护人员"
                                prop="maintenanceUser">
                    <el-input v-model="ledgerForm.maintenanceUser"
                              placeholder="请输入维护人员" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="设备层级"
                                prop="equipmentGrade">
                    <el-input v-model="ledgerForm.equipmentGrade"
                              placeholder="请输入设备层级" />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-collapse-item>
        </el-collapse>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary"
                     @click="submitForm"
                     :loading="states">确 定</el-button>
        </div>
      </el-dialog>
<!--    </div>-->

    <!--   高级筛选抽屉 -->
    <el-drawer class="el-icon-info"
               title="高级筛选"
               :append-to-body="true"
               :before-close="handleClose"
               :visible.sync="drawer"
               direction="rtl"
               custom-class="demo-drawer"
               :wrapperClosable="false">
      <div class="demo-drawer__content">
        <el-form :model="queryParams"
                 ref="queryForm">
          <el-row>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">设备位置:</label>
                  <treeselect v-model="queryParams.equipmentLocation"
                              :options="queryOptions"
                              :normalizer="normalizer"
                              placeholder="选择设备位置"
                              style="width: 160px;display:inline-block" />
                </div>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">设备类型:</label>
                  <el-select v-model="queryParams.equipmentType"
                             placeholder="请选择设备类型"
                             @change="equipmentTypeSelect"
                             style="width: 160px;">
                    <el-option v-for="item in dict.type.equipment_type"
                               :key="item.value"
                               :label="item.label"
                               :value="{ value: item.value, label: item.label }">
                    </el-option>
                  </el-select>
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">设备名称:</label>
                  <el-input v-model="queryParams.equipmentName"
                            type="text"
                            clearable
                            placeholder="请输入设备名称"
                            style="width: 160px" />
                </div>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">设备编码:</label>
                  <el-input v-model="queryParams.equipmentNumbert"
                            type="text"
                            clearable
                            placeholder="请输入设备编码"
                            style="width: 160px" />
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">台账类型:</label>
                  <el-input v-model="queryParams.accountType"
                            type="text"
                            clearable
                            placeholder="请输入台账类型"
                            style="width: 160px" />
                </div>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">制造厂商:</label>
                  <el-input v-model="queryParams.manufacturers"
                            type="text"
                            clearable
                            placeholder="请输入制造厂商"
                            style="width: 160px" />
                </div>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">维护人员:</label>
                  <el-input v-model="queryParams.maintenanceUser"
                            type="text"
                            clearable
                            placeholder="请输入维护人员"
                            style="width: 160px" />
                </div>
              </div>
            </el-col>
            <el-col :span="11">
              <div class="el-form-item el-form-item--small">
                <div class="el-form-item__content">
                  <label class="el-form-item__label"
                         style="
                      width: 100px;
                      line-height: 40px;
                      font-weight: bold;
                      font-size: 14px;
                    ">第一责任人:</label>
                  <el-select v-model="responsiblePersonArr"
                             multiple
                             filterable
                             placeholder="请选择第一责任人"
                             style="width: 160px;"
                             @change="queryPersonChange">
                    <el-option v-for="item in responseUserArr"
                               :key="item.value"
                               :label="item.label"
                               :value="item.label">
                    </el-option>
                  </el-select>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-form>
        <div class="demo-drawer__footer"
             style="text-align: center">
          <el-button v-hasPermi="['roperties_getEquipmentAccountByParams']"
                     size="medium"
                     type="primary"
                     icon="el-icon-search"
                     @click="handleQuery">查 询</el-button>
          <el-button size="medium"
                     type="default"
                     icon="el-icon-refresh-left"
                     @click="handleReset">清 空</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getTreeNode } from '@/api/equip/MG/deviceDomain'
import { getDicts } from "@/api/system/dict/data";
import {
  queryAllDataList,
  addEquipmentBasicProperties,
  deleteAccountData,
  getAccountValueById,
  updateAccountById,
  getEquipmentAccountByParams,
  getEquipmentCombineTree,
  getEquipmentByLocationId,
  getEquipmentBasicNumById
} from "@/api/equip/ME/ledgera";
import { getPersonnerlInfoList } from '@/api/human/HM/humanResourceTree'
import { isEmpty } from "element-ui/src/utils/util";
export default {
  name: "ledgera",
  components: { Treeselect },
  dicts: ["equipment_type", "equipment_profession_type", "equipment_oil_category", "equipment_assets_state", "sys_yes_no"],
  data () {
    return {
      // 第一责任人数据
      responseUserArr: [],
      normalizer (node) {
        if (node.children == null || node.children == 'null' || node.children.length == 0) {
          delete node.children
        }
        return {
          id: node.id,
          label: node.domainNo + "-" + node.domainName,
          children: node.children
        }
      },
      normalizer_1 (node) {
        if (node.children == null || node.children == 'null' || node.children.length == 0) {
          delete node.children
        }
        return {
          id: node.id,
          label: node.equipmentName,
          children: node.children
        }
      },
      // 折叠面板
      activeNames: ['1', '2'],
      // 总条数
      total: 0,
      // 获取域编码长度
      domainNo: "",
      //提交状态按钮
      states: false,
      // 遮罩层
      loading: true,
      // 树节点过滤
      filterText: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询抽屉
      drawer: false,
      // 当前点击树节点id
      clickTreeNodeId: "2bfbfa2a-3357-4bdc-8ab1-789db8b79d7f",
      // 上级设备id
      upperLevelId: "",
      // 购入日期
      purchaseDate: undefined,
      // 台账信息
      ledgerForm: {
        // 使用日期
        useDate: undefined,
        // 购入日期
        purchaseDate: undefined,
        // 安装日期
        installationUnit: undefined,
        // 资产状态
        assetStatus: undefined
      },
      // 基本属性
      baseForm: {
        // 设备位置
        // equipmentLocation: undefined,
        // 设备专业
        equipmentProfessionNumber: undefined,
        // 设备类型
        equipmentType: undefined,
        // 第一责任人
        firstResponsiblePerson: "",
        // 物联设备
        internetOfThings: "Y",
        // 油品
        oilName: undefined,
        // 油品类别
        oilTypeId: undefined
      },
      // 选中数组
      ids: [],
      // 表格数据
      tableData: [],
      // 设备位置
      locationOptions: [],
      // 查询--设备位置
      queryOptions: [],
      // 油品
      oilNameArr: [],
      // 树节点数据
      treeData: [],
      // 合并设备与设备域树结构
      combineTreeData: [],
      // 设备树节点数据
      equipmentTreeData: [],
      // 第一责任人
      responsiblePerson: [],
      // 查询第一责任人
      responsiblePersonArr: [],
      // 默认展开树节点
      defaultKeys: [],
      defaultProps: {
        children: 'children',
        label: function (data) {
          return data.domainNo + "-" + data.domainName
        },
      },
      // 查询参数
      queryParams: {
        // 设备位置
        equipmentLocation: undefined,
        // 设备类型
        equipmentType: undefined,
        // 设备类型label
        equipmentTypeLabel: undefined,
        // 设备名称
        equipmentName: undefined,
        // 设备编码
        equipmentNumbert: undefined,
        // 第一责任人
        firstResponsiblePerson: "",
        // 台账类型
        accountType: undefined,
        // 制造厂商
        manufacturers: undefined,
        // 维护人员
        maintenanceUser: undefined,
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
      },
      // 表格字段
      columns: [
        { label: '设备名称', prop: "equipmentName", align: "center", fixed: true, width: 150 },
        { label: '设备编码', prop: "equipmentNumbert", align: "center", fixed: false, width: 100 },
        { label: '设备类型', prop: "equipmentType", fixed: false, width: 100 },
        { label: '台账类型', prop: "accountType", fixed: false, width: 100 },
        { label: '资产状态', prop: "assetStatus", fixed: false, width: 100 },
        { label: '设备级别', prop: "equipmentLevel", fixed: false, width: 100 },
        { label: '成本中心', prop: "costCenter", fixed: false, width: 100 },
        { label: '设备阶层', prop: "equipmentClass", fixed: false, width: 100 },
        { label: '购入日期', prop: "purchaseDate", fixed: false, width: 100 },
        { label: '维护单位', prop: "maintenanceUnit", fixed: false, width: 100 },
        { label: '产权单位', prop: "propertyUnit", fixed: false, width: 100 },
        { label: '安装日期', prop: "installationUnit", fixed: false, width: 100 },
        { label: '维护人员', prop: "maintenanceUser", fixed: false, width: 100 },
        { label: '设备层级', prop: "equipmentGrade", fixed: false, width: 100 },
      ],
      // 表单校验
      baseFormRules: {
        equipmentLocation: [
          { required: true, message: "请选择设备位置", trigger: "change" }
        ],
        equipmentType: [
          { required: true, message: "请选择设备类型", trigger: "change" }
        ],
        equipmentProfessionNumber: [
          { required: true, message: "请选择设备专业", trigger: "change" }
        ],
        equipmentName: [
          { required: true, message: "请输入设备名称", trigger: "blur" },
          { max: 20, message: '设备名称最大长度为 20 个字符', trigger: 'blur' }
        ],
        equipmentNumbert: [
          { required: true, message: "请输入设备编码", trigger: "blur" },
          { max: 10, message: '设备编码最大长度为 10 个字符', trigger: 'blur' }
        ],
        internetOfThings: [
          { required: true, message: "请选择物联设备", trigger: "change" }
        ]
      },
      //设备台账表单验证
      baseFormRules2: {
        supplier: [
          { max: 20, message: '供应厂商最大长度为 20 个字符', trigger: 'blur' }
        ],
        specificationsAndModels: [
          { max: 30, message: '规格型号最长为30 个字符', trigger: 'blur' }

        ],
        accountType: [
          { max: 20, message: '台账类型最长为20 个字符', trigger: 'blur' }

        ],
        useUnit: [
          { max: 20, message: '使用单位最长为20 个字符', trigger: 'blur' }

        ],
        useDepartmentId: [
          { max: 20, message: '使用部门最长为20 个字符', trigger: 'blur' }

        ],
        manufacturers: [
          { max: 20, message: '制造厂商最长为20 个字符', trigger: 'blur' }

        ],
        equipmentLevel: [
          { max: 5, message: '设备级别最长为5 个字符', trigger: 'blur' }

        ],
        costCenter: [
          { max: 10, message: '成本中心最长为10 个字符', trigger: 'blur' }

        ],
        equipmentClass: [
          { max: 5, message: '设备阶层最长为5 个字符', trigger: 'blur' }

        ],
        maintenanceUnit: [
          { max: 20, message: '维护单位最长为20 个字符', trigger: 'blur' }

        ],
        propertyUnit: [
          { max: 20, message: '产权单位最长为20 个字符', trigger: 'blur' }

        ],
        maintenanceUser: [
          { max: 10, message: '维护人员最长为10 个字符', trigger: 'blur' }

        ],
        equipmentGrade: [
          { max: 10, message: '设备层级最长为10 个字符', trigger: 'blur' }

        ],
        equipmentNumbert: [
          { max: 5, message: '设备专业编码最长为5 个字符', trigger: 'blur' }

        ]

      },
      //日期小于当前时间
      pickerOptions: {},
      // 使用日期小于安装日期
      pickerOptions1: {},
    }
  },
  created () {
    this.initTree()
    this.getList()
    this.getPersonnerlInfo()
  },
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 使用日期切换
    useDateChange () {
      if (!this.ledgerForm.installationUnit) {
        this.ledgerForm.useDate = undefined
        this.$modal.msgWarning("请先选择安装日期")
      }
    },
    // 安装日期切换事件
    installDateChange (val) {
      if (!this.ledgerForm.purchaseDate) {
        this.ledgerForm.installationUnit = undefined
        this.$modal.msgWarning("请先选择购入日期")
        return
      }
      const installDate = Date.parse(new Date(val))
      this.pickerOptions1 = Object.assign({}, this.pickerOptions1, {
        disabledDate (time) {
          return time.getTime() < installDate
        }
      })
    },
    // 购入日期切换事件
    purchaseDateChange (val) {
      const startDate = Date.parse(new Date(val));
      this.pickerOptions = Object.assign({}, this.pickerOptions, {
        disabledDate (time) {
          return time.getTime() < startDate
        }
      })
    },
    // 获取人员信息
    getPersonnerlInfo () {
      getPersonnerlInfoList().then(response => {
        this.responseUserArr = response.data
      })
    },
    // 选择上级设备
    selectUpperEquipmentTree (data) {
      this.baseForm.upperLevel = data.id
    },
    // 查询条件第一责任人
    queryPersonChange (val) {
      this.responsiblePersonArr = val
    },
    // 第一责任人change事件
    responsiblePersonChange (val) {
      this.responsiblePerson = val
    },
    // 选择资产状态
    assetStatusSelect (val) {
      this.ledgerForm.assetStatus = val.label
    },
    // 选择油品
    oilNameSelect (val) {
      this.baseForm.oilName = val.label
    },
    // 油品类别
    oilTypeSelect (val) {
      this.baseForm.oilName = undefined
      this.baseForm.oilTypeId = val.label
      getDicts(val.type).then(response => {
        this.oilNameArr = response.data
      })
    },
    // 选择设备专业
    equipmentProfessionSelect (val) {
      this.baseForm.equipmentProfessionNumber = val.label
    },
    // 选择设备类型
    equipmentTypeSelect (val) {
      this.baseForm.equipmentType = val.label
    },
    // 选择设备位置
    selectLocationTree (val) {
      this.equipmentTreeData = []
      this.domainNo = val.domainNo
      getEquipmentBasicNumById(val.id).then(response => {
        this.clickTreeNodeId = response.data.equipment_location
        this.upperLevelId = response.data.upper_level
        // 根据设备位置查询上级设备
        getEquipmentByLocationId(response.data.equipment_location).then(response => {
          this.equipmentTreeData = response.data
        })
      })
      if (this.$refs.equipmentTree != undefined) {
        this.$refs.equipmentTree.clear()
        this.baseForm.upperLevel = null
      }
    },
    // 初始化树节点
    initTree () {
      // 设备域
      getTreeNode().then(response => {
        this.treeData = response.data
        this.defaultKeys.push(this.treeData[0].id)
      })
      // 合并设备域、上下级设备树
      getEquipmentCombineTree().then(response => {
        this.combineTreeData = response.data
      })
    },
    //初始化数据
    getList (page = 1) {
      this.queryParams.page = page
      this.loading = true
      queryAllDataList().then(response => {
        this.total = response.data.total
        this.tableData = response.data.list
      })
      this.loading = false
    },
    // 树节点搜索过滤
    filterNode (value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick (data) {
      this.resetQueryParams()
      this.selectLocationTree(data)
      this.queryParams.equipmentLocation = data.id
      this.handleQuery()
    },
    // 点击搜索查询
    handleQuery () {
      for (let i = 0; i < this.responsiblePersonArr.length; i++) {
        if (i == this.responsiblePersonArr.length - 1) {
          this.queryParams.firstResponsiblePerson += this.responsiblePersonArr[i]
        } else {
          this.queryParams.firstResponsiblePerson += this.responsiblePersonArr[i] + ","
        }
      }
      if (this.queryParams.equipmentType != undefined) {
        this.queryParams.equipmentTypeLabel = this.queryParams.equipmentType.label
      }
      getEquipmentAccountByParams(this.queryParams).then(response => {
        this.$modal.msgSuccess("查询成功")
        this.total = response.data.total
        this.tableData = response.data.list
        this.drawer = false
      })
    },
    // 序号设置
    indexMethod (index) {
      index = (index + 1) + (this.queryParams.page - 1) * this.queryParams.limit
      return index
    },
    // 新增方法
    handleAdd () {
      this.reset()
      this.open = true
      this.title = '添加'
      this.locationOptions = this.treeData
      this.baseForm.equipmentLocation = this.clickTreeNodeId
      if (!isEmpty(this.upperLevelId)) {
        this.baseForm.upperLevel = this.upperLevelId
      }
      this.responsiblePerson = []
    },
    // 修改方法
    handleUpdate (row) {
      this.reset()
      const typeId = row.id || this.ids
      if (this.ids.length > 1) {
        this.$modal.msgWarning("只能选择一笔数据进行修改操作")
      } else if (this.ids.length < 1 && (typeId == null || typeId.length < 1)) {
        this.$modal.msgWarning("请选择一笔数据进行操作")
      } else {
        getAccountValueById(typeId).then(response => {
          this.locationOptions = this.treeData
          this.baseForm = response.data.baseForm
          if (this.baseForm.equipmentLocation.length > 0) {
            // 根据设备位置查询上级设备
            getEquipmentByLocationId(this.baseForm.equipmentLocation).then(response => {
              this.equipmentTreeData = response.data
            })
          }
          this.domainNo = this.baseForm.equipmentLocation
          this.ledgerForm = response.data.ledgerForm
          this.responsiblePerson = response.data.baseForm.firstResponsiblePerson.split(",")
          this.open = true
          this.title = '修改'
        })
      }
    },
    // 删除方法
    handleDelete (row) {
      const ids = row.id || this.ids;
      if (ids.length > 0) {
        this.$confirm('确认删除吗?删除后数据无法找回？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteAccountData(ids).then(response => {
            this.$modal.msgSuccess("删除成功")
            this.getList()
            this.initTree()
          })
        }).catch(() => { });
      }
    },
    // 导出方法
    handleExport () {
      console.log("导出")
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 提交操作
    submitForm: function () {
      this.$refs.baseForm.validate(valid => {
        if (valid) {
          this.$refs.ledgerForm.validate(valid => {
            if (valid) {
              this.states = true
              if (this.baseForm.id != undefined && this.ledgerForm.id != undefined) {
                if (this.domainNo.length < 4) {
                  this.$modal.msgWarning("选择设备位置域编码长度必须大于4位")
                  this.states = false
                  return;
                }
                this.baseForm.firstResponsiblePerson = ""
                for (let k = 0; k < this.responsiblePerson.length; k++) {
                  if (k == this.responsiblePerson.length - 1) {
                    this.baseForm.firstResponsiblePerson += this.responsiblePerson[k]
                  } else {
                    this.baseForm.firstResponsiblePerson += this.responsiblePerson[k] + ","
                  }
                }
                const dataForm = {
                  "baseForm": this.baseForm,
                  "ledgerForm": this.ledgerForm
                }
                updateAccountById(dataForm).then(response => {
                  this.states = false
                  this.$modal.msgSuccess("修改成功")
                  this.open = false;
                  this.getList(this.queryParams.page);
                  this.initTree()
                });
              } else {
                if (this.domainNo.length < 4) {
                  this.$modal.msgWarning("选择设备位置域编码长度必须大于6位")
                  this.states = false
                  return;
                }
                for (let i = 0; i < this.responsiblePerson.length; i++) {
                  if (i == this.responsiblePerson.length - 1) {
                    this.baseForm.firstResponsiblePerson += this.responsiblePerson[i]
                  } else {
                    this.baseForm.firstResponsiblePerson += this.responsiblePerson[i] + ","
                  }
                }
                const dataForm = {
                  "baseForm": this.baseForm,
                  "ledgerForm": this.ledgerForm
                }
                // 新增基本信息
                addEquipmentBasicProperties(dataForm).then((response) => {
                  this.states = false
                  this.$modal.msgSuccess("新增成功")
                  this.open = false;
                  this.getList()
                  this.initTree()
                });
              }
            } else {
              this.$modal.msgWarning("请完善信息");
              this.states = false
            }
          })
        }
      })
    },
    // 表单重置
    reset () {
      this.baseForm = {
        // 设备位置
        // equipmentLocation: undefined,
        // 设备专业
        equipmentProfessionNumber: undefined,
        // 设备类型
        equipmentType: undefined,
        // 第一责任人
        firstResponsiblePerson: "",
        // 物联设备
        internetOfThings: "Y",
        // 油品
        oilName: undefined,
        // 油品类别
        oilTypeId: undefined,
        // 设备名称
        equipmentName: undefined,
        // 设备编码
        equipmentNumbert: undefined
      }
      this.ledgerForm = {
        // 供应厂商
        supplier: undefined,
        // 规格型号
        specificationsAndModels: undefined,
        // 设备专业编码
        equipmentNumbert: undefined,
        // 台账类型
        accountType: undefined,
        // 使用单位
        useUnit: undefined,
        // 使用日期
        useDate: undefined,
        // 使用部门
        useDepartmentId: undefined,
        // 制造厂商
        manufacturers: undefined,
        // 资产状态
        assetStatus: undefined,
        // 设备级别
        equipmentLevel: undefined,
        // 成本中心
        costCenter: undefined,
        // 设备阶层
        equipmentClass: undefined,
        // 购入日期
        purchaseDate: undefined,
        // 维护单位
        maintenanceUnit: undefined,
        // 产权单位
        propertyUnit: undefined,
        // 安装日期
        installationUnit: undefined,
        // 维护人员
        maintenanceUser: undefined,
        // 设备层级
        equipmentGrade: undefined
      }
    },
    resetQueryParams () {
      this.queryParams = {
        //当前页
        page: 1,
        //每页记录数
        limit: 10,
        // 设备位置
        equipmentLocation: undefined,
        // 设备类型
        equipmentType: undefined,
        // 设备类型label
        equipmentTypeLabel: undefined,
        // 设备名称
        equipmentName: undefined,
        // 设备编码
        equipmentNumbert: undefined,
        // 第一责任人
        firstResponsiblePerson: "",
        // 台账类型
        accountType: undefined,
        // 制造厂商
        manufacturers: undefined,
        // 维护人员
        maintenanceUser: undefined,
      }
    },
    // 取消按钮
    cancel () {
      this.states = false;
      this.open = false;
      this.reset();
    },
    // 高级筛选弹出
    handleAdvanceFilter () {
      this.resetQueryParams()
      this.queryOptions = this.treeData
      this.drawer = true;
    },
    // 重置
    handleReset () {
      this.queryParams = {
        //当前页
        page: 1,
        //每页记录数
        limit: 10,
        // 第一责任人
        firstResponsiblePerson: "",
      };
      this.responsiblePersonArr = []
      this.handleQuery();
    },
    // 高级筛选关闭
    handleClose (done) {
      this.queryParams = {
        //当前页
        page: 1,
        //每页记录数
        limit: 10,
        // 第一责任人
        firstResponsiblePerson: "",
      };
      done();
    },
  }
}
</script>

<style scoped>
/*.el-input {*/
/*  width: auto !important;*/
/*}*/
.avue-crud .el-date-editor.el-input {
  width: auto !important;
}
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>

