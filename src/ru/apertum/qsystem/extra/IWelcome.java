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
package ru.apertum.qsystem.extra;

import java.awt.Frame;
import ru.apertum.qsystem.client.model.QButton;
import ru.apertum.qsystem.common.cmd.RpcGetAllServices;
import ru.apertum.qsystem.common.model.IClientNetProperty;
import ru.apertum.qsystem.common.model.INetProperty;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.model.QAdvanceCustomer;
import ru.apertum.qsystem.server.model.QService;

/**
 *
 * @author Evgeniy Egorov
 */
public interface IWelcome extends IExtra {

    public void start(IClientNetProperty netProperty, RpcGetAllServices.ServicesForWelcome srvs);

    /**
     * Mетод который показывает модально диалог с информацией для клиентов.
     *
     * @param button Эту кнопку пользователь нажал при выборе услуги
     * @param parent фрейм относительно которого будет модальность
     * @param netProperty свойства работы с сервером
     * @param htmlText текст для прочтения
     * @param printText текст для печати
     * @param modal модальный диалог или нет
     * @param fullscreen растягивать форму на весь экран и прятать мышку или нет
     * @param delay задержка перед скрытием диалога. если 0, то нет автозакрытия диалога
     * @return продолжат сравить кастомера в очередь или нет, типа если true - все одобрено, false - что-то не понравилось клиенту и он не будет стоять
     */
    public boolean showPreInfoDialog(QButton button, Frame parent, INetProperty netProperty, String htmlText, String printText, boolean modal, boolean fullscreen, int delay);

    /**
     * Метод который показывает модально диалог ввода строки, такой кастомизированный, для специальных случаев, для них и делается плагин.
     *
     * @param button Эту кнопку пользователь нажал при выборе услуги
     * @param parent фрейм относительно которого будет модальность
     * @param modal модальный диалог или нет
     * @param netProperty свойства работы с сервером
     * @param fullscreen растягивать форму на весь экран и прятать мышку или нет
     * @param delay задержка перед скрытием диалога. если 0, то нет автозакрытия диалога
     * @param caption название на нужном языке
     * @param service услуга, в которую встает
     * @return Введеная посетителем строка. Если возвращает null на все вызовы, то это значит, что методы просто ничего не релизовывают, тогда будет показан
     * дефолтный диалог. Если метод выбросит исключение, то постановка в очередь прекратится.
     */
    public String showInputDialog(QButton button, Frame parent, boolean modal, INetProperty netProperty, boolean fullscreen, int delay, String caption, QService service);

    static public class StandInParameters {

        public INetProperty netProperty;
        public long serviceId;
        public String password;
        public int priority;
        public String inputData;

        public StandInParameters(INetProperty netProperty, long serviceId, String password, int priority, String inputData) {
            this.netProperty = netProperty;
            this.serviceId = serviceId;
            this.password = password;
            this.priority = priority;
            this.inputData = inputData;
        }

    }

    /**
     * Перез постановкой в очередь на киоске вызывается это событие. Параметры могут быть переработаны. Вызов метода постановки в очередь будут по параметрам
     * результатам обрататки.
     *
     * @param button Эту кнопку пользователь нажал при выборе услуги
     * @param params Исходные параметры для вызова команды постановки в очередь.
     * @return Переработанные при необходимости параметры для вызова команды постановки в очередь.
     */
    public StandInParameters handleStandInParams(QButton button, StandInParameters params);

    /**
     * Событие нажатия кнопки на таче.
     *
     * @param button Эту кнопку пользователь нажал при выборе услуги
     * @param servece Кнопку соотв этой услуге нажали.
     */
    public void buttonPressed(QButton button, QService servece);

    /**
     * Событие - Встал в очередь
     *
     * @param button Эту кнопку пользователь нажал при выборе услуги
     * @param customer этот встал в очередь.
     * @param service
     */
    public void readyNewCustomer(QButton button, QCustomer customer, QService service);

    /**
     * Событие - записался предварительно
     *
     * @param button Эту кнопку пользователь нажал при выборе услуги
     * @param advCustomer этот и записался
     * @param service
     */
    public void readyNewAdvCustomer(QButton button, QAdvanceCustomer advCustomer, QService service);

}
