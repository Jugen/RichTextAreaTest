# RichTextAreaTest
Testing JavaFX incubator RichTextArea using two different models to accommodate custom nodes:

1. Extending **BasicTextModel** with the use of _AbstractSegments_ to handle the custom nodes.
   
   This is just a reworking of the method I was using with RichTextFX in my own application.  
   See **CustomSegmentDemo** for a simple demonstration.
    
2. Reworking JavaFX's own internal RichTextModel to handle custom nodes internally.

   Also uses _AbstractSegments_ but mainly just as the _Supplier<Node>_.
   See **CustomSupplierDemo** for a simple demonstration.

##
___Note___: that in neither of these two models have I properly handled styling of the custom Nodes through the RichTextArea API.
##

## Problems
There are currently a few problems in the JavaFX implementation that need to be fixed regardless of the model used:

- Copy and paste of INLINE_NODEs doesn't work ?
- Undo and Redo of INLINE_NODEs doesn't work ?
- When an INLINE_NODE is the last element in a paragraph then the caret doesn't show beyond it but before it ?
- requestLayout doesn't propagate upwards past TextCell to VFlow, so if for example a Label is selected and its text changes then the Label updates but the selection doesn't ?
- Caspian CSS entries are missing.
