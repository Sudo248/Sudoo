import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/pages/user/choose_address_callback.dart';
import 'package:sudoo/domain/model/user/choose_address_step.dart';

import '../../domain/model/user/address_suggestion.dart';
import '../../resources/R.dart';

class ChooseAddressDialog extends StatelessWidget {
  final ValueNotifier<List<AddressSuggestion>?> suggestion;
  final ValueNotifier<ChooseAddressStep> step;
  final ChooseAddressCallback callback;

  const ChooseAddressDialog({
    super.key,
    required this.suggestion,
    required this.step,
    required this.callback,
  });

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Container(
        constraints: const BoxConstraints(
          minWidth: 100,
          maxWidth: 150,
          minHeight: 200,
          maxHeight: 250,
        ),
        padding: const EdgeInsets.all(10),
        decoration: BoxDecoration(borderRadius: BorderRadius.circular(10), color: Colors.white),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            ValueListenableBuilder(
              valueListenable: step,
              builder: (context, value, child) {
                if (step.value == ChooseAddressStep.finish) {
                  context.pop();
                }
                return Text(
                  value.title,
                  style: R.style.h4_1.copyWith(
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                );
              },
            ),
            const SizedBox(
              height: 20,
            ),
            Expanded(
              child: ValueListenableBuilder(
                  valueListenable: suggestion,
                  builder: (context, value, child) {
                    if (value == null) {
                      return const CircularProgressIndicator();
                    }
                    if (value.isEmpty) {
                      return const Padding(
                        padding: EdgeInsets.all(15),
                        child: Text(
                          "Empty list",
                          style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                        ),
                      );
                    }
                    return ListView.builder(
                      itemBuilder: (context, index) => GestureDetector(
                        onTap: () {
                          callback.onChooseAddress(value[index]);
                        },
                        child: Padding(
                          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 5),
                          child: Text(
                            value[index].addressName,
                            style: R.style.h5,
                          ),
                        ),
                      ),
                    );
                  }),
            ),
            const SizedBox(
              height: 20,
            ),
          ],
        ),
      ),
    );
  }
}
