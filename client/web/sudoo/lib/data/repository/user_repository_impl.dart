import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/data/api/user/user_api_service.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/user/address_suggestion.dart';
import 'package:sudoo/domain/model/user/user.dart';
import 'package:sudoo/domain/repository/user_repository.dart';

class UserRepositoryImpl with HandleResponse implements UserRepository {
  final UserService userService;

  UserRepositoryImpl(this.userService);

  @override
  Future<DataState<List<AddressSuggestion>, Exception>> getSuggestionProvince() async {
    final response = await handleResponse(() => userService.getSuggestionProvince(),
        fromJson: (json) => (json as List<dynamic>)
            .map(
              (e) => AddressSuggestion.fromJson(e as Map<String, dynamic>),
            )
            .toList());

    if (response.isSuccess) {
      final addresses = response.get() as List<AddressSuggestion>;
      return DataState.success(addresses);
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<List<AddressSuggestion>, Exception>> getSuggestionDistrict(int provinceId) async {
    final response = await handleResponse(() => userService.getSuggestionDistrict(provinceId),
        fromJson: (json) => (json as List<dynamic>)
            .map(
              (e) => AddressSuggestion.fromJson(e as Map<String, dynamic>),
        )
            .toList());

    if (response.isSuccess) {
      final addresses = response.get() as List<AddressSuggestion>;
      return DataState.success(addresses);
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<List<AddressSuggestion>, Exception>> getSuggestionWard(int districtId) async {
    final response = await handleResponse(() => userService.getSuggestionWard(districtId),
        fromJson: (json) => (json as List<dynamic>)
            .map(
              (e) => AddressSuggestion.fromJson(e as Map<String, dynamic>),
        )
            .toList());

    if (response.isSuccess) {
      final addresses = response.get() as List<AddressSuggestion>;
      return DataState.success(addresses);
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<User, Exception>> getUser() async {
    final response = await handleResponse(() => userService.getUser(),
        fromJson: (json) => User.fromJson(json as Map<String, dynamic>));
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<User, Exception>> updateUser(User user) async {
    final response = await handleResponse(
      () => userService.updateUser(user),
      fromJson: (json) => User.fromJson(json as Map<String, dynamic>),
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }
}
