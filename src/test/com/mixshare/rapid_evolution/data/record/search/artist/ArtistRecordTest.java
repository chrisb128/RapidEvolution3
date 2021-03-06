package test.com.mixshare.rapid_evolution.data.record.search.artist;

import test.RE3TestCase;

import com.mixshare.rapid_evolution.data.identifier.search.artist.ArtistIdentifier;
import com.mixshare.rapid_evolution.data.record.search.artist.ArtistRecord;
import com.mixshare.rapid_evolution.data.util.io.XMLSerializer;

public class ArtistRecordTest extends RE3TestCase {

	public void testSerialization() {
		try {		
			ArtistRecord artist = new ArtistRecord();
			
			// common
			artist.setId(new ArtistIdentifier("justice"));			
			artist.setUniqueId(420);
			
			int[] dupIds = new int[] { 1, 2 };
			artist.setDuplicateIds(dupIds);
			
			artist.setRating((byte)80);
			artist.setDisabled(true);

			// search record specific
			artist.setSourceStyleIds(new int[] { 1 });
			artist.setSourceStyleDegrees(new float[] { 0.5f });
			artist.setSourceStyleSources(new byte[] { (byte)2 });
			artist.setActualStyleIds(new int[] { 1 });
			artist.setActualStyleDegrees(new float[] { 0.7f });
						
			artist.setSourceTagIds(new int[] { 2 });
			artist.setSourceTagDegrees(new float[] { 1.0f });
			artist.setSourceTagSources(new byte[] { (byte)1 });
			artist.setActualTagIds(new int[] { 2 });
			artist.setActualTagDegrees(new float[] { 0.9f });
			
		    artist.setScore(70.0f);
		    artist.setPopularity(30.0f);

		    artist.setComments("comments");
		    
		    artist.setThumbnailImageFilename("thumb.gif");

		    artist.setUserDataTypes(new short[] { 2 });
		    artist.setUserData(new Object[] { (Object)3 });

		    artist.setMinedProfileSources(new byte[] { (byte)3 });
		    artist.setMinedProfileSourcesLastUpdated(new long[] { 123 });

		    artist.setExternalItem(true);
		    
		    artist.setPlayCount(34);

		    // artist specific
		    artist.setLabelIds(new int[] { 1 });
		    artist.setLabelDegrees(new float[] { 1.0f });		    

		    artist.setDiscogsArtistName("justice (3)");
		    artist.setLastfmArtistName("justice");
		    artist.setMbId("asdf");
		    
			artist.setLastModified(234);
			
			XMLSerializer.saveData(artist, "data/junit/temp/artist.xml");
			artist = (ArtistRecord)XMLSerializer.readData("data/junit/temp/artist.xml");

			// common
			if (!artist.getId().equals(new ArtistIdentifier("justice")))
				fail("id incorrect");			
			if (artist.getUniqueId() != 420)
				fail("unique id incorrect");

			if (artist.getDuplicateIds()[0] != 1)
				fail("dup id incorrect");
			if (artist.getDuplicateIds()[1] != 2)
				fail("dup id incorrect");
			
			if (artist.getRating() != (byte)80)
				fail("rating incorrect");
			if (!artist.isDisabled())
				fail("not disabled");

			// search record specific
			if (artist.getSourceStyleIds()[0] != 1)
				fail();
			if (artist.getSourceStyleDegrees()[0] != 0.5f)
				fail();
			if (artist.getSourceStyleSources()[0] != (byte)2)
				fail();
			if (artist.getActualStyleIds()[0] != 1)
				fail();
			if (artist.getActualStyleDegrees()[0] != 0.7f)
				fail();

			if (artist.getSourceTagIds()[0] != 2)
				fail();
			if (artist.getSourceTagDegrees()[0] != 1.0f)
				fail();
			if (artist.getSourceTagSources()[0] != (byte)1)
				fail();
			if (artist.getActualTagIds()[0] != 2)
				fail();
			if (artist.getActualTagDegrees()[0] != 0.9f)
				fail();
			
			if (artist.getScore() != 70.0f)
				fail();
			if (artist.getPopularity() != 30.0f)
				fail();
				
			if (!artist.getComments().equals("comments"))
				fail();
			if (!artist.getThumbnailImageFilename().equals("thumb.gif"))
				fail();
			
			if (artist.getUserDataTypes()[0] != (byte)2)
				fail();
			if (!artist.getUserData()[0].toString().equals("3"))				
				fail("is=" + artist.getUserData()[0]);

			if (artist.getMinedProfileSources()[0] != (byte)3)
				fail();
			if (artist.getMinedProfileSourcesLastUpdated()[0] != 123)
				fail();

			if (!artist.isExternalItem())
				fail();
			
			if (artist.getPlayCount() != 34)
				fail();
		    
			// artist specific
			if (!artist.getDiscogsArtistName().equals("justice (3)"))
				fail("is=" + artist.getDiscogsArtistName());
			if (!artist.getLastfmArtistName().equals("justice"))
				fail();
			if (!artist.getMbId().equals("asdf"))
				fail();
			
			if (artist.getLabelIds()[0] != 1)
				fail();
			if (artist.getLabelDegrees()[0] != 1.0f)
				fail();
			
			if (artist.getLastModified() != 234)
				fail("last modified incorrect, is=" + artist.getLastModified());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}	
	
}
