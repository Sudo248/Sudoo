import 'package:flutter/material.dart';
import 'package:sudoo/domain/model/user/user.dart';
import 'package:sudoo/extensions/date_time_ext.dart';

import '../../../../../resources/R.dart';

class DeliveryInfoCell extends StatelessWidget {
  final String userFullName;
  final String userPhoneNumber;
  final String address;
  final DateTime expectedReceiveDateTime;
  final TextStyle style;

  const DeliveryInfoCell({
    super.key,
    required this.userFullName,
    required this.userPhoneNumber,
    required this.address,
    required this.expectedReceiveDateTime,
    required this.style,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        RichText(
          maxLines: 1,
          text: TextSpan(
            children: [
              TextSpan(
                text: "${R.string.to}: ",
                style: style.copyWith(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                ),
              ),
              TextSpan(text: userFullName, style: style)
            ],
          ),
        ),
        const SizedBox(
          height: 3,
        ),
        RichText(
          text: TextSpan(
            children: [
              TextSpan(
                text: "${R.string.phoneNumber}: ",
                style: style.copyWith(
                  fontWeight: FontWeight.bold,
                ),
              ),
              TextSpan(text: userPhoneNumber, style: style)
            ],
          ),
        ),
        const SizedBox(
          height: 3,
        ),
        RichText(
          maxLines: 1,
          text: TextSpan(
            children: [
              TextSpan(
                text: "${R.string.deliveryTo}: ",
                style: style.copyWith(
                  fontWeight: FontWeight.bold,
                ),
              ),
              TextSpan(text: address, style: style)
            ],
          ),
        ),
        const SizedBox(
          height: 3,
        ),
        RichText(
          text: TextSpan(
            children: [
              TextSpan(
                text: "${R.string.expectedDelivery}: ",
                style: style.copyWith(
                  fontWeight: FontWeight.bold,
                ),
              ),
              TextSpan(text: expectedReceiveDateTime.formatDate(), style: style)
            ],
          ),
        ),
      ],
    );
  }
}
