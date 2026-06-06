<template>
  <div class="dept-bookings">
    <h2>Department Bookings</h2>

    <el-table v-loading="loading" :data="bookings" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="employeeName" label="Employee" />
      <el-table-column prop="carInfo" label="Car">
        <template #default="scope">
           {{ scope.row.carBrand }} {{ scope.row.carModel }} ({{ scope.row.carRegNumber }})
        </template>
      </el-table-column>
      <el-table-column prop="startDate" label="Start Date" />
      <el-table-column prop="endDate" label="End Date" />
      <el-table-column prop="purpose" label="Purpose" />
      <el-table-column prop="status" label="Status">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="200">
        <template #default="scope">
          <div v-if="scope.row.status === 'PENDING_APPROVAL' || scope.row.status === 'PENDING'">
            <el-button size="small" type="success" @click="approve(scope.row.id)">Approve</el-button>
            <el-button size="small" type="danger" @click="openRejectDialog(scope.row)">Reject</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- Reject Dialog -->
    <el-dialog v-model="rejectDialogVisible" title="Reject Booking" width="30%">
      <el-form>
        <el-form-item label="Comment">
          <el-input v-model="rejectComment" type="textarea" :rows="3" placeholder="Reason for rejection" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="rejectDialogVisible = false">Cancel</el-button>
          <el-button type="danger" @click="submitReject">Confirm Reject</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../services/api'

const loading = ref(false)
const bookings = ref([])
const rejectDialogVisible = ref(false)
const rejectComment = ref('')
const currentRejectId = ref(null)

const fetchBookings = async () => {
  loading.value = true
  try {
    const response = await api.get('/applications/department')
    bookings.value = response.data.content || response.data || []
  } catch (error) {
    ElMessage.error('Failed to fetch department bookings')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const getStatusType = (status) => {
  switch (status) {
    case 'APPROVED': return 'success'
    case 'REJECTED': return 'danger'
    case 'PENDING_APPROVAL':
    case 'PENDING': return 'warning'
    case 'CANCELED': return 'info'
    default: return ''
  }
}

const approve = async (id) => {
  try {
    await api.post(`/applications/${id}/approve`)
    ElMessage.success('Booking approved')
    fetchBookings()
  } catch (error) {
    ElMessage.error('Failed to approve booking')
    console.error(error)
  }
}

const openRejectDialog = (row) => {
  currentRejectId.value = row.id
  rejectComment.value = ''
  rejectDialogVisible.value = true
}

const submitReject = async () => {
  if (!rejectComment.value) {
    ElMessage.warning('Please provide a comment for rejection')
    return
  }
  try {
    await api.post(`/applications/${currentRejectId.value}/reject`, null, {
      params: { comment: rejectComment.value }
    })
    ElMessage.success('Booking rejected')
    rejectDialogVisible.value = false
    fetchBookings()
  } catch (error) {
    ElMessage.error('Failed to reject booking')
    console.error(error)
  }
}

onMounted(() => {
  fetchBookings()
})
</script>

<style scoped>
.dept-bookings {
  padding: 20px;
}
</style>
