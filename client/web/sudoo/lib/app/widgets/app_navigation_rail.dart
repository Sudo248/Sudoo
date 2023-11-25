import 'package:flutter/material.dart';
import 'package:sudoo/app/routes/app_router.dart';
import 'package:super_banners/super_banners.dart';

import '../../resources/R.dart';

class AppNavigationRail extends StatelessWidget {
  final ValueChanged<int> onDestinationChanged;

  const AppNavigationRail({super.key, required this.onDestinationChanged});

  @override
  Widget build(BuildContext context) {
    return AppRouter.isAdmin
        ? ValueListenableBuilder(
            valueListenable: AppRouter.adminIndexDashboard,
            builder: (context, selectedIndex, child) => _buildNavigationRail(
              destinations: _getAdminDashboardDestination(),
              selectedIndex: selectedIndex,
              onDestinationChanged: onDestinationChanged,
              bannerMessage: "ADMIN",
            ),
          )
        : ValueListenableBuilder(
            valueListenable: AppRouter.indexDashboard,
            builder: (context, selectedIndex, child) => _buildNavigationRail(
                destinations: _getDashboardDestination(),
                selectedIndex: selectedIndex,
                onDestinationChanged: onDestinationChanged,
                bannerMessage: "STAFF"),
          );
  }

  Widget _buildNavigationRail({
    required List<NavigationRailDestination> destinations,
    required int selectedIndex,
    required ValueChanged<int> onDestinationChanged,
    required String bannerMessage,
  }) {
    return Stack(children: [
      NavigationRail(
        destinations: destinations,
        selectedIndex: selectedIndex,
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
      CornerBanner(
        bannerPosition: CornerBannerPosition.topLeft,
        bannerColor: Colors.orange,
        elevation: 5,
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 10),
          child: Text(
            bannerMessage,
            style: const TextStyle(
              fontSize: 16,
              color: Colors.white,
              decoration: TextDecoration.none,
            ),
          ),
        ),
      )
    ]);
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
        icon: const Icon(Icons.featured_play_list_outlined),
        label: Text(R.string.orders),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.attach_money_outlined),
        label: Text(R.string.revenue),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.store_outlined),
        label: Text(R.string.store),
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
        label: Text(R.string.promotions),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.slideshow_outlined),
        label: Text(R.string.banner),
      ),
      NavigationRailDestination(
        icon: const Icon(Icons.store_outlined),
        label: Text(R.string.stores),
      ),
    ];
  }
}
