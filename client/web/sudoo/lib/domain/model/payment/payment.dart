import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/payment/payment_status.dart';

part 'payment.g.dart';

@JsonSerializable()
class Payment {
  final String paymentId;
  final double amount;
  final String paymentType;
  final DateTime paymentDateTime;
  final PaymentStatus status;

  Payment(
    this.paymentId,
    this.amount,
    this.paymentType,
    this.paymentDateTime,
    this.status,
  );

  factory Payment.fromJson(Map<String, dynamic> json) => _$PaymentFromJson(json);

  Map<String, dynamic> toJson() => _$PaymentToJson(this);
}
