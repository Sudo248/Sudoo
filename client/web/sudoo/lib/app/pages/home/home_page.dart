import 'package:flutter/material.dart';
import 'package:sudoo/app/widgets/empty_list.dart';

import '../../../resources/R.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    final style = R.style.h5.copyWith(
      color: Colors.black,
      fontWeight: FontWeight.bold,
    );
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Align(
          alignment: Alignment.centerLeft,
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
            child: Text(
              R.string.todo,
              style: R.style.h4_1.copyWith(
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
          ),
        ),
        ColoredBox(
          color: Colors.grey.shade300,
          child: SizedBox(
            height: 50,
            width: double.infinity,
            child: Row(
              mainAxisSize: MainAxisSize.max,
              children: [
                const SizedBox(
                  width: 10,
                ),
                Expanded(
                  child: Text(
                    R.string.type,
                    style: style,
                  ),
                ),
                Expanded(
                    child: Text(
                  R.string.code,
                  style: style,
                )),
                Expanded(
                  flex: 5,
                  child: Text(
                    R.string.description,
                    style: style,
                  ),
                ),
                Expanded(
                  child: Text(
                    R.string.updatedAt,
                    style: style,
                  ),
                ),
                const SizedBox(
                  width: 10,
                ),
              ],
            ),
          ),
        ),
        const EmptyList()
      ],
    );
  }
}