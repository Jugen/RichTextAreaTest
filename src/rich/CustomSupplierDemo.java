package rich;

import common.AbstractSegment;
import common.LabelSegment;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfx.incubator.scene.control.richtext.model.StyledSegment;
import jfx.incubator.scene.control.richtext.model.StyledSegment.Type;

/**
 * This demo creates a sample document with some text and a custom Label node.
 */
public class CustomSupplierDemo extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
    	RichTextNodeArea textArea = new RichTextNodeArea();
    	textArea.setWrapText( true );

        textArea.appendText("This example shows how to add custom nodes, for example Labels: ");
        textArea.append( new LabelSegment("[Double Click Me]") );

        // TODO StyledSegment.Type INLINE_NODE isn't handled via StyledInput ?
        // See in StyledTextModel FINAL method replace( resolver, start, end, styledInput, allowUndo ) which doesn't handle INLINE_NODE
        // Note that this is also called by read( resolver, dataFormat, inputStream )
        // This blocks being able to paste !!!

        var bp = new BorderPane( textArea );

        Scene scene = new Scene(bp, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Custom Object demo");
        primaryStage.show();

        textArea.moveDocumentEnd();

        textArea.setOnMouseClicked( ME ->
        {
        	if ( ME.getClickCount() != 2 ) return;

			if ( ((Node) ME.getTarget()).getParent() instanceof Label label )
			{
				label.setText( "[Selection Updated]" );
			}
        });


        print( textArea );
    }


    private void print( RichTextNodeArea textArea )
    {
		var changedData = "";

    	for ( int p = 0; p < textArea.getParagraphCount(); p++ )
		{
			if ( ! changedData.isEmpty() )  changedData += "\n";

			for ( StyledSegment seg : textArea.getParagraphSegments(p) )
			{
				if ( seg.getType() == Type.TEXT ) changedData += seg.getText();
				else if ( seg.getType() == Type.INLINE_NODE )
				{
					var nodeSeg = (AbstractSegment<?>) seg.getInlineNodeGenerator();
					changedData += nodeSeg.getData().toString();
				}
			}
		}

    	System.out.println( changedData );
    }
}