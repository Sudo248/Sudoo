import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/auth/auth_bloc.dart';
import 'package:sudoo/app/pages/auth/auth_form.dart';
import 'package:sudoo/app/pages/auth/views/otp_form.dart';
import 'package:sudoo/app/pages/auth/views/sign_in_form.dart';
import 'package:sudoo/app/pages/auth/views/sign_up_form.dart';

import '../../../../resources/R.dart';
import '../../../routes/app_routes.dart';

class AuthPage extends BasePage<AuthBloc> {
  AuthPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    bloc.setOnNavigationToDashBoard((path) {
      context.go(path);
    });
    final size = MediaQuery.sizeOf(context);
    return Scaffold(
      backgroundColor: R.color.primaryColor,
      body: Center(
        child: Container(
          padding: const EdgeInsets.all(20),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(20),
            boxShadow: const <BoxShadow>[
              BoxShadow(
                color: Colors.grey,
                offset: Offset(0, 3),
                blurRadius: 24,
              ),
            ],
          ),
          height: size.height * 0.7,
          width: size.width * 0.25,
          child: Column(
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
