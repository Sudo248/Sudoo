import 'package:flutter/material.dart';
import 'package:json_annotation/json_annotation.dart';

enum OrderStatus {
  @JsonValue("PREPARE")
  prepare("Prepare", Colors.amber),
  @JsonValue("READY")
  ready("Ready", Colors.orange),
  @JsonValue("TAKE_ORDER")
  takeOrder("Take order", Colors.blueGrey),
  @JsonValue("SHIPPING")
  shipping("Shipping", Colors.blue),
  @JsonValue("DELIVERED")
  delivered("Delivered", Colors.teal),
  @JsonValue("RECEIVED")
  received("Received", Colors.green),
  @JsonValue("CANCELED")
  canceled("Canceled", Colors.red);

  final String value;
  final Color color;

  const OrderStatus(this.value, this.color);

  static OrderStatus? fromValue(String? value) {
    if (value == null) return null;
    switch (value.toUpperCase()) {
      case "PREPARE":
        return OrderStatus.prepare;
      case "READY":
        return ready;
      case "TAKE ORDER":
        return takeOrder;
      case "SHIPPING":
        return shipping;
      case "DELIVERED":
        return delivered;
      case "RECEIVED":
        return received;
      default:
        return OrderStatus.canceled;
    }
  }

  static List<OrderStatus> getEnableStaffStatus() {
    return [
      OrderStatus.prepare,
      OrderStatus.ready,
      OrderStatus.takeOrder,
    ];
  }

  String getSerialization() {
    switch (this) {
      case prepare:
        return "PREPARE";
      case ready:
        return "READY";
      case takeOrder:
        return "TAKE_ORDER";
      case shipping:
        return "SHIPPING";
      case delivered:
        return "DELIVERED";
      case received:
        return "RECEIVED";
      default:
        return "CANCELED";
    }
  }
}
