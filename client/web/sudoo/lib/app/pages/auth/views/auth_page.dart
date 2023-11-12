import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/auth/auth_bloc.dart';
import 'package:sudoo/app/pages/auth/auth_form.dart';
import 'package:sudoo/app/pages/auth/views/otp_form.dart';
import 'package:sudoo/app/pages/auth/views/sign_in_form.dart';
import 'package:sudoo/app/pages/auth/views/sign_up_form.dart';

import '../../../../resources/R.dart';

class AuthPage extends BasePage<AuthBloc> {
  AuthPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    bloc.setOnNavigationToDashBoard((path) {
      context.go(path);
    });
    return Scaffold(
      backgroundColor: R.color.primaryColor,
      body: Center(
        child: Container(
          constraints: const BoxConstraints(
            minWidth: 350,
            maxWidth: 450,
            minHeight: 500,
            maxHeight: 700
          ),
          padding: const EdgeInsets.all(20),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(16),
            boxShadow: const <BoxShadow>[
              BoxShadow(
                color: Colors.grey,
                offset: Offset(0, 3),
                blurRadius: 24,
              ),
            ],
          ),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const FlutterLogo(),
              const SizedBox(
                height: 50,
              ),
              Expanded(
                child: ValueListenableBuilder(
                  valueListenable: bloc.form,
                  builder: (ctx, value, child) {
                    switch (value) {
                      case AuthForm.signInForm:
                        return SignInForm(
                          bloc: bloc,
                        );
                      case AuthForm.signUpForm:
                        return SignUpForm(
                          bloc: bloc,
                        );
                      case AuthForm.otpForm:
                        return OtpForm(
                          bloc: bloc,
                        );
                      default:
                        return const SizedBox();
                    }
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
