import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';

import '../../../resources/R.dart';
import '../../widgets/online_image.dart';

class PromotionItem extends StatelessWidget {
  final Promotion promotion;
  final ValueSetter<Promotion>? onItemClick;
  final Future<bool> Function(Promotion)? upsertPromotion;
  final ValueNotifier<bool> enable = ValueNotifier(false);

  PromotionItem(
      {super.key,
      required this.promotion,
      this.onItemClick,
      this.upsertPromotion}) {
    if (promotion.enable != null) {
      enable.value = promotion.enable!;
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => onItemClick?.call(promotion),
      child: Container(
        padding: const EdgeInsets.all(15),
        decoration: BoxDecoration(
          color: Colors.white,
          border: Border.all(color: Colors.grey),
          borderRadius: BorderRadius.circular(10.0),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SizedBox.square(
                  dimension: 100,
                  child: OnlineImage(promotion.image),
                )
              ],
            ),
            const SizedBox(
              height: 15,
            ),
            Text(
              promotion.name,
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
              style: R.style.h5.copyWith(
                color: Colors.black,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              "Total amount: ${promotion.totalAmount}",
              maxLines: 1,
              style: R.style.h5.copyWith(
                color: Colors.black,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              "Value: ${promotion.value.toStringAsFixed(1)}đ",
              maxLines: 1,
              style: R.style.h5.copyWith(
                color: Colors.black,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            promotion.enable != null
                ? Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Text(
                        "Enable: ",
                        style: R.style.h5.copyWith(color: Colors.black),
                      ),
                      const SizedBox(
                        width: 10,
                      ),
                      ValueListenableBuilder(
                        valueListenable: enable,
                        builder: (context, value, child) => Switch(
                          value: value,
                          onChanged: (value) async {
                            if (value != promotion.enable) {
                              bool? oldValue = promotion.enable;
                              promotion.enable = value;
                              await upsertPromotion
                                  ?.call(promotion)
                                  .then((isSuccess) {
                                if (isSuccess) {
                                  enable.value = value;
                                } else {
                                  promotion.enable = oldValue;
                                }
                              });
                            }
                          },
                          thumbColor: MaterialStateProperty.all(Colors.white),
                          activeTrackColor: Colors.red,
                          inactiveTrackColor: Colors.grey,
                        ),
                      )
                    ],
                  )
                : const SizedBox.shrink(),
          ],
        ),
      ),
    );
  }
}
