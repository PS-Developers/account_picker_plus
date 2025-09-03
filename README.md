# Account Picker Plus

[![pub package](https://img.shields.io/pub/v/account_picker_plus.svg)](https://pub.dev/packages/account_picker_plus)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A lightweight Flutter plugin that allows users to pick email addresses or phone numbers saved on their device without requiring additional permissions. This plugin provides a seamless user experience for account selection in your Flutter applications.

## Features

- 📧 **Email Selection**: Access saved email accounts on the device
- 📱 **Phone Number Hints**: Get phone number suggestions using Google Play Services
- 🔒 **Privacy-Focused**: No extra permissions required
- ⚡ **Lightweight**: Minimal impact on app size
- 🤖 **Android Support**: Currently supports Android devices

## Platform Support

| Platform | Support |
|----------|:-------:|
| Android  |    ✅    |
| iOS      |    ❌    |
| Web      |    ❌    |
| Desktop  |    ❌    |

## Installation

Add this to your package's `pubspec.yaml` file:

```yaml
dependencies:
  account_picker_plus: ^0.0.1
```

Then run:

```bash
flutter pub get
```

## Usage

### Import the package

```dart
import 'package:account_picker_plus/account_picker_plus.dart';
```

### Request Phone Number

Prompt the user to select a phone number from their device:

```dart
try {
  String? phoneNumber = await AccountPickerPlus.phoneHint();
  if (phoneNumber != null && phoneNumber.isNotEmpty) {
    print('Selected phone: $phoneNumber');
    // Use the phone number in your app
  } else {
    print('No phone number selected');
  }
} catch (e) {
  print('Error getting phone number: $e');
}
```

### Request Email Addresses

Get a list of email addresses saved on the device:

```dart
try {
  List<String>? emails = (await AccountPickerPlus.emailHint()).cast<String>();
  if (emails.isNotEmpty) {
    print('Available emails: $emails');
    // Show email selection dialog or use the first email
    String selectedEmail = emails.first;
  } else {
    print('No email accounts found');
  }
} catch (e) {
  print('Error getting emails: $e');
}
```

## Complete Example

```dart
import 'package:flutter/material.dart';
import 'package:account_picker_plus/account_picker_plus.dart';

class AccountPickerDemo extends StatefulWidget {
  @override
  _AccountPickerDemoState createState() => _AccountPickerDemoState();
}

class _AccountPickerDemoState extends State<AccountPickerDemo> {
  String _selectedPhone = '';
  String _selectedEmail = '';

  Future<void> _pickPhoneNumber() async {
    try {
      String? phone = await AccountPickerPlus.phoneHint();
      setState(() {
        _selectedPhone = phone ?? 'No phone selected';
      });
    } catch (e) {
      setState(() {
        _selectedPhone = 'Error: $e';
      });
    }
  }

  Future<void> _pickEmail() async {
    try {
      List<String> emails = (await AccountPickerPlus.emailHint()).cast<String>();
      if (emails.isNotEmpty) {
        // Show selection dialog or use first email
        setState(() {
          _selectedEmail = emails.first;
        });
      }
    } catch (e) {
      setState(() {
        _selectedEmail = 'Error: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Account Picker Plus Demo')),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            ElevatedButton(
              onPressed: _pickPhoneNumber,
              child: Text('Pick Phone Number'),
            ),
            SizedBox(height: 8),
            Text('Phone: $_selectedPhone'),
            SizedBox(height: 24),
            ElevatedButton(
              onPressed: _pickEmail,
              child: Text('Pick Email'),
            ),
            SizedBox(height: 8),
            Text('Email: $_selectedEmail'),
          ],
        ),
      ),
    );
  }
}
```

## Requirements

- **Flutter**: SDK >=2.18.2 <3.0.0
- **Android**: API level 21+ (Android 5.0+)
- **Google Play Services**: Required for phone number hints

## Permissions

This plugin uses minimal permissions:

- `GET_ACCOUNTS` (only for Android API 22 and below) - automatically handled
- No runtime permissions required

## How It Works

### Phone Number Selection
- Uses Google Play Services Identity API
- Presents a system dialog with available phone numbers
- No additional permissions required

### Email Selection
- Accesses device accounts through AccountManager
- Returns all email accounts configured on the device
- Filtered to show only email addresses

## Troubleshooting

### Common Issues

1. **No phone numbers available**: Ensure Google Play Services is installed and updated
2. **Empty email list**: Check if any email accounts are configured on the device
3. **Build errors**: Make sure your `minSdkVersion` is 21 or higher

### Error Handling

Always wrap calls in try-catch blocks:

```dart
try {
  String? phone = await AccountPickerPlus.phoneHint();
  // Handle success
} on PlatformException catch (e) {
  // Handle platform-specific errors
  print('Platform error: ${e.message}');
} catch (e) {
  // Handle other errors
  print('General error: $e');
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a list of changes.

## Support

If you find this plugin helpful, please give it a ⭐ on [GitHub](https://github.com/PS-Developers/account_picker_plus) and a 👍 on [pub.dev](https://pub.dev/packages/account_picker_plus)!

