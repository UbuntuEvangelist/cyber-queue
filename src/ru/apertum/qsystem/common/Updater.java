/*
 * Copyright (C) 2017 Apertum Project LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.apertum.qsystem.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.util.Pair;
import net.lingala.zip4j.exception.ZipException;
import ru.apertum.qsystem.common.exceptions.ServerException;

/**
 *
 * @author Evgeniy Egorov
 */
public class Updater {

    public static final String KEY_UPD_URL = "update_url";

    @Expose
    @SerializedName("updates")
    private final LinkedList<Update> updates = new LinkedList<>();

    public static class Update {

        @Expose
        @SerializedName("status")
        private String staus;

        @Expose
        @SerializedName("url")
        private String url;

        @Expose
        @SerializedName("checkTime")
        private Date checkTime;

        @Expose
        @SerializedName("md5")
        private String md5;

        @Expose
        @SerializedName("size")
        private Long size;

        @Expose
        @SerializedName("lastModified")
        private Long lastModified;

        @Expose
        @SerializedName("file")
        private String file;

        @Expose
        @SerializedName("downloadedTime")
        private Date downloadedTime;

        @Expose
        @SerializedName("unzipedTime")
        private Date unzipedTime;
    }

    public void save() {
        // в темповый файл
        final FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(Uses.CONFIG_FOLDER + File.separator + Uses.TEMP_UPDLOG_FILE));
        } catch (FileNotFoundException ex) {
            throw new ServerException("Не возможно создать файл состояния обновления. " + ex.getMessage());
        }
        Gson gson = null;
        try {
            gson = GsonPool.getInstance().borrowGson();
            fos.write(gson.toJson(this).getBytes("UTF-8"));
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            throw new ServerException("Не возможно сохранить изменения в поток." + ex.getMessage());
        } finally {
            GsonPool.getInstance().returnGson(gson);
        }
    }

    public static Updater load() {
        final Updater updLog;
        final File recovFile = new File(Uses.CONFIG_FOLDER + File.separator + Uses.TEMP_UPDLOG_FILE);
        if (recovFile.exists()) {
            final FileInputStream fis;
            try {
                fis = new FileInputStream(recovFile);
            } catch (FileNotFoundException ex) {
                throw new ServerException(ex);
            }
            final Scanner scan = new Scanner(fis, "utf8");
            String rec_data = "";
            while (scan.hasNextLine()) {
                rec_data += scan.nextLine();
            }
            try {
                fis.close();
            } catch (IOException ex) {
                throw new ServerException(ex);
            }

            final Gson gson = GsonPool.getInstance().borrowGson();
            try {
                updLog = gson.fromJson(rec_data, Updater.class);
            } catch (JsonSyntaxException ex) {
                throw new ServerException("Не возможно интерпритировать сохраненные данные.\n" + ex.toString());
            } finally {
                GsonPool.getInstance().returnGson(gson);
            }
        } else {
            updLog = new Updater();
        }
        return updLog;
    }

    private Update getCurrentUpdate() {
        Update update;
        if (updates.isEmpty()) {
            update = new Update();
            updates.addFirst(update);
        } else {
            update = updates.getFirst();
            if (update.unzipedTime != null) {
                update = new Update();
                update.lastModified = updates.getFirst().lastModified;
                update.size = updates.getFirst().size;
                update.md5 = updates.getFirst().md5;
                updates.addFirst(update);
            }
        }
        return update;
    }

    public void download(String url) {
        /*
        final QProperty p_url = QProperties.get().getProperty(QProperties.SECTION_SERVER, KEY_UPD_URL);
        if (p_url == null || url == null) {
            return;
        }
         */
        final Update update = getCurrentUpdate();
        final Pair<Long, Long> ufile;
        try {
            ufile = Uses.aboutFile(url);
            update.url = url;
            update.checkTime = new Date();
            update.staus = "Check about file";
        } catch (IOException ex) {
            QLog.l().logger().warn("Impossimbe to check properties of update file " + url);
            update.staus = "Fail check about file";
            save();
            return;
        }

        if (ufile.getKey().equals(update.lastModified) && ufile.getValue().equals(update.size)) {
            update.staus = "Checked. No update.";
            save();
            return;
        }

        final String theFile = Uses.TEMP_FOLDER + File.separator + url.substring(url.lastIndexOf('/') + 1, url.length());
        try {
            Uses.downloadFile(url, theFile);
            update.downloadedTime = new Date();
            update.url = url;
            update.file = theFile;
            update.staus = "Downloaded to " + theFile;
        } catch (IOException ex) {
            QLog.l().logger().warn("Downloading was failed. File " + url);
            update.staus = "Downloading was failed";
            save();
            return;
        }

        update.lastModified = ufile.getKey();
        update.size = ufile.getValue();
        save();
    }

    public void unzip() {
        final Update update = getCurrentUpdate();

        if (update.unzipedTime == null && update.url != null && update.file != null && update.downloadedTime != null
                && Files.exists(FileSystems.getDefault().getPath(update.file), new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {

            final String md5;
            try {
                md5 = Uses.md5(update.file);
            } catch (IOException ex) {
                QLog.l().logger().warn("Error to get MD5 for " + update.file);
                return;
            }
            if (md5.equals(update.md5)) {
                return;
            }

            try {
                Uses.unzip(update.file, Paths.get(".").toAbsolutePath().normalize().toString());
            } catch (ZipException ex) {
                QLog.l().logger().warn("Error unzip " + update.file + " to " + Paths.get(".").toAbsolutePath().normalize().toString());
                return;
            }
            update.unzipedTime = new Date();
            update.md5 = md5;
            update.staus = "Finished succesfully";
            save();
        } else {
            QLog.l().logger().warn("Nothing to unzip.");
        }
    }
}
