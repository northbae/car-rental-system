import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import router from './router'
import { initKeycloak } from './services/keycloak'
import { useAuthStore } from './stores/auth'

const app = createApp(App)
app.use(createPinia())
app.use(ElementPlus)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

initKeycloak((authenticated, keycloakInstance) => {
  const authStore = useAuthStore()
  if (authenticated) {
    authStore.setUser(keycloakInstance)
  }
  
  // Mount always, so we can see public routes like /register or /login
  app.use(router)
  app.mount('#app')
})
