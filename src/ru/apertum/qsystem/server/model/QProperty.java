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
package ru.apertum.qsystem.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ru.apertum.qsystem.common.Uses;

/**
 * Настройки системы в БД. Каждая настройка находится в своей секции. Секция может быть NULL. Значение параметра может быть NULL. Имя параметра не NULL.
 *
 * @author Evgeniy Egorov
 */
@Entity
@Table(name = "properties")
public class QProperty implements Serializable {

    public QProperty(String section, String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Key must be not NULL");
        }
        this.section = section;
        this.key = key;
        this.value = value;
    }

    public QProperty(String section, String key, String value, String comment) {
        if (key == null) {
            throw new IllegalArgumentException("Key must be not NULL");
        }
        this.section = section;
        this.key = key;
        this.value = value;
        this.comment = comment;
    }

    public QProperty(String section, String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must be not NULL");
        }
        this.section = section;
        this.key = key;
    }

    public QProperty() {
    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    @SerializedName("id")
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    /**
     * Раздел параметров
     */
    @Expose
    @SerializedName("section")
    @Column(name = "psection")
    private String section;

    /**
     * Ключ параметра
     */
    @Expose
    @SerializedName("key")
    @Column(name = "pkey")
    private String key;
    /**
     * Значение параметра
     */
    @Expose
    @SerializedName("value")
    @Column(name = "pvalue")
    private String value;
    /**
     * Коммент для параметра
     */
    @Expose
    @SerializedName("comment")
    @Column(name = "pcomment")
    private String comment;

    /**
     * Это поле для данных, например сереализованных объектов в XML или Json. А может и просто не структурированных.
     */
    @Expose
    @SerializedName("data")
    @Column(name = "pdata")
    private String data;
    /**
     * Скрыт ли от пользователя в админке или нет.
     */
    @Expose
    @SerializedName("hide")
    @Column(name = "hide")
    private Boolean hide = false;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must be not NULL");
        }
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public Integer getValueAsInt() {
        return value == null ? null : Integer.parseInt(value);
    }

    public Long getValueAsLong() {
        return value == null ? null : Long.parseLong(value);
    }

    public Double getValueAsDouble() {
        return value == null ? null : Double.parseDouble(value);
    }

    public Boolean getValueAsBool() {
        return value == null ? null : (value.equals("1") || value.equalsIgnoreCase("true"));
    }

    public Date getValueAsDate(String pattern) throws ParseException {
        return value == null ? null : (new SimpleDateFormat(pattern).parse(value));
    }

    public Date getValueAsDate() throws ParseException {
        return value == null ? null : (Uses.FORMAT_FOR_REP.parse(value));
    }

    public String[] getValueAsStrArray(String regExpDivider) {
        return value == null ? new String[]{} : value.split(regExpDivider);
    }

    public ArrayList<Integer> getValueAsIntArray(String regExpDivider) {
        final String[] ss = value == null ? new String[]{} : value.split(regExpDivider);
        final ArrayList<Integer> list = new ArrayList();
        for (String s : ss) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    public ArrayList<Long> getValueAsLongArray(String regExpDivider) {
        final String[] ss = value == null ? new String[]{} : value.split(regExpDivider);
        final ArrayList<Long> list = new ArrayList();
        for (String s : ss) {
            list.add(Long.parseLong(s));
        }
        return list;
    }

    public void setValueAsStrArray(List<String> strings, String divider) {
        value = strings.stream().collect(Collectors.joining(divider));
    }

    public void setValueAsIntArray(List<Integer> ints, String divider) {
        value = ints.stream().map(i -> i.toString()).collect(Collectors.joining(divider));
    }

    public void setValueAsLongArray(List<Long> longs, String divider) {
        value = longs.stream().map(i -> i.toString()).collect(Collectors.joining(divider));
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value == null ? null : value.toString();
    }

    public void setValue(Long value) {
        this.value = value == null ? null : value.toString();
    }

    public void setValue(Double value) {
        this.value = value == null ? null : value.toString();
    }

    public void setValue(Boolean value) {
        this.value = value == null ? null : (value ? "true" : "false");
    }

    public void setValue(Date value) {
        this.value = value == null ? null : (Uses.FORMAT_FOR_REP.format(value));
    }

    public void setValue(Date value, String pattern) {
        this.value = value == null ? null : (new SimpleDateFormat(pattern).format(value));
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return (getSection() == null ? "" : ("[" + (getSection().length() > 24 ? getSection().substring(0, 23) : getSection()) + "]")) + (getKey().length() > 24 ? getKey().substring(0, 23) : getKey()) + ":" + (getValue() != null && getValue().length() > 24 ? getValue().substring(0, 23) : (getValue() == null ? "" : getValue()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof QProperty) {
            final QProperty p = (QProperty) obj;
            return ((section == null ? p.getSection() == null : section.equals(p.getSection()))
                    && (key == null ? p.getKey() == null : key.equals(p.getKey()))
                    && (value == null ? p.getValue() == null : value.equals(p.getValue())));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
