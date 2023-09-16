import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/app/widgets/text_section.dart';
import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/resources/R.dart';

class ProductListItem extends StatelessWidget {
  final Product product;
  final double? width;
  final double? height;

  const ProductListItem({
    super.key,
    required this.product,
    this.width,
    this.height,
  });

  @override
  Widget build(BuildContext context) {
    final TextStyle style = R.style.h4_1;
    return SizedBox(
      width: width ?? double.infinity,
      height: height ?? double.infinity,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Row(
          children: [
            Expanded(
              flex: 1,
              child: OnlineImage(
                product.images[0],
              ),
            ),
            const SizedBox(
              width: 10,
            ),
            Expanded(
              flex: 1,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    product.name,
                    style: R.style.h5.copyWith(
                      color: Colors.black,
                      fontWeight: FontWeight.bold,
                    ),
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  TextSection(
                    label: R.string.sellerSKU,
                    labelTextStyle: style.copyWith(
                      color: Colors.grey.shade600,
                    ),
                    text: product.sku,
                    style: style.copyWith(
                      color: Colors.red,
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  TextSection(
                    label: R.string.amount,
                    labelTextStyle: style.copyWith(
                      color: Colors.grey.shade600,
                    ),
                    text: "${product.amount}",
                    style: style.copyWith(
                      color: Colors.blue,
                    ),
                  )
                ],
              ),
            ),
            Expanded(
              flex: 2,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Table(
                    border: TableBorder.all(),
                    columnWidths: const {
                      0: FlexColumnWidth(1),
                      1: FlexColumnWidth(2.5),
                    },
                    defaultVerticalAlignment: TableCellVerticalAlignment.middle,
                    children: [
                      TableRow(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 10),
                            child: Text(
                              R.string.categories,
                              style: style.copyWith(
                                color: Colors.black,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                          Text("Categories data")
                        ],
                      ),
                      TableRow(children: [
                        Padding(
                          padding: const EdgeInsets.symmetric(vertical: 10),
                          child: Text(
                            R.string.listedPrice,
                            style: style.copyWith(
                              color: Colors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        Text("${product.listedPrice}")
                      ]),
                      TableRow(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 10),
                            child: Text(
                              R.string.price,
                              style: style.copyWith(
                                color: Colors.black,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text("${product.price}"),
                              const SizedBox(
                                height: 10,
                              ),
                              Text(
                                  "${product.startDateDiscount.toString()} to ${product.endDateDiscount.toString()}")
                            ],
                          )
                        ],
                      )
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
