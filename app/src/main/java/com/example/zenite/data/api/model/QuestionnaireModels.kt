package com.example.zenite.data.api.model

import com.google.gson.annotations.SerializedName

data class QuestionnairesResponse(
    @SerializedName("questionnaires") val questionnaires: List<QuestionnaireData>
)

data class QuestionnaireResponse(
    @SerializedName("questionnaire") val questionnaire: QuestionnaireData
)

data class QuestionnaireData(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("questions") val questions: List<QuestionData>
)

data class QuestionData(
    @SerializedName("_id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("type") val type: String? = null,
    @SerializedName("options") val options: List<String>? = null // For multiple choice questions
)

data class AnswerRequest(
    @SerializedName("code") val code: String,
    @SerializedName("answers") val answers: List<AnswerData>
)

data class AnswerData(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("answer") val answer: String
)

data class AnswerSubmissionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("submission") val submission: SubmissionData
)

data class UserAnswersResponse(
    @SerializedName("answers") val answers: List<SubmissionData>
)

data class SubmissionData(
    @SerializedName("_id") val id: String,
    @SerializedName("code") val code: String,
    @SerializedName("questionnaireId") val questionnaireId: String,
    @SerializedName("answers") val answers: List<AnswerWithQuestionData>,
    @SerializedName("submittedAt") val submittedAt: String
)

data class AnswerWithQuestionData(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String
) 