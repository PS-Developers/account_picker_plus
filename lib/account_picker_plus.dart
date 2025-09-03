import 'package:flutter/services.dart';

/// A Flutter plugin for picking email addresses and phone numbers from device accounts.
///
/// This plugin provides a simple interface to access saved email accounts and
/// phone number hints without requiring additional permissions.
class AccountPickerPlus {
  static const MethodChannel _channel = MethodChannel('account_picker_plus');

  /// Prompts the user to select a phone number from their device.
  ///
  /// Uses Google Play Services Identity API to show a system dialog
  /// with available phone numbers. Returns the selected phone number
  /// or an empty string if cancelled or no number is available.
  ///
  /// Returns a [Future<String?>] containing the selected phone number.
  ///
  /// Example:
  /// ```dart
  /// String? phone = await AccountPickerPlus.phoneHint();
  /// if (phone != null && phone.isNotEmpty) {
  ///   print('Selected: $phone');
  /// }
  /// ```
  static Future<String?> phoneHint() async {
    final String? phone = await _channel.invokeMethod('requestPhoneHint');
    return phone ?? "";
  }

  /// Retrieves a list of email addresses saved on the device.
  ///
  /// Accesses device accounts through AccountManager and returns
  /// all configured email accounts. No user interaction required.
  ///
  /// Returns a [Future<List<Object?>>] containing email addresses.
  ///
  /// Example:
  /// ```dart
  /// List<String> emails = (await AccountPickerPlus.emailHint()).cast<String>();
  /// if (emails.isNotEmpty) {
  ///   print('Available emails: $emails');
  /// }
  /// ```
  static Future<List<Object?>> emailHint() async {
    final List<dynamic> email = await _channel.invokeMethod('requestEmailHint');
    return email;
  }
}
