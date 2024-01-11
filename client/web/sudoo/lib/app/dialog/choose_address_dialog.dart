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
    final size = MediaQuery.of(context).size;
    return Dialog(
      child: Container(
        constraints: BoxConstraints(
          minWidth: 200,
          maxWidth: 400,
          minHeight: size.height * 0.5,
          maxHeight: size.width * 0.3,
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
                      return const Center(
                        child: SizedBox.square(
                          dimension: 50,
                          child: CircularProgressIndicator(),
                        ),
                      );
                    }
                    if (value.isEmpty) {
                      return Center(
                        child: Text(
                          R.string.emptyList,
                          style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                        ),
                      );
                    }
                    return ListView.builder(
                      itemCount: value.length,
                      itemBuilder: (context, index) => GestureDetector(
                        onTap: () {
                          callback.onChooseAddress(value[index]);
                        },
                        child: Padding(
                          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 5),
                          child: Text(
                            value[index].addressName,
                            style: R.style.h5.copyWith(color: Colors.black),
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
