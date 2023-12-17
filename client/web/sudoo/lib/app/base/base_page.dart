import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/utils/di.dart';

abstract class BasePage<@required T extends BaseBloc> extends StatefulWidget {
  final T _bloc = getIt.get();
  T get bloc => _bloc;

  BasePage({Key? key}) : super(key: key);

  Widget build(BuildContext context);

  @mustCallSuper
  void onInit() {
    bloc.onInit();
  }

  @mustCallSuper
  void onDispose() {
    bloc.onDispose();
  }

  @override
  State<BasePage> createState() => _BasePageState();
}

class _BasePageState extends State<BasePage> {
  @override
  void initState() {
    super.initState();
    widget.onInit();
  }

  @override
  void dispose() {
    super.dispose();
    widget.onDispose();
  }

  @override
  Widget build(BuildContext context) => widget.build(context);
}

