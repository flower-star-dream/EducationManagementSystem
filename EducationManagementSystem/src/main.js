import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// 引入element-plus
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import locale from 'element-plus/es/locale/lang/zh-cn';

// 引入全局样式
import '@/assets/global.css'
import '@/assets/style.css'

// 引入全局iconfont
import '@/assets/icon/iconfont.js'
import '@/assets/icon/iconfont.css'

// 引入element-plus图标
import * as ElIconModules from '@element-plus/icons'

// 引入pinia-plugin-persistedstate
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)
app.use(pinia)

app.use(router)

app.use(ElementPlus, {locale});
for (let iconName in ElIconModules) {
  app.component(iconName, ElIconModules[iconName])
}

app.mount('#app')
