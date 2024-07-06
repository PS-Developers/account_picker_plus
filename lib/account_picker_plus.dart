import 'package:flutter/services.dart';

/// AccountPickerPlus
class AccountPickerPlus {
  static const MethodChannel _channel = MethodChannel('account_picker_plus');

  /// Get Phone number
  static Future<String?> phoneHint() async {
    final String? phone = await _channel.invokeMethod('requestPhoneHint');
    return phone ?? "";
  }

  /// Get Email data from list
  static Future<List<Object?>> emailHint() async {
    final List<dynamic> email = await _channel.invokeMethod('requestEmailHint');
    return email;
  }
}
