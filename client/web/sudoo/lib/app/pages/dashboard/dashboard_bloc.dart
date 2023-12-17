import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';

class DashboardBloc extends BaseBloc {
  final TextEditingController searchController;
  ValueNotifier<int> notificationCount = ValueNotifier(10);

  DashboardBloc() : searchController = TextEditingController();

  @override
  void onInit() {}

  @override
  void onDispose() {}
}
