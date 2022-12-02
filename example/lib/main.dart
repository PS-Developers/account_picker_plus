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
  @override
  Widget build(BuildContext context) {
    return const MaterialApp(home: AccountPicker());
  }
}

class AccountPicker extends StatefulWidget {
  const AccountPicker({Key? key}) : super(key: key);

  @override
  State<AccountPicker> createState() => _AccountPickerState();
}

class _AccountPickerState extends State<AccountPicker> {
  String phoneNumber1 = '';
  List<String> email1 = [];
  String selectEmail = '';

  Future<void> getEmailId(context) async {
    List<String>? email;
    try {
      email = (await AccountPickerPlus.emailHint()).cast<String>();
      if (email.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('No Email For Your Device..!')));
      } else {
        _settingModalBottomSheet(context);
      }
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
    return Scaffold(
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
                getEmailId(context);
              },
              child: const Text("Email"),
            ),
            Text(selectEmail),
          ],
        ),
      ),
    );
  }

  _settingModalBottomSheet(BuildContext context) {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return Container(
            height: MediaQuery.of(context).size.height / 3,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(10),
            ),
            child: ListView.builder(
                itemCount: email1.length,
                itemBuilder: (_, index) {
                  return Padding(
                    padding: const EdgeInsets.all(8),
                    child: Column(
                      children: [
                        ListTile(
                          leading: const Icon(Icons.alternate_email_rounded),
                          title: Text(email1[index].toString()),
                          onTap: () => {
                            Navigator.pop(context),
                            setState(() {
                              selectEmail = email1[index];
                            }),
                          },
                        ),
                        const Divider()
                      ],
                    ),
                  );
                }),
          );
        });
  }
}
