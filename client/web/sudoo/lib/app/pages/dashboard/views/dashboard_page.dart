import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_page.dart';
import 'package:sudoo/app/routes/app_pages.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/widgets/app_navigation_rail.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/di.dart';

import '../../../../main.dart';

class DashboardPage extends StatelessWidget {
  DashboardPage({super.key});
  final DashboardBloc bloc = getIt.get();

  @override
  Widget build(BuildContext context) {
    print("Sudoo: ${MaterialLocalizations.of(context)}");
    return Row(
      children: [
        Expanded(
          flex: 1,
          child: AppNavigationRail(
            navigator: bloc.navigator,
          ),
        ),
        Expanded(
          flex: 6,
          child: Scaffold(
            appBar: _buildAppBar(),
            body: Navigator(
              key: bloc.navigator.navigatorKey,
              onGenerateRoute: (settings) => AppPages.getPages(settings),
              initialRoute: AppRoutes.productList,
            ),
          ),
        ),
      ],
    );
  }

  AppBar _buildAppBar() {
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
      leading: SearchBar(
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
