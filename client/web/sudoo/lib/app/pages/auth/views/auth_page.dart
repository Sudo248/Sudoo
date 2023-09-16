import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/auth/auth_bloc.dart';
import 'package:sudoo/app/pages/auth/auth_form.dart';
import 'package:sudoo/app/pages/auth/views/sign_in_form.dart';
import 'package:sudoo/app/pages/auth/views/sign_up_form.dart';
import 'package:sudoo/app/widgets/loading_view.dart';

import '../../../../resources/R.dart';

class AuthPage extends BasePage<AuthBloc> {
  AuthPage({super.key});

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.sizeOf(context);
    return Scaffold(
      backgroundColor: R.color.primaryColor,
      body: Stack(
        children: [
          Center(
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
                        if (value == AuthForm.signInForm) {
                          return SignInForm(
                            bloc: bloc,
                          );
                        } else {
                          return SignUpForm(
                            bloc: bloc,
                          );
                        }
                      },
                    ),
                  ),
                ],
              ),
            ),
          ),
          LoadingView(
            controller: bloc.loadingController,
          ),
        ],
      ),
    );
  }
}
