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

  // ADMIN
  static const String adminCategories = "$admin$dashboard/categories";
  static const String adminPromotions = "$admin$dashboard/promotions";
}
