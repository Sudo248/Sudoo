import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/repository/storage_repository.dart';
import 'package:sudoo/extensions/list_ext.dart';

import '../../../domain/model/discovery/file.dart';

class SupplierBloc extends BaseBloc {
  final ProductRepository productRepository;
  final StorageRepository storageRepository;

  Supplier? supplier;
  final ValueNotifier<File?> avatar = ValueNotifier(null);
  final TextEditingController nameController = TextEditingController(),
      brandController = TextEditingController(),
      contactUrlController = TextEditingController(),
      provinceController = TextEditingController(),
      districtController = TextEditingController(),
      wardCodeController = TextEditingController(),
      addressController = TextEditingController();

  SupplierBloc(this.productRepository, this.storageRepository);

  @override
  void onDispose() {
    nameController.dispose();
    brandController.dispose();
    contactUrlController.dispose();
    provinceController.dispose();
    districtController.dispose();
    wardCodeController.dispose();
    addressController.dispose();
  }

  @override
  void onInit() {
    getSupplier();
  }

  Future<void> getSupplier() async {
    loadingController.showLoading();
    final result = await productRepository.getSupplier();
    if (result.isSuccess) {
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
      setSupplier(result.get());
    } else {
      showErrorMessage(result.getError());
    }
    loadingController.hideLoading();
  }

  Future<void> onSave() async {
    upsertSupplier(_getSupplier());
  }

  void setSupplier(Supplier supplier) {
    this.supplier = supplier;
    avatar.value = File.fromUrl(supplier.avatar);
    nameController.text = supplier.name;
    brandController.text = supplier.brand;
    districtController.text = supplier.address.districtName;
    provinceController.text = supplier.address.provinceName;
    wardCodeController.text = supplier.address.wardName;
    addressController.text = supplier.address.address;
  }

  Supplier _getSupplier() {
    supplier ??= Supplier.empty();
    supplier!.name = nameController.text;
    supplier!.brand = brandController.text;
    supplier!.address.address = addressController.text;
    return supplier!;
  }

  void setDistrict(int districtId, String districtName) {
    supplier ??= Supplier.empty();
    supplier!.address.districtID = districtId;
    supplier!.address.districtName = districtName;
    districtController.text = districtName;

  }

  void setProvince(int provinceId, String provinceName) {
    supplier ??= Supplier.empty();
    supplier!.address.provinceID = provinceId;
    supplier!.address.provinceName = provinceName;
    provinceController.text = provinceName;
  }

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
