import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/stores/store_bloc.dart';
import 'package:sudoo/app/pages/stores/store_item.dart';
import 'package:sudoo/app/widgets/empty_list.dart';

import '../../../resources/R.dart';

class StoresPage extends BasePage<StoresBloc> {
  StoresPage({super.key});

  @override
  bool get enableStatePage => true;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
          child: Text(
            R.string.listStore,
            style: R.style.h4_1.copyWith(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
        ),
        const SizedBox(
          height: 10,
        ),
        Expanded(
          child: ValueListenableBuilder(
            valueListenable: bloc.suppliers,
            builder: (context, stores, child) => Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Align(
                    alignment: Alignment.topLeft,
                    child: RichText(
                      text: TextSpan(
                        children: [
                          TextSpan(
                            text: "${R.string.total}: ",
                            style: R.style.h5.copyWith(
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          TextSpan(
                              text: "${stores.length}",
                              style: R.style.h5.copyWith(
                                color: Colors.black,
                              ))
                        ],
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: stores.isEmpty
                      ? const EmptyList()
                      : ListView.separated(
                          itemBuilder: (context, index) =>
                              StoreItem(store: stores[index]),
                          separatorBuilder: (context, index) => const SizedBox(
                            width: double.infinity,
                            height: 10,
                            child: ColoredBox(
                              color: Color(0xFFE4EAF3),
                            ),
                          ),
                          itemCount: stores.length,
                        ),
                ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}