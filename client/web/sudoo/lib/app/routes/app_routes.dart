abstract class AppRoutes {
  static const String root = "/";
  static const String admin = "/admin";
  static const String splash = "/splash";
  static const String auth = "/auth";
  static const String dashboard = "/dashboard";
  static const String user = "/user";

  // DASHBOARD
  static const String home = "/home";
  static const String products = "$dashboard/products";
  static const String productDetail = "$dashboard/product-detail";
  static const String createProduct = "$dashboard/create-product";
  static const String supplier = "$dashboard/supplier-info";
  static const String orders = "$dashboard/orders";
  static const String orderDetail = "$dashboard/order-detail";
  static const String statisticRevenue = "$dashboard/statistic/revenue";

  // ADMIN
  static const String adminCategories = "$admin$dashboard/categories";
  static const String adminPromotions = "$admin$dashboard/promotions";
  static const String adminBanners = "$admin$dashboard/banner";
  static const String adminStores = "$admin$dashboard/stores";
  static const String adminOrder = "$admin$dashboard/orders";
}
