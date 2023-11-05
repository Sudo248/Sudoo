import '../../../domain/model/user/address_suggestion.dart';

abstract class ChooseAddressCallback {
  Future<void> onChooseAddress(AddressSuggestion suggestion);
  Future<void> onChooseProvince();
  Future<void> onChooseDistrict();
  Future<void> onChooseWard();

  void setDistrict(int districtId, String districtName);
  void setProvince(int provinceId, String provinceName);
  void setWard(String wardCode, String wardName);
}