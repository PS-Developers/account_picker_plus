import 'package:flutter/services.dart';

class AccountPickerPlus {
  static const MethodChannel _channel = MethodChannel('account_picker_plus');

  static Future<String?> phoneHint() async {
    final String? phone = await _channel.invokeMethod('requestPhoneHint');
    return phone ?? "";
  }

  static Future<List<Object?>> emailHint() async {
    final List<dynamic> email = await _channel.invokeMethod('requestEmailHint');
    return email;
  }
}
