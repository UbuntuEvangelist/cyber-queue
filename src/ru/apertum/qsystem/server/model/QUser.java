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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.apertum.qsystem.common.CustomerState;
import ru.apertum.qsystem.common.exceptions.ServerException;
import ru.apertum.qsystem.common.model.QCustomer;
import ru.apertum.qsystem.server.Spring;

/**
 * Это пользователь. По большому счету роль и пользователь совпадают в системе. Класс пользователя системы.
 *
 * @author Evgeniy Egorov
 */
@Entity
@Table(name = "users")
public class QUser implements IidGetter, Serializable {

    /**
     * Конструктор для формирования из БД.
     */
    public QUser() {
    }

    @Override
    public String toString() {
        return getName();
    }
    @Expose
    @SerializedName("id")
    private Long id;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Если нужно для пользователя что-то сохранять в системных параметрах, то это надо сохранять в секцию для этой конкретного пользователя.
     *
     * @return Имя секции в системных параметах для конкретно этого пользователя.
     */
    @Transient
    public String getSectionName() {
        return "usr_" + id.toString();
    }
    /**
     * признак удаления с проставленим даты
     */
    @Column(name = "deleted")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date deleted;

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
    /**
     * Удаленный или нет. Нельзя их из базы гасить чтоб констрейнты не поехали. 0 - удаленный 1 - действующий Только для БД.
     */
    @Expose
    @SerializedName("enable")
    private Integer enable = 1;

    @Column(name = "enable")
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
    /**
     * Параметр доступа к администрированию системы.
     */
    @Expose
    @SerializedName("is_admin")
    private Boolean adminAccess = false;

    public void setAdminAccess(Boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    @Column(name = "admin_access")
    public Boolean getAdminAccess() {
        return adminAccess;
    }
    /**
     * Параметр доступа к отчетам системы.
     */
    @Expose
    @SerializedName("is_report_access")
    private Boolean reportAccess = false;

    public void setReportAccess(Boolean reportAccess) {
        this.reportAccess = reportAccess;
    }

    @Column(name = "report_access")
    public Boolean getReportAccess() {
        return reportAccess;
    }

    /**
     * Параметр разрешения ведения парраллельного приема кустомеров.
     */
    @Expose
    @SerializedName("is_parallel")
    private Boolean parallelAccess = false;

    //@Transient
    @Column(name = "parallel_access")
    public Boolean getParallelAccess() {
        return parallelAccess;
    }

    public void setParallelAccess(Boolean parallelAccess) {
        this.parallelAccess = parallelAccess;
    }

    /**
     * Пароль пользователя. В программе хранится открыто. В базе и xml зашифрован.
     */
    @Expose
    @SerializedName("pass")
    private String password = "";

    /**
     * Расшифрует
     *
     * @param password - зашифрованное слово
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Зашифрует
     *
     * @return пароль в зашифрованном виде.
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public void recoverAccess(String access) {
        this.password = access;
    }
    /**
     * Идентификатор рабочего места пользователя.
     */
    @Expose
    @SerializedName("point")
    private String point;

    public void setPoint(String point) {
        this.point = point;
    }

    @Column(name = "point")
    public String getPoint() {
        return point;
    }
    /**
     * Название пользователя.
     */
    @Expose
    @SerializedName("name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name")
    @Override
    public String getName() {
        return name;
    }
    @Expose
    @SerializedName("adress_rs")
    private Integer adressRS;

    public void setAdressRS(Integer adressRS) {
        this.adressRS = adressRS;
    }

    @Column(name = "adress_rs")
    public Integer getAdressRS() {
        return adressRS;
    }
    @Expose
    @SerializedName("point_ext")
    private String pointExt = "";

    @Column(name = "point_ext")
    public String getPointExt() {
        return pointExt;
    }

    public void setPointExt(String pointExt) {
        this.pointExt = pointExt;
    }

    /**
     * текст для вывода на главное табло в шаблоны панели вызванного и третью колонку пользователя
     */
    @Expose
    @SerializedName("tablo_text")
    private String tabloText = "";

    /**
     * текст для вывода на главное табло в шаблоны панели вызванного и третью колонку пользователя
     *
     * @return строчеп из БД
     */
    @Column(name = "tablo_text")
    public String getTabloText() {
        return tabloText;
    }

    public void setTabloText(String tabloText) {
        this.tabloText = tabloText;
    }
    //******************************************************************************************************************
    //******************************************************************************************************************
    //************************************** Услуги юзера **************************************************************
    /**
     * Множество услуг, которые обрабатывает юзер. По наименованию услуги получаем Класс - описалово участия юзера в этой услуге/ Имя услуги - IProperty
     */
    //private QPlanServiceList serviceList = new QPlanServiceList();
    @Expose
    @SerializedName("plan")
    private List<QPlanService> planServices;

    public void setPlanServices(List<QPlanService> planServices) {
        this.planServices = planServices;
        planServiceList = new QPlanServiceList(planServices);
    }

    //@OneToMany(fetch = FetchType.EAGER)//setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id", insertable = false, nullable = false, updatable = false)
    //MOSCOW
    @Fetch(FetchMode.SELECT) // Это отсечение дублирования при джойне таблици, т.к. в QPlanService есть @OneToOne к QService, и в нем есть @OneToMany к QServiceLang - дублится по количеству переводов
    public List<QPlanService> getPlanServices() {
        return planServices;
    }
    private QPlanServiceList planServiceList = new QPlanServiceList(new LinkedList<>());

    /**
     * Только для отображения в админке в виде списка
     *
     * @return
     */
    @Transient
    public QPlanServiceList getPlanServiceList() {
        return planServiceList;
    }

    public boolean hasService(long serviceId) {
        return planServices.stream().anyMatch((qPlanService) -> (serviceId == qPlanService.getService().getId()));
    }

    public boolean hasService(QService service) {
        return hasService(service.getId());
    }

    /**
     * Найти сервис из списка обслуживаемых юзером.
     *
     * @param serviceId id искомого сервиса
     * @return
     */
    public QPlanService getPlanService(long serviceId) {
        for (QPlanService qPlanService : planServices) {
            if (serviceId == qPlanService.getService().getId()) {
                return qPlanService;
            }
        }
        throw new ServerException("Не найдена обрабатываемая услуга по ID \"" + serviceId + "\" у услуги c ID = " + id);
    }

    /**
     * Найти сервис из списка обслуживаемых юзером.
     *
     * @param service искомый сервис.
     * @return
     */
    public QPlanService getPlanService(QService service) {
        return getPlanService(service.getId());
    }

    /**
     * Добавить сервис в список обслуживаемых юзером. Помнить про ДБ.
     *
     * @param service добавляемый сервис.
     */
    public void addPlanService(QService service) {
        // в список услуг
        planServiceList.addElement(new QPlanService(service, this, 1));
        servicesCnt = planServices.size();
    }

    /**
     * Добавить сервис в список обслуживаемых юзером использую параметры. Используется при добавлении на горячую.
     *
     * @param service добавляемый сервис.
     * @param coefficient приоритет обработки
     */
    public void addPlanService(QService service, int coefficient) {
        // в список услуг
        planServiceList.addElement(new QPlanService(service, this, coefficient));
        servicesCnt = planServices.size();
    }

    /**
     * Удалить сервис из списка обслуживаемых юзером.
     *
     * @param serviceId удаляемый сервис.
     * @return
     */
    public QPlanService deletePlanService(long serviceId) {
        for (QPlanService qPlanService : planServices) {
            if (serviceId == qPlanService.getService().getId()) {
                planServiceList.removeElement(qPlanService);
                forDel.add(qPlanService);
                servicesCnt = planServices.size();
                return qPlanService;
            }
        }
        throw new ServerException("Не найдена услуга по ID \"" + serviceId + "\" у услуги c ID = " + id);
    }
    private final LinkedList<QPlanService> forDel = new LinkedList<>();

    public QPlanService deletePlanService(QService service) {
        return deletePlanService(service.getId());
    }

    public void savePlan() {
        final LinkedList<QPlanService> del = new LinkedList<>();
        forDel.stream().filter((qPlanService) -> (!QServiceTree.getInstance().hasById(qPlanService.getService().getId()))).forEach((qPlanService) -> {
            del.add(qPlanService);
        });
        forDel.removeAll(del);
        Spring.getInstance().getHt().deleteAll(forDel);
        forDel.clear();
        Spring.getInstance().getHt().saveOrUpdateAll(planServices);
    }
    /**
     * Количество услуг, которые обрабатывает юзер. // едет на коиента при логине
     */
    @Expose
    @SerializedName("services_cnt")
    private int servicesCnt = 0;

    public void setServicesCnt(int servicesCnt) {
        this.servicesCnt = servicesCnt;
    }

    /**
     * Количество услуг, которые обрабатывает юзер. // едет на коиента при логине
     *
     * @return
     */
    @Transient
    public int getServicesCnt() {
        return servicesCnt;
    }
    /**
     * Customer, который попал на обработку к этому юзеру. При вызове следующего, первый в очереди кастомер, выдерается из этой очереди совсем и попадает сюда.
     * Сдесь он живет и переживает все интерпритации, которые с ним делает юзер. При редиректе в другую очередь юзером, данный кастомер отправляется в другую
     * очередь, возможно, с другим приоритетом, а эта ссылка становится null.
     */
    private QCustomer customer = null;

    /**
     * Назначить юзеру кастомера в работу. Типа текущий в работе. Если устанавливаем NULL, то это значить нужно замочить текущего, наверное закончили работать с
     * ним.
     *
     * @param customer кастомер, который идет в рвботу, типа текущий. Может быть NULL для уничтожения текущего.
     */
    public void setCustomer(QCustomer customer) {
        // небыло и не ставим
        if (customer == null && getCustomer() == null) {
            return;
        }
        // был кастомер у юзера и убираем его
        if (customer == null && getCustomer() != null) {
            // если убирается кастомер, то надо убрать признак юзера, который работает с кастомером
            finalizeCustomer();
            if (getCustomer().getUser() != null) {
                getCustomer().setUser(null);
            }
            // раз юзера убрали, то и время начала работы этого юзера тож убирать
            if (getCustomer().getStartTime() != null) {
                getCustomer().setStartTime(null);
            }
            parallelCustomers.remove(getCustomer().getId()); // он же в толпе параллельных
        } else {
            // иначе кастомеру, определившимуся к юзеру, надо поставить признак работы с опред. юзером.
            if (customer == null) {
                throw new IllegalArgumentException("Customer is null! It is impossible!");
            }
            customer.setUser(this);
            initCustomer(customer);
            parallelCustomers.put(customer.getId(), customer);
        }
        this.customer = customer;
    }

    @Transient
    public QCustomer getCustomer() {
        return customer;
    }

    /**
     * Это чтоб осталась инфа после завершения работ с кастомером. Нужно для нормативов и статистики сиюминутной
     */
    public void finalizeCustomer() {
        shadow = new Shadow(customer);
        shadow.setStartTime(null);
    }

    /**
     * Это чтоб осталась инфа сразу после вызова кастомера. Нужно для нормативов и статистики сиюминутной
     *
     * @param cust
     */
    public void initCustomer(QCustomer cust) {
        shadow = new Shadow(cust);
        shadow.setFinTime(null);
    }

    /**
     * Может быть параллельный прием. Нужно хранить всех, кого вызвали. Текущий кастомер в работе остается как есть в customer, а толпа параллельных хранится
     * тут. Будет происходить переключение на текущего в работу. Браться он будет отсюда. Переключиться можно быдет либо отдельной командой, либо указав ID
     * кастомера для которого прислана команда на сервер. Вообще, это типа отложенных, но только не надо отдельно вызывать его, просто переключиться на него в
     * customer на прямую и выполнить команду. На кого конкретно переключить приедет либо как ID в команде, либо отдельной командой.
     */
    private final LinkedHashMap<Long, QCustomer> parallelCustomers = new LinkedHashMap<>();

    @Transient
    public LinkedHashMap<Long, QCustomer> getParallelCustomers() {
        return parallelCustomers;
    }

    /**
     * Типо не набрасывать сюда посетителей при маршрутизации в списке услуг.
     */
    @Expose
    @SerializedName("pause")
    public Boolean pause = false;

    @Transient
    public Boolean isPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }
    @Expose
    @SerializedName("shadow")
    private Shadow shadow = null;

    @Transient
    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public static class Shadow {

        public Shadow() {
        }

        public Shadow(QCustomer oldCostomer) {
            this.oldCustomer = oldCostomer;
            this.idOldCustomer = oldCostomer.getId();
            this.idOldService = oldCostomer.getService().getId();
            this.oldService = oldCostomer.getService();
            this.oldNom = oldCostomer.getNumber();
            this.oldPref = oldCostomer.getPrefix();
            this.inputData = oldCostomer.getInput_data();
            this.finTime = new Date();
            this.startTime = new Date();
            if (oldCostomer.getState() == null) {
                customerState = CustomerState.STATE_INVITED;
            } else {
                customerState = oldCostomer.getState();
            }
        }
        private QService oldService;
        private QCustomer oldCustomer;
        @Expose
        @SerializedName("id_old_service")
        private Long idOldService;
        @Expose
        @SerializedName("id_old_customer")
        private Long idOldCustomer;
        @Expose
        @SerializedName("old_nom")
        private int oldNom;
        @Expose
        @SerializedName("old_pref")
        private String oldPref;
        @Expose
        @SerializedName("old_fin_time")
        private Date finTime;
        @Expose
        @SerializedName("old_start_time")
        private Date startTime;
        @Expose
        @SerializedName("inp_data")
        private String inputData;

        public String getInputData() {
            return inputData;
        }

        public void setInputData(String inputData) {
            this.inputData = inputData;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Long getIdOldCustomer() {
            return idOldCustomer;
        }

        public void setIdOldCustomer(Long idOldCustomer) {
            this.idOldCustomer = idOldCustomer;
        }

        public Long getIdOldService() {
            return idOldService;
        }

        public void setIdOldService(Long idOldService) {
            this.idOldService = idOldService;
        }

        public QCustomer getOldCustomer() {
            return oldCustomer;
        }

        public void setOldCustomer(QCustomer oldCustomer) {
            this.oldCustomer = oldCustomer;
        }

        public Date getFinTime() {
            return finTime;
        }

        public void setFinTime(Date finTime) {
            this.finTime = finTime;
        }

        public int getOldNom() {
            return oldNom;
        }

        public void setOldNom(int oldNom) {
            this.oldNom = oldNom;
        }

        public String getOldPref() {
            return oldPref;
        }

        public void setOldPref(String oldPref) {
            this.oldPref = oldPref;
        }

        public QService getOldService() {
            return oldService;
        }

        public void setOldService(QService oldService) {
            this.oldService = oldService;
        }
        @Expose
        @SerializedName("old_cust_state")
        private CustomerState customerState;

        public CustomerState getCustomerState() {
            return customerState;
        }

        public void setCustomerState(CustomerState customerState) {
            this.customerState = customerState;
        }
    }
}
