import 'package:sudoo/domain/model/discovery/file.dart';

class Extras {
  final bool enable3DViewer;
  final bool enableArViewer;
  final String? source;

  Extras({required this.enable3DViewer, required this.enableArViewer, this.source});
}