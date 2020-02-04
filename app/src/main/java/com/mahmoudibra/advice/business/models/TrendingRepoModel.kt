package com.mahmoudibra.advice.business.models

data class TrendingRepoModel(
    var author: String = "",
    var avatar: String = "",
    var forks: Int = 0,
    var language: String = "",
    var name: String = "",
    var stars: Int = 0,
    var url: String = "",
    var isSelected: Boolean = false
)
