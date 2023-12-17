import 'package:flutter/material.dart';

import '../../extensions/list_ext.dart';

class AppNavigationItem extends StatefulWidget {
  final IconData leading;
  final String title;
  final bool isSelected;
  final List<String>? items;
  final GestureTapCallback? onTapItem;
  final void Function(int)? onTapSubItem;
  final ExpansionTileController? controller;

  const AppNavigationItem({
    super.key,
    required this.leading,
    required this.title,
    this.isSelected = false,
    this.onTapItem,
    this.onTapSubItem,
    this.items,
    this.controller,
  });

  @override
  State<AppNavigationItem> createState() => _AppNavigationItemState();
}

class _AppNavigationItemState extends State<AppNavigationItem> {
  int _currentItemIndex = -1;

  @override
  Widget build(BuildContext context) {
    if (!widget.isSelected) _currentItemIndex = -1;
    return widget.items.isNullOrEmpty
        ? ListTile(
            leading: Icon(
              widget.leading,
              color: widget.isSelected ? Colors.white : Colors.grey,
            ),
            title: Text(
              widget.title,
              style: TextStyle(
                color: widget.isSelected ? Colors.white : Colors.grey,
                fontSize: 16,
              ),
            ),
            onTap: widget.onTapItem,
          )
        : ExpansionTile(
            controller: widget.controller,
            leading: Icon(
              widget.leading,
              color: widget.isSelected ? Colors.white : Colors.grey,
            ),
            iconColor: Colors.white,
            collapsedIconColor: Colors.grey,
            title: Text(
              widget.title,
              style: TextStyle(
                color: widget.isSelected ? Colors.white : Colors.grey,
                fontSize: 16,
              ),
            ),
            onExpansionChanged: (isExpanded) {
              widget.onTapItem?.call();
            },
            children: List.generate(
              widget.items!.length,
              (index) => ListTile(
                title: Text(
                  widget.items![index],
                  style: TextStyle(
                    color: widget.isSelected && _currentItemIndex == index
                        ? Colors.white
                        : Colors.grey,
                    fontSize: 15,
                  ),
                ),
                onTap: () {
                  if (_currentItemIndex == index) return;
                  widget.onTapItem?.call();
                  widget.onTapSubItem?.call(index);
                  setState(() {
                    _currentItemIndex = index;
                  });
                },
              ),
            ),
          );
  }
}
