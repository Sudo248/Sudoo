import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/model/discovery/supplier_revenue.dart';
import 'package:sudoo/domain/model/discovery/transaction.dart';
import 'package:sudoo/domain/type_date_picker.dart';

import '../../../../domain/repository/order_repository.dart';
import '../../../../domain/repository/product_repository.dart';

class StatisticRevenueBloc extends BaseBloc {
  final OrderRepository orderRepository;
  final ProductRepository productRepository;

  StatisticRevenueBloc(this.orderRepository, this.productRepository);

  final ValueNotifier<double> total = ValueNotifier(0.0);
  final ValueNotifier<SupplierRevenue> revenue = ValueNotifier(SupplierRevenue(0.0, 0.0));
  final ValueNotifier<DateTime> fromDate = ValueNotifier(DateTime.now());
  final ValueNotifier<DateTime> toDate = ValueNotifier(DateTime.now());

  final ValueNotifier<TypeDatePicker> currentCondition =
      ValueNotifier(TypeDatePicker.day);

  final ValueNotifier<Map<String, dynamic>?> data = ValueNotifier(null);

  @override
  void onDispose() {
    total.dispose();
    fromDate.dispose();
    toDate.dispose();
    currentCondition.dispose();
    data.dispose();
  }

  @override
  void onInit() {
    getSupplierRevenue();
    onStatistic();
  }

  void onChangedCondition(TypeDatePicker condition) {
    currentCondition.value = condition;
  }

  Future<void> getSupplierRevenue() async {
    final result = await productRepository.getSupplierRevenue();
    if (result.isSuccess) {
      revenue.value = result.get();
    } else {
      showErrorMessage(result.getError());
    }
  }

  Future<void> onStatistic() async {
    data.value = null;
    final result = await orderRepository.getStatisticRevenue(
      condition: currentCondition.value,
      from: fromDate.value,
      to: toDate.value,
    );
    if (result.isSuccess) {
      data.value = result.get()["data"] as Map<String, dynamic>;
      total.value = result.get()["total"] as double;
    } else {
      data.value = {};
      showErrorMessage(result.getError());
    }
  }

  Future<void> onClaim(Transaction transaction) async {
    final result = await productRepository.claimSupplierRevenue(transaction);
    if (result.isSuccess) {
      revenue.value = result.get();
    } else {
      showErrorMessage(result.getError());
    }
  }
}
