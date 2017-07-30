/*
 *  Copyright (C) 2010 {Apertum}Projects. web: www.apertum.ru email: info@apertum.ru
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.apertum.qsystem.client.common;

import java.awt.Font;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import ru.apertum.qsystem.client.QProperties;
import ru.apertum.qsystem.common.QConfig;
import ru.apertum.qsystem.common.QLog;
import ru.apertum.qsystem.common.Uses;
import ru.apertum.qsystem.common.exceptions.ClientException;
import ru.apertum.qsystem.server.model.QProperty;

/**
 * Класс загрузки и предоставления настроек пункта регистрации
 *
 * @author Evgeniy Egorov
 */
public class WelcomeParams {

    public static WelcomeParams getInstance() {
        if (WelcomeParamsHolder.INSTANCE != null) {
            WelcomeParamsHolder.INSTANCE.updateProperties();
        }
        return WelcomeParamsHolder.INSTANCE;
    }

    private static class WelcomeParamsHolder {

        private static final WelcomeParams INSTANCE = new WelcomeParams();
    }
    private Properties settings = null;
    /**
     * Константы хранения параметров в файле.
     */
    private static final String POINT = "point";
    private static final String PAPER_WIDHT = "paper_widht";
    private static final String LEFT_MARGIN = "left_margin";
    private static final String TOP_MARGIN = "top_margin";
    private static final String LINE_HEIGTH = "line_heigth";
    private static final String LINE_LENGTH = "line_length";
    private static final String SCALE_VERTICAL = "scale_vertical";
    private static final String SCALE_HORIZONTAL = "scale_horizontal";
    private static final String PRINT = "printer.print";
    private static final String PRNAME = "printer.Name";
    private static final String EXECUTIVE = "printer.MediaSizeName.EXECUTIVE";
    private static final String PRINTABLE_AREA = "printer.MediaPrintableArea";
    // параметр размера бумаги. A0 A1 ... A10 B0 B2 ... B10 C0 C1 ... C6. Пустое или неверное значение - отключен
    private static final String MEDIA_SIZE_NAME = "printer.MediaSizeName";
    // параметр размера бумаги <ширина,длинна>. Пустое или неверное значение - отключен
    private static final String FIND_MEDIA = "printer.findMedia";
    private static final String ORIENTATION_PRINT = "printer.OrientationRequested";
    private static final String LOGO = "logo";
    private static final String BARCODE = "barcode";
    private static final String INPUT_DATA_QR = "input_data_qrcode";
    private static final String LOGO_LEFT = "logo_left";
    private static final String LOGO_TOP = "logo_top";
    private static final String DELAY_PRINT = "delay_print";
    private static final String DELAY_BACK = "delay_back";
    private static final String LOGO_IMG = "logo_img";
    private static final String BACKGROUND_IMG = "background_img";
    private static final String TXT_FONT_NAME = "ticket_font_name";
    private static final String TXT_FONT_SIZE = "ticket_font_size";
    private static final String TXT_FONT_H1_SIZE = "ticket_fontH1_size";
    private static final String TXT_FONT_H2_SIZE = "ticket_fontH2_size";
    private static final String PROMO_TXT = "promo_text";
    private static final String WAIT_TXT = "wait_text";
    private static final String BOTTOM_TXT = "bottom_text";
    private static final String BOTTOM_GAP = "bottom_gap";
    private static final String ASK_LIMIT = "ask_limit";
    private static final String PAGE_LINES_COUNT = "page_lines_count";
    private static final String INFO_BUTTON = "info_button";// кнопка информационной системы на пункте регистрации
    private static final String RESPONSE_BUTTON = "response_button";// - кнопка обратной связи на пункте регистрации
    private static final String ADVANCE_BUTTON = "advance_button";// - кнопка предварительной записи на пункте регистрации
    private static final String STAND_ADVANCE_BUTTON = "stand_advance_button";// - присутствие кнопки предварительной записи на пункте регистрации (0/1)
    private static final String NUMERIC_KEYBOARD = "numeric_keyboard";// - цифровая клавиатура при вводе юзерской инфы
    private static final String ALPHABETIC_KEYBOARD = "alphabetic_keyboard";// - буквенная клавиатура при вводе юзерской инфы
    private static final String SPEC_KEYBOARD = "spec_keyboard";// - буквенная клавиатура при вводе юзерской инфы
    private static final String INPUT_FONT_SIZE = "input_font_size";// - размер шрифта вводимого текста клиентом
    private static final String LINES_BUTTON_COUNT = "lines_button_count";// - количество рядов кнопок на киоске, если будет привышение, то начнотся листание страниц
    private static final String ONE_COLUMN_BUTTON_COUNT = "one_column_buttons_count";// - количество кнопок на киоске в один стобл
    private static final String TWO_COLUMNS_BUTTON_COUNT = "two_columns_buttons_count";// - количество кнопок на киоске в два столба
    private static final String BUTTON_TYPE = "button_type";// - это внешний вид кнопки. Если его нет или ошибочный, то стандартный вид. Иначе номер вида или картинка в png желательно
    private static final String SERV_BUTTON_TYPE = "serv_button_type";// - вид управляющей кнопки на пункте регистрации. Если его нет или ошибочный, то стандартный вид. Иначе номер вида или картинка в png желательно
    private static final String SERV_VERT_BUTTON_TYPE = "serv_vert_button_type";// - вид вертикальной управляющей кнопки на пункте регистрации. Если его нет или ошибочный, то стандартный вид. Иначе номер вида или картинка в png желательно
    private static final String BUTTON_IMG = "button_img";// - это присутствие пиктограммы услуги или группы на кнопке
    private static final String RESPONSE_IMG = "response_img";// - это присутствие пиктограммы отзыва или группы отзывов на кнопках отзывов
    private static final String BUTTON_TOSTART_IMG = "button_tostart_img";// - это пиктограмма на кнопке "В начало"
    private static final String BUTTON_GOBACK_IMG = "button_goback_img";// - это пиктограмма на кнопке "Назад"
    private static final String TOP_SIZE = "top_size";// - это ширина верхней панели на п.р. с видом кнопок
    private static final String TOP_IMG = "top_img";// - это картинка на верхней панели на п.р. с видом кнопок
    private static final String TOP_IMG_SECONDARY = "top_img_secondary";// - это картинка на верхней панели на п.р. на вторичных диалогах
    private static final String PATTERN_GET_TICKET = "pattern_get_ticket";// - это шаблон текста для диалога забрать талон
    private static final String GET_TICKET_IMG = "get_ticket_img";// - это картинка для диалогаплучения талона. пустое значение - картинка по умолчанию
    private static final String PATTERN_CONFIRMATION_START = "pattern_confirmation_start";// - это шаблон текста для диалога подтверждения стоять в очереди. Встроенный текст dialogue_text.take_ticket dialog.text_before_people [[endRus]]
    private static final String CONFIRMATION_START_IMG = "confirmation_start_img";// - это картинка для диалога подтверждения стоять в очереди. пустое значение - картинка по умолчанию
    private static final String PATTERN_INFO_DIALOG = "pattern_info_dialog";// - это шаблон текста для информационных диалогов Встроенный текст dialog.message
    private static final String PATTERN_PICK_ADVANCE_TITLE = "pattern_pick_advance_title";// - шаблон текста для диалога выбора предварительной услуги. Встроенный текст: dialog_text.part1 dialog_text.part2
    private static final String INFO_BUTTON_HTMLTEXT = "info_button_htmltext";
    private static final String RESPONSE_BUTTON_HTMLTEXT = "response_button_htmltext";
    private static final String TOP_URL = "top_url";

    private static final String BTN_FONT = "serv_button_font";
    private static final String BTN_ADV_FONT = "serv_adv_button_font";

    //#RU Примерный объем талонов в рулоне
    //#EN Approximate amount of tickets in a roll
    private static final String PAPER_SIZE_ALARM = "paper_size_alarm";
    private static final String PAPER_ALARM_STEP = "paper_alarm_step";

    private WelcomeParams() {
        settings = getLoadedProperties();
        loadSettings();
    }
    public boolean print = true; // Печатаем на принтере или нет
    public int point; // указание для какого пункта регистрации услуга, 0-для всех, х-для киоска х.
    public int paperWidht; // ширина талона в пикселах
    public int leftMargin; // отступ слева
    public int topMargin; // отступ сверху
    public int lineHeigth = 12; // Ширина строки
    public int lineLenght = 40; // Длинна стоки на квитанции
    public double scaleVertical = 0.8; // маштабирование по вертикале
    public double scaleHorizontal = 0.8; // машcтабирование по горизонтали
    public PrintRequestAttributeSet printAttributeSet = new HashPrintRequestAttributeSet(); // атрибуты печати ринтера
    public boolean logo = true; // присутствие логотипа на квитанции
    public int barcode = 1; // присутствие штрихкода на квитанции
    public boolean input_data_qrcode = true; // присутствие qr-штрихкода на квитанции если клиент ввел свои персональные данные
    public boolean info = true; // кнопка информационной системы на пункте регистрации
    public String infoURL = null; // кнопка информационной системы на пункте регистрации
    public String responseURL = null; // - кнопка обратной связи на пункте регистрации
    public String infoHtml = ""; // кнопка информационной системы на пункте регистрации
    public String responseHtml = ""; // - кнопка обратной связи на пункте регистрации
    public boolean response = true; // - кнопка обратной связи на пункте регистрации
    public boolean advance = true; // - кнопка предварительной записи на пункте регистрации
    public boolean standAdvance = true; // присутствие кнопки предварительной записи на пункте регистрации (0/1)
    public int logoLeft = 50; // Отступ печати логотипа слева
    public int logoTop = -5; // Отступ печати логотипа сверху
    public String logoImg = "/ru/apertum/qsystem/client/forms/resources/logo_ticket.png"; // логотип сверху
    public String backgroundImg = "/ru/apertum/qsystem/client/forms/resources/fon_welcome.jpg"; // фоновая картинка
    public String ticketFontName = ""; // Шрифт для текста талона
    public int ticketFontSize = 0; // Размер шрифта для текста талона
    public int ticketFontH1Size = 0; // Размер шрифта для текста талона
    public int ticketFontH2Size = 0; // Размер шрифта для текста талона
    public String promoText = "Aperum projects, e-mail: info@aperum.ru"; // промотекст, печатающийся мелким шрифтом перед штрихкодом.
    public String bottomText = "Приятного ожидания. Спасибо."; // произвольный текст, печатающийся в конце квитанции после штрихкода
    public int bottomGap = 2; // Сколько строк оставить пустыми в конце напечатанного талона
    public String waitText = ""; // текст, "Ожидайте вызова на табло". Если пусто, то текст по умолчанию.
    public int askLimit = 3; // Критический размер очереди после которого спрашивать клиентов о готовности встать в очередь
    public int pageLinesCount = 30; // Количество строк на странице.
    public int linesButtonCount = 5; // количество рядов кнопок на киоске, если будет привышение, то начнотся листание страниц
    public int oneColumnButtonCount = 3; // количество кнопок на киоске в одном столбце
    public int twoColumnButtonCount = 10; // количество кнопок на киоске в двух столбцах
    public String buttonType = ""; // - это внешний вид кнопки. Если его нет или ошибочный, то стандартный вид. Иначе номер вида или картинка в png желательно
    public String servButtonType = ""; // - это внешний вид сервисной кнопки. Если его нет или ошибочный, то стандартный вид. Иначе номер вида или картинка в png желательно
    public String servVertButtonType = ""; // - это внешний вид вертикальной сервисной кнопки. Если его нет или ошибочный, то стандартный вид. Иначе номер вида или картинка в png желательно
    public boolean buttonImg = true; // - это присутствие пиктограммы услуги или группы на кнопке
    public boolean responseImg = true; // - это присутствие пиктограммы отзыва или группы на кнопке
    public File buttonToStratImg = null; // - это пиктограммы  на кнопке
    public File buttonGoBackImg = null; // - это пиктограммы  на кнопке
    public int topSize = -1; // - это ширина верхней панели на п.р. с видом кнопок
    public String topImg = ""; // - это картинка на верхней панели на п.р. с видом кнопок
    public String topURL = ""; // - а верхней панели пункта регистрации, там где заголовок и картинка в углу, можно вывести вэб-контент по URL. Оставьте пустым если не требуется
    public String topImgSecondary = ""; // - это картинка на верхней панели на п.р. на вторичных диалогах
    public PrintService printService = null; // - это принтер определенный по имени printer.Name
    public String patternGetTicket = ""; // - это шаблон текста для диалога забрать талон
    public String getTicketImg = ""; // - это картинка для диалогаплучения талона. пустое значение - картинка по умолчанию
    public String patternConfirmationStart = ""; // - это это шаблон текста для диалога подтверждения стоять в очереди. Встроенный текст dialogue_text.take_ticket dialog.text_before_people [[endRus]]
    public String confirmationStartImg = ""; // - это картинка для диалога подтверждения стоять в очереди. пустое значение - картинка по умолчанию
    public String patternInfoDialog = ""; // шаблон текста для информационных диалогов Встроенный текст dialog.message
    public String patternPickAdvanceTitle = ""; // шаблон текста для выбора предварительной услуги диалогов Встроенный текст: dialog_text.part1 dialog_text.part2

    public Font btnFont = null;
    public Font btnAdvFont = null;
    /**
     * Задержка заставки при печати в мсек.
     */
    public int delayPrint = 3000;
    public int delayBack = 40000;
    //параметры СОМ-порта для кнопок кнопочного интерфейса
    public String buttons_COM = "COM1";
    public int buttons_databits = 8;
    public int buttons_speed = 9600;
    public int buttons_parity = 0;
    public int buttons_stopbits = 1;
    public boolean numeric_keyboard = true; // - цифровая клавиатура при вводе юзерской инфы
    public boolean alphabetic_keyboard = true; // - буквенная клавиатура при вводе юзерской инфы
    public String spec_keyboard = ""; // - специальная клавиатура при вводе юзерской инфы
    public int input_font_size = 64; // - размер шрифта при вводе юзерской инфы

    public int paper_size_alarm = 700; //  Примерный объем талонов в рулоне
    public int paper_alarm_step = 30; //  Примерный объем талонов в рулоне

    /**
     * Просто загрузить из файла с поддержкой UTF-8
     *
     * @return
     */
    private Properties getLoadedProperties() {
        final Properties props = new Properties();
        final FileInputStream in;
        InputStreamReader inR = null;
        try {
            in = new FileInputStream("config" + File.separator + WSECTION);
            inR = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new ClientException("Проблемы с кодировкой при чтении. " + ex);
        } catch (FileNotFoundException ex) {
            throw new ClientException("Проблемы с файлом при чтении. File not found. " + ex);
        }
        try {
            props.load(inR);
        } catch (IOException ex) {
            throw new ClientException("Properties ware not loaded. " + ex);
        }
        updLastTime = System.currentTimeMillis();
        return props;
    }

    private static long updLastTime;

    /**
     * Загрузим настройки.
     */
    private void loadSettings() {
        QLog.l().logger().debug("Загрузим параметры из файла  \"config" + File.separator + WSECTION + "\"");

        paper_size_alarm = getProp(PAPER_SIZE_ALARM, 700); // - размер шрифта при вводе юзерской инфы
        paper_alarm_step = getProp(PAPER_ALARM_STEP, 30); // - размер шрифта при вводе юзерской инфы

        print = getProp(PRINT, true);
        point = getProp(POINT, 1); // указание для какого пункта регистрации услуга, 0-для всех, х-для киоска х.
        paperWidht = getProp(PAPER_WIDHT, 250); // ширина талона в пикселах
        leftMargin = getProp(LEFT_MARGIN, 4); // отступ слева
        topMargin = getProp(TOP_MARGIN, 4); //  отступ сверху
        lineHeigth = getProp(LINE_HEIGTH, 12); // Ширина строки
        lineLenght = getProp(LINE_LENGTH, 28); // Длинна стоки на квитанции
        scaleVertical = getProp(SCALE_VERTICAL, 1.0); // маштабирование по вертикале
        scaleHorizontal = getProp(SCALE_HORIZONTAL, 1.0);  // машcтабирование по вертикале
        logo = getProp(LOGO, true); // присутствие логотипа на квитанции
        input_data_qrcode = getProp(INPUT_DATA_QR, false); // присутствие qr-штрихкода на квитанции если клиент ввел свои персональные данные
        barcode = getProp(BARCODE, 0); // присутствие штрихкода на квитанции 1-128B/2-QR
        logoLeft = getProp(LOGO_LEFT, 75);  // Отступ печати логотипа слева
        logoTop = getProp(LOGO_TOP, 1);  // Отступ печати логотипа сверху
        delayPrint = getProp(DELAY_PRINT, 5000);  // Задержка заставки при печати в мсек.
        delayBack = getProp(DELAY_BACK, 15000);  // Задержка заставки при печати в мсек.
        logoImg = getProp(LOGO_IMG, "/ru/apertum/qsystem/client/forms/resources/logo_ticket_a.png");
        backgroundImg = getProp(BACKGROUND_IMG, "/ru/apertum/qsystem/client/forms/resources/fon_welcome.jpg");
        if (!new File(backgroundImg).exists()) {
            backgroundImg = "/ru/apertum/qsystem/client/forms/resources/fon_welcome.jpg";
        }
        ticketFontName = getProp(TXT_FONT_NAME, "Terminal");
        ticketFontSize = getProp(TXT_FONT_SIZE, 10);
        ticketFontH1Size = getProp(TXT_FONT_H1_SIZE, 65);
        ticketFontH2Size = getProp(TXT_FONT_H2_SIZE, 16);
        promoText = getProp(PROMO_TXT, "");
        bottomText = getProp(BOTTOM_TXT, "");
        bottomGap = getProp(BOTTOM_GAP, 2);
        if (bottomGap > 10) {
            bottomGap = 10;
        }
        if (bottomGap < 0) {
            bottomGap = 0;
        }
        waitText = getProp(WAIT_TXT, "");
        askLimit = getProp(ASK_LIMIT, 0); // Критический размер очереди после которого спрашивать клиентов о готовности встать в очередь
        pageLinesCount = getProp(PAGE_LINES_COUNT, 70); // Количество строк на странице
        linesButtonCount = getProp(LINES_BUTTON_COUNT, 5);  // количество рядов кнопок на киоске, если будет привышение, то начнотся листание страниц
        oneColumnButtonCount = getProp(ONE_COLUMN_BUTTON_COUNT, 3);  // количество рядов кнопок на киоске, если будет привышение, то начнотся листание страниц
        twoColumnButtonCount = getProp(TWO_COLUMNS_BUTTON_COUNT, 10);  // количество рядов кнопок на киоске, если будет привышение, то начнотся листание страниц
        buttons_COM = getProp("buttons_COM", "COM1");
        buttons_databits = getProp("buttons_databits", 8);
        buttons_speed = getProp("buttons_speed", 57600);
        buttons_parity = getProp("buttons_parity", 0);
        buttons_stopbits = getProp("buttons_stopbits", 1);
        infoURL = getProp(INFO_BUTTON, "");
        if (infoURL.length() < 4) {
            infoURL = null;
        }
        info = infoURL != null || getProp(INFO_BUTTON, true); // кнопка информационной системы на пункте регистрации
        responseURL = getProp(RESPONSE_BUTTON, "");
        if (responseURL.length() < 4) {
            responseURL = null;
        }
        response = responseURL != null || getProp(RESPONSE_BUTTON, true); // - кнопка обратной связи на пункте регистрации
        infoHtml = getProp(INFO_BUTTON_HTMLTEXT, "");
        responseHtml = getProp(RESPONSE_BUTTON_HTMLTEXT, "");
        advance = getProp(ADVANCE_BUTTON, true); // - кнопка предварительной записи на пункте регистрации
        standAdvance = getProp(STAND_ADVANCE_BUTTON, true); // присутствие кнопки предварительной записи на пункте регистрации (0/1)

        numeric_keyboard = getProp(NUMERIC_KEYBOARD, true); // - цифровая клавиатура при вводе юзерской инфы
        alphabetic_keyboard = getProp(ALPHABETIC_KEYBOARD, true); // - буквенная клавиатура при вводе юзерской инфы
        spec_keyboard = getProp(SPEC_KEYBOARD, ""); // - специальная при вводе юзерской инфы
        input_font_size = getProp(INPUT_FONT_SIZE, 64); // - размер шрифта при вводе юзерской инфы
        if (settings.containsKey(BUTTON_TYPE)) {
            switch (settings.getProperty(BUTTON_TYPE)) {
                case "1":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn1.png";
                    break;
                case "2":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn2.png";
                    break;
                case "3":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn3.png";
                    break;
                case "4":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn4.png";
                    break;
                case "5":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn5.png";
                    break;
                case "6":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn6.png";
                    break;
                case "7":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn7.png";
                    break;
                case "8":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn8.png";
                    break;
                case "9":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn9.png";
                    break;
                case "10":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn10.png";
                    break;
                case "11":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn11.png";
                    break;
                case "12":
                    buttonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn12.png";
                    break;
                default: {
                    final File f = new File(settings.getProperty(BUTTON_TYPE));
                    if (f.exists()) {
                        buttonType = settings.getProperty(BUTTON_TYPE);
                    } else {
                        buttonType = "";
                    }
                }
            }
        } else {
            buttonType = "";
        }
        if (settings.containsKey(SERV_BUTTON_TYPE)) {
            switch (settings.getProperty(SERV_BUTTON_TYPE)) {
                case "1":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn1.png";
                    break;
                case "2":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn2.png";
                    break;
                case "3":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn3.png";
                    break;
                case "4":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn4.png";
                    break;
                case "5":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn5.png";
                    break;
                case "6":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn6.png";
                    break;
                case "7":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn7.png";
                    break;
                case "8":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn8.png";
                    break;
                case "9":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn9.png";
                    break;
                case "10":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn10.png";
                    break;
                case "11":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn11.png";
                    break;
                case "12":
                    servButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn12.png";
                    break;
                default: {
                    final File f = new File(settings.getProperty(SERV_BUTTON_TYPE));
                    if (f.exists()) {
                        servButtonType = settings.getProperty(SERV_BUTTON_TYPE);
                    } else {
                        servButtonType = "";
                    }
                }
            }
        } else {
            servButtonType = "";
        }
        if (settings.containsKey(SERV_VERT_BUTTON_TYPE)) {
            switch (settings.getProperty(SERV_VERT_BUTTON_TYPE)) {
                case "1":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn1.png";
                    break;
                case "2":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn2.png";
                    break;
                case "3":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn3.png";
                    break;
                case "4":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn4.png";
                    break;
                case "5":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn5.png";
                    break;
                case "6":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn6.png";
                    break;
                case "7":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn7.png";
                    break;
                case "8":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn8.png";
                    break;
                case "9":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn9.png";
                    break;
                case "10":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn10.png";
                    break;
                case "11":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn11.png";
                    break;
                case "12":
                    servVertButtonType = "/ru/apertum/qsystem/client/forms/resources/buttons/btn12.png";
                    break;
                default: {
                    final File f = new File(settings.getProperty(SERV_VERT_BUTTON_TYPE));
                    if (f.exists()) {
                        servVertButtonType = settings.getProperty(SERV_VERT_BUTTON_TYPE);
                    } else {
                        servVertButtonType = "";
                    }
                }
            }
        } else {
            servVertButtonType = "";
        }
        buttonGoBackImg = new File(getProp(BUTTON_GOBACK_IMG, ""));
        buttonGoBackImg = buttonGoBackImg.exists() ? buttonGoBackImg : null;
        buttonToStratImg = new File(getProp(BUTTON_TOSTART_IMG, ""));
        buttonToStratImg = buttonToStratImg.exists() ? buttonToStratImg : null;
        buttonImg = getProp(BUTTON_IMG, true); // кнопка присутствие картинки на кнопках услуг
        responseImg = getProp(RESPONSE_IMG, true); // кнопка присутствие картинки на кнопках отзывов
        topImg = getProp(TOP_IMG, "");
        topURL = getProp(TOP_URL, "");
        topImgSecondary = getProp(TOP_IMG_SECONDARY, "");
        patternGetTicket = getProp(PATTERN_GET_TICKET, "<HTML><b><p align=center><span style='font-size:60.0pt;color:green'>dialogue_text.take_ticket<br></span><span style='font-size:60.0pt;color:blue'>dialogue_text.your_nom<br></span><span style='font-size:130.0pt;color:blue'>dialogue_text.number</span></p>");
        patternConfirmationStart = getProp(PATTERN_CONFIRMATION_START, "<HTML><b><p align=center><span style='font-size:60.0pt;color:green'>dialog.text_before</span><br><span style='font-size:100.0pt;color:red'>dialog.count</span><br><span style='font-size:60.0pt;color:red'>dialog.text_before_people[[endRus]]</span></p></b>");
        getTicketImg = getProp(GET_TICKET_IMG, "/ru/apertum/qsystem/client/forms/resources/getTicket.png");
        if ("".equals(getTicketImg) || !new File(getTicketImg).exists()) {
            getTicketImg = Uses.firstMonitor.getDefaultConfiguration().getBounds().height < 910 || QConfig.cfg().isDebug() || QConfig.cfg().isDemo()
                    ? "/ru/apertum/qsystem/client/forms/resources/getTicketSmall.png"
                    : "/ru/apertum/qsystem/client/forms/resources/getTicket.png";
        }
        confirmationStartImg = getProp(CONFIRMATION_START_IMG, "/ru/apertum/qsystem/client/forms/resources/vopros.png");
        if ("".equals(confirmationStartImg) || !new File(confirmationStartImg).exists()) {
            confirmationStartImg = "/ru/apertum/qsystem/client/forms/resources/vopros.png";
        }
        patternInfoDialog = getProp(PATTERN_INFO_DIALOG, "<HTML><p align=center><b><span style='font-size:60.0pt;color:red'>dialog.message</span></b></p>");
        patternPickAdvanceTitle = getProp(PATTERN_PICK_ADVANCE_TITLE, "<html><p align=center><span style='font-size:55.0;color:#DC143C'>dialog_text.part1</span><br><span style='font-size:45.0;color:#DC143C'><i>dialog_text.part2</i>");

        topSize = getProp(TOP_SIZE, -1);
        if ("1".equals(settings.getProperty(EXECUTIVE, "0"))) {
            printAttributeSet.add(MediaSizeName.EXECUTIVE);
        }
        if (!"".equals(getProp(PRNAME, ""))) {
            // Get array of all print services
            final PrintService[] services = PrinterJob.lookupPrintServices();
            // Retrieve specified print service from the array
            for (int index = 0; printService == null && index < services.length; index++) {
                if (services[index].getName().equalsIgnoreCase(settings.getProperty(PRNAME, ""))) {
                    printService = services[index];
                }
            }
        }
        if (!"".equals(getProp(PRINTABLE_AREA, "")) && getProp(PRINTABLE_AREA, "").split(",").length == 4) {
            final String[] ss = getProp(PRINTABLE_AREA, "").split(",");
            printAttributeSet.add(new MediaPrintableArea(
                    Integer.parseInt(ss[0]), // отсуп слева 
                    Integer.parseInt(ss[1]), // отсуп сверху 
                    Integer.parseInt(ss[2]), // ширина 
                    Integer.parseInt(ss[3]), // высота 
                    MediaPrintableArea.MM));
        }
        if (!"".equals(getProp(FIND_MEDIA, "")) && getProp(FIND_MEDIA, "").split(",").length == 2) {
            final String[] ss = getProp(FIND_MEDIA, "").split(",");
            final MediaSizeName mediaSizeName = MediaSize.findMedia(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]), MediaPrintableArea.MM);
            printAttributeSet.add(mediaSizeName);
        }
        switch (getProp(ORIENTATION_PRINT, "")) {
            case "1":
                printAttributeSet.add(OrientationRequested.LANDSCAPE);
                break;
            case "2":
                printAttributeSet.add(OrientationRequested.PORTRAIT);
                break;
            case "3":
                printAttributeSet.add(OrientationRequested.REVERSE_LANDSCAPE);
                break;
            case "4":
                printAttributeSet.add(OrientationRequested.REVERSE_PORTRAIT);
                break;
            default:
                ;
        }
        switch (getProp(MEDIA_SIZE_NAME, "")) {
            case "A0":
                printAttributeSet.add(MediaSizeName.ISO_A0);
                break;
            case "A1":
                printAttributeSet.add(MediaSizeName.ISO_A1);
                break;
            case "A2":
                printAttributeSet.add(MediaSizeName.ISO_A2);
                break;
            case "A3":
                printAttributeSet.add(MediaSizeName.ISO_A3);
                break;
            case "A4":
                printAttributeSet.add(MediaSizeName.ISO_A4);
                break;
            case "A5":
                printAttributeSet.add(MediaSizeName.ISO_A5);
                break;
            case "A6":
                printAttributeSet.add(MediaSizeName.ISO_A6);
                break;
            case "A7":
                printAttributeSet.add(MediaSizeName.ISO_A7);
                break;
            case "A8":
                printAttributeSet.add(MediaSizeName.ISO_A8);
                break;
            case "A9":
                printAttributeSet.add(MediaSizeName.ISO_A9);
                break;
            case "A10":
                printAttributeSet.add(MediaSizeName.ISO_A10);
                break;
            case "B0":
                printAttributeSet.add(MediaSizeName.ISO_B0);
                break;
            case "B1":
                printAttributeSet.add(MediaSizeName.ISO_B1);
                break;
            case "B2":
                printAttributeSet.add(MediaSizeName.ISO_B2);
                break;
            case "B3":
                printAttributeSet.add(MediaSizeName.ISO_B3);
                break;
            case "B4":
                printAttributeSet.add(MediaSizeName.ISO_B4);
                break;
            case "B5":
                printAttributeSet.add(MediaSizeName.ISO_B5);
                break;
            case "B6":
                printAttributeSet.add(MediaSizeName.ISO_B6);
                break;
            case "B7":
                printAttributeSet.add(MediaSizeName.ISO_B7);
                break;
            case "B8":
                printAttributeSet.add(MediaSizeName.ISO_B8);
                break;
            case "B9":
                printAttributeSet.add(MediaSizeName.ISO_B9);
                break;
            case "B10":
                printAttributeSet.add(MediaSizeName.ISO_B10);
                break;
            case "C0":
                printAttributeSet.add(MediaSizeName.ISO_C0);
                break;
            case "C1":
                printAttributeSet.add(MediaSizeName.ISO_C1);
                break;
            case "C2":
                printAttributeSet.add(MediaSizeName.ISO_C2);
                break;
            case "C3":
                printAttributeSet.add(MediaSizeName.ISO_C3);
                break;
            case "C4":
                printAttributeSet.add(MediaSizeName.ISO_C4);
                break;
            case "C5":
                printAttributeSet.add(MediaSizeName.ISO_C5);
                break;
            default:
                ;
        }
        String ptn = getProp(BTN_FONT, "");
        if (!ptn.isEmpty() && ptn.split("-").length == 3) {
            btnFont = Font.decode(ptn);
        }
        ptn = getProp(BTN_ADV_FONT, "");
        if (!ptn.isEmpty() && ptn.split("-").length == 3) {
            btnAdvFont = Font.decode(ptn);
        }
    }

    private static final String WSECTION = "welcome.properties";

    private void updateProperties() {
        if (System.currentTimeMillis() - updLastTime > 10 * 60 * 1000) {
            synchronized (WSECTION) {
                if (System.currentTimeMillis() - updLastTime > 10 * 60 * 1000) {
                    settings = getLoadedProperties();
                    QProperties.get().reload();
                    loadSettings();
                }
            }
        }
    }

    private String getProp(String propName, String defValue) {
        updateProperties();
        final String fileVal = settings == null ? defValue : settings.getProperty(propName, defValue);
        return QProperties.get().getProperty(WSECTION, propName, fileVal);
    }

    private boolean getProp(String propName, boolean defValue) {
        updateProperties();
        final String fileVal = settings == null ? null : settings.getProperty(propName);
        final QProperty prop = QProperties.get().getProperty(WSECTION, propName);
        if (prop == null || prop.getValueAsBool() == null) {
            return fileVal == null ? defValue : ("1".equals(fileVal) || "true".equals(fileVal));
        } else {
            return prop.getValueAsBool();
        }
    }

    private int getProp(String propName, int defValue) {
        updateProperties();
        final String fileVal = settings == null ? null : settings.getProperty(propName);
        final QProperty prop = QProperties.get().getProperty(WSECTION, propName);
        if (prop == null || prop.getValueAsInt() == null) {
            return fileVal == null || !Uses.isInt(fileVal) ? defValue : Integer.parseInt(fileVal);
        } else {
            return prop.getValueAsInt();
        }
    }

    private double getProp(String propName, double defValue) {
        updateProperties();
        final String fileVal = settings == null ? null : settings.getProperty(propName);
        final QProperty prop = QProperties.get().getProperty(WSECTION, propName);
        if (prop == null || prop.getValueAsDouble() == null) {
            return fileVal == null || !Uses.isFloat(fileVal) ? defValue : Double.parseDouble(fileVal);
        } else {
            return prop.getValueAsDouble();
        }
    }

}
