
import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';

part 'transaction.g.dart';

@JsonSerializable()
class Transaction implements BaseRequest {
  final String transactionId;
  final String ownerId;
  final double amount;
  final String description;

  Transaction(this.transactionId, this.ownerId, this.amount, this.description);

  factory Transaction.create({required double amount, required String description}) {
    return Transaction("", "", amount, description);
  }

  factory Transaction.fromJson(Map<String, dynamic> json) => _$TransactionFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$TransactionToJson(this);
}