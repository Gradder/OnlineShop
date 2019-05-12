package com.epam.onlineshop.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ImageWriter {

  private ImageWriter() {
  }

  public static void writeImage(ModelAndView catalog, MultipartFile file, String name) {
    if (!file.isEmpty()) {
      byte[] bytes = new byte[0];
      try {
        bytes = file.getBytes();
      } catch (IOException e) {
        log.error(e.getLocalizedMessage());
      }
      Path currentRelativePath = Paths.get("");
      String s = currentRelativePath.toAbsolutePath().toString();
      try (BufferedOutputStream stream =
          new BufferedOutputStream(new FileOutputStream(
              new File(s + "/src/main/resources/static/images/products/" + name + ".jpg")));
          BufferedOutputStream streamToTarget =
              new BufferedOutputStream(new FileOutputStream(
                  new File(s + "/target/classes/static/images/products/" + name + ".jpg")))) {

        stream.write(bytes);
        streamToTarget.write(bytes);
        catalog.setViewName("redirect:/catalog");
      } catch (FileNotFoundException e) {
        catalog.setViewName("redirect:/error");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      catalog.setViewName("redirect:/error");
    }
  }
}
