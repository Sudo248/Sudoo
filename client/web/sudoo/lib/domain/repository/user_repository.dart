import 'dart:core';

import 'package:sudoo/domain/model/user/user.dart';

import '../core/data_state.dart';
import '../model/user/address_suggestion.dart';

abstract class UserRepository {
  Future<DataState<User, Exception>> getUser();
  Future<DataState<User, Exception>> updateUser(User user);

  Future<DataState<List<AddressSuggestion>, Exception>> getSuggestionProvince();
  Future<DataState<List<AddressSuggestion>, Exception>> getSuggestionDistrict(int provinceId);
  Future<DataState<List<AddressSuggestion>, Exception>> getSuggestionWard(int districtId);

}