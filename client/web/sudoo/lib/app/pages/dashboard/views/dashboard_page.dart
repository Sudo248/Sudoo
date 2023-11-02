import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_bloc.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/widgets/app_navigation_rail.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/di.dart';
import 'package:sudoo/app/routes/app_router.dart';

// ignore: must_be_immutable
class DashboardPage extends StatelessWidget {
  final Widget child;
  DashboardPage({Key? key, required this.child})
      : super(key: key ?? const ValueKey("DashboardPageKey"));
  final DashboardBloc bloc = getIt.get();
  void go(BuildContext context, int index) {
    if (index == AppRouter.indexDashboard.value) return;
    AppRouter.indexDashboard.value = index;
    switch (index) {
      case 0:
        context.go(AppRoutes.home);
        break;
      case 1:
        context.go(AppRoutes.products);
        break;
      case 2:
        context.go(AppRoutes.createProduct);
        break;
      default:
        context.go(AppRoutes.home);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Row(
        children: [
          Expanded(
            flex: 1,
            child: AppNavigationRail(onDestinationChanged: (index) => go(context, index)),
          ),
          Expanded(
            flex: 6,
            child: Scaffold(
              appBar: _buildAppBar(context),
              body: child,
            ),
          ),
        ],
      );
  }

  AppBar _buildAppBar(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.white,
      centerTitle: true,
      actions: [
        ValueListenableBuilder(
          valueListenable: bloc.notificationCount,
          builder: (context, value, child) => Badge(
            label: Text(
              "$value",
              style: const TextStyle(color: Colors.white),
            ),
            isLabelVisible: value > 0,
            child: child,
          ),
          child: Icon(
            Icons.notifications,
            color: R.color.primaryColor,
          ),
        ),
        const SizedBox(
          width: 30,
        ),
        const CircleAvatar(
          child: FlutterLogo(),
        ),
        const SizedBox(
          width: 20,
        ),
      ],
      title: SearchBar(
        leading: const Icon(Icons.search),
        constraints: const BoxConstraints(
          minWidth: 300,
          maxWidth: 500,
        ),
        backgroundColor: MaterialStateProperty.all(Colors.grey[300]),
        hintText: "Search",
        elevation: MaterialStateProperty.all(0),
        shape: MaterialStateProperty.all(
          const ContinuousRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(20)),
          ),
        ),
        controller: bloc.searchController,
      ),
      bottom: const PreferredSize(
        preferredSize: Size.fromHeight(0),
        child: Divider(
          height: 0,
        ),
      ),
    );
  }
}
