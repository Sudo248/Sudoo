import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/user/user.dart';

class DashboardBloc extends BaseBloc {
  final TextEditingController searchController = TextEditingController();
  final ValueNotifier<int> notificationCount = ValueNotifier(10);
  final ValueNotifier<User?> user = ValueNotifier(null);

  @override
  void onInit() {}

  @override
  void onDispose() {}

  Future<void> logout() async {

  }
}
