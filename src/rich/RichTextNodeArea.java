package rich;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javafx.scene.Node;
import jfx.incubator.scene.control.richtext.RichTextArea;
import jfx.incubator.scene.control.richtext.TextPos;
import jfx.incubator.scene.control.richtext.model.StyledSegment;
import jfx.incubator.scene.control.richtext.model.StyledTextModel;
import rich.model.RichTextModel;

public class RichTextNodeArea extends RichTextArea
{
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

	public List<StyledSegment> getParagraphSegments( int index )
	{
		var p = model.getParagraph( index );
		var segmentCount = p.getSegmentCount();
		var segments = new ArrayList<StyledSegment>( segmentCount );

		for ( var segNo = 0; segNo < segmentCount; segNo++ )
		{
			segments.add( p.getSegment( segNo ) );
		}

		return segments;
	}
}
