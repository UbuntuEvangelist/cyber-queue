/*
 * Copyright (C) 2017 Egorov-EV
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
package ru.apertum.qsystem.client.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;
import ru.apertum.qsystem.common.QConfig;
import ru.apertum.qsystem.server.ServerProps;
import ru.apertum.qsystem.server.model.QProperty;

/**
 * Модель была сделана когда появиилась необходимость показывать не все секции. Это из-за скрытых параметров.
 *
 * @author Egorov-EV
 */
public class SectionListModel extends AbstractListModel<ServerProps.Section> implements MutableComboBoxModel<ServerProps.Section>, Serializable {

    private final LinkedHashMap<Integer, ServerProps.Section> sections;

    public SectionListModel() {

        this.sections = init();
    }

    /**
     * Выведем секции в список секций на вкладке. Учтем что не все проперти надо показывать. Показывать только если есть не скурытые в секции.
     */
    private LinkedHashMap<Integer, ServerProps.Section> init() {
        // учтем что не все проперти надо показывать. Показывать только если есть не скурытые в секции.
        final LinkedHashMap<Integer, ServerProps.Section> secs = new LinkedHashMap<>();
        int i = 0;
        for (ServerProps.Section sec : ServerProps.getInstance().getSections()) {
            boolean isHide = true;
            if (sec.getProperties().values().isEmpty()) {
                isHide = false;
            } else {
                for (QProperty prop : sec.getProperties().values()) {
                    if (QConfig.cfg().showHidenProps() || !prop.getHide()) {
                        isHide = false;
                        break;
                    }
                }
            }
            if (!isHide) {
                secs.put(i++, sec);
            }
        }
        return secs;
    }

    @Override
    public int getSize() {
        return sections.size();
    }

    @Override
    public ServerProps.Section getElementAt(int i) {
        return sections.get(i);
    }

    @Override
    public void addElement(ServerProps.Section e) {
        sections.put(sections.size(), e);
    }

    @Override
    public void removeElement(Object o) {
        sections.remove(findBySection((ServerProps.Section) o));
    }

    @Override
    public void insertElementAt(ServerProps.Section e, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeElementAt(int i) {
        sections.remove(i);
    }

    @Override
    public void setSelectedItem(Object o) {
        selected = findBySection((ServerProps.Section) o);
    }

    private int findBySection(ServerProps.Section sec) {
        for (int i : sections.keySet()) {
            if (sections.get(i).getName().equals(sec.getName())) {
                return i;
            }
        }
        return -1;
    }

    int selected = -1;

    @Override
    public Object getSelectedItem() {
        return selected == -1 ? null : sections.get(selected);
    }

}
