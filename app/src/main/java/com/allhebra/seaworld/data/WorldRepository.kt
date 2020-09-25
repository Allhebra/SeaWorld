package com.allhebra.seaworld.data

import android.util.Log
import com.allhebra.seaworld.*
import com.google.gson.Gson
import com.vinted.actioncable.client.kotlin.*
import java.net.URI

class WorldRepository {

    private val uri = URI(baseUrl)
    private val options = Consumer.Options()
    private var consumer: Consumer? = null
    private val worldChannel = Channel(WORLD_CHANNEL_KEY, mapOf(ID_KEY to defaultChannelId))
    private var subscription: Subscription? = null
    lateinit var cb: (cells: Array<Array<String?>>) -> Unit

    init {
        initConnection()
    }

    fun initConnection() {
        options.connection.query = mapOf(ACCESS_TOKEN_KEY to accessToken)
        options.connection.reconnection = true
        consumer = ActionCable.createConsumer(uri, options)
        subscription = consumer?.subscriptions?.create(worldChannel)
        setSubscriptionCallbacks()
        consumer?.connect()
    }

    fun reset() {
        Log.d("reset", "reset")
        subscription?.perform(Action.RESET.value)
    }

    fun nextTurn() {
        Log.d("next_step", "next_step")
        subscription?.perform(Action.NEXT_STEP.value)
    }

    fun disconnect() = consumer?.disconnect()

    private fun setSubscriptionCallbacks() {
        subscription?.onConnected = {
            Log.d("onConnected", "onConnected")
        }
        subscription?.onRejected = {
            Log.d("onRejected", "onRejected")
        }
        subscription?.onReceived = { data: Any? ->
            when (data) {
                is Map<*,*> -> {
                    Log.d("onReceived", "map ${data.keys} to ${data.values}")
                    val cells:Array<Array<String?>> = Gson().fromJson(
                        data[MAP_KEY] as String,
                        Array<Array<String?>>::class.java)
                    cb(cells)
                }
                else -> {
                    Log.d("onReceived", "another $data")
                }
            }
        }
        subscription?.onDisconnected = {
            Log.d("onDisconnected", "onDisconnected")
        }
        subscription?.onFailed = { error ->
            Log.d("onFailed", error.toString())
        }
    }

    fun setCallback(callback: (cells: Array<Array<String?>>) -> Unit) {
        cb = callback
    }
}