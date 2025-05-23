package com.example.zenite.ui.screens.personal_evolution

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PersonalEvolutionViewModel @Inject constructor() : ViewModel() {
    
    // Mood data for the weekly line chart
    private val _weeklyMoodData = MutableStateFlow(
        listOf(
            0.9f,  // Sunday - Happy
            0.7f,  // Monday - Slightly happy
            0.8f,  // Tuesday - Happy
            0.5f,  // Wednesday - Neutral
            0.9f,  // Thursday - Happy
            0.3f,  // Friday - Sad
            0.6f   // Saturday - Slightly happy
        )
    )
    val weeklyMoodData: StateFlow<List<Float>> = _weeklyMoodData.asStateFlow()
    
    // Monthly mood percentage data for the bar chart
    private val _monthlyMoodPercentages = MutableStateFlow(
        listOf(
            Pair("Ótimo", 15),
            Pair("Bom", 25),
            Pair("Normal", 15),
            Pair("Ruim", 30),
            Pair("Péssimo", 20)
        )
    )
    val monthlyMoodPercentages: StateFlow<List<Pair<String, Int>>> = _monthlyMoodPercentages.asStateFlow()
    
    // Improvement percentage
    private val _improvementPercentage = MutableStateFlow(20)
    val improvementPercentage: StateFlow<Int> = _improvementPercentage.asStateFlow()
} 