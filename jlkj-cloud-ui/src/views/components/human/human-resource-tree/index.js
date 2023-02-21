import VueSelector from './humanTree.vue'

const selector = {}
selector.install = function (Vue) {
  Vue.component('kzselector-', VueSelector)
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(selector)
}

export default selector
