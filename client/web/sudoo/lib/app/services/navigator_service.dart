import 'package:flutter/material.dart';

class NavigatorService {
  GlobalKey<NavigatorState> _navigatorKey = GlobalKey<NavigatorState>();

  GlobalKey<NavigatorState> get navigatorKey => _navigatorKey;

  BuildContext? get currentContext => _navigatorKey.currentContext;

  NavigatorState get currentState => _navigatorKey.currentState!;

  Future<T?> navigateTo<T extends Object?>(String routeName,
          {Object? arguments}) =>
      currentState.pushNamed<T>(routeName, arguments: arguments);

  void back<T extends Object?>([T? result]) => currentState.pop<T>(result);

  Future navigateOff(String routeName, {Object? arguments}) =>
      currentState.pushReplacementNamed(routeName, arguments: arguments);

  Future navigateOffUntil<T extends Object?>(
          String routeName, RoutePredicate predicate,
          {Object? arguments}) =>
      currentState.pushNamedAndRemoveUntil<T>(
        routeName,
        predicate,
        arguments: arguments,
      );

  Future navigateOffAll(String routeName, {Object? arguments}) =>
      currentState.pushNamedAndRemoveUntil(routeName, (route) => false,
          arguments: arguments);

  void removeRoute(Route<dynamic> route) => currentState.removeRoute(route);

  void close(int times) {
    if (times < 1) {
      times = 1;
    }
    int count = 0;
    return currentState.popUntil((route) => count++ == times);
  }

  // Object? get arguments {
  //   print("ModalRoute: ${ModalRoute.of(currentState)}");
  //
  //   return currentContext == null ? null : ModalRoute.of(currentContext!)?.settings.arguments;
  // }
}
