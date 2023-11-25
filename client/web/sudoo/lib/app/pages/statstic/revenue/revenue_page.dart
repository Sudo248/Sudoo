import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/statstic/revenue/revenue_bloc.dart';
import 'package:sudoo/app/widgets/blocks/range_time_block.dart';
import 'package:sudoo/domain/type_date_picker.dart';
import 'package:sudoo/extensions/double_ext.dart';

import '../../../../resources/R.dart';

class StatisticRevenuePage extends BasePage<StatisticRevenueBloc> {
  StatisticRevenuePage({super.key});

  @override
  bool get enableStatePage => true;

  final TextStyle style = const TextStyle(fontSize: 16, color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.orderDetail,
            style: style.copyWith(
              fontWeight: FontWeight.bold,
              fontSize: 20,
            ),
          ),
        ),
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(10.0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    ValueListenableBuilder(
                      valueListenable: bloc.total,
                      builder: (context, value, child) => RichText(
                        text: TextSpan(
                          children: [
                            TextSpan(
                              text: "${R.string.total}: ",
                              style: style.copyWith(
                                fontSize: 20,
                                color: Colors.black,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            TextSpan(
                              text: value.formatCurrency(),
                              style: style.copyWith(
                                fontSize: 20,
                              ),
                            )
                          ],
                        ),
                      ),
                    ),
                    Column(
                      children: [
                        ValueListenableBuilder(
                          valueListenable: bloc.currentCondition,
                          builder: (context, value, child) =>
                              DropdownButton<String>(
                            value: value.value,
                            items: TypeDatePicker.values
                                .map<DropdownMenuItem<String>>(
                                  (e) => DropdownMenuItem(
                                    value: e.value,
                                    child: Text(
                                      e.value,
                                      style: style,
                                    ),
                                  ),
                                )
                                .toList(),
                            onChanged: (value) => bloc.onChangedCondition(
                              TypeDatePicker.fromValue(value),
                            ),
                          ),
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        RangeTimeBlock(
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
                          type: bloc.currentCondition.value,
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        FilledButton(
                          style: R.buttonStyle.filledButtonStyle(),
                          onPressed: bloc.onStatistic,
                          child: Text(
                            R.string.statistic,
                            style: R.style.h5,
                          ),
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                      ],
                    )
                  ],
                ),
                const SizedBox(
                  height: 50,
                ),
                Expanded(
                  child: AspectRatio(
                    aspectRatio: 1.5,
                    child: ValueListenableBuilder(
                      valueListenable: bloc.data,
                      builder: (context, value, child) => value == null
                          ? const Center(
                              child: SizedBox.square(
                                dimension: 30,
                                child: CircularProgressIndicator(),
                              ),
                            )
                          : BarChart(
                              BarChartData(
                                gridData: const FlGridData(show: false),
                                barGroups: getBarGroup(value),
                                titlesData: getTitle(value.keys.toList()),
                                alignment: BarChartAlignment.spaceAround,
                                barTouchData: getBarTouchData(),
                                borderData: borderData,
                              ),
                              swapAnimationDuration:
                                  const Duration(milliseconds: 500),
                              swapAnimationCurve: Curves.linear,
                            ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }

  List<BarChartGroupData>? getBarGroup(Map<String, dynamic>? data) {
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
          reservedSize: 200,
          getTitlesWidget: (value, meta) {
            return Text(
              value.toStringAsFixed(1),
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
                angle: keys.length > 10 ? 45 : 0,
                alignment: Alignment.centerLeft,
                child: Text(
                ),
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
              "${rod.toY}",
              style,
            );
          }),
    );
  }

  FlBorderData get borderData {
    return FlBorderData(
      show: true,
      border: const Border(
        left: BorderSide(
          color: Colors.black,
        ),
      ),
    );
  }
}
