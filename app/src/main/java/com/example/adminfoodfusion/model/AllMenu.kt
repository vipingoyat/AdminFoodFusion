package com.example.adminfoodfusion.model

data class AllMenu(
    val foodName:String ?= null,
    val foodPrice:String ?= null,
    val foodDescription:String ?= null,
    val foodIngredients:String ?=null,
    val foodImage:String ?= null,
)
