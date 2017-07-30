/*
 * Copyright (C) 2014 Evgeniy Egorov
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
package ru.apertum.qsystem.client.common;

import java.awt.Insets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import ru.apertum.qsystem.client.QProperties;
import ru.apertum.qsystem.common.QLog;
import ru.apertum.qsystem.common.Uses;
import ru.apertum.qsystem.server.model.QProperty;

/**
 *
 * @author Evgeniy Egorov
 */
public class WelcomeBGparams {

    public static final String SCREEN = "screen";
    public static final String BUTTON = "button";
    public static final String BKG_PIC = "bkgpic";
    public static final String INSETS = "insets";
    public static final String GAPS = "gaps";

    public static WelcomeBGparams get() {
        return WelcomeBGparamsHolder.INSTANCE;
    }

    private static class WelcomeBGparamsHolder {

        private static final WelcomeBGparams INSTANCE = new WelcomeBGparams();
    }

    private WelcomeBGparams() {
    }

    /**
     *
     * @param id for service
     * @return spec file or default = WelcomeParams.getInstance().backgroundImg
     */
    public String getScreenImg(Long id) {
        QProperty property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, SCREEN + "_" + BKG_PIC + "_" + id);
        if (property == null || Files.notExists(Paths.get(property.getValue()))) {
            property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, SCREEN + "_" + BKG_PIC);
            if (property == null || Files.notExists(Paths.get(property.getValue()))) {
                return WelcomeParams.getInstance().backgroundImg;
            } else {
                return property.getValue();
            }
        } else {
            return property.getValue();
        }
    }

    /**
     * Обрамление экрана или NULL если не надо.
     *
     * @param id for service
     * @return Объект отступов.
     */
    public Insets getScreenInsets(Long id) {
        QProperty property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, SCREEN + "_" + INSETS + "_" + id);
        if (property == null || Uses.getIntsFromString(property.getValue()).isEmpty()) {
            property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, SCREEN + "_" + INSETS);
            if (property == null || Uses.getIntsFromString(property.getValue()).isEmpty()) {
                return null;
            }
        }
        return createInsets(property.getValue());
    }

    private Insets createInsets(String str) {
        final ArrayList<Integer> ints = Uses.getIntsFromString(str);
        if (ints.size() != 4) {
            QLog.l().logger().warn("Value \"" + str + "\" must has 4 digits but " + ints.size() + " found.");
            return null;
        }
        return new Insets(ints.get(0), ints.get(1), ints.get(2), ints.get(3));
    }

    /**
     * Промежутки между кнопками на экране или NULL если не надо.
     *
     * @param id for service
     * @return горизонтальное и вертикальное расстояние. Только две циферки в массиве.
     */
    public ArrayList<Integer> getScreenGaps(Long id) {
        QProperty property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, SCREEN + "_" + GAPS + "_" + id);
        if (property == null || Uses.getIntsFromString(property.getValue()).isEmpty()) {
            property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, SCREEN + "_" + GAPS);
            if (property == null || Uses.getIntsFromString(property.getValue()).isEmpty()) {
                return null;
            }
        }
        final ArrayList<Integer> ints = Uses.getIntsFromString(property.getValue());
        if (ints.size() != 2) {
            QLog.l().logger().warn(property + " Value \"" + property.getValue() + "\" must has 2 digits but " + ints.size() + " found.");
            return null;
        }
        return ints;
    }

    /**
     *
     * @param id for service
     * @return spec file or default = WelcomeParams.getInstance().backgroundImg
     */
    public String getButtonImg(Long id) {
        QProperty property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, BUTTON + "_" + BKG_PIC + "_" + id);
        if (property == null || Files.notExists(Paths.get(property.getValue()))) {
            property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, BUTTON + "_" + BKG_PIC);
            if (property == null || Files.notExists(Paths.get(property.getValue()))) {
                return WelcomeParams.getInstance().buttonType;
            } else {
                return property.getValue();
            }
        } else {
            return property.getValue();
        }
    }

    /**
     * Обрамление кнопки или NULL если не надо.
     *
     * @param id for service
     * @return Объект отступов.
     */
    public Insets getButtonInsets(Long id) {
        QProperty property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, BUTTON + "_" + INSETS + "_" + id);
        if (property == null || Uses.getIntsFromString(property.getValue()).isEmpty()) {
            property = QProperties.get().getProperty(QProperties.SECTION_WELCOME, BUTTON + "_" + INSETS);
            if (property == null || Uses.getIntsFromString(property.getValue()).isEmpty()) {
                return null;
            }
        }
        return createInsets(property.getValue());
    }

}
