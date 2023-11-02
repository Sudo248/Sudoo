import 'package:flutter/material.dart';
import 'package:sudoo/app/routes/app_router.dart';

import '../../resources/R.dart';

class AppNavigationRail extends StatelessWidget {
  final ValueChanged<int> onDestinationChanged;

  const AppNavigationRail({super.key, required this.onDestinationChanged});

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: AppRouter.indexDashboard,
      builder: (context, value, child) => NavigationRail(
        destinations: <NavigationRailDestination>[
          NavigationRailDestination(
            icon: const Icon(Icons.home),
            label: Text(R.string.home),
          ),
          NavigationRailDestination(
            icon: const Icon(Icons.list_alt),
            label: Text(R.string.listProduct),
          ),
          NavigationRailDestination(
            icon: const Icon(Icons.create),
            label: Text(R.string.createProduct),
          ),
        ],
        selectedIndex: value,
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
      ),
    );
  }
}
