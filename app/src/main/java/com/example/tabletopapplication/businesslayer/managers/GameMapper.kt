package com.example.tabletopapplication.businesslayer.managers

import com.example.tabletopapplication.businesslayer.models.GameEntity

object GameMapper {
    fun map(item: GameEntity): GameEntity = GameEntity(item.image_url, item.name)
}