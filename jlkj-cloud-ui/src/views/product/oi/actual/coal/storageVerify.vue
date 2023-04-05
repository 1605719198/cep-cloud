<!--
  交接班仓存核验
-->
<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <!--表单筛选 -->
    <div class="avue-crud__search" style="border: 0">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="6">
            <!-- <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-date-picker v-model="query.daterange"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期" />
              </div>
            </div> -->
            <el-form-item prop="timeArr">
              <el-date-picker v-model="query.timeArr" type="daterange" range-separator="至" start-placeholder="账务开始日期"
                end-placeholder="账务结束日期" value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-select v-model="query.classType"
                           placeholder="选择班别"
                           clearable>
                  <el-option v-for="item in classTypeOptions"
                             :key="item.value"
                             :label="item.label"
                             :value="item.label">
                  </el-option>
                </el-select>
              </div>
            </div>
          </el-col> -->

          <!-- <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-select v-model="query.shift"
                           placeholder="班次"
                           clearable>
                  <el-option v-for="item in shiftOptions"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value">
                  </el-option>
                </el-select>
              </div>
            </div>
          </el-col> -->

          <el-col :span="10">
            <div class="el-form-item__content" style="margin-left: 0px;">
              <el-button v-hasPermi="['productionHandoverStockVerify_list']" size="medium" type="primary"
                @click="handleQuery" icon="el-icon-search">搜 索</el-button>
              <el-button size="medium" type="default" @click="handleEmpty" icon="el-icon-refresh-left">重 置</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 表单数据 -->
    <div>
      <el-form>
        <el-table v-loading="table.loading" height="66vh" size="small" :data="tableData" stripe>

          <el-table-column prop="date" label="日期" min-width="150px" align="center">
          </el-table-column>

          <!-- <el-table-column prop="id"
                           label="编号"
                           sortable
                           min-width="100px"
                           align="center">
          </el-table-column>
          <el-table-column prop="shift"
                           label="班次"
                           align="center"
                           min-width="100px"
                           sortable
                           :formatter="shiftFormatter">

          </el-table-column>
          <el-table-column prop="class_type"
                           label="班别"
                           align="center"
                           min-width="100px"
                           sortable>
          </el-table-column>
          <el-table-column prop="start_time"
                           label="班组开始时间"
                           min-width="150px"
                           align="center"
                           sortable>
          </el-table-column>
          <el-table-column prop="end_time"
                           label="班组结束时间"
                           min-width="150px"
                           align="center"
                           sortable>
          </el-table-column> -->

          <!-- <el-table-column prop="coal_blending_level1,coal_blending_stock1"
                           label="1#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level1 }}<span v-show="scope.row.coal_blending_level1 !== null">|</span>{{
              scope.row.coal_blending_stock1
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn1" label="1#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn1 / 1000).toFixed(3) }}
            </template>
          </el-table-column>


          <!-- <el-table-column prop="coal_blending_level2,coal_blending_stock2"
                           label="2#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level2 }}<span v-show="scope.row.coal_blending_level2 !== null">|</span>{{
              scope.row.coal_blending_stock2
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn2" label="2#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn2 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level3,coal_blending_stock3"
                           label="3#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level3 }}<span v-show="scope.row.coal_blending_level3 !== null">|</span>{{
              scope.row.coal_blending_stock3
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn3" label="3#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn3 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level4,coal_blending_stock4"
                           label="4#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level4 }}<span v-show="scope.row.coal_blending_level4 !== null">|</span>{{
              scope.row.coal_blending_stock4
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn4" label="4#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn4 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level5,coal_blending_stock5"
                           label="5#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level5 }}<span v-show="scope.row.coal_blending_level5 !== null">|</span>{{
              scope.row.coal_blending_stock5
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn5" label="5#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn5 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level6,coal_blending_stock6"
                           label="6#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level6 }}<span v-show="scope.row.coal_blending_level6 !== null">|</span>{{
              scope.row.coal_blending_stock6
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn6" label="6#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn6 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level7,coal_blending_stock7"
                           label="7#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level7 }}<span v-show="scope.row.coal_blending_level7 !== null">|</span>{{
              scope.row.coal_blending_stock7
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn7" label="7#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn7 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level8,coal_blending_stock8"
                           label="8#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level8 }}<span v-show="scope.row.coal_blending_level8 !== null">|</span>{{
              scope.row.coal_blending_stock8
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn8" label="8#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn8 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level9,coal_blending_stock9"
                           label="9#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level9 }}<span v-show="scope.row.coal_blending_level9 !== null">|</span>{{
              scope.row.coal_blending_stock9
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn9" label="9#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn9 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_level10,coal_blending_stock10"
                           label="10#配煤仓"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_blending_level10 }}<span v-show="scope.row.coal_blending_level10 !== null">|</span>{{
              scope.row.coal_blending_stock10
              }}
            </template>
          </el-table-column> -->

          <el-table-column prop="bn10" label="10#配煤仓(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn10 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <el-table-column prop="bn11" label="1#储煤塔(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn11 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <el-table-column prop="bn12" label="2#储煤塔（t）" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn12 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <el-table-column prop="bn13" label="3#储煤塔(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn13 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <el-table-column prop="bn14" label="4#储煤塔(t)" align="center" min-width="100px">
            <template slot-scope="scope">
              {{ (scope.row.bn14 / 1000).toFixed(3) }}
            </template>
          </el-table-column>

          <!-- <el-table-column prop="coal_blending_stock_total"
                           label="配煤仓总存量"
                           min-width="150px"
                           align="center">
          </el-table-column> -->

          <!-- <el-table-column prop="coal_storage_tower_level1,coal_storage_tower_stock1"
                           label="1#贮煤塔"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_storage_tower_level1 }}<span v-show="scope.row.coal_storage_tower_level1 !== null">|</span>{{ scope.row.coal_storage_tower_stock1 }}
            </template>
          </el-table-column>
          <el-table-column prop="coal_storage_tower_level2,coal_storage_tower_stock2"
                           label="2#贮煤塔"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_storage_tower_level2 }}<span v-show="scope.row.coal_storage_tower_level2 !== null">|</span>{{ scope.row.coal_storage_tower_stock2 }}
            </template>
          </el-table-column>
          <el-table-column prop="coal_storage_tower_level3,coal_storage_tower_stock3"
                           label="3#贮煤塔"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_storage_tower_level3 }}<span v-show="scope.row.coal_storage_tower_level3 !== null">|</span>{{ scope.row.coal_storage_tower_stock3 }}
            </template>
          </el-table-column>
          <el-table-column prop="coal_storage_tower_stock_total123"
                           label="123#贮煤塔总存量"
                           min-width="150px"
                           align="center">
          </el-table-column>
          <el-table-column prop="coal_storage_tower_level4,coal_storage_tower_stock4"
                           label="4#贮煤塔"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_storage_tower_level4 }}<span v-show="scope.row.coal_storage_tower_level4 !== null">|</span>{{ scope.row.coal_storage_tower_stock4 }}
            </template>
          </el-table-column>
          <el-table-column prop="coal_storage_tower_level5,coal_storage_tower_stock5"
                           label="5#贮煤塔"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_storage_tower_level5 }}<span v-show="scope.row.coal_storage_tower_level5 !== null">|</span>{{ scope.row.coal_storage_tower_stock5 }}
            </template>
          </el-table-column>
          <el-table-column prop="coal_storage_tower_level6,coal_storage_tower_stock6"
                           label="6#贮煤塔"
                           align="center"
                           min-width="100px">
            <template slot-scope="scope">
              {{ scope.row.coal_storage_tower_level6 }}<span v-show="scope.row.coal_storage_tower_level6 !== null">|</span>{{ scope.row.coal_storage_tower_stock6 }}
            </template>
          </el-table-column>
          <el-table-column prop="coal_storage_tower_stock_total456"
                           label="456#贮煤塔总存量"
                           min-width="150px"
                           align="center">
          </el-table-column>
          <el-table-column prop="modify_time"
                           label="更新时间"
                           align="center"
                           min-width="150px">
          </el-table-column> -->

          <!-- <el-table-column fixed="right"
                           label="操作"
                           width="115"
                           align="center"
                           min-width="150px">
            <template slot-scope="scope">
              <el-button v-hasPermi="['productionHandoverStockVerify_list']"
                         size="mini"
                         plain
                         type="success"
                         icon="el-icon-edit"
                         @click="handleUpdate(scope.row)">修改
              </el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <div style="margin-top: 10px;right: 0;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
          <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="page.current" :page-sizes="[20, 50, 100, 200]" :page-size="page.size" :layout="page.layout"
            :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <!-- 修改弹窗 -->
    <div>
      <el-dialog title="修改" :visible.sync="dialogFormVisible" width="1400px" top="100px" ref="ref">
        <el-form :model="form" :inline="true" :rules="rules" ref="form">
          <el-row>
            <el-col :span="12">
              <el-form-item label="班组" class="item" label-width="60px" style="margin-left:10px">
                <el-col :span="12">
                  <el-input type="text" autocomplete="off" v-model="form.class_type" disabled style="width: 260px">
                    <template slot="prepend">班组名称</template>
                  </el-input>
                </el-col>
                <el-col :span="12">
                  <el-input type="text" v-model="form.shift_text" autocomplete="off"
                    style="margin-left: 14px; width: 265px" disabled>
                    <template slot="prepend">班次</template>
                  </el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="班组时间" label-width="69px">
                <el-col :span="12">
                  <el-input type="text" autocomplete="off" v-model="form.start_time"
                    style="width:268px;margin-left: 8px;" disabled><template slot="prepend">开始时间</template></el-input>
                </el-col>
                <el-col :span="12">
                  <el-input type="text" autocomplete="off" style="margin-left: 18px; width:272px"
                    v-model="form.end_time" disabled><template slot="prepend">结束时间</template></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12" style="margin-top: 13px;">
              <el-form-item label="1#配煤仓">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_level1">
                      <el-input type="text" autocomplete="off" v-model="form.coal_blending_level1">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_stock1">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_blending_stock1">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12" style="margin-top: 13px;">
              <el-form-item label="2#配煤仓">
                <el-col :span="12">
                  <el-form-item prop="coal_blending_level2">
                    <el-input type="text" autocomplete="off" v-model="form.coal_blending_level2"
                      style="margin-left: 7.55px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_blending_stock2">
                    <el-input type="text" autocomplete="off" style="margin-left: 17.5px;"
                      v-model="form.coal_blending_stock2"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="3#配煤仓">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_level3">
                      <el-input type="text" autocomplete="off" v-model="form.coal_blending_level3">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_stock3">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_blending_stock3">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="4#配煤仓" prop="">
                <el-col :span="12">
                  <el-form-item prop="coal_blending_level4">
                    <el-input type="text" autocomplete="off" v-model="form.coal_blending_level4"
                      style="margin-left: 7.55px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_blending_stock4">
                    <el-input type="text" autocomplete="off" style="margin-left: 17.5px;"
                      v-model="form.coal_blending_stock4"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="5#配煤仓" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_level5">
                      <el-input type="text" autocomplete="off" v-model="form.coal_blending_level5">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_stock5">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_blending_stock5">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="6#配煤仓" prop="">
                <el-col :span="12">
                  <el-form-item prop="coal_blending_level6">
                    <el-input type="text" autocomplete="off" v-model="form.coal_blending_level6"
                      style="margin-left: 7.55px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_blending_stock6">
                    <el-input type="text" autocomplete="off" style="margin-left: 17.5px;"
                      v-model="form.coal_blending_stock6"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="7#配煤仓" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_level7">
                      <el-input type="text" autocomplete="off" v-model="form.coal_blending_level7">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_stock7">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_blending_stock7">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="8#配煤仓" prop="">
                <el-col :span="12">
                  <el-form-item prop="coal_blending_level8">
                    <el-input type="text" autocomplete="off" v-model="form.coal_blending_level8"
                      style="margin-left: 7.55px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_blending_stock8">
                    <el-input type="text" autocomplete="off" style="margin-left: 17.5px;"
                      v-model="form.coal_blending_stock8"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="9#配煤仓" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_level9">
                      <el-input type="text" autocomplete="off" v-model="form.coal_blending_level9">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_stock9">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_blending_stock9">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="10#配煤仓" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_level10">
                      <el-input type="text" autocomplete="off" v-model="form.coal_blending_level10"
                        style="width: 273px;"><template slot="prepend">料位</template></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_blending_stock10">
                      <el-input type="text" autocomplete="off" style="margin-left:6px; width:272px ;"
                        v-model="form.coal_blending_stock10"><template slot="prepend">重量</template></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="1#贮煤塔" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_storage_tower_level1">
                      <el-input type="text" autocomplete="off" v-model="form.coal_storage_tower_level1">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_storage_tower_stock1">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_storage_tower_stock1">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="2#贮煤塔" prop="">
                <el-col :span="12">
                  <el-form-item prop="coal_storage_tower_level2">
                    <el-input type="text" autocomplete="off" v-model="form.coal_storage_tower_level2"
                      style="margin-left: 7.5px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_storage_tower_stock2">
                    <el-input type="text" autocomplete="off" style="margin-left: 17.5px;"
                      v-model="form.coal_storage_tower_stock2"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="3#贮煤塔" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_storage_tower_level3">
                      <el-input type="text" autocomplete="off" v-model="form.coal_storage_tower_level3">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_storage_tower_stock3">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_storage_tower_stock3">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="4#贮煤塔" prop="phone">
                <el-col :span="12">
                  <el-form-item prop="coal_storage_tower_level4">
                    <el-input type="text" autocomplete="off" v-model="form.coal_storage_tower_level4"
                      style="margin-left: 7.5px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_storage_tower_stock4">
                    <el-input type="text" autocomplete="off" style="margin-left: 17.5px;"
                      v-model="form.coal_storage_tower_stock4"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="5#贮煤塔" prop="">
                <el-row>
                  <el-col :span="12">
                    <el-form-item prop="coal_storage_tower_level5">
                      <el-input type="text" autocomplete="off" v-model="form.coal_storage_tower_level5">
                        <template slot="prepend">料位</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item prop="coal_storage_tower_stock5">
                      <el-input type="text" autocomplete="off" style="margin-left: 10px;"
                        v-model="form.coal_storage_tower_stock5">
                        <template slot="prepend">重量</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="6#贮煤塔" prop="">
                <el-col :span="12">
                  <el-form-item prop="coal_storage_tower_level6">
                    <el-input type="text" autocomplete="off" v-model="form.coal_storage_tower_level6"
                      style="margin-left: 7.5px;"><template slot="prepend">料位</template></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="coal_storage_tower_stock6">
                    <el-input type="text" autocomplete="off" style="margin-left:17.5px;"
                      v-model="form.coal_storage_tower_stock6"><template slot="prepend">重量</template></el-input>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="dialog-footer" style="padding-bottom: 25px">
          <el-button @click="down" style="margin-left:1145px; ">取 消</el-button>
          <el-button type="primary" @click="submitForm('form')" style="margin-right:15px">确 定</el-button>
        </div>

      </el-dialog>
    </div>
  </div>
</template>
<script>
import { getproductionHandoverStockVerify, updateproductionHandoverStockVerify } from "@/api/production/oi/storage";
import { dateFormat } from '@/utils/date'

export default {
  computed: {
  },

  name: "storageVerify",
  data() {
    // 修改数据只能输入正数和小数
    var valiNumberPass2 = (rule, value, callback) => {//正整数
      let reg = /^(\d+)(\.\d+)?$/;
      if (value === '') {
        callback(new Error('请输入内容'));
      } else if (!reg.test(value)) {
        callback(new Error('请输入0及0以上的数'));
      } else {
        callback();
      }
    }
    return {

      rules: {
        coal_blending_level1: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock1: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level2: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock2: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level3: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock3: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level4: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock4: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level5: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock5: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level6: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock6: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level7: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock7: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level8: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock8: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level9: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock9: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_level10: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_blending_stock10: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_level1: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_stock1: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_level2: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_stock2: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_level3: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_stock3: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_level4: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_stock4: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_level5: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_stock5: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_level6: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
        coal_storage_tower_stock6: [{ required: true, trigger: 'blur', validator: valiNumberPass2 }],
      },
      dialogFormVisible: false,
      dataEdit: {},
      page: {
        orderby: 'asc',
        order: 'start_time',
        size: 20,
        current: 1,
        // pageSize: 20,
        // currentPage: 1,
        total: 1,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      currentPage: 0,
      total: 0,
      classTypeOptions: [
        { value: "甲", label: "甲" },
        { value: "乙", label: "乙" },
        { value: "丙", label: "丙" },
      ],
      shiftOptions: [
        { value: "01", label: "常白班" },
        { value: "2201", label: "白班" },
        { value: "2202", label: "夜班" },
        { value: "00", label: "休息" }
      ],

      query: {
        timeArr: [
          dateFormat(new Date(), 'yyyy-MM-dd'),
          dateFormat(new Date(), 'yyyy-MM-dd')
        ],
        daterange: '',
        classType: '',
        shift: '',
        start_time: '',
        end_time: '',

      },

      table: {
        border: true,
        loading: true

      },
      tableData: [
      ],
      form: {
        shift: '',
        shift_text: "",
        modify_user_name: '',
        modify_user_id: '',
        class_type: '',
      }
    }
  },
  mounted() {
    this.handleQuery();
  },
  methods: {

    //修改表单提交
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.form.modify_user_id = this.$store.state.user.userInfo.userName;
          this.form.modify_user_name = this.$store.state.user.userInfo.nickName;
          updateproductionHandoverStockVerify(this.form).then((res) => {
            if (res.code === 200) {
              this.$message({ type: "success", message: res.msg });
              this.handleQuery()
            }
          }, error => {
            this.submitLoading = false
            window.console.log(error);
          })
          // console.log(this.form)

          this.dialogFormVisible = false

        } else {

          return false;
        }
      });

    },
    // 修改取消
    down() {
      this.$refs.form.clearValidate()
      this.dialogFormVisible = false
      this.handleQuery()
    },
    // 修改
    handleUpdate(row) {
      this.form = { ...row };
      switch (this.form.shift) {
        case "01":
          this.form.shift_text = "常白班";
          break
        case "2201":
          this.form.shift_text = "白班";
          break
        case "2202":
          this.form.shift_text = "夜班";
          break
        case "00":
          this.form.shift_text = "休息";
          break
        default:
          this.form.shift_text = row.shift;
          break
      }
      this.dialogFormVisible = true
    },
    //查询
    handleQuery() {
      this.page.current = 1;
      let query = this.query
      let page = this.page;

      // let params = {
      //   "shift": query.shift,
      //   "classType": query.classType,
      //   "startTime": query.daterange[0] === "" ? "" : query.daterange[0],
      //   "endTime": query.daterange[1] === "" ? "" : query.daterange[1],
      //   "size": page.size,
      //   "current": page.current,
      //   "order": "id",
      //   "orderby": "asc"
      // }


      let params = {
        "startTime": this.query.timeArr && this.query.timeArr[0] ? query.timeArr[0] : '',
        "endTime": this.query.timeArr && this.query.timeArr[1] ? query.timeArr[1] : '',
        "size": page.size,
        "current": page.current,
        "order": "date",
        "orderby": "desc"
      }

      this.table.loading = true;
      getproductionHandoverStockVerify(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;
        if (this.tableData.length === 0) {
          this.$message({ type: "warning", message: "暂无数据" });


        }
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      })
      // console.log(params)
    },
    // 清空
    handleEmpty() {
      this.query = {
        timeArr: [
          dateFormat(new Date(), 'yyyy-MM-dd'),
          dateFormat(new Date(), 'yyyy-MM-dd')
        ],
        daterange: '',
        classType: '',
        shift: '',
      }
      this.page.current = 1;
      this.handleQuery()
    },

    // 分页-每页多少条
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.page.size = val;
      // this.onload();
      this.handleQuery();

    },
    // 分页-当前页
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.page.current = val;
      // this.onload();
      this.handleQuery();
    },
    //提供给子类调用父类的方法
    submitAdd() {
      // console.log(JSON.stringify(data))
      this.addBox = false;
      this.editBox = false;
      this.$message({
        type: "success",
        message: "操作成功!"
      });
    },

    //班次formatter
    shiftFormatter(row) {
      switch (row.shift) {
        case "01":
          return "常白班";
        case "2201":
          return "白班";
        case "2202":
          return "夜班";
        case "00":
          return "休息";
        default:
          return row.shift;
      }
    }
  },
}
</script>

<style scoped>
.el-col {
  margin-bottom: 0px;
}

.el-form-item {
  margin-bottom: 10px;
}

.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
</style>
