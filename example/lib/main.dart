import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:account_picker_plus/account_picker_plus.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String phoneNumber1 = '';
  List<String> email1 = [];

  Future<void> getEmailId() async {
    List<String>? email;
    try {
      email = (await AccountPickerPlus.emailHint()).cast<String>();
    } on PlatformException {
      email = [];
    }
    if (!mounted) return;
    setState(() {
      email1 = email!;
    });
  }

  Future<void> getPhoneNo() async {
    String phoneNumber;
    try {
      phoneNumber =
          await AccountPickerPlus.phoneHint() ?? 'Unknown platform version';
    } on PlatformException {
      phoneNumber = 'Failed to get platform version.';
    }
    if (!mounted) return;
    setState(() {
      phoneNumber1 = phoneNumber;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              MaterialButton(
                color: Colors.blue,
                onPressed: () {
                  getPhoneNo();
                },
                child: const Text("PhoneNo"),
              ),
              Text('$phoneNumber1\n'),
              MaterialButton(
                color: Colors.blue,
                onPressed: () {
                  getEmailId();
                },
                child: const Text("Email"),
              ),
              Text('${email1}'),
            ],
          ),
        ),
      ),
    );
  }
}
