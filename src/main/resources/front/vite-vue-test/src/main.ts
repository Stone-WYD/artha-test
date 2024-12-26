
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
// 加入一些通用的 css 样式
import '@/styles/common.scss';

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
