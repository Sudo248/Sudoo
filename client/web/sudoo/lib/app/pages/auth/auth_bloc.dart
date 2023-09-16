import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/auth/auth_form.dart';
import 'package:sudoo/app/widgets/loading_view.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/validator.dart';

class AuthBloc extends BaseBloc {
  final AuthRepository authRepository;
  final formKey = GlobalKey<FormState>();
  final ValueNotifier<AuthForm> form = ValueNotifier(AuthForm.signInForm);
  final TextEditingController emailController = TextEditingController(),
      passwordController = TextEditingController(),
      confirmPasswordController = TextEditingController();
  final LoadingViewController loadingController = LoadingViewController();

  AuthBloc(this.authRepository);

  @override
  void onInit() {}

  String? emailValidator(String? email) {
    if (Validator.validateEmail(email)) {
      return null;
    } else {
      return R.string.invalidEmail;
    }
  }

  String? passwordValidatetor(String? password) {
    if (Validator.validatePassword(password)) {
      return null;
    } else {
      return R.string.invalidPassword;
    }
  }

  void navigateToSignUpForm() {
    formKey.currentState?.reset();
    form.value = AuthForm.signUpForm;
  }

  void navigateToSignInForm() {
    formKey.currentState?.reset();
    form.value = AuthForm.signInForm;
  }

  Future<void> signIn() async {
    // if (formKey.currentState!.validate() == true) {
    // loadingController.showLoading();
    // Account account = Account(
    //   emailOrPhoneNumber: emailController.text,
    //   password: passwordController.text,
    // );
    // // final result = await authRepository.signIn(account);
    // await Future.delayed(Duration(minutes: 2));
    // loadingController.hideLoading();
    // }
    scaffoldMessenger.showErrorMessage("message");
  }

  Future<void> signUp() async {}

  @override
  void onDispose() {
    // TODO: implement onDispose
  }
}
