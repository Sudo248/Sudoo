import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_bloc.dart';
import 'package:sudoo/app/routes/app_router.dart';
import 'package:sudoo/app/routes/app_routes.dart';
import 'package:sudoo/app/widgets/app_navigation_rail.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/resources/R.dart';

// ignore: must_be_immutable
class DashboardPage extends BasePage<DashboardBloc> {
  final Widget child;
  final MenuStyle menuStyle = MenuStyle(
    padding: MaterialStateProperty.all<EdgeInsetsGeometry?>(
      const EdgeInsets.only(top: 15, bottom: 20),
    ),
    backgroundColor:
        MaterialStateProperty.all<Color?>(R.color.backgroundMenuColor),
    shape: MaterialStateProperty.all<OutlinedBorder>(
      RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(5.0),
      ),
    ),
  );

  @override
  bool get wantKeepAlive => true;

  DashboardPage({Key? key, required this.child})
      : super(key: key ?? const ValueKey("DashboardPageKey"));

  void go(BuildContext context, int index) {
    if (AppRouter.isAdmin()) {
      if (index == AppRouter.adminIndexDashboard.value) return;
      AppRouter.adminIndexDashboard.value = index;
      switch (index) {
        case 0:
          _dashboardGo(context, AppRoutes.home);
          break;
        case 1:
          _dashboardGo(context, AppRoutes.adminCategories);
          break;
        case 2:
          _dashboardGo(context, AppRoutes.adminPromotions);
          break;
        case 3:
          _dashboardGo(context, AppRoutes.adminBanners);
          break;
        case 4:
          _dashboardGo(context, AppRoutes.adminStores);
          break;
        case 5:
          _dashboardGo(context, AppRoutes.adminModel);
          break;
        default:
          context.go(AppRoutes.home);
      }
    } else {
      if (index == AppRouter.indexDashboard.value) return;
      AppRouter.indexDashboard.value = index;
      switch (index) {
        case 0:
          _dashboardGo(context, AppRoutes.home);
          break;
        case 1:
          _dashboardGo(context, AppRoutes.products);
          break;
        case 2:
          _dashboardGo(context, AppRoutes.createProduct);
          break;
        case 3:
          _dashboardGo(context, AppRoutes.orders);
          break;
        case 4:
          _dashboardGo(context, AppRoutes.statisticRevenue);
        case 5:
          _dashboardGo(context, AppRoutes.supplier);
          break;
        default:
          _dashboardGo(context, AppRoutes.home);
      }
    }
  }

  void _dashboardGo(BuildContext context, String path) {
    context.go(path);
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
        // ValueListenableBuilder(
        //   valueListenable: bloc.notificationCount,
        //   builder: (context, value, child) => Badge(
        //     label: Text(
        //       "$value",
        //       style: const TextStyle(color: Colors.white),
        //     ),
        //     isLabelVisible: value > 0,
        //     child: child,
        //   ),
        //   child: Icon(
        //     Icons.notifications,
        //     color: R.color.primaryColor,
        //   ),
        // ),
        // const SizedBox(
        //   width: 30,
        // ),
        ValueListenableBuilder(
            valueListenable: bloc.user,
            builder: (context, value, child) {
              return CircleAvatar(
                child: value == null
                    ? const Padding(
                      padding: EdgeInsets.all(3.0),
                      child: CircularProgressIndicator(),
                    )
                    : MenuAnchor(
                        style: menuStyle,
                        alignmentOffset: const Offset(-10, 5),
                        builder: (context, controller, child) =>
                            GestureDetector(
                          onTap: () {
                            if (controller.isOpen) {
                              controller.close();
                            } else {
                              controller.open();
                            }
                          },
                          child: child,
                        ),
                        menuChildren: <Widget>[
                          MenuItemButton(
                            style: ButtonStyle(
                                padding: MaterialStateProperty.all<EdgeInsetsGeometry?>(
                                  const EdgeInsets.only(right: 80, left: 15),
                                )
                            ),
                            leadingIcon: const Icon(Icons.info_outline),
                            child: const Text(
                              "Thông tin người dùng",
                              style: TextStyle(fontSize: 16),
                            ),
                            onPressed: () => context.go(AppRoutes.user),
                          ),
                          MenuItemButton(
                            style: ButtonStyle(
                                padding: MaterialStateProperty.all<
                                    EdgeInsetsGeometry?>(
                              const EdgeInsets.only(right: 100, left: 15),
                            )),
                            leadingIcon: const Icon(Icons.logout),
                            child: const Text(
                              "Đăng xuất",
                              style: TextStyle(fontSize: 16),
                            ),
                            onPressed: () => bloc.logout().then((value) =>
                                context.pushReplacement(AppRoutes.auth)),
                          )
                        ],
                        child: OnlineImage(
                          value.avatar,
                          fit: BoxFit.cover,
                        ),
                      ),
              );
            }),
        const SizedBox(
          width: 20,
        ),
      ],
      // title: SearchBar(
      //   leading: const Icon(Icons.search),
      //   constraints: const BoxConstraints(
      //     minWidth: 300,
      //     maxWidth: 500,
      //   ),
      //   backgroundColor: MaterialStateProperty.all(Colors.grey[300]),
      //   hintText: "Search",
      //   elevation: MaterialStateProperty.all(0),
      //   shape: MaterialStateProperty.all(
      //     const ContinuousRectangleBorder(
      //       borderRadius: BorderRadius.all(Radius.circular(20)),
      //     ),
      //   ),
      //   controller: bloc.searchController,
      // ),
      bottom: const PreferredSize(
        preferredSize: Size.fromHeight(0),
        child: Divider(
          height: 0,
        ),
      ),
    );
  }
}
