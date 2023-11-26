import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:sudoo/app/widgets/online_image.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:url_launcher/url_launcher.dart';

import '../../../resources/R.dart';

class StoreItem extends StatelessWidget {
  final Supplier store;
  const StoreItem({super.key, required this.store});

  @override
  Widget build(BuildContext context) {
    final TextStyle style = R.style.h5.copyWith(color: Colors.black);
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          OnlineImage(
            store.avatar,
            width: 50,
            height: 50,
          ),
          const SizedBox(
            width: 30,
          ),
          Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              ..._buildTextView(R.string.name, store.name, style),
              ..._buildTextView(R.string.contactUrl, store.contactUrl, style),
              ..._buildTextView(
                  R.string.address, store.address.fullAddress, style),
              ..._buildTextView(
                  R.string.totalProduct, "${store.totalProducts ?? 0}", style),
                  _buildRatingBar(store.rate ?? 5),

            ],
          )),
        ],
      ),
    );
  }

  List<Widget> _buildTextView(String label, String value, TextStyle style) {
    return [
      RichText(
        text: TextSpan(
          style: style,
          children: [
            TextSpan(
              style: style.copyWith(fontWeight: FontWeight.bold),
              text: "$label: ",
            ),
            value.startsWith("http")
                ? TextSpan(
                    style: style.copyWith(
                      decoration: TextDecoration.underline,
                      color: Colors.lightBlue,
                    ),
                    recognizer: TapGestureRecognizer()
                      ..onTap = () => _launchUrl(value),
                    text: value,
                  )
                : TextSpan(text: value),
          ],
        ),
      ),
      const SizedBox(
        height: 10,
      ),
    ];
  }

  Widget _buildRatingBar(double rating) {
    return RatingBar.builder(
      itemSize: 20,
      ignoreGestures: true,
      initialRating: rating,
      minRating: 0,
      direction: Axis.horizontal,
      allowHalfRating: true,
      itemCount: 5,
      itemPadding: const EdgeInsets.symmetric(horizontal: 4.0),
      itemBuilder: (context, _) => const Icon(
        Icons.star,
        color: Colors.amber,
      ),
      onRatingUpdate: (rating) {
        
      },
    );
  }

  Future<void> _launchUrl(String url) {
    return launchUrl(Uri.parse(url));
  }
}
