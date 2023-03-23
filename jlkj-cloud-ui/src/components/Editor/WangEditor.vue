<template>
  <div>
    <div style="border: 1px solid #ccc; z-index: 100;">
      <Toolbar style="border-bottom: 1px solid #ccc"
               :editor="editor"
               :defaultConfig="toolbarConfig"
               :mode="mode" />
      <Editor :style="{height: height+'px',overflowY: 'hidden'}"
              v-model="html"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="onCreated"
              @onChange="onChange" />
    </div>
  </div>
</template>
<script>
import Vue from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { DomEditor } from '@wangeditor/editor'
import { upload } from "@/api/security/si/governance/index";
import globalVariable from '@/utils/global_variable'
export default Vue.extend({
  components: { Editor, Toolbar },
  //定义实现v-model的属性与事件
  model: {
    prop: "value",
    event: "change"
  },
  props: {
    //v-modal绑定的值
    value: {
      type: String,
      default: ""
    },
    height: {
      type: [String, Number],
      default: 500
    },
    placeholder: {
      type: String,
      default: '请输入内容...'
    },
    //是否只读
    isReadOnly: {
      type: Boolean,
      default: false
    },
    //是否上来就聚焦
    isAutoFocus: {
      type: Boolean,
      default: false
    },
    // 'default'（默认模式）, // or 'simple'(简洁模式)
    mode: {
      type: String,
      default: 'default'
    },
    //排除掉某些菜单(key)
    excludeKeys: {
      type: Array,
      default: () => {
        //默认没有上传视频
        return ['group-video']
      }
    }
  },
  watch: {
    //监听用户的值
    value: {
      handler (newValue) {
        // console.log(newValue)
        this.html = newValue
      },
      //立即监听
      immediate: true
    }
  },
  data () {
    return {
      editor: null,
      html: '',
      //菜单栏配置
      toolbarConfig: {
        //如果仅仅想排除掉某些菜单，其他都保留，可以使用 excludeKeys 来配置。排除菜单组，写菜单组 key 的值即可
        excludeKeys: this.excludeKeys
      },
      //编辑器配置
      editorConfig: {
        //配置编辑器 placeholder
        placeholder: this.placeholder,
        //配置编辑器是否只读
        readOnly: this.isReadOnly,
        //配置编辑器默认是否 focus
        autoFocus: this.isAutoFocus,
        MENU_CONF: {
          uploadImage: {
            //上传服务器地址
            // server: 'api/fileresource/file/upload',
            // form-data fieldName ，默认值 'wangeditor-uploaded-image'(也就是参数名)
            // fieldName: 'file',
            // 单个文件的最大体积限制，默认为 2M
            // maxFileSize: 10 * 1024 * 1024, // 10M
            // 最多可上传几个文件，默认为 100
            // maxNumberOfFiles: 10,
            // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
            // allowedFileTypes: ['image/*'],
            // 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
            // meta: {
            //   token: 'xxx',
            //   otherKey: 'yyy'
            // },
            // 将 meta 拼接到 url 参数中，默认 false
            // metaWithUrl: false,
            // 自定义增加 http  header
            // headers: { 'access-control-allow-origin': '*' },
            // 跨域是否传递 cookie ，默认为 false
            // withCredentials: true,
            // 超时时间，默认为 10 秒
            // timeout: 5 * 1000, // 5 秒,
            //自定义插入图片
            // customInsert (res, insertFn) {
            //   console.log(res)
            // }

            // 自定义上传(只有用wangEditor自己的上传才可以用上面的上传配置)
            //因为这个框架用了mock.js拦截了wangEditor的请求（需要改mock.js包里的文件）所以用了自动以的方法上传图片
            async customUpload (file, insertFn) {
              // file 即选中的文件
              // console.log(file)
              //转成formData对象传过去
              let formData = new FormData()
              formData.append('file', file)
              upload(formData).then(res => {
                // console.log(res)
                // 自己实现上传，并得到图片 url alt href
                //拼接图片地址
                // let url = `http://192.168.105.249:12888/${res.data.data.relativePath}`
                let url = globalVariable.serveAddress + res.data.data.relativePath
                let alt = res.data.data.fileName
                // console.log(url, "url")
                // 最后插入图片
                insertFn(url, alt)
              })
            }
          }
        }
      },
      // mode: 'default', // or 'simple'
    }
  },
  methods: {
    //创建富文本编辑器回调
    onCreated (editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
    },
    //改变富文本回调
    onChange (editor) {
      // console.log('onChange', editor.children)
      //查看工具栏的默认配置
      // console.log(DomEditor.getToolbar(editor))
      // console.log(this.html, "html")
      this.$emit("change", this.html); // 将内容同步到父组件中(用v-model拿值就不用这个方法了)
    },
  },
  mounted () { },
  beforeDestroy () {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
})
</script>
<!-- 引入wangeditor样式 -->
<style src="@wangeditor/editor/dist/css/style.css">
</style>
