import 'package:flutter/material.dart';

import '../../resources/R.dart';

class AppNavigationRail extends StatefulWidget {
  final ValueChanged<int> onDestinationChanged;
  const AppNavigationRail({super.key, required this.onDestinationChanged});

  @override
  State<AppNavigationRail> createState() => _AppNavigationRailState();
}

class _AppNavigationRailState extends State<AppNavigationRail> {
  int _selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    return NavigationRail(
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
      selectedIndex: _selectedIndex,
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
        child: FlutterLogo(size: 80,),
      ),
      // trailing: Expanded(
      //   child: Align(
      //     alignment: Alignment.bottomRight,
      //     child: Padding(
      //       padding: const EdgeInsets.only(bottom: 10),
      //       child: IconButton(
      //         icon: _isExpanded
      //             ? const Icon(Icons.arrow_back_ios_new_rounded)
      //             : const Icon(Icons.arrow_forward_ios_rounded),
      //         color: Colors.black54,
      //         onPressed: () {
      //           setState(() {
      //             _isExpanded = !_isExpanded;
      //           });
      //         },
      //       ),
      //     ),
      //   ),
      // ),
      onDestinationSelected: (value) {
        widget.onDestinationChanged(value);
        setState(() {
          _selectedIndex = value;
        });
      },
    );
  }
}
