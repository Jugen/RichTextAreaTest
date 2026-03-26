package basic;

import java.util.Collections;
import java.util.List;

import basic.model.SegmentBasicTextModel;
import common.AbstractSegment;
import jfx.incubator.scene.control.richtext.RichTextArea;
import jfx.incubator.scene.control.richtext.TextPos;
import jfx.incubator.scene.control.richtext.model.StyledTextModel;

/**
 * A rich text area component supporting custom segments.
 * <p>
 * SegmentRichTextArea extends RichTextArea to allow insertion, replacement,
 * and retrieval of custom segment objects (subclasses of AbstractSegment).
 * It uses SegmentBasicTextModel for segment management and provides
 * convenience methods for segment operations and text extraction.
 * <p>
 * A subclassed AbstractSegment holds a data object and has methods to
 * interact with other segments and to create a Node for presentation.
 * See LabelSegment for an example of an AbstractSegment which can be
 * embedded in the text. You may extend AbstractSegment to add your own
 * custom segment types like maybe a HyperlinkSegment or ImageSegment.
 * <p>
 * <b>Note:</b> that no text styling is accommodated !
 * <p>
 * Main features:
 * <ul>
 *   <li>Insert, append, and replace segments at specific positions</li>
 *   <li>Retrieve segments for a given paragraph</li>
 *   <li>Extract plain text from all segments</li>
 * </ul>
 *
 * @author Jurgen
 */
public class SegmentRichTextArea extends RichTextArea
{
	private SegmentBasicTextModel  model;

    public SegmentRichTextArea()
    {
    	super( new SegmentBasicTextModel() );
        setWrapText( true );
    }

	@Override protected void validateModel( StyledTextModel m )
	{
		if ( m instanceof SegmentBasicTextModel segModel ) model = segModel;
		else throw new IllegalArgumentException();
	}

    public void append( AbstractSegment<?> customSegment )
    {
        insert( getDocumentEnd(), customSegment );
    }

    public void insert( TextPos pos, AbstractSegment<?> customSegment )
    {
    	if ( pos == null ) pos = getDocumentEnd();
        replace( pos, pos, customSegment );
    }

    public void replace( TextPos start, TextPos end, AbstractSegment<?> customSegment )
    {
    	model.replaceSegment( start, end, customSegment );
    }

	public List<AbstractSegment<?>> getParagraphSegments( int index )
	{
		return Collections.unmodifiableList( model.getParagraphSegments( index ) );
	}
}
