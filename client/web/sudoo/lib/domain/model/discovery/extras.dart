import 'package:sudoo/domain/model/discovery/file.dart';

class Extras {
  final bool enable3DViewer;
  final bool enableARViewer;
  final File? source;

  Extras({required this.enable3DViewer, required this.enableARViewer, this.source});
}