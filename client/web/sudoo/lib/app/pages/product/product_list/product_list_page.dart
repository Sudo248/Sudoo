import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_data_table.dart';

import '../../../../resources/R.dart';
import '../../../widgets/empty_list.dart';

class ProductListPage extends BasePage<ProductListBloc> {

  @override
  bool get enableStatePage => true;

  @override
  bool get wantKeepAlive => true;

  ProductListPage({super.key});

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
      valueListenable: bloc.totalProducts,
      builder: (context, value, child) => value <= 0
          ? const EmptyList()
          : Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Align(
                    alignment: Alignment.topLeft,
                    child: RichText(
                      text: TextSpan(
                        children: [
                          TextSpan(
                            text: "Total product: ",
                            style: R.style.h4_1.copyWith(
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          TextSpan(
                            text: "$value",
                            style: R.style.h5.copyWith(
                              color: Colors.black,
                            ),
                          )
                        ],
                      ),
                    ),
                  ),
                ),
                child!
              ],
            ),
      child: Expanded(
        child: ProductListDataTable(
          productDataSource: bloc.productDataSource,
        ),
      ),
    );
  }
}
