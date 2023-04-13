<template>
  <basic-container>
    <avue-crud :option="option"
               :page="page"
               :table-loading="loading"
               :data="data"
               ref="crud"
               v-model="form"
               :permission="permissionList"
               @row-del="rowDel"
               @row-update="rowUpdate"
               @row-save="handleRowSave"
               @search-change="searchChange"
               @search-reset="searchReset"
               @selection-change="selectionChange"
               @current-change="currentChange"
               @size-change="sizeChange"
               @on-load="onLoad"
               @sort-change="sortChange">
      <template slot="menuLeft">
        <el-button type="primary"
                   size="small"
                   icon="el-icon-plus"
                   @click="handleAdd">新 增2
        </el-button>
      </template>
    </avue-crud>


<!-- destroy-on-close="true" 清除缓存 否则打开页面后还会显示上一次是数据  -->
    <el-dialog title="新建焦化年指标计划"
               append-to-body
               :destroy-on-close="false"
               :close-on-click-modal="false"
               :visible.sync="addBox" width="800px">
      <planCokingAdd @submitAdd="submitAdd" @close="addBox=false" ></planCokingAdd>


    </el-dialog>
  </basic-container>
</template>

<script>
  import {getList, add, update, remove} from "@/api/system/role";
  import planCokingAdd from "./planCokingAdd";

  export default {
    components: {
      planCokingAdd
    },
    data() {
      return {
        form: {},
        selectionList: [],
        addBox: false,

        query: {
          role_name: '',
        },
        loading: false,
        page: {
          pageSize: 20,
          currentPage: 1,
          total: 1,
          layout: "total, sizes, prev, pager, next, jumper",
        },
        sort: {
          order: 'role_name',
          orderby: '',
        },
        option: {
          title: 'zz',
          dialogTop: '200',
          dialogWidth: '30%',
          searchShow: true,
          searchMenuSpan: 6,
          tip: false,
          tree: true,
          border: true,
          index: true,
          selection: true,
          viewBtn: true,
          column: [
            {
              label: '年份', prop: "planYear",
              type: 'year',
              valueFormat: 'yyyy',
              searchSpan: 4,
              searchRange: false,
              sortable: true,
              search: true,
              span: 12,
            },
            {label: '全焦产率(%)', prop: "QJ", sortable: true, search: false, span: 24},
            {label: '冶金焦产率(%)', prop: "YJJ", sortable: true, span: 24},
            {label: '干熄焦产率(%)', prop: "GXJ", sortable: true, span: 24},
            {label: '石膏产率(%)', prop: "SG", sortable: true, span: 24},
            {label: '蒸汽产率(%)', prop: "ZQ", sortable: true, span: 24},
          ]
        },
        data: [],
        addForm: {},

      };
    },
    computed: {
      ...mapGetters(["userInfo", "permission"]),
      permissionList() {
        return {
          addBtn: true,//this.vaildData(this.permission.role_add, false),
          viewBtn: true,//this.vaildData(this.permission.role_view, false),
          delBtn: true,//this.vaildData(this.permission.role_delete, false),
          editBtn: true,//this.vaildData(this.permission.role_edit, false)
        };
      }
    },
    methods: {
      onLoad(page, params = {}) {
        this.loading = true;
        let query = {
          ...this.query,
          ...this.sort,
        };
        getList(page.currentPage, page.pageSize, Object.assign(params, query)).then(res => {
          this.loading = false;
          if (res.data !== null && res.data.data !== null) {
            this.data = res.data.data.records;
            this.page.total = res.data.data.total;
          }
        }, error => {
          this.loading = false;
          window.console.log(error);
        });
      },
      handleRowSave(row, done, loading) {
        row = {
          ...row,
          create_user_id: this.$store.getters.userInfo.userId,
          create_user_name: this.userInfo.userName
        };
        add(row).then((res) => {
          loading();
          if (res.data.code === '0') {
            this.$message({
              type: 'success',
              message: res.msg
            });
            done();
            this.onLoad(this.page);
          }
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        row = {
          ...row,
          modify_user_id: this.$store.getters.userInfo.userId,
          modify_user_name: this.userInfo.userName
        };
        update(row).then((res) => {
          console.log(res);
          loading();
          if (res.data.code === '0') {
            this.$message({
              type: 'success',
              message: res.msg
            });
            done();
            this.onLoad(this.page);
          }
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowDel(row) {
        this.$confirm(this.$t('message.confirm.remove'), {
          confirmButtonText: this.$t('button.confirm'),
          cancelButtonText: this.$t('button.cancel'),
          type: "warning"
        })
          .then(() => {
            return remove(row.id);
          })
          .then((res) => {
            console.log(res);
            if (res.data.code === '0') {
              this.$message({
                type: 'success',
                message: res.msg
              });
              this.onLoad(this.page);
            }
          });
      },
      searchReset() {
        this.query = {
          role_name: '',
          order: ''
        };
        this.onLoad(this.page);
      },
      searchChange(params, done) {
        this.query = params;
        this.page.currentPage = 1;
        this.onLoad(this.page, params);
        done();
      },
      selectionChange(list) {
        this.selectionList = list;
      },
      currentChange(currentPage) {
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize) {
        this.page.pageSize = pageSize;
      },
      sortChange(val) {
        this.sort.order = val.prop;
        this.sort.orderby = val.order === 'ascending' ? 'asc' : val.order === 'descending' ? 'desc' : '';
      },
      handleAdd() {
        this.addBox = true;
      },
      submitAdd(data) {
        console.log(JSON.stringify(data))
        this.addBox = false;
        this.$message({
          type: "success",
          message: "操作成功!"
        });
      },
    }
  };
</script>

<style>

</style>
