# account_picker_plus

This Flutter plugin prompts user to pick an Email or a Phone number saved on device without requiring extra permission
It is a lightweight plugin that does not require extra permission.
This plugin does not require any special device permission.

This currently works for Android only.


## Usage to request Phone

```dart
String phoneNumber = await AccountPickerPlus.phoneHint();
print(phoneNumber);
```

## Usage to request Email

```dart
List<String>? email = await AccountPickerPlus.emailHint();
print(email);
```

