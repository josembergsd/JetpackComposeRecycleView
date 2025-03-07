package br.com.dnsistemas.jetpackcomposerecycleview.model

import androidx.annotation.DrawableRes

/*
Modelo de dados =  record java
 */

data class SuperHero (
    var superheroName: String,
    var realName: String,
    var publisher: String,
    @DrawableRes var photo: Int
)