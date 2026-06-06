<template>
  <div class="dashboard">
    <el-card>
      <h2>Welcome to the Corporate Car Rental System</h2>
      <p>Hello, {{ userStore.user?.name || userStore.user?.username }}!</p>
      <p>Your roles: <el-tag v-for="role in userStore.roles" :key="role" style="margin-right: 5px">{{ role }}</el-tag></p>
    </el-card>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'

const userStore = useAuthStore()
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
</style>
