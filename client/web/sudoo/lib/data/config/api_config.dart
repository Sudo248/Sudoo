abstract class ApiConfig {
  static const String host = "http://172.16.201.168";
  static const String port = "9030";
  static const String portStorage = "8085";
  static const String baseUrl = "$host:$port/api/v1";
  static const String baseUrlStorage = "$host:$portStorage/api/v1";
  static const int timeout = 5000;
  static const String tokenType = "Bearer";
  static const String tokenName = "Authorization";
  static const String image = "image";
  static const String file = "file";
}
