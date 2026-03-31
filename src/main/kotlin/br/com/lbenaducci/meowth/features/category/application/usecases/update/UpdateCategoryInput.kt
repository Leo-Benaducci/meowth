package br.com.lbenaducci.meowth.features.category.application.usecases.update

data class UpdateCategoryInput(
    val id: String,
    val name: String,
    val icon: String,
    val color: String
)