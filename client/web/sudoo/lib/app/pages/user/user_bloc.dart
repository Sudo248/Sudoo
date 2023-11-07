import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/app/pages/user/choose_address_callback.dart';
import 'package:sudoo/domain/model/user/address_suggestion.dart';
import 'package:sudoo/domain/model/user/choose_address_step.dart';
import 'package:sudoo/domain/model/user/user.dart';
import 'package:sudoo/domain/repository/user_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../data/api/storage/storage_api_service.dart';
import '../../../domain/model/discovery/file.dart';
import '../../../domain/model/user/gender.dart';

class UserBloc extends BaseBloc implements ChooseAddressCallback {
  final UserRepository userRepository;
  final StorageService storageRepository;

  UserBloc(this.userRepository, this.storageRepository);

  final ValueNotifier<List<AddressSuggestion>?> suggestion = ValueNotifier(null);
  final ValueNotifier<ChooseAddressStep> stepChooseAddress = ValueNotifier(ChooseAddressStep.province);

  List<AddressSuggestion>? suggestionProvince, suggestionDistrict, suggestionWard;

  User? user;
  final ValueNotifier<File?> avatar = ValueNotifier(null);
  final ValueNotifier<DateTime> dob = ValueNotifier(DateTime.now());

  final TextEditingController emailController = TextEditingController(),
      nameController = TextEditingController(),
      genderController = TextEditingController(),
      provinceController = TextEditingController(),
      districtController = TextEditingController(),
      wardCodeController = TextEditingController(),
      addressController = TextEditingController();

  @override
  void onDispose() {
    emailController.dispose();
    nameController.dispose();
    genderController.dispose();
    provinceController.dispose();
    districtController.dispose();
    wardCodeController.dispose();
    addressController.dispose();
  }

  @override
  void onInit() {
    getUsers();
  }

  Future<void> getUsers() async {
    loadingController.showLoading();
    final result = await userRepository.getUser();
    if (result.isSuccess) {
      setUser(result.get());
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<void> updateUser(User user) async {
    loadingController.showLoading();
    if (avatar.value != null && !avatar.value!.bytes.isNullOrEmpty) {
      user.avatar = await uploadImage(avatar.value!.bytes!, imageName: avatar.value!.url);
    }
    final result = await userRepository.updateUser(user);
    if (result.isSuccess) {
      setUser(result.get());
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<void> onSave() async {
    updateUser(_getUser());
  }

  @override
  Future<void> onChooseAddress(AddressSuggestion suggestion) async {
    switch(stepChooseAddress.value) {
      case ChooseAddressStep.province:
        setProvince(suggestion.addressId, suggestion.addressName);
        onChooseDistrict();
        break;
      case ChooseAddressStep.district:
        setDistrict(suggestion.addressId, suggestion.addressName);
        onChooseWard();
        break;
      case ChooseAddressStep.ward:
        setWard(suggestion.addressCode, suggestion.addressName);
        stepChooseAddress.value = ChooseAddressStep.finish;
        break;
      default:
    }
  }

  @override
  Future<void> onChooseProvince() async {
    stepChooseAddress.value = ChooseAddressStep.province;
    if (suggestionProvince == null) {
      suggestion.value = null;
      suggestionProvince = await getSuggestionProvince();
    }
    suggestion.value = suggestionProvince;
  }

  @override
  Future<void> onChooseDistrict() async {
    stepChooseAddress.value = ChooseAddressStep.district;
    if (suggestionDistrict == null) {
      suggestion.value = null;
      suggestionDistrict = await getSuggestionDistrict();
    }
    suggestion.value = suggestionDistrict;
  }

  @override
  Future<void> onChooseWard() async {
    stepChooseAddress.value = ChooseAddressStep.ward;
    if (suggestionWard == null) {
      suggestion.value = null;
      suggestionWard = await getSuggestionWard();
    }
    suggestion.value = suggestionWard;
  }

  Future<List<AddressSuggestion>> getSuggestionProvince() async {
    final result = await userRepository.getSuggestionProvince();
    if (result.isSuccess) {
      final address = result.get();
      address.sort(
            (a, b) => a.addressName.compareTo(b.addressName),
      );
      return Future.value(address);
    } else {
      showErrorMessage(result.getError());
      return Future.error(result.getError());
    }
  }

  Future<List<AddressSuggestion>> getSuggestionDistrict() async {
    if (user == null) {
      showErrorMessage(Exception("User was not set"));
      return [];
    }
    if (user!.address.provinceID == 0 || user!.address.provinceName.isNullOrEmpty) {
      showErrorMessage(Exception("Must choose province before choose district"));
      return [];
    }
    final result = await userRepository.getSuggestionDistrict(user!.address.provinceID);
    if (result.isSuccess) {
      final address = result.get();
      address.sort(
            (a, b) => a.addressName.compareTo(b.addressName),
      );
      return Future.value(address);
    } else {
      showErrorMessage(result.getError());
      return Future.error(result.getError());
    }
  }

  Future<List<AddressSuggestion>> getSuggestionWard() async {
    if (user == null) {
      showErrorMessage(Exception("User was not set"));
      return [];
    }
    if (user!.address.districtID == 0 || user!.address.districtName.isNullOrEmpty) {
      showErrorMessage(Exception("Must choose district before choose ward"));
      return [];
    }
    final result = await userRepository.getSuggestionWard(user!.address.districtID);
    if (result.isSuccess) {
      final address = result.get();
      address.sort(
            (a, b) => a.addressName.compareTo(b.addressName),
      );
      return Future.value(address);
    } else {
      showErrorMessage(result.getError());
      return Future.error(result.getError());
    }
  }

  void setUser(User user) {
    this.user = user;
    avatar.value = File.fromUrl(user.avatar);
    emailController.text = user.emailOrPhoneNumber;
    nameController.text = user.fullName;
    genderController.text = user.gender.value;
    dob.value = user.dob;
    provinceController.text = user.address.provinceName;
    districtController.text = user.address.districtName;
    wardCodeController.text = user.address.wardName;
    addressController.text = user.address.address;
  }

  User _getUser() {
    user?.fullName = nameController.text;
    user?.gender = Gender.fromValue(genderController.text);
    user?.dob = dob.value;
    user?.address.address = addressController.text;
    return user!;
  }

  @override
  void setDistrict(int districtId, String districtName) {
    user?.address.districtID = districtId;
    user?.address.districtName = districtName;
    districtController.text = districtName;
  }

  @override
  void setProvince(int provinceId, String provinceName) {
    user?.address.provinceID = provinceId;
    user!.address.provinceName = provinceName;
    provinceController.text = provinceName;
  }

  @override
  void setWard(String wardCode, String wardName) {
    user?.address.wardCode = wardCode;
    user?.address.wardName = wardName;
    wardCodeController.text = wardName;
  }

  Future<String> uploadImage(List<int> bytes, {String? imageName}) async {
    final result = await storageRepository.uploadImageBytes(
      bytes,
      imageName: imageName,
    );
    if (result.isSuccess) {
      final url = result.get();
      return Future.value(url);
    } else {
      showErrorMessage(result.requireError());
      return Future.error(result.requireError());
    }
  }
}
