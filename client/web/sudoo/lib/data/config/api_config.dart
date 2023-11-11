abstract class ApiConfig {
  static const String host = "http://service.sudoo.id.vn";
  static const String port = "8080";
  static const String portStorage = "8085";
  static const String baseUrl = "$host/api/v1";
  static const String baseUrlStorage = "$host:$portStorage/api/v1";
  static const int timeout = 20000;
  static const String tokenType = "Bearer";
  static const String tokenName = "Authorization";
  static const String image = "image";
  static const String file = "file";
  static const bool useAdminSite = true;
}
