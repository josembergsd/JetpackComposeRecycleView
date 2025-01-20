package br.com.dnsistemas.jetpackcomposerecycleview.model

import androidx.annotation.DrawableRes

/*
Modelo de dados
 */

data class SuperHero (
    var superheroName: String,
    var realName: String,
    var publicher: String,
    @DrawableRes var photo: Int
)