package com.account.picker.account_picker_plus

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

class AccountPickerPlusPlugin : FlutterPlugin, MethodCallHandler,
    ActivityAware, PluginRegistry.ActivityResultListener {

    private lateinit var activity: FlutterActivity
    private lateinit var channel: MethodChannel
    private lateinit var response: Result

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "account_picker_plus")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        response = result
        when (call.method) {
            "requestPhoneHint" -> {
                getMobileNo(result)
            }
            "requestEmailHint" -> {
                getEmailId(result)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterActivity
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterActivity
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivity() {

    }

    private fun getEmailId(result1: Result) {
        val manager = activity.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        val list: Array<Account> = manager.accounts
        val emailList: ArrayList<String> = ArrayList()
        for (account in list) {
            emailList.add(account.name)
        }
        android.util.Log.i("Emails ", emailList.toString())

        result1.success(emailList)

    }

    private fun getMobileNo(result1: Result) {
        val request = GetPhoneNumberHintIntentRequest.builder().build()
        Identity.getSignInClient(activity)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener {
                activity.startIntentSenderForResult(
                    it.intentSender, 1010101, Intent(), 0, 0, 0
                )
            }
            .addOnFailureListener {
                it.message?.let(fun(_: String) {
                    result1.success("")
                })
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (data != null) {
            if (requestCode == 1010101) {
                val phoneNumber = Identity.getSignInClient(activity)
                    .getPhoneNumberFromIntent(data)
                Log.d("phone ", phoneNumber)
                response.success(phoneNumber)
            }
        }
        return true
    }
}