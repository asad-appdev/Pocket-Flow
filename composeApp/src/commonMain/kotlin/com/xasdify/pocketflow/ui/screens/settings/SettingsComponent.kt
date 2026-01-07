package com.xasdify.pocketflow.ui.screens.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.xasdify.pocketflow.domain.repository.BackupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

data class SettingsState(
    val isLoading: Boolean = false,
    val lastBackupTime: Long = 0
)

class SettingsComponent(
    componentContext: ComponentContext,
    private val backupRepository: BackupRepository,
    val onBack: () -> Unit
) : ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    private val _state = MutableValue(SettingsState())
    val state: Value<SettingsState> = _state
    
    var onShowMessage: ((String, Boolean) -> Unit)? = null // message, isError
    
    init {
        lifecycle.doOnDestroy { scope.cancel() }
        
        scope.launch {
            backupRepository.getLastBackupTime().collect { time ->
                _state.value = _state.value.copy(lastBackupTime = time)
            }
        }
    }

    fun onBackup() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                backupRepository.createLocalBackup()
                backupRepository.uploadToDrive()
                onShowMessage?.invoke("Backup completed successfully", false)
            } catch (e: Exception) {
                onShowMessage?.invoke("Backup failed: ${e.message}", true)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun onRestore() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                backupRepository.restoreFromDrive()
                onShowMessage?.invoke("Restore completed successfully", false)
            } catch (e: Exception) {
                onShowMessage?.invoke("Restore failed: ${e.message}", true)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
