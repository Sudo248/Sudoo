import 'package:flutter/material.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/widgets/app_navigation_item.dart';

import '../../resources/R.dart';
import '../services/navigator_service.dart';

class AppNavigationRail extends StatefulWidget {
  final NavigatorService navigator;
  const AppNavigationRail({super.key, required this.navigator});

  @override
  State<AppNavigationRail> createState() => _AppNavigationRailState();
}

class _AppNavigationRailState extends State<AppNavigationRail> {
  int _selectedIndex = 0;
  final ExpansionTileController manageProductController = ExpansionTileController();

  @override
  Widget build(BuildContext context) {
    return Material(
      color: R.color.backgroundNavColor,
      child: ListView(
        children: [
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 20),
            child: FlutterLogo(size: 80),
          ),
          AppNavigationItem(
            leading: Icons.home,
            title: R.string.home,
            isSelected: _selectedIndex == 0,
            onTapItem: () {
              if (_selectedIndex == 0) return;
              widget.navigator.navigateTo(AppRoutes.home);
              manageProductController.collapse();
              setState(() {
                _selectedIndex = 0;
              });
            },
          ),
          AppNavigationItem(
            controller: manageProductController,
            leading: Icons.dashboard,
            title: "Manage product",
            isSelected: _selectedIndex == 1,
            onTapItem: () {
              if (_selectedIndex == 1) return;
              setState(() {
                _selectedIndex = 1;
              });
            },
            onTapSubItem: (index) {
              switch (index) {
                case 0:
                  widget.navigator.navigateTo(AppRoutes.productList);
                  break;
                case 1:
                  widget.navigator.navigateTo(AppRoutes.addProduct);
                  break;
                default:
                  break;
              }
            },
            items: const [
              "List Product",
              "Add Product",
              "Request add Product",
            ],
          )
        ],
      ),
    );
  }

  // @override
  // Widget build(BuildContext context) {
  //   return NavigationRail(
  //     destinations: <NavigationRailDestination>[
  //       NavigationRailDestination(
  //         icon: const Icon(Icons.home),
  //         label: Text(R.string.home),
  //       ),
  //       NavigationRailDestination(
  //         icon: const Icon(Icons.chat_rounded),
  //         label: Text(R.string.home),
  //       ),
  //       NavigationRailDestination(
  //         icon: const Icon(Icons.home),
  //         label: Text(R.string.home),
  //       ),
  //     ],
  //     selectedIndex: _selectedIndex,
  //     extended: _isExpanded,
  //     backgroundColor: Colors.white,
  //     unselectedIconTheme: const IconThemeData(color: Colors.black54),
  //     unselectedLabelTextStyle: const TextStyle(color: Colors.black54),
  //     selectedIconTheme: const IconThemeData(color: Colors.white),
  //     selectedLabelTextStyle: TextStyle(color: R.color.primaryColor),
  //     indicatorColor: R.color.primaryColor,
  //     useIndicator: true,
  //     elevation: 5,
  //     leading: const Padding(
  //       padding: EdgeInsets.symmetric(vertical: 10),
  //       child: FlutterLogo(),
  //     ),
  //     trailing: Expanded(
  //       child: Align(
  //         alignment: Alignment.bottomRight,
  //         child: Padding(
  //           padding: const EdgeInsets.only(bottom: 10),
  //           child: IconButton(
  //             icon: _isExpanded
  //                 ? const Icon(Icons.arrow_back_ios_new_rounded)
  //                 : const Icon(Icons.arrow_forward_ios_rounded),
  //             color: Colors.black54,
  //             onPressed: () {
  //               setState(() {
  //                 _isExpanded = !_isExpanded;
  //               });
  //             },
  //           ),
  //         ),
  //       ),
  //     ),
  //     onDestinationSelected: (value) {
  //       widget.onItemClick(value);
  //       setState(() {
  //         _selectedIndex = value;
  //       });
  //     },
  //   );
  // }
}
