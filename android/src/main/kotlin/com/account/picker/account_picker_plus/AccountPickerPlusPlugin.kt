package com.account.picker.account_picker_plus

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentValues
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragmentActivity

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class AccountPickerPlusPlugin : FlutterPlugin, MethodCallHandler,
    ActivityAware{
    private lateinit var channel: MethodChannel
    private lateinit var activity: FlutterFragmentActivity
    private lateinit var response: Result

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "account_picker_plus")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        //response=result
        if (call.method == "requestPhoneHint") {
            Log.e("phone", "loaded")
            result.success("phone")
            //getMobileNo(result1)
        } else if (call.method == "requestEmailHint") {
            Log.e("email", "loaded")
            //result.success("email")
            getEmailId(result)
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterFragmentActivity

    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterFragmentActivity
    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }

    private fun getEmailId(result1: Result) {
        val manager = activity.getSystemService(AppCompatActivity.ACCOUNT_SERVICE) as AccountManager
        val list: Array<Account> = manager.accounts
        val emailList: ArrayList<String> = ArrayList()
        for ( account in list) {
            emailList.add(account.name)
        }
        android.util.Log.i("Emails ",emailList.toString())

        result1.success(emailList)

    }

//    private fun getMobileNo(result1: Result) {
//        val request = GetPhoneNumberHintIntentRequest.builder().build()
//        Identity.getSignInClient(activity)
//            .getPhoneNumberHintIntent(request)
//            .addOnSuccessListener {
//                phoneNumberHintIntentResultLauncher.launch(
//                    IntentSenderRequest.Builder(it.intentSender).build()
//                )
//            }
//            .addOnFailureListener {
//                it.message?.let(fun(it1: String) {
//                    result1.success("")
//                })
//            }
//
//    }
//
//    private val phoneNumberHintIntentResultLauncher: ActivityResultLauncher<IntentSenderRequest> = activity.registerForActivityResult(
//        ActivityResultContracts.StartIntentSenderForResult()
//    ) { results ->
//        try {
//            val phoneNumber = Identity.getSignInClient(activity).getPhoneNumberFromIntent(results.data)
//            android.util.Log.d("phone ",phoneNumber.takeLast(10))
//            response.success(phoneNumber.takeLast(10))
//        } catch (e: Exception) {
//            android.util.Log.d(ContentValues.TAG, e.toString())
//        }
//    }
}

