import 'package:flutter/material.dart';

import 'dependency.dart';

class BaseRoute<T> extends MaterialPageRoute<T> {
  BaseRoute({
    required WidgetBuilder builder,
    RouteSettings? settings,
    bool maintainState = true,
    bool fullscreenDialog = false,
    Dependency? dependency,
  }) : super(
          builder: builder,
          settings: settings,
          maintainState: maintainState,
          fullscreenDialog: fullscreenDialog,
        ){
    dependency?.dependencies();
  }
}