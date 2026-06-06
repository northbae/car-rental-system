<template>
  <div class="logs-view">
    <h2>System Logs</h2>

    <el-table v-loading="loading" :data="logs" style="width: 100%">
      <el-table-column prop="timestamp" label="Timestamp" width="180">
        <template #default="scope">
          {{ new Date(scope.row.timestamp).toLocaleString() }}
        </template>
      </el-table-column>
      <el-table-column prop="level" label="Level" width="100">
        <template #default="scope">
          <el-tag :type="getLevelType(scope.row.level)">{{ scope.row.level }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="service" label="Service" width="150" />
      <el-table-column prop="message" label="Message" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../services/api'

const loading = ref(false)
const logs = ref([])

const fetchLogs = async () => {
  loading.value = true
  try {
    const response = await api.get('/logs')
    logs.value = response.data.content || response.data || []
  } catch (error) {
    ElMessage.error('Failed to fetch logs')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const getLevelType = (level) => {
  switch (level?.toUpperCase()) {
    case 'INFO': return 'info'
    case 'WARN':
    case 'WARNING': return 'warning'
    case 'ERROR': return 'danger'
    case 'DEBUG': return ''
    default: return 'info'
  }
}

onMounted(() => {
  fetchLogs()
})
</script>

<style scoped>
.logs-view {
  padding: 20px;
}
</style>
