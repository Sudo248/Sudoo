import 'dart:math';

import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/dialog/claim_income_dialog.dart';
import 'package:sudoo/app/pages/statstic/revenue/revenue_bloc.dart';
import 'package:sudoo/app/widgets/blocks/range_time_block.dart';
import 'package:sudoo/app/widgets/empty_list.dart';
import 'package:sudoo/domain/type_date_picker.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../resources/R.dart';
import '../../../routes/app_routes.dart';

class StatisticRevenuePage extends BasePage<StatisticRevenueBloc> {
  StatisticRevenuePage({super.key});

  @override
  bool get enableStatePage => true;

  final TextStyle style = const TextStyle(fontSize: 16, color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.revenue,
            style: R.style.h4_1.copyWith(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
        ),
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(10.0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                ValueListenableBuilder(
                  valueListenable: bloc.revenue,
                  builder: (context, value, child) => Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          RichText(
                            text: TextSpan(
                              children: [
                                TextSpan(
                                  text: "${R.string.totalRevenue}: ",
                                  style: style.copyWith(
                                    fontSize: 20,
                                    color: Colors.black,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                TextSpan(
                                  text: value.totalRevenue.formatCurrency(),
                                  style: style.copyWith(
                                    fontSize: 20,
                                  ),
                                )
                              ],
                            ),
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          RichText(
                            text: TextSpan(
                              children: [
                                TextSpan(
                                  text: "${R.string.income}: ",
                                  style: style.copyWith(
                                    fontSize: 20,
                                    color: Colors.black,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                TextSpan(
                                  text: value.income.formatCurrency(),
                                  style: style.copyWith(
                                    fontSize: 20,
                                  ),
                                )
                              ],
                            ),
                          )
                        ],
                      ),
                      Column(
                        children: [
                          FilledButton(
                            style: R.buttonStyle.filledButtonStyle(),
                            onPressed: () => _showClaimDialog(context),
                            child: Text(
                              R.string.claim,
                              style: R.style.h5,
                            ),
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          FilledButton(
                            style: R.buttonStyle.filledButtonStyle(
                              backgroundColor: Colors.grey,
                            ),
                            onPressed: () => onHistory(context),
                            child: Text(
                              R.string.history,
                              style: R.style.h5,
                            ),
                          )
                        ],
                      ),
                    ],
                  ),
                ),
                const Divider(
                  color: Colors.blueGrey,
                  height: 50,
                  thickness: 3,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    ValueListenableBuilder(
                      valueListenable: bloc.total,
                      builder: (context, value, child) => RichText(
                        text: TextSpan(
                          children: [
                            TextSpan(
                              text: "${R.string.total}: ",
                              style: style.copyWith(
                                fontSize: 18,
                                color: Colors.black,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            TextSpan(
                              text: value.formatCurrency(),
                              style: style.copyWith(
                                fontSize: 18,
                              ),
                            )
                          ],
                        ),
                      ),
                    ),
                    const Expanded(child: SizedBox.shrink()),
                    Text(
                      "${R.string.statisticBy}: ",
                      style: style.copyWith(fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(
                      width: 5,
                    ),
                    ValueListenableBuilder(
                      valueListenable: bloc.currentCondition,
                      builder: (context, value, child) => DecoratedBox(
                        decoration: BoxDecoration(
                          border: Border.all(
                            color: Colors.grey,
                          ),
                          borderRadius: BorderRadius.circular(5),
                        ),
                        child: DropdownButton<String>(
                          padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 0),
                          value: value.displayValue,
                          underline: const SizedBox.shrink(),
                          items: TypeDatePicker.values
                              .map<DropdownMenuItem<String>>(
                                (e) => DropdownMenuItem(
                                  value: e.displayValue,
                                  child: _buildItemCondition(e),
                                ),
                              )
                              .toList(),
                          selectedItemBuilder: (context) =>
                              TypeDatePicker.values
                                  .map<Widget>(
                                    (e) => _buildItemCondition(e),
                                  )
                                  .toList(),
                          onChanged: (value) => bloc.onChangedCondition(
                            TypeDatePicker.fromDisplayValue(value),
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(
                      width: 30,
                    ),
                    ValueListenableBuilder(
                      valueListenable: bloc.currentCondition,
                      builder: (context, value, child) => RangeTimeBlock(
                        startDate: bloc.fromDate,
                        endDate: bloc.toDate,
                        onSelectedStartTime: (selectedDate) async {
                          bloc.fromDate.value = selectedDate;
                        },
                        onSelectedEndTime: (selectedDate) async {
                          bloc.toDate.value = selectedDate;
                        },
                        firstDate: DateTime(2022),
                        lastDate: DateTime.now(),
                        style: style,
                        type: value,
                      ),
                    ),
                    const SizedBox(
                      width: 30,
                    ),
                    FilledButton(
                      style: R.buttonStyle.filledButtonStyle(),
                      onPressed: bloc.onStatistic,
                      child: Text(
                        R.string.statistic,
                        style: R.style.h5,
                      ),
                    )
                  ],
                ),
                const SizedBox(
                  height: 50,
                ),
                Expanded(
                  child: ValueListenableBuilder(
                    valueListenable: bloc.data,
                    builder: (context, value, child) => value == null
                        ? const Center(
                            child: SizedBox.square(
                              dimension: 30,
                              child: CircularProgressIndicator(),
                            ),
                          )
                        : value.isEmpty
                            ? const EmptyList()
                            : Row(
                                children: [
                                  Expanded(
                                    child: LayoutBuilder(
                                      builder: (context, constraints) =>
                                          BarChart(
                                        BarChartData(
                                          gridData:
                                              const FlGridData(show: false),
                                          barGroups: getBarGroup(
                                            value,
                                            width: min(35, (constraints.maxWidth - (value.keys.length+1)*10) / value.keys.length),
                                          ),
                                          titlesData:
                                              getTitle(value.keys.toList()),
                                          alignment:
                                              BarChartAlignment.spaceAround,
                                          barTouchData: getBarTouchData(),
                                          borderData: borderData,
                                        ),
                                        swapAnimationDuration:
                                            const Duration(seconds: 3),
                                        swapAnimationCurve: Curves.linear,
                                      ),
                                    ),
                                  ),
                                  const SizedBox(
                                    width: 30,
                                  )
                                ],
                              ),
                  ),
                ),
              ],
            ),
          ),
        ),
        const SizedBox(
          height: 30,
        ),
      ],
    );
  }

  List<BarChartGroupData>? getBarGroup(Map<String, dynamic>? data,
      {double width = 1}) {
    if (data == null || data.isEmpty) return null;
    int index = 0;
    return data.values
        .map(
          (value) => BarChartGroupData(
            x: index++,
            groupVertically: true,
            barRods: [
              BarChartRodData(
                toY: value,
                color: R.color.primaryColor,
                borderRadius: const BorderRadius.only(
                  topLeft: Radius.circular(3),
                  topRight: Radius.circular(3),
                ),
                width: width,
                borderSide: BorderSide(
                  color: R.color.primaryColorDark,
                  width: 0.5,
                ),
              )
            ],
          ),
        )
        .toList();
  }

  FlTitlesData getTitle(List<String> keys) {
    return FlTitlesData(
      show: true,
      leftTitles: AxisTitles(
        drawBelowEverything: true,
        sideTitles: SideTitles(
          showTitles: true,
          reservedSize: 130,
          getTitlesWidget: (value, meta) {
            return Text(
              value.formatCurrency(),
              style: style,
              textAlign: TextAlign.center,
              maxLines: 1,
            );
          },
        ),
      ),
      bottomTitles: AxisTitles(
        sideTitles: SideTitles(
            showTitles: true,
            reservedSize: 30,
            getTitlesWidget: (value, meta) {
              return Transform.rotate(
                angle: keys.length > 15 ? 45 : 0,
                alignment: Alignment.centerLeft,
                child: Text(keys[value.toInt()]),
              );
            }),
      ),
      topTitles: const AxisTitles(),
      rightTitles: const AxisTitles(),
    );
  }

  BarTouchData getBarTouchData() {
    return BarTouchData(
      enabled: true,
      touchTooltipData: BarTouchTooltipData(
          tooltipBgColor: Colors.transparent,
          tooltipMargin: 0,
          getTooltipItem: (
            BarChartGroupData group,
            int groupIndex,
            BarChartRodData rod,
            int rodIndex,
          ) {
            return BarTooltipItem(
              rod.toY.formatCurrency(),
              style,
            );
          }),
    );
  }

  FlBorderData get borderData {
    return FlBorderData(
      show: true,
      border: const Border(left: BorderSide(), bottom: BorderSide()),
    );
  }

  Widget _buildItemCondition(TypeDatePicker type) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25, vertical: 5),
      child: Align(
        alignment: Alignment.centerLeft,
        child: Text(
          type.displayValue,
          textAlign: TextAlign.center,
          style: style.copyWith(fontWeight: FontWeight.bold),
        ),
      ),
    );
  }

  void _showClaimDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => ClaimIncomeDialog(onClaim: bloc.onClaim),
    );
  }

  void onHistory(BuildContext context) {
    context.go(AppRoutes.historyTransaction);
  }
}
