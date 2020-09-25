package com.allhebra.seaworld

const val baseUrl = "ws://sea-world.sibext.com/cable"
const val accessToken = "d994fe8e40788ceb4282bb02bab9534fe805b9ca"
const val ACCESS_TOKEN_KEY = "access_token"
const val WORLD_CHANNEL_KEY = "WorldChannel"
const val MAP_KEY = "map"
const val ID_KEY = "id"
const val defaultChannelId = 1

enum class Action(val value: String) {
    RESET("reset"),
    NEXT_STEP("next_step")
}