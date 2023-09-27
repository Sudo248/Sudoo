import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/pages/auth/auth_bloc.dart';
import 'package:sudoo/app/widgets/password_form_field.dart';
import 'package:sudoo/app/widgets/email_form_field.dart';
import 'package:sudoo/resources/R.dart';

class SignInForm extends StatefulWidget {
  final AuthBloc bloc;

  const SignInForm({
    super.key,
    required this.bloc,
  });

  @override
  State<SignInForm> createState() => _SignInFormState();
}

class _SignInFormState extends State<SignInForm> {
  final phoneNumberFocus = FocusNode();
  final passwordFocus = FocusNode();

  @override
  void dispose() {
    phoneNumberFocus.dispose();
    passwordFocus.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: widget.bloc.formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            R.string.welcomeBack,
            style: R.style.h5.copyWith(
              color: Colors.grey,
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          Text(
            R.string.signIn,
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
            onPressed: widget.bloc.signIn,
            child: Text(
              R.string.signIn,
              style: const TextStyle(color: Colors.white),
            ),
          ),
          const SizedBox(
            height: 50,
          ),
          Row(
            children: <Widget>[
              RichText(
                text: TextSpan(
                  style: R.style.h5.copyWith(
                    color: Colors.grey,
                  ),
                  children: <TextSpan>[
                    TextSpan(
                      text: R.string.dontHaveAcount,
                    ),
                    const TextSpan(text: " "),
                    TextSpan(
                      text: R.string.signUp,
                      style: R.style.h5.copyWith(
                        color: R.color.primaryColor,
                      ),
                      recognizer: TapGestureRecognizer()
                        ..onTap = widget.bloc.navigateToSignUpForm,
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
