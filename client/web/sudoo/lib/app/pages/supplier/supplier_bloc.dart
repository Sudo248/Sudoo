import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/repository/storage_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';
import 'package:sudoo/extensions/string_ext.dart';

import '../../../domain/model/discovery/file.dart';
import '../../../domain/model/user/address_suggestion.dart';
import '../../../domain/model/user/choose_address_step.dart';
import '../../../domain/repository/user_repository.dart';
import '../user/choose_address_callback.dart';

class SupplierBloc extends BaseBloc implements ChooseAddressCallback {
  static bool isRegistered = true;
  final ProductRepository productRepository;
  final StorageRepository storageRepository;
  final UserRepository userRepository;

  final ValueNotifier<List<AddressSuggestion>?> suggestion =
      ValueNotifier(null);
  final ValueNotifier<ChooseAddressStep> stepChooseAddress =
      ValueNotifier(ChooseAddressStep.province);

  final ValueNotifier<bool> showInputPhoneNumber = ValueNotifier(true);

  List<AddressSuggestion>? suggestionProvince,
      suggestionDistrict,
      suggestionWard;

  Supplier? supplier;
  final ValueNotifier<File?> avatar = ValueNotifier(null);
  final TextEditingController nameController = TextEditingController(),
      brandController = TextEditingController(),
      contactUrlController = TextEditingController(),
      provinceController = TextEditingController(),
      districtController = TextEditingController(),
      wardCodeController = TextEditingController(),
      addressController = TextEditingController(),
      phoneNumberController = TextEditingController();

  SupplierBloc(
    this.productRepository,
    this.storageRepository,
    this.userRepository,
  );

  @override
  void onDispose() {
    nameController.dispose();
    brandController.dispose();
    contactUrlController.dispose();
    provinceController.dispose();
    districtController.dispose();
    wardCodeController.dispose();
    addressController.dispose();
    phoneNumberController.dispose();
    suggestion.dispose();
    stepChooseAddress.dispose();
    showInputPhoneNumber.dispose();
  }

  @override
  void onInit() {
    getSupplier();
  }

  Future<void> getSupplier() async {
    loadingController.showLoading();
    final result = await productRepository.getSupplier();
    if (result.isSuccess) {
      showInputPhoneNumber.value = false;
      setSupplier(result.get());
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<void> upsertSupplier(Supplier supplier) async {
    loadingController.showLoading();
    if (avatar.value != null && !avatar.value!.bytes.isNullOrEmpty) {
      supplier.avatar = await uploadImage(avatar.value!.bytes!, imageName: avatar.value!.url);
    }
    final result = await productRepository.upsertSupplier(supplier);
    if (result.isSuccess) {
      isRegistered = true;
      setSupplier(result.get());
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<void> onSave() async {
    if (_validateSupplier()) {
      upsertSupplier(_getSupplier());
    }
  }

  bool _validateSupplier() {

    if (supplier == null) {
      showErrorMessage(Exception("Empty supplier"));
      return false;
    }

    if (supplier!.supplierId.isNullOrEmpty && phoneNumberController.text.isNullOrEmpty ) {
      showErrorMessage(Exception("Phone number's supplier is required"));
      return false;
    }

    if (nameController.text.isNullOrEmpty) {
      showErrorMessage(Exception("Name's supplier is required"));
      return false;
    }

    if (brandController.text.isNullOrEmpty) {
      showErrorMessage(Exception("Brand's supplier is required"));
      return false;
    }

    if (provinceController.text.isNullOrEmpty ||
        districtController.text.isNullOrEmpty ||
        wardCodeController.text.isNullOrEmpty ||
        addressController.text.isNullOrEmpty) {
      showErrorMessage(Exception("Address's supplier is required"));
      return false;
    }
    return true;
  }

  void setSupplier(Supplier supplier) {
    this.supplier = supplier;
    avatar.value = File.fromUrl(supplier.avatar);
    nameController.text = supplier.name;
    brandController.text = supplier.brand;
    contactUrlController.text = supplier.contactUrl;
    districtController.text = supplier.address.districtName;
    provinceController.text = supplier.address.provinceName;
    wardCodeController.text = supplier.address.wardName;
    addressController.text = supplier.address.address;
  }

  Supplier _getSupplier() {
    supplier ??= Supplier.empty();
    supplier!.name = nameController.text;
    supplier!.brand = brandController.text;
    supplier!.phoneNumber = phoneNumberController.text;
    supplier!.address.address = addressController.text;
    return supplier!;
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
    if (supplier == null) {
      showErrorMessage(Exception("Supplier was not set"));
      return [];
    }
    if (supplier!.address.provinceID == 0 || supplier!.address.provinceName.isNullOrEmpty) {
      showErrorMessage(Exception("Must choose province before choose district"));
      return [];
    }
    final result = await userRepository.getSuggestionDistrict(supplier!.address.provinceID);
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
    if (supplier == null) {
      showErrorMessage(Exception("Supplier was not set"));
      return [];
    }
    if (supplier!.address.districtID == 0 || supplier!.address.districtName.isNullOrEmpty) {
      showErrorMessage(Exception("Must choose district before choose ward"));
      return [];
    }
    final result = await userRepository.getSuggestionWard(supplier!.address.districtID);
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

  @override
  void setDistrict(int districtId, String districtName) {
    supplier ??= Supplier.empty();
    supplier!.address.districtID = districtId;
    supplier!.address.districtName = districtName;
    districtController.text = districtName;
  }

  @override
  void setProvince(int provinceId, String provinceName) {
    supplier ??= Supplier.empty();
    supplier!.address.provinceID = provinceId;
    supplier!.address.provinceName = provinceName;
    provinceController.text = provinceName;
  }

  @override
  void setWard(String wardCode, String wardName) {
    supplier ??= Supplier.empty();
    supplier!.address.wardCode = wardCode;
    supplier!.address.wardName = wardName;
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
