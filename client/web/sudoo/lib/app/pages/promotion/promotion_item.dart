import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';

import '../../../resources/R.dart';
import '../../widgets/online_image.dart';

class PromotionItem extends StatelessWidget {
  final Promotion promotion;
  final ValueSetter<Promotion>? onItemClick;
  final Future<bool> Function(Promotion)? upsertPromotion;
  final ValueNotifier<bool> enable = ValueNotifier(false);

  PromotionItem({super.key, required this.promotion, this.onItemClick, this.upsertPromotion}) {
    if (promotion.enable != null) {
      enable.value = promotion.enable!;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(15),
      color: Colors.white,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          AspectRatio(
            aspectRatio: 1.0,
            child: OnlineImage(promotion.image),
          ),
          const SizedBox(
            height: 15,
          ),
          Text(
            promotion.name,
            maxLines: 2,
            style: R.style.h5.copyWith(
              color: Colors.black,
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
          promotion.enable != null
              ? ValueListenableBuilder(
                  valueListenable: enable,
                  builder: (context, value, child) => Switch(
                    value: value,
                    onChanged: (value) async {
                      if (value != promotion.enable) {
                        bool? oldValue = promotion.enable;
                        promotion.enable = value;
                        await upsertPromotion?.call(promotion).then((isSuccess) {
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
              : const SizedBox.shrink(),
        ],
      ),
    );
  }
}
