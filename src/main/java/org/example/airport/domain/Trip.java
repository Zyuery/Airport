package org.example.airport.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "tripnumber", unique = true, nullable = false)
    private String tripNumber;
    public String getTripNumber() {
        return tripNumber;
    }
    public void setTripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
    }
    @Column(name = "starttime", nullable = false)
    private Date startTime;
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Column(name = "endtime", nullable = false)
    private Date endTime;
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    @Column(name = "startlocation", nullable = false)
    private String startLocation;
    public String getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
    @Column(name = "endlocation", nullable = false)
    private String endLocation;
    public String getEndLocation() {
        return endLocation;
    }
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
    @Column(name = "flightnumber", nullable = false)
    private String flightNumber;
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    @Column(name = "airline", nullable = false)
    private String airline;
    public String getAirline() {
        return airline;
    }
    public void setAirline(String airline) {
        this.airline = airline;
    }
    @Column(name = "cabinclass", nullable = false)
    @Enumerated(EnumType.STRING)
    private CabinClass cabinClass;
    public CabinClass getCabinClass() {
        return cabinClass;
    }
    public void setCabinClass(CabinClass cabinClass) {
        this.cabinClass = cabinClass;
    }
    // 定义枚举类型
    public enum CabinClass {
        economy, business, first;
        // 自定义的从字符串转换为枚举常量的方法
        public static CabinClass fromString(String cabinClass) {
            // 遍历所有的枚举常量，并忽略大小写进行比较
            for (CabinClass cabin : CabinClass.values()) {
                if (cabin.name().equalsIgnoreCase(cabinClass)) {
                    return cabin;
                }
            }
            // 如果没有匹配项，抛出异常
            throw new IllegalArgumentException("No enum constant " + CabinClass.class.getCanonicalName() + "." + cabinClass);
        }
    }

    @Column(name = "userid", nullable = false)
    private int userId;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    // 定义用户与行程之间的关
    // 一个用户可以有多条行程，一个行程只能属于一个用户
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
