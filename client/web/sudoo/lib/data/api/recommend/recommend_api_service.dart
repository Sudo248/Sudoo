
import '../api_service.dart';

class RecommendService {
  static const recommend = "/recommend";
  final ApiService api;

  RecommendService(this.api);

  Future getAllModelVersions() => api.get("$recommend/versions");
}