import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/auth/auth_form.dart';
import 'package:sudoo/app/pages/supplier/supplier_bloc.dart';
import 'package:sudoo/app/routes/app_router.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/domain/model/auth/role.dart';
import 'package:sudoo/domain/model/auth/verify_otp.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/validator.dart';

import '../../../domain/model/auth/account.dart';

class AuthBloc extends BaseBloc {
  final AuthRepository authRepository;
  final ProductRepository productRepository;
  final formKey = GlobalKey<FormState>();
  final ValueNotifier<AuthForm> form = ValueNotifier(AuthForm.signInForm);
  final TextEditingController emailController = TextEditingController(),
      passwordController = TextEditingController(),
      confirmPasswordController = TextEditingController(),
      otpController = TextEditingController();

  bool? _enableOtp;

  late Function(String) _navigateToDashboard;

  AuthBloc(this.authRepository, this.productRepository);

  void setOnNavigationToDashBoard(Function(String) navigation) {
    _navigateToDashboard = navigation;
  }

  @override
  void onInit() {
    enableOtp();
  }

  String? emailValidator(String? email) {
    if (Validator.isAdmin(emailController.text) ||
        Validator.validateEmail(email)) {
      return null;
    } else {
      return R.string.invalidEmail;
    }
  }

  String? passwordValidator(String? password) {
    if (Validator.isAdmin(emailController.text) ||
        Validator.validatePassword(password)) {
      return null;
    } else {
      return R.string.invalidPassword;
    }
  }

  void navigateToSignUpForm() {
    formKey.currentState?.reset();
    emailController.clear();
    passwordController.clear();
    form.value = AuthForm.signUpForm;
  }

  void navigateToSignInForm() {
    formKey.currentState?.reset();
    emailController.clear();
    passwordController.clear();
    confirmPasswordController.clear();
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
        AppRouter.setAdmin(result.get().role == Role.ADMIN);
        // only check for staff
        if (!AppRouter.isAdmin()) {
          final resultSupplier = await productRepository.getSupplier();
          if (!resultSupplier.isSuccess) {
            SupplierBloc.isRegistered = false;
            _navigateToDashboard.call(AppRoutes.supplier);
            return;
          }
        }
        _navigateToDashboard.call(AppRoutes.home);
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
        showInfoMessage("Sign up is successful");
        if (await enableOtp()) {
          form.value = AuthForm.otpForm;
        } else {
          navigateToSignInForm();
        }
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
      AppRouter.setAdmin(result.get().role == Role.ADMIN);
      if (!AppRouter.isAdmin()) {
        final resultSupplier = await productRepository.getSupplier();
        if (!resultSupplier.isSuccess) {
          _navigateToDashboard.call(AppRoutes.supplier);
          return;
        }
      }
      _navigateToDashboard.call(AppRoutes.home);
    } else {
      showErrorMessage(result.requireError());
    }
  }

  Future<bool> enableOtp() async {
    _enableOtp ??= (await authRepository.getConfig()).get().enableOtp;
    return _enableOtp!;
  }

  @override
  void onDispose() {
    emailController.dispose();
    passwordController.dispose();
    confirmPasswordController.dispose();
    otpController.dispose();
    form.dispose();
    loadingController.dispose();
  }
}
