package it.cnr.isti.cophir.ui.bean.image;

import it.cnr.isti.config.index.ImageDemoConfiguration;
import it.cnr.isti.exception.TechnicalRuntimeException;

import java.util.Set;

public class ImageDispatcher {

	RandomImages randomImages;
	ImageController imageController;
	ImageDemoConfiguration configuration;

	public ImageDemoConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfigurationIfNull(ImageDemoConfiguration configuration) {
		if (this.configuration == null)
			this.configuration = configuration;
	}

	public RandomImages getRandomImageGenerator() {
		return randomImages;
	}

	public void setRandomImageGeneratorIfNull(RandomImages randomImages) {
		if (this.randomImages == null)
			this.randomImages = randomImages;
	}

	public ImageController getImageController() {
		if (imageController == null) {
			String imageControllerClass = getConfiguration()
					.getImageControllerClass();
			if (imageControllerClass != null) {
				try {
					imageController = (ImageController) Class.forName(
							imageControllerClass).newInstance();
				} catch (Exception e) {
					throw new TechnicalRuntimeException(
							"Cannot instantiate image controller! check project configurations!",
							e);
				}
			}
		}

		return imageController;
	}

	// public void setImageController(ImageController imageController) {
	// this.imageController = imageController;
	// }

	public Set<String> getRandomImageSet() {
		return null;
	}

	public String getRandomThumbnailId() {
		return getRandomImageGenerator().getRandomId();
	}

	public String getThumbnailUrl(String thumbnailId, String applicationUrl) {
		if (getImageController() == null) {
			return getRandomImageGenerator().getThumbnailUrl(thumbnailId);
		} else {
			return getImageController().getImageUrl(
					getConfiguration().getDefaultDataset(), thumbnailId,
					applicationUrl);
		}
	}
	
	public String getThumbnailUrl(String thumbnailId) {
		return getThumbnailUrl(thumbnailId, "./");
	}
}
