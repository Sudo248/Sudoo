import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/pages/auth/auth_bloc.dart';

import '../../../../resources/R.dart';
import '../../../widgets/password_form_field.dart';
import '../../../widgets/email_form_field.dart';

class SignUpForm extends StatefulWidget {
  final AuthBloc bloc;

  const SignUpForm({super.key, required this.bloc});

  @override
  State<SignUpForm> createState() => _SignUpFormState();
}

class _SignUpFormState extends State<SignUpForm> {
  final phoneNumberFocus = FocusNode();
  final passwordFocus = FocusNode();
  final confirmPasswordFocus = FocusNode();

  @override
  void dispose() {
    phoneNumberFocus.dispose();
    passwordFocus.dispose();
    confirmPasswordFocus.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: widget.bloc.formKey,
      child: Column(
        children: [
          Text(
            R.string.welcomeTo,
            style: R.style.h5.copyWith(
              color: Colors.grey,
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          Text(
            R.string.signUp,
            style: R.style.h3.copyWith(
              color: Colors.black,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(
            height: 30,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 50),
            child: EmailFormField(
              controller: widget.bloc.emailController,
              validator: widget.bloc.emailValidator,
              nextFocusNode: passwordFocus,
              focusNode: phoneNumberFocus,
            ),
          ),
          const SizedBox(
            height: 30,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 50),
            child: PasswordFormField(
              controller: widget.bloc.passwordController,
              validator: widget.bloc.passwordValidator,
              focusNode: passwordFocus,
              nextForcusNode: confirmPasswordFocus,
            ),
          ),
          const SizedBox(
            height: 30,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 50),
            child: PasswordFormField(
              controller: widget.bloc.confirmPasswordController,
              validator: widget.bloc.passwordValidator,
              label: R.string.confirmPassword,
              focusNode: confirmPasswordFocus,
            ),
          ),
          const SizedBox(
            height: 50,
          ),
          TextButton(
            style: ButtonStyle(
              padding: MaterialStateProperty.all(
                const EdgeInsets.symmetric(horizontal: 50, vertical: 20),
              ),
              backgroundColor: MaterialStateProperty.all(R.color.primaryColor),
            ),
            onPressed: widget.bloc.signUp,
            child: Text(
              R.string.signUp,
              style: const TextStyle(color: Colors.white),
            ),
          ),
          const SizedBox(
            height: 50,
          ),
          Row(
            children: [
              RichText(
                text: TextSpan(
                  style: R.style.h5.copyWith(
                    color: Colors.grey,
                  ),
                  children: <TextSpan>[
                    TextSpan(
                      text: R.string.haveAccount,
                    ),
                    const TextSpan(
                      text: " ",
                    ),
                    TextSpan(
                      text: R.string.signIn,
                      style: R.style.h5.copyWith(
                        color: R.color.primaryColor,
                      ),
                      recognizer: TapGestureRecognizer()
                        ..onTap = widget.bloc.navigateToSignInForm,
                    ),
                  ],
                ),
              ),
            ],
          )
        ],
      ),
    );
  }
}
