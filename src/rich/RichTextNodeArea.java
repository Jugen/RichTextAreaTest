package rich;

import java.util.List;
import java.util.function.Supplier;

import javafx.application.Application;
import javafx.scene.Node;
import jfx.incubator.scene.control.richtext.RichTextArea;
import jfx.incubator.scene.control.richtext.TextPos;
import jfx.incubator.scene.control.richtext.model.StyledSegment;
import jfx.incubator.scene.control.richtext.model.StyledTextModel;
import rich.model.RichTextModel;

public class RichTextNodeArea extends RichTextArea
{
	// TODO Remove this when CASPIAN style sheet has been updated for RichTextArea
	private static String  userAgentStyleSheet = "";
	private RichTextModel  model;

	public RichTextNodeArea()
	{
		super( new RichTextModel() );
        setWrapText( true );
	}

	@Override protected void validateModel( StyledTextModel m )
	{
		if ( m instanceof RichTextModel rtnModel ) model = rtnModel;
		else throw new IllegalArgumentException();
	}

    public void append( Supplier<Node> customSegment )
    {
        insert( getDocumentEnd(), customSegment );
    }

    public void insert( TextPos pos, Supplier<Node> customSegment )
    {
    	if ( pos == null ) pos = getDocumentEnd();
        replace( pos, pos, customSegment );
    }

    public void replace( TextPos start, TextPos end, Supplier<Node> customSegment )
    {
		// FIXME var ch = UndoableChange.create(this, start, end);				// com.sun.UndoableChange not accessible -> protected UndoableChange createUndo( ... ) ???
    	if ( ! start.equals( end ) ) model.removeRange( start, end );
    	model.insertNodeSegment( start.index(), start.offset(), customSegment, null );
        // FIXME add(ch, newEnd);												// is private -> protected void addUndo( ... ) ???
    }

    // TODO Remove and use model.getParagraph( index ).getSegments() if changed to public
	public List<StyledSegment> getParagraphSegments( int index )
	{
		return model.getParagraphSegments( index );
	}

	@Override public String getUserAgentStylesheet()
	{
		if ( userAgentStyleSheet != null && userAgentStyleSheet.isEmpty() )
		{
            String globalCSS = System.getProperty( "javafx.userAgentStylesheetUrl" ); // JavaFX preference!
            if ( globalCSS == null ) globalCSS = Application.getUserAgentStylesheet();
            if ( globalCSS == null ) globalCSS = Application.STYLESHEET_MODENA;

            if ( globalCSS == Application.STYLESHEET_CASPIAN ) {
            	userAgentStyleSheet = this.getClass().getResource( "RichTextAreaCaspian.css" ).toString();
            }
            else userAgentStyleSheet = super.getUserAgentStylesheet();
		}
		return userAgentStyleSheet;
	}
}
