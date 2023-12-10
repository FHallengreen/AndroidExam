package com.example.androidexam.model

enum class Category(val displayName: String) {
    GeneralKnowledge("General Knowledge"),
    ScienceComputers("Science: Computers"),
    Geography("Geography"),
    Animals("Animals"),
    Celebrities("Celebrities")
}

enum class Questions(val number: Int) {
    Five(5),
    Ten(10),
    Fifteen(15),
    Twenty(20)
}

enum class Difficulty(val level: String) {
    Any("Any"),
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard")
}
