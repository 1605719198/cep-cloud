<template>
  <el-dialog title="现金流量代码"
             :visible.sync="visible" width="1080px" top="5vh" append-to-body :close-on-click-modal="false">
  <el-form label-width="148px" :model="queryParam">
    <el-form-item label="上级项目" >
      <el-row :gutter="20">
        <el-col :span="10">
          <el-input
            v-model="queryParam.parentCode"
            clearable
            disabled
          />
        </el-col>
        <el-col :span="10">
          <el-input
            v-model="queryParam.parentName"
            clearable
            disabled
          />
        </el-col>

      </el-row>

    </el-form-item>
  </el-form>
    <el-row>
    <el-table :data="tCodeList"
              @row-click="clickRow" ref="table"
              @selection-change="handleSelectionChange"
             >

        <el-table-column type="selection" width="55" align="center"/>

      <el-table-column prop="cashFlowCode" label="现金流量代码">
      </el-table-column>
      <el-table-column label="现金流量名称" align="center" prop="cashFlowName">
      </el-table-column>
      <el-table-column label="是否可用" align="center" prop="isunabl">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isunabl"/>
        </template>
      </el-table-column>
      <el-table-column label="流入流出" align="center" prop="inorout">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.aa_inorout" :value="scope.row.inorout"/>
        </template>
      </el-table-column>
      <el-table-column label="取值方式" align="center" prop="quedataway">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.aa_quedataway" :value="scope.row.quedataway"/>
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
    </el-row>
  <div slot="footer" class="dialog-footer">
    <el-button type="primary" @click="handleSelectUser">确 定</el-button>
    <el-button @click="visible = false">取 消</el-button>
  </div>
  </el-dialog>

</template>

<script>
import {listCodePop} from "@/api/finance/aa/code";
import { addCodecompid,} from "@/api/finance/aa/codecompid";
export default {
  name: "codePop",
  dicts: ['aa_quedataway', 'aa_inorout', 'sys_yes_no'],
  data() {
    return {
      // 总条数
      total: 0,
      // 遮罩层
      visible: false,
      form: {},
      // 选中数组
      codeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cashFlowName: null,
        companyId: null,
      parentCode: null,
        parentName: null,
      },
      queryParam: {
        pageNum: 1,
        pageSize: 10,
        cashFlowName: null,
        companyId: null,
        parentCode: null,
        parentName: null,
      },
      // 表单参数
      tCodeList: [],
    }
  },
  methods: {
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams = queryParams;
        this.queryParam = queryParams;
        this.form = queryParams;
      }
      this.getList();
      this.visible = true;
    },
    /** 查询现金流量代码列表 */
    getList() {
      this.loading = true;
      listCodePop(this.queryParams).then(response => {
        this.tCodeList = response.rows;
        this.total = response.total;

        this.loading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.codeList = selection.map(item => item.codeList)
      this.codeList = selection
    },
    //选择数据
    handleSelectUser() {
      if (this.codeList.length==0) {
        this.$modal.msgError("请选择要保存的现金流量代码");
        return;
      }

      for (let i = 0; i < this.codeList.length; i++) {
        if( this.form.companyId== null){
          this.$modal.msgError("请点击树节点进行保存");
          return
        }else {
          this.codeList[i].companyId = this.form.companyId
        }
        this.codeList[i].id =''
        this.codeList[i].parentId = this.form.parentId
        this.codeList[i].parentCode = this.form.parentCode
        this.codeList[i].parentName = this.form.parentName
        this.form.codeList = this.codeList
      }

          addCodecompid(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.$emit("ok",this.codeList);
          });
      this.visible = false;

    },
  }
}
</script>

<style scoped>

</style>
