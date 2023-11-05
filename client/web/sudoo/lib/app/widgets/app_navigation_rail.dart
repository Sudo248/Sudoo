import 'package:flutter/material.dart';
import 'package:sudoo/app/routes/app_router.dart';

import '../../resources/R.dart';

class AppNavigationRail extends StatelessWidget {
  final ValueChanged<int> onDestinationChanged;

  const AppNavigationRail({super.key, required this.onDestinationChanged});

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: AppRouter.isAdmin.value ? AppRouter.adminIndexDashboard : AppRouter.indexDashboard,
      builder: (context, value, child) {
        print("Sudoo: AppNavigationRail index: $value");
        return NavigationRail(
          destinations: AppRouter.isAdmin.value ? _getAdminDashboardDestination() : _getDashboardDestination(),
          selectedIndex: value >= 0 ? value : 0,
          extended: true,
          backgroundColor: R.color.backgroundNavColor,
          unselectedIconTheme: const IconThemeData(color: Colors.grey),
          unselectedLabelTextStyle: const TextStyle(color: Colors.grey),
          selectedIconTheme: const IconThemeData(color: Colors.white),
          selectedLabelTextStyle: const TextStyle(color: Colors.white),
          indicatorColor: R.color.backgroundNavColor,
          useIndicator: true,
          elevation: 5,
          leading: const Padding(
            padding: EdgeInsets.symmetric(vertical: 20),
            child: FlutterLogo(
              size: 80,
            ),
          ),
          onDestinationSelected: (value) {
            onDestinationChanged(value);
          },
        );
      },
    );
  }

  List<NavigationRailDestination> _getDashboardDestination() {
    return [
      NavigationRailDestination(
        icon: const Icon(Icons.home),
        label: Text(R.string.home),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.list_alt_outlined),
        label: Text(R.string.listProduct),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.create_outlined),
        label: Text(R.string.createProduct),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.info_outline),
        label: Text(R.string.info),
      ),
    ];
  }

  List<NavigationRailDestination> _getAdminDashboardDestination() {
    return [
      NavigationRailDestination(
        icon: const Icon(Icons.home),
        label: Text(R.string.home),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.category_outlined),
        label: Text(R.string.categories),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.discount_outlined),
        label: Text(R.string.categories),
      ),
    ];
  }
}
