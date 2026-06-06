<template>
  <el-container class="layout-container">
    <el-aside width="250px" class="aside">
      <div class="logo">Car Rental System</div>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/">
          <el-icon><DataBoard /></el-icon>
          <span>Dashboard</span>
        </el-menu-item>
        
        <!-- Employee Menu -->
        <template v-if="authStore.isEmployee">
          <el-menu-item index="/cars">
            <el-icon><Van /></el-icon>
            <span>Available Cars</span>
          </el-menu-item>
          <el-menu-item index="/my-bookings">
            <el-icon><Tickets /></el-icon>
            <span>My Bookings</span>
          </el-menu-item>
        </template>

        <!-- Manager Menu -->
        <template v-if="authStore.isManager">
          <el-menu-item index="/department-bookings">
            <el-icon><List /></el-icon>
            <span>Dept. Applications</span>
          </el-menu-item>
          <el-menu-item index="/department-stats">
            <el-icon><PieChart /></el-icon>
            <span>Dept. Statistics</span>
          </el-menu-item>
        </template>

        <!-- Admin Menu -->
        <template v-if="authStore.isAdmin">
          <el-menu-item index="/partners">
            <el-icon><Connection /></el-icon>
            <span>Manage Partners</span>
          </el-menu-item>
          <el-menu-item index="/logs">
            <el-icon><Document /></el-icon>
            <span>System Logs</span>
          </el-menu-item>
          <el-menu-item index="/statistics">
            <el-icon><DataAnalysis /></el-icon>
            <span>API Statistics</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-content">
          <span>{{ currentRouteName }}</span>
          <div class="user-info" v-if="authStore.user">
            <span class="username">{{ authStore.user?.username }}</span>
            <el-button type="danger" size="small" @click="handleLogout" plain>Logout</el-button>
          </div>
          <div class="user-info" v-else>
            <el-button type="primary" size="small" @click="handleLogin">Login</el-button>
          </div>
        </div>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const route = useRoute()
const authStore = useAuthStore()

const activeMenu = computed(() => route.path)
const currentRouteName = computed(() => route.name)

const handleLogout = () => {
  authStore.logoutUser()
}

const handleLogin = () => {
  window.location.href = '/login'
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.aside {
  background-color: #304156;
  color: white;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  background-color: #2b3643;
}
.el-menu-vertical {
  border-right: none;
}
.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  line-height: 60px;
}
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}
.username {
  font-weight: 500;
  color: #333;
}
</style>
