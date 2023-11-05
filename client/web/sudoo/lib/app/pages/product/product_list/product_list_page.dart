import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_table.dart';

import '../../../../resources/R.dart';

class ProductListPage extends BasePage<ProductListBloc> {

  @override
  bool get enableStatePage => true;

  @override
  bool get wantKeepAlive => true;

  ProductListPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: ValueListenableBuilder(
            valueListenable: bloc.totalProducts,
            builder: (context, value, child) {
              return Align(
                alignment: Alignment.topLeft,
                child: Text(
                  "Total: $value",
                  style: R.style.h5.copyWith(
                    color: Colors.red,
                  ),
                ),
              );
            },
          ),
        ),
        Expanded(
          child: ProductListDataTable(
            productDataSource: bloc.productDataSource,
          ),
        )
      ],
    );
  }
}
