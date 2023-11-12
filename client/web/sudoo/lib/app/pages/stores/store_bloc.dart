import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/repository/product_repository.dart';

class StoresBloc extends BaseBloc {

  final ProductRepository productRepository;

  ValueNotifier<List<Supplier>> suppliers = ValueNotifier([]);

  StoresBloc(this.productRepository);

  @override
  void onDispose() {
    // TODO: implement onDispose
  }

  @override
  void onInit() async {
    loadingController.showLoading();
    await getSuppliers();
    loadingController.hideLoading();
  }

  Future<List<Supplier>> getSuppliers() async {
    final result = await productRepository.getSuppliers();
    if (result.isSuccess) {
      suppliers.value = result.get();
      return Future.value(suppliers.value);
    } else {
      showErrorMessage(result.getError());
      return Future.error(result.getError());
    }
  }

}