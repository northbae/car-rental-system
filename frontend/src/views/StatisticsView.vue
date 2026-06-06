<template>
  <div class="statistics-view">
    <h2>API Usage Statistics</h2>

    <el-table v-loading="loading" :data="stats" style="width: 100%">
      <el-table-column prop="endpoint" label="Endpoint" />
      <el-table-column prop="method" label="Method" width="100">
        <template #default="scope">
          <el-tag>{{ scope.row.method }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="requestCount" label="Requests Count" width="150" />
      <el-table-column prop="averageResponseTime" label="Avg Response Time (ms)" width="200" />
      <el-table-column prop="errorCount" label="Errors Count" width="150" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../services/api'

const loading = ref(false)
const stats = ref([])

const fetchStatistics = async () => {
  loading.value = true
  try {
    const response = await api.get('/statistics')
    stats.value = response.data.content || response.data || []
  } catch (error) {
    ElMessage.error('Failed to fetch statistics')
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchStatistics()
})
</script>

<style scoped>
.statistics-view {
  padding: 20px;
}
</style>
