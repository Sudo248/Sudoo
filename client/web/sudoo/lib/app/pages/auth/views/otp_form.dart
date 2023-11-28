import 'package:flutter/material.dart';

import '../../../../resources/R.dart';
import '../auth_bloc.dart';

class OtpForm extends StatelessWidget {
  final AuthBloc bloc;

  const OtpForm({
    super.key,
    required this.bloc,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Text(
          R.string.otpAuthentication,
          style: R.style.h3.copyWith(
            color: Colors.black,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(
          height: 20,
        ),
        Text(
          R.string.otpDescription,
          style: R.style.h3.copyWith(
            color: Colors.black,
          ),
        ),
        const SizedBox(
          height: 30,
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 50),
          child: TextFormField(
            controller: bloc.otpController,
            keyboardType: TextInputType.number,
            maxLength: 6,
            decoration: const InputDecoration(
              border: OutlineInputBorder(),
            ),
          ),
        ),
        const SizedBox(
          height: 30,
        ),
        TextButton(
          style: ButtonStyle(
            padding: MaterialStateProperty.all(
              const EdgeInsets.symmetric(horizontal: 50, vertical: 20),
            ),
            backgroundColor: MaterialStateProperty.all(R.color.primaryColor),
          ),
          onPressed: bloc.submitOtp,
          child: Text(
            R.string.submit,
            style: const TextStyle(color: Colors.white),
          ),
        ),
      ],
    );
  }
}
