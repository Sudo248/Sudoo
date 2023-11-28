import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/repository/product_repository.dart';

import '../../../../domain/model/discovery/transaction.dart';

class HistoryTransactionBloc extends BaseBloc {

  final ProductRepository productRepository;

  HistoryTransactionBloc(this.productRepository);

  final ValueNotifier<List<Transaction>> transactions = ValueNotifier([]);

  @override
  void onDispose() {
    // TODO: implement onDispose
  }

  @override
  void onInit() {
    fetchTransactions();
  }

  Future<void> fetchTransactions() async {
    loadingController.showLoading();
    final response = await productRepository.getHistoryTransaction();
    if (response.isSuccess) {
      transactions.value = response.get();
    } else {
      showErrorMessage(response.getError());
    }
    loadingController.hideLoading();
  }

}