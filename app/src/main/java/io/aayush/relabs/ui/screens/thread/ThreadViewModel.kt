package io.aayush.relabs.ui.screens.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.post.Post
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.utils.DesignQuoteSpan
import io.aayush.relabs.utils.LinkTransformationMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository,
    val linkTransformationMethod: LinkTransformationMethod,
    val designQuoteSpan: DesignQuoteSpan
) : ViewModel() {

    private var currentPage = 0

    private val _threadInfo = MutableStateFlow(ThreadInfo())
    val threadInfo = _threadInfo.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(mutableListOf())
    val posts = _posts.asStateFlow()

    fun getThreadInfo(threadID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _threadInfo.value = xenforoRepository.getThreadInfo(threadID, with_posts = true)
            _posts.value = _threadInfo.value.posts
        }
    }

    fun getPosts() {
        if (_threadInfo.value.thread.thread_id != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = xenforoRepository.getThreadInfo(
                    _threadInfo.value.thread.thread_id,
                    with_posts = true,
                    page = ++currentPage
                )
            }
        }
    }
}