package dev.davlatov.github_clone_app.ui.home.details.repository.comments

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.davlatov.github_clone_app.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

}