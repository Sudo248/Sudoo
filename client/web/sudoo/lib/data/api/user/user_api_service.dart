import 'package:sudoo/data/api/api_service.dart';
import 'package:sudoo/domain/model/user/user.dart';

class UserService {
  static const users = "/users";
  static const addresses = "/addresses";
  static const suggestionProvince = "$addresses/suggestion/province";
  static const suggestionDistrict = "$addresses/suggestion/district";
  static const suggestionWard = "$addresses/suggestion/ward";

  final ApiService api;

  const UserService(this.api);

  Future getUser() => api.get(users);
  
  Future updateUser(User user) => api.put(users, request: user);

  Future getSuggestionProvince() => api.get(suggestionProvince);

  Future getSuggestionDistrict(int provinceId) => api.get("$suggestionDistrict/$provinceId");

  Future getSuggestionWard(int districtId) => api.get("$suggestionWard/$districtId");
}