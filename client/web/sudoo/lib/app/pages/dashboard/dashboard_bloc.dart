import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/user/user.dart';
import 'package:sudoo/domain/repository/user_repository.dart';

class DashboardBloc extends BaseBloc {
  final UserRepository userRepository;

  final TextEditingController searchController = TextEditingController();
  final ValueNotifier<int> notificationCount = ValueNotifier(10);
  final ValueNotifier<User?> user = ValueNotifier(null);

  DashboardBloc(this.userRepository);

  @override
  void onInit() {
    getUser();
  }

  @override
  void onDispose() {
  }

  Future<void> logout() async {

  }

  Future<void> getUser() async {
    final result = await userRepository.getUser();
    if (result.isSuccess) {
      user.value = result.get();
    } else {
      showErrorMessage(result.getError());
    }
  }
}
