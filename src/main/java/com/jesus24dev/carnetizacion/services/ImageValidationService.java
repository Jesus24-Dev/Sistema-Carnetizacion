package com.jesus24dev.carnetizacion.services;

import jakarta.annotation.PostConstruct;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class ImageValidationService {

    private CascadeClassifier faceDetector;
    private boolean opencvLoaded = false;
    private final double MIN_WHITE_BACKGROUND_RATIO = 0.7;

    @PostConstruct
    public void init() {
        try {
            nu.pattern.OpenCV.loadLocally();
            opencvLoaded = true;
            faceDetector = loadCascade("haarcascades/haarcascade_frontalface_default.xml");
            System.out.println("OpenCV y clasificador de rostros cargados");
        } catch (Exception e) {
            opencvLoaded = false;
            System.err.println("Error inicializando OpenCV: " + e.getMessage());
        }
    }

    private CascadeClassifier loadCascade(String resourcePath) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) throw new IOException("Falta el clasificador: " + resourcePath);

        File tempFile = File.createTempFile("cascade_", ".xml");
        Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return new CascadeClassifier(tempFile.getAbsolutePath());
    }

    public ValidationResult validateImage(MultipartFile file) {
        if (!opencvLoaded) {
            return new ValidationResult(false, "OpenCV no está disponible");
        }

        try {
            File tempFile = convertMultipartToFile(file);
            Mat image = Imgcodecs.imread(tempFile.getAbsolutePath());
            Files.deleteIfExists(tempFile.toPath());

            if (image.empty()) {
                return new ValidationResult(false, "No se pudo cargar la imagen");
            }

            boolean hasWhiteBackground = validateWhiteBackground(image);
            boolean hasFace = detectFace(image);

            StringBuilder msg = new StringBuilder();
            boolean isValid = true;

            if (!hasWhiteBackground) {
                isValid = false;
                msg.append("Fondo blanco insuficiente. ");
            }
            if (!hasFace) {
                isValid = false;
                msg.append("No se detectó rostro. ");
            }

            return new ValidationResult(isValid, msg.toString());

        } catch (Exception e) {
            return new ValidationResult(false, "Error en validación: " + e.getMessage());
        }
    }

    private boolean detectFace(Mat image) {
        MatOfRect faces = new MatOfRect();
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        faceDetector.detectMultiScale(gray, faces, 1.1, 3, 0,
                new Size(30, 30), new Size(500, 500));
        return faces.toArray().length > 0;
    }

    private boolean validateWhiteBackground(Mat image) {
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.adaptiveThreshold(gray, binary, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 5);
        double whiteRatio = (double) Core.countNonZero(binary) / (binary.rows() * binary.cols());
        return whiteRatio >= MIN_WHITE_BACKGROUND_RATIO;
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File tempFile = Files.createTempFile("opencv_validation", ".jpg").toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    public static class ValidationResult {
        private final boolean isValid;
        private final String message;

        public ValidationResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }

        public boolean isValid() { return isValid; }
        public String getMessage() { return message; }
    }
}
