package ru.mihena.VKBot.utils;

import api.longpoll.bots.model.objects.media.Attachment;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mihena.VKBot.model.ImageEntity;
import ru.mihena.VKBot.model.ImageService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
@Slf4j
public class ImageFromURLsaver {

    @Autowired
    private ImageService imageService;

    @Deprecated
    public ArrayList<File> saveAttachment(List<String> imageUrl, String folderName, String extension) {

        ArrayList<File> list = new ArrayList<>();

        if(!Files.exists(Path.of(folderName))) { //Создаёт дерикторию по названию и аргумента, если её не существует
            File dir = new File(folderName);
            boolean d1 = dir.mkdir();
            if(d1) log.info(folderName + " created successfully");
            else log.error("cannot create " + folderName);
        }

        for (String s : imageUrl) {
            URL url = null;
            try {
                url = new URL(s);
            } catch (MalformedURLException e) {
                log.error(e.toString());
            }
            UUID uuid = UUID.randomUUID();
            File file = new File(folderName+"/"+ folderName + uuid + "." + extension); //Создаёт файл с сгенерированным названием в виде Random UUID
            try ( //Записывает массив байт в файл
                    OutputStream os = new FileOutputStream(file)) {
                assert url != null;
                try (InputStream is = url.openStream())
                {
                    byte[] b = new byte[2048];

                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }

                    list.add(file);
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        return list;
    }

    public void saveAttachmentsToDatabase(List<Attachment> imageUrl, String folderName) {

        Map<String, String> attachments = new HashMap<>();
        //Перебор всех полученных вложений и сортировка по листам
        for (Attachment a : imageUrl) {
            if (a.getType().equals(Attachment.Type.PHOTO)) {
                int tempSize = 0;
                int tempIndex = 0;
                for (int i = 0; i < a.getPhoto().getPhotoSizes().size() - 1; i++) { //Поиск максимального размера фото
                    if (a.getPhoto().getPhotoSizes().get(i).getWidth() > tempSize) {
                        tempSize = a.getPhoto().getPhotoSizes().get(i).getWidth();
                        tempIndex = i;
                    }
                }
                attachments.put(a.getPhoto().getPhotoSizes().get(tempIndex).getSrc(), "jpg");
            }
            else if(a.getType().equals(Attachment.Type.DOC)) {
                attachments.put(a.getDoc().getUrl(), "gif");
            }
        }
        for(Map.Entry<String, String> size : attachments.entrySet()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                URL url = new URL(size.getKey());
                @Cleanup
                InputStream is = url.openStream();
                byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                int n;
                while ( (n = is.read(byteChunk)) > 0 ) {
                    baos.write(byteChunk, 0, n);
                }

                imageService.updateImage(new ImageEntity(baos.toByteArray(), size.getValue()));

            } catch (IOException e) {
                log.error(e.toString());
            }
        }
    }


    public void clearFolder(String folderName) throws IOException {
        Path path = Path.of(folderName);

        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path path1 : files)
                java.nio.file.Files.deleteIfExists(path1);
        }
    }
}
