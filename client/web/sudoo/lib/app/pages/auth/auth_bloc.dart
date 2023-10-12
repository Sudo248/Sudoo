import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/auth/auth_form.dart';
import 'package:sudoo/app/widgets/loading_view.dart';
import 'package:sudoo/domain/model/auth/verify_otp.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/validator.dart';

import '../../../domain/model/auth/account.dart';

class AuthBloc extends BaseBloc {
  final AuthRepository authRepository;
  final formKey = GlobalKey<FormState>();
  final ValueNotifier<AuthForm> form = ValueNotifier(AuthForm.signInForm);
  final TextEditingController emailController = TextEditingController(),
      passwordController = TextEditingController(),
      confirmPasswordController = TextEditingController(),
      otpController = TextEditingController();

  final LoadingViewController loadingController = LoadingViewController();

  late VoidCallback _navigateToDashboard;

  AuthBloc(this.authRepository);

  void setOnNavigationToDashBoard(VoidCallback navigation) {
    _navigateToDashboard = navigation;
  }

  @override
  void onInit() {}

  String? emailValidator(String? email) {
    if (Validator.validateEmail(email)) {
      return null;
    } else {
      return R.string.invalidEmail;
    }
  }

  String? passwordValidator(String? password) {
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
    if (formKey.currentState?.validate() == true) {
      loadingController.showLoading();
      Account account = Account(
        emailOrPhoneNumber: emailController.text,
        password: passwordController.text,
      );
      final result = await authRepository.signIn(account);
      loadingController.hideLoading();
      if (result.isSuccess) {
        _navigateToDashboard.call();
      } else {
        showErrorMessage(result.requireError());
      }
    }
  }

  Future<void> signUp() async {
    if (formKey.currentState?.validate() == true) {
      loadingController.showLoading();
      Account account = Account(
        emailOrPhoneNumber: emailController.text,
        password: passwordController.text,
      );
      final result = await authRepository.signUp(account);
      loadingController.hideLoading();
      if (result.isSuccess) {
        form.value = AuthForm.otpForm;
      } else {
        showErrorMessage(result.requireError());
      }
    }
  }

  Future<void> submitOtp() async {
    loadingController.showLoading();
    VerifyOtp verifyOtp = VerifyOtp(emailController.text, otpController.text);
    final result = await authRepository.submitOtp(verifyOtp);
    if (result.isSuccess) {
      _navigateToDashboard.call();
    } else {
      showErrorMessage(result.requireError());
    }
  }

  @override
  void onDispose() {
    emailController.dispose();
    passwordController.dispose();
    confirmPasswordController.dispose();
    otpController.dispose();
    formKey.currentState?.dispose();
    form.dispose();
    loadingController.dispose();
  }
}
