package com.example.user_feature.presentation

import android.app.assist.AssistStructure
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.util.Log
import android.view.View
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.User
import com.example.user_feature.R
import com.example.user_feature.di.UserFeatureComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class MyAutofillService  : AutofillService() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    @Inject lateinit var userRepository: UserRepository
    private var fetchedUsers = emptyList<User>()

    override fun onCreate() {
        super.onCreate()

        (applicationContext as UserFeatureComponent).injectAutofillService(this)

        serviceScope.launch {
            fetchedUsers = userRepository.usersCredentials.filter { it.email.isNotEmpty() }
        }
    }

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        val context: List<FillContext> = request.fillContexts
        val structure: AssistStructure = context[context.size - 1].structure
        val autofillMap = mutableMapOf<Field, AutofillId>()

        parseStructure(structure, autofillMap)

        if (
            autofillMap.containsKey(Field.EMAIL) &&
            autofillMap.containsKey(Field.PASSWORD) &&
            fetchedUsers.isNotEmpty()
        ) {
            val data: MutableList<Pair<String, String>> = mutableListOf<Pair<String, String>>()

            fetchedUsers.forEachIndexed { index, user ->
                data.add(Pair(user.email, user.password))
            }

            val fillResponse: FillResponse = FillResponse.Builder()
                .apply {
                    data.forEach {
                        val emailPresentation = RemoteViews(
                            packageName,
                            R.layout.custom_service_text_view
                        ).apply { setTextViewText(R.id.text1, it.first) }

                        val passwordPresentation = RemoteViews(
                            packageName,
                            R.layout.custom_service_text_view
                        ).apply { setTextViewText(R.id.text1, it.second) }

                        addDataset(
                            Dataset.Builder()
                                .setValue(
                                    autofillMap[Field.EMAIL]!!,
                                    AutofillValue.forText(it.first),
                                    emailPresentation
                                )
                                .setValue(
                                    autofillMap[Field.PASSWORD]!!,
                                    AutofillValue.forText(it.second),
                                    passwordPresentation
                                )
                                .build()
                        )
                    }
                }
                .build()

            Log.d("waiting for", "")
            callback.onSuccess(fillResponse)
        } else {
            callback.onSuccess(null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onSaveRequest(
        request: SaveRequest,
        callback: SaveCallback
    ) {callback.onSuccess()}

    private fun parseStructure(
        structure: AssistStructure,
        autofillMap: MutableMap<Field, AutofillId>
    ) {
        val windowNodes: List<AssistStructure.WindowNode> =
            structure.run {
                (0 until windowNodeCount).map { getWindowNodeAt(it) }
            }

        windowNodes.forEach { windowNode: AssistStructure.WindowNode ->
            val viewNode: AssistStructure.ViewNode? = windowNode.rootViewNode
            traverseNode(viewNode, autofillMap)
        }
    }

    private fun traverseNode(
        viewNode: AssistStructure.ViewNode?,
        autofillMap: MutableMap<Field, AutofillId>
    ) {
        viewNode?.autofillHints?.forEach { hint->
            when(hint){
                View.AUTOFILL_HINT_EMAIL_ADDRESS -> autofillMap[Field.EMAIL] = viewNode.autofillId!!
                View.AUTOFILL_HINT_PASSWORD -> autofillMap[Field.PASSWORD] = viewNode.autofillId!!
            }
        }

        val children: List<AssistStructure.ViewNode>? =
            viewNode?.run {
                (0 until childCount).map { getChildAt(it) }
            }

        children?.forEach { childNode: AssistStructure.ViewNode ->
            traverseNode(childNode, autofillMap)
        }
    }

    enum class Field{
        EMAIL, PASSWORD
    }
}