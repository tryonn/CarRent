package br.ufpe.cin.amadeus.util;


import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import br.ufpe.cin.amadeus.system.exception.ImageFormatException;

public class Thumbnail {
	int width = 124; // Lagura da miniatura
	int height = 160; // Altuta da miniatura
	

	// Método para redimensionar imagens (criar thubmnails)
	public byte[] resize(byte[] img) throws Exception {
		BufferedImage biSrc = ImageIO.read(new ByteArrayInputStream(img));
		if (biSrc == null)
			throw new ImageFormatException("Formato inválido");

        double thumbRatio = (double) width / (double) height;
        int imageWidth = biSrc.getWidth();
        int imageHeight = biSrc.getHeight();

        double imageRatio = (double) imageWidth / (double) imageHeight;

        if (thumbRatio < imageRatio) {
              height = (int) (width / imageRatio);
        } else {
              width = (int) (height * imageRatio);
        }
        
		AffineTransform at = new AffineTransform();
		at.scale((double)width/biSrc.getWidth(), (double)height/biSrc.getHeight());

		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage biDst = op.filter(biSrc, null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(biDst, "png", baos);

		return baos.toByteArray();
	}

}