package it.cnr.isti.indexer;

import it.cnr.isti.config.index.ImageDemoConfiguration;
import it.cnr.isti.config.index.ImageDemoConfigurationImpl;
import it.cnr.isti.config.index.IndexConfiguration;
import it.cnr.isti.exception.ImageIndexingException;
import it.cnr.isti.feature.extraction.FeatureExtractionException;

import java.io.IOException;

public class TestBuildImageIndex extends TestImageIndexing {

	ImageDemoConfiguration config;

	@Override
	public void testImageIndexing() throws ImageIndexingException,
			FeatureExtractionException, IOException {
		// disable indexing of test image
	}

	@Override
	protected IndexConfiguration getIndexConfig() {
		return new ImageDemoConfigurationImpl();
	}
}
