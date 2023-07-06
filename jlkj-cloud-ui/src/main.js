import Vue from 'vue'

import Cookies from 'js-cookie'
import Element from 'element-ui'
//全局修改默认配置，点击空白处不能关闭弹窗
Element.Dialog.props.closeOnClickModal.default = false
import './assets/styles/element-variables.scss'
import VueClipboard from 'vue-clipboard2'
import '@/assets/styles/index.scss' // global css
import '@/assets/styles/jlkj.scss' // jlkj css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'
import axios from 'axios'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/jlkj";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具扩展
import RightToolbar from "@/components/RightToolbar"
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'
import Tinymce from '@/components/tinymce/index.vue'
import {UserDictOnlyName,UserDictFullName} from '@/utils/dict/UserDict';
// 数据可视化看板
import * as echarts from "echarts";
//全局变量
import globalVariable from '@/utils/global_variable'
//注册全局组件(单选下拉树)
import selectTree from './components/select-Tree/selectTree.vue'

import moment from 'moment'
//注册全局组件(设备选择器)
import equipmentSelection from './components/equipment-selection/index.vue'
//注册全局组件
import tableTabs from './components/table-tabs/tableTabs.vue'
//wangEditor富文本编辑器
import wangEditor from './components/Editor/WangEditor.vue'
//注册全局组件(人员选择器)
import personnelSelection from './components/personnel-selection/index.vue'


Vue.prototype.$moment = moment
// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.UserDictOnlyName = UserDictOnlyName
Vue.prototype.UserDictFullName = UserDictFullName
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree
Vue.prototype.$axios = axios
Vue.prototype.$echarts = echarts;
//挂在全局变量
Vue.prototype.GLOBAL = globalVariable
//全局注册eventBus(页面关闭的时候需要取消关闭)
Vue.prototype.$EventBus = new Vue();

Vue.component('table-tabs', tableTabs)
Vue.component('select-tree', selectTree)
Vue.component('editor-bar', wangEditor)
// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)
Vue.component('tinymce', Tinymce)


Vue.use(directive)
// Vue.use(hljs.vuePlugin);
Vue.use(plugins)
Vue.use(VueMeta)
Vue.use(VueClipboard)

Vue.component('personnel-selection', personnelSelection)
Vue.component('equipment-selection', equipmentSelection)

DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
