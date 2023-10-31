import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/home/home_bloc.dart';

class HomePage extends BasePage<HomeBloc> {

  HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text("Wellcome back"));
  }
}