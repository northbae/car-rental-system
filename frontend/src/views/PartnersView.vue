<template>
  <div class="partners-view">
    <h2>Manage Partners</h2>

    <div style="margin-bottom: 20px">
      <el-button type="primary" @click="openAddDialog">Add Partner</el-button>
    </div>

    <el-table v-loading="loading" :data="partners" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="contactEmail" label="Email" />
      <el-table-column prop="phone" label="Phone" />
      <el-table-column label="Actions" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openEditDialog(scope.row)">Edit</el-button>
          <el-button size="small" type="danger" @click="deletePartner(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Partner Dialog -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Partner' : 'Add Partner'" width="40%">
      <el-form ref="formRef" :model="form" label-width="120px">
        <el-form-item label="Name" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="form.contactEmail" />
        </el-form-item>
        <el-form-item label="Phone">
          <el-input v-model="form.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" :loading="saving" @click="savePartner">Save</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../services/api'

const loading = ref(false)
const partners = ref([])

const dialogVisible = ref(false)
const saving = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  name: '',
  contactEmail: '',
  phone: ''
})

const fetchPartners = async () => {
  loading.value = true
  try {
    const response = await api.get('/partners')
    partners.value = response.data.content || response.data || []
  } catch (error) {
    ElMessage.error('Failed to fetch partners')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const openAddDialog = () => {
  isEdit.value = false
  form.value = { id: null, name: '', contactEmail: '', phone: '' }
  dialogVisible.value = true
}

const openEditDialog = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const savePartner = async () => {
  if (!form.value.name) {
    ElMessage.warning('Name is required')
    return
  }

  saving.value = true
  try {
    if (isEdit.value) {
      await api.put(`/partners/${form.value.id}`, form.value)
      ElMessage.success('Partner updated successfully')
    } else {
      await api.post('/partners', form.value)
      ElMessage.success('Partner added successfully')
    }
    dialogVisible.value = false
    fetchPartners()
  } catch (error) {
    ElMessage.error(`Failed to ${isEdit.value ? 'update' : 'add'} partner`)
    console.error(error)
  } finally {
    saving.value = false
  }
}

const deletePartner = async (id) => {
  try {
    await ElMessageBox.confirm('Are you sure you want to delete this partner?', 'Warning', {
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      type: 'warning',
    })
    
    await api.delete(`/partners/${id}`)
    ElMessage.success('Partner deleted successfully')
    fetchPartners()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('Failed to delete partner')
      console.error(error)
    }
  }
}

onMounted(() => {
  fetchPartners()
})
</script>

<style scoped>
.partners-view {
  padding: 20px;
}
</style>
