import 'package:flutter/cupertino.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/type_date_picker.dart';

import '../../../../domain/repository/order_repository.dart';

class StatisticRevenueBloc extends BaseBloc {
  final OrderRepository orderRepository;

  StatisticRevenueBloc(this.orderRepository);

  final ValueNotifier<double> total = ValueNotifier(0.0);
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
    onStatistic();
  }

  void onChangedCondition(TypeDatePicker condition) {
    currentCondition.value = condition;
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
      showErrorMessage(result.getError());
    }
  }
}
